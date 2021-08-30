package Controladores;

import java.util.ArrayList;

/**
 *
 * @author armando
 */
public interface OperacionesTipoMovimiento {
    public boolean agregar(Object obj);
    public boolean modificar(Object obj);
    public boolean eliminar(Object obj);
    public int nuevoID();
    public ArrayList<Object[]> consultar(String criterio);
    public boolean consultarDatos(Object obj);
    public ArrayList<Object[]> consultarFactura(String criterio, String tipo);
    public boolean consultarDatosFactura(Object obj);
}
