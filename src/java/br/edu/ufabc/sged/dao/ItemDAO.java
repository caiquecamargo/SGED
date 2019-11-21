/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ufabc.sged.dao;

import br.edu.ufabc.sged.model.Item;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 *
 * @author Caique de Camargo
 */
public class ItemDAO implements GenericDAO{
    private DataSource dataSource;
    
    public ItemDAO (DataSource dataSource){
        this.dataSource = dataSource;
    }

    @Override
    public void create(Object o) {
        try {
            if(o instanceof Item){
                Item item = (Item) o;
                String SQL = "INSERT INTO tblitem VALUES (null, ?, ?, ?, ?)";
                PreparedStatement stm = dataSource.getConnection().prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
                stm.setString(1, item.getTipo());
                stm.setString(2, item.getNome());
                stm.setString(3, item.getRestricoes());
                stm.setString(4, item.getSrc());
                int res = stm.executeUpdate();
                if (res != 0){
                    ResultSet rs = stm.getGeneratedKeys();
                    if (rs.next()){
                        item.setId(rs.getInt(1));
                    }
                    rs.close();
                }
                stm.close();
            } else {
                throw new RuntimeException("Invalid Item Model Object");
            }
        } catch (SQLException ex) {
            System.err.println("Erro ao inserir item "+ex.getMessage() + " " + ex.getErrorCode());
            throw new RuntimeException("Erro ao inserir item, contate o administrador do sistema");
        }
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
