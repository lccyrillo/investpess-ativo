create database investpess_ativo;

\c investpess_ativo

create table ativoobjeto (
    id serial not null,
    data_cadastro timestamp not null default current_timestamp,
    sigla_ativo varchar(15) not null,
    nome_ativo varchar(150) not null,
    descricao_cnpj_ativo varchar(18) not null,
    tipo_ativo int not null
);