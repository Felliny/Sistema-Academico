USE Avaliacao_2_Lab_BD

GO

/*
    DROP FUNCTION fn_pesquisar_alunos
*/

CREATE FUNCTION fn_pesquisar_alunos(@tipo_pesq VARCHAR(50), @valor_busca VARCHAR(100))
RETURNS @table TABLE
(
    cpf CHAR(11),
    ra CHAR(9),
    nome_aluno VARCHAR(100),
    nome_curso VARCHAR(100),
    dt_matricula VARCHAR(20),
    pontuacao_vestibular INT,
    posicao_vestibular INT
)
BEGIN
    SET @tipo_pesq = UPPER(@tipo_pesq)
    SET @valor_busca = UPPER(@valor_busca)

    IF (@tipo_pesq = 'CPF')
    BEGIN
        INSERT INTO @table
            SELECT a.cpf, m.ra,
            CASE
            WHEN a.nome_social IS NULL THEN UPPER(a.nome)
            WHEN a.nome_social = '' THEN UPPER(a.nome)
            ELSE UPPER(a.nome_social)
            END AS nome_aluno,
            c.nome AS nome_curso,
            CAST(m.ano_ingresso AS VARCHAR(50)) + '/' + CAST(m.semestre_ingresso AS VARCHAR(50)) AS data,
            m.pontuacao_vestibular, m.posicao_vestibular
            FROM matricula m, aluno a, curso c
            WHERE m.cpf_aluno = a.cpf AND m.cod_curso = c.codigo AND
            a.cpf LIKE '%'+@valor_busca+'%'
    END
    IF (@tipo_pesq = 'RA')
    BEGIN
        INSERT INTO @table
            SELECT a.cpf, m.ra,
            CASE
            WHEN a.nome_social IS NULL THEN UPPER(a.nome)
            WHEN a.nome_social = '' THEN UPPER(a.nome)
            ELSE UPPER(a.nome_social)
            END AS nome_aluno,
            c.nome AS nome_curso,
            CAST(m.ano_ingresso AS VARCHAR(50)) + '/' + CAST(m.semestre_ingresso AS VARCHAR(50)) AS data,
            m.pontuacao_vestibular, m.posicao_vestibular
            FROM matricula m, aluno a, curso c
            WHERE m.cpf_aluno = a.cpf AND m.cod_curso = c.codigo AND
            m.ra LIKE '%'+@valor_busca+'%'
    END
    IF (@tipo_pesq = 'NOME')
    BEGIN
        INSERT INTO @table
            SELECT a.cpf, m.ra,
            CASE
            WHEN a.nome_social IS NULL THEN UPPER(a.nome)
            WHEN a.nome_social = '' THEN UPPER(a.nome)
            ELSE UPPER(a.nome_social)
            END AS nome_aluno,
            c.nome AS nome_curso,
            CAST(m.ano_ingresso AS VARCHAR(50)) + '/' + CAST(m.semestre_ingresso AS VARCHAR(50)) AS data,
            m.pontuacao_vestibular, m.posicao_vestibular
            FROM matricula m, aluno a, curso c
            WHERE m.cpf_aluno = a.cpf AND m.cod_curso = c.codigo AND
            ((a.nome_social IS NULL AND a.nome LIKE '%'+@valor_busca+'%') OR
            (a.nome_social IS NOT NULL AND a.nome_social LIKE '%'+@valor_busca+'%'))
    END
    IF (@tipo_pesq = 'ANO')
    BEGIN
        INSERT INTO @table
            SELECT a.cpf, m.ra,
            CASE
            WHEN a.nome_social IS NULL THEN UPPER(a.nome)
            WHEN a.nome_social = '' THEN UPPER(a.nome)
            ELSE UPPER(a.nome_social)
            END AS nome_aluno,
            c.nome AS nome_curso,
            CAST(m.ano_ingresso AS VARCHAR(50)) + '/' + CAST(m.semestre_ingresso AS VARCHAR(50)) AS data,
            m.pontuacao_vestibular, m.posicao_vestibular
            FROM matricula m, aluno a, curso c
            WHERE m.cpf_aluno = a.cpf AND m.cod_curso = c.codigo AND
            m.ano_ingresso LIKE '%'+@valor_busca+'%'
    END
    IF (@tipo_pesq = 'CURSO')
    BEGIN
        INSERT INTO @table
            SELECT a.cpf, m.ra,
            CASE
            WHEN a.nome_social IS NULL THEN UPPER(a.nome)
            WHEN a.nome_social = '' THEN UPPER(a.nome)
            ELSE UPPER(a.nome_social)
            END AS nome_aluno,
            c.nome AS nome_curso,
            CAST(m.ano_ingresso AS VARCHAR(50)) + '/' + CAST(m.semestre_ingresso AS VARCHAR(50)) AS data,
            m.pontuacao_vestibular, m.posicao_vestibular
            FROM matricula m, aluno a, curso c
            WHERE m.cpf_aluno = a.cpf AND m.cod_curso = c.codigo AND
            c.nome LIKE '%'+@valor_busca+'%'
    END


    RETURN
END

-- 202321485 : ADS ; 202325763 : COMEX
/*
        SELECT * FROM fn_pesquisar_alunos('nome', 'Oliveira')
*/
