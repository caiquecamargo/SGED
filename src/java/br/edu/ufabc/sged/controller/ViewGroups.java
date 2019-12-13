/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ufabc.sged.controller;

import br.edu.ufabc.sged.dao.DataSource;
import br.edu.ufabc.sged.dao.UsuarioDAO;
import br.edu.ufabc.sged.model.Usuario;
import br.edu.ufabc.sged.util.AttributesListMaker;
import br.edu.ufabc.sged.util.HTMLFactory;
import br.edu.ufabc.sged.util.HomePageSelector;
import br.edu.ufabc.sged.util.LOGMessage;
import br.edu.ufabc.sged.util.Pages;
import br.edu.ufabc.sged.util.Parameters;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Caique de Camargo
 */
public class ViewGroups extends HttpServlet {

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
        String page = Pages.HOME;
        Usuario usuario = (Usuario) request.getSession().getAttribute(Parameters.SESSION_NAME);
        
        if (Usuario.exist(usuario)){
            DataSource datasource = new DataSource();
            UsuarioDAO usuariodao = new UsuarioDAO(datasource);
            try {
                usuario.setGrupo(usuariodao.readGrupoFromUsuario(usuario));
                datasource.getConnection().close();
                
                String applicationPath = request.getServletContext().getRealPath("");
                ArrayList<ArrayList<String>> attributesList = AttributesListMaker.getAttributesList(usuario.getGrupo());
                String pageSelector = HTMLFactory.getFormattedHTML(HomePageSelector.VIEW_GROUPS, applicationPath, attributesList);
                
                request.setAttribute(Parameters.PAGE_SELECTION, pageSelector);
                request.setAttribute(Parameters.LOG, LOGMessage.getSuccessfulRecoveryMessage("Grupos"));
            } catch (RuntimeException | SQLException e) {
                System.err.println(e.getMessage());
                request.setAttribute(Parameters.LOG, LOGMessage.getErrorRecoveryMessage("grupos") + " " + LOGMessage.CONTACT_ADMINISTRATOR);
            }
        } else {
            request.setAttribute(Parameters.LOG, LOGMessage.SESSION_EXPIRED);
            page = Pages.INDEX;
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
