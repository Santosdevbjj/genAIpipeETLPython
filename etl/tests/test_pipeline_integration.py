from etl.pipeline import run_pipeline
from etl.clients.ai_provider import MockAIProvider

def test_pipeline_runs_with_mock(monkeypatch):
    # Mock functions to avoid network
    monkeypatch.setenv("API_BASE", "http://localhost:8080/api")

    def fake_fetch(api_base, ids, jwt):
        return [{"id": 1, "nome": "Sergio", "segmento": "Varejo", "limiteCredito": 2000, "score": 700}]

    def fake_post(api_base, cliente_id, texto, canal, jwt, modelo_versao="genai-v1"):
        return {"id": 99, "clienteId": cliente_id, "texto": texto, "canal": canal}

    from etl import extract as ex
    from etl import load as ld
    ex.fetch_customers_from_api = fake_fetch
    ld.post_message = fake_post

    run_pipeline(input_path="dummy.csv", api_base="http://localhost:8080/api", jwt_token="x", ai_provider=MockAIProvider(), channel="app", ids=[1])
