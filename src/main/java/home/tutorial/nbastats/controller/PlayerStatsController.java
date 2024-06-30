package home.tutorial.nbastats.controller;


import home.tutorial.nbastats.dto.PlayerStatsDTO;
import home.tutorial.nbastats.model.PlayerStats;
import home.tutorial.nbastats.service.PlayerStatsService;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PlayerStatsController {
    private final PlayerStatsService service;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public PlayerStatsController(PlayerStatsService service, HttpServer server) {
        this.service = service;
        server.createContext("/stats/save", new SaveHandler());
    }

    class SaveHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("POST".equals(exchange.getRequestMethod())) {
                InputStream requestBody = exchange.getRequestBody();
                String body = new String(requestBody.readAllBytes(), StandardCharsets.UTF_8);
                System.out.println("Received body: " + body);

                if (body.isEmpty()) {
                    exchange.sendResponseHeaders(400, 0);
                    try (OutputStream os = exchange.getResponseBody()) {
                        os.write("Empty body".getBytes());
                    }
                    return;
                }

                PlayerStatsDTO statsDTO;
                // Обновление: Изменен метод десериализации JSON для использования объекта PlayerStats напрямую
                try {
                    statsDTO = objectMapper.readValue(body, PlayerStatsDTO.class);
                } catch (Exception e) {
                    e.printStackTrace();
                    exchange.sendResponseHeaders(400, 0);
                    try (OutputStream os = exchange.getResponseBody()) {
                        os.write("Invalid JSON format".getBytes());
                    }
                    return;
                }

                String validationError = validateStats(statsDTO);
                if (validationError != null) {
                    exchange.sendResponseHeaders(400, 0);
                    try (OutputStream os = exchange.getResponseBody()) {
                        os.write(validationError.getBytes());
                    }
                    return;
                }

                try {
                    service.savePlayerStats(statsDTO);
                    exchange.sendResponseHeaders(201, 0);
                } catch (SQLException e) {
                    e.printStackTrace();
                    exchange.sendResponseHeaders(500, 0);
                    try (OutputStream os = exchange.getResponseBody()) {
                        os.write("Failed to save stats".getBytes());
                    }
                }
            } else {
                exchange.sendResponseHeaders(405, 0);
            }
            exchange.close();
        }

        private String validateStats(PlayerStatsDTO stats) {
            if (stats.points() < 0 || stats.rebounds() < 0 || stats.assists() < 0 ||
                stats.steals() < 0 || stats.blocks() < 0 || stats.turnovers() < 0) {
                return "Invalid stats values";
            }
            if (stats.fouls() < 0 || stats.fouls() > 6) {
                return "Invalid fouls value";
            }
            if (stats.minutesPlayed() < 0 || stats.minutesPlayed() > 48.0) {
                return "Invalid minutes played value";
            }
            return null;
        }
    }
}
