USE Avaliacao_2_Lab_BD

GO

/*
DROP FUNCTION fn_disciplinas_disponiveis
*/

CREATE FUNCTION fn_disciplinas_disponiveis(@ra CHAR(9))
RETURNS @table TABLE
(
    codigo INT,
    nome VARCHAR(50),
    numero_aulas INT,
    situacao VARCHAR(12)
)
BEGIN
    INSERT INTO @table
    SELECT DISTINCT d.codigo, d.nome, d.horas_semanais,
                CASE WHEN md.estado IS NULL THEN 'disponivel' ELSE 'reprovado' END AS situacao
    FROM curso_disciplina cd
         INNER JOIN disciplina d ON cd.cod_disciplina = d.codigo
         LEFT JOIN matricula m ON m.cod_curso = cd.cod_curso AND m.ra = @ra
         LEFT JOIN matricula_disciplina md ON md.cod_disciplina = cd.cod_disciplina
                AND md.ra_matricula = m.ra
    WHERE m.ra IS NOT NULL AND cd.cod_disciplina NOT IN ( SELECT DISTINCT cod_disciplina
    FROM matricula_disciplina WHERE ra_matricula = @ra AND estado LIKE 'matriculado' OR estado LIKE 'APROVADO'
                                 OR estado LIKE 'dispensado')
    RETURN
END

-- SELECT * FROM fn_disciplinas_disponiveis('202325763')
