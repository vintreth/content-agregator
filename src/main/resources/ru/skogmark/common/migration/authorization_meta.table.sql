create table if not exists authorization_meta (
    source_id integer primary key,
    authorization_token varchar(256) not null
);
