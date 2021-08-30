package Vistas;

import Dao.DAOEmpresa;
import Dao.DAOSucursal;
import Dao.DAOUsuario;
import Modelos.Empresa;
import Modelos.Sucursal;
import Modelos.Usuario;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author armando
 */
public class JFrmUsuario extends javax.swing.JInternalFrame {

    Usuario u = new Usuario();
    Empresa e = new Empresa();
    Sucursal s = new Sucursal();

    DAOUsuario dao = new DAOUsuario();
    DAOEmpresa daoEmpresa = new DAOEmpresa();
    DAOSucursal daoSucursal = new DAOSucursal();

    ArrayList<Object[]> datos = new ArrayList<>();
    ArrayList<Object[]> datosEmpresa = new ArrayList<>();
    ArrayList<Object[]> datosSucursal = new ArrayList<>();

    //VARIABLE QUE MANEJA QUE TIPOS DE OPERACIONES SE REALIZARAN: SI VA A SER ALTA, BAJA O MODIFICACION DEL REGISTRO
    String operacion = "";

    /**
     * Creates new form JFrmUsuario
     */
    public JFrmUsuario() {
        setTitle("JFrmUsuario");
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

    public void cargarEmpresa() {
        DefaultTableModel modelo = (DefaultTableModel) tablaDatosEmpresa.getModel();
        modelo.setRowCount(0);
        datosEmpresa = daoEmpresa.consultar(txtCriterioEmpresa.getText());
        for (Object[] obj : datosEmpresa) {
            modelo.addRow(obj);
        }
        this.tablaDatosEmpresa.setModel(modelo);
    }

    public void cargarSucursal() {
        DefaultTableModel modelo = (DefaultTableModel) tablaDatosSucursal.getModel();
        modelo.setRowCount(0);
        datosSucursal = daoSucursal.consultar(txtCriterioSucursal.getText());
        for (Object[] obj : datosSucursal) {
            modelo.addRow(obj);
        }
        this.tablaDatosSucursal.setModel(modelo);
    }

    public void habilitarCampos(String accion) {
        switch (accion) {
            case "NUEVO":
                //CAMPOS
                txtCodigo.setEnabled(false);
                txtNombre.setEnabled(true);
                txtApellido.setEnabled(true);
                txtCedula.setEnabled(true);
                txtTelefono.setEnabled(true);
                txtDireccion.setEnabled(true);
                txtAlias.setEnabled(true);
                txtClave.setEnabled(true);
                txtRepetirClave.setEnabled(true);
                txtCodigoEmpresa.setEnabled(true);
                txtCodigoSucursal.setEnabled(true);
                //BOTONES
                btnNuevo.setEnabled(false);
                btnConfirmar.setEnabled(true);
                btnCancelar.setEnabled(true);
                //REDIRECIONAMOS
                txtNombre.grabFocus();
                break;
            case "MODIFICAR":
                //CAMPOS
                txtCodigo.setEnabled(false);
                txtNombre.setEnabled(true);
                txtApellido.setEnabled(true);
                txtCedula.setEnabled(true);
                txtTelefono.setEnabled(true);
                txtDireccion.setEnabled(true);
                txtAlias.setEnabled(true);
                txtClave.setEnabled(true);
                txtRepetirClave.setEnabled(true);
                txtCodigoEmpresa.setEnabled(true);
                txtCodigoSucursal.setEnabled(true);
                //BOTONES
                btnNuevo.setEnabled(false);
                btnConfirmar.setEnabled(true);
                btnCancelar.setEnabled(true);
                //REDIRECIONAMOS
                pestanha.setSelectedIndex(1);
                txtNombre.grabFocus();
                break;
            case "ELIMINAR":
                //CAMPOS
                txtCodigo.setEnabled(false);
                txtNombre.setEnabled(false);
                txtApellido.setEnabled(false);
                txtCedula.setEnabled(false);
                txtTelefono.setEnabled(false);
                txtDireccion.setEnabled(false);
                txtAlias.setEnabled(false);
                txtClave.setEnabled(false);
                txtRepetirClave.setEnabled(false);
                txtCodigoEmpresa.setEnabled(false);
                txtCodigoSucursal.setEnabled(false);
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
                txtNombre.setEnabled(false);
                txtApellido.setEnabled(false);
                txtCedula.setEnabled(false);
                txtTelefono.setEnabled(false);
                txtDireccion.setEnabled(false);
                txtAlias.setEnabled(false);
                txtClave.setEnabled(false);
                txtRepetirClave.setEnabled(false);
                txtCodigoEmpresa.setEnabled(false);
                txtCodigoSucursal.setEnabled(false);
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
                txtNombre.setEnabled(false);
                txtApellido.setEnabled(false);
                txtCedula.setEnabled(false);
                txtTelefono.setEnabled(false);
                txtDireccion.setEnabled(false);
                txtAlias.setEnabled(false);
                txtClave.setEnabled(false);
                txtRepetirClave.setEnabled(false);
                txtCodigoEmpresa.setEnabled(false);
                txtCodigoSucursal.setEnabled(false);
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
        txtNombre.setText(null);
        txtApellido.setText(null);
        txtCedula.setText(null);
        txtTelefono.setText(null);
        txtDireccion.setText(null);
        txtAlias.setText(null);
        txtClave.setText(null);
        txtRepetirClave.setText(null);
        txtCodigoEmpresa.setText(null);
        txtDescripcionEmpresa.setText(null);
        txtCodigoSucursal.setText(null);
        txtDescripcionSucursal.setText(null);
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
        String nombre = txtNombre.getText();
        String apellido = txtApellido.getText();
        String cedula = txtCedula.getText();
        String telefono = txtTelefono.getText();
        String direccion = txtDireccion.getText();
        String alias = txtAlias.getText();
        String clave;
        String repetirClave;
        int idempresa = Integer.parseInt(txtCodigoEmpresa.getText());
        int idsucursal = Integer.parseInt(txtCodigoSucursal.getText());
        switch (accion) {
            case "NUEVO":
                clave = new String(txtClave.getPassword());
                repetirClave = new String(txtRepetirClave.getPassword());

                if (nombre.isEmpty()) {
                    error += "NO PUEDE DEJAR EL CAMPO DE NOMBRE VACIO.\n";
                }
                if (apellido.isEmpty()) {
                    error += "NO PUEDE DEJAR EL CAMPO DE APELLIDO VACIO.\n";
                }
                if (cedula.isEmpty()) {
                    error += "NO PUEDE DEJAR EL CAMPO DE CÉDULA VACIO.\n";
                }
                if (idempresa == 0) {
                    error += "NO HA SELECCIONADO UNA EMPRESA.\n";
                }
                if (idsucursal == 0) {
                    error += "NO HA SELECCIONADO UNA SUCURSAL.\n";
                }
                if (alias.isEmpty()) {
                    error += "NO PUEDE DEJAR EL CAMPO DE ALIAS VACIO.\n";
                }
                if (clave.isEmpty()) {
                    error += "NO PUEDE DEJAR EL CAMPO DE LA CONTRASEÑA VACIA.\n";
                } else {
                    if (!repetirClave.equals(clave)) {
                        error += "LAS CLAVES INGRESADAS NO COINCIDEN. VUELVA A VERIFICAR.\n";
                    }
                }
                if (error.isEmpty()) {
                    String claveCifrado;
                    String texto = clave;
                    claveCifrado = DigestUtils.md5Hex(texto);
                    u.setIdusuario(id);
                    u.setNombre(nombre);
                    u.setApellido(apellido);
                    u.setCedula(cedula);
                    u.setTelefono(telefono);
                    u.setDireccion(direccion);
                    u.setAlias(alias);
                    u.setClave(claveCifrado);
                    u.setIdempresa(idempresa);
                    u.setIdsucursal(idsucursal);
                    dao.agregar(u);
                    cargar();
                } else {
                    JOptionPane.showMessageDialog(null, error, "ERRORES", JOptionPane.ERROR_MESSAGE);
                }
                break;
            case "MODIFICAR":
                clave = new String(txtClave.getPassword());
                repetirClave = new String(txtRepetirClave.getPassword());

                if (nombre.isEmpty()) {
                    error += "NO PUEDE DEJAR EL CAMPO DE NOMBRE VACIO.\n";
                }
                if (apellido.isEmpty()) {
                    error += "NO PUEDE DEJAR EL CAMPO DE APELLIDO VACIO.\n";
                }
                if (cedula.isEmpty()) {
                    error += "NO PUEDE DEJAR EL CAMPO DE CÉDULA VACIO.\n";
                }
                if (idempresa == 0) {
                    error += "NO HA SELECCIONADO UNA EMPRESA.\n";
                }
                if (idsucursal == 0) {
                    error += "NO HA SELECCIONADO UNA SUCURSAL.\n";
                }
                if (alias.isEmpty()) {
                    error += "NO PUEDE DEJAR EL CAMPO DE ALIAS VACIO.\n";
                }
                if (clave.isEmpty()) {
                    error += "NO PUEDE DEJAR EL CAMPO DE LA CONTRASEÑA VACIA.\n";
                } else {
                    if (!repetirClave.equals(clave)) {
                        error += "LAS CLAVES INGRESADAS NO COINCIDEN. VUELVA A VERIFICAR.\n";
                    }
                }
                if (error.isEmpty()) {
                    String claveCifrado;
                    String texto = clave;
                    claveCifrado = DigestUtils.md5Hex(texto);

                    u.setIdusuario(id);
                    u.setNombre(nombre);
                    u.setApellido(apellido);
                    u.setCedula(cedula);
                    u.setTelefono(telefono);
                    u.setDireccion(direccion);
                    u.setAlias(alias);
                    u.setClave(claveCifrado);
                    u.setIdempresa(idempresa);
                    u.setIdsucursal(idsucursal);
                    dao.modificar(u);
                    cargar();
                } else {
                    JOptionPane.showMessageDialog(null, error, "ERRORES", JOptionPane.ERROR_MESSAGE);
                }
                break;
            case "ELIMINAR":
                if (error.isEmpty()) {
                    u.setIdusuario(id);
                    dao.eliminar(u);
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
            u.setIdusuario(id);
            dao.consultarDatos(u);
            txtCodigo.setText("" + u.getIdusuario());
            txtNombre.setText(u.getNombre());
            txtApellido.setText(u.getApellido());
            txtCedula.setText(u.getCedula());
            txtTelefono.setText(u.getTelefono());
            txtDireccion.setText(u.getDireccion());
            txtAlias.setText(u.getAlias());

            int idempresa = u.getIdempresa();
            e.setIdempresa(idempresa);
            daoEmpresa.consultarDatos(e);
            txtCodigoEmpresa.setText("" + e.getIdempresa());
            txtDescripcionEmpresa.setText(e.getRazonsocial());

            int idsucursal = u.getIdsucursal();
            s.setIdsucursal(idsucursal);
            daoSucursal.consultarDatos(s);
            txtCodigoSucursal.setText("" + s.getIdsucursal());
            txtDescripcionSucursal.setText(s.getDescripcion());
            habilitarCampos(operacion);
        } else {
            JOptionPane.showMessageDialog(null, "SELECCIONE UNA FILA", "ADVERTENCIA", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void buscarEmpresa() {
        cargarEmpresa();
        BuscadorEmpresa.setModal(true);
        BuscadorEmpresa.setSize(540, 285);
        BuscadorEmpresa.setLocationRelativeTo(this);
        BuscadorEmpresa.setVisible(true);
        int fila = tablaDatosEmpresa.getSelectedRow();
        if (fila >= 0) {
            txtCodigoEmpresa.setText(tablaDatosEmpresa.getValueAt(fila, 0).toString());
            txtDescripcionEmpresa.setText(tablaDatosEmpresa.getValueAt(fila, 1).toString());
        } else {
            txtCodigoEmpresa.setText(null);
            txtDescripcionEmpresa.setText(null);
        }
    }

    private void buscarSucursal() {
        cargarSucursal();
        BuscadorSucursal.setModal(true);
        BuscadorSucursal.setSize(540, 285);
        BuscadorSucursal.setLocationRelativeTo(this);
        BuscadorSucursal.setVisible(true);
        int fila = tablaDatosSucursal.getSelectedRow();
        if (fila >= 0) {
            txtCodigoSucursal.setText(tablaDatosSucursal.getValueAt(fila, 0).toString());
            txtDescripcionSucursal.setText(tablaDatosSucursal.getValueAt(fila, 1).toString());
        } else {
            txtCodigoSucursal.setText(null);
            txtDescripcionSucursal.setText(null);
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
        BuscadorEmpresa = new javax.swing.JDialog();
        jPanel3 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        txtCriterioEmpresa = new org.jdesktop.swingx.JXTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaDatosEmpresa = new javax.swing.JTable();
        BuscadorSucursal = new javax.swing.JDialog();
        jPanel4 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        txtCriterioSucursal = new org.jdesktop.swingx.JXTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaDatosSucursal = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        pestanha = new javax.swing.JTabbedPane();
        pestanhaLista = new javax.swing.JPanel();
        txtCriterio = new org.jdesktop.swingx.JXTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaDatos = new javax.swing.JTable();
        pestanhaABM = new javax.swing.JPanel();
        txtNombre = new org.jdesktop.swingx.JXTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtCodigo = new org.jdesktop.swingx.JXTextField();
        btnCancelar = new javax.swing.JButton();
        btnConfirmar = new javax.swing.JButton();
        btnNuevo = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        txtTelefono = new org.jdesktop.swingx.JXTextField();
        jLabel5 = new javax.swing.JLabel();
        txtCedula = new org.jdesktop.swingx.JXTextField();
        jLabel6 = new javax.swing.JLabel();
        txtApellido = new org.jdesktop.swingx.JXTextField();
        jLabel7 = new javax.swing.JLabel();
        txtDireccion = new org.jdesktop.swingx.JXTextField();
        jLabel8 = new javax.swing.JLabel();
        txtAlias = new org.jdesktop.swingx.JXTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtRepetirClave = new RSMaterialComponent.RSPasswordMaterial();
        txtClave = new RSMaterialComponent.RSPasswordMaterial();
        jLabel11 = new javax.swing.JLabel();
        txtCodigoEmpresa = new org.jdesktop.swingx.JXTextField();
        txtDescripcionEmpresa = new org.jdesktop.swingx.JXTextField();
        jLabel12 = new javax.swing.JLabel();
        txtCodigoSucursal = new org.jdesktop.swingx.JXTextField();
        txtDescripcionSucursal = new org.jdesktop.swingx.JXTextField();

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

        jLabel13.setBackground(new java.awt.Color(50, 104, 151));
        jLabel13.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("BUSCADOR DE EMPRESAS");
        jLabel13.setOpaque(true);

        txtCriterioEmpresa.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtCriterioEmpresa.setPrompt("Aqui puede ingresar los filtros para la busqueda..");
        txtCriterioEmpresa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCriterioEmpresaActionPerformed(evt);
            }
        });
        txtCriterioEmpresa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCriterioEmpresaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCriterioEmpresaKeyTyped(evt);
            }
        });

        tablaDatosEmpresa.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        tablaDatosEmpresa.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "<html><p style=\"text-align:center\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Código</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Razón Social</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Ruc</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Telefono</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Direccion</span></span></span></p></html> "
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaDatosEmpresa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaDatosEmpresaMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tablaDatosEmpresa);
        if (tablaDatosEmpresa.getColumnModel().getColumnCount() > 0) {
            tablaDatosEmpresa.getColumnModel().getColumn(0).setMinWidth(60);
            tablaDatosEmpresa.getColumnModel().getColumn(0).setPreferredWidth(60);
            tablaDatosEmpresa.getColumnModel().getColumn(0).setMaxWidth(60);
            tablaDatosEmpresa.getColumnModel().getColumn(2).setMinWidth(0);
            tablaDatosEmpresa.getColumnModel().getColumn(2).setPreferredWidth(0);
            tablaDatosEmpresa.getColumnModel().getColumn(2).setMaxWidth(0);
            tablaDatosEmpresa.getColumnModel().getColumn(3).setMinWidth(0);
            tablaDatosEmpresa.getColumnModel().getColumn(3).setPreferredWidth(0);
            tablaDatosEmpresa.getColumnModel().getColumn(3).setMaxWidth(0);
            tablaDatosEmpresa.getColumnModel().getColumn(4).setMinWidth(0);
            tablaDatosEmpresa.getColumnModel().getColumn(4).setPreferredWidth(0);
            tablaDatosEmpresa.getColumnModel().getColumn(4).setMaxWidth(0);
        }

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCriterioEmpresa, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCriterioEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout BuscadorEmpresaLayout = new javax.swing.GroupLayout(BuscadorEmpresa.getContentPane());
        BuscadorEmpresa.getContentPane().setLayout(BuscadorEmpresaLayout);
        BuscadorEmpresaLayout.setHorizontalGroup(
            BuscadorEmpresaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BuscadorEmpresaLayout.setVerticalGroup(
            BuscadorEmpresaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jLabel14.setBackground(new java.awt.Color(50, 104, 151));
        jLabel14.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("BUSCADOR DE SUCURSALES");
        jLabel14.setOpaque(true);

        txtCriterioSucursal.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtCriterioSucursal.setPrompt("Aqui puede ingresar los filtros para la busqueda..");
        txtCriterioSucursal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCriterioSucursalActionPerformed(evt);
            }
        });
        txtCriterioSucursal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCriterioSucursalKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCriterioSucursalKeyTyped(evt);
            }
        });

        tablaDatosSucursal.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        tablaDatosSucursal.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "<html><p style=\"text-align:center\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Código</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Descripción</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Telefono</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Direccion</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Codigo Empresa</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Empresa</span></span></span></p></html> "
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaDatosSucursal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaDatosSucursalMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tablaDatosSucursal);
        if (tablaDatosSucursal.getColumnModel().getColumnCount() > 0) {
            tablaDatosSucursal.getColumnModel().getColumn(0).setMinWidth(60);
            tablaDatosSucursal.getColumnModel().getColumn(0).setPreferredWidth(60);
            tablaDatosSucursal.getColumnModel().getColumn(0).setMaxWidth(60);
            tablaDatosSucursal.getColumnModel().getColumn(2).setMinWidth(0);
            tablaDatosSucursal.getColumnModel().getColumn(2).setPreferredWidth(0);
            tablaDatosSucursal.getColumnModel().getColumn(2).setMaxWidth(0);
            tablaDatosSucursal.getColumnModel().getColumn(3).setMinWidth(0);
            tablaDatosSucursal.getColumnModel().getColumn(3).setPreferredWidth(0);
            tablaDatosSucursal.getColumnModel().getColumn(3).setMaxWidth(0);
            tablaDatosSucursal.getColumnModel().getColumn(4).setMinWidth(0);
            tablaDatosSucursal.getColumnModel().getColumn(4).setPreferredWidth(0);
            tablaDatosSucursal.getColumnModel().getColumn(4).setMaxWidth(0);
            tablaDatosSucursal.getColumnModel().getColumn(5).setMinWidth(0);
            tablaDatosSucursal.getColumnModel().getColumn(5).setPreferredWidth(0);
            tablaDatosSucursal.getColumnModel().getColumn(5).setMaxWidth(0);
        }

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCriterioSucursal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCriterioSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout BuscadorSucursalLayout = new javax.swing.GroupLayout(BuscadorSucursal.getContentPane());
        BuscadorSucursal.getContentPane().setLayout(BuscadorSucursalLayout);
        BuscadorSucursalLayout.setHorizontalGroup(
            BuscadorSucursalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BuscadorSucursalLayout.setVerticalGroup(
            BuscadorSucursalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setClosable(true);
        setIconifiable(true);

        jPanel1.setBackground(new java.awt.Color(50, 104, 151));

        jLabel1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Mantenimiento de Usuarios");

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
                "<html><p style=\"text-align:center\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Código</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Nombre</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Apellido</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Cédula</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Teléfono</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Dirección</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Alias</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Código Empresa</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Empresa</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Código Sucursal</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Sucursal</span></span></span></p></html> "
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
        tablaDatos.setComponentPopupMenu(menuDesplegable);
        jScrollPane1.setViewportView(tablaDatos);
        if (tablaDatos.getColumnModel().getColumnCount() > 0) {
            tablaDatos.getColumnModel().getColumn(0).setMinWidth(60);
            tablaDatos.getColumnModel().getColumn(0).setPreferredWidth(60);
            tablaDatos.getColumnModel().getColumn(0).setMaxWidth(60);
            tablaDatos.getColumnModel().getColumn(5).setMinWidth(0);
            tablaDatos.getColumnModel().getColumn(5).setPreferredWidth(0);
            tablaDatos.getColumnModel().getColumn(5).setMaxWidth(0);
            tablaDatos.getColumnModel().getColumn(7).setMinWidth(0);
            tablaDatos.getColumnModel().getColumn(7).setPreferredWidth(0);
            tablaDatos.getColumnModel().getColumn(7).setMaxWidth(0);
            tablaDatos.getColumnModel().getColumn(8).setMinWidth(0);
            tablaDatos.getColumnModel().getColumn(8).setPreferredWidth(0);
            tablaDatos.getColumnModel().getColumn(8).setMaxWidth(0);
            tablaDatos.getColumnModel().getColumn(9).setMinWidth(0);
            tablaDatos.getColumnModel().getColumn(9).setPreferredWidth(0);
            tablaDatos.getColumnModel().getColumn(9).setMaxWidth(0);
            tablaDatos.getColumnModel().getColumn(10).setMinWidth(0);
            tablaDatos.getColumnModel().getColumn(10).setPreferredWidth(0);
            tablaDatos.getColumnModel().getColumn(10).setMaxWidth(0);
        }

        javax.swing.GroupLayout pestanhaListaLayout = new javax.swing.GroupLayout(pestanhaLista);
        pestanhaLista.setLayout(pestanhaListaLayout);
        pestanhaListaLayout.setHorizontalGroup(
            pestanhaListaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pestanhaListaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pestanhaListaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCriterio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 842, Short.MAX_VALUE))
                .addContainerGap())
        );
        pestanhaListaLayout.setVerticalGroup(
            pestanhaListaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pestanhaListaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtCriterio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE)
                .addContainerGap())
        );

        pestanha.addTab("Listado", pestanhaLista);

        pestanhaABM.setBackground(new java.awt.Color(255, 255, 255));

        txtNombre.setEnabled(false);
        txtNombre.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtNombre.setPrompt("Nombre del usuario...");
        txtNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreActionPerformed(evt);
            }
        });
        txtNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreKeyTyped(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel2.setText("Código:");

        jLabel3.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel3.setText("Nombre:");

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
        jLabel4.setText("Apellido:");

        txtTelefono.setEnabled(false);
        txtTelefono.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtTelefono.setPrompt("Teléfono del usuario...");
        txtTelefono.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTelefonoActionPerformed(evt);
            }
        });
        txtTelefono.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTelefonoKeyTyped(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel5.setText("Cédula:");

        txtCedula.setEnabled(false);
        txtCedula.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtCedula.setPrompt("Cédula de idendidad del usuario...");
        txtCedula.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCedulaActionPerformed(evt);
            }
        });
        txtCedula.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCedulaKeyTyped(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel6.setText("Teléfono:");

        txtApellido.setEnabled(false);
        txtApellido.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtApellido.setPrompt("Apellido del usuario...");
        txtApellido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtApellidoActionPerformed(evt);
            }
        });
        txtApellido.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtApellidoKeyTyped(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel7.setText("Dirección:");

        txtDireccion.setEnabled(false);
        txtDireccion.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtDireccion.setPrompt("Dirección del usuario...");
        txtDireccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDireccionActionPerformed(evt);
            }
        });
        txtDireccion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDireccionKeyTyped(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel8.setText("Alias:");

        txtAlias.setEnabled(false);
        txtAlias.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtAlias.setPrompt("Alias del usuario...");
        txtAlias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAliasActionPerformed(evt);
            }
        });
        txtAlias.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAliasKeyTyped(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel9.setText("Clave:");

        jLabel10.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel10.setText("Repetir:");

        txtRepetirClave.setForeground(new java.awt.Color(0, 0, 0));
        txtRepetirClave.setColorMaterial(new java.awt.Color(0, 0, 0));
        txtRepetirClave.setEnabled(false);
        txtRepetirClave.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtRepetirClave.setPhColor(new java.awt.Color(0, 0, 0));
        txtRepetirClave.setPlaceholder("Repetir clave del usuario...");
        txtRepetirClave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtRepetirClaveActionPerformed(evt);
            }
        });
        txtRepetirClave.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtRepetirClaveKeyTyped(evt);
            }
        });

        txtClave.setForeground(new java.awt.Color(0, 0, 0));
        txtClave.setColorMaterial(new java.awt.Color(0, 0, 0));
        txtClave.setEnabled(false);
        txtClave.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtClave.setPhColor(new java.awt.Color(0, 0, 0));
        txtClave.setPlaceholder("Clave del usuario...");
        txtClave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtClaveActionPerformed(evt);
            }
        });
        txtClave.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtClaveKeyTyped(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel11.setText("Empresa:");

        txtCodigoEmpresa.setEnabled(false);
        txtCodigoEmpresa.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtCodigoEmpresa.setPrompt("Cód. Empr.");
        txtCodigoEmpresa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoEmpresaActionPerformed(evt);
            }
        });
        txtCodigoEmpresa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodigoEmpresaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodigoEmpresaKeyTyped(evt);
            }
        });

        txtDescripcionEmpresa.setEnabled(false);
        txtDescripcionEmpresa.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtDescripcionEmpresa.setPrompt("Descripción o nombre de la empresa...");

        jLabel12.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel12.setText("Sucursal:");

        txtCodigoSucursal.setEnabled(false);
        txtCodigoSucursal.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtCodigoSucursal.setPrompt("Cód. Suc.");
        txtCodigoSucursal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoSucursalActionPerformed(evt);
            }
        });
        txtCodigoSucursal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodigoSucursalKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodigoSucursalKeyTyped(evt);
            }
        });

        txtDescripcionSucursal.setEnabled(false);
        txtDescripcionSucursal.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtDescripcionSucursal.setPrompt("Descripción o nombre de la sucursal...");

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
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pestanhaABMLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pestanhaABMLayout.createSequentialGroup()
                                        .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(11, 11, 11)
                                        .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 51, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pestanhaABMLayout.createSequentialGroup()
                                        .addGroup(pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(txtCodigo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pestanhaABMLayout.createSequentialGroup()
                                                .addGroup(pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                    .addGroup(pestanhaABMLayout.createSequentialGroup()
                                                        .addComponent(txtCodigoEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(txtDescripcionEmpresa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                    .addComponent(txtDireccion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(txtCedula, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
                                                    .addComponent(txtClave, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE))
                                                .addGroup(pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addGroup(pestanhaABMLayout.createSequentialGroup()
                                                        .addGap(11, 11, 11)
                                                        .addGroup(pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addGroup(pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                .addComponent(jLabel6)
                                                                .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                                    .addGroup(pestanhaABMLayout.createSequentialGroup()
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                        .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                                        .addGap(0, 0, Short.MAX_VALUE)))
                                .addGroup(pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pestanhaABMLayout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtApellido, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
                                            .addComponent(txtTelefono, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                    .addGroup(pestanhaABMLayout.createSequentialGroup()
                                        .addGap(7, 7, 7)
                                        .addGroup(pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(pestanhaABMLayout.createSequentialGroup()
                                                .addComponent(txtCodigoSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(txtDescripcionSucursal, javax.swing.GroupLayout.DEFAULT_SIZE, 274, Short.MAX_VALUE))
                                            .addComponent(txtRepetirClave, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                            .addGroup(pestanhaABMLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtAlias, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)))))
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
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(txtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtCedula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(txtAlias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10)
                    .addComponent(txtRepetirClave, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtClave, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtCodigoEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDescripcionEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(txtCodigoSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDescripcionSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 61, Short.MAX_VALUE)
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

    private void txtNombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            evt.setKeyChar(Character.toUpperCase(c));
        }
        if (txtNombre.getText().length() == 100) {
            evt.consume();
        }
    }//GEN-LAST:event_txtNombreKeyTyped

    private void txtNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreActionPerformed
        if (txtNombre.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "NO PUEDE DEJAR EL CAMPO VACIO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else {
            txtApellido.grabFocus();
        }
    }//GEN-LAST:event_txtNombreActionPerformed

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

    private void txtTelefonoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTelefonoActionPerformed
        txtDireccion.grabFocus();
    }//GEN-LAST:event_txtTelefonoActionPerformed

    private void txtTelefonoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefonoKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            evt.setKeyChar(Character.toUpperCase(c));
        }
        if (txtTelefono.getText().length() == 25) {
            evt.consume();
        }
    }//GEN-LAST:event_txtTelefonoKeyTyped

    private void txtCedulaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCedulaActionPerformed
        if (txtCedula.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "NO PUEDE DEJAR EL CAMPO VACIO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else {
            txtTelefono.grabFocus();
        }
    }//GEN-LAST:event_txtCedulaActionPerformed

    private void txtCedulaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCedulaKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            evt.setKeyChar(Character.toUpperCase(c));
        }
        if (txtCedula.getText().length() == 25) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCedulaKeyTyped

    private void txtApellidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtApellidoActionPerformed
        if (txtApellido.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "NO PUEDE DEJAR EL CAMPO VACIO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else {
            txtCedula.grabFocus();
        }
    }//GEN-LAST:event_txtApellidoActionPerformed

    private void txtApellidoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtApellidoKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            evt.setKeyChar(Character.toUpperCase(c));
        }
        if (txtApellido.getText().length() == 100) {
            evt.consume();
        }
    }//GEN-LAST:event_txtApellidoKeyTyped

    private void txtDireccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDireccionActionPerformed
        txtAlias.grabFocus();
    }//GEN-LAST:event_txtDireccionActionPerformed

    private void txtDireccionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDireccionKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            evt.setKeyChar(Character.toUpperCase(c));
        }
        if (txtDireccion.getText().length() == 250) {
            evt.consume();
        }
    }//GEN-LAST:event_txtDireccionKeyTyped

    private void txtAliasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAliasActionPerformed
        if (txtAlias.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "NO PUEDE DEJAR EL CAMPO VACIO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else {
            txtClave.grabFocus();
        }
    }//GEN-LAST:event_txtAliasActionPerformed

    private void txtAliasKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAliasKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            evt.setKeyChar(Character.toUpperCase(c));
        }
        if (txtAlias.getText().length() == 25) {
            evt.consume();
        }
    }//GEN-LAST:event_txtAliasKeyTyped

    private void txtCodigoEmpresaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoEmpresaActionPerformed
        if (txtCodigoEmpresa.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "NO PUEDE DEJAR EL CAMPO DE EMPRESA VACIO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else {
            int idempresa = Integer.parseInt(txtCodigoEmpresa.getText());
            e.setIdempresa(idempresa);
            boolean resultado = daoEmpresa.consultarDatos(e);
            if (resultado == true) {
                txtDescripcionEmpresa.setText(e.getRazonsocial());
                txtCodigoSucursal.grabFocus();
            } else {
                txtCodigoEmpresa.setText(null);
                txtDescripcionEmpresa.setText(null);
                txtCodigoEmpresa.grabFocus();
            }
        }
    }//GEN-LAST:event_txtCodigoEmpresaActionPerformed

    private void txtCodigoEmpresaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoEmpresaKeyPressed
        if (evt.VK_F1 == evt.getKeyCode()) {
            buscarEmpresa();
        }
    }//GEN-LAST:event_txtCodigoEmpresaKeyPressed

    private void txtCodigoEmpresaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoEmpresaKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            getToolkit().beep();
            evt.consume();
        }
        if (txtCodigoEmpresa.getText().length() == 10) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCodigoEmpresaKeyTyped

    private void txtCodigoSucursalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoSucursalActionPerformed
        if (txtCodigoSucursal.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "NO PUEDE DEJAR EL CAMPO DE SUCURSAL VACIO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else {
            int idsucursal = Integer.parseInt(txtCodigoSucursal.getText());
            s.setIdsucursal(idsucursal);
            boolean resultado = daoSucursal.consultarDatos(s);
            if (resultado == true) {
                txtDescripcionSucursal.setText(s.getDescripcion());
                btnConfirmar.grabFocus();
            } else {
                txtCodigoSucursal.setText(null);
                txtDescripcionSucursal.setText(null);
                txtCodigoSucursal.grabFocus();
            }
        }
    }//GEN-LAST:event_txtCodigoSucursalActionPerformed

    private void txtCodigoSucursalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoSucursalKeyPressed
        if (evt.VK_F1 == evt.getKeyCode()) {
            buscarSucursal();
        }
    }//GEN-LAST:event_txtCodigoSucursalKeyPressed

    private void txtCodigoSucursalKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoSucursalKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            getToolkit().beep();
            evt.consume();
        }
        if (txtCodigoSucursal.getText().length() == 10) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCodigoSucursalKeyTyped

    private void txtClaveKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtClaveKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            evt.setKeyChar(Character.toUpperCase(c));
        }
        if (txtClave.getText().length() == 25) {
            evt.consume();
        }
    }//GEN-LAST:event_txtClaveKeyTyped

    private void txtRepetirClaveKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRepetirClaveKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            evt.setKeyChar(Character.toUpperCase(c));
        }
        if (txtRepetirClave.getText().length() == 25) {
            evt.consume();
        }
    }//GEN-LAST:event_txtRepetirClaveKeyTyped

    private void txtClaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtClaveActionPerformed
        String clave = new String(txtClave.getPassword());
        if (clave.isEmpty()) {
            JOptionPane.showMessageDialog(null, "NO PUEDE DEJAR EL CAMPO VACIO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else {
            txtRepetirClave.grabFocus();
        }
    }//GEN-LAST:event_txtClaveActionPerformed

    private void txtRepetirClaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtRepetirClaveActionPerformed
        String clave = new String(txtClave.getPassword());
        String repetirClave = new String(txtRepetirClave.getPassword());
        if (repetirClave.isEmpty()) {
            JOptionPane.showMessageDialog(null, "NO PUEDE DEJAR EL CAMPO VACIO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else {

            if (repetirClave.equals(clave)) {
                txtCodigoEmpresa.grabFocus();
            } else {
                JOptionPane.showMessageDialog(null, "LA CLAVES INGRESADAS NO COINCIDEN", "ADVERTENCIA", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_txtRepetirClaveActionPerformed

    private void txtCriterioEmpresaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCriterioEmpresaActionPerformed
        cargarEmpresa();
    }//GEN-LAST:event_txtCriterioEmpresaActionPerformed

    private void txtCriterioEmpresaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCriterioEmpresaKeyPressed
        if (evt.VK_ESCAPE == evt.getKeyCode()) {
            txtCodigoEmpresa.setText(null);
            txtDescripcionEmpresa.setText(null);
            txtCodigoEmpresa.grabFocus();
            BuscadorEmpresa.dispose();
        }
    }//GEN-LAST:event_txtCriterioEmpresaKeyPressed

    private void txtCriterioEmpresaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCriterioEmpresaKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            evt.setKeyChar(Character.toUpperCase(c));
        }
        if (txtCriterio.getText().length() == 100) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCriterioEmpresaKeyTyped

    private void tablaDatosEmpresaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaDatosEmpresaMouseClicked
        if (evt.getClickCount() == 2) {
            if (tablaDatosEmpresa.getSelectedRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "SELECCIONE UNA FILA");
            } else {
                txtCriterioEmpresa.setText(null);
                BuscadorEmpresa.dispose();
            }
        }
    }//GEN-LAST:event_tablaDatosEmpresaMouseClicked

    private void txtCriterioSucursalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCriterioSucursalActionPerformed
        cargarSucursal();
    }//GEN-LAST:event_txtCriterioSucursalActionPerformed

    private void txtCriterioSucursalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCriterioSucursalKeyPressed
        if (evt.VK_ESCAPE == evt.getKeyCode()) {
            txtCodigoSucursal.setText(null);
            txtDescripcionSucursal.setText(null);
            txtCodigoSucursal.grabFocus();
            BuscadorSucursal.dispose();
        }
    }//GEN-LAST:event_txtCriterioSucursalKeyPressed

    private void txtCriterioSucursalKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCriterioSucursalKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            evt.setKeyChar(Character.toUpperCase(c));
        }
        if (txtCriterio.getText().length() == 100) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCriterioSucursalKeyTyped

    private void tablaDatosSucursalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaDatosSucursalMouseClicked
        if (evt.getClickCount() == 2) {
            if (tablaDatosSucursal.getSelectedRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "SELECCIONE UNA FILA");
            } else {
                txtCriterioSucursal.setText(null);
                BuscadorSucursal.dispose();
            }
        }
    }//GEN-LAST:event_tablaDatosSucursalMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDialog BuscadorEmpresa;
    private javax.swing.JDialog BuscadorSucursal;
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
    private javax.swing.JTable tablaDatosEmpresa;
    private javax.swing.JTable tablaDatosSucursal;
    private org.jdesktop.swingx.JXTextField txtAlias;
    private org.jdesktop.swingx.JXTextField txtApellido;
    private org.jdesktop.swingx.JXTextField txtCedula;
    private RSMaterialComponent.RSPasswordMaterial txtClave;
    private org.jdesktop.swingx.JXTextField txtCodigo;
    private org.jdesktop.swingx.JXTextField txtCodigoEmpresa;
    private org.jdesktop.swingx.JXTextField txtCodigoSucursal;
    private org.jdesktop.swingx.JXTextField txtCriterio;
    private org.jdesktop.swingx.JXTextField txtCriterioEmpresa;
    private org.jdesktop.swingx.JXTextField txtCriterioSucursal;
    private org.jdesktop.swingx.JXTextField txtDescripcionEmpresa;
    private org.jdesktop.swingx.JXTextField txtDescripcionSucursal;
    private org.jdesktop.swingx.JXTextField txtDireccion;
    private org.jdesktop.swingx.JXTextField txtNombre;
    private RSMaterialComponent.RSPasswordMaterial txtRepetirClave;
    private org.jdesktop.swingx.JXTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
}
