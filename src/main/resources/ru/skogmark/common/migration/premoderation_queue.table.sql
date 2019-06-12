create table if not exists premoderation_queue (
    id bigserial primary key not null,
    channel_id biginteger not null,
    title varchar(128),
    text varchar(255),
    images text,
    created_dt timestamp with timezone default now(),
    changed_dt timestamp with timezone
);