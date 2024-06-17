package edu.fatec.Avaliacao2_LBD.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import edu.fatec.Avaliacao2_LBD.model.Disciplina;
import edu.fatec.Avaliacao2_LBD.model.Horario;
import edu.fatec.Avaliacao2_LBD.model.Matricula;
import edu.fatec.Avaliacao2_LBD.model.MatriculaDisciplina;
import edu.fatec.Avaliacao2_LBD.persistence.DisciplinaDAO;
import edu.fatec.Avaliacao2_LBD.persistence.GenericDAO;
import edu.fatec.Avaliacao2_LBD.persistence.MatriculaDAO;
import edu.fatec.Avaliacao2_LBD.persistence.MatriculaDisciplinaDAO;

@Controller
public class RealizarMatriculaController {
	@Autowired
	GenericDAO gdao;
	@Autowired
	DisciplinaDAO disciplinaDAO;
	@Autowired
	MatriculaDisciplinaDAO md_dao;
	@Autowired
	MatriculaDAO matriculaDAO;
	
	
	@RequestMapping(name = "realizar_matricula", value = "/realizar_matricula", method = RequestMethod.GET)
	public ModelAndView doGet(@RequestParam Map<String, String> allRequestParam, ModelMap model) {
		String ra = allRequestParam.get("ra") == null ? "" : allRequestParam.get("ra");
		String cod_disc = allRequestParam.get("cd") == null ? "" : allRequestParam.get("cd");
		String nome_disc = allRequestParam.get("n") == null ? "" : allRequestParam.get("n");
		String dia = allRequestParam.get("d") == null ? "" : allRequestParam.get("d");
		String acao = allRequestParam.get("acao") == null ? "" : allRequestParam.get("acao");
		String id_matricula = allRequestParam.get("id") == null ? "" : allRequestParam.get("id");

		String saida = "";
		String erro = "";
		String matricula_valida;
		String busca_valida = "";
		String carga_horaria;

		List<Horario> horarios = new ArrayList<>();
		Disciplina disciplina_selecionada = new Disciplina();
		Matricula matricula = new Matricula();
		List<MatriculaDisciplina> matriculasDisciplina = new ArrayList<>();
		matricula.setRa(ra);

		try {
			if (acao.equalsIgnoreCase("selecionar") || acao.equalsIgnoreCase("alterar"))
				busca_valida = "valido";
			if (acao.equalsIgnoreCase("selecionar")) {
				acao = "SELECIONAR";
				matriculasDisciplina = listarDisciplinasDisponiveis(ra);
				carga_horaria = "%";
				if (cod_disc != null && !cod_disc.isEmpty()) {
					disciplina_selecionada = buscarDisciplina(Integer.parseInt(cod_disc));
					carga_horaria = String.valueOf(disciplina_selecionada.getHoras_semanais());
				}
				horarios = listarHorariosDisponiveis(ra, Integer.parseInt(dia), carga_horaria);
				erro = horarios.isEmpty() ? "Não há horários disponives para " + get_dia_semana(Integer.parseInt(dia))
						: "";
			}
			if (acao.equalsIgnoreCase("alterar")) {
				acao = "ALTERAR";
				matriculasDisciplina = listarDisciplinasMatriculadas(ra);
				carga_horaria = "%";
				if (cod_disc != null && !cod_disc.isEmpty()) {
					disciplina_selecionada = buscarDisciplina(Integer.parseInt(cod_disc));
					carga_horaria = String.valueOf(disciplina_selecionada.getHoras_semanais());
				}
				horarios = listarHorariosDisponiveis(ra, Integer.parseInt(dia), carga_horaria);
				erro = horarios.isEmpty() ? "Não há horários disponives para " + get_dia_semana(Integer.parseInt(dia))
						: "";
			}
		} catch (SQLException | ClassNotFoundException e) {
			erro = e.getMessage();
		} finally {
			matricula_valida = !horarios.isEmpty() ? "valido" : "";
			model.addAttribute("matricula_valida", matricula_valida);
			model.addAttribute("busca_valida", busca_valida);
			model.addAttribute("horarios", horarios);
			model.addAttribute("matricula", matricula);
			model.addAttribute("id_matricula", id_matricula);
			model.addAttribute("cod_disc", cod_disc);
			model.addAttribute("nome_disc", nome_disc);
			model.addAttribute("dia", dia);
			model.addAttribute("saida", saida);
			model.addAttribute("erro", erro);
			model.addAttribute("acao", acao);
			model.addAttribute("matriculasDisciplina", matriculasDisciplina);
		}

		return new ModelAndView("aluno_realizar_matricula");
	}

