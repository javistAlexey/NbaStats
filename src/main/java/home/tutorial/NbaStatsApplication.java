package home.tutorial;


import com.sun.net.httpserver.HttpServer;
import home.tutorial.nbastats.controller.AverageStatsController;
import home.tutorial.nbastats.controller.PlayerStatsController;
import home.tutorial.nbastats.repository.PlayerStatsRepository;
import home.tutorial.nbastats.service.AverageStatsService;
import home.tutorial.nbastats.service.PlayerStatsService;
import java.net.InetSocketAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.IOException;

public class NbaStatsApplication {

    public static void main(String[] args) {
        try {
            Connection connection =
                DriverManager.getConnection("jdbc:postgresql://localhost:5432/nba_stats", "user", "password");
            PlayerStatsRepository repository = new PlayerStatsRepository(connection);
            PlayerStatsService playerStatsService = new PlayerStatsService(repository);
            AverageStatsService averageStatsService = new AverageStatsService(repository);

            String port = System.getenv("PORT");
            if (port == null || port.isEmpty()) {
                port = "8080";  // Порт по умолчанию
            }
            int portNumber = Integer.parseInt(port);

            // Создаем сервер
            HttpServer server = HttpServer.create(new InetSocketAddress(portNumber), 0);

            PlayerStatsController playerStatsController = new PlayerStatsController(playerStatsService,server);
            AverageStatsController averageStatsController = new AverageStatsController(averageStatsService, server);
            System.out.println("Server started on port " + portNumber);
            // Запуск сервера
            server.start();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}

