package model;

import java.sql.Date;

public class Financa {
    private int id;
    private String nome;
    private String classificacao;
    private double valor;
    private Date dataFinanca;
    private Date dataCadastro;
    private String tipoTransacao;

    public Financa(int id, String nome, String classificacao, double valor, Date dataFinanca, Date dataCadastro, String tipoTransacao) {
        this.id = id;
        this.nome = nome;
        this.classificacao = classificacao;
        this.valor = valor;
        this.dataFinanca = dataFinanca;
        this.dataCadastro = dataCadastro;
        this.tipoTransacao = tipoTransacao;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getClassificacao() {
        return classificacao;
    }

    public double getValor() {
        return valor;
    }

    public Date getDataFinanca() {
        return dataFinanca;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public String getTipoTransacao() {
        return tipoTransacao;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setClassificacao(String classificacao) {
        this.classificacao = classificacao;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public void setDataFinanca(Date dataFinanca) {
        this.dataFinanca = dataFinanca;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public void setTipoTransacao(String tipoTransacao) {
        this.tipoTransacao = tipoTransacao;
    }
}
