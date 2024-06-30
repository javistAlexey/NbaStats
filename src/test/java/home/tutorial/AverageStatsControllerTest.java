package home.tutorial;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpServer;
import home.tutorial.nbastats.controller.AverageStatsController;
import home.tutorial.nbastats.dto.PlayerStatsDTO;
import home.tutorial.nbastats.dto.TeamStatsDTO;
import home.tutorial.nbastats.service.AverageStatsService;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URL;
import java.sql.SQLException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class AverageStatsControllerTest {

    @Mock
    private AverageStatsService averageStatsService;

    private HttpServer server;
    private ObjectMapper objectMapper = new ObjectMapper();
    private AverageStatsController averageStatsController;

    @BeforeEach
    void setUp() throws IOException {
        MockitoAnnotations.openMocks(this);
        server = HttpServer.create(new InetSocketAddress(8081), 0);
        averageStatsController = new AverageStatsController(averageStatsService, server);
        server.start();
    }

    @AfterEach
    void tearDown() {
        server.stop(0);
    }

    @Test
    void testGetPlayerAverageStats() throws IOException, SQLException {
        URL url = new URL("http://localhost:8081/stats/average/player?playerId=1");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        PlayerStatsDTO statsDTO = new PlayerStatsDTO(1, 101, 23, 11, 5, 2, 1, 3, 4, 34.5f);
        when(averageStatsService.getPlayerAverageStats(1)).thenReturn(statsDTO);

        int responseCode = connection.getResponseCode();
        assertEquals(HttpURLConnection.HTTP_OK, responseCode);

        verify(averageStatsService, times(1)).getPlayerAverageStats(1);
    }

    @Test
    void testGetTeamAverageStats() throws IOException, SQLException {
        URL url = new URL("http://localhost:8081/stats/average/team?teamId=101");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        TeamStatsDTO teamStatsDTO = new TeamStatsDTO(101, 23, 11, 5, 2, 1, 3, 4, 34.5f);
        when(averageStatsService.getTeamAverageStats(101)).thenReturn(teamStatsDTO);

        int responseCode = connection.getResponseCode();
        assertEquals(HttpURLConnection.HTTP_OK, responseCode);

        verify(averageStatsService, times(1)).getTeamAverageStats(101);
    }
}
