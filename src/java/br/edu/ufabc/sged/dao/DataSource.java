/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ufabc.sged.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Caique de Camargo
 */
public class DataSource {
    private String hostname;
    private String username;
    private String password;
    private String database;
    private Connection connection;
    
    public DataSource() throws SQLException{
        hostname = "localhost";
        database = "sged";
        username = "sged";
        password = "CaiqueMetal199#";
        String URL = "jdbc:mysql://"+hostname+":3306/"+database+"?useTimezone=true&serverTimezone=UTC";
        DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        connection = DriverManager.getConnection(URL, username, password);
    }
    
    public Connection getConnection(){
        return this.connection;
    }
}
