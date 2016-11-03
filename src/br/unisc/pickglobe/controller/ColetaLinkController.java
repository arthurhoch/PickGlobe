
package br.unisc.pickglobe.controller;

import br.unisc.pickglobe.dao.ColetaLinkDAO;
import br.unisc.pickglobe.databaseConnect.Connect;
import br.unisc.pickglobe.model.ColetaLink;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author will
 */
public class ColetaLinkController {
    
    private final ColetaLinkDAO dao = new ColetaLinkDAO();
    static PreparedStatement stmp = null;
    
    public boolean insert(ColetaLink coletaLink){
        try {
            Connection con;
            con = Connect.Connect();
            dao.create(con, coletaLink);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
     public boolean update(ColetaLink newColetaLink, ColetaLink oldColetaLink){
         try {
            Connection con;
            con = Connect.Connect();
            dao.update(con, newColetaLink, oldColetaLink);
            return true;
         } catch (Exception e) {
             return false;
         }
     }
     
}
