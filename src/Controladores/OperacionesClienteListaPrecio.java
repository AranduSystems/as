package Controladores;

import java.util.ArrayList;

/**
 *
 * @author armando
 */
public interface OperacionesClienteListaPrecio {
    public boolean agregar(Object obj);
    public boolean modificar(Object obj);
    public boolean eliminar(Object obj);
    public ArrayList<Object[]> consultar(int idcliente);
    public boolean consultarDatos(Object obj);
}
