# Makefile
.PHONY: build run test clean

build:
	mvn clean package -DskipTests

run: build
	docker-compose down --remove-orphans
	docker-compose up --build

test:
	mvn test

test-api:
	@echo "Testing Valid Deal..."
	curl -X POST http://localhost:8080/api/deals -H "Content-Type: application/json" -d '{"dealUniqueId":"M-001","fromCurrency":"USD","toCurrency":"JOD","dealTimestamp":"2026-02-22T12:00:00Z","dealAmount":100}'