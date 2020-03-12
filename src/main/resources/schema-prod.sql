create sequence hibernate_sequence start 1 increment 1;
create table discipline
(
    id              int8 not null,
    discipline_type int4 not null,
    field_type      int4 not null,
    division_name   varchar(255),
    primary key (id)
);
create table division
(
    name varchar(255) not null,
    primary key (name)
);
create table manager
(
    id int8 not null,
    primary key (id)
);
create table player
(
    id                    int8        not null,
    club                  varchar(50) not null,
    first_name            varchar(50) not null,
    gender                int4        not null,
    last_name             varchar(50) not null,
    created_by_manager_id int8,
    primary key (id)
);
create table registration
(
    id                       int8 not null,
    registration_date        date not null,
    state                    int4 not null,
    created_by_manager_id    int8,
    partner_id               int8,
    player_id                int8,
    tournament_discipline_id int8,
    primary key (id)
);
create table tournament
(
    id               int8        not null,
    close_of_entries date        not null,
    "end"            date        not null,
    name             varchar(50) not null,
    start            date        not null,
    primary key (id)
);
create table tournament_discipline
(
    id               int8   not null,
    capacity         int4   not null check (capacity >= 1),
    registration_fee float8 not null,
    discipline_id    int8,
    tournament_id    int8,
    primary key (id)
);
alter table if exists player
    add constraint UKtlcjj3n40s0ajueu8owsyscad unique (first_name, last_name, gender, club);
alter table if exists discipline
    add constraint FKtq6u76woehchd6sntyacwaf9q foreign key (division_name) references division;
alter table if exists player
    add constraint FKhybqiqeeek1f4cngt1bar664q foreign key (created_by_manager_id) references manager;
alter table if exists registration
    add constraint FKcde7355animc6dqsqwjt1doyi foreign key (created_by_manager_id) references manager;
alter table if exists registration
    add constraint FKhmyyu2ljiwo8x10kjfab1pkru foreign key (partner_id) references player;
alter table if exists registration
    add constraint FKswk5vywwvd2r4knle35xjygao foreign key (player_id) references player;
alter table if exists registration
    add constraint FKif3c0waonn41d3n17ci6j7erg foreign key (tournament_discipline_id) references tournament_discipline;
alter table if exists tournament_discipline
    add constraint FKmd3du7y64cywy8aea1l9terot foreign key (discipline_id) references discipline;
alter table if exists tournament_discipline
    add constraint FK4nqu9yp0852tly8yewyen1p2 foreign key (tournament_id) references tournament;


