import pandas as pd
from etl.extract import read_ids_from_sheet

def test_read_ids_from_csv(tmp_path):
    p = tmp_path / "ids.csv"
    pd.DataFrame({"id": [1, 2, 3]}).to_csv(p, index=False)
    ids = read_ids_from_sheet(str(p))
    assert ids == [1, 2, 3]
