from etl.transform import build_prompt, sanitize_output

def test_build_prompt_contains_nome():
    prompt = build_prompt({"nome": "Sergio", "segmento": "Varejo"})
    assert "Sergio" in prompt

def test_sanitize_output_limit():
    text = "a" * 1000
    cleaned = sanitize_output(text)
    assert len(cleaned) == 600
