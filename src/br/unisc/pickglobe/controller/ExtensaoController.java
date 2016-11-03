
package br.unisc.pickglobe.controller;

import br.unisc.pickglobe.dao.ExtensaoDAO;
import br.unisc.pickglobe.databaseConnect.Connect;
import br.unisc.pickglobe.model.Extensao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author will
 */
public class ExtensaoController {
    
    private final ExtensaoDAO dao = new ExtensaoDAO();
    static PreparedStatement stmp = null;
    
    public boolean insert(Extensao extensao){
        try {
            Connection con;
            con = Connect.Connect();
            dao.create(con, extensao);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
     public boolean update(Extensao extensao){
         try {
            Connection con;
            con = Connect.Connect();
            dao.update(con, extensao);
            return true;
         } catch (Exception e) {
             return false;
         }
     }
     
     public boolean delete(Extensao extensao){
         try {
            Connection con;
            con = Connect.Connect();
            dao.delete(con, extensao);
            return true;
         } catch (Exception e) {
             return false;
         }
     }
     
     public Object select(int codExtensao)throws SQLException{
         try {
            Connection con;
            con = Connect.Connect();
            return dao.get(con, codExtensao);
         } catch (Exception e) {
             e.printStackTrace();
             return null;
         }
     }
     
     public ArrayList<Extensao> selectAll(){
         try {
            ArrayList<Extensao> listaExtensao = new ArrayList();
            Connection con;
            con = Connect.Connect();
            listaExtensao = dao.listExtensoes(con);
            return listaExtensao;
         } catch (Exception e) {
             return null;
         }
     }
}
