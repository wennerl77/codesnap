package model;

import java.sql.Date;

/**
 * Modelo de Financa no Java do banco de dados
 * @author WennerLucas
 */
public class Financa {
    private int id;
    private String nome;
    private String classificacao;
    private float valor;
    private Date dataEntrada;
    private Date dataCadastro;
    private String tipoTransacao;

    public Financa(String nome, String classificacao, float valor, Date dataEntrada, Date dataCadastro, String tipoTransacao) {
        this.nome = nome;
        this.classificacao = classificacao;
        this.valor = valor;
        this.dataEntrada = dataEntrada;
        this.dataCadastro = dataCadastro;
        this.tipoTransacao = tipoTransacao;
    }

    public Financa(int id, String nome, String classificacao, float valor, Date dataEntrada, Date dataCadastro, String tipoTransacao) {
        this.id = id;
        this.nome = nome;
        this.classificacao = classificacao;
        this.valor = valor;
        this.dataEntrada = dataEntrada;
        this.dataCadastro = dataCadastro;
        this.tipoTransacao = tipoTransacao;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getClassificacao() {
        return classificacao;
    }

    public float getValor() {
        return valor;
    }

    public Date getDataFinanca() {
        return dataEntrada;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public String getTipoTransacao() {
        return tipoTransacao;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setClassificacao(String classificacao) {
        this.classificacao = classificacao;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public void setDataFinanca(Date dataFinanca) {
        this.dataEntrada = dataFinanca;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public void setTipoTransacao(String tipoTransacao) {
        this.tipoTransacao = tipoTransacao;
    }
    
    @Override
    public String toString() {
        return "Financa{" + "id=" + id + ", nome=" + nome + ", classificacao=" + classificacao + ", valor=" + valor + ", dataEntrada=" + dataEntrada + ", dataCadastro=" + dataCadastro + ", tipoTransacao=" + tipoTransacao + '}';
    }
}
