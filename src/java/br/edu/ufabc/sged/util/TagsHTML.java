/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ufabc.sged.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 *
 * @author Caique de Camargo
 */
public class TagsHTML {
    private final static HashMap<String, ArrayList<String>> TAGS = new HashMap<String, ArrayList<String>>();

    static {
        TAGS.put(HomePageSelector.ADD_ITEM, new ArrayList<>());
        TAGS.put(HomePageSelector.VIEW_ITENS, new ArrayList<>(Arrays.asList("?id?", "?nome?", "?tipo?", "?src?")));
        TAGS.put(HomePageSelector.ADD_GROUP, new ArrayList<>());
        TAGS.put(HomePageSelector.VIEW_GROUPS, new ArrayList<>(Arrays.asList("?id?", "?nome?", "?nivel?", "?descricao?")));
        TAGS.put(HomePageSelector.EDIT_GROUP, new ArrayList<>(Arrays.asList("?id?", "?nome?", "?nivel?", "?descricao?")));
        TAGS.put(HomePageSelector.VIEW_GROUP_MEMBERS, new ArrayList<>(Arrays.asList("?id?", "?nome?", "?email?", "?nivel_de_acesso?")));
        TAGS.put(HomePageSelector.VALIDATE_USER, new ArrayList<>(Arrays.asList("?id?", "?nome?", "?email?")));
        TAGS.put(HomePageSelector.VIEW_USERS, new ArrayList<>(Arrays.asList("?id?", "?nome?", "?email?", "?nivel_de_acesso?")));
        TAGS.put(HomePageSelector.EDIT_USER, new ArrayList<>(Arrays.asList("?id?", "?nome?", "?email?", "?nivel_de_acesso?")));
    }

    public static final ArrayList getTagsById(String id) {
        return TAGS.get(id);
    }
}
