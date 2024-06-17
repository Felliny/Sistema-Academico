package edu.fatec.Avaliacao2_LBD.persistence;

import edu.fatec.Avaliacao2_LBD.model.Matricula;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

/*
Interface:
Create,
Update,
Delete,
and Find
 */
@Repository
public interface I_MatriculaDAO
{
    public String insert (Matricula matricula)	throws SQLException, ClassNotFoundException;
    public String update (Matricula matricula)	throws SQLException, ClassNotFoundException;
    public String disable (Matricula matricula)    throws SQLException, ClassNotFoundException;
    public String enable (Matricula matricula)    throws SQLException, ClassNotFoundException;
    public Matricula findCpf(Matricula matricula)         throws SQLException, ClassNotFoundException;
    public Matricula findRa(Matricula matricula)         throws SQLException, ClassNotFoundException;
}
