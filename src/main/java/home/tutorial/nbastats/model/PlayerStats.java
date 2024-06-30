package home.tutorial.nbastats.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = PlayerStats.Builder.class)
public class PlayerStats {
    private final int  playerId;
    private final int teamId;
    private final int points;
    private final int rebounds;
    private final int assists;
    private final int steals;
    private final int blocks;
    private final int fouls;
    private final int turnovers;
    private final float minutesPlayed;

    private PlayerStats(Builder builder) {
        this.playerId = builder.playerId;
        this.teamId = builder.teamId;
        this.points = builder.points;
        this.rebounds = builder.rebounds;
        this.assists = builder.assists;
        this.steals = builder.steals;
        this.blocks = builder.blocks;
        this.fouls = builder.fouls;
        this.turnovers = builder.turnovers;
        this.minutesPlayed = builder.minutesPlayed;
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static class Builder {
        private int playerId;
        private int teamId;
        private int points;
        private int rebounds;
        private int assists;
        private int steals;
        private int blocks;
        private int fouls;
        private int turnovers;
        private float minutesPlayed;

        public Builder playerId(int playerId) {
            this.playerId = playerId;
            return this;
        }

        public Builder teamId(int teamId) {
            this.teamId = teamId;
            return this;
        }

        public Builder points(int points) {
            this.points = points;
            return this;
        }

        public Builder rebounds(int rebounds) {
            this.rebounds = rebounds;
            return this;
        }

        public Builder assists(int assists) {
            this.assists = assists;
            return this;
        }

        public Builder steals(int steals) {
            this.steals = steals;
            return this;
        }

        public Builder blocks(int blocks) {
            this.blocks = blocks;
            return this;
        }

        public Builder fouls(int fouls) {
            this.fouls = fouls;
            return this;
        }

        public Builder turnovers(int turnovers) {
            this.turnovers = turnovers;
            return this;
        }

        public Builder minutesPlayed(float minutesPlayed) {
            this.minutesPlayed = minutesPlayed;
            return this;
        }

        public PlayerStats build() {
            return new PlayerStats(this);
        }
    }

    // Getters
    public int getPlayerId() {
        return playerId;
    }

    public int getTeamId() {
        return teamId;
    }

    public int getPoints() {
        return points;
    }

    public int getRebounds() {
        return rebounds;
    }

    public int getAssists() {
        return assists;
    }

    public int getSteals() {
        return steals;
    }

    public int getBlocks() {
        return blocks;
    }

    public int getFouls() {
        return fouls;
    }

    public int getTurnovers() {
        return turnovers;
    }

    public float getMinutesPlayed() {
        return minutesPlayed;
    }
}
