# etl/src/etl/models/customer.py
from pydantic import BaseModel, EmailStr

class Customer(BaseModel):
    id: int
    nome: str
    email: EmailStr
    segmento: str
    limiteCredito: int | None = 0
    score: int | None = 0
