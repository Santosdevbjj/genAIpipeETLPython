# etl/src/etl/utils/io.py
import os

def read_file(path: str) -> str:
    with open(path, "r", encoding="utf-8") as f:
        return f.read()

def ensure_dir(path: str):
    os.makedirs(path, exist_ok=True)
