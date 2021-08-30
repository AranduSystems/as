package Modelos;

import java.util.Date;

/**
 *
 * @author armando
 */
public class ImpresoraTimbrado {

    private int idimpresora;
    private int idtimbrado;
    private int idtipocomprobante;
    private int establecimiento;
    private int puntoemision;
    private int numerotimbrado;
    private int numeroinicial;
    private int numerofinal;
    private Date fechainicial;
    private Date fechafinal;

    public ImpresoraTimbrado() {
    }

    public int getIdimpresora() {
        return idimpresora;
    }

    public void setIdimpresora(int idimpresora) {
        this.idimpresora = idimpresora;
    }

    public int getIdtimbrado() {
        return idtimbrado;
    }

    public void setIdtimbrado(int idtimbrado) {
        this.idtimbrado = idtimbrado;
    }

    public int getIdtipocomprobante() {
        return idtipocomprobante;
    }

    public void setIdtipocomprobante(int idtipocomprobante) {
        this.idtipocomprobante = idtipocomprobante;
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

    public int getNumerotimbrado() {
        return numerotimbrado;
    }

    public void setNumerotimbrado(int numerotimbrado) {
        this.numerotimbrado = numerotimbrado;
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

}
