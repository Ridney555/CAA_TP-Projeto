create database SGE;

use SGE;

create table eventos(
id int not null,
nome varchar(50),
Data date,
horaInicio time,
horaFim time,
capcidadeMaxima int,
primary key (id)
);

create table participante(
id int not null,
nome varchar(50),
telefone int,
primary key(id)
);

create table inscricao(
id int not null,
idParticipante int,
idEvento int,
primary key(id),
foreign key (idParticipante) references Participante(id),
foreign key (idEvento) references Eventos(id)
);

describe eventos;
describe participantes;
describe eventos;

select * FROM eventos;
select * FROM particpantes;
select * FROM inscricao;