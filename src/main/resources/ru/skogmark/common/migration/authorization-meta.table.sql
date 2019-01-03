create table if not exists authorization_meta (
    source_id integer not null,
    authorization_token varchar(256) not null
);

create unique index if not exists idx_authorization_meta_source_id on authorization_meta(source_id);