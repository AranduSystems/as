package Dao;

import Controladores.Database;
import Controladores.OperacionesUsuario;
import Modelos.Usuario;
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
public class DAOUsuario implements OperacionesUsuario {

    //CONEXION A LAS CLASE DE MODELOS Y CONTROLADORES
    Database db = new Database();
    Usuario u = new Usuario();

    @Override
    public boolean agregar(Object obj) {
        u = (Usuario) obj;
        String sql = "INSERT INTO usuario VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        Connection con;
        PreparedStatement ps;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setInt(1, u.getIdusuario());
            ps.setString(2, u.getNombre());
            ps.setString(3, u.getApellido());
            ps.setString(4, u.getCedula());
            ps.setString(5, u.getTelefono());
            ps.setString(6, u.getDireccion());
            ps.setString(7, u.getAlias());
            ps.setString(8, u.getClave());
            ps.setInt(9, u.getIdempresa());
            ps.setInt(10, u.getIdsucursal());
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
        u = (Usuario) obj;
        String sql = "UPDATE usuario\n"
                + "	SET\n"
                + "		nombre=?,\n"
                + "		apellido=?,\n"
                + "		cedula=?,\n"
                + "		telefono=?,\n"
                + "		direccion=?,\n"
                + "		alias=?,\n"
                + "		clave=?,\n"
                + "		idempresa=?,\n"
                + "		idsucursal=?\n"
                + "	WHERE idusuario=?;";
        Connection con;
        PreparedStatement ps;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setString(1, u.getNombre());
            ps.setString(2, u.getApellido());
            ps.setString(3, u.getCedula());
            ps.setString(4, u.getTelefono());
            ps.setString(5, u.getDireccion());
            ps.setString(6, u.getAlias());
            ps.setString(7, u.getClave());
            ps.setInt(8, u.getIdempresa());
            ps.setInt(9, u.getIdusuario());
            ps.setInt(10, u.getIdusuario());
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
        u = (Usuario) obj;
        String sql = "DELETE FROM usuario WHERE idusuario = ?;";
        Connection con;
        PreparedStatement ps;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setInt(1, u.getIdusuario());
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
        String sql = "select idusuario + 1 as proximo_cod_libre\n"
                + "  from (select 0 as idusuario\n"
                + "         union all\n"
                + "        select idusuario\n"
                + "          from usuario) t1\n"
                + " where not exists (select null\n"
                + "                     from usuario t2\n"
                + "                    where t2.idusuario = t1.idusuario + 1)\n"
                + " order by idusuario\n"
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
                + "U.idusuario, \n"
                + "U.nombre, \n"
                + "U.apellido, \n"
                + "U.cedula, \n"
                + "U.telefono, \n"
                + "U.direccion, \n"
                + "U.alias, \n"
                + "U.idempresa, \n"
                + "E.razonsocial,\n"
                + "U.idsucursal,\n"
                + "S.descripcion\n"
                + "FROM usuario AS U\n"
                + "INNER JOIN empresa AS E ON E.idempresa = U.idempresa\n"
                + "INNER JOIN sucursal AS S ON S.idsucursal = U.idempresa\n"
                + "WHERE CONCAT(U.nombre, U.apellido, U.cedula, U.telefono, U.alias, E.razonsocial, S.descripcion) LIKE ?\n"
                + "ORDER BY U.nombre;";
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
                Object[] fila = new Object[11];
                fila[0] = rs.getInt(1);
                fila[1] = rs.getString(2);
                fila[2] = rs.getString(3);
                fila[3] = rs.getString(4);
                fila[4] = rs.getString(5);
                fila[5] = rs.getString(6);
                fila[6] = rs.getString(7);
                fila[7] = rs.getInt(8);
                fila[8] = rs.getString(9);
                fila[9] = rs.getInt(10);
                fila[10] = rs.getString(11);
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
        u = (Usuario) obj;
        String sql = "SELECT * FROM usuario WHERE idusuario = ?;";
        Connection con;
        PreparedStatement ps;
        ResultSet rs;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setInt(1, u.getIdusuario());
            rs = ps.executeQuery();
            if (rs.next()) {
                u.setIdusuario(rs.getInt(1));
                u.setNombre(rs.getString(2));
                u.setApellido(rs.getString(3));
                u.setCedula(rs.getString(4));
                u.setTelefono(rs.getString(5));
                u.setDireccion(rs.getString(6));
                u.setAlias(rs.getString(7));
                u.setClave(rs.getString(8));
                u.setIdempresa(rs.getInt(9));
                u.setIdsucursal(rs.getInt(10));
                con.close();
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "NO EXISTE USUARIO CON EL CÓDIGO INGRESADO...", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
                con.close();
                return false;
            }
        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "HA OCURRIDO UN ERROR AL OBTENER EL REGISTRO SELECCIONADO \n" + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

}
