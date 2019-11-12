/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ufabc.sged.dao;

import java.util.List;

/**
 *
 * @author Caique de Camargo
 */
public interface GenericDAO {
    public int create(Object o);
    public void update(Object o);
    public void delete(Object o);
    public List<Object> read(Object o);
}
