package edu.fatec.Avaliacao2_LBD.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AreaAlunoController
{
	@RequestMapping(name = "area_aluno", value = "/area_aluno", method = RequestMethod.GET)
    public ModelAndView doGet(ModelMap model)
    {
        return new ModelAndView("area_aluno");
    }

    @RequestMapping(name = "area_aluno", value = "/area_aluno", method = RequestMethod.POST)
    public ModelAndView doPost(@RequestParam Map<String, String> allRequestParam, ModelMap model)
    {
        return new ModelAndView("area_aluno");
    }
}
