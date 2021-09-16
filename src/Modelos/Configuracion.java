package Modelos;

/**
 *
 * @author armando
 */
public class Configuracion {

    private int idconfiguracion;
    private int idsucursal;
    private int fac_con_rec;
    private int fac_cre_rec;
    private int rec_pag_rec;
    private int fac_con_emi;
    private int fac_cre_emi;
    private String permitir_venta_negativa;
    private int articulo_tipo_servicio;

    public Configuracion() {
    }

    public int getIdconfiguracion() {
        return idconfiguracion;
    }

    public void setIdconfiguracion(int idconfiguracion) {
        this.idconfiguracion = idconfiguracion;
    }

    public int getIdsucursal() {
        return idsucursal;
    }

    public void setIdsucursal(int idsucursal) {
        this.idsucursal = idsucursal;
    }

    public int getFac_con_rec() {
        return fac_con_rec;
    }

    public void setFac_con_rec(int fac_con_rec) {
        this.fac_con_rec = fac_con_rec;
    }

    public int getFac_cre_rec() {
        return fac_cre_rec;
    }

    public void setFac_cre_rec(int fac_cre_rec) {
        this.fac_cre_rec = fac_cre_rec;
    }

    public int getRec_pag_rec() {
        return rec_pag_rec;
    }

    public void setRec_pag_rec(int rec_pag_rec) {
        this.rec_pag_rec = rec_pag_rec;
    }

    public int getFac_con_emi() {
        return fac_con_emi;
    }

    public void setFac_con_emi(int fac_con_emi) {
        this.fac_con_emi = fac_con_emi;
    }

    public int getFac_cre_emi() {
        return fac_cre_emi;
    }

    public void setFac_cre_emi(int fac_cre_emi) {
        this.fac_cre_emi = fac_cre_emi;
    }

    public String getPermitir_venta_negativa() {
        return permitir_venta_negativa;
    }

    public void setPermitir_venta_negativa(String permitir_venta_negativa) {
        this.permitir_venta_negativa = permitir_venta_negativa;
    }

    public int getArticulo_tipo_servicio() {
        return articulo_tipo_servicio;
    }

    public void setArticulo_tipo_servicio(int articulo_tipo_servicio) {
        this.articulo_tipo_servicio = articulo_tipo_servicio;
    }
    
}
