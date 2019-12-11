/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ufabc.sged.dao;

import br.edu.ufabc.sged.model.Item;
import br.edu.ufabc.sged.util.LOGMessage;
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
    private final DataSource dataSource;
    private static final String CREATE_SQL = "INSERT INTO tblitem VALUES (null, ?, ?, ?, ?)";
    private static final String DELETE_SQL = "DELETE FROM tblitem where idItem = ?";
    private static final String DELETE_RELATIONSHIP_SQL = "DELETE FROM tblusuarioitem where idItem = ?";
    
    public ItemDAO (DataSource dataSource){
        this.dataSource = dataSource;
    }

    @Override
    public void create(Object o) throws RuntimeException, SQLException {
        if(o instanceof Item){
            Item item = (Item) o;
            try (PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(CREATE_SQL, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, item.getTipo());
                preparedStatement.setString(2, item.getNome());
                preparedStatement.setString(3, item.getRestricoes());
                preparedStatement.setString(4, item.getSrc());
                int resultQuery = preparedStatement.executeUpdate();
                if (resultQuery != 0){
                    try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                        if (resultSet.next()) item.setId(resultSet.getInt(1));
                    }
                }
            }
        } else {
            throw new RuntimeException(LOGMessage.getInvalidModelObjectMessage("Item"));
        }
    }

    @Override
    public void update(Object o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Object o) throws RuntimeException, SQLException{
        if(o instanceof Item){
            Item item = (Item) o;
            try (PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(DELETE_SQL)) {
                preparedStatement.setInt(1, item.getId());
                preparedStatement.executeUpdate();
            }
        } else {
            throw new RuntimeException(LOGMessage.getInvalidModelObjectMessage("Item"));
        }
    }

    @Override
    public List<Object> read(Object o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void deleteRelationship(Object o) throws RuntimeException, SQLException{
        if(o instanceof Item){
            Item item = (Item) o;
            try (PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(DELETE_RELATIONSHIP_SQL)) {
                preparedStatement.setInt(1, item.getId());
                preparedStatement.executeUpdate();
            }
        } else {
            throw new RuntimeException(LOGMessage.getInvalidModelObjectMessage("Item"));
        }
    }
}
