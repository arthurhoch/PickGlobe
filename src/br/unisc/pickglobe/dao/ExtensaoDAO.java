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
public class ExtensaoDAO extends DAO<Extensao> {

    @Override
    public boolean create(Connection con, Extensao extensao) throws SQLException {
        try {
            String insertSQL = " insert into Extensao values ( ";
            insertSQL += "codExtensao, " + extensao.getCodExtensao();
            insertSQL += "tipoExtensao)" + extensao.getCodExtensao();

            PreparedStatement stm
                    = con.prepareStatement(insertSQL);

            stm.execute();
            stm.close();
            con.close();

            System.out.println("Extensao adicionada com sucesso!");
            return true;
        } catch (Exception e) {
            System.out.println("Exessao: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Connection con, Extensao extensao) throws SQLException {
        try {
            String updateSQL = (" UPDATE Extensao SET ");
            updateSQL += (" tipoExtensao" + extensao.getTipoExtensao());
            updateSQL += (" WHERE Extensao.codExtensao = " + extensao.getCodExtensao());

            Statement st = con.createStatement();
            st.executeUpdate(updateSQL);
            con.commit();
            System.out.println("A " + extensao.getTipoExtensao() + " foi atualizada com sucesso!");

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro ao atualizar a extensao: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(Connection con, Extensao extensao) throws SQLException {
        try {
            String deleteSQL = "DELETE FROM extensao WHERE codExtensao = " + extensao.getCodExtensao();
            Statement st = con.createStatement();
            st.executeUpdate(deleteSQL);
            con.commit();
            System.out.println("A " + extensao.getTipoExtensao() + " foi removida com sucesso!");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro ao remover a extensao: " + e.getMessage());
            return false;
        }

    }

    @Override
    public Object get(Connection con, Extensao extensao) throws SQLException {
        try {
            ArrayList<Extensao> listaExtensao = new ArrayList<>();

            if (extensao.getTipoExtensao() != null) {
                String selectSQL = "SELECT * FROM Extensao";
                selectSQL += "WHERE Extensao.tipoExtensao LIKE " + extensao.getTipoExtensao();

                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(selectSQL);

                while (rs.next()) {
                    Extensao ext = new Extensao();
                    ext.setCodExtensao(rs.getInt("codExtensao"));
                    ext.setTipoExtensao(rs.getString("tipoExtensao"));

                    listaExtensao.add(ext);
                }

                rs.close();
                st.close();
                con.close();
            } else {
                String selectSQL = "SELECT * FROM Extensao";

                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(selectSQL);

                while (rs.next()) {
                    Extensao ext = new Extensao();
                    ext.setCodExtensao(rs.getInt("codExtensao"));
                    ext.setTipoExtensao(rs.getString("tipoExtensao"));

                    listaExtensao.add(ext);
                }

                rs.close();
                st.close();
                con.close();
            }
            return listaExtensao;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro ao buscar a extensao: " + e.getMessage());
            return false;
        }
    }

}
