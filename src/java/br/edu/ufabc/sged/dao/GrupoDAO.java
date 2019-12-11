/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ufabc.sged.dao;

import br.edu.ufabc.sged.model.Grupo;
import br.edu.ufabc.sged.model.Usuario;
import br.edu.ufabc.sged.util.LOGMessage;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Caique de Camargo
 */
public class GrupoDAO implements GenericDAO{
    private final DataSource dataSource;
    private static final String INSERT_SQL = "INSERT INTO tblgrupo values (null, ?, ?, ?)";
    private static final String UPDATE_SQL = "UPDATE tblgrupo   SET nome = ?, descricao = ?, nivel = ? WHERE idGrupo = ?";
    private static final String DELETE_SQL = "DELETE FROM tblgrupo where idGrupo = ?";
    private static final String READ_SQL   = "SELECT * FROM tblgrupo where idGrupo = ?";
    private static final String DELETE_RELATIONSHIP_SQL = "DELETE FROM tblusuariogrupo where idGrupo = ?";
    private static final String READ_MEMBERS_SQL = "SELECT tblusuario.idUsuario, tblusuario.nome, tblusuario.email, tblusuario.nivelDeAcesso, tblusuario.situacao FROM tblusuariogrupo INNER JOIN tblusuario ON tblusuariogrupo.idUsuario = tblusuario.idUsuario WHERE tblusuariogrupo.idGrupo = ?";
    
    public GrupoDAO (DataSource dataSource){
        this.dataSource = dataSource;
    }

    @Override
    public void create(Object o) throws RuntimeException, SQLException {
        if(o instanceof Grupo){
            try (PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {
                Grupo grupo = (Grupo) o;
                preparedStatement.setString(1, grupo.getNome());
                preparedStatement.setString(2, grupo.getDescricao());
                preparedStatement.setInt(3, grupo.getNivel());
                int resultQuery = preparedStatement.executeUpdate();
                if (resultQuery != 0){
                    try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                        if (resultSet.next()) grupo.setId(resultSet.getInt(1));
                    }
                }
            }
        } else {
            throw new RuntimeException(LOGMessage.getInvalidModelObjectMessage("Group"));
        }
    }

    @Override
    public void update(Object o) throws RuntimeException, SQLException{
        if(o instanceof Grupo){
            Grupo grupo = (Grupo) o;
            try(PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(UPDATE_SQL)){
                preparedStatement.setString(1, grupo.getNome());
                preparedStatement.setString(2, grupo.getDescricao());
                preparedStatement.setInt(3, grupo.getNivel());
                preparedStatement.setInt(4, grupo.getId());
                preparedStatement.executeUpdate();
            }
        } else {
            throw new RuntimeException(LOGMessage.getInvalidModelObjectMessage("Group"));
        }
    }

    @Override
    public void delete(Object o) throws RuntimeException, SQLException {
        if(o instanceof Grupo){
            Grupo grupo = (Grupo) o;
            try (PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(DELETE_SQL)) {
                preparedStatement.setInt(1, grupo.getId());
                preparedStatement.executeUpdate();
            }
        } else {
            throw new RuntimeException(LOGMessage.getInvalidModelObjectMessage("Group"));
        }
    }

    @Override
    public List<Object> read(Object o) throws RuntimeException, SQLException {
        if(o instanceof Grupo){
            Grupo grupo = (Grupo) o;
            try(PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(READ_SQL)){
                preparedStatement.setInt(1, grupo.getId());
                try(ResultSet resultSet = preparedStatement.executeQuery()){
                    ArrayList<Object> listGroups = new ArrayList<>();
                    while(resultSet.next()){
                        Grupo readGroup = new Grupo();
                        readGroup.setId(resultSet.getInt("idGrupo"));
                        readGroup.setNome(resultSet.getString("nome"));
                        readGroup.setDescricao(resultSet.getString("descricao"));
                        readGroup.setNivel(resultSet.getInt("nivel"));
                        listGroups.add(readGroup);
                    }
                    return listGroups;
                }
            }
        } else {
            throw new RuntimeException(LOGMessage.getInvalidModelObjectMessage("Group"));
        }
    }

    public void deleteRelationship(Object o) throws RuntimeException, SQLException{
        if(o instanceof Grupo){
            Grupo grupo = (Grupo) o;
            try (PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(DELETE_RELATIONSHIP_SQL)) {
                preparedStatement.setInt(1, grupo.getId());
                preparedStatement.executeUpdate();
            }
        } else {
            throw new RuntimeException(LOGMessage.getInvalidModelObjectMessage("Group"));
        }
    }
    
    public List<Object> readMembers(Object o) throws RuntimeException, SQLException{
        if(o instanceof Grupo){
            Grupo grupo = (Grupo) o;
            try (PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(READ_MEMBERS_SQL)) {
                preparedStatement.setInt(1, grupo.getId());
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    ArrayList<Object> listMembers = new ArrayList<>();
                    while(resultSet.next()){
                        Usuario user = new Usuario();
                        user.setId(resultSet.getInt("idUsuario"));
                        user.setNome(resultSet.getString("nome"));
                        user.setEmail(resultSet.getString("email"));
                        user.setNivel_de_acesso(resultSet.getInt("nivelDeAcesso"));
                        user.setSituacao(resultSet.getInt("situacao"));
                        listMembers.add(user);
                    }
                    return listMembers;
                }
            }
        } else {
            throw new RuntimeException(LOGMessage.getInvalidModelObjectMessage("Group"));
        }
    }
}
