package Dao;

import Controladores.Database;
import Controladores.OperacionesTimbrado;
import Modelos.Timbrado;
import java.sql.Connection;
import java.sql.Date;
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
public class DAOTimbrado implements OperacionesTimbrado {

    //CONEXION A LAS CLASE DE MODELOS Y CONTROLADORES
    Database db = new Database();
    Timbrado t = new Timbrado();

    @Override
    public boolean agregar(Object obj) {
        t = (Timbrado) obj;
        String sql = "INSERT INTO timbrado\n"
                + "(idtimbrado, establecimiento, puntoemision, \n"
                + "timbrado, numeroinicial, numerofinal, fechainicial, \n"
                + "fechafinal, idcaja, idtipocomprobante)\n"
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        Connection con;
        PreparedStatement ps;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setInt(1, t.getIdtimbrado());
            ps.setInt(2, t.getEstablecimiento());
            ps.setInt(3, t.getPuntoemision());
            ps.setInt(4, t.getTimbrado());
            ps.setInt(5, t.getNumeroinicial());
            ps.setInt(6, t.getNumerofinal());
            ps.setDate(7, (Date) t.getFechainicial());
            ps.setDate(8, (Date) t.getFechafinal());
            ps.setInt(9, t.getIdcaja());
            ps.setInt(10, t.getIdtipocomprobante());
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
        t = (Timbrado) obj;
        String sql = "UPDATE timbrado\n"
                + "	SET\n"
                + "		establecimiento=?,\n"
                + "		puntoemision=?,\n"
                + "		timbrado=?,\n"
                + "		numeroinicial=?,\n"
                + "		numerofinal=?,\n"
                + "		fechainicial=?,\n"
                + "		fechafinal=?,\n"
                + "		idcaja=?,\n"
                + "		idtipocomprobante=?\n"
                + "	WHERE idtimbrado=?;";
        Connection con;
        PreparedStatement ps;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setInt(1, t.getEstablecimiento());
            ps.setInt(2, t.getPuntoemision());
            ps.setInt(3, t.getTimbrado());
            ps.setInt(4, t.getNumeroinicial());
            ps.setInt(5, t.getNumerofinal());
            ps.setDate(6, (Date) t.getFechainicial());
            ps.setDate(7, (Date) t.getFechafinal());
            ps.setInt(8, t.getIdcaja());
            ps.setInt(9, t.getIdtipocomprobante());
            ps.setInt(10, t.getIdtimbrado());
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
        t = (Timbrado) obj;
        String sql = "DELETE FROM timbrado WHERE idtimbrado = ?;";
        Connection con;
        PreparedStatement ps;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setInt(1, t.getIdtimbrado());
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
        String sql = "select idtimbrado + 1 as proximo_cod_libre\n"
                + "  from (select 0 as idtimbrado\n"
                + "         union all\n"
                + "        select idtimbrado\n"
                + "          from timbrado) t1\n"
                + " where not exists (select null\n"
                + "                     from timbrado t2\n"
                + "                    where t2.idtimbrado = t1.idtimbrado + 1)\n"
                + " order by idtimbrado\n"
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
                + "T.idtimbrado, \n"
                + "LPAD(T.establecimiento, 3, 0) AS establecimiento, \n"
                + "LPAD(T.puntoemision, 3, 0) AS puntoemision, \n"
                + "T.timbrado, \n"
                + "LPAD(T.numeroinicial, 7, 0) AS numeroinicial, \n"
                + "LPAD(T.numerofinal, 7, 0) AS numerofinal, \n"
                + "DATE_FORMAT(T.fechainicial,'%d/%m/%Y') AS fechainicial, \n"
                + "DATE_FORMAT(T.fechafinal,'%d/%m/%Y') AS fechafinal, \n"
                + "T.idcaja, \n"
                + "C.descripcion,\n"
                + "T.idtipocomprobante,\n"
                + "TM.descripcion\n"
                + "FROM timbrado AS T\n"
                + "INNER JOIN caja AS C ON C.idcaja = T.idcaja\n"
                + "INNER JOIN tipo_comprobante AS TM ON TM.idtipo = T.idtipocomprobante\n"
                + "WHERE CONCAT(T.timbrado, T.numeroinicial, T.numerofinal, T.idtimbrado, C.descripcion, TM.descripcion) LIKE ?\n"
                + "ORDER BY T.timbrado;";
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
                Object[] fila = new Object[12];
                fila[0] = rs.getInt(1);
                fila[1] = rs.getString(2);
                fila[2] = rs.getString(3);
                fila[3] = rs.getInt(4);
                fila[4] = rs.getString(5);
                fila[5] = rs.getString(6);
                fila[6] = rs.getString(7);
                fila[7] = rs.getString(8);
                fila[8] = rs.getInt(9);
                fila[9] = rs.getString(10);
                fila[10] = rs.getInt(11);
                fila[11] = rs.getString(12);
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
        t = (Timbrado) obj;
        String sql = "SELECT * FROM timbrado WHERE idtimbrado = ?;";
        Connection con;
        PreparedStatement ps;
        ResultSet rs;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setInt(1, t.getIdtimbrado());
            rs = ps.executeQuery();
            if (rs.next()) {
                t.setIdtimbrado(rs.getInt(1));
                t.setEstablecimiento(rs.getInt(2));
                t.setPuntoemision(rs.getInt(3));
                t.setTimbrado(rs.getInt(4));
                t.setNumeroinicial(rs.getInt(5));
                t.setNumerofinal(rs.getInt(6));
                t.setFechainicial(rs.getDate(7));
                t.setFechafinal(rs.getDate(8));
                t.setIdcaja(rs.getInt(9));
                t.setIdtipocomprobante(rs.getInt(10));
                con.close();
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "NO EXISTE TIMBRADO CON EL CÓDIGO INGRESADO...", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
                con.close();
                return false;
            }
        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "HA OCURRIDO UN ERROR AL OBTENER EL REGISTRO SELECCIONADO \n" + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

}
