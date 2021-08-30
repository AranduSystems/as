package Vistas;

import App.appLogin;
import Dao.DAOArticulo;
import Dao.DAOArticuloPeriodo;
import Dao.DAOCProveedor;
import Dao.DAOCompra;
import Dao.DAOCompraCuota;
import Dao.DAOCompraDetalle;
import Dao.DAOConfiguracion;
import Dao.DAOCotizacion;
import Dao.DAOCuenta;
import Dao.DAODeposito;
import Dao.DAOImpuesto;
import Dao.DAOMoneda;
import Modelos.Articulo;
import Modelos.ArticuloPeriodo;
import Modelos.Compra;
import Modelos.CompraCuota;
import Modelos.CompraDetalle;
import Modelos.Configuracion;
import Modelos.Cuenta;
import Modelos.Deposito;
import Modelos.Impuesto;
import Modelos.Moneda;
import Modelos.Proveedor;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author armando
 */
public class JFrmCompra extends javax.swing.JInternalFrame {

    Compra c = new Compra();
    CompraDetalle cd = new CompraDetalle();
    Moneda m = new Moneda();
    Deposito d = new Deposito();
    Proveedor p = new Proveedor();
    Articulo a = new Articulo();
    Configuracion co = new Configuracion();
    Impuesto i = new Impuesto();
    CompraCuota cc = new CompraCuota();
    ArticuloPeriodo ap = new ArticuloPeriodo();
    Cuenta cu = new Cuenta();

    DAOCompra dao = new DAOCompra();
    DAOCompraDetalle daoCompraDetalle = new DAOCompraDetalle();
    DAOMoneda daoMoneda = new DAOMoneda();
    DAOCotizacion daoCotizacion = new DAOCotizacion();
    DAODeposito daoDeposito = new DAODeposito();
    DAOCProveedor daoProveedor = new DAOCProveedor();
    DAOArticulo daoArticulo = new DAOArticulo();
    DAOConfiguracion daoConfiguracion = new DAOConfiguracion();
    DAOImpuesto daoImpuesto = new DAOImpuesto();
    DAOCompraCuota daoCompraCuota = new DAOCompraCuota();
    DAOArticuloPeriodo daoArticuloPeriodo = new DAOArticuloPeriodo();
    DAOCuenta daoCuenta = new DAOCuenta();

    ArrayList<Object[]> datos = new ArrayList<>();
    ArrayList<Object[]> datosMoneda = new ArrayList<>();
    ArrayList<Object[]> datosDeposito = new ArrayList<>();
    ArrayList<Object[]> datosProveedor = new ArrayList<>();
    ArrayList<Object[]> datosArticulo = new ArrayList<>();
    ArrayList<Object[]> datosCuenta = new ArrayList<>();

    String tres_ceros = String.format("%%0%dd", 3);
    String siete_ceros = String.format("%%0%dd", 7);
    SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat formatoUS = new SimpleDateFormat("yyyy-MM-dd");
    Date SYSDATE = new Date();

    int codigoarticulo;
    Double valorCosto = 0.0;
    Double valorCantidad = 0.0;
    int idtipomovimientocontado;
    int idtipomovimientocredito;
    int Establecimiento;
    Double valorTotalNeto = 0.0;
    Double valorTotalIva = 0.0;
    Double porcentajeIva = 0.0;
    Double valorMontoCuota = 0.0;
    Double valorMontoTotalCuota = 0.0;
    Double valorTotalDocumentoCuota = 0.0;
    Double valorTotalDocumento = 0.0;

    /**
     * Creates new form JFrmCompra
     */
    public JFrmCompra() {
        setTitle("JFrmCompra");
        initComponents();
        txtFecha.setFormats(formato);
        txtVencimientoCuota.setFormats(formato);
        String fechaActual = formato.format(SYSDATE);
        txtFecha.setDate(daoCotizacion.parseFecha(fechaActual));
        rbContado.setSelected(true);
    }

