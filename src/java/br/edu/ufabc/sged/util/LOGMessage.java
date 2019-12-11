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
    public static final String ERROR_CREATE_USER = "Erro ao criar usuário.";
    public static final String CONTACT_ADMINISTRATOR = "Entre em contato com o admistrador do sistema.";
    public static final String ERROR_RETURNING_ITEM_TYPE = "Erro ao retornar tipo do arquivo";
    public static final String ERROR_UPLOAD_FILE_MESSAGE = "Erro ao carregar arquivo";
    public static final String SUCCESSFUL_VALIDATE_USER_MESSAGE = "Usuário validado com sucesso";
    public static final String ERROR_VALIDATE_USER_MESSAGE = "Erro ao validar usuário";
    public static final String USER_WITHOUT_PERMISSION = "Sem autorização para realizar a tarefa";
    public static final String NOT_VALIDATED_USER = "Usuário ainda não validado, consulte seu superior direto";
    public static final String ERROR_LOGIN = "Usuário ou senha inválidos";
    public static final String SUCCESSFUL_RECOVERY_GROUP_MEMBERS = "Membros do grupo recuperados com sucesso";
    public static final String ERROR_RECOVERY_GROUP_MEMBERS = "Erro ao recuperar membros do grupo";
    public static final String USER_EXIST = "Usuário existente";
    
    public static final String getSuccessfulUploadFileMessage(String m){
        return "Arquivo carregado com sucesso no caminho " + m;
    }
    
    public static final String getSuccessfulAddingMessage(String m){
        return m + " adicionado com sucesso.";
    }
    
    public static final String getErrorAddingMessage(String m){
        return "Erro ao adicionar " + m + ".";
    }
    
    public static final String getSuccessfulDeleteMessage(String m){
        return m + " deletado com sucesso.";
    }
    
    public static final String getErrorDeleteMessage(String m){
        return "Erro ao deletar " + m;
    }
    
    public static final String getSuccessfulRecoveryMessage(String m){
        return m + " recuperados com sucesso";
    }
    
    public static final String getErrorRecoveryMessage(String m){
        return "Erro ao recuperar " + m;
    }
    
    public static final String getSuccessfulUpdatingMessage(String m){
        return m + " atualizafo com sucesso";
    }
    
    public static final String getErrorUpdatingMessage(String m){
        return "Erro ao atualizar " + m;
    }
    
    public static final String getInvalidModelObjectMessage(String m){
        return "Invalid " + m + " model Object";
    }
}
