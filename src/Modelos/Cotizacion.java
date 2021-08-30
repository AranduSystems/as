package Modelos;

import java.util.Date;

/**
 *
 * @author armando
 */
public class Cotizacion {

    private int idmoneda;
    private Date fecha;
    private double tasacompra;
    private double tasaventa;

    public Cotizacion() {
    }

    public int getIdmoneda() {
        return idmoneda;
    }

    public void setIdmoneda(int idmoneda) {
        this.idmoneda = idmoneda;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }    

    public double getTasacompra() {
        return tasacompra;
    }

    public void setTasacompra(double tasacompra) {
        this.tasacompra = tasacompra;
    }

    public double getTasaventa() {
        return tasaventa;
    }

    public void setTasaventa(double tasaventa) {
        this.tasaventa = tasaventa;
    }
    
}
