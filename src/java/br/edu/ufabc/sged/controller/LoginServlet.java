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
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
        String user = request.getParameter("txt_user");
        String password = request.getParameter("txt_password");
        System.out.println(user);
        System.out.println(password);
        
        Usuario incompleto = new Usuario();
        incompleto.setEmail(user);
        incompleto.setSenha(password);
        
        String page = "/index.jsp";
        
        try {
            DataSource ds = new DataSource();
            UsuarioDAO userDAO = new UsuarioDAO(ds);
            List<Object> res = userDAO.read(incompleto);
            if (res != null && res.size()>0){
                page = "/home.jsp";
                request.getSession().setAttribute("usuario", res.get(0));
            } else {
                request.setAttribute("errorSTR", "Usuario / Senha inválidos");
            }
            ds.getConnection().close();
        } catch (Exception e) {
            request.setAttribute("errorSTR", "Erro ao recuperar");
        }         
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
        dispatcher.forward(request, response);
    }
}
