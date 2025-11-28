# etl/src/etl/transform.py
from textwrap import dedent

def build_prompt(c: dict) -> str:
    nome = c.get("nome", "Cliente")
    segmento = c.get("segmento", "Varejo")
    limite = c.get("limiteCredito", 0)
    score = c.get("score", 0)
    return dedent(f"""
    Você é um redator do Santander. Crie uma mensagem curta e clara para {nome}.
    Segmento: {segmento}. Limite atual: {limite}. Score: {score}.
    Objetivo: aumentar engajamento e uso responsável de crédito.
    Tom: profissional, empático, sem promessas irreais.
    Inclua uma chamada à ação discreta e personalizada.
    Saída com no máximo 600 caracteres.
    """)

def sanitize_output(text: str) -> str:
    cleaned = " ".join(text.split())
    if len(cleaned) > 600:
        cleaned = cleaned[:600]
    return cleaned
