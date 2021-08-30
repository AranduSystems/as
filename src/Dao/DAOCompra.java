package Dao;

import Controladores.Database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import Controladores.OperacionesCompra;
import Modelos.Compra;
import java.sql.Date;
import java.util.ArrayList;

/**
 *
 * @author armando
 */
public class DAOCompra implements OperacionesCompra {

    //CONEXION A LAS CLASE DE MODELOS Y CONTROLADORES
    Database db = new Database();
    Compra c = new Compra();

    @Override
    public boolean agregar(Object obj) {
        c = (Compra) obj;
        String sql = "INSERT INTO compra\n"
                + "(idcompra, numerodocumento, numerotimbrado, fecha, \n"
                + "observacion, idmoneda, iddeposito, idtipomovimiento, idproveedor, idusuario, totalneto, totaliva, idcuenta)\n"
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        Connection con;
        PreparedStatement ps;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setInt(1, c.getIdcompra());
            ps.setString(2, c.getNumerodocumento());
            ps.setInt(3, c.getNumerotimbrado());
            ps.setDate(4, (Date) c.getFecha());
            ps.setString(5, c.getObservacion());
            ps.setInt(6, c.getIdmoneda());
            ps.setInt(7, c.getIddeposito());
            ps.setInt(8, c.getIdtipomovimiento());
            ps.setInt(9, c.getIdproveedor());
            ps.setInt(10, c.getIdusuario());
            ps.setDouble(11, c.getTotalneto());
            ps.setDouble(12, c.getTotaliva());
            ps.setInt(13, c.getIdcuenta());
            int filas = ps.executeUpdate();
            if (filas > 0) {
                con.close();
                //JOptionPane.showMessageDialog(null, "COMPRA REGISTRADA EXITOSAMENTE", "EXITO", JOptionPane.INFORMATION_MESSAGE);
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
        c = (Compra) obj;
        String sql = "UPDATE compra\n"
                + "	SET\n"
                + "		numerodocumento=?,\n"
                + "		numerotimbrado=?,\n"
                + "		fecha=?,\n"
                + "		observacion=?,\n"
                + "		idmoneda=?,\n"
                + "		iddeposito=?,\n"
                + "		idtipomovimiento=?,\n"
                + "		idproveedor=?,\n"
                + "		idusuario=?\n"
                + "		totalneto=?\n"
                + "		totaliva=?\n"
                + "		idcuenta=?\n"
                + "	WHERE idcompra=?;";
        Connection con;
        PreparedStatement ps;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setInt(1, c.getIdcompra());
            ps.setString(2, c.getNumerodocumento());
            ps.setInt(3, c.getNumerotimbrado());
            ps.setDate(4, (Date) c.getFecha());
            ps.setString(5, c.getObservacion());
            ps.setInt(6, c.getIdmoneda());
            ps.setInt(7, c.getIddeposito());
            ps.setInt(8, c.getIdtipomovimiento());
            ps.setInt(9, c.getIdproveedor());
            ps.setInt(10, c.getIdusuario());
            ps.setDouble(11, c.getTotalneto());
            ps.setDouble(12, c.getTotaliva());
            ps.setInt(13, c.getIdcuenta());
            int filas = ps.executeUpdate();
            if (filas > 0) {
                con.close();
                JOptionPane.showMessageDialog(null, "COMPRA ACTUALIZADA EXITOSAMENTE", "EXITO", JOptionPane.INFORMATION_MESSAGE);
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
        c = (Compra) obj;
        String sql = "DELETE FROM compra WHERE idcompra = ?;";
        Connection con;
        PreparedStatement ps;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setInt(1, c.getIdcompra());
            int filas = ps.executeUpdate();
            if (filas > 0) {
                con.close();
                JOptionPane.showMessageDialog(null, "COMPRA ELIMINADA EXITOSAMENTE", "EXITO", JOptionPane.INFORMATION_MESSAGE);
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
        String sql = "select idcompra + 1 as proximo_cod_libre\n"
                + "  from (select 0 as idcompra\n"
                + "         union all\n"
                + "        select idcompra\n"
                + "          from compra) t1\n"
                + " where not exists (select null\n"
                + "                     from compra t2\n"
                + "                    where t2.idcompra = t1.idcompra + 1)\n"
                + " order by idcompra\n"
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
    public boolean verificarExistenciaCompra(String numerodocumento, int numerotimbrado) {
        String sql = "SELECT COUNT(*) CONTADOR FROM compra AS C WHERE C.numerodocumento LIKE ? AND C.numerotimbrado = ?;";
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
            JOptionPane.showMessageDialog(null, "HA OCURRIDO UN ERROR AL VERIFICAR EL CÓDIGO DE BARRA \n" + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return resultado;
    }

    @Override
    public ArrayList<Object[]> consultar(String criterio, int idtipomovimiento) {
        String sql = "SELECT\n"
                + "CONVERT(SUBSTR(C.numerodocumento, 1, 3), INTEGER) AS Establecimiento,\n"
                + "CONVERT(SUBSTR(C.numerodocumento, 5, 3), INTEGER) AS PuntoEmision,\n"
                + "CONVERT(SUBSTR(C.numerodocumento, 9, 7), INTEGER) AS Numero,\n"
                + "C.numerodocumento AS Comprobante,\n"
                + "C.numerotimbrado AS timbrado,\n"
                + "C.idcompra AS CodigoCompra,\n"
                + "DATE_FORMAT(C.fecha, '%d/%m/%Y') AS FechaCompra,\n"
                + "C.observacion AS ObservacionCompra,\n"
                + "C.idmoneda AS CodigoMoneda,\n"
                + "M.descripcion AS DescripcionMoneda,\n"
                + "C.iddeposito AS CodigoDeposito,\n"
                + "D.descripcion AS DescripcionDeposito,\n"
                + "C.idproveedor AS CodigoProveedor,\n"
                + "P.razonsocial AS DescripcionProveedor,\n"
                + "C.idusuario AS CodigoUsuario,\n"
                + "CONCAT(U.nombre,' ',U.apellido) AS DescripcionUsuario,\n"
                + "C.totalneto AS MontoTotalSinIva,\n"
                + "C.totaliva AS MontoTotalIva,\n"
                + "IF(C.idcuenta=0, NULL, C.idcuenta) AS CodigoCuenta,\n"
                + "IF(C.idcuenta=0, 'NULO',CU.descripcion) AS DescripcionCuenta\n"
                + "FROM compra AS C\n"
                + "INNER JOIN moneda AS M ON M.idmoneda = C.idmoneda\n"
                + "INNER JOIN deposito AS D ON D.iddeposito = C.iddeposito\n"
                + "INNER JOIN proveedor AS P ON P.idproveedor = C.idproveedor\n"
                + "INNER JOIN usuario AS U ON U.idusuario = C.idusuario\n"
                + "LEFT JOIN cuenta AS CU ON CU.idcuenta = C.idcuenta\n"
                + "WHERE CONCAT(C.numerodocumento, C.numerotimbrado, P.razonsocial, P.ruc, P.telefono) LIKE ?\n"
                + "AND C.idtipomovimiento = ?\n"
                + "ORDER BY C.fecha;";
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
                Object[] fila = new Object[20];
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
        c = (Compra) obj;
        String sql = "SELECT * FROM compra WHERE idcompra = ?;";
        Connection con;
        PreparedStatement ps;
        ResultSet rs;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setInt(1, c.getIdcompra());
            rs = ps.executeQuery();
            if (rs.next()) {
                c.setNumerodocumento(rs.getString(2));
                c.setNumerotimbrado(rs.getInt(3));
                con.close();
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "NO EXISTE COMPRA CON EL CÓDIGO INGRESADO...", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
                con.close();
                return false;
            }
        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "HA OCURRIDO UN ERROR AL OBTENER EL REGISTRO SELECCIONADO \n" + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
}
