package edu.fatec.Avaliacao2_LBD.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Telefone
{
    private String numero;

    @Override
    public String toString() {
        return numero;
    }

    @Override
    public boolean equals(Object o) {
        Telefone telefone = (Telefone) o;

        return (numero.equals(telefone.numero));
    }
}
