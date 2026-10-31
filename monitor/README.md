# DeFi Oracle Arbitrage Monitor

A read-only service that compares the Aave V3 oracle price and a Uniswap V3 quote
for WETH/USDC at the same Ethereum block, and flags divergence. Signal/research
tool — a divergence is **not** a tradeable arbitrage.

## Prerequisites
- JDK 21
- Docker + Docker Compose

## Run locally
1. Start Postgres:        `docker compose up -d`
2. Start the app:         `./gradlew bootRun`   (Ctrl-C to stop)
3. Check health:          `curl http://localhost:8080/actuator/health`  → `{"status":"UP"}`

Stop Postgres when done:   `docker compose down`

## Config
Datasource is set in `src/main/resources/application-local.yml` (profile `local`,
active by default). Postgres runs on host port **5433** (mapped to container 5432).

## Test
`./gradlew test`  — uses Testcontainers, so Docker must be running.