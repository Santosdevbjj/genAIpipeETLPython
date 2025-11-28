# Makefile
.PHONY: api-build api-run etl-venv etl-run docker-up docker-down test-java test-python docs

api-build:
	cd api && ./gradlew clean build

api-run:
	java -jar api/build/libs/genai-etl-api.jar

etl-venv:
	cd etl && python -m venv .venv && . ./.venv/bin/activate && pip install -r requirements.txt

etl-run:
	cd etl && . ./.venv/bin/activate && python src/app.py --input data/clientes.xlsx --channel app

docker-up:
	docker compose up -d --build

docker-down:
	docker compose down

test-java:
	cd api && ./gradlew test

test-python:
	cd etl && . ./.venv/bin/activate && pytest -q

docs:
	sphinx-build -b html docs docs/_build/html
