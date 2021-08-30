package Vistas;

import Dao.DAOPrograma;
import Dao.DAOUsuario;
import Dao.DAOUsuarioPrograma;
import Modelos.Programa;
import Modelos.Usuario;
import Modelos.UsuarioPrograma;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author armando
 */
public class JFrmUsuarioPrograma extends javax.swing.JInternalFrame {

    UsuarioPrograma up = new UsuarioPrograma();
    Usuario u = new Usuario();
    Programa p = new Programa();

    DAOUsuarioPrograma dao = new DAOUsuarioPrograma();
    DAOUsuario daoUsuario = new DAOUsuario();
    DAOPrograma daoPrograma = new DAOPrograma();

    ArrayList<Object[]> datos = new ArrayList<>();
    ArrayList<Object[]> datosUsuario = new ArrayList<>();
    ArrayList<Object[]> datosPrograma = new ArrayList<>();

    //VARIABLE QUE MANEJA QUE TIPOS DE OPERACIONES SE REALIZARAN: SI VA A SER ALTA, BAJA O MODIFICACION DEL REGISTRO
    String operacion = "";

    /**
     * Creates new form JFrmUsuarioPrograma
     */
    public JFrmUsuarioPrograma() {
        setTitle("JFrmUsuarioPrograma");
        initComponents();
        //cargar();
    }

    public void cargar() {
        DefaultTableModel modelo = (DefaultTableModel) tablaDatos.getModel();
        modelo.setRowCount(0);
        int idusuario = 0;
        if (txtCodigoUsuarioCriterio.getText().isEmpty()) {
            idusuario = 0;
        } else {
            idusuario = Integer.parseInt(txtCodigoUsuarioCriterio.getText());
        }
        datos = dao.consultar(idusuario);
        for (Object[] obj : datos) {
            modelo.addRow(obj);
        }
        this.tablaDatos.setModel(modelo);
    }

    public void cargarUsuario() {
        DefaultTableModel modelo = (DefaultTableModel) tablaDatosUsuario.getModel();
        modelo.setRowCount(0);
        datosUsuario = daoUsuario.consultar(txtCriterioUsuario.getText());
        for (Object[] obj : datosUsuario) {
            modelo.addRow(obj);
        }
        this.tablaDatosUsuario.setModel(modelo);
    }

    public void cargarUsuarioDos() {
        DefaultTableModel modelo = (DefaultTableModel) tablaDatosUsuarioDos.getModel();
        modelo.setRowCount(0);
        datosUsuario = daoUsuario.consultar(txtCriterioUsuarioDos.getText());
        for (Object[] obj : datosUsuario) {
            modelo.addRow(obj);
        }
        this.tablaDatosUsuarioDos.setModel(modelo);
    }

    public void cargarPrograma() {
        DefaultTableModel modelo = (DefaultTableModel) tablaDatosPrograma.getModel();
        modelo.setRowCount(0);
        datosPrograma = daoPrograma.consultar(txtCriterioPrograma.getText());
        for (Object[] obj : datosPrograma) {
            modelo.addRow(obj);
        }
        this.tablaDatosPrograma.setModel(modelo);
    }

