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
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sun.font.FontUtilities;

/**
 *
 * @author Caique de Camargo
 */
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request = Parameters.setNullAttributesToRequest(request);
        String page = Pages.LOGIN;
        
        Usuario userToLogin = setUserWithRequestAttributes(request);
        
        try {
            DataSource datasource = new DataSource();
            UsuarioDAO userDAO = new UsuarioDAO(datasource);
            List<Object> res = userDAO.login(userToLogin);
            if (res.size() > 0){
                Usuario usuario = (Usuario) res.get(0);
                if(usuario.getSituacao() != 0){
                    page = Pages.HOME;
                    request.getSession().setAttribute(Parameters.SESSION_NAME, usuario);
                } else {
                    request.setAttribute(Parameters.LOG, LOGMessage.NOT_VALIDATED_USER);
                }
            } else {
                request.setAttribute(Parameters.LOG, LOGMessage.ERROR_LOGIN);
            }
            datasource.getConnection().close();
        } catch (RuntimeException | SQLException e) {
            System.err.println(e.getMessage());
            request.setAttribute(Parameters.LOG, LOGMessage.getErrorRecoveryMessage("usuário") + " " + LOGMessage.CONTACT_ADMINISTRATOR);
        }        
        
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
        dispatcher.forward(request, response);
    }
    
    private static Usuario setUserWithRequestAttributes(HttpServletRequest request){
        String user = request.getParameter("txt_user");
        String password = request.getParameter("txt_password");
        
        Usuario userToLogin = new Usuario();
        userToLogin.setEmail(user);
        userToLogin.setSenha(password);
        
        return userToLogin;
    }
}
