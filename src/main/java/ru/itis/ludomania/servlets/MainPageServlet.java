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

@WebServlet("/main_page")
public class MainPageServlet extends HttpServlet {
    OpenCaseService openCaseService;
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        openCaseService = (OpenCaseService) getServletContext().getAttribute("auctionService");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Case> casesList = openCaseService.getCasesList();

        request.setAttribute("casesList", casesList);
        request.getRequestDispatcher("mainpage.ftl").forward(request, response);
    }
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        // Логика обработки запроса для страницы "Главная"
//        // В этом примере просто возвращаем HTML страницу с формой открытия кейсов
//        response.setContentType("text/html;charset=UTF-8");
//        response.getWriter().println("<html><head><title>Главная</title></head><body>");
//        response.getWriter().println("<h1>Открытие кейсов</h1>");
//        response.getWriter().println("<form action=\"open_case\" method=\"post\">");
//        response.getWriter().println("<input type=\"submit\" value=\"Открыть кейс\">");
//        response.getWriter().println("</form>");
//        response.getWriter().println("</body></html>");
//    }
}