package Controladores;

import java.util.ArrayList;

/**
 *
 * @author armando
 */
public interface OperacionesTipoTarjeta {
    public boolean agregar(Object obj);
    public boolean modificar(Object obj);
    public boolean eliminar(Object obj);
    public int nuevoID();
    public ArrayList<Object[]> consultar(String criterio);
}
