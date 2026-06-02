create database SGE;

use SGE;

create table localidades(
id int,
nome varchar (60),
primary key (id)
);

create table rotas(
idOrigem int not null,
idDestenino int not null,
distancia_km int,
primary key (idOrigem, idDestenino),
foreign key (idOrigem) references localidades(id),
foreign key (idDestenino) references localidades(id)
);

create table automoveis(
id int,
modelo varchar(50),
placa varchar(50),
capcaidade int,
primary key (id)
);

create table encomendas(
id int,
discricao text,
idOrigem int,
idDestino int,
idVeiculo int,
statusEncomenda varchar(35),
primary key (id),                                                
foreign key (idOrigem) references localidades(id),
foreign key (idDestino) references localidades(id),
foreign key (idVeiculo) references automoveis(id)
);
