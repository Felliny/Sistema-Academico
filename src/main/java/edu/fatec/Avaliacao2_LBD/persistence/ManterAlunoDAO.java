package edu.fatec.Avaliacao2_LBD.persistence;

import edu.fatec.Avaliacao2_LBD.model.Curso;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class ManterAlunoDAO {

    private GenericDAO gDAO;


    public List<Curso> getCursos() throws SQLException, ClassNotFoundException {
        List<Curso> cursos= new ArrayList<>();

        Connection c= gDAO.getConnection();

        String sql= """
                select curso.nome from curso
                """;
        PreparedStatement ps= c.prepareStatement(sql);
        ResultSet rs= ps.executeQuery();

        while (rs.next()){
            Curso curso= new Curso();

            curso.setNome(rs.getString(1));

            cursos.add(curso);
        }

        rs.close();
        ps.close();
        c.close();


        return cursos;
    }
}
