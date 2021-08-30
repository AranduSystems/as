package Vistas;

import Dao.DAOCProveedor;
import Dao.DAOCompra;
import Dao.DAOCompraPagoCuota;
import Dao.DAOCompraPagoCuotaAnulado;
import Dao.DAOCotizacion;
import Dao.DAOMotivoAnulacion;
import Dao.DAOUsuario;
import Modelos.Compra;
import Modelos.CompraPagoCuota;
import Modelos.Proveedor;
import Modelos.MotivoAnulacion;
import Modelos.Usuario;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author armando
 */
public class JFrmConsultaPagoCuota extends javax.swing.JInternalFrame {

    Proveedor p = new Proveedor();
    MotivoAnulacion ma = new MotivoAnulacion();
    CompraPagoCuota cpc = new CompraPagoCuota();
    Usuario u = new Usuario();
    Compra c = new Compra();

    DAOCProveedor daoProveedor = new DAOCProveedor();
    DAOCompraPagoCuota daoPagoCuota = new DAOCompraPagoCuota();
    DAOCompraPagoCuotaAnulado daoPagoCuotaAnulado = new DAOCompraPagoCuotaAnulado();
    DAOMotivoAnulacion daoMotivoAnulacion = new DAOMotivoAnulacion();
    DAOCotizacion daoCotizacion = new DAOCotizacion();
    DAOUsuario daoUsuario = new DAOUsuario();
    DAOCompra daoCompra = new DAOCompra();

    ArrayList<Object[]> datosProveedor = new ArrayList<>();
    ArrayList<Object[]> datosPagoCuota = new ArrayList<>();
    ArrayList<Object[]> datosMotivo = new ArrayList<>();

    ArrayList<Object[]> datos = new ArrayList<>();
    
    SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat formatoHora = new SimpleDateFormat("dd/MM/yyyy : HH:mm:ss");

    //VARIABLE QUE MANEJA QUE TIPOS DE OPERACIONES SE REALIZARAN: SI VA A SER ALTA, BAJA O MODIFICACION DEL REGISTRO
    String operacion = "";
    Double montoPago = 0.0;
    
    

    /**
     * Creates new form JFrmConsultaPagoCuota
     */
    public JFrmConsultaPagoCuota() {
        setTitle("JFrmConsultaPagoCuota");
        initComponents();
    }

    public void cargarProveedor() {
        DefaultTableModel modelo = (DefaultTableModel) tablaDatosProveedor.getModel();
        modelo.setRowCount(0);
        datosProveedor = daoProveedor.consultar(txtCriterioProveedor.getText());
        for (Object[] obj : datosProveedor) {
            modelo.addRow(obj);
        }
        this.tablaDatosProveedor.setModel(modelo);
    }

    private void buscarProveedor() {
        cargarProveedor();
        BuscadorProveedor.setModal(true);
        BuscadorProveedor.setSize(540, 285);
        BuscadorProveedor.setLocationRelativeTo(this);
        BuscadorProveedor.setVisible(true);
        int fila = tablaDatosProveedor.getSelectedRow();
        if (fila >= 0) {
            txtCodigoProveedor.setText(tablaDatosProveedor.getValueAt(fila, 0).toString());
            txtDescripcionProveedor.setText(tablaDatosProveedor.getValueAt(fila, 1).toString());
            txtRucProveedor.setText(tablaDatosProveedor.getValueAt(fila, 3).toString());
        } else {
            txtCodigoProveedor.setText(null);
            txtDescripcionProveedor.setText(null);
            txtRucProveedor.setText(null);
        }
    }

    public void cargarPagos() {
        DefaultTableModel modelo = (DefaultTableModel) tablaDatosPagos.getModel();
        modelo.setRowCount(0);
        int idproveedor = Integer.parseInt(txtCodigoProveedor.getText());
        String criterio = txtCriterioPagos.getText();
        datosPagoCuota = daoPagoCuota.consultarPagosCuotas(idproveedor, criterio);
        for (Object[] obj : datosPagoCuota) {
            modelo.addRow(obj);
        }
        this.tablaDatosPagos.setModel(modelo);
    }

