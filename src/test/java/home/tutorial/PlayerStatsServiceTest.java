package home.tutorial;


import home.tutorial.nbastats.dto.PlayerStatsDTO;
import home.tutorial.nbastats.mapper.PlayerStatsMapper;
import home.tutorial.nbastats.model.PlayerStats;
import home.tutorial.nbastats.repository.PlayerStatsRepository;
import home.tutorial.nbastats.service.PlayerStatsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PlayerStatsServiceTest {

    @Mock
    private PlayerStatsRepository repository;

    @InjectMocks
    private PlayerStatsService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSavePlayerStats() throws SQLException {
        PlayerStatsDTO statsDTO = new PlayerStatsDTO(1, 101, 23, 11, 5, 2, 1, 3, 4, 34.5f);
        PlayerStats stats = PlayerStatsMapper.toModel(statsDTO);

        service.savePlayerStats(statsDTO);

        ArgumentCaptor<PlayerStats> captor = ArgumentCaptor.forClass(PlayerStats.class);
        verify(repository, times(1)).save(captor.capture());

        PlayerStats capturedStats = captor.getValue();
        assertEquals(stats.getPlayerId(), capturedStats.getPlayerId());
        assertEquals(stats.getTeamId(), capturedStats.getTeamId());
        assertEquals(stats.getPoints(), capturedStats.getPoints());
        assertEquals(stats.getRebounds(), capturedStats.getRebounds());
        assertEquals(stats.getAssists(), capturedStats.getAssists());
        assertEquals(stats.getSteals(), capturedStats.getSteals());
        assertEquals(stats.getBlocks(), capturedStats.getBlocks());
        assertEquals(stats.getFouls(), capturedStats.getFouls());
        assertEquals(stats.getTurnovers(), capturedStats.getTurnovers());
        assertEquals(stats.getMinutesPlayed(), capturedStats.getMinutesPlayed());
    }

    @Test
    void testGetPlayerAverageStats() throws SQLException {
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

        when(repository.findPlayerAverageStats(1)).thenReturn(stats);

        PlayerStatsDTO result = service.getPlayerAverageStats(1);

        assertEquals(stats.getPlayerId(), result.playerId());
        assertEquals(stats.getTeamId(), result.teamId());
        assertEquals(stats.getPoints(), result.points());
        assertEquals(stats.getRebounds(), result.rebounds());
        assertEquals(stats.getAssists(), result.assists());
        assertEquals(stats.getSteals(), result.steals());
        assertEquals(stats.getBlocks(), result.blocks());
        assertEquals(stats.getFouls(), result.fouls());
        assertEquals(stats.getTurnovers(), result.turnovers());
        assertEquals(stats.getMinutesPlayed(), result.minutesPlayed());
    }
}
