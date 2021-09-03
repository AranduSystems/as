package Modelos;

/**
 *
 * @author armando
 */
public class Vendedor {
    private int idvendedor;
    private String nombre;
    private String apellido;
    private String estado;
    private double porcentajecomision;
    private int idempresa;
    private int idsucursal;

    public Vendedor() {
    }

    public int getIdvendedor() {
        return idvendedor;
    }

    public void setIdvendedor(int idvendedor) {
        this.idvendedor = idvendedor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public double getPorcentajecomision() {
        return porcentajecomision;
    }

    public void setPorcentajecomision(double porcentajecomision) {
        this.porcentajecomision = porcentajecomision;
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
    
}
