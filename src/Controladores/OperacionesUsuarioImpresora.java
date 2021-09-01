package Controladores;

import java.util.ArrayList;

/**
 *
 * @author armando
 */
public interface OperacionesUsuarioImpresora {
    public boolean agregar(Object obj);
    public boolean eliminar(Object obj);
    public ArrayList<Object[]> consultar(String criterio);
    public boolean consultarDatos(Object obj);
    public boolean consultarDatosImpresora(Object obj);
}
