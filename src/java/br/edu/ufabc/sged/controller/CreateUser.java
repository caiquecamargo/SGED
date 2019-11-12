/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ufabc.sged.controller;

import br.edu.ufabc.sged.dao.DataSource;
import br.edu.ufabc.sged.dao.UsuarioDAO;
import br.edu.ufabc.sged.model.Usuario;
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
        
        String page = "/newuser.jsp";
        String nome  = request.getParameter("txt_nome");
        String email = request.getParameter("txt_email");
        String senha = request.getParameter("txt_senha");

        Usuario usuario = new Usuario();
        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setSenha(senha);

        DataSource datasource = new DataSource();
        UsuarioDAO usuarioDAO = new UsuarioDAO(datasource);
        int erro = usuarioDAO.create(usuario);

        try{
            datasource.getConnection().close();
            System.out.println(usuario.getId());
            if (erro == 0) {
                page = "/index.jsp";
                request.setAttribute("errorSTR", " ");
            } else {
                if (erro == 1062) request.setAttribute("errorSTR", "Usuário já existe");
            }
        } catch (SQLException ex){
            System.out.println("Erro ao fechar Conexão - "+ex.getMessage());
            request.setAttribute("errorMSG", "Erro ao criar nova conta de usuário");
        }
        
        System.out.println(page);
        
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
        dispatcher.forward(request, response);
    }


}
