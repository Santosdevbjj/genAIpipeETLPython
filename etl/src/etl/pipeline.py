# etl/src/etl/pipeline.py
from typing import Iterable
from .extract import read_ids_from_sheet, fetch_customers_from_api
from .transform import build_prompt, sanitize_output
from .load import post_message
from .clients.ai_provider import AIProvider
from .utils.logging import logger

def run_pipeline(input_path: str, api_base: str, jwt_token: str, ai_provider: AIProvider,
                 channel: str = "app", ids: Iterable[int] | None = None):
    if ids is None:
        ids = read_ids_from_sheet(input_path)

    customers = fetch_customers_from_api(api_base, ids, jwt_token)

    for c in customers:
        prompt = build_prompt(c)
        raw_text = ai_provider.generate(prompt)
        text = sanitize_output(raw_text)
        result = post_message(api_base, c["id"], text, channel, jwt_token, modeloVersao="genai-v1")
        logger.info(f"Mensagem criada: {result.get('id')} para cliente {c['id']}")
