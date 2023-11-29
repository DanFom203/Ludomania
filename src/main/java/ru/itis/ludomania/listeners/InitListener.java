package ru.itis.ludomania.listeners;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import ru.itis.ludomania.exceptions.CustomException;
import ru.itis.ludomania.repositories.CasesRepository;
import ru.itis.ludomania.repositories.UsersRepository;
import ru.itis.ludomania.repositories.impl.CasesRepositoryImpl;
import ru.itis.ludomania.repositories.impl.UsersRepositoryImpl;
import ru.itis.ludomania.services.AuthorizationService;
import ru.itis.ludomania.services.PasswordEncoder;
import ru.itis.ludomania.services.UserMapper;
import ru.itis.ludomania.services.impl.AuthorizationServiceImpl;
import ru.itis.ludomania.services.impl.PasswordEncoderImpl;
import ru.itis.ludomania.services.impl.UserMapperImpl;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.SQLException;

@WebListener
public class InitListener implements ServletContextListener {
    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "Danfom2004";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/ludomania_project";
    private static final String DB_DRIVER = "org.postgresql.Driver";

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
        try {
            usersRepository = new UsersRepositoryImpl(ds);
            casesRepository = new CasesRepositoryImpl(ds);
        } catch (SQLException e) {
            throw new CustomException("Did not reached connection");
        }
        AuthorizationService authorizationService = new AuthorizationServiceImpl(usersRepository, userMapper, passwordEncoder);

        ServletContext servletContext = sce.getServletContext();
        servletContext.setAttribute("usersRepository", usersRepository);
        servletContext.setAttribute("authorizationService", authorizationService);
        servletContext.setAttribute("casesRepository", casesRepository);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {}
}
