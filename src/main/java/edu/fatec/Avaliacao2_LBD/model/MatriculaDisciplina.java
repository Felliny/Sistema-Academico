package edu.fatec.Avaliacao2_LBD.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class MatriculaDisciplina
{
    private int id, ano, semestre, dia_semana;
    private Horario horario;
    private Disciplina disciplina;
    private String situacao;
    private String nota_final;
    private int total_faltas;
}
