package edu.fatec.Avaliacao2_LBD.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor

public class Matricula
{
    private String ra;
    private int pontuacao_vestibular, posicao_vestibular,
            ano_ingresso, semestre_ingresso, ano_limite, semestre_limite;
    private boolean matricula_ativa;
    private Aluno aluno;
    private Curso curso;
    private List<MatriculaDisciplina> matriculaDisciplinas;

    public Matricula() {
        posicao_vestibular = 1;
    }

    @Override
    public String toString() {
        return (this.ra);
    }
}
