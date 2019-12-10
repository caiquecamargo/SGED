/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ufabc.sged.controller;

import br.edu.ufabc.sged.dao.DataSource;
import br.edu.ufabc.sged.dao.UsuarioDAO;
import br.edu.ufabc.sged.model.Usuario;
import br.edu.ufabc.sged.util.HomePageSelector;
import br.edu.ufabc.sged.util.LOGMessage;
import br.edu.ufabc.sged.util.Pages;
import br.edu.ufabc.sged.util.Parameters;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Caique de Camargo
 */
public class ValidateUser extends HttpServlet {

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request = Parameters.setNullAttributesToRequest(request);
        Usuario usuario = (Usuario) request.getSession().getAttribute(Parameters.SESSION_NAME);
        String page = Pages.HOME;
        
        if (Usuario.exist(usuario)){
            request.setAttribute(Parameters.PAGE_SELECTION, HomePageSelector.VIEW_USERS);

            DataSource datasource = new DataSource();
            UsuarioDAO userDAO = new UsuarioDAO(datasource);
            try{
                List<Object> notSettedUers = userDAO.readNotSettedUsers(usuario);
                request.setAttribute(Parameters.OBJECT_LIST, notSettedUers);
                if (notSettedUers == null){
                    request.setAttribute(Parameters.LOG, LOGMessage.NO_USERS_TO_VALIDATE);
                } else {
                    request.setAttribute(Parameters.LOG, LOGMessage.USERS_SUCCEFULLY_RECOVERED);
                }
                datasource.getConnection().close();
            } catch (RuntimeException | SQLException e){
                System.err.println(e.getMessage());
                request.setAttribute(Parameters.LOG, LOGMessage.getErrorRecoveryMessage("usuários") + " " + LOGMessage.CONTACT_ADMINISTRATOR);
            }
        } else {
            request.setAttribute(Parameters.LOG, LOGMessage.SESSION_EXPIRED);
            page = Pages.INDEX;
        }

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
        dispatcher.forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request = Parameters.setNullAttributesToRequest(request);
        Usuario usuario = (Usuario) request.getSession().getAttribute(Parameters.SESSION_NAME);
        String page = Pages.HOME;
        
        if (Usuario.exist(usuario)){
            request.setAttribute(Parameters.PAGE_SELECTION, HomePageSelector.VALIDATE_USER);
            
            Usuario incompleteUserToEnable = new Usuario();        
            int idEnableUsuario = Integer.parseInt(request.getParameter("txt_id_usuario"));
            int nivel_de_acesso = Integer.parseInt(request.getParameter("txt_nivel_de_acesso"));
            incompleteUserToEnable.setId(idEnableUsuario);
            incompleteUserToEnable.setNivel_de_acesso(nivel_de_acesso);
            
            try{
                DataSource datasource = new DataSource();
                UsuarioDAO userDAO = new UsuarioDAO(datasource);
                Usuario enabledUser = (Usuario) userDAO.read(incompleteUserToEnable).get(0);
                if (usuario.havePermission(enabledUser)) {
                    userDAO.enableUser(enabledUser);
                    request.setAttribute(Parameters.LOG, LOGMessage.SUCCESSFUL_VALIDATE_USER_MESSAGE);
                } else {
                    request.setAttribute(Parameters.LOG, LOGMessage.USER_WITHOUT_PERMISSION);
                }
                datasource.getConnection().close();
            } catch (RuntimeException | SQLException e){
                System.err.println(e.getMessage());
                request.setAttribute(Parameters.LOG, LOGMessage.ERROR_VALIDATE_USER_MESSAGE + " " + LOGMessage.CONTACT_ADMINISTRATOR);
            }
        } else {
            request.setAttribute(Parameters.LOG, LOGMessage.SESSION_EXPIRED);
            page = Pages.INDEX;
        }
        
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
        dispatcher.forward(request, response);
    }
}
