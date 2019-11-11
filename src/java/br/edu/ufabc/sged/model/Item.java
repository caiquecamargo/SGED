/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ufabc.sged.model;

/**
 *
 * @author Caique de Camargo
 */
public abstract class Item implements java.io.Serializable{
    private int     id;
    private String  tipo;
    private String  nome;
    private String  restricoes;
    private String  src;

    /**
     * @return the id
     */
    public int getId(){
        return id;
    }
    
    /**
     * @param id the id to set
     */
    public void setId(int id){
        this.id = id;
    }

    /**
     * @return the tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the restricoes
     */
    public String getRestricoes() {
        return restricoes;
    }

    /**
     * @param restricoes the restricoes to set
     */
    public void setRestricoes(String restricoes) {
        this.restricoes = restricoes;
    }

    /**
     * @return the src
     */
    public String getSrc() {
        return src;
    }

    /**
     * @param src the src to set
     */
    public void setSrc(String src) {
        this.src = src;
    }
    
    

}
