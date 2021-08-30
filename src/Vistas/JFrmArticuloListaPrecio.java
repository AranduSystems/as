package Vistas;

import Dao.DAOArticulo;
import Dao.DAOArticuloListaPrecio;
import Dao.DAOListaPrecio;
import Dao.DAOMoneda;
import Modelos.Articulo;
import Modelos.ArticuloListaPrecio;
import Modelos.ListaPrecio;
import Modelos.Moneda;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author armando
 */
public class JFrmArticuloListaPrecio extends javax.swing.JInternalFrame {

    Articulo a = new Articulo();
    ListaPrecio lp = new ListaPrecio();
    Moneda m = new Moneda();
    ArticuloListaPrecio alp = new ArticuloListaPrecio();

    DAOArticuloListaPrecio dao = new DAOArticuloListaPrecio();
    DAOArticulo daoArticulo = new DAOArticulo();
    DAOListaPrecio daoListaPrecio = new DAOListaPrecio();
    DAOMoneda daoMoneda = new DAOMoneda();

    ArrayList<Object[]> datos = new ArrayList<>();
    ArrayList<Object[]> datosArticulo = new ArrayList<>();
    ArrayList<Object[]> datosListasPrecios = new ArrayList<>();

    //VARIABLE QUE MANEJA QUE TIPOS DE OPERACIONES SE REALIZARAN: SI VA A SER ALTA, BAJA O MODIFICACION DEL REGISTRO
    String operacion = "";
    int idmoneda;
    double precio = 0.0;
    double costo = 0.0;
    double porcentaje = 0.0;
    double preciorecomendado = 0.0;

