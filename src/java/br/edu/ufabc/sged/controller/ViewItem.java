/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ufabc.sged.controller;

import br.edu.ufabc.sged.dao.DataSource;
import br.edu.ufabc.sged.dao.UsuarioDAO;
import br.edu.ufabc.sged.model.Item;
import br.edu.ufabc.sged.model.Usuario;
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
public class ViewItem extends HttpServlet {

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
                usuario.setItens(usuariodao.readUsuarioItem(usuario));
                datasource.getConnection().close();
                request.setAttribute(Parameters.LOG, LOGMessage.getSuccessfulRecoveryMessage("Itens"));
                String paginaHeader = "<h1 class=\"titulo\">Seus arquivos</h1>\n" +
"                <div class=\"lista-item\">\n" +
"                    <div class=\"trigger-wrapper\">\n" +
"                        <div class=\"trigger-label\">\n" +
"                            <h3 class=\"column-name\">Nome</h3>\n" +
"                            <h3 class=\"column-name\">Tipo</h3>\n" +
"                            <h3 class=\"column-name\">SRC</h3>\n" +
"                        </div>\n" +
"                    </div>\n" +
"                </div>\n";
                String paginaContent = "";
                for(Object e: usuario.getItens()){
                    Item item = (Item) e;
                    paginaContent += 
        "                    <div class=\"lista-item\">\n" +
        "                        <input type=\"checkbox\" class=\"trigger-input\" id=" + item.getId() +">\n" +
        "                        <div class=\"trigger-wrapper\">\n" +
        "                            <label for=" + item.getId() + " class=\"trigger-label\">\n" +
        "                                <h3 class=\"trigger-nome\">" + item.getNome() + "</h3>\n" +
        "                                <h3 class=\"trigger-tipo\">" + item.getTipo()+ "</h3>\n" +
        "                                <h3 class=\"trigger-src\">" + item.getSrc()+ "</h3>\n" +
        "                            </label>\n" +
        "                            <form action=\"deleteitem\" method=\"POST\" class=\"form-trigger\">\n" +
        "                                <input value="+ item.getId() +" name=\"txt_id_item\" class=\"notdisplay\">\n" +
        "                                <input value="+ item.getSrc()+" name=\"txt_src\" class=\"notdisplay\">\n" +
        "                                <input value="+ item.getTipo()+" name=\"txt_tipo\" class=\"notdisplay\">\n" +
        "                                <button class=\"trigger-conteudo\" type=\"submit\">Excluir</button>\n" +
        "                            </form>\n" +
        "                        </div>\n" +
        "                    </div>\n";
                }
                request.setAttribute(Parameters.PAGE_SELECTION, paginaHeader + paginaContent);
                //request.setAttribute(Parameters.PAGE_SELECTION, HomePageSelector.VIEW_ITENS);
                request.setAttribute(Parameters.OBJECT_LIST, usuario.getItens());
            } catch (RuntimeException | SQLException e) {
                System.err.println(e.getMessage());
                request.setAttribute(Parameters.LOG, LOGMessage.getErrorRecoveryMessage("itens") + " " + LOGMessage.CONTACT_ADMINISTRATOR);
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
