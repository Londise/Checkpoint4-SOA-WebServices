alter table instrucoes
    add column cancelado tinyint not null default 0,
    add column motivo_cancelamento varchar(30),
    add column data_cancelamento datetime;
