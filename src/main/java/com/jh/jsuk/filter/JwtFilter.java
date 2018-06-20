package com.jh.jsuk.filter;

import com.jushang.entity.AdminLog;
import com.jushang.entity.ParentUser;
import com.jushang.entity.jwt.JwtParam;
import com.jushang.service.AdminLogService;
import com.jushang.service.DistributionUserService;
import com.jushang.service.ShopUserService;
import com.jushang.service.UserService;
import com.jushang.utils.FastJsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;

@WebFilter
@Order(2)
public class JwtFilter implements Filter {
    @Autowired
    private ShopUserService shopUserService;
    @Autowired
    private DistributionUserService distributionUserService;
    @Autowired
    private UserService userService;
    @Autowired
    private AdminLogService adminLogService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //System.out.println("进入JWT_filter");

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String servletPath = ((HttpServletRequest) servletRequest).getServletPath();
        String[] split = servletPath.split("\\.");
        if (split.length > 0 && httpServletRequest.getSession().getAttribute("adminUserName") != null && httpServletRequest.getParameterMap().size() != 0) {
            if (!"js".equals(split[split.length - 1]) &&
                    !"css".equals(split[split.length - 1]) &&
                    !"ico".equals(split[split.length - 1]) &&
                    !"html".equals(split[split.length - 1]) &&
                    !"woff".equals(split[split.length - 1]) &&
                    !"ttf".equals(split[split.length - 1]) &&
                    !"png".equals(split[split.length - 1]) &&
                    !"jpg".equals(split[split.length - 1]) &&
                    !"img".equals(split[split.length - 1]) &&
                    !"gif".equals(split[split.length - 1])) {

                AdminLog al = new AdminLog();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String user = httpServletRequest.getSession().getAttribute("adminUserName").toString();
                al.setUserName(user);
                String paramJson = FastJsonUtil.toJson(httpServletRequest.getParameterMap());
                al.setParam(paramJson);
                String nowTime = sdf.format(System.currentTimeMillis());
                al.setServletPath(servletPath);
                al.setOperationTime(nowTime);
                al.insert();
            }
        }
        if (servletPath.indexOf("/login") != -1
                || servletPath.indexOf("swagger") != -1
                || servletPath.indexOf("bindAccount") != -1
                || servletPath.indexOf("editPasswordByCode") != -1
                || servletPath.indexOf("agreement") != -1
                || servletPath.indexOf("aliPayNotify") != -1
                || servletPath.indexOf("wxPayNotify") != -1
                || servletPath.indexOf("keepSubmit") != -1
                || servletPath.indexOf("/sms/") != -1
                || servletPath.indexOf("upload") != -1
                || servletPath.indexOf(".js") != -1
                || servletPath.indexOf(".css") != -1
                || servletPath.indexOf(".woff") != -1
                || servletPath.indexOf("/ui/") != -1
                || servletPath.indexOf("shopExamine/submit") != -1
                || servletPath.indexOf("provinces") != -1
                || servletPath.indexOf("citys") != -1
                || servletPath.indexOf("areas") != -1
                || servletPath.indexOf("version") != -1
                || servletPath.indexOf("images") != -1
                || servletPath.indexOf("register") != -1
                || servletPath.indexOf("test") != -1
                || servletPath.indexOf("v2") != -1
                || servletPath.indexOf("configuration") != -1
                || servletPath.indexOf("ui") != -1
                || servletPath.indexOf("/user/invitationRegister") != -1
                || servletPath.indexOf("/shop/edit") != -1
                //|| servletPath.indexOf("/distributionUser/edit") != -1
                || servletPath.indexOf("/provinces/list") != -1
                || servletPath.indexOf("/citys/list") != -1
                || servletPath.indexOf("/areas/list") != -1
                || servletPath.indexOf("uiGetInfo") != -1
                || servletPath.indexOf("/dict/") != -1
                || servletPath.indexOf("/sys/") != -1

                ////////////////////////////////////////////////
                // 以下是方便测试,可能会出现效验bug
                ////////////////////////////////////////////////
                // 首页模块
                || servletPath.indexOf("/getAll") != -1
                || servletPath.indexOf("/getNiceChoose") != -1
                || servletPath.indexOf("/getMoreInfo") != -1
                || servletPath.indexOf("/getVipShop") != -1
                || servletPath.indexOf("/hotel") != -1
                // 商家店铺相关
                || servletPath.indexOf("/moreShopList") != -1
                || servletPath.indexOf("/show") != -1
                || servletPath.indexOf("/getShopCategoryByShopId") != -1
                // 专题精选
                || servletPath.indexOf("/specialTheme") != -1
                // 模块相关
                || servletPath.indexOf("/modularPortal") != -1
                // 便捷生活
                || servletPath.indexOf("/lifeClass") != -1
                // 商家端-获取分类列表
                || servletPath.indexOf("/getModularList") != -1

            ////////////////////////////////////////////////
            //方便ios上架开放接口 TODO 上架后最好注掉
            //|| servletPath.indexOf("/banner") != -1
            //|| servletPath.indexOf("/goodsLabel") != -1
            //|| servletPath.indexOf("/shop") != -1
            //|| servletPath.indexOf("/advertisement") != -1
            //|| servletPath.indexOf("/hotAdvisory") != -1
            //|| servletPath.indexOf("/shoppingCart") != -1
            //|| servletPath.indexOf("/evaluate") != -1
            //|| servletPath.indexOf("/shopGoods") != -1
            //|| servletPath.indexOf("/wish") != -1
            ///////////////////////////////////////////////
                )


