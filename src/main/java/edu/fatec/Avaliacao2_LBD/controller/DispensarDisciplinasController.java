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

import edu.fatec.Avaliacao2_LBD.model.Matricula;
import edu.fatec.Avaliacao2_LBD.model.MatriculaDisciplina;
import edu.fatec.Avaliacao2_LBD.persistence.FuncionalidadesSecretariaDAO;
import edu.fatec.Avaliacao2_LBD.persistence.GenericDAO;

@Controller
public class DispensarDisciplinasController 
{
	@Autowired
	GenericDAO gdao;
	@Autowired
	FuncionalidadesSecretariaDAO fdao;
	
	@RequestMapping(name = "dispensar_disciplinas", value = "/dispensar_disciplinas", method = RequestMethod.GET)
    public ModelAndView doGet(@RequestParam Map<String, String> allRequestParam, ModelMap model)
    {
		String acao = allRequestParam.get("acao") == null ? "" : allRequestParam.get("acao");
    	String str_id = allRequestParam.get("id") == null ? "" : allRequestParam.get("id");
    	String ra = allRequestParam.get("ra") == null ? "" : allRequestParam.get("ra");
    	String str_cod = allRequestParam.get("cod") == null ? "" : allRequestParam.get("cod");
    	
    	String saida = "";
		String erro = "";
		
		Matricula matricula = new Matricula();
		List<MatriculaDisciplina> matDisciplinas = new ArrayList<MatriculaDisciplina>();
		
		try 
		{	
			if (acao.equalsIgnoreCase("dispensar") && !str_id.equalsIgnoreCase("") 
					&& !str_cod.equalsIgnoreCase("") && !ra.equalsIgnoreCase(""))
			{
				int id = Integer.parseInt(str_id);
				int codigo = Integer.parseInt(str_cod);
				saida = dispensarDisciplina(ra, id, codigo);
			}
			
			if (!ra.equalsIgnoreCase(""))
			{
				matricula = buscarAluno(ra);
				matDisciplinas = buscarDisciplinas(ra);
			}
		} 
		catch (SQLException | ClassNotFoundException e) 
		{
			erro = e.getMessage();
		}
		finally 
		{
            model.addAttribute("matricula", matricula);
            model.addAttribute("matDisciplinas", matDisciplinas);
            model.addAttribute("saida", saida);
            model.addAttribute("erro", erro);
		}
        
        return new ModelAndView("secretaria_dispensar_disciplina");
    }

	@RequestMapping(name = "dispensar_disciplinas", value = "/dispensar_disciplinas", method = RequestMethod.POST)
    public ModelAndView doPost(@RequestParam Map<String, String> allRequestParam, ModelMap model)
    {
    	String cmd = allRequestParam.get("botao") == null ? "" : allRequestParam.get("botao");
    	String ra = allRequestParam.get("ra") == null ? "" : allRequestParam.get("ra");
    	
    	String saida = "";
		String erro = "";
		
		Matricula matricula = new Matricula();
		List<MatriculaDisciplina> matDisciplinas = new ArrayList<MatriculaDisciplina>();
		
		try 
		{
			if (cmd.equalsIgnoreCase("Buscar"))
			{
				matricula = buscarAluno(ra);
				matDisciplinas = buscarDisciplinas(ra);
			}
		} 
		catch (SQLException | ClassNotFoundException e) 
		{
			erro = e.getMessage();
		}
		finally 
		{
            model.addAttribute("matricula", matricula);
            model.addAttribute("matDisciplinas", matDisciplinas);
            model.addAttribute("saida", saida);
            model.addAttribute("erro", erro);
		}
		
        return new ModelAndView("secretaria_dispensar_disciplina");
    }

    private String dispensarDisciplina(String ra, int id, int cod) throws SQLException, ClassNotFoundException {
		return fdao.dispensarDisciplina(ra, id, cod);
	}
    
	private List<MatriculaDisciplina> buscarDisciplinas(String ra) throws SQLException, ClassNotFoundException {
		return fdao.buscarDisciplinasParaDispensa(ra);
	}

	private Matricula buscarAluno(String ra) throws SQLException, ClassNotFoundException
    {
    	return fdao.buscarAluno(ra);
    }
}