    private void buscarPagos() {
        cargarPagos();
        BuscadorPagos.setModal(true);
        BuscadorPagos.setSize(710, 360);
        BuscadorPagos.setLocationRelativeTo(this);
        BuscadorPagos.setVisible(true);
        int fila = tablaDatosPagos.getSelectedRow();
        DecimalFormat formatter = new DecimalFormat("#,###.000");
        if (fila >= 0) {
            int idpago = Integer.parseInt(tablaDatosPagos.getValueAt(fila, 0).toString());
            cpc.setIdpago(idpago);
            daoPagoCuota.consultarDatos(cpc);
            txtNumeroPago.setText("" + cpc.getIdpago());
            
            int idcompra = cpc.getIdcompra();
            c.setIdcompra(idcompra);
            daoCompra.consultarDatos(c);
            txtComprobante.setText(c.getNumerodocumento());
            
            txtRecibo.setText(cpc.getNumerocomprobante());
            
            double monto = cpc.getMonto();
            txtMontoPago.setText(formatter.format(monto));
            
            int idusuario = cpc.getIdusuario();
            u.setIdusuario(idusuario);
            daoUsuario.consultarDatos(u);
            txtCodigoUsuario.setText("" + idusuario);
            txtDescripcionUsuario.setText(u.getNombre() + ' ' + u.getApellido());
            
            txtFechaPago.setText(formato.format(cpc.getFechapago()));
            btnCancelar.grabFocus();
        } else {
            txtNumeroPago.setText(null);
            txtComprobante.setText(null);
            txtRecibo.setText(null);
            txtMontoPago.setText(null);
            txtFechaPago.setText(null);
            txtCodigoUsuario.setText(null);
            txtDescripcionUsuario.setText(null);

            montoPago = 0.0;
        }
    }

