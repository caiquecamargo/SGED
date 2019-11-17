/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ufabc.sged.controller;

import br.edu.ufabc.sged.dao.DataSource;
import br.edu.ufabc.sged.dao.GrupoDAO;
import br.edu.ufabc.sged.dao.UsuarioDAO;
import br.edu.ufabc.sged.model.Grupo;
import br.edu.ufabc.sged.model.Usuario;
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
public class AddGroup extends HttpServlet {

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
        ArrayList<Object> list = new ArrayList<>();
        request.setAttribute("errorSTR", "");
        request.setAttribute("pagina", "adicionar grupo");
        request.setAttribute("objectList", list);
        
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
        String page      = "/home.jsp";
        String nome      = request.getParameter("txt_nome");
        String descricao = request.getParameter("txt_descricao");
        int    nivel     = Integer.parseInt(request.getParameter("txt_nivel"));
        
        Grupo grupo = new Grupo();
        grupo.setNome(nome);
        grupo.setDescricao(descricao);
        grupo.setNivel(nivel);
        
        DataSource datasource = new DataSource();
        GrupoDAO   grupodao   = new GrupoDAO(datasource);
        
        try {
            grupodao.create(grupo);
            Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
            UsuarioDAO userdao = new UsuarioDAO(datasource);
            userdao.setUsuarioGrupo(usuario, grupo);
            ArrayList<Object> list = new ArrayList<>();
            request.setAttribute("errorSTR", "Grupo adicionado com sucesso");
            request.setAttribute("pagina", "adicionar grupo");
            request.setAttribute("objectList", list);
            datasource.getConnection().close();
        } catch (RuntimeException e) {
            request.setAttribute("errorStr", e.getMessage());
        } catch (SQLException ex){
            System.err.println("Erro ao adicionar grupo.  " +ex.getMessage());
        }
        
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
        dispatcher.forward(request, response);
    }
}
