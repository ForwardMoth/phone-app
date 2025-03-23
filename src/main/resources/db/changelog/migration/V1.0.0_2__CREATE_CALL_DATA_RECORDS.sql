CREATE TABLE call_data
(
    id                  bigserial                   not null,
    created             timestamp with time zone    not null default now(),
    updated             timestamp with time zone    not null default now(),
    uuid                uuid                        not null,
    incoming_caller_id  bigint                      not null,
    outcoming_caller_id bigint                      not null,
    start_call_time     timestamp without time zone not null,
    finish_call_time    timestamp without time zone not null,

    constraint pk_call_data_id primary key (id),
    constraint uq_call_data_uuid unique (uuid),
    constraint fk_call_data_incoming_caller_id foreign key (incoming_caller_id) references callers (id),
    constraint fk_call_data_outcoming_caller_id foreign key (outcoming_caller_id) references callers (id)
);

comment on table call_data is 'Действия, совершенные абонентом за тарифицируемый период';
comment on column call_data.id is 'Внутренний идентификатор';
comment on column call_data.created is 'Дата и время создания';
comment on column call_data.updated is 'Дата и время создания';
comment on column call_data.uuid is 'Внешний идентификатор';
comment on column call_data.incoming_caller_id is 'Идентификатор входящего абонента';
comment on column call_data.outcoming_caller_id is 'Идентификатор исходящего абонента';
comment on column call_data.start_call_time is 'Дата и время начала звонка';
comment on column call_data.finish_call_time is 'Дата и время окончания звонка';

