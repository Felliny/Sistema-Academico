USE Avaliacao_2_Lab_BD

GO

/*
    DROP FUNCTION fn_buscar_disciplinas_pra_historico
*/

CREATE FUNCTION fn_buscar_disciplinas_pra_historico(@ra VARCHAR(50))
RETURNS @table TABLE
(
    codigo INT,
    nome_disc VARCHAR(100),
    nome_professor VARCHAR(100),
    nota_final VARCHAR(10),
    faltas INT
)
BEGIN

    INSERT INTO @table (codigo, nome_disc, nome_professor, nota_final, faltas)
        SELECT DISTINCT d.codigo AS codigo_disciplina, d.nome AS nome_disciplina, prof.nome AS nome_professor,
        CASE WHEN (md.estado LIKE 'DISPENSADO') THEN 'D'
        ELSE CAST(dbo.fn_calcular_nota(n.nota_1, n.nota_2, n.nota_3) AS VARCHAR(100)) END AS nota_final,
        SUM(CASE WHEN prec.aula_1 = 0 THEN 1 ELSE 0 END) + SUM(CASE WHEN prec.aula_2 = 0 THEN 1 ELSE 0 END) +
        SUM(CASE WHEN prec.aula_3 = 0 THEN 1 ELSE 0 END) + SUM(CASE WHEN prec.aula_4 = 0 THEN 1 ELSE 0 END) AS faltas
        FROM matricula_disciplina md INNER JOIN nota n ON n.id_matricula_disc = md.id
        INNER JOIN disciplina d ON d.codigo = md.cod_disciplina
        INNER JOIN professor prof ON prof.id = d.id_professor
        LEFT JOIN presenca prec ON prec.id_matricula_disc = md.id
        WHERE (md.estado LIKE 'APROVADO' OR md.estado LIKE 'DISPENSADO') AND md.ra_matricula = @ra
        GROUP BY d.codigo, d.nome, prof.nome, n.nota_1, n.nota_2, n.nota_3, md.estado

    RETURN

END

-- 202321485 : ADS ; 202325763 : COMEX
-- SELECT * FROM fn_buscar_disciplinas_pra_historico('202321485')
