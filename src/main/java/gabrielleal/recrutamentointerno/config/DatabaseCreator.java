package gabrielleal.recrutamentointerno.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Component

public class DatabaseCreator {

    @Autowired
    private Environment env;

    @PostConstruct
    public void init() {
        String baseUrl = env.getProperty("spring.datasource.base-url", "jdbc:postgresql://localhost:5432/");
        String user = env.getProperty("spring.datasource.username");
        String password = env.getProperty("spring.datasource.password");
        String dbName = env.getProperty("spring.datasource.database-name", "recrutamento");



        try (Connection conn = DriverManager.getConnection(baseUrl + "postgres", user, password);
             Statement stmt = conn.createStatement()) {

            // Verificar se o banco de dados já existe
            String checkDbExistsQuery = "SELECT 1 FROM pg_database WHERE datname = '" + dbName + "'";
            try (ResultSet rs = stmt.executeQuery(checkDbExistsQuery)) {
                if (rs.next()) {
                    System.out.println("Database already exists");
                    return; // Retornar imediatamente se o banco de dados já existir
                }
            }

            // Criar o banco de dados se ele não existir
            String createDbQuery = "CREATE DATABASE " + dbName;
            stmt.executeUpdate(createDbQuery);
            System.out.println("Database created successfully");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}