    /**
     * Creates new form JFrmArticuloListaPrecio
     */
    public JFrmArticuloListaPrecio() {
        setTitle("JFrmArticuloListaPrecio");
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

    public void cargarArticulo() {
        String filtro = "";
        if (rbDescripcion.isSelected()) {
            filtro = "descripcion";
        } else if (rbReferencia.isSelected()) {
            filtro = "referencia";
        } else if (rbCodAlfanumerico.isSelected()) {
            filtro = "codigoalfanumerico";
        } else if (rbCodBarra.isSelected()) {
            filtro = "codigobarra";
        }
        DefaultTableModel modelo = (DefaultTableModel) tablaDatosArticulo.getModel();
        modelo.setRowCount(0);
        datosArticulo = daoArticulo.consultar(txtCriterioArticulo.getText(), filtro);
        for (Object[] obj : datosArticulo) {
            modelo.addRow(obj);
        }
        this.tablaDatosArticulo.setModel(modelo);
    }

    private void buscarArticulo() {
        cargarArticulo();
        BuscadorArticulo.setModal(true);
        BuscadorArticulo.setSize(710, 360);
        BuscadorArticulo.setLocationRelativeTo(this);
        BuscadorArticulo.setVisible(true);
        int fila = tablaDatosArticulo.getSelectedRow();
        if (fila >= 0) {
            txtCodigoArticulo.setText(tablaDatosArticulo.getValueAt(fila, 0).toString());
            txtDescripcionArticulo.setText(tablaDatosArticulo.getValueAt(fila, 1).toString());
            txtCodigoListaPrecio.grabFocus();
        } else {
            txtCodigoArticulo.setText(null);
            txtDescripcionArticulo.setText(null);
        }
    }

    public void cargarListaPrecio() {
        DefaultTableModel modelo = (DefaultTableModel) tablaDatosListaPrecio.getModel();
        modelo.setRowCount(0);
        datosListasPrecios = daoListaPrecio.consultar(txtCriterioListaPrecio.getText());
        for (Object[] obj : datosListasPrecios) {
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
            txtCodigoListaPrecio.setText(tablaDatosListaPrecio.getValueAt(fila, 0).toString());
            txtDescripcionListaPrecio.setText(tablaDatosListaPrecio.getValueAt(fila, 1).toString());
            txtDescripcionMoneda.setText(tablaDatosListaPrecio.getValueAt(fila, 3).toString());
            // SE CONSULTA SI YA EXISTE UN REGISTRO CON EL ARTICULO Y LA LISTA PARA RECUPERAR EL PRECIO SI ES QUE POSEE
            alp.setIdarticulo(Integer.parseInt(txtCodigoArticulo.getText()));
            alp.setIdlista(lp.getIdlista());
            boolean resultadoDos = dao.consultarDatos(alp);
            if (resultadoDos == true) {
                operacion = "MODIFICAR";
                txtPrecio.setText("" + alp.getPrecio());
            }
        } else {
            txtCodigoListaPrecio.setText(null);
            txtDescripcionListaPrecio.setText(null);
            txtDescripcionMoneda.setText(null);
        }
    }

    public void habilitarCampos(String accion) {
        switch (accion) {
            case "NUEVO":
                //CAMPOS
                txtCodigoArticulo.setEnabled(true);
                txtCodigoListaPrecio.setEnabled(true);
                txtPrecio.setEnabled(true);
                txtCosto.setEnabled(true);
                txtPorcentaje.setEnabled(true);
                //BOTONES
                btnNuevo.setEnabled(false);
                btnConfirmar.setEnabled(true);
                btnCancelar.setEnabled(true);
                //REDIRECIONAMOS
                txtCodigoArticulo.grabFocus();
                break;
            case "MODIFICAR":
                //CAMPOS
                txtCodigoArticulo.setEnabled(false);
                txtCodigoListaPrecio.setEnabled(false);
                txtPrecio.setEnabled(true);
                txtCosto.setEnabled(true);
                txtPorcentaje.setEnabled(true);
                //BOTONES
                btnNuevo.setEnabled(false);
                btnConfirmar.setEnabled(true);
                btnCancelar.setEnabled(true);
                //REDIRECIONAMOS
                pestanha.setSelectedIndex(1);
                txtCosto.grabFocus();
                break;
            case "ELIMINAR":
                //CAMPOS
                txtCodigoArticulo.setEnabled(false);
                txtCodigoListaPrecio.setEnabled(false);
                txtPrecio.setEnabled(false);
                txtCosto.setEnabled(false);
                txtPorcentaje.setEnabled(false);
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
                txtCodigoArticulo.setEnabled(false);
                txtCodigoListaPrecio.setEnabled(false);
                txtPrecio.setEnabled(false);
                txtCosto.setEnabled(false);
                txtPorcentaje.setEnabled(false);
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
                txtCodigoArticulo.setEnabled(false);
                txtCodigoListaPrecio.setEnabled(false);
                txtPrecio.setEnabled(false);
                txtCosto.setEnabled(false);
                txtPorcentaje.setEnabled(false);
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
        txtCodigoArticulo.setText(null);
        txtDescripcionArticulo.setText(null);
        txtCodigoListaPrecio.setText(null);
        txtDescripcionListaPrecio.setText(null);
        txtDescripcionMoneda.setText(null);
        txtPrecio.setText(null);
        txtCosto.setText(null);
        txtPorcentaje.setText(null);
        txtPrecioVentaRecomendado.setText(null);
        idmoneda = 0;
        precio = 0.0;
        costo = 0.0;
        preciorecomendado = 0.0;
        porcentaje = 0.0;
        operacion = "";
    }

    public void guardar(String accion) {
        //CAPTURA Y VALIDACIONES DE LOS DATOS RECIBIDOS
        String error = "";
        int idarticulo = Integer.parseInt(txtCodigoArticulo.getText());
        int idlista = Integer.parseInt(txtCodigoListaPrecio.getText());
        double precioventa = precio;
        switch (accion) {
            case "NUEVO":
                if (idarticulo == 0) {
                    error += "NO HA SELECCIONADO UN ARTÍCULO.\n";
                }
                if (idlista == 0) {
                    error += "NO HA SELECCIONADO UNA LISTA DE PRECIO.\n";
                }
                if (precioventa <= 0) {
                    error += "EL PRECIO DE VENTA DEBE DE SER MAYOR A 0.\n";
                }
                if (error.isEmpty()) {
                    alp.setIdarticulo(idarticulo);
                    alp.setIdlista(idlista);
                    alp.setPrecio(precioventa);
                    dao.agregar(alp);
                    cargar();
                } else {
                    JOptionPane.showMessageDialog(null, error, "ERRORES", JOptionPane.ERROR_MESSAGE);
                }
                break;
            case "MODIFICAR":
                if (idarticulo == 0) {
                    error += "NO HA SELECCIONADO UN ARTÍCULO.\n";
                }
                if (idlista == 0) {
                    error += "NO HA SELECCIONADO UNA LISTA DE PRECIO.\n";
                }
                if (precioventa <= 0) {
                    error += "EL PRECIO DE VENTA DEBE DE SER MAYOR A 0.\n";
                }
                if (error.isEmpty()) {
                    alp.setIdarticulo(idarticulo);
                    alp.setIdlista(idlista);
                    alp.setPrecio(precioventa);
                    dao.modificar(alp);
                    cargar();
                } else {
                    JOptionPane.showMessageDialog(null, error, "ERRORES", JOptionPane.ERROR_MESSAGE);
                }
                break;
            case "ELIMINAR":
                if (error.isEmpty()) {
                    alp.setIdarticulo(idarticulo);
                    alp.setIdlista(idlista);
                    dao.eliminar(alp);
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
            int idarticulo = Integer.parseInt(tablaDatos.getValueAt(fila, 0).toString());
            int idlista = Integer.parseInt(tablaDatos.getValueAt(fila, 2).toString());
            alp.setIdarticulo(idarticulo);
            alp.setIdlista(idlista);
            dao.consultarDatos(alp);

            //SE OBTIENE LOS DATOS DEL ARTICULO DESDE EL MODELO Y EL DAO PERTENECIENTE A ARTICULOS
            a.setIdarticulo(alp.getIdarticulo());
            daoArticulo.consultarDatos(a);
            txtCodigoArticulo.setText("" + a.getIdarticulo());
            txtDescripcionArticulo.setText(a.getDescripcion());
            //SE OBTIENE LOS DATOS DE LA LISTA DE PRECIOS DESDE EL MODELO Y EL DAO PERTENECIENTE A LISTAS DE PRECIOS
            lp.setIdlista(idlista);
            daoListaPrecio.consultarDatos(lp);
            txtCodigoListaPrecio.setText("" + lp.getIdlista());
            txtDescripcionListaPrecio.setText(lp.getDescripcion());
            //SE OBTIENE LOS DATOS DE LA MOENDA DESDE EL MODELO Y EL DAO PERTENECIENTE A MONEDAS
            m.setIdmoneda(lp.getIdmoneda());
            daoMoneda.consultarDatos(m);
            idmoneda = m.getIdmoneda();
            txtDescripcionMoneda.setText(m.getDescripcion());
            //SE OBTIENE EL PRECIO DE VENTA ALMACENADO EN EL REGISTRO
            txtPrecio.setText("" + alp.getPrecio());

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
        BuscadorArticulo = new javax.swing.JDialog();
        jPanel9 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        txtCriterioArticulo = new org.jdesktop.swingx.JXTextField();
        jScrollPane5 = new javax.swing.JScrollPane();
        tablaDatosArticulo = new javax.swing.JTable();
        rbDescripcion = new javax.swing.JRadioButton();
        rbReferencia = new javax.swing.JRadioButton();
        rbCodAlfanumerico = new javax.swing.JRadioButton();
        rbCodBarra = new javax.swing.JRadioButton();
        grupoFiltroArticulo = new javax.swing.ButtonGroup();
        BuscadorListaPrecio = new javax.swing.JDialog();
        jPanel4 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        txtCriterioListaPrecio = new org.jdesktop.swingx.JXTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaDatosListaPrecio = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        pestanha = new javax.swing.JTabbedPane();
        pestanhaLista = new javax.swing.JPanel();
        txtCriterio = new org.jdesktop.swingx.JXTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaDatos = new javax.swing.JTable();
        pestanhaABM = new javax.swing.JPanel();
        btnCancelar = new javax.swing.JButton();
        btnConfirmar = new javax.swing.JButton();
        btnNuevo = new javax.swing.JButton();
        txtCodigoArticulo = new org.jdesktop.swingx.JXTextField();
        txtDescripcionArticulo = new org.jdesktop.swingx.JXTextField();
        jLabel6 = new javax.swing.JLabel();
        txtCodigoListaPrecio = new org.jdesktop.swingx.JXTextField();
        txtDescripcionListaPrecio = new org.jdesktop.swingx.JXTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtDescripcionMoneda = new org.jdesktop.swingx.JXTextField();
        jLabel9 = new javax.swing.JLabel();
        txtPrecio = new org.jdesktop.swingx.JXTextField();
        jLabel11 = new javax.swing.JLabel();
        txtCosto = new org.jdesktop.swingx.JXTextField();
        jLabel12 = new javax.swing.JLabel();
        txtPorcentaje = new org.jdesktop.swingx.JXTextField();
        jLabel15 = new javax.swing.JLabel();
        txtPrecioVentaRecomendado = new org.jdesktop.swingx.JXTextField();

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

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));

        jLabel14.setBackground(new java.awt.Color(50, 104, 151));
        jLabel14.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("BUSCADOR DE ARTÍCULOS");
        jLabel14.setOpaque(true);

        txtCriterioArticulo.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtCriterioArticulo.setPrompt("Aqui puede ingresar los filtros para la busqueda..");
        txtCriterioArticulo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCriterioArticuloActionPerformed(evt);
            }
        });
        txtCriterioArticulo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCriterioArticuloKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCriterioArticuloKeyTyped(evt);
            }
        });

        tablaDatosArticulo.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        tablaDatosArticulo.setModel(new javax.swing.table.DefaultTableModel(
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
        tablaDatosArticulo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaDatosArticuloMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tablaDatosArticulo);
        if (tablaDatosArticulo.getColumnModel().getColumnCount() > 0) {
            tablaDatosArticulo.getColumnModel().getColumn(0).setMinWidth(60);
            tablaDatosArticulo.getColumnModel().getColumn(0).setPreferredWidth(60);
            tablaDatosArticulo.getColumnModel().getColumn(0).setMaxWidth(60);
        }

        grupoFiltroArticulo.add(rbDescripcion);
        rbDescripcion.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        rbDescripcion.setSelected(true);
        rbDescripcion.setText("Descripción");
        rbDescripcion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbDescripcionActionPerformed(evt);
            }
        });
        rbDescripcion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                rbDescripcionKeyPressed(evt);
            }
        });

        grupoFiltroArticulo.add(rbReferencia);
        rbReferencia.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        rbReferencia.setText("Referencia");
        rbReferencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbReferenciaActionPerformed(evt);
            }
        });
        rbReferencia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                rbReferenciaKeyPressed(evt);
            }
        });

        grupoFiltroArticulo.add(rbCodAlfanumerico);
        rbCodAlfanumerico.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        rbCodAlfanumerico.setText("Cód. Alfanumérico");
        rbCodAlfanumerico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbCodAlfanumericoActionPerformed(evt);
            }
        });
        rbCodAlfanumerico.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                rbCodAlfanumericoKeyPressed(evt);
            }
        });

        grupoFiltroArticulo.add(rbCodBarra);
        rbCodBarra.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        rbCodBarra.setText("Cód. Barra");
        rbCodBarra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbCodBarraActionPerformed(evt);
            }
        });
        rbCodBarra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                rbCodBarraKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(rbDescripcion)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rbReferencia)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rbCodAlfanumerico)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rbCodBarra)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 680, Short.MAX_VALUE)
                    .addComponent(txtCriterioArticulo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 680, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbDescripcion)
                    .addComponent(rbReferencia)
                    .addComponent(rbCodAlfanumerico)
                    .addComponent(rbCodBarra))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCriterioArticulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 254, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout BuscadorArticuloLayout = new javax.swing.GroupLayout(BuscadorArticulo.getContentPane());
        BuscadorArticulo.getContentPane().setLayout(BuscadorArticuloLayout);
        BuscadorArticuloLayout.setHorizontalGroup(
            BuscadorArticuloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BuscadorArticuloLayout.setVerticalGroup(
            BuscadorArticuloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jLabel10.setBackground(new java.awt.Color(50, 104, 151));
        jLabel10.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("BUSCADOR DE LISTAS DE PRECIOS");
        jLabel10.setOpaque(true);

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
                "<html><p style=\"text-align:center\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Código</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Descripción</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Cod.Moneda</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">DescripcionMoneda</span></span></span></p></html> "
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class
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
        tablaDatosListaPrecio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaDatosListaPrecioMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tablaDatosListaPrecio);
        if (tablaDatosListaPrecio.getColumnModel().getColumnCount() > 0) {
            tablaDatosListaPrecio.getColumnModel().getColumn(0).setMinWidth(60);
            tablaDatosListaPrecio.getColumnModel().getColumn(0).setPreferredWidth(60);
            tablaDatosListaPrecio.getColumnModel().getColumn(0).setMaxWidth(60);
            tablaDatosListaPrecio.getColumnModel().getColumn(2).setMinWidth(0);
            tablaDatosListaPrecio.getColumnModel().getColumn(2).setPreferredWidth(0);
            tablaDatosListaPrecio.getColumnModel().getColumn(2).setMaxWidth(0);
            tablaDatosListaPrecio.getColumnModel().getColumn(3).setMinWidth(0);
            tablaDatosListaPrecio.getColumnModel().getColumn(3).setPreferredWidth(0);
            tablaDatosListaPrecio.getColumnModel().getColumn(3).setMaxWidth(0);
        }

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCriterioListaPrecio, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCriterioListaPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout BuscadorListaPrecioLayout = new javax.swing.GroupLayout(BuscadorListaPrecio.getContentPane());
        BuscadorListaPrecio.getContentPane().setLayout(BuscadorListaPrecioLayout);
        BuscadorListaPrecioLayout.setHorizontalGroup(
            BuscadorListaPrecioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        BuscadorListaPrecioLayout.setVerticalGroup(
            BuscadorListaPrecioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setClosable(true);
        setIconifiable(true);

        jPanel1.setBackground(new java.awt.Color(50, 104, 151));

        jLabel1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Mantenimiento de Precios de Artículos por Listas de Precios.");

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
                "<html><p style=\"text-align:center\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Cód. Art.</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Artículo</span></span></span></p></html> ", "<html><p style=\"text-align:center\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Cód. Lista</span></span></span></p></html> ", "<html><p style=\"text-align:center\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Lista</span></span></span></p></html> ", "<html><p style=\"text-align:center\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Precio</span></span></span></p></html> "
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.Double.class
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
        tablaDatos.setComponentPopupMenu(menuDesplegable);
        jScrollPane1.setViewportView(tablaDatos);
        if (tablaDatos.getColumnModel().getColumnCount() > 0) {
            tablaDatos.getColumnModel().getColumn(0).setMinWidth(70);
            tablaDatos.getColumnModel().getColumn(0).setPreferredWidth(70);
            tablaDatos.getColumnModel().getColumn(0).setMaxWidth(70);
            tablaDatos.getColumnModel().getColumn(2).setMinWidth(70);
            tablaDatos.getColumnModel().getColumn(2).setPreferredWidth(70);
            tablaDatos.getColumnModel().getColumn(2).setMaxWidth(70);
            tablaDatos.getColumnModel().getColumn(3).setMinWidth(150);
            tablaDatos.getColumnModel().getColumn(3).setPreferredWidth(150);
            tablaDatos.getColumnModel().getColumn(3).setMaxWidth(150);
            tablaDatos.getColumnModel().getColumn(4).setMinWidth(125);
            tablaDatos.getColumnModel().getColumn(4).setPreferredWidth(125);
            tablaDatos.getColumnModel().getColumn(4).setMaxWidth(125);
        }

        javax.swing.GroupLayout pestanhaListaLayout = new javax.swing.GroupLayout(pestanhaLista);
        pestanhaLista.setLayout(pestanhaListaLayout);
        pestanhaListaLayout.setHorizontalGroup(
            pestanhaListaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pestanhaListaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pestanhaListaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCriterio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 789, Short.MAX_VALUE))
                .addContainerGap())
        );
        pestanhaListaLayout.setVerticalGroup(
            pestanhaListaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pestanhaListaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtCriterio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 252, Short.MAX_VALUE)
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

        txtCodigoArticulo.setEnabled(false);
        txtCodigoArticulo.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtCodigoArticulo.setPrompt("Cód. Art.");
        txtCodigoArticulo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoArticuloActionPerformed(evt);
            }
        });
        txtCodigoArticulo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodigoArticuloKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodigoArticuloKeyTyped(evt);
            }
        });

        txtDescripcionArticulo.setEnabled(false);
        txtDescripcionArticulo.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtDescripcionArticulo.setPrompt("Descripción o nombre del artículo...");

        jLabel6.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel6.setText("Artículo:");

        txtCodigoListaPrecio.setEnabled(false);
        txtCodigoListaPrecio.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtCodigoListaPrecio.setPrompt("Cód. Lis.");
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
        txtDescripcionListaPrecio.setPrompt("Descripción o nombre de la lista de precios...");

        jLabel7.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel7.setText("Lista de Precio:");

        jLabel8.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel8.setText("Moneda:");

        txtDescripcionMoneda.setEnabled(false);
        txtDescripcionMoneda.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtDescripcionMoneda.setPrompt("Descripción o nombre de la moneda...");

        jLabel9.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel9.setText("Precio Venta:");

        txtPrecio.setEnabled(false);
        txtPrecio.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtPrecio.setPrompt("Precio de Venta del artículo...");
        txtPrecio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPrecioActionPerformed(evt);
            }
        });
        txtPrecio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPrecioKeyTyped(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel11.setText("Costo:");

        txtCosto.setEnabled(false);
        txtCosto.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtCosto.setPrompt("Costo del Artículo...");
        txtCosto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCostoActionPerformed(evt);
            }
        });
        txtCosto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCostoKeyTyped(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel12.setText("% Ganancia:");

        txtPorcentaje.setEnabled(false);
        txtPorcentaje.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtPorcentaje.setPrompt("Porcentaje de ganacia...");
        txtPorcentaje.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPorcentajeActionPerformed(evt);
            }
        });
        txtPorcentaje.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPorcentajeKeyTyped(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel15.setText("Precio de Venta Recomendado:");

        txtPrecioVentaRecomendado.setBackground(new java.awt.Color(204, 255, 204));
        txtPrecioVentaRecomendado.setEnabled(false);
        txtPrecioVentaRecomendado.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        txtPrecioVentaRecomendado.setPrompt("Precio recomendado...");
        txtPrecioVentaRecomendado.setPromptForeground(new java.awt.Color(109, 109, 109));

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
                            .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pestanhaABMLayout.createSequentialGroup()
                                .addComponent(txtCodigoArticulo, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtDescripcionArticulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(pestanhaABMLayout.createSequentialGroup()
                                .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pestanhaABMLayout.createSequentialGroup()
                                .addGroup(pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(pestanhaABMLayout.createSequentialGroup()
                                        .addComponent(txtCodigoListaPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtDescripcionListaPrecio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel8))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pestanhaABMLayout.createSequentialGroup()
                                        .addComponent(txtCosto, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pestanhaABMLayout.createSequentialGroup()
                                        .addComponent(txtPorcentaje, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                                        .addComponent(jLabel15)))
                                .addGap(6, 6, 6)
                                .addGroup(pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtPrecioVentaRecomendado, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtDescripcionMoneda, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE))))))
                .addContainerGap())
        );
        pestanhaABMLayout.setVerticalGroup(
            pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pestanhaABMLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCodigoArticulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDescripcionArticulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCodigoListaPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDescripcionListaPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDescripcionMoneda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCosto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPorcentaje, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPrecioVentaRecomendado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pestanhaABMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 106, Short.MAX_VALUE)
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

    private void txtCriterioArticuloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCriterioArticuloActionPerformed
        cargarArticulo();
    }//GEN-LAST:event_txtCriterioArticuloActionPerformed

    private void txtCriterioArticuloKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCriterioArticuloKeyPressed
        if (evt.VK_ESCAPE == evt.getKeyCode()) {
            txtCodigoArticulo.setText(null);
            txtDescripcionArticulo.setText(null);
            txtCriterioArticulo.setText(null);
            BuscadorArticulo.dispose();
        }
    }//GEN-LAST:event_txtCriterioArticuloKeyPressed

    private void txtCriterioArticuloKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCriterioArticuloKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            evt.setKeyChar(Character.toUpperCase(c));
        }
        if (txtCriterioArticulo.getText().length() == 100) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCriterioArticuloKeyTyped

    private void tablaDatosArticuloMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaDatosArticuloMouseClicked
        if (evt.getClickCount() == 2) {
            if (tablaDatosArticulo.getSelectedRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "SELECCIONE UNA FILA");
            } else {
                txtCriterioArticulo.setText(null);
                BuscadorArticulo.dispose();
            }
        }
    }//GEN-LAST:event_tablaDatosArticuloMouseClicked

    private void rbDescripcionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbDescripcionActionPerformed
        txtCriterioArticulo.grabFocus();
    }//GEN-LAST:event_rbDescripcionActionPerformed

    private void rbDescripcionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rbDescripcionKeyPressed
        if (evt.VK_ENTER == evt.getKeyCode()) {
            txtCriterioArticulo.grabFocus();
        }
    }//GEN-LAST:event_rbDescripcionKeyPressed

    private void rbReferenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbReferenciaActionPerformed
        txtCriterioArticulo.grabFocus();
    }//GEN-LAST:event_rbReferenciaActionPerformed

    private void rbReferenciaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rbReferenciaKeyPressed
        if (evt.VK_ENTER == evt.getKeyCode()) {
            txtCriterioArticulo.grabFocus();
        }
    }//GEN-LAST:event_rbReferenciaKeyPressed

    private void rbCodAlfanumericoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbCodAlfanumericoActionPerformed
        txtCriterioArticulo.grabFocus();
    }//GEN-LAST:event_rbCodAlfanumericoActionPerformed

    private void rbCodAlfanumericoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rbCodAlfanumericoKeyPressed
        if (evt.VK_ENTER == evt.getKeyCode()) {
            txtCriterioArticulo.grabFocus();
        }
    }//GEN-LAST:event_rbCodAlfanumericoKeyPressed

    private void rbCodBarraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbCodBarraActionPerformed
        txtCriterioArticulo.grabFocus();
    }//GEN-LAST:event_rbCodBarraActionPerformed

    private void rbCodBarraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rbCodBarraKeyPressed
        if (evt.VK_ENTER == evt.getKeyCode()) {
            txtCriterioArticulo.grabFocus();
        }
    }//GEN-LAST:event_rbCodBarraKeyPressed

    private void txtCodigoArticuloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoArticuloActionPerformed
        if (txtCodigoArticulo.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "NO PUEDE DEJAR EL CAMPO DE ARTÍCULO VACIO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
            txtDescripcionArticulo.setText(null);
        } else {
            int idarticulo = Integer.parseInt(txtCodigoArticulo.getText());
            a.setIdarticulo(idarticulo);
            boolean resultado = daoArticulo.consultarDatos(a);
            if (resultado == true) {
                txtDescripcionArticulo.setText(a.getDescripcion());
                txtCodigoListaPrecio.grabFocus();
            } else {
                txtCodigoArticulo.setText(null);
                txtDescripcionArticulo.setText(null);
                txtCodigoArticulo.grabFocus();
            }
        }
    }//GEN-LAST:event_txtCodigoArticuloActionPerformed

    private void txtCodigoArticuloKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoArticuloKeyPressed
        if (evt.VK_F1 == evt.getKeyCode()) {
            buscarArticulo();
        }
    }//GEN-LAST:event_txtCodigoArticuloKeyPressed

    private void txtCodigoArticuloKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoArticuloKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            getToolkit().beep();
            evt.consume();
        }
        if (txtCodigoArticulo.getText().length() == 10) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCodigoArticuloKeyTyped

    private void txtCodigoListaPrecioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoListaPrecioActionPerformed
        if (txtCodigoListaPrecio.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "NO PUEDE DEJAR EL CAMPO DE LISTA DE PRECIO VACIO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
            txtDescripcionListaPrecio.setText(null);
            txtDescripcionMoneda.setText(null);
        } else {
            int idlistaprecio = Integer.parseInt(txtCodigoListaPrecio.getText());
            lp.setIdlista(idlistaprecio);
            boolean resultado = daoListaPrecio.consultarDatos(lp);
            if (resultado == true) {
                txtDescripcionListaPrecio.setText(lp.getDescripcion());
                idmoneda = lp.getIdmoneda();
                m.setIdmoneda(idmoneda);
                daoMoneda.consultarDatos(m);
                txtDescripcionMoneda.setText(m.getDescripcion());

                // SE CONSULTA SI YA EXISTE UN REGISTRO CON EL ARTICULO Y LA LISTA PARA RECUPERAR EL PRECIO SI ES QUE POSEE
                alp.setIdarticulo(Integer.parseInt(txtCodigoArticulo.getText()));
                alp.setIdlista(lp.getIdlista());
                boolean resultadoDos = dao.consultarDatos(alp);
                if (resultadoDos == true) {
                    operacion = "MODIFICAR";
                    txtPrecio.setText("" + alp.getPrecio());
                }

                txtCosto.grabFocus();
            } else {
                txtCodigoListaPrecio.setText(null);
                txtDescripcionListaPrecio.setText(null);
                txtDescripcionMoneda.setText(null);
                txtCodigoListaPrecio.grabFocus();
            }
        }
    }//GEN-LAST:event_txtCodigoListaPrecioActionPerformed

    private void txtCodigoListaPrecioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoListaPrecioKeyPressed
        if (evt.VK_F1 == evt.getKeyCode()) {
            buscarListaPrecio();
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

    private void txtCriterioListaPrecioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCriterioListaPrecioActionPerformed
        cargarListaPrecio();
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

    private void txtPrecioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPrecioActionPerformed
        if (txtPrecio.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "NO PUEDE DEJAR EL CAMPO DE PRECIO VACIO", "ATENCIÓN", JOptionPane.WARNING_MESSAGE);
        } else {
            String number = txtPrecio.getText();
            double monto = Double.parseDouble(number);
            if (monto < 0) {
                JOptionPane.showMessageDialog(null, "EL PRECIO DE VENTA NO PUEDE SER MENOR A 0", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
            } else {
                precio = monto;
                DecimalFormat formatter;
                if (idmoneda == 1) {
                    formatter = new DecimalFormat("#,###");
                } else {
                    formatter = new DecimalFormat("#,###.000");
                }
                txtPrecio.setText(formatter.format(monto));
                btnConfirmar.grabFocus();
            }
        }
    }//GEN-LAST:event_txtPrecioActionPerformed

    private void txtPrecioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrecioKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            getToolkit().beep();
            evt.consume();
        }
        if (c == ',') {
            getToolkit().beep();
            evt.consume();
        }
        if (txtPrecio.getText().length() == 20) {
            evt.consume();
        }
    }//GEN-LAST:event_txtPrecioKeyTyped

    private void txtCostoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCostoActionPerformed
        if (txtCosto.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "NO PUEDE DEJAR EL CAMPO DE COSTO VACIO", "ATENCIÓN", JOptionPane.WARNING_MESSAGE);
        } else {
            String number = txtCosto.getText();
            double monto = Double.parseDouble(number);
            if (monto <= 0) {
                JOptionPane.showMessageDialog(null, "EL COSTO NO PUEDE SER MENOR O IGUAL A 0", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
            } else {
                costo = monto;
                DecimalFormat formatter;
                if (idmoneda == 1) {
                    formatter = new DecimalFormat("#,###");
                } else {
                    formatter = new DecimalFormat("#,###.000");
                }
                txtCosto.setText(formatter.format(monto));
                txtPorcentaje.grabFocus();
            }
        }
    }//GEN-LAST:event_txtCostoActionPerformed

    private void txtCostoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCostoKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            getToolkit().beep();
            evt.consume();
        }
        if (c == ',') {
            getToolkit().beep();
            evt.consume();
        }
        if (txtCosto.getText().length() == 20) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCostoKeyTyped

    private void txtPorcentajeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPorcentajeActionPerformed
        if (txtPorcentaje.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "NO PUEDE DEJAR EL CAMPO DE PORCENTAJE DE GANANCIA VACIA", "ATENCIÓN", JOptionPane.WARNING_MESSAGE);
        } else {
            String number = txtPorcentaje.getText();
            double monto = Double.parseDouble(number);
            if (monto <= 0) {
                JOptionPane.showMessageDialog(null, "EL PORCENTAJE DE GANANCIA NO PUEDE SER MENOR O IGUAL A 0", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
            } else {
                porcentaje = monto;
                DecimalFormat formatter = new DecimalFormat("#,###.00");
                DecimalFormat formatterDos;
                txtPorcentaje.setText(formatter.format(monto));

                //CALCULAR EL PRECIO DE VENTA RECOMENDADO
                double preciorecom = (costo + ((costo * porcentaje) / 100));
                preciorecomendado = (costo + ((costo * porcentaje) / 100));
                if (idmoneda == 1) {
                    preciorecomendado = Math.round(preciorecom);
                    formatterDos = new DecimalFormat("#,###");
                } else {
                    preciorecomendado = (Math.round(preciorecom * 1000.0) / 1000.0);
                    formatterDos = new DecimalFormat("#,###.000");
                }
                txtPrecioVentaRecomendado.setText(formatterDos.format(preciorecomendado));
                txtPrecio.setText("" + preciorecomendado);
                txtPrecio.grabFocus();
            }
        }
    }//GEN-LAST:event_txtPorcentajeActionPerformed

    private void txtPorcentajeKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPorcentajeKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            getToolkit().beep();
            evt.consume();
        }
        if (c == ',') {
            getToolkit().beep();
            evt.consume();
        }
        if (txtPorcentaje.getText().length() == 10) {
            evt.consume();
        }
    }//GEN-LAST:event_txtPorcentajeKeyTyped


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDialog BuscadorArticulo;
    private javax.swing.JDialog BuscadorListaPrecio;
    private javax.swing.JMenuItem Eliminar;
    private javax.swing.JMenuItem Modificar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnConfirmar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.ButtonGroup grupoFiltroArticulo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JPopupMenu menuDesplegable;
    private javax.swing.JTabbedPane pestanha;
    private javax.swing.JPanel pestanhaABM;
    private javax.swing.JPanel pestanhaLista;
    private javax.swing.JRadioButton rbCodAlfanumerico;
    private javax.swing.JRadioButton rbCodBarra;
    private javax.swing.JRadioButton rbDescripcion;
    private javax.swing.JRadioButton rbReferencia;
    private javax.swing.JTable tablaDatos;
    private javax.swing.JTable tablaDatosArticulo;
    private javax.swing.JTable tablaDatosListaPrecio;
    private org.jdesktop.swingx.JXTextField txtCodigoArticulo;
    private org.jdesktop.swingx.JXTextField txtCodigoListaPrecio;
    private org.jdesktop.swingx.JXTextField txtCosto;
    private org.jdesktop.swingx.JXTextField txtCriterio;
    private org.jdesktop.swingx.JXTextField txtCriterioArticulo;
    private org.jdesktop.swingx.JXTextField txtCriterioListaPrecio;
    private org.jdesktop.swingx.JXTextField txtDescripcionArticulo;
    private org.jdesktop.swingx.JXTextField txtDescripcionListaPrecio;
    private org.jdesktop.swingx.JXTextField txtDescripcionMoneda;
    private org.jdesktop.swingx.JXTextField txtPorcentaje;
    private org.jdesktop.swingx.JXTextField txtPrecio;
    private org.jdesktop.swingx.JXTextField txtPrecioVentaRecomendado;
    // End of variables declaration//GEN-END:variables
}
