
package br.unisc.pickglobe.dao;

import br.unisc.pickglobe.model.ColetaLink;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author will
 */
public class ColetaLinkDAO {
    
    
    public boolean create(Connection con, ColetaLink coletaLink) throws SQLException {
        try {
            //insert into Extensao (tipoExtensao) values ('asdas')
            String insertSQL = " insert into ColetaLink (Coleta_codColeta, Link_codLink) VALUES ( ";
            insertSQL +=  coletaLink.getColeta_codColeta()+", ";
            insertSQL +=  coletaLink.getLink_codLink()+") ";
            System.out.println(insertSQL);
            PreparedStatement stm = 
                    con.prepareStatement(insertSQL);
            
            stm.execute();
            stm.close();
            con.close();
            
            System.out.println("ColetaLink adicionada com sucesso!");
            return true;
        } catch (Exception e) {
            System.out.println("Exessao: "+e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean update(Connection con, ColetaLink newColetaLink, ColetaLink oldColetaLink) throws SQLException {
        try {
            String updateSQL = (" UPDATE ColetaLink SET ");
            updateSQL += (" Coleta_codColeta = " + newColetaLink.getColeta_codColeta()+", ");
            updateSQL += (" Link_codLink = " + newColetaLink.getLink_codLink());
            updateSQL += (" WHERE ColetaLink.Coleta_codColeta = " + oldColetaLink.getColeta_codColeta());
            updateSQL += (" AND ColetaLink.Link_codLink = " + oldColetaLink.getLink_codLink());
            System.out.println(updateSQL);
            Statement st = con.createStatement();
            st.executeUpdate(updateSQL);
            System.out.println("A ColetaLink foi atualizada com sucesso !");

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro ao atualizar ColetaLink: "+e.getMessage());
            return false;
        }
    }
}
