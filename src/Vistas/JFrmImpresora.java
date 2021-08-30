package Vistas;

import Dao.DAOImpresora;
import Modelos.Impresora;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author armando
 */
public class JFrmImpresora extends javax.swing.JInternalFrame {

    Impresora i = new Impresora();
    DAOImpresora dao = new DAOImpresora();
    ArrayList<Object[]> datos = new ArrayList<>();

    //VARIABLE QUE MANEJA QUE TIPOS DE OPERACIONES SE REALIZARAN: SI VA A SER ALTA, BAJA O MODIFICACION DEL REGISTRO
    String operacion = "";
    String tres_ceros = String.format("%%0%dd", 3);
    String siete_ceros = String.format("%%0%dd", 7);

    /**
     * Creates new form JFrmImpresora
     */
    public JFrmImpresora() {
        setTitle("JFrmImpresora");
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

    public void habilitarCampos(String accion) {
        switch (accion) {
            case "NUEVO":
                //CAMPOS
                txtCodigo.setEnabled(false);
                txtDescripcion.setEnabled(true);
                
                txtEstablecimientoFactura.setEnabled(true);
                txtPuntoEmisionFactura.setEnabled(true);
                txtNumeroFactura.setEnabled(true);
                txtUltimoNumeroFactura.setEnabled(false);
                
                txtEstablecimientoRecibo.setEnabled(true);
                txtPuntoEmisionRecibo.setEnabled(true);
                txtNumeroRecibo.setEnabled(true);
                txtUltimoNumeroRecibo.setEnabled(false);
                
                txtEstablecimientoNotaCredito.setEnabled(true);
                txtPuntoEmisionNotaCredito.setEnabled(true);
                txtNumeroNotaCredito.setEnabled(true);
                txtUltimoNumeroNotaCredito.setEnabled(false);
                
                txtEstablecimientoNotaDebito.setEnabled(true);
                txtPuntoEmisionNotaDebito.setEnabled(true);
                txtNumeroNotaDebito.setEnabled(true);
                txtUltimoNumeroNotaDebito.setEnabled(false);
                
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
                txtEstablecimientoFactura.setEnabled(true);
                txtPuntoEmisionFactura.setEnabled(true);
                txtNumeroFactura.setEnabled(true);
                txtUltimoNumeroFactura.setEnabled(false);
                
                txtEstablecimientoRecibo.setEnabled(true);
                txtPuntoEmisionRecibo.setEnabled(true);
                txtNumeroRecibo.setEnabled(true);
                txtUltimoNumeroRecibo.setEnabled(false);
                
                txtEstablecimientoNotaCredito.setEnabled(true);
                txtPuntoEmisionNotaCredito.setEnabled(true);
                txtNumeroNotaCredito.setEnabled(true);
                txtUltimoNumeroNotaCredito.setEnabled(false);
                
                txtEstablecimientoNotaDebito.setEnabled(true);
                txtPuntoEmisionNotaDebito.setEnabled(true);
                txtNumeroNotaDebito.setEnabled(true);
                txtUltimoNumeroNotaDebito.setEnabled(false);
                
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
                txtEstablecimientoFactura.setEnabled(false);
                txtPuntoEmisionFactura.setEnabled(false);
                txtNumeroFactura.setEnabled(false);
                txtUltimoNumeroFactura.setEnabled(false);
                
                txtEstablecimientoRecibo.setEnabled(false);
                txtPuntoEmisionRecibo.setEnabled(false);
                txtNumeroRecibo.setEnabled(false);
                txtUltimoNumeroRecibo.setEnabled(false);
                
                txtEstablecimientoNotaCredito.setEnabled(false);
                txtPuntoEmisionNotaCredito.setEnabled(false);
                txtNumeroNotaCredito.setEnabled(false);
                txtUltimoNumeroNotaCredito.setEnabled(false);
                
                txtEstablecimientoNotaDebito.setEnabled(false);
                txtPuntoEmisionNotaDebito.setEnabled(false);
                txtNumeroNotaDebito.setEnabled(false);
                txtUltimoNumeroNotaDebito.setEnabled(false);
                
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
                txtEstablecimientoFactura.setEnabled(false);
                txtPuntoEmisionFactura.setEnabled(false);
                txtNumeroFactura.setEnabled(false);
                txtUltimoNumeroFactura.setEnabled(false);
                
                txtEstablecimientoRecibo.setEnabled(false);
                txtPuntoEmisionRecibo.setEnabled(false);
                txtNumeroRecibo.setEnabled(false);
                txtUltimoNumeroRecibo.setEnabled(false);
                
                txtEstablecimientoNotaCredito.setEnabled(false);
                txtPuntoEmisionNotaCredito.setEnabled(false);
                txtNumeroNotaCredito.setEnabled(false);
                txtUltimoNumeroNotaCredito.setEnabled(false);
                
                txtEstablecimientoNotaDebito.setEnabled(false);
                txtPuntoEmisionNotaDebito.setEnabled(false);
                txtNumeroNotaDebito.setEnabled(false);
                txtUltimoNumeroNotaDebito.setEnabled(false);
                
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
                txtEstablecimientoFactura.setEnabled(false);
                txtPuntoEmisionFactura.setEnabled(false);
                txtNumeroFactura.setEnabled(false);
                txtUltimoNumeroFactura.setEnabled(false);
                
                txtEstablecimientoRecibo.setEnabled(false);
                txtPuntoEmisionRecibo.setEnabled(false);
                txtNumeroRecibo.setEnabled(false);
                txtUltimoNumeroRecibo.setEnabled(false);
                
                txtEstablecimientoNotaCredito.setEnabled(false);
                txtPuntoEmisionNotaCredito.setEnabled(false);
                txtNumeroNotaCredito.setEnabled(false);
                txtUltimoNumeroNotaCredito.setEnabled(false);
                
                txtEstablecimientoNotaDebito.setEnabled(false);
                txtPuntoEmisionNotaDebito.setEnabled(false);
                txtNumeroNotaDebito.setEnabled(false);
                txtUltimoNumeroNotaDebito.setEnabled(false);
                
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
        txtEstablecimientoFactura.setText(null);
        txtPuntoEmisionFactura.setText(null);
        txtNumeroFactura.setText(null);
        txtUltimoNumeroFactura.setText(null);
        
        txtEstablecimientoRecibo.setText(null);
        txtPuntoEmisionRecibo.setText(null);
        txtNumeroRecibo.setText(null);
        txtUltimoNumeroRecibo.setText(null);
        
        txtEstablecimientoNotaCredito.setText(null);
        txtPuntoEmisionNotaCredito.setText(null);
        txtNumeroNotaCredito.setText(null);
        txtUltimoNumeroNotaCredito.setText(null);
        
        txtEstablecimientoNotaDebito.setText(null);
        txtPuntoEmisionNotaDebito.setText(null);
        txtNumeroNotaDebito.setText(null);
        txtUltimoNumeroNotaDebito.setText(null);
        
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
        String ultimo_numero_factura = txtUltimoNumeroFactura.getText();
        String ultimo_numero_recibo = txtUltimoNumeroRecibo.getText();
        String ultimo_numero_nota_credito = txtUltimoNumeroNotaCredito.getText();
        String ultimo_numero_nota_debito = txtUltimoNumeroNotaDebito.getText();
        switch (accion) {
            case "NUEVO":
                if (descripcion.isEmpty()) {
                    error += "NO PUEDE DEJAR EL CAMPO DE DESCRIPCIÓN VACIO.\n";
                }
                if (ultimo_numero_factura.isEmpty()) {
                    error += "NO PUEDE DEJAR EL CAMPO DE ULTIMO N° FACTURA VACIO.\n";
                }
                if (ultimo_numero_recibo.isEmpty()) {
                    error += "NO PUEDE DEJAR EL CAMPO DE ULTIMO N° RECIBO VACIO.\n";
                }
                if (ultimo_numero_nota_credito.isEmpty()) {
                    error += "NO PUEDE DEJAR EL CAMPO DE ULTIMO N° NOTA DE CRÉDITO VACIO.\n";
                }
                if (ultimo_numero_nota_debito.isEmpty()) {
                    error += "NO PUEDE DEJAR EL CAMPO DE ULTIMO N° NOTA DE DÉBITO VACIO.\n";
                }
                if (error.isEmpty()) {
                    i.setIdimpresora(id);
                    i.setDescripcion(descripcion);
                    i.setUltimo_numero_factura(ultimo_numero_factura);
                    i.setUltimo_numero_recibo(ultimo_numero_recibo);
                    i.setUltimo_numero_nota_credito(ultimo_numero_nota_credito);
                    i.setUltimo_numero_nota_debito(ultimo_numero_nota_debito);
                    dao.agregar(i);
                    cargar();
                } else {
                    JOptionPane.showMessageDialog(null, error, "ERRORES", JOptionPane.ERROR_MESSAGE);
                }
                break;
            case "MODIFICAR":
                if (descripcion.isEmpty()) {
                    error += "NO PUEDE DEJAR EL CAMPO DE DESCRIPCIÓN VACIO.\n";
                }
                if (ultimo_numero_factura.isEmpty()) {
                    error += "NO PUEDE DEJAR EL CAMPO DE ULTIMO N° FACTURA VACIO.\n";
                }
                if (ultimo_numero_recibo.isEmpty()) {
                    error += "NO PUEDE DEJAR EL CAMPO DE ULTIMO N° RECIBO VACIO.\n";
                }
                if (ultimo_numero_nota_credito.isEmpty()) {
                    error += "NO PUEDE DEJAR EL CAMPO DE ULTIMO N° NOTA DE CRÉDITO VACIO.\n";
                }
                if (ultimo_numero_nota_debito.isEmpty()) {
                    error += "NO PUEDE DEJAR EL CAMPO DE ULTIMO N° NOTA DE DÉBITO VACIO.\n";
                }
                if (error.isEmpty()) {
                    i.setIdimpresora(id);
                    i.setDescripcion(descripcion);
                    i.setUltimo_numero_factura(ultimo_numero_factura);
                    i.setUltimo_numero_recibo(ultimo_numero_recibo);
                    i.setUltimo_numero_nota_credito(ultimo_numero_nota_credito);
                    i.setUltimo_numero_nota_debito(ultimo_numero_nota_debito);
                    dao.modificar(i);
                    cargar();
                } else {
                    JOptionPane.showMessageDialog(null, error, "ERRORES", JOptionPane.ERROR_MESSAGE);
                }
                break;
            case "ELIMINAR":
                if (error.isEmpty()) {
                    i.setIdimpresora(id);
                    dao.eliminar(i);
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
            i.setIdimpresora(id);
            dao.consultarDatos(i);
            txtCodigo.setText("" + id);
            txtDescripcion.setText(i.getDescripcion());
            
            String ult_numero_factura = i.getUltimo_numero_factura();
            txtUltimoNumeroFactura.setText(ult_numero_factura);
            int est_factura = Integer.parseInt(ult_numero_factura.substring(1, 3));
            txtEstablecimientoFactura.setText(String.format(tres_ceros, est_factura));
            int pun_emi_factura = Integer.parseInt(ult_numero_factura.substring(5, 7));
            txtPuntoEmisionFactura.setText(String.format(tres_ceros, pun_emi_factura));
            int num_factura = Integer.parseInt(ult_numero_factura.substring(9, 15));
            txtNumeroFactura.setText(String.format(siete_ceros, num_factura));
            
            String ult_numero_recibo = i.getUltimo_numero_recibo();
            txtUltimoNumeroRecibo.setText(ult_numero_recibo);
            int est_recibo = Integer.parseInt(ult_numero_recibo.substring(1, 3));
            txtEstablecimientoRecibo.setText(String.format(tres_ceros, est_recibo));
            int pun_emi_recibo = Integer.parseInt(ult_numero_recibo.substring(5, 7));
            txtPuntoEmisionRecibo.setText(String.format(tres_ceros, pun_emi_recibo));
            int num_recibo = Integer.parseInt(ult_numero_recibo.substring(9, 15));
            txtNumeroRecibo.setText(String.format(siete_ceros, num_recibo));
            
            String ult_numero_nota_credito = i.getUltimo_numero_nota_credito();
            txtUltimoNumeroNotaCredito.setText(ult_numero_nota_credito);
            int est_nota_credito = Integer.parseInt(ult_numero_nota_credito.substring(1, 3));
            txtEstablecimientoNotaCredito.setText(String.format(tres_ceros, est_nota_credito));
            int pun_emi_nota_credito = Integer.parseInt(ult_numero_nota_credito.substring(5, 7));
            txtPuntoEmisionNotaCredito.setText(String.format(tres_ceros, pun_emi_nota_credito));
            int num_nota_credito = Integer.parseInt(ult_numero_nota_credito.substring(9, 15));
            txtNumeroNotaCredito.setText(String.format(siete_ceros, num_nota_credito));
            
            String ult_numero_nota_debito = i.getUltimo_numero_nota_debito();
            txtUltimoNumeroNotaDebito.setText(ult_numero_nota_debito);
            int est_nota_debito = Integer.parseInt(ult_numero_nota_debito.substring(1, 3));
            txtEstablecimientoNotaDebito.setText(String.format(tres_ceros, est_nota_debito));
            int pun_emi_nota_debito = Integer.parseInt(ult_numero_nota_debito.substring(5, 7));
            txtPuntoEmisionNotaDebito.setText(String.format(tres_ceros, pun_emi_nota_debito));
            int num_nota_debito = Integer.parseInt(ult_numero_nota_debito.substring(9, 15));
            txtNumeroNotaDebito.setText(String.format(siete_ceros, num_nota_debito));
            
            habilitarCampos(operacion);
        } else {
            JOptionPane.showMessageDialog(null, "SELECCIONE UNA FILA", "ADVERTENCIA", JOptionPane.ERROR_MESSAGE);
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
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtEstablecimientoFactura = new org.jdesktop.swingx.JXTextField();
        txtPuntoEmisionFactura = new org.jdesktop.swingx.JXTextField();
        txtNumeroFactura = new org.jdesktop.swingx.JXTextField();
        txtUltimoNumeroFactura = new org.jdesktop.swingx.JXTextField();
        txtEstablecimientoRecibo = new org.jdesktop.swingx.JXTextField();
        txtPuntoEmisionRecibo = new org.jdesktop.swingx.JXTextField();
        txtNumeroRecibo = new org.jdesktop.swingx.JXTextField();
        txtUltimoNumeroRecibo = new org.jdesktop.swingx.JXTextField();
        txtEstablecimientoNotaCredito = new org.jdesktop.swingx.JXTextField();
        txtPuntoEmisionNotaCredito = new org.jdesktop.swingx.JXTextField();
        txtNumeroNotaCredito = new org.jdesktop.swingx.JXTextField();
        txtUltimoNumeroNotaCredito = new org.jdesktop.swingx.JXTextField();
        txtEstablecimientoNotaDebito = new org.jdesktop.swingx.JXTextField();
        txtPuntoEmisionNotaDebito = new org.jdesktop.swingx.JXTextField();
        txtNumeroNotaDebito = new org.jdesktop.swingx.JXTextField();
        txtUltimoNumeroNotaDebito = new org.jdesktop.swingx.JXTextField();

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

        setClosable(true);
        setIconifiable(true);

        jPanel1.setBackground(new java.awt.Color(50, 104, 151));

        jLabel1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Mantenimiento de Impresoras");

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
        tablaDatos.setComponentPopupMenu(menuDesplegable);
        jScrollPane1.setViewportView(tablaDatos);
        if (tablaDatos.getColumnModel().getColumnCount() > 0) {
            tablaDatos.getColumnModel().getColumn(0).setMinWidth(60);
            tablaDatos.getColumnModel().getColumn(0).setPreferredWidth(60);
            tablaDatos.getColumnModel().getColumn(0).setMaxWidth(60);
        }

        javax.swing.GroupLayout pestanhaListaLayout = new javax.swing.GroupLayout(pestanhaLista);
        pestanhaLista.setLayout(pestanhaListaLayout);
        pestanhaListaLayout.setHorizontalGroup(
            pestanhaListaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pestanhaListaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pestanhaListaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 501, Short.MAX_VALUE)
                    .addComponent(txtCriterio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pestanhaListaLayout.setVerticalGroup(
            pestanhaListaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pestanhaListaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtCriterio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE)
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
        jLabel4.setText("Ult. N° Factura:");

        jLabel5.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel5.setText("Ult. N° Recibo:");

        jLabel6.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel6.setText("Ult. N° Nota de Crédito:");

        jLabel7.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel7.setText("Ult. N° Nota de Débito:");

        txtEstablecimientoFactura.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtEstablecimientoFactura.setEnabled(false);
        txtEstablecimientoFactura.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtEstablecimientoFactura.setPrompt("000");
        txtEstablecimientoFactura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEstablecimientoFacturaActionPerformed(evt);
            }
        });
        txtEstablecimientoFactura.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtEstablecimientoFacturaKeyTyped(evt);
            }
        });

