import sys
import pandas as pd
from io import StringIO

CHUNK_SZ = 50_000

def do_map(lines):
  df = pd.read_csv(
          StringIO(lines),
          header=None,
          engine='python',
          error_bad_lines=False,
          warn_bad_lines=False,
  )
  col = df.iloc[:,9]
  return col.notnull().sum(), col.mean(), col.var()

buff = []
for idx, line in enumerate(sys.stdin):
  if idx == 0:
      continue
  if idx % CHUNK_SZ == 0:
    result = do_map("\n".join(buff))
    if result:
      print("\t".join(str(x) for x in result))
    buff = []
  else:
    buff.append(line)
if buff:
  result = do_map("\n".join(buff))
  if result:
    print("\t".join(str(x) for x in result))