# Dicionário de Dados

## Cliente
- **id:** inteiro, PK.
- **nome:** string (<=120), obrigatório.
- **email:** string, RFC 5322, obrigatório.
- **segmento:** enum {Varejo, Select, Private}, obrigatório.
- **limite_credito:** inteiro >=0.
- **score:** inteiro [0, 1000].

## MensagemMarketing
- **id:** inteiro, PK.
- **cliente_id:** FK -> clientes.id, obrigatório.
- **texto:** texto (<=1000), obrigatório.
- **canal:** enum {email, sms, app}, obrigatório.
- **modelo_versao:** string.
- **created_at:** timestamp, gerado pelo sistema.
