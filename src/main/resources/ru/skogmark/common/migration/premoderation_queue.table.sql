create table if not exists premoderation_queue (
    id bigserial primary key,
    channel_id integer not null,
    title varchar(128),
    text text,
    images text,
    created_dt timestamp with time zone not null default now(),
    changed_dt timestamp with time zone
);