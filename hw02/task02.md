# 1. (15 балов) Сделать таблицу artists
**Задание:**
Сделать таблицу artists в Hive и вставить туда значения, используя датасет
https://www.kaggle.com/pieca111/music-artists-popularity - 15 баллов

**Решение:**
Создаем таблицу:
```sql
CREATE TABLE IF NOT EXISTS artists (
    `mbid` string,
    `artist_mb` string,
    `artist_lastfm` string,
    `country_mb` string,
    `country_lastfm` string,
    `tags_mb` string,
    `tags_lastfm` string,
    `listeners_lastfm` int,
    `scrobbles_lastfm` int,
    `ambiguous_artist` string
 )
 ROW FORMAT DELIMITED
 FIELDS TERMINATED BY ','
 TBLPROPERTIES("skip.header.line.count"="1");
```

Загружаем данные:
```sql
LOAD DATA INPATH '/user/fatuus/artists.csv' INTO TABLE artists;
```


Несмотря на то что при создании таблицы я указал что нужно игнорировать первую строку, так как это хэдер. В таблице все равно был хедер как первая строка. Поэтому пришлось повторно указать что нужно игнорировать первую строку (хэдер файла). В этот раз помогло.
```sql
ALTER TABLE tablename SET TBLPROPERTIES ("skip.header.line.count"="1");
```

2. (5 балов) Найти исполнителя с максимальным числом скробблов
**Задание:**
Используя Hive найти исполнителя с максимальным числом скробблов (команды и результаты записать в файл и добавить в репозиторий).

```sql
SELECT tbl1.artist_mb, tbl1.scrobbles_lastfm FROM artists tbl1
JOIN (select max(scrobbles_lastfm) col  FROM artists) tbl2
on tbl1.scrobbles_lastfm=tbl2.col;
```

```
tbl1.artist_mb,tbl1.scrobbles_lastfm
The Beatles,517126254
```

3. (10 баллов) Самый популярный тэг на ластфм

**Задание:**
Используя Hive найти Самый популярный тэг на ластфм (команды и результаты записать в файл и добавить в репозиторий).

**Решение:**
Для самопроверки посмотрим побольше тэгов - 15, самый первый и будет ответом на вопрос
```sql
SELECT tag_lastfm, count(*) as qty
FROM artists lateral view explode(split(tags_lastfm,';')) tags_lastfm AS tag_lastfm 
group BY tag_lastfm
SORT BY qty DESC
LIMIT 15;
```
Видим что на первом месте пустая строка, ее пропускаем. Тогда самый популярный тэг это "seen alive":
```
tag_lastfm,qty
,1083950
 seen live,81278
 rock,64902
 electronic,58163
 All,48631
 under 2000 listeners,48301
 alternative,42067
 pop,41557
 indie,39333
 experimental,37665
 female vocalists,33097
 metal,32334
 punk,28441
 electronica,28127
 american,27318
```

```sql
SELECT artist_mb, count(*) as qty
FROM artists
GROUP BY artist_mb
SORT BY qty DESC;
```

```sql
SELECT left_tbl.artist_mb, left_tbl.scrobbles_lastfm
FROM artists left_tbl
JOIN (SELECT MAX(scrobbles_lastfm) FROM artists) right_tbl
ON left_tbl.artist_mb==right_tbl.artist_mb
WHERE left_tbl.scrobbles_lastfm=right_tbl.scrobbles_lastfm;
```