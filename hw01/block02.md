
См. флаги “-mkdir” и “-touchz“

1.[2 балла] Создайте папку в корневой HDFS-папке
```
root@fa57f86858de:/# hdfs dfs -ls /       
Found 1 items
drwxr-xr-x   - root supergroup          0 2022-10-08 12:10 /rmstate
root@fa57f86858de:/# hdfs dfs -mkdir /dir1
root@fa57f86858de:/# hdfs dfs -ls /
Found 2 items
drwxr-xr-x   - root supergroup          0 2022-10-08 12:44 /dir1
drwxr-xr-x   - root supergroup          0 2022-10-08 12:10 /rmstate
root@fa57f86858de:/#
```

2. [2 балла] Создайте в созданной папке новую вложенную папку.
```
root@fa57f86858de:/# hdfs dfs -ls /dir1
root@fa57f86858de:/# hdfs dfs -mkdir /dir1/dir2
root@fa57f86858de:/# hdfs dfs -ls /dir1
Found 1 items
drwxr-xr-x   - root supergroup          0 2022-10-08 12:47 /dir1/dir2
root@fa57f86858de:/#
```
3. [3 балла] Что такое Trash в распределенной FS? Как сделать так, чтобы файлы удалялись сразу, минуя “Trash”?

Trash это мусорная корзина. В нее перемещаются файлы, удаленные пользователем.
Файлы хранятся в течении периода восстановления, который задается в настройках name node - опция "Filesystem Trash Interval".

Чтобы удалить файл сразу без переноса в Trash можно команде удаления использовать флаг "skipTrash".
Чтобы отключить Trash вообще, можно установить период восстановления ноль.

4. [2 балла] Создайте пустой файл в подпапке из пункта 2.
```
root@fa57f86858de:/# hdfs dfs -ls /dir1/dir2
root@fa57f86858de:/# hdfs dfs -touchz /dir1/dir2/text.txt
root@fa57f86858de:/# hdfs dfs -ls /dir1/dir2
Found 1 items
-rw-r--r--   3 root supergroup          0 2022-10-08 12:56 /dir1/dir2/text.txt
root@fa57f86858de:/#
```
5. [2 балла] Удалите созданный файл.
```
root@fa57f86858de:/# hdfs dfs -ls /dir1/dir2/text.txt
-rw-r--r--   3 root supergroup          0 2022-10-08 12:56 /dir1/dir2/text.txt
root@fa57f86858de:/# hdfs dfs -rm /dir1/dir2/text.txt
Deleted /dir1/dir2/text.txt
root@fa57f86858de:/# hdfs dfs -ls /dir1/dir2/text.txt
ls: `/dir1/dir2/text.txt': No such file or directory
root@fa57f86858de:/#
```
6. [2 балла] Удалите созданные папки.
```
root@fa57f86858de:/# hdfs dfs -ls /dir1              
Found 1 items
drwxr-xr-x   - root supergroup          0 2022-10-08 12:57 /dir1/dir2
root@fa57f86858de:/# hdfs dfs -rmr /dir1
rmr: DEPRECATED: Please use '-rm -r' instead.
Deleted /dir1
root@fa57f86858de:/# hdfs dfs -ls /dir1
ls: `/dir1': No such file or directory
```

См. флаги “-put”, “-cat”, “-tail”, “-cp”

1. [3 балла] Скопируйте любой в новую папку на HDFS
```
root@fa57f86858de:/# cat > test.txt
test
root@fa57f86858de:/# hdfs dfs -put ./test.txt /
2022-10-08 13:02:27,825 INFO sasl.SaslDataTransferClient: SASL encryption trust check: localHostTrusted = false, remoteHostTrusted = false
root@fa57f86858de:/# hdfs dfs -cat /test.txt
2022-10-08 13:02:52,183 INFO sasl.SaslDataTransferClient: SASL encryption trust check: localHostTrusted = false, remoteHostTrusted = false
test
root@fa57f86858de:/#
```
2. [3 балла] Выведите содержимое HDFS-файла на экран.
```
root@fa57f86858de:/# hdfs dfs -cat /test.txt
2022-10-08 13:02:52,183 INFO sasl.SaslDataTransferClient: SASL encryption trust check: localHostTrusted = false, remoteHostTrusted = false
test
root@fa57f86858de:/#
```
3. [3 балла] Выведите содержимое нескольких последних строчек HDFS-файла на экран.
Подготовка:
```
root@fa57f86858de:/# cat > test.txt
line1
line2
line3
line4
line5
root@fa57f86858de:/# hdfs dfs -put -f ./test.txt /
2022-10-08 13:05:03,914 INFO sasl.SaslDataTransferClient: SASL encryption trust check: localHostTrusted = false, remoteHostTrusted = false
```

