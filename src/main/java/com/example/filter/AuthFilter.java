package com.example.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.logging.Logger;

@WebFilter("/user/*")
public class AuthFilter implements Filter {
    private static final Logger log = Logger.getLogger(AuthFilter.class.getName());
    private static final String LOGIN_JSP = "/login.jsp";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;

        if (req.getSession().getAttribute("user") == null) {
            try {
                req.getRequestDispatcher(LOGIN_JSP).forward(servletRequest, servletResponse);
            } catch (ServletException | IOException e) {
                log.info("Failed to forward Get request to page: " + LOGIN_JSP + " " + e.getMessage());
            }

        } else filterChain.doFilter(servletRequest, servletResponse);
    }

}