# Arquitetura

- **API Java (Spring Boot):** camadas Controller → Service → Repository; segurança OAuth2/JWT; JPA + Flyway.
- **Banco:** PostgreSQL (ACID) com migrações versionadas.
- **ETL Python:** E (planilha/CSV/API), T (prompt + IA generativa + sanitização), L (POST na API).
- **Infra:** Docker Compose, ambientes via `.env`.
- **Testes:** JUnit/Mockito/Testcontainers (Java) e Pytest (Python).
- **Docs:** Sphinx + Markdown; publicação opcional em GitHub Pages.
