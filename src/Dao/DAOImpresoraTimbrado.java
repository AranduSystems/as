package Dao;

import Controladores.Database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import Controladores.OperacionesImpresoraTimbrado;
import Modelos.ImpresoraTimbrado;
import java.sql.Date;

/**
 *
 * @author armando
 */
public class DAOImpresoraTimbrado implements OperacionesImpresoraTimbrado {

    //CONEXION A LAS CLASE DE MODELOS Y CONTROLADORES
    Database db = new Database();
    ImpresoraTimbrado it = new ImpresoraTimbrado();

    @Override
    public boolean agregar(Object obj) {
        it = (ImpresoraTimbrado) obj;
        String sql = "INSERT INTO impresora_timbrado\n"
                + "(idimpresora, idtimbrado, idtipocomprobante, \n"
                + "establecimiento, puntoemision, numerotimbrado, numeroinicial, \n"
                + "numerofinal, fechainicial, fechafinal)\n"
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        Connection con;
        PreparedStatement ps;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setInt(1, it.getIdimpresora());
            ps.setInt(2, it.getIdtimbrado());
            ps.setInt(3, it.getIdtipocomprobante());
            ps.setInt(4, it.getEstablecimiento());
            ps.setInt(5, it.getPuntoemision());
            ps.setInt(6, it.getNumerotimbrado());
            ps.setInt(7, it.getNumeroinicial());
            ps.setInt(8, it.getNumerofinal());
            ps.setDate(9, (Date) it.getFechainicial());
            ps.setDate(10, (Date) it.getFechafinal());
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
        it = (ImpresoraTimbrado) obj;
        String sql = "UPDATE impresora_timbrado\n"
                + "	SET\n"
                + "		idtipocomprobante=?,\n"
                + "		establecimiento=?,\n"
                + "		puntoemision=?,\n"
                + "		numerotimbrado=?,\n"
                + "		numeroinicial=?,\n"
                + "		numerofinal=?,\n"
                + "		fechainicial=?,\n"
                + "		fechafinal=?\n"
                + "	WHERE idimpresora=? AND idtimbrado=?;";
        Connection con;
        PreparedStatement ps;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setInt(1, it.getIdtipocomprobante());
            ps.setInt(2, it.getEstablecimiento());
            ps.setInt(3, it.getPuntoemision());
            ps.setInt(4, it.getNumerotimbrado());
            ps.setInt(5, it.getNumeroinicial());
            ps.setInt(6, it.getNumerofinal());
            ps.setDate(7, (Date) it.getFechainicial());
            ps.setDate(8, (Date) it.getFechafinal());
            ps.setInt(9, it.getIdimpresora());
            ps.setInt(10, it.getIdtimbrado());
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
        it = (ImpresoraTimbrado) obj;
        String sql = "DELETE FROM impresora_timbrado WHERE idimpresora=? AND idtimbrado=?;";
        Connection con;
        PreparedStatement ps;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setInt(1, it.getIdimpresora());
            ps.setInt(2, it.getIdtimbrado());
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
                + "          from impresora_timbrado) t1\n"
                + " where not exists (select null\n"
                + "                     from impresora_timbrado t2\n"
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
    public ArrayList<Object[]> consultar(int idimpresora) {
        String sql = "SELECT\n"
                + "IT.idimpresora,\n"
                + "I.descripcion AS impresora,\n"
                + "IT.idtimbrado,\n"
                + "IT.idtipocomprobante,\n"
                + "TC.descripcion AS tipo_comprobante,\n"
                + "LPAD(IT.establecimiento, 3, 0) AS establecimiento,\n"
                + "LPAD(IT.puntoemision, 3, 0) AS puntoemision,\n"
                + "IT.numerotimbrado,\n"
                + "LPAD(IT.numeroinicial, 7, 0) AS numeroinicial,\n"
                + "LPAD(IT.numerofinal, 7, 0) AS numerofinal,\n"
                + "DATE_FORMAT(IT.fechainicial, '%d/%m/%Y') AS FECHA_INICIAL,\n"
                + "DATE_FORMAT(IT.fechafinal, '%d/%m/%Y') AS FECHA_FINAL\n"
                + "FROM impresora_timbrado AS IT\n"
                + "INNER JOIN impresora AS I ON I.idimpresora = IT.idimpresora\n"
                + "INNER JOIN tipo_comprobante AS TC ON TC.idtipo = IT.idtipocomprobante\n"
                + "WHERE IT.idimpresora = ?\n"
                + "ORDER BY I.descripcion;";
        Connection con;
        PreparedStatement ps;
        ResultSet rs;
        ArrayList<Object[]> datos = new ArrayList<>();
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setInt(1, idimpresora);
            rs = ps.executeQuery();
            while (rs.next()) {
                Object[] fila = new Object[12];
                fila[0] = rs.getInt(1);
                fila[1] = rs.getString(2);
                fila[2] = rs.getInt(3);
                fila[3] = rs.getInt(4);
                fila[4] = rs.getString(5);
                fila[5] = rs.getString(6);
                fila[6] = rs.getString(7);
                fila[7] = rs.getString(8);
                fila[8] = rs.getString(9);
                fila[9] = rs.getString(10);
                fila[10] = rs.getString(11);
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
        it = (ImpresoraTimbrado) obj;
        String sql = "SELECT * \n"
                + "FROM impresora_timbrado\n"
                + "WHERE idimpresora=? AND idtimbrado=?;";
        Connection con;
        PreparedStatement ps;
        ResultSet rs;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setInt(1, it.getIdimpresora());
            ps.setInt(2, it.getIdtimbrado());
            rs = ps.executeQuery();
            if (rs.next()) {
                it.setIdimpresora(rs.getInt(1));
                it.setIdtimbrado(rs.getInt(2));
                it.setIdtipocomprobante(rs.getInt(3));
                it.setEstablecimiento(rs.getInt(4));
                it.setPuntoemision(rs.getInt(5));
                it.setNumerotimbrado(rs.getInt(6));
                it.setNumeroinicial(rs.getInt(7));
                it.setNumerofinal(rs.getInt(8));
                it.setFechainicial(rs.getDate(9));
                it.setFechafinal(rs.getDate(10));
                con.close();
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "NO EXISTE TIMBRADO DE IMPRESORA CON LOS CÓDIGO INGRESADO...", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
                con.close();
                return false;
            }
        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "HA OCURRIDO UN ERROR AL OBTENER EL REGISTRO SELECCIONADO \n" + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    @Override
    public boolean consultarDatosTimbrado(Object obj, Date fecha) {
        it = (ImpresoraTimbrado) obj;
        String sql = "SELECT * \n"
                + "FROM impresora_timbrado\n"
                + "WHERE idimpresora=? AND idtipocomprobante=?\n"
                + "AND ? BETWEEN fechainicial AND fechafinal\n"
                + "ORDER BY fechafinal ASC LIMIT 1;";
        Connection con;
        PreparedStatement ps;
        ResultSet rs;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setInt(1, it.getIdimpresora());
            ps.setInt(2, it.getIdtipocomprobante());
            ps.setDate(3, fecha);
            rs = ps.executeQuery();
            if (rs.next()) {
                it.setIdimpresora(rs.getInt(1));
                it.setIdtimbrado(rs.getInt(2));
                it.setIdtipocomprobante(rs.getInt(3));
                it.setEstablecimiento(rs.getInt(4));
                it.setPuntoemision(rs.getInt(5));
                it.setNumerotimbrado(rs.getInt(6));
                it.setNumeroinicial(rs.getInt(7));
                it.setNumerofinal(rs.getInt(8));
                it.setFechainicial(rs.getDate(9));
                it.setFechafinal(rs.getDate(10));
                con.close();
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "NO EXISTE TIMBRADO DE IMPRESORA CON LOS CÓDIGO INGRESADO...", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
                con.close();
                return false;
            }
        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "HA OCURRIDO UN ERROR AL OBTENER EL REGISTRO SELECCIONADO \n" + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

}
