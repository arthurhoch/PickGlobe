
package br.unisc.pickglobe.dao;

import br.unisc.pickglobe.model.Coleta;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author will
 */
public class ColetaDAO extends DAO<Coleta>{
    
    @Override
    public boolean create(Connection con, Coleta coleta) throws SQLException {
        try {
            //insert into Extensao (tipoExtensao) values ('asdas')
            String insertSQL = " insert into Coleta (md5, Site_codSites) VALUES ( ";
            insertSQL +=  " '"+coleta.getMd5()+"', ";
            insertSQL +=  coleta.getSite_codSites()+") ";
            System.out.println(insertSQL);
            PreparedStatement stm = 
                    con.prepareStatement(insertSQL);
            
            stm.execute();
            stm.close();
            con.close();
            
            System.out.println("Coleta adicionada com sucesso!");
            return true;
        } catch (Exception e) {
            System.out.println("Exessao: "+e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Connection con, Coleta coleta) throws SQLException {
        try {
            String updateSQL = (" UPDATE Coleta SET ");
            updateSQL += (" md5 = " + "'"+coleta.getMd5()+"', ");
            updateSQL += (" Site_codSites = " + coleta.getSite_codSites());
            updateSQL += (" WHERE Coleta.codColeta = " + coleta.getCodColeta());
            System.out.println(updateSQL);
            Statement st = con.createStatement();
            st.executeUpdate(updateSQL);
            System.out.println("A coleta: "+coleta.getCodColeta()+ ", foi atualizada com sucesso !");

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro ao atualizar a coleta: "+e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(Connection con, Coleta site) throws SQLException {
        try {
            String deleteSQL = "DELETE FROM Coleta WHERE codColeta = "+site.getCodColeta();
            System.out.println(deleteSQL);
            Statement st = con.createStatement();
            st.executeUpdate(deleteSQL);
            System.out.println("A coleta: "+site.getCodColeta()+ " ,foi removida com sucesso!");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro ao remover coleta: "+e.getMessage());
            return false;
        }
        
        
    }
    
    @Override
    public Object get(Connection con, int codColeta) throws SQLException {
        try {
            String selectSQL = "SELECT * FROM Coleta ";
            selectSQL += "WHERE Coleta.codColeta= " + codColeta;
            System.out.println(selectSQL);
            
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(selectSQL);
            Coleta coleta = new Coleta();
            rs.next();
            
            coleta.setCodColeta(rs.getInt("codColeta"));
            coleta.setMd5(rs.getString("md5"));
            coleta.setSite_codSites(rs.getInt("Site_codSites"));

            rs.close();
            st.close();
            con.close();

            return coleta;
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Excessao ao buscar a coleta: "+e.getMessage());
            return null;
        }
    }
    
    public ArrayList<Coleta> listColeta(Connection con){
        try {
            ArrayList<Coleta> listaColeta = new ArrayList<>();
            
            String selectSQL = "SELECT * FROM Coleta ";
            System.out.println(selectSQL);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(selectSQL);

            while (rs.next()) {
                Coleta coleta = new Coleta();
                
                coleta.setCodColeta(rs.getInt("codColeta"));
                coleta.setMd5(rs.getString("md5"));
                coleta.setSite_codSites(rs.getInt("Site_codSites"));

                listaColeta.add(coleta);
            }
            
            rs.close();
            st.close();
            con.close();
            return listaColeta;
        } catch (Exception e) {
            return null;
        }
    }
    
}
