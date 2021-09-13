package Controladores;
/**
 *
 * @author armando
 */
public interface OperacionesArticuloDeposito {
    public boolean verificarExistenciaDeposito(int idarticulo, int iddeposito);
    public boolean consultarDatos(Object obj);
}
