package Controladores;

import java.util.ArrayList;

/**
 *
 * @author armando
 */
public interface OperacionesCompra {
    public boolean agregar(Object obj);
    public boolean modificar(Object obj);
    public boolean eliminar(Object obj);
    public int nuevoID();
    public boolean verificarExistenciaCompra(String numerodocumento, int numerotimbrado);
    public ArrayList<Object[]> consultar(String criterio, int idtipomovimiento);
    public boolean consultarDatos(Object obj);
}
