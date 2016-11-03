
package br.unisc.pickglobe.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author will
 */
public abstract class DAO<T> {
    
    
    public List<Object> parametros;
    
    public abstract boolean create (Connection con, T objeto) throws SQLException;
    public abstract boolean update (Connection con, T objeto) throws SQLException;
    public abstract boolean delete (Connection con, T objeto) throws SQLException;
    public abstract Object get (Connection con, int cod) throws SQLException;
    
}
