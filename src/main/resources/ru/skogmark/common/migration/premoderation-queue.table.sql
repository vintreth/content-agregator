create table if not exists premoderation_queue(
    id bigserial primary key,
    text text,
    image_id bigint,
    created_dt timestamp with timezone default now()
);