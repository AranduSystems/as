package Modelos;

/**
 *
 * @author armando
 */
public class Impresora {
   private int idimpresora;
   private String descripcion;
   private String ultimo_numero_factura;
   private String ultimo_numero_recibo;
   private String ultimo_numero_nota_credito;
   private String ultimo_numero_nota_debito;

    public Impresora() {
    }

    public int getIdimpresora() {
        return idimpresora;
    }

    public void setIdimpresora(int idimpresora) {
        this.idimpresora = idimpresora;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUltimo_numero_factura() {
        return ultimo_numero_factura;
    }

    public void setUltimo_numero_factura(String ultimo_numero_factura) {
        this.ultimo_numero_factura = ultimo_numero_factura;
    }

    public String getUltimo_numero_recibo() {
        return ultimo_numero_recibo;
    }

    public void setUltimo_numero_recibo(String ultimo_numero_recibo) {
        this.ultimo_numero_recibo = ultimo_numero_recibo;
    }

    public String getUltimo_numero_nota_credito() {
        return ultimo_numero_nota_credito;
    }

    public void setUltimo_numero_nota_credito(String ultimo_numero_nota_credito) {
        this.ultimo_numero_nota_credito = ultimo_numero_nota_credito;
    }

    public String getUltimo_numero_nota_debito() {
        return ultimo_numero_nota_debito;
    }

    public void setUltimo_numero_nota_debito(String ultimo_numero_nota_debito) {
        this.ultimo_numero_nota_debito = ultimo_numero_nota_debito;
    }
   
}
