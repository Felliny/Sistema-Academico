USE Avaliacao_2_Lab_BD

GO

/*
    DROP FUNCTION fn_buscar_disciplinas_pra_dispensa
*/

DROP FUNCTION fn_buscar_disciplinas_pra_dispensa

GO

CREATE FUNCTION fn_buscar_disciplinas_pra_dispensa(@ra VARCHAR(50))
RETURNS @table TABLE
(
    id_matricula INT,
    codigo INT,
    nome_disc VARCHAR(100),
    nome_professor VARCHAR(100),
    num_aulas INT,
    situacao VARCHAR(20)
)
BEGIN
    DECLARE @curso INT

    SELECT @curso=c.codigo FROM curso c, matricula m WHERE m.cod_curso = c.codigo AND m.ra = @ra

    INSERT INTO @table (id_matricula, codigo, nome_disc, nome_professor, num_aulas, situacao)
        SELECT id_matricula_disc, codigo_disciplina, nome_disciplina, nome_professor, horas_semanais, situacao
        FROM (
            SELECT
                CASE WHEN (md.id IS NULL OR md.id = 0) THEN 0 ELSE md.id END AS id_matricula_disc,
                c.cod_disciplina AS codigo_disciplina,
                d.nome AS nome_disciplina,
                prof.nome AS nome_professor,
                d.horas_semanais,
                CASE WHEN (md.estado IS NULL) THEN 'DISPONIVEL' ELSE UPPER(md.estado) END AS situacao,
                ROW_NUMBER() OVER(PARTITION BY c.cod_disciplina ORDER BY CASE WHEN md.estado = 'MATRICULADO' THEN 0 ELSE 1 END) AS rn

            FROM curso_disciplina c
            LEFT JOIN matricula_disciplina md ON md.cod_disciplina = c.cod_disciplina AND md.ra_matricula = @ra
            JOIN disciplina d ON c.cod_disciplina = d.codigo
            JOIN professor prof ON d.id_professor = prof.id

            WHERE c.cod_curso = @curso
            AND (md.estado != 'DISPENSADO' OR md.estado IS NULL)
            AND d.codigo NOT IN (
                SELECT d.codigo AS cod
                FROM matricula_disciplina md, disciplina d, horario h
                WHERE md.cod_disciplina = d.codigo AND md.id_horario = h.id AND
                    ra_matricula = @ra AND estado LIKE 'aprovado'
            )
        ) AS temp
        WHERE rn = 1;

    RETURN

END

-- 202321485 : ADS ; 202325763 : COMEX
-- SELECT * FROM fn_buscar_disciplinas_pra_dispensa('202321485')
