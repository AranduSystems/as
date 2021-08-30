package Dao;

import Controladores.Database;
import Controladores.OperacionesArticuloPeriodo;
import Modelos.ArticuloPeriodo;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author armando
 */
public class DAOArticuloPeriodo implements OperacionesArticuloPeriodo {

    //CONEXION A LAS CLASE DE MODELOS Y CONTROLADORES
    Database db = new Database();
    ArticuloPeriodo ap = new ArticuloPeriodo();

    @Override
    public boolean consultarDatos(Object obj) {
        ap = (ArticuloPeriodo) obj;
        String sql = "SELECT costo FROM articulo_periodo WHERE idarticulo = ? AND idperiodo = ? AND idmoneda = ?;";
        Connection con;
        PreparedStatement ps;
        ResultSet rs;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setInt(1, ap.getIdarticulo());
            ps.setInt(2, ap.getIdperiodo());
            ps.setInt(3, ap.getIdmoneda());
            rs = ps.executeQuery();
            if (rs.next()) {
                ap.setCosto(rs.getDouble(1));
                con.close();
                return true;
            } else {
                ap.setCosto(0);
                con.close();
                return false;
            }
        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "HA OCURRIDO UN ERROR AL OBTENER EL REGISTRO SELECCIONADO \n" + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    @Override
    public int obtenerPeriodo(String fecha) {
        String sql = "SELECT FP_ACT_PERIODO_INS_UPD(?)";
        Connection con;
        PreparedStatement ps;
        ResultSet rs;
        boolean valor = false;
        int idperiodo = 0;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setString(1, fecha);
            rs = ps.executeQuery();
            if (rs.next()) {
                idperiodo = rs.getInt(1);
            }
            con.close();
        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "HA OCURRIDO UN ERROR AL OBTENER PERIODO\n" + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return idperiodo;
    }
}