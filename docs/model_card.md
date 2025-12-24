**Personalização de Marketing com IA (GPT-4)**

​Este Model Card detalha o uso do componente de Inteligência Artificial Generativa dentro do pipeline de ETL para a geração de mensagens de marketing personalizadas.

**​1. Detalhes do Modelo**

• ​Desenvolvedor: Sérgio Santos (Integração via OpenAI GPT-4)

• ​Data da Versão: Novembro, 2025

​• Tipo de Modelo: Large Language Model (LLM) baseado em Transformer.

• ​Documentação Relacionada: Prompts Base | Regras de Segurança

​**2. Uso Pretendido**

​Caso de Uso Primário: Geração automática de textos curtos para notificações (App, SMS, E-mail) baseados no segmento do cliente (Varejo, Select, Private).

​**Entradas (Features):** - Nome do Cliente (para saudação).

• ​Segmento de conta.

• ​Limite de crédito atual.

• ​Score de crédito.

​• **Saída:** Mensagem de até 600 caracteres com tom profissional, empático e focado em benefícios.

**​3. Fatores de Desempenho e Métricas**

• **​KPIs de Negócio:** Taxa de Abertura (CTR), Conversão de Produtos Oferecidos.
​Métricas Técnicas:

• **​Latência:** Tempo médio de resposta da API de IA por registro.

• **​Taxa de Sucesso do ETL:** Porcentagem de mensagens geradas que passaram na sanitização de caracteres.

• **Idempotência:** Eficácia do Hash SHA-256 em evitar chamadas duplicadas.

**​4. Dados de Treinamento e Prompt Engineering**

• ​**Prompt Base:** O modelo utiliza few-shot prompting para garantir que o tom de voz do banco seja respeitado.

• ""​Variáveis Dinâmicas:** Os dados são injetados via templates Pydantic no Python para evitar prompt injection.

**​5. Limitações e Riscos**

• ​Alucinações: O modelo pode sugerir benefícios que não existem no produto real se o prompt não for restritivo.

• ​Custo: Chamadas frequentes a modelos de ponta (GPT-4) podem encarecer o pipeline se não houver cache/idempotência.

• ​Privacidade (LGPD): Nenhum dado sensível como CPF, senhas ou histórico detalhado de transações é enviado ao provedor de IA. Apenas dados de segmentação e limites.

**​6. Governança e Segurança**

• **​Filtros de Conteúdo:** O pipeline contém uma camada de sanitização (etl/src/etl/transform.py) que remove caracteres especiais indesejados e valida o comprimento da mensagem.

• **​Safety Rules:*** Implementação de regras que impedem a geração de conteúdo ofensivo ou promessas financeiras irrealistas. 



