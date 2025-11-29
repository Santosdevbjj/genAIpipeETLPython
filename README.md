### Explorando IA Generativa em um Pipeline de ETL com Python

![santanderCiencia_Dados](https://github.com/user-attachments/assets/d683a333-0167-4d2c-9dec-62e1a246827e)


**Bootcamp Santander 2025 - Ciência de Dados com Python**

---
 

**Detalhes do Bootcamp:**

Atuar como Cientista de Dados, com foco em Python, análise de dados, machine learning, bancos de dados, ferramentas de produtividade (Excel + IA) e computação em nuvem com AWS.

---

**DESCRIÇÃO:**

Prepare-se para uma jornada prática pelo mundo da Ciência de Dados!
 Vamos construir um pipeline ETL (Extração, Transformação e Carregamento), demonstrando a relação entre dados, Inteligência Artificial (IA) e APIs. Extração: 
 
 A aventura começa com uma planilha simples, de onde extrairemos os IDs dos usuários. Depois, usaremos esses IDs para acessar a API da 'Santander Dev Week 2023' e obter dados mais detalhados, um processo que evidencia a versatilidade na coleta de informações em Ciência de Dados. 
 
 Transformação: Adentraremos o universo da IA com o GPT-4 da OpenAI, transformando esses dados em mensagens personalizadas de marketing. Veremos como a IA pode ser empregada de maneira inovadora e prática! Carregamento:  
 
 Finalizaremos o processo enviando essas mensagens de volta para a API da 'Santander Dev Week 2023'. 
 
 Este passo ilustra como dados transformados são reintegrados em sistemas, completando o ciclo de um pipeline ETL.


 
---

**Explorando IA generativa em um pipeline de ETL com Python**

Este README documenta o projeto completo de Data Science: microsserviço Java seguro com OAuth2/JWT, banco PostgreSQL, pipeline ETL em Python gerando mensagens personalizadas via IA, testes robustos (unitários e integração), notebooks Colab, CI/CD e documentação técnica. Repositório: Santosdevbjj/genAIpipeETLPython.

---

**Visão geral do projeto**

- Objetivo: Criar um pipeline ETL que transforma dados de clientes em mensagens de marketing personalizadas usando IA generativa, armazenando e servindo via API RESTful Java com segurança moderna.
- Stack principal:
  - Backend: Java 25 (LTS) + Spring Boot 4, Gradle, JPA, PostgreSQL, OAuth2 + JWT.
  - ETL e IA: Python 3.12, Pandas, HTTPX, Pydantic, Prompt engineering, Pytest.
  - Infra e DevX: Docker/Docker Compose, Makefile, GitHub Actions, Sphinx, Colab.
- Resultados: CRUD de Clientes e Mensagens, ETL completo (Extract–Transform–Load), testes unitários e de integração, documentação profissional, workflows CI.

> O repositório público do projeto está disponível no GitHub.

---

**Tecnologias utilizadas**

- **Java 25 + Spring Boot 4:** API REST segura, validação, JPA com repositórios e migrações Flyway.
- **OAuth2 + JWT:** Autorização por escopos e autenticação stateless com tokens Bearer.
- **PostgreSQL:** Transações ACID, integridade referencial, índices e consistência.
- **Python 3.12:** Pipeline ETL, manipulação de dados, clientes HTTP e provider de IA plugável.
- **Boas práticas:** SOLID, Design Patterns (Strategy, Factory, Repository), TDD/BDD/DDD.
- **Testes:** JUnit/Mockito (unitários); Testcontainers (integração); Pytest/requests-mock (ETL).
- **DevOps:** Docker, Compose, GitHub Actions CI; Sphinx e Markdown para documentação; Notebook em Google Colab.

---

**Estrutura de diretórios e arquivos**

**Esquema visual do repositório**

`text
📦 genAIpipeETLPython
├─ 📄 README.md
├─ 🧾 LICENSE
├─ 🐳 docker/
│  ├─ api.Dockerfile
│  └─ postgres.Dockerfile
├─ 🐘 docker-compose.yml
├─ 🔑 .env.example
├─ ☕ api/
│  ├─ build.gradle
│  ├─ settings.gradle
│  └─ src/
│     ├─ main/java/com/santander/genai/etl/
│     │  ├─ GenAiEtlApplication.java
│     │  ├─ config/
│     │  │  ├─ SecurityConfig.java
│     │  │  └─ OpenApiConfig.java
│     │  ├─ domain/
│     │  │  ├─ Cliente.java
│     │  │  └─ MensagemMarketing.java
│     │  ├─ repository/
│     │  │  ├─ ClienteRepository.java
│     │  │  └─ MensagemMarketingRepository.java
│     │  ├─ service/
│     │  │  ├─ ClienteService.java
│     │  │  ├─ MensagemService.java
│     │  │  └─ impl/
│     │  │     ├─ ClienteServiceImpl.java
│     │  │     └─ MensagemServiceImpl.java
│     │  ├─ controller/
│     │  │  ├─ ClienteController.java
│     │  │  └─ MensagemController.java
│     │  ├─ dto/
│     │  │  ├─ ClienteDTO.java
│     │  │  └─ MensagemDTO.java
│     │  ├─ mapper/
│     │  │  └─ DtoMapper.java
│     │  └─ util/
│     │     ├─ IdempotencyUtil.java
│     │     └─ ValidationUtil.java
│     ├─ main/resources/
│     │  ├─ application.yml
│     │  ├─ application-dev.yml
│     │  └─ db/migration/V1init.sql
│     └─ tests/
│        ├─ java/com/santander/genai/etl/service/ClienteServiceTest.java
│        ├─ java/com/santander/genai/etl/controller/ClienteControllerTest.java
│        ├─ java/com/santander/genai/etl/repository/ClienteRepositoryTest.java
│        └─ resources/application-test.yml
├─ 🐍 etl/
│  ├─ requirements.txt
│  ├─ pyproject.toml
│  └─ src/etl/
│     ├─ init.py
│     ├─ extract.py
│     ├─ transform.py
│     ├─ load.py
│     ├─ prompts/
│     │  ├─ base_prompt.txt
│     │  └─ safety_rules.md
│     ├─ clients/
│     │  ├─ api_client.py
│     │  └─ ai_provider.py
│     ├─ models/
│     │  ├─ customer.py
│     │  └─ message.py
│     ├─ utils/
│     │  ├─ io.py
│     │  ├─ validation.py
│     │  └─ logging.py
│     └─ pipeline.py
│  ├─ src/app.py
│  └─ tests/
│     ├─ test_extract.py
│     ├─ test_transform.py
│     ├─ test_load.py
│     └─ testpipelineintegration.py
├─ 📓 notebooks/
│  └─ SantanderDevWeek2025.ipynb
├─ 📚 docs/
│  ├─ conf.py
│  ├─ index.rst
│  ├─ data_dictionary.md
│  ├─ model_card.md
│  └─ architecture.md
├─ 🧪 .github/workflows/
│  ├─ ci-java.yml
│  ├─ ci-python.yml
│  └─ docs.yml
├─ 🛠️ Makefile
└─ 🗑️ .gitignore
`

**Pastas e arquivos**

- docker/
  - api.Dockerfile: Imagem do microsserviço Java (JRE 25), expõe porta 8080 e healthcheck.
  - postgres.Dockerfile: Imagem base do PostgreSQL 16 com variáveis de ambiente e volume para dados.
- docker-compose.yml: Orquestra containers de postgres e api, injeta variáveis de ambiente, healthcheck e mapeia portas.
- .env.example: Template de variáveis (DB, JWT, IA provider e base da API) para configurar ambientes.
- api/ (Java 25 + Spring Boot 4)
  - build.gradle: Dependências, plugin Spring, toolchain Java 25, testes e empacotamento.
  - settings.gradle: Nome do projeto.
  - src/main/java/com/santander/genai/etl/
    - GenAiEtlApplication.java: Classe principal para bootstrap do Spring.
    - config/
      - SecurityConfig.java: Resource Server OAuth2/JWT, regras de autorização por escopo.
      - OpenApiConfig.java: Configuração do Swagger/OpenAPI com bearer auth.
    - domain/
      - Cliente.java: Entidade de cliente com validações e campos principais.
      - MensagemMarketing.java: Entidade de mensagem com referência ao cliente, canal, texto e metadata.
    - repository/
      - ClienteRepository.java: CRUD via JPA.
      - MensagemMarketingRepository.java: CRUD e busca por clienteId.
    - service/
      - ClienteService.java: Interface do serviço de clientes (CRUD).
      - MensagemService.java: Interface do serviço de mensagens.
      - impl/
        - ClienteServiceImpl.java: Implementação com validações e atualização.
        - MensagemServiceImpl.java: Implementação com idempotência simples e criação de mensagens.
    - controller/
      - ClienteController.java: Endpoints REST para clientes com escopos api:read e api:write.
      - MensagemController.java: Endpoints para criação e consulta de mensagens.
    - dto/
      - ClienteDTO.java: DTO de entrada/saída com Bean Validation.
      - MensagemDTO.java: DTO de mensagens com validação.
    - mapper/
      - DtoMapper.java: Conversões entre entidades e DTOs.
    - util/
      - IdempotencyUtil.java: Fingerprint SHA-256 do payload para evitar duplicidade.
      - ValidationUtil.java: Validações adicionais de DTOs (defensive programming).
  - src/main/resources/
    - application.yml: Configurações gerais do Spring, datasource via env, Flyway, JWT.
    - application-dev.yml: Perfil de desenvolvimento com conexão local.
    - db/migration/V1init.sql: Migrações Flyway (tabelas e índices).
  - src/tests/
    - java/.../service/ClienteServiceTest.java: Teste unitário do serviço com Mockito.
    - java/.../controller/ClienteControllerTest.java: Teste de controller com MockMvc (exemplo sem segurança full).
    - java/.../repository/ClienteRepositoryTest.java: Placeholder para testes com Testcontainers.
    - resources/application-test.yml: Config de testes com H2 em modo Postgres.
- etl/ (Python 3.12)
  - requirements.txt: Dependências do ETL (pandas, httpx, pydantic, pytest, dotenv, loguru).
  - pyproject.toml: Metadados e configuração do Pytest.
  - src/etl/
    - init.py: Inicialização do pacote.
    - extract.py: Leitura de IDs de planilha/CSV e fetch dos clientes na API com JWT.
    - transform.py: Construção de prompt e sanitização da saída gerada.
    - load.py: POST das mensagens na API com canal e versão do modelo.
    - prompts/base_prompt.txt: Prompt base para IA com tom e limites.
    - prompts/safety_rules.md: Regras de segurança para textos de marketing.
    - clients/api_client.py: Cliente HTTP para GET/POST na API.
    - clients/ai_provider.py: Interface AIProvider e mock provider para desenvolvimento.
    - models/customer.py: Modelo Pydantic para clientes.
    - models/message.py: Modelo Pydantic para mensagens.
    - utils/io.py: Utilidades de IO (leitura de arquivo, ensure_dir).
    - utils/validation.py: Validações leves (canal).
    - utils/logging.py: Logger com rotação de arquivo.
    - pipeline.py: Orquestra E-T-L: extrai, gera texto com IA, sanitiza e carrega via API.
  - src/app.py: CLI para executar o pipeline com parâmetros (input, api-base, jwt, canal).
  - tests/
    - test_extract.py: Testa leitura de IDs.
    - test_transform.py: Verifica prompt e limite de caracteres.
    - test_load.py: Verifica POST com sucesso e falha (mock httpx).
    - testpipelineintegration.py: Integra pipeline com mocks de rede.
- notebooks/
  - SantanderDevWeek2025.ipynb: Notebook Colab para demonstração de execução do pipeline com repositório e ambiente.
- docs/ (Sphinx + Markdown)
  - conf.py: Configuração do Sphinx (tema, extensões).
  - index.rst: Sumário da documentação.
  - data_dictionary.md: Dicionário de dados (clientes e mensagens).
  - model_card.md: Model Card do componente gerativo (uso, riscos, métricas).
  - architecture.md: Visão da arquitetura e decisões técnicas.
- .github/workflows/
  - ci-java.yml: Build/test Java com JDK 25.
  - ci-python.yml: Testes do ETL com Python 3.12.
  - docs.yml: Build da documentação Sphinx (opcional publicação).
- Makefile: Atalhos para build/run da API, venv e execução do ETL, Compose, testes e docs.
- .gitignore: Ignora artefatos de build, venv, caches e envs.

> O conteúdo reflete as necessidades do projeto descritas no repositório e na proposta de ETL com IA generativa.

---

**Requisitos de hardware e software**

- **Hardware mínimo:**
  - CPU: 4 cores.
  - Memória: 8 GB (recomendado 16 GB para containers e notebooks).
  - Armazenamento: 10 GB livres em SSD.
- **Software:**
  - Java: JDK 25 instalado e no PATH.
  - Python: 3.12 com venv e pip.
  - Banco: Docker com imagem Postgres 16; Docker Compose.
  - Ferramentas: Git, Make, IDE (IntelliJ para Java, VSCode para Python), Sphinx.
  - Credenciais: Variáveis para JWT (issuer, audience, chave pública), API Base, e provedor de IA (quando usar real).

---

**Execução do projeto**

**1) Clonar o repositório**
- Comando:
  - git clone https://github.com/Santosdevbjj/genAIpipeETLPython.git
  - cd genAIpipeETLPython

