package Controladores;

import java.util.ArrayList;

/**
 *
 * @author armando
 */
public interface OperacionesCompraPagoCuotaAnulado {
    public boolean agregar(Object obj);
    public boolean eliminar(Object obj);
    public int nuevoID();
    public ArrayList<Object[]> consultar(String criterio, int idproveedor);
    public boolean consultarDatos(Object obj);
}
