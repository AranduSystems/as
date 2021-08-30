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
   
}
