package Modelos;

/**
 *
 * @author armando
 */
public class Impuesto {
    private int idimpuesto;
    private String descripcion;
    private double porcentaje;

    public Impuesto() {
    }

    public int getIdimpuesto() {
        return idimpuesto;
    }

    public void setIdimpuesto(int idimpuesto) {
        this.idimpuesto = idimpuesto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(double porcentaje) {
        this.porcentaje = porcentaje;
    }
    
    
}
