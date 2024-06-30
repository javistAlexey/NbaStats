package home.tutorial.nbastats.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import home.tutorial.nbastats.dto.TeamStatsDTO;
import home.tutorial.nbastats.model.TeamStats;
import home.tutorial.nbastats.service.AverageStatsService;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;

public class AverageStatsController {
    private final AverageStatsService service;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public AverageStatsController(AverageStatsService service, HttpServer server) {
        this.service = service;
        server.createContext("/stats/average/player", new PlayerAverageHandler());
        server.createContext("/stats/average/team", new TeamAverageHandler());
    }

    private class PlayerAverageHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("GET".equals(exchange.getRequestMethod())) {
                String query = exchange.getRequestURI().getQuery();
                int playerId = Integer.parseInt(query.split("=")[1]);

                try {
                    var averageStats = service.getPlayerAverageStats(playerId);
                    String response = objectMapper.writeValueAsString(averageStats);
                    exchange.sendResponseHeaders(200, response.length());
                    try (OutputStream os = exchange.getResponseBody()) {
                        os.write(response.getBytes());
                    }
                } catch (SQLException e) {
                    exchange.sendResponseHeaders(500, 0);
                    try (OutputStream os = exchange.getResponseBody()) {
                        os.write("Failed to get stats".getBytes());
                    }
                }
            } else {
                exchange.sendResponseHeaders(405, 0);
            }
            exchange.close();
        }

    }

    private class TeamAverageHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("GET".equals(exchange.getRequestMethod())) {
                String query = exchange.getRequestURI().getQuery();
                int teamId = Integer.parseInt(query.split("=")[1]);

                try {
                    var averageStats = service.getTeamAverageStats(teamId);
                    String response = objectMapper.writeValueAsString(averageStats);
                    exchange.sendResponseHeaders(200, response.length());
                    try (OutputStream os = exchange.getResponseBody()) {
                        os.write(response.getBytes());
                    }
                } catch (SQLException e) {
                    exchange.sendResponseHeaders(500, 0);
                    try (OutputStream os = exchange.getResponseBody()) {
                        os.write("Failed to get stats".getBytes());
                    }
                }
            } else {
                exchange.sendResponseHeaders(405, 0);
            }
            exchange.close();
        }
    }
}

