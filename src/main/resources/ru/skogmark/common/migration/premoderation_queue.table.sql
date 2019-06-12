create table if not exists premoderation_queue (
    id bigserial primary key,
    channel_id integer not null,
    title varchar(128),
    text varchar(255),
    images text,
    created_dt timestamp with timezone not null default now(),
    changed_dt timestamp with timezone
);