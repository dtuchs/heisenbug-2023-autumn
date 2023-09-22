create table if not exists `user`
(
    id                      binary(16)  unique not null default (UUID_TO_BIN(UUID(), true)),
    username                varchar(50) unique not null,
    password                varchar(255)       not null,
    enabled                 boolean            not null,
    account_non_expired     boolean            not null,
    account_non_locked      boolean            not null,
    credentials_non_expired boolean            not null,
    primary key (id, username)
);

create table if not exists `authority`
(
    id        binary(16)  unique not null default (UUID_TO_BIN(UUID(), true)),
    user_id   binary(16)         not null,
    authority varchar(50)        not null,
    primary key (id),
    constraint fk_authorities_users foreign key (user_id) references `user` (id)
);
