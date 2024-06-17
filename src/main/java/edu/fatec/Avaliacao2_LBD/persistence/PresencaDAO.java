package edu.fatec.Avaliacao2_LBD.persistence;

import edu.fatec.Avaliacao2_LBD.model.Conteudo;
import edu.fatec.Avaliacao2_LBD.model.Curso;
import edu.fatec.Avaliacao2_LBD.model.Disciplina;
import edu.fatec.Avaliacao2_LBD.model.Presenca;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PresencaDAO implements ICRUD<Presenca> {
    GenericDAO gdao;

    public PresencaDAO(GenericDAO gdao) {
        this.gdao = gdao;
    }


    @Override
    public String insert(Presenca presenca) throws SQLException, ClassNotFoundException {
        return "";
    }

    @Override
    public String update(Presenca presenca) throws SQLException, ClassNotFoundException {
        return "";
    }

    @Override
    public String delete(Presenca presenca) throws SQLException, ClassNotFoundException {
        return "";
    }

    @Override
    public Presenca find(Presenca presenca) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public List<Presenca> list() throws SQLException, ClassNotFoundException {

        return List.of();
    }

    public List<Curso> listCurso() throws SQLException, ClassNotFoundException {
        List<Curso> cursos = new ArrayList<>();

        Connection c= gdao.getConnection();
        String sql = """
                select c.codigo,
                       c.nome
                from curso c
                """;
        PreparedStatement ps = c.prepareStatement(sql);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Curso curso = new Curso();

            curso.setCodigo(rs.getInt(1));
            curso.setNome(rs.getString(2));

            cursos.add(curso);
        }

        return cursos;
    }

    public List<Disciplina> listDisciplina(Curso curso) throws SQLException, ClassNotFoundException {
        List<Disciplina> disciplinas = new ArrayList<>();

        Connection c= gdao.getConnection();
        String sql = """
                select d.codigo,
                       d.nome
                from disciplina d, curso c, curso_disciplina cd
                where cd.cod_disciplina = d.codigo and
                      cd.cod_curso = c.codigo and
                      c.codigo = ?
                order by d.nome
                """;
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setInt(1, curso.getCodigo());

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Disciplina disciplina = new Disciplina();

            disciplina.setCodigo(rs.getInt(1));
            disciplina.setNome(rs.getString(2));

            disciplinas.add(disciplina);
        }

        return disciplinas;
    }

    public List<Conteudo> listConteudo(Disciplina disciplina) throws SQLException, ClassNotFoundException {
        List<Conteudo> conteudos = new ArrayList<>();

        Connection c= gdao.getConnection();
        String sql= """
                select c.id,
                       c.titulo
                from conteudo c, disciplina d
                where c.cod_disciplina = d.codigo and
                      d.codigo = ?
                """;
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setInt(1, disciplina.getCodigo());

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Conteudo conteudo = new Conteudo();

            conteudo.setId(rs.getInt(1));
            conteudo.setTitulo(rs.getString(2));

            conteudos.add(conteudo);
        }

        return conteudos;
    }

    public List<Presenca> listPresenca(Disciplina disciplina) throws SQLException, ClassNotFoundException {
        List<Presenca> presencas = new ArrayList<>();

        Connection c= gdao.getConnection();
        String sql= """
                select convert(varchar, p.data, 103) as dataFomatada,
                       c.titulo,
                       p.id_conteudo
                from disciplina d, presenca p, conteudo c, matricula_disciplina md
                where c.id = p.id_conteudo and
                      d.codigo = c.cod_disciplina and
                      md.id = p.id_matricula_disc and
                      d.codigo = ?
                group by p.data, p.id_conteudo, c.titulo
                order by dataFomatada desc
                """;
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setInt(1, disciplina.getCodigo());

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Presenca presenca = new Presenca();

            presenca.setData(rs.getString("dataFomatada"));
            presenca.setConteudo_titulo(rs.getString("titulo"));
            presenca.setId_conteudo(rs.getInt("id_conteudo"));

            presencas.add(presenca);
        }

        return presencas;
    }

    public List<Presenca> consultarChamada(Presenca presenca) throws SQLException, ClassNotFoundException {
        List<Presenca> presencas = new ArrayList<>();

        Connection c= gdao.getConnection();
        String sql= """
                select a.nome,
                       p.aula_1,
                       p.aula_2,
                       p.aula_3,
                       p.aula_4,
                       c.id,
                       h.horario_inicio,\s
                       h.num_aulas,
                       p.id_matricula_disc
                from presenca p, conteudo c, matricula_disciplina md, matricula m, aluno a, horario h
                where c.id = p.id_conteudo and
                      p.id_matricula_disc = md.id and
                      md.ra_matricula = m.ra and
                      m.cpf_aluno = a.cpf and
                      h.id = md.id_horario and
                      p.id_conteudo = ?
                """;
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setInt(1, presenca.getId_conteudo());

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Presenca presenca1 = new Presenca();

            presenca1.setNome_aluno(rs.getString("nome"));
            presenca1.setPresenca1(rs.getInt(2));
            presenca1.setPresenca2(rs.getInt(3));
            presenca1.setPresenca3(rs.getInt(4));
            presenca1.setPresenca4(rs.getInt(5));
            presenca1.setId_conteudo(rs.getInt(6));
            presenca1.setHorario_inicio(rs.getTime(7));
            presenca1.setNum_aulas(rs.getInt(8));
            presenca1.setId_matricula_disciplina(rs.getInt(9));

            presencas.add(presenca1);
        }

        return presencas;
    }

    public void editarChamada(List<Presenca> presencas) throws SQLException, ClassNotFoundException {
        Connection c= gdao.getConnection();

        for (Presenca presenca : presencas) {
            String sql= """
                update presenca
                set aula_1 = ?, aula_2 = ?, aula_3 = ?, aula_4 = ?
                where id_matricula_disc = ? and
                      id_conteudo = ?
                """;
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, presenca.getPresenca1());
            ps.setInt(2, presenca.getPresenca2());
            ps.setInt(3, presenca.getPresenca3());
            ps.setInt(4, presenca.getPresenca4());
            ps.setInt(5, presenca.getId_matricula_disciplina());
            ps.setInt(6, presenca.getId_conteudo());

            ps.executeUpdate();
        }
    }

    public List<Presenca> gerarChamada(Disciplina disciplina) throws SQLException, ClassNotFoundException {
        List<Presenca> presencas = new ArrayList<>();

        Connection c= gdao.getConnection();
        String sql= """
                select a.nome,
                       h.num_aulas,
                       h.horario_inicio,
                       md.id,
                       d.codigo
                from matricula_disciplina md, disciplina d, aluno a, matricula m, horario h
                where
                    md.cod_disciplina = d.codigo and
                    md.ra_matricula = m.ra and
                    m.cpf_aluno = a.cpf and
                    md.estado = 'MATRICULADO' and
                    h.id = md.id_horario and
                    md.cod_disciplina = ?
                group by a.nome, h.num_aulas, md.id, h.horario_inicio, d.codigo
                """;
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setInt(1, disciplina.getCodigo());

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Presenca presenca = new Presenca();

            presenca.setNome_aluno(rs.getString("nome"));
            presenca.setNum_aulas(rs.getInt("num_aulas"));
            presenca.setHorario_inicio(rs.getTime(3));
            presenca.setId_matricula_disciplina(rs.getInt("id"));
            presenca.setId_disciplina(rs.getInt(5));

            presencas.add(presenca);
        }

        return presencas;
    }

    public Boolean verificarChamadaExistente(Presenca presenca) throws SQLException, ClassNotFoundException {
        Connection c= gdao.getConnection();
        String sql = """
                select a.nome
                from matricula_disciplina md, aluno a, matricula m, horario h,
                     conteudo c, presenca p, disciplina d
                where
                    d.codigo = md.cod_disciplina and
                    c.cod_disciplina = d.codigo and
                    p.id_matricula_disc = md.id and
                    md.ra_matricula = m.ra and
                    m.cpf_aluno = a.cpf and
                    h.id = md.id_horario and
                    p.id_conteudo = c.id and
                    m.matricula_ativa = 1 and
                    md.cod_disciplina = ? and
                    c.id = ?
                group by a.nome
                """;
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setInt(1, presenca.getId_disciplina());
        ps.setInt(2, presenca.getId_conteudo());

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return true;
        }


        return false;
    }

    public void salvarChamada(List<Presenca> chamada) throws SQLException, ClassNotFoundException {
        Connection c= gdao.getConnection();

        String sqlPresenca = """
                insert into presenca (id_matricula_disc, id_conteudo, aula_1, aula_2, aula_3, aula_4, data)
                values
                    (?, ?, ?, ?, ?, ?, getdate())
                """;
        PreparedStatement psPresenca = c.prepareStatement(sqlPresenca);

        for (Presenca p : chamada) {
            psPresenca.setInt(1, p.getId_matricula_disciplina());
            psPresenca.setInt(2, p.getId_conteudo());
            psPresenca.setInt(3, p.getPresenca1());
            psPresenca.setInt(4, p.getPresenca2());
            psPresenca.setInt(5, p.getPresenca3());
            psPresenca.setInt(6, p.getPresenca4());

            psPresenca.addBatch();
        }
        psPresenca.executeBatch();
    }
}
