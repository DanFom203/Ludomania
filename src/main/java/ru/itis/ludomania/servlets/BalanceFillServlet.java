package ru.itis.ludomania.servlets;

import ru.itis.ludomania.dto.UserDto;
import ru.itis.ludomania.model.User;
import ru.itis.ludomania.services.impl.OpenCaseService;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@WebServlet("/balance/donate")
public class BalanceFillServlet extends HttpServlet {
    OpenCaseService openCaseService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        openCaseService = (OpenCaseService) getServletContext().getAttribute("openCaseService");
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            UUID userId = (UUID) req.getSession(false).getAttribute("userId");
            UserDto user = (UserDto) req.getSession(false).getAttribute("user");
            double balance = Double.parseDouble(req.getParameter("balance"));
            user.setBalance(user.getBalance() + balance);

            openCaseService.updateUserBalance(userId, balance, "plus");
            req.setAttribute("user", user);
            resp.sendRedirect( req.getContextPath() + "/profile");
        } catch (NullPointerException | NumberFormatException e) {
            resp.sendRedirect( req.getContextPath() + "/profile");
        }
    }
}
