package Controladores;

import java.util.ArrayList;

/**
 *
 * @author armando
 */
public interface OperacionesCuenta {
    public boolean agregar(Object obj);
    public boolean modificar(Object obj);
    public boolean eliminar(Object obj);
    public int nuevoID();
    public ArrayList<Object[]> consultarDescripcion(String criterio);
    public ArrayList<Object[]> consultar(String criterio, int idmoneda);
    public boolean consultarDatos(Object obj);
}
