### Explorando IA Generativa em um Pipeline de ETL com Python   
 
![santanderCiencia_Dados](https://github.com/user-attachments/assets/d683a333-0167-4d2c-9dec-62e1a246827e)


**Bootcamp Santander 2025 - Ciência de Dados com Python**


---

## 📑 Sumário

- [Visão Geral](#visão-geral)  
- [Objetivo do Projeto](#objetivo-do-projeto)  
- [Arquitetura e Decisões Técnicas](#arquitetura-e-decisoes-tecnicas)  
- [Tecnologias Utilizadas](#tecnologias-utilizadas)  
- [Estrutura do Repositório](#estrutura-do-repositorio)  
- [Requisitos do Sistema](#requisitos-do-sistema)  
- [Como Executar](#como-executar)  
- [Estratégia de Testes](#estrategia-de-testes)  
- [Aprendizados e Desafios](#aprendizados-e-desafios)  
- [Próximos Passos](#proximos-passos)  
- [Contato](#contato)  

---

## 🌐 Visão Geral

Este projeto apresenta um ecossistema completo de dados: um **microsserviço robusto em Java 25** integrado a um **pipeline de ETL em Python 3.12** que utiliza **IA Generativa (GPT-4)** para personalização de marketing.

O sistema resolve o problema de **escala na comunicação com clientes**: extraímos dados brutos, utilizamos IA para gerar mensagens personalizadas e reinserimos esses dados em um ambiente seguro, auditável e versionado.  

---

## 🎯 Objetivo do Projeto

- **Pipeline ETL Realista:** Demonstrar o ciclo Extract-Transform-Load entre diferentes tecnologias (Python ↔ Java).  
- **Engenharia de Prompts:** Aplicar IA para transformar dados de crédito e score em mensagens de marketing empáticas e sanitizadas.  
- **Segurança e Robustez:** Implementar padrões de mercado como OAuth2, JWT stateless e idempotência.  
- **Cultura DevOps:** Garantir reprodutibilidade via Docker, Makefile e automação com CI/CD.  

---

## 🏗 Arquitetura e Decisões Técnicas

A arquitetura separa claramente **processamento de dados** e **persistência/serviço**, seguindo boas práticas de engenharia de software.

- **Python para ETL:** Escolhido pela agilidade no ecossistema de dados. Pandas para manipulação, Pydantic para validação e integração com GPT-4 via interface plugável.  
- **Java para Backend:** Spring Boot 4 com Java 25 garante escalabilidade e segurança. API atua como "Single Source of Truth" para clientes e mensagens.  
- **Segurança:** OAuth2 + JWT stateless permite que o ETL seja executado de forma distribuída ou agendada (ex.: CronJob).  
- **Idempotência:** Fingerprint SHA-256 no carregamento de mensagens evita duplicidade causada por falhas de rede.  
- **Trade-offs:** Optamos por balancear segurança, desempenho e simplicidade, mantendo o pipeline testável e modular.  

---

## 🛠 Tecnologias Utilizadas

- **Linguagens:** Java 25 (LTS), Python 3.12  
- **Frameworks e Bibliotecas:** Spring Boot 4, Spring Security, JPA/Hibernate, Pandas, Pydantic, Pytest, HTTPX  
- **Banco de Dados:** PostgreSQL 16, Flyway (migrações)  
- **Inteligência Artificial:** GPT-4 via provider plugável, engenharia de prompts  
- **Testes:** JUnit 5, Mockito, Testcontainers (Java), Pytest + httpx-mock (Python)  
- **Infraestrutura e DevOps:** Docker, Docker Compose, Makefile, GitHub Actions, Sphinx  
- **Boas Práticas:** SOLID, Design Patterns (Repository, Strategy, Factory), TDD/BDD/DDD  

---

## 📂 Estrutura do Repositório

```text
genAIpipeETLPython/
├── api/                        # Microsserviço Java
│   ├── src/main/java/com/santander/genai/etl/
│   │   ├── config/             # Configurações de segurança e Swagger/OpenAPI
│   │   ├── controller/         # Endpoints REST (Clientes e Mensagens)
│   │   ├── domain/             # Entidades: Cliente, MensagemMarketing
│   │   ├── dto/                # DTOs de entrada/saída com validação
│   │   ├── mapper/             # Conversões entre DTOs e entidades
│   │   ├── repository/         # CRUD com JPA
│   │   └── service/impl/       # Lógica de negócio, idempotência e validações
│   └── src/main/resources/     # application.yml, migrações Flyway
│   └── src/test/java/...       # Testes unitários e integração
├── etl/                        # Pipeline Python
│   ├── src/etl/
│   │   ├── clients/            # Integração com API e provider de IA
│   │   ├── prompts/            # Templates de prompts e regras de segurança
│   │   ├── models/             # Schemas Pydantic (Cliente, Mensagem)
│   │   ├── utils/              # Logging, IO, validação de dados
│   │   ├── extract.py           # Leitura de planilhas e fetch de clientes
│   │   ├── transform.py         # Construção de prompts e sanitização
│   │   ├── load.py              # POST de mensagens via API
│   │   └── pipeline.py          # Orquestra ETL completo
│   └── tests/                  # Testes unitários e integração do ETL
│   └── requirements.txt        # Dependências Python
│   └── pyproject.toml          # Configuração do Pytest
├── docker/                     # Dockerfiles e composição de containers
├── notebooks/                  # Google Colab para demonstração
├── docs/                       # Documentação Sphinx, dicionário de dados e Model Cards
├── Makefile                    # Comandos de build, run, tests e docs
└── .env.example                # Template de variáveis de ambiente

```

---


**Descrição das Pastas e arquivos**

- **docker/**
  - **api.Dockerfile:** Imagem do microsserviço Java (JRE 25), expõe porta 8080 e healthcheck.
  - **postgres.Dockerfile:** Imagem base do PostgreSQL 16 com variáveis de ambiente e volume para dados.
- **docker-compose.yml:** Orquestra containers de postgres e api, injeta variáveis de ambiente, healthcheck e mapeia portas.
- **.env.example:** Template de variáveis (DB, JWT, IA provider e base da API) para configurar ambientes.
- **api/ (Java 25 + Spring Boot 4)**
  - **build.gradle:** Dependências, plugin Spring, toolchain Java 25, testes e empacotamento.
  - **settings.gradle:** Nome do projeto.
  - src/main/java/com/santander/genai/etl/
    - **GenAiEtlApplication.java:** Classe principal para bootstrap do Spring.
    - **config/**
      - **SecurityConfig.java:** Resource Server OAuth2/JWT, regras de autorização por escopo.
      - **OpenApiConfig.java:** Configuração do Swagger/OpenAPI com bearer auth.
    - **domain/**
      - **Cliente.java:** Entidade de cliente com validações e campos principais.
      - **MensagemMarketing.java:** Entidade de mensagem com referência ao cliente, canal, texto e metadata.
    - **repository/**
      - **ClienteRepository.java:** CRUD via JPA.
      - **MensagemMarketingRepository.java:** CRUD e busca por clienteId.
    - **service/**
      - **ClienteService.java:** Interface do serviço de clientes (CRUD).
      - **MensagemService.java:** Interface do serviço de mensagens.
      - **impl/**
        - **ClienteServiceImpl.java:** Implementação com validações e atualização.
        - **MensagemServiceImpl.java:** Implementação com idempotência simples e criação de mensagens.
    - **controller/**
      - **ClienteController.java:** Endpoints REST para clientes com escopos api:read e api:write.
      - **MensagemController.java:** Endpoints para criação e consulta de mensagens.
    - **dto/**
      - **ClienteDTO.java:** DTO de entrada/saída com Bean Validation.
      - **MensagemDTO.java:** DTO de mensagens com validação.
    - **mapper/**
      - **DtoMapper.java:** Conversões entre entidades e DTOs.
    - **util/**
      - **IdempotencyUtil.java:** Fingerprint SHA-256 do payload para evitar duplicidade.
      - **ValidationUtil.java:** Validações adicionais de DTOs (defensive programming).
  - **src/main/resources/**
    - **application.yml:** Configurações gerais do Spring, datasource via env, Flyway, JWT.
    - **application-dev.yml:** Perfil de desenvolvimento com conexão local.
    - **db/migration/V1init.sql:** Migrações Flyway (tabelas e índices).
  - **src/tests/**
    - **java/.../service/ClienteServiceTest.java:** Teste unitário do serviço com Mockito.
    - **java/.../controller/ClienteControllerTest.java:** Teste de controller com MockMvc (exemplo sem segurança full).
    - **java/.../repository/ClienteRepositoryTest.java:** Placeholder para testes com Testcontainers.
    - **resources/application-test.yml:** Config de testes com H2 em modo Postgres.
- **etl/ (Python 3.12)**
  - **requirements.txt:** Dependências do ETL (pandas, httpx, pydantic, pytest, dotenv, loguru).
  - **pyproject.toml:** Metadados e configuração do Pytest.
  - **src/etl/**
    - **init.py:** Inicialização do pacote.
    - **extract.py:** Leitura de IDs de planilha/CSV e fetch dos clientes na API com JWT.
    - **transform.py:** Construção de prompt e sanitização da saída gerada.
    - **load.py:** POST das mensagens na API com canal e versão do modelo.
    - **prompts/base_prompt.txt:** Prompt base para IA com tom e limites.
    - **prompts/safety_rules.md:** Regras de segurança para textos de marketing.
    - **clients/api_client.py:** Cliente HTTP para GET/POST na API.
    - **clients/ai_provider.py:** Interface AIProvider e mock provider para desenvolvimento.
    - **models/customer.py:** Modelo Pydantic para clientes.
    - **models/message.py:** Modelo Pydantic para mensagens.
    - **utils/io.py:** Utilidades de IO (leitura de arquivo, ensure_dir).
    - **utils/validation.py:** Validações leves (canal).
    - **utils/logging.py:** Logger com rotação de arquivo.
    - **pipeline.py:** Orquestra E-T-L: extrai, gera texto com IA, sanitiza e carrega via API.
  - **src/app.py:** CLI para executar o pipeline com parâmetros (input, api-base, jwt, canal).
  - **tests/**
    - **test_extract.py:** Testa leitura de IDs.
    - **test_transform.py:** Verifica prompt e limite de caracteres.
    - **test_load.py:** Verifica POST com sucesso e falha (mock httpx).
    - **testpipelineintegration.py:** Integra pipeline com mocks de rede.
- **notebooks/**
  - **SantanderDevWeek2025.ipynb:** Notebook Colab para demonstração de execução do pipeline com repositório e ambiente.
- **docs/ (Sphinx + Markdown)**
  - **conf.py:** Configuração do Sphinx (tema, extensões).
  - **index.rst:** Sumário da documentação.
  - **data_dictionary.md:** Dicionário de dados (clientes e mensagens).
  - **model_card.md:** Model Card do componente gerativo (uso, riscos, métricas).
  - **architecture.md:** Visão da arquitetura e decisões técnicas.
- **.github/workflows/**
  - **ci-java.yml:** Build/test Java com JDK 25.
  - **ci-python.yml:** Testes do ETL com Python 3.12.
  - **docs.yml:** Build da documentação Sphinx (opcional publicação).
- **Makefile:** Atalhos para build/run da API, venv e execução do ETL, Compose, testes e docs.
- **.gitignore:** Ignora artefatos de build, venv, caches e envs.






---

💻 **Requisitos do Sistema**

**Hardware mínimo:**
• CPU: 4 cores
• RAM: 8 GB (recomendado 16 GB)
• Armazenamento: 10 GB livres em SSD


**Software:**

• Docker >= 24
• Docker Compose >= 2
•™Git >= 2.40
• JDK 25
• Python 3.12
• Virtualenv para Python (python -m venv .venv)


**Ambiente:**
Variáveis de ambiente configuradas via .env (ex.: DB, JWT, API Base, IA provider)




## Como Executar

**1) Clonar o repositório**
   

```
git clone https://github.com/Santosdevbjj/genAIpipeETLPython.git
cd genAIpipeETLPython
cp .env.example .env  # Edite com suas credenciais
```



**3) Subir infraestrutura (Docker)**
   

```
make docker-up
```



**4) Rodar a API Java**
   
• **Local:**

```
cd api
./gradlew clean build
java -jar build/libs/genai-etl-api.jar
Swagger UI: http://localhost:8080/swagger-ui
```


**4) Rodar o Pipeline ETL Python**
   

```
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

  
  
**5) Rodar Notebook Colab**

• Abrir notebooks/SantanderDevWeek2025.ipynb

• Executar células para clonar repo, instalar dependências do ETL e rodar pipeline demonstrativo


🧪 **Estratégia de Testes**

• **Java:** Testes unitários com Mockito; integração com Testcontainers usando Postgres real

• **Python:** Mocks de rede (httpx-mock) para validar ETL sem consumir tokens de IA ou depender da API online

• **Arquivos importantes:**

• etl/tests/test_extract.py

• etl/tests/test_transform.py

• etl/tests/test_load.py

• etl/tests/test_pipeline_integration.py


**Comandos:**


```
# Java
./gradlew test
# Python
pytest -v etl/tests/
```



🧠 **Aprendizados e Desafios**

• Lidar com natureza não-determinística da IA exigiu sanitização e limites rígidos de caracteres para manter consistência no banco.

• Separar entidades de domínio e DTOs em Java facilitou manutenção e evolução do modelo de mensagens.

• Implementar idempotência economizou recursos financeiros, evitando chamadas redundantes à API da OpenAI.

• Criar testes integrados e mocks de rede aumentou a confiabilidade e reprodutibilidade do pipeline.


📈 **Próximos Passos**

• Integrar Micrometer para métricas expostas a Prometheus/Grafana

• Adicionar Redis para cache de consultas frequentes

• Criar dashboard em Streamlit para visualização dos resultados do ETL

• Versionar prompts em Model Registry como artefatos de ML




---

**Perguntas frequentes**

- **Preciso de API externa?** Não. O ETL pode operar com CSV/Excel e gravar saídas locais (JSON/MD), mas o projeto demonstra a integração com API para ciclo completo.
- **IA generativa real?** O provider é pluggable. Use o mock durante desenvolvimento e configure um provider real (OpenAI/Azure) em produção, respeitando o Model Card.
- **Tokens JWT:** Em dev, usar chave pública local no .env. Em prod, preferir JWKs do Authorization Server e rotação de chaves.

---

**Autor:** 
  Sergio Santos 


---

**Contato:**


[![Portfólio Sérgio Santos](https://img.shields.io/badge/Portfólio-Sérgio_Santos-111827?style=for-the-badge&logo=githubpages&logoColor=00eaff)](https://santosdevbjj.github.io/portfolio/)
[![LinkedIn Sérgio Santos](https://img.shields.io/badge/LinkedIn-Sérgio_Santos-0A66C2?style=for-the-badge&logo=linkedin&logoColor=white)](https://linkedin.com/in/santossergioluiz) 

---


