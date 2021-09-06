package Vistas;

import Dao.DAOCliente;
import Dao.DAOClienteListaPrecio;
import Dao.DAOListaPrecio;
import Modelos.Cliente;
import Modelos.ClienteListaPrecio;
import Modelos.ListaPrecio;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author armando
 */
public class JFrmClienteListaPrecio extends javax.swing.JInternalFrame {

    ClienteListaPrecio clp = new ClienteListaPrecio();
    Cliente c = new Cliente();
    ListaPrecio lp = new ListaPrecio();

    DAOClienteListaPrecio dao = new DAOClienteListaPrecio();
    DAOCliente daoCliente = new DAOCliente();
    DAOListaPrecio daoListaPrecio = new DAOListaPrecio();

    ArrayList<Object[]> datos = new ArrayList<>();
    ArrayList<Object[]> datosCliente = new ArrayList<>();
    ArrayList<Object[]> datosListaPrecio = new ArrayList<>();

    //VARIABLE QUE MANEJA QUE TIPOS DE OPERACIONES SE REALIZARAN: SI VA A SER ALTA, BAJA O MODIFICACION DEL REGISTRO
    String operacion = "";
    double valorMontoDescuento = 0.0;
    double valorMontoRecargo = 0.0;

    /**
     * Creates new form JFrmClienteListaPrecio
     */
    public JFrmClienteListaPrecio() {
        setTitle("JFrmClienteListaPrecio");
        initComponents();
        //cargar();
    }

    public void cargar() {
        DefaultTableModel modelo = (DefaultTableModel) tablaDatos.getModel();
        modelo.setRowCount(0);
        int idcliente = 0;
        if (txtCodigoClienteCriterio.getText().isEmpty()) {
            idcliente = 0;
        } else {
            idcliente = Integer.parseInt(txtCodigoClienteCriterio.getText());
        }
        datos = dao.consultar(idcliente);
        for (Object[] obj : datos) {
            modelo.addRow(obj);
        }
        this.tablaDatos.setModel(modelo);
    }

    public void cargarCliente() {
        DefaultTableModel modelo = (DefaultTableModel) tablaDatosUsuario.getModel();
        modelo.setRowCount(0);
        datosCliente = daoCliente.consultar(txtCriterioUsuario.getText());
        for (Object[] obj : datosCliente) {
            modelo.addRow(obj);
        }
        this.tablaDatosUsuario.setModel(modelo);
    }

    public void cargarClienteDos() {
        DefaultTableModel modelo = (DefaultTableModel) tablaDatosClienteDos.getModel();
        modelo.setRowCount(0);
        datosCliente = daoCliente.consultar(txtCriterioClienteDos.getText());
        for (Object[] obj : datosCliente) {
            modelo.addRow(obj);
        }
        this.tablaDatosClienteDos.setModel(modelo);
    }

    public void cargarPrograma() {
        DefaultTableModel modelo = (DefaultTableModel) tablaDatosListaPrecio.getModel();
        modelo.setRowCount(0);
        datosListaPrecio = daoListaPrecio.consultarSinMoneda(txtCriterioListaPrecio.getText());
        for (Object[] obj : datosListaPrecio) {
            modelo.addRow(obj);
        }
        this.tablaDatosListaPrecio.setModel(modelo);
    }

