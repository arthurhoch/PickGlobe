
package br.unisc.pickglobe.controller;

import br.unisc.pickglobe.dao.ListaPalavrasDAO;
import br.unisc.pickglobe.databaseConnect.Connect;
import br.unisc.pickglobe.model.ListaPalavras;
import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 *
 * @author will
 */
public class ListaPalavrasController {
    private final ListaPalavrasDAO dao = new ListaPalavrasDAO();
    static PreparedStatement stmp = null;
    
    public boolean insert(ListaPalavras listaPalavras){
        try {
            Connection con;
            con = Connect.Connect();
            dao.create(con, listaPalavras);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
     public boolean update(ListaPalavras newListaPalavras, ListaPalavras oldListaPalavras){
         try {
            Connection con;
            con = Connect.Connect();
            dao.update(con, newListaPalavras, oldListaPalavras);
            return true;
         } catch (Exception e) {
             return false;
         }
     }
}
