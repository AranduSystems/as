package Dao;

import Controladores.Database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import Controladores.OperacionesCompraDetalle;
import Modelos.CompraDetalle;
/**
 *
 * @author armando
 */
public class DAOCompraDetalle implements OperacionesCompraDetalle {

    //CONEXION A LAS CLASE DE MODELOS Y CONTROLADORES
    Database db = new Database();
    CompraDetalle cd = new CompraDetalle();

    @Override
    public boolean agregar(Object obj) {
        cd = (CompraDetalle) obj;
        String sql = "INSERT INTO compra_detalle\n"
                + "(idcompra, idarticulo, costo, cantidad, numeroitem, iva, porcentajeiva)\n"
                + "VALUES (?, ?, ?, ?, ?, ?, ?);";
        Connection con;
        PreparedStatement ps;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setInt(1, cd.getIdcompra());
            ps.setInt(2, cd.getIdarticulo());
            ps.setDouble(3, cd.getCosto());
            ps.setDouble(4, cd.getCantidad());
            ps.setInt(5, cd.getNumero_item());
            ps.setDouble(6, cd.getIva());
            ps.setDouble(7, cd.getPorcentaje_iva());
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
