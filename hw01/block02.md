
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