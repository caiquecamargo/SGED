create database sged;
create user 'sged'@'localhost' identified by 'CaiqueMetal199#';
grant all privileges on sged.* to 'sged'@'localhost';

use sged;

create table tblUsuario (
	idUsuario int not null auto_increment,
    nome varchar(100) not null,
    email varchar(100) not null unique,
    senha varchar(30),
    nivelDeAcesso int,
    situacao int,
    constraint pk_usuario primary key (idUsuario)
);

create table tblItem (
	idItem int not null auto_increment,
    tipo varchar(10),
    nome varchar(100),
    restricoes varchar(1000),
    src varchar(1000),
    constraint pk_item primary key (idItem)
);

create table tblGrupo (
	idGrupo int not null auto_increment,
    nome varchar(50),
    descricao varchar(300),
    nivel int not null,
    constraint pk_grupo primary key (idGrupo)
);

create table tblUsuarioGrupo(
	idUsuario int not null,
    idGrupo int not null,
    constraint pk_ug primary key (idUsuario, idGrupo),
    constraint pk_us foreign key (idUsuario) references tblUsuario (idUsuario),
    constraint pk_gr foreign key (idGrupo) references tblGrupo(idGrupo)
);

create table tblUsuarioItem(
	idUsuario int not null,
    idItem int not null,
    constraint pk_ui primary key (idUsuario, idItem),
    constraint fk_ui foreign key (idUsuario) references tblUsuario (idUsuario),
    constraint fk_it foreign key (idItem) references tblItem (idItem)
);

insert into tblUsuario values (null, 'Professor Isidro', 'isidro@professorisidro.com.br', '1234', 0, 1);
insert into tblUsuario values (null, 'Caique de Camargo', 'caique.de.camargo@hotmail.com', '1234', 0, 1);
insert into tblUsuario values (null, 'Giovanna Murakami de Oliveira', 'giovanna.murakami@gmail.com', '1234', 0, 1);
insert into tblUsuario values (null, 'teste', 'teste@teste.com', '1234', 1, 1);

SELECT * FROM tblUsuario;