package ru.itis.ludomania.servlets;

import ru.itis.ludomania.dto.SignUpForm;
import ru.itis.ludomania.dto.UserDto;
import ru.itis.ludomania.exceptions.CustomException;
import ru.itis.ludomania.services.AuthorizationService;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;

@WebServlet("/sign-up")
public class SignUpServlet extends HttpServlet {
    private AuthorizationService authorizationService;

    @Override
    public void init(ServletConfig config) {
        ServletContext servletContext = config.getServletContext();
        authorizationService = (AuthorizationService) servletContext.getAttribute("authorizationService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("sign-up.ftl").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDto user;
        try {
            if ((req.getParameter("birthdate").isEmpty())) {
                String mess = "Birthdate cannot be null";
                req.setAttribute("errorMessage", mess);
                throw new CustomException(mess);
            }
            SignUpForm form = SignUpForm.builder()
                    .firstName(req.getParameter("firstName"))
                    .lastName(req.getParameter("lastName"))
                    .email(req.getParameter("email"))
                    .password(req.getParameter("password"))
                    .birthdate(
                            Date.valueOf(req.getParameter("birthdate"))
                    )
                    .build();
            user = authorizationService.signUp(form);
        } catch (CustomException e) {
            req.setAttribute("errorMessage", e.getMessage());
            req.getRequestDispatcher("sign-up.ftl").forward(req, resp);
            return;
        }
        HttpSession session = req.getSession(true);
        session.setAttribute("user", user);
        resp.sendRedirect("profile");
    }
}
