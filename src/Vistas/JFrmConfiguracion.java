package Vistas;

import Dao.DAOConfiguracion;
import Dao.DAOSucursal;
import Dao.DAOTipoArticulo;
import Dao.DAOTipoMovimiento;
import Modelos.Configuracion;
import Modelos.Sucursal;
import Modelos.TipoArticulo;
import Modelos.TipoMovimiento;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author armando
 */
public class JFrmConfiguracion extends javax.swing.JInternalFrame {

    Configuracion c = new Configuracion();
    Sucursal s = new Sucursal();
    TipoMovimiento tm = new TipoMovimiento();
    TipoArticulo ta = new TipoArticulo();

    DAOConfiguracion dao = new DAOConfiguracion();
    DAOSucursal daoSucursal = new DAOSucursal();
    DAOTipoMovimiento daoTipoMovimiento = new DAOTipoMovimiento();
    DAOTipoArticulo daoTipoArticulo = new DAOTipoArticulo();

    ArrayList<Object[]> datos = new ArrayList<>();
    ArrayList<Object[]> datosSucursal = new ArrayList<>();
    ArrayList<Object[]> datosTipoMovimiento = new ArrayList<>();
    ArrayList<Object[]> datosTipoArticulo = new ArrayList<>();

    //VARIABLE QUE MANEJA QUE TIPOS DE OPERACIONES SE REALIZARAN: SI VA A SER ALTA, BAJA O MODIFICACION DEL REGISTRO
    String operacion = "";

    /**
     * Creates new form JFrmConfiguracion
     */
    public JFrmConfiguracion() {
        setTitle("JFrmConfiguracion");
        initComponents();
        cargar();
    }

    public void cargar() {
        DefaultTableModel modelo = (DefaultTableModel) tablaDatos.getModel();
        modelo.setRowCount(0);
        datos = dao.consultar(txtCriterio.getText());
        for (Object[] obj : datos) {
            modelo.addRow(obj);
        }
        this.tablaDatos.setModel(modelo);
    }

    public void cargarSucursal() {
        DefaultTableModel modelo = (DefaultTableModel) tablaDatosSucursal.getModel();
        modelo.setRowCount(0);
        datosSucursal = daoSucursal.consultar(txtCriterioSucursal.getText());
        for (Object[] obj : datosSucursal) {
            modelo.addRow(obj);
        }
        this.tablaDatosSucursal.setModel(modelo);
    }

