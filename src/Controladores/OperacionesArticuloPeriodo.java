package Controladores;

import java.util.Date;


/**
 *
 * @author armando
 */
public interface OperacionesArticuloPeriodo {
    public boolean consultarDatos(Object obj);
    public boolean obtenerUltimoCosto(Object obj);
    public int obtenerPeriodo(String fecha);
}
