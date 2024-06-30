CREATE TABLE IF NOT EXISTS player_stats
(
    id             SERIAL PRIMARY KEY,
    player_id      INT NOT NULL,
    team_id        INT NOT NULL,
    points         INT CHECK (points >= 0),
    rebounds       INT CHECK (rebounds >= 0),
    assists        INT CHECK (assists >= 0),
    steals         INT CHECK (steals >= 0),
    blocks         INT CHECK (blocks >= 0),
    fouls          INT CHECK (fouls >= 0 AND fouls <= 6),
    turnovers      INT CHECK (turnovers >= 0),
    minutes_played FLOAT CHECK (minutes_played >= 0 AND minutes_played <= 48.0),
    created_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
