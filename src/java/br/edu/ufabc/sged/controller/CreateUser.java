/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ufabc.sged.controller;

import br.edu.ufabc.sged.dao.DataSource;
import br.edu.ufabc.sged.dao.UsuarioDAO;
import br.edu.ufabc.sged.model.Usuario;
import br.edu.ufabc.sged.util.LOGMessage;
import br.edu.ufabc.sged.util.Pages;
import br.edu.ufabc.sged.util.Parameters;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Caique de Camargo
 */
public class CreateUser extends HttpServlet {
    
    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String page = Pages.NEW_USER;
        String nome  = request.getParameter("txt_nome");
        String email = request.getParameter("txt_email");
        String senha = request.getParameter("txt_senha");

        Usuario usuario = new Usuario();
        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setSenha(senha);

        DataSource datasource = new DataSource();
        UsuarioDAO usuarioDAO = new UsuarioDAO(datasource);
        
        try {
            usuarioDAO.create(usuario);
            datasource.getConnection().close();
            page = Pages.INDEX;
            request.setAttribute(Parameters.LOG, LOGMessage.NULL);
        } catch (RuntimeException | SQLException e) {
            System.err.println(e.getMessage());
            request.setAttribute(Parameters.LOG, LOGMessage.ERROR_CREATE_USER + " " + LOGMessage.CONTACT_ADMINISTRATOR);
        }
        
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
        dispatcher.forward(request, response);
    }


}
