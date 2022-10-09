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