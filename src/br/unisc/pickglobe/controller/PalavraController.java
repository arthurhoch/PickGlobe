
package br.unisc.pickglobe.controller;

import br.unisc.pickglobe.dao.PalavraDAO;
import br.unisc.pickglobe.databaseConnect.Connect;
import br.unisc.pickglobe.model.Palavra;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author will
 */
public class PalavraController {
    private final PalavraDAO dao = new PalavraDAO();
    static PreparedStatement stmp = null;
    
    public boolean insert(Palavra palavra){
        try {
            Connection con;
            con = Connect.Connect();
            dao.create(con, palavra);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
     public boolean update(Palavra palavra){
         try {
            Connection con;
            con = Connect.Connect();
            dao.update(con, palavra);
            return true;
         } catch (Exception e) {
             return false;
         }
     }
     
     public boolean delete(Palavra palavra){
         try {
            Connection con;
            con = Connect.Connect();
            dao.delete(con, palavra);
            return true;
         } catch (Exception e) {
             return false;
         }
     }
     
     public Object select(int codPalavra)throws SQLException{
         try {
            Connection con;
            con = Connect.Connect();
            return dao.get(con, codPalavra);
         } catch (Exception e) {
             e.printStackTrace();
             return null;
         }
     }
     
     public ArrayList<Palavra> selectAll(){
         try {
            ArrayList<Palavra> listaPalavras = new ArrayList();
            Connection con;
            con = Connect.Connect();
            listaPalavras = dao.listPalavra(con);
            return listaPalavras;
         } catch (Exception e) {
             return null;
         }
     }
}
