-- api/src/main/resources/db/migration/V1__init.sql

CREATE TABLE IF NOT EXISTS clientes (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(120) NOT NULL,
    email VARCHAR(160) NOT NULL,
    segmento VARCHAR(40) NOT NULL,
    limite_credito INT CHECK (limite_credito >= 0),
    score INT CHECK (score >= 0 AND score <= 1000)
);

CREATE TABLE IF NOT EXISTS mensagens_marketing (
    id SERIAL PRIMARY KEY,
    cliente_id BIGINT NOT NULL REFERENCES clientes(id) ON DELETE CASCADE,
    texto TEXT NOT NULL,
    canal VARCHAR(20) NOT NULL,
    modelo_versao VARCHAR(50),
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE INDEX IF NOT EXISTS idx_mensagens_cliente ON mensagens_marketing (cliente_id);
