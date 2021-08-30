package Vistas;

import Dao.DAOCProveedor;
import Dao.DAOCompraAnulado;
import Dao.DAOCuenta;
import Dao.DAODeposito;
import Dao.DAOMoneda;
import Dao.DAOMotivoAnulacion;
import Dao.DAOTipoMovimiento;
import Dao.DAOUsuario;
import Modelos.CompraAnulado;
import Modelos.Cuenta;
import Modelos.Deposito;
import Modelos.Moneda;
import Modelos.MotivoAnulacion;
import Modelos.Proveedor;
import Modelos.TipoMovimiento;
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
public class JFrmConsultaCompraAnulado extends javax.swing.JInternalFrame {

    TipoMovimiento tm = new TipoMovimiento();
    CompraAnulado ca = new CompraAnulado();
    Moneda m = new Moneda();
    Deposito d = new Deposito();
    Proveedor p = new Proveedor();
    Usuario u = new Usuario();
    Cuenta c = new Cuenta();
    MotivoAnulacion ma = new MotivoAnulacion();

    DAOTipoMovimiento daoTipoMovimiento = new DAOTipoMovimiento();
    DAOCompraAnulado daoCompraAnulado = new DAOCompraAnulado();
    DAOMoneda daoMoneda = new DAOMoneda();
    DAODeposito daoDeposito = new DAODeposito();
    DAOCProveedor daoProveedor = new DAOCProveedor();
    DAOUsuario daoUsuario = new DAOUsuario();
    DAOCuenta daoCuenta = new DAOCuenta();
    DAOMotivoAnulacion daoMotivoAnulacion = new DAOMotivoAnulacion();

    ArrayList<Object[]> datosTipoMovimiento = new ArrayList<>();
    ArrayList<Object[]> datosCompras = new ArrayList<>();

    String tres_ceros = String.format("%%0%dd", 3);
    String siete_ceros = String.format("%%0%dd", 7);
    int Establecimiento;
    Double montoTotalSinIva = 0.0;
    Double montoTotalIva = 0.0;
    Double montoTotal = 0.0;

    SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy : HH:mm:ss");

    /**
     * Creates new form JFrmConsultaCompraAnulado
     */
    public JFrmConsultaCompraAnulado() {
        setTitle("JFrmConsultaCompraAnulado");
        initComponents();
    }

    public void cargarTipoMovimiento() {
        DefaultTableModel modelo = (DefaultTableModel) tablaDatosTipoMovimiento.getModel();
        modelo.setRowCount(0);
        datosTipoMovimiento = daoTipoMovimiento.consultarFactura(txtCriterioTipoMovimiento.getText(), "RECIBIDA");
        for (Object[] obj : datosTipoMovimiento) {
            modelo.addRow(obj);
        }
        this.tablaDatosTipoMovimiento.setModel(modelo);
    }

