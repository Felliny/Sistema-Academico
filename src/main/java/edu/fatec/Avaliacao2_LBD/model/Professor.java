package edu.fatec.Avaliacao2_LBD.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Professor 
{
	private int id;
	private String nome;
	
	@Override
	public String toString() {
		return this.nome;
	}
}
