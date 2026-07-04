create database SGC;

use SGC;

create table Eventos(
id int,
nome varchar(50),
Data date,
horaInicio time,
horaFim time,
capcidadeMaxima int,
primary key (id)
);

create table Participante(
id int,
nome varchar(50),
telefone int,
primary key(id)
);

create table Inscricao(
id int,
idParticipante int,
idEvento int,
primary key(id),
foreign key (idParticipante) references Participante(id),
foreign key (idEvento) references Eventos(id)
);

