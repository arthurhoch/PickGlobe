
package br.unisc.pickglobe.dao;

import br.unisc.pickglobe.model.PalavraLink;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author will
 */
public class PalavraLinkDAO {
    
    
    public boolean create(Connection con, PalavraLink palavraLink) throws SQLException {
        try {
            //insert into Extensao (tipoExtensao) values ('asdas')
            String insertSQL = " insert into PalavraLink (Palavra_codPalavras, Link_codLink, qtd) VALUES ( ";
            insertSQL +=  palavraLink.getPalavra_codPalavras()+", ";
            insertSQL +=  palavraLink.getLink_codLink()+", ";
            insertSQL +=  palavraLink.getQtd()+") ";
            System.out.println(insertSQL);
            PreparedStatement stm = 
                    con.prepareStatement(insertSQL);
            
            stm.execute();
            stm.close();
            con.close();
            
            System.out.println("PalavraLink adicionada com sucesso!");
            return true;
        } catch (Exception e) {
            System.out.println("Exessao PalavraLink: "+e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean update(Connection con, PalavraLink newPalavraLink, PalavraLink oldPalavraLink) throws SQLException {
        try {
            String updateSQL = (" UPDATE PalavraLink SET ");
            updateSQL += (" Palavra_codPalavras = " + newPalavraLink.getPalavra_codPalavras()+", ");
            updateSQL += (" Link_codLink = " + newPalavraLink.getLink_codLink()+", ");
            updateSQL += (" qtd = " + newPalavraLink.getQtd());
            updateSQL += (" WHERE PalavraLink.Palavra_codPalavras = " + oldPalavraLink.getPalavra_codPalavras());
            updateSQL += (" AND PalavraLink.Link_codLink = " + oldPalavraLink.getLink_codLink());
            updateSQL += (" AND PalavraLink.qtd = " + oldPalavraLink.getQtd());
            System.out.println(updateSQL);
            Statement st = con.createStatement();
            st.executeUpdate(updateSQL);
            System.out.println("PalavraLink foi atualizada com sucesso !");

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro ao atualizar PalavraLink: "+e.getMessage());
            return false;
        }
    }
}
