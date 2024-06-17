package edu.fatec.Avaliacao2_LBD.controller;

import java.sql.Date;
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

import edu.fatec.Avaliacao2_LBD.model.Aluno;
import edu.fatec.Avaliacao2_LBD.model.Curso;
import edu.fatec.Avaliacao2_LBD.model.Matricula;
import edu.fatec.Avaliacao2_LBD.model.Telefone;
import edu.fatec.Avaliacao2_LBD.persistence.AlunoDAO;
import edu.fatec.Avaliacao2_LBD.persistence.CursoDAO;
import edu.fatec.Avaliacao2_LBD.persistence.GenericDAO;
import edu.fatec.Avaliacao2_LBD.persistence.MatriculaDAO;
import edu.fatec.Avaliacao2_LBD.persistence.TelefoneDAO;
import edu.fatec.Avaliacao2_LBD.utils.MatriculaAlunoBuilder;

@Controller
public class ManterAlunoController 
{
	@Autowired
	GenericDAO gdao;
	@Autowired
    CursoDAO cursoDAO;
	@Autowired
	MatriculaDAO matriculaDAO;
	@Autowired
	TelefoneDAO telefoneDAO;
	@Autowired
	AlunoDAO alunoDAO;
	
	@RequestMapping(name = "manter_aluno", value = "/manter_aluno", method = RequestMethod.GET)
    public ModelAndView doGet(ModelMap model)
    {
		String erro = "";
        String saida = "";

        List<Curso> cursos = new ArrayList<>();

        try
        {
            cursos = buscarCursos();
        }
        catch (SQLException | ClassNotFoundException e)
        {
            erro = e.getMessage();
        }
        finally
        {
            model.addAttribute("cursos", cursos);
            model.addAttribute("saida", saida);
            model.addAttribute("erro", erro);
        }
		
        return new ModelAndView("secretaria_manter_aluno");
    }

