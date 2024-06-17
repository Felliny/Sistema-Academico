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

import edu.fatec.Avaliacao2_LBD.model.MatriculaDisciplina;
import edu.fatec.Avaliacao2_LBD.persistence.ConsultarDisciplinasDAO;

@Controller
public class ConsultarDisciplinasController
{
	@Autowired
	ConsultarDisciplinasDAO consultarDisciplinasDAO;
	
	@RequestMapping(name = "consultar_disciplinas", value = "/consultar_disciplinas", method = RequestMethod.GET)
    public ModelAndView doGet(ModelMap model)
    {
        return new ModelAndView("aluno_consultar_disciplinas");
    }

    @RequestMapping(name = "consultar_disciplinas", value = "/consultar_disciplinas", method = RequestMethod.POST)
    public ModelAndView doPost(@RequestParam Map<String, String> allRequestParam, ModelMap model)
    {
    	String cmd = allRequestParam.get("botao");
        String ra = allRequestParam.get("ra");


//        Retorno
        String erro= "";
        List<MatriculaDisciplina> listaDisciplinas= new ArrayList<>();

        try {
            if (ra.length() != 9){
                erro = "RA inválido";
            }
            else if (!consultarDisciplinasDAO.verificarRA(ra)){
                erro = "Matrícula não Encontrada";
            }
            else {
                if (cmd.contains("Buscar")) {
                    listaDisciplinas = consultarDisciplinasDAO.getDiciplinas(ra);
                }
            }

        } catch (SQLException | ClassNotFoundException e){
            erro = "Erro em consultar disciplinas";
        } finally {
            model.addAttribute("listaDisciplinas", listaDisciplinas);

            if (listaDisciplinas.isEmpty() && erro.isEmpty()){
                erro = "O Aluno não possui disciplinas matriculadas";
            }

            model.addAttribute("erro", erro);
        }
    	
        return new ModelAndView("aluno_consultar_disciplinas");
    }
}
