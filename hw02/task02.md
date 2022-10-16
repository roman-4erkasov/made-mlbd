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

# 2. (5 балов) Найти исполнителя с максимальным числом скробблов
**Задание:**
Используя Hive найти исполнителя с максимальным числом скробблов (команды и результаты записать в файл и добавить в репозиторий).

```sql
SELECT tbl1.artist_mb, tbl1.scrobbles_lastfm FROM artists tbl1
JOIN (select max(scrobbles_lastfm) col  FROM artists) tbl2
on tbl1.scrobbles_lastfm=tbl2.col;
```
Вывод "task02_query01.csv":
```
tbl1.artist_mb,tbl1.scrobbles_lastfm
The Beatles,517126254
```

# 3. (10 баллов) Самый популярный тэг на ластфм

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

Вывод "task02_query02.csv":
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
Видим что на первом месте пустая строка, ее пропускаем. Тогда самый популярный тэг это "seen alive".

# 4. (10 баллов) Самые популярные исполнители 10 самых популярных тегов 

Используя Hive найти самых популярных исполнителей 10 самых популярных тегов ластфм (команды и результаты записать в файл и добавить в репозиторий).

Возьмем 8 самых популярных тега из прошлого запроса.
```sql
SELECT artist_lastfm, listeners_lastfm
FROM artists
WHERE (
  instr(tags_lastfm, 'seen live')>0 
  or instr(tags_lastfm, 'rock')>0
  or instr(tags_lastfm, 'electronic')>0
  or instr(tags_lastfm, 'All')>0
  or instr(tags_lastfm, 'under 2000 listeners')>0
  or instr(tags_lastfm, 'alternative')>0
  or instr(tags_lastfm, 'pop')>0
  or instr(tags_lastfm, 'indie')>0
)
SORT BY listeners_lastfm DESC
limit 15;
```

Вывод "task02_query03.csv":
```
artist_lastfm,listeners_lastfm
Coldplay,5381567
Radiohead,4732528
Red Hot Chili Peppers,4620835
Rihanna,4558193
Eminem,4517997
The Killers,4428868
Kanye West,4390502
Nirvana,4272894
Muse,4089612
Queen,4023379
Foo Fighters,4005236
Linkin Park,3978390
Lady Gaga,3820581
The Rolling Stones,3798330
Daft Punk,3782404

```

4. Любой другой инсайт на ваше усмотрение - 10 баллов

Самые популярные российские исполнители:
```sql
SELECT artist_lastfm, country_lastfm, listeners_lastfm
FROM artists 
WHERE country_lastfm = 'Russia'
SORT BY listeners_lastfm DESC
```

Вывод "task02_query04.csv": 
```
artist_lastfm,country_lastfm,listeners_lastfm
Pyotr Ilyich Tchaikovsky,Russia,990022
Pyotr Ilyich Tchaikovsky,Russia,990022
t.A.T.u.,Russia,926338
t.A.T.u.,Russia,926338
Sergei Rachmaninoff,Russia,642317
Sergei Rachmaninoff,Russia,642317
Sergei Prokofiev,Russia,492185
Sergei Prokofiev,Russia,492185
Dmitri Shostakovich,Russia,489443
Dmitri Shostakovich,Russia,489443
Igor Stravinsky,Russia,412364
Igor Stravinsky,Russia,412364
YG,Russia,403408
YG,Russia,403408
Сплин,Russia,377948
```
