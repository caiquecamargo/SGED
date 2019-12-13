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
import java.util.List;

/**
 *
 * @author Caique de Camargo
 */
public class AttributesListMaker {
    public static ArrayList<ArrayList<String>> getAttributesList(List<Object> objectList) throws RuntimeException{
        ArrayList<ArrayList<String>> attributesList = new ArrayList<>();
        objectList.forEach((object) -> {
            attributesList.add(getAttributes(object));
        });
        return attributesList;
    }
    
    private static ArrayList<String> getAttributes(Object object){
        if (object instanceof Item)
            return getItemAttributes(object);
        if (object instanceof Grupo)
            return getGroupAttributes(object);
        if (object instanceof Usuario)
            return getUserAttributes(object);
        throw new RuntimeException("Invalid Model Object");
    }
    
    private static ArrayList<String> getItemAttributes(Object object){
        ArrayList<String> attributes = new ArrayList<>();
        Item item = (Item) object;
        attributes.add((Integer.toString(item.getId())));
        attributes.add(item.getNome());
        attributes.add(item.getTipo());
        attributes.add(item.getSrc());
        return attributes;
    }
    
    private static ArrayList<String> getGroupAttributes(Object object){
        ArrayList<String> attributes = new ArrayList<>();
        Grupo group = (Grupo) object;
        attributes.add((Integer.toString(group.getId())));
        attributes.add(group.getNome());
        attributes.add((Integer.toString(group.getNivel())));
        attributes.add(group.getDescricao());
        return attributes;
    }
    
    private static ArrayList<String> getUserAttributes(Object object){
        ArrayList<String> attributes = new ArrayList<>();
        Usuario user = (Usuario) object;
        attributes.add((Integer.toString(user.getId())));
        attributes.add(user.getNome());
        attributes.add(user.getEmail());
        attributes.add((Integer.toString(user.getNivel_de_acesso())));
        return attributes;
    }    
}
