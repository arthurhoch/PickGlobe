
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
 * @author will
 */
public class LinkDAO extends DAO<Link>{

    @Override
    public boolean create(Connection con, Link link) throws SQLException {
        try {
            //insert into Extensao (tipoExtensao) values ('asdas')
            String insertSQL = " insert into link (url, caminho) VALUES (";
            insertSQL +=  "'"+link.getUrl()+"', ";
            insertSQL +=  "'"+link.getCaminho()+"')";
            System.out.println(insertSQL);
            PreparedStatement stm = 
                    con.prepareStatement(insertSQL);
            
            stm.execute();
            stm.close();
            con.close();
            
            System.out.println("Link adicionado com sucesso!");
            return true;
        } catch (Exception e) {
            System.out.println("Exessao: "+e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Connection con, Link link) throws SQLException {
        try {
            String updateSQL = (" UPDATE link SET ");
            updateSQL += (" url = " + "'"+link.getUrl()+"', ");
            updateSQL += (" caminho = " + "'"+link.getCaminho()+"', ");
            updateSQL += (" WHERE link.codLink = " + link.getCodLink());
            System.out.println(updateSQL);
            Statement st = con.createStatement();
            st.executeUpdate(updateSQL);
            System.out.println("A "+link.getUrl()+ " foi atualizada com sucesso para !");

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro ao atualizar o link: "+e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(Connection con, Link link) throws SQLException {
        try {
            String deleteSQL = "DELETE FROM link WHERE codLink = "+link.getCodLink();
            System.out.println(deleteSQL);
            Statement st = con.createStatement();
            st.executeUpdate(deleteSQL);
            System.out.println("O link "+link.getUrl()+ " foi removido com sucesso!");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro ao remover o link: "+e.getMessage());
            return false;
        }
        
        
    }
    
    @Override
    public Object get(Connection con, int codLink) throws SQLException {
        try {
            String selectSQL = "SELECT * FROM link ";
            selectSQL += "WHERE link.codLink = " + codLink;
            System.out.println(selectSQL);
            
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(selectSQL);
            Link link = new Link();
            rs.next();
            
            link.setCodLink(rs.getInt("codLink"));
            link.setUrl(rs.getString("url"));
            link.setCaminho(rs.getString("caminho"));


            rs.close();
            st.close();
            con.close();

            return link;
            
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Excessao ao buscar o link: "+e.getMessage());
            return null;
        }
    }
    
    public ArrayList<Link> listLink(Connection con){
        try {
            ArrayList<Link> listaLink = new ArrayList<>();
            
            String selectSQL = "SELECT * FROM Link ";
            System.out.println(selectSQL);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(selectSQL);

            while (rs.next()) {
                Link link = new Link();
                link.setCodLink(rs.getInt("codLink"));
                link.setUrl(rs.getString("url"));
                link.setCaminho(rs.getString("caminho"));

                listaLink.add(link);
            }
            
            
            rs.close();
            st.close();
            con.close();
            return listaLink;
        } catch (Exception e) {
            return null;
        }
    }
}
