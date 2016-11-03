
package br.unisc.pickglobe.controller;

import br.unisc.pickglobe.dao.ListaPalavrasPalavraDAO;
import br.unisc.pickglobe.databaseConnect.Connect;
import br.unisc.pickglobe.model.ListaPalavrasPalavra;
import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 *
 * @author will
 */
public class ListaPalavrasPalavraController {
    private final ListaPalavrasPalavraDAO dao = new ListaPalavrasPalavraDAO();
    static PreparedStatement stmp = null;
    
    public boolean insert(ListaPalavrasPalavra listaPalavrasPalavra){
        try {
            Connection con;
            con = Connect.Connect();
            dao.create(con, listaPalavrasPalavra);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
     public boolean update(ListaPalavrasPalavra newListaPalavrasPalavra, ListaPalavrasPalavra oldListaPalavrasPalavra){
         try {
            Connection con;
            con = Connect.Connect();
            dao.update(con, newListaPalavrasPalavra, oldListaPalavrasPalavra);
            return true;
         } catch (Exception e) {
             return false;
         }
     }
}
