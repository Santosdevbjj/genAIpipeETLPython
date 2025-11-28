# etl/src/etl/extract.py
from typing import List
import pandas as pd
from .utils.logging import logger

def read_ids_from_sheet(path: str) -> List[int]:
    logger.info(f"Lendo planilha/CSV: {path}")
    if path.endswith(".xlsx"):
        df = pd.read_excel(path)
    else:
        df = pd.read_csv(path)
    ids = df["id"].dropna().astype(int).tolist()
    logger.info(f"IDs extraídos: {ids}")
    return ids

def fetch_customers_from_api(api_base: str, ids: List[int], jwt_token: str) -> List[dict]:
    import httpx
    headers = {"Authorization": f"Bearer {jwt_token}"}
    customers = []
    with httpx.Client(timeout=20) as client:
        for cid in ids:
            resp = client.get(f"{api_base}/clientes/{cid}", headers=headers)
            if resp.status_code == 200:
                customers.append(resp.json())
            else:
                logger.warning(f"Cliente {cid} não encontrado ou erro {resp.status_code}")
    return customers
