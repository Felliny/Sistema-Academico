package edu.fatec.Avaliacao2_LBD.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Curso
{
    private int codigo, carga_horaria, nota_enade;
    private String nome, sigla, turno;
    private List<Disciplina> disciplinas;
}