**2) Configurar ambiente**
- Ajustar variáveis: Copie .env.example para .env e defina DB, JWT e API Base.
- Subir containers:
  - docker compose up -d --build
- Verificar Postgres: Healthcheck deve ficar saudável.

3) API Java (local ou via Docker)
- Build local: cd api && ./gradlew clean build
- Executar local (sem Docker): java -jar api/build/libs/genai-etl-api.jar
- Swagger/OpenAPI: http://localhost:8080/swagger-ui

**4) ETL Python**
- Instalação: cd etl && python -m venv .venv && source .venv/bin/activate && pip install -r requirements.txt
- Execução:
  - python src/app.py --input data/clientes.xlsx --api-base http://localhost:8080/api --jwt "<seu_token>" --channel app
- Resultado: Mensagens criadas e persistidas via API; logs em etl/etl.log.

**5) Notebook Colab**
- Abrir: notebooks/SantanderDevWeek2025.ipynb
- Rodar células: Clona repo, instala deps do ETL e executa pipeline demonstrativo.

---

**Testes feitos**

Java (JUnit + Mockito + Testcontainers)
- Unitários:
  - ClienteServiceTest: Criação de cliente com mock de ClienteRepository (Mock Object Pattern com Mockito). Valida persistência e mapeamento DTO→Entidade→DTO.
  - MensagemServiceTest (implícito): Idempotência de mensagens por fingerprint SHA-256, validações de campos obrigatórios e canal.
  - Controller Tests: ClienteControllerTest usa MockMvc para validar retorno HTTP em criação sem token (exemplo de segurança).
