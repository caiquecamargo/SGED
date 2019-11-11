/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ufabc.sged.dao;

import br.edu.ufabc.sged.model.Doc;
import br.edu.ufabc.sged.model.Item;
import br.edu.ufabc.sged.model.Music;
import br.edu.ufabc.sged.model.PDF;
import br.edu.ufabc.sged.model.Usuario;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Caique de Camargo
 */
public class UsuarioDAO implements GenericDAO{

    @Override
    public void create(Object o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Object o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Object o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Object> read(Object o) {
        Usuario usuario = new Usuario();
        usuario.setId(1);
        usuario.setEmail("caique.de.camargo@hotmail.com");
        usuario.setNome("Caique de Camargo");
        usuario.setPassword("1234");
        usuario.setGrupo("Admin");
        usuario.setNivel_de_acesso(0);
        
        ArrayList<Item> itens = new ArrayList<Item>();
        Item pdf = new PDF();
        pdf.setId(1);
        pdf.setNome("Descricao do site");
        pdf.setRestricoes("");
        pdf.setSrc("arquivos/descricao.pdf");
        itens.add(pdf);
        
        Item music = new Music();
        music.setId(2);
        music.setNome("Symphony of Destruction");
        music.setRestricoes("");
        music.setSrc("music/symphony_of_destruction.mp3");
        itens.add(music);
        
        Item doc = new Doc();
        doc.setId(3);
        doc.setNome("Formulario");
        doc.setRestricoes("");
        doc.setSrc("arquivos/formulario.doc");
        itens.add(doc);
        
        usuario.setItens(itens);
        
        ArrayList<Object> resultado = new ArrayList<Object>();
        resultado.add(resultado);
        
        return resultado;
    }
    
}
