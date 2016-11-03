
package br.unisc.pickglobe.controller;

import br.unisc.pickglobe.dao.ListaPalavrasNomeDAO;
import br.unisc.pickglobe.databaseConnect.Connect;
import br.unisc.pickglobe.model.ListaPalavrasNome;
import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 *
 * @author will
 */
public class ListaPalavrasNomeController {
    private final ListaPalavrasNomeDAO dao = new ListaPalavrasNomeDAO();
    static PreparedStatement stmp = null;
    
    public boolean insert(ListaPalavrasNome extensaoSite){
        try {
            Connection con;
            con = Connect.Connect();
            dao.create(con, extensaoSite);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
     public boolean update(ListaPalavrasNome newListaPalavrasNome, ListaPalavrasNome oldListaPalavrasNome){
         try {
            Connection con;
            con = Connect.Connect();
            dao.update(con, newListaPalavrasNome, oldListaPalavrasNome);
            return true;
         } catch (Exception e) {
             return false;
         }
     }
}
