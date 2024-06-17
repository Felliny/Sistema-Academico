/*
USE master
GO
DROP DATABASE Avaliacao_2_Lab_BD
*/

/*
    CREATE DATABASE Avaliacao_2_Lab_BD
 GO
    USE Avaliacao_2_Lab_BD
*/

/*
DROP TABLE presenca; GO
DROP TABLE nota; GO
DROP TABLE matricula_disciplina; GO
DROP TABLE conteudo; GO
DROP TABLE curso_disciplina; GO
DROP TABLE disciplina; GO
DROP TABLE professor; GO
DROP TABLE horario; GO
DROP TABLE matricula; GO
DROP TABLE telefone; GO
DROP TABLE aluno; GO
DROP TABLE curso;
*/

CREATE TABLE aluno
(
    cpf CHAR(11) NOT NULL,
    nome VARCHAR(100) NOT NULL,
    nome_social VARCHAR(100),
    data_nasc DATE NOT NULL,
    email_pessoal VARCHAR(100) NOT NULL,
    email_corporativo VARCHAR(100) NOT NULL,
    data_conclusao_seg_grau DATE NOT NULL,
    instituicao_seg_grau VARCHAR(100) NOT NULL,

    PRIMARY KEY(cpf)
)

CREATE TABLE telefone
(
    cpf_aluno   CHAR(11),
    numero CHAR(12) NOT NULL,

    PRIMARY KEY (cpf_aluno, numero),
    FOREIGN KEY (cpf_aluno) REFERENCES aluno
)

CREATE TABLE professor
(
    id      INT NOT NULL,
    nome    VARCHAR(100) NOT NULL,

    PRIMARY KEY (id)
)

CREATE TABLE disciplina
(
    codigo INT NOT NULL,
    nome VARCHAR(100) NOT NULL,
    horas_semanais INT NOT NULL,
    periodo_recomendado INT NOT NULL,
    id_professor    INT NOT NULL,

    PRIMARY KEY(codigo),
    FOREIGN KEY (id_professor) REFERENCES professor
)


CREATE TABLE curso
(
    codigo INT NOT NULL,
    nome VARCHAR(40) NOT NULL,
    carga_horaria INT NOT NULL,
    sigla VARCHAR(10) NOT NULL,
    nota_enade INT NOT NULL,
    turno VARCHAR(10) NOT NULL,

    PRIMARY KEY(codigo)
)


CREATE TABLE conteudo
(
    id INT IDENTITY NOT NULL,
    cod_disciplina  INT         NOT NULL,
    titulo       VARCHAR(100) NOT NULL,

    PRIMARY KEY(id),
    FOREIGN KEY(cod_disciplina) REFERENCES disciplina
)


CREATE TABLE horario
(
    id CHAR(5) NOT NULL,
    horario_inicio TIME NOT NULL,
    horario_fim TIME NOT NULL,
    num_aulas    INT  NOT NULL,
    turno VARCHAR(10) NOT NULL,

    PRIMARY KEY(id)
)


CREATE TABLE curso_disciplina
(
    cod_disciplina  INT NOT NULL,
    cod_curso       INT NOT NULL,

    PRIMARY KEY(cod_disciplina, cod_curso),
    FOREIGN KEY(cod_disciplina) REFERENCES disciplina,
    FOREIGN KEY(cod_curso)      REFERENCES curso
)


CREATE TABLE matricula
(
    ra CHAR(9) NOT NULL,
    cpf_aluno CHAR(11) NOT NULL,
    cod_curso INT NOT NULL,
    pontuacao_vestibular INT NOT NULL,
    posicao_vestibular INT NOT NULL,
    ano_ingresso INT NOT NULL,
    semestre_ingresso INT NOT NULL,
    ano_limite_graduacao INT NOT NULL,
    semestre_limite_graduacao INT NOT NULL,
    matricula_ativa BIT NOT NULL ,

    PRIMARY KEY(ra),
    FOREIGN KEY(cpf_aluno) REFERENCES aluno,
    FOREIGN KEY(cod_curso) REFERENCES curso
)


CREATE TABLE matricula_disciplina
(
    id     int Identity NOT NULL,
    id_horario   CHAR(5) NOT NULL,
    ra_matricula  CHAR(9) NOT NULL,
    cod_disciplina  INT NOT NULL,
    ano_matricula  INT NOT NULL,
    semestre_matricula INT NOT NULL,
    estado    VARCHAR(12) NOT NULL,
    dia_semana INT NOT NULL,

    PRIMARY KEY(id),
    FOREIGN KEY(ra_matricula)   REFERENCES matricula,
    FOREIGN KEY(id_horario)     REFERENCES horario,
    FOREIGN KEY(cod_disciplina) REFERENCES disciplina
)

CREATE TABLE nota
(
    id_matricula_disc INT NOT NULL,
    nota_1 DECIMAL(4, 2) NOT NULL,
    nota_2 DECIMAL(4, 2) NOT NULL,
    nota_3 DECIMAL(4, 2) NOT NULL,
    data DATE,

    PRIMARY KEY (id_matricula_disc),
    FOREIGN KEY (id_matricula_disc) REFERENCES matricula_disciplina
)

CREATE TABLE presenca
(
    id INT NOT NULL,
    id_matricula_disc INT NOT NULL,
    id_conteudo INT NOT NULL,
    aula_1 BIT NOT NULL,
    aula_2 BIT NOT NULL,
    aula_3 BIT,
    aula_4 BIT,
    data DATE,

    PRIMARY KEY (id),
    FOREIGN KEY (id_matricula_disc) REFERENCES matricula_disciplina,
    FOREIGN KEY (id_conteudo) REFERENCES conteudo
)
