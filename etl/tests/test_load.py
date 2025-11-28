import httpx
import pytest
from etl.load import post_message

def test_post_message_raises_on_error(httpx_mock):
    httpx_mock.add_response(method="POST", url="http://x/api/mensagens", status_code=400)
    with pytest.raises(httpx.HTTPStatusError):
        post_message("http://x/api", 1, "t", "app", "jwt")

def test_post_message_ok(httpx_mock):
    httpx_mock.add_response(method="POST", url="http://x/api/mensagens", json={"id": 10}, status_code=201)
    r = post_message("http://x/api", 1, "t", "app", "jwt")
    assert r["id"] == 10
