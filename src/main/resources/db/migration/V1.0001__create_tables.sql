create table cad_cidade
(
    codigo_ibge      varchar(255) not null,
    capital          boolean,
    latitude         double precision,
    longitude        double precision,
    meso_regiao      varchar(150) not null,
    micro_regiao     varchar(150) not null,
    no_accents       varchar(150) not null,
    nome             varchar(150) not null,
    nome_alternativo varchar(150) not null,
    cad_estado_id    varchar(2)   not null,
    primary key (codigo_ibge)
);

create table cad_estado
(
    cad_estado_id varchar(2) not null,
    sigla         varchar(2),
    primary key (cad_estado_id)
);

alter table cad_cidade
    add constraint FK_UF foreign key (cad_estado_id) references cad_estado (cad_estado_id);