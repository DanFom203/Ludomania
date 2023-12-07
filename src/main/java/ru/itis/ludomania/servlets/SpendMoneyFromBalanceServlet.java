package ru.itis.ludomania.servlets;

import ru.itis.ludomania.dto.UserDto;
import ru.itis.ludomania.exceptions.CustomException;
import ru.itis.ludomania.services.impl.OpenCaseService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@WebServlet("/balance/update")
public class SpendMoneyFromBalanceServlet extends HttpServlet {
    OpenCaseService openCaseService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        openCaseService = (OpenCaseService) getServletContext().getAttribute("openCaseService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String referer = req.getHeader("referer");

        // Сохраняем URL в сессии
        req.getSession().setAttribute("previousPage", referer);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            UUID userId = (UUID) req.getSession(false).getAttribute("userId");
            UserDto user = (UserDto) req.getSession(false).getAttribute("user");
            String casePriceString = req.getParameter("casePrice");
            if (casePriceString.length() > 3) {
                casePriceString = casePriceString.replace(" ", "");
            }
            double casePrice = Double.parseDouble(casePriceString);
            user.setBalance(user.getBalance() - casePrice);

            openCaseService.updateUserBalance(userId, casePrice, "minus");
            req.setAttribute("user", user);
            String previousPage = (String) req.getSession().getAttribute("previousPage");

            if (previousPage != null && !previousPage.isEmpty()) {
                resp.sendRedirect(previousPage);
            } else {
                resp.sendRedirect(req.getContextPath());
            }
        } catch (NullPointerException | NumberFormatException e) {
            throw new CustomException(e.getLocalizedMessage());
        }
    }
}
