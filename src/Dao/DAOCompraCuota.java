package Dao;

import Controladores.Database;
import Controladores.OperacionesCompraCuota;
import Modelos.CompraCuota;
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
public class DAOCompraCuota implements OperacionesCompraCuota {

    //CONEXION A LAS CLASE DE MODELOS Y CONTROLADORES
    Database db = new Database();
    CompraCuota cc = new CompraCuota();

    @Override
    public boolean agregar(Object obj) {
        cc = (CompraCuota) obj;
        String sql = "INSERT INTO compra_cuota\n"
                + "(idcompra, numero, monto, fechavencimiento)\n"
                + "VALUES (?, ?, ?, ?);";
        Connection con;
        PreparedStatement ps;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setInt(1, cc.getIdcompra());
            ps.setInt(2, cc.getNumero());
            ps.setDouble(3, cc.getMonto());
            ps.setDate(4, (Date) cc.getFechavencimiento());
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
