--Poando o banco de dados do EstagioTrack

--Para limpar seu banco, execute o comando a seguir
--TRUNCATE TABLE aluno, aluno_competencias, empresa, oferta, oferta_competencias, candidatura  CASCADE;

INSERT INTO aluno values
( 1,'amanda@gmail.com','Feminino','Amanda Cruz','amandaCruz','123'),
(2,'george@gmail.com','Masculino','George Barbosa','georgeD','123'),
(3,'brian.com','Masculino','Brian Rafael','brian','123'),
(4,'olivia@gmail.com','Feminino','Olivia Oliva','oliviaO','123'),
(5, 'mariana@gmail.com', 'Feminino', 'Mariana Oliveira', 'marianaO', '123'),
(6, 'daniel@gmail.com', 'Masculino', 'Daniel Costa', 'danielCosta', '123'),
(7, 'sofia@gmail.com', 'Feminino', 'Sofia Almeida', 'sofiaAlmeida', '123'),
(8, 'andre@gmail.com', 'Masculino', 'André Pereira', 'andrePereira', '123'),
(9, 'patricia@gmail.com', 'Feminino', 'Patricia Santos', 'patriciaSantos', '123'),
(10, 'luana@gmail.com', 'Feminino', 'Luana Silva', 'luanaSilva', '123');

INSERT INTO aluno_competencias values
( 1, 'Java'),
( 1, 'HTML'),
( 1, 'JavaScript'),
( 2, 'Java'),
( 2, 'SQL'),
( 3, 'C++'),
( 3, 'Python'),
( 4, 'Ruby'),
( 4, 'SQL'),
( 5, 'C#'),
( 5, 'CSS'),
( 6, 'Ruby'),
( 7, 'C'),
( 7, 'HTML'),
( 7, 'Ruby'),
( 8, 'Python'),
( 9, 'Ruby'),
( 10, 'CSS'),
( 10, 'JavaScript');

INSERT into empresa VALUES
(1,'Desenvolvimento de Software','Expedicionários', '58041060','João Pessoa','74512589678546','' ,'havaianas@gmail.com','PB','Avenida Capitão João FreirE','4','Havaianas','Carla','123'),
(2,'Big Data e Analytics','Expedicionários', '58041060','João Pessoa','74512589678546','' ,'lg@gmail.com','PB','Avenida Capitão João FreirE','4','LG','Fernadno','123'),
(3,'Inteligência Artificial','Expedicionários', '58041060','João Pessoa','74512589678546','' ,'google@gmail.com','PB','Avenida Capitão João FreirE','4','Google','Henry','123'),
(4, 'Computação em Núvem', 'Tambaú', '58039070', 'João Pessoa', '74512589678900', '', 'apple@gmail.com', 'PB', 'Rua das Acácias', '50', 'Apple', 'John', '124'),
(5, 'Suporte Técnico', 'Manaíra', '58038100', 'João Pessoa', '74512589678899', '', 'ibm@gmail.com', 'PB', 'Rua do Sol', '12', 'IBM', 'Alice', '125'),
(6, 'Segurança da Informação', 'Cabo Branco', '58045200', 'João Pessoa', '74512589678888', '', 'amazon@gmail.com', 'PB', 'Avenida Atlântica', '30', 'Amazon', 'Sophia', '126'),
(7, 'Inteligência Artificial', 'Altiplano', '58045100', 'João Pessoa', '74512589678777', '', 'nintendo@gmail.com', 'PB', 'Rua das Flores', '120', 'Nintendo', 'Luigi', '127');

INSERT INTO oferta VALUES
(1,	'havaianas@gmail.com', 'ABERTA','Desenvolvedor', null, '500.00', 1 ,'Desenvolvimento de Software', '28', '','Sim'),
(2,	'apple@gmail.com','ABERTA', 'Suporte',  null , '600.00', 4 ,'Computação em Nuvem','25', 'Disponibilidade para viagens' ,'Sim'),
(3,	'amazon@gmail.com','ABERTA','Analista',null, '750.00',	6,'Big Data e Analytics', '20', '', 'Não');

INSERT INTO oferta_competencias values
( 1, 'Java'),
( 1, 'HTML'),
( 1, 'JavaScript'),
( 2, 'Python'),
( 2, 'SQL'),
( 3, 'C++'),
( 3, 'Python');

INSERT INTO candidatura VALUES 
(1,'amanda@gmail.com','PENDENTE',1,1),
(2,'george@gmail.com','PENDENTE',2,1),
(3,'mariana@gmail.com','PENDENTE',5,2),
(4,'mariana@gmail.com','PENDENTE',5,3),
(5,'mariana@gmail.com','PENDENTE',5,1),
(6,'luana@gmail.com','PENDENTE',10,3),
(7,'brian.com','PENDENTE',3,3),
(8,'olivia@gmail.com','PENDENTE',4,1),
(9,'olivia@gmail.com','PENDENTE',4,2);

select * from aluno;
select * from aluno_competencias;

select * from empresa;

select * from oferta;
select * from oferta_competencias;

select * from candidatura;