        {

            filterChain.doFilter(servletRequest, servletResponse);

        } else {
            FilteredRequest request = new FilteredRequest(servletRequest, new HashMap<String, String[]>(httpServletRequest.getParameterMap()));

            //filterChain.doFilter(request,response);

            System.out.println(servletPath);
            JwtParam jwtParam = request.getJwtParam();
            if (null != jwtParam) {
                try {
                    ParentUser user = null;
                    switch (jwtParam.getLoginType()) {
                        case 1:
                            user = shopUserService.selectById(jwtParam.getUserId());
                            break;
                        case 2:
                            user = distributionUserService.selectById(jwtParam.getUserId());
                            break;
                        case 3:
                            user = userService.selectById(jwtParam.getUserId());
                            break;
                    }
                    if (user.getCanUse() == 0) {
                        RequestDispatcher dispatcher = request.getRequestDispatcher("/noLogin");
                        dispatcher.forward(request, response);
                        response.setStatus(2002);
                    }

                    if (user != null) {
                        System.out.println(user.getLastLoginTime().getTime() + "===========" + Math.round((double) jwtParam.getLoginTime().getTime() / 1000)
                                * 1000);
                        if (user.getLastLoginTime().getTime() == Math.round((double) jwtParam.getLoginTime().getTime() / 1000) * 1000) {
                            System.out.println("认证成功");
                            filterChain.doFilter(request, response);
                        } else {
                            System.out.println("认证过期");
                            RequestDispatcher dispatcher = request.getRequestDispatcher("/overdue");
                            dispatcher.forward(request, response);
                        }
                    } else {
                        //没有找到该用户
                        System.out.println("没有找到该用户");
                        RequestDispatcher dispatcher = request.getRequestDispatcher("/noFound");
                        dispatcher.forward(request, response);
                    }
                } catch (Exception e) {
                    //认证过期  这里当作认证未知错误处理
                    System.out.println("认证过期2");
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/overdue");
                    dispatcher.forward(request, response);
                }
            } else {
                //认证失败
                System.out.println(servletPath);
                System.out.println("认证失败");
                RequestDispatcher dispatcher = request.getRequestDispatcher("/noLogin");
                dispatcher.forward(request, response);
            }
        }
    }

    @Override
    public void destroy() {

    }
}
