CREATE DATABASE projeto_livraria
GO
USE projeto_livraria

CREATE TABLE Categoria(
codigo		INT				NOT NULL	IDENTITY(1,1),
nome		VARCHAR(60)		NOT NULL
PRIMARY KEY(codigo)
)
GO
CREATE TABLE Livro(
id			INT				NOT NULL	IDENTITY(1000,1),
titulo		VARCHAR(200)	NOT NULL,
ano			INT				NOT NULL	CHECK(ano > 0),
lingua		VARCHAR(20)		NOT NULL,
formato		VARCHAR(10)		NOT NULL,
resumo		VARCHAR(500)	NOT NULL,
categoria	INT				NOT NULL
PRIMARY KEY(id)
FOREIGN KEY(categoria) REFERENCES Categoria(codigo)
)
GO
CREATE TABLE Autor(
codigo			INT				NOT NULL	IDENTITY(2000,1),
nome			VARCHAR(200)	NOT NULL,
data_nascimento	DATE			NOT NULL,
pais			VARCHAR(80)		NOT NULL,
biografia		VARCHAR(500)	NOT NULL
PRIMARY KEY(codigo)
)
GO
CREATE TABLE Livro_Autor(
id_livro	INT		NOT NULL,
id_autor	INT		NOT NULL
PRIMARY KEY(id_livro, id_autor)
FOREIGN KEY(id_livro) REFERENCES Livro(id),
FOREIGN KEY(id_autor) REFERENCES Autor(codigo)
)
GO
CREATE TABLE Editora(
cnpj			VARCHAR(14)		NOT NULL	CHECK(LEN(cnpj) = 14),
nome			VARCHAR(50)		NOT NULL,
email			VARCHAR(50)		NOT NULL,
telefone		VARCHAR(11)		NOT NULL	CHECK(LEN(telefone) >= 8),
logradouro_end	VARCHAR(100)	NOT NULL,
numero_end		INT				NOT NULL	CHECK(numero_end > 0),
complemento_end	VARCHAR(255)	NULL,
cep				CHAR(8)			NOT NULL	CHECK(LEN(cep) = 8)
PRIMARY KEY(cnpj)
)
GO
CREATE TABLE Edicao(
isbn			VARCHAR(14)		NOT NULL	CHECK(LEN(isbn) = 13),
ano				INT				NOT NULL	CHECK(ano > 0),
numero_paginas	INT				NOT NULL	CHECK(numero_paginas > 0),
qtd_estoque		INT				NOT NULL	CHECK(qtd_estoque >= 0),
valor_unitario	DECIMAL(7,2)	NOT NULL	CHECK(valor_unitario > 0)
PRIMARY KEY(isbn)
)
GO
CREATE TABLE Livro_Editora_Edicao(
id_livro		INT				NOT NULL,
id_editora		VARCHAR(14)		NOT NULL,
id_edicao		VARCHAR(14)		NOT NULL
PRIMARY KEY(id_livro, id_editora, id_edicao)
FOREIGN KEY(id_livro) REFERENCES Livro(id),
FOREIGN KEY(id_editora) REFERENCES Editora(cnpj),
FOREIGN KEY(id_edicao) REFERENCES Edicao(isbn)
)
GO
CREATE TABLE Usuario(
id				INT				NOT NULL	IDENTITY(5500,1),
nome			VARCHAR(150)	NOT NULL,
username		VARCHAR(25)		NOT NULL	UNIQUE,
senha			VARCHAR(15)		NOT NULL	CHECK(LEN(senha) >= 8),
email			VARCHAR(50)		NOT NULL
PRIMARY KEY(id)
)

SELECT * FROM Categoria
SELECT * FROM Livro
SELECT * FROM Autor
SELECT * FROM Livro_Autor
SELECT * FROM Editora
SELECT * FROM Edicao
SELECT * FROM Livro_Editora_Edicao
SELECT * FROM Usuario

EXEC sp_help Categoria
EXEC sp_help Livro
EXEC sp_help Autor
EXEC sp_help Livro_Autor
EXEC sp_help Editora
EXEC sp_help Edicao
EXEC sp_help Livro_Editora_Edicao
EXEC sp_help Usuario

DROP TABLE Edicao
DROP TABLE Editora
DROP TABLE Livro_Editora_Edicao