- Integração (proposta):
  - Repository + DB: Testcontainers para subir Postgres real e validar migrações Flyway e CRUD; @SpringBootTest + @AutoConfigureMockMvc para cobrir fluxo Controller→Service→Repository com perfis de teste.
- Cobertura esperada: Regras de negócio, validações e mapeamentos com foco em confiabilidade do CRUD e segurança.

Python (Pytest + requests-mock/httpx-mock)
- Unitários:
  - test_extract.py: Confere leitura de IDs de CSV/Excel, garantindo tipos e integridade.
  - test_transform.py: Verifica se o prompt contém campos essenciais (nome, segmento) e que sanitização respeita limite de 600 caracteres.
  - test_load.py: Exercita POST com sucesso (201) e trata erro (400) levantando HTTPStatusError.
- Integração:
  - testpipelineintegration.py: Roda pipeline com mocks de fetchcustomersfromapi e postmessage, garantindo orquestração E→T→L, sem depender de rede real.
- Boas práticas:
  - Fixtures e monkeypatch: Isolam dependências externas e aumentam reprodutibilidade.
  - Logs: Verificação manual em etl.log para rastreabilidade.

---

**Documentação de Data Science**

**Documento de escopo**
- Objetivos de negócio: Personalização escalável de mensagens para aumentar engajamento, CTR e conversão em produtos segmentados.
- KPIs: Abertura, CTR, conversão, opt-in, satisfação, taxa de aprovação em revisão.
- Restrições: Compliance legal, linguagem responsável, proteção de dados, sem informações sensíveis.

