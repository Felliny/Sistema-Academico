package edu.fatec.Avaliacao2_LBD.controller;

import edu.fatec.Avaliacao2_LBD.SpringServlet;
import edu.fatec.Avaliacao2_LBD.model.Conteudo;
import edu.fatec.Avaliacao2_LBD.model.Curso;
import edu.fatec.Avaliacao2_LBD.model.Disciplina;
import edu.fatec.Avaliacao2_LBD.model.Presenca;
import edu.fatec.Avaliacao2_LBD.persistence.GenericDAO;
import edu.fatec.Avaliacao2_LBD.persistence.PresencaDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class ManterPresencaController {

    @Autowired
    GenericDAO gdao;
    @Autowired
    PresencaDAO presencaDAO;


    List<Curso> cursos= new ArrayList<>();
    List<Disciplina> disciplinas= new ArrayList<>();
    List<Conteudo> conteudos= new ArrayList<>();
    List<Presenca> chamada= new ArrayList<>();
    Boolean editarChamada= false;


    @RequestMapping(name = "manter_chamada", value = "/manter_chamada", method = RequestMethod.GET)
    public ModelAndView doGet(@RequestParam Map<String, String> allRequestParams, ModelMap model) {
        String id_conteudo = allRequestParams.get("id");
        String cmd = allRequestParams.get("acao");


        String erro = "";
        String saida = "";
        Presenca presenca = new Presenca();
        chamada = null;

        try {
            cursos = presencaDAO.listCurso();

            if (id_conteudo != null & cmd != null) {
                presenca.setId_conteudo(Integer.parseInt(id_conteudo));
                if (cmd.equalsIgnoreCase("Editar")){
                    chamada = presencaDAO.consultarChamada(presenca);
                    editarChamada = true;
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            erro = e.getMessage();
        } finally {
            model.addAttribute("chamada", chamada);
            model.addAttribute("cursos", cursos);
            model.addAttribute("erro", erro);
            model.addAttribute("saida", saida);
        }


        return new ModelAndView("manter_chamada");
    }


    @RequestMapping(name = "manter_chamada", value = "/manter_chamada", method = RequestMethod.POST)
    public ModelAndView doPost(@RequestParam Map<String, String> allRequestParams, ModelMap model) {
        String botao = allRequestParams.get("botao");
        String curso_codigo = allRequestParams.get("curso");
        String disciplina= allRequestParams.get("disciplina");
        String conteudo= allRequestParams.get("conteudo");


        String erro = "";
        String saida = "";
        Curso c = new Curso();
        Disciplina d = new Disciplina();
        Presenca p= new Presenca();
        List<Presenca> presencas = new ArrayList<>();


        try {
            // Carregar combobox de CURSO
            cursos = presencaDAO.listCurso();
            if (curso_codigo != null){
                c.setCodigo(Integer.parseInt(curso_codigo));
                disciplinas = presencaDAO.listDisciplina(c);
                saida = "Disciplinas Carregadas";
            }


            if (botao != null){
                if (botao.equals("Adicionar Chamada")){
                    editarChamada= false;
                    chamada = null;
                    if (disciplina != null){
                        d.setCodigo(Integer.parseInt(disciplina));

                        conteudos = presencaDAO.listConteudo(d);
                        if (!conteudos.isEmpty()){
                            chamada = presencaDAO.gerarChamada(d);
                            if (chamada.isEmpty()){
                                erro = "Nenhum aluno encontrado";
                            }
                            else {

                                for (Presenca presenca : chamada){
                                    presenca.setPresenca1(2);
                                    presenca.setPresenca2(2);
                                    presenca.setPresenca3(2);
                                    presenca.setPresenca4(2);
                                }

                                saida = "Chamada Adicionada";
                            }
                        }
                        else {
                            erro = "Disciplina não contém Conteúdo criado";
                        }


                    }
                    else {
                        erro = "Selecione uma disciplina";
                    }
                }
                if (botao.equals("Buscar Chamadas")){
                    chamada = null;
                    editarChamada= false;
                    if (disciplina != null){
                        d.setCodigo(Integer.parseInt(disciplina));
                        //Carregar chamada
                        presencas = presencaDAO.listPresenca(d);

                        if (!presencas.isEmpty()){
                            saida = "Chamadas carregadas";
                        }
                        else {
                            erro = "Disciplina não possui chamada criada";
                        }

                    }
                    else {
                        erro = "Selecione uma disciplina";
                    }
                }
                if (botao.equals("Salvar Chamada")) {
                    if (!editarChamada) {
                        if (chamada != null) {
                            if (!chamada.isEmpty()) {
                                if (conteudo != null){
                                    p = chamada.get(0);
                                    p.setId_conteudo(Integer.parseInt(conteudo));
                                    if (!presencaDAO.verificarChamadaExistente(p)) {
                                        for (Presenca presenca : chamada) {
                                            presenca.setId_conteudo(Integer.parseInt(conteudo));
                                        }

                                        lerPresencas(allRequestParams);
                                        presencaDAO.salvarChamada(chamada);
                                        chamada = null;
                                        saida = "Chamada salva com sucesso";
                                    }
                                    else {
                                        erro = "Já existe uma chamada criada nesse conteúdo";
                                        chamada = null;
                                    }
                                }
                                else {
                                    lerPresencas(allRequestParams);
                                    erro = "Selecione um conteúdo";
                                }

                            } else {
                                erro = "Adicione uma chamada";
                            }
                        } else {
                            erro = "A chamada esta vazia, adicione uma";
                        }
                    }
                    else {
                        erro = "Para salvar esta chamada pressione o botão 'Editar Chamada'";
                        lerPresencas(allRequestParams);
                    }
                }
                if (botao.equals("Editar Chamada")) {
                    if (editarChamada) {
                        if (chamada != null){
                            lerPresencas(allRequestParams);
                            presencaDAO.editarChamada(chamada);
                            saida = "Chamada editada com sucesso";
                            editarChamada = false;
                            chamada = null;
                        }
                    }
                    else {
                        if (chamada == null) {
                            erro = "Busque uma chamada e depois selecione a opção editar";
                        }
                        else {
                            erro = "Para salvar esta chamada pressione o botão 'Salvar Chamada'";
                            lerPresencas(allRequestParams);
                        }
                    }
                }
            }


        } catch (SQLException | ClassNotFoundException e) {
            erro = e.getMessage();
        }finally {
            model.addAttribute("conteudos", conteudos);
            model.addAttribute("chamada", chamada);
            model.addAttribute("cursos", cursos);
            model.addAttribute("disciplinas", disciplinas);
            model.addAttribute("presencas", presencas);
            model.addAttribute("curso", curso_codigo);
            model.addAttribute("erro", erro);
            model.addAttribute("saida", saida);
        }

        return new ModelAndView("manter_chamada");
    }

    /**
     * Atuliza a lista chamada com os checkbox checados na tela
     * @param allRequestParams componentes da tela
     */
    private void lerPresencas(@RequestParam Map<String, String> allRequestParams) {
        if (chamada != null){
            String presenca1;
            String presenca2;
            String presenca3;
            String presenca4;
            for (Presenca presenca : chamada) {
                int id_matricula = presenca.getId_matricula_disciplina();
                presenca1 = allRequestParams.get("presenca1" + id_matricula);
                presenca2 = allRequestParams.get("presenca2" + id_matricula);
                presenca3 = allRequestParams.get("presenca3" + id_matricula);
                presenca4 = allRequestParams.get("presenca4" + id_matricula);

                if (presenca.getNum_aulas() == 4) {
                    if (presenca1 != null) {
                        presenca.setPresenca1(1);
                    } else {
                        presenca.setPresenca1(0);
                    }
                    if (presenca2 != null) {
                        presenca.setPresenca2(1);
                    } else {
                        presenca.setPresenca2(0);
                    }
                    if (presenca3 != null) {
                        presenca.setPresenca3(1);
                    } else {
                        presenca.setPresenca3(0);
                    }
                    if (presenca4 != null) {
                        presenca.setPresenca4(1);
                    } else {
                        presenca.setPresenca4(0);
                    }
                } else {
                    if (presenca1 != null) {
                        presenca.setPresenca1(1);
                    } else {
                        presenca.setPresenca1(0);
                    }
                    if (presenca2 != null) {
                        presenca.setPresenca2(1);
                    } else {
                        presenca.setPresenca2(0);
                    }
                }
            }
        }
    }

}
