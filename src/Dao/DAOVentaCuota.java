package Dao;

import Controladores.Database;
import Controladores.OperacionesVentaCuota;
import Modelos.VentaCuota;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author armando
 */
public class DAOVentaCuota implements OperacionesVentaCuota {

    //CONEXION A LAS CLASE DE MODELOS Y CONTROLADORES
    Database db = new Database();
    VentaCuota vc = new VentaCuota();

    @Override
    public boolean agregar(Object obj) {
        vc = (VentaCuota) obj;
        String sql = "INSERT INTO venta_cuota\n"
                + "(idventa, numero, monto, fechavencimiento)\n"
                + "VALUES (?, ?, ?, ?);";
        Connection con;
        PreparedStatement ps;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setInt(1, vc.getIdventa());
            ps.setInt(2, vc.getNumero());
            ps.setDouble(3, vc.getMonto());
            ps.setDate(4, (Date) vc.getFechavencimiento());
            int filas = ps.executeUpdate();
            if (filas > 0) {
                con.close();
                return true;
            } else {
                con.close();
                return false;
            }
        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "HA OCURRIDO UN ERROR AL INSERTAR LOS DATOS DE LA CUOTA \n" + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
}
