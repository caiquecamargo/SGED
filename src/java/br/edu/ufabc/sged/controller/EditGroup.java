/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ufabc.sged.controller;

import br.edu.ufabc.sged.dao.DataSource;
import br.edu.ufabc.sged.dao.GrupoDAO;
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
public class EditGroup extends HttpServlet {

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
        String page = "/home.jsp";
        Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
        
        DataSource datasource = new DataSource();
        UsuarioDAO usuariodao = new UsuarioDAO(datasource);
        
        try {
            List<Object> list = usuariodao.readUsuarioGrupo(usuario);
            datasource.getConnection().close();
            request.setAttribute("errorSTR", "Grupos recuperados com sucesso");
            request.setAttribute("pagina", "editar grupo");
            request.setAttribute("objectList", list);
        } catch (RuntimeException e) {
            request.setAttribute("errorSTR", e.getMessage());
        } catch (SQLException ex){
            System.err.println("Erro ao recuperar grupos. "+ex.getMessage());
            request.setAttribute("errorSTR", "Erro Desconhecido ao recuperar Grupo. Contate admistrador do sistema");
        }        
        
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
        dispatcher.forward(request, response);
    }

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
        
    }
}