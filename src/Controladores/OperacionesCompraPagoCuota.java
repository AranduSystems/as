package Controladores;

import java.util.ArrayList;

/**
 *
 * @author armando
 */
public interface OperacionesCompraPagoCuota {
    public boolean agregar(Object obj);
    public boolean eliminar(Object obj);
    public int nuevoID();
    public boolean consultarDatos(Object obj);
    public ArrayList<Object[]> consultarCuotasCompra(int idproveedor, int idmoneda, String criterio);
    public ArrayList<Object[]> consultarPagosCuotas(int idproveedor, String criterio);
}
