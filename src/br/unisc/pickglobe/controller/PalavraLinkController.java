
package br.unisc.pickglobe.controller;

import br.unisc.pickglobe.dao.PalavraLinkDAO;
import br.unisc.pickglobe.databaseConnect.Connect;
import br.unisc.pickglobe.model.PalavraLink;
import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 *
 * @author will
 */
public class PalavraLinkController {
    
    private final PalavraLinkDAO dao = new PalavraLinkDAO();
    static PreparedStatement stmp = null;
    
    public boolean insert(PalavraLink palavraLink){
        try {
            Connection con;
            con = Connect.Connect();
            dao.create(con, palavraLink);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
     public boolean update(PalavraLink newPalavraLink, PalavraLink oldPalavraLink){
         try {
            Connection con;
            con = Connect.Connect();
            dao.update(con, newPalavraLink, oldPalavraLink);
            return true;
         } catch (Exception e) {
             return false;
         }
     }
}
