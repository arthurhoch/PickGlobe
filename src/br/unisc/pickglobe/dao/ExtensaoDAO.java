package br.unisc.pickglobe.dao;

import br.unisc.pickglobe.model.Extensao;
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
public class ExtensaoDAO extends DAO<Extensao>{

    @Override
    public boolean create(Connection con, Extensao extensao) throws SQLException {
        try {
            //insert into Extensao (tipoExtensao) values ('asdas')
            String insertSQL = " insert into extensao (tipoExtensao) VALUES (";
            insertSQL +=  "'"+extensao.getTipoExtensao() +"')";
            System.out.println(insertSQL);
            PreparedStatement stm = 
                    con.prepareStatement(insertSQL);
            
            stm.execute();
            stm.close();
            con.close();
            
            System.out.println("Extensao adicionada com sucesso!");
            return true;
        } catch (Exception e) {
            System.out.println("Exessao: "+e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Connection con, Extensao extensao) throws SQLException {
        try {
            String updateSQL = (" UPDATE Extensao SET ");
            updateSQL += (" tipoExtensao = " + "'"+extensao.getTipoExtensao()+"'");
            updateSQL += (" WHERE Extensao.codExtensoes = " + extensao.getCodExtensao());
            System.out.println(updateSQL);
            Statement st = con.createStatement();
            st.executeUpdate(updateSQL);
            System.out.println("A "+extensao.getTipoExtensao()+ " foi atualizada com sucesso para !");

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro ao atualizar a extensao: "+e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(Connection con, Extensao extensao) throws SQLException {
        try {
            String deleteSQL = "DELETE FROM extensao WHERE codExtensoes = "+extensao.getCodExtensao();
            System.out.println(deleteSQL);
            Statement st = con.createStatement();
            st.executeUpdate(deleteSQL);
            System.out.println("A "+extensao.getTipoExtensao()+ " foi removida com sucesso!");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro ao remover a extensao: "+e.getMessage());
            return false;
        }
        
        
    }
    
    @Override
    public Object get(Connection con, int codExtensao) throws SQLException {
        try {
            String selectSQL = "SELECT * FROM Extensao ";
            selectSQL += "WHERE Extensao.codExtensoes = " + codExtensao;
            System.out.println(selectSQL);
            
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(selectSQL);
            Extensao extensao = new Extensao();
            rs.next();
            
            System.out.println(rs.getInt("codExtensoes") + rs.getString("tipoExtensao"));
            extensao.setCodExtensao(rs.getInt("codExtensoes"));
            extensao.setTipoExtensao(rs.getString("tipoExtensao"));


            rs.close();
            st.close();
            con.close();

            return extensao;
            
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Excessao ao buscar a extensao: "+e.getMessage());
            return null;
        }
    }
    
    public ArrayList<Extensao> listExtensoes(Connection con){
        try {
            ArrayList<Extensao> listaExtensao = new ArrayList<>();
            
            String selectSQL = "SELECT * FROM Extensao ";
            System.out.println(selectSQL);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(selectSQL);

            while (rs.next()) {
                Extensao ext = new Extensao();
                ext.setCodExtensao(rs.getInt("codExtensoes"));
                ext.setTipoExtensao(rs.getString("tipoExtensao"));

                listaExtensao.add(ext);
            }
            
            
            rs.close();
            st.close();
            con.close();
            return listaExtensao;
        } catch (Exception e) {
            return null;
        }
    }
  
    
}
