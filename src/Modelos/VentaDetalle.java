package Modelos;

import java.util.Date;

/**
 *
 * @author armando
 */
public class VentaDetalle {
    private int idventa;
    private int idarticulo;
    private double precio;
    private double cantidad;
    private int numero_item;
    private double iva;
    private double porcentaje_iva;
    private String referencia;

    public VentaDetalle() {
    }

    public int getIdventa() {
        return idventa;
    }

    public void setIdventa(int idventa) {
        this.idventa = idventa;
    }

    public int getIdarticulo() {
        return idarticulo;
    }

    public void setIdarticulo(int idarticulo) {
        this.idarticulo = idarticulo;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
    
    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public int getNumero_item() {
        return numero_item;
    }

    public void setNumero_item(int numero_item) {
        this.numero_item = numero_item;
    }

    public double getIva() {
        return iva;
    }

    public void setIva(double iva) {
        this.iva = iva;
    }

    public double getPorcentaje_iva() {
        return porcentaje_iva;
    }

    public void setPorcentaje_iva(double porcentaje_iva) {
        this.porcentaje_iva = porcentaje_iva;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }
    
    
}
