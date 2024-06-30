package home.tutorial;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpServer;
import home.tutorial.nbastats.controller.PlayerStatsController;
import home.tutorial.nbastats.dto.PlayerStatsDTO;
import home.tutorial.nbastats.service.PlayerStatsService;
import java.sql.SQLException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PlayerStatsControllerTest {

    @Mock
    private PlayerStatsService playerStatsService;

    private HttpServer server;
    private ObjectMapper objectMapper = new ObjectMapper();
    private PlayerStatsController playerStatsController;

    @BeforeEach
    void setUp() throws IOException {
        MockitoAnnotations.openMocks(this);
        server = HttpServer.create(new InetSocketAddress(8081), 0);
        playerStatsController = new PlayerStatsController(playerStatsService, server);
        server.start();
    }

    @AfterEach
    void tearDown() {
        server.stop(0);
    }

    @Test
    void testSavePlayerStats() throws IOException, SQLException {
        URL url = new URL("http://localhost:8081/stats/save");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/json");

        PlayerStatsDTO statsDTO = new PlayerStatsDTO(1, 101, 23, 11, 5, 2, 1, 3, 4, 34.5f);
        String json = objectMapper.writeValueAsString(statsDTO);

        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = json.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        int responseCode = connection.getResponseCode();
        assertEquals(HttpURLConnection.HTTP_CREATED, responseCode);

        verify(playerStatsService, times(1)).savePlayerStats(any(PlayerStatsDTO.class));
    }
}
