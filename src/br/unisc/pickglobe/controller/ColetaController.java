
package br.unisc.pickglobe.controller;

import br.unisc.pickglobe.dao.ColetaDAO;
import br.unisc.pickglobe.databaseConnect.Connect;
import br.unisc.pickglobe.model.Coleta;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author will
 */
public class ColetaController {
    
    
    private final ColetaDAO dao = new ColetaDAO();
    static PreparedStatement stmp = null;
    
    public boolean insert(Coleta coleta){
        try {
            Connection con;
            con = Connect.Connect();
            dao.create(con, coleta);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
     public boolean update(Coleta coleta){
         try {
            Connection con;
            con = Connect.Connect();
            dao.update(con, coleta);
            return true;
         } catch (Exception e) {
             return false;
         }
     }
     
     public boolean delete(Coleta coleta){
         try {
            Connection con;
            con = Connect.Connect();
            dao.delete(con, coleta);
            return true;
         } catch (Exception e) {
             return false;
         }
     }
     
     public Object select(int codColeta)throws SQLException{
         try {
            Connection con;
            con = Connect.Connect();
            return dao.get(con, codColeta);
         } catch (Exception e) {
             e.printStackTrace();
             return null;
         }
     }
     
     public ArrayList<Coleta> selectAll(){
         try {
            ArrayList<Coleta> listaExtensao = new ArrayList();
            Connection con;
            con = Connect.Connect();
            listaExtensao = dao.listColeta(con);
            return listaExtensao;
         } catch (Exception e) {
             return null;
         }
     }
}
