package ru.kpfu.itis.lobanov.controller.filter;

import ru.kpfu.itis.lobanov.service.impl.UserServiceImpl;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;
import java.io.IOException;

@WebFilter(filterName = "authenticationFilter", urlPatterns = "/*")
public class AuthenticationFilter extends HttpFilter {
    private UserServiceImpl userService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        super.init(filterConfig);
        userService = (UserServiceImpl) getServletContext().getAttribute("userService");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        String uri = httpServletRequest.getRequestURI();
        HttpSession httpSession = httpServletRequest.getSession(false);
        boolean isAuth = userService.isAuthorized(httpServletRequest, httpServletResponse);
        if (!isAuth && httpSession == null && !uri.contains("registration") && !uri.contains("login")) {
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/login");
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
}
