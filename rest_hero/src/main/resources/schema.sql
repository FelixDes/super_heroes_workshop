create table hero
(
    id         bigint      not null
        primary key,
    level      integer     not null
        constraint hero_level_check
            check (level >= 1),
    name       varchar(50) not null,
    other_name varchar(255),
    picture    varchar(255),
    powers     varchar(255)
);