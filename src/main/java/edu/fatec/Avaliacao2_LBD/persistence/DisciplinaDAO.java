package edu.fatec.Avaliacao2_LBD.persistence;

import edu.fatec.Avaliacao2_LBD.model.Disciplina;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class DisciplinaDAO implements ICRUD<Disciplina>
{
    GenericDAO gdao;

    public DisciplinaDAO(GenericDAO gdao) {
        this.gdao = gdao;
    }

    @Override
    public String insert(Disciplina disciplina) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public String update(Disciplina disciplina) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public String delete(Disciplina disciplina) throws SQLException, ClassNotFoundException {
        return null;
    }

    public Disciplina find(Disciplina disciplina) throws SQLException, ClassNotFoundException
    {
        Connection con = gdao.getConnection();
        String query = "SELECT codigo, nome, horas_semanais FROM disciplina WHERE codigo=?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setInt(1, disciplina.getCodigo());
        ResultSet rs = ps.executeQuery();
        if (rs.next())
        {
            disciplina.setCodigo(rs.getInt("codigo"));
            disciplina.setNome(rs.getString("nome"));
            disciplina.setHoras_semanais(rs.getInt("horas_semanais"));
        }

        return disciplina;
    }

    @Override
    public List<Disciplina> list() throws SQLException, ClassNotFoundException {
        return null;
    }

}
