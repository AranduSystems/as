package Vistas;

import Dao.DAOCaja;
import Dao.DAOCotizacion;
import Dao.DAOTimbrado;
import Dao.DAOTipoComprobante;
import Modelos.Caja;
import Modelos.Timbrado;
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
public class JFrmTimbrado extends javax.swing.JInternalFrame {

    Timbrado t = new Timbrado();
    DAOTimbrado dao = new DAOTimbrado();
    ArrayList<Object[]> datos = new ArrayList<>();

    Caja c = new Caja();
    DAOCaja daoCaja = new DAOCaja();
    ArrayList<Object[]> datosCaja = new ArrayList<>();

    TipoComprobante tm = new TipoComprobante();
    DAOTipoComprobante daoTipoComprobante = new DAOTipoComprobante();
    ArrayList<Object[]> datosTipoComprobante = new ArrayList<>();
    
    DAOCotizacion daoCotizacion = new DAOCotizacion();

    //VARIABLE QUE MANEJA QUE TIPOS DE OPERACIONES SE REALIZARAN: SI VA A SER ALTA, BAJA O MODIFICACION DEL REGISTRO
    String operacion = "";
    String tres_ceros = String.format("%%0%dd", 3);
    String siete_ceros = String.format("%%0%dd", 7);
    SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

