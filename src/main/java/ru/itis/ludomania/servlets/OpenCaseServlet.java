package ru.itis.ludomania.servlets;

import ru.itis.ludomania.model.Case;
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

@WebServlet("/openCasePage")
public class OpenCaseServlet extends HttpServlet {
    OpenCaseService openCaseService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        openCaseService = (OpenCaseService) getServletContext().getAttribute("openCaseService");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int caseId = Integer.parseInt(request.getParameter("caseId"));
        Case selectedCase = openCaseService.getCaseById(caseId);

        List<WeaponSkin> skinsList = openCaseService.getSkinsForCase(caseId);
        List<WeaponSkin> randomSkins = openCaseService.getRandomSkins(skinsList);

        request.setAttribute("selectedCase", selectedCase);
        request.setAttribute("randomSkins", randomSkins);
        request.setAttribute("caseImagePath", "resources/img/Case" + caseId + ".jpeg");
        request.setAttribute("skinImagePath", "resources/img/skinsImages/");
        request.setAttribute("skinsList", skinsList);
        request.getRequestDispatcher("open_case_page.ftl").forward(request, response);
    }
}