    public void limpiarCampos() {
        txtCodigoProveedor.setText(null);
        txtDescripcionProveedor.setText(null);
        txtRucProveedor.setText(null);
        txtNumeroPago.setText(null);
        txtComprobante.setText(null);
        txtRecibo.setText(null);
        txtMontoPago.setText(null);
        txtFechaPago.setText(null);
        txtCodigoUsuario.setText(null);
        txtDescripcionUsuario.setText(null);
        montoPago = 0.0;
        txtCodigoProveedor.grabFocus();
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        BuscadorProveedor = new javax.swing.JDialog();
        jPanel6 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        txtCriterioProveedor = new org.jdesktop.swingx.JXTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        tablaDatosProveedor = new javax.swing.JTable();
        BuscadorPagos = new javax.swing.JDialog();
        jPanel5 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        txtCriterioPagos = new org.jdesktop.swingx.JXTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaDatosPagos = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        txtCodigoProveedor = new org.jdesktop.swingx.JXTextField();
        txtDescripcionProveedor = new org.jdesktop.swingx.JXTextField();
        jLabel12 = new javax.swing.JLabel();
        txtRucProveedor = new org.jdesktop.swingx.JXTextField();
        jSeparator1 = new javax.swing.JSeparator();
        txtNumeroPago = new org.jdesktop.swingx.JXTextField();
        jLabel23 = new javax.swing.JLabel();
        btnBuscar = new javax.swing.JButton();
        txtComprobante = new org.jdesktop.swingx.JXTextField();
        jLabel29 = new javax.swing.JLabel();
        txtRecibo = new org.jdesktop.swingx.JXTextField();
        jLabel30 = new javax.swing.JLabel();
        txtMontoPago = new org.jdesktop.swingx.JXTextField();
        jLabel27 = new javax.swing.JLabel();
        txtCodigoUsuario = new org.jdesktop.swingx.JXTextField();
        txtDescripcionUsuario = new org.jdesktop.swingx.JXTextField();
        jLabel22 = new javax.swing.JLabel();
        btnCancelar = new javax.swing.JButton();
        jLabel28 = new javax.swing.JLabel();
        txtFechaPago = new org.jdesktop.swingx.JXTextField();

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        jLabel13.setBackground(new java.awt.Color(50, 104, 151));
        jLabel13.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("BUSCADOR DE PROVEEDORES");
        jLabel13.setOpaque(true);

        txtCriterioProveedor.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtCriterioProveedor.setPrompt("Aqui puede ingresar los filtros para la busqueda..");
        txtCriterioProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCriterioProveedorActionPerformed(evt);
            }
        });
        txtCriterioProveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCriterioProveedorKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCriterioProveedorKeyTyped(evt);
            }
        });

        tablaDatosProveedor.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        tablaDatosProveedor.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "<html><p style=\"text-align:center\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Código</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Razón Social</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Propietario</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Ruc</span></span></span></p></html> "
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
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
        tablaDatosProveedor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaDatosProveedorMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tablaDatosProveedor);
        if (tablaDatosProveedor.getColumnModel().getColumnCount() > 0) {
            tablaDatosProveedor.getColumnModel().getColumn(0).setMinWidth(60);
            tablaDatosProveedor.getColumnModel().getColumn(0).setPreferredWidth(60);
            tablaDatosProveedor.getColumnModel().getColumn(0).setMaxWidth(60);
            tablaDatosProveedor.getColumnModel().getColumn(3).setMinWidth(100);
            tablaDatosProveedor.getColumnModel().getColumn(3).setPreferredWidth(100);
            tablaDatosProveedor.getColumnModel().getColumn(3).setMaxWidth(100);
        }

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCriterioProveedor, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCriterioProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout BuscadorProveedorLayout = new javax.swing.GroupLayout(BuscadorProveedor.getContentPane());
        BuscadorProveedor.getContentPane().setLayout(BuscadorProveedorLayout);
        BuscadorProveedorLayout.setHorizontalGroup(
            BuscadorProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BuscadorProveedorLayout.setVerticalGroup(
            BuscadorProveedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jLabel11.setBackground(new java.awt.Color(50, 104, 151));
        jLabel11.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("BUSCADOR DE PAGOS");
        jLabel11.setOpaque(true);

        txtCriterioPagos.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtCriterioPagos.setPrompt("Aqui puede ingresar los filtros para la busqueda..");
        txtCriterioPagos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCriterioPagosActionPerformed(evt);
            }
        });
        txtCriterioPagos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCriterioPagosKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCriterioPagosKeyTyped(evt);
            }
        });

        tablaDatosPagos.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        tablaDatosPagos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "<html><p style=\"text-align:center\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">N° Pago</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">N°Documento</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">N° Recibo</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Monto</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">CodigoCuenta</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Cuenta</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">CodigoUsuario</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Usuario</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Fecha Pago</span></span></span></p></html> "
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaDatosPagos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaDatosPagosMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tablaDatosPagos);
        if (tablaDatosPagos.getColumnModel().getColumnCount() > 0) {
            tablaDatosPagos.getColumnModel().getColumn(0).setMinWidth(80);
            tablaDatosPagos.getColumnModel().getColumn(0).setPreferredWidth(80);
            tablaDatosPagos.getColumnModel().getColumn(0).setMaxWidth(80);
            tablaDatosPagos.getColumnModel().getColumn(3).setMinWidth(100);
            tablaDatosPagos.getColumnModel().getColumn(3).setPreferredWidth(100);
            tablaDatosPagos.getColumnModel().getColumn(3).setMaxWidth(100);
            tablaDatosPagos.getColumnModel().getColumn(4).setMinWidth(0);
            tablaDatosPagos.getColumnModel().getColumn(4).setPreferredWidth(0);
            tablaDatosPagos.getColumnModel().getColumn(4).setMaxWidth(0);
            tablaDatosPagos.getColumnModel().getColumn(5).setMinWidth(0);
            tablaDatosPagos.getColumnModel().getColumn(5).setPreferredWidth(0);
            tablaDatosPagos.getColumnModel().getColumn(5).setMaxWidth(0);
            tablaDatosPagos.getColumnModel().getColumn(6).setMinWidth(0);
            tablaDatosPagos.getColumnModel().getColumn(6).setPreferredWidth(0);
            tablaDatosPagos.getColumnModel().getColumn(6).setMaxWidth(0);
            tablaDatosPagos.getColumnModel().getColumn(7).setMinWidth(0);
            tablaDatosPagos.getColumnModel().getColumn(7).setPreferredWidth(0);
            tablaDatosPagos.getColumnModel().getColumn(7).setMaxWidth(0);
            tablaDatosPagos.getColumnModel().getColumn(8).setMinWidth(100);
            tablaDatosPagos.getColumnModel().getColumn(8).setPreferredWidth(100);
            tablaDatosPagos.getColumnModel().getColumn(8).setMaxWidth(100);
        }

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCriterioPagos, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 680, Short.MAX_VALUE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 680, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCriterioPagos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout BuscadorPagosLayout = new javax.swing.GroupLayout(BuscadorPagos.getContentPane());
        BuscadorPagos.getContentPane().setLayout(BuscadorPagosLayout);
        BuscadorPagosLayout.setHorizontalGroup(
            BuscadorPagosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BuscadorPagosLayout.setVerticalGroup(
            BuscadorPagosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setClosable(true);
        setIconifiable(true);

        jPanel1.setBackground(new java.awt.Color(50, 104, 151));

        jLabel1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Consulta de Pago de Cuota");

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

        jLabel8.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel8.setText("Proveedor:");

        txtCodigoProveedor.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        txtCodigoProveedor.setPrompt("Cód. Proveedor");
        txtCodigoProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoProveedorActionPerformed(evt);
            }
        });
        txtCodigoProveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodigoProveedorKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodigoProveedorKeyTyped(evt);
            }
        });

        txtDescripcionProveedor.setToolTipText("Razón Social del proveedor...");
        txtDescripcionProveedor.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtDescripcionProveedor.setEnabled(false);
        txtDescripcionProveedor.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        txtDescripcionProveedor.setPrompt("Razón Social del proveedor...");

        jLabel12.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel12.setText("R.U.C.:");

        txtRucProveedor.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtRucProveedor.setEnabled(false);
        txtRucProveedor.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        txtRucProveedor.setPrompt("R. U. C.");
        txtRucProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtRucProveedorActionPerformed(evt);
            }
        });
        txtRucProveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtRucProveedorKeyTyped(evt);
            }
        });

        txtNumeroPago.setBackground(new java.awt.Color(0, 102, 102));
        txtNumeroPago.setForeground(new java.awt.Color(255, 255, 255));
        txtNumeroPago.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        txtNumeroPago.setEnabled(false);
        txtNumeroPago.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtNumeroPago.setPrompt("Nº");
        txtNumeroPago.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNumeroPagoKeyTyped(evt);
            }
        });

        jLabel23.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel23.setText("Nº de Pago:");

        btnBuscar.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/icons8_browse_folder_16px.png"))); // NOI18N
        btnBuscar.setText("Buscar");
        btnBuscar.setOpaque(false);
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        txtComprobante.setBackground(new java.awt.Color(0, 102, 102));
        txtComprobante.setForeground(new java.awt.Color(255, 255, 255));
        txtComprobante.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtComprobante.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        txtComprobante.setEnabled(false);
        txtComprobante.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        txtComprobante.setPrompt("000-000-0000000");
        txtComprobante.setPromptForeground(new java.awt.Color(153, 153, 153));

        jLabel29.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel29.setText("Nº Factura:");

        txtRecibo.setBackground(new java.awt.Color(0, 102, 102));
        txtRecibo.setForeground(new java.awt.Color(255, 255, 255));
        txtRecibo.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtRecibo.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        txtRecibo.setEnabled(false);
        txtRecibo.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        txtRecibo.setPrompt("000-000-0000000");
        txtRecibo.setPromptForeground(new java.awt.Color(153, 153, 153));

        jLabel30.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel30.setText("Nº Recibo:");

        txtMontoPago.setBackground(new java.awt.Color(0, 102, 102));
        txtMontoPago.setForeground(new java.awt.Color(255, 255, 255));
        txtMontoPago.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        txtMontoPago.setEnabled(false);
        txtMontoPago.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        txtMontoPago.setPrompt("Monto Pago");

        jLabel27.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel27.setText("Monto del Pago:");

        txtCodigoUsuario.setBackground(new java.awt.Color(0, 102, 102));
        txtCodigoUsuario.setForeground(new java.awt.Color(255, 255, 255));
        txtCodigoUsuario.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        txtCodigoUsuario.setEnabled(false);
        txtCodigoUsuario.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        txtCodigoUsuario.setPrompt("Cód. Usuario");

        txtDescripcionUsuario.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtDescripcionUsuario.setEnabled(false);
        txtDescripcionUsuario.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        txtDescripcionUsuario.setPrompt("Descripción o nombre de la usuario...");

        jLabel22.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel22.setText("Usuario:");

        btnCancelar.setBackground(new java.awt.Color(255, 204, 204));
        btnCancelar.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/icons8_cancel_16px.png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.setOpaque(false);
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        jLabel28.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel28.setText("Fecha del Pago:");

        txtFechaPago.setBackground(new java.awt.Color(0, 102, 102));
        txtFechaPago.setForeground(new java.awt.Color(255, 255, 255));
        txtFechaPago.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        txtFechaPago.setEnabled(false);
        txtFechaPago.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        txtFechaPago.setPrompt("Fecha del pago...");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCodigoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDescripcionUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtFechaPago, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(txtCodigoProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtDescripcionProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(txtRucProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNumeroPago, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnBuscar))
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                    .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtMontoPago, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                    .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtComprobante, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                    .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtRecibo, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCodigoProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDescripcionProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtRucProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNumeroPago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtComprobante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtRecibo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMontoPago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCodigoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDescripcionUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFechaPago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCancelar)
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
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtCodigoProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoProveedorActionPerformed
        if (txtCodigoProveedor.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "NO PUEDE DEJAR EL CAMPO DE PROVEEDOR VACIO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else {
            int idproveedor = Integer.parseInt(txtCodigoProveedor.getText());
            p.setIdproveedor(idproveedor);
            boolean resultado = daoProveedor.consultarDatos(p);
            if (resultado == true) {
                txtDescripcionProveedor.setText(p.getRazonsocial());
                txtRucProveedor.setText(p.getRuc());
                btnBuscar.grabFocus();
            } else {
                txtCodigoProveedor.setText(null);
                txtDescripcionProveedor.setText(null);
                txtRucProveedor.setText(null);
                txtCodigoProveedor.grabFocus();
            }
        }
    }//GEN-LAST:event_txtCodigoProveedorActionPerformed

    private void txtCodigoProveedorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoProveedorKeyPressed
        if (evt.VK_F1 == evt.getKeyCode()) {
            buscarProveedor();
        }
    }//GEN-LAST:event_txtCodigoProveedorKeyPressed

    private void txtCodigoProveedorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoProveedorKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            getToolkit().beep();
            evt.consume();
        }
        if (txtCodigoProveedor.getText().length() == 10) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCodigoProveedorKeyTyped

    private void txtRucProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtRucProveedorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRucProveedorActionPerformed

    private void txtRucProveedorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRucProveedorKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRucProveedorKeyTyped

    private void txtCriterioProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCriterioProveedorActionPerformed
        cargarProveedor();
    }//GEN-LAST:event_txtCriterioProveedorActionPerformed

    private void txtCriterioProveedorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCriterioProveedorKeyPressed
        if (evt.VK_ESCAPE == evt.getKeyCode()) {
            txtCodigoProveedor.setText(null);
            txtDescripcionProveedor.setText(null);
            txtCodigoProveedor.grabFocus();
            BuscadorProveedor.dispose();
        }
    }//GEN-LAST:event_txtCriterioProveedorKeyPressed

    private void txtCriterioProveedorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCriterioProveedorKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            evt.setKeyChar(Character.toUpperCase(c));
        }
        if (txtCriterioProveedor.getText().length() == 100) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCriterioProveedorKeyTyped

    private void tablaDatosProveedorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaDatosProveedorMouseClicked
        if (evt.getClickCount() == 2) {
            if (tablaDatosProveedor.getSelectedRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "SELECCIONE UNA FILA");
            } else {
                txtCriterioProveedor.setText(null);
                BuscadorProveedor.dispose();
            }
        }
    }//GEN-LAST:event_tablaDatosProveedorMouseClicked

    private void txtNumeroPagoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumeroPagoKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            getToolkit().beep();
            evt.consume();
        }
        if (c == ',') {
            getToolkit().beep();
            evt.consume();
        }
        if (txtNumeroPago.getText().length() == 20) {
            evt.consume();
        }
    }//GEN-LAST:event_txtNumeroPagoKeyTyped

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        buscarPagos();
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        int res = JOptionPane.showConfirmDialog(null, "¿ESTA SEGURO DE CANCELAR LOS CAMBIOS?", "ADVERTENCIA", JOptionPane.YES_NO_OPTION);
        if (res != 1) {
            limpiarCampos();
        }
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void txtCriterioPagosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCriterioPagosActionPerformed
        cargarPagos();
    }//GEN-LAST:event_txtCriterioPagosActionPerformed

    private void txtCriterioPagosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCriterioPagosKeyPressed
        if (evt.VK_ESCAPE == evt.getKeyCode()) {
            txtNumeroPago.setText(null);
            txtComprobante.setText(null);
            btnBuscar.grabFocus();
            BuscadorPagos.dispose();
        }
    }//GEN-LAST:event_txtCriterioPagosKeyPressed

    private void txtCriterioPagosKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCriterioPagosKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            evt.setKeyChar(Character.toUpperCase(c));
        }
        if (txtCriterioPagos.getText().length() == 100) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCriterioPagosKeyTyped

    private void tablaDatosPagosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaDatosPagosMouseClicked
        if (evt.getClickCount() == 2) {
            if (tablaDatosPagos.getSelectedRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "SELECCIONE UNA FILA");
            } else {
                txtCriterioPagos.setText(null);
                BuscadorPagos.dispose();
            }
        }
    }//GEN-LAST:event_tablaDatosPagosMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDialog BuscadorPagos;
    private javax.swing.JDialog BuscadorProveedor;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable tablaDatosPagos;
    private javax.swing.JTable tablaDatosProveedor;
    private org.jdesktop.swingx.JXTextField txtCodigoProveedor;
    private org.jdesktop.swingx.JXTextField txtCodigoUsuario;
    private org.jdesktop.swingx.JXTextField txtComprobante;
    private org.jdesktop.swingx.JXTextField txtCriterioPagos;
    private org.jdesktop.swingx.JXTextField txtCriterioProveedor;
    private org.jdesktop.swingx.JXTextField txtDescripcionProveedor;
    private org.jdesktop.swingx.JXTextField txtDescripcionUsuario;
    private org.jdesktop.swingx.JXTextField txtFechaPago;
    private org.jdesktop.swingx.JXTextField txtMontoPago;
    private org.jdesktop.swingx.JXTextField txtNumeroPago;
    private org.jdesktop.swingx.JXTextField txtRecibo;
    private org.jdesktop.swingx.JXTextField txtRucProveedor;
    // End of variables declaration//GEN-END:variables
}
