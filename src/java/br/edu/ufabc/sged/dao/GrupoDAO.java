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
import java.util.List;

/**
 *
 * @author Caique de Camargo
 */
public class GrupoDAO implements GenericDAO{
    private DataSource dataSource;
    
    public GrupoDAO (DataSource dataSource){
        this.dataSource = dataSource;
    }

    @Override
    public void create(Object o) {
        try {
            if(o instanceof Grupo){
                String SQL = "INSERT INTO tblgrupo values (null, ?, ?, ?)";
                PreparedStatement stm = dataSource.getConnection().prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
                Grupo grupo = (Grupo) o;
                stm.setString(1, grupo.getNome());
                stm.setString(2, grupo.getDescricao());
                stm.setInt(3, grupo.getNivel());
                int res = stm.executeUpdate();
                if (res != 0){
                    ResultSet rs = stm.getGeneratedKeys();
                    if (rs.next()){
                        grupo.setId(rs.getInt(1));
                    }
                    rs.close();
                }
                stm.close();
            } else {
                throw new RuntimeException("Invalid Group Model Object");
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao inserir grupo "+ex.getMessage() + " " + ex.getErrorCode());
            throw new RuntimeException("Erro ao adicionar grupo, contate administrador do sistema");
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
    
    public Object readOnly(Object o){
        try {
            if(o instanceof Grupo){
                Grupo grupo = (Grupo) o;
                String SQL = "SELECT * FROM tblgrupo WHERE idGrupo = ?";
                PreparedStatement stm = dataSource.getConnection().prepareStatement(SQL);
                stm.setInt(1, grupo.getId());
                ResultSet rs = stm.executeQuery();
                if(rs.next()){
                    grupo.setNome(rs.getString("nome"));
                    grupo.setDescricao(rs.getString("descricao"));
                    grupo.setNivel(rs.getInt("nivel"));
                }
                stm.close();
                return grupo;
            } else {
                throw new RuntimeException("Invalid Group Model Object");
            }
        } catch (SQLException ex) {
            System.err.println("Erro ao recuperar Grupo. "+ex.getMessage());
            throw new RuntimeException("Erro ao recuperar Grupo.");
        }
    }
    
}
