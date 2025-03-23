CREATE TABLE callers
(
    id           bigserial                not null,
    uuid         uuid                     not null,
    created      timestamp with time zone not null default now(),
    updated      timestamp with time zone not null default now(),
    actual       boolean                  not null default true,
    phone_number varchar(255)             not null,

    constraint pk_callers_id primary key (id),
    constraint uq_callers_uuid unique (uuid)
);

create index idx_callers_actual on callers (actual);

comment on table callers is 'Справочник абонентов';
comment on column callers.id is 'Внутренний идентификатор';
comment on column callers.uuid is 'Внешний идентификатор';
comment on column callers.created is 'Дата и время создания';
comment on column callers.updated is 'Дата и время создания';
comment on column callers.actual is 'Признак актуальности';
comment on column callers.phone_number is 'Номер абонента';

