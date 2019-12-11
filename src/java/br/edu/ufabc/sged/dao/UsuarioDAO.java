/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ufabc.sged.dao;

import br.edu.ufabc.sged.model.Doc;
import br.edu.ufabc.sged.model.Grupo;
import br.edu.ufabc.sged.model.Image;
import br.edu.ufabc.sged.model.Item;
import br.edu.ufabc.sged.model.Music;
import br.edu.ufabc.sged.model.PDF;
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
public class UsuarioDAO implements GenericDAO{
    private final DataSource dataSource;
    private static final String CREATE_SQL = "INSERT INTO tblusuario values (null, ?, ?, ?, null, 0)";
    private static final String READ_SQL   = "SELECT * FROM tblusuario WHERE idUsuario = ?";
    private static final String UPDATE_SQL = "";
    private static final String DELETE_SQL = "";
    private static final String LOGIN_SQL  = "SELECT * FROM tblusuario WHERE email = ? AND senha = ?";
    private static final String READ_NOT_SETTED_USER_SQL = "SELECT * FROM tblusuario WHERE situacao = 0";
    private static final String SET_GRUPO_FROM_USUARIO_SQL = "INSERT INTO tblusuariogrupo values(?, ?)";
    private static final String READ_GRUPO_FROM_USUARIO_SQL = "SELECT * FROM tblusuariogrupo INNER JOIN tblgrupo ON tblusuariogrupo.idGrupo = tblgrupo.idGrupo WHERE tblusuariogrupo.idUsuario = ?";
    private static final String READ_USUARIO_FROM_GRUPO_SQL = "SELECT tblusuario.idUsuario, tblusuario.nome, tblusuario.email, tblusuario.nivelDeAcesso, tblusuario.situacao FROM tblusuariogrupo INNER JOIN (SELECT tblgrupo.idGrupo FROM tblusuariogrupo INNER JOIN tblgrupo ON tblusuariogrupo.idGrupo = tblgrupo.idGrupo WHERE tblusuariogrupo.idUsuario = ?) as result on result.idGrupo = tblusuariogrupo.idGrupo INNER JOIN tblusuario on tblusuariogrupo.idUsuario = tblusuario.idUsuario WHERE tblusuario.nivelDeAcesso > ? GROUP BY tblusuario.nome";
    private static final String SET_USUARIO_ITEM_SQL = "INSERT INTO tblusuarioitem VALUES (?, ?)";
    private static final String READ_USUARIO_ITEM_SQL = "SELECT * FROM tblusuarioitem INNER JOIN tblitem ON tblusuarioitem.idItem = tblitem.idItem WHERE tblusuarioitem.idUsuario = ?";
    private static final String ENABLE_USER_SQL = "UPDATE tblusuario SET situacao = 1, nivelDeAcesso = ? WHERE idUsuario = ?";
    
    
    public UsuarioDAO (DataSource dataSource){
        this.dataSource = dataSource;
    }
    
    @Override
    public void create(Object o) throws RuntimeException, SQLException {
        try {
            if(o instanceof Usuario){
                try (PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(CREATE_SQL, Statement.RETURN_GENERATED_KEYS)) {
                    Usuario usuario = (Usuario) o;
                    preparedStatement.setString(1, usuario.getNome());
                    preparedStatement.setString(2, usuario.getEmail());
                    preparedStatement.setString(3, usuario.getSenha());
                    int res = preparedStatement.executeUpdate();
                    if (res != 0){
                        try (ResultSet rs = preparedStatement.getGeneratedKeys()) {
                            if (rs.next()){
                                usuario.setId(rs.getInt(1));
                            }
                        }
                    }
                }
            } else {
                throw new RuntimeException(LOGMessage.getInvalidModelObjectMessage("User"));
            }
        } catch (SQLException ex) {
            if (ex.getErrorCode() == 1062)throw new RuntimeException(LOGMessage.USER_EXIST);
            throw new RuntimeException(ex.getMessage());
        }
    }
    
