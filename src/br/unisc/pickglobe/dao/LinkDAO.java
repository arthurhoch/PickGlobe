/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unisc.pickglobe.dao;

import br.unisc.pickglobe.model.Link;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author arthurhoch
 */
public class LinkDAO extends DAO<Link> {

    @Override
    public boolean create(Connection con, Link link) throws SQLException {
        try {
            String insertSQL = " insert into Link values ( ";
            insertSQL += "url, " + link.getUrl();
            insertSQL += "caminho)" + link.getCaminho();
            
            try (PreparedStatement stm = con.prepareStatement(insertSQL)) {
                stm.execute();
            }
            con.close();
            
            System.out.println("Link adicionada com sucesso!");
            return true;
        } catch (Exception e) {
            System.out.println("Link: "+e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Connection con, Link link) throws SQLException {
        try {
            String updateSQL = (" UPDATE Link SET ");
            updateSQL += (" url" + link.getUrl());
            updateSQL += (" WHERE link.url = " + link.getUrl());

            Statement st = con.createStatement();
            st.executeUpdate(updateSQL);
            con.commit();
            System.out.println("A "+link.getUrl()+ " foi atualizada com sucesso!");

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro ao atualizar a link: "+e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(Connection con, Link link) throws SQLException {
        try {
            String deleteSQL = "DELETE FROM Link WHERE url = "+link.getCaminho();
            Statement st = con.createStatement();
            st.executeUpdate(deleteSQL);
            con.commit();
            System.out.println("A "+link.getUrl()+ " foi removida com sucesso!");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro ao remover a link: "+e.getMessage());
            return false;
        }
    }

    @Override
    public Object get(Connection con, Link link) throws SQLException {
          try {
            ArrayList<Link> listLink = new ArrayList<>();

            if (link.getUrl() != null){
                String selectSQL = "SELECT * FROM Link";
                selectSQL += "WHERE Link.url LIKE "+ link.getUrl();

                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(selectSQL);


                while (rs.next()) {
                    Link l = new Link();
                    l.setUrl(rs.getString("url"));
                    l.setCaminho("caminho");
                    
                    listLink.add(l);
                }
                
                rs.close();
                st.close();
                con.close();
            }else {
                String selectSQL = "SELECT * FROM Link";

                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(selectSQL);

                while (rs.next()) {
                    Link l = new Link();
                    l.setUrl(rs.getString("url"));
                    l.setCaminho("caminho");
                    
                    listLink.add(l);
                }
                
                rs.close();
                st.close();
                con.close();
            }
            return listLink;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro ao buscar a link: "+e.getMessage());
            return false;
        }
    }
    
}
