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

public class Disciplina
{
    private int codigo, horas_semanais;
    private String nome;
    private Professor professor;
    private List<Conteudo> conteudos;
}
