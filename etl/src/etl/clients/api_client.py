# etl/src/etl/clients/api_client.py
from typing import Dict, Any
import httpx

class ApiClient:
    def __init__(self, base_url: str, jwt_token: str, timeout: int = 20):
        self.base_url = base_url.rstrip("/")
        self.jwt_token = jwt_token
        self.timeout = timeout

    def _headers(self) -> Dict[str, str]:
        return {"Authorization": f"Bearer {self.jwt_token}"}

    def get_cliente(self, cliente_id: int) -> Dict[str, Any]:
        with httpx.Client(timeout=self.timeout) as client:
            resp = client.get(f"{self.base_url}/clientes/{cliente_id}", headers=self._headers())
            resp.raise_for_status()
            return resp.json()

    def post_mensagem(self, payload: Dict[str, Any]) -> Dict[str, Any]:
        with httpx.Client(timeout=self.timeout) as client:
            resp = client.post(f"{self.base_url}/mensagens", json=payload, headers=self._headers())
            resp.raise_for_status()
            return resp.json()
