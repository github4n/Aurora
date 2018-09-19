package me.aurora.config.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 将项目路径装入session
 * @author 郑杰
 * @date 2018/08/20 13:41:13
 */
@WebFilter(filterName = "pathFilter",urlPatterns = "/*")
public class PathFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest=(HttpServletRequest)request;
        /**
         *request.getSchema()可以返回当前页面使用的协议
         *request.getServerName()可以返回当前页面所在的服务器的名字
         *request.getServerPort()可以返回当前页面所在的服务器使用的端口
         *request.getContextPath()可以返回当前页面所在的应用的名字
         *这四个拼装起来，就是当前应用的跟路径了
         */
//        +":"+request.getServerPort()
        String path=request.getScheme()+"://"+request.getServerName();
        HttpSession httpSession= httpServletRequest.getSession();
        httpServletRequest.getSession().setAttribute("path",path);
        chain.doFilter(httpServletRequest, response);
    }
    @Override
    public void destroy() {

    }
}