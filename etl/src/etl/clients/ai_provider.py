# etl/src/etl/clients/ai_provider.py
from typing import Protocol

class AIProvider(Protocol):
    def generate(self, prompt: str) -> str: ...

class MockAIProvider:
    def generate(self, prompt: str) -> str:
        # Retorna um texto simples para desenvolvimento e testes
        return ("Olá! Vimos que você está aproveitando seus produtos. "
                "Que tal explorar benefícios exclusivos no app Santander? "
                "Acompanhe seu limite e oportunidades com responsabilidade. "
                "Acesse o app e conheça as novidades.")

# Exemplo de adaptação para provedores reais (OpenAI/Azure) pode ser feito aqui,
# mantendo esta interface.
