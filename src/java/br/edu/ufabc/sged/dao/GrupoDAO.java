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
public class GrupoDAO implements GenericDAO{
    private final DataSource dataSource;
    
    public GrupoDAO (DataSource dataSource){
        this.dataSource = dataSource;
    }

    @Override
    public void create(Object o) {
        try {
            if(o instanceof Grupo){
                String SQL = "INSERT INTO tblgrupo values (null, ?, ?, ?)";
                try (PreparedStatement stm = dataSource.getConnection().prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {
                    Grupo grupo = (Grupo) o;
                    stm.setString(1, grupo.getNome());
                    stm.setString(2, grupo.getDescricao());
                    stm.setInt(3, grupo.getNivel());
                    int res = stm.executeUpdate();
                    if (res != 0){
                        try (ResultSet rs = stm.getGeneratedKeys()) {
                            if (rs.next()){
                                grupo.setId(rs.getInt(1));
                            }
                        }
                    }
                }
            } else {
                throw new RuntimeException("Invalid Group Model Object");
            }
        } catch (SQLException ex) {
            System.err.println("Erro ao inserir grupo "+ex.getMessage() + " " + ex.getErrorCode());
            throw new RuntimeException("Erro ao adicionar grupo, contate administrador do sistema");
        }
    }

    @Override
    public void update(Object o) {
        try {
            if(o instanceof Grupo){
                Grupo grupo = (Grupo) o;
                String SQL = "UPDATE tblgrupo   SET nome = ?, descricao = ?, nivel = ? WHERE idGrupo = ?";
                try(PreparedStatement stm = dataSource.getConnection().prepareStatement(SQL)){
                    stm.setString(1, grupo.getNome());
                    stm.setString(2, grupo.getDescricao());
                    stm.setInt(3, grupo.getNivel());
                    stm.setInt(4, grupo.getId());
                    stm.executeUpdate();
                }
            } else {
                throw new RuntimeException("Invalid Group Model Object");
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            throw new RuntimeException("Erro ao ler Grupo. Contate administrador do sistema");
        }
    }

    @Override
    public void delete(Object o) {
        try {
            if(o instanceof Grupo){
                Grupo grupo = (Grupo) o;
                String SQL = "DELETE FROM tblgrupo where idGrupo = ?";
                try (PreparedStatement stm = dataSource.getConnection().prepareStatement(SQL)) {
                    stm.setInt(1, grupo.getId());
                    stm.executeUpdate();
                }
            } else {
                throw new RuntimeException("Invalid Grupo Model Object");
            }
        } catch (SQLException ex) {
            System.err.println("Erro ao deletar grupo "+ex.getMessage() + " " + ex.getErrorCode());
            throw new RuntimeException("Erro ao deletar grupo, contate administrador do sistema");
        }
    }

    @Override
    public List<Object> read(Object o) {
        try {
            if(o instanceof Grupo){
                Grupo grupo = (Grupo) o;
                String SQL = "SELECT * FROM tblgrupo where idGrupo = ?";
                try(PreparedStatement stm = dataSource.getConnection().prepareStatement(SQL)){
                    stm.setInt(1, grupo.getId());
                    try(ResultSet rs = stm.executeQuery()){
                        ArrayList<Object> result = new ArrayList<>();
                        while(rs.next()){
                            Grupo g = new Grupo();
                            g.setId(rs.getInt("idGrupo"));
                            g.setNome(rs.getString("nome"));
                            g.setDescricao(rs.getString("descricao"));
                            g.setNivel(rs.getInt("nivel"));
                            result.add(g);
                        }
                        return result;
                    }
                }
            } else {
                throw new RuntimeException("Invalid Group Model Object");
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            throw new RuntimeException("Erro ao ler Grupo. Contate administrador do sistema");
        }
    }

    public void deleteRelationship(Object o){
        try {
            if(o instanceof Grupo){
                Grupo grupo = (Grupo) o;
                String SQL = "DELETE FROM tblusuariogrupo where idGrupo = ?";
                PreparedStatement stm = dataSource.getConnection().prepareStatement(SQL);
                stm.setInt(1, grupo.getId());
                stm.executeUpdate();
                stm.close();
            } else {
                throw new RuntimeException("Invalid Grupo Model Object");
            }
        } catch (SQLException ex) {
            System.err.println("Erro ao deletar grupo "+ex.getMessage() + " " + ex.getErrorCode());
            throw new RuntimeException("Erro ao deletar grupo, contate administrador do sistema");
        }
    }
    
    public List<Object> readMembers(Object o){
        try {
            if(o instanceof Grupo){
                Grupo grupo = (Grupo) o;
                String SQL = "SELECT tblusuario.idUsuario, tblusuario.nome, tblusuario.email, tblusuario.nivelDeAcesso, tblusuario.situacao FROM tblusuariogrupo INNER JOIN tblusuario ON tblusuariogrupo.idUsuario = tblusuario.idUsuario WHERE tblusuariogrupo.idGrupo = ?";
                ArrayList<Object> result;
                try (PreparedStatement stm = dataSource.getConnection().prepareStatement(SQL)) {
                    stm.setInt(1, grupo.getId());
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
                throw new RuntimeException("Invalid Group Model Object");
            }
        } catch (SQLException ex) {
            System.err.println("Erro ao recuperar usuarios do grupo. "+ex.getMessage());
            throw new RuntimeException("Erro ao recuperar usuarios do grupo.");
        }
    }
}
