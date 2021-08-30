package Dao;

import Controladores.Database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import Controladores.OperacionesCaja;
import Modelos.Caja;
import Modelos.Impresora;

/**
 *
 * @author armando
 */
public class DAOImpresora implements OperacionesCaja {

    //CONEXION A LAS CLASE DE MODELOS Y CONTROLADORES
    Database db = new Database();
    Impresora i = new Impresora();

    @Override
    public boolean agregar(Object obj) {
        i = (Impresora) obj;
        String sql = "INSERT INTO impresora\n"
                + "(idimpresora, descripcion, \n"
                + "ultimo_numero_factura, ultimo_numero_recibo, \n"
                + "ultimo_numero_nota_credito, ultimo_numero_nota_debito)\n"
                + "VALUES (?, ?, ?, ?, ?, ?);";
        Connection con;
        PreparedStatement ps;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setInt(1, i.getIdimpresora());
            ps.setString(2, i.getDescripcion());
            ps.setString(3, i.getUltimo_numero_factura());
            ps.setString(4, i.getUltimo_numero_recibo());
            ps.setString(5, i.getUltimo_numero_nota_credito());
            ps.setString(6, i.getUltimo_numero_nota_debito());
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
        i = (Impresora) obj;
        String sql = "UPDATE impresora\n"
                + "	SET\n"
                + "		descripcion=?,\n"
                + "		ultimo_numero_factura=?,\n"
                + "		ultimo_numero_recibo=?,\n"
                + "		ultimo_numero_nota_credito=?,\n"
                + "		ultimo_numero_nota_debito=?\n"
                + "	WHERE idimpresora=?;";
        Connection con;
        PreparedStatement ps;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setString(1, i.getDescripcion());
            ps.setString(2, i.getUltimo_numero_factura());
            ps.setString(3, i.getUltimo_numero_recibo());
            ps.setString(4, i.getUltimo_numero_nota_credito());
            ps.setString(5, i.getUltimo_numero_nota_debito());
            ps.setInt(6, i.getIdimpresora());
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
        i = (Impresora) obj;
        String sql = "DELETE FROM impresora WHERE idimpresora=?;";
        Connection con;
        PreparedStatement ps;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setInt(1, i.getIdimpresora());
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
        String sql = "select idimpresora + 1 as proximo_cod_libre\n"
                + "  from (select 0 as idimpresora\n"
                + "         union all\n"
                + "        select idimpresora\n"
                + "          from impresora) t1\n"
                + " where not exists (select null\n"
                + "                     from impresora t2\n"
                + "                    where t2.idimpresora = t1.idimpresora + 1)\n"
                + " order by idimpresora\n"
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
                + "I.idimpresora, \n"
                + "I.descripcion, \n"
                + "I.ultimo_numero_factura, \n"
                + "I.ultimo_numero_recibo, \n"
                + "I.ultimo_numero_nota_credito, \n"
                + "I.ultimo_numero_nota_debito\n"
                + "FROM impresora AS I\n"
                + "WHERE I.descripcion LIKE ?\n"
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
                Object[] fila = new Object[6];
                fila[0] = rs.getInt(1);
                fila[1] = rs.getString(2);
                fila[2] = rs.getString(3);
                fila[3] = rs.getString(4);
                fila[4] = rs.getString(5);
                fila[5] = rs.getString(6);
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
        i = (Impresora) obj;
        String sql = "SELECT * FROM impresora WHERE idimpresora = ?;";
        Connection con;
        PreparedStatement ps;
        ResultSet rs;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setInt(1, i.getIdimpresora());
            rs = ps.executeQuery();
            if (rs.next()) {
                i.setIdimpresora(rs.getInt(1));
                i.setDescripcion(rs.getString(2));
                i.setUltimo_numero_factura(rs.getString(3));
                i.setUltimo_numero_recibo(rs.getString(4));
                i.setUltimo_numero_nota_credito(rs.getString(5));
                i.setUltimo_numero_nota_debito(rs.getString(6));
                con.close();
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "NO EXISTE IMPRESORA CON EL CÓDIGO INGRESADO...", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
                con.close();
                return false;
            }
        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "HA OCURRIDO UN ERROR AL OBTENER EL REGISTRO SELECCIONADO \n" + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

}
