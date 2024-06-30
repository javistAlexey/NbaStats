package home.tutorial.nbastats.dto;


public record TeamStatsDTO(
    int teamId,
    int points,
    int rebounds,
    int assists,
    int steals,
    int blocks,
    int fouls,
    int turnovers,
    float minutesPlayed
) {}