        txtPuntoEmisionFactura.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtPuntoEmisionFactura.setEnabled(false);
        txtPuntoEmisionFactura.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtPuntoEmisionFactura.setPrompt("000");
        txtPuntoEmisionFactura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPuntoEmisionFacturaActionPerformed(evt);
            }
        });
        txtPuntoEmisionFactura.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPuntoEmisionFacturaKeyTyped(evt);
            }
        });

        txtNumeroFactura.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNumeroFactura.setEnabled(false);
        txtNumeroFactura.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtNumeroFactura.setPrompt("000");
        txtNumeroFactura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumeroFacturaActionPerformed(evt);
            }
        });
        txtNumeroFactura.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNumeroFacturaKeyTyped(evt);
            }
        });

        txtUltimoNumeroFactura.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtUltimoNumeroFactura.setEnabled(false);
        txtUltimoNumeroFactura.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        txtUltimoNumeroFactura.setPrompt("000-000-0000000");

        txtEstablecimientoRecibo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtEstablecimientoRecibo.setEnabled(false);
        txtEstablecimientoRecibo.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtEstablecimientoRecibo.setPrompt("000");
        txtEstablecimientoRecibo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEstablecimientoReciboActionPerformed(evt);
            }
        });
        txtEstablecimientoRecibo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtEstablecimientoReciboKeyTyped(evt);
            }
        });

        txtPuntoEmisionRecibo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtPuntoEmisionRecibo.setEnabled(false);
        txtPuntoEmisionRecibo.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtPuntoEmisionRecibo.setPrompt("000");
        txtPuntoEmisionRecibo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPuntoEmisionReciboActionPerformed(evt);
            }
        });
        txtPuntoEmisionRecibo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPuntoEmisionReciboKeyTyped(evt);
            }
        });

        txtNumeroRecibo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNumeroRecibo.setEnabled(false);
        txtNumeroRecibo.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtNumeroRecibo.setPrompt("000");
        txtNumeroRecibo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumeroReciboActionPerformed(evt);
            }
        });
        txtNumeroRecibo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNumeroReciboKeyTyped(evt);
            }
        });

        txtUltimoNumeroRecibo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtUltimoNumeroRecibo.setEnabled(false);
        txtUltimoNumeroRecibo.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        txtUltimoNumeroRecibo.setPrompt("000-000-0000000");

        txtEstablecimientoNotaCredito.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtEstablecimientoNotaCredito.setEnabled(false);
        txtEstablecimientoNotaCredito.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtEstablecimientoNotaCredito.setPrompt("000");
        txtEstablecimientoNotaCredito.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEstablecimientoNotaCreditoActionPerformed(evt);
            }
        });
        txtEstablecimientoNotaCredito.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtEstablecimientoNotaCreditoKeyTyped(evt);
            }
        });

        txtPuntoEmisionNotaCredito.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtPuntoEmisionNotaCredito.setEnabled(false);
        txtPuntoEmisionNotaCredito.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtPuntoEmisionNotaCredito.setPrompt("000");
        txtPuntoEmisionNotaCredito.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPuntoEmisionNotaCreditoActionPerformed(evt);
            }
        });
        txtPuntoEmisionNotaCredito.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPuntoEmisionNotaCreditoKeyTyped(evt);
            }
        });

        txtNumeroNotaCredito.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNumeroNotaCredito.setEnabled(false);
        txtNumeroNotaCredito.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtNumeroNotaCredito.setPrompt("000");
        txtNumeroNotaCredito.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumeroNotaCreditoActionPerformed(evt);
            }
        });
        txtNumeroNotaCredito.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNumeroNotaCreditoKeyTyped(evt);
            }
        });

        txtUltimoNumeroNotaCredito.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtUltimoNumeroNotaCredito.setEnabled(false);
        txtUltimoNumeroNotaCredito.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        txtUltimoNumeroNotaCredito.setPrompt("000-000-0000000");

        txtEstablecimientoNotaDebito.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtEstablecimientoNotaDebito.setEnabled(false);
        txtEstablecimientoNotaDebito.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtEstablecimientoNotaDebito.setPrompt("000");
        txtEstablecimientoNotaDebito.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEstablecimientoNotaDebitoActionPerformed(evt);
            }
        });
        txtEstablecimientoNotaDebito.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtEstablecimientoNotaDebitoKeyTyped(evt);
            }
        });

        txtPuntoEmisionNotaDebito.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtPuntoEmisionNotaDebito.setEnabled(false);
        txtPuntoEmisionNotaDebito.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtPuntoEmisionNotaDebito.setPrompt("000");
        txtPuntoEmisionNotaDebito.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPuntoEmisionNotaDebitoActionPerformed(evt);
            }
        });
        txtPuntoEmisionNotaDebito.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPuntoEmisionNotaDebitoKeyTyped(evt);
            }
        });

        txtNumeroNotaDebito.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNumeroNotaDebito.setEnabled(false);
        txtNumeroNotaDebito.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtNumeroNotaDebito.setPrompt("000");
        txtNumeroNotaDebito.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumeroNotaDebitoActionPerformed(evt);
            }
        });
        txtNumeroNotaDebito.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNumeroNotaDebitoKeyTyped(evt);
            }
        });

        txtUltimoNumeroNotaDebito.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtUltimoNumeroNotaDebito.setEnabled(false);
        txtUltimoNumeroNotaDebito.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        txtUltimoNumeroNotaDebito.setPrompt("000-000-0000000");

        javax.swing.GroupLayout pestanhaABMLayout = new javax.swing.GroupLayout(pestanhaABM);
        pestanhaABM.setLayout(pestanhaABMLayout);
        pestanhaABMLayout.setHorizontalGroup(
            pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pestanhaABMLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pestanhaABMLayout.createSequentialGroup()
                        .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnConfirmar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(pestanhaABMLayout.createSequentialGroup()
                            .addGroup(pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(pestanhaABMLayout.createSequentialGroup()
                                            .addComponent(txtEstablecimientoFactura, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(txtPuntoEmisionFactura, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(txtNumeroFactura, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(txtUltimoNumeroFactura, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pestanhaABMLayout.createSequentialGroup()
                                            .addComponent(txtEstablecimientoRecibo, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(txtPuntoEmisionRecibo, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(txtNumeroRecibo, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(txtUltimoNumeroRecibo, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pestanhaABMLayout.createSequentialGroup()
                                        .addComponent(txtEstablecimientoNotaCredito, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtPuntoEmisionNotaCredito, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtNumeroNotaCredito, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtUltimoNumeroNotaCredito, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(pestanhaABMLayout.createSequentialGroup()
                                    .addComponent(txtEstablecimientoNotaDebito, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtPuntoEmisionNotaDebito, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtNumeroNotaDebito, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtUltimoNumeroNotaDebito, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGroup(pestanhaABMLayout.createSequentialGroup()
                            .addGroup(pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)))))
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
                    .addComponent(txtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtEstablecimientoFactura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPuntoEmisionFactura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNumeroFactura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtUltimoNumeroFactura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addGroup(pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtEstablecimientoRecibo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtPuntoEmisionRecibo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtNumeroRecibo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtUltimoNumeroRecibo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addGroup(pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtEstablecimientoNotaCredito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtPuntoEmisionNotaCredito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtNumeroNotaCredito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtUltimoNumeroNotaCredito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addGroup(pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtEstablecimientoNotaDebito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtPuntoEmisionNotaDebito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtNumeroNotaDebito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtUltimoNumeroNotaDebito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 59, Short.MAX_VALUE)
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
            txtEstablecimientoFactura.grabFocus();
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

    private void txtEstablecimientoFacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEstablecimientoFacturaActionPerformed
        if (txtEstablecimientoFactura.getText().isEmpty()) {
            txtEstablecimientoFactura.setText(String.format(tres_ceros, 0));
        } else {
            int establecimiento = Integer.parseInt(txtEstablecimientoFactura.getText());
            txtEstablecimientoFactura.setText(String.format(tres_ceros, establecimiento));
            txtPuntoEmisionFactura.grabFocus();
        }
    }//GEN-LAST:event_txtEstablecimientoFacturaActionPerformed

    private void txtEstablecimientoFacturaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEstablecimientoFacturaKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            getToolkit().beep();
            evt.consume();
        }
        if (txtEstablecimientoFactura.getText().length() == 3) {
            evt.consume();
        }
    }//GEN-LAST:event_txtEstablecimientoFacturaKeyTyped

    private void txtPuntoEmisionFacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPuntoEmisionFacturaActionPerformed
        if (txtPuntoEmisionFactura.getText().isEmpty()) {
            txtPuntoEmisionFactura.setText(String.format(tres_ceros, 0));
        } else {
            int puntoemision = Integer.parseInt(txtPuntoEmisionFactura.getText());
            txtPuntoEmisionFactura.setText(String.format(tres_ceros, puntoemision));
            txtNumeroFactura.grabFocus();
        }
    }//GEN-LAST:event_txtPuntoEmisionFacturaActionPerformed

    private void txtPuntoEmisionFacturaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPuntoEmisionFacturaKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            getToolkit().beep();
            evt.consume();
        }
        if (txtPuntoEmisionFactura.getText().length() == 3) {
            evt.consume();
        }
    }//GEN-LAST:event_txtPuntoEmisionFacturaKeyTyped

    private void txtNumeroFacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumeroFacturaActionPerformed
        if (txtNumeroFactura.getText().isEmpty()) {
            txtNumeroFactura.setText(String.format(tres_ceros, 0));
        } else {
            int numerofactura = Integer.parseInt(txtNumeroFactura.getText());
            txtNumeroFactura.setText(String.format(siete_ceros, numerofactura));
            txtUltimoNumeroFactura.setText(txtEstablecimientoFactura.getText() + "-"
                    + txtPuntoEmisionFactura.getText() + "-"
                    + txtNumeroFactura.getText());
            txtEstablecimientoRecibo.grabFocus();
        }
    }//GEN-LAST:event_txtNumeroFacturaActionPerformed

    private void txtNumeroFacturaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumeroFacturaKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            getToolkit().beep();
            evt.consume();
        }
        if (txtNumeroFactura.getText().length() == 7) {
            evt.consume();
        }
    }//GEN-LAST:event_txtNumeroFacturaKeyTyped

    private void txtEstablecimientoReciboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEstablecimientoReciboActionPerformed
        if (txtEstablecimientoRecibo.getText().isEmpty()) {
            txtEstablecimientoRecibo.setText(String.format(tres_ceros, 0));
        } else {
            int establecimiento = Integer.parseInt(txtEstablecimientoRecibo.getText());
            txtEstablecimientoRecibo.setText(String.format(tres_ceros, establecimiento));
            txtPuntoEmisionRecibo.grabFocus();
        }
    }//GEN-LAST:event_txtEstablecimientoReciboActionPerformed

    private void txtEstablecimientoReciboKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEstablecimientoReciboKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            getToolkit().beep();
            evt.consume();
        }
        if (txtEstablecimientoRecibo.getText().length() == 3) {
            evt.consume();
        }
    }//GEN-LAST:event_txtEstablecimientoReciboKeyTyped

    private void txtPuntoEmisionReciboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPuntoEmisionReciboActionPerformed
        if (txtPuntoEmisionRecibo.getText().isEmpty()) {
            txtPuntoEmisionRecibo.setText(String.format(tres_ceros, 0));
        } else {
            int puntoemision = Integer.parseInt(txtPuntoEmisionRecibo.getText());
            txtPuntoEmisionRecibo.setText(String.format(tres_ceros, puntoemision));
            txtNumeroRecibo.grabFocus();
        }
    }//GEN-LAST:event_txtPuntoEmisionReciboActionPerformed

    private void txtPuntoEmisionReciboKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPuntoEmisionReciboKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            getToolkit().beep();
            evt.consume();
        }
        if (txtPuntoEmisionRecibo.getText().length() == 3) {
            evt.consume();
        }
    }//GEN-LAST:event_txtPuntoEmisionReciboKeyTyped

    private void txtNumeroReciboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumeroReciboActionPerformed
        if (txtNumeroRecibo.getText().isEmpty()) {
            txtNumeroRecibo.setText(String.format(tres_ceros, 0));
        } else {
            int numerofactura = Integer.parseInt(txtNumeroRecibo.getText());
            txtNumeroRecibo.setText(String.format(siete_ceros, numerofactura));
            txtUltimoNumeroRecibo.setText(txtEstablecimientoRecibo.getText() + "-"
                    + txtPuntoEmisionRecibo.getText() + "-"
                    + txtNumeroRecibo.getText());
            txtEstablecimientoNotaCredito.grabFocus();
        }
    }//GEN-LAST:event_txtNumeroReciboActionPerformed

    private void txtNumeroReciboKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumeroReciboKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            getToolkit().beep();
            evt.consume();
        }
        if (txtNumeroRecibo.getText().length() == 7) {
            evt.consume();
        }
    }//GEN-LAST:event_txtNumeroReciboKeyTyped

    private void txtEstablecimientoNotaCreditoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEstablecimientoNotaCreditoActionPerformed
        if (txtEstablecimientoNotaCredito.getText().isEmpty()) {
            txtEstablecimientoNotaCredito.setText(String.format(tres_ceros, 0));
        } else {
            int establecimiento = Integer.parseInt(txtEstablecimientoNotaCredito.getText());
            txtEstablecimientoNotaCredito.setText(String.format(tres_ceros, establecimiento));
            txtPuntoEmisionNotaCredito.grabFocus();
        }
    }//GEN-LAST:event_txtEstablecimientoNotaCreditoActionPerformed

    private void txtEstablecimientoNotaCreditoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEstablecimientoNotaCreditoKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            getToolkit().beep();
            evt.consume();
        }
        if (txtEstablecimientoNotaCredito.getText().length() == 3) {
            evt.consume();
        }
    }//GEN-LAST:event_txtEstablecimientoNotaCreditoKeyTyped

    private void txtPuntoEmisionNotaCreditoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPuntoEmisionNotaCreditoActionPerformed
        if (txtPuntoEmisionNotaCredito.getText().isEmpty()) {
            txtPuntoEmisionNotaCredito.setText(String.format(tres_ceros, 0));
        } else {
            int puntoemision = Integer.parseInt(txtPuntoEmisionNotaCredito.getText());
            txtPuntoEmisionNotaCredito.setText(String.format(tres_ceros, puntoemision));
            txtNumeroNotaCredito.grabFocus();
        }
    }//GEN-LAST:event_txtPuntoEmisionNotaCreditoActionPerformed

    private void txtPuntoEmisionNotaCreditoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPuntoEmisionNotaCreditoKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            getToolkit().beep();
            evt.consume();
        }
        if (txtPuntoEmisionNotaCredito.getText().length() == 3) {
            evt.consume();
        }
    }//GEN-LAST:event_txtPuntoEmisionNotaCreditoKeyTyped

    private void txtNumeroNotaCreditoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumeroNotaCreditoActionPerformed
        if (txtNumeroNotaCredito.getText().isEmpty()) {
            txtNumeroNotaCredito.setText(String.format(tres_ceros, 0));
        } else {
            int numerofactura = Integer.parseInt(txtNumeroNotaCredito.getText());
            txtNumeroNotaCredito.setText(String.format(siete_ceros, numerofactura));
            txtUltimoNumeroNotaCredito.setText(txtEstablecimientoNotaCredito.getText() + "-"
                    + txtPuntoEmisionNotaCredito.getText() + "-"
                    + txtNumeroNotaCredito.getText());
            txtEstablecimientoNotaDebito.grabFocus();
        }
    }//GEN-LAST:event_txtNumeroNotaCreditoActionPerformed

    private void txtNumeroNotaCreditoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumeroNotaCreditoKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            getToolkit().beep();
            evt.consume();
        }
        if (txtNumeroNotaCredito.getText().length() == 7) {
            evt.consume();
        }
    }//GEN-LAST:event_txtNumeroNotaCreditoKeyTyped

    private void txtEstablecimientoNotaDebitoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEstablecimientoNotaDebitoActionPerformed
        if (txtEstablecimientoNotaDebito.getText().isEmpty()) {
            txtEstablecimientoNotaDebito.setText(String.format(tres_ceros, 0));
        } else {
            int establecimiento = Integer.parseInt(txtEstablecimientoNotaDebito.getText());
            txtEstablecimientoNotaDebito.setText(String.format(tres_ceros, establecimiento));
            txtPuntoEmisionNotaDebito.grabFocus();
        }
    }//GEN-LAST:event_txtEstablecimientoNotaDebitoActionPerformed

    private void txtEstablecimientoNotaDebitoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEstablecimientoNotaDebitoKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            getToolkit().beep();
            evt.consume();
        }
        if (txtEstablecimientoNotaDebito.getText().length() == 3) {
            evt.consume();
        }
    }//GEN-LAST:event_txtEstablecimientoNotaDebitoKeyTyped

    private void txtPuntoEmisionNotaDebitoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPuntoEmisionNotaDebitoActionPerformed
        if (txtPuntoEmisionNotaDebito.getText().isEmpty()) {
            txtPuntoEmisionNotaDebito.setText(String.format(tres_ceros, 0));
        } else {
            int puntoemision = Integer.parseInt(txtPuntoEmisionNotaDebito.getText());
            txtPuntoEmisionNotaDebito.setText(String.format(tres_ceros, puntoemision));
            txtNumeroNotaDebito.grabFocus();
        }
    }//GEN-LAST:event_txtPuntoEmisionNotaDebitoActionPerformed

    private void txtPuntoEmisionNotaDebitoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPuntoEmisionNotaDebitoKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            getToolkit().beep();
            evt.consume();
        }
        if (txtPuntoEmisionNotaDebito.getText().length() == 3) {
            evt.consume();
        }
    }//GEN-LAST:event_txtPuntoEmisionNotaDebitoKeyTyped

    private void txtNumeroNotaDebitoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumeroNotaDebitoActionPerformed
        if (txtNumeroNotaDebito.getText().isEmpty()) {
            txtNumeroNotaDebito.setText(String.format(tres_ceros, 0));
        } else {
            int numerofactura = Integer.parseInt(txtNumeroNotaDebito.getText());
            txtNumeroNotaDebito.setText(String.format(siete_ceros, numerofactura));
            txtUltimoNumeroNotaDebito.setText(txtEstablecimientoNotaDebito.getText() + "-"
                    + txtPuntoEmisionNotaDebito.getText() + "-"
                    + txtNumeroNotaDebito.getText());
            btnConfirmar.grabFocus();
        }
    }//GEN-LAST:event_txtNumeroNotaDebitoActionPerformed

    private void txtNumeroNotaDebitoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumeroNotaDebitoKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            getToolkit().beep();
            evt.consume();
        }
        if (txtNumeroNotaDebito.getText().length() == 7) {
            evt.consume();
        }
    }//GEN-LAST:event_txtNumeroNotaDebitoKeyTyped


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem Eliminar;
    private javax.swing.JMenuItem Modificar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnConfirmar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPopupMenu menuDesplegable;
    private javax.swing.JTabbedPane pestanha;
    private javax.swing.JPanel pestanhaABM;
    private javax.swing.JPanel pestanhaLista;
    private javax.swing.JTable tablaDatos;
    private org.jdesktop.swingx.JXTextField txtCodigo;
    private org.jdesktop.swingx.JXTextField txtCriterio;
    private org.jdesktop.swingx.JXTextField txtDescripcion;
    private org.jdesktop.swingx.JXTextField txtEstablecimientoFactura;
    private org.jdesktop.swingx.JXTextField txtEstablecimientoNotaCredito;
    private org.jdesktop.swingx.JXTextField txtEstablecimientoNotaDebito;
    private org.jdesktop.swingx.JXTextField txtEstablecimientoRecibo;
    private org.jdesktop.swingx.JXTextField txtNumeroFactura;
    private org.jdesktop.swingx.JXTextField txtNumeroNotaCredito;
    private org.jdesktop.swingx.JXTextField txtNumeroNotaDebito;
    private org.jdesktop.swingx.JXTextField txtNumeroRecibo;
    private org.jdesktop.swingx.JXTextField txtPuntoEmisionFactura;
    private org.jdesktop.swingx.JXTextField txtPuntoEmisionNotaCredito;
    private org.jdesktop.swingx.JXTextField txtPuntoEmisionNotaDebito;
    private org.jdesktop.swingx.JXTextField txtPuntoEmisionRecibo;
    private org.jdesktop.swingx.JXTextField txtUltimoNumeroFactura;
    private org.jdesktop.swingx.JXTextField txtUltimoNumeroNotaCredito;
    private org.jdesktop.swingx.JXTextField txtUltimoNumeroNotaDebito;
    private org.jdesktop.swingx.JXTextField txtUltimoNumeroRecibo;
    // End of variables declaration//GEN-END:variables
}
