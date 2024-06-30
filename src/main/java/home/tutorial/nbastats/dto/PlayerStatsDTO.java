package home.tutorial.nbastats.dto;


public record PlayerStatsDTO(
    int playerId,
    int teamId,
    int points,
    int rebounds,
    int assists,
    int steals,
    int blocks,
    int fouls,
    int turnovers,
    float minutesPlayed
) {
}
