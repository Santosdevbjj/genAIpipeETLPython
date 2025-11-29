from etl.pipeline import run_pipeline
from etl.clients.ai_provider import MockAIProvider
# Importamos o tipo de erro esperado para fazer asserts
from httpx import ConnectError 
# Importamos o fixture httpx_mock, que é injetado pelo pytest-httpx

# 💡 NOVO: Adicionamos o fixture 'httpx_mock' na assinatura da função.
def test_pipeline_runs_successfully(httpx_mock):
    # O API_BASE usado aqui deve corresponder à URL base usada no seu código.
    api_base = "http://localhost:8080/api"
    customer_id = 1
    
    # ----------------------------------------------------
    # 1. Simular a resposta da chamada de EXTRAÇÃO (GET /clientes/{id})
    # O httpx_mock irá interceptar a chamada feita pelo 'etl.extract'
    httpx_mock.add_response(
        url=f"{api_base}/clientes/{customer_id}",
        method="GET",
        # Resposta que o seu teste espera para o processamento
        json={
            "id": 1, 
            "nome": "Sergio", 
            "segmento": "Varejo", 
            "limiteCredito": 2000, 
            "score": 700
        }
    )

    # ----------------------------------------------------
    # 2. Simular a resposta da chamada de LOAD (POST /mensagens)
    # O httpx_mock irá interceptar a chamada feita pelo 'etl.load'
    httpx_mock.add_response(
        url=f"{api_base}/mensagens",
        method="POST",
        # Resposta esperada da API após o POST
        json={
            "id": 99, 
            "clienteId": customer_id, 
            "texto": "Mensagem de Marketing Gerada", 
            "canal": "app"
        }
    )
    # ----------------------------------------------------
    
    # A classe MockAIProvider simula a resposta da IA, mantemos essa simulação.
    
    # Executa a pipeline, que agora usará as respostas simuladas
    run_pipeline(
        input_path="dummy.csv", 
        api_base=api_base, 
        jwt_token="x", 
        ai_provider=MockAIProvider(), 
        channel="app", 
        ids=[customer_id]
    )
    
    # OPCIONAL: Adicionar asserts para verificar se todas as chamadas esperadas ocorreram.
    # Exemplo (requer que a pipeline não lance exceções):
    assert len(httpx_mock.get_requests()) == 2
