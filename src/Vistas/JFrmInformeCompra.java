package Vistas;

import Controladores.Database;
import Dao.DAOCotizacion;
import Dao.DAOMoneda;
import Modelos.Moneda;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author armando
 */
public class JFrmInformeCompra extends javax.swing.JInternalFrame {

    Database db = new Database();

    Moneda m = new Moneda();

    DAOCotizacion daoCotizacion = new DAOCotizacion();
    DAOMoneda daoMoneda = new DAOMoneda();

    ArrayList<Object[]> datosMoneda = new ArrayList<>();

    SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
    Date SYSDATE = new Date();

    /**
     * Creates new form JFrmInformeCompra
     */
    public JFrmInformeCompra() {
        setTitle("JFrmInformeCompra");
        initComponents();
        txtFechaDesde.setFormats(formato);
        txtFechaHasta.setFormats(formato);
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

    private void abrirReporte(String ruta_archivo, Map parametros) {
        try {

            Connection con;
            Class.forName(db.getDriver());
            con = DriverManager.getConnection(db.getUrl(), db.getUser(), db.getPass());

            InputStream reporteInputStream = JFrmInformeCompra.class.getResourceAsStream("/Informes/" + ruta_archivo);
            JasperPrint jp = JasperFillManager.fillReport(reporteInputStream, parametros, con);
            JasperViewer jv = new JasperViewer(jp, false);
            jv.setVisible(true);

        } catch (JRException | ClassNotFoundException | SQLException ex) {
            System.out.println(ex);
            JOptionPane.showMessageDialog(null, "ERROR: " + ex);
        }
    }

    private void generarInforme() {

        String msj = "";
        Date fechaDesde = null;
        java.sql.Date fechaDesdeSQL = null;
        Date fechaHasta = null;
        java.sql.Date fechaHastaSQL = null;

        if (txtFechaDesde.getDate() != null) {
            fechaDesde = txtFechaDesde.getDate();
            fechaDesdeSQL = new java.sql.Date(fechaDesde.getTime());
        }
        if (txtFechaHasta.getDate() != null) {
            fechaHasta = txtFechaHasta.getDate();
            fechaHastaSQL = new java.sql.Date(fechaHasta.getTime());
        }
        if (fechaDesde == null) {
            msj += "NO HA CARGADO UNA FECHA INICIAL PARA EL FILTRO DEL INFORME. \n";
        }
        if (fechaHasta == null) {
            msj += "NO HA CARGADO UNA FECHA FINAL PARA EL FILTRO DEL INFORME. \n";
        }

        String ruta = "";
        String moneda = txtCodigoMoneda.getText();
        int idmoneda = 0;
        if (!moneda.isEmpty()) {
            idmoneda = Integer.parseInt(txtCodigoMoneda.getText());
        }
        if (idmoneda == 0) {
            //ruta = "src/Informes/resumen_compras_multimoneda.jasper";
            ruta = "resumen_compras_multimoneda.jasper";
        } else {
            //ruta = "src/Informes/resumen_compras_unimoneda.jasper";
            ruta = "resumen_compras_unimoneda.jasper";
        }
        Map parametros = new HashMap();
        parametros.put("P_INFORME", "RESUMEN DE COMPRAS");
        parametros.put("P_FECHA_INICIAL", fechaDesdeSQL);
        parametros.put("P_FECHA_FINAL", fechaHastaSQL);
        parametros.put("P_MONEDA", idmoneda);

        if (msj.isEmpty()) {
            abrirReporte(ruta, parametros);
        } else {
            JOptionPane.showMessageDialog(null, msj, "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void limpiarCampos() {
        txtFechaDesde.setDate(null);
        txtFechaHasta.setDate(null);
        txtCodigoMoneda.setText(null);
        txtDescripcionMoneda.setText(null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        BuscadorMoneda = new javax.swing.JDialog();
        jPanel3 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        txtCriterioMoneda = new org.jdesktop.swingx.JXTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaDatosMonedas = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtFechaDesde = new org.jdesktop.swingx.JXDatePicker();
        jLabel4 = new javax.swing.JLabel();
        txtFechaHasta = new org.jdesktop.swingx.JXDatePicker();
        txtCodigoMoneda = new org.jdesktop.swingx.JXTextField();
        txtDescripcionMoneda = new org.jdesktop.swingx.JXTextField();
        jLabel5 = new javax.swing.JLabel();
        btnGenerar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel7.setBackground(new java.awt.Color(50, 104, 151));
        jLabel7.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("BUSCADOR DE MONEDAS");
        jLabel7.setOpaque(true);

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

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCriterioMoneda, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
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
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BuscadorMonedaLayout.setVerticalGroup(
            BuscadorMonedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setClosable(true);
        setIconifiable(true);

        jPanel1.setBackground(new java.awt.Color(50, 104, 151));

        jLabel1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Informe de Compras del Sistema");

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

        jLabel3.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel3.setText("Fecha desde:");

        txtFechaDesde.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFechaDesdeActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel4.setText("Fecha hasta:");

        txtFechaHasta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFechaHastaActionPerformed(evt);
            }
        });

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

        jLabel5.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel5.setText("Moneda:");

        btnGenerar.setBackground(new java.awt.Color(204, 255, 255));
        btnGenerar.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        btnGenerar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/icons8_checked_16px_1.png"))); // NOI18N
        btnGenerar.setText("Generar");
        btnGenerar.setOpaque(false);
        btnGenerar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerarActionPerformed(evt);
            }
        });

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
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCodigoMoneda, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtFechaDesde, javax.swing.GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE)
                            .addComponent(txtFechaHasta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDescripcionMoneda, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnGenerar, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFechaDesde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFechaHasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDescripcionMoneda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCodigoMoneda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGenerar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
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

    private void txtFechaDesdeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFechaDesdeActionPerformed
        if (txtFechaDesde.getDate() == null) {
            String fechaActual = formato.format(SYSDATE);
            txtFechaDesde.setDate(daoCotizacion.parseFecha(fechaActual));
        } else {
            txtFechaHasta.grabFocus();
        }
    }//GEN-LAST:event_txtFechaDesdeActionPerformed

    private void txtFechaHastaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFechaHastaActionPerformed
        if (txtFechaHasta.getDate() == null) {
            String fechaActual = formato.format(SYSDATE);
            txtFechaHasta.setDate(daoCotizacion.parseFecha(fechaActual));
        } else {
            txtCodigoMoneda.grabFocus();
        }
    }//GEN-LAST:event_txtFechaHastaActionPerformed

    private void txtCodigoMonedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoMonedaActionPerformed
        if (txtCodigoMoneda.getText().isEmpty()) {
            txtDescripcionMoneda.setText("TODAS LAS MONEDAS...");
            btnGenerar.grabFocus();
        } else {
            int idmoneda = Integer.parseInt(txtCodigoMoneda.getText());
            m.setIdmoneda(idmoneda);
            boolean resultado = daoMoneda.consultarDatos(m);
            if (resultado == true) {
                txtDescripcionMoneda.setText(m.getDescripcion());
                btnGenerar.grabFocus();
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

    private void txtDescripcionMonedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDescripcionMonedaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescripcionMonedaActionPerformed

    private void txtDescripcionMonedaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescripcionMonedaKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescripcionMonedaKeyTyped

    private void btnGenerarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerarActionPerformed
        int res = JOptionPane.showConfirmDialog(null, "¿GENERAR RESUMEN DE COMPRAS?", "ADVERTENCIA", JOptionPane.YES_NO_OPTION);
        if (res != 1) {
            generarInforme();
        }
    }//GEN-LAST:event_btnGenerarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        limpiarCampos();
    }//GEN-LAST:event_btnCancelarActionPerformed

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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDialog BuscadorMoneda;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGenerar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tablaDatosMonedas;
    private org.jdesktop.swingx.JXTextField txtCodigoMoneda;
    private org.jdesktop.swingx.JXTextField txtCriterioMoneda;
    private org.jdesktop.swingx.JXTextField txtDescripcionMoneda;
    private org.jdesktop.swingx.JXDatePicker txtFechaDesde;
    private org.jdesktop.swingx.JXDatePicker txtFechaHasta;
    // End of variables declaration//GEN-END:variables
}
