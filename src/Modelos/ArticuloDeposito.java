package Modelos;

/**
 *
 * @author armando
 */
public class ArticuloDeposito {
    private int idarticulo;
    private int iddeposito;
    private double cantidad;

    public ArticuloDeposito() {
    }

    public int getIdarticulo() {
        return idarticulo;
    }

    public void setIdarticulo(int idarticulo) {
        this.idarticulo = idarticulo;
    }

    public int getIddeposito() {
        return iddeposito;
    }

    public void setIddeposito(int iddeposito) {
        this.iddeposito = iddeposito;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }
    
}
