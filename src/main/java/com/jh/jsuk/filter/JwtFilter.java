package com.jh.jsuk.filter;


import com.jh.jsuk.common.SessionListener;
import com.jh.jsuk.conf.Session;
import com.jh.jsuk.entity.Log;
import com.jh.jsuk.entity.jwt.JwtParam;
import com.jh.jsuk.envm.UserType;
import com.jh.jsuk.exception.OverdueException;
import com.jh.jsuk.utils.EnumUitl;
import com.jh.jsuk.utils.FastJsonUtil;
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

//    @Autowired
//    private ManagerUserService managerUserService;
//    @Autowired
//    private DistributionUserService distributionUserService;
//    @Autowired
//    private UserService userService;
//    @Autowired
//    private LogService adminLogService;

//    @Autowired
//    Session session;

    @Autowired
    SessionListener sessionListener;

    @Autowired
    Session session;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException,
        ServletException {
        //System.out.println("进入JWT_filter");

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String servletPath = ((HttpServletRequest) servletRequest).getServletPath();
        String[] split = servletPath.split("\\.");
        if (split.length > 0 && httpServletRequest.getSession().getAttribute("adminUserName") != null
            && httpServletRequest.getParameterMap().size() != 0) {
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

                Log al = new Log();
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
            || servletPath.indexOf("favicon") != -1
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
            || servletPath.indexOf("/webhooks") != -1

            ////////////////////////////////////////////////
            // 以下是方便测试,可能会出现效验bug
            ////////////////////////////////////////////////
            // 获取首页相关信息-上部分
            || servletPath.indexOf("/getAll") != -1
            // 获取首页相关信息-下部分
            || servletPath.indexOf("/getAllBelow") != -1
            || servletPath.indexOf("/getNiceChoose") != -1
            || servletPath.indexOf("/getMoreInfo") != -1
            || servletPath.indexOf("/getVipShop") != -1
            || servletPath.indexOf("/getIsRecommend") != -1
            // 限时秒杀相关
            || servletPath.indexOf("/getKillTime") != -1
            || servletPath.indexOf("/findKillShopGoods") != -1
            // 商品相关API
            || servletPath.indexOf("/getShopGoodsByAttributeId") != -1
            || servletPath.indexOf("/getShopGoodsBy") != -1
            || servletPath.indexOf("/getShopGoodsByCategoryId") != -1
            || servletPath.indexOf("/getShopGoodsOnCategoryBy") != -1
            || servletPath.indexOf("/getShopGoodsById") != -1
            || servletPath.indexOf("/getShopListByLike") != -1
            || servletPath.indexOf("/getShopGoodsByServiceOrPrice") != -1
            || servletPath.indexOf("/getShopList") != -1
            // 模块相关API
            || servletPath.indexOf("/getShopAndGoodsByModular") != -1
            || servletPath.indexOf("/shopListByModularId") != -1
            || servletPath.indexOf("/shopGoodsListByModularId") != -1
            || servletPath.indexOf("/getModularList") != -1
            // 快递跑腿相关API
            || servletPath.indexOf("/expressRunBanner") != -1
            // 商品类型-品牌相关操作
            || servletPath.indexOf("/getBrandByCategoryId") != -1
            || servletPath.indexOf("/getShopGoodsByBrandId") != -1
            // 地址定位选择API
            || servletPath.indexOf("/getOpenCityList") != -1
            // 商户端/平台用户相关
            || servletPath.indexOf("/resultCode") != -1
            || servletPath.indexOf("/editPassword") != -1
            || servletPath.indexOf("getQrCode") != -1
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
        ) {

            filterChain.doFilter(servletRequest, servletResponse);

        } else {
            FilteredRequest request = new FilteredRequest(servletRequest, new HashMap<String, String[]>(httpServletRequest.getParameterMap()));
            //filterChain.doFilter(request,response);
            try {
                // 避免在jwt解析失败时手机端报其他异常
                request.parseJwt();
//            System.out.println(servletPath);
                JwtParam jwtParam = request.getJwtParam();
                if (null != jwtParam) {
                    try {
                        Integer userId = jwtParam.getUserId();
                        UserType userType = EnumUitl.toEnum(UserType.class, jwtParam.getLoginType());
                        if (!session.isValid()) {
                            sessionListener.updateSession(userId, userType);
                        }
                        filterChain.doFilter(request, response);
//                        if (!session.canUse()) {
//                            RequestDispatcher dispatcher = request.getRequestDispatcher("/noLogin");
//                            dispatcher.forward(request, response);
//                            response.setStatus(2002);
//                        }
////                        if (user != null) {
//                            System.out.println(.getLastLoginTime().getTime() + "===========" + Math.round((double) jwtParam.getLoginTime().getTime
//                                () / 1000) * 1000);
//                            if (user.getLastLoginTime().getTime() == Math.round((double) jwtParam.getLoginTime().getTime() / 1000) * 1000) {
//                                System.out.println("认证成功");
//                                filterChain.doFilter(request, response);
//                            } else {
//                                System.out.println("认证过期");
////                                RequestDispatcher dispatcher = request.getRequestDispatcher("/overdue");
////                                dispatcher.forward(request, response);
//                                filterChain.doFilter(request, response);
//                            }
//                        } else {
//                            //没有找到该用户
//                            System.out.println("没有找到该用户");
//                            RequestDispatcher dispatcher = request.getRequestDispatcher("/noFound");
//                            dispatcher.forward(request, response);
//                        }
                    } catch (Exception e) {
                        //认证过期  这里当作认证未知错误处理
                        System.out.println("认证过期2");
                        e.printStackTrace();
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
            } catch (OverdueException e) {
//                e.printStackTrace();
//                System.out.println("认证过期");
                RequestDispatcher dispatcher = request.getRequestDispatcher("/overdue");
                dispatcher.forward(request, response);
            }
        }
    }

    @Override
    public void destroy() {

    }
}
