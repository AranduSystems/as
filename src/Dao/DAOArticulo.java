package Dao;

import Controladores.Database;
import Controladores.OperacionesArticulo;
import Modelos.Articulo;
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
public class DAOArticulo implements OperacionesArticulo {

    //CONEXION A LAS CLASE DE MODELOS Y CONTROLADORES
    Database db = new Database();
    Articulo a = new Articulo();

    @Override
    public boolean agregar(Object obj) {
        a = (Articulo) obj;
        String sql = "INSERT INTO articulo VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        Connection con;
        PreparedStatement ps;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setInt(1, a.getIdarticulo());
            ps.setString(2, a.getDescripcion());
            ps.setString(3, a.getReferencia());
            ps.setString(4, a.getCodigoalfanumerico());
            ps.setString(5, a.getCodigobarra());
            ps.setString(6, a.getEstado());
            ps.setString(7, a.getObservacion());
            ps.setInt(8, a.getIdmarca());
            ps.setInt(9, a.getIdlinea());
            ps.setInt(10, a.getIdseccion());
            ps.setInt(11, a.getIdtipo());
            ps.setInt(12, a.getIdunidad());
            ps.setInt(13, a.getIdimpuesto());
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
        a = (Articulo) obj;
        String sql = "UPDATE articulo\n"
                + "	SET\n"
                + "		descripcion=?,\n"
                + "		referencia=?,\n"
                + "		codigoalfanumerico=?,\n"
                + "		codigobarra=?,\n"
                + "		estado=?,\n"
                + "		observacion=?,\n"
                + "		idmarca=?,\n"
                + "		idlinea=?,\n"
                + "		idseccion=?,\n"
                + "		idtipo=?,\n"
                + "		idunidad=?,\n"
                + "		idimpuesto=?\n"
                + "	WHERE idarticulo=?;";
        Connection con;
        PreparedStatement ps;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setString(1, a.getDescripcion());
            ps.setString(2, a.getReferencia());
            ps.setString(3, a.getCodigoalfanumerico());
            ps.setString(4, a.getCodigobarra());
            ps.setString(5, a.getEstado());
            ps.setString(6, a.getObservacion());
            ps.setInt(7, a.getIdmarca());
            ps.setInt(8, a.getIdlinea());
            ps.setInt(9, a.getIdseccion());
            ps.setInt(10, a.getIdtipo());
            ps.setInt(11, a.getIdunidad());
            ps.setInt(12, a.getIdimpuesto());
            ps.setInt(13, a.getIdarticulo());

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
        a = (Articulo) obj;
        String sql = "DELETE FROM articulo WHERE idarticulo = ?;";
        Connection con;
        PreparedStatement ps;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setInt(1, a.getIdarticulo());
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
        String sql = "select idarticulo + 1 as proximo_cod_libre\n"
                + "  from (select 0 as idarticulo\n"
                + "         union all\n"
                + "        select idarticulo\n"
                + "          from articulo) t1\n"
                + " where not exists (select null\n"
                + "                     from articulo t2\n"
                + "                    where t2.idarticulo = t1.idarticulo + 1)\n"
                + " order by idarticulo\n"
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
    public ArrayList<Object[]> consultar(String criterio, String filtro) {
        String sql = "SELECT \n"
                + "	idarticulo, \n"
                + "	descripcion, \n"
                + "	referencia, \n"
                + "	codigoalfanumerico, \n"
                + "	codigobarra, \n"
                + "	IF(estado = 'A', 'ACTIVO', 'INACTIVO') AS estado\n"
                + "FROM articulo\n"
                + "WHERE " + filtro + " LIKE ?\n"
                + "ORDER BY descripcion;";
        Connection con;
        PreparedStatement ps;
        ResultSet rs;
        ArrayList<Object[]> datos = new ArrayList<>();
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setString(1, "%" + criterio + "%");
//            System.out.println(ps);
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
        a = (Articulo) obj;
        String sql = "SELECT * FROM articulo WHERE idarticulo = ?;";
        Connection con;
        PreparedStatement ps;
        ResultSet rs;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setInt(1, a.getIdarticulo());
            rs = ps.executeQuery();
            if (rs.next()) {
                a.setIdarticulo(rs.getInt(1));
                a.setDescripcion(rs.getString(2));
                a.setReferencia(rs.getString(3));
                a.setCodigoalfanumerico(rs.getString(4));
                a.setCodigobarra(rs.getString(5));
                a.setEstado(rs.getString(6));
                a.setObservacion(rs.getString(7));
                a.setIdmarca(rs.getInt(8));
                a.setIdlinea(rs.getInt(9));
                a.setIdseccion(rs.getInt(10));
                a.setIdtipo(rs.getInt(11));
                a.setIdunidad(rs.getInt(12));
                a.setIdimpuesto(rs.getInt(13));
                con.close();
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "NO EXISTE ARTÍCULO CON EL CÓDIGO INGRESADO...", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
                con.close();
                return false;
            }
        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "HA OCURRIDO UN ERROR AL OBTENER EL REGISTRO SELECCIONADO \n" + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    @Override
    public boolean verificarCodigoAlfanumerico(String codigo, String idarticulo) {
        String sql = "SELECT COUNT(*), idarticulo, descripcion CONTADOR FROM articulo WHERE codigoalfanumerico LIKE ? and idarticulo not in (?);";
        Connection con;
        PreparedStatement ps;
        ResultSet rs;
        boolean valor = false;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setString(1, codigo);
            ps.setString(2, idarticulo);
            rs = ps.executeQuery();
            if (rs.next()) {
                if (rs.getInt(1) >= 1) {
                    JOptionPane.showMessageDialog(null, "EL CÓDIGO ALFANUMÉRICO PERTENECE AL ARTÍCULO: " + rs.getString(2) + "-" + rs.getString(3));
                    valor = true;
                } else {
                    valor = false;
                }
            }
            con.close();
        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "HA OCURRIDO UN ERROR AL VERIFICAR EL CÓDIGO ALFANUMÉRICO \n" + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return valor;
    }

    @Override
    public boolean verificarCodigoBarra(String codigo, String idarticulo) {
        String sql = "SELECT COUNT(*), idarticulo, descripcion CONTADOR FROM articulo WHERE codigobarra LIKE ? and idarticulo not in (?);";
        Connection con;
        PreparedStatement ps;
        ResultSet rs;
        boolean valor = false;
        try {
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setString(1, codigo);
            ps.setString(2, idarticulo);
            rs = ps.executeQuery();
            if (rs.next()) {
                if (rs.getInt(1) >= 1) {
                    JOptionPane.showMessageDialog(null, "EL CÓDIGO DE BARRA PERTENECE AL ARTÍCULO: " + rs.getString(2) + "-" + rs.getString(3));
                    valor = true;
                } else {
                    valor = false;
                }
            }
            con.close();
        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "HA OCURRIDO UN ERROR AL VERIFICAR EL CÓDIGO DE BARRA \n" + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return valor;
    }

    @Override
    public boolean busquedaArticulo(Object obj) {
        a = (Articulo) obj;
        String sql = "";
        Connection con;
        PreparedStatement ps;
        ResultSet rs;
        boolean resultado = false;
        try {
            sql = "SELECT * FROM articulo WHERE codigobarra = ?;";
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
            ps = con.prepareStatement(sql);
            ps.setString(1, a.getCodigobarra());
            rs = ps.executeQuery();
            if (rs.next()) {
                a.setIdarticulo(rs.getInt(1));
                a.setDescripcion(rs.getString(2));
                a.setReferencia(rs.getString(3));
                a.setCodigoalfanumerico(rs.getString(4));
                a.setCodigobarra(rs.getString(5));
                a.setEstado(rs.getString(6));
                a.setObservacion(rs.getString(7));
                a.setIdmarca(rs.getInt(8));
                a.setIdlinea(rs.getInt(9));
                a.setIdseccion(rs.getInt(10));
                a.setIdtipo(rs.getInt(11));
                a.setIdunidad(rs.getInt(12));
                a.setIdimpuesto(rs.getInt(13));
                con.close();
                resultado = true;
            } else {
                con.close();
                resultado = false;
            }
            if (resultado == false) {
                sql = "SELECT * FROM articulo WHERE codigoalfanumerico = ?;";
                Class.forName(db.getDriver());
                con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
                ps = con.prepareStatement(sql);
                ps.setString(1, a.getCodigoalfanumerico());
                rs = ps.executeQuery();
                if (rs.next()) {
                    a.setIdarticulo(rs.getInt(1));
                    a.setDescripcion(rs.getString(2));
                    a.setReferencia(rs.getString(3));
                    a.setCodigoalfanumerico(rs.getString(4));
                    a.setCodigobarra(rs.getString(5));
                    a.setEstado(rs.getString(6));
                    a.setObservacion(rs.getString(7));
                    a.setIdmarca(rs.getInt(8));
                    a.setIdlinea(rs.getInt(9));
                    a.setIdseccion(rs.getInt(10));
                    a.setIdtipo(rs.getInt(11));
                    a.setIdunidad(rs.getInt(12));
                    a.setIdimpuesto(rs.getInt(13));
                    con.close();
                    resultado = true;
                } else {
                    con.close();
                    resultado = false;
                }
            }
            if (resultado == false) {
                sql = "SELECT * FROM articulo WHERE idarticulo = ?;";
                Class.forName(db.getDriver());
                con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());
                ps = con.prepareStatement(sql);
                ps.setInt(1, a.getIdarticulo());
                rs = ps.executeQuery();
                if (rs.next()) {
                    a.setIdarticulo(rs.getInt(1));
                    a.setDescripcion(rs.getString(2));
                    a.setReferencia(rs.getString(3));
                    a.setCodigoalfanumerico(rs.getString(4));
                    a.setCodigobarra(rs.getString(5));
                    a.setEstado(rs.getString(6));
                    a.setObservacion(rs.getString(7));
                    a.setIdmarca(rs.getInt(8));
                    a.setIdlinea(rs.getInt(9));
                    a.setIdseccion(rs.getInt(10));
                    a.setIdtipo(rs.getInt(11));
                    a.setIdunidad(rs.getInt(12));
                    a.setIdimpuesto(rs.getInt(13));
                    con.close();
                    resultado = true;
                } else {
                    con.close();
                    resultado = false;
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "HA OCURRIDO UN ERROR AL OBTENER EL REGISTRO SELECCIONADO \n" + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return resultado;
    }
}
