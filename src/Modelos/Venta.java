package Modelos;

import java.util.Date;

/**
 *
 * @author armando
 */
public class Venta {
    private int idventa;
    private String numerodocumento;
    private int numerotimbrado;
    private Date fecha;
    private String observacion;
    private int idmoneda;
    private int iddeposito;
    private int idtipomovimiento;
    private int idcliente;
    private int idusuario;
    private double totalneto;
    private double totaliva;
    private int idcuenta;
    private int idempresa;
    private int idsucursal;
    private int idvendedor;

    public Venta() {
    }

    public int getIdventa() {
        return idventa;
    }

    public void setIdventa(int idventa) {
        this.idventa = idventa;
    }

    public String getNumerodocumento() {
        return numerodocumento;
    }

    public void setNumerodocumento(String numerodocumento) {
        this.numerodocumento = numerodocumento;
    }

    public int getNumerotimbrado() {
        return numerotimbrado;
    }

    public void setNumerotimbrado(int numerotimbrado) {
        this.numerotimbrado = numerotimbrado;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public int getIdmoneda() {
        return idmoneda;
    }

    public void setIdmoneda(int idmoneda) {
        this.idmoneda = idmoneda;
    }

    public int getIddeposito() {
        return iddeposito;
    }

    public void setIddeposito(int iddeposito) {
        this.iddeposito = iddeposito;
    }

    public int getIdtipomovimiento() {
        return idtipomovimiento;
    }

    public void setIdtipomovimiento(int idtipomovimiento) {
        this.idtipomovimiento = idtipomovimiento;
    }

    public int getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(int idcliente) {
        this.idcliente = idcliente;
    }
    
    public int getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(int idusuario) {
        this.idusuario = idusuario;
    }

    public double getTotalneto() {
        return totalneto;
    }

    public void setTotalneto(double totalneto) {
        this.totalneto = totalneto;
    }

    public double getTotaliva() {
        return totaliva;
    }

    public void setTotaliva(double totaliva) {
        this.totaliva = totaliva;
    }

    public int getIdcuenta() {
        return idcuenta;
    }

    public void setIdcuenta(int idcuenta) {
        this.idcuenta = idcuenta;
    }

    public int getIdempresa() {
        return idempresa;
    }

    public void setIdempresa(int idempresa) {
        this.idempresa = idempresa;
    }

    public int getIdsucursal() {
        return idsucursal;
    }

    public void setIdsucursal(int idsucursal) {
        this.idsucursal = idsucursal;
    }

    public int getIdvendedor() {
        return idvendedor;
    }

    public void setIdvendedor(int idvendedor) {
        this.idvendedor = idvendedor;
    }
    
}
