package com.example.projetopresencaaluno.modelos;

public class Disciplina {

    private String nome;
    private Integer iniciaEm;
    private Integer finalizaEm;
    private Integer diaSemana;

    public Disciplina () {

    }

    public Disciplina(String nome, int iniciaEm, int finalizaEm, Integer diaSemana) {
        this.nome = nome;
        this.iniciaEm = iniciaEm;
        this.finalizaEm = finalizaEm;
        this.diaSemana = diaSemana;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getIniciaEm() {
        return iniciaEm;
    }

    public void setIniciaEm(Integer iniciaEm) {
        this.iniciaEm = iniciaEm;
    }

    public Integer getFinalizaEm() {
        return finalizaEm;
    }

    public void setFinalizaEm(Integer finalizaEm) {
        this.finalizaEm = finalizaEm;
    }

    public Integer getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(Integer diaSemana) {
        this.diaSemana = diaSemana;
    }
}
