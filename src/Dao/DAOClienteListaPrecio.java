package Dao;

import Controladores.Database;
import Controladores.OperacionesClienteListaPrecio;
import Modelos.ClienteListaPrecio;
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
public class DAOClienteListaPrecio implements OperacionesClienteListaPrecio {

    //CONEXION A LAS CLASE DE MODELOS Y CONTROLADORES
    Database db = new Database();
    ClienteListaPrecio clp = new ClienteListaPrecio();

    @Override
    public boolean agregar(Object obj) {
        clp = (ClienteListaPrecio) obj;
        String sql = "INSERT INTO cliente_lista_precio\n"
                + "(idcliente, idlista, descuento, recargo)\n"
                + "VALUES (?, ?, ?, ?);";
        Connection con;
        PreparedStatement ps;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setInt(1, clp.getIdcliente());
            ps.setInt(2, clp.getIdlista());
            ps.setDouble(3, clp.getDescuento());
            ps.setDouble(4, clp.getRecargo());
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
        clp = (ClienteListaPrecio) obj;
        String sql = "UPDATE cliente_lista_precio\n"
                + "	SET\n"
                + "		descuento=?,\n"
                + "		recargo=?\n"
                + "	WHERE idcliente=? AND idlista=?;";
        Connection con;
        PreparedStatement ps;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setDouble(1, clp.getDescuento());
            ps.setDouble(2, clp.getRecargo());
            ps.setInt(3, clp.getIdcliente());
            ps.setInt(4, clp.getIdlista());
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
        clp = (ClienteListaPrecio) obj;
        String sql = "DELETE FROM cliente_lista_precio WHERE idcliente=? AND idlista=?;";
        Connection con;
        PreparedStatement ps;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setInt(1, clp.getIdcliente());
            ps.setInt(2, clp.getIdlista());
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
    public boolean consultarDatos(Object obj) {
        clp = (ClienteListaPrecio) obj;
        String sql = "SELECT \n"
                + "idcliente, \n"
                + "idlista, \n"
                + "descuento, \n"
                + "recargo\n"
                + "FROM cliente_lista_precio AS CLP\n"
                + "WHERE CLP.idcliente = ? AND CLP.idlista = ?;";
        Connection con;
        PreparedStatement ps;
        ResultSet rs;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setInt(1, clp.getIdcliente());
            ps.setInt(2, clp.getIdlista());
            rs = ps.executeQuery();
            if (rs.next()) {
                clp.setIdcliente(rs.getInt(1));
                clp.setIdlista(rs.getInt(2));
                clp.setDescuento(rs.getDouble(3));
                clp.setRecargo(rs.getDouble(4));
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
    public ArrayList<Object[]> consultar(int idcliente) {
        String sql = "SELECT \n"
                + "CLP.idcliente, \n"
                + "CONCAT(C.nombre,' ',C.apellido) AS cliente,\n"
                + "C.ruc,\n"
                + "CLP.idlista, \n"
                + "LP.descripcion AS lista_precio,\n"
                + "CLP.descuento, \n"
                + "CLP.recargo\n"
                + "FROM cliente_lista_precio AS CLP\n"
                + "INNER JOIN cliente AS C ON C.idcliente = CLP.idcliente\n"
                + "INNER JOIN lista_precio AS LP ON LP.idlista = CLP.idlista\n"
                + "WHERE CLP.idcliente = ?\n"
                + "ORDER BY LP.descripcion;";
        Connection con;
        PreparedStatement ps;
        ResultSet rs;
        ArrayList<Object[]> datos = new ArrayList<>();
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setInt(1, idcliente);
            rs = ps.executeQuery();
            while (rs.next()) {
                Object[] fila = new Object[7];
                fila[0] = rs.getInt(1);
                fila[1] = rs.getString(2);
                fila[2] = rs.getString(3);
                fila[3] = rs.getInt(4);
                fila[4] = rs.getString(5);
                fila[5] = rs.getDouble(6);
                fila[6] = rs.getDouble(7);
                datos.add(fila);
            }
            con.close();
        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "HA OCURRIDO UN ERROR AL OBTENER LA LISTA DE LOS DATOS \n" + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return datos;
    }
}
