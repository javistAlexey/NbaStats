package home.tutorial.nbastats.repository;


import home.tutorial.nbastats.model.PlayerStats;
import home.tutorial.nbastats.model.TeamStats;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;

public class PlayerStatsRepository {

    private final DataSource dataSource;

    public PlayerStatsRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    public void save(PlayerStats stats) throws SQLException {
        String sql = "INSERT INTO player_stats (player_id, team_id, points, rebounds, assists, steals, blocks, fouls, turnovers, minutes_played) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, stats.getPlayerId());
            stmt.setInt(2, stats.getTeamId());
            stmt.setInt(3, stats.getPoints());
            stmt.setInt(4, stats.getRebounds());
            stmt.setInt(5, stats.getAssists());
            stmt.setInt(6, stats.getSteals());
            stmt.setInt(7, stats.getBlocks());
            stmt.setInt(8, stats.getFouls());
            stmt.setInt(9, stats.getTurnovers());
            stmt.setFloat(10, stats.getMinutesPlayed());
            stmt.executeUpdate();
        }
    }

    public PlayerStats findPlayerAverageStats(int playerId) throws SQLException {
        String sql = "SELECT team_id, AVG(points) as points, AVG(rebounds) as rebounds, AVG(assists) as assists, AVG(steals) as steals, AVG(blocks) as blocks, AVG(fouls) as fouls, AVG(turnovers) as turnovers, AVG(minutes_played) as minutesPlayed FROM player_stats WHERE player_id = ? GROUP BY team_id";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql))  {
            stmt.setInt(1, playerId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    PlayerStats stats = new PlayerStats.Builder()
                        .playerId(playerId)
                        .teamId(rs.getInt("team_id"))
                        .points(rs.getInt("points"))
                        .rebounds(rs.getInt("rebounds"))
                        .assists(rs.getInt("assists"))
                        .steals(rs.getInt("steals"))
                        .blocks(rs.getInt("blocks"))
                        .fouls(rs.getInt("fouls"))
                        .turnovers(rs.getInt("turnovers"))
                        .minutesPlayed(rs.getFloat("minutesPlayed"))
                        .build();
                    return stats;
                } else {
                    return null;
                }
            }
        }
    }
    public TeamStats findTeamAverageStats(int teamId) throws SQLException {
        String sql = "SELECT AVG(points) as points, AVG(rebounds) as rebounds, AVG(assists) as assists, AVG(steals) as steals, AVG(blocks) as blocks, AVG(fouls) as fouls, AVG(turnovers) as turnovers, AVG(minutes_played) as minutesPlayed FROM player_stats WHERE team_id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, teamId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new TeamStats.Builder()
                        .teamId(teamId)
                        .points(rs.getInt("points"))
                        .rebounds(rs.getInt("rebounds"))
                        .assists(rs.getInt("assists"))
                        .steals(rs.getInt("steals"))
                        .blocks(rs.getInt("blocks"))
                        .fouls(rs.getInt("fouls"))
                        .turnovers(rs.getInt("turnovers"))
                        .minutesPlayed(rs.getFloat("minutesPlayed"))
                        .build();
                } else {
                    return null;
                }
            }
        }
    }
}