//package com.jh.jsuk.filter;
//
//import com.jushang.entity.SysUser;
//import org.springframework.core.annotation.Order;
//
//import javax.servlet.*;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import java.io.IOException;
//
//@WebFilter
//@Order(1)
//public class LoginFilter implements Filter {
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//
//    }
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        //System.out.println("进入login_filter");
//        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
//        HttpServletResponse response = (HttpServletResponse) servletResponse;
//        String servletPath = ((HttpServletRequest) servletRequest).getServletPath();
//        if (servletPath.indexOf("/login") != -1 || servletPath.indexOf("/ui/register") != -1) {
//            filterChain.doFilter(servletRequest, servletResponse);
//        } else if (servletPath.indexOf("/ui/") != -1) {
//            HttpSession session = httpServletRequest.getSession();
//            SysUser user = (SysUser) session.getAttribute("user");
//            if (user != null) {
//                filterChain.doFilter(servletRequest, servletResponse);
//            } else {
//                RequestDispatcher dispatcher = httpServletRequest.getRequestDispatcher("/ui/login");
//                dispatcher.forward(httpServletRequest, response);
//            }
//        } else {
//            filterChain.doFilter(servletRequest, servletResponse);
//        }
//    }
//
//    @Override
//    public void destroy() {
//
//    }
//}
