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

2. 

```sql
SELECT artist_mb, count(*) as qty
FROM artists
GROUP BY artist_mb
SORT BY qty DESC;
```