    private void buscarTipoMovimiento() {
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

    public void cargarComprasAnuladas() {
        DefaultTableModel modelo = (DefaultTableModel) tablaDatosCompras.getModel();
        modelo.setRowCount(0);
        int idtipomovimiento = Integer.parseInt(txtCodigoTipoMovimiento.getText());
        String criterio = txtCriterioCompras.getText();
        datosCompras = daoCompraAnulado.consultar(criterio, idtipomovimiento);
        for (Object[] obj : datosCompras) {
            modelo.addRow(obj);
        }
        this.tablaDatosCompras.setModel(modelo);
    }

    private void buscarComprasAnuladas() {
        cargarComprasAnuladas();
        BuscadorCompras.setModal(true);
        BuscadorCompras.setSize(710, 360);
        BuscadorCompras.setLocationRelativeTo(this);
        BuscadorCompras.setVisible(true);
        int fila = tablaDatosCompras.getSelectedRow();
        DecimalFormat formatter;
        if (fila >= 0) {
            int idcompraanulado = Integer.parseInt(tablaDatosCompras.getValueAt(fila, 0).toString());
            int establecimiento = Integer.parseInt(tablaDatosCompras.getValueAt(fila, 1).toString());
            int puntoemision = Integer.parseInt(tablaDatosCompras.getValueAt(fila, 2).toString());
            int numero = Integer.parseInt(tablaDatosCompras.getValueAt(fila, 3).toString());
            txtEstablecimiento.setText(String.format(tres_ceros, establecimiento));
            txtPuntoEmision.setText(String.format(tres_ceros, puntoemision));
            txtNumero.setText(String.format(siete_ceros, numero));
            txtComprobante.setText(tablaDatosCompras.getValueAt(fila, 4).toString());
            txtTimbrado.setText(tablaDatosCompras.getValueAt(fila, 5).toString());
            txtFecha.setText(tablaDatosCompras.getValueAt(fila, 6).toString());

            ca.setIdcompraanulado(idcompraanulado);
            daoCompraAnulado.consultarDatos(ca);
            txtCodigoCompra.setText("" + ca.getIdcompra());
            txtObservacionCompra.setText(ca.getObservacion());
            int idmoneda = ca.getIdmoneda();
            if (idmoneda == 1) {
                formatter = new DecimalFormat("#,###");
            } else {
                formatter = new DecimalFormat("#,###.000");
            }
            m.setIdmoneda(idmoneda);
            daoMoneda.consultarDatos(m);
            txtCodigoMoneda.setText("" + idmoneda);
            txtDescripcionMoneda.setText(m.getDescripcion());

            int iddeposito = ca.getIddeposito();
            d.setIddeposito(iddeposito);
            daoDeposito.consultarDatos(d);
            txtCodigoDeposito.setText("" + iddeposito);
            txtDescripcionDeposito.setText(d.getDescripcion());

            int idproveedor = ca.getIdproveedor();
            p.setIdproveedor(idproveedor);
            daoProveedor.consultarDatos(p);
            txtCodigoProveedor.setText("" + idproveedor);
            txtDescripcionProveedor.setText(p.getRazonsocial());

            int idusuario = ca.getIdusuario();
            u.setIdusuario(idusuario);
            daoUsuario.consultarDatos(u);
            txtCodigoUsuario.setText("" + idusuario);
            txtDescripcionUsuario.setText(u.getNombre() + ' ' + u.getApellido());

            montoTotalSinIva = ca.getTotalneto();
            txtTotalNeto.setText(formatter.format(montoTotalSinIva));
            montoTotalIva = ca.getTotaliva();
            txtTotalIva.setText(formatter.format(montoTotalIva));
            montoTotal = montoTotalSinIva + montoTotalIva;
            txtMontoTotal.setText(formatter.format(montoTotal));

            int idcuenta = ca.getIdcuenta();
            if (idcuenta != 0) {
                c.setIdcuenta(idcuenta);
                c.setIdmoneda(idmoneda);
                daoCuenta.consultarDatos(c);
                txtCodigoCuenta.setText("" + idcuenta);
                txtDescripcionCuenta.setText(c.getDescripcion());
            } else {
                txtCodigoCuenta.setText(null);
                txtDescripcionCuenta.setText(null);
            }

            txtFechaHoraAnulado.setText(formato.format(ca.getFechahoranulado()));
            txtObservacionAnulacion.setText(ca.getObservacionanulado());

            int idmotivo = ca.getIdmotivo();
            ma.setIdmotivo(idmotivo);
            daoMotivoAnulacion.consultarDatos(ma);
            txtCodigoMotivoAnulacion.setText("" + idmotivo);
            txtDescripcionMotivoAnulacion.setText(ma.getDescripcion());

            int idusuarioanulacion = ca.getIdusuarioanulado();
            u.setIdusuario(idusuarioanulacion);
            daoUsuario.consultarDatos(u);
            txtCodigoUsuarioAnulacion.setText("" + idusuario);
            txtDescripcionUsuarioAnulacion.setText(u.getNombre() + ' ' + u.getApellido());

            btnCancelar.grabFocus();
        } else {
            txtEstablecimiento.setText(null);
            txtPuntoEmision.setText(null);
            txtNumero.setText(null);
            txtComprobante.setText(null);
            txtTimbrado.setText(null);
            txtCodigoCompra.setText(null);
            txtFecha.setText(null);
            txtObservacionCompra.setText(null);
            txtCodigoMoneda.setText(null);
            txtDescripcionMoneda.setText(null);
            txtCodigoDeposito.setText(null);
            txtDescripcionDeposito.setText(null);
            txtCodigoProveedor.setText(null);
            txtDescripcionProveedor.setText(null);
            txtCodigoUsuario.setText(null);
            txtDescripcionUsuario.setText(null);
            txtTotalNeto.setText(null);
            txtTotalIva.setText(null);
            txtMontoTotal.setText(null);
            txtCodigoCuenta.setText(null);
            txtDescripcionCuenta.setText(null);
            montoTotalSinIva = 0.0;
            montoTotalIva = 0.0;
            montoTotal = 0.0;

            txtFechaHoraAnulado.setText(null);
            txtObservacionAnulacion.setText(null);
            txtCodigoMotivoAnulacion.setText(null);
            txtDescripcionMotivoAnulacion.setText(null);
            txtCodigoUsuarioAnulacion.setText(null);
            txtDescripcionUsuarioAnulacion.setText(null);
            
            txtCodigoTipoMovimiento.grabFocus();
        }
    }

    private void limpiarCampos() {
        txtEstablecimiento.setText(null);
        txtPuntoEmision.setText(null);
        txtNumero.setText(null);
        txtComprobante.setText(null);
        txtTimbrado.setText(null);
        txtCodigoCompra.setText(null);
        txtFecha.setText(null);
        txtObservacionCompra.setText(null);
        txtCodigoMoneda.setText(null);
        txtDescripcionMoneda.setText(null);
        txtCodigoDeposito.setText(null);
        txtDescripcionDeposito.setText(null);
        txtCodigoProveedor.setText(null);
        txtDescripcionProveedor.setText(null);
        txtCodigoUsuario.setText(null);
        txtDescripcionUsuario.setText(null);
        txtTotalNeto.setText(null);
        txtTotalIva.setText(null);
        txtMontoTotal.setText(null);
        txtCodigoCuenta.setText(null);
        txtDescripcionCuenta.setText(null);
        montoTotalSinIva = 0.0;
        montoTotalIva = 0.0;
        montoTotal = 0.0;
        txtCodigoTipoMovimiento.setText(null);
        txtDescripcionTipoMovimiento.setText(null);

        txtFechaHoraAnulado.setText(null);
        txtObservacionAnulacion.setText(null);
        txtCodigoMotivoAnulacion.setText(null);
        txtDescripcionMotivoAnulacion.setText(null);
        txtCodigoUsuarioAnulacion.setText(null);
        txtDescripcionUsuarioAnulacion.setText(null);

        txtCodigoTipoMovimiento.grabFocus();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        BuscadorTipoMovimiento = new javax.swing.JDialog();
        jPanel3 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txtCriterioTipoMovimiento = new org.jdesktop.swingx.JXTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaDatosTipoMovimiento = new javax.swing.JTable();
        BuscadorCompras = new javax.swing.JDialog();
        jPanel5 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        txtCriterioCompras = new org.jdesktop.swingx.JXTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        tablaDatosCompras = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtCodigoTipoMovimiento = new org.jdesktop.swingx.JXTextField();
        txtDescripcionTipoMovimiento = new org.jdesktop.swingx.JXTextField();
        btnBuscar = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        txtEstablecimiento = new org.jdesktop.swingx.JXTextField();
        txtPuntoEmision = new org.jdesktop.swingx.JXTextField();
        txtNumero = new org.jdesktop.swingx.JXTextField();
        txtComprobante = new org.jdesktop.swingx.JXTextField();
        txtTimbrado = new org.jdesktop.swingx.JXTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtCodigoCompra = new org.jdesktop.swingx.JXTextField();
        jLabel9 = new javax.swing.JLabel();
        txtFecha = new org.jdesktop.swingx.JXTextField();
        jLabel10 = new javax.swing.JLabel();
        txtObservacionCompra = new org.jdesktop.swingx.JXTextField();
        jLabel11 = new javax.swing.JLabel();
        txtCodigoMoneda = new org.jdesktop.swingx.JXTextField();
        txtDescripcionMoneda = new org.jdesktop.swingx.JXTextField();
        jLabel12 = new javax.swing.JLabel();
        txtCodigoDeposito = new org.jdesktop.swingx.JXTextField();
        txtDescripcionDeposito = new org.jdesktop.swingx.JXTextField();
        jLabel13 = new javax.swing.JLabel();
        txtCodigoProveedor = new org.jdesktop.swingx.JXTextField();
        txtDescripcionProveedor = new org.jdesktop.swingx.JXTextField();
        jLabel14 = new javax.swing.JLabel();
        txtCodigoUsuario = new org.jdesktop.swingx.JXTextField();
        txtDescripcionUsuario = new org.jdesktop.swingx.JXTextField();
        jLabel15 = new javax.swing.JLabel();
        txtTotalNeto = new org.jdesktop.swingx.JXTextField();
        jLabel16 = new javax.swing.JLabel();
        txtTotalIva = new org.jdesktop.swingx.JXTextField();
        txtMontoTotal = new org.jdesktop.swingx.JXTextField();
        jLabel21 = new javax.swing.JLabel();
        txtCodigoCuenta = new org.jdesktop.swingx.JXTextField();
        jLabel17 = new javax.swing.JLabel();
        txtDescripcionCuenta = new org.jdesktop.swingx.JXTextField();
        jLabel18 = new javax.swing.JLabel();
        txtFechaHoraAnulado = new org.jdesktop.swingx.JXTextField();
        jLabel19 = new javax.swing.JLabel();
        txtObservacionAnulacion = new org.jdesktop.swingx.JXTextField();
        jLabel20 = new javax.swing.JLabel();
        txtCodigoMotivoAnulacion = new org.jdesktop.swingx.JXTextField();
        txtDescripcionMotivoAnulacion = new org.jdesktop.swingx.JXTextField();
        jLabel22 = new javax.swing.JLabel();
        txtCodigoUsuarioAnulacion = new org.jdesktop.swingx.JXTextField();
        txtDescripcionUsuarioAnulacion = new org.jdesktop.swingx.JXTextField();
        btnCancelar = new javax.swing.JButton();

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
            tablaDatosTipoMovimiento.getColumnModel().getColumn(2).setMinWidth(100);
            tablaDatosTipoMovimiento.getColumnModel().getColumn(2).setPreferredWidth(100);
            tablaDatosTipoMovimiento.getColumnModel().getColumn(2).setMaxWidth(100);
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

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jLabel23.setBackground(new java.awt.Color(50, 104, 151));
        jLabel23.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel23.setText("BUSCADOR DE COMPRAS ANULADAS");
        jLabel23.setOpaque(true);

        txtCriterioCompras.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtCriterioCompras.setPrompt("Aqui puede ingresar los filtros para la busqueda..");
        txtCriterioCompras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCriterioComprasActionPerformed(evt);
            }
        });
        txtCriterioCompras.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCriterioComprasKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCriterioComprasKeyTyped(evt);
            }
        });

        tablaDatosCompras.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        tablaDatosCompras.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "<html><p style=\"text-align:center\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Cod.Comp.Anul.</span></span></span></p></html> ", "<html><p style=\"text-align:center\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Establecimiento</span></span></span></p></html> ", "<html><p style=\"text-align:center\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">PuntoEmision</span></span></span></p></html> ", "<html><p style=\"text-align:center\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Numero</span></span></span></p></html> ", "<html><p style=\"text-align:center\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Comprobante</span></span></span></p></html> ", "<html><p style=\"text-align:center\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Timbrado</span></span></span></p></html> ", "<html><p style=\"text-align:center\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Fecha</span></span></span></p></html> ", "<html><p style=\"text-align:center\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Cod.Prov</span></span></span></p></html> ", "<html><p style=\"text-align:center\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Proveedor</span></span></span></p></html> "
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class
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
        tablaDatosCompras.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaDatosComprasMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tablaDatosCompras);
        if (tablaDatosCompras.getColumnModel().getColumnCount() > 0) {
            tablaDatosCompras.getColumnModel().getColumn(0).setMinWidth(0);
            tablaDatosCompras.getColumnModel().getColumn(0).setPreferredWidth(0);
            tablaDatosCompras.getColumnModel().getColumn(0).setMaxWidth(0);
            tablaDatosCompras.getColumnModel().getColumn(1).setMinWidth(0);
            tablaDatosCompras.getColumnModel().getColumn(1).setPreferredWidth(0);
            tablaDatosCompras.getColumnModel().getColumn(1).setMaxWidth(0);
            tablaDatosCompras.getColumnModel().getColumn(2).setMinWidth(0);
            tablaDatosCompras.getColumnModel().getColumn(2).setPreferredWidth(0);
            tablaDatosCompras.getColumnModel().getColumn(2).setMaxWidth(0);
            tablaDatosCompras.getColumnModel().getColumn(3).setMinWidth(0);
            tablaDatosCompras.getColumnModel().getColumn(3).setPreferredWidth(0);
            tablaDatosCompras.getColumnModel().getColumn(3).setMaxWidth(0);
            tablaDatosCompras.getColumnModel().getColumn(7).setMinWidth(0);
            tablaDatosCompras.getColumnModel().getColumn(7).setPreferredWidth(0);
            tablaDatosCompras.getColumnModel().getColumn(7).setMaxWidth(0);
        }

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCriterioCompras, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 680, Short.MAX_VALUE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 680, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCriterioCompras, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout BuscadorComprasLayout = new javax.swing.GroupLayout(BuscadorCompras.getContentPane());
        BuscadorCompras.getContentPane().setLayout(BuscadorComprasLayout);
        BuscadorComprasLayout.setHorizontalGroup(
            BuscadorComprasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BuscadorComprasLayout.setVerticalGroup(
            BuscadorComprasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setClosable(true);
        setIconifiable(true);

        jPanel1.setBackground(new java.awt.Color(50, 104, 151));

        jLabel1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Consulta de Compras Anuladas");

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

        jLabel4.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel4.setText("Tipo de Movimiento:");

        txtCodigoTipoMovimiento.setToolTipText("Cód. T.M.");
        txtCodigoTipoMovimiento.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        txtCodigoTipoMovimiento.setPrompt("Cód. T.M.");
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

        txtDescripcionTipoMovimiento.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtDescripcionTipoMovimiento.setEnabled(false);
        txtDescripcionTipoMovimiento.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
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

        btnBuscar.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/icons8_browse_folder_16px.png"))); // NOI18N
        btnBuscar.setText("Buscar");
        btnBuscar.setOpaque(false);
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel5.setText("Comprobante:");

        txtEstablecimiento.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtEstablecimiento.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtEstablecimiento.setEnabled(false);
        txtEstablecimiento.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        txtEstablecimiento.setPrompt("000");

        txtPuntoEmision.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtPuntoEmision.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtPuntoEmision.setEnabled(false);
        txtPuntoEmision.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        txtPuntoEmision.setPrompt("000");

        txtNumero.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNumero.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtNumero.setEnabled(false);
        txtNumero.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        txtNumero.setPrompt("0000000");

        txtComprobante.setBackground(new java.awt.Color(0, 102, 102));
        txtComprobante.setForeground(new java.awt.Color(255, 255, 255));
        txtComprobante.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtComprobante.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        txtComprobante.setEnabled(false);
        txtComprobante.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        txtComprobante.setPrompt("000-000-0000000");
        txtComprobante.setPromptForeground(new java.awt.Color(153, 153, 153));

        txtTimbrado.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTimbrado.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtTimbrado.setEnabled(false);
        txtTimbrado.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        txtTimbrado.setPrompt("00000000");

        jLabel7.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel7.setText("Timbrado:");

        jLabel8.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel8.setText("Código Compra:");

        txtCodigoCompra.setBackground(new java.awt.Color(0, 102, 102));
        txtCodigoCompra.setForeground(new java.awt.Color(255, 255, 255));
        txtCodigoCompra.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCodigoCompra.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        txtCodigoCompra.setEnabled(false);
        txtCodigoCompra.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        txtCodigoCompra.setPrompt("Id compra...");
        txtCodigoCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoCompraActionPerformed(evt);
            }
        });
        txtCodigoCompra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodigoCompraKeyTyped(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel9.setText("Fecha:");

        txtFecha.setBackground(new java.awt.Color(0, 102, 102));
        txtFecha.setForeground(new java.awt.Color(255, 255, 255));
        txtFecha.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtFecha.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        txtFecha.setEnabled(false);
        txtFecha.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        txtFecha.setPrompt("Fec. Compra...");
        txtFecha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFechaActionPerformed(evt);
            }
        });
        txtFecha.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtFechaKeyTyped(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel10.setText("Observación:");

        txtObservacionCompra.setBackground(new java.awt.Color(0, 102, 102));
        txtObservacionCompra.setForeground(new java.awt.Color(255, 255, 255));
        txtObservacionCompra.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtObservacionCompra.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        txtObservacionCompra.setEnabled(false);
        txtObservacionCompra.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        txtObservacionCompra.setPrompt("Observaciones que posee la compra...");
        txtObservacionCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtObservacionCompraActionPerformed(evt);
            }
        });
        txtObservacionCompra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtObservacionCompraKeyTyped(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel11.setText("Moneda:");

        txtCodigoMoneda.setBackground(new java.awt.Color(0, 102, 102));
        txtCodigoMoneda.setForeground(new java.awt.Color(255, 255, 255));
        txtCodigoMoneda.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        txtCodigoMoneda.setEnabled(false);
        txtCodigoMoneda.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        txtCodigoMoneda.setPrompt("Cód. Mon.");
        txtCodigoMoneda.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodigoMonedaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodigoMonedaKeyTyped(evt);
            }
        });

        txtDescripcionMoneda.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtDescripcionMoneda.setEnabled(false);
        txtDescripcionMoneda.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        txtDescripcionMoneda.setPrompt("Descripción o nombre de la moneda...");
        txtDescripcionMoneda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDescripcionMonedaActionPerformed(evt);
            }
        });
        txtDescripcionMoneda.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDescripcionMonedaKeyTyped(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel12.setText("Depósito:");

        txtCodigoDeposito.setBackground(new java.awt.Color(0, 102, 102));
        txtCodigoDeposito.setForeground(new java.awt.Color(255, 255, 255));
        txtCodigoDeposito.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        txtCodigoDeposito.setEnabled(false);
        txtCodigoDeposito.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        txtCodigoDeposito.setPrompt("Cód. Dep.");
        txtCodigoDeposito.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodigoDepositoKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodigoDepositoKeyTyped(evt);
            }
        });

        txtDescripcionDeposito.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtDescripcionDeposito.setEnabled(false);
        txtDescripcionDeposito.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        txtDescripcionDeposito.setPrompt("Descripción o nombre del depósito...");
        txtDescripcionDeposito.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDescripcionDepositoActionPerformed(evt);
            }
        });
        txtDescripcionDeposito.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDescripcionDepositoKeyTyped(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel13.setText("Proveedor:");

        txtCodigoProveedor.setBackground(new java.awt.Color(0, 102, 102));
        txtCodigoProveedor.setForeground(new java.awt.Color(255, 255, 255));
        txtCodigoProveedor.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        txtCodigoProveedor.setEnabled(false);
        txtCodigoProveedor.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        txtCodigoProveedor.setPrompt("Cód. Prov.");
        txtCodigoProveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodigoProveedorKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodigoProveedorKeyTyped(evt);
            }
        });

        txtDescripcionProveedor.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtDescripcionProveedor.setEnabled(false);
        txtDescripcionProveedor.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        txtDescripcionProveedor.setPrompt("Descripción o nombre del proveedor...");
        txtDescripcionProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDescripcionProveedorActionPerformed(evt);
            }
        });
        txtDescripcionProveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDescripcionProveedorKeyTyped(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel14.setText("Usuario:");

        txtCodigoUsuario.setBackground(new java.awt.Color(0, 102, 102));
        txtCodigoUsuario.setForeground(new java.awt.Color(255, 255, 255));
        txtCodigoUsuario.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        txtCodigoUsuario.setEnabled(false);
        txtCodigoUsuario.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        txtCodigoUsuario.setPrompt("Cód. Usua.");
        txtCodigoUsuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodigoUsuarioKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodigoUsuarioKeyTyped(evt);
            }
        });

        txtDescripcionUsuario.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtDescripcionUsuario.setEnabled(false);
        txtDescripcionUsuario.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        txtDescripcionUsuario.setPrompt("Descripción o nombre del usuario...");
        txtDescripcionUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDescripcionUsuarioActionPerformed(evt);
            }
        });
        txtDescripcionUsuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDescripcionUsuarioKeyTyped(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel15.setText("Monto Neto:");

        txtTotalNeto.setBackground(new java.awt.Color(0, 102, 102));
        txtTotalNeto.setForeground(new java.awt.Color(255, 255, 255));
        txtTotalNeto.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtTotalNeto.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        txtTotalNeto.setEnabled(false);
        txtTotalNeto.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        txtTotalNeto.setPrompt("Monto Neto...");
        txtTotalNeto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTotalNetoKeyTyped(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel16.setText("Monto Iva:");

        txtTotalIva.setBackground(new java.awt.Color(0, 102, 102));
        txtTotalIva.setForeground(new java.awt.Color(255, 255, 255));
        txtTotalIva.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtTotalIva.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        txtTotalIva.setEnabled(false);
        txtTotalIva.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        txtTotalIva.setPrompt("Monto Iva...");
        txtTotalIva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTotalIvaActionPerformed(evt);
            }
        });
        txtTotalIva.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTotalIvaKeyTyped(evt);
            }
        });

        txtMontoTotal.setBackground(new java.awt.Color(0, 102, 102));
        txtMontoTotal.setForeground(new java.awt.Color(255, 255, 255));
        txtMontoTotal.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtMontoTotal.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        txtMontoTotal.setEnabled(false);
        txtMontoTotal.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        txtMontoTotal.setPrompt("Monto Total...");
        txtMontoTotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMontoTotalActionPerformed(evt);
            }
        });
        txtMontoTotal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtMontoTotalKeyTyped(evt);
            }
        });

        jLabel21.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel21.setText("Monto Total:");

        txtCodigoCuenta.setBackground(new java.awt.Color(0, 102, 102));
        txtCodigoCuenta.setForeground(new java.awt.Color(255, 255, 255));
        txtCodigoCuenta.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        txtCodigoCuenta.setEnabled(false);
        txtCodigoCuenta.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        txtCodigoCuenta.setPrompt("Cód. Cue.");
        txtCodigoCuenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodigoCuentaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodigoCuentaKeyTyped(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel17.setText("Cuenta:");

        txtDescripcionCuenta.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtDescripcionCuenta.setEnabled(false);
        txtDescripcionCuenta.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        txtDescripcionCuenta.setPrompt("Descripción o nombre de la cuenta...");

        jLabel18.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel18.setText("Fecha/Hora Anulado:");

        txtFechaHoraAnulado.setBackground(new java.awt.Color(0, 102, 102));
        txtFechaHoraAnulado.setForeground(new java.awt.Color(255, 255, 255));
        txtFechaHoraAnulado.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtFechaHoraAnulado.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        txtFechaHoraAnulado.setEnabled(false);
        txtFechaHoraAnulado.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        txtFechaHoraAnulado.setPrompt("Fecha y hora de la anulación...");
        txtFechaHoraAnulado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFechaHoraAnuladoActionPerformed(evt);
            }
        });
        txtFechaHoraAnulado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtFechaHoraAnuladoKeyTyped(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel19.setText("Obs. Anulación:");

        txtObservacionAnulacion.setBackground(new java.awt.Color(0, 102, 102));
        txtObservacionAnulacion.setForeground(new java.awt.Color(255, 255, 255));
        txtObservacionAnulacion.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtObservacionAnulacion.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        txtObservacionAnulacion.setEnabled(false);
        txtObservacionAnulacion.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        txtObservacionAnulacion.setPrompt("Observaciones de la anulación...");
        txtObservacionAnulacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtObservacionAnulacionActionPerformed(evt);
            }
        });
        txtObservacionAnulacion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtObservacionAnulacionKeyTyped(evt);
            }
        });

        jLabel20.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel20.setText("Motivo Anulación:");

        txtCodigoMotivoAnulacion.setBackground(new java.awt.Color(0, 102, 102));
        txtCodigoMotivoAnulacion.setForeground(new java.awt.Color(255, 255, 255));
        txtCodigoMotivoAnulacion.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        txtCodigoMotivoAnulacion.setEnabled(false);
        txtCodigoMotivoAnulacion.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        txtCodigoMotivoAnulacion.setPrompt("Cód. Mot. Anul.");
        txtCodigoMotivoAnulacion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodigoMotivoAnulacionKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodigoMotivoAnulacionKeyTyped(evt);
            }
        });

        txtDescripcionMotivoAnulacion.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtDescripcionMotivoAnulacion.setEnabled(false);
        txtDescripcionMotivoAnulacion.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        txtDescripcionMotivoAnulacion.setPrompt("Descripción o nombre del motivo de anulacion..");

        jLabel22.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel22.setText("Usuario Anulación:");

        txtCodigoUsuarioAnulacion.setBackground(new java.awt.Color(0, 102, 102));
        txtCodigoUsuarioAnulacion.setForeground(new java.awt.Color(255, 255, 255));
        txtCodigoUsuarioAnulacion.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        txtCodigoUsuarioAnulacion.setEnabled(false);
        txtCodigoUsuarioAnulacion.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        txtCodigoUsuarioAnulacion.setPrompt("Cód. Usua. Anul.");
        txtCodigoUsuarioAnulacion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodigoUsuarioAnulacionKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodigoUsuarioAnulacionKeyTyped(evt);
            }
        });

        txtDescripcionUsuarioAnulacion.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtDescripcionUsuarioAnulacion.setEnabled(false);
        txtDescripcionUsuarioAnulacion.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        txtDescripcionUsuarioAnulacion.setPrompt("Descripción o nombre del usuario de la anulacion...");

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

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel19, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtCodigoCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtTotalNeto, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txtCodigoProveedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txtCodigoUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txtCodigoDeposito, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txtCodigoMoneda, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtDescripcionUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtDescripcionProveedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtDescripcionDeposito, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtDescripcionMoneda, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(jLabel16)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtTotalIva, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(73, 73, 73)
                                        .addComponent(jLabel21)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtMontoTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(txtDescripcionCuenta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(txtCodigoTipoMovimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtDescripcionTipoMovimiento, javax.swing.GroupLayout.DEFAULT_SIZE, 383, Short.MAX_VALUE)
                                .addGap(8, 8, 8)
                                .addComponent(btnBuscar))
                            .addComponent(txtObservacionCompra, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(txtFechaHoraAnulado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(249, 249, 249))
                            .addComponent(txtObservacionAnulacion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtTimbrado, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                            .addComponent(txtCodigoCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel9)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                            .addComponent(txtEstablecimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(txtPuntoEmision, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(txtNumero, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(txtComprobante, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtCodigoMotivoAnulacion, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtCodigoUsuarioAnulacion, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtDescripcionUsuarioAnulacion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtDescripcionMotivoAnulacion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtCodigoTipoMovimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDescripcionTipoMovimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtEstablecimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPuntoEmision, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtComprobante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTimbrado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCodigoCompra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtObservacionCompra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtCodigoMoneda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDescripcionMoneda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txtCodigoDeposito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDescripcionDeposito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txtCodigoProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDescripcionProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(txtCodigoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDescripcionUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTotalNeto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15)
                    .addComponent(txtTotalIva, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16)
                    .addComponent(txtMontoTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(txtCodigoCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDescripcionCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFechaHoraAnulado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtObservacionAnulacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(txtCodigoMotivoAnulacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDescripcionMotivoAnulacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(txtCodigoUsuarioAnulacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDescripcionUsuarioAnulacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                .addGap(0, 0, 0)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtCodigoTipoMovimientoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoTipoMovimientoActionPerformed
        if (txtCodigoTipoMovimiento.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "NO PUEDE DEJAR EL CAMPO DE TIPO DE MOVIMIENTO VACIO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else {
            int idtipomovimiento = Integer.parseInt(txtCodigoTipoMovimiento.getText());
            tm.setIdtipomovimiento(idtipomovimiento);
            boolean resultado = daoTipoMovimiento.consultarDatosFactura(tm);
            if (resultado == true) {
                txtDescripcionTipoMovimiento.setText(tm.getDescripcion());
                btnBuscar.grabFocus();
            } else {
                txtCodigoTipoMovimiento.setText(null);
                txtDescripcionTipoMovimiento.setText(null);
                txtCodigoTipoMovimiento.grabFocus();
            }
        }
    }//GEN-LAST:event_txtCodigoTipoMovimientoActionPerformed

    private void txtCodigoTipoMovimientoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoTipoMovimientoKeyPressed
        if (evt.VK_F1 == evt.getKeyCode()) {
            buscarTipoMovimiento();
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

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        if (txtCodigoTipoMovimiento.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "NO HA SELECCIONADO UN TIPO DE MOVIMIENTO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else {
            buscarComprasAnuladas();
        }
    }//GEN-LAST:event_btnBuscarActionPerformed

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
        if (txtCriterioTipoMovimiento.getText().length() == 100) {
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

    private void txtCodigoCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoCompraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodigoCompraActionPerformed

    private void txtCodigoCompraKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoCompraKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodigoCompraKeyTyped

    private void txtFechaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFechaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFechaActionPerformed

    private void txtFechaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFechaKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFechaKeyTyped

    private void txtObservacionCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtObservacionCompraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtObservacionCompraActionPerformed

    private void txtObservacionCompraKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtObservacionCompraKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtObservacionCompraKeyTyped

    private void txtCodigoMonedaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoMonedaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodigoMonedaKeyPressed

    private void txtCodigoMonedaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoMonedaKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodigoMonedaKeyTyped

    private void txtDescripcionMonedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDescripcionMonedaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescripcionMonedaActionPerformed

    private void txtDescripcionMonedaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescripcionMonedaKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescripcionMonedaKeyTyped

    private void txtCodigoDepositoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoDepositoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodigoDepositoKeyPressed

    private void txtCodigoDepositoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoDepositoKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodigoDepositoKeyTyped

    private void txtDescripcionDepositoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDescripcionDepositoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescripcionDepositoActionPerformed

    private void txtDescripcionDepositoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescripcionDepositoKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescripcionDepositoKeyTyped

    private void txtCodigoProveedorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoProveedorKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodigoProveedorKeyPressed

    private void txtCodigoProveedorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoProveedorKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodigoProveedorKeyTyped

    private void txtDescripcionProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDescripcionProveedorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescripcionProveedorActionPerformed

    private void txtDescripcionProveedorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescripcionProveedorKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescripcionProveedorKeyTyped

    private void txtCodigoUsuarioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoUsuarioKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodigoUsuarioKeyPressed

    private void txtCodigoUsuarioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoUsuarioKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodigoUsuarioKeyTyped

    private void txtDescripcionUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDescripcionUsuarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescripcionUsuarioActionPerformed

    private void txtDescripcionUsuarioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescripcionUsuarioKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescripcionUsuarioKeyTyped

    private void txtTotalNetoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTotalNetoKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTotalNetoKeyTyped

    private void txtTotalIvaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTotalIvaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTotalIvaActionPerformed

    private void txtTotalIvaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTotalIvaKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTotalIvaKeyTyped

    private void txtMontoTotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMontoTotalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMontoTotalActionPerformed

    private void txtMontoTotalKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMontoTotalKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMontoTotalKeyTyped

    private void txtCodigoCuentaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoCuentaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodigoCuentaKeyPressed

    private void txtCodigoCuentaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoCuentaKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodigoCuentaKeyTyped

    private void txtFechaHoraAnuladoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFechaHoraAnuladoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFechaHoraAnuladoActionPerformed

    private void txtFechaHoraAnuladoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFechaHoraAnuladoKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFechaHoraAnuladoKeyTyped

    private void txtObservacionAnulacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtObservacionAnulacionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtObservacionAnulacionActionPerformed

    private void txtObservacionAnulacionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtObservacionAnulacionKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtObservacionAnulacionKeyTyped

    private void txtCodigoMotivoAnulacionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoMotivoAnulacionKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodigoMotivoAnulacionKeyPressed

    private void txtCodigoMotivoAnulacionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoMotivoAnulacionKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodigoMotivoAnulacionKeyTyped

    private void txtCodigoUsuarioAnulacionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoUsuarioAnulacionKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodigoUsuarioAnulacionKeyPressed

    private void txtCodigoUsuarioAnulacionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoUsuarioAnulacionKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodigoUsuarioAnulacionKeyTyped

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        int res = JOptionPane.showConfirmDialog(null, "¿ESTA SEGURO DE CANCELAR LOS CAMBIOS?", "ADVERTENCIA", JOptionPane.YES_NO_OPTION);
        if (res != 1) {
            limpiarCampos();
        }
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void txtCriterioComprasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCriterioComprasActionPerformed
        cargarComprasAnuladas();
    }//GEN-LAST:event_txtCriterioComprasActionPerformed

    private void txtCriterioComprasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCriterioComprasKeyPressed
        if (evt.VK_ESCAPE == evt.getKeyCode()) {
            txtEstablecimiento.setText(null);
            txtPuntoEmision.setText(null);
            txtNumero.setText(null);
            txtComprobante.setText(null);
            txtTimbrado.setText(null);
            txtCodigoCompra.setText(null);
            txtFecha.setText(null);
            txtObservacionCompra.setText(null);
            txtCodigoMoneda.setText(null);
            txtDescripcionMoneda.setText(null);
            txtCodigoDeposito.setText(null);
            txtDescripcionDeposito.setText(null);
            txtCodigoProveedor.setText(null);
            txtDescripcionProveedor.setText(null);
            txtCodigoUsuario.setText(null);
            txtDescripcionUsuario.setText(null);
            txtTotalNeto.setText(null);
            txtTotalIva.setText(null);
            txtCodigoCuenta.setText(null);
            txtDescripcionCuenta.setText(null);
            btnBuscar.grabFocus();
            BuscadorCompras.dispose();
        }
    }//GEN-LAST:event_txtCriterioComprasKeyPressed

    private void txtCriterioComprasKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCriterioComprasKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            evt.setKeyChar(Character.toUpperCase(c));
        }
        if (txtCriterioCompras.getText().length() == 100) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCriterioComprasKeyTyped

    private void tablaDatosComprasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaDatosComprasMouseClicked
        if (evt.getClickCount() == 2) {
            if (tablaDatosCompras.getSelectedRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "SELECCIONE UNA FILA");
            } else {
                txtCriterioCompras.setText(null);
                BuscadorCompras.dispose();
            }
        }
    }//GEN-LAST:event_tablaDatosComprasMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDialog BuscadorCompras;
    private javax.swing.JDialog BuscadorTipoMovimiento;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCancelar;
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
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable tablaDatosCompras;
    private javax.swing.JTable tablaDatosTipoMovimiento;
    private org.jdesktop.swingx.JXTextField txtCodigoCompra;
    private org.jdesktop.swingx.JXTextField txtCodigoCuenta;
    private org.jdesktop.swingx.JXTextField txtCodigoDeposito;
    private org.jdesktop.swingx.JXTextField txtCodigoMoneda;
    private org.jdesktop.swingx.JXTextField txtCodigoMotivoAnulacion;
    private org.jdesktop.swingx.JXTextField txtCodigoProveedor;
    private org.jdesktop.swingx.JXTextField txtCodigoTipoMovimiento;
    private org.jdesktop.swingx.JXTextField txtCodigoUsuario;
    private org.jdesktop.swingx.JXTextField txtCodigoUsuarioAnulacion;
    private org.jdesktop.swingx.JXTextField txtComprobante;
    private org.jdesktop.swingx.JXTextField txtCriterioCompras;
    private org.jdesktop.swingx.JXTextField txtCriterioTipoMovimiento;
    private org.jdesktop.swingx.JXTextField txtDescripcionCuenta;
    private org.jdesktop.swingx.JXTextField txtDescripcionDeposito;
    private org.jdesktop.swingx.JXTextField txtDescripcionMoneda;
    private org.jdesktop.swingx.JXTextField txtDescripcionMotivoAnulacion;
    private org.jdesktop.swingx.JXTextField txtDescripcionProveedor;
    private org.jdesktop.swingx.JXTextField txtDescripcionTipoMovimiento;
    private org.jdesktop.swingx.JXTextField txtDescripcionUsuario;
    private org.jdesktop.swingx.JXTextField txtDescripcionUsuarioAnulacion;
    private org.jdesktop.swingx.JXTextField txtEstablecimiento;
    private org.jdesktop.swingx.JXTextField txtFecha;
    private org.jdesktop.swingx.JXTextField txtFechaHoraAnulado;
    private org.jdesktop.swingx.JXTextField txtMontoTotal;
    private org.jdesktop.swingx.JXTextField txtNumero;
    private org.jdesktop.swingx.JXTextField txtObservacionAnulacion;
    private org.jdesktop.swingx.JXTextField txtObservacionCompra;
    private org.jdesktop.swingx.JXTextField txtPuntoEmision;
    private org.jdesktop.swingx.JXTextField txtTimbrado;
    private org.jdesktop.swingx.JXTextField txtTotalIva;
    private org.jdesktop.swingx.JXTextField txtTotalNeto;
    // End of variables declaration//GEN-END:variables
}
