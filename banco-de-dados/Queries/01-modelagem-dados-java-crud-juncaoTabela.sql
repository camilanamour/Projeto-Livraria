use projeto_livraria

-- Categoria (entidade 1)
INSERT INTO Categoria VALUES (?)
SELECT codigo, nome FROM Categoria WHERE codigo = ?
SELECT codigo, nome FROM Categoria

-- Autor (entidade 2)
INSERT INTO Autor VALUES(?,?,?,?)
SELECT codigo, nome, data_nascimento AS nascimento, pais, biografia FROM Autor WHERE codigo = ?
UPDATE Autor SET nome = ?, data_nascimento = ?, pais = ?, biografia = ? WHERE codigo = ?
DELETE Autor WHERE codigo = ?
SELECT codigo, nome, data_nascimento AS nascimento, pais, biografia FROM Autor ORDER BY nome ASC
SELECT codigo, nome, data_nascimento AS nascimento, pais, biografia FROM Autor WHERE ? LIKE ? ORDER BY ? ASC

-- Livro (entidade 3)
INSERT INTO Livro VALUES(?,?,?,?,?,?)
SELECT id, titulo, ano, lingua, formato, resumo, categoria FROM Livro WHERE id = ?
UPDATE Livro SET titulo = ?, ano = ?, lingua = ?, formato = ?, resumo = ?, categoria = ? WHERE id = ?
DELETE Livro WHERE id = ?
SELECT id, titulo, ano, lingua, formato, resumo, categoria FROM Livro ORDER BY titulo ASC
SELECT id, titulo, ano, lingua, formato, resumo, categoria FROM Livro WHERE titulo LIKE ? ORDER BY titulo ASC
SELECT id FROM Livro WHERE titulo = ? AND resumo = ?

-- Editora (entidade 4)
INSERT INTO Editora VALUES(?,?,?,?,?,?,?,?)

SELECT SUBSTRING(cnpj, 1, 2) + '.' + SUBSTRING(cnpj, 3, 3) + '.' + SUBSTRING(cnpj, 6, 3)+ '/' + 
SUBSTRING(cnpj, 9, 4) + '-' + SUBSTRING(cnpj, 13, 2) AS cnpj, 
nome, email, SUBSTRING(telefone, 1, 4) + '-' +
SUBSTRING(telefone, 5, 4) AS telefone, logradouro_end, numero_end, complemento_end, 
SUBSTRING(cep, 1, 5) + '-' + SUBSTRING(telefone, 6, 3) AS cep 
FROM Editora WHERE cnpj LIKE ?

SELECT cnpj FROM Editora WHERE nome = ?

UPDATE Editora SET cnpj = ?, nome = ?, email = ?, telefone = ?, logradouro_end = ?, numero_end = ?, complemento_end = ?, cep = ? WHERE cnpj = ?
DELETE Editora WHERE cnpj = ?

SELECT SUBSTRING(cnpj, 1, 2) + '.' + SUBSTRING(cnpj, 3, 3) + '.' + SUBSTRING(cnpj, 6, 3)
+ '/' + SUBSTRING(cnpj, 9, 4) + '-' + SUBSTRING(cnpj, 13, 2) AS cnpj, nome, email, 
SUBSTRING(telefone, 1, 4) + '-' + SUBSTRING(telefone, 5, 4) AS telefone, logradouro_end, 
numero_end, complemento_end, SUBSTRING(cep, 1, 5) + '-' + SUBSTRING(telefone, 6, 3) AS cep
FROM Editora

SELECT SUBSTRING(cnpj, 1, 2) + '.' + SUBSTRING(cnpj, 3, 3) + '.' + SUBSTRING(cnpj, 6, 3)
'/' + SUBSTRING(cnpj, 9, 4) + '-' + SUBSTRING(cnpj, 13, 2) AS cnpj, nome, email, 
SUBSTRING(telefone, 1, 4) + '-' + SUBSTRING(telefone, 5, 4) AS telefone, 
logradouro_end, numero_end, complemento_end, SUBSTRING(cep, 1, 5) + '-' + SUBSTRING(telefone, 6, 3) AS cep 
FROM Editora WHERE ? LIKE ? ORDER BY ? ASC

