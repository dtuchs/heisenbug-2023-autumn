create table if not exists `user`
(
    id        binary(16)   unique not null default (UUID_TO_BIN(UUID(), true)),
    username  varchar(50)  unique not null,
    firstname varchar(255),
    lastname  varchar(255),
    avatar    longblob,
    primary key (id)
);

create table if not exists `country`
(
    id        binary(16)    unique not null default (UUID_TO_BIN(UUID(), true)),
    name      varchar(255)  unique not null,
    primary key (id)
);

create table if not exists `museum`
(
    id          binary(16)    unique not null default (UUID_TO_BIN(UUID(), true)),
    title       varchar(255)  unique not null,
    description varchar(1000),
    city        varchar(255),
    photo       longblob,
    country_id  binary(16)     not null,
    primary key (id),
    constraint fk_country_id foreign key (country_id) references `country` (id)
);

create table if not exists `artist`
(
    id        binary(16)    unique not null default (UUID_TO_BIN(UUID(), true)),
    name      varchar(255)  unique not null,
    biography varchar(2000) not null,
    photo     longblob,
    primary key (id)
);

create table if not exists `painting`
(
    id          binary(16)      unique not null default (UUID_TO_BIN(UUID(), true)),
    title       varchar(255)    not null,
    description varchar(1000),
    artist_id   binary(16)     not null,
    museum_id   binary(16),
    content     longblob,
    primary key (id),
    constraint fk_artist_id foreign key (artist_id) references `artist` (id),
    constraint fk_museum_id foreign key (museum_id) references `museum` (id)
);

