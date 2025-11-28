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

# 🏦 Projeto genAIpipeETLPython: Pipeline ETL com IA Generativa para Marketing Personalizado

## 🎯 Visão Geral do Projeto

Este projeto demonstra a criação de um **Pipeline de ETL (Extração, Transformação e Carregamento)** robusto, utilizando **Python** (com Google Colab) e um **Microsserviço de Backend** em **Java/Spring Boot 4**.

O objetivo central é alavancar o poder da **Inteligência Artificial Generativa (GPT-4)** para transformar dados brutos de clientes em **mensagens de marketing altamente personalizadas**.

## 💻 Tecnologias e Ferramentas

| Categoria | Tecnologia | Versão/Padrão | Propósito |
| :--- | :--- | :--- | :--- |
| **Backend/API** | **Java** | 25 (LTS) | Linguagem de desenvolvimento principal do microsserviço. |
| **Framework** | **Spring Boot** | 4.x | Criação da API RESTful (CRUD) e gestão de microsserviços. |
| **Banco de Dados** | **PostgreSQL** | Mais recente | Persistência de dados (Clientes, Mensagens) com garantias **ACID**. |
| **Segurança** | **OAuth 2.0 + JWT** | Padrão | Autorização e Autenticação robusta para a API. |
| **Build Tool** | **Gradle** | Mais recente | Gerenciamento de dependências e build do projeto Java. |
| **Desenvolvimento** | **SOLID, Design Patterns, TDD/BDD/DDD** | Padrão | Melhores práticas de Engenharia de Software. |
| **Testes** | **JUnit 5, Mockito** | Padrão | Testes Unitários Fortes e Testes de Integração Sólidos. |
| **Data Science/ETL**| **Python** | 3.14 | Linguagem principal para o pipeline ETL. |
| **IA Generativa**| **OpenAI (GPT-4)** | API | Transformação de dados em mensagens de marketing personalizadas. |
| **Ambiente ETL** | **Google Colab** | Notebook | Ambiente de execução para o pipeline de Data Science. |

## 🛠️ Requisitos de Hardware e Software

### Requisitos de Software
* **JDK 25**
* **Python 3.14**
* **PostgreSQL** (Rodando localmente ou via Docker)
* **Conta OpenAI** (com chave de API configurada)
* **Git**

### Requisitos de Hardware
* Mínimo de 8GB de RAM.
* 5GB de espaço em disco livre.

## 📂 Estrutura de Pastas e Arquivos

O projeto é dividido em três módulos principais: a API Java, o Pipeline Python e a Documentação.

| Caminho | Tipo | Descrição |
| :--- | :--- | :--- |
| `api-java/` | Pasta | Contém todo o código-fonte e configuração do Microsserviço Spring Boot. |
| `api-java/src/main/java/com/santander/genai/model/` | Pasta | Classes **POJO/JPA Entities** (`Customer.java`, `MarketingMessage.java`). |
| `api-java/src/main/java/com/santander/genai/service/` | Pasta | Camada de **Negócio** (Lógica do CRUD, Aplicação do **SOLID**). |
| `api-java/src/test/java/.../service/` | Pasta | Testes Unitários de Serviço usando **JUnit** e **Mockito**. |
| `etl-python/` | Pasta | Contém todos os scripts e notebooks do pipeline de Data Science. |
| `etl-python/SantanderDevWeek2023.ipynb` | Arquivo | **Notebook principal** (Google Colab) que orquestra o pipeline ETL. |
| `etl-python/pipeline/extract.py` | Arquivo | Funções de **Extração** de IDs e detalhes de cliente via API. |
| `etl-python/pipeline/transform.py` | Arquivo | Funções de **Transformação** que chamam a API do GPT-4. |
| `etl-python/data/user_ids.csv` | Arquivo | Arquivo de entrada simulado para extração inicial de IDs de cliente. |
| `docs/` | Pasta | Contém a documentação profissional de Data Science. |
| `docs/project_scope.md` | Arquivo | Documento detalhando **Objetivos de Negócio** e **Métricas de Sucesso**. |
| `docs/model_card.md` | Arquivo | Documentação do modelo (GPT-4) conforme as melhores práticas de MLOps. |

## 🚀 Como Executar o Projeto

1.  **Clone o Repositório:** `git clone https://github.com/Santosdevbjj/genAIpipeETLPython.git`
2.  **Configurar o Banco de Dados:** Inicie o servidor PostgreSQL e atualize as credenciais no arquivo `api-java/src/main/resources/application.properties`.
3.  **Executar o Backend Java:** Navegue até `api-java/` e execute o build e a aplicação via Gradle: `./gradlew bootRun`.
4.  **Configurar a Chave OpenAI:** Abra o notebook `etl-python/SantanderDevWeek2023.ipynb` no Google Colab e insira sua chave de API nos campos de configuração.
5.  **Executar o Pipeline ETL:** Execute as células do notebook do Colab sequencialmente (Extração, Transformação, Carregamento).




---

**Contato:**


[![Portfólio Sérgio Santos](https://img.shields.io/badge/Portfólio-Sérgio_Santos-111827?style=for-the-badge&logo=githubpages&logoColor=00eaff)](https://santosdevbjj.github.io/portfolio/)
[![LinkedIn Sérgio Santos](https://img.shields.io/badge/LinkedIn-Sérgio_Santos-0A66C2?style=for-the-badge&logo=linkedin&logoColor=white)](https://linkedin.com/in/santossergioluiz) 

---


