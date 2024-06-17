package edu.fatec.Avaliacao2_LBD.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Horario
{
    private String codigo, turno;
    private Time horario_inicio, horario_termino;
    private int numero_aulas;
}
