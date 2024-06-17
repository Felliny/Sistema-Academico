package edu.fatec.Avaliacao2_LBD.persistence;

import edu.fatec.Avaliacao2_LBD.model.Horario;
import edu.fatec.Avaliacao2_LBD.model.Matricula;
import edu.fatec.Avaliacao2_LBD.model.MatriculaDisciplina;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface I_MatriculaDisciplinaDAO
{
    public String insert (Matricula matricula, MatriculaDisciplina matriculaDisciplina)
            throws SQLException, ClassNotFoundException;
    public String update(Matricula matricula, MatriculaDisciplina matriculaDisciplina)
            throws SQLException, ClassNotFoundException;
    public List<Horario> list_horarios_disponiveis(String ra, int dia, String carga_disciplina)
            throws SQLException, ClassNotFoundException;
    List<MatriculaDisciplina> list_disciplinas_disponiveis(String ra)
            throws SQLException, ClassNotFoundException;
    public List<MatriculaDisciplina> list_disciplinas_matriculadas(String ra)
            throws SQLException, ClassNotFoundException;

}
