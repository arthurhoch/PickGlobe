
package br.unisc.pickglobe.dao;

import br.unisc.pickglobe.model.ListaPalavrasNome;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author will
 */
public class ListaPalavrasNomeDAO {
    
    
    public boolean create(Connection con, ListaPalavrasNome listaPalavrasNome) throws SQLException {
        try {
            //insert into Extensao (tipoExtensao) values ('asdas')
            String insertSQL = " insert into ListaPalavrasNome (ListaPalavras_Site_codSites, Nome_codNomes) VALUES ( ";
            insertSQL +=  listaPalavrasNome.getListaPalavras_Site_codSites()+", ";
            insertSQL +=  listaPalavrasNome.getNome_codNomes()+") ";
            System.out.println(insertSQL);
            PreparedStatement stm = 
                    con.prepareStatement(insertSQL);
            
            stm.execute();
            stm.close();
            con.close();
            
            System.out.println("ListaPalavrasNome adicionada com sucesso!");
            return true;
        } catch (Exception e) {
            System.out.println("Exessao: "+e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean update(Connection con, ListaPalavrasNome newListaPalavrasNome, ListaPalavrasNome oldListaPalavrasNome) throws SQLException {
        try {
            String updateSQL = (" UPDATE ListaPalavrasNome SET ");
            updateSQL += (" ListaPalavras_Site_codSites = " + newListaPalavrasNome.getListaPalavras_Site_codSites()+", ");
            updateSQL += (" Nome_codNomes = " + newListaPalavrasNome.getNome_codNomes());
            updateSQL += (" WHERE ListaPalavrasNome.ListaPalavras_Site_codSites = " + oldListaPalavrasNome.getListaPalavras_Site_codSites());
            updateSQL += (" AND ListaPalavrasNome.Nome_codNomes = " + oldListaPalavrasNome.getNome_codNomes());
            System.out.println(updateSQL);
            Statement st = con.createStatement();
            st.executeUpdate(updateSQL);
            System.out.println("A ListaPalavrasNome foi atualizada com sucesso !");

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro ao atualizar ListaPalavrasNome: "+e.getMessage());
            return false;
        }
    }
}
