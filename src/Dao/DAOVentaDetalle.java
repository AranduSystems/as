package Dao;

import Controladores.Database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import Controladores.OperacionesVentaDetalle;
import Modelos.VentaDetalle;
/**
 *
 * @author armando
 */
public class DAOVentaDetalle implements OperacionesVentaDetalle {

    //CONEXION A LAS CLASE DE MODELOS Y CONTROLADORES
    Database db = new Database();
    VentaDetalle vd = new VentaDetalle();

    @Override
    public boolean agregar(Object obj) {
        vd = (VentaDetalle) obj;
        String sql = "INSERT INTO venta_detalle\n"
                + "(idventa, idarticulo, precio, cantidad, numeroitem, iva, porcentajeiva, referencia)\n"
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
        Connection con;
        PreparedStatement ps;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setInt(1, vd.getIdventa());
            ps.setInt(2, vd.getIdarticulo());
            ps.setDouble(3, vd.getPrecio());
            ps.setDouble(4, vd.getCantidad());
            ps.setInt(5, vd.getNumero_item());
            ps.setDouble(6, vd.getIva());
            ps.setDouble(7, vd.getPorcentaje_iva());
            ps.setString(8, vd.getReferencia());
            int filas = ps.executeUpdate();
            if (filas > 0) {
                con.close();
                return true;
            } else {
                con.close();
                return false;
            }
        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "HA OCURRIDO UN ERROR AL INSERTAR LOS DATOS DEL DETALLE \n" + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
}
