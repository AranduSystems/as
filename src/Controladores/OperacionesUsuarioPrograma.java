package Controladores;

import java.util.ArrayList;
import javax.swing.JTable;

/**
 *
 * @author armando
 */
public interface OperacionesUsuarioPrograma {
    public boolean agregar(Object obj);
    public boolean eliminar(Object obj);
    public ArrayList<Object[]> consultar(int id);
    public boolean consultarDatos(Object obj);
    public void addCheckBox(int column, JTable table);
}
