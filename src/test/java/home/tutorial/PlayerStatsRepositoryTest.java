package home.tutorial;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import home.tutorial.nbastats.model.PlayerStats;
import home.tutorial.nbastats.repository.PlayerStatsRepository;
import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PlayerStatsRepositoryTest {

    private PlayerStatsRepository repository;
    private DataSource dataSource;

    @BeforeEach
    void setUp() throws SQLException {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:h2:mem:testdb");
        config.setUsername("sa");
        config.setPassword("");
        dataSource = new HikariDataSource(config);

        try (Connection connection = dataSource.getConnection()) {
            String createTableSQL = "CREATE TABLE player_stats (" +
                "player_id INT," +
                "team_id INT," +
                "points INT," +
                "rebounds INT," +
                "assists INT," +
                "steals INT," +
                "blocks INT," +
                "fouls INT," +
                "turnovers INT," +
                "minutes_played FLOAT)";
            connection.createStatement().execute(createTableSQL);
        }

        repository = new PlayerStatsRepository(dataSource);
    }

    @Test
    void testSave() throws SQLException {
        PlayerStats stats = new PlayerStats.Builder()
            .playerId(1)
            .teamId(101)
            .points(23)
            .rebounds(11)
            .assists(5)
            .steals(2)
            .blocks(1)
            .fouls(3)
            .turnovers(4)
            .minutesPlayed(34.5f)
            .build();

        repository.save(stats);

        PlayerStats retrievedStats = repository.findPlayerAverageStats(1);

        assertNotNull(retrievedStats);
        assertEquals(stats.getPlayerId(), retrievedStats.getPlayerId());
        assertEquals(stats.getTeamId(), retrievedStats.getTeamId());
        assertEquals(stats.getPoints(), retrievedStats.getPoints());
        assertEquals(stats.getRebounds(), retrievedStats.getRebounds());
        assertEquals(stats.getAssists(), retrievedStats.getAssists());
        assertEquals(stats.getSteals(), retrievedStats.getSteals());
        assertEquals(stats.getBlocks(), retrievedStats.getBlocks());
        assertEquals(stats.getFouls(), retrievedStats.getFouls());
        assertEquals(stats.getTurnovers(), retrievedStats.getTurnovers());
        assertEquals(stats.getMinutesPlayed(), retrievedStats.getMinutesPlayed());
    }
}

