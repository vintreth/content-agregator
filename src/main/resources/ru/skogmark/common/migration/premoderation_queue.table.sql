create table if not exists premoderation_queue (
    id bigserial primary key not null,
    channel_id biginteger,
    text varchar(255) not null,
    images text,
    created_dt timestamp with timezone default now()
);