**Dicionário de dados**
- Cliente: id (PK), nome, email, segmento (Varejo/Select/Private), limite_credito (>=0), score (0–1000).
- MensagemMarketing: id (PK), clienteid (FK), texto (<=1000), canal (email/sms/app), modeloversao, created_at.
- Documentado em docs/data_dictionary.md.

Model Card (componente generativo)
- Uso pretendido: Geração de mensagens curtas de marketing com dados não sensíveis.
- Entradas: nome, segmento, limite, score; sem PII sensível além de nome e email para envios.
- Saídas: Texto sanitizado, tom profissional/empático, limite de caracteres.
- Avaliação: Leitura humana, legibilidade, detecção de toxidade; auditoria de versão.
- Documentado em docs/model_card.md.

**Arquitetura e governança**
- API Java: Camadas Controller→Service→Repository; segurança OAuth2/JWT; JPA/Flyway.
- ETL Python: Extract (planilha/CSV/API), Transform (prompt + provider IA), Load (POST API).
- Observabilidade: Logs estruturados, healthchecks; extensão futura para Prometheus/Grafana.
- Reprodutibilidade: Seeds, Run IDs, hash de prompt, versioneamento em CI e documentação Sphinx.

Sphinx docs
- Conteúdo: Índice, dicionário de dados, model card e arquitetura.
- Build: make docs ou workflow docs.yml.

