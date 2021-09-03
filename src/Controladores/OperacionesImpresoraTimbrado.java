package Controladores;

import java.sql.Date;
import java.util.ArrayList;

/**
 *
 * @author armando
 */
public interface OperacionesImpresoraTimbrado {
    public boolean agregar(Object obj);
    public boolean modificar(Object obj);
    public boolean eliminar(Object obj);
    public int nuevoID();
    public ArrayList<Object[]> consultar(int idimpresora);
    public boolean consultarDatos(Object obj);
    public boolean consultarDatosTimbrado(Object obj, Date fecha);
}