	@RequestMapping(name = "realizar_matricula", value = "/realizar_matricula", method = RequestMethod.POST)
	public ModelAndView doPost(@RequestParam Map<String, String> allRequestParam, ModelMap model) {
		String cmd = allRequestParam.get("botao") == null ? "" : allRequestParam.get("botao");
		String ra = allRequestParam.get("ra") == null ? "" : allRequestParam.get("ra");
		String dia_semana = allRequestParam.get("dia_semana") == null ? "" : allRequestParam.get("dia_semana");
		// dados de matricula
		String id_matricula = allRequestParam.get("id_matricula") == null ? "" : allRequestParam.get("id_matricula");
		String cod_disc = allRequestParam.get("cod_disc") == null ? "" : allRequestParam.get("cod_disc");
		String nome_disc = allRequestParam.get("nome_disc") == null ? "" : allRequestParam.get("nome_disc");
		String horario = allRequestParam.get("horario") == null ? "" : allRequestParam.get("horario");

		String saida = "";
		String erro = "";
		String acao = allRequestParam.get("acao") == null ? "" : allRequestParam.get("acao");
		String busca_valida;

		List<MatriculaDisciplina> matriculasDisciplina = new ArrayList<>();
		Matricula matricula = new Matricula();

		try {
			if (!cmd.isEmpty()) {
				matricula.setRa(ra);
				matricula = buscarMatricula(matricula);
			}
			if (cmd.equalsIgnoreCase("Buscar")) {
				if (matricula.toString() == null)
					erro = "Matricula não encontrada";
				else
					saida = "Olá " + matricula.getAluno().getNome() + "!";
			}
			if (cmd.equalsIgnoreCase("Realizar Matricula")) {
				saida = realizarMatricula(matricula, cod_disc, horario, dia_semana);
				cod_disc = "";
				nome_disc = "";
				horario = "";
			}
			if (cmd.equalsIgnoreCase("Alterar Matricula")) {
				saida = alterarMatricula(matricula, id_matricula, cod_disc, horario, dia_semana);
				cod_disc = "";
				nome_disc = "";
				horario = "";
			}
			if (cmd.equalsIgnoreCase("Listar Disciplinas Disponiveis")) {
				matriculasDisciplina = listarDisciplinasDisponiveis(ra);
				acao = "SELECIONAR";
			}
			if (cmd.equalsIgnoreCase("Listar Disciplinas Matriculadas")) {
				matriculasDisciplina = listarDisciplinasMatriculadas(ra);
				acao = "ALTERAR";
			}
		} catch (SQLException | ClassNotFoundException e) {
			erro = e.getMessage();
		} finally {
			busca_valida = (dia_semana != null && matricula.getRa() != null) ? "valido" : "";

			model.addAttribute("saida", saida);
			model.addAttribute("erro", erro);
			model.addAttribute("acao", acao);
			model.addAttribute("busca_valida", busca_valida);
			model.addAttribute("matricula", matricula);
			model.addAttribute("matriculasDisciplina", matriculasDisciplina);
			// Tabela matricula
			model.addAttribute("cod_disc", cod_disc);
			model.addAttribute("nome_disc", nome_disc);
			model.addAttribute("horario", horario);
		}

		return new ModelAndView("aluno_realizar_matricula");
	}

	private String alterarMatricula(Matricula matricula, String idMatricula, String codDisc, String horario,
			String diaSemana) throws SQLException, ClassNotFoundException {
		Disciplina d = new Disciplina();
		d.setCodigo(Integer.parseInt(codDisc));
		Horario h = new Horario();
		h.setCodigo(horario);
		MatriculaDisciplina matriculaDisciplina = new MatriculaDisciplina();
		matriculaDisciplina.setId(Integer.parseInt(idMatricula));
		matriculaDisciplina.setDisciplina(d);
		matriculaDisciplina.setHorario(h);
		matriculaDisciplina.setDia_semana(Integer.parseInt(diaSemana));
		return md_dao.update(matricula, matriculaDisciplina);
	}

	private Matricula buscarMatricula(Matricula matricula) throws SQLException, ClassNotFoundException {
		return matriculaDAO.findRa(matricula);
	}

	private String realizarMatricula(Matricula matricula, String codDisc, String codHorario, String diaSemana)
			throws SQLException, ClassNotFoundException {
		Disciplina d = new Disciplina();
		d.setCodigo(Integer.parseInt(codDisc));
		Horario h = new Horario();
		h.setCodigo(codHorario);
		MatriculaDisciplina matriculaDisciplina = new MatriculaDisciplina();
		matriculaDisciplina.setDisciplina(d);
		matriculaDisciplina.setHorario(h);
		matriculaDisciplina.setDia_semana(Integer.parseInt(diaSemana));
		return md_dao.insert(matricula, matriculaDisciplina);
	}

	private List<MatriculaDisciplina> listarDisciplinasMatriculadas(String ra)
			throws SQLException, ClassNotFoundException {
		return md_dao.list_disciplinas_matriculadas(ra);
	}

	private List<MatriculaDisciplina> listarDisciplinasDisponiveis(String ra)
			throws SQLException, ClassNotFoundException {
		return md_dao.list_disciplinas_disponiveis(ra);
	}

	private List<Horario> listarHorariosDisponiveis(String ra, int diaSemana, String carga_horaria_disciplina)
			throws SQLException, ClassNotFoundException {
		return md_dao.list_horarios_disponiveis(ra, diaSemana, carga_horaria_disciplina);
	}

	private Disciplina buscarDisciplina(int cod_disciplina) throws SQLException, ClassNotFoundException {
		Disciplina disciplina = new Disciplina();
		disciplina.setCodigo(cod_disciplina);
		return disciplinaDAO.find(disciplina);
	}

	private String get_dia_semana(int dia_numerico) {
		return switch (dia_numerico) {
		case 2 -> "Segunda-Feira";
		case 3 -> "Terça-Feira";
		case 4 -> "Quarta-Feira";
		case 5 -> "Quinta-Feira";
		case 6 -> "Sexta-Feira";
		case 7 -> "Sábado";
		default -> "Domingo";
		};
	}
}
