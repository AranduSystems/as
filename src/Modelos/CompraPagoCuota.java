package Modelos;

import java.util.Date;

/**
 *
 * @author armando
 */
public class CompraPagoCuota {
    private int idpago;
    private int idcompra;
    private int numero;
    private Date fechapago;
    private double monto;
    private int idcuenta;
    private int  idusuario;
    private String  numerocomprobante;
    private int idtipomovimiento;

    public CompraPagoCuota() {
    }

    public int getIdpago() {
        return idpago;
    }

    public void setIdpago(int idpago) {
        this.idpago = idpago;
    }

    public int getIdcompra() {
        return idcompra;
    }

    public void setIdcompra(int idcompra) {
        this.idcompra = idcompra;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public Date getFechapago() {
        return fechapago;
    }

    public void setFechapago(Date fechapago) {
        this.fechapago = fechapago;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public int getIdcuenta() {
        return idcuenta;
    }

    public void setIdcuenta(int idcuenta) {
        this.idcuenta = idcuenta;
    }

    public int getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(int idusuario) {
        this.idusuario = idusuario;
    }

    public String getNumerocomprobante() {
        return numerocomprobante;
    }

    public void setNumerocomprobante(String numerocomprobante) {
        this.numerocomprobante = numerocomprobante;
    }

    public int getIdtipomovimiento() {
        return idtipomovimiento;
    }

    public void setIdtipomovimiento(int idtipomovimiento) {
        this.idtipomovimiento = idtipomovimiento;
    }
}
