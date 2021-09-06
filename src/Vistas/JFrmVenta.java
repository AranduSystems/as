package Vistas;

import App.appLogin;
import Dao.DAOCliente;
import Dao.DAOConfiguracion;
import Dao.DAOCotizacion;
import Dao.DAOCuenta;
import Dao.DAOImpresora;
import Dao.DAOImpresoraTimbrado;
import Dao.DAOMoneda;
import Dao.DAOTipoMovimiento;
import Dao.DAOUsuarioImpresora;
import Dao.DAOVenta;
import Dao.DAODeposito;
import Dao.DAOListaPrecio;
import Dao.DAOVendedor;
import Modelos.Cliente;
import Modelos.Configuracion;
import Modelos.Cuenta;
import Modelos.Deposito;
import Modelos.Impresora;
import Modelos.ImpresoraTimbrado;
import Modelos.ListaPrecio;
import Modelos.Moneda;
import Modelos.TipoMovimiento;
import Modelos.UsuarioImpresora;
import Modelos.Vendedor;
import Modelos.Venta;
import java.util.Date;
import javax.swing.JOptionPane;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author armando
 */
public class JFrmVenta extends javax.swing.JInternalFrame {

    Configuracion co = new Configuracion();
    UsuarioImpresora ui = new UsuarioImpresora();
    ImpresoraTimbrado it = new ImpresoraTimbrado();
    TipoMovimiento tm = new TipoMovimiento();
    Impresora i = new Impresora();
    Venta v = new Venta();
    Cuenta cu = new Cuenta();
    Moneda m = new Moneda();
    Deposito d = new Deposito();
    Cliente cli = new Cliente();
    ListaPrecio lp = new ListaPrecio();
    Vendedor ven = new Vendedor();

    DAOCotizacion daoCotizacion = new DAOCotizacion();
    DAOConfiguracion daoConfiguracion = new DAOConfiguracion();
    DAOUsuarioImpresora daoUsuarioImpresora = new DAOUsuarioImpresora();
    DAOImpresoraTimbrado daoImpresoraTimbrado = new DAOImpresoraTimbrado();
    DAOTipoMovimiento daoTipoMovimiento = new DAOTipoMovimiento();
    DAOImpresora daoImpresora = new DAOImpresora();
    DAOVenta dao = new DAOVenta();
    DAOCuenta daoCuenta = new DAOCuenta();
    DAOMoneda daoMoneda = new DAOMoneda();
    DAODeposito daoDeposito = new DAODeposito();
    DAOCliente daoCliente = new DAOCliente();
    DAOListaPrecio daoListaPrecio = new DAOListaPrecio();
    DAOVendedor daoVendedor = new DAOVendedor();

    ArrayList<Object[]> datosMoneda = new ArrayList<>();
    ArrayList<Object[]> datosCuenta = new ArrayList<>();
    ArrayList<Object[]> datosDeposito = new ArrayList<>();
    ArrayList<Object[]> datosListaPrecio = new ArrayList<>();
    ArrayList<Object[]> datosCliente = new ArrayList<>();
    ArrayList<Object[]> datosVendedor = new ArrayList<>();

    Date SYSDATE = new Date();
    SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat formatoUS = new SimpleDateFormat("yyyy-MM-dd");
    String tres_ceros = String.format("%%0%dd", 3);
    String siete_ceros = String.format("%%0%dd", 7);

    int Establecimiento;
    int idempresa = 0;
    int idsucursal = 0;
    int idtipomovimientocontado;
    int idtipomovimientocredito;
    String ultimo_numero_factura = "";
    int idimpresora;

    /**
     * Creates new form JFrmVenta
     */
    public JFrmVenta() {
        setTitle("JFrmVenta");
        initComponents();
        txtFecha.setFormats(formato);
        String fechaActual = formato.format(SYSDATE);
        txtFecha.setDate(daoCotizacion.parseFecha(fechaActual));
        rbContado.setSelected(true);
    }

    public void obtenerConfiguracion() {
        boolean resultado;
        int codigosucursal = appLogin.IDSUCURSAL;
        idempresa = appLogin.IDEMPRESA;
        idsucursal = appLogin.IDSUCURSAL;
        co.setIdsucursal(codigosucursal);
        resultado = daoConfiguracion.consultarDatos(co);
        if (resultado == true) {
            idtipomovimientocontado = co.getFac_con_emi();
            idtipomovimientocredito = co.getFac_cre_emi();
        } else {
            JOptionPane.showMessageDialog(null, "NO SE HA ENCONTRADO LA CONFIGURACIÓN NECESARIA.\n"
                    + " VERIFIQUE EN SISTEMA GENERAL SI LA CONFIGURACIÓN ESTA CARGADA DE FORMA CORRECTA. ", "ADVERTENCIA", JOptionPane.ERROR_MESSAGE);
            dispose();
        }
    }

    public void obtenerImpresora() {
        boolean resultadoImpresora;
        int idusuario = appLogin.IDUSUARIO;
        ui.setIdusuario(idusuario);
        resultadoImpresora = daoUsuarioImpresora.consultarDatosImpresora(ui);
        if (resultadoImpresora) {
            idimpresora = ui.getIdimpresora();
            boolean resultadoUltimoNumeroFactura;
            i.setIdimpresora(idimpresora);
            resultadoUltimoNumeroFactura = daoImpresora.consultarDatos(i);
            if (resultadoUltimoNumeroFactura) {
                ultimo_numero_factura = i.getUltimo_numero_factura();
                obtenerTimbrado();
            }
        } else {
            JOptionPane.showMessageDialog(null, "EL USUARIO NO TIENE HABILITADO NINGUNA IMPRESORA.\nINGRESE EN SISTEMA Y ASIGNE UNA IMPRESORA PRIMERO", "ERROR", JOptionPane.ERROR_MESSAGE);
            dispose();
        }
    }

