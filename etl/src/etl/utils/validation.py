# etl/src/etl/utils/validation.py
def is_valid_channel(ch: str) -> bool:
    return ch in {"email", "sms", "app"}
