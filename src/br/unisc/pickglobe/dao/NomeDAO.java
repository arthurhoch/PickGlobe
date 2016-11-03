
package br.unisc.pickglobe.dao;

import br.unisc.pickglobe.model.Nome;
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
public class NomeDAO extends DAO<Nome>{
    
    @Override
    public boolean create(Connection con, Nome nome) throws SQLException {
        try {
            //insert into Extensao (tipoExtensao) values ('asdas')
            String insertSQL = " insert into Nome (nome) VALUES ( ";
            insertSQL +=  " '"+nome.getNome()+"') ";
            PreparedStatement stm = 
                    con.prepareStatement(insertSQL);
            
            stm.execute();
            stm.close();
            con.close();
            
            System.out.println("Nome "+nome.getNome()+", adicionado com sucesso!");
            return true;
        } catch (Exception e) {
            System.out.println("Exessao ao adicionar nome: "+e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Connection con, Nome nome) throws SQLException {
        try {
            String updateSQL = (" UPDATE Nome SET ");
            updateSQL += (" nome = " + "'"+nome.getNome()+"' ");
            updateSQL += (" WHERE Nome.codNomes = " + nome.getCodNomes());
            System.out.println(updateSQL);
            Statement st = con.createStatement();
            st.executeUpdate(updateSQL);
            System.out.println("O nome: "+nome.getNome()+ ", foi atualizado com sucesso !");

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro ao atualizar nome: "+e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(Connection con, Nome nome) throws SQLException {
        try {
            String deleteSQL = "DELETE FROM Nome WHERE codNomes = "+nome.getCodNomes();
            System.out.println(deleteSQL);
            Statement st = con.createStatement();
            st.executeUpdate(deleteSQL);
            System.out.println("O nome: "+nome.getNome()+ " ,foi removido com sucesso!");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro ao remover nome: "+e.getMessage());
            return false;
        }
        
        
    }
    
    @Override
    public Object get(Connection con, int codNome) throws SQLException {
        try {
            String selectSQL = "SELECT * FROM Nome ";
            selectSQL += "WHERE Nome.codNomes= " + codNome;
            System.out.println(selectSQL);
            
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(selectSQL);
            Nome nome = new Nome();
            rs.next();
            
            nome.setCodNomes(rs.getInt("codNomes"));
            nome.setNome(rs.getString("nome"));

            rs.close();
            st.close();
            con.close();

            return nome;
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Excessao ao buscar o nome: "+codNome+"! "+e.getMessage());
            return null;
        }
    }
    
    public ArrayList<Nome> listNome(Connection con){
        try {
            ArrayList<Nome> listaNomes = new ArrayList<>();
            
            String selectSQL = "SELECT * FROM Nome ";
            System.out.println(selectSQL);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(selectSQL);

            while (rs.next()) {
                Nome nome = new Nome();
                
                nome.setCodNomes(rs.getInt("codNomes"));
                nome.setNome(rs.getString("nome"));

                listaNomes.add(nome);
            }
            
            rs.close();
            st.close();
            con.close();
            return listaNomes;
        } catch (Exception e) {
            return null;
        }
    }
}
