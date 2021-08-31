package Vistas;

import Dao.DAOImpresora;
import Dao.DAOImpresoraTimbrado;
import Dao.DAOTipoComprobante;
import Modelos.Impresora;
import Modelos.ImpresoraTimbrado;
import Modelos.TipoComprobante;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author armando
 */
public class JFrmImpresoraTimbrado extends javax.swing.JInternalFrame {

    ImpresoraTimbrado it = new ImpresoraTimbrado();
    Impresora i = new Impresora();
    TipoComprobante tc = new TipoComprobante();

    DAOImpresoraTimbrado dao = new DAOImpresoraTimbrado();
    DAOImpresora daoImpresora = new DAOImpresora();
    DAOTipoComprobante daoTipoComprobante = new DAOTipoComprobante();

    ArrayList<Object[]> datos = new ArrayList<>();
    ArrayList<Object[]> datosImpresora = new ArrayList<>();
    ArrayList<Object[]> datosTipoComprobante = new ArrayList<>();

    //VARIABLE QUE MANEJA QUE TIPOS DE OPERACIONES SE REALIZARAN: SI VA A SER ALTA, BAJA O MODIFICACION DEL REGISTRO
    String operacion = "";

    String tres_ceros = String.format("%%0%dd", 3);
    String siete_ceros = String.format("%%0%dd", 7);
    SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

    /**
     * Creates new form JFrmImpresoraTimbrado
     */
    public JFrmImpresoraTimbrado() {
        setTitle("JFrmImpresoraTimbrado");
        initComponents();
        txtFechaInicial.setFormats(formato);
        txtFechaFinal.setFormats(formato);
    }

    public void cargar() {
        DefaultTableModel modelo = (DefaultTableModel) tablaDatos.getModel();
        modelo.setRowCount(0);
        int idimpresora = 0;
        if (txtCodigoImpresoraCriterio.getText().isEmpty()) {
            idimpresora = 0;
        } else {
            idimpresora = Integer.parseInt(txtCodigoImpresoraCriterio.getText());
        }
        datos = dao.consultar(idimpresora);
        for (Object[] obj : datos) {
            modelo.addRow(obj);
        }
        this.tablaDatos.setModel(modelo);
    }

    public void cargarImpresora() {
        DefaultTableModel modelo = (DefaultTableModel) tablaDatosImpresora.getModel();
        modelo.setRowCount(0);
        datosImpresora = daoImpresora.consultar(txtCriterioImpresora.getText());
        for (Object[] obj : datosImpresora) {
            modelo.addRow(obj);
        }
        this.tablaDatosImpresora.setModel(modelo);
    }

    public void cargarImpresoraDos() {
        DefaultTableModel modelo = (DefaultTableModel) tablaDatosImpresoraDos.getModel();
        modelo.setRowCount(0);
        datosImpresora = daoImpresora.consultar(txtCriterioImpresoraDos.getText());
        for (Object[] obj : datosImpresora) {
            modelo.addRow(obj);
        }
        this.tablaDatosImpresoraDos.setModel(modelo);
    }

    public void cargarTipoComprobante() {
        DefaultTableModel modelo = (DefaultTableModel) tablaDatosTipoComprobante.getModel();
        modelo.setRowCount(0);
        datosTipoComprobante = daoTipoComprobante.consultar(txtCriterioTipoComprobante.getText());
        for (Object[] obj : datosTipoComprobante) {
            modelo.addRow(obj);
        }
        this.tablaDatosTipoComprobante.setModel(modelo);
    }

