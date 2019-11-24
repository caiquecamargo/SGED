/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ufabc.sged.controller;

import br.edu.ufabc.sged.dao.DataSource;
import br.edu.ufabc.sged.dao.GrupoDAO;
import br.edu.ufabc.sged.model.Grupo;
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
public class ViewGroupMembers extends HttpServlet {

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
        Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
        int     idGrupo = Integer.parseInt(request.getParameter("txt_id_grupo"));
        Grupo incompleto = new Grupo();
        incompleto.setId(idGrupo);
        
        String page = "/home.jsp";
        ArrayList<Object> list = new ArrayList<>();
        request.setAttribute("errorSTR", "");
        request.setAttribute("pagina", "usuario do grupo");
        request.setAttribute("objectList", list);
        
        if (usuario != null){
            DataSource datasource = new DataSource();
            GrupoDAO grupoDAO = new GrupoDAO(datasource);
            
            try {
                List<Object> result = grupoDAO.readMembers(incompleto);
                datasource.getConnection().close();
                request.setAttribute("objectList", result);
                request.setAttribute("errorSTR", "Membros recuperados com sucesso");
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
                request.setAttribute("errorSTR", "Erro ao recuperar usuários do grupo. Contate administrador do sistema");
            }
        } else {
            request.setAttribute("errorSTR", "Sessão expirada");
            page = "/index.jsp";
        }
        
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
        dispatcher.forward(request, response);
    }

}
