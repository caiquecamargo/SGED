/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ufabc.sged.controller;

import br.edu.ufabc.sged.dao.DataSource;
import br.edu.ufabc.sged.dao.ItemDAO;
import br.edu.ufabc.sged.model.Item;
import br.edu.ufabc.sged.model.Usuario;
import br.edu.ufabc.sged.util.LOGMessage;
import br.edu.ufabc.sged.util.Pages;
import br.edu.ufabc.sged.util.Parameters;
import br.edu.ufabc.sged.util.ServletNames;
import java.io.File;
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
public class DeleteItem extends HttpServlet {

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
        
        int  idItem = Integer.parseInt(request.getParameter("txt_id_item"));
        String tipo = request.getParameter("txt_tipo");
        String src  = request.getParameter("txt_src");
        
        if(Usuario.exist(usuario)){              
            try {
                Item item = Item.getItemAsType(tipo);                        
                item.setId(idItem);
                item.setSrc(src);

                DataSource datasource = new DataSource();
                ItemDAO itemDAO = new ItemDAO(datasource);

                itemDAO.deleteRelationship(item);
                itemDAO.delete(item);
                datasource.getConnection().close();

                File file = new File(item.getSrc());
                file.delete();
                request.setAttribute(Parameters.LOG, LOGMessage.getSuccessfulDeleteMessage(item.getTipo()));

                page = ServletNames.VIEW_ITEM;
            } catch (RuntimeException | SQLException e){
                System.err.println(e.getMessage());
                request.setAttribute(Parameters.LOG, LOGMessage.getErrorDeleteMessage("item") + " " + LOGMessage.CONTACT_ADMINISTRATOR);
            } 
        } else {
            request.setAttribute(Parameters.LOG, LOGMessage.SESSION_EXPIRED);
            page = Pages.INDEX;
        }
        
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
        dispatcher.forward(request, response);
    }
}
