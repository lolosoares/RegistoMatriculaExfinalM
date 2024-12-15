use POO2BD;

show databases;
select *from Estudantes;

#Exercicio final POO2
create database if not exists RegistroMatricula;
CREATE TABLE IF NOT EXISTS Cliente (
    matricula INT,
    nome VARCHAR(50),
    idade INT,
    sexo CHAR(1) CHECK (sexo IN ('M', 'F'))
);

insert into Cliente(matricula,nome,idade,sexo) Values("20231198","Robson Soares",34,"M");
insert into Cliente(matricula,nome,idade,sexo) Values("20231197","Antonia Soares",24,"F");
insert into Cliente(matricula,nome,idade,sexo) Values("20231199","Paito Soares",24,"M");

use RegistroMatricula;
show tables;
select *from Cliente;
