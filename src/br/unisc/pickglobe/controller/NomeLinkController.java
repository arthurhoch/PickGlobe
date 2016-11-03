
package br.unisc.pickglobe.controller;

import br.unisc.pickglobe.dao.NomeLinkDAO;
import br.unisc.pickglobe.databaseConnect.Connect;
import br.unisc.pickglobe.model.NomeLink;
import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 *
 * @author will
 */
public class NomeLinkController {
    private final NomeLinkDAO dao = new NomeLinkDAO();
    static PreparedStatement stmp = null;
    
    public boolean insert(NomeLink nomeLink){
        try {
            Connection con;
            con = Connect.Connect();
            dao.create(con, nomeLink);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
     public boolean update(NomeLink newNomeLink, NomeLink oldNomeLink){
         try {
            Connection con;
            con = Connect.Connect();
            dao.update(con, newNomeLink, oldNomeLink);
            return true;
         } catch (Exception e) {
             return false;
         }
     }
}
