
package br.unisc.pickglobe.controller;

import br.unisc.pickglobe.dao.NomeDAO;
import br.unisc.pickglobe.databaseConnect.Connect;
import br.unisc.pickglobe.model.Nome;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author will
 */
public class NomeController {
    private final NomeDAO dao = new NomeDAO();
    static PreparedStatement stmp = null;
    
    public boolean insert(Nome nome){
        try {
            Connection con;
            con = Connect.Connect();
            dao.create(con, nome);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
     public boolean update(Nome nome){
         try {
            Connection con;
            con = Connect.Connect();
            dao.update(con, nome);
            return true;
         } catch (Exception e) {
             return false;
         }
     }
     
     public boolean delete(Nome nome){
         try {
            Connection con;
            con = Connect.Connect();
            dao.delete(con, nome);
            return true;
         } catch (Exception e) {
             return false;
         }
     }
     
     public Object select(int codNome)throws SQLException{
         try {
            Connection con;
            con = Connect.Connect();
            return dao.get(con, codNome);
         } catch (Exception e) {
             e.printStackTrace();
             return null;
         }
     }
     
     public ArrayList<Nome> selectAll(){
         try {
            ArrayList<Nome> listaNomes = new ArrayList();
            Connection con;
            con = Connect.Connect();
            listaNomes = dao.listNome(con);
            return listaNomes;
         } catch (Exception e) {
             return null;
         }
     }
}