    @RequestMapping(name = "manter_aluno", value = "/manter_aluno", method = RequestMethod.POST)
    public ModelAndView doPost(@RequestParam Map<String, String> allRequestParam, @RequestParam("telefones") String[] jsp_telefones, ModelMap model)
    {
    	String cmd = allRequestParam.get("botao") == null ? "" : allRequestParam.get("botao");
        String cpf = allRequestParam.get("cpf") == null ? "" : allRequestParam.get("cpf");
        String nome = allRequestParam.get("nome") == null ? "" : allRequestParam.get("nome");
        String nome_soc = allRequestParam.get("nome_soc") == null ? "" : allRequestParam.get("nome_soc");
        String dt_nasc = allRequestParam.get("dt_nasc") == null ? "" : allRequestParam.get("dt_nasc");
        String email_p = allRequestParam.get("email_p") == null ? "" : allRequestParam.get("email_p");
        String email_c = allRequestParam.get("email_c") == null ? "" : allRequestParam.get("email_c");
        String dt_seg_grau = allRequestParam.get("dt_seg_grau") == null ? "" : allRequestParam.get("dt_seg_grau");
        String inst_seg_grau = allRequestParam.get("inst_seg_grau") == null ? "" : allRequestParam.get("inst_seg_grau");
        String pontuacao_vest = allRequestParam.get("pontuacao_vest") == null ? "" : allRequestParam.get("pontuacao_vest");
        String posicao_vest = allRequestParam.get("posicao_vest") == null ? "" : allRequestParam.get("posicao_vest");
        String cod_curso = allRequestParam.get("curso") == null ? "" : allRequestParam.get("curso");
        String turno = allRequestParam.get("turno") == null ? "" : allRequestParam.get("turno");

        String ra = allRequestParam.get("ra") == null ? "" : allRequestParam.get("ra");
        String ano_i = allRequestParam.get("ano_i") == null ? "" : allRequestParam.get("ano_i");
        String semes_i = allRequestParam.get("semes_i") == null ? "" : allRequestParam.get("semes_i");
    	
        String erro = "";
        String saida = "";

        MatriculaAlunoBuilder builder = new MatriculaAlunoBuilder();
        Matricula matricula = new Matricula();
        List<Telefone> telefones = getTelefones(jsp_telefones);
        List<Curso> cursos = new ArrayList<>();
        Aluno aluno = new Aluno();
        
        try
        {
            cursos = buscarCursos();
            if (cmd.equalsIgnoreCase("Buscar"))
            {
                aluno.setCpf(cpf);
                aluno = buscarAluno(aluno);
                saida = "Nenhum aluno encontrado!";
                if (aluno.getCpf() != null)
                {
                    matricula.setAluno(aluno);
                    matricula = buscarMatricula(matricula);
                    saida = "";
                }
                telefones = aluno.getTelefones();
            }
            if (cmd.equalsIgnoreCase("Cadastrar") || cmd.equalsIgnoreCase("Alterar"))
            {
                Curso curso = new Curso();
                curso.setCodigo(Integer.parseInt(cod_curso));
                curso.setTurno(turno);

                Date dt_nascimento = dt_nasc.isEmpty() ? null:Date.valueOf(dt_nasc);
                Date dt_conclusao = dt_seg_grau.isEmpty() ? null:Date.valueOf(dt_seg_grau);
                builder
                        .addCpf(cpf).addNome(nome).addNome_social(nome_soc).addDt_nasc(dt_nascimento)
                        .addEmail_pessoal(email_p).addEmail_corporativo(email_c)
                        .addDt_conclusao_seg_grau(dt_conclusao)
                        .addInstituicao_seg_grau(inst_seg_grau)
                .addTelefones(telefones);
                aluno = builder.getAluno();

                builder.addAluno(aluno).addCurso(curso)
                        .addPontuacao_vestibular(Integer.parseInt(pontuacao_vest))
                        .addPosicao_vestibular(Integer.parseInt(posicao_vest));
                matricula = builder.getMatricula();
            }
            if (cmd.equalsIgnoreCase("Cadastrar"))
            {
                saida = cadastarMatricula(aluno, matricula);
                aluno = new Aluno();
                matricula = new Matricula();
                telefones = new ArrayList<>();
            }
            if (cmd.equalsIgnoreCase("Alterar"))
            {
                int ano_ingresso = ano_i.isEmpty()? -1:Integer.parseInt(ano_i);
                int semestre_ingresso = semes_i.isEmpty()? -1: Integer.parseInt(semes_i);
                builder.addRa(ra).addAno_ingresso(ano_ingresso).addSemestre_ingresso(semestre_ingresso);
                saida = alterarMatricula(aluno, matricula);
                aluno = new Aluno();
                matricula = new Matricula();
                telefones = new ArrayList<>();
            }
            if (cmd.equalsIgnoreCase("Desativar Matricula"))
            {
                matricula.setRa(ra);
                matricula.setAluno(aluno);
                matricula.setCurso(new Curso());
                saida = desativarMatricula(matricula);
                aluno = new Aluno();
                matricula = new Matricula();
                telefones = new ArrayList<>();
            }
            if (cmd.equalsIgnoreCase("Ativar Matricula"))
            {
                matricula.setRa(ra);
                matricula.setAluno(aluno);
                matricula.setCurso(new Curso());
                saida = ativarMatricula(matricula);
                aluno = new Aluno();
                matricula = new Matricula();
                telefones = new ArrayList<>();
            }
            if (cmd.equalsIgnoreCase("Novo Cadastro"))
            {
                aluno = new Aluno();
                matricula = new Matricula();
                telefones = new ArrayList<>();
            }
        }
        catch (SQLException | ClassNotFoundException | IllegalArgumentException e)
        {
            erro = e.getMessage();
            aluno = new Aluno();
            matricula = new Matricula();
            telefones = new ArrayList<>();
        }
        finally
        {
            model.addAttribute("cursos", cursos);
            model.addAttribute("telefones", telefones);
            model.addAttribute("aluno", aluno);
            model.addAttribute("matricula", matricula);

            model.addAttribute("saida", saida);
            model.addAttribute("erro", erro);
        }
    	
        return new ModelAndView("secretaria_manter_aluno");
    }
    
    private List<Telefone> getTelefones(String[] telefones_str) {
        List<Telefone> telefones = new ArrayList<>();
        if (telefones_str != null)
        {
            for (String telefone_str : telefones_str)
            {
            	if (telefone_str != "")
            	{
	                Telefone telefone = new Telefone(telefone_str);
	                telefones.add(telefone);
            	}
            }
        }
        return telefones;
    }

    private Aluno buscarAluno(Aluno aluno)
            throws SQLException, ClassNotFoundException
    {
        return alunoDAO.find(aluno);
    }

    private Matricula buscarMatricula(Matricula matricula)
            throws SQLException, ClassNotFoundException
    {
        return matriculaDAO.findCpf(matricula);
    }

    private String cadastarMatricula(Aluno aluno, Matricula matricula)
            throws SQLException, ClassNotFoundException
    {
        alunoDAO.insert(aluno);
        for (Telefone telefone : aluno.getTelefones())
            telefoneDAO.insert(aluno, telefone);
        return matriculaDAO.insert(matricula);
    }

    private String alterarMatricula(Aluno aluno, Matricula matricula)
            throws SQLException, ClassNotFoundException
    {
        alunoDAO.update(aluno);
        telefoneDAO.update(aluno);
        return matriculaDAO.update(matricula);
    }

    private String desativarMatricula(Matricula matricula)
            throws SQLException, ClassNotFoundException, NullPointerException
    {
        return matriculaDAO.disable(matricula);
    }

    private String ativarMatricula(Matricula matricula)
            throws SQLException, ClassNotFoundException
    {
        return matriculaDAO.enable(matricula);
    }

    private List<Curso> buscarCursos()
            throws SQLException, ClassNotFoundException
    {
        return cursoDAO.list();
    }
}