insert into `country` (name) value ('Австралия');
insert into `country` (name) value ('Австрия');
insert into `country` (name) value ('Азербайджан');
insert into `country` (name) value ('Албания');
insert into `country` (name) value ('Алжир');
insert into `country` (name) value ('Ангола');
insert into `country` (name) value ('Андорра');
insert into `country` (name) value ('Антигуа и Барбуда');
insert into `country` (name) value ('Аргентина');
insert into `country` (name) value ('Армения');
insert into `country` (name) value ('Афганистан');
insert into `country` (name) value ('Багамские Острова');
insert into `country` (name) value ('Бангладеш');
insert into `country` (name) value ('Барбадос');
insert into `country` (name) value ('Бахрейн');
insert into `country` (name) value ('Белиз');
insert into `country` (name) value ('Белоруссия');
insert into `country` (name) value ('Бельгия');
insert into `country` (name) value ('Бенин');
insert into `country` (name) value ('Болгария');
insert into `country` (name) value ('Боливия');
insert into `country` (name) value ('Босния и Герцеговина');
insert into `country` (name) value ('Ботсвана');
insert into `country` (name) value ('Бразилия');
insert into `country` (name) value ('Бруней');
insert into `country` (name) value ('Буркина-Фасо');
insert into `country` (name) value ('Бурунди');
insert into `country` (name) value ('Бутан');
insert into `country` (name) value ('Вануату');
insert into `country` (name) value ('Великобритания');
insert into `country` (name) value ('Венгрия');
insert into `country` (name) value ('Венесуэла');
insert into `country` (name) value ('Восточный Тимор');
insert into `country` (name) value ('Вьетнам');
insert into `country` (name) value ('Габон');
insert into `country` (name) value ('Республика Гаити');
insert into `country` (name) value ('Гайана');
insert into `country` (name) value ('Гамбия');
insert into `country` (name) value ('Гана');
insert into `country` (name) value ('Гватемала');
insert into `country` (name) value ('Гвинея');
insert into `country` (name) value ('Гвинея-Бисау');
insert into `country` (name) value ('Германия');
insert into `country` (name) value ('Гондурас');
insert into `country` (name) value ('Гренада');
insert into `country` (name) value ('Греция');
insert into `country` (name) value ('Грузия');
insert into `country` (name) value ('Дания');
insert into `country` (name) value ('Джибути');
insert into `country` (name) value ('Доминика');
insert into `country` (name) value ('Доминиканская Республика');
insert into `country` (name) value ('Египет');
insert into `country` (name) value ('Замбия');
insert into `country` (name) value ('Зимбабве');
insert into `country` (name) value ('Израиль');
insert into `country` (name) value ('Индия');
insert into `country` (name) value ('Индонезия');
insert into `country` (name) value ('Иордания');
insert into `country` (name) value ('Ирак');
insert into `country` (name) value ('Иран');
insert into `country` (name) value ('Ирландия');
insert into `country` (name) value ('Исландия');
insert into `country` (name) value ('Испания');
insert into `country` (name) value ('Италия');
insert into `country` (name) value ('Йемен');
insert into `country` (name) value ('Кабо-Верде');
insert into `country` (name) value ('Казахстан');
insert into `country` (name) value ('Камбоджа');
insert into `country` (name) value ('Камерун');
insert into `country` (name) value ('Канада');
insert into `country` (name) value ('Катар');
insert into `country` (name) value ('Кения');
insert into `country` (name) value ('Республика Кипр');
insert into `country` (name) value ('Киргизия');
insert into `country` (name) value ('Кирибати');
insert into `country` (name) value ('Китай');
insert into `country` (name) value ('Колумбия');
insert into `country` (name) value ('Коморы');
insert into `country` (name) value ('Республика Конго');
insert into `country` (name) value ('Демократическая Республика Конго');
insert into `country` (name) value ('Корейская Народно-Демократическая Республика');
insert into `country` (name) value ('Республика Корея');
insert into `country` (name) value ('Коста-Рика');
insert into `country` (name) value ('Кот-д’Ивуар');
insert into `country` (name) value ('Куба');
insert into `country` (name) value ('Кувейт');
insert into `country` (name) value ('Лаос');
insert into `country` (name) value ('Латвия');
insert into `country` (name) value ('Лесото');
insert into `country` (name) value ('Либерия');
insert into `country` (name) value ('Ливан');
insert into `country` (name) value ('Ливия');
insert into `country` (name) value ('Литва');
insert into `country` (name) value ('Лихтенштейн');
insert into `country` (name) value ('Люксембург');
insert into `country` (name) value ('Маврикий');
insert into `country` (name) value ('Мавритания');
insert into `country` (name) value ('Мадагаскар');
insert into `country` (name) value ('Малави');
insert into `country` (name) value ('Малайзия');
insert into `country` (name) value ('Мали');
insert into `country` (name) value ('Мальдивы');
insert into `country` (name) value ('Мальта');
insert into `country` (name) value ('Марокко');
insert into `country` (name) value ('Маршалловы Острова');
insert into `country` (name) value ('Мексика');
insert into `country` (name) value ('Федеративные Штаты Микронезии');
insert into `country` (name) value ('Мозамбик');
insert into `country` (name) value ('Молдавия');
insert into `country` (name) value ('Монако');
insert into `country` (name) value ('Монголия');
insert into `country` (name) value ('Мьянма');
insert into `country` (name) value ('Намибия');
insert into `country` (name) value ('Науру');
insert into `country` (name) value ('Непал');
insert into `country` (name) value ('Нигер');
insert into `country` (name) value ('Нигерия');
insert into `country` (name) value ('Нидерланды');
insert into `country` (name) value ('Никарагуа');
insert into `country` (name) value ('Новая Зеландия');
insert into `country` (name) value ('Норвегия');
insert into `country` (name) value ('Объединённые Арабские Эмираты');
insert into `country` (name) value ('Оман');
insert into `country` (name) value ('Пакистан');
insert into `country` (name) value ('Палау');
insert into `country` (name) value ('Панама');
insert into `country` (name) value ('Папуа — Новая Гвинея');
insert into `country` (name) value ('Парагвай');
insert into `country` (name) value ('Перу');
insert into `country` (name) value ('Польша');
insert into `country` (name) value ('Португалия');
insert into `country` (name) value ('Россия');
insert into `country` (name) value ('Руанда');
insert into `country` (name) value ('Румыния');
insert into `country` (name) value ('Сальвадор');
insert into `country` (name) value ('Самоа');
insert into `country` (name) value ('Сан-Марино');
insert into `country` (name) value ('Сан-Томе и Принсипи');
insert into `country` (name) value ('Саудовская Аравия');
insert into `country` (name) value ('Флаг Северной Македонии');
insert into `country` (name) value ('Сейшельские Острова');
insert into `country` (name) value ('Сенегал');
insert into `country` (name) value ('Сент-Винсент и Гренадины');
insert into `country` (name) value ('Сент-Китс и Невис');
insert into `country` (name) value ('Сент-Люсия');
insert into `country` (name) value ('Сербия');
insert into `country` (name) value ('Сингапур');
insert into `country` (name) value ('Сирия');
insert into `country` (name) value ('Словакия');
insert into `country` (name) value ('Словения');
insert into `country` (name) value ('Соединённые Штаты Америки');
insert into `country` (name) value ('Соломоновы Острова');
insert into `country` (name) value ('Сомали');
insert into `country` (name) value ('Судан');
insert into `country` (name) value ('Суринам');
insert into `country` (name) value ('Сьерра-Леоне');
insert into `country` (name) value ('Таджикистан');
insert into `country` (name) value ('Таиланд');
insert into `country` (name) value ('Танзания');
insert into `country` (name) value ('Того');
insert into `country` (name) value ('Тонга');
insert into `country` (name) value ('Тринидад и Тобаго');
insert into `country` (name) value ('Тувалу');
insert into `country` (name) value ('Тунис');
insert into `country` (name) value ('Туркменистан');
insert into `country` (name) value ('Турция');
insert into `country` (name) value ('Уганда');
insert into `country` (name) value ('Узбекистан');
insert into `country` (name) value ('Украина');
insert into `country` (name) value ('Уругвай');
insert into `country` (name) value ('Фиджи');
insert into `country` (name) value ('Филиппины');
insert into `country` (name) value ('Финляндия');
insert into `country` (name) value ('Франция');
insert into `country` (name) value ('Хорватия');
insert into `country` (name) value ('Центральноафриканская Республика');
insert into `country` (name) value ('Чад');
insert into `country` (name) value ('Черногория');
insert into `country` (name) value ('Чехия');
insert into `country` (name) value ('Чили');
insert into `country` (name) value ('Швейцария');
insert into `country` (name) value ('Швеция');
insert into `country` (name) value ('Флаг Шри-Ланки');
insert into `country` (name) value ('Эквадор');
insert into `country` (name) value ('Экваториальная Гвинея');
insert into `country` (name) value ('Эритрея');
insert into `country` (name) value ('Эсватини');
insert into `country` (name) value ('Эстония');
insert into `country` (name) value ('Эфиопия');
insert into `country` (name) value ('Южно-Африканская Республика');
insert into `country` (name) value ('Южный Судан');
insert into `country` (name) value ('Ямайка');
insert into `country` (name) value ('Япония');
insert into `country` (name) value ('Ватикан');
insert into `country` (name) value ('Палестина');