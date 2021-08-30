package Controladores;

import java.util.ArrayList;

/**
 *
 * @author armando
 */
public interface OperacionesCompraAnulado {
    public boolean agregar(Object obj);
    public boolean eliminar(Object obj);
    public int nuevoID();
    public ArrayList<Object[]> consultar(String criterio, int idtipomovimiento);
    public boolean consultarDatos(Object obj);
    public boolean verificarPagos(int idcompra);
}
