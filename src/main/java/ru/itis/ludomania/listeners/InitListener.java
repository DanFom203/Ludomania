package ru.itis.ludomania.listeners;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import ru.itis.ludomania.exceptions.CustomException;
import ru.itis.ludomania.repositories.*;
import ru.itis.ludomania.repositories.impl.*;
import ru.itis.ludomania.services.AuthorizationService;
import ru.itis.ludomania.services.FilesService;
import ru.itis.ludomania.services.PasswordEncoder;
import ru.itis.ludomania.services.UserMapper;
import ru.itis.ludomania.services.impl.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.SQLException;

@WebListener
public class InitListener implements ServletContextListener {
    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "{password_hidden}";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/ludomania_project";
    private static final String DB_DRIVER = "org.postgresql.Driver";
    private static final String IMAGES_STORAGE_PATH = "C:\\Users\\Danii\\Downloads\\Ludomania\\src\\main\\webapp\\resources\\img\\avatarImages\\";

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println("Postgresql Driver not found.");
        }

        HikariConfig config = new HikariConfig();
        config.setDriverClassName(DB_DRIVER);
        config.setUsername(DB_USERNAME);
        config.setPassword(DB_PASSWORD);
        config.setJdbcUrl(DB_URL);
        HikariDataSource ds = new HikariDataSource( config );

        PasswordEncoder passwordEncoder = new PasswordEncoderImpl();
        UserMapper userMapper = new UserMapperImpl();
        UsersRepository usersRepository;
        CasesRepository casesRepository;
        SkinsRepository skinsRepository;
        UsersSkinsRepository usersSkinsRepository;
        FilesRepository filesRepository;
        try {
            usersRepository = new UsersRepositoryImpl(ds);
            casesRepository = new CasesRepositoryImpl(ds);
            skinsRepository = new SkinsRepositoryImpl(ds);
            filesRepository = new FilesRepositoryImpl(ds);
            usersSkinsRepository = new UsersSkinsConnectionRepositoryImpl(ds);
        } catch (SQLException e) {
            throw new CustomException("Did not reached connection");
        }
        AuthorizationService authorizationService = new AuthorizationServiceImpl(usersRepository, userMapper, passwordEncoder);
        FilesService filesService = new FilesServiceImpl(IMAGES_STORAGE_PATH, filesRepository, usersRepository);

        ServletContext servletContext = sce.getServletContext();
        servletContext.setAttribute("usersRepository", usersRepository);
        servletContext.setAttribute("authorizationService", authorizationService);
        servletContext.setAttribute("casesRepository", casesRepository);
        servletContext.setAttribute("filesService", filesService);
        servletContext.setAttribute("openCaseService", new OpenCaseService(casesRepository, skinsRepository, usersRepository, usersSkinsRepository));
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {}
}
