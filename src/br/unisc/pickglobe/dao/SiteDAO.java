/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unisc.pickglobe.dao;

import br.unisc.pickglobe.model.Site;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author will
 */
public class SiteDAO extends DAO<Site>{
    
    
    @Override
    public boolean create(Connection con, Site site) throws SQLException {
        try {
            //insert into Extensao (tipoExtensao) values ('asdas')
            String insertSQL = " insert into Site (tempoColeta, url, status, descricao) VALUES ( ";
            insertSQL +=  site.getTempoColeta()+", ";
            insertSQL +=  " '"+site.getUrl()+"', ";
            insertSQL +=  site.getStatus()+", ";
            insertSQL +=  " '"+site.getDescricao()+"') ";
            System.out.println(insertSQL);
            PreparedStatement stm = 
                    con.prepareStatement(insertSQL);
            
            stm.execute();
            stm.close();
            con.close();
            
            System.out.println("Site adicionado com sucesso!");
            return true;
        } catch (Exception e) {
            System.out.println("Exessao: "+e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Connection con, Site site) throws SQLException {
        try {
            String updateSQL = (" UPDATE Site SET ");
            updateSQL += (" tempoColeta = " + site.getTempoColeta()+", ");
            updateSQL += (" url = " + "'"+site.getUrl()+"', ");
            updateSQL += (" status = " + site.getStatus() +", ");
            updateSQL += (" descricao = " + "'"+site.getDescricao()+"' ");
            updateSQL += (" WHERE Site.codSites = " + site.getCodSites());
            System.out.println(updateSQL);
            Statement st = con.createStatement();
            st.executeUpdate(updateSQL);
            System.out.println("A "+site.getDescricao()+ " foi atualizada com sucesso !");

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro ao atualizar o site: "+e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(Connection con, Site site) throws SQLException {
        try {
            String deleteSQL = "DELETE FROM Site WHERE codSites = "+site.getCodSites();
            System.out.println(deleteSQL);
            Statement st = con.createStatement();
            st.executeUpdate(deleteSQL);
            System.out.println("O "+site.getDescricao()+ " foi removido com sucesso!");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro ao remover o site: "+e.getMessage());
            return false;
        }
        
        
    }
    
    @Override
    public Object get(Connection con, int codSite) throws SQLException {
        try {
            String selectSQL = "SELECT * FROM Site ";
            selectSQL += "WHERE Site.codSites = " + codSite;
            System.out.println(selectSQL);
            
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(selectSQL);
            Site site = new Site();
            rs.next();
            
            site.setCodSites(rs.getInt("codSites"));
            site.setTempoColeta(rs.getTimestamp("tempoColeta"));
            site.setUrl(rs.getString("url"));
            site.setStatus(rs.getInt("status"));
            site.setDescricao(rs.getString("descricao"));


            rs.close();
            st.close();
            con.close();

            return site;
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Excessao ao buscar o site: "+e.getMessage());
            return null;
        }
    }
    
    public Object get(Connection con, String URL) throws SQLException {
        try {
            String selectSQL = "SELECT * FROM Site ";
            selectSQL += "WHERE Site.url = " + URL;
            System.out.println(selectSQL);
            
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(selectSQL);
            Site site = new Site();
            rs.next();
            
            site.setCodSites(rs.getInt("codSites"));
            site.setTempoColeta(rs.getTimestamp("tempoColeta"));
            site.setUrl(rs.getString("url"));
            site.setStatus(rs.getInt("status"));
            site.setDescricao(rs.getString("descricao"));


            rs.close();
            st.close();
            con.close();

            return site;
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Excessao ao buscar o site: "+e.getMessage());
            return null;
        }
    }
    
    public ArrayList<Site> listSite(Connection con){
        try {
            ArrayList<Site> listaExtensao = new ArrayList<>();
            
            String selectSQL = "SELECT * FROM Site ";
            System.out.println(selectSQL);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(selectSQL);

            while (rs.next()) {
                Site site = new Site();
                
                site.setCodSites(rs.getInt("codSites"));
                site.setTempoColeta(rs.getTimestamp("tempoColeta"));
                site.setUrl(rs.getString("url"));
                site.setStatus(rs.getInt("status"));
                site.setDescricao(rs.getString("descricao"));

                listaExtensao.add(site);
            }
            
            
            rs.close();
            st.close();
            con.close();
            return listaExtensao;
        } catch (Exception e) {
            return null;
        }
    }
}