    public void habilitarCampos(String accion) {
        switch (accion) {
            case "NUEVO":
                //CAMPOS
                txtCodigoUsuario.setEnabled(true);
                txtCodigoPrograma.setEnabled(true);
                //BOTONES
                btnNuevo.setEnabled(false);
                btnConfirmar.setEnabled(true);
                btnCancelar.setEnabled(true);
                //REDIRECIONAMOS
                txtCodigoUsuario.grabFocus();
                break;
            case "MODIFICAR":
                //CAMPOS
                txtCodigoUsuario.setEnabled(true);
                txtCodigoPrograma.setEnabled(true);
                //BOTONES
                btnNuevo.setEnabled(false);
                btnConfirmar.setEnabled(true);
                btnCancelar.setEnabled(true);
                //REDIRECIONAMOS
                pestanha.setSelectedIndex(1);
                txtCodigoUsuario.grabFocus();
                break;
            case "ELIMINAR":
                //CAMPOS
                txtCodigoUsuario.setEnabled(false);
                txtCodigoPrograma.setEnabled(false);
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
                txtCodigoUsuario.setEnabled(false);
                txtCodigoPrograma.setEnabled(false);
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
                txtCodigoUsuario.setEnabled(false);
                txtCodigoPrograma.setEnabled(false);
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
        txtCodigoUsuario.setText(null);
        txtDescripcionUsuario.setText(null);
        txtCodigoPrograma.setText(null);
        txtDescripcionPrograma.setText(null);
        txtCodigoUsuarioCriterio.setText(null);
        txtDescripcionUsuarioCriterio.setText(null);
        operacion = "";
    }

    public void guardar(String accion) {
        //CAPTURA Y VALIDACIONES DE LOS DATOS RECIBIDOS
        String error = "";

        int idusuario = Integer.parseInt(txtCodigoUsuario.getText());
        int idprograma = Integer.parseInt(txtCodigoPrograma.getText());
        switch (accion) {
            case "NUEVO":
                if (idusuario == 0) {
                    error += "NO HA SELECCIONADO UN USUARIO.\n";
                }
                if (idprograma == 0) {
                    error += "NO HA SELECCIONADO UN PROGRAMA.\n";
                }
                if (error.isEmpty()) {
                    up.setIdusuario(idusuario);
                    up.setIdprograma(idprograma);
                    dao.agregar(up);
                    cargar();
                } else {
                    JOptionPane.showMessageDialog(null, error, "ERRORES", JOptionPane.ERROR_MESSAGE);
                }
                break;
            /*case "MODIFICAR":
                if (idusuario == 0) {
                    error += "NO HA SELECCIONADO UN USUARIO.\n";
                }
                if (idprograma == 0) {
                    error += "NO HA SELECCIONADO UN PROGRAMA.\n";
                }
                if (error.isEmpty()) {
                    up.setIdusuario(idusuario);
                    up.setIdprograma(idprograma);
                    dao.modificar(up);
                    cargar();
                } else {
                    JOptionPane.showMessageDialog(null, error, "ERRORES", JOptionPane.ERROR_MESSAGE);
                }
                break;*/
            case "ELIMINAR":
                if (error.isEmpty()) {
                    up.setIdusuario(idusuario);
                    up.setIdprograma(idprograma);
                    dao.eliminar(up);
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
            String idusuario = txtCodigoUsuarioCriterio.getText();
            String usuariodescripcion = txtDescripcionUsuarioCriterio.getText();
            String idprograma = tablaDatos.getValueAt(fila, 0).toString();
            String programadescripcion = tablaDatos.getValueAt(fila, 1).toString();
            txtCodigoUsuario.setText(idusuario);
            txtDescripcionUsuario.setText(usuariodescripcion);
            txtCodigoPrograma.setText(idprograma);
            txtDescripcionPrograma.setText(programadescripcion);
            habilitarCampos(operacion);
        } else {
            JOptionPane.showMessageDialog(null, "SELECCIONE UNA FILA", "ADVERTENCIA", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void buscarUsuario() {
        cargarUsuario();
        BuscadorUsuarioCriterio.setModal(true);
        BuscadorUsuarioCriterio.setSize(540, 285);
        BuscadorUsuarioCriterio.setLocationRelativeTo(this);
        BuscadorUsuarioCriterio.setVisible(true);
        int fila = tablaDatosUsuario.getSelectedRow();
        if (fila >= 0) {
            txtCodigoUsuarioCriterio.setText(tablaDatosUsuario.getValueAt(fila, 0).toString());
            txtDescripcionUsuarioCriterio.setText(tablaDatosUsuario.getValueAt(fila, 1).toString());
        } else {
            txtCodigoUsuarioCriterio.setText(null);
            txtDescripcionUsuarioCriterio.setText(null);
        }
    }

    private void buscarUsuarioDos() {
        cargarUsuarioDos();
        BuscadorUsuario.setModal(true);
        BuscadorUsuario.setSize(540, 285);
        BuscadorUsuario.setLocationRelativeTo(this);
        BuscadorUsuario.setVisible(true);
        int fila = tablaDatosUsuarioDos.getSelectedRow();
        if (fila >= 0) {
            txtCodigoUsuario.setText(tablaDatosUsuarioDos.getValueAt(fila, 0).toString());
            txtDescripcionUsuario.setText(tablaDatosUsuarioDos.getValueAt(fila, 1).toString()+" "+tablaDatosUsuarioDos.getValueAt(fila, 2).toString());
        } else {
            txtCodigoUsuario.setText(null);
            txtDescripcionUsuario.setText(null);
        }
    }

    private void buscarPrograma() {
        cargarPrograma();
        BuscadorPrograma.setModal(true);
        BuscadorPrograma.setSize(540, 285);
        BuscadorPrograma.setLocationRelativeTo(this);
        BuscadorPrograma.setVisible(true);
        int fila = tablaDatosPrograma.getSelectedRow();
        if (fila >= 0) {
            txtCodigoPrograma.setText(tablaDatosPrograma.getValueAt(fila, 0).toString());
            txtDescripcionPrograma.setText(tablaDatosPrograma.getValueAt(fila, 1).toString());
        } else {
            txtCodigoPrograma.setText(null);
            txtDescripcionPrograma.setText(null);
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
        BuscadorUsuarioCriterio = new javax.swing.JDialog();
        jPanel3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        txtCriterioUsuario = new org.jdesktop.swingx.JXTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaDatosUsuario = new javax.swing.JTable();
        BuscadorUsuario = new javax.swing.JDialog();
        jPanel4 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        txtCriterioUsuarioDos = new org.jdesktop.swingx.JXTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaDatosUsuarioDos = new javax.swing.JTable();
        BuscadorPrograma = new javax.swing.JDialog();
        jPanel5 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        txtCriterioPrograma = new org.jdesktop.swingx.JXTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        tablaDatosPrograma = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        pestanha = new javax.swing.JTabbedPane();
        pestanhaLista = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaDatos = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        txtCodigoUsuarioCriterio = new org.jdesktop.swingx.JXTextField();
        txtDescripcionUsuarioCriterio = new org.jdesktop.swingx.JXTextField();
        pestanhaABM = new javax.swing.JPanel();
        btnCancelar = new javax.swing.JButton();
        btnConfirmar = new javax.swing.JButton();
        btnNuevo = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        txtCodigoUsuario = new org.jdesktop.swingx.JXTextField();
        txtDescripcionUsuario = new org.jdesktop.swingx.JXTextField();
        jLabel7 = new javax.swing.JLabel();
        txtCodigoPrograma = new org.jdesktop.swingx.JXTextField();
        txtDescripcionPrograma = new org.jdesktop.swingx.JXTextField();

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
        jLabel5.setText("BUSCADOR DE USUARIOS");
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
                "<html><p style=\"text-align:center\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Código</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Nombre</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Apellido</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Cédula</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Teléfono</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Dirección</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">alias</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">idempresa</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">empresa</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">idsucursal</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">sucursal</span></span></span></p></html> "
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false
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
            tablaDatosUsuario.getColumnModel().getColumn(3).setMinWidth(0);
            tablaDatosUsuario.getColumnModel().getColumn(3).setPreferredWidth(0);
            tablaDatosUsuario.getColumnModel().getColumn(3).setMaxWidth(0);
            tablaDatosUsuario.getColumnModel().getColumn(4).setMinWidth(0);
            tablaDatosUsuario.getColumnModel().getColumn(4).setPreferredWidth(0);
            tablaDatosUsuario.getColumnModel().getColumn(4).setMaxWidth(0);
            tablaDatosUsuario.getColumnModel().getColumn(5).setMinWidth(0);
            tablaDatosUsuario.getColumnModel().getColumn(5).setPreferredWidth(0);
            tablaDatosUsuario.getColumnModel().getColumn(5).setMaxWidth(0);
            tablaDatosUsuario.getColumnModel().getColumn(7).setMinWidth(0);
            tablaDatosUsuario.getColumnModel().getColumn(7).setPreferredWidth(0);
            tablaDatosUsuario.getColumnModel().getColumn(7).setMaxWidth(0);
            tablaDatosUsuario.getColumnModel().getColumn(8).setMinWidth(0);
            tablaDatosUsuario.getColumnModel().getColumn(8).setPreferredWidth(0);
            tablaDatosUsuario.getColumnModel().getColumn(8).setMaxWidth(0);
            tablaDatosUsuario.getColumnModel().getColumn(9).setMinWidth(0);
            tablaDatosUsuario.getColumnModel().getColumn(9).setPreferredWidth(0);
            tablaDatosUsuario.getColumnModel().getColumn(9).setMaxWidth(0);
            tablaDatosUsuario.getColumnModel().getColumn(10).setMinWidth(0);
            tablaDatosUsuario.getColumnModel().getColumn(10).setPreferredWidth(0);
            tablaDatosUsuario.getColumnModel().getColumn(10).setMaxWidth(0);
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

        javax.swing.GroupLayout BuscadorUsuarioCriterioLayout = new javax.swing.GroupLayout(BuscadorUsuarioCriterio.getContentPane());
        BuscadorUsuarioCriterio.getContentPane().setLayout(BuscadorUsuarioCriterioLayout);
        BuscadorUsuarioCriterioLayout.setHorizontalGroup(
            BuscadorUsuarioCriterioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BuscadorUsuarioCriterioLayout.setVerticalGroup(
            BuscadorUsuarioCriterioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jLabel8.setBackground(new java.awt.Color(50, 104, 151));
        jLabel8.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("BUSCADOR DE USUARIOS");
        jLabel8.setOpaque(true);

        txtCriterioUsuarioDos.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtCriterioUsuarioDos.setPrompt("Aqui puede ingresar los filtros para la busqueda..");
        txtCriterioUsuarioDos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCriterioUsuarioDosActionPerformed(evt);
            }
        });
        txtCriterioUsuarioDos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCriterioUsuarioDosKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCriterioUsuarioDosKeyTyped(evt);
            }
        });

        tablaDatosUsuarioDos.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        tablaDatosUsuarioDos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "<html><p style=\"text-align:center\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Código</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Nombre</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Apellido</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Cédula</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Teléfono</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Dirección</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">alias</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">idempresa</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">empresa</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">idsucursal</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">sucursal</span></span></span></p></html> "
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaDatosUsuarioDos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaDatosUsuarioDosMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tablaDatosUsuarioDos);
        if (tablaDatosUsuarioDos.getColumnModel().getColumnCount() > 0) {
            tablaDatosUsuarioDos.getColumnModel().getColumn(0).setMinWidth(60);
            tablaDatosUsuarioDos.getColumnModel().getColumn(0).setPreferredWidth(60);
            tablaDatosUsuarioDos.getColumnModel().getColumn(0).setMaxWidth(60);
            tablaDatosUsuarioDos.getColumnModel().getColumn(3).setMinWidth(0);
            tablaDatosUsuarioDos.getColumnModel().getColumn(3).setPreferredWidth(0);
            tablaDatosUsuarioDos.getColumnModel().getColumn(3).setMaxWidth(0);
            tablaDatosUsuarioDos.getColumnModel().getColumn(4).setMinWidth(0);
            tablaDatosUsuarioDos.getColumnModel().getColumn(4).setPreferredWidth(0);
            tablaDatosUsuarioDos.getColumnModel().getColumn(4).setMaxWidth(0);
            tablaDatosUsuarioDos.getColumnModel().getColumn(5).setMinWidth(0);
            tablaDatosUsuarioDos.getColumnModel().getColumn(5).setPreferredWidth(0);
            tablaDatosUsuarioDos.getColumnModel().getColumn(5).setMaxWidth(0);
            tablaDatosUsuarioDos.getColumnModel().getColumn(7).setMinWidth(0);
            tablaDatosUsuarioDos.getColumnModel().getColumn(7).setPreferredWidth(0);
            tablaDatosUsuarioDos.getColumnModel().getColumn(7).setMaxWidth(0);
            tablaDatosUsuarioDos.getColumnModel().getColumn(8).setMinWidth(0);
            tablaDatosUsuarioDos.getColumnModel().getColumn(8).setPreferredWidth(0);
            tablaDatosUsuarioDos.getColumnModel().getColumn(8).setMaxWidth(0);
            tablaDatosUsuarioDos.getColumnModel().getColumn(9).setMinWidth(0);
            tablaDatosUsuarioDos.getColumnModel().getColumn(9).setPreferredWidth(0);
            tablaDatosUsuarioDos.getColumnModel().getColumn(9).setMaxWidth(0);
            tablaDatosUsuarioDos.getColumnModel().getColumn(10).setMinWidth(0);
            tablaDatosUsuarioDos.getColumnModel().getColumn(10).setPreferredWidth(0);
            tablaDatosUsuarioDos.getColumnModel().getColumn(10).setMaxWidth(0);
        }

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCriterioUsuarioDos, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCriterioUsuarioDos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout BuscadorUsuarioLayout = new javax.swing.GroupLayout(BuscadorUsuario.getContentPane());
        BuscadorUsuario.getContentPane().setLayout(BuscadorUsuarioLayout);
        BuscadorUsuarioLayout.setHorizontalGroup(
            BuscadorUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BuscadorUsuarioLayout.setVerticalGroup(
            BuscadorUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jLabel9.setBackground(new java.awt.Color(50, 104, 151));
        jLabel9.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("BUSCADOR DE PROGRAMAS");
        jLabel9.setOpaque(true);

        txtCriterioPrograma.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtCriterioPrograma.setPrompt("Aqui puede ingresar los filtros para la busqueda..");
        txtCriterioPrograma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCriterioProgramaActionPerformed(evt);
            }
        });
        txtCriterioPrograma.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCriterioProgramaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCriterioProgramaKeyTyped(evt);
            }
        });

        tablaDatosPrograma.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        tablaDatosPrograma.setModel(new javax.swing.table.DefaultTableModel(
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
        tablaDatosPrograma.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaDatosProgramaMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tablaDatosPrograma);
        if (tablaDatosPrograma.getColumnModel().getColumnCount() > 0) {
            tablaDatosPrograma.getColumnModel().getColumn(0).setMinWidth(60);
            tablaDatosPrograma.getColumnModel().getColumn(0).setPreferredWidth(60);
            tablaDatosPrograma.getColumnModel().getColumn(0).setMaxWidth(60);
        }

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCriterioPrograma, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCriterioPrograma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout BuscadorProgramaLayout = new javax.swing.GroupLayout(BuscadorPrograma.getContentPane());
        BuscadorPrograma.getContentPane().setLayout(BuscadorProgramaLayout);
        BuscadorProgramaLayout.setHorizontalGroup(
            BuscadorProgramaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BuscadorProgramaLayout.setVerticalGroup(
            BuscadorProgramaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setClosable(true);
        setIconifiable(true);

        jPanel1.setBackground(new java.awt.Color(50, 104, 151));

        jLabel1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Asignación de Programas a Usuarios");

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

        jLabel6.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel6.setText("Usuario:");

        txtCodigoUsuarioCriterio.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtCodigoUsuarioCriterio.setPrompt("Cód. Usuario");
        txtCodigoUsuarioCriterio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoUsuarioCriterioActionPerformed(evt);
            }
        });
        txtCodigoUsuarioCriterio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodigoUsuarioCriterioKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodigoUsuarioCriterioKeyTyped(evt);
            }
        });

        txtDescripcionUsuarioCriterio.setEnabled(false);
        txtDescripcionUsuarioCriterio.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtDescripcionUsuarioCriterio.setPrompt("Descripción o nombre del usuario...");

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
                        .addComponent(txtCodigoUsuarioCriterio, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDescripcionUsuarioCriterio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pestanhaListaLayout.setVerticalGroup(
            pestanhaListaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pestanhaListaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pestanhaListaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtCodigoUsuarioCriterio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDescripcionUsuarioCriterio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
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
        jLabel4.setText("Usuario:");

        txtCodigoUsuario.setEnabled(false);
        txtCodigoUsuario.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtCodigoUsuario.setPrompt("Cód. Usu.");
        txtCodigoUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoUsuarioActionPerformed(evt);
            }
        });
        txtCodigoUsuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodigoUsuarioKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodigoUsuarioKeyTyped(evt);
            }
        });

        txtDescripcionUsuario.setEnabled(false);
        txtDescripcionUsuario.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtDescripcionUsuario.setPrompt("Descripción o nombre del usuario...");

        jLabel7.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel7.setText("Programa:");

        txtCodigoPrograma.setEnabled(false);
        txtCodigoPrograma.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtCodigoPrograma.setPrompt("Cód. Pro.");
        txtCodigoPrograma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoProgramaActionPerformed(evt);
            }
        });
        txtCodigoPrograma.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodigoProgramaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodigoProgramaKeyTyped(evt);
            }
        });

        txtDescripcionPrograma.setEnabled(false);
        txtDescripcionPrograma.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtDescripcionPrograma.setPrompt("Descripción o nombre del programa...");

        javax.swing.GroupLayout pestanhaABMLayout = new javax.swing.GroupLayout(pestanhaABM);
        pestanhaABM.setLayout(pestanhaABMLayout);
        pestanhaABMLayout.setHorizontalGroup(
            pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pestanhaABMLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pestanhaABMLayout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 72, Short.MAX_VALUE)
                        .addGap(18, 18, Short.MAX_VALUE)
                        .addGroup(pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pestanhaABMLayout.createSequentialGroup()
                                .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnConfirmar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pestanhaABMLayout.createSequentialGroup()
                                .addComponent(txtCodigoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtDescripcionUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 405, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(pestanhaABMLayout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 72, Short.MAX_VALUE)
                        .addGap(18, 18, Short.MAX_VALUE)
                        .addComponent(txtCodigoPrograma, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDescripcionPrograma, javax.swing.GroupLayout.PREFERRED_SIZE, 405, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        pestanhaABMLayout.setVerticalGroup(
            pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pestanhaABMLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtCodigoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDescripcionUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtCodigoPrograma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDescripcionPrograma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 215, Short.MAX_VALUE)
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

    private void txtCodigoUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoUsuarioActionPerformed
        if (txtCodigoUsuario.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "NO PUEDE DEJAR EL CAMPO DE USUARIO VACIO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else {
            int idusuario = Integer.parseInt(txtCodigoUsuario.getText());
            u.setIdusuario(idusuario);
            boolean resultado = daoUsuario.consultarDatos(u);
            if (resultado == true) {
                txtDescripcionUsuario.setText(u.getNombre() + " " + u.getApellido());
                txtCodigoPrograma.grabFocus();
            } else {
                txtCodigoUsuario.setText(null);
                txtDescripcionUsuario.setText(null);
                txtCodigoUsuario.grabFocus();
            }
        }
    }//GEN-LAST:event_txtCodigoUsuarioActionPerformed

    private void txtCodigoUsuarioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoUsuarioKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            getToolkit().beep();
            evt.consume();
        }
        if (txtCodigoUsuario.getText().length() == 10) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCodigoUsuarioKeyTyped

    private void txtCriterioUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCriterioUsuarioActionPerformed
        cargarUsuario();
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
            txtCodigoUsuario.setText(null);
            txtDescripcionUsuario.setText(null);
            txtCodigoUsuario.grabFocus();
            BuscadorUsuarioCriterio.dispose();
        }
    }//GEN-LAST:event_txtCriterioUsuarioKeyPressed

    private void tablaDatosUsuarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaDatosUsuarioMouseClicked
        if (evt.getClickCount() == 2) {
            if (tablaDatosUsuario.getSelectedRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "SELECCIONE UNA FILA");
            } else {
                txtCriterioUsuario.setText(null);
                BuscadorUsuarioCriterio.dispose();
            }
        }
    }//GEN-LAST:event_tablaDatosUsuarioMouseClicked

    private void txtCodigoUsuarioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoUsuarioKeyPressed
        if (evt.VK_F1 == evt.getKeyCode()) {
            buscarUsuarioDos();
        }
    }//GEN-LAST:event_txtCodigoUsuarioKeyPressed

    private void txtCodigoUsuarioCriterioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoUsuarioCriterioActionPerformed
        if (txtCodigoUsuarioCriterio.getText().isEmpty()) {
            txtCodigoUsuarioCriterio.setText(null);
            txtDescripcionUsuarioCriterio.setText(null);
            txtCodigoUsuarioCriterio.grabFocus();
        } else {
            int idusuario = Integer.parseInt(txtCodigoUsuarioCriterio.getText());
            u.setIdusuario(idusuario);
            boolean resultado = daoUsuario.consultarDatos(u);
            if (resultado == true) {
                txtDescripcionUsuarioCriterio.setText(u.getNombre() + " " + u.getApellido());
                cargar();
            } else {
                txtCodigoUsuarioCriterio.setText(null);
                txtDescripcionUsuarioCriterio.setText(null);
                txtCodigoUsuarioCriterio.grabFocus();
                cargar();
            }
        }
    }//GEN-LAST:event_txtCodigoUsuarioCriterioActionPerformed

    private void txtCodigoUsuarioCriterioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoUsuarioCriterioKeyPressed
        if (evt.VK_F1 == evt.getKeyCode()) {
            buscarUsuario();
        }
    }//GEN-LAST:event_txtCodigoUsuarioCriterioKeyPressed

    private void txtCodigoUsuarioCriterioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoUsuarioCriterioKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            getToolkit().beep();
            evt.consume();
        }
        if (txtCodigoUsuarioCriterio.getText().length() == 10) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCodigoUsuarioCriterioKeyTyped

    private void txtCodigoProgramaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoProgramaActionPerformed
        if (txtCodigoPrograma.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "NO PUEDE DEJAR EL CAMPO DE PROGRAMA VACIO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else {
            int idprograma = Integer.parseInt(txtCodigoPrograma.getText());
            p.setIdprograma(idprograma);
            boolean resultado = daoPrograma.consultarDatos(p);
            if (resultado == true) {
                txtDescripcionPrograma.setText(p.getDescripcion());
                up.setIdusuario(Integer.parseInt(txtCodigoUsuario.getText()));
                up.setIdprograma(idprograma);
                boolean existe = dao.consultarDatos(up);
                if (existe == true) {
                    JOptionPane.showMessageDialog(null, "ESTE PROGRAMA YA ESTA ASIGNADO AL USUARIO SELECCIONADO", "ADVERTENCIA", JOptionPane.ERROR_MESSAGE);
                } else {
                    btnConfirmar.grabFocus();
                }
            } else {
                txtCodigoPrograma.setText(null);
                txtDescripcionPrograma.setText(null);
                txtCodigoPrograma.grabFocus();
            }
        }
    }//GEN-LAST:event_txtCodigoProgramaActionPerformed

    private void txtCodigoProgramaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoProgramaKeyPressed
        if (evt.VK_F1 == evt.getKeyCode()) {
            buscarPrograma();
        }
    }//GEN-LAST:event_txtCodigoProgramaKeyPressed

    private void txtCodigoProgramaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoProgramaKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            getToolkit().beep();
            evt.consume();
        }
        if (txtCodigoPrograma.getText().length() == 10) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCodigoProgramaKeyTyped

    private void txtCriterioUsuarioDosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCriterioUsuarioDosActionPerformed
        cargarUsuarioDos();
    }//GEN-LAST:event_txtCriterioUsuarioDosActionPerformed

    private void txtCriterioUsuarioDosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCriterioUsuarioDosKeyPressed
        if (evt.VK_ESCAPE == evt.getKeyCode()) {
            txtCodigoUsuario.setText(null);
            txtDescripcionUsuario.setText(null);
            txtCodigoUsuario.grabFocus();
            BuscadorUsuario.dispose();
        }
    }//GEN-LAST:event_txtCriterioUsuarioDosKeyPressed

    private void txtCriterioUsuarioDosKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCriterioUsuarioDosKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            evt.setKeyChar(Character.toUpperCase(c));
        }
        if (txtCriterioUsuarioDos.getText().length() == 100) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCriterioUsuarioDosKeyTyped

    private void tablaDatosUsuarioDosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaDatosUsuarioDosMouseClicked
        if (evt.getClickCount() == 2) {
            if (tablaDatosUsuarioDos.getSelectedRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "SELECCIONE UNA FILA");
            } else {
                txtCriterioUsuarioDos.setText(null);
                BuscadorUsuario.dispose();
            }
        }
    }//GEN-LAST:event_tablaDatosUsuarioDosMouseClicked

    private void txtCriterioProgramaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCriterioProgramaActionPerformed
        cargarPrograma();
    }//GEN-LAST:event_txtCriterioProgramaActionPerformed

    private void txtCriterioProgramaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCriterioProgramaKeyPressed
        if (evt.VK_ESCAPE == evt.getKeyCode()) {
            txtCodigoPrograma.setText(null);
            txtDescripcionPrograma.setText(null);
            txtCodigoPrograma.grabFocus();
            BuscadorPrograma.dispose();
        }
    }//GEN-LAST:event_txtCriterioProgramaKeyPressed

    private void txtCriterioProgramaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCriterioProgramaKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            evt.setKeyChar(Character.toUpperCase(c));
        }
        if (txtCriterioPrograma.getText().length() == 100) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCriterioProgramaKeyTyped

    private void tablaDatosProgramaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaDatosProgramaMouseClicked
        if (evt.getClickCount() == 2) {
            if (tablaDatosPrograma.getSelectedRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "SELECCIONE UNA FILA");
            } else {
                txtCriterioPrograma.setText(null);
                BuscadorPrograma.dispose();
            }
        }
    }//GEN-LAST:event_tablaDatosProgramaMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDialog BuscadorPrograma;
    private javax.swing.JDialog BuscadorUsuario;
    private javax.swing.JDialog BuscadorUsuarioCriterio;
    private javax.swing.JMenuItem Eliminar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnConfirmar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JLabel jLabel1;
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
    private javax.swing.JTable tablaDatosPrograma;
    private javax.swing.JTable tablaDatosUsuario;
    private javax.swing.JTable tablaDatosUsuarioDos;
    private org.jdesktop.swingx.JXTextField txtCodigoPrograma;
    private org.jdesktop.swingx.JXTextField txtCodigoUsuario;
    private org.jdesktop.swingx.JXTextField txtCodigoUsuarioCriterio;
    private org.jdesktop.swingx.JXTextField txtCriterioPrograma;
    private org.jdesktop.swingx.JXTextField txtCriterioUsuario;
    private org.jdesktop.swingx.JXTextField txtCriterioUsuarioDos;
    private org.jdesktop.swingx.JXTextField txtDescripcionPrograma;
    private org.jdesktop.swingx.JXTextField txtDescripcionUsuario;
    private org.jdesktop.swingx.JXTextField txtDescripcionUsuarioCriterio;
    // End of variables declaration//GEN-END:variables
}
