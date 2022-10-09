1. Среднее и дисперсия обычными споспобом:
Код:
```py
import pandas as pd
df = pd.read_csv("~/datasets/AB_NYC_2019.csv")
print(f"n={df.price.notnull().sum()} mean={df.price.mean():.2f} var={df.price.var():.2f}")
```

Вывод:
```
n=48895 mean=152.72 var=57674.03
```

2. mapper и reducer для среднего
```py
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
```

```py
import sys
  
n_buff = None
avg_buff = None
var_buff =  None

for line in sys.stdin:
  n_str, avg_str, variance_str = line.split("\t")
  n = int(n_str)
  avg = float(avg_str)
  variance = float(variance_str)
  if n_buff is None:
    n_buff = n
    avg_buff = avg
    var_buff = variance
  else:
    var_buff = (n_buff * var_buff + n * variance) / (n_buff + n) + n * n_buff * ((avg_buff - avg) / (n_buff + n)) ** 2
    avg_buff = (n_buff * avg_buff + n * avg) / (n_buff + n)
    n_buff += n
print(f"n={n_buff} mean={avg_buff:.2f}")
```

Вывод:
```
n=48895 mean=152.72 
```

2. mapper и reducer для дисперсии
```py
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
```

```py
import sys
  
n_buff = None
avg_buff = None
var_buff =  None

for line in sys.stdin:
  n_str, avg_str, variance_str = line.split("\t")
  n = int(n_str)
  avg = float(avg_str)
  variance = float(variance_str)
  if n_buff is None:
    n_buff = n
    avg_buff = avg
    var_buff = variance
  else:
    var_buff = (n_buff * var_buff + n * variance) / (n_buff + n) + n * n_buff * ((avg_buff - avg) / (n_buff + n)) ** 2
    avg_buff = (n_buff * avg_buff + n * avg) / (n_buff + n)
    n_buff += n
print(f"n={n_buff} variance={var_buff:.2f}")
```

Вывод:
```
n=48895 var=57674.03
```