package Vistas;

import Dao.DAOArticulo;
import Dao.DAOImpuesto;
import Dao.DAOLinea;
import Dao.DAOMarca;
import Dao.DAOSeccion;
import Dao.DAOTipoArticulo;
import Dao.DAOUnidadMedida;
import Modelos.Articulo;
import Modelos.Impuesto;
import Modelos.Linea;
import Modelos.Marca;
import Modelos.Seccion;
import Modelos.TipoArticulo;
import Modelos.UnidadMedida;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author armando
 */
public class JFrmArticulo extends javax.swing.JInternalFrame {

    Articulo a = new Articulo();
    Marca m = new Marca();
    Linea l = new Linea();
    Seccion s = new Seccion();
    TipoArticulo ta = new TipoArticulo();
    UnidadMedida um = new UnidadMedida();
    Impuesto i = new Impuesto();

    DAOArticulo dao = new DAOArticulo();
    DAOMarca daoMarca = new DAOMarca();
    DAOLinea daoLinea = new DAOLinea();
    DAOSeccion daoSeccion = new DAOSeccion();
    DAOTipoArticulo daoTipoArticulo = new DAOTipoArticulo();
    DAOUnidadMedida daoUnidadMedida = new DAOUnidadMedida();
    DAOImpuesto daoImpuesto = new DAOImpuesto();

    ArrayList<Object[]> datos = new ArrayList<>();
    ArrayList<Object[]> datosMarca = new ArrayList<>();
    ArrayList<Object[]> datosLinea = new ArrayList<>();
    ArrayList<Object[]> datosSeccion = new ArrayList<>();
    ArrayList<Object[]> datosTipoArticulo = new ArrayList<>();
    ArrayList<Object[]> datosUnidadMedida = new ArrayList<>();
    ArrayList<Object[]> datosImpuesto = new ArrayList<>();

    //VARIABLE QUE MANEJA QUE TIPOS DE OPERACIONES SE REALIZARAN: SI VA A SER ALTA, BAJA O MODIFICACION DEL REGISTRO
    String operacion = "";

    /**
     * Creates new form JFrmArticulo
     */
    public JFrmArticulo() {
        setTitle("JFrmArticulo");
        initComponents();
        cargar();
    }

    public void cargar() {
        DefaultTableModel modelo = (DefaultTableModel) tablaDatos.getModel();
        modelo.setRowCount(0);
        datos = dao.consultar(txtCriterio.getText(), " CONCAT(descripcion, referencia, codigoalfanumerico, codigobarra, idarticulo)");
        for (Object[] obj : datos) {
            modelo.addRow(obj);
        }
        this.tablaDatos.setModel(modelo);
    }

    public void cargarMarca() {
        DefaultTableModel modelo = (DefaultTableModel) tablaDatosMarca.getModel();
        modelo.setRowCount(0);
        datosMarca = daoMarca.consultar(txtCriterioMarca.getText());
        for (Object[] obj : datosMarca) {
            modelo.addRow(obj);
        }
        this.tablaDatosMarca.setModel(modelo);
    }

    public void cargarLinea() {
        DefaultTableModel modelo = (DefaultTableModel) tablaDatosLinea.getModel();
        modelo.setRowCount(0);
        datosLinea = daoLinea.consultar(txtCriterioLinea.getText());
        for (Object[] obj : datosLinea) {
            modelo.addRow(obj);
        }
        this.tablaDatosLinea.setModel(modelo);
    }

    public void cargarSeccion() {
        DefaultTableModel modelo = (DefaultTableModel) tablaDatosSeccion.getModel();
        modelo.setRowCount(0);
        datosSeccion = daoSeccion.consultar(txtCriterioSeccion.getText());
        for (Object[] obj : datosSeccion) {
            modelo.addRow(obj);
        }
        this.tablaDatosSeccion.setModel(modelo);
    }

    public void cargarTipoArticulo() {
        DefaultTableModel modelo = (DefaultTableModel) tablaDatosTipoArticulo.getModel();
        modelo.setRowCount(0);
        datosTipoArticulo = daoTipoArticulo.consultar(txtCriterioTipoArticulo.getText());
        for (Object[] obj : datosTipoArticulo) {
            modelo.addRow(obj);
        }
        this.tablaDatosTipoArticulo.setModel(modelo);
    }

    public void cargarUnidadMedida() {
        DefaultTableModel modelo = (DefaultTableModel) tablaDatosUnidadMedida.getModel();
        modelo.setRowCount(0);
        datosUnidadMedida = daoUnidadMedida.consultar(txtCriterioUnidadMedida.getText());
        for (Object[] obj : datosUnidadMedida) {
            modelo.addRow(obj);
        }
        this.tablaDatosUnidadMedida.setModel(modelo);
    }

    public void cargarImpuesto() {
        DefaultTableModel modelo = (DefaultTableModel) tablaDatosImpuesto.getModel();
        modelo.setRowCount(0);
        datosImpuesto = daoImpuesto.consultar(txtCriterioImpuesto.getText());
        for (Object[] obj : datosImpuesto) {
            modelo.addRow(obj);
        }
        this.tablaDatosImpuesto.setModel(modelo);
    }

