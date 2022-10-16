1. Загружаем csv-файл в Hive
```sql
LOAD DATA INPATH '/user/fatuus/artists.csv' 
INTO TABLE artists;

CREATE EXTERNAL TABLE artists 
(`col1` string, `col2` string)
(
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
ROW FORMAT DELIMITED FIELDS TERMINATED BY "\u003B" STORED AS TEXTFILE
LOCATION "/user/fatuus/artists.csv" 
TBLPROPERTIES("skip.header.line.count"="1");
```

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
 FIELDS TERMINATED BY ',';
```

```sql
LOAD DATA INPATH '/user/fatuus/artists.csv' INTO TABLE artists;
```