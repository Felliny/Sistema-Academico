package edu.fatec.Avaliacao2_LBD.persistence;

import edu.fatec.Avaliacao2_LBD.model.Disciplina;
import edu.fatec.Avaliacao2_LBD.model.Horario;
import edu.fatec.Avaliacao2_LBD.model.MatriculaDisciplina;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class ConsultarDisciplinasDAO {


    private GenericDAO gDAO= new GenericDAO();


    public List<MatriculaDisciplina> getDiciplinas(String ra) throws SQLException, ClassNotFoundException {
        List<MatriculaDisciplina> disciplinas= new ArrayList<>();

        Connection c = gDAO.getConnection();

        String sql = """
                select disciplina.codigo,
                           disciplina.nome,
                           matricula_disciplina.estado,
                           horario.horario_inicio,
                           horario.horario_fim,
                           matricula_disciplina.dia_semana
                    from matricula_disciplina, disciplina, horario, matricula
                    where disciplina.codigo = matricula_disciplina.cod_disciplina and
                          horario.id = matricula_disciplina.id_horario and
                          matricula.ra = matricula_disciplina.ra_matricula and
                          matricula.ra = ?
                """;
        PreparedStatement ps= c.prepareStatement(sql);
        ps.setString(1, ra);
        ResultSet rs = ps.executeQuery();

        while (rs.next()){
            Disciplina disciplina= new Disciplina();
            Horario horario= new Horario();
            MatriculaDisciplina matriculaDisciplina= new MatriculaDisciplina();

            disciplina.setCodigo(rs.getInt(1));
            disciplina.setNome(rs.getString(2));
            matriculaDisciplina.setSituacao(rs.getString(3));
            horario.setHorario_inicio(rs.getTime(4));
            horario.setHorario_termino(rs.getTime(5));
            matriculaDisciplina.setDia_semana(rs.getInt(6));

            matriculaDisciplina.setDisciplina(disciplina);
            matriculaDisciplina.setHorario(horario);

            disciplinas.add(matriculaDisciplina);
        }
        rs.close();
        ps.close();
        c.close();

        return disciplinas;
    }


    public Boolean verificarRA(String ra) throws SQLException, ClassNotFoundException {
        Connection c= gDAO.getConnection();

        String sql = """
                select *
                    from matricula
                    where ra = ?
                """;
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setString(1, ra);
        ResultSet rs= ps.executeQuery();

        if (rs.next()){
            return true;
        }

        rs.close();
        ps.close();
        c.close();

        return false;
    }
}
