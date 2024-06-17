package edu.fatec.Avaliacao2_LBD.persistence;

import edu.fatec.Avaliacao2_LBD.model.Aluno;
import edu.fatec.Avaliacao2_LBD.model.Curso;
import edu.fatec.Avaliacao2_LBD.model.Matricula;

import java.sql.*;

import org.springframework.stereotype.Repository;

@Repository
public class MatriculaDAO implements I_MatriculaDAO
{
    GenericDAO gdao;

    public MatriculaDAO(GenericDAO gdao) {
        this.gdao = gdao;
    }

    public Matricula findCpf(Matricula matricula) throws SQLException, ClassNotFoundException
    {
        Connection c = gdao.getConnection();
        String query = "SELECT m.ra, m.cpf_aluno as cpf, a.nome as nome, m.cod_curso as cod_curso, m.pontuacao_vestibular as pont_vest, " +
                "m.posicao_vestibular as pos_vest, m.ano_ingresso as a_ingresso, m.semestre_ingresso as s_ingresso, " +
                "m.ano_limite_graduacao as a_limite, m.semestre_limite_graduacao as s_limite, " +
                "m.matricula_ativa as ativa " +
                "FROM matricula m, aluno a " +
                "WHERE m.cpf_aluno = a.cpf AND m.cpf_aluno = ? ";
        PreparedStatement ps = c.prepareStatement(query);
        ps.setString(1, matricula.getAluno().getCpf());
        ResultSet rs = ps.executeQuery();
        if (rs.next())
        {
            matricula.setRa(rs.getString("ra"));
            matricula.setPontuacao_vestibular(rs.getInt("pont_vest"));
            matricula.setPosicao_vestibular(rs.getInt("pos_vest"));
            matricula.setAno_ingresso(rs.getInt("a_ingresso"));
            matricula.setSemestre_ingresso(rs.getInt("s_ingresso"));
            matricula.setAno_limite(rs.getInt("a_limite"));
            matricula.setSemestre_limite(rs.getInt("s_limite"));
            matricula.setMatricula_ativa(rs.getBoolean("ativa"));

            Curso curso = new Curso();
            curso.setCodigo(rs.getInt("cod_curso"));
            CursoDAO cursoDAO = new CursoDAO(gdao);
            matricula.setCurso(cursoDAO.find(curso));
        }
        return matricula;
    }

    public Matricula findRa(Matricula matricula) throws SQLException, ClassNotFoundException
    {
        Connection c = gdao.getConnection();
        String query = "SELECT m.ra, m.cpf_aluno as cpf, a.nome as nome, m.cod_curso as cod_curso, m.pontuacao_vestibular as pont_vest, " +
                "m.posicao_vestibular as pos_vest, m.ano_ingresso as a_ingresso, m.semestre_ingresso as s_ingresso, " +
                "m.ano_limite_graduacao as a_limite, m.semestre_limite_graduacao as s_limite, " +
                "m.matricula_ativa as ativa " +
                "FROM matricula m, aluno a " +
                "WHERE m.cpf_aluno = a.cpf AND m.ra = ? ";
        PreparedStatement ps = c.prepareStatement(query);
        ps.setString(1, matricula.getRa());
        ResultSet rs = ps.executeQuery();
        if (rs.next())
        {
            matricula.setRa(rs.getString("ra"));
            matricula.setPontuacao_vestibular(rs.getInt("pont_vest"));
            matricula.setPosicao_vestibular(rs.getInt("pos_vest"));
            matricula.setAno_ingresso(rs.getInt("a_ingresso"));
            matricula.setSemestre_ingresso(rs.getInt("s_ingresso"));
            matricula.setAno_limite(rs.getInt("a_limite"));
            matricula.setSemestre_limite(rs.getInt("s_limite"));
            matricula.setMatricula_ativa(rs.getBoolean("ativa"));

            Aluno aluno = new Aluno();
            aluno.setCpf(rs.getString("cpf"));
            AlunoDAO alunoDAO = new AlunoDAO(gdao);
            matricula.setAluno(alunoDAO.find(aluno));

            Curso curso = new Curso();
            curso.setCodigo(rs.getInt("cod_curso"));
            CursoDAO cursoDAO = new CursoDAO(gdao);
            matricula.setCurso(cursoDAO.find(curso));
        }
        return matricula;
    }

    public String insert(Matricula matricula) throws SQLException, ClassNotFoundException {
        return iud_matricula("I", matricula);
    }

    public String update(Matricula matricula) throws SQLException, ClassNotFoundException {
        return iud_matricula("U", matricula);
    }

    public String disable(Matricula matricula) throws SQLException, ClassNotFoundException {
        return iud_matricula("D", matricula);
    }

    public String enable(Matricula matricula) throws SQLException, ClassNotFoundException {
        return iud_matricula("A", matricula);
    }

    private String iud_matricula(String modo, Matricula matricula) throws SQLException, ClassNotFoundException
    {
        Connection con = gdao.getConnection();
        String query = "{ CALL sp_uid_matricula(?, ?, ?, ?, ?, ?, ?, ?, ?) }";
        CallableStatement cs = con.prepareCall(query);
        cs.setString(1, modo);
        cs.setString(2, matricula.getRa());
        cs.setString(3, matricula.getAluno().getCpf());
        cs.setInt(4, matricula.getCurso().getCodigo());
        cs.setInt(5, matricula.getPontuacao_vestibular());
        cs.setInt(6, matricula.getPosicao_vestibular());
        cs.setInt(7, matricula.getAno_ingresso());
        cs.setInt(8, matricula.getSemestre_ingresso());
        cs.registerOutParameter(9, Types.VARCHAR);
        cs.execute();
        String saida = cs.getString(9);

        cs.close();
        con.close();
        return saida;
    }
}
