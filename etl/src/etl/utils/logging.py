# etl/src/etl/utils/logging.py
from loguru import logger

logger.add("etl.log", rotation="500 KB", level="INFO", enqueue=True, backtrace=False, diagnose=False)
