# Model Card — Componente Generativo

- **Objetivo:** gerar mensagens curtas de marketing personalizadas.
- **Entradas:** nome, segmento, limite, score. Não incluir dados sensíveis.
- **Saídas:** texto sanitizado (<=600 chars), tom profissional e empático.
- **Métricas:** legibilidade (ex.: FKGL), taxa de revisão aprovada, detecção de toxidade (zero tolerância).
- **Versão do modelo:** genai-v1 (mock no desenvolvimento).
- **Riscos e mitigação:** vieses e linguagem inadequada; mitigados por regras de segurança e revisão humana.
