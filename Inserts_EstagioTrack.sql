--Poando o banco de dados do EstagioTrack

--Para limpar seu banco, execute o comando a seguir
--TRUNCATE TABLE aluno, aluno_competencias, empresa, oferta, oferta_competencias, candidatura, competencia_template  CASCADE;

-- SQL para popular a tabela read-only de competências a ser utilizada nos templates.-
INSERT INTO competencia_template (id, nome, icone_url) VALUES
(1, 'C', 'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/c/c-original.svg'),
(2, 'C#', 'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/csharp/csharp-original.svg'),
(3, 'C++', 'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/cplusplus/cplusplus-original.svg'),
(4, 'CSS', 'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/css3/css3-original.svg'),
(5, 'HTML', 'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/html5/html5-original.svg'),
(6, 'Java', 'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/java/java-original.svg'),
(7, 'JavaScript', 'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/javascript/javascript-original.svg'),
(8, 'Python', 'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/python/python-original.svg'),
(9, 'Ruby', 'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/ruby/ruby-original.svg'),
(10, 'SQL', 'https://cdn.jsdelivr.net/gh/devicons/devicon/icons/mysql/mysql-original.svg');

--SELECT * FROM competencia_template

INSERT INTO aluno (id, email, nome, nome_usuario, senha, genero) VALUES
(1, 'amanda@gmail.com', 'Amanda Cruz', 'amandaCruz', '123', 'Feminino'),
(2, 'george@gmail.com', 'George Barbosa', 'georgeD', '123', 'Masculino'),
(3, 'brian@gmail.com', 'Brian Rafael', 'brian', '123', 'Masculino'),
(4, 'olivia@gmail.com', 'Olivia Oliva', 'oliviaO', '123', 'Feminino'),
(5, 'mariana@gmail.com', 'Mariana Oliveira', 'marianaO', '123', 'Feminino'),
(6, 'daniel@gmail.com', 'Daniel Costa', 'danielCosta', '123', 'Masculino'),
(7, 'sofia@gmail.com', 'Sofia Almeida', 'sofiaAlmeida', '123', 'Feminino'),
(8, 'andre@gmail.com', 'André Pereira', 'andrePereira', '123', 'Masculino'),
(9, 'patricia@gmail.com', 'Patricia Santos', 'patriciaSantos', '123', 'Feminino'),
(10, 'luana@gmail.com', 'Luana Silva', 'luanaSilva', '123', 'Feminino');

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

INSERT INTO empresa (id, cnpj, nome_usuario, cep, logradouro, numero_imovel, bairro, cidade, estado, telefone, atividade_principal, site, comprovante_endereco, razao_social, responsavel, email, senha) 
VALUES 
(1, '12345678000199', '12345678000199', '58000000', 'Rua das Startups', '101', 'Centro', 'João Pessoa', 'PB', '(83) 99999-9999', 'Desenvolvimento de Software', 'https://www.techinnovate.com', null, 'Tech Innovate LTDA', 'Lucas Souza', 'contato@techinnovate.com', 'senhaSegura123'),
(2, '98765432000187', '98765432000187', '58001000', 'Av. Epitácio Pessoa', '305', 'Tambaú', 'João Pessoa', 'PB', '(83) 98888-8888', 'Serviços de Web Design', 'https://www.websolutions.com', null, 'Web Solutions LTDA', 'Maria Silva', 'contato@websolutions.com', 'senhaSegura456'),
(3, '45612378000177', '45612378000177', '58002000', 'Rua da Tecnologia', '500', 'Bessa', 'João Pessoa', 'PB', '(83) 98777-7777', 'Computação em Nuvem', 'https://www.clouddata.com', null, 'Cloud Data SA', 'João Carvalho', 'contato@clouddata.com', 'senhaSegura789'),
(4, '32165498000155', '32165498000155', '58003000', 'Av. Cabo Branco', '123', 'Cabo Branco', 'João Pessoa', 'PB', '(83) 98666-6666', 'Consultoria em TI', 'https://www.nextgenit.com', null, 'NextGen IT LTDA', 'Ana Santos', 'contato@nextgenit.com', 'senhaSegura101'),
(5, '78912345000166', '78912345000166', '58004000', 'Rua das Inovações', '200', 'Altiplano', 'João Pessoa', 'PB', '(83) 98555-5555', 'Segurança Cibernética', 'https://www.cybertech.com', null, 'CyberTech Security LTDA', 'Carlos Lima', 'contato@cybertech.com', 'senhaSegura202'),
(6, '65432109000144', '65432109000144', '58005000', 'Av. Dom Pedro II', '450', 'Torre', 'João Pessoa', 'PB', '(83) 98444-4444', 'Desenvolvimento de Aplicações Móveis', 'https://www.devmasters.com', null, 'Dev Masters LTDA', 'Paulo Oliveira', 'contato@devmasters.com', 'senhaSegura303'),
(7, '15975368000133', '15975368000133', '58006000', 'Rua do Big Data', '789', 'Bancários', 'João Pessoa', 'PB', '(83) 98333-3333', 'Análise de Dados', 'https://www.dataanalytics.com', null, 'Data Analytics LTDA', 'Juliana Costa', 'contato@dataanalytics.com', 'senhaSegura404');

INSERT INTO oferta VALUES
(1,	'Desenvolvimento de Software', '28', 'havaianas@gmail.com', null, 'ABERTA', 'Desenvolvedor', 'Sim', '500.00', 1),
(2,	'Computação em Nuvem', '25', 'apple@gmail.com', 'Disponibilidade para viagens', 'INTERROMPIDA', 'Suporte', 'Sim', '600.00', 4),
(3,	'Big Data e Analytics', '20', 'amazon@gmail.com', null, 'ABERTA', 'Analista', 'Não', '750.00', 6);

INSERT INTO oferta_competencias values
(1, 'Java'),
(1, 'HTML'),
(1, 'JavaScript'),
(2, 'Python'),
(2, 'SQL'),
(3, 'C++'),
(3, 'Python');

INSERT INTO candidatura VALUES 
(1,'amanda@gmail.com','PENDENTE',1,1),
(2,'george@gmail.com','PENDENTE',2,1),
(3,'mariana@gmail.com','REJEITADA',5,2),
(4,'mariana@gmail.com','PENDENTE',5,3),
(5,'mariana@gmail.com','PENDENTE',5,1),
(6,'luana@gmail.com','PENDENTE',10,3),
(7,'brian@gmail.com','PENDENTE',3,3),
(8,'olivia@gmail.com','PENDENTE',4,1),
(9,'olivia@gmail.com','REJEITADA',4,2);

select * from aluno;
select * from aluno_competencias;

select * from empresa;

select * from oferta;
select * from oferta_competencias;

select * from candidatura;

select * from competencia_template;