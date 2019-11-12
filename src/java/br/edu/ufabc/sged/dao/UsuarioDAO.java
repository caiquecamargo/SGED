/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ufabc.sged.dao;

import br.edu.ufabc.sged.model.Usuario;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import sun.jvm.hotspot.utilities.soql.SOQLException;

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
                String SQL = "INSERT INTO tblUsuario values (null, ?, ?, ?, null)";
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
            System.out.println("Erro ao inserir usuário "+ex.getMessage());
        }
    }
    
    @Override
    public List<Object> read(Object o) {
        try{
            if(o instanceof Usuario){
                Usuario incompleto = (Usuario) o;
                String SQL = "SELECT * FROM tblUsuario WHERE email = ? AND senha = ?";
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
    
}
