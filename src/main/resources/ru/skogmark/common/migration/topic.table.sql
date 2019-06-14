create table if not exists topic (
    id bigserial primary key,
    channel_id integer not null,
    title varchar(128),
    text text,
    images text,
    published_dt timestamp with time zone not null default now(),
    active smallint not null
);