/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ufabc.sged.util;

/**
 *
 * @author Caique
 */
public final class LOGMessage {
    public static final String SESSION_EXPIRED = "Sessão expirada";
    public static final String NULL = "";
    public static final String USERS_SUCCEFULLY_RECOVERED = "Usuário recuperados com sucesso";
    public static final String NO_USERS_TO_VALIDATE = "Não há usuários para validação";
    
    public static String getSuccessfulAddingMessage(String m){
        return m + " adicionado com sucesso.";
    }
    
    public static String getErrorAddingMessage(String m){
        return "Erro ao adicionar " + m + ".";
    }
}