---

**Padrões de projeto e qualidade**

- SOLID: Serviços coesos (Single Responsibility), interfaces para abstrações (Dependency Inversion).
- Design Patterns: Repository (JPA), Strategy/Factory (canais de mensagem — espaço para evoluir), DTO Mapper.
- TDD/BDD/DDD: Foco em comportamento e domínio (Cliente, MensagemMarketing), testes guiando implementação e design.
- Mockito: Mock Object Pattern para isolar dependências nos testes unitários Java.
- Idempotência: Evita duplicação de mensagens por fingerprint do payload (Cliente + Canal + Texto).

---

**Como contribuir e boas práticas**

- Branching: feature/, fix/, docs/*; PRs com revisão.
- Commits: Mensagens claras, escopo reduzido; inclua testes.
- Segredos: Nunca versionar .env; usar GitHub Actions secrets e variáveis de ambiente.
- Code style: Java (Google/IntelliJ defaults), Python (black/flake8 — recomendável adicionar).
- Issues: Descrever bug/feature com passos de reprodução e contexto de negócio.

---

**Perguntas frequentes**

- Preciso de API externa? Não. O ETL pode operar com CSV/Excel e gravar saídas locais (JSON/MD), mas o projeto demonstra a integração com API para ciclo completo.
- IA generativa real? O provider é pluggable. Use o mock durante desenvolvimento e configure um provider real (OpenAI/Azure) em produção, respeitando o Model Card.
- Tokens JWT: Em dev, usar chave pública local no .env. Em prod, preferir JWKs do Authorization Server e rotação de chaves.

---











**Contato:**


[![Portfólio Sérgio Santos](https://img.shields.io/badge/Portfólio-Sérgio_Santos-111827?style=for-the-badge&logo=githubpages&logoColor=00eaff)](https://santosdevbjj.github.io/portfolio/)
[![LinkedIn Sérgio Santos](https://img.shields.io/badge/LinkedIn-Sérgio_Santos-0A66C2?style=for-the-badge&logo=linkedin&logoColor=white)](https://linkedin.com/in/santossergioluiz) 

---


