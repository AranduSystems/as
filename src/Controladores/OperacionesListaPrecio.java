package Controladores;

import java.util.ArrayList;

/**
 *
 * @author armando
 */
public interface OperacionesListaPrecio {
    public boolean agregar(Object obj);
    public boolean modificar(Object obj);
    public boolean eliminar(Object obj);
    public int nuevoID();
    public ArrayList<Object[]> consultar(String criterio, int idmoneda);
    public ArrayList<Object[]> consultarSinMoneda(String criterio);
    public boolean consultarDatos(Object obj);
    public boolean consultarDatosListaPrecioMoneda(Object obj);
}