    public void habilitarCampos(String accion) {
        switch (accion) {
            case "NUEVO":
                //CAMPOS
                txtCodigoCliente.setEnabled(true);
                txtCodigoListaPrecio.setEnabled(true);
                txtPorcentajeDescuento.setEnabled(true);
                txtPorcentajeRecargo.setEnabled(true);
                //BOTONES
                btnNuevo.setEnabled(false);
                btnConfirmar.setEnabled(true);
                btnCancelar.setEnabled(true);
                //REDIRECIONAMOS
                txtCodigoCliente.grabFocus();
                break;
            case "MODIFICAR":
                //CAMPOS
                txtCodigoCliente.setEnabled(false);
                txtCodigoListaPrecio.setEnabled(false);
                txtPorcentajeDescuento.setEnabled(true);
                txtPorcentajeRecargo.setEnabled(true);
                //BOTONES
                btnNuevo.setEnabled(false);
                btnConfirmar.setEnabled(true);
                btnCancelar.setEnabled(true);
                //REDIRECIONAMOS
                pestanha.setSelectedIndex(1);
                txtPorcentajeDescuento.grabFocus();
                break;
            case "ELIMINAR":
                //CAMPOS
                txtCodigoCliente.setEnabled(false);
                txtCodigoListaPrecio.setEnabled(false);
                txtPorcentajeDescuento.setEnabled(false);
                txtPorcentajeRecargo.setEnabled(false);
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
                txtCodigoCliente.setEnabled(false);
                txtCodigoListaPrecio.setEnabled(false);
                txtPorcentajeDescuento.setEnabled(false);
                txtPorcentajeRecargo.setEnabled(false);
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
                txtCodigoCliente.setEnabled(false);
                txtCodigoListaPrecio.setEnabled(false);
                txtPorcentajeDescuento.setEnabled(false);
                txtPorcentajeRecargo.setEnabled(false);
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
        txtCriterioUsuario.setText(null);
        txtCodigoCliente.setText(null);
        txtDescripcionCliente.setText(null);
        txtCodigoListaPrecio.setText(null);
        txtDescripcionListaPrecio.setText(null);
        txtCodigoClienteCriterio.setText(null);
        txtDescripcionClienteCriterio.setText(null);
        txtPorcentajeDescuento.setText(null);
        txtPorcentajeRecargo.setText(null);
        operacion = "";
        valorMontoDescuento = 0.0;
        valorMontoRecargo = 0.0;
    }

    public void guardar(String accion) {
        //CAPTURA Y VALIDACIONES DE LOS DATOS RECIBIDOS
        String error = "";

        int idcliente = Integer.parseInt(txtCodigoCliente.getText());
        int idlistaprecio = Integer.parseInt(txtCodigoListaPrecio.getText());
        double porcentaje_descuento = valorMontoDescuento;
        double porcentaje_recargo = valorMontoRecargo;
        switch (accion) {
            case "NUEVO":
                if (idcliente == 0) {
                    error += "NO HA SELECCIONADO UN CLIENTE.\n";
                }
                if (idlistaprecio == 0) {
                    error += "NO HA SELECCIONADO UNA LISTA DE PRECIO.\n";
                }
                if (porcentaje_descuento < 0) {
                    error += "EL PORCENTAJE DE DESCUENTO NO PUEDE SER MENOR A 0.\n";
                }
                if (porcentaje_recargo < 0) {
                    error += "EL PORCENTAJE DE RECARGO NO PUEDE SER MENOR A 0.\n";
                }
                if (error.isEmpty()) {
                    clp.setIdcliente(idcliente);
                    clp.setIdlista(idlistaprecio);
                    clp.setDescuento(porcentaje_descuento);
                    clp.setRecargo(porcentaje_recargo);
                    dao.agregar(clp);
                    cargar();
                } else {
                    JOptionPane.showMessageDialog(null, error, "ERRORES", JOptionPane.ERROR_MESSAGE);
                }
                break;
            case "MODIFICAR":
                if (idcliente == 0) {
                    error += "NO HA SELECCIONADO UN CLIENTE.\n";
                }
                if (idlistaprecio == 0) {
                    error += "NO HA SELECCIONADO UNA LISTA DE PRECIO.\n";
                }
                if (porcentaje_descuento < 0) {
                    error += "EL PORCENTAJE DE DESCUENTO NO PUEDE SER MENOR A 0.\n";
                }
                if (porcentaje_recargo < 0) {
                    error += "EL PORCENTAJE DE RECARGO NO PUEDE SER MENOR A 0.\n";
                }
                if (error.isEmpty()) {
                    clp.setIdcliente(idcliente);
                    clp.setIdlista(idlistaprecio);
                    clp.setDescuento(porcentaje_descuento);
                    clp.setRecargo(porcentaje_recargo);
                    dao.modificar(clp);
                    cargar();
                } else {
                    JOptionPane.showMessageDialog(null, error, "ERRORES", JOptionPane.ERROR_MESSAGE);
                }
                break;
            case "ELIMINAR":
                if (error.isEmpty()) {
                    clp.setIdcliente(idcliente);
                    clp.setIdlista(idlistaprecio);
                    dao.eliminar(clp);
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
            int idcliente = Integer.parseInt(txtCodigoClienteCriterio.getText());
            int idlistaprecio = Integer.parseInt(tablaDatos.getValueAt(fila, 3).toString());
            clp.setIdcliente(idcliente);
            clp.setIdlista(idlistaprecio);
            dao.consultarDatos(clp);
            txtCodigoCliente.setText("" + idcliente);
            c.setIdcliente(idcliente);
            daoCliente.consultarDatos(c);
            txtDescripcionCliente.setText(c.getNombre() + " " + c.getApellido());
            txtCodigoListaPrecio.setText("" + idlistaprecio);
            lp.setIdlista(idlistaprecio);
            daoListaPrecio.consultarDatos(lp);
            txtDescripcionListaPrecio.setText(lp.getDescripcion());
            DecimalFormat formateador = new DecimalFormat("#,###.00");
            valorMontoDescuento = clp.getDescuento();
            txtPorcentajeDescuento.setText(""+valorMontoDescuento);
            valorMontoRecargo = clp.getRecargo();
            txtPorcentajeRecargo.setText(""+valorMontoRecargo);
            habilitarCampos(operacion);
        } else {
            JOptionPane.showMessageDialog(null, "SELECCIONE UNA FILA", "ADVERTENCIA", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void buscarCliente() {
        cargarCliente();
        BuscadorClienteCriterio.setModal(true);
        BuscadorClienteCriterio.setSize(540, 285);
        BuscadorClienteCriterio.setLocationRelativeTo(this);
        BuscadorClienteCriterio.setVisible(true);
        int fila = tablaDatosUsuario.getSelectedRow();
        if (fila >= 0) {
            txtCodigoClienteCriterio.setText(tablaDatosUsuario.getValueAt(fila, 0).toString());
            txtDescripcionClienteCriterio.setText(tablaDatosUsuario.getValueAt(fila, 1).toString());
        } else {
            txtCodigoClienteCriterio.setText(null);
            txtDescripcionClienteCriterio.setText(null);
        }
    }

    private void buscarUsuarioDos() {
        cargarClienteDos();
        BuscadorCliente.setModal(true);
        BuscadorCliente.setSize(540, 285);
        BuscadorCliente.setLocationRelativeTo(this);
        BuscadorCliente.setVisible(true);
        int fila = tablaDatosClienteDos.getSelectedRow();
        if (fila >= 0) {
            txtCodigoCliente.setText(tablaDatosClienteDos.getValueAt(fila, 0).toString());
            txtDescripcionCliente.setText(tablaDatosClienteDos.getValueAt(fila, 1).toString() + " " + tablaDatosClienteDos.getValueAt(fila, 2).toString());
        } else {
            txtCodigoCliente.setText(null);
            txtDescripcionCliente.setText(null);
        }
    }

    private void buscarPrograma() {
        cargarPrograma();
        BuscadorListaPrecio.setModal(true);
        BuscadorListaPrecio.setSize(540, 285);
        BuscadorListaPrecio.setLocationRelativeTo(this);
        BuscadorListaPrecio.setVisible(true);
        int fila = tablaDatosListaPrecio.getSelectedRow();
        if (fila >= 0) {
            txtCodigoListaPrecio.setText(tablaDatosListaPrecio.getValueAt(fila, 0).toString());
            txtDescripcionListaPrecio.setText(tablaDatosListaPrecio.getValueAt(fila, 1).toString());
        } else {
            txtCodigoListaPrecio.setText(null);
            txtDescripcionListaPrecio.setText(null);
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
        BuscadorClienteCriterio = new javax.swing.JDialog();
        jPanel3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        txtCriterioUsuario = new org.jdesktop.swingx.JXTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaDatosUsuario = new javax.swing.JTable();
        BuscadorCliente = new javax.swing.JDialog();
        jPanel4 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        txtCriterioClienteDos = new org.jdesktop.swingx.JXTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaDatosClienteDos = new javax.swing.JTable();
        BuscadorListaPrecio = new javax.swing.JDialog();
        jPanel5 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        txtCriterioListaPrecio = new org.jdesktop.swingx.JXTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        tablaDatosListaPrecio = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        pestanha = new javax.swing.JTabbedPane();
        pestanhaLista = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaDatos = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        txtCodigoClienteCriterio = new org.jdesktop.swingx.JXTextField();
        txtDescripcionClienteCriterio = new org.jdesktop.swingx.JXTextField();
        pestanhaABM = new javax.swing.JPanel();
        btnCancelar = new javax.swing.JButton();
        btnConfirmar = new javax.swing.JButton();
        btnNuevo = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        txtCodigoCliente = new org.jdesktop.swingx.JXTextField();
        txtDescripcionCliente = new org.jdesktop.swingx.JXTextField();
        jLabel7 = new javax.swing.JLabel();
        txtCodigoListaPrecio = new org.jdesktop.swingx.JXTextField();
        txtDescripcionListaPrecio = new org.jdesktop.swingx.JXTextField();
        jLabel10 = new javax.swing.JLabel();
        txtPorcentajeDescuento = new org.jdesktop.swingx.JXTextField();
        jLabel11 = new javax.swing.JLabel();
        txtPorcentajeRecargo = new org.jdesktop.swingx.JXTextField();

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

        jLabel5.setBackground(new java.awt.Color(50, 104, 151));
        jLabel5.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("BUSCADOR DE CLIENTES");
        jLabel5.setOpaque(true);

        txtCriterioUsuario.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtCriterioUsuario.setPrompt("Aqui puede ingresar los filtros para la busqueda..");
        txtCriterioUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCriterioUsuarioActionPerformed(evt);
            }
        });
        txtCriterioUsuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCriterioUsuarioKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCriterioUsuarioKeyTyped(evt);
            }
        });

        tablaDatosUsuario.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        tablaDatosUsuario.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "<html><p style=\"text-align:center\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Código</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Nombre</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Apellido</span></span></span></p></html> "
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
        tablaDatosUsuario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaDatosUsuarioMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tablaDatosUsuario);
        if (tablaDatosUsuario.getColumnModel().getColumnCount() > 0) {
            tablaDatosUsuario.getColumnModel().getColumn(0).setMinWidth(60);
            tablaDatosUsuario.getColumnModel().getColumn(0).setPreferredWidth(60);
            tablaDatosUsuario.getColumnModel().getColumn(0).setMaxWidth(60);
        }

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCriterioUsuario, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCriterioUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout BuscadorClienteCriterioLayout = new javax.swing.GroupLayout(BuscadorClienteCriterio.getContentPane());
        BuscadorClienteCriterio.getContentPane().setLayout(BuscadorClienteCriterioLayout);
        BuscadorClienteCriterioLayout.setHorizontalGroup(
            BuscadorClienteCriterioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BuscadorClienteCriterioLayout.setVerticalGroup(
            BuscadorClienteCriterioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jLabel8.setBackground(new java.awt.Color(50, 104, 151));
        jLabel8.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("BUSCADOR DE CLIENTES");
        jLabel8.setOpaque(true);

        txtCriterioClienteDos.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtCriterioClienteDos.setPrompt("Aqui puede ingresar los filtros para la busqueda..");
        txtCriterioClienteDos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCriterioClienteDosActionPerformed(evt);
            }
        });
        txtCriterioClienteDos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCriterioClienteDosKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCriterioClienteDosKeyTyped(evt);
            }
        });

        tablaDatosClienteDos.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        tablaDatosClienteDos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "<html><p style=\"text-align:center\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Código</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Nombre</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Apellido</span></span></span></p></html> "
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
        tablaDatosClienteDos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaDatosClienteDosMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tablaDatosClienteDos);
        if (tablaDatosClienteDos.getColumnModel().getColumnCount() > 0) {
            tablaDatosClienteDos.getColumnModel().getColumn(0).setMinWidth(60);
            tablaDatosClienteDos.getColumnModel().getColumn(0).setPreferredWidth(60);
            tablaDatosClienteDos.getColumnModel().getColumn(0).setMaxWidth(60);
        }

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCriterioClienteDos, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCriterioClienteDos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout BuscadorClienteLayout = new javax.swing.GroupLayout(BuscadorCliente.getContentPane());
        BuscadorCliente.getContentPane().setLayout(BuscadorClienteLayout);
        BuscadorClienteLayout.setHorizontalGroup(
            BuscadorClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BuscadorClienteLayout.setVerticalGroup(
            BuscadorClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jLabel9.setBackground(new java.awt.Color(50, 104, 151));
        jLabel9.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("BUSCADOR DE LISTAS DE PRECIOS");
        jLabel9.setOpaque(true);

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

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCriterioListaPrecio, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
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
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BuscadorListaPrecioLayout.setVerticalGroup(
            BuscadorListaPrecioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setClosable(true);
        setIconifiable(true);

        jPanel1.setBackground(new java.awt.Color(50, 104, 151));

        jLabel1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Asignación de Listas de Precios a Clientes");

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
                "<html><p style=\"text-align:center\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Código</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Descripción</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Ruc</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Cod.Lista</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Lista de Precio</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">% Descuento</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">% Recargo</span></span></span></p></html> "
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.Double.class, java.lang.Double.class
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
            tablaDatos.getColumnModel().getColumn(0).setMinWidth(0);
            tablaDatos.getColumnModel().getColumn(0).setPreferredWidth(0);
            tablaDatos.getColumnModel().getColumn(0).setMaxWidth(0);
            tablaDatos.getColumnModel().getColumn(1).setMinWidth(0);
            tablaDatos.getColumnModel().getColumn(1).setPreferredWidth(0);
            tablaDatos.getColumnModel().getColumn(1).setMaxWidth(0);
            tablaDatos.getColumnModel().getColumn(2).setMinWidth(0);
            tablaDatos.getColumnModel().getColumn(2).setPreferredWidth(0);
            tablaDatos.getColumnModel().getColumn(2).setMaxWidth(0);
            tablaDatos.getColumnModel().getColumn(3).setMinWidth(70);
            tablaDatos.getColumnModel().getColumn(3).setPreferredWidth(70);
            tablaDatos.getColumnModel().getColumn(3).setMaxWidth(70);
            tablaDatos.getColumnModel().getColumn(5).setMinWidth(100);
            tablaDatos.getColumnModel().getColumn(5).setPreferredWidth(100);
            tablaDatos.getColumnModel().getColumn(5).setMaxWidth(100);
            tablaDatos.getColumnModel().getColumn(6).setMinWidth(100);
            tablaDatos.getColumnModel().getColumn(6).setPreferredWidth(100);
            tablaDatos.getColumnModel().getColumn(6).setMaxWidth(100);
        }

        jLabel6.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel6.setText("Cliente:");

        txtCodigoClienteCriterio.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtCodigoClienteCriterio.setPrompt("Cód. Cliente");
        txtCodigoClienteCriterio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoClienteCriterioActionPerformed(evt);
            }
        });
        txtCodigoClienteCriterio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodigoClienteCriterioKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodigoClienteCriterioKeyTyped(evt);
            }
        });

        txtDescripcionClienteCriterio.setEnabled(false);
        txtDescripcionClienteCriterio.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtDescripcionClienteCriterio.setPrompt("Descripción o nombre del usuario...");

        javax.swing.GroupLayout pestanhaListaLayout = new javax.swing.GroupLayout(pestanhaLista);
        pestanhaLista.setLayout(pestanhaListaLayout);
        pestanhaListaLayout.setHorizontalGroup(
            pestanhaListaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pestanhaListaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pestanhaListaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 571, Short.MAX_VALUE)
                    .addGroup(pestanhaListaLayout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCodigoClienteCriterio, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDescripcionClienteCriterio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pestanhaListaLayout.setVerticalGroup(
            pestanhaListaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pestanhaListaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pestanhaListaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtCodigoClienteCriterio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDescripcionClienteCriterio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        txtDescripcionClienteCriterio.getAccessibleContext().setAccessibleDescription("Descripción o nombre del cliente...");

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
        jLabel4.setText("Cliente:");

        txtCodigoCliente.setEnabled(false);
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

        txtDescripcionCliente.setEnabled(false);
        txtDescripcionCliente.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtDescripcionCliente.setPrompt("Descripción o nombre del cliente...");

        jLabel7.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel7.setText("Lista de Precio:");

        txtCodigoListaPrecio.setEnabled(false);
        txtCodigoListaPrecio.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtCodigoListaPrecio.setPrompt("Cód. L.P.");
        txtCodigoListaPrecio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoListaPrecioActionPerformed(evt);
            }
        });
        txtCodigoListaPrecio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodigoListaPrecioKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodigoListaPrecioKeyTyped(evt);
            }
        });

        txtDescripcionListaPrecio.setEnabled(false);
        txtDescripcionListaPrecio.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtDescripcionListaPrecio.setPrompt("Descripción o nombre de la lista de precio...");

        jLabel10.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel10.setText("% Descuento:");

        txtPorcentajeDescuento.setEnabled(false);
        txtPorcentajeDescuento.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtPorcentajeDescuento.setPrompt("Porcentaje del descuento");
        txtPorcentajeDescuento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPorcentajeDescuentoActionPerformed(evt);
            }
        });
        txtPorcentajeDescuento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPorcentajeDescuentoKeyTyped(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel11.setText("% Recargo:");

        txtPorcentajeRecargo.setEnabled(false);
        txtPorcentajeRecargo.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtPorcentajeRecargo.setPrompt("Porcentaje del recargo");
        txtPorcentajeRecargo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPorcentajeRecargoActionPerformed(evt);
            }
        });
        txtPorcentajeRecargo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPorcentajeRecargoKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout pestanhaABMLayout = new javax.swing.GroupLayout(pestanhaABM);
        pestanhaABM.setLayout(pestanhaABMLayout);
        pestanhaABMLayout.setHorizontalGroup(
            pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pestanhaABMLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pestanhaABMLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnConfirmar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pestanhaABMLayout.createSequentialGroup()
                        .addGroup(pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCodigoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCodigoListaPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDescripcionListaPrecio, javax.swing.GroupLayout.DEFAULT_SIZE, 406, Short.MAX_VALUE)
                            .addComponent(txtDescripcionCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(pestanhaABMLayout.createSequentialGroup()
                        .addGroup(pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtPorcentajeDescuento, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                            .addComponent(txtPorcentajeRecargo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pestanhaABMLayout.setVerticalGroup(
            pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pestanhaABMLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtCodigoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDescripcionCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCodigoListaPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDescripcionListaPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtPorcentajeDescuento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtPorcentajeRecargo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 171, Short.MAX_VALUE)
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

    private void txtCodigoClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoClienteActionPerformed
        if (txtCodigoCliente.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "NO PUEDE DEJAR EL CAMPO DE CLIENTE VACIO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else {
            int idcliente = Integer.parseInt(txtCodigoCliente.getText());
            c.setIdcliente(idcliente);
            boolean resultado = daoCliente.consultarDatos(c);
            if (resultado == true) {
                txtDescripcionCliente.setText(c.getNombre() + " " + c.getApellido());
                txtCodigoListaPrecio.grabFocus();
            } else {
                txtCodigoCliente.setText(null);
                txtDescripcionCliente.setText(null);
                txtCodigoCliente.grabFocus();
            }
        }
    }//GEN-LAST:event_txtCodigoClienteActionPerformed

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

    private void txtCriterioUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCriterioUsuarioActionPerformed
        cargarCliente();
    }//GEN-LAST:event_txtCriterioUsuarioActionPerformed

    private void txtCriterioUsuarioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCriterioUsuarioKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            evt.setKeyChar(Character.toUpperCase(c));
        }
        if (txtCriterioUsuario.getText().length() == 100) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCriterioUsuarioKeyTyped

    private void txtCriterioUsuarioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCriterioUsuarioKeyPressed
        if (evt.VK_ESCAPE == evt.getKeyCode()) {
            txtCodigoCliente.setText(null);
            txtDescripcionCliente.setText(null);
            txtCodigoCliente.grabFocus();
            BuscadorClienteCriterio.dispose();
        }
    }//GEN-LAST:event_txtCriterioUsuarioKeyPressed

    private void tablaDatosUsuarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaDatosUsuarioMouseClicked
        if (evt.getClickCount() == 2) {
            if (tablaDatosUsuario.getSelectedRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "SELECCIONE UNA FILA");
            } else {
                txtCriterioUsuario.setText(null);
                BuscadorClienteCriterio.dispose();
            }
        }
    }//GEN-LAST:event_tablaDatosUsuarioMouseClicked

    private void txtCodigoClienteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoClienteKeyPressed
        if (evt.VK_F1 == evt.getKeyCode()) {
            buscarUsuarioDos();
        }
    }//GEN-LAST:event_txtCodigoClienteKeyPressed

    private void txtCodigoClienteCriterioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoClienteCriterioActionPerformed
        if (txtCodigoClienteCriterio.getText().isEmpty()) {
            txtCodigoClienteCriterio.setText(null);
            txtDescripcionClienteCriterio.setText(null);
            txtCodigoClienteCriterio.grabFocus();
        } else {
            int idcliente = Integer.parseInt(txtCodigoClienteCriterio.getText());
            c.setIdcliente(idcliente);
            boolean resultado = daoCliente.consultarDatos(c);
            if (resultado == true) {
                txtDescripcionClienteCriterio.setText(c.getNombre() + " " + c.getApellido());
                cargar();
            } else {
                txtCodigoClienteCriterio.setText(null);
                txtDescripcionClienteCriterio.setText(null);
                txtCodigoClienteCriterio.grabFocus();
                cargar();
            }
        }
    }//GEN-LAST:event_txtCodigoClienteCriterioActionPerformed

    private void txtCodigoClienteCriterioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoClienteCriterioKeyPressed
        if (evt.VK_F1 == evt.getKeyCode()) {
            buscarCliente();
        }
    }//GEN-LAST:event_txtCodigoClienteCriterioKeyPressed

    private void txtCodigoClienteCriterioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoClienteCriterioKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            getToolkit().beep();
            evt.consume();
        }
        if (txtCodigoClienteCriterio.getText().length() == 10) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCodigoClienteCriterioKeyTyped

    private void txtCodigoListaPrecioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoListaPrecioActionPerformed
        if (txtCodigoListaPrecio.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "NO PUEDE DEJAR EL CAMPO DE LISTA DE PRECIO VACIO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else {
            int idlistaprecio = Integer.parseInt(txtCodigoListaPrecio.getText());
            lp.setIdlista(idlistaprecio);
            boolean resultado = daoListaPrecio.consultarDatos(lp);
            if (resultado == true) {
                txtDescripcionListaPrecio.setText(lp.getDescripcion());
                txtPorcentajeDescuento.grabFocus();
            } else {
                txtCodigoListaPrecio.setText(null);
                txtDescripcionListaPrecio.setText(null);
                txtCodigoListaPrecio.grabFocus();
            }
        }
    }//GEN-LAST:event_txtCodigoListaPrecioActionPerformed

    private void txtCodigoListaPrecioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoListaPrecioKeyPressed
        if (evt.VK_F1 == evt.getKeyCode()) {
            buscarPrograma();
        }
    }//GEN-LAST:event_txtCodigoListaPrecioKeyPressed

    private void txtCodigoListaPrecioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoListaPrecioKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            getToolkit().beep();
            evt.consume();
        }
        if (txtCodigoListaPrecio.getText().length() == 10) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCodigoListaPrecioKeyTyped

    private void txtCriterioClienteDosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCriterioClienteDosActionPerformed
        cargarClienteDos();
    }//GEN-LAST:event_txtCriterioClienteDosActionPerformed

    private void txtCriterioClienteDosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCriterioClienteDosKeyPressed
        if (evt.VK_ESCAPE == evt.getKeyCode()) {
            txtCodigoCliente.setText(null);
            txtDescripcionCliente.setText(null);
            txtCodigoCliente.grabFocus();
            BuscadorCliente.dispose();
        }
    }//GEN-LAST:event_txtCriterioClienteDosKeyPressed

    private void txtCriterioClienteDosKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCriterioClienteDosKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            evt.setKeyChar(Character.toUpperCase(c));
        }
        if (txtCriterioClienteDos.getText().length() == 100) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCriterioClienteDosKeyTyped

    private void tablaDatosClienteDosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaDatosClienteDosMouseClicked
        if (evt.getClickCount() == 2) {
            if (tablaDatosClienteDos.getSelectedRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "SELECCIONE UNA FILA");
            } else {
                txtCriterioClienteDos.setText(null);
                BuscadorCliente.dispose();
            }
        }
    }//GEN-LAST:event_tablaDatosClienteDosMouseClicked

    private void txtCriterioListaPrecioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCriterioListaPrecioActionPerformed
        cargarPrograma();
    }//GEN-LAST:event_txtCriterioListaPrecioActionPerformed

    private void txtCriterioListaPrecioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCriterioListaPrecioKeyPressed
        if (evt.VK_ESCAPE == evt.getKeyCode()) {
            txtCodigoListaPrecio.setText(null);
            txtDescripcionListaPrecio.setText(null);
            txtCodigoListaPrecio.grabFocus();
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

    private void txtPorcentajeDescuentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPorcentajeDescuentoActionPerformed
        if (txtPorcentajeDescuento.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "NO PUEDE DEJAR EL CAMPO DE PORCENTAJE DE DESCUENTO VACIO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else {
            String number = txtPorcentajeDescuento.getText();
            double monto = Double.parseDouble(number);
            if (monto < 0.0) {
                JOptionPane.showMessageDialog(null, "EL PORCENTAJE DE DESCUENTO NO PUEDE SER MENOR A 0", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
            } else {
                valorMontoDescuento = monto;
                DecimalFormat formateador = new DecimalFormat("#,###.00");
                txtPorcentajeDescuento.setText(formateador.format(valorMontoDescuento));
                txtPorcentajeRecargo.grabFocus();
            }
        }
    }//GEN-LAST:event_txtPorcentajeDescuentoActionPerformed

    private void txtPorcentajeDescuentoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPorcentajeDescuentoKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            getToolkit().beep();
            evt.consume();
        }
        if (c == ',') {
            evt.consume();
        }
        if (txtPorcentajeDescuento.getText().length() == 10) {
            evt.consume();
        }
    }//GEN-LAST:event_txtPorcentajeDescuentoKeyTyped

    private void txtPorcentajeRecargoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPorcentajeRecargoActionPerformed
        if (txtPorcentajeRecargo.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "NO PUEDE DEJAR EL CAMPO DE PORCENTAJE DE RECARGO VACIO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else {
            String number = txtPorcentajeRecargo.getText();
            double monto = Double.parseDouble(number);
            if (monto < 0.0) {
                JOptionPane.showMessageDialog(null, "EL PORCENTAJE DE RECARGO NO PUEDE SER MENOR A 0", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
            } else {
                valorMontoRecargo = monto;
                DecimalFormat formateador = new DecimalFormat("#,###.00");
                txtPorcentajeRecargo.setText(formateador.format(valorMontoRecargo));
                btnConfirmar.grabFocus();
            }
        }
    }//GEN-LAST:event_txtPorcentajeRecargoActionPerformed

    private void txtPorcentajeRecargoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPorcentajeRecargoKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPorcentajeRecargoKeyTyped

    private void ModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ModificarActionPerformed
        operacion = "MODIFICAR";
        recuperarDatos();
    }//GEN-LAST:event_ModificarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDialog BuscadorCliente;
    private javax.swing.JDialog BuscadorClienteCriterio;
    private javax.swing.JDialog BuscadorListaPrecio;
    private javax.swing.JMenuItem Eliminar;
    private javax.swing.JMenuItem Modificar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnConfirmar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
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
    private javax.swing.JTable tablaDatosClienteDos;
    private javax.swing.JTable tablaDatosListaPrecio;
    private javax.swing.JTable tablaDatosUsuario;
    private org.jdesktop.swingx.JXTextField txtCodigoCliente;
    private org.jdesktop.swingx.JXTextField txtCodigoClienteCriterio;
    private org.jdesktop.swingx.JXTextField txtCodigoListaPrecio;
    private org.jdesktop.swingx.JXTextField txtCriterioClienteDos;
    private org.jdesktop.swingx.JXTextField txtCriterioListaPrecio;
    private org.jdesktop.swingx.JXTextField txtCriterioUsuario;
    private org.jdesktop.swingx.JXTextField txtDescripcionCliente;
    private org.jdesktop.swingx.JXTextField txtDescripcionClienteCriterio;
    private org.jdesktop.swingx.JXTextField txtDescripcionListaPrecio;
    private org.jdesktop.swingx.JXTextField txtPorcentajeDescuento;
    private org.jdesktop.swingx.JXTextField txtPorcentajeRecargo;
    // End of variables declaration//GEN-END:variables
}
