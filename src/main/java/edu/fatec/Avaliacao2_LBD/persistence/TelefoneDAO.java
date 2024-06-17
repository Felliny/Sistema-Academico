package edu.fatec.Avaliacao2_LBD.persistence;

import edu.fatec.Avaliacao2_LBD.model.Aluno;
import edu.fatec.Avaliacao2_LBD.model.Telefone;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class TelefoneDAO implements I_TelefoneDAO
{
    GenericDAO gdao;

    public TelefoneDAO(GenericDAO gdao) {
        this.gdao = gdao;
    }

    public void insert(Aluno aluno, Telefone telefone) throws SQLException, ClassNotFoundException
    {
        id_telefone("I", aluno, telefone);
    }

    @Override
    public void update(Aluno aluno) throws SQLException, ClassNotFoundException
    {
        List<Telefone> telefones_bd = list(aluno);
        List<Telefone> delete = getNoContains(telefones_bd, aluno.getTelefones());
        List<Telefone> insert = getNoContains(aluno.getTelefones(), telefones_bd);

        if (!delete.isEmpty())
        {
            for (Telefone telefone : delete)
                delete(aluno, telefone);
        }
        if (!insert.isEmpty())
        {
            for (Telefone telefone : insert)
                insert(aluno, telefone);
        }
    }

    private List<Telefone> getNoContains(List<Telefone> listA, List<Telefone> listB)
    {
        List<Telefone> result = new ArrayList<>();

        for (Telefone telefone : listA)
        {
            if (!listB.contains(telefone))
                result.add(telefone);
        }

        return result;
    }

    public void delete(Aluno aluno, Telefone telefone) throws SQLException, ClassNotFoundException
    {
        id_telefone("D", aluno, telefone);
    }

    private String id_telefone(String modo, Aluno aluno, Telefone telefone) throws SQLException, ClassNotFoundException
    {
        Connection con = gdao.getConnection();
        String query = "{ CALL sp_id_telefone(?, ?, ?, ?) }";
        CallableStatement cs = con.prepareCall(query);
        cs.setString(1, modo);
        cs.setString(2, aluno.getCpf());
        cs.setString(3, telefone.getNumero());
        cs.registerOutParameter(4, Types.VARCHAR);
        cs.execute();
        String saida = cs.getString(4);

        cs.close();
        con.close();
        return saida;
    }

    public List<Telefone> list(Aluno aluno) throws SQLException, ClassNotFoundException
    {
        Connection con = gdao.getConnection();
        String query = "SELECT numero FROM telefone WHERE cpf_aluno = ?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, aluno.getCpf());
        ResultSet rs = ps.executeQuery();

        List<Telefone> telefones = new ArrayList<>();
        while (rs.next())
        {
            telefones.add(new Telefone(rs.getString("numero")));
        }
        return telefones;
    }
}
