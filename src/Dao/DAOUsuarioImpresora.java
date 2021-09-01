package Dao;

import Controladores.Database;
import Controladores.OperacionesUsuarioImpresora;
import Modelos.UsuarioImpresora;
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
public class DAOUsuarioImpresora implements OperacionesUsuarioImpresora {

    //CONEXION A LAS CLASE DE MODELOS Y CONTROLADORES
    Database db = new Database();
    UsuarioImpresora ui = new UsuarioImpresora();

    @Override
    public boolean agregar(Object obj) {
        ui = (UsuarioImpresora) obj;
        String sql = "INSERT INTO usuario_impresora\n"
                + "(idusuario, idimpresora)\n"
                + "VALUES (?, ?);";
        Connection con;
        PreparedStatement ps;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setInt(1, ui.getIdusuario());
            ps.setInt(2, ui.getIdimpresora());
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
    public boolean eliminar(Object obj) {
        ui = (UsuarioImpresora) obj;
        String sql = "DELETE FROM usuario_impresora WHERE idusuario=?;";
        Connection con;
        PreparedStatement ps;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setInt(1, ui.getIdusuario());
            int filas = ps.executeUpdate();
            if (filas > 0) {
                con.close();
                //JOptionPane.showMessageDialog(null, "ELIMINACIÓN EXITOSA", "EXITO", JOptionPane.INFORMATION_MESSAGE);
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
    public boolean consultarDatos(Object obj) {
        ui = (UsuarioImpresora) obj;
        String sql = "SELECT *\n"
                + "FROM usuario_impresora a WHERE a.idusuario = ? AND a.idimpresora = ?;";
        Connection con;
        PreparedStatement ps;
        ResultSet rs;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setInt(1, ui.getIdusuario());
            ps.setInt(2, ui.getIdimpresora());
            rs = ps.executeQuery();
            if (rs.next()) {
                ui.setIdusuario(rs.getInt(1));
                ui.setIdimpresora(rs.getInt(2));
                con.close();
                return true;
            } else {
                con.close();
                return false;
            }
        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "HA OCURRIDO UN ERROR AL OBTENER EL REGISTRO SELECCIONADO \n" + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    @Override
    public ArrayList<Object[]> consultar(String criterio) {
        String sql = "SELECT \n"
                + "UI.idusuario, \n"
                + "CONCAT(U.nombre,' ',U.apellido) AS usuario,\n"
                + "UI.idimpresora,\n"
                + "I.descripcion AS impresora\n"
                + "FROM usuario_impresora AS UI\n"
                + "INNER JOIN usuario AS U ON U.idusuario = UI.idusuario\n"
                + "INNER JOIN impresora AS I ON I.idimpresora = UI.idimpresora\n"
                + "WHERE CONCAT(I.descripcion, U.nombre, U.apellido, U.cedula) LIKE ?\n"
                + "ORDER BY I.descripcion;";
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
                Object[] fila = new Object[4];
                fila[0] = rs.getInt(1);
                fila[1] = rs.getString(2);
                fila[2] = rs.getInt(3);
                fila[3] = rs.getString(4);
                datos.add(fila);
            }
            con.close();
        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "HA OCURRIDO UN ERROR AL OBTENER LA LISTA DE LOS DATOS \n" + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return datos;
    }

    @Override
    public boolean consultarDatosImpresora(Object obj) {
        ui = (UsuarioImpresora) obj;
        String sql = "SELECT *\n"
                + "FROM usuario_impresora a WHERE a.idusuario = ?;";
        Connection con;
        PreparedStatement ps;
        ResultSet rs;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setInt(1, ui.getIdusuario());
            rs = ps.executeQuery();
            if (rs.next()) {
                ui.setIdusuario(rs.getInt(1));
                ui.setIdimpresora(rs.getInt(2));
                con.close();
                return true;
            } else {
                con.close();
                return false;
            }
        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "HA OCURRIDO UN ERROR AL OBTENER EL REGISTRO SELECCIONADO \n" + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
}
