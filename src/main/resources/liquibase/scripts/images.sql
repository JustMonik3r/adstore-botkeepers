 create table image(
 id integer generated by default as identity primary key,
 size bigint,
 media_type text,
 data bytea
 );