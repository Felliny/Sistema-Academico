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
import edu.fatec.Avaliacao2_LBD.persistence.FuncionalidadesSecretariaDAO;
import edu.fatec.Avaliacao2_LBD.persistence.GenericDAO;

@Controller
public class ConsultarAlunosController 
{
	@Autowired
	GenericDAO gdao;
	@Autowired
	FuncionalidadesSecretariaDAO fdao;
	
	@RequestMapping(name = "consultar_alunos", value = "/consultar_alunos", method = RequestMethod.GET)
    public ModelAndView doGet(ModelMap model)
    {
		String erro = "";
        String saida = "";

        List<Matricula> matriculas = new ArrayList<>();

        try
        {
        	matriculas = buscarAlunos("cpf", "%");
        }
        catch (SQLException | ClassNotFoundException e)
        {
            erro = e.getMessage();
        }
        finally
        {
            model.addAttribute("matriculas", matriculas);
            model.addAttribute("saida", saida);
            model.addAttribute("erro", erro);
        }
		
        return new ModelAndView("secretaria_consultar_alunos");
    }

	@RequestMapping(name = "consultar_alunos", value = "/consultar_alunos", method = RequestMethod.POST)
    public ModelAndView doPost(@RequestParam Map<String, String> allRequestParam, ModelMap model)
    {
		String cmd = allRequestParam.get("botao") == null ? "" : allRequestParam.get("botao");
		String tipo_busca = allRequestParam.get("tipo_busca") == null ? "" : allRequestParam.get("tipo_busca");
		String valor_busca = allRequestParam.get("valor_pesquisa") == null ? "" : allRequestParam.get("valor_pesquisa");
		
		String saida = "";
		String erro = "";
		List<Matricula> matriculas = new ArrayList<>();
		
		try {
			if (cmd.equalsIgnoreCase("Buscar")) {
				matriculas = buscarAlunos(tipo_busca, valor_busca);
			}
		} catch (SQLException | ClassNotFoundException e) {
			erro = e.getMessage();
		} finally {
            model.addAttribute("matriculas", matriculas);
			model.addAttribute("saida", saida);
			model.addAttribute("erro", erro);
		}
		
        return new ModelAndView("secretaria_consultar_alunos");
    }

    private List<Matricula> buscarAlunos(String tipo_busca, String valor) 
            throws SQLException, ClassNotFoundException
    {
		return fdao.buscarAlunos(tipo_busca, valor);
	}
}