    public void habilitarCampos(String accion) {
        switch (accion) {
            case "NUEVO":
                //CAMPOS
                txtCodigo.setEnabled(false);
                txtDescripcion.setEnabled(true);
                txtReferencia.setEnabled(true);
                txtCodigoAlfanumerico.setEnabled(true);
                txtCodigoBarra.setEnabled(true);
                rbActivo.setEnabled(true);
                rbInactivo.setEnabled(true);
                txtObservacion.setEnabled(true);
                txtCodigoMarca.setEnabled(true);
                txtCodigoLinea.setEnabled(true);
                txtCodigoSeccion.setEnabled(true);
                txtCodigoTipoArticulo.setEnabled(true);
                txtCodigoUnidadMedida.setEnabled(true);
                txtCodigoImpuesto.setEnabled(true);
                //BOTONES
                btnNuevo.setEnabled(false);
                btnConfirmar.setEnabled(true);
                btnCancelar.setEnabled(true);
                //REDIRECIONAMOS
                txtDescripcion.grabFocus();
                break;
            case "MODIFICAR":
                //CAMPOS
                txtCodigo.setEnabled(false);
                txtDescripcion.setEnabled(true);
                txtReferencia.setEnabled(true);
                txtCodigoAlfanumerico.setEnabled(true);
                txtCodigoBarra.setEnabled(true);
                rbActivo.setEnabled(true);
                rbInactivo.setEnabled(true);
                txtObservacion.setEnabled(true);
                txtCodigoMarca.setEnabled(true);
                txtCodigoLinea.setEnabled(true);
                txtCodigoSeccion.setEnabled(true);
                txtCodigoTipoArticulo.setEnabled(true);
                txtCodigoUnidadMedida.setEnabled(true);
                txtCodigoImpuesto.setEnabled(true);
                //BOTONES
                btnNuevo.setEnabled(false);
                btnConfirmar.setEnabled(true);
                btnCancelar.setEnabled(true);
                //REDIRECIONAMOS
                pestanha.setSelectedIndex(1);
                txtDescripcion.grabFocus();
                break;
            case "ELIMINAR":
                //CAMPOS
                txtCodigo.setEnabled(false);
                txtDescripcion.setEnabled(false);
                txtReferencia.setEnabled(false);
                txtCodigoAlfanumerico.setEnabled(false);
                txtCodigoBarra.setEnabled(false);
                rbActivo.setEnabled(false);
                rbInactivo.setEnabled(false);
                txtObservacion.setEnabled(false);
                txtCodigoMarca.setEnabled(false);
                txtCodigoLinea.setEnabled(false);
                txtCodigoSeccion.setEnabled(false);
                txtCodigoTipoArticulo.setEnabled(false);
                txtCodigoUnidadMedida.setEnabled(false);
                txtCodigoImpuesto.setEnabled(false);
                //BOTONES
                btnNuevo.setEnabled(false);
                btnConfirmar.setEnabled(true);
                btnCancelar.setEnabled(true);
                //REDIRECIONAMOS
                pestanha.setSelectedIndex(1);
                btnConfirmar.grabFocus();
                break;
            case "CANCELAR":
                //CAMPOS
                txtCodigo.setEnabled(false);
                txtDescripcion.setEnabled(false);
                txtReferencia.setEnabled(false);
                txtCodigoAlfanumerico.setEnabled(false);
                txtCodigoBarra.setEnabled(false);
                rbActivo.setEnabled(false);
                rbInactivo.setEnabled(false);
                txtObservacion.setEnabled(false);
                txtCodigoMarca.setEnabled(false);
                txtCodigoLinea.setEnabled(false);
                txtCodigoSeccion.setEnabled(false);
                txtCodigoTipoArticulo.setEnabled(false);
                txtCodigoUnidadMedida.setEnabled(false);
                txtCodigoImpuesto.setEnabled(false);
                //BOTONES
                btnNuevo.setEnabled(true);
                btnConfirmar.setEnabled(false);
                btnCancelar.setEnabled(false);
                //REDIRECIONAMOS
                pestanha.setSelectedIndex(0);
                txtCriterio.grabFocus();
                break;
            case "GUARDAR":
                //CAMPOS
                txtCodigo.setEnabled(false);
                txtDescripcion.setEnabled(false);
                txtReferencia.setEnabled(false);
                txtCodigoAlfanumerico.setEnabled(false);
                txtCodigoBarra.setEnabled(false);
                rbActivo.setEnabled(false);
                rbInactivo.setEnabled(false);
                txtObservacion.setEnabled(false);
                txtCodigoMarca.setEnabled(false);
                txtCodigoLinea.setEnabled(false);
                txtCodigoSeccion.setEnabled(false);
                txtCodigoTipoArticulo.setEnabled(false);
                txtCodigoUnidadMedida.setEnabled(false);
                txtCodigoImpuesto.setEnabled(false);
                //BOTONES
                btnNuevo.setEnabled(true);
                btnConfirmar.setEnabled(false);
                btnCancelar.setEnabled(false);
                //REDIRECIONAMOS
                btnNuevo.grabFocus();
                break;
            default:
                JOptionPane.showMessageDialog(null, "HA OCURRIDO UN ERROR EN LA HABILITACIÓN DE LOS CAMPOS. AVISE AL ADMINISTRADOR", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void limpiarCampos() {
        txtCriterio.setText(null);
        txtCodigo.setText(null);
        txtDescripcion.setText(null);
        txtReferencia.setText(null);
        txtCodigoAlfanumerico.setText(null);
        txtCodigoBarra.setText(null);
        rbActivo.setSelected(true);
        txtObservacion.setText(null);
        txtCodigoMarca.setText(null);
        txtDescripcionMarca.setText(null);
        txtCodigoLinea.setText(null);
        txtDescripcionLinea.setText(null);
        txtCodigoSeccion.setText(null);
        txtDescripcionSeccion.setText(null);
        txtCodigoTipoArticulo.setText(null);
        txtDescripcionTipoArticulo.setText(null);
        txtCodigoUnidadMedida.setText(null);
        txtDescripcionUnidadMedida.setText(null);
        txtCodigoImpuesto.setText(null);
        txtDescripcionImpuesto.setText(null);
        operacion = "";
    }

    public void guardar(String accion) {
        //CAPTURA Y VALIDACIONES DE LOS DATOS RECIBIDOS
        String error = "";
        int id = 0;
        if (accion.equals("NUEVO")) {
            id = dao.nuevoID();
        } else {
            if (txtCodigo.getText().isEmpty()) {
                id = 0;
            } else {
                id = Integer.parseInt(txtCodigo.getText());
            }
        }
        String descripcion = txtDescripcion.getText();
        String referencia = txtReferencia.getText();
        String cod_alfanumerico = txtCodigoAlfanumerico.getText();
        String cod_barra = txtCodigoBarra.getText();
        String estado;
        if (rbActivo.isSelected()) {
            estado = "A";
        } else {
            estado = "I";
        }
        String observacion = txtObservacion.getText();
        int idmarca = Integer.parseInt(txtCodigoMarca.getText());
        int idlinea = Integer.parseInt(txtCodigoLinea.getText());
        int idseccion = Integer.parseInt(txtCodigoSeccion.getText());
        int idtipoarticulo = Integer.parseInt(txtCodigoTipoArticulo.getText());
        int idunidadmedida = Integer.parseInt(txtCodigoUnidadMedida.getText());
        int idimpuesto = Integer.parseInt(txtCodigoImpuesto.getText());

        switch (accion) {
            case "NUEVO":
                if (descripcion.isEmpty()) {
                    error += "NO PUEDE DEJAR EL CAMPO DE DESCRIPCIÓN VACIO.\n";
                }
                if (idmarca == 0) {
                    error += "NO HA SELECCIONADO UNA MARCA PARA EL ARTÍCULO.\n";
                }
                if (idlinea == 0) {
                    error += "NO HA SELECCIONADO UNA LÍNEA PARA EL ARTÍCULO.\n";
                }
                if (idseccion == 0) {
                    error += "NO HA SELECCIONADO UNA SECCION PARA EL ARTÍCULO.\n";
                }
                if (idtipoarticulo == 0) {
                    error += "NO HA SELECCIONADO UN TIPO DE ARTÍCULO PARA EL ARTÍCULO.\n";
                }
                if (idunidadmedida == 0) {
                    error += "NO HA SELECCIONADO UNA UNIDAD DE MEDIDA PARA EL ARTÍCULO.\n";
                }
                if (idimpuesto == 0) {
                    error += "NO HA SELECCIONADO UN IMPUESTO PARA EL ARTÍCULO.\n";
                }
                if (error.isEmpty()) {
                    a.setIdarticulo(id);
                    a.setDescripcion(descripcion);
                    a.setReferencia(referencia);
                    a.setCodigoalfanumerico(cod_alfanumerico);
                    a.setCodigobarra(cod_barra);
                    a.setEstado(estado);
                    a.setObservacion(observacion);
                    a.setIdmarca(idmarca);
                    a.setIdlinea(idlinea);
                    a.setIdseccion(idseccion);
                    a.setIdtipo(idtipoarticulo);
                    a.setIdunidad(idunidadmedida);
                    a.setIdimpuesto(idimpuesto);
                    dao.agregar(a);
                    cargar();
                } else {
                    JOptionPane.showMessageDialog(null, error, "ERRORES", JOptionPane.ERROR_MESSAGE);
                }
                break;
            case "MODIFICAR":
                if (descripcion.isEmpty()) {
                    error += "NO PUEDE DEJAR EL CAMPO DE DESCRIPCIÓN VACIO.\n";
                }
                if (idmarca == 0) {
                    error += "NO HA SELECCIONADO UNA MARCA PARA EL ARTÍCULO.\n";
                }
                if (idlinea == 0) {
                    error += "NO HA SELECCIONADO UNA LÍNEA PARA EL ARTÍCULO.\n";
                }
                if (idseccion == 0) {
                    error += "NO HA SELECCIONADO UNA SECCION PARA EL ARTÍCULO.\n";
                }
                if (idtipoarticulo == 0) {
                    error += "NO HA SELECCIONADO UN TIPO DE ARTÍCULO PARA EL ARTÍCULO.\n";
                }
                if (idunidadmedida == 0) {
                    error += "NO HA SELECCIONADO UNA UNIDAD DE MEDIDA PARA EL ARTÍCULO.\n";
                }
                if (idimpuesto == 0) {
                    error += "NO HA SELECCIONADO UN IMPUESTO PARA EL ARTÍCULO.\n";
                }
                if (error.isEmpty()) {
                    a.setDescripcion(descripcion);
                    a.setReferencia(referencia);
                    a.setCodigoalfanumerico(cod_alfanumerico);
                    a.setCodigobarra(cod_barra);
                    a.setEstado(estado);
                    a.setObservacion(observacion);
                    a.setIdmarca(idmarca);
                    a.setIdlinea(idlinea);
                    a.setIdseccion(idseccion);
                    a.setIdtipo(idtipoarticulo);
                    a.setIdunidad(idunidadmedida);
                    a.setIdimpuesto(idimpuesto);
                    a.setIdarticulo(id);
                    dao.modificar(a);
                    cargar();
                } else {
                    JOptionPane.showMessageDialog(null, error, "ERRORES", JOptionPane.ERROR_MESSAGE);
                }
                break;
            case "ELIMINAR":
                if (error.isEmpty()) {
                    a.setIdarticulo(id);
                    dao.eliminar(a);
                    cargar();
                }
                break;
            default:
                JOptionPane.showMessageDialog(null, "HA OCURRIDO UN ERROR EN LA OPERACION PARA LA BASE DE DATOS. AVISE AL ADMINISTRADOR", "ERROR", JOptionPane.ERROR_MESSAGE);
        }

    }

    public void recuperarDatos() {
        int fila = tablaDatos.getSelectedRow();
        String referencia = null;
        String codigoalfanumerico = null;
        String codigobarra = null;
        String estado = null;
        String observacion = null;
        int idmarca = 0;
        int idlinea = 0;
        int idseccion = 0;
        int idtipo = 0;
        int idunidad = 0;
        int idimpuesto = 0;

        if (fila >= 0) {
            int id = Integer.parseInt(tablaDatos.getValueAt(fila, 0).toString());
            //System.out.println("" + id);
            String descripcion = tablaDatos.getValueAt(fila, 1).toString();
            a.setIdarticulo(id);
            dao.consultarDatos(a);
            referencia = a.getReferencia();
            codigoalfanumerico = a.getCodigoalfanumerico();
            codigobarra = a.getCodigobarra();
            estado = a.getEstado();
            observacion = a.getObservacion();
            idmarca = a.getIdmarca();
            idlinea = a.getIdlinea();
            idseccion = a.getIdseccion();
            idtipo = a.getIdtipo();
            idunidad = a.getIdunidad();
            idimpuesto = a.getIdimpuesto();
            //ASIGNAMOS LOS VALORES RECUPERADOS A LOS CAMPOS DEL PROGRAMA
            txtCodigo.setText("" + id);
            txtDescripcion.setText(descripcion);
            txtReferencia.setText(referencia);
            txtCodigoAlfanumerico.setText(codigoalfanumerico);
            txtCodigoBarra.setText(codigobarra);
            if (estado.equals("A")) {
                rbActivo.setSelected(true);
            } else {
                rbInactivo.setSelected(true);
            }
            txtObservacion.setText(observacion);

            m.setIdmarca(idmarca);
            daoMarca.consultarDatos(m);
            txtCodigoMarca.setText("" + idmarca);
            txtDescripcionMarca.setText(m.getDescripcion());

            l.setIdlinea(idlinea);
            daoLinea.consultarDatos(l);
            txtCodigoLinea.setText("" + idlinea);
            txtDescripcionLinea.setText(l.getDescripcion());

            s.setIdseccion(idseccion);
            daoSeccion.consultarDatos(s);
            txtCodigoSeccion.setText("" + idseccion);
            txtDescripcionSeccion.setText(s.getDescripcion());

            ta.setIdtipo(idtipo);
            daoTipoArticulo.consultarDatos(ta);
            txtCodigoTipoArticulo.setText("" + idtipo);
            txtDescripcionTipoArticulo.setText(ta.getDescripcion());

            um.setIdunidad(idunidad);
            daoUnidadMedida.consultarDatos(um);
            txtCodigoUnidadMedida.setText("" + idunidad);
            txtDescripcionUnidadMedida.setText(um.getDescripcion());

            i.setIdimpuesto(idimpuesto);
            daoImpuesto.consultarDatos(i);
            txtCodigoImpuesto.setText("" + idimpuesto);
            txtDescripcionImpuesto.setText(i.getDescripcion());

            habilitarCampos(operacion);
        } else {
            JOptionPane.showMessageDialog(null, "SELECCIONE UNA FILA", "ADVERTENCIA", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void buscarMarca() {
        cargarMarca();
        BuscadorMarca.setModal(true);
        BuscadorMarca.setSize(540, 285);
        BuscadorMarca.setLocationRelativeTo(this);
        BuscadorMarca.setVisible(true);
        int fila = tablaDatosMarca.getSelectedRow();
        if (fila >= 0) {
            txtCodigoMarca.setText(tablaDatosMarca.getValueAt(fila, 0).toString());
            txtDescripcionMarca.setText(tablaDatosMarca.getValueAt(fila, 1).toString());
        } else {
            txtCodigoMarca.setText(null);
            txtDescripcionMarca.setText(null);
        }
    }

    private void buscarLinea() {
        cargarLinea();
        BuscadorLinea.setModal(true);
        BuscadorLinea.setSize(540, 285);
        BuscadorLinea.setLocationRelativeTo(this);
        BuscadorLinea.setVisible(true);
        int fila = tablaDatosLinea.getSelectedRow();
        if (fila >= 0) {
            txtCodigoLinea.setText(tablaDatosLinea.getValueAt(fila, 0).toString());
            txtDescripcionLinea.setText(tablaDatosLinea.getValueAt(fila, 1).toString());
        } else {
            txtCodigoLinea.setText(null);
            txtDescripcionLinea.setText(null);
        }
    }

    private void buscarSeccion() {
        cargarSeccion();
        BuscadorSeccion.setModal(true);
        BuscadorSeccion.setSize(540, 285);
        BuscadorSeccion.setLocationRelativeTo(this);
        BuscadorSeccion.setVisible(true);
        int fila = tablaDatosSeccion.getSelectedRow();
        if (fila >= 0) {
            txtCodigoSeccion.setText(tablaDatosSeccion.getValueAt(fila, 0).toString());
            txtDescripcionSeccion.setText(tablaDatosSeccion.getValueAt(fila, 1).toString());
        } else {
            txtCodigoSeccion.setText(null);
            txtDescripcionSeccion.setText(null);
        }
    }

    private void buscarTipoArticulo() {
        cargarTipoArticulo();
        BuscadorTipoArticulo.setModal(true);
        BuscadorTipoArticulo.setSize(540, 285);
        BuscadorTipoArticulo.setLocationRelativeTo(this);
        BuscadorTipoArticulo.setVisible(true);
        int fila = tablaDatosTipoArticulo.getSelectedRow();
        if (fila >= 0) {
            txtCodigoTipoArticulo.setText(tablaDatosTipoArticulo.getValueAt(fila, 0).toString());
            txtDescripcionTipoArticulo.setText(tablaDatosTipoArticulo.getValueAt(fila, 1).toString());
        } else {
            txtCodigoTipoArticulo.setText(null);
            txtDescripcionTipoArticulo.setText(null);
        }
    }

    private void buscarUnidadMedida() {
        cargarUnidadMedida();
        BuscadorUnidadMedida.setModal(true);
        BuscadorUnidadMedida.setSize(540, 285);
        BuscadorUnidadMedida.setLocationRelativeTo(this);
        BuscadorUnidadMedida.setVisible(true);
        int fila = tablaDatosUnidadMedida.getSelectedRow();
        if (fila >= 0) {
            txtCodigoUnidadMedida.setText(tablaDatosUnidadMedida.getValueAt(fila, 0).toString());
            txtDescripcionUnidadMedida.setText(tablaDatosUnidadMedida.getValueAt(fila, 1).toString());
        } else {
            txtCodigoUnidadMedida.setText(null);
            txtDescripcionUnidadMedida.setText(null);
        }
    }

    private void buscarImpuesto() {
        cargarImpuesto();
        BuscadorImpuesto.setModal(true);
        BuscadorImpuesto.setSize(540, 285);
        BuscadorImpuesto.setLocationRelativeTo(this);
        BuscadorImpuesto.setVisible(true);
        int fila = tablaDatosImpuesto.getSelectedRow();
        if (fila >= 0) {
            txtCodigoImpuesto.setText(tablaDatosImpuesto.getValueAt(fila, 0).toString());
            txtDescripcionImpuesto.setText(tablaDatosImpuesto.getValueAt(fila, 1).toString());
        } else {
            txtCodigoImpuesto.setText(null);
            txtDescripcionImpuesto.setText(null);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        menuDesplegable = new javax.swing.JPopupMenu();
        Modificar = new javax.swing.JMenuItem();
        Eliminar = new javax.swing.JMenuItem();
        grupoEstado = new javax.swing.ButtonGroup();
        BuscadorMarca = new javax.swing.JDialog();
        jPanel3 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        txtCriterioMarca = new org.jdesktop.swingx.JXTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaDatosMarca = new javax.swing.JTable();
        BuscadorLinea = new javax.swing.JDialog();
        jPanel4 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        txtCriterioLinea = new org.jdesktop.swingx.JXTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaDatosLinea = new javax.swing.JTable();
        BuscadorSeccion = new javax.swing.JDialog();
        jPanel5 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        txtCriterioSeccion = new org.jdesktop.swingx.JXTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        tablaDatosSeccion = new javax.swing.JTable();
        BuscadorTipoArticulo = new javax.swing.JDialog();
        jPanel6 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        txtCriterioTipoArticulo = new org.jdesktop.swingx.JXTextField();
        jScrollPane5 = new javax.swing.JScrollPane();
        tablaDatosTipoArticulo = new javax.swing.JTable();
        BuscadorUnidadMedida = new javax.swing.JDialog();
        jPanel7 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        txtCriterioUnidadMedida = new org.jdesktop.swingx.JXTextField();
        jScrollPane6 = new javax.swing.JScrollPane();
        tablaDatosUnidadMedida = new javax.swing.JTable();
        BuscadorImpuesto = new javax.swing.JDialog();
        jPanel8 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        txtCriterioImpuesto = new org.jdesktop.swingx.JXTextField();
        jScrollPane7 = new javax.swing.JScrollPane();
        tablaDatosImpuesto = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        pestanha = new javax.swing.JTabbedPane();
        pestanhaLista = new javax.swing.JPanel();
        txtCriterio = new org.jdesktop.swingx.JXTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaDatos = new javax.swing.JTable();
        pestanhaABM = new javax.swing.JPanel();
        txtDescripcion = new org.jdesktop.swingx.JXTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtCodigo = new org.jdesktop.swingx.JXTextField();
        btnCancelar = new javax.swing.JButton();
        btnConfirmar = new javax.swing.JButton();
        btnNuevo = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        txtReferencia = new org.jdesktop.swingx.JXTextField();
        jLabel5 = new javax.swing.JLabel();
        txtCodigoAlfanumerico = new org.jdesktop.swingx.JXTextField();
        jLabel6 = new javax.swing.JLabel();
        txtCodigoBarra = new org.jdesktop.swingx.JXTextField();
        rbActivo = new javax.swing.JRadioButton();
        rbInactivo = new javax.swing.JRadioButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtObservacion = new org.jdesktop.swingx.JXTextField();
        jLabel9 = new javax.swing.JLabel();
        txtCodigoMarca = new org.jdesktop.swingx.JXTextField();
        txtDescripcionMarca = new org.jdesktop.swingx.JXTextField();
        jLabel10 = new javax.swing.JLabel();
        txtCodigoLinea = new org.jdesktop.swingx.JXTextField();
        txtDescripcionLinea = new org.jdesktop.swingx.JXTextField();
        jLabel11 = new javax.swing.JLabel();
        txtCodigoSeccion = new org.jdesktop.swingx.JXTextField();
        txtDescripcionSeccion = new org.jdesktop.swingx.JXTextField();
        jLabel12 = new javax.swing.JLabel();
        txtCodigoTipoArticulo = new org.jdesktop.swingx.JXTextField();
        txtDescripcionTipoArticulo = new org.jdesktop.swingx.JXTextField();
        jLabel13 = new javax.swing.JLabel();
        txtCodigoUnidadMedida = new org.jdesktop.swingx.JXTextField();
        jLabel14 = new javax.swing.JLabel();
        txtCodigoImpuesto = new org.jdesktop.swingx.JXTextField();
        txtDescripcionImpuesto = new org.jdesktop.swingx.JXTextField();
        txtDescripcionUnidadMedida = new org.jdesktop.swingx.JXTextField();

        Modificar.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        Modificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/icons8_edit_file_16px.png"))); // NOI18N
        Modificar.setText("Modificar");
        Modificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ModificarActionPerformed(evt);
            }
        });
        menuDesplegable.add(Modificar);

        Eliminar.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        Eliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/icons8_delete_file_16px.png"))); // NOI18N
        Eliminar.setText("Eliminar");
        Eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EliminarActionPerformed(evt);
            }
        });
        menuDesplegable.add(Eliminar);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel15.setBackground(new java.awt.Color(50, 104, 151));
        jLabel15.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("BUSCADOR DE MARCAS");
        jLabel15.setOpaque(true);

        txtCriterioMarca.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtCriterioMarca.setPrompt("Aqui puede ingresar los filtros para la busqueda..");
        txtCriterioMarca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCriterioMarcaActionPerformed(evt);
            }
        });
        txtCriterioMarca.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCriterioMarcaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCriterioMarcaKeyTyped(evt);
            }
        });

        tablaDatosMarca.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        tablaDatosMarca.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "<html><p style=\"text-align:center\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Código</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Descripción</span></span></span></p></html> "
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaDatosMarca.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaDatosMarcaMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tablaDatosMarca);
        if (tablaDatosMarca.getColumnModel().getColumnCount() > 0) {
            tablaDatosMarca.getColumnModel().getColumn(0).setMinWidth(60);
            tablaDatosMarca.getColumnModel().getColumn(0).setPreferredWidth(60);
            tablaDatosMarca.getColumnModel().getColumn(0).setMaxWidth(60);
        }

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCriterioMarca, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCriterioMarca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout BuscadorMarcaLayout = new javax.swing.GroupLayout(BuscadorMarca.getContentPane());
        BuscadorMarca.getContentPane().setLayout(BuscadorMarcaLayout);
        BuscadorMarcaLayout.setHorizontalGroup(
            BuscadorMarcaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BuscadorMarcaLayout.setVerticalGroup(
            BuscadorMarcaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jLabel16.setBackground(new java.awt.Color(50, 104, 151));
        jLabel16.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("BUSCADOR DE LÍNEAS");
        jLabel16.setOpaque(true);

        txtCriterioLinea.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtCriterioLinea.setPrompt("Aqui puede ingresar los filtros para la busqueda..");
        txtCriterioLinea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCriterioLineaActionPerformed(evt);
            }
        });
        txtCriterioLinea.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCriterioLineaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCriterioLineaKeyTyped(evt);
            }
        });

        tablaDatosLinea.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        tablaDatosLinea.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "<html><p style=\"text-align:center\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Código</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Descripción</span></span></span></p></html> "
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaDatosLinea.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaDatosLineaMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tablaDatosLinea);
        if (tablaDatosLinea.getColumnModel().getColumnCount() > 0) {
            tablaDatosLinea.getColumnModel().getColumn(0).setMinWidth(60);
            tablaDatosLinea.getColumnModel().getColumn(0).setPreferredWidth(60);
            tablaDatosLinea.getColumnModel().getColumn(0).setMaxWidth(60);
        }

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCriterioLinea, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCriterioLinea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout BuscadorLineaLayout = new javax.swing.GroupLayout(BuscadorLinea.getContentPane());
        BuscadorLinea.getContentPane().setLayout(BuscadorLineaLayout);
        BuscadorLineaLayout.setHorizontalGroup(
            BuscadorLineaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BuscadorLineaLayout.setVerticalGroup(
            BuscadorLineaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jLabel17.setBackground(new java.awt.Color(50, 104, 151));
        jLabel17.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("BUSCADOR DE SECCIONES");
        jLabel17.setOpaque(true);

        txtCriterioSeccion.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtCriterioSeccion.setPrompt("Aqui puede ingresar los filtros para la busqueda..");
        txtCriterioSeccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCriterioSeccionActionPerformed(evt);
            }
        });
        txtCriterioSeccion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCriterioSeccionKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCriterioSeccionKeyTyped(evt);
            }
        });

        tablaDatosSeccion.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        tablaDatosSeccion.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "<html><p style=\"text-align:center\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Código</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Descripción</span></span></span></p></html> "
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaDatosSeccion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaDatosSeccionMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tablaDatosSeccion);
        if (tablaDatosSeccion.getColumnModel().getColumnCount() > 0) {
            tablaDatosSeccion.getColumnModel().getColumn(0).setMinWidth(60);
            tablaDatosSeccion.getColumnModel().getColumn(0).setPreferredWidth(60);
            tablaDatosSeccion.getColumnModel().getColumn(0).setMaxWidth(60);
        }

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCriterioSeccion, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCriterioSeccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout BuscadorSeccionLayout = new javax.swing.GroupLayout(BuscadorSeccion.getContentPane());
        BuscadorSeccion.getContentPane().setLayout(BuscadorSeccionLayout);
        BuscadorSeccionLayout.setHorizontalGroup(
            BuscadorSeccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BuscadorSeccionLayout.setVerticalGroup(
            BuscadorSeccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        jLabel18.setBackground(new java.awt.Color(50, 104, 151));
        jLabel18.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("BUSCADOR DE TIPOS DE ARTÍCULOS");
        jLabel18.setOpaque(true);

        txtCriterioTipoArticulo.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtCriterioTipoArticulo.setPrompt("Aqui puede ingresar los filtros para la busqueda..");
        txtCriterioTipoArticulo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCriterioTipoArticuloActionPerformed(evt);
            }
        });
        txtCriterioTipoArticulo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCriterioTipoArticuloKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCriterioTipoArticuloKeyTyped(evt);
            }
        });

        tablaDatosTipoArticulo.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        tablaDatosTipoArticulo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "<html><p style=\"text-align:center\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Código</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Descripción</span></span></span></p></html> "
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaDatosTipoArticulo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaDatosTipoArticuloMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tablaDatosTipoArticulo);
        if (tablaDatosTipoArticulo.getColumnModel().getColumnCount() > 0) {
            tablaDatosTipoArticulo.getColumnModel().getColumn(0).setMinWidth(60);
            tablaDatosTipoArticulo.getColumnModel().getColumn(0).setPreferredWidth(60);
            tablaDatosTipoArticulo.getColumnModel().getColumn(0).setMaxWidth(60);
        }

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCriterioTipoArticulo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCriterioTipoArticulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout BuscadorTipoArticuloLayout = new javax.swing.GroupLayout(BuscadorTipoArticulo.getContentPane());
        BuscadorTipoArticulo.getContentPane().setLayout(BuscadorTipoArticuloLayout);
        BuscadorTipoArticuloLayout.setHorizontalGroup(
            BuscadorTipoArticuloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BuscadorTipoArticuloLayout.setVerticalGroup(
            BuscadorTipoArticuloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        jLabel19.setBackground(new java.awt.Color(50, 104, 151));
        jLabel19.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setText("BUSCADOR DE UNIDADES DE MEDIDAS");
        jLabel19.setOpaque(true);

        txtCriterioUnidadMedida.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtCriterioUnidadMedida.setPrompt("Aqui puede ingresar los filtros para la busqueda..");
        txtCriterioUnidadMedida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCriterioUnidadMedidaActionPerformed(evt);
            }
        });
        txtCriterioUnidadMedida.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCriterioUnidadMedidaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCriterioUnidadMedidaKeyTyped(evt);
            }
        });

        tablaDatosUnidadMedida.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        tablaDatosUnidadMedida.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "<html><p style=\"text-align:center\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Código</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Descripción</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Simbolo</span></span></span></p></html> "
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaDatosUnidadMedida.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaDatosUnidadMedidaMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(tablaDatosUnidadMedida);
        if (tablaDatosUnidadMedida.getColumnModel().getColumnCount() > 0) {
            tablaDatosUnidadMedida.getColumnModel().getColumn(0).setMinWidth(60);
            tablaDatosUnidadMedida.getColumnModel().getColumn(0).setPreferredWidth(60);
            tablaDatosUnidadMedida.getColumnModel().getColumn(0).setMaxWidth(60);
            tablaDatosUnidadMedida.getColumnModel().getColumn(2).setMinWidth(0);
            tablaDatosUnidadMedida.getColumnModel().getColumn(2).setPreferredWidth(0);
            tablaDatosUnidadMedida.getColumnModel().getColumn(2).setMaxWidth(0);
        }

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCriterioUnidadMedida, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCriterioUnidadMedida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout BuscadorUnidadMedidaLayout = new javax.swing.GroupLayout(BuscadorUnidadMedida.getContentPane());
        BuscadorUnidadMedida.getContentPane().setLayout(BuscadorUnidadMedidaLayout);
        BuscadorUnidadMedidaLayout.setHorizontalGroup(
            BuscadorUnidadMedidaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BuscadorUnidadMedidaLayout.setVerticalGroup(
            BuscadorUnidadMedidaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));

        jLabel20.setBackground(new java.awt.Color(50, 104, 151));
        jLabel20.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setText("BUSCADOR DE IMPUESTOS");
        jLabel20.setOpaque(true);

        txtCriterioImpuesto.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtCriterioImpuesto.setPrompt("Aqui puede ingresar los filtros para la busqueda..");
        txtCriterioImpuesto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCriterioImpuestoActionPerformed(evt);
            }
        });
        txtCriterioImpuesto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCriterioImpuestoKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCriterioImpuestoKeyTyped(evt);
            }
        });

        tablaDatosImpuesto.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        tablaDatosImpuesto.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "<html><p style=\"text-align:center\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Código</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Descripción</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Porcentaje</span></span></span></p></html> "
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaDatosImpuesto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaDatosImpuestoMouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(tablaDatosImpuesto);
        if (tablaDatosImpuesto.getColumnModel().getColumnCount() > 0) {
            tablaDatosImpuesto.getColumnModel().getColumn(0).setMinWidth(60);
            tablaDatosImpuesto.getColumnModel().getColumn(0).setPreferredWidth(60);
            tablaDatosImpuesto.getColumnModel().getColumn(0).setMaxWidth(60);
            tablaDatosImpuesto.getColumnModel().getColumn(2).setMinWidth(0);
            tablaDatosImpuesto.getColumnModel().getColumn(2).setPreferredWidth(0);
            tablaDatosImpuesto.getColumnModel().getColumn(2).setMaxWidth(0);
        }

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCriterioImpuesto, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCriterioImpuesto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout BuscadorImpuestoLayout = new javax.swing.GroupLayout(BuscadorImpuesto.getContentPane());
        BuscadorImpuesto.getContentPane().setLayout(BuscadorImpuestoLayout);
        BuscadorImpuestoLayout.setHorizontalGroup(
            BuscadorImpuestoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BuscadorImpuestoLayout.setVerticalGroup(
            BuscadorImpuestoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setClosable(true);
        setIconifiable(true);

        jPanel1.setBackground(new java.awt.Color(50, 104, 151));

        jLabel1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Mantenimiento de Artículos");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        pestanha.setBackground(new java.awt.Color(255, 255, 255));
        pestanha.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        pestanha.setOpaque(true);

        pestanhaLista.setBackground(new java.awt.Color(255, 255, 255));

        txtCriterio.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtCriterio.setPrompt("Aqui puede ingresar los filtros para la busqueda..");
        txtCriterio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCriterioActionPerformed(evt);
            }
        });
        txtCriterio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCriterioKeyTyped(evt);
            }
        });

        tablaDatos.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        tablaDatos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "<html><p style=\"text-align:center\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Código</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Descripción</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Referencia</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Cód.Alfanumérico</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Cód.Barra</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Estado</span></span></span></p></html> "
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaDatos.setComponentPopupMenu(menuDesplegable);
        jScrollPane1.setViewportView(tablaDatos);
        if (tablaDatos.getColumnModel().getColumnCount() > 0) {
            tablaDatos.getColumnModel().getColumn(0).setMinWidth(60);
            tablaDatos.getColumnModel().getColumn(0).setPreferredWidth(60);
            tablaDatos.getColumnModel().getColumn(0).setMaxWidth(60);
            tablaDatos.getColumnModel().getColumn(3).setMinWidth(150);
            tablaDatos.getColumnModel().getColumn(3).setPreferredWidth(150);
            tablaDatos.getColumnModel().getColumn(3).setMaxWidth(150);
            tablaDatos.getColumnModel().getColumn(4).setMinWidth(150);
            tablaDatos.getColumnModel().getColumn(4).setPreferredWidth(150);
            tablaDatos.getColumnModel().getColumn(4).setMaxWidth(150);
            tablaDatos.getColumnModel().getColumn(5).setMinWidth(60);
            tablaDatos.getColumnModel().getColumn(5).setPreferredWidth(60);
            tablaDatos.getColumnModel().getColumn(5).setMaxWidth(60);
        }

        javax.swing.GroupLayout pestanhaListaLayout = new javax.swing.GroupLayout(pestanhaLista);
        pestanhaLista.setLayout(pestanhaListaLayout);
        pestanhaListaLayout.setHorizontalGroup(
            pestanhaListaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pestanhaListaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pestanhaListaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCriterio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 939, Short.MAX_VALUE))
                .addContainerGap())
        );
        pestanhaListaLayout.setVerticalGroup(
            pestanhaListaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pestanhaListaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtCriterio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 332, Short.MAX_VALUE)
                .addContainerGap())
        );

        pestanha.addTab("Listado", pestanhaLista);

        pestanhaABM.setBackground(new java.awt.Color(255, 255, 255));

        txtDescripcion.setEnabled(false);
        txtDescripcion.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtDescripcion.setPrompt("Nombre o descripción...");
        txtDescripcion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDescripcionActionPerformed(evt);
            }
        });
        txtDescripcion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDescripcionKeyTyped(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel2.setText("Código:");

        jLabel3.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel3.setText("Descripción:");

        txtCodigo.setEnabled(false);
        txtCodigo.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtCodigo.setPrompt("Código interno...");

        btnCancelar.setBackground(new java.awt.Color(255, 204, 204));
        btnCancelar.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/icons8_cancel_16px.png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.setEnabled(false);
        btnCancelar.setOpaque(false);
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnConfirmar.setBackground(new java.awt.Color(204, 255, 255));
        btnConfirmar.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        btnConfirmar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/icons8_checked_16px_1.png"))); // NOI18N
        btnConfirmar.setText("Confirmar");
        btnConfirmar.setEnabled(false);
        btnConfirmar.setOpaque(false);
        btnConfirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmarActionPerformed(evt);
            }
        });

        btnNuevo.setBackground(new java.awt.Color(197, 255, 226));
        btnNuevo.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        btnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/icons8_add_16px.png"))); // NOI18N
        btnNuevo.setText("Nuevo");
        btnNuevo.setOpaque(false);
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel4.setText("Referencia:");

        txtReferencia.setEnabled(false);
        txtReferencia.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtReferencia.setPrompt("Referencias del artículo...");
        txtReferencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtReferenciaActionPerformed(evt);
            }
        });
        txtReferencia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtReferenciaKeyTyped(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel5.setText("Cód. Alfa.:");

        txtCodigoAlfanumerico.setEnabled(false);
        txtCodigoAlfanumerico.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtCodigoAlfanumerico.setPrompt("Código Alfanumérico...");
        txtCodigoAlfanumerico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoAlfanumericoActionPerformed(evt);
            }
        });
        txtCodigoAlfanumerico.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodigoAlfanumericoKeyTyped(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel6.setText("Cód. Barra:");

        txtCodigoBarra.setEnabled(false);
        txtCodigoBarra.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtCodigoBarra.setPrompt("Código de Barra...");
        txtCodigoBarra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoBarraActionPerformed(evt);
            }
        });
        txtCodigoBarra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodigoBarraKeyTyped(evt);
            }
        });

        grupoEstado.add(rbActivo);
        rbActivo.setSelected(true);
        rbActivo.setText("ACTIVO");
        rbActivo.setEnabled(false);
        rbActivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbActivoActionPerformed(evt);
            }
        });
        rbActivo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                rbActivoKeyPressed(evt);
            }
        });

        grupoEstado.add(rbInactivo);
        rbInactivo.setText("INACTIVO");
        rbInactivo.setEnabled(false);
        rbInactivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbInactivoActionPerformed(evt);
            }
        });
        rbInactivo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                rbInactivoKeyPressed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel7.setText("Estado:");

        jLabel8.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel8.setText("Obs:");

        txtObservacion.setEnabled(false);
        txtObservacion.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtObservacion.setPrompt("Observaciónes del artículo...");
        txtObservacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtObservacionActionPerformed(evt);
            }
        });
        txtObservacion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtObservacionKeyTyped(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel9.setText("Marca:");

        txtCodigoMarca.setEnabled(false);
        txtCodigoMarca.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtCodigoMarca.setPrompt("Cód.Marca");
        txtCodigoMarca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoMarcaActionPerformed(evt);
            }
        });
        txtCodigoMarca.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodigoMarcaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodigoMarcaKeyTyped(evt);
            }
        });

        txtDescripcionMarca.setEnabled(false);
        txtDescripcionMarca.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtDescripcionMarca.setPrompt("Descripción o nombre de la marca...");
        txtDescripcionMarca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDescripcionMarcaActionPerformed(evt);
            }
        });
        txtDescripcionMarca.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDescripcionMarcaKeyTyped(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel10.setText("Línea:");

        txtCodigoLinea.setEnabled(false);
        txtCodigoLinea.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtCodigoLinea.setPrompt("Cód.Línea");
        txtCodigoLinea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoLineaActionPerformed(evt);
            }
        });
        txtCodigoLinea.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodigoLineaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodigoLineaKeyTyped(evt);
            }
        });

        txtDescripcionLinea.setEnabled(false);
        txtDescripcionLinea.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtDescripcionLinea.setPrompt("Descripción o nombre de la línea...");
        txtDescripcionLinea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDescripcionLineaActionPerformed(evt);
            }
        });
        txtDescripcionLinea.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDescripcionLineaKeyTyped(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel11.setText("Seccion:");

        txtCodigoSeccion.setEnabled(false);
        txtCodigoSeccion.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtCodigoSeccion.setPrompt("Cód.Seccion");
        txtCodigoSeccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoSeccionActionPerformed(evt);
            }
        });
        txtCodigoSeccion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodigoSeccionKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodigoSeccionKeyTyped(evt);
            }
        });

        txtDescripcionSeccion.setEnabled(false);
        txtDescripcionSeccion.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtDescripcionSeccion.setPrompt("Descripción o nombre de la seccion...");
        txtDescripcionSeccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDescripcionSeccionActionPerformed(evt);
            }
        });
        txtDescripcionSeccion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDescripcionSeccionKeyTyped(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel12.setText("T. Artículo:");

        txtCodigoTipoArticulo.setEnabled(false);
        txtCodigoTipoArticulo.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtCodigoTipoArticulo.setPrompt("Cód.T.Artículo");
        txtCodigoTipoArticulo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoTipoArticuloActionPerformed(evt);
            }
        });
        txtCodigoTipoArticulo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodigoTipoArticuloKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodigoTipoArticuloKeyTyped(evt);
            }
        });

        txtDescripcionTipoArticulo.setEnabled(false);
        txtDescripcionTipoArticulo.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtDescripcionTipoArticulo.setPrompt("Descripción o nombre del tipo de artículo...");
        txtDescripcionTipoArticulo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDescripcionTipoArticuloActionPerformed(evt);
            }
        });
        txtDescripcionTipoArticulo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDescripcionTipoArticuloKeyTyped(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel13.setText("Unidad M.:");

        txtCodigoUnidadMedida.setEnabled(false);
        txtCodigoUnidadMedida.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtCodigoUnidadMedida.setPrompt("Cód.U.M.");
        txtCodigoUnidadMedida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoUnidadMedidaActionPerformed(evt);
            }
        });
        txtCodigoUnidadMedida.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodigoUnidadMedidaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodigoUnidadMedidaKeyTyped(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel14.setText("Impuesto:");

        txtCodigoImpuesto.setEnabled(false);
        txtCodigoImpuesto.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtCodigoImpuesto.setPrompt("Cód.Impuesto");
        txtCodigoImpuesto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoImpuestoActionPerformed(evt);
            }
        });
        txtCodigoImpuesto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodigoImpuestoKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodigoImpuestoKeyTyped(evt);
            }
        });

        txtDescripcionImpuesto.setEnabled(false);
        txtDescripcionImpuesto.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtDescripcionImpuesto.setPrompt("Descripción o nombre del impuesto...");
        txtDescripcionImpuesto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDescripcionImpuestoActionPerformed(evt);
            }
        });
        txtDescripcionImpuesto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDescripcionImpuestoKeyTyped(evt);
            }
        });

        txtDescripcionUnidadMedida.setEnabled(false);
        txtDescripcionUnidadMedida.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtDescripcionUnidadMedida.setPrompt("Descripción o nombre de la unidad de medida...");
        txtDescripcionUnidadMedida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDescripcionUnidadMedidaActionPerformed(evt);
            }
        });
        txtDescripcionUnidadMedida.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDescripcionUnidadMedidaKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout pestanhaABMLayout = new javax.swing.GroupLayout(pestanhaABM);
        pestanhaABM.setLayout(pestanhaABMLayout);
        pestanhaABMLayout.setHorizontalGroup(
            pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pestanhaABMLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pestanhaABMLayout.createSequentialGroup()
                        .addGroup(pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pestanhaABMLayout.createSequentialGroup()
                                .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(pestanhaABMLayout.createSequentialGroup()
                                .addComponent(txtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel4)
                                .addGap(18, 18, 18)
                                .addComponent(txtReferencia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pestanhaABMLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnConfirmar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pestanhaABMLayout.createSequentialGroup()
                        .addGroup(pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pestanhaABMLayout.createSequentialGroup()
                                .addComponent(rbActivo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rbInactivo))
                            .addComponent(txtCodigoAlfanumerico, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtCodigoBarra, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtObservacion, javax.swing.GroupLayout.DEFAULT_SIZE, 397, Short.MAX_VALUE)))
                    .addGroup(pestanhaABMLayout.createSequentialGroup()
                        .addGroup(pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(pestanhaABMLayout.createSequentialGroup()
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtCodigoSeccion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(pestanhaABMLayout.createSequentialGroup()
                                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtCodigoUnidadMedida, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(pestanhaABMLayout.createSequentialGroup()
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtCodigoMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pestanhaABMLayout.createSequentialGroup()
                                .addComponent(txtDescripcionUnidadMedida, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(pestanhaABMLayout.createSequentialGroup()
                                .addComponent(txtDescripcionMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(pestanhaABMLayout.createSequentialGroup()
                                .addComponent(txtDescripcionSeccion, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, 63, Short.MAX_VALUE)))
                        .addGroup(pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pestanhaABMLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtCodigoTipoArticulo, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtCodigoLinea, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pestanhaABMLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCodigoImpuesto, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtDescripcionImpuesto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtDescripcionTipoArticulo, javax.swing.GroupLayout.DEFAULT_SIZE, 306, Short.MAX_VALUE)
                            .addComponent(txtDescripcionLinea, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        pestanhaABMLayout.setVerticalGroup(
            pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pestanhaABMLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(txtReferencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtCodigoAlfanumerico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(txtCodigoBarra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbActivo)
                    .addComponent(rbInactivo)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8)
                    .addComponent(txtObservacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCodigoMarca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDescripcionMarca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(txtCodigoLinea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDescripcionLinea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCodigoSeccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDescripcionSeccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(txtCodigoTipoArticulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDescripcionTipoArticulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCodigoUnidadMedida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(txtCodigoImpuesto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDescripcionImpuesto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14)
                    .addComponent(txtDescripcionUnidadMedida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 139, Short.MAX_VALUE)
                .addGroup(pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnConfirmar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        txtCodigoSeccion.getAccessibleContext().setAccessibleName("");

        pestanha.addTab("Operaciónes", pestanhaABM);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pestanha)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pestanha)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtCriterioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCriterioActionPerformed
        cargar();
    }//GEN-LAST:event_txtCriterioActionPerformed

    private void txtCriterioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCriterioKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            evt.setKeyChar(Character.toUpperCase(c));
        }
        if (txtCriterio.getText().length() == 100) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCriterioKeyTyped

    private void txtDescripcionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescripcionKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            evt.setKeyChar(Character.toUpperCase(c));
        }
        if (txtDescripcion.getText().length() == 100) {
            evt.consume();
        }
    }//GEN-LAST:event_txtDescripcionKeyTyped

    private void txtDescripcionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDescripcionActionPerformed
        if (txtDescripcion.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "NO PUEDE DEJAR EL CAMPO VACIO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else {
            txtReferencia.grabFocus();
        }
    }//GEN-LAST:event_txtDescripcionActionPerformed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        limpiarCampos();
        operacion = "NUEVO";
        habilitarCampos(operacion);
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        operacion = "CANCELAR";
        habilitarCampos(operacion);
        limpiarCampos();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmarActionPerformed
        int res = JOptionPane.showConfirmDialog(null, "¿ESTA SEGURO DE CONFIRMAR LOS CAMBIOS?", "ADVERTENCIA", JOptionPane.YES_NO_OPTION);
        if (res != 1) {
            guardar(operacion);
            habilitarCampos("GUARDAR");
            limpiarCampos();
        }
    }//GEN-LAST:event_btnConfirmarActionPerformed

    private void ModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ModificarActionPerformed
        operacion = "MODIFICAR";
        recuperarDatos();
    }//GEN-LAST:event_ModificarActionPerformed

    private void EliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EliminarActionPerformed
        operacion = "ELIMINAR";
        recuperarDatos();
    }//GEN-LAST:event_EliminarActionPerformed

    private void txtReferenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtReferenciaActionPerformed
        txtCodigoAlfanumerico.grabFocus();
    }//GEN-LAST:event_txtReferenciaActionPerformed

    private void txtReferenciaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtReferenciaKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            evt.setKeyChar(Character.toUpperCase(c));
        }
        if (txtReferencia.getText().length() == 100) {
            evt.consume();
        }
    }//GEN-LAST:event_txtReferenciaKeyTyped

    private void txtCodigoAlfanumericoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoAlfanumericoActionPerformed
        if (txtCodigoAlfanumerico.getText().isEmpty()) {
            txtCodigoBarra.grabFocus();
        } else {
            //VALIDACIONES PARA QUE NO EXISTAN DOS CODIGOS ALFANUMÉRICOS IGUALES
            if (dao.verificarCodigoAlfanumerico(txtCodigoAlfanumerico.getText(), txtCodigo.getText()) == false) {
                txtCodigoBarra.grabFocus();
            }
        }
    }//GEN-LAST:event_txtCodigoAlfanumericoActionPerformed

    private void txtCodigoAlfanumericoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoAlfanumericoKeyTyped
        if (txtCodigoAlfanumerico.getText().length() == 50) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCodigoAlfanumericoKeyTyped

    private void txtCodigoBarraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoBarraActionPerformed
        if (txtCodigoBarra.getText().isEmpty()) {
            rbActivo.grabFocus();
        } else {
            //VALIDACIONES PARA QUE NO EXISTAN DOS CODIGOS ALFANUMÉRICOS IGUALES
            if (dao.verificarCodigoBarra(txtCodigoBarra.getText(), txtCodigo.getText()) == false) {
                rbActivo.grabFocus();
            }
        }
    }//GEN-LAST:event_txtCodigoBarraActionPerformed

    private void txtCodigoBarraKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoBarraKeyTyped
        if (txtCodigoBarra.getText().length() == 50) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCodigoBarraKeyTyped

    private void txtObservacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtObservacionActionPerformed
        txtCodigoMarca.grabFocus();
    }//GEN-LAST:event_txtObservacionActionPerformed

    private void txtObservacionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtObservacionKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            evt.setKeyChar(Character.toUpperCase(c));
        }
        if (txtObservacion.getText().length() == 250) {
            evt.consume();
        }
    }//GEN-LAST:event_txtObservacionKeyTyped

    private void txtCodigoMarcaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoMarcaActionPerformed
        if (txtCodigoMarca.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "NO PUEDE DEJAR EL CAMPO VACIO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else {
            int idmarca = Integer.parseInt(txtCodigoMarca.getText());
            m.setIdmarca(idmarca);
            boolean resultado = daoMarca.consultarDatos(m);
            if (resultado == true) {
                txtDescripcionMarca.setText(m.getDescripcion());
                txtCodigoLinea.grabFocus();
            } else {
                txtCodigoMarca.setText(null);
                txtDescripcionMarca.setText(null);
                txtCodigoMarca.grabFocus();
            }
        }
    }//GEN-LAST:event_txtCodigoMarcaActionPerformed

    private void txtCodigoMarcaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoMarcaKeyPressed
        if (evt.VK_F1 == evt.getKeyCode()) {
            buscarMarca();
        }
    }//GEN-LAST:event_txtCodigoMarcaKeyPressed

    private void txtCodigoMarcaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoMarcaKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            getToolkit().beep();
            evt.consume();
        }
        if (txtCodigoMarca.getText().length() == 10) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCodigoMarcaKeyTyped

    private void txtDescripcionMarcaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDescripcionMarcaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescripcionMarcaActionPerformed

    private void txtDescripcionMarcaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescripcionMarcaKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescripcionMarcaKeyTyped

    private void txtCodigoLineaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoLineaActionPerformed
        if (txtCodigoLinea.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "NO PUEDE DEJAR EL CAMPO VACIO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else {
            int idlinea = Integer.parseInt(txtCodigoLinea.getText());
            l.setIdlinea(idlinea);
            boolean resultado = daoLinea.consultarDatos(l);
            if (resultado == true) {
                txtDescripcionLinea.setText(l.getDescripcion());
                txtCodigoSeccion.grabFocus();
            } else {
                txtCodigoLinea.setText(null);
                txtDescripcionLinea.setText(null);
                txtCodigoLinea.grabFocus();
            }
        }
    }//GEN-LAST:event_txtCodigoLineaActionPerformed

    private void txtCodigoLineaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoLineaKeyPressed
        if (evt.VK_F1 == evt.getKeyCode()) {
            buscarLinea();
        }
    }//GEN-LAST:event_txtCodigoLineaKeyPressed

    private void txtCodigoLineaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoLineaKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            getToolkit().beep();
            evt.consume();
        }
        if (txtCodigoLinea.getText().length() == 10) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCodigoLineaKeyTyped

    private void txtDescripcionLineaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDescripcionLineaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescripcionLineaActionPerformed

    private void txtDescripcionLineaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescripcionLineaKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescripcionLineaKeyTyped

    private void txtCodigoSeccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoSeccionActionPerformed
        if (txtCodigoSeccion.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "NO PUEDE DEJAR EL CAMPO VACIO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else {
            int idlinea = Integer.parseInt(txtCodigoSeccion.getText());
            s.setIdseccion(idlinea);
            boolean resultado = daoSeccion.consultarDatos(s);
            if (resultado == true) {
                txtDescripcionSeccion.setText(s.getDescripcion());
                txtCodigoTipoArticulo.grabFocus();
            } else {
                txtCodigoSeccion.setText(null);
                txtDescripcionSeccion.setText(null);
                txtCodigoSeccion.grabFocus();
            }
        }
    }//GEN-LAST:event_txtCodigoSeccionActionPerformed

    private void txtCodigoSeccionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoSeccionKeyPressed
        if (evt.VK_F1 == evt.getKeyCode()) {
            buscarSeccion();
        }
    }//GEN-LAST:event_txtCodigoSeccionKeyPressed

    private void txtCodigoSeccionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoSeccionKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            getToolkit().beep();
            evt.consume();
        }
        if (txtCodigoSeccion.getText().length() == 10) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCodigoSeccionKeyTyped

    private void txtDescripcionSeccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDescripcionSeccionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescripcionSeccionActionPerformed

    private void txtDescripcionSeccionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescripcionSeccionKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescripcionSeccionKeyTyped

    private void txtCodigoTipoArticuloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoTipoArticuloActionPerformed
        if (txtCodigoTipoArticulo.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "NO PUEDE DEJAR EL CAMPO VACIO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else {
            int idtipo = Integer.parseInt(txtCodigoTipoArticulo.getText());
            ta.setIdtipo(idtipo);
            boolean resultado = daoTipoArticulo.consultarDatos(ta);
            if (resultado == true) {
                txtDescripcionTipoArticulo.setText(ta.getDescripcion());
                txtCodigoUnidadMedida.grabFocus();
            } else {
                txtCodigoTipoArticulo.setText(null);
                txtDescripcionTipoArticulo.setText(null);
                txtCodigoTipoArticulo.grabFocus();
            }
        }
    }//GEN-LAST:event_txtCodigoTipoArticuloActionPerformed

    private void txtCodigoTipoArticuloKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoTipoArticuloKeyPressed
        if (evt.VK_F1 == evt.getKeyCode()) {
            buscarTipoArticulo();
        }
    }//GEN-LAST:event_txtCodigoTipoArticuloKeyPressed

    private void txtCodigoTipoArticuloKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoTipoArticuloKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            getToolkit().beep();
            evt.consume();
        }
        if (txtCodigoTipoArticulo.getText().length() == 10) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCodigoTipoArticuloKeyTyped

    private void txtDescripcionTipoArticuloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDescripcionTipoArticuloActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescripcionTipoArticuloActionPerformed

    private void txtDescripcionTipoArticuloKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescripcionTipoArticuloKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescripcionTipoArticuloKeyTyped

    private void txtCodigoUnidadMedidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoUnidadMedidaActionPerformed
        if (txtCodigoUnidadMedida.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "NO PUEDE DEJAR EL CAMPO VACIO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else {
            int idunidadmedida = Integer.parseInt(txtCodigoUnidadMedida.getText());
            um.setIdunidad(idunidadmedida);
            boolean resultado = daoUnidadMedida.consultarDatos(um);
            if (resultado == true) {
                txtDescripcionUnidadMedida.setText(um.getDescripcion());
                txtCodigoImpuesto.grabFocus();
            } else {
                txtCodigoUnidadMedida.setText(null);
                txtDescripcionUnidadMedida.setText(null);
                txtCodigoUnidadMedida.grabFocus();
            }
        }
    }//GEN-LAST:event_txtCodigoUnidadMedidaActionPerformed

    private void txtCodigoUnidadMedidaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoUnidadMedidaKeyPressed
        if (evt.VK_F1 == evt.getKeyCode()) {
            buscarUnidadMedida();
        }
    }//GEN-LAST:event_txtCodigoUnidadMedidaKeyPressed

    private void txtCodigoUnidadMedidaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoUnidadMedidaKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            getToolkit().beep();
            evt.consume();
        }
        if (txtCodigoUnidadMedida.getText().length() == 10) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCodigoUnidadMedidaKeyTyped

    private void txtCodigoImpuestoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoImpuestoActionPerformed
        if (txtCodigoImpuesto.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "NO PUEDE DEJAR EL CAMPO VACIO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else {
            int idimpuesto = Integer.parseInt(txtCodigoImpuesto.getText());
            i.setIdimpuesto(idimpuesto);
            boolean resultado = daoImpuesto.consultarDatos(i);
            if (resultado == true) {
                txtDescripcionImpuesto.setText(i.getDescripcion());
                btnConfirmar.grabFocus();
            } else {
                txtCodigoImpuesto.setText(null);
                txtDescripcionImpuesto.setText(null);
                txtCodigoImpuesto.grabFocus();
            }
        }
    }//GEN-LAST:event_txtCodigoImpuestoActionPerformed

    private void txtCodigoImpuestoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoImpuestoKeyPressed
        if (evt.VK_F1 == evt.getKeyCode()) {
            buscarImpuesto();
        }
    }//GEN-LAST:event_txtCodigoImpuestoKeyPressed

    private void txtCodigoImpuestoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoImpuestoKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            getToolkit().beep();
            evt.consume();
        }
        if (txtCodigoImpuesto.getText().length() == 10) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCodigoImpuestoKeyTyped

    private void txtDescripcionImpuestoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDescripcionImpuestoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescripcionImpuestoActionPerformed

    private void txtDescripcionImpuestoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescripcionImpuestoKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescripcionImpuestoKeyTyped

    private void txtDescripcionUnidadMedidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDescripcionUnidadMedidaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescripcionUnidadMedidaActionPerformed

    private void txtDescripcionUnidadMedidaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescripcionUnidadMedidaKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescripcionUnidadMedidaKeyTyped

    private void rbActivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbActivoActionPerformed
        txtObservacion.grabFocus();
    }//GEN-LAST:event_rbActivoActionPerformed

    private void rbInactivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbInactivoActionPerformed
        txtObservacion.grabFocus();
    }//GEN-LAST:event_rbInactivoActionPerformed

    private void txtCriterioMarcaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCriterioMarcaActionPerformed
        cargarMarca();
    }//GEN-LAST:event_txtCriterioMarcaActionPerformed

    private void txtCriterioMarcaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCriterioMarcaKeyPressed
        if (evt.VK_ESCAPE == evt.getKeyCode()) {
            txtCodigoMarca.setText(null);
            txtDescripcionMarca.setText(null);
            txtCodigoMarca.grabFocus();
            BuscadorMarca.dispose();
        }
    }//GEN-LAST:event_txtCriterioMarcaKeyPressed

    private void txtCriterioMarcaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCriterioMarcaKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            evt.setKeyChar(Character.toUpperCase(c));
        }
        if (txtCriterioMarca.getText().length() == 100) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCriterioMarcaKeyTyped

    private void tablaDatosMarcaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaDatosMarcaMouseClicked
        if (evt.getClickCount() == 2) {
            if (tablaDatosMarca.getSelectedRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "SELECCIONE UNA FILA");
            } else {
                txtCriterioMarca.setText(null);
                BuscadorMarca.dispose();
            }
        }
    }//GEN-LAST:event_tablaDatosMarcaMouseClicked

    private void rbActivoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rbActivoKeyPressed
        txtObservacion.grabFocus();
    }//GEN-LAST:event_rbActivoKeyPressed

    private void rbInactivoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rbInactivoKeyPressed
        txtObservacion.grabFocus();
    }//GEN-LAST:event_rbInactivoKeyPressed

    private void txtCriterioLineaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCriterioLineaActionPerformed
        cargarLinea();
    }//GEN-LAST:event_txtCriterioLineaActionPerformed

    private void txtCriterioLineaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCriterioLineaKeyPressed
        if (evt.VK_ESCAPE == evt.getKeyCode()) {
            txtCodigoLinea.setText(null);
            txtDescripcionLinea.setText(null);
            txtCodigoLinea.grabFocus();
            BuscadorLinea.dispose();
        }
    }//GEN-LAST:event_txtCriterioLineaKeyPressed

    private void txtCriterioLineaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCriterioLineaKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            evt.setKeyChar(Character.toUpperCase(c));
        }
        if (txtCriterioLinea.getText().length() == 100) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCriterioLineaKeyTyped

    private void tablaDatosLineaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaDatosLineaMouseClicked
        if (evt.getClickCount() == 2) {
            if (tablaDatosLinea.getSelectedRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "SELECCIONE UNA FILA");
            } else {
                txtCriterioLinea.setText(null);
                BuscadorLinea.dispose();
            }
        }
    }//GEN-LAST:event_tablaDatosLineaMouseClicked

    private void txtCriterioSeccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCriterioSeccionActionPerformed
        cargarSeccion();
    }//GEN-LAST:event_txtCriterioSeccionActionPerformed

    private void txtCriterioSeccionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCriterioSeccionKeyPressed
        if (evt.VK_ESCAPE == evt.getKeyCode()) {
            txtCodigoSeccion.setText(null);
            txtDescripcionSeccion.setText(null);
            txtCodigoSeccion.grabFocus();
            BuscadorSeccion.dispose();
        }
    }//GEN-LAST:event_txtCriterioSeccionKeyPressed

    private void txtCriterioSeccionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCriterioSeccionKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            evt.setKeyChar(Character.toUpperCase(c));
        }
        if (txtCriterioSeccion.getText().length() == 100) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCriterioSeccionKeyTyped

    private void tablaDatosSeccionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaDatosSeccionMouseClicked
        if (evt.getClickCount() == 2) {
            if (tablaDatosSeccion.getSelectedRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "SELECCIONE UNA FILA");
            } else {
                txtCriterioSeccion.setText(null);
                BuscadorSeccion.dispose();
            }
        }
    }//GEN-LAST:event_tablaDatosSeccionMouseClicked

    private void txtCriterioTipoArticuloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCriterioTipoArticuloActionPerformed
        buscarTipoArticulo();
    }//GEN-LAST:event_txtCriterioTipoArticuloActionPerformed

    private void txtCriterioTipoArticuloKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCriterioTipoArticuloKeyPressed
        if (evt.VK_ESCAPE == evt.getKeyCode()) {
            txtCodigoTipoArticulo.setText(null);
            txtDescripcionTipoArticulo.setText(null);
            txtCodigoTipoArticulo.grabFocus();
            BuscadorTipoArticulo.dispose();
        }
    }//GEN-LAST:event_txtCriterioTipoArticuloKeyPressed

    private void txtCriterioTipoArticuloKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCriterioTipoArticuloKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            evt.setKeyChar(Character.toUpperCase(c));
        }
        if (txtCriterioTipoArticulo.getText().length() == 100) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCriterioTipoArticuloKeyTyped

    private void tablaDatosTipoArticuloMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaDatosTipoArticuloMouseClicked
        if (evt.getClickCount() == 2) {
            if (tablaDatosTipoArticulo.getSelectedRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "SELECCIONE UNA FILA");
            } else {
                txtCriterioTipoArticulo.setText(null);
                BuscadorTipoArticulo.dispose();
            }
        }
    }//GEN-LAST:event_tablaDatosTipoArticuloMouseClicked

    private void txtCriterioUnidadMedidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCriterioUnidadMedidaActionPerformed
        buscarUnidadMedida();
    }//GEN-LAST:event_txtCriterioUnidadMedidaActionPerformed

    private void txtCriterioUnidadMedidaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCriterioUnidadMedidaKeyPressed
        if (evt.VK_ESCAPE == evt.getKeyCode()) {
            txtCodigoUnidadMedida.setText(null);
            txtDescripcionUnidadMedida.setText(null);
            txtCodigoUnidadMedida.grabFocus();
            BuscadorUnidadMedida.dispose();
        }
    }//GEN-LAST:event_txtCriterioUnidadMedidaKeyPressed

    private void txtCriterioUnidadMedidaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCriterioUnidadMedidaKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            evt.setKeyChar(Character.toUpperCase(c));
        }
        if (txtCriterioUnidadMedida.getText().length() == 100) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCriterioUnidadMedidaKeyTyped

    private void tablaDatosUnidadMedidaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaDatosUnidadMedidaMouseClicked
        if (evt.getClickCount() == 2) {
            if (tablaDatosUnidadMedida.getSelectedRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "SELECCIONE UNA FILA");
            } else {
                txtCriterioUnidadMedida.setText(null);
                BuscadorUnidadMedida.dispose();
            }
        }
    }//GEN-LAST:event_tablaDatosUnidadMedidaMouseClicked

    private void txtCriterioImpuestoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCriterioImpuestoActionPerformed
        buscarImpuesto();
    }//GEN-LAST:event_txtCriterioImpuestoActionPerformed

    private void txtCriterioImpuestoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCriterioImpuestoKeyPressed
        if (evt.VK_ESCAPE == evt.getKeyCode()) {
            txtCodigoImpuesto.setText(null);
            txtDescripcionImpuesto.setText(null);
            txtCodigoImpuesto.grabFocus();
            BuscadorImpuesto.dispose();
        }
    }//GEN-LAST:event_txtCriterioImpuestoKeyPressed

    private void txtCriterioImpuestoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCriterioImpuestoKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            evt.setKeyChar(Character.toUpperCase(c));
        }
        if (txtCriterioImpuesto.getText().length() == 100) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCriterioImpuestoKeyTyped

    private void tablaDatosImpuestoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaDatosImpuestoMouseClicked
        if (evt.getClickCount() == 2) {
            if (tablaDatosImpuesto.getSelectedRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "SELECCIONE UNA FILA");
            } else {
                txtCriterioImpuesto.setText(null);
                BuscadorImpuesto.dispose();
            }
        }
    }//GEN-LAST:event_tablaDatosImpuestoMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDialog BuscadorImpuesto;
    private javax.swing.JDialog BuscadorLinea;
    private javax.swing.JDialog BuscadorMarca;
    private javax.swing.JDialog BuscadorSeccion;
    private javax.swing.JDialog BuscadorTipoArticulo;
    private javax.swing.JDialog BuscadorUnidadMedida;
    private javax.swing.JMenuItem Eliminar;
    private javax.swing.JMenuItem Modificar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnConfirmar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.ButtonGroup grupoEstado;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JPopupMenu menuDesplegable;
    private javax.swing.JTabbedPane pestanha;
    private javax.swing.JPanel pestanhaABM;
    private javax.swing.JPanel pestanhaLista;
    private javax.swing.JRadioButton rbActivo;
    private javax.swing.JRadioButton rbInactivo;
    private javax.swing.JTable tablaDatos;
    private javax.swing.JTable tablaDatosImpuesto;
    private javax.swing.JTable tablaDatosLinea;
    private javax.swing.JTable tablaDatosMarca;
    private javax.swing.JTable tablaDatosSeccion;
    private javax.swing.JTable tablaDatosTipoArticulo;
    private javax.swing.JTable tablaDatosUnidadMedida;
    private org.jdesktop.swingx.JXTextField txtCodigo;
    private org.jdesktop.swingx.JXTextField txtCodigoAlfanumerico;
    private org.jdesktop.swingx.JXTextField txtCodigoBarra;
    private org.jdesktop.swingx.JXTextField txtCodigoImpuesto;
    private org.jdesktop.swingx.JXTextField txtCodigoLinea;
    private org.jdesktop.swingx.JXTextField txtCodigoMarca;
    private org.jdesktop.swingx.JXTextField txtCodigoSeccion;
    private org.jdesktop.swingx.JXTextField txtCodigoTipoArticulo;
    private org.jdesktop.swingx.JXTextField txtCodigoUnidadMedida;
    private org.jdesktop.swingx.JXTextField txtCriterio;
    private org.jdesktop.swingx.JXTextField txtCriterioImpuesto;
    private org.jdesktop.swingx.JXTextField txtCriterioLinea;
    private org.jdesktop.swingx.JXTextField txtCriterioMarca;
    private org.jdesktop.swingx.JXTextField txtCriterioSeccion;
    private org.jdesktop.swingx.JXTextField txtCriterioTipoArticulo;
    private org.jdesktop.swingx.JXTextField txtCriterioUnidadMedida;
    private org.jdesktop.swingx.JXTextField txtDescripcion;
    private org.jdesktop.swingx.JXTextField txtDescripcionImpuesto;
    private org.jdesktop.swingx.JXTextField txtDescripcionLinea;
    private org.jdesktop.swingx.JXTextField txtDescripcionMarca;
    private org.jdesktop.swingx.JXTextField txtDescripcionSeccion;
    private org.jdesktop.swingx.JXTextField txtDescripcionTipoArticulo;
    private org.jdesktop.swingx.JXTextField txtDescripcionUnidadMedida;
    private org.jdesktop.swingx.JXTextField txtObservacion;
    private org.jdesktop.swingx.JXTextField txtReferencia;
    // End of variables declaration//GEN-END:variables
}
