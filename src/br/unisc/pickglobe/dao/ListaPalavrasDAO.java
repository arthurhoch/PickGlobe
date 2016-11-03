/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unisc.pickglobe.dao;

import br.unisc.pickglobe.model.ListaPalavras;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author will
 */
public class ListaPalavrasDAO {
    
    public boolean create(Connection con, ListaPalavras listaPalavras) throws SQLException {
        try {
            //insert into Extensao (tipoExtensao) values ('asdas')
            String insertSQL = " insert into ListaPalavras (Site_codSites) VALUES ( ";
            insertSQL +=  listaPalavras.getSite_codSites()+") ";
            System.out.println(insertSQL);
            PreparedStatement stm = 
                    con.prepareStatement(insertSQL);
            
            stm.execute();
            stm.close();
            con.close();
            
            System.out.println("ListaPalavras adicionada com sucesso!");
            return true;
        } catch (Exception e) {
            System.out.println("Exessao: "+e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean update(Connection con, ListaPalavras newListaPalavras, ListaPalavras oldListaPalavras) throws SQLException {
        try {
            String updateSQL = (" UPDATE ListaPalavras SET ");
            updateSQL += (" Site_codSites = " + newListaPalavras.getSite_codSites());
            updateSQL += (" WHERE ListaPalavras.Site_codSites = " + oldListaPalavras.getSite_codSites());
            System.out.println(updateSQL);
            Statement st = con.createStatement();
            st.executeUpdate(updateSQL);
            System.out.println("A ListaPalavras foi atualizada com sucesso !");

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro ao atualizar ListaPalavras: "+e.getMessage());
            return false;
        }
    }
}
