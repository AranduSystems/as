package Modelos;

import java.sql.Timestamp;
import java.util.Date;

/**
 *
 * @author armando
 */
public class CompraPagoCuotaAnulado {
    private int idpagoanulado;
    private Timestamp fechahoranulado;
    private String observacion;
    private int idmotivo;
    private int idusuario;
    private int idpago;
    private Date fechapago;
    private double monto;
    private String numerocomprobante;
    private String numerorecibo;
    
    public CompraPagoCuotaAnulado() {
    }

    public int getIdpagoanulado() {
        return idpagoanulado;
    }

    public void setIdpagoanulado(int idpagoanulado) {
        this.idpagoanulado = idpagoanulado;
    }

    public Timestamp getFechahoranulado() {
        return fechahoranulado;
    }

    public void setFechahoranulado(Timestamp fechahoranulado) {
        this.fechahoranulado = fechahoranulado;
    }
    
    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public int getIdmotivo() {
        return idmotivo;
    }

    public void setIdmotivo(int idmotivo) {
        this.idmotivo = idmotivo;
    }

    public int getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(int idusuario) {
        this.idusuario = idusuario;
    }

    public int getIdpago() {
        return idpago;
    }

    public void setIdpago(int idpago) {
        this.idpago = idpago;
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

    public String getNumerocomprobante() {
        return numerocomprobante;
    }

    public void setNumerocomprobante(String numerocomprobante) {
        this.numerocomprobante = numerocomprobante;
    }

    public String getNumerorecibo() {
        return numerorecibo;
    }

    public void setNumerorecibo(String numerorecibo) {
        this.numerorecibo = numerorecibo;
    }
}
