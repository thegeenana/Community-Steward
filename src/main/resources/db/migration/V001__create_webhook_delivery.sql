create table webhook_delivery (
    delivery_id varchar(64) primary key,
    event_name varchar(64) not null,
    action varchar(64),
    installation_id bigint,
    repository_id bigint,
    received_at timestamptz not null,
    processing_state varchar(32) not null default 'RECEIVED',
    processed_at timestamptz,
    failure_reason varchar(500)
);
create index webhook_delivery_repository_received_idx
    on webhook_delivery (repository_id, received_at desc);
