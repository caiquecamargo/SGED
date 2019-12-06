/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ufabc.sged.model;

import java.util.List;

/**
 *
 * @author Caique de Camargo
 */
public class Usuario implements java.io.Serializable{
    private int     id;
    private String  nome;
    private String  email;
    private String  senha;
    private int     nivel_de_acesso;
    private int     situacao;
    private List<Object>  grupo;
    private List<Object> itens;
    
    public static final boolean exist(Usuario u){
        if (u != null) return true;
        return false;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
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
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the senha
     */
    public String getSenha() {
        return senha;
    }

    /**
     * @param senha the senha to set
     */
    public void setSenha(String senha) {
        this.senha = senha;
    }

    /**
     * @return the nivel_de_acesso
     */
    public int getNivel_de_acesso() {
        return nivel_de_acesso;
    }

    /**
     * @param nivel_de_acesso the nivel_de_acesso to set
     */
    public void setNivel_de_acesso(int nivel_de_acesso) {
        this.nivel_de_acesso = nivel_de_acesso;
    }

    /**
     * @return the grupo
     */
    public List<Object> getGrupo() {
        return grupo;
    }

    /**
     * @param grupo the grupo to set
     */
    public void setGrupo(List<Object> grupo) {
        this.grupo = grupo;
    }

    /**
     * @return the itens
     */
    public List<Object> getItens() {
        return itens;
    }

    /**
     * @param itens the itens to set
     */
    public void setItens(List<Object> itens) {
        this.itens = itens;
    }

    /**
     * @return the situacao
     */
    public int getSituacao() {
        return situacao;
    }

    /**
     * @param situacao the situacao to set
     */
    public void setSituacao(int situacao) {
        this.situacao = situacao;
    }
    
}
