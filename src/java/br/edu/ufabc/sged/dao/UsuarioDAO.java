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
    
    public UsuarioDAO (DataSource dataSource){
        this.dataSource = dataSource;
    }
    
    @Override
    public void create(Object o) {
        try {
            if(o instanceof Usuario){
                String SQL = "INSERT INTO tblusuario values (null, ?, ?, ?, null, 0)";
                try (PreparedStatement stm = dataSource.getConnection().prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {
                    Usuario usuario = (Usuario) o;
                    stm.setString(1, usuario.getNome());
                    stm.setString(2, usuario.getEmail());
                    stm.setString(3, usuario.getSenha());
                    int res = stm.executeUpdate();
                    if (res != 0){
                        try (ResultSet rs = stm.getGeneratedKeys()) {
                            if (rs.next()){
                                usuario.setId(rs.getInt(1));
                            }
                        }
                    }
                }
            } else {
                throw new RuntimeException("Invalid User Model Object");
            }
        } catch (SQLException ex) {
            System.err.println("Erro ao inserir usuário "+ex.getMessage());
            if (ex.getErrorCode() == 1062)throw new RuntimeException("Usuário já existe");
            throw new RuntimeException("Erro ao adicionar usuário, contate administrador do sistema");
        }
    }
    
    @Override
    public List<Object> read(Object o) {
        try{
            if(o instanceof Usuario){
                Usuario incompleto = (Usuario) o;
                String SQL = "SELECT * FROM tblusuario WHERE idUsuario = ?";
                ArrayList<Object> result;
                try (PreparedStatement stm = dataSource.getConnection().prepareStatement(SQL)) {
                    stm.setInt(1, incompleto.getId());
                    try (ResultSet rs = stm.executeQuery()) {
                        result = new ArrayList<>();
                        if (rs.next()){
                            Usuario usuario = new Usuario();
                            usuario.setId(rs.getInt("idUsuario"));
                            usuario.setNome(rs.getString("nome"));
                            usuario.setEmail(rs.getString("email"));
                            usuario.setSenha(rs.getString("senha"));
                            usuario.setNivel_de_acesso(rs.getInt("nivelDeAcesso"));
                            usuario.setSituacao(rs.getInt("situacao"));
                            result.add(usuario);
                        }
                    }
                }
                return result;
            }else{
                throw new RuntimeException("Invalid Object");
            }
        }catch(SQLException ex){
            System.out.println("Erro ao recuperar Usuario - "+ex.getMessage());
        }
        return null;
    }

    @Override
    public void update(Object o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Object o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public List<Object> login(Object o) {
        try{
            if(o instanceof Usuario){
                Usuario incompleto = (Usuario) o;
                String SQL = "SELECT * FROM tblusuario WHERE email = ? AND senha = ?";
                ArrayList<Object> result;
                try (PreparedStatement stm = dataSource.getConnection().prepareStatement(SQL)) {
                    stm.setString(1, incompleto.getEmail());
                    stm.setString(2, incompleto.getSenha());
                    try (ResultSet rs = stm.executeQuery()) {
                        result = new ArrayList<>();
                        if (rs.next()){
                            Usuario usuario = new Usuario();
                            usuario.setId(rs.getInt("idUsuario"));
                            usuario.setNome(rs.getString("nome"));
                            usuario.setEmail(rs.getString("email"));
                            usuario.setSenha(rs.getString("senha"));
                            usuario.setNivel_de_acesso(rs.getInt("nivelDeAcesso"));
                            usuario.setSituacao(rs.getInt("situacao"));
                            result.add(usuario);
                        }
                    }
                }
                return result;
            }else{
                throw new RuntimeException("Invalid Object");
            }
        }catch(SQLException ex){
            System.out.println("Erro ao recuperar Usuario - "+ex.getMessage());
        }
        return null;
    }
    
    public List<Object> readNotSettedUsers(Object o){
        try {
            if (o instanceof Usuario){
                Usuario user = (Usuario) o;
                String SQL = "SELECT * FROM tblusuario WHERE situacao = 0";
                ArrayList<Object> result;
                try (PreparedStatement stm = dataSource.getConnection().prepareStatement(SQL); ResultSet rs = stm.executeQuery()) {
                    result = new ArrayList<>();
                    while (rs.next()){
                        System.out.println("criado um usuario");
                        Usuario usuario = new Usuario();
                        usuario.setId(rs.getInt("idUsuario"));
                        usuario.setNome(rs.getString("nome"));
                        usuario.setEmail(rs.getString("email"));
                        usuario.setSenha(rs.getString("senha"));
                        usuario.setNivel_de_acesso(rs.getInt("nivelDeAcesso"));
                        usuario.setSituacao(rs.getInt("situacao"));
                        result.add(usuario);
                    }                  }
                return result;
            } else {
                throw new RuntimeException("Invalid object");
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao recuperar Usuario - "+ex.getMessage());
        }
        return null;
    }
    
    public void setGrupoFromUsuario(Object u, Object g){
        try {
            if(u instanceof Usuario && g instanceof Grupo){
                Usuario user  = (Usuario) u;
                Grupo   group = (Grupo)   g;
                String SQL = "INSERT INTO tblusuariogrupo values(?, ?)";
                try (PreparedStatement stm = dataSource.getConnection().prepareStatement(SQL)) {
                    stm.setInt(1, user.getId());
                    stm.setInt(2, group.getId());
                    stm.executeUpdate();
                }
            } else {
                throw new RuntimeException("Invalid User Model Object");
            }
        } catch (SQLException ex) {
            System.err.println("Erro ao adicionar relação UsuarioGrupo. "+ex.getMessage());
            throw new RuntimeException("Erro ao adicionar relação UsuarioGrupo");
        }
    }
    
    public List<Object> readGrupoFromUsuario(Object o){
        try {
            if (o instanceof Usuario){
                Usuario usuario = (Usuario) o;
                String SQL = "SELECT * FROM tblusuariogrupo INNER JOIN tblgrupo ON tblusuariogrupo.idGrupo = tblgrupo.idGrupo WHERE tblusuariogrupo.idUsuario = ?";
                ArrayList<Object> grupos;
                try (PreparedStatement stm = dataSource.getConnection().prepareStatement(SQL)) {
                    stm.setInt(1, usuario.getId());
                    try (ResultSet rs = stm.executeQuery()) {
                        grupos = new ArrayList<>();
                        while(rs.next()){
                            Grupo grupo = new Grupo();
                            grupo.setId(rs.getInt("idGrupo"));
                            grupo.setNome(rs.getString("nome"));
                            grupo.setDescricao(rs.getString("descricao"));
                            grupo.setNivel(rs.getInt("nivel"));
                            grupos.add(grupo);
                        }
                    }
                }
                return grupos;
            } else {
                throw new RuntimeException("Invalid User Model Object");
            }
        } catch (SQLException ex) {
            System.err.println("Erro ao recuperar grupos do usuário. "+ex.getMessage());
            throw new RuntimeException("Erro ao recuperar grupos do usuário. "+ex.getMessage());
        }
    }
    
    public List<Object> readUsuarioFromGrupo(Object o){
        try {
            if(o instanceof Usuario){
                Usuario usuario = (Usuario) o;
                String SQL = "SELECT tblusuario.idUsuario, tblusuario.nome, tblusuario.email, tblusuario.nivelDeAcesso, tblusuario.situacao FROM tblusuariogrupo INNER JOIN (SELECT tblgrupo.idGrupo FROM tblusuariogrupo INNER JOIN tblgrupo ON tblusuariogrupo.idGrupo = tblgrupo.idGrupo WHERE tblusuariogrupo.idUsuario = ?) as result on result.idGrupo = tblusuariogrupo.idGrupo INNER JOIN tblusuario on tblusuariogrupo.idUsuario = tblusuario.idUsuario WHERE tblusuario.nivelDeAcesso > ? GROUP BY tblusuario.nome";
                ArrayList<Object> result;
                try (PreparedStatement stm = dataSource.getConnection().prepareStatement(SQL)) {
                    stm.setInt(1, usuario.getId());
                    stm.setInt(2, usuario.getNivel_de_acesso());
                    try (ResultSet rs = stm.executeQuery()) {
                        result = new ArrayList<>();
                        while(rs.next()){
                            Usuario user = new Usuario();
                            user.setId(rs.getInt("idUsuario"));
                            user.setNome(rs.getString("nome"));
                            user.setEmail(rs.getString("email"));
                            user.setNivel_de_acesso(rs.getInt("nivelDeAcesso"));
                            user.setSituacao(rs.getInt("situacao"));
                            result.add(user);
                        }
                    }
                }
                return result;
            } else {
                throw new RuntimeException("Invalid User Model Object");
            }
        } catch (SQLException ex) {
            System.err.println("Erro ao recuperar usuarios dos grupos. "+ex.getMessage());
            throw new RuntimeException("Erro ao recuperar usuarios dos grupos.");
        }
    }
    
    public void setUsuarioItem(Object u, Object i){
        try{
            if(u instanceof Usuario && i instanceof Item){
                Usuario user = (Usuario) u;
                Item    item = (Item)    i;
                String SQL = "INSERT INTO tblusuarioitem VALUES (?, ?)";
                try (PreparedStatement stm = dataSource.getConnection().prepareStatement(SQL)) {
                    stm.setInt(1, user.getId());
                    stm.setInt(2, item.getId());
                    stm.executeUpdate();
                }
            } else {
                throw new RuntimeException("Invalid Model Object in Insert Relation of Usuario AND Item");
            }
        } catch (SQLException ex){
            System.err.println("Erro ao adicionar relação UsuarioItem. "+ex.getMessage());
            throw new RuntimeException("Erro ao adicionar relação UsuarioITem");
        }
    }
    
    public List<Object> readUsuarioItem(Object o){
        try {
            if (o instanceof Usuario){
                Usuario usuario = (Usuario) o;
                String SQL = "SELECT * FROM tblusuarioitem INNER JOIN tblitem ON tblusuarioitem.idItem = tblitem.idItem WHERE tblusuarioitem.idUsuario = ?";
                ArrayList<Object> items;
                try (PreparedStatement stm = dataSource.getConnection().prepareStatement(SQL)) {
                    stm.setInt(1, usuario.getId());
                    try (ResultSet rs = stm.executeQuery()) {
                        items = new ArrayList<>();
                        Item item = null;
                        while(rs.next()){
                            switch (rs.getString("tipo")){
                                case "PDF":
                                    item = new PDF();
                                    break;
                                case "DOC":
                                    item = new Doc();
                                    break;
                                case "IMAGE":
                                    item = new Image();
                                    break;
                                case "MUSIC":
                                    item = new Music();
                                    break;
                                default:
                                    throw new RuntimeException("Erro ao recuperar item");
                            }
                            item.setId(rs.getInt("idItem"));
                            item.setNome(rs.getString("nome"));
                            item.setRestricoes(rs.getString("restricoes"));
                            item.setSrc(rs.getString("src"));
                            items.add(item);
                        }
                    }
                }
                return items;
            } else {
                throw new RuntimeException("Invalid User Model Object");
            }
        } catch (SQLException ex) {
            System.err.println("Erro ao recuperar grupos do usuário. "+ex.getMessage());
            throw new RuntimeException("Erro ao recuperar grupos do usuário. "+ex.getMessage());
        }
    }
    
    public void enableUser(Object o){
        try {
            if(o instanceof Usuario){
                Usuario user = (Usuario) o;
                String SQL = "UPDATE tblusuario SET situacao = 1, nivelDeAcesso = ? WHERE idUsuario = ?";
                try (PreparedStatement stm = dataSource.getConnection().prepareStatement(SQL)) {
                    stm.setInt(1, user.getNivel_de_acesso());
                    stm.setInt(2, user.getId());
                    stm.executeUpdate();
                }
            } else {
                throw new RuntimeException("Not a valid User Object");
            }
        } catch (SQLException ex) {
            System.err.println("Erro ao habilitar usuário");
            throw new RuntimeException("Erro ao habilitar, consulte admistrador do sistema");
        }
    }
}