    public void habilitarCampos(String accion) {
        switch (accion) {
            case "NUEVO":
                //CAMPOS
                txtCodigoImpresora.setEnabled(true);
                txtCodigoTipoComprobante.setEnabled(true);
                txtPuntoEmision.setEnabled(true);
                txtEstablecimiento.setEnabled(true);
                txtNumeroInicial.setEnabled(true);
                txtNumeroFinal.setEnabled(true);
                txtFechaInicial.setEnabled(true);
                txtFechaFinal.setEnabled(true);
                txtTimbrado.setEnabled(true);
                //BOTONES
                btnNuevo.setEnabled(false);
                btnConfirmar.setEnabled(true);
                btnCancelar.setEnabled(true);
                //REDIRECIONAMOS
                txtCodigoImpresora.grabFocus();
                break;
            case "MODIFICAR":
                //CAMPOS
                txtCodigoImpresora.setEnabled(false);
                txtCodigoTipoComprobante.setEnabled(true);
                txtPuntoEmision.setEnabled(true);
                txtEstablecimiento.setEnabled(true);
                txtNumeroInicial.setEnabled(true);
                txtNumeroFinal.setEnabled(true);
                txtFechaInicial.setEnabled(true);
                txtFechaFinal.setEnabled(true);
                txtTimbrado.setEnabled(true);
                //BOTONES
                btnNuevo.setEnabled(false);
                btnConfirmar.setEnabled(true);
                btnCancelar.setEnabled(true);
                //REDIRECIONAMOS
                pestanha.setSelectedIndex(1);
                txtCodigoTipoComprobante.grabFocus();
                break;
            case "ELIMINAR":
                //CAMPOS
                txtCodigoImpresora.setEnabled(false);
                txtCodigoTipoComprobante.setEnabled(false);
                txtPuntoEmision.setEnabled(false);
                txtEstablecimiento.setEnabled(false);
                txtNumeroInicial.setEnabled(false);
                txtNumeroFinal.setEnabled(false);
                txtFechaInicial.setEnabled(false);
                txtFechaFinal.setEnabled(false);
                txtTimbrado.setEnabled(false);
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
                txtCodigoImpresora.setEnabled(false);
                txtCodigoTipoComprobante.setEnabled(false);
                txtPuntoEmision.setEnabled(false);
                txtEstablecimiento.setEnabled(false);
                txtNumeroInicial.setEnabled(false);
                txtNumeroFinal.setEnabled(false);
                txtFechaInicial.setEnabled(false);
                txtFechaFinal.setEnabled(false);
                txtTimbrado.setEnabled(false);
                //BOTONES
                btnNuevo.setEnabled(true);
                btnConfirmar.setEnabled(false);
                btnCancelar.setEnabled(false);
                //REDIRECIONAMOS
                pestanha.setSelectedIndex(0);
                //txtCriterio.grabFocus();
                break;
            case "GUARDAR":
                //CAMPOS
                txtCodigoImpresora.setEnabled(false);
                txtCodigoTipoComprobante.setEnabled(false);
                txtPuntoEmision.setEnabled(false);
                txtEstablecimiento.setEnabled(false);
                txtNumeroInicial.setEnabled(false);
                txtNumeroFinal.setEnabled(false);
                txtFechaInicial.setEnabled(false);
                txtFechaFinal.setEnabled(false);
                txtTimbrado.setEnabled(false);
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
        txtCriterioImpresora.setText(null);
        txtDescripcionImpresoraCriterio.setText(null);
        txtCriterioImpresoraDos.setText(null);
        txtCriterioTipoComprobante.setText(null);
        txtCodigo.setText(null);
        txtCodigoImpresora.setText(null);
        txtDescripcionImpresora.setText(null);
        txtCodigoTipoComprobante.setText(null);
        txtDescripcionTipoComprobante.setText(null);
        txtEstablecimiento.setText(null);
        txtPuntoEmision.setText(null);
        txtTimbrado.setText(null);
        txtFechaInicial.setDate(null);
        txtFechaFinal.setDate(null);
        txtNumeroInicial.setText(null);
        txtNumeroFinal.setText(null);
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
        int idimpresora = Integer.parseInt(txtCodigoImpresora.getText());
        int idtipocomprobante = Integer.parseInt(txtCodigoTipoComprobante.getText());
        int establecimiento = Integer.parseInt(txtEstablecimiento.getText());
        int puntoemision = Integer.parseInt(txtPuntoEmision.getText());
        int numerotimbrado = Integer.parseInt(txtTimbrado.getText());
        Date fechaInicial = txtFechaInicial.getDate();
        java.sql.Date fechaInicialSQL = new java.sql.Date(fechaInicial.getTime());
        Date fechaFinal = txtFechaFinal.getDate();
        java.sql.Date fechaFinalSQL = new java.sql.Date(fechaFinal.getTime());
        int numeroinicial = Integer.parseInt(txtNumeroInicial.getText());
        int numerofinal = Integer.parseInt(txtNumeroFinal.getText());
        switch (accion) {
            case "NUEVO":
                if (idimpresora == 0) {
                    error += "NO PUEDE DEJAR EL CAMPO DE IMPRESORA VACIO.\n";
                }
                if (puntoemision == 0) {
                    error += "NO PUEDE DEJAR EL CAMPO DE PUNTO DE EMISION VACIO.\n";
                }
                if (establecimiento == 0) {
                    error += "NO PUEDE DEJAR EL CAMPO DE ESTABLECIMIENTO VACIO.\n";
                }
                if (numeroinicial == 0) {
                    error += "NO PUEDE DEJAR EL CAMPO DE Nº INICIAL VACIO.\n";
                }
                if (numerofinal == 0) {
                    error += "NO PUEDE DEJAR EL CAMPO DE Nº FINAL VACIO.\n";
                }
                if (fechaInicial == null) {
                    error += "NO PUEDE DEJAR EL CAMPO DE FECHA INICIAL VACIO.\n";
                }
                if (fechaFinal == null) {
                    error += "NO PUEDE DEJAR EL CAMPO DE FECHA FINAL VACIO.\n";
                }
                if (idtipocomprobante == 0) {
                    error += "NO PUEDE DEJAR EL CAMPO DE TIPO DE COMPROBANTE VACIO.\n";
                }
                if (numerotimbrado == 0) {
                    error += "NO PUEDE DEJAR EL CAMPO DE TIMBRADO VACIO.\n";
                }
                if (error.isEmpty()) {
                    it.setIdimpresora(idimpresora);
                    it.setIdtimbrado(id);
                    it.setIdtipocomprobante(idtipocomprobante);
                    it.setEstablecimiento(establecimiento);
                    it.setPuntoemision(puntoemision);
                    it.setNumerotimbrado(numerotimbrado);
                    it.setNumeroinicial(numeroinicial);
                    it.setNumerofinal(numerofinal);
                    it.setFechainicial(fechaInicialSQL);
                    it.setFechafinal(fechaFinalSQL);
                    dao.agregar(it);
                    cargar();
                } else {
                    JOptionPane.showMessageDialog(null, error, "ERRORES", JOptionPane.ERROR_MESSAGE);
                }
                break;
            case "MODIFICAR":
                if (idimpresora == 0) {
                    error += "NO PUEDE DEJAR EL CAMPO DE IMPRESORA VACIO.\n";
                }
                if (puntoemision == 0) {
                    error += "NO PUEDE DEJAR EL CAMPO DE PUNTO DE EMISION VACIO.\n";
                }
                if (establecimiento == 0) {
                    error += "NO PUEDE DEJAR EL CAMPO DE ESTABLECIMIENTO VACIO.\n";
                }
                if (numeroinicial == 0) {
                    error += "NO PUEDE DEJAR EL CAMPO DE Nº INICIAL VACIO.\n";
                }
                if (numerofinal == 0) {
                    error += "NO PUEDE DEJAR EL CAMPO DE Nº FINAL VACIO.\n";
                }
                if (fechaInicial == null) {
                    error += "NO PUEDE DEJAR EL CAMPO DE FECHA INICIAL VACIO.\n";
                }
                if (fechaFinal == null) {
                    error += "NO PUEDE DEJAR EL CAMPO DE FECHA FINAL VACIO.\n";
                }
                if (idtipocomprobante == 0) {
                    error += "NO PUEDE DEJAR EL CAMPO DE TIPO DE COMPROBANTE VACIO.\n";
                }
                if (numerotimbrado == 0) {
                    error += "NO PUEDE DEJAR EL CAMPO DE TIMBRADO VACIO.\n";
                }
                if (error.isEmpty()) {
                    it.setIdimpresora(idimpresora);
                    it.setIdtimbrado(id);
                    it.setIdtipocomprobante(idtipocomprobante);
                    it.setEstablecimiento(establecimiento);
                    it.setPuntoemision(puntoemision);
                    it.setNumerotimbrado(numerotimbrado);
                    it.setNumeroinicial(numeroinicial);
                    it.setNumerofinal(numerofinal);
                    it.setFechainicial(fechaInicialSQL);
                    it.setFechafinal(fechaFinalSQL);
                    dao.modificar(it);
                    cargar();
                } else {
                    JOptionPane.showMessageDialog(null, error, "ERRORES", JOptionPane.ERROR_MESSAGE);
                }
                break;
            case "ELIMINAR":
                if (error.isEmpty()) {
                    it.setIdimpresora(idimpresora);
                    it.setIdtimbrado(id);
                    dao.eliminar(it);
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
            int idimpresora = Integer.parseInt(tablaDatos.getValueAt(fila, 0).toString());
            int idtimbrado = Integer.parseInt(tablaDatos.getValueAt(fila, 2).toString());
            it.setIdimpresora(idimpresora);
            it.setIdtimbrado(idtimbrado);
            dao.consultarDatos(it);
            txtCodigo.setText(""+it.getIdtimbrado());
            txtCodigoImpresora.setText(""+it.getIdimpresora());
            i.setIdimpresora(idimpresora);
            daoImpresora.consultarDatos(i);
            txtDescripcionImpresora.setText(i.getDescripcion());
            tc.setIdtipo(it.getIdtipocomprobante());
            txtCodigoTipoComprobante.setText(""+it.getIdtipocomprobante());
            txtDescripcionTipoComprobante.setText(tc.getDescripcion());
            txtEstablecimiento.setText(String.format(tres_ceros, it.getEstablecimiento()));
            txtPuntoEmision.setText(String.format(tres_ceros, it.getPuntoemision()));
            txtTimbrado.setText(""+it.getNumerotimbrado());
            txtFechaInicial.setDate(it.getFechainicial());
            txtFechaFinal.setDate(it.getFechafinal());
            txtNumeroInicial.setText(String.format(siete_ceros, it.getNumeroinicial()));
            txtNumeroFinal.setText(String.format(siete_ceros, it.getNumerofinal()));
            
            habilitarCampos(operacion);
        } else {
            JOptionPane.showMessageDialog(null, "SELECCIONE UNA FILA", "ADVERTENCIA", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void buscarImpresora() {
        cargarImpresora();
        BuscadorImpresoraCriterio.setModal(true);
        BuscadorImpresoraCriterio.setSize(540, 285);
        BuscadorImpresoraCriterio.setLocationRelativeTo(this);
        BuscadorImpresoraCriterio.setVisible(true);
        int fila = tablaDatosImpresora.getSelectedRow();
        if (fila >= 0) {
            txtCodigoImpresoraCriterio.setText(tablaDatosImpresora.getValueAt(fila, 0).toString());
            txtDescripcionImpresoraCriterio.setText(tablaDatosImpresora.getValueAt(fila, 1).toString());
        } else {
            txtCodigoImpresoraCriterio.setText(null);
            txtDescripcionImpresoraCriterio.setText(null);
        }
    }

    private void buscarImpresoraDos() {
        cargarImpresoraDos();
        BuscadorImpresora.setModal(true);
        BuscadorImpresora.setSize(540, 285);
        BuscadorImpresora.setLocationRelativeTo(this);
        BuscadorImpresora.setVisible(true);
        int fila = tablaDatosImpresoraDos.getSelectedRow();
        if (fila >= 0) {
            txtCodigoImpresora.setText(tablaDatosImpresoraDos.getValueAt(fila, 0).toString());
            txtDescripcionImpresora.setText(tablaDatosImpresoraDos.getValueAt(fila, 1).toString());
        } else {
            txtCodigoImpresora.setText(null);
            txtDescripcionImpresora.setText(null);
        }
    }

    private void buscarTipoComprobante() {
        cargarTipoComprobante();
        BuscadorTipoComprobante.setModal(true);
        BuscadorTipoComprobante.setSize(540, 285);
        BuscadorTipoComprobante.setLocationRelativeTo(this);
        BuscadorTipoComprobante.setVisible(true);
        int fila = tablaDatosTipoComprobante.getSelectedRow();
        if (fila >= 0) {
            txtCodigoTipoComprobante.setText(tablaDatosTipoComprobante.getValueAt(fila, 0).toString());
            txtDescripcionTipoComprobante.setText(tablaDatosTipoComprobante.getValueAt(fila, 1).toString());
        } else {
            txtCodigoTipoComprobante.setText(null);
            txtDescripcionTipoComprobante.setText(null);
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
        Eliminar = new javax.swing.JMenuItem();
        Modificar = new javax.swing.JMenuItem();
        BuscadorImpresoraCriterio = new javax.swing.JDialog();
        jPanel3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        txtCriterioImpresora = new org.jdesktop.swingx.JXTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaDatosImpresora = new javax.swing.JTable();
        BuscadorImpresora = new javax.swing.JDialog();
        jPanel4 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        txtCriterioImpresoraDos = new org.jdesktop.swingx.JXTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaDatosImpresoraDos = new javax.swing.JTable();
        BuscadorTipoComprobante = new javax.swing.JDialog();
        jPanel5 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        txtCriterioTipoComprobante = new org.jdesktop.swingx.JXTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        tablaDatosTipoComprobante = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        pestanha = new javax.swing.JTabbedPane();
        pestanhaLista = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaDatos = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        txtCodigoImpresoraCriterio = new org.jdesktop.swingx.JXTextField();
        txtDescripcionImpresoraCriterio = new org.jdesktop.swingx.JXTextField();
        pestanhaABM = new javax.swing.JPanel();
        btnCancelar = new javax.swing.JButton();
        btnConfirmar = new javax.swing.JButton();
        btnNuevo = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        txtCodigoImpresora = new org.jdesktop.swingx.JXTextField();
        txtDescripcionImpresora = new org.jdesktop.swingx.JXTextField();
        jLabel7 = new javax.swing.JLabel();
        txtCodigoTipoComprobante = new org.jdesktop.swingx.JXTextField();
        txtDescripcionTipoComprobante = new org.jdesktop.swingx.JXTextField();
        jLabel2 = new javax.swing.JLabel();
        txtCodigo = new org.jdesktop.swingx.JXTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtEstablecimiento = new org.jdesktop.swingx.JXTextField();
        txtPuntoEmision = new org.jdesktop.swingx.JXTextField();
        jLabel12 = new javax.swing.JLabel();
        txtTimbrado = new org.jdesktop.swingx.JXTextField();
        jLabel13 = new javax.swing.JLabel();
        txtFechaInicial = new org.jdesktop.swingx.JXDatePicker();
        txtFechaFinal = new org.jdesktop.swingx.JXDatePicker();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtNumeroInicial = new org.jdesktop.swingx.JXTextField();
        jLabel16 = new javax.swing.JLabel();
        txtNumeroFinal = new org.jdesktop.swingx.JXTextField();

        Eliminar.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        Eliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/icons8_delete_file_16px.png"))); // NOI18N
        Eliminar.setText("Eliminar");
        Eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EliminarActionPerformed(evt);
            }
        });
        menuDesplegable.add(Eliminar);

        Modificar.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        Modificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/icons8_edit_file_16px.png"))); // NOI18N
        Modificar.setText("Modificar");
        Modificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ModificarActionPerformed(evt);
            }
        });
        menuDesplegable.add(Modificar);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel5.setBackground(new java.awt.Color(50, 104, 151));
        jLabel5.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("BUSCADOR DE IMPRESORAS");
        jLabel5.setOpaque(true);

        txtCriterioImpresora.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtCriterioImpresora.setPrompt("Aqui puede ingresar los filtros para la busqueda..");
        txtCriterioImpresora.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCriterioImpresoraActionPerformed(evt);
            }
        });
        txtCriterioImpresora.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCriterioImpresoraKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCriterioImpresoraKeyTyped(evt);
            }
        });

        tablaDatosImpresora.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        tablaDatosImpresora.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "<html><p style=\"text-align:center\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Código</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Descripcion</span></span></span></p></html> "
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
        tablaDatosImpresora.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaDatosImpresoraMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tablaDatosImpresora);
        if (tablaDatosImpresora.getColumnModel().getColumnCount() > 0) {
            tablaDatosImpresora.getColumnModel().getColumn(0).setMinWidth(60);
            tablaDatosImpresora.getColumnModel().getColumn(0).setPreferredWidth(60);
            tablaDatosImpresora.getColumnModel().getColumn(0).setMaxWidth(60);
        }

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCriterioImpresora, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCriterioImpresora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout BuscadorImpresoraCriterioLayout = new javax.swing.GroupLayout(BuscadorImpresoraCriterio.getContentPane());
        BuscadorImpresoraCriterio.getContentPane().setLayout(BuscadorImpresoraCriterioLayout);
        BuscadorImpresoraCriterioLayout.setHorizontalGroup(
            BuscadorImpresoraCriterioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BuscadorImpresoraCriterioLayout.setVerticalGroup(
            BuscadorImpresoraCriterioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jLabel8.setBackground(new java.awt.Color(50, 104, 151));
        jLabel8.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("BUSCADOR DE IMPRESORAS");
        jLabel8.setOpaque(true);

        txtCriterioImpresoraDos.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtCriterioImpresoraDos.setPrompt("Aqui puede ingresar los filtros para la busqueda..");
        txtCriterioImpresoraDos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCriterioImpresoraDosActionPerformed(evt);
            }
        });
        txtCriterioImpresoraDos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCriterioImpresoraDosKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCriterioImpresoraDosKeyTyped(evt);
            }
        });

        tablaDatosImpresoraDos.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        tablaDatosImpresoraDos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "<html><p style=\"text-align:center\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Código</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Descripcion</span></span></span></p></html> "
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
        tablaDatosImpresoraDos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaDatosImpresoraDosMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tablaDatosImpresoraDos);
        if (tablaDatosImpresoraDos.getColumnModel().getColumnCount() > 0) {
            tablaDatosImpresoraDos.getColumnModel().getColumn(0).setMinWidth(60);
            tablaDatosImpresoraDos.getColumnModel().getColumn(0).setPreferredWidth(60);
            tablaDatosImpresoraDos.getColumnModel().getColumn(0).setMaxWidth(60);
        }

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCriterioImpresoraDos, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCriterioImpresoraDos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout BuscadorImpresoraLayout = new javax.swing.GroupLayout(BuscadorImpresora.getContentPane());
        BuscadorImpresora.getContentPane().setLayout(BuscadorImpresoraLayout);
        BuscadorImpresoraLayout.setHorizontalGroup(
            BuscadorImpresoraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BuscadorImpresoraLayout.setVerticalGroup(
            BuscadorImpresoraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jLabel9.setBackground(new java.awt.Color(50, 104, 151));
        jLabel9.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("BUSCADOR DE TIPOS DE COMPROBANTES");
        jLabel9.setOpaque(true);

        txtCriterioTipoComprobante.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtCriterioTipoComprobante.setPrompt("Aqui puede ingresar los filtros para la busqueda..");
        txtCriterioTipoComprobante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCriterioTipoComprobanteActionPerformed(evt);
            }
        });
        txtCriterioTipoComprobante.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCriterioTipoComprobanteKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCriterioTipoComprobanteKeyTyped(evt);
            }
        });

        tablaDatosTipoComprobante.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        tablaDatosTipoComprobante.setModel(new javax.swing.table.DefaultTableModel(
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
        tablaDatosTipoComprobante.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaDatosTipoComprobanteMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tablaDatosTipoComprobante);
        if (tablaDatosTipoComprobante.getColumnModel().getColumnCount() > 0) {
            tablaDatosTipoComprobante.getColumnModel().getColumn(0).setMinWidth(60);
            tablaDatosTipoComprobante.getColumnModel().getColumn(0).setPreferredWidth(60);
            tablaDatosTipoComprobante.getColumnModel().getColumn(0).setMaxWidth(60);
        }

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCriterioTipoComprobante, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCriterioTipoComprobante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout BuscadorTipoComprobanteLayout = new javax.swing.GroupLayout(BuscadorTipoComprobante.getContentPane());
        BuscadorTipoComprobante.getContentPane().setLayout(BuscadorTipoComprobanteLayout);
        BuscadorTipoComprobanteLayout.setHorizontalGroup(
            BuscadorTipoComprobanteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BuscadorTipoComprobanteLayout.setVerticalGroup(
            BuscadorTipoComprobanteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setClosable(true);
        setIconifiable(true);

        jPanel1.setBackground(new java.awt.Color(50, 104, 151));

        jLabel1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Mantenimiento de Timbrados de Impresoras");

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

        tablaDatos.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        tablaDatos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "<html><p style=\"text-align:center\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Código</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Impresora</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Cod.Timbrado</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Cod.TipoComprobante</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">T.Comprobante</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Est.</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">P.Emi.</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">N°Timbrado</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">N°Inicial</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">N°Final</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Fec.Inicial.</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Fec.Final</span></span></span></p></html> "
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false
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
            tablaDatos.getColumnModel().getColumn(2).setMinWidth(0);
            tablaDatos.getColumnModel().getColumn(2).setPreferredWidth(0);
            tablaDatos.getColumnModel().getColumn(2).setMaxWidth(0);
            tablaDatos.getColumnModel().getColumn(3).setMinWidth(0);
            tablaDatos.getColumnModel().getColumn(3).setPreferredWidth(0);
            tablaDatos.getColumnModel().getColumn(3).setMaxWidth(0);
            tablaDatos.getColumnModel().getColumn(5).setMinWidth(55);
            tablaDatos.getColumnModel().getColumn(5).setPreferredWidth(55);
            tablaDatos.getColumnModel().getColumn(5).setMaxWidth(55);
            tablaDatos.getColumnModel().getColumn(6).setMinWidth(55);
            tablaDatos.getColumnModel().getColumn(6).setPreferredWidth(55);
            tablaDatos.getColumnModel().getColumn(6).setMaxWidth(55);
            tablaDatos.getColumnModel().getColumn(7).setMinWidth(85);
            tablaDatos.getColumnModel().getColumn(7).setPreferredWidth(85);
            tablaDatos.getColumnModel().getColumn(7).setMaxWidth(85);
            tablaDatos.getColumnModel().getColumn(8).setMinWidth(65);
            tablaDatos.getColumnModel().getColumn(8).setPreferredWidth(65);
            tablaDatos.getColumnModel().getColumn(8).setMaxWidth(65);
            tablaDatos.getColumnModel().getColumn(9).setMinWidth(65);
            tablaDatos.getColumnModel().getColumn(9).setPreferredWidth(65);
            tablaDatos.getColumnModel().getColumn(9).setMaxWidth(65);
            tablaDatos.getColumnModel().getColumn(10).setMinWidth(80);
            tablaDatos.getColumnModel().getColumn(10).setPreferredWidth(80);
            tablaDatos.getColumnModel().getColumn(10).setMaxWidth(80);
            tablaDatos.getColumnModel().getColumn(11).setMinWidth(80);
            tablaDatos.getColumnModel().getColumn(11).setPreferredWidth(80);
            tablaDatos.getColumnModel().getColumn(11).setMaxWidth(80);
        }

        jLabel6.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel6.setText("Impresora:");

        txtCodigoImpresoraCriterio.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtCodigoImpresoraCriterio.setPrompt("Cód. Impresora");
        txtCodigoImpresoraCriterio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoImpresoraCriterioActionPerformed(evt);
            }
        });
        txtCodigoImpresoraCriterio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodigoImpresoraCriterioKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodigoImpresoraCriterioKeyTyped(evt);
            }
        });

        txtDescripcionImpresoraCriterio.setEnabled(false);
        txtDescripcionImpresoraCriterio.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtDescripcionImpresoraCriterio.setPrompt("Descripción o nombre de la impresora...");

        javax.swing.GroupLayout pestanhaListaLayout = new javax.swing.GroupLayout(pestanhaLista);
        pestanhaLista.setLayout(pestanhaListaLayout);
        pestanhaListaLayout.setHorizontalGroup(
            pestanhaListaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pestanhaListaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pestanhaListaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 891, Short.MAX_VALUE)
                    .addGroup(pestanhaListaLayout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCodigoImpresoraCriterio, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDescripcionImpresoraCriterio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pestanhaListaLayout.setVerticalGroup(
            pestanhaListaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pestanhaListaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pestanhaListaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtCodigoImpresoraCriterio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDescripcionImpresoraCriterio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pestanha.addTab("Listado", pestanhaLista);

        pestanhaABM.setBackground(new java.awt.Color(255, 255, 255));

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
        jLabel4.setText("Impresora:");

        txtCodigoImpresora.setEnabled(false);
        txtCodigoImpresora.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtCodigoImpresora.setPrompt("Cód. Impresora");
        txtCodigoImpresora.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoImpresoraActionPerformed(evt);
            }
        });
        txtCodigoImpresora.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodigoImpresoraKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodigoImpresoraKeyTyped(evt);
            }
        });

        txtDescripcionImpresora.setEnabled(false);
        txtDescripcionImpresora.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtDescripcionImpresora.setPrompt("Descripción o nombre de la impresora...");
        txtDescripcionImpresora.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDescripcionImpresoraActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel7.setText("T. Comprobante:");

        txtCodigoTipoComprobante.setEnabled(false);
        txtCodigoTipoComprobante.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtCodigoTipoComprobante.setPrompt("Cód. T. Comp.");
        txtCodigoTipoComprobante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoTipoComprobanteActionPerformed(evt);
            }
        });
        txtCodigoTipoComprobante.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodigoTipoComprobanteKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodigoTipoComprobanteKeyTyped(evt);
            }
        });

        txtDescripcionTipoComprobante.setEnabled(false);
        txtDescripcionTipoComprobante.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtDescripcionTipoComprobante.setPrompt("Descripción o nombre del tipo de comprobante...");

        jLabel2.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel2.setText("Código:");

        txtCodigo.setEnabled(false);
        txtCodigo.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtCodigo.setPrompt("Código interno...");

        jLabel10.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel10.setText("Establecimiento:");

        jLabel11.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel11.setText("Punto Emision:");

        txtEstablecimiento.setEnabled(false);
        txtEstablecimiento.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtEstablecimiento.setPrompt("Ejemplo: 001");
        txtEstablecimiento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEstablecimientoActionPerformed(evt);
            }
        });
        txtEstablecimiento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtEstablecimientoKeyTyped(evt);
            }
        });

        txtPuntoEmision.setEnabled(false);
        txtPuntoEmision.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtPuntoEmision.setPrompt("Ejemplo: 001");
        txtPuntoEmision.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPuntoEmisionActionPerformed(evt);
            }
        });
        txtPuntoEmision.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPuntoEmisionKeyTyped(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel12.setText("N° Timbrado:");

        txtTimbrado.setEnabled(false);
        txtTimbrado.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtTimbrado.setPrompt("Ejemplo: 88888888");
        txtTimbrado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimbradoActionPerformed(evt);
            }
        });
        txtTimbrado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTimbradoKeyTyped(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel13.setText("Fecha Inicial:");

        txtFechaInicial.setEnabled(false);
        txtFechaInicial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFechaInicialActionPerformed(evt);
            }
        });

        txtFechaFinal.setEnabled(false);
        txtFechaFinal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFechaFinalActionPerformed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel14.setText("Fecha Final:");

        jLabel15.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel15.setText("N° Inicial:");

        txtNumeroInicial.setEnabled(false);
        txtNumeroInicial.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtNumeroInicial.setPrompt("Ejemplo: 0000001");
        txtNumeroInicial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumeroInicialActionPerformed(evt);
            }
        });
        txtNumeroInicial.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNumeroInicialKeyTyped(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel16.setText("N° Final:");

        txtNumeroFinal.setEnabled(false);
        txtNumeroFinal.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtNumeroFinal.setPrompt("Ejemplo: 0001000");
        txtNumeroFinal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumeroFinalActionPerformed(evt);
            }
        });
        txtNumeroFinal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNumeroFinalKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout pestanhaABMLayout = new javax.swing.GroupLayout(pestanhaABM);
        pestanhaABM.setLayout(pestanhaABMLayout);
        pestanhaABMLayout.setHorizontalGroup(
            pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pestanhaABMLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pestanhaABMLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnConfirmar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pestanhaABMLayout.createSequentialGroup()
                        .addGroup(pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pestanhaABMLayout.createSequentialGroup()
                                .addComponent(txtCodigoTipoComprobante, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtDescripcionTipoComprobante, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(pestanhaABMLayout.createSequentialGroup()
                                .addGroup(pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pestanhaABMLayout.createSequentialGroup()
                                        .addComponent(txtEstablecimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel11)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtPuntoEmision, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                                        .addComponent(jLabel12))
                                    .addGroup(pestanhaABMLayout.createSequentialGroup()
                                        .addGroup(pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(pestanhaABMLayout.createSequentialGroup()
                                                .addComponent(jLabel16)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(txtNumeroFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(pestanhaABMLayout.createSequentialGroup()
                                                .addGroup(pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(pestanhaABMLayout.createSequentialGroup()
                                                        .addGap(243, 243, 243)
                                                        .addComponent(jLabel14))
                                                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(txtFechaInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(txtFechaFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(0, 0, Short.MAX_VALUE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtTimbrado, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pestanhaABMLayout.createSequentialGroup()
                                .addComponent(txtCodigoImpresora, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtDescripcionImpresora, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(pestanhaABMLayout.createSequentialGroup()
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNumeroInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
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
                    .addComponent(jLabel4)
                    .addComponent(txtCodigoImpresora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDescripcionImpresora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtCodigoTipoComprobante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDescripcionTipoComprobante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtEstablecimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(txtPuntoEmision, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTimbrado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFechaInicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtFechaFinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16)
                    .addComponent(txtNumeroFinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel15)
                        .addComponent(txtNumeroInicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 118, Short.MAX_VALUE)
                .addGroup(pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnConfirmar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pestanha.addTab("Operaciónes", pestanhaABM);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
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

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        limpiarCampos();
        operacion = "NUEVO";
        habilitarCampos(operacion);
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        operacion = "CANCELAR";
        habilitarCampos(operacion);
        limpiarCampos();
        cargar();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmarActionPerformed
        int res = JOptionPane.showConfirmDialog(null, "¿ESTA SEGURO DE CONFIRMAR LOS CAMBIOS?", "ADVERTENCIA", JOptionPane.YES_NO_OPTION);
        if (res != 1) {
            guardar(operacion);
            habilitarCampos("GUARDAR");
            limpiarCampos();
            cargar();
        }
    }//GEN-LAST:event_btnConfirmarActionPerformed

    private void EliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EliminarActionPerformed
        operacion = "ELIMINAR";
        recuperarDatos();
    }//GEN-LAST:event_EliminarActionPerformed

    private void txtCodigoImpresoraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoImpresoraActionPerformed
        if (txtCodigoImpresora.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "NO PUEDE DEJAR EL CAMPO DE IMPRESORA VACIO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else {
            int idimpresora = Integer.parseInt(txtCodigoImpresora.getText());
            i.setIdimpresora(idimpresora);
            boolean resultado = daoImpresora.consultarDatos(i);
            if (resultado == true) {
                txtDescripcionImpresora.setText(i.getDescripcion());
                txtCodigoTipoComprobante.grabFocus();
            } else {
                txtCodigoImpresora.setText(null);
                txtDescripcionImpresora.setText(null);
                txtCodigoImpresora.grabFocus();
            }
        }
    }//GEN-LAST:event_txtCodigoImpresoraActionPerformed

    private void txtCodigoImpresoraKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoImpresoraKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            getToolkit().beep();
            evt.consume();
        }
        if (txtCodigoImpresora.getText().length() == 10) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCodigoImpresoraKeyTyped

    private void txtCriterioImpresoraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCriterioImpresoraActionPerformed
        cargarImpresora();
    }//GEN-LAST:event_txtCriterioImpresoraActionPerformed

    private void txtCriterioImpresoraKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCriterioImpresoraKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            evt.setKeyChar(Character.toUpperCase(c));
        }
        if (txtCriterioImpresora.getText().length() == 100) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCriterioImpresoraKeyTyped

    private void txtCriterioImpresoraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCriterioImpresoraKeyPressed
        if (evt.VK_ESCAPE == evt.getKeyCode()) {
            txtCodigoImpresora.setText(null);
            txtDescripcionImpresora.setText(null);
            txtCodigoImpresora.grabFocus();
            BuscadorImpresoraCriterio.dispose();
        }
    }//GEN-LAST:event_txtCriterioImpresoraKeyPressed

    private void tablaDatosImpresoraMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaDatosImpresoraMouseClicked
        if (evt.getClickCount() == 2) {
            if (tablaDatosImpresora.getSelectedRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "SELECCIONE UNA FILA");
            } else {
                txtCriterioImpresora.setText(null);
                BuscadorImpresoraCriterio.dispose();
            }
        }
    }//GEN-LAST:event_tablaDatosImpresoraMouseClicked

    private void txtCodigoImpresoraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoImpresoraKeyPressed
        if (evt.VK_F1 == evt.getKeyCode()) {
            buscarImpresoraDos();
        }
    }//GEN-LAST:event_txtCodigoImpresoraKeyPressed

    private void txtCodigoImpresoraCriterioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoImpresoraCriterioActionPerformed
        if (txtCodigoImpresoraCriterio.getText().isEmpty()) {
            txtCodigoImpresoraCriterio.setText(null);
            txtDescripcionImpresoraCriterio.setText(null);
            txtCodigoImpresoraCriterio.grabFocus();
        } else {
            int idimpresora = Integer.parseInt(txtCodigoImpresoraCriterio.getText());
            i.setIdimpresora(idimpresora);
            boolean resultado = daoImpresora.consultarDatos(i);
            if (resultado == true) {
                txtDescripcionImpresoraCriterio.setText(i.getDescripcion());
                cargar();
            } else {
                txtCodigoImpresoraCriterio.setText(null);
                txtDescripcionImpresoraCriterio.setText(null);
                txtCodigoImpresoraCriterio.grabFocus();
                cargar();
            }
        }
    }//GEN-LAST:event_txtCodigoImpresoraCriterioActionPerformed

    private void txtCodigoImpresoraCriterioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoImpresoraCriterioKeyPressed
        if (evt.VK_F1 == evt.getKeyCode()) {
            buscarImpresora();
        }
    }//GEN-LAST:event_txtCodigoImpresoraCriterioKeyPressed

    private void txtCodigoImpresoraCriterioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoImpresoraCriterioKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            getToolkit().beep();
            evt.consume();
        }
        if (txtCodigoImpresoraCriterio.getText().length() == 10) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCodigoImpresoraCriterioKeyTyped

    private void txtCodigoTipoComprobanteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoTipoComprobanteActionPerformed
        if (txtCodigoTipoComprobante.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "NO PUEDE DEJAR EL CAMPO DE TIPO DE COMPROBANTE VACIO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else {
            int idtipocomprobante = Integer.parseInt(txtCodigoTipoComprobante.getText());
            tc.setIdtipo(idtipocomprobante);
            boolean resultado = daoTipoComprobante.consultarDatos(tc);
            if (resultado == true) {
                txtDescripcionTipoComprobante.setText(tc.getDescripcion());
                txtEstablecimiento.grabFocus();
            } else {
                txtCodigoTipoComprobante.setText(null);
                txtDescripcionTipoComprobante.setText(null);
                txtCodigoTipoComprobante.grabFocus();
            }
        }
    }//GEN-LAST:event_txtCodigoTipoComprobanteActionPerformed

    private void txtCodigoTipoComprobanteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoTipoComprobanteKeyPressed
        if (evt.VK_F1 == evt.getKeyCode()) {
            buscarTipoComprobante();
        }
    }//GEN-LAST:event_txtCodigoTipoComprobanteKeyPressed

    private void txtCodigoTipoComprobanteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoTipoComprobanteKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            getToolkit().beep();
            evt.consume();
        }
        if (txtCodigoTipoComprobante.getText().length() == 10) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCodigoTipoComprobanteKeyTyped

    private void txtCriterioImpresoraDosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCriterioImpresoraDosActionPerformed
        cargarImpresoraDos();
    }//GEN-LAST:event_txtCriterioImpresoraDosActionPerformed

    private void txtCriterioImpresoraDosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCriterioImpresoraDosKeyPressed
        if (evt.VK_ESCAPE == evt.getKeyCode()) {
            txtCodigoImpresora.setText(null);
            txtDescripcionImpresora.setText(null);
            txtCodigoImpresora.grabFocus();
            BuscadorImpresora.dispose();
        }
    }//GEN-LAST:event_txtCriterioImpresoraDosKeyPressed

    private void txtCriterioImpresoraDosKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCriterioImpresoraDosKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            evt.setKeyChar(Character.toUpperCase(c));
        }
        if (txtCriterioImpresoraDos.getText().length() == 100) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCriterioImpresoraDosKeyTyped

    private void tablaDatosImpresoraDosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaDatosImpresoraDosMouseClicked
        if (evt.getClickCount() == 2) {
            if (tablaDatosImpresoraDos.getSelectedRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "SELECCIONE UNA FILA");
            } else {
                txtCriterioImpresoraDos.setText(null);
                BuscadorImpresora.dispose();
            }
        }
    }//GEN-LAST:event_tablaDatosImpresoraDosMouseClicked

    private void txtCriterioTipoComprobanteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCriterioTipoComprobanteActionPerformed
        cargarTipoComprobante();
    }//GEN-LAST:event_txtCriterioTipoComprobanteActionPerformed

    private void txtCriterioTipoComprobanteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCriterioTipoComprobanteKeyPressed
        if (evt.VK_ESCAPE == evt.getKeyCode()) {
            txtCodigoTipoComprobante.setText(null);
            txtDescripcionTipoComprobante.setText(null);
            txtCodigoTipoComprobante.grabFocus();
            BuscadorTipoComprobante.dispose();
        }
    }//GEN-LAST:event_txtCriterioTipoComprobanteKeyPressed

    private void txtCriterioTipoComprobanteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCriterioTipoComprobanteKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            evt.setKeyChar(Character.toUpperCase(c));
        }
        if (txtCriterioTipoComprobante.getText().length() == 100) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCriterioTipoComprobanteKeyTyped

    private void tablaDatosTipoComprobanteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaDatosTipoComprobanteMouseClicked
        if (evt.getClickCount() == 2) {
            if (tablaDatosTipoComprobante.getSelectedRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "SELECCIONE UNA FILA");
            } else {
                txtCriterioTipoComprobante.setText(null);
                BuscadorTipoComprobante.dispose();
            }
        }
    }//GEN-LAST:event_tablaDatosTipoComprobanteMouseClicked

    private void txtEstablecimientoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEstablecimientoActionPerformed
        if (txtEstablecimiento.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "NO PUEDE DEJAR EL CAMPO VACIO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else {
            txtEstablecimiento.setText(String.format(tres_ceros, Integer.parseInt(txtEstablecimiento.getText())));
            txtPuntoEmision.grabFocus();
        }
    }//GEN-LAST:event_txtEstablecimientoActionPerformed

    private void txtEstablecimientoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEstablecimientoKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            getToolkit().beep();
            evt.consume();
        }
        if (txtEstablecimiento.getText().length() == 3) {
            evt.consume();
        }
    }//GEN-LAST:event_txtEstablecimientoKeyTyped

    private void txtPuntoEmisionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPuntoEmisionActionPerformed
        if (txtPuntoEmision.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "NO PUEDE DEJAR EL CAMPO VACIO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else {
            txtPuntoEmision.setText(String.format(tres_ceros, Integer.parseInt(txtPuntoEmision.getText())));
            txtTimbrado.grabFocus();
        }
    }//GEN-LAST:event_txtPuntoEmisionActionPerformed

    private void txtPuntoEmisionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPuntoEmisionKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            getToolkit().beep();
            evt.consume();
        }
        if (txtPuntoEmision.getText().length() == 3) {
            evt.consume();
        }
    }//GEN-LAST:event_txtPuntoEmisionKeyTyped

    private void txtTimbradoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimbradoActionPerformed
        if (txtTimbrado.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "NO PUEDE DEJAR EL CAMPO VACIO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else {
            txtFechaInicial.grabFocus();
        }
    }//GEN-LAST:event_txtTimbradoActionPerformed

    private void txtTimbradoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimbradoKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            getToolkit().beep();
            evt.consume();
        }
        if (txtTimbrado.getText().length() == 10) {
            evt.consume();
        }
    }//GEN-LAST:event_txtTimbradoKeyTyped

    private void txtFechaInicialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFechaInicialActionPerformed
        if (txtFechaInicial.getDate() == null) {
            JOptionPane.showMessageDialog(null, "NO PUEDE DEJAR EL CAMPO DE FECHA INICIAL VACIO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else {
            txtFechaFinal.grabFocus();
        }
    }//GEN-LAST:event_txtFechaInicialActionPerformed

    private void txtFechaFinalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFechaFinalActionPerformed
        if (txtFechaInicial.getDate() == null) {
            JOptionPane.showMessageDialog(null, "NO PUEDE DEJAR EL CAMPO DE FECHA FINAL VACIO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else {
            txtNumeroInicial.grabFocus();
        }
    }//GEN-LAST:event_txtFechaFinalActionPerformed

    private void txtDescripcionImpresoraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDescripcionImpresoraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescripcionImpresoraActionPerformed

    private void txtNumeroInicialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumeroInicialActionPerformed
        if (txtNumeroInicial.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "NO PUEDE DEJAR EL CAMPO VACIO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else {
            txtNumeroInicial.setText(String.format(siete_ceros, Integer.parseInt(txtNumeroInicial.getText())));
            txtNumeroFinal.grabFocus();
        }
    }//GEN-LAST:event_txtNumeroInicialActionPerformed

    private void txtNumeroInicialKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumeroInicialKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            getToolkit().beep();
            evt.consume();
        }
        if (txtNumeroInicial.getText().length() == 7) {
            evt.consume();
        }
    }//GEN-LAST:event_txtNumeroInicialKeyTyped

    private void txtNumeroFinalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumeroFinalActionPerformed
        if (txtNumeroFinal.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "NO PUEDE DEJAR EL CAMPO VACIO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else {
            txtNumeroFinal.setText(String.format(siete_ceros, Integer.parseInt(txtNumeroFinal.getText())));
            btnConfirmar.grabFocus();
        }
    }//GEN-LAST:event_txtNumeroFinalActionPerformed

    private void txtNumeroFinalKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumeroFinalKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNumeroFinalKeyTyped

    private void ModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ModificarActionPerformed
        operacion = "MODIFICAR";
        recuperarDatos();
    }//GEN-LAST:event_ModificarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDialog BuscadorImpresora;
    private javax.swing.JDialog BuscadorImpresoraCriterio;
    private javax.swing.JDialog BuscadorTipoComprobante;
    private javax.swing.JMenuItem Eliminar;
    private javax.swing.JMenuItem Modificar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnConfirmar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
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
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JPopupMenu menuDesplegable;
    private javax.swing.JTabbedPane pestanha;
    private javax.swing.JPanel pestanhaABM;
    private javax.swing.JPanel pestanhaLista;
    private javax.swing.JTable tablaDatos;
    private javax.swing.JTable tablaDatosImpresora;
    private javax.swing.JTable tablaDatosImpresoraDos;
    private javax.swing.JTable tablaDatosTipoComprobante;
    private org.jdesktop.swingx.JXTextField txtCodigo;
    private org.jdesktop.swingx.JXTextField txtCodigoImpresora;
    private org.jdesktop.swingx.JXTextField txtCodigoImpresoraCriterio;
    private org.jdesktop.swingx.JXTextField txtCodigoTipoComprobante;
    private org.jdesktop.swingx.JXTextField txtCriterioImpresora;
    private org.jdesktop.swingx.JXTextField txtCriterioImpresoraDos;
    private org.jdesktop.swingx.JXTextField txtCriterioTipoComprobante;
    private org.jdesktop.swingx.JXTextField txtDescripcionImpresora;
    private org.jdesktop.swingx.JXTextField txtDescripcionImpresoraCriterio;
    private org.jdesktop.swingx.JXTextField txtDescripcionTipoComprobante;
    private org.jdesktop.swingx.JXTextField txtEstablecimiento;
    private org.jdesktop.swingx.JXDatePicker txtFechaFinal;
    private org.jdesktop.swingx.JXDatePicker txtFechaInicial;
    private org.jdesktop.swingx.JXTextField txtNumeroFinal;
    private org.jdesktop.swingx.JXTextField txtNumeroInicial;
    private org.jdesktop.swingx.JXTextField txtPuntoEmision;
    private org.jdesktop.swingx.JXTextField txtTimbrado;
    // End of variables declaration//GEN-END:variables
}
