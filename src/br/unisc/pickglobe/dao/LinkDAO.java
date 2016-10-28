/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unisc.pickglobe.dao;

import br.unisc.pickglobe.model.Link;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author arthurhoch
 */
public class LinkDAO extends DAO<Link> {

    @Override
    public boolean create(Connection con, Link Link) throws SQLException {
        try {
            String insertSQL = " insert into Link values ( ";
            insertSQL += "codExtensao, " + Link.getCodExtensao();
            insertSQL += "tipoExtensao)" + Link.getCodExtensao();
            
            PreparedStatement stm = 
                    con.prepareStatement(insertSQL);
            
            stm.execute();
            stm.close();
            con.close();
            
            System.out.println("Extensao adicionada com sucesso!");
            return true;
        } catch (Exception e) {
            System.out.println("Exessao: "+e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Connection con, Link objeto) throws SQLException {
    }

    @Override
    public boolean delete(Connection con, Link objeto) throws SQLException {
    }

    @Override
    public Object get(Connection con, Link objeto) throws SQLException {
    }
    
}
