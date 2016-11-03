
package br.unisc.pickglobe.controller;

import br.unisc.pickglobe.dao.ExtensaoSiteDAO;
import br.unisc.pickglobe.databaseConnect.Connect;
import br.unisc.pickglobe.model.ExtensaoSite;
import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 *
 * @author will
 */
public class ExtensaoSiteController {
    private final ExtensaoSiteDAO dao = new ExtensaoSiteDAO();
    static PreparedStatement stmp = null;
    
    public boolean insert(ExtensaoSite extensaoSite){
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
    
     public boolean update(ExtensaoSite newExtensaoSite, ExtensaoSite oldExtensaoSite){
         try {
            Connection con;
            con = Connect.Connect();
            dao.update(con, newExtensaoSite, oldExtensaoSite);
            return true;
         } catch (Exception e) {
             return false;
         }
     }
}
