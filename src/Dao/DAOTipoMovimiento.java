package Dao;

import Controladores.Database;
import Controladores.OperacionesTipoMovimiento;
import Modelos.TipoMovimiento;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author armando
 */
public class DAOTipoMovimiento implements OperacionesTipoMovimiento {

    //CONEXION A LAS CLASE DE MODELOS Y CONTROLADORES
    Database db = new Database();
    TipoMovimiento tm = new TipoMovimiento();

    @Override
    public boolean agregar(Object obj) {
        tm = (TipoMovimiento) obj;
        String sql = "INSERT INTO tipo_movimiento VALUES(?, ?, ?, ?);";
        Connection con;
        PreparedStatement ps;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setInt(1, tm.getIdtipomovimiento());
            ps.setString(2, tm.getDescripcion());
            ps.setString(3, tm.getAbreviacion());
            ps.setInt(4, tm.getIdtipo());
            int filas = ps.executeUpdate();
            if (filas > 0) {
                con.close();
                JOptionPane.showMessageDialog(null, "REGISTRO EXITOSO", "EXITO", JOptionPane.INFORMATION_MESSAGE);
                return true;
            } else {
                con.close();
                return false;
            }
        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "HA OCURRIDO UN ERROR AL INSERTAR LOS DATOS \n" + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    @Override
    public boolean modificar(Object obj) {
        tm = (TipoMovimiento) obj;
        String sql = "UPDATE tipo_movimiento\n"
                + "	SET\n"
                + "		descripcion=?,\n"
                + "		abreviacion=?,\n"
                + "		idtipo=?\n"
                + "	WHERE idtipomovimiento=?;;";
        Connection con;
        PreparedStatement ps;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setString(1, tm.getDescripcion());
            ps.setString(2, tm.getAbreviacion());
            ps.setInt(3, tm.getIdtipo());
            ps.setInt(4, tm.getIdtipomovimiento());
            int filas = ps.executeUpdate();
            if (filas > 0) {
                con.close();
                JOptionPane.showMessageDialog(null, "ACTUALIZACIÓN EXITOSA", "EXITO", JOptionPane.INFORMATION_MESSAGE);
                return true;
            } else {
                con.close();
                return false;
            }
        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "HA OCURRIDO UN ERROR AL MODIFICAR LOS DATOS \n" + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    @Override
    public boolean eliminar(Object obj) {
        tm = (TipoMovimiento) obj;
        String sql = "DELETE FROM tipo_movimiento WHERE idtipomovimiento = ?;";
        Connection con;
        PreparedStatement ps;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setInt(1, tm.getIdtipomovimiento());
            int filas = ps.executeUpdate();
            if (filas > 0) {
                con.close();
                JOptionPane.showMessageDialog(null, "ELIMINACIÓN EXITOSA", "EXITO", JOptionPane.INFORMATION_MESSAGE);
                return true;
            } else {
                con.close();
                return false;
            }
        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "HA OCURRIDO UN ERROR AL ELIMINAR LOS DATOS \n" + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    @Override
    public int nuevoID() {
        String sql = "select idtipomovimiento + 1 as proximo_cod_libre\n"
                + "  from (select 0 as idtipomovimiento\n"
                + "         union all\n"
                + "        select idtipomovimiento\n"
                + "          from tipo_movimiento) t1\n"
                + " where not exists (select null\n"
                + "                     from TIPO_MOVIMIENTO t2\n"
                + "                    where t2.idtipomovimiento = t1.idtipomovimiento + 1)\n"
                + " order by idtipomovimiento\n"
                + " LIMIT 1;";
        Connection con;
        PreparedStatement ps;
        ResultSet rs;
        int id = 0;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                id = rs.getInt(1);
            }
            con.close();
        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "HA OCURRIDO UN ERROR AL OBTENER UN NUEVO CÓDIGO \n" + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return id;
    }

    @Override
    public ArrayList<Object[]> consultar(String criterio) {
        String sql = "SELECT \n"
                + "TM.idtipomovimiento, \n"
                + "TM.descripcion, \n"
                + "TM.abreviacion, \n"
                + "TM.idtipo,\n"
                + "TC.descripcion\n"
                + "FROM tipo_movimiento AS TM\n"
                + "INNER JOIN tipo_comprobante AS TC ON TC.idtipo = TM.idtipo\n"
                + "WHERE CONCAT(TM.descripcion, TM.abreviacion, TM.idtipo, TC.descripcion) LIKE ?\n"
                + "ORDER BY TM.descripcion;";
        Connection con;
        PreparedStatement ps;
        ResultSet rs;
        ArrayList<Object[]> datos = new ArrayList<>();
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setString(1, "%" + criterio + "%");
            rs = ps.executeQuery();
            while (rs.next()) {
                Object[] fila = new Object[5];
                fila[0] = rs.getInt(1);
                fila[1] = rs.getString(2);
                fila[2] = rs.getString(3);
                fila[3] = rs.getInt(4);
                fila[4] = rs.getString(5);
                datos.add(fila);
            }
            con.close();
        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "HA OCURRIDO UN ERROR AL OBTENER LA LISTA DE LOS DATOS \n" + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return datos;
    }

    @Override
    public boolean consultarDatos(Object obj) {
        tm = (TipoMovimiento) obj;
        String sql = "SELECT * FROM tipo_movimiento WHERE idtipomovimiento = ?;";
        Connection con;
        PreparedStatement ps;
        ResultSet rs;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setInt(1, tm.getIdtipomovimiento());
            rs = ps.executeQuery();
            if (rs.next()) {
                tm.setIdtipo(rs.getInt(1));
                tm.setDescripcion(rs.getString(2));
                tm.setAbreviacion(rs.getString(3));
                tm.setIdtipo(rs.getInt(4));
                con.close();
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "NO EXISTE TIPO DE MOVIMIENTO CON EL CÓDIGO INGRESADO...", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
                con.close();
                return false;
            }
        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "HA OCURRIDO UN ERROR AL OBTENER EL REGISTRO SELECCIONADO \n" + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    @Override
    public ArrayList<Object[]> consultarFactura(String criterio, String tipo) {
        String sql = "SELECT \n"
                + "TM.idtipomovimiento, \n"
                + "TM.descripcion, \n"
                + "TM.abreviacion, \n"
                + "TM.idtipo,\n"
                + "TC.descripcion\n"
                + "FROM tipo_movimiento AS TM\n"
                + "INNER JOIN tipo_comprobante AS TC ON TC.idtipo = TM.idtipo\n"
                + "WHERE CONCAT(TM.descripcion, TM.abreviacion, TM.idtipo, TC.descripcion) LIKE ?\n"
                + "AND TC.idtipo = 1\n"
                + "AND TM.descripcion LIKE ?\n"
                + "ORDER BY TM.descripcion;";
        Connection con;
        PreparedStatement ps;
        ResultSet rs;
        ArrayList<Object[]> datos = new ArrayList<>();
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setString(1, "%" + criterio + "%");
            ps.setString(2, "%" + tipo + "%");
            rs = ps.executeQuery();
            while (rs.next()) {
                Object[] fila = new Object[5];
                fila[0] = rs.getInt(1);
                fila[1] = rs.getString(2);
                fila[2] = rs.getString(3);
                fila[3] = rs.getInt(4);
                fila[4] = rs.getString(5);
                datos.add(fila);
            }
            con.close();
        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "HA OCURRIDO UN ERROR AL OBTENER LA LISTA DE LOS DATOS \n" + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return datos;
    }

    @Override
    public boolean consultarDatosFactura(Object obj) {
        tm = (TipoMovimiento) obj;
        String sql = "SELECT TM.* FROM tipo_movimiento AS TM\n"
                + "INNER JOIN tipo_comprobante AS TC ON TC.idtipo = TM.idtipo\n"
                + "WHERE TM.idtipomovimiento = ?\n"
                + "AND 	TC.idtipo = 1\n"
                + "AND 	TM.descripcion LIKE '%RECIBIDA%';";
        Connection con;
        PreparedStatement ps;
        ResultSet rs;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setInt(1, tm.getIdtipomovimiento());
            rs = ps.executeQuery();
            if (rs.next()) {
                tm.setIdtipo(rs.getInt(1));
                tm.setDescripcion(rs.getString(2));
                tm.setAbreviacion(rs.getString(3));
                tm.setIdtipo(rs.getInt(4));
                con.close();
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "NO EXISTE TIPO DE MOVIMIENTO CON EL CÓDIGO INGRESADO...", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
                con.close();
                return false;
            }
        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "HA OCURRIDO UN ERROR AL OBTENER EL REGISTRO SELECCIONADO \n" + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

}
