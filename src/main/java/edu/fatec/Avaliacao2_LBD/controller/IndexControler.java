package edu.fatec.Avaliacao2_LBD.controller;

import java.util.Map;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.stereotype.Controller;

@Controller
public class IndexControler 
{
	@RequestMapping(name = "index", value = "/index", method = RequestMethod.GET)
    public ModelAndView doGet(ModelMap model)
    {
        return new ModelAndView("index");
    }

    @RequestMapping(name = "index", value = "/index", method = RequestMethod.POST)
    public ModelAndView doPost(@RequestParam Map<String, String> allRequestParam, ModelMap model)
    {
        return new ModelAndView("index");
    }
}
