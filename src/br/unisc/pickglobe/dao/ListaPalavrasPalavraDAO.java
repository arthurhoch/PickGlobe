/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unisc.pickglobe.dao;

import br.unisc.pickglobe.model.ListaPalavrasPalavra;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author will
 */
public class ListaPalavrasPalavraDAO {
    
    public boolean create(Connection con, ListaPalavrasPalavra listaPalavrasPalavra) throws SQLException {
        try {
            //insert into Extensao (tipoExtensao) values ('asdas')
            String insertSQL = " insert into ListaPalavrasPalavra (ListaPalavras_Site_codSites, Palavra_codPalavras) VALUES ( ";
            insertSQL +=  listaPalavrasPalavra.getListaPalavras_Site_codSites()+", ";
            insertSQL +=  listaPalavrasPalavra.getPalavra_codPalavras()+") ";
            System.out.println(insertSQL);
            PreparedStatement stm = 
                    con.prepareStatement(insertSQL);
            
            stm.execute();
            stm.close();
            con.close();
            
            System.out.println("ListaPalavrasPalavra adicionada com sucesso!");
            return true;
        } catch (Exception e) {
            System.out.println("Exessao: "+e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean update(Connection con, ListaPalavrasPalavra newListaPalavrasPalavra, ListaPalavrasPalavra oldListaPalavrasPalavra) throws SQLException {
        try {
            String updateSQL = (" UPDATE ListaPalavrasPalavra SET ");
            updateSQL += (" ListaPalavras_Site_codSites = " + newListaPalavrasPalavra.getListaPalavras_Site_codSites()+", ");
            updateSQL += (" Palavra_codPalavras = " + newListaPalavrasPalavra.getPalavra_codPalavras());
            updateSQL += (" WHERE ListaPalavrasPalavra.ListaPalavras_Site_codSites = " + oldListaPalavrasPalavra.getListaPalavras_Site_codSites());
            updateSQL += (" AND ListaPalavrasPalavra.Palavra_codPalavras = " + oldListaPalavrasPalavra.getPalavra_codPalavras());
            System.out.println(updateSQL);
            Statement st = con.createStatement();
            st.executeUpdate(updateSQL);
            System.out.println("A ListaPalavrasPalavra foi atualizada com sucesso !");

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro ao atualizar ListaPalavrasPalavra: "+e.getMessage());
            return false;
        }
    }
}
