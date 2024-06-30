package home.tutorial.nbastats.mapper;

import home.tutorial.nbastats.dto.PlayerStatsDTO;
import home.tutorial.nbastats.model.PlayerStats;

public class PlayerStatsMapper {

    public static PlayerStats toModel(PlayerStatsDTO dto) {
        return new PlayerStats.Builder()
            .playerId(dto.playerId())
            .teamId(dto.teamId())
            .points(dto.points())
            .rebounds(dto.rebounds())
            .assists(dto.assists())
            .steals(dto.steals())
            .blocks(dto.blocks())
            .fouls(dto.fouls())
            .turnovers(dto.turnovers())
            .minutesPlayed(dto.minutesPlayed())
            .build();
    }

    public static PlayerStatsDTO toDTO(PlayerStats model) {
        return new PlayerStatsDTO(
            model.getPlayerId(),
            model.getTeamId(),
            model.getPoints(),
            model.getRebounds(),
            model.getAssists(),
            model.getSteals(),
            model.getBlocks(),
            model.getFouls(),
            model.getTurnovers(),
            model.getMinutesPlayed()
        );
    }
}
