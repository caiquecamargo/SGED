/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ufabc.sged.controller;

import br.edu.ufabc.sged.dao.DataSource;
import br.edu.ufabc.sged.dao.ItemDAO;
import br.edu.ufabc.sged.model.Doc;
import br.edu.ufabc.sged.model.Image;
import br.edu.ufabc.sged.model.Item;
import br.edu.ufabc.sged.model.Music;
import br.edu.ufabc.sged.model.PDF;
import br.edu.ufabc.sged.model.Usuario;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
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
        Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
        ArrayList<Object> list = new ArrayList<>();
        request.setAttribute("objectList", list);
        request.setAttribute("errorSTR", "");
        request.setAttribute("pagina", "visualizar item");
        
        int  idItem = Integer.parseInt(request.getParameter("txt_id_item"));
        System.out.println(request.getParameter("txt_tipo"));
        String tipo = request.getParameter("txt_tipo");
        System.out.println(request.getParameter("txt_src"));
        String src  = request.getParameter("txt_src");
        String page = "/home.jsp";
        
        Item item = null;
        switch (tipo) {
            case "PDF":
                item = new PDF();
                break;
            case "MUSIC":
                item = new Music();
                break;
            case "DOC":
                item = new Doc();
                break;
            case "IMAGE":
                item = new Image();
                break;
            default:
                break;
        }
        
        if(usuario != null){
            if(item != null){
                item.setId(idItem);
                item.setSrc(src);
                
                DataSource datasource = new DataSource();
                ItemDAO itemDAO = new ItemDAO(datasource);
                
                try {
                    itemDAO.deleteRelationship(item);
                    itemDAO.delete(item);
                    datasource.getConnection().close();
                    
                    File file = new File(item.getSrc());
                    file.delete();
                    request.setAttribute("errorSTR", "Item excluido com sucesso.");
                } catch (RuntimeException e){
                    System.err.println(e.getMessage());
                    request.setAttribute("errorSTR", e.getMessage());
                } catch (SQLException ex) {
                    System.err.println(ex.getMessage());
                }
            } else {
                request.setAttribute("errorSTR", "Erro ao excluir item");
            }
        } else {
            request.setAttribute("errorSTR", "Sessão expirada");
            page = "/index.jsp";
        }
        
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
        dispatcher.forward(request, response);
    }
}
