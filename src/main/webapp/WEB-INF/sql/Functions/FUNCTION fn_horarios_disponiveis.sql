/*
USE Avaliacao_2_Lab_BD
*/

-- DROP FUNCTION fn_horarios_disponiveis
CREATE FUNCTION fn_horarios_disponiveis(@dia_semana INT, @ra CHAR(9), @carga_h_disciplina CHAR(1))
RETURNS @table TABLE
(
    id CHAR(5),
    horario_inicio TIME,
    numero_aulas INT,
    horario_termino TIME
)
BEGIN
    
    INSERT INTO @table
    SELECT id, horario_inicio, num_aulas, horario_fim FROM horario
    WHERE num_aulas LIKE @carga_h_disciplina AND
    horario.horario_inicio NOT IN ( SELECT DISTINCT h.horario_inicio FROM matricula_disciplina, horario h
    WHERE matricula_disciplina.id_horario = h.id AND ra_matricula = @ra AND estado LIKE 'matriculado' AND dia_semana = @dia_semana)
    AND horario.horario_inicio NOT IN (
    SELECT DISTINCT DATEADD(MINUTE, (55 * (h.num_aulas - 2)), h.horario_inicio) FROM matricula_disciplina, horario h
    WHERE matricula_disciplina.id_horario = h.id AND ra_matricula = @ra AND estado LIKE 'matriculado' AND dia_semana = @dia_semana)
    AND horario.horario_fim NOT IN (
    SELECT DISTINCT DATEADD(MINUTE, (55 * (h.num_aulas - 2)) - 10, h.horario_inicio) FROM matricula_disciplina, horario h
    WHERE matricula_disciplina.id_horario = h.id AND ra_matricula = @ra AND estado LIKE 'matriculado' AND dia_semana = @dia_semana)

    RETURN
END


-- SELECT * FROM fn_horarios_disponiveis (3, '202211589', '%')

