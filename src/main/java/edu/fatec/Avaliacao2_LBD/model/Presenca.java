package edu.fatec.Avaliacao2_LBD.model;

import lombok.*;

import java.sql.Time;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Presenca {
    private int id_conteudo;
    private int id_disciplina;
    private int id_matricula_disciplina;
    private String nome_aluno;
    private int presenca1;
    private int presenca2;
    private int presenca3;
    private int presenca4;
    private String data;
    private int num_aulas;
    private Time horario_inicio;
    private String conteudo_titulo;
}
