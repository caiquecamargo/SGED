/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ufabc.sged.util;

import br.edu.ufabc.sged.model.Grupo;
import br.edu.ufabc.sged.model.Item;
import br.edu.ufabc.sged.model.Usuario;
import java.util.ArrayList;

/**
 *
 * @author Caique de Camargo
 */
public class AttributesListMaker {
    public static ArrayList<ArrayList<String>> getAttributesListItem(ArrayList<Item> itemsList){
        ArrayList<ArrayList<String>> attributesList = new ArrayList<>();
        for(Item item : itemsList){
            ArrayList<String> attributes = new ArrayList<>();
            attributes.add((Integer.toString(item.getId())));
            attributes.add(item.getNome());
            attributes.add(item.getTipo());
            attributes.add(item.getSrc());
            attributesList.add(attributes);
        }
        return attributesList;
    }
    
    public static ArrayList<ArrayList<String>> getAttributesListGroup(ArrayList<Grupo> groupsList){
        ArrayList<ArrayList<String>> attributesList = new ArrayList<>();
        for(Grupo group : groupsList){
            ArrayList<String> attributes = new ArrayList<>();
            attributes.add((Integer.toString(group.getId())));
            attributes.add(group.getNome());
            attributes.add((Integer.toString(group.getNivel())));
            attributes.add(group.getDescricao());
            attributesList.add(attributes);
        }
        return attributesList;
    }
    
    public static ArrayList<ArrayList<String>> getAttributesListUser(ArrayList<Usuario> usersList){
        ArrayList<ArrayList<String>> attributesList = new ArrayList<>();
        for(Usuario users : usersList){
            ArrayList<String> attributes = new ArrayList<>();
            attributes.add((Integer.toString(users.getId())));
            attributes.add(users.getNome());
            attributes.add(users.getEmail());
            attributes.add((Integer.toString(users.getNivel_de_acesso())));
            attributesList.add(attributes);
        }
        return attributesList;
    }
    
}
