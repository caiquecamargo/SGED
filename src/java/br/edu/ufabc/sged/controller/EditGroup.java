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
import br.edu.ufabc.sged.util.LOGMessage;
import br.edu.ufabc.sged.util.Pages;
import br.edu.ufabc.sged.util.Parameters;
import br.edu.ufabc.sged.util.ServletNames;
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
        request = Parameters.setNullAttributesToRequest(request);
        Usuario usuario = (Usuario) request.getSession().getAttribute(Parameters.SESSION_NAME);
        String page = Pages.HOME;
        
        if (Usuario.exist(usuario)){
            Grupo  grupo = setGroupWithRequestAttributes(request);
            
            try {
                DataSource datasource = new DataSource();
                GrupoDAO   grupoDAO   = new GrupoDAO(datasource);
            
                grupoDAO.update(grupo);
                datasource.getConnection().close();
                
                request.setAttribute(Parameters.LOG, LOGMessage.getSuccessfulUpdatingMessage("Grupo"));
                page = ServletNames.VIEW_GROUPS;
            } catch (RuntimeException | SQLException e) {
                System.err.println(e.getMessage());
                request.setAttribute(Parameters.LOG, LOGMessage.getErrorUpdatingMessage("grupo"));
            }
        } else {
            request.setAttribute(Parameters.LOG, LOGMessage.SESSION_EXPIRED);
            page = Pages.INDEX;
        }
        
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
        dispatcher.forward(request, response);
    }
    
    private static Grupo setGroupWithRequestAttributes(HttpServletRequest request){
        Grupo  grupo     = new Grupo();
        int    idGrupo   = Integer.parseInt(request.getParameter("txt_id_grupo"));
        String nome      = request.getParameter("txt_nome");
        String descricao = request.getParameter("txt_descricao");
        int    nivel     = Integer.parseInt(request.getParameter("txt_nivel"));

        grupo.setId(idGrupo);
        grupo.setNome(nome);
        grupo.setDescricao(descricao);
        grupo.setNivel(nivel);
        
        return grupo;
    }
}