    public void cargarTipoMovimiento() {
        DefaultTableModel modelo = (DefaultTableModel) tablaDatosTipoMovimiento.getModel();
        modelo.setRowCount(0);
        datosTipoMovimiento = daoTipoMovimiento.consultar(txtCriterioTipoMovimiento.getText());
        for (Object[] obj : datosTipoMovimiento) {
            modelo.addRow(obj);
        }
        this.tablaDatosTipoMovimiento.setModel(modelo);
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

    public void habilitarCampos(String accion) {
        switch (accion) {
            case "NUEVO":
                //CAMPOS
                txtCodigo.setEnabled(false);
                txtCodigoSucursal.setEnabled(true);
                txtCodigoTipoMovimiento.setEnabled(true);
                txtCodigoTipoMovimientoCCRER.setEnabled(true);
                txtCodigoTipoMovimientoRPAGR.setEnabled(true);
                txtCodigoTipoMovimientoFCONE.setEnabled(true);
                txtCodigoTipoMovimientoFCREE.setEnabled(true);
                rbPermitir.setEnabled(true);
                rbRestringir.setEnabled(true);
                txtCodigoTipoArticulo.setEnabled(true);
                //BOTONES
                btnNuevo.setEnabled(false);
                btnConfirmar.setEnabled(true);
                btnCancelar.setEnabled(true);
                //REDIRECIONAMOS
                txtCodigoSucursal.grabFocus();
                break;
            case "MODIFICAR":
                //CAMPOS
                txtCodigo.setEnabled(false);
                txtCodigoSucursal.setEnabled(false);
                txtCodigoTipoMovimiento.setEnabled(true);
                txtCodigoTipoMovimientoCCRER.setEnabled(true);
                txtCodigoTipoMovimientoRPAGR.setEnabled(true);
                txtCodigoTipoMovimientoFCONE.setEnabled(true);
                txtCodigoTipoMovimientoFCREE.setEnabled(true);
                rbPermitir.setEnabled(true);
                rbRestringir.setEnabled(true);
                txtCodigoTipoArticulo.setEnabled(true);
                //BOTONES
                btnNuevo.setEnabled(false);
                btnConfirmar.setEnabled(true);
                btnCancelar.setEnabled(true);
                //REDIRECIONAMOS
                pestanha.setSelectedIndex(1);
                txtCodigoTipoMovimiento.grabFocus();
                break;
            case "ELIMINAR":
                //CAMPOS
                txtCodigo.setEnabled(false);
                txtCodigoSucursal.setEnabled(false);
                txtCodigoTipoMovimiento.setEnabled(false);
                txtCodigoTipoMovimientoCCRER.setEnabled(false);
                txtCodigoTipoMovimientoRPAGR.setEnabled(false);
                txtCodigoTipoMovimientoFCONE.setEnabled(false);
                txtCodigoTipoMovimientoFCREE.setEnabled(false);
                rbPermitir.setEnabled(false);
                rbRestringir.setEnabled(false);
                txtCodigoTipoArticulo.setEnabled(false);
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
                txtCodigoSucursal.setEnabled(false);
                txtCodigoTipoMovimiento.setEnabled(false);
                txtCodigoTipoMovimientoCCRER.setEnabled(false);
                txtCodigoTipoMovimientoRPAGR.setEnabled(false);
                txtCodigoTipoMovimientoFCONE.setEnabled(false);
                txtCodigoTipoMovimientoFCREE.setEnabled(false);
                rbPermitir.setEnabled(false);
                rbRestringir.setEnabled(false);
                txtCodigoTipoArticulo.setEnabled(false);
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
                txtCodigoSucursal.setEnabled(false);
                txtCodigoTipoMovimiento.setEnabled(false);
                txtCodigoTipoMovimientoCCRER.setEnabled(false);
                txtCodigoTipoMovimientoRPAGR.setEnabled(false);
                txtCodigoTipoMovimientoFCONE.setEnabled(false);
                txtCodigoTipoMovimientoFCREE.setEnabled(false);
                rbPermitir.setEnabled(false);
                rbRestringir.setEnabled(false);
                txtCodigoTipoArticulo.setEnabled(false);
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
        txtCodigoSucursal.setText(null);
        txtDescripcionSucursal.setText(null);
        txtCodigoTipoMovimiento.setText(null);
        txtCodigoTipoMovimientoCCRER.setText(null);
        txtCodigoTipoMovimientoRPAGR.setText(null);
        txtCodigoTipoMovimientoFCONE.setText(null);
        txtCodigoTipoMovimientoFCREE.setText(null);
        txtCodigoTipoArticulo.setText(null);
        txtDescripcionTipoMovimiento.setText(null);
        txtDescripcionTipoMovimientoCCRER.setText(null);
        txtDescripcionTipoMovimientoRPAGR.setText(null);
        txtDescripcionTipoMovimientoFCONE.setText(null);
        txtDescripcionTipoMovimientoFCREE.setText(null);
        txtDescripcionTipoArticulo.setText(null);
        rbPermitir.setSelected(true);
        
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
        int idsucursal = Integer.parseInt(txtCodigoSucursal.getText());
        int cconr = Integer.parseInt(txtCodigoTipoMovimiento.getText());
        int ccrer = Integer.parseInt(txtCodigoTipoMovimientoCCRER.getText());
        int rpagr = Integer.parseInt(txtCodigoTipoMovimientoRPAGR.getText());
        int fcone = Integer.parseInt(txtCodigoTipoMovimientoFCONE.getText());
        int fcree = Integer.parseInt(txtCodigoTipoMovimientoFCREE.getText());
        String estado;
        if (rbPermitir.isSelected()) {
            estado = "S";
        } else {
            estado = "N";
        }
        int articulo_tipo_servicio = Integer.parseInt(txtCodigoTipoArticulo.getText());
        switch (accion) {
            case "NUEVO":
                if (idsucursal == 0) {
                    error += "NO PUEDE DEJAR EL CAMPO DE SUCURSAL VACIO.\n";
                }
                if (cconr == 0) {
                    error += "NO PUEDE DEJAR EL CAMPO DE COMPRA CONTADO RECIBIDA VACIO.\n";
                }
                if (ccrer == 0) {
                    error += "NO PUEDE DEJAR EL CAMPO DE COMPRA CRÉDITO RECIBIDA VACIO.\n";
                }
                if (rpagr == 0) {
                    error += "NO PUEDE DEJAR EL CAMPO DE RECIBO DE PAGO RECIBIDA VACIO.\n";
                }
                if (fcone == 0) {
                    error += "NO PUEDE DEJAR EL CAMPO DE VENTA CONTADO EMITIDA VACIO.\n";
                }
                if (fcree == 0) {
                    error += "NO PUEDE DEJAR EL CAMPO DE VENTA CREDITO EMITIDA VACIO.\n";
                }
                if (articulo_tipo_servicio == 0) {
                    error += "NO PUEDE DEJAR EL CAMPO DE ARTICULO TIPO SERVICIO VACIO.\n";
                }
                if (error.isEmpty()) {
                    c.setIdconfiguracion(id);
                    c.setIdsucursal(idsucursal);
                    c.setFac_con_rec(cconr);
                    c.setFac_cre_rec(ccrer);
                    c.setRec_pag_rec(rpagr);
                    c.setFac_con_emi(fcone);
                    c.setFac_cre_emi(fcree);
                    c.setPermitir_venta_negativa(estado);
                    c.setArticulo_tipo_servicio(articulo_tipo_servicio);
                    dao.agregar(c);
                    cargar();
                } else {
                    JOptionPane.showMessageDialog(null, error, "ERRORES", JOptionPane.ERROR_MESSAGE);
                }
                break;
            case "MODIFICAR":
                if (idsucursal == 0) {
                    error += "NO PUEDE DEJAR EL CAMPO DE SUCURSAL VACIO.\n";
                }
                if (cconr == 0) {
                    error += "NO PUEDE DEJAR EL CAMPO DE COMPRA CONTADO RECIBIDA VACIO.\n";
                }
                if (ccrer == 0) {
                    error += "NO PUEDE DEJAR EL CAMPO DE COMPRA CRÉDITO RECIBIDA VACIO.\n";
                }
                if (rpagr == 0) {
                    error += "NO PUEDE DEJAR EL CAMPO DE RECIBO DE PAGO RECIBIDA VACIO.\n";
                }
                if (fcone == 0) {
                    error += "NO PUEDE DEJAR EL CAMPO DE VENTA CONTADO EMITIDA VACIO.\n";
                }
                if (fcree == 0) {
                    error += "NO PUEDE DEJAR EL CAMPO DE VENTA CREDITO EMITIDA VACIO.\n";
                }
                if (articulo_tipo_servicio == 0) {
                    error += "NO PUEDE DEJAR EL CAMPO DE ARTICULO TIPO SERVICIO VACIO.\n";
                }
                if (error.isEmpty()) {
                    c.setIdconfiguracion(id);
                    c.setIdsucursal(idsucursal);
                    c.setFac_con_rec(cconr);
                    c.setFac_cre_rec(ccrer);
                    c.setRec_pag_rec(rpagr);
                    c.setFac_con_emi(fcone);
                    c.setFac_cre_emi(fcree);
                    c.setPermitir_venta_negativa(estado);
                    c.setArticulo_tipo_servicio(articulo_tipo_servicio);
                    dao.modificar(c);
                    cargar();
                } else {
                    JOptionPane.showMessageDialog(null, error, "ERRORES", JOptionPane.ERROR_MESSAGE);
                }
                break;
            case "ELIMINAR":
                if (error.isEmpty()) {
                    c.setIdconfiguracion(id);
                    dao.eliminar(c);
                    cargar();
                }
                break;
            default:
                JOptionPane.showMessageDialog(null, "HA OCURRIDO UN ERROR EN LA OPERACION PARA LA BASE DE DATOS. AVISE AL ADMINISTRADOR", "ERROR", JOptionPane.ERROR_MESSAGE);
        }

    }

    public void recuperarDatos() {
        int fila = tablaDatos.getSelectedRow();
        if (fila >= 0) {
            int id = Integer.parseInt(tablaDatos.getValueAt(fila, 0).toString());
            int idsucursal = Integer.parseInt(tablaDatos.getValueAt(fila, 1).toString());
            c.setIdconfiguracion(id);
            c.setIdsucursal(idsucursal);
            dao.consultarDatos(c);
            txtCodigo.setText("" + c.getIdconfiguracion());
            s.setIdsucursal(c.getIdsucursal());
            daoSucursal.consultarDatos(s);
            txtCodigoSucursal.setText("" + s.getIdsucursal());
            txtDescripcionSucursal.setText("" + s.getDescripcion());

            tm.setIdtipomovimiento(c.getFac_con_rec());
            daoTipoMovimiento.consultarDatos(tm);
            txtCodigoTipoMovimiento.setText("" + tm.getIdtipomovimiento());
            txtDescripcionTipoMovimiento.setText(tm.getDescripcion());

            tm.setIdtipomovimiento(c.getFac_cre_rec());
            daoTipoMovimiento.consultarDatos(tm);
            txtCodigoTipoMovimientoCCRER.setText("" + tm.getIdtipomovimiento());
            txtDescripcionTipoMovimientoCCRER.setText(tm.getDescripcion());

            tm.setIdtipomovimiento(c.getRec_pag_rec());
            daoTipoMovimiento.consultarDatos(tm);
            txtCodigoTipoMovimientoRPAGR.setText("" + tm.getIdtipomovimiento());
            txtDescripcionTipoMovimientoRPAGR.setText(tm.getDescripcion());

            tm.setIdtipomovimiento(c.getFac_con_emi());
            daoTipoMovimiento.consultarDatos(tm);
            txtCodigoTipoMovimientoFCONE.setText("" + tm.getIdtipomovimiento());
            txtDescripcionTipoMovimientoFCONE.setText(tm.getDescripcion());

            tm.setIdtipomovimiento(c.getFac_cre_emi());
            daoTipoMovimiento.consultarDatos(tm);
            txtCodigoTipoMovimientoFCREE.setText("" + tm.getIdtipomovimiento());
            txtDescripcionTipoMovimientoFCREE.setText(tm.getDescripcion());

            String estado = c.getPermitir_venta_negativa();
            if (estado.equals("S")) {
                rbPermitir.setSelected(true);
            } else {
                rbRestringir.setSelected(true);
            }
            
            ta.setIdtipo(c.getArticulo_tipo_servicio());
            daoTipoArticulo.consultarDatos(ta);
            txtCodigoTipoArticulo.setText("" + ta.getIdtipo());
            txtDescripcionTipoArticulo.setText(ta.getDescripcion());
            
            habilitarCampos(operacion);
        } else {
            JOptionPane.showMessageDialog(null, "SELECCIONE UNA FILA", "ADVERTENCIA", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void buscarSucursal() {
        cargarSucursal();
        BuscadorSucursal.setModal(true);
        BuscadorSucursal.setSize(540, 285);
        BuscadorSucursal.setLocationRelativeTo(this);
        BuscadorSucursal.setVisible(true);
        int fila = tablaDatosSucursal.getSelectedRow();
        if (fila >= 0) {
            txtCodigoSucursal.setText(tablaDatosSucursal.getValueAt(fila, 0).toString());
            txtDescripcionSucursal.setText(tablaDatosSucursal.getValueAt(fila, 1).toString());
        } else {
            txtCodigoSucursal.setText(null);
            txtDescripcionSucursal.setText(null);
        }
    }

    private void buscarTipoMovimientoCCONR() {
        cargarTipoMovimiento();
        BuscadorTipoMovimiento.setModal(true);
        BuscadorTipoMovimiento.setSize(540, 285);
        BuscadorTipoMovimiento.setLocationRelativeTo(this);
        BuscadorTipoMovimiento.setVisible(true);
        int fila = tablaDatosTipoMovimiento.getSelectedRow();
        if (fila >= 0) {
            txtCodigoTipoMovimiento.setText(tablaDatosTipoMovimiento.getValueAt(fila, 0).toString());
            txtDescripcionTipoMovimiento.setText(tablaDatosTipoMovimiento.getValueAt(fila, 1).toString());
        } else {
            txtCodigoTipoMovimiento.setText(null);
            txtDescripcionTipoMovimiento.setText(null);
        }
    }

    private void buscarTipoMovimientoCCRER() {
        cargarTipoMovimiento();
        BuscadorTipoMovimiento.setModal(true);
        BuscadorTipoMovimiento.setSize(540, 285);
        BuscadorTipoMovimiento.setLocationRelativeTo(this);
        BuscadorTipoMovimiento.setVisible(true);
        int fila = tablaDatosTipoMovimiento.getSelectedRow();
        if (fila >= 0) {
            txtCodigoTipoMovimientoCCRER.setText(tablaDatosTipoMovimiento.getValueAt(fila, 0).toString());
            txtDescripcionTipoMovimientoCCRER.setText(tablaDatosTipoMovimiento.getValueAt(fila, 1).toString());
        } else {
            txtCodigoTipoMovimientoCCRER.setText(null);
            txtDescripcionTipoMovimientoCCRER.setText(null);
        }
    }

    private void buscarTipoMovimientoRPAGR() {
        cargarTipoMovimiento();
        BuscadorTipoMovimiento.setModal(true);
        BuscadorTipoMovimiento.setSize(540, 285);
        BuscadorTipoMovimiento.setLocationRelativeTo(this);
        BuscadorTipoMovimiento.setVisible(true);
        int fila = tablaDatosTipoMovimiento.getSelectedRow();
        if (fila >= 0) {
            txtCodigoTipoMovimientoRPAGR.setText(tablaDatosTipoMovimiento.getValueAt(fila, 0).toString());
            txtDescripcionTipoMovimientoRPAGR.setText(tablaDatosTipoMovimiento.getValueAt(fila, 1).toString());
        } else {
            txtCodigoTipoMovimientoRPAGR.setText(null);
            txtDescripcionTipoMovimientoRPAGR.setText(null);
        }
    }

    private void buscarTipoMovimientoFCONE() {
        cargarTipoMovimiento();
        BuscadorTipoMovimiento.setModal(true);
        BuscadorTipoMovimiento.setSize(540, 285);
        BuscadorTipoMovimiento.setLocationRelativeTo(this);
        BuscadorTipoMovimiento.setVisible(true);
        int fila = tablaDatosTipoMovimiento.getSelectedRow();
        if (fila >= 0) {
            txtCodigoTipoMovimientoFCONE.setText(tablaDatosTipoMovimiento.getValueAt(fila, 0).toString());
            txtDescripcionTipoMovimientoFCONE.setText(tablaDatosTipoMovimiento.getValueAt(fila, 1).toString());
        } else {
            txtCodigoTipoMovimientoFCONE.setText(null);
            txtDescripcionTipoMovimientoFCONE.setText(null);
        }
    }

    private void buscarTipoMovimientoFCREE() {
        cargarTipoMovimiento();
        BuscadorTipoMovimiento.setModal(true);
        BuscadorTipoMovimiento.setSize(540, 285);
        BuscadorTipoMovimiento.setLocationRelativeTo(this);
        BuscadorTipoMovimiento.setVisible(true);
        int fila = tablaDatosTipoMovimiento.getSelectedRow();
        if (fila >= 0) {
            txtCodigoTipoMovimientoFCREE.setText(tablaDatosTipoMovimiento.getValueAt(fila, 0).toString());
            txtDescripcionTipoMovimientoFCREE.setText(tablaDatosTipoMovimiento.getValueAt(fila, 1).toString());
        } else {
            txtCodigoTipoMovimientoFCREE.setText(null);
            txtDescripcionTipoMovimientoFCREE.setText(null);
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
        BuscadorSucursal = new javax.swing.JDialog();
        jPanel4 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        txtCriterioSucursal = new org.jdesktop.swingx.JXTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaDatosSucursal = new javax.swing.JTable();
        BuscadorTipoMovimiento = new javax.swing.JDialog();
        jPanel3 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txtCriterioTipoMovimiento = new org.jdesktop.swingx.JXTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaDatosTipoMovimiento = new javax.swing.JTable();
        grupoFacturacion = new javax.swing.ButtonGroup();
        BuscadorTipoArticulo = new javax.swing.JDialog();
        jPanel6 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        txtCriterioTipoArticulo = new org.jdesktop.swingx.JXTextField();
        jScrollPane5 = new javax.swing.JScrollPane();
        tablaDatosTipoArticulo = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        pestanha = new javax.swing.JTabbedPane();
        pestanhaLista = new javax.swing.JPanel();
        txtCriterio = new org.jdesktop.swingx.JXTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaDatos = new javax.swing.JTable();
        pestanhaABM = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtCodigo = new org.jdesktop.swingx.JXTextField();
        btnCancelar = new javax.swing.JButton();
        btnConfirmar = new javax.swing.JButton();
        btnNuevo = new javax.swing.JButton();
        txtCodigoSucursal = new org.jdesktop.swingx.JXTextField();
        txtDescripcionSucursal = new org.jdesktop.swingx.JXTextField();
        jLabel12 = new javax.swing.JLabel();
        txtCodigoTipoMovimiento = new org.jdesktop.swingx.JXTextField();
        txtDescripcionTipoMovimiento = new org.jdesktop.swingx.JXTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtCodigoTipoMovimientoCCRER = new org.jdesktop.swingx.JXTextField();
        txtDescripcionTipoMovimientoCCRER = new org.jdesktop.swingx.JXTextField();
        jLabel7 = new javax.swing.JLabel();
        txtCodigoTipoMovimientoRPAGR = new org.jdesktop.swingx.JXTextField();
        txtDescripcionTipoMovimientoRPAGR = new org.jdesktop.swingx.JXTextField();
        jLabel8 = new javax.swing.JLabel();
        txtCodigoTipoMovimientoFCONE = new org.jdesktop.swingx.JXTextField();
        txtDescripcionTipoMovimientoFCONE = new org.jdesktop.swingx.JXTextField();
        jLabel9 = new javax.swing.JLabel();
        txtCodigoTipoMovimientoFCREE = new org.jdesktop.swingx.JXTextField();
        txtDescripcionTipoMovimientoFCREE = new org.jdesktop.swingx.JXTextField();
        jLabel10 = new javax.swing.JLabel();
        rbPermitir = new javax.swing.JRadioButton();
        rbRestringir = new javax.swing.JRadioButton();
        jLabel11 = new javax.swing.JLabel();
        txtCodigoTipoArticulo = new org.jdesktop.swingx.JXTextField();
        txtDescripcionTipoArticulo = new org.jdesktop.swingx.JXTextField();

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

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jLabel14.setBackground(new java.awt.Color(50, 104, 151));
        jLabel14.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("BUSCADOR DE SUCURSALES");
        jLabel14.setOpaque(true);

        txtCriterioSucursal.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtCriterioSucursal.setPrompt("Aqui puede ingresar los filtros para la busqueda..");
        txtCriterioSucursal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCriterioSucursalActionPerformed(evt);
            }
        });
        txtCriterioSucursal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCriterioSucursalKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCriterioSucursalKeyTyped(evt);
            }
        });

        tablaDatosSucursal.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        tablaDatosSucursal.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "<html><p style=\"text-align:center\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Código</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Descripción</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Telefono</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Direccion</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Codigo Empresa</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Empresa</span></span></span></p></html> "
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class
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
        tablaDatosSucursal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaDatosSucursalMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tablaDatosSucursal);
        if (tablaDatosSucursal.getColumnModel().getColumnCount() > 0) {
            tablaDatosSucursal.getColumnModel().getColumn(0).setMinWidth(60);
            tablaDatosSucursal.getColumnModel().getColumn(0).setPreferredWidth(60);
            tablaDatosSucursal.getColumnModel().getColumn(0).setMaxWidth(60);
            tablaDatosSucursal.getColumnModel().getColumn(2).setMinWidth(0);
            tablaDatosSucursal.getColumnModel().getColumn(2).setPreferredWidth(0);
            tablaDatosSucursal.getColumnModel().getColumn(2).setMaxWidth(0);
            tablaDatosSucursal.getColumnModel().getColumn(3).setMinWidth(0);
            tablaDatosSucursal.getColumnModel().getColumn(3).setPreferredWidth(0);
            tablaDatosSucursal.getColumnModel().getColumn(3).setMaxWidth(0);
            tablaDatosSucursal.getColumnModel().getColumn(4).setMinWidth(0);
            tablaDatosSucursal.getColumnModel().getColumn(4).setPreferredWidth(0);
            tablaDatosSucursal.getColumnModel().getColumn(4).setMaxWidth(0);
            tablaDatosSucursal.getColumnModel().getColumn(5).setMinWidth(0);
            tablaDatosSucursal.getColumnModel().getColumn(5).setPreferredWidth(0);
            tablaDatosSucursal.getColumnModel().getColumn(5).setMaxWidth(0);
        }

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCriterioSucursal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCriterioSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout BuscadorSucursalLayout = new javax.swing.GroupLayout(BuscadorSucursal.getContentPane());
        BuscadorSucursal.getContentPane().setLayout(BuscadorSucursalLayout);
        BuscadorSucursalLayout.setHorizontalGroup(
            BuscadorSucursalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BuscadorSucursalLayout.setVerticalGroup(
            BuscadorSucursalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel6.setBackground(new java.awt.Color(50, 104, 151));
        jLabel6.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("BUSCADOR DE TIPOS DE MOVIMIENTOS");
        jLabel6.setOpaque(true);

        txtCriterioTipoMovimiento.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtCriterioTipoMovimiento.setPrompt("Aqui puede ingresar los filtros para la busqueda..");
        txtCriterioTipoMovimiento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCriterioTipoMovimientoActionPerformed(evt);
            }
        });
        txtCriterioTipoMovimiento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCriterioTipoMovimientoKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCriterioTipoMovimientoKeyTyped(evt);
            }
        });

        tablaDatosTipoMovimiento.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        tablaDatosTipoMovimiento.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "<html><p style=\"text-align:center\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Código</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Descripción</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Abreviacion</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">idTipoComprobante</span></span></span></p></html> "
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaDatosTipoMovimiento.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaDatosTipoMovimientoMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tablaDatosTipoMovimiento);
        if (tablaDatosTipoMovimiento.getColumnModel().getColumnCount() > 0) {
            tablaDatosTipoMovimiento.getColumnModel().getColumn(0).setMinWidth(60);
            tablaDatosTipoMovimiento.getColumnModel().getColumn(0).setPreferredWidth(60);
            tablaDatosTipoMovimiento.getColumnModel().getColumn(0).setMaxWidth(60);
            tablaDatosTipoMovimiento.getColumnModel().getColumn(3).setMinWidth(0);
            tablaDatosTipoMovimiento.getColumnModel().getColumn(3).setPreferredWidth(0);
            tablaDatosTipoMovimiento.getColumnModel().getColumn(3).setMaxWidth(0);
        }

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCriterioTipoMovimiento, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCriterioTipoMovimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout BuscadorTipoMovimientoLayout = new javax.swing.GroupLayout(BuscadorTipoMovimiento.getContentPane());
        BuscadorTipoMovimiento.getContentPane().setLayout(BuscadorTipoMovimientoLayout);
        BuscadorTipoMovimientoLayout.setHorizontalGroup(
            BuscadorTipoMovimientoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BuscadorTipoMovimientoLayout.setVerticalGroup(
            BuscadorTipoMovimientoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

        setClosable(true);
        setIconifiable(true);

        jPanel1.setBackground(new java.awt.Color(50, 104, 151));

        jLabel1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Mantenimiento de Configuración");

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
                "<html><p style=\"text-align:center\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Cód.Config.</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Cod.Suc.</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Sucursal</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Cod.TipoMovCCONR</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Com.Con.Rec.</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Cod.TipoMovCCRER</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Com.Cre.Rec.</span></span></span></p></html> "
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
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
            tablaDatos.getColumnModel().getColumn(0).setMinWidth(80);
            tablaDatos.getColumnModel().getColumn(0).setPreferredWidth(80);
            tablaDatos.getColumnModel().getColumn(0).setMaxWidth(80);
            tablaDatos.getColumnModel().getColumn(1).setMinWidth(65);
            tablaDatos.getColumnModel().getColumn(1).setPreferredWidth(65);
            tablaDatos.getColumnModel().getColumn(1).setMaxWidth(65);
            tablaDatos.getColumnModel().getColumn(3).setMinWidth(0);
            tablaDatos.getColumnModel().getColumn(3).setPreferredWidth(0);
            tablaDatos.getColumnModel().getColumn(3).setMaxWidth(0);
            tablaDatos.getColumnModel().getColumn(4).setMinWidth(0);
            tablaDatos.getColumnModel().getColumn(4).setPreferredWidth(0);
            tablaDatos.getColumnModel().getColumn(4).setMaxWidth(0);
            tablaDatos.getColumnModel().getColumn(5).setMinWidth(0);
            tablaDatos.getColumnModel().getColumn(5).setPreferredWidth(0);
            tablaDatos.getColumnModel().getColumn(5).setMaxWidth(0);
            tablaDatos.getColumnModel().getColumn(6).setMinWidth(0);
            tablaDatos.getColumnModel().getColumn(6).setPreferredWidth(0);
            tablaDatos.getColumnModel().getColumn(6).setMaxWidth(0);
        }

        javax.swing.GroupLayout pestanhaListaLayout = new javax.swing.GroupLayout(pestanhaLista);
        pestanhaLista.setLayout(pestanhaListaLayout);
        pestanhaListaLayout.setHorizontalGroup(
            pestanhaListaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pestanhaListaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pestanhaListaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCriterio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 571, Short.MAX_VALUE))
                .addContainerGap())
        );
        pestanhaListaLayout.setVerticalGroup(
            pestanhaListaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pestanhaListaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtCriterio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE)
                .addContainerGap())
        );

        pestanha.addTab("Listado", pestanhaLista);

        pestanhaABM.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel2.setText("Código:");

        txtCodigo.setEnabled(false);
        txtCodigo.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtCodigo.setPrompt("Cód. Conf.");

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

        txtCodigoSucursal.setEnabled(false);
        txtCodigoSucursal.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtCodigoSucursal.setPrompt("Cód. Suc.");
        txtCodigoSucursal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoSucursalActionPerformed(evt);
            }
        });
        txtCodigoSucursal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodigoSucursalKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodigoSucursalKeyTyped(evt);
            }
        });

        txtDescripcionSucursal.setEnabled(false);
        txtDescripcionSucursal.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtDescripcionSucursal.setPrompt("Descripción o nombre de la sucursal...");

        jLabel12.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel12.setText("Sucursal:");

        txtCodigoTipoMovimiento.setToolTipText("Cód. T.M.");
        txtCodigoTipoMovimiento.setEnabled(false);
        txtCodigoTipoMovimiento.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtCodigoTipoMovimiento.setPrompt("Cód. T.C.");
        txtCodigoTipoMovimiento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoTipoMovimientoActionPerformed(evt);
            }
        });
        txtCodigoTipoMovimiento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodigoTipoMovimientoKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodigoTipoMovimientoKeyTyped(evt);
            }
        });

        txtDescripcionTipoMovimiento.setEnabled(false);
        txtDescripcionTipoMovimiento.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtDescripcionTipoMovimiento.setPrompt("Descripción o nombre del tipo de movimiento...");
        txtDescripcionTipoMovimiento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDescripcionTipoMovimientoActionPerformed(evt);
            }
        });
        txtDescripcionTipoMovimiento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDescripcionTipoMovimientoKeyTyped(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel4.setText("Com.Con.Rec.:");

        jLabel5.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel5.setText("Com.Cre.Rec.:");

        txtCodigoTipoMovimientoCCRER.setEnabled(false);
        txtCodigoTipoMovimientoCCRER.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtCodigoTipoMovimientoCCRER.setPrompt("Cód. T.C.");
        txtCodigoTipoMovimientoCCRER.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoTipoMovimientoCCRERActionPerformed(evt);
            }
        });
        txtCodigoTipoMovimientoCCRER.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodigoTipoMovimientoCCRERKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodigoTipoMovimientoCCRERKeyTyped(evt);
            }
        });

        txtDescripcionTipoMovimientoCCRER.setEnabled(false);
        txtDescripcionTipoMovimientoCCRER.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtDescripcionTipoMovimientoCCRER.setPrompt("Descripción o nombre del tipo de movimiento...");
        txtDescripcionTipoMovimientoCCRER.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDescripcionTipoMovimientoCCRERActionPerformed(evt);
            }
        });
        txtDescripcionTipoMovimientoCCRER.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDescripcionTipoMovimientoCCRERKeyTyped(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel7.setText("Rec. Pag. Rec.:");

        txtCodigoTipoMovimientoRPAGR.setEnabled(false);
        txtCodigoTipoMovimientoRPAGR.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtCodigoTipoMovimientoRPAGR.setPrompt("Cód. T.C.");
        txtCodigoTipoMovimientoRPAGR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoTipoMovimientoRPAGRActionPerformed(evt);
            }
        });
        txtCodigoTipoMovimientoRPAGR.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodigoTipoMovimientoRPAGRKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodigoTipoMovimientoRPAGRKeyTyped(evt);
            }
        });

        txtDescripcionTipoMovimientoRPAGR.setEnabled(false);
        txtDescripcionTipoMovimientoRPAGR.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtDescripcionTipoMovimientoRPAGR.setPrompt("Descripción o nombre del tipo de movimiento...");
        txtDescripcionTipoMovimientoRPAGR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDescripcionTipoMovimientoRPAGRActionPerformed(evt);
            }
        });
        txtDescripcionTipoMovimientoRPAGR.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDescripcionTipoMovimientoRPAGRKeyTyped(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel8.setText("Ven.Con.Emi.:");

        txtCodigoTipoMovimientoFCONE.setToolTipText("Cód. T.M.");
        txtCodigoTipoMovimientoFCONE.setEnabled(false);
        txtCodigoTipoMovimientoFCONE.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtCodigoTipoMovimientoFCONE.setPrompt("Cód. T.C.");
        txtCodigoTipoMovimientoFCONE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoTipoMovimientoFCONEActionPerformed(evt);
            }
        });
        txtCodigoTipoMovimientoFCONE.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodigoTipoMovimientoFCONEKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodigoTipoMovimientoFCONEKeyTyped(evt);
            }
        });

        txtDescripcionTipoMovimientoFCONE.setEnabled(false);
        txtDescripcionTipoMovimientoFCONE.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtDescripcionTipoMovimientoFCONE.setPrompt("Descripción o nombre del tipo de movimiento...");
        txtDescripcionTipoMovimientoFCONE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDescripcionTipoMovimientoFCONEActionPerformed(evt);
            }
        });
        txtDescripcionTipoMovimientoFCONE.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDescripcionTipoMovimientoFCONEKeyTyped(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel9.setText("Ven.Cre.Emi.:");

        txtCodigoTipoMovimientoFCREE.setEnabled(false);
        txtCodigoTipoMovimientoFCREE.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtCodigoTipoMovimientoFCREE.setPrompt("Cód. T.C.");
        txtCodigoTipoMovimientoFCREE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoTipoMovimientoFCREEActionPerformed(evt);
            }
        });
        txtCodigoTipoMovimientoFCREE.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodigoTipoMovimientoFCREEKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodigoTipoMovimientoFCREEKeyTyped(evt);
            }
        });

        txtDescripcionTipoMovimientoFCREE.setEnabled(false);
        txtDescripcionTipoMovimientoFCREE.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtDescripcionTipoMovimientoFCREE.setPrompt("Descripción o nombre del tipo de movimiento...");
        txtDescripcionTipoMovimientoFCREE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDescripcionTipoMovimientoFCREEActionPerformed(evt);
            }
        });
        txtDescripcionTipoMovimientoFCREE.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDescripcionTipoMovimientoFCREEKeyTyped(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel10.setText("Facturacion:");

        grupoFacturacion.add(rbPermitir);
        rbPermitir.setSelected(true);
        rbPermitir.setText("PERMITIR FACTURACION NEGATIVA");
        rbPermitir.setEnabled(false);
        rbPermitir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbPermitirActionPerformed(evt);
            }
        });
        rbPermitir.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                rbPermitirKeyPressed(evt);
            }
        });

        grupoFacturacion.add(rbRestringir);
        rbRestringir.setText("RESTRINGIR FACTURACION NEGATIVA");
        rbRestringir.setEnabled(false);
        rbRestringir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbRestringirActionPerformed(evt);
            }
        });
        rbRestringir.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                rbRestringirKeyPressed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel11.setText("Art. Tip. Serv.:");

        txtCodigoTipoArticulo.setEnabled(false);
        txtCodigoTipoArticulo.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtCodigoTipoArticulo.setPrompt("Cód. T.A.");
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
        txtDescripcionTipoArticulo.setPrompt("Descripción o nombre del tipo de articulo...");
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

        javax.swing.GroupLayout pestanhaABMLayout = new javax.swing.GroupLayout(pestanhaABM);
        pestanhaABM.setLayout(pestanhaABMLayout);
        pestanhaABMLayout.setHorizontalGroup(
            pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pestanhaABMLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pestanhaABMLayout.createSequentialGroup()
                        .addGap(0, 262, Short.MAX_VALUE)
                        .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnConfirmar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pestanhaABMLayout.createSequentialGroup()
                        .addGroup(pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pestanhaABMLayout.createSequentialGroup()
                                .addGroup(pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(txtCodigoSucursal, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
                                    .addComponent(txtCodigoTipoMovimiento, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
                                    .addComponent(txtCodigo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtDescripcionTipoMovimiento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtDescripcionSucursal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(pestanhaABMLayout.createSequentialGroup()
                                .addComponent(txtCodigoTipoMovimientoCCRER, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtDescripcionTipoMovimientoCCRER, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(pestanhaABMLayout.createSequentialGroup()
                                .addGroup(pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(txtCodigoTipoMovimientoFCREE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtCodigoTipoMovimientoFCONE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtCodigoTipoMovimientoRPAGR, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtDescripcionTipoMovimientoRPAGR, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtDescripcionTipoMovimientoFCONE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtDescripcionTipoMovimientoFCREE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(pestanhaABMLayout.createSequentialGroup()
                                .addComponent(rbPermitir)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(rbRestringir))
                            .addGroup(pestanhaABMLayout.createSequentialGroup()
                                .addComponent(txtCodigoTipoArticulo, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtDescripcionTipoArticulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
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
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtCodigoSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDescripcionSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtCodigoTipoMovimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtDescripcionTipoMovimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtCodigoTipoMovimientoCCRER, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtDescripcionTipoMovimientoCCRER, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCodigoTipoMovimientoRPAGR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDescripcionTipoMovimientoRPAGR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCodigoTipoMovimientoFCONE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDescripcionTipoMovimientoFCONE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCodigoTipoMovimientoFCREE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDescripcionTipoMovimientoFCREE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rbPermitir)
                    .addComponent(rbRestringir))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCodigoTipoArticulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDescripcionTipoArticulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnConfirmar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(84, 84, 84))
        );

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
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pestanha, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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

    private void txtCodigoSucursalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoSucursalActionPerformed
        if (txtCodigoSucursal.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "NO PUEDE DEJAR EL CAMPO DE SUCURSAL VACIO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else {
            int existe = 0;
            if (operacion == "NUEVO") {
                existe = dao.verificarConfiguracion(Integer.parseInt(txtCodigoSucursal.getText()));
                if (existe == 0) {
                    int idsucursal = Integer.parseInt(txtCodigoSucursal.getText());
                    s.setIdsucursal(idsucursal);
                    boolean resultado = daoSucursal.consultarDatos(s);
                    if (resultado == true) {
                        txtDescripcionSucursal.setText(s.getDescripcion());
                        txtCodigoTipoMovimiento.grabFocus();
                    } else {
                        txtCodigoSucursal.setText(null);
                        txtDescripcionSucursal.setText(null);
                        txtCodigoSucursal.grabFocus();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "YA EXISTE UNA CONFIGURACIÓN PARA ESTA SUCURSAL SELECCIONADA", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                int idsucursal = Integer.parseInt(txtCodigoSucursal.getText());
                s.setIdsucursal(idsucursal);
                boolean resultado = daoSucursal.consultarDatos(s);
                if (resultado == true) {
                    txtDescripcionSucursal.setText(s.getDescripcion());
                    txtCodigoTipoMovimiento.grabFocus();
                } else {
                    txtCodigoSucursal.setText(null);
                    txtDescripcionSucursal.setText(null);
                    txtCodigoSucursal.grabFocus();
                }
            }
        }
    }//GEN-LAST:event_txtCodigoSucursalActionPerformed

    private void txtCodigoSucursalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoSucursalKeyPressed
        if (evt.VK_F1 == evt.getKeyCode()) {
            buscarSucursal();
        }
    }//GEN-LAST:event_txtCodigoSucursalKeyPressed

    private void txtCodigoSucursalKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoSucursalKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            getToolkit().beep();
            evt.consume();
        }
        if (txtCodigoSucursal.getText().length() == 10) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCodigoSucursalKeyTyped

    private void txtCriterioSucursalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCriterioSucursalActionPerformed
        cargarSucursal();
    }//GEN-LAST:event_txtCriterioSucursalActionPerformed

    private void txtCriterioSucursalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCriterioSucursalKeyPressed
        if (evt.VK_ESCAPE == evt.getKeyCode()) {
            txtCodigoSucursal.setText(null);
            txtDescripcionSucursal.setText(null);
            txtCodigoSucursal.grabFocus();
            BuscadorSucursal.dispose();
        }
    }//GEN-LAST:event_txtCriterioSucursalKeyPressed

    private void txtCriterioSucursalKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCriterioSucursalKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            evt.setKeyChar(Character.toUpperCase(c));
        }
        if (txtCriterio.getText().length() == 100) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCriterioSucursalKeyTyped

    private void tablaDatosSucursalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaDatosSucursalMouseClicked
        if (evt.getClickCount() == 2) {
            if (tablaDatosSucursal.getSelectedRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "SELECCIONE UNA FILA");
            } else {
                txtCriterioSucursal.setText(null);
                BuscadorSucursal.dispose();
            }
        }
    }//GEN-LAST:event_tablaDatosSucursalMouseClicked

    private void txtCodigoTipoMovimientoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoTipoMovimientoActionPerformed
        if (txtCodigoTipoMovimiento.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "NO PUEDE DEJAR EL CAMPO DE TIPO DE MOVIMIENTO VACIO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else {
            int idtipomovimientp = Integer.parseInt(txtCodigoTipoMovimiento.getText());
            tm.setIdtipomovimiento(idtipomovimientp);
            boolean resultado = daoTipoMovimiento.consultarDatos(tm);
            if (resultado == true) {
                txtDescripcionTipoMovimiento.setText(tm.getDescripcion());
                txtCodigoTipoMovimientoCCRER.grabFocus();
            } else {
                txtCodigoTipoMovimiento.setText(null);
                txtDescripcionTipoMovimiento.setText(null);
                txtCodigoTipoMovimiento.grabFocus();
            }
        }
    }//GEN-LAST:event_txtCodigoTipoMovimientoActionPerformed

    private void txtCodigoTipoMovimientoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoTipoMovimientoKeyPressed
        if (evt.VK_F1 == evt.getKeyCode()) {
            buscarTipoMovimientoCCONR();
        }
    }//GEN-LAST:event_txtCodigoTipoMovimientoKeyPressed

    private void txtCodigoTipoMovimientoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoTipoMovimientoKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            getToolkit().beep();
            evt.consume();
        }
        if (txtCodigoTipoMovimiento.getText().length() == 10) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCodigoTipoMovimientoKeyTyped

    private void txtDescripcionTipoMovimientoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDescripcionTipoMovimientoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescripcionTipoMovimientoActionPerformed

    private void txtDescripcionTipoMovimientoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescripcionTipoMovimientoKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescripcionTipoMovimientoKeyTyped

    private void txtCriterioTipoMovimientoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCriterioTipoMovimientoActionPerformed
        cargarTipoMovimiento();
    }//GEN-LAST:event_txtCriterioTipoMovimientoActionPerformed

    private void txtCriterioTipoMovimientoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCriterioTipoMovimientoKeyPressed
        if (evt.VK_ESCAPE == evt.getKeyCode()) {
            txtCodigoTipoMovimiento.setText(null);
            txtDescripcionTipoMovimiento.setText(null);
            txtCodigoTipoMovimiento.grabFocus();
            BuscadorTipoMovimiento.dispose();
        }
    }//GEN-LAST:event_txtCriterioTipoMovimientoKeyPressed

    private void txtCriterioTipoMovimientoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCriterioTipoMovimientoKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            evt.setKeyChar(Character.toUpperCase(c));
        }
        if (txtCriterio.getText().length() == 100) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCriterioTipoMovimientoKeyTyped

    private void tablaDatosTipoMovimientoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaDatosTipoMovimientoMouseClicked
        if (evt.getClickCount() == 2) {
            if (tablaDatosTipoMovimiento.getSelectedRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "SELECCIONE UNA FILA");
            } else {
                txtCriterioTipoMovimiento.setText(null);
                BuscadorTipoMovimiento.dispose();
            }
        }
    }//GEN-LAST:event_tablaDatosTipoMovimientoMouseClicked

    private void txtCodigoTipoMovimientoCCRERActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoTipoMovimientoCCRERActionPerformed
        if (txtCodigoTipoMovimientoCCRER.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "NO PUEDE DEJAR EL CAMPO DE TIPO DE MOVIMIENTO VACIO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else {
            int idtipomovimiento = Integer.parseInt(txtCodigoTipoMovimientoCCRER.getText());
            tm.setIdtipomovimiento(idtipomovimiento);
            boolean resultado = daoTipoMovimiento.consultarDatos(tm);
            if (resultado == true) {
                txtDescripcionTipoMovimientoCCRER.setText(tm.getDescripcion());
                txtCodigoTipoMovimientoRPAGR.grabFocus();
            } else {
                txtCodigoTipoMovimientoCCRER.setText(null);
                txtDescripcionTipoMovimientoCCRER.setText(null);
                txtCodigoTipoMovimientoCCRER.grabFocus();
            }
        }
    }//GEN-LAST:event_txtCodigoTipoMovimientoCCRERActionPerformed

    private void txtCodigoTipoMovimientoCCRERKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoTipoMovimientoCCRERKeyPressed
        if (evt.VK_F1 == evt.getKeyCode()) {
            buscarTipoMovimientoCCRER();
        }
    }//GEN-LAST:event_txtCodigoTipoMovimientoCCRERKeyPressed

    private void txtCodigoTipoMovimientoCCRERKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoTipoMovimientoCCRERKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            getToolkit().beep();
            evt.consume();
        }
        if (txtCodigoTipoMovimientoCCRER.getText().length() == 10) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCodigoTipoMovimientoCCRERKeyTyped

    private void txtDescripcionTipoMovimientoCCRERActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDescripcionTipoMovimientoCCRERActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescripcionTipoMovimientoCCRERActionPerformed

    private void txtDescripcionTipoMovimientoCCRERKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescripcionTipoMovimientoCCRERKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescripcionTipoMovimientoCCRERKeyTyped

    private void txtCodigoTipoMovimientoRPAGRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoTipoMovimientoRPAGRActionPerformed
        if (txtCodigoTipoMovimientoRPAGR.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "NO PUEDE DEJAR EL CAMPO DE TIPO DE MOVIMIENTO VACIO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else {
            int idtipomovimiento = Integer.parseInt(txtCodigoTipoMovimientoRPAGR.getText());
            tm.setIdtipomovimiento(idtipomovimiento);
            boolean resultado = daoTipoMovimiento.consultarDatos(tm);
            if (resultado == true) {
                txtDescripcionTipoMovimientoRPAGR.setText(tm.getDescripcion());
                txtCodigoTipoMovimientoFCONE.grabFocus();
            } else {
                txtCodigoTipoMovimientoRPAGR.setText(null);
                txtDescripcionTipoMovimientoRPAGR.setText(null);
                txtCodigoTipoMovimientoRPAGR.grabFocus();
            }
        }
    }//GEN-LAST:event_txtCodigoTipoMovimientoRPAGRActionPerformed

    private void txtCodigoTipoMovimientoRPAGRKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoTipoMovimientoRPAGRKeyPressed
        if (evt.VK_F1 == evt.getKeyCode()) {
            buscarTipoMovimientoRPAGR();
        }
    }//GEN-LAST:event_txtCodigoTipoMovimientoRPAGRKeyPressed

    private void txtCodigoTipoMovimientoRPAGRKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoTipoMovimientoRPAGRKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            getToolkit().beep();
            evt.consume();
        }
        if (txtCodigoTipoMovimientoRPAGR.getText().length() == 10) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCodigoTipoMovimientoRPAGRKeyTyped

    private void txtDescripcionTipoMovimientoRPAGRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDescripcionTipoMovimientoRPAGRActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescripcionTipoMovimientoRPAGRActionPerformed

    private void txtDescripcionTipoMovimientoRPAGRKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescripcionTipoMovimientoRPAGRKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescripcionTipoMovimientoRPAGRKeyTyped

    private void txtCodigoTipoMovimientoFCONEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoTipoMovimientoFCONEActionPerformed
        if (txtCodigoTipoMovimientoFCONE.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "NO PUEDE DEJAR EL CAMPO DE TIPO DE MOVIMIENTO VACIO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else {
            int idtipomovimientO = Integer.parseInt(txtCodigoTipoMovimientoFCONE.getText());
            tm.setIdtipomovimiento(idtipomovimientO);
            boolean resultado = daoTipoMovimiento.consultarDatos(tm);
            if (resultado == true) {
                txtDescripcionTipoMovimientoFCONE.setText(tm.getDescripcion());
                txtCodigoTipoMovimientoFCREE.grabFocus();
            } else {
                txtCodigoTipoMovimientoFCONE.setText(null);
                txtDescripcionTipoMovimientoFCONE.setText(null);
                txtCodigoTipoMovimientoFCONE.grabFocus();
            }
        }
    }//GEN-LAST:event_txtCodigoTipoMovimientoFCONEActionPerformed

    private void txtCodigoTipoMovimientoFCONEKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoTipoMovimientoFCONEKeyPressed
        if (evt.VK_F1 == evt.getKeyCode()) {
            buscarTipoMovimientoFCONE();
        }
    }//GEN-LAST:event_txtCodigoTipoMovimientoFCONEKeyPressed

    private void txtCodigoTipoMovimientoFCONEKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoTipoMovimientoFCONEKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            getToolkit().beep();
            evt.consume();
        }
        if (txtCodigoTipoMovimientoFCONE.getText().length() == 10) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCodigoTipoMovimientoFCONEKeyTyped

    private void txtDescripcionTipoMovimientoFCONEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDescripcionTipoMovimientoFCONEActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescripcionTipoMovimientoFCONEActionPerformed

    private void txtDescripcionTipoMovimientoFCONEKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescripcionTipoMovimientoFCONEKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescripcionTipoMovimientoFCONEKeyTyped

    private void txtCodigoTipoMovimientoFCREEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoTipoMovimientoFCREEActionPerformed
        if (txtCodigoTipoMovimientoFCREE.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "NO PUEDE DEJAR EL CAMPO DE TIPO DE MOVIMIENTO VACIO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else {
            int idtipomovimientO = Integer.parseInt(txtCodigoTipoMovimientoFCREE.getText());
            tm.setIdtipomovimiento(idtipomovimientO);
            boolean resultado = daoTipoMovimiento.consultarDatos(tm);
            if (resultado == true) {
                txtDescripcionTipoMovimientoFCREE.setText(tm.getDescripcion());
                rbPermitir.grabFocus();
            } else {
                txtCodigoTipoMovimientoFCREE.setText(null);
                txtDescripcionTipoMovimientoFCREE.setText(null);
                txtCodigoTipoMovimientoFCREE.grabFocus();
            }
        }
    }//GEN-LAST:event_txtCodigoTipoMovimientoFCREEActionPerformed

    private void txtCodigoTipoMovimientoFCREEKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoTipoMovimientoFCREEKeyPressed
        if (evt.VK_F1 == evt.getKeyCode()) {
            buscarTipoMovimientoFCREE();
        }
    }//GEN-LAST:event_txtCodigoTipoMovimientoFCREEKeyPressed

    private void txtCodigoTipoMovimientoFCREEKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoTipoMovimientoFCREEKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            getToolkit().beep();
            evt.consume();
        }
        if (txtCodigoTipoMovimientoFCREE.getText().length() == 10) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCodigoTipoMovimientoFCREEKeyTyped

    private void txtDescripcionTipoMovimientoFCREEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDescripcionTipoMovimientoFCREEActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescripcionTipoMovimientoFCREEActionPerformed

    private void txtDescripcionTipoMovimientoFCREEKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescripcionTipoMovimientoFCREEKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescripcionTipoMovimientoFCREEKeyTyped

    private void rbPermitirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbPermitirActionPerformed
        txtCodigoTipoArticulo.grabFocus();
    }//GEN-LAST:event_rbPermitirActionPerformed

    private void rbPermitirKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rbPermitirKeyPressed
        txtCodigoTipoArticulo.grabFocus();
    }//GEN-LAST:event_rbPermitirKeyPressed

    private void rbRestringirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbRestringirActionPerformed
        txtCodigoTipoArticulo.grabFocus();
    }//GEN-LAST:event_rbRestringirActionPerformed

    private void rbRestringirKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rbRestringirKeyPressed
        txtCodigoTipoArticulo.grabFocus();
    }//GEN-LAST:event_rbRestringirKeyPressed

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

    private void txtCodigoTipoArticuloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoTipoArticuloActionPerformed
        if (txtCodigoTipoArticulo.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "NO PUEDE DEJAR EL CAMPO VACIO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else {
            int idtipo = Integer.parseInt(txtCodigoTipoArticulo.getText());
            ta.setIdtipo(idtipo);
            boolean resultado = daoTipoArticulo.consultarDatos(ta);
            if (resultado == true) {
                txtDescripcionTipoArticulo.setText(ta.getDescripcion());
                btnConfirmar.grabFocus();
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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDialog BuscadorSucursal;
    private javax.swing.JDialog BuscadorTipoArticulo;
    private javax.swing.JDialog BuscadorTipoMovimiento;
    private javax.swing.JMenuItem Eliminar;
    private javax.swing.JMenuItem Modificar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnConfirmar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.ButtonGroup grupoFacturacion;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
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
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JPopupMenu menuDesplegable;
    private javax.swing.JTabbedPane pestanha;
    private javax.swing.JPanel pestanhaABM;
    private javax.swing.JPanel pestanhaLista;
    private javax.swing.JRadioButton rbPermitir;
    private javax.swing.JRadioButton rbRestringir;
    private javax.swing.JTable tablaDatos;
    private javax.swing.JTable tablaDatosSucursal;
    private javax.swing.JTable tablaDatosTipoArticulo;
    private javax.swing.JTable tablaDatosTipoMovimiento;
    private org.jdesktop.swingx.JXTextField txtCodigo;
    private org.jdesktop.swingx.JXTextField txtCodigoSucursal;
    private org.jdesktop.swingx.JXTextField txtCodigoTipoArticulo;
    private org.jdesktop.swingx.JXTextField txtCodigoTipoMovimiento;
    private org.jdesktop.swingx.JXTextField txtCodigoTipoMovimientoCCRER;
    private org.jdesktop.swingx.JXTextField txtCodigoTipoMovimientoFCONE;
    private org.jdesktop.swingx.JXTextField txtCodigoTipoMovimientoFCREE;
    private org.jdesktop.swingx.JXTextField txtCodigoTipoMovimientoRPAGR;
    private org.jdesktop.swingx.JXTextField txtCriterio;
    private org.jdesktop.swingx.JXTextField txtCriterioSucursal;
    private org.jdesktop.swingx.JXTextField txtCriterioTipoArticulo;
    private org.jdesktop.swingx.JXTextField txtCriterioTipoMovimiento;
    private org.jdesktop.swingx.JXTextField txtDescripcionSucursal;
    private org.jdesktop.swingx.JXTextField txtDescripcionTipoArticulo;
    private org.jdesktop.swingx.JXTextField txtDescripcionTipoMovimiento;
    private org.jdesktop.swingx.JXTextField txtDescripcionTipoMovimientoCCRER;
    private org.jdesktop.swingx.JXTextField txtDescripcionTipoMovimientoFCONE;
    private org.jdesktop.swingx.JXTextField txtDescripcionTipoMovimientoFCREE;
    private org.jdesktop.swingx.JXTextField txtDescripcionTipoMovimientoRPAGR;
    // End of variables declaration//GEN-END:variables
}
