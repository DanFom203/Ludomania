package ru.itis.ludomania.servlets;

import ru.itis.ludomania.model.Case;
import ru.itis.ludomania.services.impl.OpenCaseService;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/main")
public class MainPageServlet extends HttpServlet {
    OpenCaseService openCaseService;
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        openCaseService = (OpenCaseService) getServletContext().getAttribute("openCaseService");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Case> casesList = openCaseService.getCasesList();

        request.setAttribute("casesList", casesList);
        request.setAttribute("imagePath", "resources/img/Case");
        request.getRequestDispatcher("mainpage.ftl").forward(request, response);
    }

}