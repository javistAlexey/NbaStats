package home.tutorial.nbastats.mapper;

import home.tutorial.nbastats.dto.TeamStatsDTO;
import home.tutorial.nbastats.model.TeamStats;

public class TeamStatsMapper {

    public static TeamStats toModel(TeamStatsDTO dto) {
        return new TeamStats.Builder()
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

    public static TeamStatsDTO toDTO(TeamStats model) {
        return new TeamStatsDTO(
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
