package Controladores;

import java.util.ArrayList;

/**
 *
 * @author armando
 */
public interface OperacionesVendedor {
    public boolean agregar(Object obj);
    public boolean modificar(Object obj);
    public boolean eliminar(Object obj);
    public int nuevoID();
    public ArrayList<Object[]> consultar(String criterio);
    public ArrayList<Object[]> consultarVendedorEmpresaSucursal(String criterio, int idempresa, int idsucursal);
    public boolean consultarDatos(Object obj);
    public boolean consultarDatosVendedorEmpresaSucursal(Object obj);
}
