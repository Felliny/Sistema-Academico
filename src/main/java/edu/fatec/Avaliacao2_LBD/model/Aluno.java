package edu.fatec.Avaliacao2_LBD.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Aluno
{
    private String cpf;
    private String nome;
    private String nome_social;
    private String email_pessoal;
    private String email_corporativo;
    private String instituicao_seg_grau;
    private Date   dt_nasc;
    private Date   dt_conclusao_seg_grau;
    private List<Telefone> telefones;
}
