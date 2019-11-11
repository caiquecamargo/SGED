/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ufabc.sged.controller;

import br.edu.ufabc.sged.dao.UsuarioDAO;
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
        String user = request.getParameter("txt_user");
        String password = request.getParameter("txt_password");
        System.out.println(user);
        System.out.println(password);
        String page;
        
        List<Object> res;
        UsuarioDAO userDAO = new UsuarioDAO();
        res = userDAO.read(null);
            
        if (user.equals("caique.de.camargo@hotmail.com") && password.equals("1234")){
            
            request.getSession().setAttribute("Usuario", res.get(0));
            page = "/myaccount.jsp";
        }else{
            request.setAttribute("errorSTR", "Email / Senha não conferem");
            page = "/error.jsp";
        }
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
        dispatcher.forward(request, response);
    }
}
