package edu.fatec.Avaliacao2_LBD.persistence;

import edu.fatec.Avaliacao2_LBD.model.Aluno;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class AlunoDAO implements ICRUD<Aluno>
{
    GenericDAO gdao;

    public AlunoDAO(GenericDAO gdao) {
        this.gdao = gdao;
    }

    @Override
    public String insert(Aluno aluno) throws SQLException, ClassNotFoundException {
        return iud_aluno("I", aluno);
    }

    @Override
    public String update(Aluno aluno) throws SQLException, ClassNotFoundException {
        return iud_aluno("U", aluno);
    }

    @Override
    public String delete(Aluno aluno) throws SQLException, ClassNotFoundException {
        return iud_aluno("D", aluno);
    }

    private String iud_aluno(String modo, Aluno aluno) throws SQLException, ClassNotFoundException
    {
        Connection con = gdao.getConnection();
        String query = "{ CALL sp_iud_aluno(?, ?, ?, ?, ?, ?, ?, ?, ?, ?) }";
        CallableStatement cs = con.prepareCall(query);
        cs.setString(1, modo);
        cs.setString(2, aluno.getCpf());
        cs.setString(3, aluno.getNome());
        cs.setString(4, aluno.getNome_social());
        cs.setDate(5, aluno.getDt_nasc());
        cs.setString(6, aluno.getEmail_pessoal());
        cs.setString(7, aluno.getEmail_corporativo());
        cs.setDate(8, aluno.getDt_conclusao_seg_grau());
        cs.setString(9, aluno.getInstituicao_seg_grau());
        cs.registerOutParameter(10, Types.VARCHAR);
        cs.execute();
        String saida = cs.getString(10);

        cs.close();
        con.close();
        return saida;
    }

    @Override
    public Aluno find(Aluno aluno) throws SQLException, ClassNotFoundException
    {
        Connection con = gdao.getConnection();
        String query = "SELECT cpf, nome, nome_social as nome_s, data_nasc as dt_nasc, email_pessoal as email_p, " +
                "email_corporativo as email_c, data_conclusao_seg_grau as dt_seg_grau, " +
                "instituicao_seg_grau as int_seg_grau " +
                "FROM aluno WHERE cpf=?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, aluno.getCpf());
        ResultSet rs = ps.executeQuery();
        aluno = new Aluno();

        if (rs.next())
        {
            aluno.setCpf(rs.getString("cpf"));
            aluno.setNome(rs.getString("nome"));
            aluno.setNome_social(rs.getString("nome_s"));
            aluno.setDt_nasc(rs.getDate("dt_nasc"));
            aluno.setEmail_pessoal(rs.getString("email_p"));
            aluno.setEmail_corporativo(rs.getString("email_c"));
            aluno.setDt_conclusao_seg_grau(rs.getDate("dt_seg_grau"));
            aluno.setInstituicao_seg_grau(rs.getString("int_seg_grau"));

            TelefoneDAO telefoneDAO = new TelefoneDAO(gdao);
            aluno.setTelefones(telefoneDAO.list(aluno));

        }
        return aluno;
    }

    @Override
    public List<Aluno> list() throws SQLException, ClassNotFoundException
    {
        Connection con = gdao.getConnection();
        String query = "SELECT cpf, nome, nome_social as nome_s, data_nasc as dt_nasc, email_pessoal as email_p, " +
                "email_corporativo as email_c, data_conclusao_seg_grau as dt_seg_grau, " +
                "instituicao_seg_grau as int_seg_grau " +
                "FROM aluno ";
        PreparedStatement ps = con.prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        List<Aluno> alunos = new ArrayList<>();
        while (rs.next())
        {
            Aluno aluno = new Aluno();
            aluno.setCpf(rs.getString("cpf"));
            aluno.setNome(rs.getString("nome"));
            aluno.setNome_social(rs.getString("nome_s"));
            aluno.setDt_nasc(rs.getDate("dt_nasc"));
            aluno.setEmail_pessoal(rs.getString("email_p"));
            aluno.setEmail_corporativo(rs.getString("email_c"));
            aluno.setDt_conclusao_seg_grau(rs.getDate("dt_seg_grau"));
            aluno.setInstituicao_seg_grau(rs.getString("int_seg_grau"));

            TelefoneDAO telefoneDAO = new TelefoneDAO(gdao);
            aluno.setTelefones(telefoneDAO.list(aluno));

            alunos.add(aluno);
        }
        return alunos;
    }
}
