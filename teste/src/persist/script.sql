use teste;

drop table if exists aluno;
create table aluno (
	id BIGINT primary key auto_increment,
	matricula varchar(50) not null,
	nome varchar(255),
	email varchar(50) not null,
	
);



select * from aluno