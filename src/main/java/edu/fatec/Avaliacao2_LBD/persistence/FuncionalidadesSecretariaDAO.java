package edu.fatec.Avaliacao2_LBD.persistence;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import edu.fatec.Avaliacao2_LBD.model.Aluno;
import edu.fatec.Avaliacao2_LBD.model.Curso;
import edu.fatec.Avaliacao2_LBD.model.Disciplina;
import edu.fatec.Avaliacao2_LBD.model.Matricula;
import edu.fatec.Avaliacao2_LBD.model.MatriculaDisciplina;
import edu.fatec.Avaliacao2_LBD.model.Professor;

@Repository
public class FuncionalidadesSecretariaDAO {
	GenericDAO gdao;

	public FuncionalidadesSecretariaDAO(GenericDAO gdao) {
		this.gdao = gdao;
	}

	public List<Matricula> buscarAlunos(String tipo_busca, String valor_pesquisa)
			throws SQLException, ClassNotFoundException {
		Connection con = gdao.getConnection();
		String query = "SELECT f.cpf AS cpf, f.ra AS ra, f.nome_aluno AS nome_aluno, f.nome_curso AS nome_curso, f.dt_matricula AS dt_matricula, ";
		query += "f.pontuacao_vestibular AS pontuacao_vestibular, f.posicao_vestibular AS posicao_vestibular FROM fn_pesquisar_alunos (?, ?) AS f";

		PreparedStatement ps = con.prepareStatement(query);
		ps.setString(1, tipo_busca);
		ps.setString(2, valor_pesquisa);

		List<Matricula> matriculas = new ArrayList<Matricula>();
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			Curso curso = new Curso();
			Aluno aluno = new Aluno();
			Matricula matricula = new Matricula();

			aluno.setCpf(rs.getString("cpf"));
			aluno.setNome(rs.getString("nome_aluno"));
			aluno.setNome_social(rs.getString("nome_aluno"));

			curso.setNome(rs.getString("nome_curso"));

			matricula.setRa(rs.getString("ra"));
			String[] dt = rs.getString("dt_matricula").split("/");
			matricula.setAno_ingresso(Integer.parseInt(dt[0]));
			matricula.setSemestre_ingresso(Integer.parseInt(dt[1]));
			matricula.setPontuacao_vestibular(rs.getInt("pontuacao_vestibular"));
			matricula.setPosicao_vestibular(rs.getInt("posicao_vestibular"));
			matricula.setAluno(aluno);
			matricula.setCurso(curso);

			matriculas.add(matricula);
		}

		return matriculas;
	}

	public Matricula buscarAluno(String ra) throws SQLException, ClassNotFoundException {
		Connection con = gdao.getConnection();
		String query = "SELECT f.ra AS ra, f.nome_aluno AS nome_aluno, f.nome_curso AS nome_curso, f.dt_matricula AS dt_matricula,  ";
		query += "f.pontuacao_vestibular AS pontuacao_vestibular, f.posicao_vestibular AS posicao_vestibular FROM fn_dados_de_aluno (?) AS f";

		PreparedStatement ps = con.prepareStatement(query);
		ps.setString(1, ra);

		Curso curso = new Curso();
		Aluno aluno = new Aluno();
		Matricula matricula = new Matricula();
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {

			aluno.setNome(rs.getString("nome_aluno"));
			aluno.setNome_social(rs.getString("nome_aluno"));

			curso.setNome(rs.getString("nome_curso"));

			matricula.setRa(rs.getString("ra"));
			String[] dt = rs.getString("dt_matricula").split("/");
			matricula.setAno_ingresso(Integer.parseInt(dt[0]));
			matricula.setSemestre_ingresso(Integer.parseInt(dt[1]));
			matricula.setPontuacao_vestibular(rs.getInt("pontuacao_vestibular"));
			matricula.setPosicao_vestibular(rs.getInt("posicao_vestibular"));
			matricula.setAluno(aluno);
			matricula.setCurso(curso);
		}

		return matricula;
	}

	public List<MatriculaDisciplina> buscarDisciplinasParaHistorico(String ra)
			throws SQLException, ClassNotFoundException {
		Connection con = gdao.getConnection();
		String query = "SELECT f.codigo AS codigo, f.nome_disc AS nome_disc, f.nome_professor AS nome_professor,  ";
		query += "f.nota_final AS nota_final, f.faltas AS faltas FROM fn_buscar_disciplinas_pra_historico (?) AS f";

		PreparedStatement ps = con.prepareStatement(query);
		ps.setString(1, ra);

		List<MatriculaDisciplina> mDisciplinas = new ArrayList<>();
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			Disciplina disciplina = new Disciplina();
			Professor professor = new Professor();
			MatriculaDisciplina mDisc = new MatriculaDisciplina();

			professor.setNome(rs.getString("nome_professor"));
			;

			disciplina.setCodigo(rs.getInt("codigo"));
			disciplina.setNome(rs.getString("nome_disc"));
			disciplina.setProfessor(professor);

			mDisc.setNota_final(rs.getString("nota_final"));
			mDisc.setTotal_faltas(rs.getInt("faltas"));
			mDisc.setDisciplina(disciplina);

			mDisciplinas.add(mDisc);
		}

		return mDisciplinas;
	}

	public List<MatriculaDisciplina> buscarDisciplinasParaDispensa(String ra)
			throws SQLException, ClassNotFoundException {
		Connection con = gdao.getConnection();
		String query = "SELECT f.id_matricula AS id_matricula, f.codigo AS codigo, f.nome_disc AS nome_disc, f.nome_professor AS nome_professor, ";
		query += "f.num_aulas AS num_aulas, f.situacao AS situacao FROM fn_buscar_disciplinas_pra_dispensa (?) AS f";  

		PreparedStatement ps = con.prepareStatement(query);
		ps.setString(1, ra);

		List<MatriculaDisciplina> mDisciplinas = new ArrayList<>();
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			Disciplina disciplina = new Disciplina();
			Professor professor = new Professor();
			MatriculaDisciplina mDisc = new MatriculaDisciplina();

			professor.setNome(rs.getString("nome_professor"));

			disciplina.setCodigo(rs.getInt("codigo"));
			disciplina.setNome(rs.getString("nome_disc"));
			disciplina.setHoras_semanais(rs.getInt("num_aulas"));
			disciplina.setProfessor(professor);

			mDisc.setId(rs.getInt("id_matricula"));
			mDisc.setSituacao(rs.getString("situacao"));
			mDisc.setDisciplina(disciplina);

			mDisciplinas.add(mDisc);
		}

		return mDisciplinas;
	}

	public String dispensarDisciplina(String ra, int id, int codigo) throws SQLException, ClassNotFoundException 
	{
		Connection con = gdao.getConnection();
        String query = "{ CALL sp_dispensar_disciplina(?, ?, ?, ?) }";
        CallableStatement cs = con.prepareCall(query);
        cs.setString(1, ra);
        cs.setInt(2, id);
        cs.setInt(3, codigo);
        cs.registerOutParameter(4, Types.VARCHAR);
        cs.execute();
        String saida = cs.getString(4);

        cs.close();
        con.close();
		return saida;
	}

}
