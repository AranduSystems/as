package Modelos;

import java.sql.Timestamp;
import java.util.Date;

/**
 *
 * @author armando
 */
public class CompraAnulado {
    
    private int idcompraanulado;
    private Timestamp fechahoranulado;
    private String observacionanulado;
    private int idmotivo;
    private int idusuarioanulado;
    private int idcompra;
    private String numerodocumento;
    private int numerotimbrado;
    private Date fecha;
    private String observacion;
    private int idmoneda;
    private int iddeposito;
    private int idtipomovimiento;
    private int idproveedor;
    private int idusuario;
    private double totalneto;
    private double totaliva;
    private int idcuenta;
    
    public CompraAnulado() {
    }

    public int getIdcompraanulado() {
        return idcompraanulado;
    }

    public void setIdcompraanulado(int idcompraanulado) {
        this.idcompraanulado = idcompraanulado;
    }

    public Timestamp getFechahoranulado() {
        return fechahoranulado;
    }

    public void setFechahoranulado(Timestamp fechahoranulado) {
        this.fechahoranulado = fechahoranulado;
    }

    public String getObservacionanulado() {
        return observacionanulado;
    }

    public void setObservacionanulado(String observacionanulado) {
        this.observacionanulado = observacionanulado;
    }

    public int getIdmotivo() {
        return idmotivo;
    }

    public void setIdmotivo(int idmotivo) {
        this.idmotivo = idmotivo;
    }

    public int getIdusuarioanulado() {
        return idusuarioanulado;
    }

    public void setIdusuarioanulado(int idusuarioanulado) {
        this.idusuarioanulado = idusuarioanulado;
    }

    public int getIdcompra() {
        return idcompra;
    }

    public void setIdcompra(int idcompra) {
        this.idcompra = idcompra;
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

    public int getIdproveedor() {
        return idproveedor;
    }

    public void setIdproveedor(int idproveedor) {
        this.idproveedor = idproveedor;
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
    
    
}
