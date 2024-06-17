package edu.fatec.Avaliacao2_LBD.persistence;

import edu.fatec.Avaliacao2_LBD.model.Aluno;
import edu.fatec.Avaliacao2_LBD.model.Telefone;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Repository;

/*
Interface:
Create,
Update,
Delete,
and List
 */
@Repository
public interface I_TelefoneDAO
{
    public void insert(Aluno aluno, Telefone telefone) throws SQLException, ClassNotFoundException;
    public void update (Aluno aluno)	throws SQLException, ClassNotFoundException;
    public void delete(Aluno aluno, Telefone telefone) throws SQLException, ClassNotFoundException;
    public List<Telefone> list(Aluno aluno) throws SQLException, ClassNotFoundException;
}

