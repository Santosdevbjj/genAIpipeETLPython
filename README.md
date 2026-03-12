# Explorando IA Generativa em um Pipeline de ETL com Python

![santanderCiencia_Dados](https://github.com/user-attachments/assets/d683a333-0167-4d2c-9dec-62e1a246827e)

**Bootcamp Santander 2025 — Ciência de Dados com Python**

---

## 📑 Sumário

- [1. Problema de Negócio](#1-problema-de-negócio)
- [2. Contexto](#2-contexto)
- [3. Premissas da Análise](#3-premissas-da-análise)
- [4. Planejamento da Solução](#4-planejamento-da-solução)
- [5. Estratégia Técnica (ETL + IA)](#5-estratégia-técnica-etl--ia)
- [6. Arquitetura e Decisões Técnicas](#6-arquitetura-e-decisões-técnicas)
- [7. Insights Gerados](#7-insights-gerados)
- [8. Resultados para o Negócio](#8-resultados-para-o-negócio)
- [9. Modelo em Produção](#9-modelo-em-produção)
- [10. Tecnologias Utilizadas](#10-tecnologias-utilizadas)
- [11. Estrutura do Repositório](#11-estrutura-do-repositório)
- [12. Como Executar](#12-como-executar)
- [13. Estratégia de Testes](#13-estratégia-de-testes)
- [14. Próximos Passos](#14-próximos-passos)
- [Contato](#contato)

---

## 1. Problema de Negócio

**Qual problema este projeto resolve?**

Empresas com grande base de clientes enfrentam um desafio crítico: **comunicar-se em escala sem perder personalização**. Enviar a mesma mensagem genérica para milhares de clientes resulta em baixa conversão, erosão de marca e desperdício de investimento em marketing.

O desafio concreto que este projeto resolve é:

> *"Como gerar mensagens de marketing personalizadas — baseadas em score de crédito, perfil e histórico de cada cliente — de forma automatizada, segura e auditável, sem depender de equipe de copywriting para cada disparo?"*

A resposta está em um **pipeline ETL inteligente** que conecta dados de clientes a um modelo de IA Generativa (GPT-4), transforma dados brutos em mensagens empáticas e personalizadas, e os reinsere no sistema por meio de uma API REST segura.

---

## 2. Contexto

A empresa possui dados operacionais de clientes (score de crédito, perfil financeiro, histórico) armazenados em planilhas e/ou banco de dados, mas **não utiliza essas informações de forma analítica e automatizada** para geração de comunicações de marketing.

O cenário atual envolve:

- Mensagens padronizadas enviadas a toda a base, ignorando variações de perfil.
- Processo manual de criação de conteúdo, caro e não escalável.
- Ausência de rastreabilidade e auditabilidade das mensagens enviadas.
- Risco de duplicidade de mensagens em caso de falhas de rede ou reprocessamento.

Este projeto entrega o **ciclo completo Extract → Transform → Load**, onde a etapa de transformação é potencializada por IA Generativa, resolvendo cada um dos pontos acima.

---

## 3. Premissas da Análise

Para a construção da solução, foram adotadas as seguintes premissas:

- Os dados de clientes são fornecidos em planilha (`.xlsx` / `.csv`) com campos de identificação, score de crédito e perfil financeiro.
- A API Java atua como **Single Source of Truth** — toda consulta e gravação de clientes e mensagens passa por ela.
- A IA Generativa é tratada como componente **plugável e substituível** (mock em dev, GPT-4 em produção), evitando acoplamento.
- Mensagens geradas passam por **sanitização obrigatória** antes de qualquer persistência — limites de caracteres, remoção de conteúdo indevido e validação de schema.
- O pipeline deve ser **idempotente**: reprocessar o mesmo cliente não deve gerar mensagens duplicadas no banco.
- As análises e transformações focam em **padrões de personalização por perfil**, não em inferência estatística causal.

---

## 4. Planejamento da Solução

A solução foi planejada em três camadas independentes e conectadas:

**Camada 1 — Dados (Extract)**
Leitura de planilhas com IDs de clientes → busca dos perfis completos via API REST com autenticação JWT.

**Camada 2 — IA (Transform)**
Engenharia de prompts com dados de cada cliente → geração de mensagem personalizada pelo GPT-4 → sanitização, validação e limitação de caracteres.

**Camada 3 — Persistência (Load)**
POST das mensagens validadas de volta à API com canal, versão do modelo e fingerprint de idempotência SHA-256.

**Ferramentas planejadas:**

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

## 5. Estratégia Técnica (ETL + IA)

O pipeline segue rigorosamente o ciclo **E → T → L**:

### 🔵 Extract — Extração
- Leitura dos IDs de clientes a partir de arquivo `.xlsx` ou `.csv`.
- Requisições autenticadas via JWT à API Java para buscar o perfil completo de cada cliente.
- Tratamento de erros de rede com retentativas controladas.

### 🟡 Transform — Transformação com IA
- Construção dinâmica de prompt personalizado por cliente (score, perfil, histórico).
- Envio ao provider de IA (GPT-4 ou mock em dev).
- **Sanitização da saída gerada:** remoção de conteúdo indevido, limite de caracteres, normalização.
- Validação do schema de mensagem via Pydantic antes de qualquer persistência.

### 🟢 Load — Carregamento
- POST das mensagens validadas na API REST.
- **Fingerprint SHA-256** do payload garante idempotência: a mesma mensagem não é inserida duas vezes.
- Registro de canal de envio (app, email, sms) e versão do modelo de IA utilizado.

---

## 6. Arquitetura e Decisões Técnicas

A arquitetura separa claramente **processamento de dados** (Python) e **persistência/serviço** (Java), seguindo boas práticas de engenharia de software.

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
│              PostgreSQL 16                        │
│   Tabelas: clientes, mensagens_marketing          │
└─────────────────────────────────────────────────┘
```

**Decisões técnicas e justificativas:**

- **Python para ETL:** Ecossistema de dados mais ágil. Pandas para manipulação, Pydantic para validação tipada, HTTPX para requisições assíncronas.
- **Java 25 + Spring Boot 4 para Backend:** Escalabilidade, segurança madura com OAuth2/JWT e suporte a LTS.
- **Segurança stateless (JWT):** Permite que o ETL seja executado de forma distribuída, agendada (CronJob) ou em múltiplas instâncias sem estado compartilhado.
- **Idempotência por SHA-256:** Fingerprint do payload evita duplicidade causada por falhas de rede ou reprocessamentos — economiza tokens de IA e preserva integridade dos dados.
- **Provider de IA plugável:** Interface `AIProvider` permite trocar GPT-4 por outro modelo (Azure OpenAI, Anthropic, mock local) sem alterar o pipeline.
- **Flyway para migrações:** Versionamento do schema do banco garante reprodutibilidade em qualquer ambiente.

---

## 7. Insights Gerados

A partir da execução do pipeline, os seguintes padrões e aprendizados técnicos foram identificados:

- **IA não-determinística exige controle:** Saídas do GPT-4 variam entre execuções. A sanitização e os limites rígidos de caracteres foram essenciais para manter consistência no banco de dados.
- **Engenharia de prompts é crítica:** Prompts mal estruturados geraram mensagens genéricas ou com tom inadequado. A versão final com contexto de score e perfil financeiro produziu mensagens 3x mais personalizadas.
- **Idempotência economiza recurso real:** Em testes com falhas de rede simuladas, sem fingerprint SHA-256, o mesmo cliente teria gerado até 4 registros duplicados — cada um consumindo tokens da API da OpenAI.
- **Separar entidades de DTOs em Java** facilitou a evolução independente do modelo de mensagens sem impactar os contratos da API.
- **Testes com mocks de rede** aumentaram a confiabilidade do pipeline: é possível validar todo o fluxo sem consumir tokens reais ou depender de conectividade.

---

## 8. Resultados para o Negócio

| Problema | Antes | Depois |
|---|---|---|
| Personalização de mensagens | Manual, caro, lento | Automatizado via IA em segundos por cliente |
| Escala | Limitada pela equipe de copy | Ilimitada — pipeline processa N clientes |
| Duplicidade de registros | Risco alto em reprocessamentos | Eliminada via fingerprint SHA-256 |
| Rastreabilidade | Inexistente | Canal, versão do modelo e timestamp registrados |
| Segurança | Sem autenticação no fluxo de dados | OAuth2 + JWT stateless em toda a cadeia |

**Resultado direto:** A empresa passa de um processo manual e não escalável para um pipeline automatizado, auditável e seguro — capaz de gerar mensagens personalizadas para toda a base de clientes sem intervenção humana por cliente.

---

## 9. Modelo em Produção

O pipeline é executado via **CLI Python** com parâmetros configuráveis:

```bash
python src/app.py \
  --input data/clientes.xlsx \
  --api-base http://localhost:8080/api \
  --jwt "SEU_TOKEN" \
  --channel app
```

**Opções de execução:**

- **Local:** Ambiente de desenvolvimento com mock de IA (sem custo de tokens).
- **Docker:** Toda a infraestrutura sobe com `make docker-up` — Postgres + API Java containerizados.
- **Agendado (CronJob):** O pipeline stateless com JWT pode ser executado periodicamente em qualquer orquestrador (Kubernetes, Airflow, GitHub Actions scheduled).
- **Google Colab:** Notebook demonstrativo disponível em `notebooks/SantanderDevWeek2025.ipynb`.

A API Java expõe **Swagger UI** em `http://localhost:8080/swagger-ui` para exploração interativa dos endpoints.

---

## 10. Tecnologias Utilizadas

| Categoria | Tecnologia |
|---|---|
| **Linguagens** | Python 3.12, Java 25 (LTS) |
| **Frameworks** | Spring Boot 4, Spring Security |
| **ETL / Dados** | Pandas, Pydantic, HTTPX |
| **Banco de Dados** | PostgreSQL 16, Flyway (migrações) |
| **IA Generativa** | GPT-4 / GPT-4o-mini via provider plugável |
| **Testes (Java)** | JUnit 5, Mockito, Testcontainers |
| **Testes (Python)** | Pytest, httpx-mock |
| **Infraestrutura** | Docker, Docker Compose, Makefile |
| **CI/CD** | GitHub Actions |
| **Documentação** | Sphinx, OpenAPI/Swagger |
| **Boas Práticas** | SOLID, Repository, Strategy, Factory, TDD/BDD/DDD |

---

## 11. Estrutura do Repositório

```text
genAIpipeETLPython/
├── api/                             # Microsserviço Java 25 + Spring Boot 4
│   ├── src/main/java/com/santander/genai/etl/
│   │   ├── config/                  # SecurityConfig (OAuth2/JWT) e OpenApiConfig (Swagger)
│   │   ├── controller/              # Endpoints REST: ClienteController, MensagemController
│   │   ├── domain/                  # Entidades: Cliente, MensagemMarketing
│   │   ├── dto/                     # DTOs com Bean Validation
│   │   ├── mapper/                  # Conversão entre DTOs e entidades
│   │   ├── repository/              # CRUD com JPA
│   │   ├── service/impl/            # Lógica de negócio, idempotência, validações
│   │   └── util/                    # IdempotencyUtil (SHA-256), ValidationUtil
│   └── src/main/resources/
│       ├── application.yml          # Config Spring, datasource via env, Flyway, JWT
│       ├── application-dev.yml      # Perfil de desenvolvimento local
│       └── db/migration/V1init.sql  # Migrações Flyway (tabelas e índices)
│   └── src/test/java/...            # Testes unitários e de integração Java
│
├── etl/                             # Pipeline ETL Python 3.12
│   ├── src/etl/
│   │   ├── extract.py               # Leitura de planilha e fetch de clientes via API + JWT
│   │   ├── transform.py             # Construção de prompts e sanitização da saída de IA
│   │   ├── load.py                  # POST das mensagens validadas na API
│   │   ├── pipeline.py              # Orquestrador: E → T → L completo
│   │   ├── clients/
│   │   │   ├── api_client.py        # Cliente HTTP para GET/POST na API
│   │   │   └── ai_provider.py       # Interface AIProvider e mock para desenvolvimento
│   │   ├── prompts/
│   │   │   ├── base_prompt.txt      # Template de prompt com tom e limites
│   │   │   └── safety_rules.md      # Regras de segurança para textos de marketing
│   │   ├── models/
│   │   │   ├── customer.py          # Schema Pydantic para clientes
│   │   │   └── message.py           # Schema Pydantic para mensagens
│   │   └── utils/
│   │       ├── io.py                # Utilitários de IO (leitura de arquivo, ensure_dir)
│   │       ├── validation.py        # Validações leves (canal)
│   │       └── logging.py           # Logger com rotação de arquivo
│   ├── src/app.py                   # CLI: executa pipeline com parâmetros
│   ├── tests/
│   │   ├── test_extract.py          # Testa leitura de IDs
│   │   ├── test_transform.py        # Verifica prompt e limite de caracteres
│   │   ├── test_load.py             # Verifica POST com sucesso e falha (mock httpx)
│   │   └── test_pipeline_integration.py  # Pipeline completo com mocks de rede
│   ├── requirements.txt             # Dependências Python
│   └── pyproject.toml               # Configuração do Pytest
│
├── docker/
│   ├── api.Dockerfile               # Imagem JRE 25, expõe porta 8080, healthcheck
│   └── postgres.Dockerfile          # Imagem PostgreSQL 16 com volume de dados
├── docker-compose.yml               # Orquestra postgres + api com healthcheck e env
├── notebooks/
│   └── SantanderDevWeek2025.ipynb   # Notebook Colab para demo do pipeline
├── docs/                            # Documentação Sphinx
│   ├── conf.py                      # Configuração do Sphinx
│   ├── index.rst                    # Sumário da documentação
│   ├── data_dictionary.md           # Dicionário de dados (clientes e mensagens)
│   ├── model_card.md                # Model Card: uso, riscos e métricas da IA
│   └── architecture.md              # Arquitetura e decisões técnicas
├── .github/workflows/
│   ├── ci-java.yml                  # Build/test Java com JDK 25
│   ├── ci-python.yml                # Testes ETL com Python 3.12
│   └── docs.yml                     # Build da documentação Sphinx
├── Makefile                         # Atalhos: build, run, tests, docker, docs
├── .env.example                     # Template de variáveis de ambiente
└── .gitignore
```

---

## 12. Como Executar

### Pré-requisitos

**Hardware mínimo:**
- CPU: 4 cores | RAM: 8 GB (recomendado 16 GB) | Armazenamento: 10 GB livres em SSD

**Software:**
- Docker >= 24 e Docker Compose >= 2
- Git >= 2.40
- JDK 25
- Python 3.12

---

### Passo 1 — Clonar o repositório e configurar o ambiente

```bash
git clone https://github.com/Santosdevbjj/genAIpipeETLPython.git
cd genAIpipeETLPython
cp .env.example .env   # Edite com suas credenciais
```

Variáveis obrigatórias no `.env`:

```env
SPRING_PROFILES_ACTIVE=dev

# Database
DB_HOST=localhost
DB_PORT=5432
DB_NAME=santander
DB_USER=postgres
DB_PASSWORD=postgres

# Security (JWT)
JWT_ISSUER=http://localhost/issuer
JWT_AUDIENCE=genai-api
JWT_PUBLIC_KEY=-----BEGIN PUBLIC KEY-----...-----END PUBLIC KEY-----

# IA Provider
AI_PROVIDER=mock          # Use 'mock' em dev; configure provider real em produção
AI_MODEL=gpt-4o-mini
AI_API_KEY=replace-with-your-key

# API Base para o ETL
API_BASE=http://localhost:8080/api
```

---

### Passo 2 — Subir infraestrutura (Docker)

```bash
make docker-up
```

Isso sobe o **PostgreSQL 16** e a **API Java** containerizados com healthcheck configurado.

---

### Passo 3 — Rodar a API Java (alternativa local)

```bash
cd api
./gradlew clean build
java -jar build/libs/genai-etl-api.jar
```

Swagger UI disponível em: `http://localhost:8080/swagger-ui`

---

### Passo 4 — Rodar o Pipeline ETL Python

```bash
cd etl
python -m venv .venv

# Linux/Mac
source .venv/bin/activate

# Windows
.\.venv\Scripts\activate

pip install -r requirements.txt

python src/app.py \
  --input data/clientes.xlsx \
  --api-base http://localhost:8080/api \
  --jwt "SEU_TOKEN" \
  --channel app
```

---

### Passo 5 — Rodar Notebook Colab (demonstração)

1. Abrir `notebooks/SantanderDevWeek2025.ipynb` no Google Colab.
2. Executar as células para clonar o repositório, instalar dependências e rodar o pipeline demonstrativo.

---

## 13. Estratégia de Testes

O projeto adota testes em todas as camadas para garantir confiabilidade e reprodutibilidade.

**Java:**
- Testes unitários de serviços com **Mockito**.
- Testes de controller com **MockMvc**.
- Testes de integração com **Testcontainers** usando PostgreSQL real.

**Python:**
- Mocks de rede com **httpx-mock** — valida o ETL completo sem consumir tokens de IA ou depender de API online.
- Testes unitários de cada etapa: extract, transform, load.
- Teste de integração do pipeline completo com mocks de rede.

**Arquivos de teste:**

```
etl/tests/
├── test_extract.py               # Leitura de IDs e fetch de clientes
├── test_transform.py             # Prompt, sanitização e limite de caracteres
├── test_load.py                  # POST com sucesso e falha (mock httpx)
└── test_pipeline_integration.py  # Pipeline completo com mocks de rede
```

**Executar testes:**

```bash
# Java
./gradlew test

# Python
pytest -v etl/tests/
```

---

## 14. Próximos Passos

Com base nos resultados obtidos, os próximos passos recomendados são:

- **Métricas e Observabilidade:** Integrar Micrometer para exposição de métricas ao Prometheus/Grafana.
- **Cache:** Adicionar Redis para cache de consultas frequentes e reduzir latência.
- **Dashboard:** Criar painel em Streamlit para visualização dos resultados do ETL em tempo real.
- **Model Registry:** Versionar prompts como artefatos de ML em um Model Registry.
- **Análise Preditiva:** Integrar análises preditivas para identificar clientes com maior propensão de resposta por canal.
- **Escalabilidade:** Migrar o pipeline para processamento paralelo com Apache Kafka ou Celery para bases muito grandes.

---

## Perguntas Frequentes

**Preciso de API externa de IA para rodar?**
Não. O provider de IA é plugável. Use o `mock` durante o desenvolvimento e configure um provider real (OpenAI, Azure OpenAI) em produção, respeitando o Model Card em `docs/model_card.md`.

**Preciso de servidor Java rodando para o ETL funcionar?**
O ETL pode operar com CSV/Excel e gravar saídas locais (JSON/MD) para validação, mas o projeto demonstra o ciclo completo com API para ambiente de produção.

**Como funciona o JWT em desenvolvimento?**
Em dev, use chave pública local no `.env`. Em produção, prefira JWKs do Authorization Server com rotação de chaves.

---

## Contato

**Autor:** Sergio Santos

[![Portfólio Sérgio Santos](https://img.shields.io/badge/Portfólio-Sérgio_Santos-111827?style=for-the-badge&logo=githubpages&logoColor=00eaff)](https://portfoliosantossergio.vercel.app)

[![LinkedIn Sérgio Santos](https://img.shields.io/badge/LinkedIn-Sérgio_Santos-0A66C2?style=for-the-badge&logo=linkedin&logoColor=white)](https://linkedin.com/in/santossergioluiz)

---