    public void obtenerTimbrado() {
        int idtipomovimiento;
        if (rbContado.isSelected()) {
            idtipomovimiento = idtipomovimientocontado;
        } else {
            idtipomovimiento = idtipomovimientocredito;
        }
        tm.setIdtipomovimiento(idtipomovimiento);
        boolean resultado = daoTipoMovimiento.consultarDatos(tm);
        int idtipo;
        if (resultado) {
            idtipo = tm.getIdtipo();
            it.setIdimpresora(idimpresora);
            it.setIdtipocomprobante(idtipo);
            Date fecha = txtFecha.getDate();
            java.sql.Date fechaSQL = new java.sql.Date(fecha.getTime());
            boolean resultadoTimbrado = daoImpresoraTimbrado.consultarDatosTimbrado(it, fechaSQL);
            if (resultadoTimbrado) {
                int timbrado = it.getNumerotimbrado();
                int numeroInicial = it.getNumeroinicial();
                int numeroFinal = it.getNumerofinal();

                txtTimbrado.setText("" + timbrado);
                int establecimiento = Integer.parseInt(ultimo_numero_factura.substring(0, 3));
                int puntoemision = Integer.parseInt(ultimo_numero_factura.substring(4, 7));
                int numero = Integer.parseInt(ultimo_numero_factura.substring(8, 15));
                txtEstablecimiento.setText(String.format(tres_ceros, establecimiento));
                txtPuntoEmision.setText(String.format(tres_ceros, puntoemision));
                txtNumero.setText(String.format(siete_ceros, (numero + 1)));
                String nuevo_numero_factura = (String.format(tres_ceros, establecimiento) + "-" + String.format(tres_ceros, puntoemision) + "-" + String.format(siete_ceros, (numero + 1)));
                txtComprobante.setText(nuevo_numero_factura);
                int nuevo_numero = numero + 1;
                if ((nuevo_numero + 1) < numeroFinal && (nuevo_numero + 1) > numeroInicial) {
                    //VALIDAR DUPLICACION DE DOCUMENTO
                    boolean existe = dao.verificarExistenciaVenta(txtComprobante.getText(), Integer.parseInt(txtTimbrado.getText()));
                    if (existe) {
                        JOptionPane.showMessageDialog(null, "EL TIMBRADO Y EL NUMERO DE DOCUMENTO YA FUE INGRESADO.\n"
                                + "VERIFIQUE LA IMPRESORA Y EL TIMBRADO EN MANTENIMIENTOS", "ERROR", JOptionPane.ERROR_MESSAGE);
                        dispose();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "EL TIMBRADO YA NO POSEE NUMEROS DISPONIBLES...", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "NO SE HAN ENCONTRADO LOS DATOS PARA VERIFICAR EL TIMBRADO...", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void cargarMoneda() {
        DefaultTableModel modelo = (DefaultTableModel) tablaDatosMonedas.getModel();
        modelo.setRowCount(0);
        datosMoneda = daoMoneda.consultar(txtCriterioMoneda.getText());
        for (Object[] obj : datosMoneda) {
            modelo.addRow(obj);
        }
        this.tablaDatosMonedas.setModel(modelo);
    }

    private void buscarMoneda() {
        cargarMoneda();
        BuscadorMoneda.setModal(true);
        BuscadorMoneda.setSize(540, 285);
        BuscadorMoneda.setLocationRelativeTo(this);
        BuscadorMoneda.setVisible(true);
        int fila = tablaDatosMonedas.getSelectedRow();
        if (fila >= 0) {
            txtCodigoMoneda.setText(tablaDatosMonedas.getValueAt(fila, 0).toString());
            txtDescripcionMoneda.setText(tablaDatosMonedas.getValueAt(fila, 1).toString());
        } else {
            txtCodigoMoneda.setText(null);
            txtDescripcionMoneda.setText(null);
        }
    }

    public void cargarCuenta() {
        DefaultTableModel modelo = (DefaultTableModel) tablaDatosCuenta.getModel();
        modelo.setRowCount(0);
        int idmoneda = Integer.parseInt(txtCodigoMoneda.getText());
        datosCuenta = daoCuenta.consultar(txtCriterioCuenta.getText(), idmoneda);
        for (Object[] obj : datosCuenta) {
            modelo.addRow(obj);
        }
        this.tablaDatosCuenta.setModel(modelo);
    }

    private void buscarCuenta() {
        cargarCuenta();
        BuscadorCuenta.setModal(true);
        BuscadorCuenta.setSize(540, 285);
        BuscadorCuenta.setLocationRelativeTo(this);
        BuscadorCuenta.setVisible(true);
        int fila = tablaDatosCuenta.getSelectedRow();
        if (fila >= 0) {
            txtCodigoCuenta.setText(tablaDatosCuenta.getValueAt(fila, 0).toString());
            txtDescripcionCuenta.setText(tablaDatosCuenta.getValueAt(fila, 1).toString());
        } else {
            txtCodigoCuenta.setText(null);
            txtDescripcionCuenta.setText(null);
        }
    }

    public void cargarDeposito() {
        DefaultTableModel modelo = (DefaultTableModel) tablaDatosDepositos.getModel();
        modelo.setRowCount(0);
        datosDeposito = daoDeposito.consultar(txtCriterioDeposito.getText());
        for (Object[] obj : datosDeposito) {
            modelo.addRow(obj);
        }
        this.tablaDatosDepositos.setModel(modelo);
    }

    private void buscarDeposito() {
        cargarDeposito();
        BuscadorDeposito.setModal(true);
        BuscadorDeposito.setSize(540, 285);
        BuscadorDeposito.setLocationRelativeTo(this);
        BuscadorDeposito.setVisible(true);
        int fila = tablaDatosDepositos.getSelectedRow();
        if (fila >= 0) {
            txtCodigoDeposito.setText(tablaDatosDepositos.getValueAt(fila, 0).toString());
            txtDescripcionDeposito.setText(tablaDatosDepositos.getValueAt(fila, 1).toString());
        } else {
            txtCodigoDeposito.setText(null);
            txtDescripcionDeposito.setText(null);
        }
    }

    public void cargarListaPrecio() {
        DefaultTableModel modelo = (DefaultTableModel) tablaDatosListaPrecio.getModel();
        modelo.setRowCount(0);
        int idmoneda = Integer.parseInt(txtCodigoMoneda.getText());
        datosListaPrecio = daoListaPrecio.consultar(txtCriterioListaPrecio.getText(), idmoneda);
        for (Object[] obj : datosListaPrecio) {
            modelo.addRow(obj);
        }
        this.tablaDatosListaPrecio.setModel(modelo);
    }

    private void buscarListaPrecio() {
        cargarListaPrecio();
        BuscadorListaPrecio.setModal(true);
        BuscadorListaPrecio.setSize(540, 285);
        BuscadorListaPrecio.setLocationRelativeTo(this);
        BuscadorListaPrecio.setVisible(true);
        int fila = tablaDatosListaPrecio.getSelectedRow();
        if (fila >= 0) {
            txtCodigoLista.setText(tablaDatosListaPrecio.getValueAt(fila, 0).toString());
            txtDescripcionListaPrecio.setText(tablaDatosListaPrecio.getValueAt(fila, 1).toString());
        } else {
            txtCodigoLista.setText(null);
            txtDescripcionListaPrecio.setText(null);
        }
    }

    public void cargarCliente() {
        DefaultTableModel modelo = (DefaultTableModel) tablaDatosCliente.getModel();
        modelo.setRowCount(0);
        datosCliente = daoCliente.consultar(txtCriterioCliente.getText());
        for (Object[] obj : datosCliente) {
            modelo.addRow(obj);
        }
        this.tablaDatosCliente.setModel(modelo);
    }

    private void buscarCliente() {
        cargarCliente();
        BuscadorCliente.setModal(true);
        BuscadorCliente.setSize(540, 285);
        BuscadorCliente.setLocationRelativeTo(this);
        BuscadorCliente.setVisible(true);
        int fila = tablaDatosCliente.getSelectedRow();
        if (fila >= 0) {
            int idcliente = Integer.parseInt(tablaDatosCliente.getValueAt(fila, 0).toString());
            cli.setIdcliente(idcliente);
            daoCliente.consultarDatos(cli);
            txtCodigoCliente.setText("" + idcliente);
            txtDescripcionCliente.setText(cli.getNombre() + " " + cli.getApellido());
        } else {
            txtCodigoCliente.setText(null);
            txtDescripcionCliente.setText(null);
        }
    }
    public void cargarVendedor() {
        DefaultTableModel modelo = (DefaultTableModel) tablaDatosVendedor.getModel();
        modelo.setRowCount(0);
        datosVendedor = daoVendedor.consultar(txtCriterioVendedor.getText());
        for (Object[] obj : datosVendedor) {
            modelo.addRow(obj);
        }
        this.tablaDatosVendedor.setModel(modelo);
    }
    
    private void buscarVendedor() {
        cargarVendedor();
        BuscadorMarca.setModal(true);
        BuscadorMarca.setSize(540, 285);
        BuscadorMarca.setLocationRelativeTo(this);
        BuscadorMarca.setVisible(true);
        int fila = tablaDatosVendedor.getSelectedRow();
        if (fila >= 0) {
            txtCodigoVendedor.setText(tablaDatosVendedor.getValueAt(fila, 0).toString());
            txtDescripcionVendedor.setText(tablaDatosVendedor.getValueAt(fila, 1).toString());
        } else {
            txtCodigoVendedor.setText(null);
            txtDescripcionVendedor.setText(null);
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

        grupoContadoCredito = new javax.swing.ButtonGroup();
        BuscadorMoneda = new javax.swing.JDialog();
        jPanel4 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        txtCriterioMoneda = new org.jdesktop.swingx.JXTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaDatosMonedas = new javax.swing.JTable();
        BuscadorCuenta = new javax.swing.JDialog();
        jPanel13 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        txtCriterioCuenta = new org.jdesktop.swingx.JXTextField();
        jScrollPane7 = new javax.swing.JScrollPane();
        tablaDatosCuenta = new javax.swing.JTable();
        BuscadorDeposito = new javax.swing.JDialog();
        jPanel5 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        txtCriterioDeposito = new org.jdesktop.swingx.JXTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaDatosDepositos = new javax.swing.JTable();
        BuscadorListaPrecio = new javax.swing.JDialog();
        jPanel6 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        txtCriterioListaPrecio = new org.jdesktop.swingx.JXTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        tablaDatosListaPrecio = new javax.swing.JTable();
        BuscadorCliente = new javax.swing.JDialog();
        jPanel7 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        txtCriterioCliente = new org.jdesktop.swingx.JXTextField();
        jScrollPane5 = new javax.swing.JScrollPane();
        tablaDatosCliente = new javax.swing.JTable();
        BuscadorMarca = new javax.swing.JDialog();
        jPanel8 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        txtCriterioVendedor = new org.jdesktop.swingx.JXTextField();
        jScrollPane6 = new javax.swing.JScrollPane();
        tablaDatosVendedor = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        rbContado = new javax.swing.JRadioButton();
        rbCredito = new javax.swing.JRadioButton();
        jLabel3 = new javax.swing.JLabel();
        txtFecha = new org.jdesktop.swingx.JXDatePicker();
        jLabel4 = new javax.swing.JLabel();
        txtEstablecimiento = new org.jdesktop.swingx.JXTextField();
        txtPuntoEmision = new org.jdesktop.swingx.JXTextField();
        txtNumero = new org.jdesktop.swingx.JXTextField();
        txtComprobante = new org.jdesktop.swingx.JXTextField();
        jLabel5 = new javax.swing.JLabel();
        txtTimbrado = new org.jdesktop.swingx.JXTextField();
        jLabel21 = new javax.swing.JLabel();
        txtCodigoCuenta = new org.jdesktop.swingx.JXTextField();
        txtDescripcionCuenta = new org.jdesktop.swingx.JXTextField();
        jLabel8 = new javax.swing.JLabel();
        txtCodigoMoneda = new org.jdesktop.swingx.JXTextField();
        txtDescripcionMoneda = new org.jdesktop.swingx.JXTextField();
        jLabel6 = new javax.swing.JLabel();
        txtCodigoDeposito = new org.jdesktop.swingx.JXTextField();
        txtDescripcionDeposito = new org.jdesktop.swingx.JXTextField();
        jLabel9 = new javax.swing.JLabel();
        txtCodigoCliente = new org.jdesktop.swingx.JXTextField();
        txtDescripcionCliente = new org.jdesktop.swingx.JXTextField();
        txtDescripcionListaPrecio = new org.jdesktop.swingx.JXTextField();
        jLabel12 = new javax.swing.JLabel();
        txtCodigoLista = new org.jdesktop.swingx.JXTextField();
        jLabel15 = new javax.swing.JLabel();
        txtCodigoVendedor = new org.jdesktop.swingx.JXTextField();
        txtDescripcionVendedor = new org.jdesktop.swingx.JXTextField();

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jLabel10.setBackground(new java.awt.Color(50, 104, 151));
        jLabel10.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("BUSCADOR DE MONEDAS");
        jLabel10.setOpaque(true);

        txtCriterioMoneda.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtCriterioMoneda.setPrompt("Aqui puede ingresar los filtros para la busqueda..");
        txtCriterioMoneda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCriterioMonedaActionPerformed(evt);
            }
        });
        txtCriterioMoneda.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCriterioMonedaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCriterioMonedaKeyTyped(evt);
            }
        });

        tablaDatosMonedas.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        tablaDatosMonedas.setModel(new javax.swing.table.DefaultTableModel(
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
        tablaDatosMonedas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaDatosMonedasMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tablaDatosMonedas);
        if (tablaDatosMonedas.getColumnModel().getColumnCount() > 0) {
            tablaDatosMonedas.getColumnModel().getColumn(0).setMinWidth(60);
            tablaDatosMonedas.getColumnModel().getColumn(0).setPreferredWidth(60);
            tablaDatosMonedas.getColumnModel().getColumn(0).setMaxWidth(60);
        }

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCriterioMoneda, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCriterioMoneda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout BuscadorMonedaLayout = new javax.swing.GroupLayout(BuscadorMoneda.getContentPane());
        BuscadorMoneda.getContentPane().setLayout(BuscadorMonedaLayout);
        BuscadorMonedaLayout.setHorizontalGroup(
            BuscadorMonedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BuscadorMonedaLayout.setVerticalGroup(
            BuscadorMonedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));

        jLabel22.setBackground(new java.awt.Color(50, 104, 151));
        jLabel22.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setText("BUSCADOR DE CUENTAS");
        jLabel22.setOpaque(true);

        txtCriterioCuenta.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtCriterioCuenta.setPrompt("Aqui puede ingresar los filtros para la busqueda..");
        txtCriterioCuenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCriterioCuentaActionPerformed(evt);
            }
        });
        txtCriterioCuenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCriterioCuentaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCriterioCuentaKeyTyped(evt);
            }
        });

        tablaDatosCuenta.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        tablaDatosCuenta.setModel(new javax.swing.table.DefaultTableModel(
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
        tablaDatosCuenta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaDatosCuentaMouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(tablaDatosCuenta);
        if (tablaDatosCuenta.getColumnModel().getColumnCount() > 0) {
            tablaDatosCuenta.getColumnModel().getColumn(0).setMinWidth(60);
            tablaDatosCuenta.getColumnModel().getColumn(0).setPreferredWidth(60);
            tablaDatosCuenta.getColumnModel().getColumn(0).setMaxWidth(60);
        }

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCriterioCuenta, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCriterioCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout BuscadorCuentaLayout = new javax.swing.GroupLayout(BuscadorCuenta.getContentPane());
        BuscadorCuenta.getContentPane().setLayout(BuscadorCuentaLayout);
        BuscadorCuentaLayout.setHorizontalGroup(
            BuscadorCuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BuscadorCuentaLayout.setVerticalGroup(
            BuscadorCuentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jLabel11.setBackground(new java.awt.Color(50, 104, 151));
        jLabel11.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("BUSCADOR DE DEPÓSITOS");
        jLabel11.setOpaque(true);

        txtCriterioDeposito.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtCriterioDeposito.setPrompt("Aqui puede ingresar los filtros para la busqueda..");
        txtCriterioDeposito.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCriterioDepositoActionPerformed(evt);
            }
        });
        txtCriterioDeposito.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCriterioDepositoKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCriterioDepositoKeyTyped(evt);
            }
        });

        tablaDatosDepositos.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        tablaDatosDepositos.setModel(new javax.swing.table.DefaultTableModel(
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
        tablaDatosDepositos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaDatosDepositosMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tablaDatosDepositos);
        if (tablaDatosDepositos.getColumnModel().getColumnCount() > 0) {
            tablaDatosDepositos.getColumnModel().getColumn(0).setMinWidth(60);
            tablaDatosDepositos.getColumnModel().getColumn(0).setPreferredWidth(60);
            tablaDatosDepositos.getColumnModel().getColumn(0).setMaxWidth(60);
        }

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCriterioDeposito, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCriterioDeposito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout BuscadorDepositoLayout = new javax.swing.GroupLayout(BuscadorDeposito.getContentPane());
        BuscadorDeposito.getContentPane().setLayout(BuscadorDepositoLayout);
        BuscadorDepositoLayout.setHorizontalGroup(
            BuscadorDepositoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BuscadorDepositoLayout.setVerticalGroup(
            BuscadorDepositoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        jLabel13.setBackground(new java.awt.Color(50, 104, 151));
        jLabel13.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("BUSCADOR DE LISTAS DE PRECIOS");
        jLabel13.setOpaque(true);

        txtCriterioListaPrecio.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtCriterioListaPrecio.setPrompt("Aqui puede ingresar los filtros para la busqueda..");
        txtCriterioListaPrecio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCriterioListaPrecioActionPerformed(evt);
            }
        });
        txtCriterioListaPrecio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCriterioListaPrecioKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCriterioListaPrecioKeyTyped(evt);
            }
        });

        tablaDatosListaPrecio.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        tablaDatosListaPrecio.setModel(new javax.swing.table.DefaultTableModel(
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
        tablaDatosListaPrecio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaDatosListaPrecioMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tablaDatosListaPrecio);
        if (tablaDatosListaPrecio.getColumnModel().getColumnCount() > 0) {
            tablaDatosListaPrecio.getColumnModel().getColumn(0).setMinWidth(60);
            tablaDatosListaPrecio.getColumnModel().getColumn(0).setPreferredWidth(60);
            tablaDatosListaPrecio.getColumnModel().getColumn(0).setMaxWidth(60);
        }

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCriterioListaPrecio, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCriterioListaPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout BuscadorListaPrecioLayout = new javax.swing.GroupLayout(BuscadorListaPrecio.getContentPane());
        BuscadorListaPrecio.getContentPane().setLayout(BuscadorListaPrecioLayout);
        BuscadorListaPrecioLayout.setHorizontalGroup(
            BuscadorListaPrecioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BuscadorListaPrecioLayout.setVerticalGroup(
            BuscadorListaPrecioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        jLabel14.setBackground(new java.awt.Color(50, 104, 151));
        jLabel14.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("BUSCADOR DE CLIENTES");
        jLabel14.setOpaque(true);

        txtCriterioCliente.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtCriterioCliente.setPrompt("Aqui puede ingresar los filtros para la busqueda..");
        txtCriterioCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCriterioClienteActionPerformed(evt);
            }
        });
        txtCriterioCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCriterioClienteKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCriterioClienteKeyTyped(evt);
            }
        });

        tablaDatosCliente.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        tablaDatosCliente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "<html><p style=\"text-align:center\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Código</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Nombre</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Apellido</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Ruc</span></span></span></p></html> "
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
        tablaDatosCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaDatosClienteMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tablaDatosCliente);
        if (tablaDatosCliente.getColumnModel().getColumnCount() > 0) {
            tablaDatosCliente.getColumnModel().getColumn(0).setMinWidth(60);
            tablaDatosCliente.getColumnModel().getColumn(0).setPreferredWidth(60);
            tablaDatosCliente.getColumnModel().getColumn(0).setMaxWidth(60);
            tablaDatosCliente.getColumnModel().getColumn(3).setMinWidth(100);
            tablaDatosCliente.getColumnModel().getColumn(3).setPreferredWidth(100);
            tablaDatosCliente.getColumnModel().getColumn(3).setMaxWidth(100);
        }

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCriterioCliente, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCriterioCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout BuscadorClienteLayout = new javax.swing.GroupLayout(BuscadorCliente.getContentPane());
        BuscadorCliente.getContentPane().setLayout(BuscadorClienteLayout);
        BuscadorClienteLayout.setHorizontalGroup(
            BuscadorClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BuscadorClienteLayout.setVerticalGroup(
            BuscadorClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));

        jLabel16.setBackground(new java.awt.Color(50, 104, 151));
        jLabel16.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("BUSCADOR DE VENDEDORES");
        jLabel16.setOpaque(true);

        txtCriterioVendedor.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtCriterioVendedor.setPrompt("Aqui puede ingresar los filtros para la busqueda..");
        txtCriterioVendedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCriterioVendedorActionPerformed(evt);
            }
        });
        txtCriterioVendedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCriterioVendedorKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCriterioVendedorKeyTyped(evt);
            }
        });

        tablaDatosVendedor.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        tablaDatosVendedor.setModel(new javax.swing.table.DefaultTableModel(
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
        tablaDatosVendedor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaDatosVendedorMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(tablaDatosVendedor);
        if (tablaDatosVendedor.getColumnModel().getColumnCount() > 0) {
            tablaDatosVendedor.getColumnModel().getColumn(0).setMinWidth(60);
            tablaDatosVendedor.getColumnModel().getColumn(0).setPreferredWidth(60);
            tablaDatosVendedor.getColumnModel().getColumn(0).setMaxWidth(60);
        }

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCriterioVendedor, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCriterioVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout BuscadorMarcaLayout = new javax.swing.GroupLayout(BuscadorMarca.getContentPane());
        BuscadorMarca.getContentPane().setLayout(BuscadorMarcaLayout);
        BuscadorMarcaLayout.setHorizontalGroup(
            BuscadorMarcaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BuscadorMarcaLayout.setVerticalGroup(
            BuscadorMarcaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setClosable(true);
        setIconifiable(true);
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameOpened(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(50, 104, 151));

        jLabel1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Movimiento de Ventas");

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

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel2.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel2.setText("Tipo de Venta:");

        grupoContadoCredito.add(rbContado);
        rbContado.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        rbContado.setSelected(true);
        rbContado.setText("CONTADO");
        rbContado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbContadoActionPerformed(evt);
            }
        });
        rbContado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                rbContadoKeyPressed(evt);
            }
        });

        grupoContadoCredito.add(rbCredito);
        rbCredito.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        rbCredito.setText("CRÉDITO");
        rbCredito.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbCreditoActionPerformed(evt);
            }
        });
        rbCredito.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                rbCreditoKeyPressed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel3.setText("Fecha:");

        txtFecha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFechaActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel4.setText("Comprobante:");

        txtEstablecimiento.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtEstablecimiento.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtEstablecimiento.setPrompt("000");
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

        txtPuntoEmision.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtPuntoEmision.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtPuntoEmision.setPrompt("000");
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

        txtNumero.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNumero.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtNumero.setPrompt("0000000");
        txtNumero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumeroActionPerformed(evt);
            }
        });
        txtNumero.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNumeroKeyTyped(evt);
            }
        });

        txtComprobante.setBackground(new java.awt.Color(0, 102, 102));
        txtComprobante.setForeground(new java.awt.Color(255, 255, 255));
        txtComprobante.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtComprobante.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        txtComprobante.setEnabled(false);
        txtComprobante.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        txtComprobante.setPrompt("000-000-0000000");
        txtComprobante.setPromptForeground(new java.awt.Color(153, 153, 153));

        jLabel5.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel5.setText("Timbrado:");

        txtTimbrado.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTimbrado.setEnabled(false);
        txtTimbrado.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtTimbrado.setPrompt("00000000");
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

        jLabel21.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel21.setText("Cuenta:");

        txtCodigoCuenta.setEnabled(false);
        txtCodigoCuenta.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtCodigoCuenta.setPrompt("Cód. Cue.");
        txtCodigoCuenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoCuentaActionPerformed(evt);
            }
        });
        txtCodigoCuenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodigoCuentaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodigoCuentaKeyTyped(evt);
            }
        });

        txtDescripcionCuenta.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtDescripcionCuenta.setEnabled(false);
        txtDescripcionCuenta.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        txtDescripcionCuenta.setPrompt("Descripción o nombre de la cuenta...");

        jLabel8.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("Moneda:");

        txtCodigoMoneda.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtCodigoMoneda.setPrompt("Cód. Mon.");
        txtCodigoMoneda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoMonedaActionPerformed(evt);
            }
        });
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

        jLabel6.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Depósito:");

        txtCodigoDeposito.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtCodigoDeposito.setPrompt("Cód. Dep.");
        txtCodigoDeposito.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoDepositoActionPerformed(evt);
            }
        });
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

        jLabel9.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("Cliente:");

        txtCodigoCliente.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtCodigoCliente.setPrompt("Cód. Cli.");
        txtCodigoCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoClienteActionPerformed(evt);
            }
        });
        txtCodigoCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodigoClienteKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodigoClienteKeyTyped(evt);
            }
        });

        txtDescripcionCliente.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtDescripcionCliente.setEnabled(false);
        txtDescripcionCliente.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        txtDescripcionCliente.setPrompt("Nombre del cliente...");

        txtDescripcionListaPrecio.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtDescripcionListaPrecio.setEnabled(false);
        txtDescripcionListaPrecio.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        txtDescripcionListaPrecio.setPrompt("Descripción o nombre de la lista...");

        jLabel12.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel12.setText("Lista de Precio:");

        txtCodigoLista.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtCodigoLista.setPrompt("Cód. L.P.");
        txtCodigoLista.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoListaActionPerformed(evt);
            }
        });
        txtCodigoLista.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodigoListaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodigoListaKeyTyped(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel15.setText("Vendedor:");

        txtCodigoVendedor.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtCodigoVendedor.setPrompt("Cód. Ven.");
        txtCodigoVendedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoVendedorActionPerformed(evt);
            }
        });
        txtCodigoVendedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodigoVendedorKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodigoVendedorKeyTyped(evt);
            }
        });

        txtDescripcionVendedor.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        txtDescripcionVendedor.setEnabled(false);
        txtDescripcionVendedor.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        txtDescripcionVendedor.setPrompt("Descripción o nombre del vendedor...");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(rbContado)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rbCredito))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtTimbrado, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                .addComponent(txtEstablecimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtPuntoEmision, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNumero, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtComprobante, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 72, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 72, Short.MAX_VALUE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 72, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(txtCodigoMoneda, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDescripcionMoneda, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(txtCodigoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDescripcionCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(txtCodigoDeposito, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDescripcionDeposito, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txtCodigoLista, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtCodigoVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtCodigoCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtDescripcionCuenta, javax.swing.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
                    .addComponent(txtDescripcionVendedor, javax.swing.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
                    .addComponent(txtDescripcionListaPrecio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rbContado)
                    .addComponent(rbCredito))
                .addGap(3, 3, 3)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCodigoMoneda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDescripcionMoneda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDescripcionListaPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCodigoLista, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtEstablecimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPuntoEmision, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtComprobante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCodigoDeposito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDescripcionDeposito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCodigoVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDescripcionVendedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtCodigoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtDescripcionCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtDescripcionCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtCodigoCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtTimbrado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(141, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(310, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        obtenerConfiguracion();
    }//GEN-LAST:event_formInternalFrameOpened

    private void rbContadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbContadoActionPerformed
        txtCodigoCuenta.setEnabled(true);
        txtFecha.grabFocus();
    }//GEN-LAST:event_rbContadoActionPerformed

    private void rbContadoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rbContadoKeyPressed
        if (evt.VK_ENTER == evt.getKeyCode()) {
            txtCodigoCuenta.setEnabled(true);
            txtFecha.grabFocus();
        }
    }//GEN-LAST:event_rbContadoKeyPressed

    private void rbCreditoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbCreditoActionPerformed
        txtCodigoCuenta.setEnabled(false);
        txtFecha.grabFocus();
    }//GEN-LAST:event_rbCreditoActionPerformed

    private void rbCreditoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rbCreditoKeyPressed
        if (evt.VK_ENTER == evt.getKeyCode()) {
            txtCodigoCuenta.setEnabled(false);
            txtFecha.grabFocus();
        }
    }//GEN-LAST:event_rbCreditoKeyPressed

    private void txtFechaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFechaActionPerformed
        if (txtFecha.getDate() == null) {
            JOptionPane.showMessageDialog(null, "NO PUEDE DEJAR EL CAMPO DE FECHA VACIA", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else {
            obtenerImpresora();
            txtEstablecimiento.grabFocus();
        }
    }//GEN-LAST:event_txtFechaActionPerformed

    private void txtEstablecimientoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEstablecimientoActionPerformed
        if (txtEstablecimiento.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "NO PUEDE DEJAR EL CAMPO VACIO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else {
            Establecimiento = Integer.parseInt(txtEstablecimiento.getText());
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
            txtNumero.grabFocus();
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

    private void txtNumeroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumeroActionPerformed
        if (txtNumero.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "NO PUEDE DEJAR EL CAMPO VACIO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else {
            txtNumero.setText(String.format(siete_ceros, Integer.parseInt(txtNumero.getText())));
            txtComprobante.setText(txtEstablecimiento.getText() + "-" + txtPuntoEmision.getText() + "-" + txtNumero.getText());
            txtCodigoMoneda.grabFocus();
        }
    }//GEN-LAST:event_txtNumeroActionPerformed

    private void txtNumeroKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumeroKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            getToolkit().beep();
            evt.consume();
        }
        if (txtNumero.getText().length() == 7) {
            evt.consume();
        }
    }//GEN-LAST:event_txtNumeroKeyTyped

    private void txtTimbradoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimbradoActionPerformed
        /*if (txtTimbrado.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "NO PUEDE DEJAR EL CAMPO DE TIMBRADO VACIO.", "ATENCIÓN", JOptionPane.WARNING_MESSAGE);
        } else {
            boolean resultado = false;
            if (txtTimbrado.getText().length() < 8) {
                int respuesta = JOptionPane.showConfirmDialog(null, "¿ESTA SEGURO DEL TIMBRADO INGRESADO?",
                    "ADVERTENCIA", JOptionPane.YES_NO_OPTION);
                if (respuesta != 1) {
                    resultado = dao.verificarExistenciaCompra(txtComprobante.getText(), Integer.parseInt(txtTimbrado.getText()));
                    if (resultado == true) {
                        JOptionPane.showMessageDialog(null, "YA EXISTE UNA COMPRA CON EL NÙMERO DE DOCUMENTO Y EL TIMBRADO INGRESADO.", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
                    } else {
                        txtCodigoMoneda.grabFocus();
                    }
                }
            } else {
                resultado = dao.verificarExistenciaCompra(txtComprobante.getText(), Integer.parseInt(txtTimbrado.getText()));
                if (resultado == true) {
                    JOptionPane.showMessageDialog(null, "YA EXISTE UNA COMPRA CON EL NÙMERO DE DOCUMENTO Y EL TIMBRADO INGRESADO.", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
                } else {
                    txtCodigoMoneda.grabFocus();
                }
            }
        }*/
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

    private void txtCodigoCuentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoCuentaActionPerformed
        if (rbContado.isSelected()) {
            if (txtCodigoCuenta.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "NO PUEDE DEJAR EL CAMPO DE CUENTA VACIO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
            } else {
                int idcuenta = Integer.parseInt(txtCodigoCuenta.getText());
                int idmoneda = Integer.parseInt(txtCodigoMoneda.getText());
                cu.setIdcuenta(idcuenta);
                cu.setIdmoneda(idmoneda);
                boolean resultado = daoCuenta.consultarDatos(cu);
                if (resultado == true) {
                    txtDescripcionCuenta.setText(cu.getDescripcion());
                    //txtObservacion.grabFocus();
                } else {
                    txtCodigoCuenta.setText(null);
                    txtDescripcionCuenta.setText(null);
                    txtCodigoCuenta.grabFocus();
                }
            }
        }
    }//GEN-LAST:event_txtCodigoCuentaActionPerformed

    private void txtCodigoCuentaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoCuentaKeyPressed
        if (rbContado.isSelected()) {
            if (evt.VK_F1 == evt.getKeyCode()) {
                buscarCuenta();
            }
        }
    }//GEN-LAST:event_txtCodigoCuentaKeyPressed

    private void txtCodigoCuentaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoCuentaKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            getToolkit().beep();
            evt.consume();
        }
        if (txtCodigoCuenta.getText().length() == 10) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCodigoCuentaKeyTyped

    private void txtCodigoMonedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoMonedaActionPerformed
        if (txtCodigoMoneda.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "NO PUEDE DEJAR EL CAMPO DE MONEDA VACIO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else {
            int idmoneda = Integer.parseInt(txtCodigoMoneda.getText());
            m.setIdmoneda(idmoneda);
            boolean resultado = daoMoneda.consultarDatos(m);
            Date fecha = txtFecha.getDate();
            java.sql.Date fechaSQL = new java.sql.Date(fecha.getTime());
            boolean cotizacion = daoCotizacion.verificarCotizacion("" + fechaSQL, idmoneda);
            if (resultado == true) {
                if (idmoneda != 1) {
                    if (cotizacion == false) {
                        JOptionPane.showMessageDialog(null, "NO EXISTE UNA COTIZACION PARA LA FECHA INGRESADA.", "ADVERTENCIA", JOptionPane.ERROR_MESSAGE);
                        dispose();
                    } else {
                        txtDescripcionMoneda.setText(m.getDescripcion());
                        txtCodigoLista.grabFocus();
                    }
                } else {
                    txtDescripcionMoneda.setText(m.getDescripcion());
                    txtCodigoLista.grabFocus();
                }
            } else {
                txtCodigoMoneda.setText(null);
                txtDescripcionMoneda.setText(null);
                txtCodigoMoneda.grabFocus();
            }
        }
    }//GEN-LAST:event_txtCodigoMonedaActionPerformed

    private void txtCodigoMonedaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoMonedaKeyPressed
        if (evt.VK_F1 == evt.getKeyCode()) {
            buscarMoneda();
        }
    }//GEN-LAST:event_txtCodigoMonedaKeyPressed

    private void txtCodigoMonedaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoMonedaKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            getToolkit().beep();
            evt.consume();
        }
        if (txtCodigoMoneda.getText().length() == 10) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCodigoMonedaKeyTyped

    private void txtCriterioMonedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCriterioMonedaActionPerformed
        cargarMoneda();
    }//GEN-LAST:event_txtCriterioMonedaActionPerformed

    private void txtCriterioMonedaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCriterioMonedaKeyPressed
        if (evt.VK_ESCAPE == evt.getKeyCode()) {
            txtCodigoMoneda.setText(null);
            txtDescripcionMoneda.setText(null);
            txtCodigoMoneda.grabFocus();
            BuscadorMoneda.dispose();
        }
    }//GEN-LAST:event_txtCriterioMonedaKeyPressed

    private void txtCriterioMonedaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCriterioMonedaKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            evt.setKeyChar(Character.toUpperCase(c));
        }
        if (txtCriterioMoneda.getText().length() == 100) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCriterioMonedaKeyTyped

    private void tablaDatosMonedasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaDatosMonedasMouseClicked
        if (evt.getClickCount() == 2) {
            if (tablaDatosMonedas.getSelectedRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "SELECCIONE UNA FILA");
            } else {
                txtCriterioMoneda.setText(null);
                BuscadorMoneda.dispose();
            }
        }
    }//GEN-LAST:event_tablaDatosMonedasMouseClicked

    private void txtCriterioCuentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCriterioCuentaActionPerformed
        cargarCuenta();
    }//GEN-LAST:event_txtCriterioCuentaActionPerformed

    private void txtCriterioCuentaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCriterioCuentaKeyPressed
        if (evt.VK_ESCAPE == evt.getKeyCode()) {
            txtCodigoCuenta.setText(null);
            txtDescripcionCuenta.setText(null);
            txtCodigoCuenta.grabFocus();
            BuscadorCuenta.dispose();
        }
    }//GEN-LAST:event_txtCriterioCuentaKeyPressed

    private void txtCriterioCuentaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCriterioCuentaKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            evt.setKeyChar(Character.toUpperCase(c));
        }
        if (txtCriterioCuenta.getText().length() == 100) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCriterioCuentaKeyTyped

    private void tablaDatosCuentaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaDatosCuentaMouseClicked
        if (evt.getClickCount() == 2) {
            if (tablaDatosCuenta.getSelectedRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "SELECCIONE UNA FILA");
            } else {
                txtCriterioCuenta.setText(null);
                BuscadorCuenta.dispose();
            }
        }
    }//GEN-LAST:event_tablaDatosCuentaMouseClicked

    private void txtCodigoDepositoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoDepositoActionPerformed
        if (txtCodigoDeposito.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "NO PUEDE DEJAR EL CAMPO DE DEPÓSITO VACIO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else {
            int iddeposito = Integer.parseInt(txtCodigoDeposito.getText());
            d.setIddeposito(iddeposito);
            boolean resultado = daoDeposito.consultarDatos(d);
            if (resultado == true) {
                txtDescripcionDeposito.setText(d.getDescripcion());
                txtCodigoVendedor.grabFocus();
            } else {
                txtCodigoDeposito.setText(null);
                txtDescripcionDeposito.setText(null);
                txtCodigoDeposito.grabFocus();
            }
        }
    }//GEN-LAST:event_txtCodigoDepositoActionPerformed

    private void txtCodigoDepositoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoDepositoKeyPressed
        if (evt.VK_F1 == evt.getKeyCode()) {
            buscarDeposito();
        }
    }//GEN-LAST:event_txtCodigoDepositoKeyPressed

    private void txtCodigoDepositoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoDepositoKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            getToolkit().beep();
            evt.consume();
        }
        if (txtCodigoDeposito.getText().length() == 10) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCodigoDepositoKeyTyped

    private void txtCriterioDepositoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCriterioDepositoActionPerformed
        cargarDeposito();
    }//GEN-LAST:event_txtCriterioDepositoActionPerformed

    private void txtCriterioDepositoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCriterioDepositoKeyPressed
        if (evt.VK_ESCAPE == evt.getKeyCode()) {
            txtCodigoDeposito.setText(null);
            txtDescripcionDeposito.setText(null);
            txtCodigoDeposito.grabFocus();
            BuscadorDeposito.dispose();
        }
    }//GEN-LAST:event_txtCriterioDepositoKeyPressed

    private void txtCriterioDepositoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCriterioDepositoKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            evt.setKeyChar(Character.toUpperCase(c));
        }
        if (txtCriterioDeposito.getText().length() == 100) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCriterioDepositoKeyTyped

    private void tablaDatosDepositosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaDatosDepositosMouseClicked
        if (evt.getClickCount() == 2) {
            if (tablaDatosDepositos.getSelectedRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "SELECCIONE UNA FILA");
            } else {
                txtCriterioDeposito.setText(null);
                BuscadorDeposito.dispose();
            }
        }
    }//GEN-LAST:event_tablaDatosDepositosMouseClicked

    private void txtCodigoClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoClienteActionPerformed
        if (txtCodigoCliente.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "NO PUEDE DEJAR EL CAMPO DE CLIENTE VACIO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else {
            int idcliente = Integer.parseInt(txtCodigoCliente.getText());
            cli.setIdcliente(idcliente);
            boolean resultado = daoCliente.consultarDatos(cli);
            if (resultado == true) {
                txtDescripcionCliente.setText(cli.getNombre() + " " + cli.getApellido());
                if (rbContado.isSelected()) {
                    txtCodigoCuenta.grabFocus();
                } else {
                    //txtObservacion.grabFocus();
                }
            } else {
                txtCodigoCliente.setText(null);
                txtDescripcionCliente.setText(null);
                txtCodigoCliente.grabFocus();
            }
        }
    }//GEN-LAST:event_txtCodigoClienteActionPerformed

    private void txtCodigoClienteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoClienteKeyPressed
        if (evt.VK_F1 == evt.getKeyCode()) {
            buscarCliente();
        }
    }//GEN-LAST:event_txtCodigoClienteKeyPressed

    private void txtCodigoClienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoClienteKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            getToolkit().beep();
            evt.consume();
        }
        if (txtCodigoCliente.getText().length() == 10) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCodigoClienteKeyTyped

    private void txtCodigoListaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoListaActionPerformed
        if (txtCodigoLista.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "NO PUEDE DEJAR EL CAMPO DE LISTA DE PRECIO VACIO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else {
            int idlista = Integer.parseInt(txtCodigoLista.getText());
            int idmoneda = Integer.parseInt(txtCodigoMoneda.getText());
            lp.setIdlista(idlista);
            lp.setIdmoneda(idmoneda);
            boolean resultado = daoListaPrecio.consultarDatosListaPrecioMoneda(lp);
            if (resultado == true) {
                txtDescripcionListaPrecio.setText(lp.getDescripcion());
                txtCodigoDeposito.grabFocus();
            } else {
                txtCodigoLista.setText(null);
                txtDescripcionListaPrecio.setText(null);
                txtCodigoLista.grabFocus();
            }
        }
    }//GEN-LAST:event_txtCodigoListaActionPerformed

    private void txtCodigoListaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoListaKeyPressed
        if (evt.VK_F1 == evt.getKeyCode()) {
            buscarListaPrecio();
        }
    }//GEN-LAST:event_txtCodigoListaKeyPressed

    private void txtCodigoListaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoListaKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            getToolkit().beep();
            evt.consume();
        }
        if (txtCodigoLista.getText().length() == 10) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCodigoListaKeyTyped

    private void txtCriterioListaPrecioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCriterioListaPrecioActionPerformed
        cargarListaPrecio();
    }//GEN-LAST:event_txtCriterioListaPrecioActionPerformed

    private void txtCriterioListaPrecioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCriterioListaPrecioKeyPressed
        if (evt.VK_ESCAPE == evt.getKeyCode()) {
            txtCodigoLista.setText(null);
            txtDescripcionListaPrecio.setText(null);
            txtCodigoLista.grabFocus();
            BuscadorListaPrecio.dispose();
        }
    }//GEN-LAST:event_txtCriterioListaPrecioKeyPressed

    private void txtCriterioListaPrecioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCriterioListaPrecioKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            evt.setKeyChar(Character.toUpperCase(c));
        }
        if (txtCriterioListaPrecio.getText().length() == 100) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCriterioListaPrecioKeyTyped

    private void tablaDatosListaPrecioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaDatosListaPrecioMouseClicked
        if (evt.getClickCount() == 2) {
            if (tablaDatosListaPrecio.getSelectedRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "SELECCIONE UNA FILA");
            } else {
                txtCriterioListaPrecio.setText(null);
                BuscadorListaPrecio.dispose();
            }
        }
    }//GEN-LAST:event_tablaDatosListaPrecioMouseClicked

    private void txtCriterioClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCriterioClienteActionPerformed
        cargarCliente();
    }//GEN-LAST:event_txtCriterioClienteActionPerformed

    private void txtCriterioClienteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCriterioClienteKeyPressed
        if (evt.VK_ESCAPE == evt.getKeyCode()) {
            txtCodigoCliente.setText(null);
            txtDescripcionCliente.setText(null);
            txtCodigoCliente.grabFocus();
            BuscadorCliente.dispose();
        }
    }//GEN-LAST:event_txtCriterioClienteKeyPressed

    private void txtCriterioClienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCriterioClienteKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            evt.setKeyChar(Character.toUpperCase(c));
        }
        if (txtCriterioCliente.getText().length() == 100) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCriterioClienteKeyTyped

    private void tablaDatosClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaDatosClienteMouseClicked
        if (evt.getClickCount() == 2) {
            if (tablaDatosCliente.getSelectedRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "SELECCIONE UNA FILA");
            } else {
                txtCriterioCliente.setText(null);
                BuscadorCliente.dispose();
            }
        }
    }//GEN-LAST:event_tablaDatosClienteMouseClicked

    private void txtCodigoVendedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoVendedorActionPerformed
        if (txtCodigoVendedor.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "NO PUEDE DEJAR EL CAMPO DE VENDEDOR VACIO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else {
            int idvendedor = Integer.parseInt(txtCodigoVendedor.getText());
            ven.setIdvendedor(idvendedor);
            boolean resultado = daoVendedor.consultarDatos(ven);
            if (resultado == true) {
                txtDescripcionVendedor.setText(ven.getNombre()+" "+ven.getApellido());
                txtCodigoCliente.grabFocus();
            } else {
                txtCodigoVendedor.setText(null);
                txtDescripcionVendedor.setText(null);
                txtCodigoVendedor.grabFocus();
            }
        }
    }//GEN-LAST:event_txtCodigoVendedorActionPerformed

    private void txtCodigoVendedorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoVendedorKeyPressed
        if (evt.VK_F1 == evt.getKeyCode()) {
            buscarVendedor();
        }
    }//GEN-LAST:event_txtCodigoVendedorKeyPressed

    private void txtCodigoVendedorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoVendedorKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            getToolkit().beep();
            evt.consume();
        }
        if (txtCodigoVendedor.getText().length() == 10) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCodigoVendedorKeyTyped

    private void txtCriterioVendedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCriterioVendedorActionPerformed
        cargarVendedor();
    }//GEN-LAST:event_txtCriterioVendedorActionPerformed

    private void txtCriterioVendedorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCriterioVendedorKeyPressed
        if (evt.VK_ESCAPE == evt.getKeyCode()) {
            txtCodigoVendedor.setText(null);
            txtDescripcionVendedor.setText(null);
            txtCodigoVendedor.grabFocus();
            BuscadorMarca.dispose();
        }
    }//GEN-LAST:event_txtCriterioVendedorKeyPressed

    private void txtCriterioVendedorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCriterioVendedorKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            evt.setKeyChar(Character.toUpperCase(c));
        }
        if (txtCriterioVendedor.getText().length() == 100) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCriterioVendedorKeyTyped

    private void tablaDatosVendedorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaDatosVendedorMouseClicked
        if (evt.getClickCount() == 2) {
            if (tablaDatosVendedor.getSelectedRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "SELECCIONE UNA FILA");
            } else {
                txtCriterioVendedor.setText(null);
                BuscadorMarca.dispose();
            }
        }
    }//GEN-LAST:event_tablaDatosVendedorMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDialog BuscadorCliente;
    private javax.swing.JDialog BuscadorCuenta;
    private javax.swing.JDialog BuscadorDeposito;
    private javax.swing.JDialog BuscadorListaPrecio;
    private javax.swing.JDialog BuscadorMarca;
    private javax.swing.JDialog BuscadorMoneda;
    private javax.swing.ButtonGroup grupoContadoCredito;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JRadioButton rbContado;
    private javax.swing.JRadioButton rbCredito;
    private javax.swing.JTable tablaDatosCliente;
    private javax.swing.JTable tablaDatosCuenta;
    private javax.swing.JTable tablaDatosDepositos;
    private javax.swing.JTable tablaDatosListaPrecio;
    private javax.swing.JTable tablaDatosMonedas;
    private javax.swing.JTable tablaDatosVendedor;
    private org.jdesktop.swingx.JXTextField txtCodigoCliente;
    private org.jdesktop.swingx.JXTextField txtCodigoCuenta;
    private org.jdesktop.swingx.JXTextField txtCodigoDeposito;
    private org.jdesktop.swingx.JXTextField txtCodigoLista;
    private org.jdesktop.swingx.JXTextField txtCodigoMoneda;
    private org.jdesktop.swingx.JXTextField txtCodigoVendedor;
    private org.jdesktop.swingx.JXTextField txtComprobante;
    private org.jdesktop.swingx.JXTextField txtCriterioCliente;
    private org.jdesktop.swingx.JXTextField txtCriterioCuenta;
    private org.jdesktop.swingx.JXTextField txtCriterioDeposito;
    private org.jdesktop.swingx.JXTextField txtCriterioListaPrecio;
    private org.jdesktop.swingx.JXTextField txtCriterioMoneda;
    private org.jdesktop.swingx.JXTextField txtCriterioVendedor;
    private org.jdesktop.swingx.JXTextField txtDescripcionCliente;
    private org.jdesktop.swingx.JXTextField txtDescripcionCuenta;
    private org.jdesktop.swingx.JXTextField txtDescripcionDeposito;
    private org.jdesktop.swingx.JXTextField txtDescripcionListaPrecio;
    private org.jdesktop.swingx.JXTextField txtDescripcionMoneda;
    private org.jdesktop.swingx.JXTextField txtDescripcionVendedor;
    private org.jdesktop.swingx.JXTextField txtEstablecimiento;
    private org.jdesktop.swingx.JXDatePicker txtFecha;
    private org.jdesktop.swingx.JXTextField txtNumero;
    private org.jdesktop.swingx.JXTextField txtPuntoEmision;
    private org.jdesktop.swingx.JXTextField txtTimbrado;
    // End of variables declaration//GEN-END:variables
}
