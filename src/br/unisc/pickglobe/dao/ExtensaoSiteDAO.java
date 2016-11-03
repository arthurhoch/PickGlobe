
package br.unisc.pickglobe.dao;

import br.unisc.pickglobe.model.ExtensaoSite;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author will
 */
public class ExtensaoSiteDAO {
    
    public boolean create(Connection con, ExtensaoSite extensaoSite) throws SQLException {
        try {
            //insert into Extensao (tipoExtensao) values ('asdas')
            String insertSQL = " insert into ExtensaoSite (Extensoes_codExtensoes, Sites_codSites) VALUES ( ";
            insertSQL +=  extensaoSite.getExtensoes_codExtensoes()+", ";
            insertSQL +=  extensaoSite.getSites_codSites()+") ";
            System.out.println(insertSQL);
            PreparedStatement stm = 
                    con.prepareStatement(insertSQL);
            
            stm.execute();
            stm.close();
            con.close();
            
            System.out.println("ExtensaoSite adicionada com sucesso!");
            return true;
        } catch (Exception e) {
            System.out.println("Exessao: "+e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean update(Connection con, ExtensaoSite newExtensaoSite, ExtensaoSite oldExtensaoSite) throws SQLException {
        try {
            String updateSQL = (" UPDATE ExtensaoSite SET ");
            updateSQL += (" Extensoes_codExtensoes = " + newExtensaoSite.getExtensoes_codExtensoes()+", ");
            updateSQL += (" Sites_codSites = " + newExtensaoSite.getSites_codSites());
            updateSQL += (" WHERE ExtensaoSite.Extensoes_codExtensoes = " + oldExtensaoSite.getExtensoes_codExtensoes());
            updateSQL += (" AND ExtensaoSite.Sites_codSites = " + oldExtensaoSite.getSites_codSites());
            System.out.println(updateSQL);
            Statement st = con.createStatement();
            st.executeUpdate(updateSQL);
            System.out.println("A ExtensaoSite foi atualizada com sucesso !");

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro ao atualizar ExtensaoSite"+e.getMessage());
            return false;
        }
    }
}
