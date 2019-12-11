/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ufabc.sged.dao;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Caique de Camargo
 */
public interface GenericDAO {
    public void create(Object o) throws RuntimeException, SQLException;
    public void update(Object o) throws RuntimeException, SQLException;
    public void delete(Object o) throws RuntimeException, SQLException;
    public List<Object> read(Object o) throws RuntimeException, SQLException;
}