    /**
     * Creates new form JFrmTimbrado
     */
    public JFrmTimbrado() {
        setTitle("JFrmTimbrado");
        initComponents();
        cargar();
        txtFechaInicial.setFormats(formato);
        txtFechaFinal.setFormats(formato);
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

    public void cargarCaja() {
        DefaultTableModel modelo = (DefaultTableModel) tablaDatosCaja.getModel();
        modelo.setRowCount(0);
        datosCaja = daoCaja.consultar(txtCriterioCaja.getText());
        for (Object[] obj : datosCaja) {
            modelo.addRow(obj);
        }
        this.tablaDatos.setModel(modelo);
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
                txtCodigo.setEnabled(false);
                txtPuntoEmision.setEnabled(true);
                txtEstablecimiento.setEnabled(true);
                txtNumeroInicial.setEnabled(true);
                txtNumeroFinal.setEnabled(true);
                txtFechaInicial.setEnabled(true);
                txtFechaFinal.setEnabled(true);
                txtCodigoCaja.setEnabled(true);
                txtCodigoTipoComprobante.setEnabled(true);
                txtTimbrado.setEnabled(true);
                //BOTONES
                btnNuevo.setEnabled(false);
                btnConfirmar.setEnabled(true);
                btnCancelar.setEnabled(true);
                //REDIRECIONAMOS
                txtPuntoEmision.grabFocus();
                break;
            case "MODIFICAR":
                //CAMPOS
                txtCodigo.setEnabled(false);
                txtPuntoEmision.setEnabled(true);
                txtEstablecimiento.setEnabled(true);
                txtNumeroInicial.setEnabled(true);
                txtNumeroFinal.setEnabled(true);
                txtFechaInicial.setEnabled(true);
                txtFechaFinal.setEnabled(true);
                txtCodigoCaja.setEnabled(true);
                txtCodigoTipoComprobante.setEnabled(true);
                txtTimbrado.setEnabled(true);
                //BOTONES
                btnNuevo.setEnabled(false);
                btnConfirmar.setEnabled(true);
                btnCancelar.setEnabled(true);
                //REDIRECIONAMOS
                pestanha.setSelectedIndex(1);
                txtPuntoEmision.grabFocus();
                break;
            case "ELIMINAR":
                //CAMPOS
                txtCodigo.setEnabled(false);
                txtPuntoEmision.setEnabled(false);
                txtEstablecimiento.setEnabled(false);
                txtNumeroInicial.setEnabled(false);
                txtNumeroFinal.setEnabled(false);
                txtFechaInicial.setEnabled(false);
                txtFechaFinal.setEnabled(false);
                txtCodigoCaja.setEnabled(false);
                txtCodigoTipoComprobante.setEnabled(false);
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
                txtCodigo.setEnabled(false);
                txtPuntoEmision.setEnabled(false);
                txtEstablecimiento.setEnabled(false);
                txtNumeroInicial.setEnabled(false);
                txtNumeroFinal.setEnabled(false);
                txtFechaInicial.setEnabled(false);
                txtFechaFinal.setEnabled(false);
                txtCodigoCaja.setEnabled(false);
                txtCodigoTipoComprobante.setEnabled(false);
                txtTimbrado.setEnabled(false);
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
                txtPuntoEmision.setEnabled(false);
                txtEstablecimiento.setEnabled(false);
                txtNumeroInicial.setEnabled(false);
                txtNumeroFinal.setEnabled(false);
                txtFechaInicial.setEnabled(false);
                txtFechaFinal.setEnabled(false);
                txtCodigoCaja.setEnabled(false);
                txtCodigoTipoComprobante.setEnabled(false);
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
        txtCriterio.setText(null);
        txtCodigo.setText(null);
        txtPuntoEmision.setText(null);
        txtEstablecimiento.setText(null);
        txtNumeroInicial.setText(null);
        txtNumeroFinal.setText(null);
        txtFechaInicial.setDate(null);
        txtFechaFinal.setDate(null);
        txtCodigoCaja.setText(null);
        txtDescripcionCaja.setText(null);
        txtCodigoTipoComprobante.setText(null);
        txtDescripcionTipoComprobante.setText(null);
        txtTimbrado.setText(null);
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
        int puntoExpedicion = Integer.parseInt(txtPuntoEmision.getText());
        int establecimiento = Integer.parseInt(txtEstablecimiento.getText());
        int numeroInicial = Integer.parseInt(txtNumeroInicial.getText());
        int numeroFinal = Integer.parseInt(txtNumeroFinal.getText());
        Date fechaInicial = txtFechaInicial.getDate();
        java.sql.Date fechaInicialSQL = new java.sql.Date(fechaInicial.getTime());
        Date fechaFinal = txtFechaFinal.getDate();
        java.sql.Date fechaFinalSQL = new java.sql.Date(fechaFinal.getTime());
        int idcaja = Integer.parseInt(txtCodigoCaja.getText());
        int idtipomovimiento = Integer.parseInt(txtCodigoTipoComprobante.getText());
        int timbrado = Integer.parseInt(txtTimbrado.getText());
        switch (accion) {
            case "NUEVO":
                if (puntoExpedicion == 0) {
                    error += "NO PUEDE DEJAR EL CAMPO DE PUNTO DE EXPEDICIÓN VACIO.\n";
                }
                if (establecimiento == 0) {
                    error += "NO PUEDE DEJAR EL CAMPO DE ESTABLECIMIENTO VACIO.\n";
                }
                if (numeroInicial == 0) {
                    error += "NO PUEDE DEJAR EL CAMPO DE Nº INICIAL VACIO.\n";
                }
                if (numeroFinal == 0) {
                    error += "NO PUEDE DEJAR EL CAMPO DE Nº FINAL VACIO.\n";
                }
                if (fechaInicial == null) {
                    error += "NO PUEDE DEJAR EL CAMPO DE FECHA INICIAL VACIO.\n";
                }
                if (fechaFinal == null) {
                    error += "NO PUEDE DEJAR EL CAMPO DE FECHA FINAL VACIO.\n";
                }
                if (idcaja == 0) {
                    error += "NO PUEDE DEJAR EL CAMPO DE CAJA VACIO.\n";
                }
                if (idtipomovimiento == 0) {
                    error += "NO PUEDE DEJAR EL CAMPO DE TIPO DE COMPROBANTE VACIO.\n";
                }
                if (timbrado == 0) {
                    error += "NO PUEDE DEJAR EL CAMPO DE TIMBRADO VACIO.\n";
                }
                if (error.isEmpty()) {
                    t.setIdtimbrado(id);
                    t.setPuntoemision(puntoExpedicion);
                    t.setEstablecimiento(establecimiento);
                    t.setNumeroinicial(numeroInicial);
                    t.setNumerofinal(numeroFinal);
                    t.setFechainicial(fechaInicialSQL);
                    t.setFechafinal(fechaFinalSQL);
                    t.setIdcaja(idcaja);
                    t.setIdtipocomprobante(idtipomovimiento);
                    t.setTimbrado(timbrado);
                    dao.agregar(t);
                    cargar();
                } else {
                    JOptionPane.showMessageDialog(null, error, "ERRORES", JOptionPane.ERROR_MESSAGE);
                }
                break;
            case "MODIFICAR":
                if (puntoExpedicion == 0) {
                    error += "NO PUEDE DEJAR EL CAMPO DE PUNTO DE EXPEDICIÓN VACIO.\n";
                }
                if (establecimiento == 0) {
                    error += "NO PUEDE DEJAR EL CAMPO DE ESTABLECIMIENTO VACIO.\n";
                }
                if (numeroInicial == 0) {
                    error += "NO PUEDE DEJAR EL CAMPO DE Nº INICIAL VACIO.\n";
                }
                if (numeroFinal == 0) {
                    error += "NO PUEDE DEJAR EL CAMPO DE Nº FINAL VACIO.\n";
                }
                if (fechaInicial == null) {
                    error += "NO PUEDE DEJAR EL CAMPO DE FECHA INICIAL VACIO.\n";
                }
                if (fechaFinal == null) {
                    error += "NO PUEDE DEJAR EL CAMPO DE FECHA FINAL VACIO.\n";
                }
                if (idcaja == 0) {
                    error += "NO PUEDE DEJAR EL CAMPO DE CAJA VACIO.\n";
                }
                if (idtipomovimiento == 0) {
                    error += "NO PUEDE DEJAR EL CAMPO DE TIPO DE MOVIMIENTO VACIO.\n";
                }
                if (timbrado == 0) {
                    error += "NO PUEDE DEJAR EL CAMPO DE TIMBRADO VACIO.\n";
                }
                if (error.isEmpty()) {
                    t.setIdtimbrado(id);
                    t.setPuntoemision(puntoExpedicion);
                    t.setEstablecimiento(establecimiento);
                    t.setNumeroinicial(numeroInicial);
                    t.setNumerofinal(numeroFinal);
                    t.setFechainicial(fechaInicialSQL);
                    t.setFechafinal(fechaFinalSQL);
                    t.setIdcaja(idcaja);
                    t.setIdtipocomprobante(idtipomovimiento);
                    t.setTimbrado(timbrado);
                    dao.modificar(t);
                    cargar();
                } else {
                    JOptionPane.showMessageDialog(null, error, "ERRORES", JOptionPane.ERROR_MESSAGE);
                }
                break;
            case "ELIMINAR":
                if (error.isEmpty()) {
                    t.setIdtimbrado(id);
                    dao.eliminar(t);
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
            String fechaInicial = tablaDatos.getValueAt(fila, 6).toString();
            String fechaFinal = tablaDatos.getValueAt(fila, 7).toString();
            t.setIdtimbrado(id);
            dao.consultarDatos(t);
            txtCodigo.setText("" + t.getIdtimbrado());
            txtPuntoEmision.setText(String.format(tres_ceros, t.getPuntoemision()));
            txtEstablecimiento.setText(String.format(tres_ceros, t.getEstablecimiento()));
            txtNumeroInicial.setText(String.format(siete_ceros, t.getNumeroinicial()));
            txtNumeroFinal.setText(String.format(siete_ceros, t.getNumerofinal()));
            txtFechaInicial.setDate(daoCotizacion.parseFecha(fechaInicial));
            txtFechaFinal.setDate(daoCotizacion.parseFecha(fechaFinal));
            int idcaja = t.getIdcaja();
            c.setIdcaja(idcaja);
            daoCaja.consultarDatos(c);
            txtCodigoCaja.setText("" + idcaja);
            txtDescripcionCaja.setText(c.getDescripcion());
            int idtipomovimiento = t.getIdtipocomprobante();
            tm.setIdtipo(idtipomovimiento);
            daoTipoComprobante.consultarDatos(tm);
            txtCodigoTipoComprobante.setText(""+idtipomovimiento);
            txtDescripcionTipoComprobante.setText(tm.getDescripcion());
            txtTimbrado.setText(""+t.getTimbrado());
            habilitarCampos(operacion);
        } else {
            JOptionPane.showMessageDialog(null, "SELECCIONE UNA FILA", "ADVERTENCIA", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void buscarCaja() {
        cargarCaja();
        BuscadorCaja.setModal(true);
        BuscadorCaja.setSize(540, 285);
        BuscadorCaja.setLocationRelativeTo(this);
        BuscadorCaja.setVisible(true);
        int fila = tablaDatosCaja.getSelectedRow();
        if (fila >= 0) {
            txtCodigoCaja.setText(tablaDatosCaja.getValueAt(fila, 0).toString());
            txtDescripcionCaja.setText(tablaDatosCaja.getValueAt(fila, 1).toString());
        } else {
            txtCodigoCaja.setText(null);
            txtDescripcionCaja.setText(null);
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
        Modificar = new javax.swing.JMenuItem();
        Eliminar = new javax.swing.JMenuItem();
        BuscadorCaja = new javax.swing.JDialog();
        jPanel3 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        txtCriterioCaja = new org.jdesktop.swingx.JXTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaDatosCaja = new javax.swing.JTable();
        BuscadorTipoComprobante = new javax.swing.JDialog();
        jPanel4 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        txtCriterioTipoComprobante = new org.jdesktop.swingx.JXTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaDatosTipoComprobante = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        pestanha = new javax.swing.JTabbedPane();
        pestanhaLista = new javax.swing.JPanel();
        txtCriterio = new org.jdesktop.swingx.JXTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaDatos = new javax.swing.JTable();
        pestanhaABM = new javax.swing.JPanel();
        txtPuntoEmision = new org.jdesktop.swingx.JXTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtCodigo = new org.jdesktop.swingx.JXTextField();
        btnCancelar = new javax.swing.JButton();
        btnConfirmar = new javax.swing.JButton();
        btnNuevo = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        txtEstablecimiento = new org.jdesktop.swingx.JXTextField();
        jLabel5 = new javax.swing.JLabel();
        txtNumeroInicial = new org.jdesktop.swingx.JXTextField();
        jLabel6 = new javax.swing.JLabel();
        txtNumeroFinal = new org.jdesktop.swingx.JXTextField();
        txtFechaInicial = new org.jdesktop.swingx.JXDatePicker();
        jLabel7 = new javax.swing.JLabel();
        txtFechaFinal = new org.jdesktop.swingx.JXDatePicker();
        jLabel8 = new javax.swing.JLabel();
        txtCodigoCaja = new org.jdesktop.swingx.JXTextField();
        txtDescripcionCaja = new org.jdesktop.swingx.JXTextField();
        jLabel9 = new javax.swing.JLabel();
        txtCodigoTipoComprobante = new org.jdesktop.swingx.JXTextField();
        txtDescripcionTipoComprobante = new org.jdesktop.swingx.JXTextField();
        jLabel10 = new javax.swing.JLabel();
        txtTimbrado = new org.jdesktop.swingx.JXTextField();
        jLabel11 = new javax.swing.JLabel();

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

        jLabel12.setBackground(new java.awt.Color(50, 104, 151));
        jLabel12.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("BUSCADOR DE CAJAS");
        jLabel12.setOpaque(true);

        txtCriterioCaja.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtCriterioCaja.setPrompt("Aqui puede ingresar los filtros para la busqueda..");
        txtCriterioCaja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCriterioCajaActionPerformed(evt);
            }
        });
        txtCriterioCaja.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCriterioCajaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCriterioCajaKeyTyped(evt);
            }
        });

        tablaDatosCaja.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        tablaDatosCaja.setModel(new javax.swing.table.DefaultTableModel(
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
        tablaDatosCaja.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaDatosCajaMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tablaDatosCaja);
        if (tablaDatosCaja.getColumnModel().getColumnCount() > 0) {
            tablaDatosCaja.getColumnModel().getColumn(0).setMinWidth(60);
            tablaDatosCaja.getColumnModel().getColumn(0).setPreferredWidth(60);
            tablaDatosCaja.getColumnModel().getColumn(0).setMaxWidth(60);
        }

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCriterioCaja, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCriterioCaja, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout BuscadorCajaLayout = new javax.swing.GroupLayout(BuscadorCaja.getContentPane());
        BuscadorCaja.getContentPane().setLayout(BuscadorCajaLayout);
        BuscadorCajaLayout.setHorizontalGroup(
            BuscadorCajaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BuscadorCajaLayout.setVerticalGroup(
            BuscadorCajaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jLabel13.setBackground(new java.awt.Color(50, 104, 151));
        jLabel13.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("BUSCADOR DE TIPOS DE COMPROBANTES");
        jLabel13.setOpaque(true);

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
        jScrollPane3.setViewportView(tablaDatosTipoComprobante);
        if (tablaDatosTipoComprobante.getColumnModel().getColumnCount() > 0) {
            tablaDatosTipoComprobante.getColumnModel().getColumn(0).setMinWidth(60);
            tablaDatosTipoComprobante.getColumnModel().getColumn(0).setPreferredWidth(60);
            tablaDatosTipoComprobante.getColumnModel().getColumn(0).setMaxWidth(60);
        }

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCriterioTipoComprobante, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCriterioTipoComprobante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout BuscadorTipoComprobanteLayout = new javax.swing.GroupLayout(BuscadorTipoComprobante.getContentPane());
        BuscadorTipoComprobante.getContentPane().setLayout(BuscadorTipoComprobanteLayout);
        BuscadorTipoComprobanteLayout.setHorizontalGroup(
            BuscadorTipoComprobanteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BuscadorTipoComprobanteLayout.setVerticalGroup(
            BuscadorTipoComprobanteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setClosable(true);
        setIconifiable(true);

        jPanel1.setBackground(new java.awt.Color(50, 104, 151));

        jLabel1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Mantenimiento de Timbrados");

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
                "<html><p style=\"text-align:center\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Código</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Establecimiento</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">P.Emisión</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Timbrado</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Nº Inicial</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Nº Final</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">F. Inicial</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">F. Final</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">CódigoCaja</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Caja</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">CódigoTipoMovimiento</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">TipoMovimiento</span></span></span></p></html> "
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class
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
            tablaDatos.getColumnModel().getColumn(8).setMinWidth(0);
            tablaDatos.getColumnModel().getColumn(8).setPreferredWidth(0);
            tablaDatos.getColumnModel().getColumn(8).setMaxWidth(0);
            tablaDatos.getColumnModel().getColumn(9).setMinWidth(0);
            tablaDatos.getColumnModel().getColumn(9).setPreferredWidth(0);
            tablaDatos.getColumnModel().getColumn(9).setMaxWidth(0);
            tablaDatos.getColumnModel().getColumn(10).setMinWidth(0);
            tablaDatos.getColumnModel().getColumn(10).setPreferredWidth(0);
            tablaDatos.getColumnModel().getColumn(10).setMaxWidth(0);
            tablaDatos.getColumnModel().getColumn(11).setMinWidth(0);
            tablaDatos.getColumnModel().getColumn(11).setPreferredWidth(0);
            tablaDatos.getColumnModel().getColumn(11).setMaxWidth(0);
        }

        javax.swing.GroupLayout pestanhaListaLayout = new javax.swing.GroupLayout(pestanhaLista);
        pestanhaLista.setLayout(pestanhaListaLayout);
        pestanhaListaLayout.setHorizontalGroup(
            pestanhaListaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pestanhaListaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pestanhaListaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCriterio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 634, Short.MAX_VALUE))
                .addContainerGap())
        );
        pestanhaListaLayout.setVerticalGroup(
            pestanhaListaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pestanhaListaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtCriterio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
                .addContainerGap())
        );

        pestanha.addTab("Listado", pestanhaLista);

        pestanhaABM.setBackground(new java.awt.Color(255, 255, 255));

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

        jLabel2.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel2.setText("Código:");

        jLabel3.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel3.setText("Punto de Emisión:");

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
        jLabel4.setText("Establecimiento:");

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

        jLabel5.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel5.setText("Nº Inicial:");

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

        jLabel6.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel6.setText("Nº Final:");

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

        txtFechaInicial.setEnabled(false);
        txtFechaInicial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFechaInicialActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel7.setText("Fecha Inicial:");

        txtFechaFinal.setEnabled(false);
        txtFechaFinal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFechaFinalActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel8.setText("Fecha Final:");

        txtCodigoCaja.setEnabled(false);
        txtCodigoCaja.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtCodigoCaja.setPrompt("Cód. Caja");
        txtCodigoCaja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoCajaActionPerformed(evt);
            }
        });
        txtCodigoCaja.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodigoCajaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodigoCajaKeyTyped(evt);
            }
        });

        txtDescripcionCaja.setEnabled(false);
        txtDescripcionCaja.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtDescripcionCaja.setPrompt("Descripción o nombre de la caja...");
        txtDescripcionCaja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDescripcionCajaActionPerformed(evt);
            }
        });
        txtDescripcionCaja.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDescripcionCajaKeyTyped(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel9.setText("Caja:");

        txtCodigoTipoComprobante.setEnabled(false);
        txtCodigoTipoComprobante.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtCodigoTipoComprobante.setPrompt("Cód. T.C.");
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
        txtDescripcionTipoComprobante.setPrompt("Descripción o nombre del tipo de comprobante..");
        txtDescripcionTipoComprobante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDescripcionTipoComprobanteActionPerformed(evt);
            }
        });
        txtDescripcionTipoComprobante.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDescripcionTipoComprobanteKeyTyped(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel10.setText("Tipo Comprob.:");

        txtTimbrado.setEnabled(false);
        txtTimbrado.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtTimbrado.setPrompt("Nº del timbrado...");
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

        jLabel11.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel11.setText("Timbrado:");

        javax.swing.GroupLayout pestanhaABMLayout = new javax.swing.GroupLayout(pestanhaABM);
        pestanhaABM.setLayout(pestanhaABMLayout);
        pestanhaABMLayout.setHorizontalGroup(
            pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pestanhaABMLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pestanhaABMLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnConfirmar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pestanhaABMLayout.createSequentialGroup()
                        .addGroup(pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pestanhaABMLayout.createSequentialGroup()
                                .addComponent(txtPuntoEmision, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtEstablecimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pestanhaABMLayout.createSequentialGroup()
                                .addGroup(pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(txtFechaInicial, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtNumeroInicial, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtNumeroFinal, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                                    .addComponent(txtFechaFinal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(pestanhaABMLayout.createSequentialGroup()
                                .addComponent(txtCodigoCaja, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtDescripcionCaja, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(pestanhaABMLayout.createSequentialGroup()
                                .addComponent(txtCodigoTipoComprobante, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtDescripcionTipoComprobante, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(pestanhaABMLayout.createSequentialGroup()
                                .addGroup(pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtTimbrado, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))))
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
                    .addComponent(txtPuntoEmision, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtEstablecimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtNumeroInicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNumeroFinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtFechaInicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(txtFechaFinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCodigoCaja, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDescripcionCaja, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCodigoTipoComprobante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDescripcionTipoComprobante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTimbrado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
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

    private void txtPuntoEmisionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPuntoEmisionActionPerformed
        if (txtPuntoEmision.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "NO PUEDE DEJAR EL CAMPO VACIO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else {
            txtPuntoEmision.setText(String.format(tres_ceros, Integer.parseInt(txtPuntoEmision.getText())));
            txtEstablecimiento.grabFocus();
        }
    }//GEN-LAST:event_txtPuntoEmisionActionPerformed

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

    private void txtEstablecimientoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEstablecimientoActionPerformed
        if (txtEstablecimiento.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "NO PUEDE DEJAR EL CAMPO VACIO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else {
            txtEstablecimiento.setText(String.format(tres_ceros, Integer.parseInt(txtEstablecimiento.getText())));
            txtNumeroInicial.grabFocus();
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
            txtFechaInicial.grabFocus();
        }
    }//GEN-LAST:event_txtNumeroFinalActionPerformed

    private void txtNumeroFinalKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumeroFinalKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            getToolkit().beep();
            evt.consume();
        }
        if (txtNumeroFinal.getText().length() == 7) {
            evt.consume();
        }
    }//GEN-LAST:event_txtNumeroFinalKeyTyped

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
            txtCodigoCaja.grabFocus();
        }
    }//GEN-LAST:event_txtFechaFinalActionPerformed

    private void txtCodigoCajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoCajaActionPerformed
        if (txtCodigoCaja.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "NO PUEDE DEJAR EL CAMPO DE CAJA VACIO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else {
            int idcaja = Integer.parseInt(txtCodigoCaja.getText());
            c.setIdcaja(idcaja);
            boolean resultado = daoCaja.consultarDatos(c);
            if (resultado == true) {
                txtDescripcionCaja.setText(c.getDescripcion());
                txtCodigoTipoComprobante.grabFocus();
            } else {
                txtCodigoCaja.setText(null);
                txtDescripcionCaja.setText(null);
                txtCodigoTipoComprobante.grabFocus();
            }
        }
    }//GEN-LAST:event_txtCodigoCajaActionPerformed

    private void txtCodigoCajaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoCajaKeyPressed
        if (evt.VK_F1 == evt.getKeyCode()) {
            buscarCaja();
        }
    }//GEN-LAST:event_txtCodigoCajaKeyPressed

    private void txtCodigoCajaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoCajaKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            getToolkit().beep();
            evt.consume();
        }
        if (txtCodigoCaja.getText().length() == 10) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCodigoCajaKeyTyped

    private void txtDescripcionCajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDescripcionCajaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescripcionCajaActionPerformed

    private void txtDescripcionCajaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescripcionCajaKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescripcionCajaKeyTyped

    private void txtCodigoTipoComprobanteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoTipoComprobanteActionPerformed
        if (txtCodigoTipoComprobante.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "NO PUEDE DEJAR EL CAMPO DE TIPO DE COMPROBANTE VACIO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else {
            int idtipomovimiento = Integer.parseInt(txtCodigoTipoComprobante.getText());
            tm.setIdtipo(idtipomovimiento);
            boolean resultado = daoTipoComprobante.consultarDatos(tm);
            if (resultado == true) {
                txtDescripcionTipoComprobante.setText(tm.getDescripcion());
                txtTimbrado.grabFocus();
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
        if (txtCodigoCaja.getText().length() == 10) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCodigoTipoComprobanteKeyTyped

    private void txtDescripcionTipoComprobanteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDescripcionTipoComprobanteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescripcionTipoComprobanteActionPerformed

    private void txtDescripcionTipoComprobanteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescripcionTipoComprobanteKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescripcionTipoComprobanteKeyTyped

    private void txtTimbradoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimbradoActionPerformed
        if (txtTimbrado.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "NO PUEDE DEJAR EL CAMPO DE TIMBRADO VACIO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else {
            btnConfirmar.grabFocus();
        }
    }//GEN-LAST:event_txtTimbradoActionPerformed

    private void txtTimbradoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimbradoKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            getToolkit().beep();
            evt.consume();
        }
        if (txtTimbrado.getText().length() == 8) {
            evt.consume();
        }
    }//GEN-LAST:event_txtTimbradoKeyTyped

    private void txtCriterioCajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCriterioCajaActionPerformed
        cargarCaja();
    }//GEN-LAST:event_txtCriterioCajaActionPerformed

    private void txtCriterioCajaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCriterioCajaKeyPressed
        if (evt.VK_ESCAPE == evt.getKeyCode()) {
            txtCodigoCaja.setText(null);
            txtDescripcionCaja.setText(null);
            txtCodigoCaja.grabFocus();
            BuscadorCaja.dispose();
        }
    }//GEN-LAST:event_txtCriterioCajaKeyPressed

    private void txtCriterioCajaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCriterioCajaKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            evt.setKeyChar(Character.toUpperCase(c));
        }
        if (txtCriterio.getText().length() == 100) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCriterioCajaKeyTyped

    private void tablaDatosCajaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaDatosCajaMouseClicked
        if (evt.getClickCount() == 2) {
            if (tablaDatosCaja.getSelectedRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "SELECCIONE UNA FILA");
            } else {
                txtCriterioCaja.setText(null);
                BuscadorCaja.dispose();
            }
        }
    }//GEN-LAST:event_tablaDatosCajaMouseClicked

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
        if (txtCriterio.getText().length() == 100) {
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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDialog BuscadorCaja;
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
    private javax.swing.JLabel jLabel2;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JPopupMenu menuDesplegable;
    private javax.swing.JTabbedPane pestanha;
    private javax.swing.JPanel pestanhaABM;
    private javax.swing.JPanel pestanhaLista;
    private javax.swing.JTable tablaDatos;
    private javax.swing.JTable tablaDatosCaja;
    private javax.swing.JTable tablaDatosTipoComprobante;
    private org.jdesktop.swingx.JXTextField txtCodigo;
    private org.jdesktop.swingx.JXTextField txtCodigoCaja;
    private org.jdesktop.swingx.JXTextField txtCodigoTipoComprobante;
    private org.jdesktop.swingx.JXTextField txtCriterio;
    private org.jdesktop.swingx.JXTextField txtCriterioCaja;
    private org.jdesktop.swingx.JXTextField txtCriterioTipoComprobante;
    private org.jdesktop.swingx.JXTextField txtDescripcionCaja;
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
