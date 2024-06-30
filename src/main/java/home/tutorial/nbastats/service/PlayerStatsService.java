package home.tutorial.nbastats.service;


import home.tutorial.nbastats.dto.PlayerStatsDTO;
import home.tutorial.nbastats.mapper.PlayerStatsMapper;
import home.tutorial.nbastats.model.PlayerStats;
import home.tutorial.nbastats.repository.PlayerStatsRepository;
import java.sql.SQLException;

public class PlayerStatsService {
    private final PlayerStatsRepository repository;

    public PlayerStatsService(PlayerStatsRepository repository) {
        this.repository = repository;
    }

    // Обновление: использование мэппера для преобразования DTO в модель
    public void savePlayerStats(PlayerStatsDTO statsDTO) throws SQLException {
        PlayerStats stats = PlayerStatsMapper.toModel(statsDTO);
        repository.save(stats);
    }

    // Обновление: использование мэппера для преобразования модели в DTO
    public PlayerStatsDTO getPlayerAverageStats(int playerId) throws SQLException {
        PlayerStats stats = repository.findPlayerAverageStats(playerId);
        if (stats == null) {
            return null;
        }
        return PlayerStatsMapper.toDTO(stats);
    }
}