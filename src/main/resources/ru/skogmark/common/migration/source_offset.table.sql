create table if not exists source_offset (
    source_id integer primary key,
    offset_value bigint not null
);
