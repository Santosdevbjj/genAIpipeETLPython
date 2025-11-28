# etl/src/app.py
import argparse
import os
from dotenv import load_dotenv

from etl.clients.ai_provider import MockAIProvider
from etl.pipeline import run_pipeline

def main():
    load_dotenv()
    parser = argparse.ArgumentParser(description="Executa pipeline ETL com IA generativa")
    parser.add_argument("--input", required=True, help="Caminho da planilha/CSV com coluna 'id'")
    parser.add_argument("--api-base", default=os.getenv("API_BASE", "http://localhost:8080/api"))
    parser.add_argument("--jwt", default=os.getenv("JWT_TOKEN", ""))
    parser.add_argument("--channel", default="app", choices=["app", "email", "sms"])
    args = parser.parse_args()

    ai = MockAIProvider()
    run_pipeline(args.input, args.api_base, args.jwt, ai, channel=args.channel)

if __name__ == "__main__":
    main()
