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
import java.io.PrintWriter;
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
public class AddUser extends HttpServlet {

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
        Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
        String page = "/home.jsp";
        ArrayList<Object> list = new ArrayList<>();
        request.setAttribute("errorSTR", "");
        request.setAttribute("pagina", "");
        request.setAttribute("objectList", list);
        
        if (usuario != null){
            request.setAttribute("pagina", "adicionar usuario");

            DataSource datasource = new DataSource();
            UsuarioDAO userDAO = new UsuarioDAO(datasource);
            try{
                List<Object> res = userDAO.readNotSettedUsers(usuario);
                request.setAttribute("objectList", res);
                if (res == null){
                    request.setAttribute("errorSTR", "Não há novos usuários para serem validados");
                } else {
                    request.setAttribute("errorSTR", "Usuários recuperados com sucesso.");
                }
                datasource.getConnection().close();
            } catch (RuntimeException e){
                System.err.println(e.getMessage());
                request.setAttribute("errorSTR", e.getMessage());
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
                request.setAttribute("errorSTR", ex.getMessage());
            }
        } else {
            request.setAttribute("errorSTR", "Sessão expirada");
            page = "/index.jsp";
        }

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
        dispatcher.forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }
}
