
package br.unisc.pickglobe.controller;

import br.unisc.pickglobe.dao.SiteDAO;
import br.unisc.pickglobe.databaseConnect.Connect;
import br.unisc.pickglobe.model.Site;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author will
 */
public class SiteController {
    private final SiteDAO dao = new SiteDAO();
    static PreparedStatement stmp = null;
    
    public boolean insert(Site site){
        try {
            Connection con;
            con = Connect.Connect();
            dao.create(con, site);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
     public boolean update(Site site){
         try {
            Connection con;
            con = Connect.Connect();
            dao.update(con, site);
            return true;
         } catch (Exception e) {
             return false;
         }
     }
     
     public boolean delete(Site site){
         try {
            Connection con;
            con = Connect.Connect();
            dao.delete(con, site);
            return true;
         } catch (Exception e) {
             return false;
         }
     }
     
     public Object select(int codSite)throws SQLException{
         try {
            Connection con;
            con = Connect.Connect();
            return dao.get(con, codSite);
         } catch (Exception e) {
             e.printStackTrace();
             return null;
         }
     }
     
     public ArrayList<Site> selectAll(){
         try {
            ArrayList<Site> listaSites = new ArrayList();
            Connection con;
            con = Connect.Connect();
            listaSites = dao.listSite(con);
            return listaSites;
         } catch (Exception e) {
             return null;
         }
     }
}
