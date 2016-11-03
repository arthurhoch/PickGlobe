package br.unisc.pickglobe.databaseConnect;

/**
 *
 * @author will
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {

    public static Connection Connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/dbPGBD?"
                            + "user=root&password=root");            

        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Seguinte erro ao tentar conectar: " + ex.getMessage());
            return null;
        }
    }
}
