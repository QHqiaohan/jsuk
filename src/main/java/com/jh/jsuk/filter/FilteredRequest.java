package com.jh.jsuk.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jh.jsuk.conf.Constant;
import com.jh.jsuk.entity.jwt.JwtParam;
import com.jh.jsuk.exception.OverdueException;
import com.jh.jsuk.utils.JwtHelper;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

@Slf4j
public class FilteredRequest extends HttpServletRequestWrapper {
    private String jwt = null;
    private Map<String, String[]> params;
    private JwtParam jwtParam;

    public FilteredRequest(ServletRequest req, Map<String, String[]> newParams) {
        super((HttpServletRequest) req);
        this.params = newParams == null ? new HashMap<String, String[]>() : newParams;
//        System.out.println(req.getClass());
        this.jwt = ((HttpServletRequest) req).getHeader(Constant.JWT_HEADER);
    }

    /**
     * 解析 jwt 避免在解析失败是整个 整个请求都为空
     * @throws OverdueException
     */
    public void parseJwt() throws OverdueException {
        if (jwt != null) {
            JwtHelper jwtHelper = new JwtHelper();
            Claims claims = null;
            try {
                claims = jwtHelper.parseJWT(jwt);
                String subject = claims.getSubject();
                ObjectMapper objectMapper = new ObjectMapper();
                JwtParam jwtParam = null;
//                try {
                    jwtParam = objectMapper.readValue(subject, JwtParam.class);
                    this.jwtParam = jwtParam;
                    params.put("userId", new String[]{jwtParam.getUserId().toString()});
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
            } catch (Exception e) {
//                e.printStackTrace();
                log.error(e.getLocalizedMessage());
                throw new OverdueException();
            }
        }
    }

    public JwtParam getJwtParam() {
        return jwtParam;
    }

    public String getJwt() {
        return jwt;
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return params;
    }

    @Override
    public String getParameter(String name) {
        String result = "";
        Object v = params.get(name);
        if (v == null) {
            result = null;
        } else if (v instanceof String[]) {
            String[] strArr = (String[]) v;
            if (strArr.length > 0) {
                result = strArr[0];
            } else {
                result = null;
            }
        } else if (v instanceof String) {
            result = (String) v;
        } else {
            result = v.toString();
        }

        return result;
    }

    @Override
    public Enumeration getParameterNames() {
        return new Vector(params.keySet()).elements();
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] result = null;

        Object v = params.get(name);
        if (v == null) {
            result = null;
        } else if (v instanceof String[]) {
            result = (String[]) v;
        } else if (v instanceof String) {
            result = new String[]{(String) v};
        } else {
            result = new String[]{v.toString()};
        }

        return result;
    }
}
