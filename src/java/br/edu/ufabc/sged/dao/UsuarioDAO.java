/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ufabc.sged.dao;

import br.edu.ufabc.sged.model.Grupo;
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
    private DataSource dataSource;
    
    public UsuarioDAO (DataSource dataSource){
        this.dataSource = dataSource;
    }
    
    @Override
    public void create(Object o) {
        try {
            if(o instanceof Usuario){
                String SQL = "INSERT INTO tblusuario values (null, ?, ?, ?, null, 0)";
                PreparedStatement stm = dataSource.getConnection().prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
                Usuario usuario = (Usuario) o;
                stm.setString(1, usuario.getNome());
                stm.setString(2, usuario.getEmail());
                stm.setString(3, usuario.getSenha());
                int res = stm.executeUpdate();
                if (res != 0){
                    ResultSet rs = stm.getGeneratedKeys();
                    if (rs.next()){
                        usuario.setId(rs.getInt(1));
                    }
                    rs.close();
                }
                stm.close();
            } else {
                throw new RuntimeException("Invalid User Model Object");
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao inserir usuário "+ex.getMessage() + " " + ex.getErrorCode());
            if (ex.getErrorCode() == 1062)throw new RuntimeException("Usuário já existe");
            throw new RuntimeException("Erro ao adicionar usuário, contate administrador do sistema");
        }
    }
    
    @Override
    public List<Object> read(Object o) {
        try{
            if(o instanceof Usuario){
                Usuario incompleto = (Usuario) o;
                String SQL = "SELECT * FROM tblusuario WHERE email = ? AND senha = ?";
                PreparedStatement stm = dataSource.getConnection().prepareStatement(SQL);
                stm.setString(1, incompleto.getEmail());
                stm.setString(2, incompleto.getSenha());
                ResultSet rs = stm.executeQuery();
                ArrayList<Object> result = new ArrayList<Object>();
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
                stm.close();
                rs.close();
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
    
    public List<Object> readNotSettedUsers(Object o){
        try {
            if (o instanceof Usuario){
                Usuario user = (Usuario) o;
                String SQL = "SELECT * FROM tblusuario WHERE situacao = 0";
                PreparedStatement stm = dataSource.getConnection().prepareStatement(SQL);
                ResultSet rs = stm.executeQuery();
                ArrayList<Object> result = new ArrayList<Object>();
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
                }
                stm.close();
                rs.close();
                return result;
            } else {
                throw new RuntimeException("Invalid object");
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao recuperar Usuario - "+ex.getMessage());
        }
        return null;
    }
    
    public void setUsuarioGrupo(Object u, Object g){
        try {
            if(u instanceof Usuario){
                Usuario user  = (Usuario) u;
                Grupo   group = (Grupo)   g;
                String SQL = "INSERT INTO tblusuariogrupo values(?, ?)";
                PreparedStatement stm = dataSource.getConnection().prepareStatement(SQL);
                stm.setInt(1, user.getId());
                stm.setInt(2, group.getId());
                stm.executeUpdate();
                stm.close();
            } else {
                throw new RuntimeException("Invalid User Model Object");
            }
        } catch (SQLException ex) {
            System.err.println("Erro ao adicionar relação UsuarioGrupo. "+ex.getMessage());
            throw new RuntimeException("Erro ao adicionar relação UsuarioGrupo");
        }
    }
    
    public List<Object> readUsuarioGrupo(Object o){
        try {
            if (o instanceof Usuario){
                Usuario usuario = (Usuario) o;
                String SQL = "SELECT * FROM tblusuariogrupo WHERE idUsuario = ?";
                PreparedStatement stm = dataSource.getConnection().prepareStatement(SQL);
                stm.setInt(1, usuario.getId());
                ResultSet rs = stm.executeQuery();
                ArrayList<Object> grupos = new ArrayList<>();
                while(rs.next()){
                    Grupo grupo = new Grupo();
                    grupo.setId(rs.getInt("idGrupo"));
                    GrupoDAO grupodao = new GrupoDAO(dataSource);
                    try {
                        grupos.add(grupodao.readOnly(grupo));
                    } catch (RuntimeException e) {
                        System.out.println(e.getMessage());
                        throw new RuntimeException(e.getMessage());
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
    
}
