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

delete from `rococo-auth`.authority;
delete from `rococo-auth`.user;

insert into `rococo-auth`.user (id, username, password, enabled, account_non_expired, account_non_locked, credentials_non_expired) values (0x1ACEC3FD2569434188929AAE56653EA7, 'dima', '{bcrypt}$2a$10$I0OfayQFt7JH2L36QrClae.GBmqb3Vkg6ygNK/F98LG4/GteWxnAe', 1, 1, 1, 1);
insert into `rococo-auth`.user (id, username, password, enabled, account_non_expired, account_non_locked, credentials_non_expired) values (0x845EB95B4C384ADEAFC7209D4B2ADED2, 'valentin', '{bcrypt}$2a$10$EkKgBosHKp90/Fd4b1qbPOIqRdO3BenUqEuIfMqsHS62RuyqWWZIe', 1, 1, 1, 1);
insert into `rococo-auth`.user (id, username, password, enabled, account_non_expired, account_non_locked, credentials_non_expired) values (0x854400E56046431494E8300A1B380D76, 'ivan', '{bcrypt}$2a$10$K0i2MKCT5vFbxtqvVuXmd.JygXkDKvMqF/p7qiNrFq3R5kDi7b.7a', 1, 1, 1, 1);
insert into `rococo-auth`.user (id, username, password, enabled, account_non_expired, account_non_locked, credentials_non_expired) values (0xCA6812F37CDB45779FE189E272A79FE5, 'petr', '{bcrypt}$2a$10$o5xd.nRIJEtRpZW41AMQ1.a9SHypbwq2SUDmGSGQusRC3PN27G1lS', 1, 1, 1, 1);

insert into `rococo-auth`.authority (id, user_id, authority) values (0x0BA383FE777F4B9ABF413E0D0BD2B33F, 0x845EB95B4C384ADEAFC7209D4B2ADED2, 'read');
insert into `rococo-auth`.authority (id, user_id, authority) values (0x1B686C1A2FC14C3082DED2C36127374D, 0x1ACEC3FD2569434188929AAE56653EA7, 'read');
insert into `rococo-auth`.authority (id, user_id, authority) values (0x1D5722E71FDA4FB090FD0990B7A61080, 0xCA6812F37CDB45779FE189E272A79FE5, 'read');
insert into `rococo-auth`.authority (id, user_id, authority) values (0x29AD851356A146EEAA004A48031B5865, 0xCA6812F37CDB45779FE189E272A79FE5, 'write');
insert into `rococo-auth`.authority (id, user_id, authority) values (0x62DAD0E631494AC489CDF8023ADDBC32, 0x854400E56046431494E8300A1B380D76, 'read');
insert into `rococo-auth`.authority (id, user_id, authority) values (0x80F94AE271B74B11AA01379259020FCC, 0x845EB95B4C384ADEAFC7209D4B2ADED2, 'write');
insert into `rococo-auth`.authority (id, user_id, authority) values (0xBF76BB04502642698F419E8A29C2154B, 0x1ACEC3FD2569434188929AAE56653EA7, 'write');
insert into `rococo-auth`.authority (id, user_id, authority) values (0xE005BF5028B943B8BBE4539DE95A20C9, 0x854400E56046431494E8300A1B380D76, 'write');
