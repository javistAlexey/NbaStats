package home.tutorial.nbastats.service;


import home.tutorial.nbastats.dto.PlayerStatsDTO;
import home.tutorial.nbastats.dto.TeamStatsDTO;
import home.tutorial.nbastats.mapper.PlayerStatsMapper;
import home.tutorial.nbastats.mapper.TeamStatsMapper;
import home.tutorial.nbastats.model.PlayerStats;
import home.tutorial.nbastats.model.TeamStats;
import home.tutorial.nbastats.repository.PlayerStatsRepository;
import java.sql.SQLException;

public class AverageStatsService {
    private final PlayerStatsRepository repository;

    public AverageStatsService(PlayerStatsRepository repository) {
        this.repository = repository;
    }

    public PlayerStatsDTO getPlayerAverageStats(int playerId) throws SQLException {
        PlayerStats stats = repository.findPlayerAverageStats(playerId);
        if (stats == null) {
            return null;}
        return PlayerStatsMapper.toDTO(stats);
    }


    public TeamStatsDTO getTeamAverageStats(int teamId) throws SQLException {
        TeamStats stats = repository.findTeamAverageStats(teamId);
        if (stats == null) {
            return null;
        }
        return TeamStatsMapper.toDTO(stats);
    }
}
