package Dao;

import Controladores.Database;
import Controladores.OperacionesArticuloDeposito;
import Modelos.ArticuloDeposito;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author armando
 */
public class DAOArticuloDeposito implements OperacionesArticuloDeposito {

    //CONEXION A LAS CLASE DE MODELOS Y CONTROLADORES
    Database db = new Database();
    ArticuloDeposito ad = new ArticuloDeposito();

    @Override
    public boolean verificarExistenciaDeposito(int idarticulo, int iddeposito) {
        String sql = "SELECT\n"
                + "AD.cantidad\n"
                + "FROM articulo_deposito AS AD\n"
                + "WHERE AD.idarticulo = ?\n"
                + "AND 	AD.iddeposito = ?;";
        Connection con;
        PreparedStatement ps;
        ResultSet rs;
        boolean valor = false;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setInt(1, idarticulo);
            ps.setInt(2, iddeposito);
            rs = ps.executeQuery();
            if (rs.next()) {
                if (rs.getInt(1) >= 1) {
                    valor = true;
                } else {
                    valor = false;
                }
            }
            con.close();
        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "HA OCURRIDO UN ERROR AL VERIFICAR LAS EXISTENCIAS \n" + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return valor;
    }

    @Override
    public boolean consultarDatos(Object obj) {
        ad = (ArticuloDeposito) obj;
        String sql = "SELECT * FROM articulo_deposito WHERE idarticulo = ? AND iddeposito = ?;";
        Connection con;
        PreparedStatement ps;
        ResultSet rs;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setInt(1, ad.getIdarticulo());
            ps.setInt(2, ad.getIddeposito());
            rs = ps.executeQuery();
            if (rs.next()) {
                ad.setIdarticulo(rs.getInt(1));
                ad.setIddeposito(rs.getInt(2));
                ad.setCantidad(rs.getDouble(3));
                con.close();
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "EL ARTICULO NO POSEE EXISTENCIAS...", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
                con.close();
                return false;
            }
        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "HA OCURRIDO UN ERROR AL OBTENER EL REGISTRO SELECCIONADO \n" + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
}
