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
public class CursoDAO implements ICRUD<Curso>
{
    GenericDAO gdao;

    public CursoDAO(GenericDAO gdao)
    {
        this.gdao = gdao;
    }

    @Override
    public String insert(Curso curso) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public String update(Curso curso) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public String delete(Curso curso) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public Curso find(Curso curso) throws SQLException, ClassNotFoundException
    {
        Connection con = gdao.getConnection();
        String query = "SELECT codigo, nome, carga_horaria, sigla, nota_enade, turno " +
                "FROM curso WHERE codigo=?";

        PreparedStatement ps = con.prepareStatement(query);
        ps.setInt(1, curso.getCodigo());
        ResultSet rs = ps.executeQuery();

        curso = new Curso();
        if (rs.next())
        {
            curso.setCodigo(rs.getInt("codigo"));
            curso.setNome(rs.getString("nome"));
            curso.setCarga_horaria(rs.getInt("carga_horaria"));
            curso.setSigla(rs.getString("sigla"));
            curso.setNota_enade(rs.getInt("nota_enade"));
            curso.setTurno(rs.getString("turno"));
        }
        return curso;
    }

    public List<Curso> list ()
            throws SQLException, ClassNotFoundException
    {
        Connection con = gdao.getConnection();
        String query = "SELECT codigo, nome, carga_horaria, sigla, nota_enade, turno FROM curso";

        PreparedStatement ps = con.prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        List<Curso> cursos = new ArrayList<>();
        while (rs.next())
        {
            Curso curso = new Curso();
            curso.setCodigo(rs.getInt("codigo"));
            curso.setNome(rs.getString("nome"));
            curso.setCarga_horaria(rs.getInt("carga_horaria"));
            curso.setSigla(rs.getString("sigla"));
            curso.setNota_enade(rs.getInt("nota_enade"));
            curso.setTurno(rs.getString("turno"));
            cursos.add(curso);
        }
        return cursos;
    }
}
