package Modelos;

import java.util.Date;

/**
 *
 * @author armando
 */
public class Timbrado {
    private int idtimbrado;
    private int establecimiento;
    private int puntoemision;
    private int timbrado;
    private int numeroinicial;
    private int numerofinal;
    private Date fechainicial;
    private Date fechafinal;
    private int idcaja;
    private int idtipocomprobante;

    public Timbrado() {
    }

    public int getIdtimbrado() {
        return idtimbrado;
    }

    public void setIdtimbrado(int idtimbrado) {
        this.idtimbrado = idtimbrado;
    }

    public int getEstablecimiento() {
        return establecimiento;
    }

    public void setEstablecimiento(int establecimiento) {
        this.establecimiento = establecimiento;
    }

    public int getPuntoemision() {
        return puntoemision;
    }

    public void setPuntoemision(int puntoemision) {
        this.puntoemision = puntoemision;
    }

    public int getTimbrado() {
        return timbrado;
    }

    public void setTimbrado(int timbrado) {
        this.timbrado = timbrado;
    }

    public int getNumeroinicial() {
        return numeroinicial;
    }

    public void setNumeroinicial(int numeroinicial) {
        this.numeroinicial = numeroinicial;
    }

    public int getNumerofinal() {
        return numerofinal;
    }

    public void setNumerofinal(int numerofinal) {
        this.numerofinal = numerofinal;
    }

    public Date getFechainicial() {
        return fechainicial;
    }

    public void setFechainicial(Date fechainicial) {
        this.fechainicial = fechainicial;
    }

    public Date getFechafinal() {
        return fechafinal;
    }

    public void setFechafinal(Date fechafinal) {
        this.fechafinal = fechafinal;
    }

    public int getIdcaja() {
        return idcaja;
    }

    public void setIdcaja(int idcaja) {
        this.idcaja = idcaja;
    }

    public int getIdtipocomprobante() {
        return idtipocomprobante;
    }

    public void setIdtipocomprobante(int idtipocomprobante) {
        this.idtipocomprobante = idtipocomprobante;
    }
    
}
