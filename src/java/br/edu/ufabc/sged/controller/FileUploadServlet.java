/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ufabc.sged.controller;

import br.edu.ufabc.sged.dao.DataSource;
import br.edu.ufabc.sged.dao.ItemDAO;
import br.edu.ufabc.sged.dao.UsuarioDAO;
import br.edu.ufabc.sged.model.Item;
import br.edu.ufabc.sged.model.Usuario;
import br.edu.ufabc.sged.util.HomePageSelector;
import br.edu.ufabc.sged.util.LOGMessage;
import br.edu.ufabc.sged.util.Pages;
import br.edu.ufabc.sged.util.Parameters;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 *
 * @author Caique de Camargo
 */
@WebServlet("/uploadFiles")
@MultipartConfig(
        fileSizeThreshold   = 1024 * 1024 * 1,  // 1 MB
        maxFileSize         = 1024 * 1024 * 10, // 10 MB
        maxRequestSize      = 1024 * 1024 * 15, // 15 MB
        location            = ""
)
public class FileUploadServlet extends HttpServlet {
    
    private static final long serialVersionUID = 205242440643911308L;

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
        String applicationPath = request.getServletContext().getRealPath("");
        String uploadFilePath  = applicationPath + Parameters.UPLOAD_DIR + File.separator + usuario.getId();
        
        String page = Pages.HOME;
        
        if (Usuario.exist(usuario)){
            request.setAttribute(Parameters.PAGE_SELECTION, HomePageSelector.ADD_ITEM);
            
            Part part = request.getPart("file");            
            String[] header = part.getHeader("content-type").split("/");
            
            try{
                Item item = Item.getItemByHeader(header);
                
                uploadFilePath += File.separator + item.getTipo();
                File fileSaveDir = new File(uploadFilePath);
                if(!fileSaveDir.exists()){
                    fileSaveDir.mkdirs();
                }

                item.setNome(part.getSubmittedFileName());
                item.setRestricoes(request.getParameter("txt_restricoes"));
                item.setSrc(uploadFilePath + File.separator + part.getSubmittedFileName());

                try{
                    DataSource dataSource = new DataSource();
                    ItemDAO itemDAO = new ItemDAO(dataSource);
                    itemDAO.create(item);
                    UsuarioDAO userDAO = new UsuarioDAO(dataSource);
                    userDAO.setUsuarioItem(usuario, item);
                    dataSource.getConnection().close();

                    part.write(item.getSrc());
                    request.setAttribute(Parameters.LOG, LOGMessage.getSuccessfulUploadFileMessage(item.getSrc()));
                } catch (RuntimeException | SQLException e){
                    System.err.println(e);
                    request.setAttribute(Parameters.LOG, LOGMessage.ERROR_UPLOAD_FILE_MESSAGE + " " + LOGMessage.CONTACT_ADMINISTRATOR);
                }
            } catch (RuntimeException e) {
                System.err.println(e);
                request.setAttribute(Parameters.LOG, LOGMessage.ERROR_RETURNING_ITEM_TYPE + " " + LOGMessage.CONTACT_ADMINISTRATOR);
            }    
        } else {
            request.setAttribute(Parameters.LOG, LOGMessage.SESSION_EXPIRED);
            page = Pages.INDEX;
        }
        
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
        dispatcher.forward(request, response);
    }
    
    
}
