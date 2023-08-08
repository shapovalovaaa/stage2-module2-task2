package com.example.servlet;

import com.example.Users;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.logging.Logger;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final Logger log = Logger.getLogger(LoginServlet.class.getName());
    private static final String LOGIN_JSP = "/login.jsp";
    private static final String HELLO_JSP = "/user/hello.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        String redirectPage;

        if (req.getSession().getAttribute("user") == null) {
            redirectPage = LOGIN_JSP;
        } else redirectPage = HELLO_JSP;

        try {
            resp.sendRedirect(redirectPage);
        } catch (IOException e) {
            log.info("Failed to redirect Post request to page: " + redirectPage + " " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        String login = req.getParameter("login");
        String pass = req.getParameter("password");
        HttpSession session = req.getSession();

        if (Users.getInstance().getUsers().contains(login) && !pass.isEmpty()) {
            session.setAttribute("user", login);
            try {
                resp.sendRedirect(HELLO_JSP);
            } catch (IOException e) {
                log.info("Failed to redirect Post request to page: " + HELLO_JSP + " " + e.getMessage());
            }
        } else try {
            req.getRequestDispatcher(LOGIN_JSP).forward(req, resp);
        } catch (ServletException | IOException e) {
            log.info("Failed to forward Post request to page: " + LOGIN_JSP + " " + e.getMessage());
        }

    }
}