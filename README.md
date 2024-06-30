# NBA Player Statistics Application

This application is designed to log and calculate statistics for NBA players. 

## Prerequisites

- Docker
- Docker Compose

## Running the Application

The application can be easily run using Docker Compose. Follow the steps below to get started:

1. Clone the repository

2. Build and start the application using Docker Compose:

docker-compose up --build

This command will build the Docker images and start the containers for the application and the PostgreSQL database.

3. Testing the Application
You can test the application using HTTP requests defined in the Nba-stats.http file.
This file contains requests for saving player statistics and retrieving aggregate statistics.


json
{
"playerId": 1,
"teamId": 101,
"points": 23,
"rebounds": 11,
"assists": 5,
"steals": 2,
"blocks": 1,
"fouls": 3,
"turnovers": 4,
"minutesPlayed": 34.5
}

Environment Variables
DB_HOST: Database host (default: localhost)
DB_PORT: Database port (default: 5432)
DB_NAME: Database name (default: nba_stats)
DB_USER: Database user (default: user)
DB_PASSWORD: Database password (default: password)
PORT: Application port (default: 8080)
POOL_SIZE: Database connection pool size (default: 10)