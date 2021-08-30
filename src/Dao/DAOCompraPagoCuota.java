package Dao;

import Controladores.Database;
import Controladores.OperacionesCompraPagoCuota;
import Modelos.CompraPagoCuota;
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
public class DAOCompraPagoCuota implements OperacionesCompraPagoCuota {

    //CONEXION A LAS CLASE DE MODELOS Y CONTROLADORES
    Database db = new Database();
    CompraPagoCuota cpc = new CompraPagoCuota();

    @Override
    public boolean agregar(Object obj) {
        cpc = (CompraPagoCuota) obj;
        String sql = "INSERT INTO compra_pago_cuota\n"
                + "(idpago, idcompra, numero, fechapago, monto, idcuenta, idusuario, numerocomprobante, idtipomovimiento)\n"
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
        Connection con;
        PreparedStatement ps;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setInt(1, cpc.getIdpago());
            ps.setInt(2, cpc.getIdcompra());
            ps.setInt(3, cpc.getNumero());
            ps.setDate(4, (Date) cpc.getFechapago());
            ps.setDouble(5, cpc.getMonto());
            ps.setInt(6, cpc.getIdcuenta());
            ps.setInt(7, cpc.getIdusuario());
            ps.setString(8, cpc.getNumerocomprobante());
            ps.setInt(9, cpc.getIdtipomovimiento());
            int filas = ps.executeUpdate();
            if (filas > 0) {
                con.close();
                JOptionPane.showMessageDialog(null, "REGISTRO DE PAGO EXITOSO", "EXITO", JOptionPane.INFORMATION_MESSAGE);
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
        cpc = (CompraPagoCuota) obj;
        String sql = "DELETE FROM compra_pago_cuota WHERE idpago=? AND idcompra=? AND numero=?;";
        Connection con;
        PreparedStatement ps;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setInt(1, cpc.getIdpago());
            ps.setInt(2, cpc.getIdcompra());
            ps.setInt(3, cpc.getNumero());
            int filas = ps.executeUpdate();
            if (filas > 0) {
                con.close();
                JOptionPane.showMessageDialog(null, "ELIMINACIÓN DEL PAGO EXITOSA", "EXITO", JOptionPane.INFORMATION_MESSAGE);
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
        String sql = "select idpago + 1 as proximo_cod_libre\n"
                + "  from (select 0 as idpago\n"
                + "         union all\n"
                + "        select idpago\n"
                + "          from compra_pago_cuota) t1\n"
                + " where not exists (select null\n"
                + "                     from compra_pago_cuota t2\n"
                + "                    where t2.idpago = t1.idpago + 1)\n"
                + " order by idpago\n"
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
        cpc = (CompraPagoCuota) obj;
        String sql = "SELECT * FROM compra_pago_cuota WHERE idpago = ?;";
        Connection con;
        PreparedStatement ps;
        ResultSet rs;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setInt(1, cpc.getIdpago());
            rs = ps.executeQuery();
            if (rs.next()) {
                cpc.setIdpago(rs.getInt(1));
                cpc.setIdcompra(rs.getInt(2));
                cpc.setNumero(rs.getInt(3));
                cpc.setFechapago(rs.getDate(4));
                cpc.setMonto(rs.getDouble(5));
                cpc.setIdcuenta(rs.getInt(6));
                cpc.setIdusuario(rs.getInt(7));
                cpc.setNumerocomprobante(rs.getString(8));
                cpc.setIdtipomovimiento(rs.getInt(9));
                con.close();
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "NO EXISTE PAGO CON EL CÓDIGO INGRESADO...", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
                con.close();
                return false;
            }
        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "HA OCURRIDO UN ERROR AL OBTENER EL REGISTRO SELECCIONADO \n" + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    @Override
    public ArrayList<Object[]> consultarCuotasCompra(int idproveedor, int idmoneda, String criterio) {
        String sql = "SELECT\n"
                + "C.idproveedor, \n"
                + "C.idmoneda,\n"
                + "C.numerodocumento, C.numerotimbrado, (C.totalneto + C.totaliva) AS total_documento,\n"
                + "CC.numero, CC.monto AS monto_cuota, DATE_FORMAT(CC.fechavencimiento,'%d/%m/%Y') AS vencimiento,\n"
                + "CC.monto - IFNULL((SELECT sum(p.monto) FROM compra_pago_cuota AS p WHERE p.idcompra = CC.idcompra AND p.numero = CC.numero), 0) AS saldo,\n"
                + "C.idcompra\n"
                + "FROM compra_cuota AS CC\n"
                + "INNER JOIN compra AS C ON C.idcompra = CC.idcompra\n"
                + "LEFT JOIN compra_pago_cuota AS CPC ON CPC.idcompra = CC.idcompra AND CPC.numero = CC.numero\n"
                + "INNER JOIN proveedor AS P ON P.idproveedor = C.idproveedor\n"
                + "INNER JOIN moneda AS M ON M.idmoneda = C.idmoneda\n"
                + "WHERE CC.monto - IFNULL((SELECT sum(p.monto) FROM compra_pago_cuota AS p \n"
                + "WHERE p.idcompra = CC.idcompra AND p.numero = CC.numero), 0) > 0 \n"
                + "AND P.idproveedor = ?\n"
                + "AND M.idmoneda = ?\n"
                + "AND CONCAT(C.numerodocumento, DATE_FORMAT(CC.fechavencimiento,'%d/%m/%Y')) LIKE ?\n"
                + "GROUP BY C.numerodocumento, CC.numero, CC.fechavencimiento;";
        Connection con;
        PreparedStatement ps;
        ResultSet rs;
        ArrayList<Object[]> datos = new ArrayList<>();
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setInt(1, idproveedor);
            ps.setInt(2, idmoneda);
            ps.setString(3, "%" + criterio + "%");
            rs = ps.executeQuery();
            while (rs.next()) {
                Object[] fila = new Object[10];
                fila[0] = rs.getInt(1);
                fila[1] = rs.getInt(2);
                fila[2] = rs.getString(3);
                fila[3] = rs.getInt(4);
                fila[4] = rs.getDouble(5);
                fila[5] = rs.getInt(6);
                fila[6] = rs.getDouble(7);
                fila[7] = rs.getString(8);
                fila[8] = rs.getDouble(9);
                fila[9] = rs.getInt(10);
                datos.add(fila);
            }
            con.close();
        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "HA OCURRIDO UN ERROR AL OBTENER LA LISTA DE LOS DATOS \n" + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return datos;
    }

    @Override
    public ArrayList<Object[]> consultarPagosCuotas(int idproveedor, String criterio) {
        String sql = "SELECT \n"
                + "CPC.idpago, \n"
                + "C.numerodocumento AS comprobante_factura,\n"
                + "CPC.numerocomprobante AS comprobante_pago,\n"
                + "CPC.monto,\n"
                + "CPC.idcuenta,\n"
                + "CU.descripcion AS cuenta_bancaria,\n"
                + "CPC.idusuario,\n"
                + "CONCAT(U.nombre,' ',U.apellido) AS usuario_pago,\n"
                + "DATE_FORMAT(CPC.fechapago, '%d/%m/%Y') AS fecha_pago\n"
                + "FROM compra_pago_cuota AS CPC\n"
                + "INNER JOIN compra_cuota AS CC ON CC.idcompra = CPC.idcompra AND CC.numero = CPC.numero\n"
                + "INNER JOIN compra AS C ON C.idcompra = CC.idcompra\n"
                + "INNER JOIN cuenta AS CU ON CU.idcuenta = CPC.idcuenta\n"
                + "INNER JOIN usuario AS U ON U.idusuario = CPC.idusuario\n"
                + "WHERE CONCAT(C.numerodocumento, CPC.numerocomprobante, DATE_FORMAT(CPC.fechapago, '%d/%m/%Y')) LIKE ?\n"
                + "AND C.idproveedor = ?\n"
                + "ORDER BY CPC.numerocomprobante;";
        Connection con;
        PreparedStatement ps;
        ResultSet rs;
        ArrayList<Object[]> datos = new ArrayList<>();
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setString(1, "%" + criterio + "%");
            ps.setInt(2, idproveedor);
            rs = ps.executeQuery();
            while (rs.next()) {
                Object[] fila = new Object[9];
                fila[0] = rs.getInt(1);
                fila[1] = rs.getString(2);
                fila[2] = rs.getString(3);
                fila[3] = rs.getDouble(4);
                fila[4] = rs.getInt(5);
                fila[5] = rs.getString(6);
                fila[6] = rs.getInt(7);
                fila[7] = rs.getString(8);
                fila[8] = rs.getString(9);
                datos.add(fila);
            }
            con.close();
        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "HA OCURRIDO UN ERROR AL OBTENER LA LISTA DE LOS DATOS \n" + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return datos;
    }

}