-- Edição (entidade 5)
INSERT INTO Edicao VALUES (?, ?, ?, ?, ?) 

SELECT * FROM Edicao e, Editora d, Livro l, Livro_Editora_Edicao lee 
WHERE e.isbn = lee.id_edicao AND d.cnpj = lee.id_editora AND l.id = lee.id_livro AND isbn = ?

UPDATE Edicao SET isbn = ?, ano = ?, numero_paginas = ?, qtd_estoque = ?, valor_unitario = ? WHERE isbn = ? 
DELETE Edicao WHERE isbn = ?

SELECT * FROM Edicao e, Editora d, Livro l, Livro_Editora_Edicao lee 
WHERE e.isbn = lee.id_edicao AND d.cnpj = lee.id_editora AND l.id = lee.id_livro

-- Usuario (entidade 6)
INSERT INTO Usuario VALUES(?,?,?,?)
SELECT username, senha, nome FROM Usuario
SELECT email FROM Usuario

-- Junção de Tabelas

-- Livro_Autor
INSERT INTO Livro_Autor VALUES (?,?)
DELETE Livro_Autor WHERE id_livro = ?

-- Autor
SELECT Autor.codigo AS cod, Autor.nome AS nome 
FROM Autor INNER JOIN Livro_Autor
ON Autor.codigo = Livro_Autor.id_autor
INNER JOIN Livro 
ON Livro.id = Livro_Autor.id_livro
WHERE Livro.id = ?
ORDER BY Autor.nome ASC

-- Categoria
SELECT Livro.id AS id, Livro.titulo AS titulo, 
Livro.ano AS ano, Livro.lingua AS lingua, 
Livro.formato AS formato, Livro.resumo AS resumo, Livro.categoria AS categoria
FROM Livro INNER JOIN Categoria
ON Livro.categoria = Categoria.codigo 
WHERE Categoria.nome LIKE ? 
ORDER BY Categoria.nome ASC

-- Autor		
SELECT Livro.id AS id, Livro.titulo AS titulo, 
Livro.ano AS ano, Livro.lingua AS lingua, 
Livro.formato AS formato, Livro.resumo AS resumo, 
Livro.categoria AS categoria 
FROM Livro INNER JOIN Livro_Autor
ON Livro.id = Livro_Autor.id_livro
INNER JOIN Autor
ON Autor.codigo = Livro_Autor.id_autor
WHERE Autor.nome LIKE ?
ORDER BY Autor.nome ASC

-- Livro_Editora_Edicao
INSERT INTO Livro_Editora_Edicao VALUES (?, ?, ?)
UPDATE Livro_Editora_Edicao SET id_livro = ?, id_editora = ?, id_edicao = ? WHERE id_edicao = ?
DELETE Livro_Editora_Edicao WHERE id_edicao = ?

-- Livro
SELECT * FROM Edicao e, Editora d, Livro l, Livro_Editora_Edicao lee 
WHERE e.isbn = lee.id_edicao AND d.cnpj = lee.id_editora AND l.id = lee.id_livro
AND l.titulo LIKE ? ORDER BY l.titulo ASC

-- Editora
SELECT * FROM Edicao e, Editora d, Livro l, Livro_Editora_Edicao lee 
WHERE e.isbn = lee.id_edicao AND d.cnpj = lee.id_editora AND l.id = lee.id_livro
AND d.nome LIKE ? ORDER BY d.nome ASC
	
-- Edição
SELECT * FROM Edicao e, Editora d, Livro l, Livro_Editora_Edicao lee
WHERE e.isbn = lee.id_edicao AND d.cnpj = lee.id_editora AND l.id = lee.id_livro
AND e. ? LIKE ? ORDER BY e.? ASC

