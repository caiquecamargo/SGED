/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ufabc.sged.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
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
        String applicationPath = request.getServletContext().getRealPath("");
        String uploadFilePath  = applicationPath + File.separator + UPLOAD_DIR;
        
        File fileSaveDir = new File(uploadFilePath);
        if(!fileSaveDir.exists()){
            fileSaveDir.mkdirs();
        }
        System.out.println("Upload File Directory: "+fileSaveDir.getAbsolutePath());
        
        String fileName = null;
        for(Part part:request.getParts()){
            fileName = extractFileName(part);
            part.write(uploadFilePath + File.separator + fileName);
        }
        
        request.setAttribute("errorSTR", fileName + " File uploaded successfully!");
        ArrayList<Object> list = new ArrayList<>();
        request.setAttribute("pagina", "adicionar item");
        request.setAttribute("objectList", list);
        
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/home.jsp");
        dispatcher.forward(request, response);
    }
    
    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length()-1);
            }
        }
        return "";
    }
}
