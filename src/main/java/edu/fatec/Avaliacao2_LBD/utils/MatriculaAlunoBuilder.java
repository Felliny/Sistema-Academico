package edu.fatec.Avaliacao2_LBD.utils;

import edu.fatec.Avaliacao2_LBD.model.Aluno;
import edu.fatec.Avaliacao2_LBD.model.Curso;
import edu.fatec.Avaliacao2_LBD.model.Matricula;
import edu.fatec.Avaliacao2_LBD.model.Telefone;
import lombok.Getter;

import java.sql.Date;
import java.util.List;

@Getter

public class MatriculaAlunoBuilder
{
    private Aluno aluno;
    private Matricula matricula;

    public MatriculaAlunoBuilder() {
        aluno = new Aluno();
        matricula = new Matricula();
    }

    public MatriculaAlunoBuilder addCpf(String cpf)
    {
        if (cpf == null || cpf.isEmpty())
            throw new IllegalArgumentException("CPF tem que ser preenchido!");
        this.aluno.setCpf(cpf);
        return this;
    }

    public MatriculaAlunoBuilder addNome(String nome)
    {
        if (nome == null || nome.isEmpty())
            throw new IllegalArgumentException("Nome tem que ser preenchido!");
        this.aluno.setNome(nome);
        return this;
    }

    public MatriculaAlunoBuilder addNome_social(String nome_social)
    {
        this.aluno.setNome_social(nome_social);
        return this;
    }

    public MatriculaAlunoBuilder addEmail_pessoal(String email_pessoal)
    {
        if (email_pessoal == null || email_pessoal.isEmpty())
            throw new IllegalArgumentException("E-mail Pessoal tem que ser preenchido!");
        this.aluno.setEmail_pessoal(email_pessoal);
        return this;
    }

    public MatriculaAlunoBuilder addEmail_corporativo(String email_corporativo)
    {
        if (email_corporativo == null || email_corporativo.isEmpty())
            throw new IllegalArgumentException("E-mail Corporativo tem que ser preenchido!");
        this.aluno.setEmail_corporativo(email_corporativo);
        return this;
    }

    public MatriculaAlunoBuilder addInstituicao_seg_grau(String instituicao_seg_grau)
    {
        if (instituicao_seg_grau == null || instituicao_seg_grau.isEmpty())
            throw new IllegalArgumentException("Nome da Instituição de Conclusão do 2º grau tem que ser preenchido!");
        this.aluno.setInstituicao_seg_grau(instituicao_seg_grau);
        return this;
    }

    public MatriculaAlunoBuilder addDt_nasc(Date dt_nasc)
    {
        if (dt_nasc == null)
            throw new IllegalArgumentException("Data de Nascimento tem que ser preenchido!");
        this.aluno.setDt_nasc(dt_nasc);
        return this;
    }

    public MatriculaAlunoBuilder addDt_conclusao_seg_grau(Date dt_conclusao_seg_grau)
    {
        if (dt_conclusao_seg_grau == null)
            throw new IllegalArgumentException("Data de conclusão do 2º grau tem que ser preenchido!");
        this.aluno.setDt_conclusao_seg_grau(dt_conclusao_seg_grau);
        return this;
    }

    public MatriculaAlunoBuilder addTelefones(List<Telefone> telefones)
    {
        if (telefones == null || telefones.isEmpty())
            throw new IllegalArgumentException("Deve haver, ao menos, 1 telefone!");
        this.aluno.setTelefones(telefones);
        return this;
    }

    public MatriculaAlunoBuilder addRa(String ra)
    {
        if (ra == null || ra.isEmpty())
            throw new IllegalArgumentException("RA tem que ser preenchido!");
        this.matricula.setRa(ra);
        return this;
    }

    public MatriculaAlunoBuilder addPontuacao_vestibular(int pontuacao_vestibular)
    {
        if (pontuacao_vestibular < 0)
            throw new IllegalArgumentException("Pontuação do Vestibular tem que ser preenchida!");
        this.matricula.setPontuacao_vestibular(pontuacao_vestibular);
        return this;
    }

    public MatriculaAlunoBuilder addPosicao_vestibular(int posicao_vestibular)
    {
        if (posicao_vestibular < 0)
            throw new IllegalArgumentException("Posição do Vestibular tem que ser preenchida!");
        if (posicao_vestibular < 1)
            throw new IllegalArgumentException("Posição do Vestibular inválida!");
        this.matricula.setPosicao_vestibular(posicao_vestibular);
        return this;
    }

    public MatriculaAlunoBuilder addAno_ingresso(int ano_ingresso)
    {
        if (ano_ingresso < 0)
            throw new IllegalArgumentException("Ano de ingresso tem que ser preenchido!");
        if (ano_ingresso < 1800)
            throw new IllegalArgumentException("Ano de ingresso inválido!");
        this.matricula.setAno_ingresso(ano_ingresso);
        return this;
    }

    public MatriculaAlunoBuilder addSemestre_ingresso(int semestre_ingresso)
    {
        if (semestre_ingresso < 0)
            throw new IllegalArgumentException("Semestre de ingresso tem que ser preenchido!");
        if (semestre_ingresso != 1 && semestre_ingresso != 2)
            throw new IllegalArgumentException("Semestre de ingresso inválido!");
        this.matricula.setSemestre_ingresso(semestre_ingresso);
        return this;
    }

    public MatriculaAlunoBuilder addAno_limite(int ano_limite)
    {
        if (ano_limite < 0)
            throw new IllegalArgumentException("Ano Limite tem que ser preenchido!");
        if (ano_limite < 1800)
            throw new IllegalArgumentException("Ano Limite inválido!");
        this.matricula.setAno_limite(ano_limite);
        return this;
    }

    public MatriculaAlunoBuilder addSemestre_limite(int semestre_limite)
    {
        if (semestre_limite < 0)
            throw new IllegalArgumentException("Semestre Limite tem que ser preenchido!");
        if (semestre_limite != 1 && semestre_limite != 2)
            throw new IllegalArgumentException("Semestre Limite inválido!");
        this.matricula.setSemestre_limite(semestre_limite);
        return this;
    }

    public MatriculaAlunoBuilder addMatricula_ativa(boolean matricula_ativa)
    {
        this.matricula.setMatricula_ativa(matricula_ativa);
        return this;
    }

    public MatriculaAlunoBuilder addAluno(Aluno aluno)
    {
        if (aluno == null || aluno.getCpf() == null)
            throw new IllegalArgumentException("Aluno tem que ser preenchido!");
        this.matricula.setAluno(aluno);
        return this;
    }

    public MatriculaAlunoBuilder addCurso(Curso curso)
    {
        if (curso == null)
            throw new IllegalArgumentException("Curso tem que ser preenchido!");
        this.matricula.setCurso(curso);
        return this;
    }
}