    public void obtenerConfiguracion() {
        boolean resultado;
        int codigosucursal = appLogin.IDSUCURSAL;
        co.setIdsucursal(codigosucursal);
        resultado = daoConfiguracion.consultarDatos(co);
        if (resultado == true) {
            idtipomovimientocontado = co.getFac_con_rec();
            idtipomovimientocredito = co.getFac_cre_rec();
        } else {
            JOptionPane.showMessageDialog(null, "NO SE HA ENCONTRADO LA CONFIGURACIÓN NECESARIA.\n"
                    + " VERIFIQUE EN SISTEMA GENERAL SI LA CONFIGURACIÓN ESTA CARGADA DE FORMA CORRECTA. ", "ADVERTENCIA", JOptionPane.ERROR_MESSAGE);
            dispose();
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

    public void cargarDeposito() {
        DefaultTableModel modelo = (DefaultTableModel) tablaDatosDepositos.getModel();
        modelo.setRowCount(0);
        datosDeposito = daoDeposito.consultar(txtCriterioDeposito.getText());
        for (Object[] obj : datosDeposito) {
            modelo.addRow(obj);
        }
        this.tablaDatosDepositos.setModel(modelo);
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
            codigoarticulo = Integer.parseInt(tablaDatosArticulo.getValueAt(fila, 0).toString());
            a.setIdarticulo(codigoarticulo);
            daoArticulo.consultarDatos(a);
            i.setIdimpuesto(a.getIdimpuesto());
            daoImpuesto.consultarDatos(i);
            porcentajeIva = i.getPorcentaje();
            txtCosto.grabFocus();
        } else {
            txtCodigoArticulo.setText(null);
            txtDescripcionArticulo.setText(null);
        }
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

    private void limpiarCamposArticulos() {
        txtCodigoArticulo.setText(null);
        txtDescripcionArticulo.setText(null);
        txtCosto.setText(null);
        txtCantidad.setText(null);
        codigoarticulo = 0;
        valorCosto = 0.0;
        valorCantidad = 0.0;
        porcentajeIva = 0.0;
    }

    private void agregar() {
        String msj = "";
        int numero_item = 0;
        String referencia = txtCodigoArticulo.getText();
        int idarticulo = codigoarticulo;
        String articulo = txtDescripcionArticulo.getText();
        Double costo = valorCosto;
        Double cantidad = valorCantidad;
        Double porc_iva = porcentajeIva;

        if (msj.isEmpty()) {
            int i;
            boolean existe = false;
            for (i = 0; i < tablaDatos.getRowCount(); i++) {
                int iditemtabla = Integer.parseInt(tablaDatos.getValueAt(i, 2).toString());
                if (iditemtabla == idarticulo) {
                    existe = true;
                    break;
                }
            }
            if (existe == false) {
                insertarArticulo(numero_item, referencia, idarticulo, articulo, costo, cantidad, porc_iva);
                calcularTotales();
            } else {
                actualizarArticulo(i, cantidad, costo);
                calcularTotales();
            }
        }
    }

    private void insertarArticulo(int numero_item, String referencia, int idarticulo, String descripcion, double costo, double cantidad, double porcentaje) {
        if (cantidad <= 0) {
            JOptionPane.showMessageDialog(null, "NO PUEDE AGREGAR UN PRODUCTO CON LA CANTIDAD 0");
        } else {
            Object[] fila = new Object[8];
            fila[0] = numero_item;
            fila[1] = referencia;
            fila[2] = idarticulo;
            fila[3] = descripcion;
            fila[4] = costo;
            fila[5] = cantidad;
            fila[6] = cantidad * costo;
            fila[7] = porcentaje;

            DefaultTableModel modelo = (DefaultTableModel) tablaDatos.getModel();
            modelo.addRow(fila);
            calcularNumeroItem();
            limpiarCamposArticulos();
            txtCodigoArticulo.grabFocus();
        }
    }

    private void actualizarArticulo(int i, double cantidad, double costo) {
        Double CANTIDAD_ANTERIOR = Double.parseDouble(tablaDatos.getValueAt(i, 5).toString());
        Double CANTIDAD_NUEVA = cantidad + CANTIDAD_ANTERIOR;
        System.out.println("ubicacion: " + i);
        System.out.println("cantidad: " + CANTIDAD_NUEVA);
        System.out.println("costo: " + costo);

        if (CANTIDAD_NUEVA > 0.0) {
            //System.out.println("aqui");
            tablaDatos.setValueAt(costo, i, 4);
            double SUBTOTAL = CANTIDAD_NUEVA * costo;

            tablaDatos.setValueAt(CANTIDAD_NUEVA, i, 5);
            tablaDatos.setValueAt(SUBTOTAL, i, 6);
            limpiarCamposArticulos();
            txtCodigoArticulo.grabFocus();
        } else {
            JOptionPane.showMessageDialog(null, "LA CANTIDAD EN LA TABLA NO PUEDE SER MENOR A 0");
        }
    }

    private void calcularNumeroItem() {
        for (int i = 0; i < tablaDatos.getRowCount(); i++) {
            tablaDatos.setValueAt(i + 1, i, 0);
        }
    }

    private void retirar() {
        int indice = tablaDatos.getSelectedRow();
        if (indice >= 0) {
            int respuesta;
            respuesta = JOptionPane.showConfirmDialog(null, "¿ESTA SEGURO DE QUITAR LA FILA SELECCIONADA?",
                    "ADVERTENCIA", JOptionPane.YES_NO_OPTION);
            if (respuesta != 1) {
                DefaultTableModel modelo = (DefaultTableModel) tablaDatos.getModel();
                modelo.removeRow(indice);
                calcularNumeroItem();
            }
        } else {
            JOptionPane.showMessageDialog(null, "SELECCIONE UNA FILA PARA RETIRAR\n");
        }
    }

    private void guardar() {
        if (rbCredito.isSelected()) {
            if (Double.compare(valorTotalDocumento, valorTotalDocumentoCuota) == 0) {
                int id = guardarCabecera();
                if (id != 0) {
                    guardarDetalle(id);
                    guardarCuotas(id);
                    JOptionPane.showMessageDialog(null, "COMPRA REGISTRADA EXITOSAMENTE...", "MENSAJE", JOptionPane.INFORMATION_MESSAGE);
                    cancelar();
                } else {
                    JOptionPane.showMessageDialog(null, "HA OCURRIDO UN ERROR AL MOMENTO DE GUARDAR LOS DATOS...", "ERROR", JOptionPane.ERROR_MESSAGE);
                    cancelar();
                }
            } else {
                JOptionPane.showMessageDialog(null, "EXISTEN DIFERENCIAS ENTRE LOS MONTOS DE LAS CUOTAS Y EL COMPROBANTE...", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            int id = guardarCabecera();
            if (id != 0) {
                guardarDetalle(id);
                JOptionPane.showMessageDialog(null, "COMPRA REGISTRADA EXITOSAMENTE...", "MENSAJE", JOptionPane.INFORMATION_MESSAGE);
                cancelar();
            } else {
                JOptionPane.showMessageDialog(null, "HA OCURRIDO UN ERROR AL MOMENTO DE GUARDAR LOS DATOS...", "ERROR", JOptionPane.ERROR_MESSAGE);
                cancelar();
            }
        }
    }

    private int guardarCabecera() {
        //VARIABLE PARA ALMACENAR MENSAJES
        String msj = "";
        //CAMPOS PARA LA BASE DE DATOS
        int id = dao.nuevoID();
        String numerodocumento = txtComprobante.getText();
        int numerotimbrado = Integer.parseInt(txtTimbrado.getText());
        Date fecha = txtFecha.getDate();
        java.sql.Date fechaSQL = new java.sql.Date(fecha.getTime());
        String observacion = txtObservacion.getText();

        int idMoneda = Integer.parseInt(txtCodigoMoneda.getText());
        int idDeposito = Integer.parseInt(txtCodigoDeposito.getText());
        int idtipomovimiento;
        if (rbContado.isSelected()) {
            idtipomovimiento = idtipomovimientocontado;
        } else {
            idtipomovimiento = idtipomovimientocredito;
        }
        int idProveedor = Integer.parseInt(txtCodigoProveedor.getText());
        int idUsuario = appLogin.IDUSUARIO;
        double totalneto = 0.0;
        double totaliva = 0.0;
        int idcuenta = 0;
        if (txtCodigoCuenta.getText().isEmpty()) {
            idcuenta = 0;
        } else {
            idcuenta = Integer.parseInt(txtCodigoCuenta.getText());
        }
        //VALIDACIONES
        if (fecha == null) {
            msj += "LA FECHA DE LA OPERACION NO PUEDE SER NULO.\n";
        }
        if (idProveedor == 0) {
            msj += "NO HA SELECCIONADO NINGUN PROVEEDOR.\n";
        }
        if (idMoneda == 0) {
            msj += "NO HA SELECCIONADO NINGUNA MONEDA.\n";
        }
        if (idDeposito == 0) {
            msj += "NO HA SELECCIONADO NINGUN DEPÓSITO.\n";
        }
        if (idtipomovimiento == 0) {
            msj += "NO HA SELECCIONADO NINGUN TIPO DE MOVIMIENTO.\n";
        }
        if (idUsuario == 0) {
            msj += "NO SE HA RECUPERADO NINGUN USUARIO.\n";
        }
        if (dao.verificarExistenciaCompra(numerodocumento, numerotimbrado) == true) {
            msj += "YA EXISTE UNA COMPRA CON EL NÙMERO DE DOCUMENTO Y EL TIMBRADO INGRESADO.\n";
        }
        if (rbContado.isSelected()) {
            if (idcuenta == 0) {
                msj += "NO SE HA SELECCIONADO NINGUNA CUENTA PARA LA COMPRA.\n";
            }
        }
        if (msj.isEmpty()) {
            c.setIdcompra(id);
            c.setNumerodocumento(numerodocumento);
            c.setNumerotimbrado(numerotimbrado);
            c.setFecha(fechaSQL);
            c.setObservacion(observacion);
            c.setIdmoneda(idMoneda);
            c.setIddeposito(idDeposito);
            c.setIdtipomovimiento(idtipomovimiento);
            c.setIdproveedor(idProveedor);
            c.setIdusuario(idUsuario);
            double costoFinal = 0.0;
            double ivaFinal = 0.0;
            for (int i = 0; i < tablaDatos.getRowCount(); i++) {
                double porcentaje = Double.parseDouble(tablaDatos.getValueAt(i, 7).toString());
                double costo = Double.parseDouble(tablaDatos.getValueAt(i, 6).toString());
                if (porcentaje == 0.0) {
                    ivaFinal = 0.0;
                    costoFinal = (costo);
                }
                if (porcentaje == 5.0) {
                    ivaFinal = costo / 21;
                    costoFinal = (costo - (costo / 21));
                }
                if (porcentaje == 10.0) {
                    ivaFinal = costo / 11;
                    costoFinal = (costo - (costo / 11));
                }
                if (idMoneda == 1) {
                    totaliva = totaliva + (Math.round(ivaFinal));
                    totalneto = totalneto + (Math.round(costoFinal));
                } else {
                    totaliva = totaliva + (Math.round(ivaFinal * 1000.0) / 1000.0);
                    totalneto = totalneto + (Math.round(costoFinal * 1000.0) / 1000.0);
                }
            }
            if (idMoneda == 1) {
                c.setTotalneto(Math.round(totalneto));
                c.setTotaliva(Math.round(totaliva));
            } else {
                c.setTotalneto(Math.round(totalneto * 1000.0) / 1000.0);
                c.setTotaliva(Math.round(totaliva * 1000.0) / 1000.0);
            }
            c.setIdcuenta(idcuenta);
            dao.agregar(c);
        }
        return id;
    }

    private void guardarDetalle(int id) {
        int idmoneda = Integer.parseInt(txtCodigoMoneda.getText());
        for (int i = 0; i < tablaDatos.getRowCount(); i++) {
            int numeroitem = Integer.parseInt(tablaDatos.getValueAt(i, 0).toString());
            int idarticulo = Integer.parseInt(tablaDatos.getValueAt(i, 2).toString());
            double cantidad = Double.parseDouble(tablaDatos.getValueAt(i, 5).toString());
            double porcentaje = Double.parseDouble(tablaDatos.getValueAt(i, 7).toString());
            double costo = Double.parseDouble(tablaDatos.getValueAt(i, 4).toString());
            cd.setIdcompra(id);
            cd.setNumero_item(numeroitem);
            cd.setIdarticulo(idarticulo);
            cd.setCantidad(cantidad);
            double costoFinal = 0.0;
            double ivaFinal = 0.0;
            if (porcentaje == 0.0) {
                ivaFinal = 0.0;
                costoFinal = (costo);
            }
            if (porcentaje == 5.0) {
                ivaFinal = costo / 21;
                costoFinal = (costo - (costo / 21));
            }
            if (porcentaje == 10.0) {
                ivaFinal = costo / 11;
                costoFinal = (costo - (costo / 11));
            }

            if (idmoneda == 1) {
                cd.setIva(Math.round(ivaFinal));
                cd.setCosto(Math.round(costoFinal));
            } else {
                cd.setIva(Math.round(ivaFinal * 1000.0) / 1000.0);
                cd.setCosto(Math.round(costoFinal * 1000.0) / 1000.0);
            }
            cd.setPorcentaje_iva(porcentaje);
            daoCompraDetalle.agregar(cd);
        }
    }

    private void guardarCuotas(int idcompra) {
        for (int i = 0; i < tablaCuotas.getRowCount(); i++) {
            int numeroCuota = Integer.parseInt(tablaCuotas.getValueAt(i, 0).toString());
            Date fechaVencimiento = daoCotizacion.parseFecha(tablaCuotas.getValueAt(i, 1).toString());
            java.sql.Date fechaSQL = new java.sql.Date(fechaVencimiento.getTime());
            double monto = Double.parseDouble(tablaCuotas.getValueAt(i, 2).toString());
            cc.setIdcompra(idcompra);
            cc.setNumero(numeroCuota);
            cc.setFechavencimiento(fechaSQL);
            cc.setMonto(monto);
            daoCompraCuota.agregar(cc);
        }
    }

    private void cancelar() {
        txtPuntoEmision.setText(null);
        txtEstablecimiento.setText(null);
        txtNumero.setText(null);
        txtComprobante.setText(null);
        txtTimbrado.setText(null);
        txtObservacion.setText(null);
        txtCodigoMoneda.setText(null);
        txtDescripcionMoneda.setText(null);
        txtCodigoDeposito.setText(null);
        txtDescripcionDeposito.setText(null);
        txtCodigoProveedor.setText(null);
        txtDescripcionProveedor.setText(null);
        txtCodigoCuenta.setText(null);
        txtDescripcionCuenta.setText(null);
        txtRucProveedor.setText(null);
        txtTotal.setText(null);
        DefaultTableModel modelo = (DefaultTableModel) tablaDatos.getModel();
        modelo.setRowCount(0);
        DefaultTableModel modeloCuotas = (DefaultTableModel) tablaCuotas.getModel();
        modeloCuotas.setRowCount(0);

        String fechaActual = formato.format(SYSDATE);
        txtFecha.setDate(daoCotizacion.parseFecha(fechaActual));
        rbContado.grabFocus();
    }

    private void calcularTotales() {
        int idmoneda = Integer.parseInt(txtCodigoMoneda.getText());
        DecimalFormat formatter;
        if (idmoneda == 1) {
            formatter = new DecimalFormat("#,###");
        } else {
            formatter = new DecimalFormat("#,###.000");
        }
        double TOTAL = 0.0;
        for (int i = 0; i < tablaDatos.getRowCount(); i++) {
            TOTAL = TOTAL + Double.parseDouble((tablaDatos.getValueAt(i, 6).toString()));
        }
        valorTotalDocumento = TOTAL;
        txtTotal.setText(formatter.format(TOTAL));
    }

    private void formularioCuotas() {
        PanelCuotas.setModal(true);
        PanelCuotas.setSize(514, 350);
        PanelCuotas.setLocationRelativeTo(this);
        PanelCuotas.setVisible(true);
    }

    private void agregarCuota() {
        String msj = "";
        int numeroCuota = Integer.parseInt(txtNumeroCuota.getText());
        Date fecha = txtVencimientoCuota.getDate();
        Double montoCuota = valorMontoCuota;

        if (msj.isEmpty()) {
            int i;
            boolean existe = false;
            for (i = 0; i < tablaCuotas.getRowCount(); i++) {
                int numeroCuotaTabla = Integer.parseInt(tablaCuotas.getValueAt(i, 0).toString());
                if (numeroCuotaTabla == numeroCuota) {
                    existe = true;
                    break;
                }
            }
            if (existe == false) {
                insertarCuota(numeroCuota, formato.format(fecha), montoCuota);
                calcularTotalCuota();
            }
        }
    }

    private void insertarCuota(int numeroCuota, String fechaVencimiento, double montoCuota) {
        if (montoCuota <= 0) {
            JOptionPane.showMessageDialog(null, "NO PUEDE AGREGAR UN PRODUCTO CON LA CANTIDAD 0");
        } else {
            if (valorTotalDocumentoCuota > valorTotalDocumento) {
                JOptionPane.showMessageDialog(null, "EL VALOR TOTAL DE LAS CUOTAS SUPERA AL MONTO TOTAL DEL COMPROBANTE");
            } else {
                if (montoCuota > valorTotalDocumento) {
                    JOptionPane.showMessageDialog(null, "EL VALOR DE LA CUOTA SUPERA EL MONTO TOTAL DEL COMPROBANTE");
                } else {
                    Object[] fila = new Object[3];
                    fila[0] = numeroCuota;
                    fila[1] = fechaVencimiento;
                    fila[2] = montoCuota;

                    DefaultTableModel modelo = (DefaultTableModel) tablaCuotas.getModel();
                    modelo.addRow(fila);
                    txtNumeroCuota.setText(null);
                    txtVencimientoCuota.setDate(null);
                    txtMontoCuota.setText(null);
                    txtNumeroCuota.grabFocus();
                }
            }
        }
    }

    private void calcularTotalCuota() {
        int idmoneda = Integer.parseInt(txtCodigoMoneda.getText());
        DecimalFormat formatter;
        if (idmoneda == 1) {
            formatter = new DecimalFormat("#,###");
        } else {
            formatter = new DecimalFormat("#,###.000");
        }
        double TOTAL = 0.0;
        for (int i = 0; i < tablaCuotas.getRowCount(); i++) {
            TOTAL = TOTAL + Double.parseDouble((tablaCuotas.getValueAt(i, 2).toString()));
        }
        valorTotalDocumentoCuota = TOTAL;
        txtTotalMontoCuotas.setText(formatter.format(TOTAL));
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
        jPanel4 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        txtCriterioMoneda = new org.jdesktop.swingx.JXTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaDatosMonedas = new javax.swing.JTable();
        grupoContadoCredito = new javax.swing.ButtonGroup();
        BuscadorDeposito = new javax.swing.JDialog();
        jPanel5 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        txtCriterioDeposito = new org.jdesktop.swingx.JXTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaDatosDepositos = new javax.swing.JTable();
        BuscadorProveedor = new javax.swing.JDialog();
        jPanel6 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        txtCriterioProveedor = new org.jdesktop.swingx.JXTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        tablaDatosProveedor = new javax.swing.JTable();
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
        PanelCuotas = new javax.swing.JDialog();
        jPanel10 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tablaCuotas = new javax.swing.JTable();
        txtTotalMontoCuotas = new org.jdesktop.swingx.JXTextField();
        jLabel20 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        txtNumeroCuota = new org.jdesktop.swingx.JXTextField();
        jLabel18 = new javax.swing.JLabel();
        txtVencimientoCuota = new org.jdesktop.swingx.JXDatePicker();
        jLabel19 = new javax.swing.JLabel();
        txtMontoCuota = new org.jdesktop.swingx.JXTextField();
        btnAgregarCuota = new javax.swing.JButton();
        btnCancelarCuota = new javax.swing.JButton();
        btnConfirmarCuota = new javax.swing.JButton();
        BuscadorCuenta = new javax.swing.JDialog();
        jPanel13 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        txtCriterioCuenta = new org.jdesktop.swingx.JXTextField();
        jScrollPane7 = new javax.swing.JScrollPane();
        tablaDatosCuenta = new javax.swing.JTable();
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
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtCodigoMoneda = new org.jdesktop.swingx.JXTextField();
        txtDescripcionMoneda = new org.jdesktop.swingx.JXTextField();
        txtCodigoDeposito = new org.jdesktop.swingx.JXTextField();
        txtDescripcionDeposito = new org.jdesktop.swingx.JXTextField();
        txtCodigoProveedor = new org.jdesktop.swingx.JXTextField();
        txtDescripcionProveedor = new org.jdesktop.swingx.JXTextField();
        jLabel9 = new javax.swing.JLabel();
        txtObservacion = new org.jdesktop.swingx.JXTextField();
        jLabel12 = new javax.swing.JLabel();
        txtRucProveedor = new org.jdesktop.swingx.JXTextField();
        txtCodigoCuenta = new org.jdesktop.swingx.JXTextField();
        txtDescripcionCuenta = new org.jdesktop.swingx.JXTextField();
        jLabel21 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        txtCodigoArticulo = new org.jdesktop.swingx.JXTextField();
        txtCantidad = new org.jdesktop.swingx.JXTextField();
        txtCosto = new org.jdesktop.swingx.JXTextField();
        txtDescripcionArticulo = new org.jdesktop.swingx.JXTextField();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaDatos = new javax.swing.JTable();
        btnAgregar = new javax.swing.JButton();
        btnRetirar = new javax.swing.JButton();
        btnConfirmar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        txtTotal = new org.jdesktop.swingx.JXTextField();
        jLabel15 = new javax.swing.JLabel();

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
            tablaDatosProveedor.getColumnModel().getColumn(2).setMinWidth(0);
            tablaDatosProveedor.getColumnModel().getColumn(2).setPreferredWidth(0);
            tablaDatosProveedor.getColumnModel().getColumn(2).setMaxWidth(0);
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

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));

        jLabel16.setBackground(new java.awt.Color(50, 104, 151));
        jLabel16.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("CUOTAS RELACIONADAS A LA COMPRA");
        jLabel16.setOpaque(true);

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tablaCuotas.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        tablaCuotas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "<html><p style=\"text-align:center\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Nº</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Fecha de Vencimiento</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Monto</span></span></span></p></html> "
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Double.class
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
        jScrollPane6.setViewportView(tablaCuotas);
        if (tablaCuotas.getColumnModel().getColumnCount() > 0) {
            tablaCuotas.getColumnModel().getColumn(0).setMinWidth(30);
            tablaCuotas.getColumnModel().getColumn(0).setPreferredWidth(30);
            tablaCuotas.getColumnModel().getColumn(0).setMaxWidth(30);
            tablaCuotas.getColumnModel().getColumn(2).setMinWidth(100);
            tablaCuotas.getColumnModel().getColumn(2).setPreferredWidth(100);
            tablaCuotas.getColumnModel().getColumn(2).setMaxWidth(100);
        }

        txtTotalMontoCuotas.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTotalMontoCuotas.setEnabled(false);
        txtTotalMontoCuotas.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtTotalMontoCuotas.setPrompt("0");

        jLabel20.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel20.setText("Total de la cuota:");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTotalMontoCuotas, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTotalMontoCuotas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));
        jPanel12.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel17.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("Nº de la cuota");

        txtNumeroCuota.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNumeroCuota.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtNumeroCuota.setPrompt("000");
        txtNumeroCuota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumeroCuotaActionPerformed(evt);
            }
        });
        txtNumeroCuota.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNumeroCuotaKeyTyped(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("Vencimiento");

        txtVencimientoCuota.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtVencimientoCuota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtVencimientoCuotaActionPerformed(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setText("Monto");

        txtMontoCuota.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtMontoCuota.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtMontoCuota.setPrompt("0");
        txtMontoCuota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMontoCuotaActionPerformed(evt);
            }
        });
        txtMontoCuota.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtMontoCuotaKeyTyped(evt);
            }
        });

        btnAgregarCuota.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        btnAgregarCuota.setText("Agregar");
        btnAgregarCuota.setOpaque(false);
        btnAgregarCuota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarCuotaActionPerformed(evt);
            }
        });

        btnCancelarCuota.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        btnCancelarCuota.setText("Cancelar");
        btnCancelarCuota.setOpaque(false);
        btnCancelarCuota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarCuotaActionPerformed(evt);
            }
        });

        btnConfirmarCuota.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        btnConfirmarCuota.setText("Confirmar");
        btnConfirmarCuota.setOpaque(false);
        btnConfirmarCuota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmarCuotaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(btnCancelarCuota, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnAgregarCuota, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtMontoCuota, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtVencimientoCuota, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                            .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtNumeroCuota, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(btnConfirmarCuota, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNumeroCuota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtVencimientoCuota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtMontoCuota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnAgregarCuota)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCancelarCuota)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnConfirmarCuota)
                .addContainerGap(12, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 10, Short.MAX_VALUE))
            .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout PanelCuotasLayout = new javax.swing.GroupLayout(PanelCuotas.getContentPane());
        PanelCuotas.getContentPane().setLayout(PanelCuotasLayout);
        PanelCuotasLayout.setHorizontalGroup(
            PanelCuotasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        PanelCuotasLayout.setVerticalGroup(
            PanelCuotasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
        jLabel1.setText("Movimiento de Compras");

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
        jLabel2.setText("Tipo de Compra:");

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

        jLabel6.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Depósito:");

        jLabel7.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Moneda:");

        jLabel8.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("Proveedor:");

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

        txtCodigoProveedor.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtCodigoProveedor.setPrompt("Cód. Prov.");
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

        jLabel9.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("Observación:");

        txtObservacion.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtObservacion.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtObservacion.setPrompt("Observaciónes correspondientes de la compra...");
        txtObservacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtObservacionActionPerformed(evt);
            }
        });
        txtObservacion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtObservacionKeyTyped(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel12.setText("R.U.C.:");

        txtRucProveedor.setEnabled(false);
        txtRucProveedor.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        txtRucProveedor.setPrompt("Registro Único del Contribuyente...");
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

        jLabel21.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel21.setText("Cuenta:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(rbContado)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rbCredito)
                                .addGap(185, 185, 185))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(txtFecha, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(225, 225, 225)))
                        .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                .addComponent(txtCodigoCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtDescripcionCuenta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(txtEstablecimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtPuntoEmision, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(txtTimbrado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNumero, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtComprobante, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
                                .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLabel9))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(txtCodigoMoneda, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtDescripcionMoneda, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(txtCodigoDeposito, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtDescripcionDeposito, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(txtCodigoProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtDescripcionProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtRucProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtObservacion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(rbContado)
                        .addComponent(rbCredito))
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCodigoMoneda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDescripcionMoneda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtEstablecimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPuntoEmision, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtComprobante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCodigoDeposito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDescripcionDeposito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTimbrado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCodigoProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDescripcionProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtRucProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtObservacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCodigoCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDescripcionCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        txtCodigoArticulo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCodigoArticulo.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        txtCodigoArticulo.setPrompt("Código del Artículo");
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

        txtCantidad.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCantidad.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        txtCantidad.setPrompt("Cantidad...");
        txtCantidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCantidadActionPerformed(evt);
            }
        });
        txtCantidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCantidadKeyTyped(evt);
            }
        });

        txtCosto.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCosto.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        txtCosto.setPrompt("Costo...");
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

        txtDescripcionArticulo.setEditable(false);
        txtDescripcionArticulo.setBackground(new java.awt.Color(255, 255, 255));
        txtDescripcionArticulo.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtDescripcionArticulo.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        txtDescripcionArticulo.setPrompt("Nombre o descripcion del Artículo...");
        txtDescripcionArticulo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDescripcionArticuloActionPerformed(evt);
            }
        });
        txtDescripcionArticulo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDescripcionArticuloKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtCodigoArticulo, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtDescripcionArticulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCosto, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCodigoArticulo, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCosto, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDescripcionArticulo, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tablaDatos.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        tablaDatos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "<html><p style=\"text-align:center\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Nº</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Referencia</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Código</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Artículo</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Costo</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Cantidad</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">Sub Total</span></span></span></p></html> ", "<html><p style=\"text-align:right\"><span style=\"color:#000066\"><span style=\"font-family:SansSerif\"><span style=\"font-size:10px\">%Iva</span></span></span></p></html> "
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tablaDatos);
        if (tablaDatos.getColumnModel().getColumnCount() > 0) {
            tablaDatos.getColumnModel().getColumn(0).setMinWidth(35);
            tablaDatos.getColumnModel().getColumn(0).setPreferredWidth(35);
            tablaDatos.getColumnModel().getColumn(0).setMaxWidth(35);
            tablaDatos.getColumnModel().getColumn(1).setMinWidth(150);
            tablaDatos.getColumnModel().getColumn(1).setPreferredWidth(150);
            tablaDatos.getColumnModel().getColumn(1).setMaxWidth(150);
            tablaDatos.getColumnModel().getColumn(2).setMinWidth(0);
            tablaDatos.getColumnModel().getColumn(2).setPreferredWidth(0);
            tablaDatos.getColumnModel().getColumn(2).setMaxWidth(0);
            tablaDatos.getColumnModel().getColumn(4).setMinWidth(100);
            tablaDatos.getColumnModel().getColumn(4).setPreferredWidth(100);
            tablaDatos.getColumnModel().getColumn(4).setMaxWidth(100);
            tablaDatos.getColumnModel().getColumn(5).setMinWidth(100);
            tablaDatos.getColumnModel().getColumn(5).setPreferredWidth(100);
            tablaDatos.getColumnModel().getColumn(5).setMaxWidth(100);
            tablaDatos.getColumnModel().getColumn(6).setMinWidth(100);
            tablaDatos.getColumnModel().getColumn(6).setPreferredWidth(100);
            tablaDatos.getColumnModel().getColumn(6).setMaxWidth(100);
            tablaDatos.getColumnModel().getColumn(7).setMinWidth(0);
            tablaDatos.getColumnModel().getColumn(7).setPreferredWidth(0);
            tablaDatos.getColumnModel().getColumn(7).setMaxWidth(0);
        }

        btnAgregar.setBackground(new java.awt.Color(235, 235, 235));
        btnAgregar.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        btnAgregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/icons8_plus_32px.png"))); // NOI18N
        btnAgregar.setText("Agregar");
        btnAgregar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAgregar.setOpaque(false);
        btnAgregar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });

        btnRetirar.setBackground(new java.awt.Color(235, 235, 235));
        btnRetirar.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        btnRetirar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/icons8_minus_32px.png"))); // NOI18N
        btnRetirar.setText("Retirar");
        btnRetirar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnRetirar.setOpaque(false);
        btnRetirar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnRetirar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRetirarActionPerformed(evt);
            }
        });

        btnConfirmar.setBackground(new java.awt.Color(235, 235, 235));
        btnConfirmar.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        btnConfirmar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/icons8_checked_32px.png"))); // NOI18N
        btnConfirmar.setText("Confirmar");
        btnConfirmar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnConfirmar.setOpaque(false);
        btnConfirmar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnConfirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmarActionPerformed(evt);
            }
        });

        btnCancelar.setBackground(new java.awt.Color(235, 235, 235));
        btnCancelar.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/icons8_cancel_32px.png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCancelar.setOpaque(false);
        btnCancelar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnSalir.setBackground(new java.awt.Color(235, 235, 235));
        btnSalir.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/icons8_shutdown_32px.png"))); // NOI18N
        btnSalir.setText("Salir");
        btnSalir.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSalir.setOpaque(false);
        btnSalir.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        txtTotal.setEditable(false);
        txtTotal.setBackground(new java.awt.Color(255, 255, 255));
        txtTotal.setForeground(new java.awt.Color(0, 153, 0));
        txtTotal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtTotal.setFont(new java.awt.Font("SansSerif", 0, 24)); // NOI18N
        txtTotal.setPrompt("0");

        jLabel15.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel15.setText("Total de la Compra:");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1109, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnAgregar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnRetirar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnConfirmar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnCancelar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(btnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnRetirar, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnConfirmar, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtTotal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

    private void txtFechaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFechaActionPerformed
        if (txtFecha.getDate() == null) {
            JOptionPane.showMessageDialog(null, "NO PUEDE DEJAR EL CAMPO DE FECHA VACIA", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else {
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
        if (txtTimbrado.getText().isEmpty()) {
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
        }
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
                        txtCodigoDeposito.grabFocus();
                    }
                } else {
                    txtDescripcionMoneda.setText(m.getDescripcion());
                    txtCodigoDeposito.grabFocus();
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

    private void txtCodigoDepositoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoDepositoActionPerformed
        if (txtCodigoDeposito.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "NO PUEDE DEJAR EL CAMPO DE DEPÓSITO VACIO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else {
            int iddeposito = Integer.parseInt(txtCodigoDeposito.getText());
            d.setIddeposito(iddeposito);
            boolean resultado = daoDeposito.consultarDatos(d);
            if (resultado == true) {
                txtDescripcionDeposito.setText(d.getDescripcion());
                txtCodigoProveedor.grabFocus();
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
                if (rbContado.isSelected()) {
                    txtCodigoCuenta.grabFocus();
                } else {
                    txtObservacion.grabFocus();
                }
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

    private void txtObservacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtObservacionActionPerformed
        txtCodigoArticulo.grabFocus();
    }//GEN-LAST:event_txtObservacionActionPerformed

    private void txtObservacionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtObservacionKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            evt.setKeyChar(Character.toUpperCase(c));
        }
        if (txtObservacion.getText().length() == 255) {
            evt.consume();
        }
    }//GEN-LAST:event_txtObservacionKeyTyped

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

    private void rbContadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbContadoActionPerformed
        txtCodigoCuenta.setEnabled(true);
        txtFecha.grabFocus();
    }//GEN-LAST:event_rbContadoActionPerformed

    private void rbCreditoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbCreditoActionPerformed
        txtCodigoCuenta.setEnabled(false);
        txtFecha.grabFocus();
    }//GEN-LAST:event_rbCreditoActionPerformed

    private void rbContadoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rbContadoKeyPressed
        if (evt.VK_ENTER == evt.getKeyCode()) {
            txtCodigoCuenta.setEnabled(true);
            txtFecha.grabFocus();
        }
    }//GEN-LAST:event_rbContadoKeyPressed

    private void rbCreditoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rbCreditoKeyPressed
        if (evt.VK_ENTER == evt.getKeyCode()) {
            txtCodigoCuenta.setEnabled(false);
            txtFecha.grabFocus();
        }
    }//GEN-LAST:event_rbCreditoKeyPressed

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

    private void txtCodigoArticuloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoArticuloActionPerformed
        if (txtCodigoArticulo.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "NO PUEDE DEJAR EL CAMPO DE CODIGO DEL ARTÍCULO VACIO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
            txtCodigoArticulo.setText(null);
            txtDescripcionArticulo.setText(null);
        } else {
            String criterio = txtCodigoArticulo.getText();
            boolean resultado = false;
            a.setCodigobarra(criterio);
            resultado = daoArticulo.busquedaArticulo(a);
            if (resultado == false) {
                a.setCodigoalfanumerico(criterio);
                resultado = daoArticulo.busquedaArticulo(a);
            }
            if (resultado == false) {
                a.setIdarticulo(Integer.parseInt(criterio));
                resultado = daoArticulo.busquedaArticulo(a);
            }
            if (resultado == true) {
                txtDescripcionArticulo.setText(a.getDescripcion());
                codigoarticulo = a.getIdarticulo();
                i.setIdimpuesto(a.getIdimpuesto());
                daoImpuesto.consultarDatos(i);
                porcentajeIva = i.getPorcentaje();
                a.setCodigobarra(null);
                a.setCodigoalfanumerico(null);
                a.setIdarticulo(0);
                /*OBTENER EL PERIODO*/
                int idperiodo = daoArticuloPeriodo.obtenerPeriodo(formatoUS.format(txtFecha.getDate()));
                /*OBTENER COSTO*/
                ap.setIdarticulo(codigoarticulo);
                ap.setIdperiodo(idperiodo);
                ap.setIdmoneda(Integer.parseInt(txtCodigoMoneda.getText()));
                daoArticuloPeriodo.consultarDatos(ap);
                txtCosto.setText("" + ap.getCosto());
                txtCosto.grabFocus();
                txtCosto.selectAll();
            } else {
                JOptionPane.showMessageDialog(null, "NO EXISTE EL CÓDIGO DEL ARTÍCULO INGRESADO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
                txtCodigoArticulo.setText(null);
                txtDescripcionArticulo.setText(null);
                a.setCodigobarra(null);
                a.setCodigoalfanumerico(null);
                a.setIdarticulo(0);
                txtCodigoArticulo.grabFocus();
            }
        }
    }//GEN-LAST:event_txtCodigoArticuloActionPerformed

    private void txtCodigoArticuloKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoArticuloKeyTyped
        if (txtCodigoArticulo.getText().length() == 50) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCodigoArticuloKeyTyped

    private void txtCantidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCantidadActionPerformed
        if (txtCantidad.getText().isEmpty()) {
            txtCantidad.setText("1");
            //JOptionPane.showMessageDialog(null, "NO PUEDE DEJAR EL CAMPO DE CANTIDAD VACIO", "ATENCIÓN", JOptionPane.WARNING_MESSAGE);
        } else {
            String number = txtCantidad.getText();
            double monto = Double.parseDouble(number);
            int idmoneda = Integer.parseInt(txtCodigoMoneda.getText());
            if (monto <= 0) {
                JOptionPane.showMessageDialog(null, "LA CANTIDAD NO PUEDE SER MENOR O IGUAL 0", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
            } else {
                valorCantidad = monto;
                DecimalFormat formatter;
                formatter = new DecimalFormat("#,###.000");
                txtCantidad.setText(formatter.format(monto));
                agregar();
            }
        }
    }//GEN-LAST:event_txtCantidadActionPerformed

    private void txtCantidadKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            getToolkit().beep();
            evt.consume();
        }
        if (c == ',') {
            getToolkit().beep();
            evt.consume();
        }
        if (txtCantidad.getText().length() == 24) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCantidadKeyTyped

    private void txtCostoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCostoActionPerformed
        if (txtCosto.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "NO PUEDE DEJAR EL CAMPO DE COSTO VACIO", "ATENCIÓN", JOptionPane.WARNING_MESSAGE);
        } else {
            String number = txtCosto.getText();
            double monto = Double.parseDouble(number);
            int idmoneda = Integer.parseInt(txtCodigoMoneda.getText());
            if (monto <= 0) {
                JOptionPane.showMessageDialog(null, "EL COSTO NO PUEDE SER MENOR O IGUAL 0", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
            } else {
                valorCosto = monto;
                DecimalFormat formatter;
                if (idmoneda == 1) {
                    formatter = new DecimalFormat("#,###");
                } else {
                    formatter = new DecimalFormat("#,###.000");
                }
                txtCosto.setText(formatter.format(monto));
                txtCantidad.grabFocus();
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
        if (txtCosto.getText().length() == 24) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCostoKeyTyped

    private void txtDescripcionArticuloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDescripcionArticuloActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescripcionArticuloActionPerformed

    private void txtDescripcionArticuloKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescripcionArticuloKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescripcionArticuloKeyTyped

    private void txtCriterioArticuloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCriterioArticuloActionPerformed
        cargarArticulo();
    }//GEN-LAST:event_txtCriterioArticuloActionPerformed

    private void txtCriterioArticuloKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCriterioArticuloKeyPressed
        if (evt.VK_ESCAPE == evt.getKeyCode()) {
            txtCodigoArticulo.setText(null);
            txtDescripcionArticulo.setText(null);
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

    private void txtCodigoArticuloKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoArticuloKeyPressed
        if (evt.VK_F1 == evt.getKeyCode()) {
            buscarArticulo();
        }
        if (evt.VK_F2 == evt.getKeyCode()) {
            btnConfirmar.grabFocus();
        }
    }//GEN-LAST:event_txtCodigoArticuloKeyPressed

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        String number = txtCantidad.getText();
        double monto = Double.parseDouble(number);
        if (monto <= 0) {
            JOptionPane.showMessageDialog(null, "LA CANTIDAD NO PUEDE SER MENOR O IGUAL 0", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else {
            valorCantidad = monto;
            agregar();
        }
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnRetirarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRetirarActionPerformed
        retirar();
    }//GEN-LAST:event_btnRetirarActionPerformed

    private void btnConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmarActionPerformed
        if (rbCredito.isSelected()) {
            formularioCuotas();
        }
        int respuesta;
        respuesta = JOptionPane.showConfirmDialog(null, "¿CONFIRMAR COMPRA?",
                "ADVERTENCIA", JOptionPane.YES_NO_OPTION);
        if (respuesta != 1) {
            guardar();
            rbContado.grabFocus();
        }
    }//GEN-LAST:event_btnConfirmarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        int respuesta;
        respuesta = JOptionPane.showConfirmDialog(null, "¿ESTA SEGURO DE CANCELAR LOS CAMBIOS?",
                "ADVERTENCIA", JOptionPane.YES_NO_OPTION);
        if (respuesta != 1) {
            cancelar();
        }
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        int respuesta;
        respuesta = JOptionPane.showConfirmDialog(null, "¿ESTA SEGURO DE SALIR DEL PROGRAMA?",
                "ADVERTENCIA", JOptionPane.YES_NO_OPTION);
        if (respuesta != 1) {
            dispose();
        }
    }//GEN-LAST:event_btnSalirActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        obtenerConfiguracion();
    }//GEN-LAST:event_formInternalFrameOpened

    private void txtNumeroCuotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumeroCuotaActionPerformed
        if (txtNumeroCuota.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "NO PUEDE DEJAR EL CAMPO DE NÚMERO DE CUOTA VACIO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else {
            int numeroCuota = Integer.parseInt(txtNumeroCuota.getText());
            int i;
            boolean existe = false;
            /* Calendar calendar = Calendar.getInstance();
            calendar.setTime(txtFecha.getDate());
            calendar.add(Calendar.MONTH, 1);*/
            if (numeroCuota > 0) {
                for (i = 0; i < tablaCuotas.getRowCount(); i++) {
                    int numeroCuotaTabla = Integer.parseInt(tablaCuotas.getValueAt(i, 0).toString());
                    if (numeroCuotaTabla == numeroCuota) {
                        existe = true;
                        break;
                    }
                }
                if (existe == true) {
                    JOptionPane.showMessageDialog(null, "YA EXISTE UNA CUOTA CON EL NUMERO INGRESADO EN LA TABLA DE CUOTAS.", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
                } else {
                    /*txtVencimientoCuota.setDate(calendar.getTime());*/
                    txtVencimientoCuota.grabFocus();
                }
            } else {
                JOptionPane.showMessageDialog(null, "NÚMERO DE CUOTA DEBE DE SER MAYOR A 0", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
            }
        }
    }//GEN-LAST:event_txtNumeroCuotaActionPerformed

    private void txtNumeroCuotaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumeroCuotaKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            getToolkit().beep();
            evt.consume();
        }
        if (txtNumeroCuota.getText().length() == 3) {
            evt.consume();
        }
    }//GEN-LAST:event_txtNumeroCuotaKeyTyped

    private void txtVencimientoCuotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtVencimientoCuotaActionPerformed
        if (txtVencimientoCuota.getDate() == null) {
            JOptionPane.showMessageDialog(null, "NO PUEDE DEJAR EL CAMPO DE LA FECHA DE VENCIMIENTO VACIA", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else {
            txtMontoCuota.grabFocus();
        }
    }//GEN-LAST:event_txtVencimientoCuotaActionPerformed

    private void btnAgregarCuotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarCuotaActionPerformed
        agregarCuota();
    }//GEN-LAST:event_btnAgregarCuotaActionPerformed

    private void btnCancelarCuotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarCuotaActionPerformed
        int respuesta;
        respuesta = JOptionPane.showConfirmDialog(null, "¿ESTA SEGURO DE CANCELAR LAS CUOTAS?",
                "ADVERTENCIA", JOptionPane.YES_NO_OPTION);
        if (respuesta != 1) {
            txtNumeroCuota.setText(null);
            txtVencimientoCuota.setDate(null);
            txtMontoCuota.setText(null);
            DefaultTableModel modelo = (DefaultTableModel) tablaCuotas.getModel();
            modelo.setRowCount(0);
        }
    }//GEN-LAST:event_btnCancelarCuotaActionPerformed

    private void btnConfirmarCuotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmarCuotaActionPerformed
        int respuesta;
        respuesta = JOptionPane.showConfirmDialog(null, "¿ESTA SEGURO DE CONFIRMAR LOS DATOS DE LAS CUOTAS?",
                "ADVERTENCIA", JOptionPane.YES_NO_OPTION);
        if (respuesta != 1) {
            PanelCuotas.dispose();
        }
    }//GEN-LAST:event_btnConfirmarCuotaActionPerformed

    private void txtMontoCuotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMontoCuotaActionPerformed
        if (txtMontoCuota.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "NO PUEDE DEJAR EL CAMPO DE MONTO DE LA CUOTA VACIO", "ATENCIÓN", JOptionPane.WARNING_MESSAGE);
        } else {
            String number = txtMontoCuota.getText();
            double monto = Double.parseDouble(number);
            int idmoneda = Integer.parseInt(txtCodigoMoneda.getText());
            if (monto <= 0) {
                JOptionPane.showMessageDialog(null, "EL MONTO DE LA CUOTA NO PUEDE SER MENOR O IGUAL 0", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
            } else {
                valorMontoCuota = monto;
                DecimalFormat formatter;
                if (idmoneda == 1) {
                    formatter = new DecimalFormat("#,###");
                } else {
                    formatter = new DecimalFormat("#,###.000");
                }
                txtMontoCuota.setText(formatter.format(valorMontoCuota));
                btnAgregarCuota.grabFocus();
            }
        }
    }//GEN-LAST:event_txtMontoCuotaActionPerformed

    private void txtMontoCuotaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMontoCuotaKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            getToolkit().beep();
            evt.consume();
        }
        if (c == ',') {
            getToolkit().beep();
            evt.consume();
        }
        if (txtMontoCuota.getText().length() == 24) {
            evt.consume();
        }
    }//GEN-LAST:event_txtMontoCuotaKeyTyped

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
                    txtObservacion.grabFocus();
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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDialog BuscadorArticulo;
    private javax.swing.JDialog BuscadorCuenta;
    private javax.swing.JDialog BuscadorDeposito;
    private javax.swing.JDialog BuscadorMoneda;
    private javax.swing.JDialog BuscadorProveedor;
    private javax.swing.JDialog PanelCuotas;
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnAgregarCuota;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnCancelarCuota;
    private javax.swing.JButton btnConfirmar;
    private javax.swing.JButton btnConfirmarCuota;
    private javax.swing.JButton btnRetirar;
    private javax.swing.JButton btnSalir;
    private javax.swing.ButtonGroup grupoContadoCredito;
    private javax.swing.ButtonGroup grupoFiltroArticulo;
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
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JRadioButton rbCodAlfanumerico;
    private javax.swing.JRadioButton rbCodBarra;
    private javax.swing.JRadioButton rbContado;
    private javax.swing.JRadioButton rbCredito;
    private javax.swing.JRadioButton rbDescripcion;
    private javax.swing.JRadioButton rbReferencia;
    private javax.swing.JTable tablaCuotas;
    private javax.swing.JTable tablaDatos;
    private javax.swing.JTable tablaDatosArticulo;
    private javax.swing.JTable tablaDatosCuenta;
    private javax.swing.JTable tablaDatosDepositos;
    private javax.swing.JTable tablaDatosMonedas;
    private javax.swing.JTable tablaDatosProveedor;
    private org.jdesktop.swingx.JXTextField txtCantidad;
    private org.jdesktop.swingx.JXTextField txtCodigoArticulo;
    private org.jdesktop.swingx.JXTextField txtCodigoCuenta;
    private org.jdesktop.swingx.JXTextField txtCodigoDeposito;
    private org.jdesktop.swingx.JXTextField txtCodigoMoneda;
    private org.jdesktop.swingx.JXTextField txtCodigoProveedor;
    private org.jdesktop.swingx.JXTextField txtComprobante;
    private org.jdesktop.swingx.JXTextField txtCosto;
    private org.jdesktop.swingx.JXTextField txtCriterioArticulo;
    private org.jdesktop.swingx.JXTextField txtCriterioCuenta;
    private org.jdesktop.swingx.JXTextField txtCriterioDeposito;
    private org.jdesktop.swingx.JXTextField txtCriterioMoneda;
    private org.jdesktop.swingx.JXTextField txtCriterioProveedor;
    private org.jdesktop.swingx.JXTextField txtDescripcionArticulo;
    private org.jdesktop.swingx.JXTextField txtDescripcionCuenta;
    private org.jdesktop.swingx.JXTextField txtDescripcionDeposito;
    private org.jdesktop.swingx.JXTextField txtDescripcionMoneda;
    private org.jdesktop.swingx.JXTextField txtDescripcionProveedor;
    private org.jdesktop.swingx.JXTextField txtEstablecimiento;
    private org.jdesktop.swingx.JXDatePicker txtFecha;
    private org.jdesktop.swingx.JXTextField txtMontoCuota;
    private org.jdesktop.swingx.JXTextField txtNumero;
    private org.jdesktop.swingx.JXTextField txtNumeroCuota;
    private org.jdesktop.swingx.JXTextField txtObservacion;
    private org.jdesktop.swingx.JXTextField txtPuntoEmision;
    private org.jdesktop.swingx.JXTextField txtRucProveedor;
    private org.jdesktop.swingx.JXTextField txtTimbrado;
    private org.jdesktop.swingx.JXTextField txtTotal;
    private org.jdesktop.swingx.JXTextField txtTotalMontoCuotas;
    private org.jdesktop.swingx.JXDatePicker txtVencimientoCuota;
    // End of variables declaration//GEN-END:variables
}
