package Dao;

import Controladores.Database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import Controladores.OperacionesVenta;
import Modelos.Venta;
import java.sql.Date;
import java.util.ArrayList;

/**
 *
 * @author armando
 */
public class DAOVenta implements OperacionesVenta {

    //CONEXION A LAS CLASE DE MODELOS Y CONTROLADORES
    Database db = new Database();
    Venta v = new Venta();

    @Override
    public boolean agregar(Object obj) {
        v = (Venta) obj;
        String sql = "INSERT INTO venta\n"
                + "(idventa, numerodocumento, numerotimbrado, fecha, \n"
                + "observacion, idmoneda, iddeposito, idtipomovimiento, \n"
                + "idcliente, idusuario, totalneto, totaliva, \n"
                + "idcuenta, idempresa, idsucursal, idvendedor)\n"
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        Connection con;
        PreparedStatement ps;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setInt(1, v.getIdventa());
            ps.setString(2, v.getNumerodocumento());
            ps.setInt(3, v.getNumerotimbrado());
            ps.setDate(4, (Date) v.getFecha());
            ps.setString(5, v.getObservacion());
            ps.setInt(6, v.getIdmoneda());
            ps.setInt(7, v.getIddeposito());
            ps.setInt(8, v.getIdtipomovimiento());
            ps.setInt(9, v.getIdcliente());
            ps.setInt(10, v.getIdusuario());
            ps.setDouble(11, v.getTotalneto());
            ps.setDouble(12, v.getTotaliva());
            ps.setInt(13, v.getIdcuenta());
            ps.setInt(14, v.getIdempresa());
            ps.setInt(15, v.getIdsucursal());
            ps.setInt(16, v.getIdvendedor());
            int filas = ps.executeUpdate();
            if (filas > 0) {
                con.close();
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
        v = (Venta) obj;
        String sql = "UPDATE venta\n"
                + "	SET\n"
                + "		numerodocumento=?,\n"
                + "		numerotimbrado=?,\n"
                + "		fecha=?,\n"
                + "		observacion=?,\n"
                + "		idmoneda=?,\n"
                + "		iddeposito=?,\n"
                + "		idtipomovimiento=?,\n"
                + "		idcliente=?,\n"
                + "		idusuario=?,\n"
                + "		totalneto=?,\n"
                + "		totaliva=?,\n"
                + "		idcuenta=?,\n"
                + "		idempresa=?,\n"
                + "		idsucursal=?,\n"
                + "		idvendedor=?\n"
                + "	WHERE idventa=?;";
        Connection con;
        PreparedStatement ps;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setString(1, v.getNumerodocumento());
            ps.setInt(2, v.getNumerotimbrado());
            ps.setDate(3, (Date) v.getFecha());
            ps.setString(4, v.getObservacion());
            ps.setInt(5, v.getIdmoneda());
            ps.setInt(6, v.getIddeposito());
            ps.setInt(7, v.getIdtipomovimiento());
            ps.setInt(8, v.getIdcliente());
            ps.setInt(9, v.getIdusuario());
            ps.setDouble(10, v.getTotalneto());
            ps.setDouble(11, v.getTotaliva());
            ps.setInt(12, v.getIdcuenta());
            ps.setInt(13, v.getIdempresa());
            ps.setInt(14, v.getIdsucursal());
            ps.setInt(15, v.getIdvendedor());
            ps.setInt(16, v.getIdventa());
            int filas = ps.executeUpdate();
            if (filas > 0) {
                con.close();
                JOptionPane.showMessageDialog(null, "VENTA ACTUALIZADA EXITOSAMENTE", "EXITO", JOptionPane.INFORMATION_MESSAGE);
                return true;
            } else {
                con.close();
                return false;
            }
        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "HA OCURRIDO UN ERROR AL ACTUALIZAR LOS DATOS \n" + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    @Override
    public boolean eliminar(Object obj) {
        v = (Venta) obj;
        String sql = "DELETE FROM venta WHERE idventa = ?;";
        Connection con;
        PreparedStatement ps;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setInt(1, v.getIdventa());
            int filas = ps.executeUpdate();
            if (filas > 0) {
                con.close();
                JOptionPane.showMessageDialog(null, "VENTA ELIMINADA EXITOSAMENTE", "EXITO", JOptionPane.INFORMATION_MESSAGE);
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
        String sql = "select idventa + 1 as proximo_cod_libre\n"
                + "  from (select 0 as idventa\n"
                + "         union all\n"
                + "        select idventa\n"
                + "          from venta) t1\n"
                + " where not exists (select null\n"
                + "                     from venta t2\n"
                + "                    where t2.idventa = t1.idventa + 1)\n"
                + " order by idventa\n"
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
    public boolean verificarExistenciaVenta(String numerodocumento, int numerotimbrado) {
        String sql = "SELECT COUNT(*) CONTADOR FROM venta AS V WHERE V.numerodocumento LIKE ? AND V.numerotimbrado = ?;";
        Connection con;
        PreparedStatement ps;
        ResultSet rs;
        boolean resultado = false;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setString(1, numerodocumento);
            ps.setInt(2, numerotimbrado);
            rs = ps.executeQuery();
            if (rs.next()) {
                if (rs.getInt(1) >= 1) {
                    resultado = true;
                } else {
                    resultado = false;
                }
            }
            con.close();
        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "HA OCURRIDO UN ERROR AL VERIFICAR EL NUMERO DEL DOCUMENTO \n" + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return resultado;
    }

    @Override
    public ArrayList<Object[]> consultar(String criterio, int idtipomovimiento) {
        String sql = "SELECT\n"
                + "CONVERT(SUBSTR(V.numerodocumento, 1, 3), INTEGER) AS Establecimiento,\n"
                + "CONVERT(SUBSTR(V.numerodocumento, 5, 3), INTEGER) AS PuntoEmision,\n"
                + "CONVERT(SUBSTR(V.numerodocumento, 9, 7), INTEGER) AS Numero,\n"
                + "V.numerodocumento AS Comprobante,\n"
                + "V.numerotimbrado AS timbrado,\n"
                + "V.idventa AS CodigoVenta,\n"
                + "DATE_FORMAT(V.fecha, '%d/%m/%Y') AS FechaVenta,\n"
                + "V.observacion AS ObservacionVenta,\n"
                + "V.idmoneda AS CodigoMoneda,\n"
                + "M.descripcion AS DescripcionMoneda,\n"
                + "V.iddeposito AS CodigoDeposito,\n"
                + "D.descripcion AS DescripcionDeposito,\n"
                + "V.idcliente AS CodigoCliente,\n"
                + "CONCAT(C.nombre,' ',C.apellido) AS DescripcionCliente,\n"
                + "V.idusuario AS CodigoUsuario,\n"
                + "CONCAT(U.nombre,' ',U.apellido) AS DescripcionUsuario,\n"
                + "V.totalneto AS MontoTotalSinIva,\n"
                + "V.totaliva AS MontoTotalIva,\n"
                + "IF(V.idcuenta=0, NULL, V.idcuenta) AS CodigoCuenta,\n"
                + "IF(V.idcuenta=0, 'NULO',CU.descripcion) AS DescripcionCuenta,\n"
                + "V.idempresa AS CodigoEmpresa,\n"
                + "V.idsucursal AS CodigoSucursal\n"
                + "FROM venta AS V\n"
                + "INNER JOIN moneda AS M ON M.idmoneda = V.idmoneda\n"
                + "INNER JOIN deposito AS D ON D.iddeposito = V.iddeposito\n"
                + "INNER JOIN cliente AS C ON C.idcliente = V.idcliente\n"
                + "INNER JOIN usuario AS U ON U.idusuario = V.idusuario\n"
                + "LEFT JOIN cuenta AS CU ON CU.idcuenta = V.idcuenta\n"
                + "WHERE CONCAT(V.numerodocumento, V.numerotimbrado, C.nombre, C.apellido, C.ruc, C.telefono) LIKE ?\n"
                + "AND V.idtipomovimiento = ?\n"
                + "ORDER BY V.fecha;";
        Connection con;
        PreparedStatement ps;
        ResultSet rs;
        ArrayList<Object[]> datos = new ArrayList<>();
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setString(1, "%" + criterio + "%");
            ps.setInt(2, idtipomovimiento);
            rs = ps.executeQuery();
            while (rs.next()) {
                Object[] fila = new Object[22];
                fila[0] = rs.getInt(1);
                fila[1] = rs.getInt(2);
                fila[2] = rs.getInt(3);
                fila[3] = rs.getString(4);
                fila[4] = rs.getInt(5);
                fila[5] = rs.getInt(6);
                fila[6] = rs.getString(7);
                fila[7] = rs.getString(8);
                fila[8] = rs.getInt(9);
                fila[9] = rs.getString(10);
                fila[10] = rs.getInt(11);
                fila[11] = rs.getString(12);
                fila[12] = rs.getInt(13);
                fila[13] = rs.getString(14);
                fila[14] = rs.getInt(15);
                fila[15] = rs.getString(16);
                fila[16] = rs.getDouble(17);
                fila[17] = rs.getDouble(18);
                fila[18] = rs.getInt(19);
                fila[19] = rs.getString(20);
                fila[20] = rs.getInt(21);
                fila[21] = rs.getInt(22);
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
        v = (Venta) obj;
        String sql = "SELECT * FROM venta WHERE idventa = ?;";
        Connection con;
        PreparedStatement ps;
        ResultSet rs;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setInt(1, v.getIdventa());
            rs = ps.executeQuery();
            if (rs.next()) {
                v.setNumerodocumento(rs.getString(2));
                v.setNumerotimbrado(rs.getInt(3));
                con.close();
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "NO EXISTE VENTA CON EL CÓDIGO INGRESADO...", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
                con.close();
                return false;
            }
        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "HA OCURRIDO UN ERROR AL OBTENER EL REGISTRO SELECCIONADO \n" + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
}
