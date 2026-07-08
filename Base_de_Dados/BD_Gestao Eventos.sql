create database SGE1;

use SGE1;

create table eventos(
id int not null,
nome varchar(50),
Data date,
horaInicio time,
horaFim time,
capcidadeMaxima int,
primary key (id)
);

create table participantes(
id int not null,
nome varchar(50),
telefone int,
primary key(id)
);

create table inscricao(
id int not null,
idParticipantes int,
idEventos int,
primary key(id),
foreign key (idParticipantes) references participantes(id),
foreign key (idEventos) references eventos(id)
);

describe eventos;
describe participantes;
describe inscricao;

select * FROM eventos;
select * FROM particpantes;
select * FROM inscricao;