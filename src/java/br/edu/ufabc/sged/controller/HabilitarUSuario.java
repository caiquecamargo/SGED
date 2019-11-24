/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ufabc.sged.controller;

import br.edu.ufabc.sged.dao.DataSource;
import br.edu.ufabc.sged.dao.UsuarioDAO;
import br.edu.ufabc.sged.model.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
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
public class HabilitarUSuario extends HttpServlet {

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
        ArrayList<Object> list = new ArrayList<>();
        request.setAttribute("objectList", list);
        request.setAttribute("errorSTR", "");
        request.setAttribute("pagina", "adicionar usuario");
        
        Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
        int idEnableUsuario = Integer.parseInt(request.getParameter("txt_id_usuario"));
        int nivel_de_acesso = Integer.parseInt(request.getParameter("txt_nivel_de_acesso"));
        
        String page = "/home.jsp";
        
        if (usuario != null){
            Usuario incompleto = new Usuario();
            incompleto.setId(idEnableUsuario);
            incompleto.setNivel_de_acesso(nivel_de_acesso);
            DataSource datasource = new DataSource();
            UsuarioDAO userDAO = new UsuarioDAO(datasource);    
            try{
                List<Object> res = userDAO.read(incompleto);
                if (res.size() > 0){
                    Usuario enableUser = (Usuario) res.get(0);
                    if (usuario.getNivel_de_acesso() > enableUser.getNivel_de_acesso() || usuario.getNivel_de_acesso() == 0) {
                        userDAO.enableUser(enableUser);
                        request.setAttribute("errorSTR", "Usuário habilitado com sucesso");
                    } else {
                        request.setAttribute("errorSTR", "Sem autorização para realizar tarefa");
                    }
                } else {
                    System.err.println("Erro ao habilitar usuario");
                    request.setAttribute("errorSTR", "Erro ao habilitar usuario, contate administrador do sistema");
                    request.setAttribute("pagina", "adicionar usuario");
                }
            } catch (Exception e){
                System.err.println("Erro ao habilitar usuario. " + e.getMessage());
                request.setAttribute("errorSTR", "Erro ao habilitar usuario, contate administrador do sistema");
                request.setAttribute("pagina", "adicionar usuario");
            }
        } else {
            request.setAttribute("errorSTR", "Sessão expirada");
            page = "/index.jsp";
        }
        
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
        dispatcher.forward(request, response);
    }

}
