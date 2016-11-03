
package br.unisc.pickglobe.controller;

import br.unisc.pickglobe.dao.LinkDAO;
import br.unisc.pickglobe.databaseConnect.Connect;
import br.unisc.pickglobe.model.Link;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author will
 */
public class LinkController {
    
    private final LinkDAO dao = new LinkDAO();
    static PreparedStatement stmp = null;
    
    public boolean insert(Link link){
        try {
            Connection con;
            con = Connect.Connect();
            dao.create(con, link);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
     public boolean update(Link link){
         try {
            Connection con;
            con = Connect.Connect();
            dao.update(con, link);
            return true;
         } catch (Exception e) {
             return false;
         }
     }
     
     public boolean delete(Link link){
         try {
            Connection con;
            con = Connect.Connect();
            dao.delete(con, link);
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
     
     public ArrayList<Link> selectAll(){
         try {
            ArrayList<Link> listaLink = new ArrayList();
            Connection con;
            con = Connect.Connect();
            listaLink = dao.listLink(con);
            return listaLink;
         } catch (Exception e) {
             return null;
         }
     }
}
