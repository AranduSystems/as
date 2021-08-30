package Dao;

import Controladores.Database;
import Controladores.OperacionesConfiguracion;
import Modelos.Configuracion;
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
public class DAOConfiguracion implements OperacionesConfiguracion {

    //CONEXION A LAS CLASE DE MODELOS Y CONTROLADORES
    Database db = new Database();
    Configuracion c = new Configuracion();

    @Override
    public boolean agregar(Object obj) {
        c = (Configuracion) obj;
        String sql = "INSERT INTO configuracion\n"
                + "(idconfiguracion, idsucursal, fac_con_rec, fac_cre_rec, rec_pag_rec)\n"
                + "VALUES (?, ?, ?, ?, ?);";
        Connection con;
        PreparedStatement ps;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setInt(1, c.getIdconfiguracion());
            ps.setInt(2, c.getIdsucursal());
            ps.setInt(3, c.getFac_con_rec());
            ps.setInt(4, c.getFac_cre_rec());
            ps.setInt(5, c.getRec_pag_rec());
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
        c = (Configuracion) obj;
        String sql = "UPDATE configuracion\n"
                + "	SET\n"
                + "		idsucursal=?,\n"
                + "		fac_con_rec=?,\n"
                + "		fac_cre_rec=?,\n"
                + "		rec_pag_rec=?\n"
                + "	WHERE idconfiguracion=?;";
        Connection con;
        PreparedStatement ps;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setInt(1, c.getIdsucursal());
            ps.setInt(2, c.getFac_con_rec());
            ps.setInt(3, c.getFac_cre_rec());
            ps.setInt(4, c.getRec_pag_rec());
            ps.setInt(5, c.getIdconfiguracion());
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
        c = (Configuracion) obj;
        String sql = "DELETE FROM configuracion WHERE idconfiguracion=?;";
        Connection con;
        PreparedStatement ps;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setInt(1, c.getIdconfiguracion());
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
        String sql = "select idconfiguracion + 1 as proximo_cod_libre\n"
                + "  from (select 0 as idconfiguracion\n"
                + "         union all\n"
                + "        select idconfiguracion\n"
                + "          from configuracion) t1\n"
                + " where not exists (select null\n"
                + "                     from configuracion t2\n"
                + "                    where t2.idconfiguracion = t1.idconfiguracion + 1)\n"
                + " order by idconfiguracion\n"
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
        String sql = "SELECT\n"
                + "C.idconfiguracion,\n"
                + "C.idsucursal,\n"
                + "S.descripcion,\n"
                + "C.fac_con_rec,\n"
                + "TMCCONR.abreviacion,\n"
                + "C.fac_cre_rec,\n"
                + "TMCCRER.abreviacion\n"
                + "FROM configuracion AS C\n"
                + "INNER JOIN sucursal AS S ON S.idsucursal = C.idsucursal\n"
                + "INNER JOIN tipo_movimiento AS TMCCONR ON TMCCONR.idtipomovimiento = C.fac_con_rec\n"
                + "INNER JOIN tipo_movimiento AS TMCCRER ON TMCCRER.idtipomovimiento = C.fac_cre_rec\n"
                + "WHERE CONCAT(S.descripcion, TMCCONR.descripcion, TMCCRER.descripcion) LIKE ?\n"
                + "ORDER BY 1 ASC;";
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
                Object[] fila = new Object[7];
                fila[0] = rs.getInt(1);
                fila[1] = rs.getInt(2);
                fila[2] = rs.getString(3);
                fila[3] = rs.getInt(4);
                fila[4] = rs.getString(5);
                fila[5] = rs.getInt(6);
                fila[6] = rs.getString(7);
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
        c = (Configuracion) obj;
        String sql = "SELECT * FROM configuracion WHERE idsucursal = ?;";
        Connection con;
        PreparedStatement ps;
        ResultSet rs;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setInt(1, c.getIdsucursal());
            rs = ps.executeQuery();
            if (rs.next()) {
                c.setIdconfiguracion(rs.getInt(1));
                c.setIdsucursal(rs.getInt(2));
                c.setFac_con_rec(rs.getInt(3));
                c.setFac_cre_rec(rs.getInt(4));
                c.setRec_pag_rec(rs.getInt(5));
                con.close();
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "NO EXISTE CONFIGURACION CON EL CÓDIGO INGRESADO...", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
                con.close();
                return false;
            }
        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "HA OCURRIDO UN ERROR AL OBTENER EL REGISTRO SELECCIONADO \n" + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    @Override
    public int verificarConfiguracion(int idsucursal) {
        String sql = "SELECT\n"
                + "COUNT(*)\n"
                + "FROM configuracion AS C\n"
                + "WHERE C.idsucursal = ?;";
        Connection con;
        PreparedStatement ps;
        ResultSet rs;
        int existe = 0;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setInt(1, idsucursal);
            rs = ps.executeQuery();
            if (rs.next()) {
                existe = rs.getInt(1);
            }
            con.close();
        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "HA OCURRIDO UN ERROR AL VERIFICAR CONFIGURACIÓNV \n" + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return existe;
    }

}
