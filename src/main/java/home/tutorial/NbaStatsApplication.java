package home.tutorial;


import com.sun.net.httpserver.HttpServer;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import home.tutorial.nbastats.config.Config;
import home.tutorial.nbastats.controller.AverageStatsController;
import home.tutorial.nbastats.controller.PlayerStatsController;
import home.tutorial.nbastats.repository.PlayerStatsRepository;
import home.tutorial.nbastats.service.AverageStatsService;
import home.tutorial.nbastats.service.PlayerStatsService;
import io.github.cdimascio.dotenv.Dotenv;
import java.io.IOException;
import java.net.InetSocketAddress;
import javax.sql.DataSource;

public class NbaStatsApplication {

    public static void main(String[] args) {
        try {
            Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();

            DataSource dataSource = createDataSource();
            PlayerStatsRepository repository = new PlayerStatsRepository(dataSource);
            PlayerStatsService playerStatsService = new PlayerStatsService(repository);
            AverageStatsService averageStatsService = new AverageStatsService(repository);

            String port = Config.get("PORT", "8080");
            int portNumber = Integer.parseInt(port);

            HttpServer server = HttpServer.create(new InetSocketAddress(portNumber), 0);
            new PlayerStatsController(playerStatsService, server);
            new AverageStatsController(averageStatsService, server);

            System.out.println("Server started on port " + portNumber);

            server.start();
        } catch (IOException e) {
           e.printStackTrace();
        }
    }

    private static DataSource createDataSource() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(String.format("jdbc:postgresql://%s:%s/%s",
            Config.get("DB_HOST", "localhost"),
            Config.get("DB_PORT", "5432"),
            Config.get("DB_NAME", "nba_stats")));
        hikariConfig.setUsername(Config.get("DB_USER", "user"));
        hikariConfig.setPassword(Config.get("DB_PASSWORD", "password"));
        hikariConfig.setMaximumPoolSize(Config.getInt("POOL_SIZE", 10));
        return new HikariDataSource(hikariConfig);
    }
}

