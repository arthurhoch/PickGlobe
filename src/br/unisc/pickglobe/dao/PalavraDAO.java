
package br.unisc.pickglobe.dao;

import br.unisc.pickglobe.model.Palavra;
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
public class PalavraDAO extends DAO<Palavra>{
    
    @Override
    public boolean create(Connection con, Palavra palavra) throws SQLException {
        try {
            //insert into Extensao (tipoExtensao) values ('asdas')
            String insertSQL = " insert into palavra (palavra, tipo) VALUES (";
            insertSQL +=  "'"+palavra.getPalavra() +"'";
            insertSQL +=  "'"+palavra.getTipo() +"')";
            System.out.println(insertSQL);
            PreparedStatement stm = 
                    con.prepareStatement(insertSQL);
            
            stm.execute();
            stm.close();
            con.close();
            
            System.out.println("Palavra adicionada com sucesso!");
            return true;
        } catch (Exception e) {
            System.out.println("Exessao: "+e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Connection con, Palavra palavra) throws SQLException {
        try {
            String updateSQL = (" UPDATE palavra SET ");
            updateSQL += (" palavra = " + "'"+palavra.getPalavra()+"'");
            updateSQL += (" tipo = " + "'"+palavra.getTipo()+"'");
            updateSQL += (" WHERE palavra.codPalavras = " + palavra.getCodPalavras());
            System.out.println(updateSQL);
            Statement st = con.createStatement();
            st.executeUpdate(updateSQL);
            System.out.println("A "+palavra.getPalavra()+ " foi atualizada com sucesso !");

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro ao atualizar a palavra: "+e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(Connection con, Palavra palavra) throws SQLException {
        try {
            String deleteSQL = "DELETE FROM palavra WHERE codPalavras = "+palavra.getCodPalavras();
            System.out.println(deleteSQL);
            Statement st = con.createStatement();
            st.executeUpdate(deleteSQL);
            System.out.println("A "+palavra.getPalavra()+ " foi removida com sucesso!");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro ao remover a palavra: "+e.getMessage());
            return false;
        }
        
        
    }
    
    @Override
    public Object get(Connection con, int codPalavra) throws SQLException {
        try {
            String selectSQL = "SELECT * FROM Palavra ";
            selectSQL += "WHERE palavra.codPalavras = " + codPalavra;
            System.out.println(selectSQL);
            
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(selectSQL);
            Palavra palavra = new Palavra();
            rs.next();
            
            palavra.setCodPalavras(rs.getInt("codPalavras"));
            palavra.setPalavra(rs.getString("palavra"));
            palavra.setTipo(rs.getString("tipo"));


            rs.close();
            st.close();
            con.close();

            return palavra;
            
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Excessao ao buscar a palavra: "+e.getMessage());
            return null;
        }
    }
    
    public ArrayList<Palavra> listPalavra(Connection con){
        try {
            ArrayList<Palavra> listaPalavra = new ArrayList<>();
            
            String selectSQL = "SELECT * FROM Palavra ";
            System.out.println(selectSQL);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(selectSQL);

            while (rs.next()) {
                Palavra ext = new Palavra();
                
                ext.setCodPalavras(rs.getInt("codPalavras"));
                ext.setPalavra(rs.getString("palavra"));
                ext.setTipo(rs.getString("tipo"));

                listaPalavra.add(ext);
            }
            
            
            rs.close();
            st.close();
            con.close();
            return listaPalavra;
        } catch (Exception e) {
            return null;
        }
    }
}
