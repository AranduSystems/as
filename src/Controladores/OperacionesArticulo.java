package Controladores;

import java.util.ArrayList;

/**
 *
 * @author armando
 */
public interface OperacionesArticulo {
    public boolean agregar(Object obj);
    public boolean modificar(Object obj);
    public boolean eliminar(Object obj);
    public int nuevoID();
    public ArrayList<Object[]> consultar(String criterio, String filtro);
    public boolean consultarDatos(Object obj);
    public boolean verificarCodigoAlfanumerico(String codigo, String id);
    public boolean verificarCodigoBarra(String codigo, String id);
    public boolean busquedaArticulo(Object obj);
}
