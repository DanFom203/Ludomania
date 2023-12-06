package ru.itis.ludomania.servlets.avatar;

import ru.itis.ludomania.exceptions.CustomException;
import ru.itis.ludomania.model.FileInfo;
import ru.itis.ludomania.services.FilesService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@WebServlet("/files/*")
public class FileDownloadServlet extends HttpServlet {
    private FilesService filesService;
    @Override
    public void init(ServletConfig config) {
        this.filesService = (FilesService) config.getServletContext().getAttribute("filesService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UUID fileId;
        try {
            String fileIdString = request.getRequestURI().substring(20);
            fileId = UUID.fromString(fileIdString);
        } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
            response.setStatus(400);
            response.getWriter().println("Wrong request");
            return;
        }

        try {
            FileInfo fileInfo = filesService.getFileInfo(fileId);
            response.setContentType(fileInfo.getType());
            response.setContentLength(fileInfo.getSize().intValue());
            response.setHeader("Content-Disposition", "filename=\"" + fileInfo.getOriginalFileName() + "\"");
            filesService.readFileFromStorage(fileId, response.getOutputStream());
            response.flushBuffer();
        } catch (CustomException e) {
            response.setStatus(404);
            response.getWriter().println(e.getMessage());
        }
    }
}
