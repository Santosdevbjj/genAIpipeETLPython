# etl/src/etl/load.py
import httpx
from .utils.logging import logger

def post_message(api_base: str, cliente_id: int, texto: str, canal: str, jwt_token: str, modelo_versao: str = "genai-v1") -> dict:
    headers = {"Authorization": f"Bearer {jwt_token}"}
    payload = {
        "clienteId": cliente_id,
        "texto": texto,
        "canal": canal,
        "modeloVersao": modelo_versao
    }
    logger.info(f"POST mensagem para cliente {cliente_id} canal {canal}")
    with httpx.Client(timeout=20) as client:
        resp = client.post(f"{api_base}/mensagens", json=payload, headers=headers)
        resp.raise_for_status()
        return resp.json()
