/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ufabc.sged.util;

import java.util.HashMap;

/**
 *
 * @author Caique de Camargo
 */
public class PageSRC {
    private final static HashMap<String, String> PAGE_SRC = new HashMap<String, String>();

    static {
        PAGE_SRC.put(HomePageSelector.ADD_ITEM, "./pages/adicionarItem.html");
        PAGE_SRC.put(HomePageSelector.VIEW_ITENS, "./pages/visualizarItem.html");
        PAGE_SRC.put(HomePageSelector.ADD_GROUP, "./pages/adicionarGrupo.html");
        PAGE_SRC.put(HomePageSelector.VIEW_GROUPS, "./pages/visualizarGrupos.html");
        PAGE_SRC.put(HomePageSelector.EDIT_GROUP, "./pages/editarGrupo.html");
        PAGE_SRC.put(HomePageSelector.VIEW_GROUP_MEMBERS, "./pages/usuariosGrupo.html");
        PAGE_SRC.put(HomePageSelector.VALIDATE_USER, "./pages/validarUsuario.html");
        PAGE_SRC.put(HomePageSelector.VIEW_USERS, "./pages/visualizarUsuarios.html");
        PAGE_SRC.put(HomePageSelector.EDIT_USER, "./pages/editarUsuario.html");
    }

    public static final String getPageSRCById(String id) {
        return PAGE_SRC.get(id);
    }
}
