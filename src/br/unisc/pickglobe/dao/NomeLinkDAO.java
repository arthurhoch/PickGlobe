
package br.unisc.pickglobe.dao;

import br.unisc.pickglobe.model.NomeLink;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author will
 */
public class NomeLinkDAO {
    
    
    public boolean create(Connection con, NomeLink coletaLink) throws SQLException {
        try {
            //insert into Extensao (tipoExtensao) values ('asdas')
            String insertSQL = " insert into NomeLink (Nome_codNomes, Link_codLink, qtd) VALUES ( ";
            insertSQL +=  coletaLink.getNome_codNomes()+", ";
            insertSQL +=  coletaLink.getLink_codLink()+", ";
            insertSQL +=  coletaLink.getQtd()+") ";
            System.out.println(insertSQL);
            PreparedStatement stm = 
                    con.prepareStatement(insertSQL);
            
            stm.execute();
            stm.close();
            con.close();
            
            System.out.println("NomeLink adicionada com sucesso!");
            return true;
        } catch (Exception e) {
            System.out.println("Exessao NomeLink: "+e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean update(Connection con, NomeLink newNomeLink, NomeLink oldNomeLink) throws SQLException {
        try {
            String updateSQL = (" UPDATE NomeLink SET ");
            updateSQL += (" Nome_codNomes = " + newNomeLink.getNome_codNomes()+", ");
            updateSQL += (" Link_codLink = " + newNomeLink.getLink_codLink()+", ");
            updateSQL += (" qtd = " + newNomeLink.getQtd());
            updateSQL += (" WHERE NomeLink.Nome_codNomes = " + oldNomeLink.getNome_codNomes());
            updateSQL += (" AND NomeLink.Link_codLink = " + oldNomeLink.getLink_codLink());
            updateSQL += (" AND NomeLink.qtd = " + oldNomeLink.getQtd());
            System.out.println(updateSQL);
            Statement st = con.createStatement();
            st.executeUpdate(updateSQL);
            System.out.println("NomeLink foi atualizada com sucesso !");

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro ao atualizar NomeLink: "+e.getMessage());
            return false;
        }
    }
}
