package Dao;

import Controladores.Database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import Controladores.OperacionesVendedor;
import Modelos.Vendedor;

/**
 *
 * @author armando
 */
public class DAOVendedor implements OperacionesVendedor {

    //CONEXION A LAS CLASE DE MODELOS Y CONTROLADORES
    Database db = new Database();
    Vendedor v = new Vendedor();

    @Override
    public boolean agregar(Object obj) {
        v = (Vendedor) obj;
        String sql = "INSERT INTO vendedor\n"
                + "(idvendedor, nombre, apellido, estado, porcentajecomision, idempresa, idsucursal)\n"
                + "VALUES (?, ?, ?, ?, ?, ?, ?);";
        Connection con;
        PreparedStatement ps;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setInt(1, v.getIdvendedor());
            ps.setString(2, v.getNombre());
            ps.setString(3, v.getApellido());
            ps.setString(4, v.getEstado());
            ps.setDouble(5, v.getPorcentajecomision());
            ps.setInt(6, v.getIdempresa());
            ps.setInt(7, v.getIdsucursal());
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
        v = (Vendedor) obj;
        String sql = "UPDATE vendedor\n"
                + "	SET\n"
                + "		nombre=?,\n"
                + "		apellido=?,\n"
                + "		estado=?,\n"
                + "		porcentajecomision=?,\n"
                + "		idempresa=?,\n"
                + "		idsucursal=?\n"
                + "	WHERE idvendedor=?;";
        Connection con;
        PreparedStatement ps;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setString(1, v.getNombre());
            ps.setString(2, v.getApellido());
            ps.setString(3, v.getEstado());
            ps.setDouble(4, v.getPorcentajecomision());
            ps.setInt(5, v.getIdempresa());
            ps.setInt(6, v.getIdsucursal());
            ps.setInt(7, v.getIdvendedor());
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
        v = (Vendedor) obj;
        String sql = "DELETE FROM vendedor WHERE idvendedor=?;";
        Connection con;
        PreparedStatement ps;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setInt(1, v.getIdvendedor());
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
        String sql = "select idvendedor + 1 as proximo_cod_libre\n"
                + "  from (select 0 as idvendedor\n"
                + "         union all\n"
                + "        select idvendedor\n"
                + "          from vendedor) t1\n"
                + " where not exists (select null\n"
                + "                     from vendedor t2\n"
                + "                    where t2.idvendedor = t1.idvendedor + 1)\n"
                + " order by idvendedor\n"
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
                + "V.idvendedor, \n"
                + "CONCAT(V.nombre,' ',V.apellido) as vendedor, \n"
                + "V.apellido, \n"
                + "IF(estado = 'A', 'ACTIVO', 'INACTIVO') AS estado,\n"
                + "V.porcentajecomision, \n"
                + "V.idempresa, \n"
                + "V.idsucursal\n"
                + "FROM vendedor AS V\n"
                + "INNER JOIN empresa AS E ON E.idempresa = V.idempresa\n"
                + "INNER JOIN sucursal AS S ON S.idsucursal = V.idsucursal\n"
                + "WHERE CONCAT(V.nombre, V.apellido, E.razonsocial, S.descripcion) LIKE ?\n"
                + "ORDER BY V.nombre;";
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
                Object[] fila = new Object[7];
                fila[0] = rs.getInt(1);
                fila[1] = rs.getString(2);
                fila[2] = rs.getString(3);
                fila[3] = rs.getString(4);
                fila[4] = rs.getDouble(5);
                fila[5] = rs.getInt(6);
                fila[6] = rs.getInt(7);
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
        v = (Vendedor) obj;
        String sql = "SELECT * FROM vendedor WHERE idvendedor = ?;";
        Connection con;
        PreparedStatement ps;
        ResultSet rs;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setInt(1, v.getIdvendedor());
            rs = ps.executeQuery();
            if (rs.next()) {
                v.setIdvendedor(rs.getInt(1));
                v.setNombre(rs.getString(2));
                v.setApellido(rs.getString(3));
                v.setEstado(rs.getString(4));
                v.setPorcentajecomision(rs.getDouble(5));
                v.setIdempresa(rs.getInt(6));
                v.setIdsucursal(rs.getInt(7));
                con.close();
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "NO EXISTE VENDEDOR CON EL CÓDIGO INGRESADO...", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
                con.close();
                return false;
            }
        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "HA OCURRIDO UN ERROR AL OBTENER EL REGISTRO SELECCIONADO \n" + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    @Override
    public boolean consultarDatosVendedorEmpresaSucursal(Object obj) {
        v = (Vendedor) obj;
        String sql = "SELECT * FROM vendedor WHERE idvendedor = ? AND idempresa = ? AND idsucursal = ?;";
        Connection con;
        PreparedStatement ps;
        ResultSet rs;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setInt(1, v.getIdvendedor());
            ps.setInt(2, v.getIdempresa());
            ps.setInt(3, v.getIdsucursal());
            rs = ps.executeQuery();
            if (rs.next()) {
                v.setIdvendedor(rs.getInt(1));
                v.setNombre(rs.getString(2));
                v.setApellido(rs.getString(3));
                v.setEstado(rs.getString(4));
                v.setPorcentajecomision(rs.getDouble(5));
                v.setIdempresa(rs.getInt(6));
                v.setIdsucursal(rs.getInt(7));
                con.close();
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "NO EXISTE VENDEDOR CON EL CÓDIGO INGRESADO...", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
                con.close();
                return false;
            }
        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "HA OCURRIDO UN ERROR AL OBTENER EL REGISTRO SELECCIONADO \n" + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    @Override
    public ArrayList<Object[]> consultarVendedorEmpresaSucursal(String criterio, int idempresa, int idsucursal) {
        String sql = "SELECT \n"
                + "V.idvendedor, \n"
                + "CONCAT(V.nombre,' ',V.apellido) as vendedor, \n"
                + "V.apellido, \n"
                + "IF(estado = 'A', 'ACTIVO', 'INACTIVO') AS estado,\n"
                + "V.porcentajecomision, \n"
                + "V.idempresa, \n"
                + "V.idsucursal\n"
                + "FROM vendedor AS V\n"
                + "INNER JOIN empresa AS E ON E.idempresa = V.idempresa\n"
                + "INNER JOIN sucursal AS S ON S.idsucursal = V.idsucursal\n"
                + "WHERE CONCAT(V.nombre, V.apellido, E.razonsocial, S.descripcion) LIKE ?\n"
                + "AND V.idempresa = ?\n"
                + "AND V.idsucursal = ?\n"
                + "ORDER BY V.nombre;";
        Connection con;
        PreparedStatement ps;
        ResultSet rs;
        ArrayList<Object[]> datos = new ArrayList<>();
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setString(1, "%" + criterio + "%");
            ps.setInt(2, idempresa);
            ps.setInt(3, idsucursal);
            rs = ps.executeQuery();
            while (rs.next()) {
                Object[] fila = new Object[7];
                fila[0] = rs.getInt(1);
                fila[1] = rs.getString(2);
                fila[2] = rs.getString(3);
                fila[3] = rs.getString(4);
                fila[4] = rs.getDouble(5);
                fila[5] = rs.getInt(6);
                fila[6] = rs.getInt(7);
                datos.add(fila);
            }
            con.close();
        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "HA OCURRIDO UN ERROR AL OBTENER LA LISTA DE LOS DATOS \n" + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return datos;
    }

}
