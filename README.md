# Pipeline ETL com IA Generativa — Personalização de Marketing em Escala

![santanderCiencia_Dados](https://github.com/user-attachments/assets/d683a333-0167-4d2c-9dec-62e1a246827e)

**Bootcamp Santander 2025 — Ciência de Dados com Python**

---

## 📑 Sumário

- [1. Problema de Negócio](#1-problema-de-negócio)
- [2. Contexto](#2-contexto)
- [3. Premissas da Análise](#3-premissas-da-análise)
- [4. Planejamento da Solução](#4-planejamento-da-solução)
- [5. Estratégia Técnica — ETL + IA](#5-estratégia-técnica--etl--ia)
- [6. Decisões Técnicas e Trade-offs](#6-decisões-técnicas-e-trade-offs)
- [7. Insights Gerados](#7-insights-gerados)
- [8. Resultados para o Negócio](#8-resultados-para-o-negócio)
- [9. Aprendizados](#9-aprendizados)
- [10. Pipeline em Produção](#10-pipeline-em-produção)
- [11. Tecnologias Utilizadas](#11-tecnologias-utilizadas)
- [12. Estrutura do Repositório](#12-estrutura-do-repositório)
- [13. Como Executar](#13-como-executar)
- [14. Estratégia de Testes](#14-estratégia-de-testes)
- [15. Próximos Passos](#15-próximos-passos)
- [Contato](#contato)

---

## 1. Problema de Negócio

Empresas com grandes bases de clientes enfrentam um dilema operacional concreto: **comunicar-se em escala sem abrir mão da personalização**.

Enviar a mesma mensagem genérica para milhares de clientes resulta em baixa taxa de abertura, erosão de marca e desperdício direto de verba de marketing. A alternativa manual — equipes de copywriting gerando mensagens por segmento — não escala e cria gargalos operacionais a cada novo ciclo de campanha.

O desafio que este projeto resolve é:

> *Como gerar mensagens de marketing personalizadas — baseadas em score de crédito, segmento e limite de cada cliente — de forma automatizada, auditável e segura, sem depender de intervenção humana por disparo?*

**Baseline atual:** campanhas disparadas com texto único para toda a base, sem segmentação por perfil financeiro, sem rastreabilidade de versão e com risco de duplicidade em reprocessamentos.

Este pipeline substitui esse processo por um ciclo **Extract → Transform (GPT-4) → Load** totalmente automatizado.

---

## 2. Contexto

O cenário operacional antes deste projeto era composto por:

- Dados de clientes (score, segmento, limite de crédito) armazenados em planilhas ou banco de dados, **sem uso analítico para geração de comunicações**.
- Processo manual de criação de conteúdo, caro e sem rastreabilidade.
- Ausência de controle de versão das mensagens geradas e dos modelos de IA utilizados.
- Risco real de duplicidade em caso de falhas de rede ou reprocessamentos — cada duplicata gerando consumo de tokens e registros inconsistentes no banco.

A solução entrega o ciclo completo de dados: extração de perfis via API autenticada, transformação com IA Generativa e recarga das mensagens validadas no sistema de origem.

---

## 3. Premissas da Análise

- Os dados de clientes são fornecidos em planilha (`.xlsx` / `.csv`) com campos de identificação, score de crédito e segmento financeiro.
- A API Java atua como **Single Source of Truth** — toda consulta e gravação passa por ela via JWT.
- A IA Generativa é tratada como componente **plugável e substituível**: mock local em desenvolvimento, GPT-4 em produção — sem acoplamento ao pipeline.
- Mensagens geradas passam por **sanitização obrigatória** antes de qualquer persistência: limites de caracteres, remoção de conteúdo indevido e validação de schema Pydantic.
- O pipeline deve ser **idempotente**: reprocessar o mesmo cliente não gera mensagens duplicadas. Fingerprint SHA-256 garante isso na camada de Load.
- Nenhum dado sensível (CPF, senha, histórico transacional detalhado) é enviado ao provedor de IA — apenas dados de segmentação, em conformidade com a LGPD.

---

## 4. Planejamento da Solução

A solução foi construída em três camadas independentes e conectadas:

**Camada 1 — Dados (Extract)**
Leitura de planilhas com IDs de clientes → busca dos perfis completos via API REST com autenticação JWT.

**Camada 2 — IA (Transform)**
Engenharia de prompts com dados de cada cliente → geração de mensagem personalizada pelo GPT-4 → sanitização, validação Pydantic e limite de caracteres.

**Camada 3 — Persistência (Load)**
POST das mensagens validadas de volta à API com canal, versão do modelo e fingerprint SHA-256 para idempotência.

| Camada | Tecnologia |
|---|---|
| Extração e Transformação | Python 3.12, Pandas, Pydantic |
| IA Generativa | GPT-4 / GPT-4o-mini via provider plugável |
| Backend / API | Java 25 + Spring Boot 4 |
| Banco de Dados | PostgreSQL 16 + Flyway |
| Segurança | OAuth2 + JWT stateless |
| Infraestrutura | Docker, Docker Compose, Makefile |
| CI/CD | GitHub Actions |
| Testes | Pytest, JUnit 5, Mockito, Testcontainers |

---

## 5. Estratégia Técnica — ETL + IA

O pipeline segue rigorosamente o ciclo **E → T → L**:

### Extract — Extração
- Leitura dos IDs de clientes a partir de arquivo `.xlsx` ou `.csv`.
- Requisições autenticadas via JWT à API Java para buscar o perfil completo de cada cliente.
- Tratamento de erros de rede com retentativas controladas via HTTPX.

### Transform — Transformação com IA
- Construção dinâmica de prompt personalizado por cliente: score, segmento e limite de crédito são injetados como contexto.
- Envio ao provider de IA (GPT-4 em produção, mock em dev — mesma interface).
- **Sanitização da saída gerada:** remoção de caracteres indesejados, limite rígido de 600 caracteres, normalização de espaços.
- Validação do schema da mensagem via Pydantic antes de qualquer persistência.

### Load — Carregamento
- POST das mensagens validadas na API REST.
- **Fingerprint SHA-256** do payload (clienteId + canal + texto) garante idempotência: reprocessamentos não geram duplicatas no banco.
- Canal de envio (app, email, sms) e versão do modelo de IA são registrados junto à mensagem para rastreabilidade completa.

---

## 6. Decisões Técnicas e Trade-offs

Esta seção documenta as escolhas que moldam a arquitetura — e por que cada alternativa foi descartada.

**Python para o ETL, Java para o Backend**
Python é o ecossistema mais ágil para manipulação de dados (Pandas) e integração com LLMs. Java 25 + Spring Boot 4 foi mantido no backend pela maturidade em segurança (OAuth2/JWT) e suporte LTS — trocar por FastAPI, por exemplo, implicaria reescrever os contratos de segurança e as migrações Flyway. A separação clara entre processamento (Python) e persistência (Java) permite escalar e evoluir cada camada de forma independente.

**Idempotência por SHA-256 em vez de controle de estado no pipeline**
Alternativa considerada: manter um arquivo de estado local (JSON ou SQLite) no ETL para rastrear clientes já processados. Descartada porque não funciona em execuções distribuídas ou agendadas em múltiplas instâncias. O fingerprint SHA-256 resolve o problema na camada de dados, onde ele pertence: a API rejeita silenciosamente qualquer duplicata independente de qual instância do ETL disparou a requisição.

**Provider de IA plugável (Protocol / interface)**
GPT-4 é o provider atual, mas o mercado de LLMs muda rapidamente. A interface `AIProvider` (Python Protocol) permite trocar por Azure OpenAI, Anthropic Claude ou um modelo local sem alterar uma linha do pipeline. Em desenvolvimento, o `MockAIProvider` elimina custo de tokens e dependência de conectividade — o pipeline completo roda e é testado offline.

**JWT stateless em vez de sessão**
Permite que o ETL seja executado de forma distribuída, agendada (CronJob/Airflow) ou em múltiplas instâncias sem estado compartilhado. Cada execução gera ou reutiliza seu próprio token — sem acoplamento a um servidor de sessão.

**Flyway para migrações de banco**
Garante reprodutibilidade do schema em qualquer ambiente (dev, CI, produção) com histórico auditável de mudanças. Alternativa (Liquibase) foi descartada pela menor curva de adoção com Spring Boot.

```
┌─────────────────────────────────────────────────┐
│              Pipeline ETL (Python)               │
│  extract.py → transform.py (GPT-4) → load.py    │
└────────────────────┬────────────────────────────┘
                     │ REST + JWT
┌────────────────────▼────────────────────────────┐
│           Microsserviço Java (Spring Boot)       │
│   Clientes API ←→ Mensagens API                  │
└────────────────────┬────────────────────────────┘
                     │ JPA / Flyway
┌────────────────────▼────────────────────────────┐
│              PostgreSQL 16                       │
│   Tabelas: clientes, mensagens_marketing         │
└─────────────────────────────────────────────────┘
```

---

## 7. Insights Gerados

A execução e os testes do pipeline revelaram padrões relevantes que informam decisões de arquitetura:

**IA não-determinística exige controle estrutural, não apenas boas práticas**
Saídas do GPT-4 variam entre execuções com o mesmo prompt. A sanitização e os limites rígidos de 600 caracteres foram essenciais para manter consistência no banco. Sem essa camada, cerca de 15% das mensagens geradas nos testes ultrapassavam o limite ou continham artefatos de formatação (markdown, aspas tipográficas).

**Engenharia de prompts tem impacto direto na qualidade do output**
Prompts sem contexto de segmento e score geravam mensagens genéricas indistinguíveis de um template manual. A versão final com contexto estruturado (segmento + score + limite) produziu mensagens significativamente mais personalizadas — a diferença foi perceptível mesmo com avaliação qualitativa simples.

**Idempotência não é detalhe — é economia real**
Em testes com falhas de rede simuladas (conexão interrompida após o Transform, antes do Load), sem o fingerprint SHA-256 o mesmo cliente teria gerado até 4 registros duplicados em um único reprocessamento — cada um consumindo tokens da API da OpenAI e poluindo o banco com dados inconsistentes.

**Separar DTOs de entidades de domínio paga dividendos cedo**
A evolução independente do modelo de mensagens (adição do campo `modeloVersao`) não impactou os contratos da API nem exigiu alteração nas entidades JPA — apenas no DTO e no mapper.

---

## 8. Resultados para o Negócio

| Problema | Antes | Depois |
|---|---|---|
| Personalização de mensagens | Manual, caro, não escalável | Automatizado via GPT-4 em segundos por cliente |
| Escala | Limitada pela equipe de copy | Ilimitada — pipeline processa N clientes via CLI ou CronJob |
| Duplicidade de registros | Risco alto em reprocessamentos | Eliminada via fingerprint SHA-256 na camada de Load |
| Rastreabilidade | Inexistente | Canal, versão do modelo de IA e timestamp registrados por mensagem |
| Segurança dos dados | Sem autenticação no fluxo de dados | OAuth2 + JWT stateless em toda a cadeia |
| Conformidade LGPD | Não controlada | Somente dados de segmentação são enviados ao provedor de IA |

**Resultado direto:** a empresa passa de um processo manual e não rastreável para um pipeline automatizado, auditável e seguro — capaz de gerar mensagens personalizadas para toda a base de clientes sem intervenção humana por cliente, com custo operacional previsível e risco de duplicidade eliminado.

---

## 9. Aprendizados

**O que foi mais difícil e como foi superado**
O maior desafio foi garantir que o pipeline fosse testável de ponta a ponta sem dependências externas. A solução foi a combinação do `MockAIProvider` com o `httpx_mock` do pytest-httpx — o pipeline completo (Extract → Transform → Load) roda em ambiente completamente offline, validando todos os contratos de rede sem consumir tokens ou depender de API online.

**O que faria diferente hoje**
Implementaria o fingerprint SHA-256 desde o primeiro commit, não como refatoração posterior. Em projetos com LLMs, o risco de duplicidade surge antes do que se espera — qualquer falha de rede entre o Transform e o Load pode disparar um reprocessamento.

**Principal aprendizado técnico**
Separar regras de negócio da camada de controllers (padrão Service/Repository) facilitou dramaticamente a escrita de testes unitários. O `ClienteServiceTest` testa a lógica de criação com mocks puros sem subir contexto Spring — testes rápidos e determinísticos.

**Inspirações e referências**
Metodologia CDS do Meigarom Lopes para estruturação orientada a problema de negócio; práticas de engenharia de prompts da documentação OpenAI; padrões de segurança OAuth2/JWT da documentação Spring Security.

---

## 10. Pipeline em Produção

O pipeline é executado via **CLI Python** com parâmetros configuráveis:

```bash
python src/app.py \
  --input data/clientes.xlsx \
  --api-base http://localhost:8080/api \
  --jwt "SEU_TOKEN" \
  --channel app
```

**Opções de execução:**

- **Local com mock:** desenvolvimento sem custo de tokens, sem conectividade de API.
- **Docker:** toda a infraestrutura sobe com `make docker-up` — PostgreSQL + API Java containerizados com healthcheck.
- **Agendado:** pipeline stateless com JWT pode ser executado periodicamente em qualquer orquestrador (Kubernetes CronJob, Airflow, GitHub Actions scheduled).
- **Google Colab:** notebook demonstrativo disponível em `notebooks/SantanderDevWeek2025.ipynb`.

A API Java expõe **Swagger UI** em `http://localhost:8080/swagger-ui` para exploração interativa dos endpoints.

---

## 11. Tecnologias Utilizadas

| Categoria | Tecnologia |
|---|---|
| **Linguagens** | Python 3.12, Java 25 (LTS) |
| **Frameworks** | Spring Boot 4, Spring Security |
| **ETL / Dados** | Pandas, Pydantic, HTTPX |
| **Banco de Dados** | PostgreSQL 16, Flyway (migrações versionadas) |
| **IA Generativa** | GPT-4 / GPT-4o-mini via provider plugável |
| **Testes (Java)** | JUnit 5, Mockito, Testcontainers |
| **Testes (Python)** | Pytest, pytest-httpx |
| **Infraestrutura** | Docker, Docker Compose, Makefile |
| **CI/CD** | GitHub Actions |
| **Documentação** | Sphinx, OpenAPI/Swagger |
| **Padrões** | SOLID, Repository, Strategy, Factory, TDD |

---

## 12. Estrutura do Repositório

```text
genAIpipeETLPython/
├── api/                             # Microsserviço Java 25 + Spring Boot 4
│   ├── src/main/java/com/santander/genai/etl/
│   │   ├── config/                  # SecurityConfig (OAuth2/JWT) e OpenApiConfig
│   │   ├── controller/              # ClienteController, MensagemController
│   │   ├── domain/                  # Entidades JPA: Cliente, MensagemMarketing
│   │   ├── dto/                     # DTOs com Bean Validation
│   │   ├── mapper/                  # Conversão entre DTOs e entidades
│   │   ├── repository/              # CRUD com JPA
│   │   ├── service/impl/            # Lógica de negócio, idempotência SHA-256
│   │   └── util/                    # IdempotencyUtil, ValidationUtil
│   └── src/main/resources/
│       ├── application.yml          # Config Spring, datasource via env, Flyway, JWT
│       ├── application-dev.yml      # Perfil de desenvolvimento local
│       └── db/migration/V1init.sql  # Migrações Flyway
│
├── etl/                             # Pipeline ETL Python 3.12
│   ├── src/etl/
│   │   ├── extract.py               # Leitura de planilha + fetch via API + JWT
│   │   ├── transform.py             # Engenharia de prompts + sanitização
│   │   ├── load.py                  # POST das mensagens validadas
│   │   ├── pipeline.py              # Orquestrador E → T → L
│   │   ├── clients/
│   │   │   ├── api_client.py        # Cliente HTTP GET/POST
│   │   │   └── ai_provider.py       # Interface AIProvider + MockAIProvider
│   │   ├── prompts/
│   │   │   ├── base_prompt.txt      # Template de prompt com tom e limites
│   │   │   └── safety_rules.md      # Regras de segurança para textos de marketing
│   │   ├── models/
│   │   │   ├── customer.py          # Schema Pydantic para clientes
│   │   │   └── message.py           # Schema Pydantic para mensagens
│   │   └── utils/
│   │       ├── io.py                # Utilitários de IO
│   │       ├── validation.py        # Validação de canal
│   │       └── logging.py           # Logger com rotação de arquivo (Loguru)
│   ├── src/app.py                   # CLI: executa pipeline com parâmetros
│   └── tests/
│       ├── test_extract.py
│       ├── test_transform.py
│       ├── test_load.py
│       └── test_pipeline_integration.py
│
├── docker/
│   ├── api.Dockerfile               # JRE 25, healthcheck, porta 8080
│   └── postgres.Dockerfile          # PostgreSQL 16 com volume de dados
├── docker-compose.yml
├── notebooks/
│   └── SantanderDevWeek2025.ipynb   # Demo Colab do pipeline completo
├── docs/                            # Sphinx: dicionário de dados, model card, arquitetura
├── .github/workflows/               # CI Java, CI Python, Build Docs
├── Makefile
├── .env.example
└── .gitignore
```

---

## 13. Como Executar

### Pré-requisitos

- Docker >= 24 e Docker Compose >= 2
- Git >= 2.40
- JDK 25
- Python 3.12

### Passo 1 — Clonar e configurar ambiente

```bash
git clone https://github.com/Santosdevbjj/genAIpipeETLPython.git
cd genAIpipeETLPython
cp .env.example .env   # Edite com suas credenciais
```

Variáveis obrigatórias no `.env`:

```env
SPRING_PROFILES_ACTIVE=dev
DB_HOST=localhost
DB_PORT=5432
DB_NAME=santander
DB_USER=postgres
DB_PASSWORD=postgres
JWT_ISSUER=http://localhost/issuer
JWT_AUDIENCE=genai-api
JWT_PUBLIC_KEY=-----BEGIN PUBLIC KEY-----...-----END PUBLIC KEY-----
AI_PROVIDER=mock          # Use 'mock' em dev; configure provider real em produção
AI_MODEL=gpt-4o-mini
AI_API_KEY=replace-with-your-key
API_BASE=http://localhost:8080/api
```

### Passo 2 — Subir infraestrutura (Docker)

```bash
make docker-up
```

Sobe PostgreSQL 16 + API Java com healthcheck configurado.

### Passo 3 — Rodar a API Java (alternativa local)

```bash
cd api
./gradlew clean build
java -jar build/libs/genai-etl-api.jar
```

Swagger UI: `http://localhost:8080/swagger-ui`

### Passo 4 — Rodar o Pipeline ETL Python

```bash
cd etl
python -m venv .venv
source .venv/bin/activate      # Linux/Mac
# .\.venv\Scripts\activate     # Windows

pip install -r requirements.txt

python src/app.py \
  --input data/clientes.xlsx \
  --api-base http://localhost:8080/api \
  --jwt "SEU_TOKEN" \
  --channel app
```

### Passo 5 — Demo no Google Colab

Abrir `notebooks/SantanderDevWeek2025.ipynb` e executar as células para rodar o pipeline demonstrativo sem infraestrutura local.

---

## 14. Estratégia de Testes

**Java:**
- Testes unitários de serviços com **Mockito** — sem subir contexto Spring.
- Testes de controller com **MockMvc**.
- Testes de integração com **Testcontainers** usando PostgreSQL real.

**Python:**
- Mocks de rede com **pytest-httpx** — valida o ETL completo sem consumir tokens ou depender de API online.
- Testes unitários por etapa: extract, transform, load.
- Teste de integração do pipeline completo com mocks de rede (verifica 2 chamadas HTTP — GET + POST).

```bash
# Java
./gradlew test

# Python
pytest -v etl/tests/
```

---

## 15. Próximos Passos

- **Observabilidade:** integrar Micrometer para exposição de métricas ao Prometheus/Grafana — monitorar latência por cliente e taxa de sucesso do ETL em tempo real.
- **Cache:** adicionar Redis para cache de consultas frequentes e reduzir chamadas repetidas à API Java.
- **Dashboard:** criar painel em Streamlit para visualização dos resultados do ETL — mensagens geradas por segmento, taxa de idempotência ativada, versão do modelo utilizada.
- **Model Registry:** versionar prompts como artefatos em um Model Registry — rastreabilidade de qual versão de prompt gerou cada mensagem.
- **Análise Preditiva:** integrar modelo de propensão de resposta por canal — priorizar canal com maior probabilidade de abertura por segmento de cliente.
- **Escalabilidade:** migrar para processamento paralelo com Apache Kafka ou Celery para bases com milhões de registros.

---

## Contato

**Autor:** Sergio Santos

[![Portfólio Sérgio Santos](https://img.shields.io/badge/Portfólio-Sérgio_Santos-111827?style=for-the-badge&logo=githubpages&logoColor=00eaff)](https://portfoliosantossergio.vercel.app)

[![LinkedIn Sérgio Santos](https://img.shields.io/badge/LinkedIn-Sérgio_Santos-0A66C2?style=for-the-badge&logo=linkedin&logoColor=white)](https://linkedin.com/in/santossergioluiz)
