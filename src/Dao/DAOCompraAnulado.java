package Dao;

import Controladores.Database;
import Controladores.OperacionesCompraAnulado;
import Modelos.CompraAnulado;
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
public class DAOCompraAnulado implements OperacionesCompraAnulado {

    //CONEXION A LAS CLASE DE MODELOS Y CONTROLADORES
    Database db = new Database();
    CompraAnulado ca = new CompraAnulado();

    @Override
    public boolean agregar(Object obj) {
        ca = (CompraAnulado) obj;
        String sql = "INSERT INTO compra_anulado\n"
                + "(idcompraanulado, fechahoraanulado, observacionanulado, \n"
                + "idmotivo, idusuarioanulado, idcompra, numerodocumento, \n"
                + "numerotimbrado, fecha, observacion, idmoneda, \n"
                + "iddeposito, idtipomovimiento, idproveedor, idusuario, \n"
                + "totalneto, totaliva, idcuenta)\n"
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        Connection con;
        PreparedStatement ps;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setInt(1, ca.getIdcompraanulado());
            ps.setTimestamp(2, ca.getFechahoranulado());
            ps.setString(3, ca.getObservacionanulado());
            ps.setInt(4, ca.getIdmotivo());
            ps.setInt(5, ca.getIdusuarioanulado());
            ps.setInt(6, ca.getIdcompra());
            ps.setString(7, ca.getNumerodocumento());
            ps.setInt(8, ca.getNumerotimbrado());
            ps.setDate(9, (Date) ca.getFecha());
            ps.setString(10, ca.getObservacion());
            ps.setInt(11, ca.getIdmoneda());
            ps.setInt(12, ca.getIddeposito());
            ps.setInt(13, ca.getIdtipomovimiento());
            ps.setInt(14, ca.getIdproveedor());
            ps.setInt(15, ca.getIdusuario());
            ps.setDouble(16, ca.getTotalneto());
            ps.setDouble(17, ca.getTotaliva());
            ps.setInt(18, ca.getIdcuenta());
            int filas = ps.executeUpdate();
            if (filas > 0) {
                con.close();
                JOptionPane.showMessageDialog(null, "REGISTRO DE ANULACIÓN DE COMPRA EXITOSA", "EXITO", JOptionPane.INFORMATION_MESSAGE);
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
    public boolean eliminar(Object obj) {
        ca = (CompraAnulado) obj;
        String sql = "DELETE FROM compra_anulado WHERE idcompraanulacion=?;";
        Connection con;
        PreparedStatement ps;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setInt(1, ca.getIdcompraanulado());
            int filas = ps.executeUpdate();
            if (filas > 0) {
                con.close();
                JOptionPane.showMessageDialog(null, "ELIMINACIÓN DE LA ANULACIÓN DE COMPRA EXITOSA", "EXITO", JOptionPane.INFORMATION_MESSAGE);
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
        String sql = "select idcompraanulado + 1 as proximo_cod_libre\n"
                + "  from (select 0 as idcompraanulado\n"
                + "         union all\n"
                + "        select idcompraanulado\n"
                + "          from compra_anulado) t1\n"
                + " where not exists (select null\n"
                + "                     from compra_anulado t2\n"
                + "                    where t2.idcompraanulado = t1.idcompraanulado + 1)\n"
                + " order by idcompraanulado\n"
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
    public boolean consultarDatos(Object obj) {
        ca = (CompraAnulado) obj;
        String sql = "SELECT * FROM compra_anulado WHERE idcompraanulado = ?;";
        Connection con;
        PreparedStatement ps;
        ResultSet rs;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setInt(1, ca.getIdcompraanulado());
            rs = ps.executeQuery();
            if (rs.next()) {
                ca.setIdcompraanulado(rs.getInt(1));
                ca.setFechahoranulado(rs.getTimestamp(2));
                ca.setObservacionanulado(rs.getString(3));
                ca.setIdmotivo(rs.getInt(4));
                ca.setIdusuarioanulado(rs.getInt(5));
                ca.setIdcompra(rs.getInt(6));
                ca.setNumerodocumento(rs.getString(7));
                ca.setNumerotimbrado(rs.getInt(8));
                ca.setFecha(rs.getDate(9));
                ca.setObservacion(rs.getString(10));
                ca.setIdmoneda(rs.getInt(11));
                ca.setIddeposito(rs.getInt(12));
                ca.setIdtipomovimiento(rs.getInt(13));
                ca.setIdproveedor(rs.getInt(14));
                ca.setIdusuario(rs.getInt(15));
                ca.setTotalneto(rs.getDouble(16));
                ca.setTotaliva(rs.getDouble(17));
                ca.setIdcuenta(rs.getInt(18));
                con.close();
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "NO EXISTE ANULACIÓN DE COMPRA CON EL CÓDIGO INGRESADO...", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
                con.close();
                return false;
            }
        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "HA OCURRIDO UN ERROR AL OBTENER EL REGISTRO SELECCIONADO \n" + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    @Override
    public ArrayList<Object[]> consultar(String criterio, int idtipomovimiento) {
        String sql = "SELECT\n"
                + "VCA.idcompraanulado AS idanulacion,\n"
                + "CONVERT(SUBSTR(VCA.numerodocumento, 1, 3), INTEGER) AS establecimiento,\n"
                + "CONVERT(SUBSTR(VCA.numerodocumento, 5, 3), INTEGER) AS puntemision,\n"
                + "CONVERT(SUBSTR(VCA.numerodocumento, 9, 7), INTEGER) AS numero,\n"
                + "VCA.numerodocumento AS numero_documento,\n"
                + "VCA.numerotimbrado AS numero_timbrado,\n"
                + "VCA.Fecha_comprobante AS fecha_comprobante,\n"
                + "VCA.idproveedor AS idproveedor,\n"
                + "VCA.Proveedor AS proveedor\n"
                + "FROM v_compra_anulado AS VCA\n"
                + "WHERE VCA.idtipomovimiento = ?\n"
                + "AND 	CONCAT(VCA.Motivo, VCA.numerodocumento, VCA.numerotimbrado, VCA.Fecha_comprobante, VCA.Moneda, VCA.Proveedor) LIKE ?\n"
                + "ORDER BY VCA.Fecha_hora_anulado DESC;";
        Connection con;
        PreparedStatement ps;
        ResultSet rs;
        ArrayList<Object[]> datos = new ArrayList<>();
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setInt(1, idtipomovimiento);
            ps.setString(2, "%" + criterio + "%");
            rs = ps.executeQuery();
            while (rs.next()) {
                Object[] fila = new Object[9];
                fila[0] = rs.getInt(1);
                fila[1] = rs.getInt(2);
                fila[2] = rs.getInt(3);
                fila[3] = rs.getInt(4);
                fila[4] = rs.getString(5);
                fila[5] = rs.getInt(6);
                fila[6] = rs.getString(7);
                fila[7] = rs.getInt(8);
                fila[8] = rs.getString(9);
                datos.add(fila);
            }
            con.close();
        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "HA OCURRIDO UN ERROR AL OBTENER LA LISTA DE LOS DATOS \n" + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return datos;
    }

    @Override
    public boolean verificarPagos(int idcompra) {
        String sql = "SELECT COUNT(*) CONTADOR\n"
                + "FROM compra_pago_cuota AS CPC\n"
                + "WHERE CPC.idcompra = ?;";
        Connection con;
        PreparedStatement ps;
        ResultSet rs;
        boolean valor = false;
        int existe = 0;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setInt(1, idcompra);
            rs = ps.executeQuery();
            if (rs.next()) {
                existe = rs.getInt(1);
                if (existe > 0) {
                    valor = true;
                } else {
                    valor = false;
                }
            }
            con.close();
        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "HA OCURRIDO UN ERROR AL VERIFICAR LOS PAGOS DE LA COMPRA\n" + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return valor;
    }

}
