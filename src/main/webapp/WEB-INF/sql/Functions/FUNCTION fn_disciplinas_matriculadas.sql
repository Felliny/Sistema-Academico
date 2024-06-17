/*
USE Avaliacao_2_Lab_BD
*/

/*
DROP FUNCTION fn_disciplinas_matriculadas
*/

CREATE FUNCTION fn_disciplinas_matriculadas(@ra CHAR(9))
RETURNS @table TABLE (
    id INT,
    codigo INT,
    nome VARCHAR(50),
    numero_aulas INT,
    horario TIME,
    dia INT
)
BEGIN
    INSERT INTO @table
    SELECT md.id AS id, d.codigo AS cod, d.nome AS nome,
           d.horas_semanais AS carga_h, h.horario_inicio AS horario, md.dia_semana AS dia
    FROM matricula_disciplina md, disciplina d, horario h
    WHERE md.cod_disciplina = d.codigo AND md.id_horario = h.id AND
        ra_matricula = @ra AND estado LIKE 'matriculado'

    RETURN
END

-- SELECT * FROM fn_disciplinas_matriculadas('202211876') AS fn