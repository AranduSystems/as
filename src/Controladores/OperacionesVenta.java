package Controladores;

import java.util.ArrayList;

/**
 *
 * @author armando
 */
public interface OperacionesVenta {
    public boolean agregar(Object obj);
    public boolean modificar(Object obj);
    public boolean eliminar(Object obj);
    public int nuevoID();
    public boolean verificarExistenciaVenta(String numerodocumento, int numerotimbrado);
    public ArrayList<Object[]> consultar(String criterio, int idtipomovimiento);
    public boolean consultarDatos(Object obj);
}