    @Override
    public List<Object> read(Object o) throws RuntimeException, SQLException {
        if(o instanceof Usuario){
            Usuario incompleto = (Usuario) o;
            try (PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(READ_SQL)) {
                preparedStatement.setInt(1, incompleto.getId());
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    ArrayList<Object> userList = new ArrayList<>();
                    if (resultSet.next()){
                        Usuario usuario = new Usuario();
                        usuario.setId(resultSet.getInt("idUsuario"));
                        usuario.setNome(resultSet.getString("nome"));
                        usuario.setEmail(resultSet.getString("email"));
                        usuario.setSenha(resultSet.getString("senha"));
                        usuario.setNivel_de_acesso(resultSet.getInt("nivelDeAcesso"));
                        usuario.setSituacao(resultSet.getInt("situacao"));
                        userList.add(usuario);
                    }
                    return userList;
                }
            }
        }else{
            throw new RuntimeException(LOGMessage.getInvalidModelObjectMessage("User"));
        }
    }

    @Override
    public void update(Object o) throws RuntimeException, SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Object o) throws RuntimeException, SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public List<Object> login(Object o) throws RuntimeException, SQLException {
        if(o instanceof Usuario){
            Usuario userToLogin = (Usuario) o;
            try (PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(LOGIN_SQL)) {
                preparedStatement.setString(1, userToLogin.getEmail());
                preparedStatement.setString(2, userToLogin.getSenha());
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    ArrayList<Object> result = new ArrayList<>();
                    if (resultSet.next()){
                        Usuario usuario = new Usuario();
                        usuario.setId(resultSet.getInt("idUsuario"));
                        usuario.setNome(resultSet.getString("nome"));
                        usuario.setEmail(resultSet.getString("email"));
                        usuario.setSenha(resultSet.getString("senha"));
                        usuario.setNivel_de_acesso(resultSet.getInt("nivelDeAcesso"));
                        usuario.setSituacao(resultSet.getInt("situacao"));
                        result.add(usuario);
                    }
                    return result;
                }
            }
            
        }else{
            throw new RuntimeException(LOGMessage.getInvalidModelObjectMessage("User"));
        }
    }
    
    public List<Object> readNotSettedUsers(Object o) throws RuntimeException, SQLException {
        if (o instanceof Usuario){
            Usuario user = (Usuario) o;
            try (PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(READ_NOT_SETTED_USER_SQL)) {
                ArrayList<Object> result = new ArrayList<>();
                try (ResultSet resultSet = preparedStatement.executeQuery()){
                    while (resultSet.next()){
                        Usuario usuario = new Usuario();
                        usuario.setId(resultSet.getInt("idUsuario"));
                        usuario.setNome(resultSet.getString("nome"));
                        usuario.setEmail(resultSet.getString("email"));
                        usuario.setSenha(resultSet.getString("senha"));
                        usuario.setNivel_de_acesso(resultSet.getInt("nivelDeAcesso"));
                        usuario.setSituacao(resultSet.getInt("situacao"));
                        result.add(usuario);
                    }
                    return result;
                }
            }
        } else {
            throw new RuntimeException(LOGMessage.getInvalidModelObjectMessage("User"));
        }
    }
    
    public void setGrupoFromUsuario(Object u, Object g) throws RuntimeException, SQLException {
        if(u instanceof Usuario && g instanceof Grupo){
            Usuario user  = (Usuario) u;
            Grupo   group = (Grupo)   g;
            try (PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(SET_GRUPO_FROM_USUARIO_SQL)) {
                preparedStatement.setInt(1, user.getId());
                preparedStatement.setInt(2, group.getId());
                preparedStatement.executeUpdate();
            }
        } else {
            throw new RuntimeException(LOGMessage.getInvalidModelObjectMessage("User"));
        }
    }
    
    public List<Object> readGrupoFromUsuario(Object o) throws RuntimeException, SQLException {
        if (o instanceof Usuario){
            Usuario usuario = (Usuario) o;
            try (PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(READ_GRUPO_FROM_USUARIO_SQL)) {
                preparedStatement.setInt(1, usuario.getId());
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    ArrayList<Object> gruposList = new ArrayList<>();
                    while(resultSet.next()){
                        Grupo grupo = new Grupo();
                        grupo.setId(resultSet.getInt("idGrupo"));
                        grupo.setNome(resultSet.getString("nome"));
                        grupo.setDescricao(resultSet.getString("descricao"));
                        grupo.setNivel(resultSet.getInt("nivel"));
                        gruposList.add(grupo);
                    }
                    return gruposList;
                }
            }
            
        } else {
            throw new RuntimeException(LOGMessage.getInvalidModelObjectMessage("User"));
        }
    }
    
    public List<Object> readUsuarioFromGrupo(Object o) throws RuntimeException, SQLException {
        if(o instanceof Usuario){
            Usuario usuario = (Usuario) o;
            try (PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(READ_USUARIO_FROM_GRUPO_SQL)) {
                preparedStatement.setInt(1, usuario.getId());
                preparedStatement.setInt(2, usuario.getNivel_de_acesso());
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    ArrayList<Object> usersOfGroup = new ArrayList<>();
                    while(resultSet.next()){
                        Usuario user = new Usuario();
                        user.setId(resultSet.getInt("idUsuario"));
                        user.setNome(resultSet.getString("nome"));
                        user.setEmail(resultSet.getString("email"));
                        user.setNivel_de_acesso(resultSet.getInt("nivelDeAcesso"));
                        user.setSituacao(resultSet.getInt("situacao"));
                        usersOfGroup.add(user);
                    }
                    return usersOfGroup;
                }
            }
            
        } else {
            throw new RuntimeException(LOGMessage.getInvalidModelObjectMessage("User"));
        }
    }
    
    public void setUsuarioItem(Object u, Object i) throws RuntimeException, SQLException {
        if(u instanceof Usuario && i instanceof Item){
            Usuario user = (Usuario) u;
            Item    item = (Item)    i;
            try (PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(SET_USUARIO_ITEM_SQL)) {
                preparedStatement.setInt(1, user.getId());
                preparedStatement.setInt(2, item.getId());
                preparedStatement.executeUpdate();
            }
        } else {
            throw new RuntimeException(LOGMessage.getInvalidModelObjectMessage("User or Item"));
        }
    }
    
    public List<Object> readUsuarioItem(Object o) throws RuntimeException, SQLException {
        if (o instanceof Usuario){
            Usuario usuario = (Usuario) o;
            try (PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(READ_USUARIO_ITEM_SQL)) {
                preparedStatement.setInt(1, usuario.getId());
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    ArrayList<Object> itemsList = new ArrayList<>();
                    while(resultSet.next()){
                        Item item = Item.getItemAsType(resultSet.getString("tipo"));
                        item.setId(resultSet.getInt("idItem"));
                        item.setNome(resultSet.getString("nome"));
                        item.setRestricoes(resultSet.getString("restricoes"));
                        item.setSrc(resultSet.getString("src"));
                        itemsList.add(item);
                    }
                    return itemsList;
                }
            }
        } else {
            throw new RuntimeException(LOGMessage.getInvalidModelObjectMessage("User"));
        }
    }
    
    public void enableUser(Object o) throws RuntimeException, SQLException {
        if(o instanceof Usuario){
            Usuario user = (Usuario) o;
            try (PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(ENABLE_USER_SQL)) {
                preparedStatement.setInt(1, user.getNivel_de_acesso());
                preparedStatement.setInt(2, user.getId());
                preparedStatement.executeUpdate();
            }
        } else {
            throw new RuntimeException(LOGMessage.getInvalidModelObjectMessage("User"));
        }
    }
}