3.1  Вариант 1
```
root@fa57f86858de:/# hdfs dfs -tail  /test.txt
2022-10-08 13:07:26,081 INFO sasl.SaslDataTransferClient: SASL encryption trust check: localHostTrusted = false, remoteHostTrusted = false
line1
line2
line3
line4
line5
```
3.2 Вариант 2
```
root@fa57f86858de:/# hdfs dfs -cat /test.txt | tail -1
2022-10-08 13:08:15,086 INFO sasl.SaslDataTransferClient: SASL encryption trust check: localHostTrusted = false, remoteHostTrusted = false
line5
root@fa57f86858de:/#
```
4. [3 балла] Выведите содержимое нескольких первых строчек HDFS-файла на экран.
4.1 Вариант 1
```
root@fa57f86858de:/# hdfs dfs -head /test.txt
2022-10-08 13:09:40,282 INFO sasl.SaslDataTransferClient: SASL encryption trust check: localHostTrusted = false, remoteHostTrusted = false
line1
line2
line3
line4
line5
```
4.2  Вариант 2
```
root@fa57f86858de:/# hdfs dfs -cat /test.txt | head -1
2022-10-08 13:09:56,766 INFO sasl.SaslDataTransferClient: SASL encryption trust check: localHostTrusted = false, remoteHostTrusted = false
line1
root@fa57f86858de:/#
```

5. [3 балла] Переместите копию файла в HDFS на новую локацию.
```
root@fa57f86858de:/# mkdir newlocation
root@fa57f86858de:/# hdfs dfs -get /test.txt ./newlocation/
2022-10-08 13:15:54,759 INFO sasl.SaslDataTransferClient: SASL encryption trust check: localHostTrusted = false, remoteHostTrusted = false
root@fa57f86858de:/# cat newlocation/test.txt 
line1
line2
line3
line4
line5
root@fa57f86858de:/#
```

2. [4 баллов] Изменить replication factor для файла. Как долго занимает время на увеличение /
уменьшение числа реплик для файла?
```
root@fa57f86858de:/# 
root@fa57f86858de:/# hdfs dfs -setrep -w 1 /test.txt
Replication 1 set: /test.txt
Waiting for /test.txt ...
WARNING: the waiting time may be long for DECREASING the number of replications.
. done
root@fa57f86858de:/# hdfs dfs -setrep -w 2 /test.txt
Replication 2 set: /test.txt
Waiting for /test.txt .... done
root@fa57f86858de:/#
```
В моем случае изменение происходило replication factor примерно за 15 секунд.


3. [4 баллов] Найдите информацию по файлу, блокам и их расположениям с помощью “hdfs fsck”
```
root@fa57f86858de:/# hdfs fsck /test.txt -files -blocks -locations
Connecting to namenode via http://namenode:9870/fsck?ugi=root&files=1&blocks=1&locations=1&path=%2Ftest.txt
FSCK started by root (auth:SIMPLE) from /172.19.0.3 for path /test.txt at Sat Oct 08 13:27:25 UTC 2022
/test.txt 30 bytes, replicated: replication=2, 1 block(s):  OK
0. BP-1789072463-172.18.0.8-1665228523115:blk_1073741840_1016 len=30 Live_repl=2  [DatanodeInfoWithStorage[172.19.0.6:9866,DS-d2df9cbb-cbb9-4497-aaad-d16f933cf8c1,DISK], DatanodeInfoWithStorage[172.19.0.5:9866,DS-051f2b3b-acd2-4f3b-83c1-29180ec78536,DISK]]


Status: HEALTHY
 Number of data-nodes:  3
 Number of racks:               1
 Total dirs:                    0
 Total symlinks:                0

Replicated Blocks:
 Total size:    30 B
 Total files:   1
 Total blocks (validated):      1 (avg. block size 30 B)
 Minimally replicated blocks:   1 (100.0 %)
 Over-replicated blocks:        0 (0.0 %)
 Under-replicated blocks:       0 (0.0 %)
 Mis-replicated blocks:         0 (0.0 %)
 Default replication factor:    3
 Average block replication:     2.0
 Missing blocks:                0
 Corrupt blocks:                0
 Missing replicas:              0 (0.0 %)

Erasure Coded Block Groups:
 Total size:    0 B
 Total files:   0
 Total block groups (validated):        0
 Minimally erasure-coded block groups:  0
 Over-erasure-coded block groups:       0
 Under-erasure-coded block groups:      0
 Unsatisfactory placement block groups: 0
 Average block group size:      0.0
 Missing block groups:          0
 Corrupt block groups:          0
 Missing internal blocks:       0
FSCK ended at Sat Oct 08 13:27:25 UTC 2022 in 2 milliseconds
```

4. [4 баллов] Получите информацию по любому блоку из п.2 с помощью "hdfs fsck -blockId”.
Обратите внимание на Generation Stamp (GS number).
```
root@fa57f86858de:/# hdfs fsck -blockId blk_1073741840
Connecting to namenode via http://namenode:9870/fsck?ugi=root&blockId=blk_1073741840+&path=%2F
FSCK started by root (auth:SIMPLE) from /172.19.0.3 at Sat Oct 08 13:40:39 UTC 2022

Block Id: blk_1073741840
Block belongs to: /test.txt
No. of Expected Replica: 2
No. of live Replica: 2
No. of excess Replica: 0
No. of stale Replica: 0
No. of decommissioned Replica: 0
No. of decommissioning Replica: 0
No. of corrupted Replica: 0
Block replica on datanode/rack: 4422d70d7f8b/default-rack is HEALTHY
Block replica on datanode/rack: 9a5ea2d4bf9d/default-rack is HEALTHY
root@fa57f86858de:/#
```
