package Dao;

import Controladores.Database;
import Controladores.OperacionesArticuloListaPrecio;
import Modelos.ArticuloListaPrecio;
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
public class DAOArticuloListaPrecio implements OperacionesArticuloListaPrecio {

    //CONEXION A LAS CLASE DE MODELOS Y CONTROLADORES
    Database db = new Database();
    ArticuloListaPrecio alp = new ArticuloListaPrecio();

    @Override
    public boolean agregar(Object obj) {
        alp = (ArticuloListaPrecio) obj;
        String sql = "INSERT INTO articulo_lista_precio VALUES(?, ?, ?);";
        Connection con;
        PreparedStatement ps;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setInt(1, alp.getIdarticulo());
            ps.setInt(2, alp.getIdlista());
            ps.setDouble(3, alp.getPrecio());
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
        alp = (ArticuloListaPrecio) obj;
        String sql = "UPDATE articulo_lista_precio SET precio = ? WHERE idarticulo = ? AND idlista = ?;";
        Connection con;
        PreparedStatement ps;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setDouble(1, alp.getPrecio());
            ps.setInt(2, alp.getIdarticulo());
            ps.setInt(3, alp.getIdlista());
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
        alp = (ArticuloListaPrecio) obj;
        String sql = "DELETE FROM articulo_lista_precio WHERE idarticulo = ? AND idlista = ?;";
        Connection con;
        PreparedStatement ps;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setInt(1, alp.getIdarticulo());
            ps.setInt(2, alp.getIdlista());
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
    public ArrayList<Object[]> consultar(String criterio) {
        String sql = "SELECT\n"
                + "ALP.idarticulo,\n"
                + "A.descripcion AS articulo,\n"
                + "ALP.idlista,\n"
                + "LP.descripcion AS lista_precio,\n"
                + "ALP.precio\n"
                + "FROM articulo_lista_precio AS ALP \n"
                + "INNER JOIN articulo AS A ON A.idarticulo = ALP.idarticulo\n"
                + "INNER JOIN lista_precio AS LP ON LP.idlista = ALP.idlista\n"
                + "WHERE CONCAT(A.descripcion, A.referencia, A.codigoalfanumerico, A.codigobarra, LP.descripcion) LIKE ?\n"
                + "ORDER BY A.descripcion;";
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
                Object[] fila = new Object[5];
                fila[0] = rs.getInt(1);
                fila[1] = rs.getString(2);
                fila[2] = rs.getInt(3);
                fila[3] = rs.getString(4);
                fila[4] = rs.getDouble(5);
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
        alp = (ArticuloListaPrecio) obj;
        String sql = "SELECT * FROM articulo_lista_precio WHERE idarticulo = ? AND idlista = ?;";
        Connection con;
        PreparedStatement ps;
        ResultSet rs;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setInt(1, alp.getIdarticulo());
            ps.setInt(2, alp.getIdlista());
            rs = ps.executeQuery();
            if (rs.next()) {
                alp.setIdarticulo(rs.getInt(1));
                alp.setIdlista(rs.getInt(2));
                alp.setPrecio(rs.getDouble(3));
                con.close();
                return true;
            } else {
                //JOptionPane.showMessageDialog(null, "NO EXISTE ARTICULO/LISTA PRECIO CON EL CÓDIGO INGRESADO...", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
                con.close();
                return false;
            }
        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "HA OCURRIDO UN ERROR AL OBTENER EL REGISTRO SELECCIONADO \n" + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

}
