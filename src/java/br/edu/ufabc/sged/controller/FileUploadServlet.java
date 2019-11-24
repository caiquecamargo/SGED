/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ufabc.sged.controller;

import br.edu.ufabc.sged.dao.DataSource;
import br.edu.ufabc.sged.dao.ItemDAO;
import br.edu.ufabc.sged.dao.UsuarioDAO;
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
    
//    private static final long serialVersionUID = 205242440643911308L;
    private static final String UPLOAD_DIR = "uploads";

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
        
        ArrayList<Object> list = new ArrayList<>();
        request.setAttribute("errorSTR", "");
        request.setAttribute("pagina", "");
        request.setAttribute("objectList", list);
        
        Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
        String applicationPath = request.getServletContext().getRealPath("");
        String uploadFilePath  = applicationPath + UPLOAD_DIR + File.separator + usuario.getId();
        
        String page = "/home.jsp";
        
        if (usuario != null){
            Item item = null;
            Part part = request.getPart("file");               
            String[] header = part.getHeader("content-type").split("/");
            if(header[0].equals("application")){
                if(header[1].equals("pdf")){
                    item = new PDF();
                } else if (header[1].equals("doc") || header[1].equals("docx")) {
                    item = new Doc();
                }
            } else if (header[0].equals("image")){
                item = new Image();
            } else if (header[0].equals("audio")){
                item = new Music();
            }

            if(item != null){
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
                    request.setAttribute("errorSTR", "Arquivo carregado com sucesso no caminho: " + item.getSrc());
                } catch (RuntimeException e){
                    request.setAttribute("errorSTR", e.getMessage());
                } catch (SQLException ex){
                    System.err.println("Erro ao fechar conexão. "+ex.getMessage());
                    request.setAttribute("errorSTR", "Erro ao inserir arquivo, contate administrado do sistema");
                }
            } else {
                request.setAttribute("errorSTR", "Não foi possível realizar o Upload, tipo de arquivo não suportado");
            }    
        } else {
            request.setAttribute("errorSTR", "Sessão expirada");
            page = "/index.jsp";
        }

        request.setAttribute("pagina", "adicionar item");
        
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
        dispatcher.forward(request, response);
    }
    
    
}
