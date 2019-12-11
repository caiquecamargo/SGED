/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ufabc.sged.controller;

import br.edu.ufabc.sged.util.LOGMessage;
import br.edu.ufabc.sged.util.Pages;
import br.edu.ufabc.sged.util.Parameters;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Caique de Camargo
 */
public class NewUser extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute(Parameters.LOG, LOGMessage.NULL);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(Pages.NEW_USER);
        dispatcher.forward(request, response);
    }
}
