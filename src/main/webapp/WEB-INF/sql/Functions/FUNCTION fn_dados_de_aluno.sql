USE Avaliacao_2_Lab_BD

GO

/*
    DROP FUNCTION fn_dados_de_aluno
*/

CREATE FUNCTION fn_dados_de_aluno (@ra CHAR(9))
RETURNS @table TABLE
(
    ra CHAR(9),
    nome_aluno VARCHAR(100),
    nome_curso VARCHAR(100),
    dt_matricula VARCHAR(20),
    pontuacao_vestibular INT,
    posicao_vestibular INT
)
BEGIN
    INSERT INTO @table
        SELECT m.ra,
        CASE
        WHEN a.nome_social IS NULL THEN UPPER(a.nome)
        WHEN a.nome_social = '' THEN UPPER(a.nome)
        ELSE UPPER(a.nome_social)
        END AS nome_aluno,
        c.nome AS nome_curso,
        CAST(m.ano_ingresso AS VARCHAR(50)) + '/' + CAST(m.semestre_ingresso AS VARCHAR(50)) AS data,
        m.pontuacao_vestibular, m.posicao_vestibular
        FROM matricula m, aluno a, curso c
        WHERE m.ra = @ra
        AND m.cpf_aluno = a.cpf AND m.cod_curso = c.codigo

    RETURN
END

-- 202321485 : ADS ; 202325763 : COMEX
-- SELECT * FROM fn_dados_de_aluno('202325763')

