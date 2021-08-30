package Dao;

import Controladores.Database;
import Controladores.OperacionesCompraPagoCuotaAnulado;
import Modelos.CompraPagoCuotaAnulado;
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
public class DAOCompraPagoCuotaAnulado implements OperacionesCompraPagoCuotaAnulado {

    //CONEXION A LAS CLASE DE MODELOS Y CONTROLADORES
    Database db = new Database();
    CompraPagoCuotaAnulado cpca = new CompraPagoCuotaAnulado();

    @Override
    public boolean agregar(Object obj) {
        cpca = (CompraPagoCuotaAnulado) obj;
        String sql = "INSERT INTO compra_pago_cuota_anulado\n"
                + "(idpagoanulado, fechahoraanulado, observacion, idmotivo, \n"
                + "idusuario, idpago,\n"
                + "fechapago, monto, numerocomprobante, numerorecibo)\n"
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        Connection con;
        PreparedStatement ps;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setInt(1, cpca.getIdpagoanulado());
            ps.setTimestamp(2, cpca.getFechahoranulado());
            ps.setString(3, cpca.getObservacion());
            ps.setInt(4, cpca.getIdmotivo());
            ps.setInt(5, cpca.getIdusuario());
            ps.setInt(6, cpca.getIdpago());
            ps.setDate(7, (Date) cpca.getFechapago());
            ps.setDouble(8, cpca.getMonto());
            ps.setString(9, cpca.getNumerocomprobante());
            ps.setString(10, cpca.getNumerorecibo());
            int filas = ps.executeUpdate();
            if (filas > 0) {
                con.close();
                JOptionPane.showMessageDialog(null, "REGISTRO DE ANULACIÓN DE PAGO EXITOSO", "EXITO", JOptionPane.INFORMATION_MESSAGE);
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
        cpca = (CompraPagoCuotaAnulado) obj;
        String sql = "DELETE FROM compra_pago_cuota_anulado WHERE idpagoanulado=?;";
        Connection con;
        PreparedStatement ps;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setInt(1, cpca.getIdpagoanulado());
            int filas = ps.executeUpdate();
            if (filas > 0) {
                con.close();
                JOptionPane.showMessageDialog(null, "ELIMINACIÓN DE LA ANULACIÓN DE PAGO EXITOSA", "EXITO", JOptionPane.INFORMATION_MESSAGE);
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
        String sql = "select idpagoanulado + 1 as proximo_cod_libre\n"
                + "  from (select 0 as idpagoanulado\n"
                + "         union all\n"
                + "        select idpagoanulado\n"
                + "          from compra_pago_cuota_anulado) t1\n"
                + " where not exists (select null\n"
                + "                     from compra_pago_cuota_anulado t2\n"
                + "                    where t2.idpagoanulado = t1.idpagoanulado + 1)\n"
                + " order by idpagoanulado\n"
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
        cpca = (CompraPagoCuotaAnulado) obj;
        String sql = "SELECT * FROM compra_pago_cuota_anulado WHERE idpagoanulado = ?;";
        Connection con;
        PreparedStatement ps;
        ResultSet rs;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setInt(1, cpca.getIdpagoanulado());
            rs = ps.executeQuery();
            if (rs.next()) {
                cpca.setIdpago(rs.getInt(1));
                cpca.setFechahoranulado(rs.getTimestamp(2));
                cpca.setObservacion(rs.getString(3));
                cpca.setIdmotivo(rs.getInt(4));
                cpca.setIdusuario(rs.getInt(5));
                cpca.setIdpago(rs.getInt(6));
                cpca.setFechapago(rs.getDate(7));
                cpca.setMonto(rs.getDouble(8));
                cpca.setNumerocomprobante(rs.getString(9));
                cpca.setNumerorecibo(rs.getString(10));
                con.close();
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "NO EXISTE ANULACIÓN DE PAGO CON EL CÓDIGO INGRESADO...", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
                con.close();
                return false;
            }
        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "HA OCURRIDO UN ERROR AL OBTENER EL REGISTRO SELECCIONADO \n" + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    @Override
    public ArrayList<Object[]> consultar(String criterio, int idproveedor) {
        String sql = "SELECT \n"
                + "CPCA.idpagoanulado, \n"
                + "DATE_FORMAT(CPCA.fechahoraanulado, '%d/%m/%Y %H:%i:%s') AS fecha_anulacion, \n"
                + "CPCA.observacion, \n"
                + "CPCA.idmotivo, \n"
                + "CPCA.idusuario, \n"
                + "CPCA.idpago, \n"
                + "CPCA.numerorecibo,\n"
                + "DATE_FORMAT(CPCA.fechapago, '%d/%m/%Y') AS fecha_pago,\n"
                + "CPCA.monto\n"
                + "FROM compra_pago_cuota_anulado AS CPCA\n"
                + "INNER JOIN motivo_anulacion AS MA ON MA.idmotivo = CPCA.idmotivo\n"
                + "INNER JOIN usuario AS U ON U.idusuario = CPCA.idusuario\n"
                + "WHERE CONCAT(CPCA.numerocomprobante, CPCA.numerorecibo, CPCA.idpago, DATE_FORMAT(CPCA.fechapago, '%d/%m/%Y')) LIKE ?;";
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
                Object[] fila = new Object[9];
                fila[0] = rs.getInt(1);
                fila[1] = rs.getString(2);
                fila[2] = rs.getString(3);
                fila[3] = rs.getInt(4);
                fila[4] = rs.getInt(5);
                fila[5] = rs.getInt(6);
                fila[6] = rs.getString(7);
                fila[7] = rs.getString(8);
                fila[8] = rs.getDouble(9);
                datos.add(fila);
            }
            con.close();
        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "HA OCURRIDO UN ERROR AL OBTENER LA LISTA DE LOS DATOS \n" + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return datos;
    }

}
