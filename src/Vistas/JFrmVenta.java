package Vistas;

import App.appLogin;
import Dao.DAOConfiguracion;
import Dao.DAOCotizacion;
import Dao.DAOImpresora;
import Dao.DAOImpresoraTimbrado;
import Dao.DAOTipoMovimiento;
import Dao.DAOUsuarioImpresora;
import Dao.DAOVenta;
import Modelos.Configuracion;
import Modelos.Impresora;
import Modelos.ImpresoraTimbrado;
import Modelos.TipoMovimiento;
import Modelos.UsuarioImpresora;
import Modelos.Venta;
import java.util.Date;
import javax.swing.JOptionPane;
import java.text.SimpleDateFormat;

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

    DAOCotizacion daoCotizacion = new DAOCotizacion();
    DAOConfiguracion daoConfiguracion = new DAOConfiguracion();
    DAOUsuarioImpresora daoUsuarioImpresora = new DAOUsuarioImpresora();
    DAOImpresoraTimbrado daoImpresoraTimbrado = new DAOImpresoraTimbrado();
    DAOTipoMovimiento daoTipoMovimiento = new DAOTipoMovimiento();
    DAOImpresora daoImpresora = new DAOImpresora();
    DAOVenta dao = new DAOVenta();

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
            boolean resultadoTimbrado = daoImpresoraTimbrado.consultarDatosTimbrado(it);
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
                if ((numero+1)<numeroFinal && (numero+1)>numeroInicial) {
                    //VALIDAR DUPLICACION DE DOCUMENTO
                    boolean existe = dao.verificarExistenciaVenta(txtComprobante.getText(), Integer.parseInt(txtTimbrado.getText()));
                    if (existe) {
                        
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "EL TIMBRADO YA NO POSEE NUMEROS DISPONIBLES...", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "NO SE HAN ENCONTRADO LOS DATOS PARA VERIFICAR EL TIMBRADO...", "ERROR", JOptionPane.ERROR_MESSAGE);
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
                .addContainerGap(883, Short.MAX_VALUE))
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
                    .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtEstablecimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPuntoEmision, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtComprobante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTimbrado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(144, Short.MAX_VALUE))
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
                .addContainerGap(352, Short.MAX_VALUE))
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
        //txtCodigoCuenta.setEnabled(true);
        txtFecha.grabFocus();
    }//GEN-LAST:event_rbContadoActionPerformed

    private void rbContadoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rbContadoKeyPressed
        if (evt.VK_ENTER == evt.getKeyCode()) {
            //txtCodigoCuenta.setEnabled(true);
            txtFecha.grabFocus();
        }
    }//GEN-LAST:event_rbContadoKeyPressed

    private void rbCreditoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbCreditoActionPerformed
        //txtCodigoCuenta.setEnabled(false);
        txtFecha.grabFocus();
    }//GEN-LAST:event_rbCreditoActionPerformed

    private void rbCreditoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rbCreditoKeyPressed
        if (evt.VK_ENTER == evt.getKeyCode()) {
            //txtCodigoCuenta.setEnabled(false);
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
            txtTimbrado.grabFocus();
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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup grupoContadoCredito;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JRadioButton rbContado;
    private javax.swing.JRadioButton rbCredito;
    private org.jdesktop.swingx.JXTextField txtComprobante;
    private org.jdesktop.swingx.JXTextField txtEstablecimiento;
    private org.jdesktop.swingx.JXDatePicker txtFecha;
    private org.jdesktop.swingx.JXTextField txtNumero;
    private org.jdesktop.swingx.JXTextField txtPuntoEmision;
    private org.jdesktop.swingx.JXTextField txtTimbrado;
    // End of variables declaration//GEN-END:variables
}
