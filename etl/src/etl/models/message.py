# etl/src/etl/models/message.py
from pydantic import BaseModel

class Message(BaseModel):
    clienteId: int
    texto: str
    canal: str
    modeloVersao: str = "genai-v1"
