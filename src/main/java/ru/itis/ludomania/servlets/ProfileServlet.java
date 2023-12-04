package ru.itis.ludomania.servlets;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.itis.ludomania.model.WeaponSkin;
import ru.itis.ludomania.services.impl.OpenCaseService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {
    OpenCaseService openCaseService;
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        openCaseService = (OpenCaseService) getServletContext().getAttribute("openCaseService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Object user = req.getSession().getAttribute("user");
        UUID userId = (UUID) req.getSession(false).getAttribute("userId");

        List<WeaponSkin> usersSkins = openCaseService.getUsersSkins(userId);
        req.setAttribute("user", user);
        req.setAttribute("usersSkins", usersSkins);
        req.setAttribute("skinImagePath", "resources/img/skinsImages/");
        req.getRequestDispatcher("profile.ftl").forward(req, resp);
    }

}


