package Controladores;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author armando
 */
public interface OperacionesCotizacion {
    public boolean agregar(Object obj);
    public boolean modificar(Object obj);
    public boolean eliminar(Object obj);
    public ArrayList<Object[]> consultar(String criterio);
    public boolean consultarDatos(Object obj);
    public Date parseFecha(String cadena);
    public boolean verificarCotizacion(String fecha, int idmoneda);
}
