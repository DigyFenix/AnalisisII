package Views;

import Views.Utils.VBusqueda;
import Controladores.DaoDepartamento;
import Controladores.DaoEmpleado;
import Controladores.DaoIngresoEgreso;
import Controladores.DaoMovimiento;
import DigyFenix.*;
import Enums.BusquedaFrame;
import Enums.InsertUpdate;
import Modelos.Empleado;
import Modelos.IngresoEgreso;
import Modelos.Movimiento;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author echacon
 */
public class VIngresoEgreso extends javax.swing.JInternalFrame {

    HashMap<String, Object> mapFields = new HashMap<>();
    IngresoEgreso ineg;
    DaoIngresoEgreso daoIngEg;
    Empleado empleado;
    Movimiento mov;

    Calendar cal = Calendar.getInstance();

    /**
     * Creates new form v_empresa
     */
    public VIngresoEgreso() {
        initComponents();

        daoIngEg = new DaoIngresoEgreso();

        empleado = new Empleado();
        mov = new Movimiento();

        //Inicio las variables
        nuevaIngresoEgreso();

        //Configuro los botonos
        DFUtils.setColorABotton(btnGuardar);
        DFUtils.setColorABotton(btnMovBusqueda);
        DFUtils.setColorABotton(btnEmpBusqueda);
        DFUtils.setColorABotton(btnNuevo);

        mapearFiels();

        llenarTablaIngresoEgresos();

        ineg.limpiarFields(true);
        ineg.fieldsActivados(false);

    }

    private void mapearFiels() {
        //System.out.println("mapearFiels:" + DFUtils.getMapearFiels(ineg.getVariables()));
        mapFields.put("fecha", txbFecha);
        mapFields.put("monto", txbMonto);
        mapFields.put("cantidad", txbCantidad);
        ineg.mapearFields(mapFields);
    }

    public void busquedaFinalizada(Object obj) {
        try {
            if (obj instanceof Empleado) {
                empleado = (Empleado) obj;
                txbEmpleado.setText(empleado.getNombre() + " - " + (new DaoDepartamento().buscarPorId(empleado.getIddepartamento()).getDescripcion()));
                txbMovimiento.requestFocus();
            } else if (obj instanceof Movimiento) {
                mov = (Movimiento) obj;
                txbMovimiento.setText(mov.getDescripcion() + " - " + mov.getMonto());
                txbMonto.setText(mov.getMonto().toString());
                txbCantidad.requestFocus();
            }
        } catch (Exception e) {
            DFOptionPane.mostrar_Error(e.getMessage());
        }

    }

    private void guardar() {
        if (empleado.getIdempleado() != null && mov.getIdrhMovimiento() != null) {

            try {

                ineg.setMonto(Float.parseFloat(txbMonto.getText()));
                ineg.setFecha(DFDate.get_fechaDate(txbFecha.getText()));
                boolean nueva = (ineg.getIdrhIngresoEgreso() == null);
                //Primero se guardan los datos auxiliares para actualizar ids

                ineg.setIdempleado(empleado.getIdempleado());
                ineg.setIdmovimiento(mov.getIdrhMovimiento());

                String tipo = "";
                if (nueva) {
                    daoIngEg.insertarActualizar(ineg, InsertUpdate.INSERTAR);
                    tipo = "insertado";
                } else {
                    daoIngEg.insertarActualizar(ineg, InsertUpdate.ACTUALIZAR);
                    tipo = "actualizado";
                }
                DFOptionPane.mostrar_Realizado(ineg.getClass().getSimpleName() + " " + tipo + " corretamente");
                nuevaIngresoEgreso();
                llenarTablaIngresoEgresos();
            } catch (Exception e) {
                DFOptionPane.mostrar_Error(e.toString());
            }

        } else {
            DFOptionPane.mostrar_Error("Debe igresar el empleado y el movimiento ");
        }
    }

    private void nuevaIngresoEgreso() {
        try {
            ineg = new IngresoEgreso();
            ineg.mapearFields(mapFields);
            ineg.limpiarFields(true);
            txbEmpleado.requestFocus();
            txbFecha.setText(DFDate.get_fecha(cal.getTime()));
            ineg.fieldsActivados(true);
        } catch (Exception e) {
            DFOptionPane.mostrar_Error(e.toString());
        }
    }

    private void llenarTablaIngresoEgresos() {
        String encabezado[] = {"Id", "Empleado", "Movimiento", "Monto Mov", "Cantidad", "Fecha"};
        List<IngresoEgreso> list = daoIngEg.listar();
        List<String[]> rows = new ArrayList<>();
        list.forEach((t) -> {
            try {
                Empleado emp = new DaoEmpleado().buscarPorId(t.getIdempleado());
                String row[] = {"" + t.getIdrhIngresoEgreso(),
                    emp.getNombre() + " " + emp.getApellido(),
                    new DaoMovimiento().buscarPorId(t.getIdmovimiento()).getDescripcion(),
                    "" + t.getMonto(),
                    "" + t.getCantidad(),
                    DFDate.get_fecha(t.getFecha())
                };
                rows.add(row);
            } catch (Exception e) {
                System.out.println("Error: " + e.toString());
            }
        });

        DFTable.llenarTablaCol0NoEditable(tblNominas, encabezado, rows);
    }

    private void cargarDetalle(String id) {
        try {
            ineg = daoIngEg.buscarPorId(Integer.parseInt(id));
            this.empleado = new DaoEmpleado().buscarPorId(ineg.getIdempleado());
            txbEmpleado.setText(empleado.getNombre() + " - " + (new DaoDepartamento().buscarPorId(empleado.getIddepartamento()).getDescripcion()));

            this.mov = new DaoMovimiento().buscarPorId(ineg.getIdmovimiento());
            txbMovimiento.setText(mov.getDescripcion() + " - " + mov.getMonto());

            ineg.mapearFields(mapFields);
            ineg.llenarFields();
            ineg.fieldsActivados(true);
        } catch (Exception e) {
            DFOptionPane.mostrar_Error(e.toString());
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

        txbMonto = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        btnGuardar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblNominas = new javax.swing.JTable();
        tblNominas.getSelectionModel().addListSelectionListener( 
            new ListSelectionListener(){
                public void valueChanged(ListSelectionEvent event){
                    if(!event.getValueIsAdjusting() && (tblNominas.getSelectedRow() >= 0)){
                        cargarDetalle(""+tblNominas.getValueAt(tblNominas.getSelectedRow(), 0));
                    }
                }
            }
        );
        btnMovBusqueda = new javax.swing.JButton();
        txbEmpleado = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txbFecha = new javax.swing.JTextField();
        txbMovimiento = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        btnNuevo = new javax.swing.JButton();
        btnEmpBusqueda = new javax.swing.JButton();
        txbCantidad = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setResizable(true);
        setTitle("Movimientos de ingreso o egreso del empleado");
        setMaximumSize(new java.awt.Dimension(721, 377));
        setMinimumSize(new java.awt.Dimension(721, 377));
        setName(""); // NOI18N
        setPreferredSize(new java.awt.Dimension(721, 377));
        getContentPane().setLayout(null);

        txbMonto.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        getContentPane().add(txbMonto);
        txbMonto.setBounds(400, 210, 80, 30);

        jLabel2.setText("Monto");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(400, 190, 80, 20);

        btnGuardar.setBackground(new java.awt.Color(0, 153, 102));
        btnGuardar.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnGuardar.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardar.setText("Guardar");
        btnGuardar.setBorder(null);
        btnGuardar.setBorderPainted(false);
        btnGuardar.setContentAreaFilled(false);
        btnGuardar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGuardar.setOpaque(false);
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        getContentPane().add(btnGuardar);
        btnGuardar.setBounds(570, 260, 110, 40);

        tblNominas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(tblNominas);

        getContentPane().add(jScrollPane2);
        jScrollPane2.setBounds(10, 10, 690, 160);

        btnMovBusqueda.setBackground(new java.awt.Color(0, 153, 102));
        btnMovBusqueda.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnMovBusqueda.setForeground(new java.awt.Color(255, 255, 255));
        btnMovBusqueda.setText("B");
        btnMovBusqueda.setBorder(null);
        btnMovBusqueda.setBorderPainted(false);
        btnMovBusqueda.setContentAreaFilled(false);
        btnMovBusqueda.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnMovBusqueda.setOpaque(false);
        btnMovBusqueda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMovBusquedaActionPerformed(evt);
            }
        });
        getContentPane().add(btnMovBusqueda);
        btnMovBusqueda.setBounds(10, 270, 40, 30);
        getContentPane().add(txbEmpleado);
        txbEmpleado.setBounds(50, 210, 320, 30);

        jLabel6.setText("Empleado");
        getContentPane().add(jLabel6);
        jLabel6.setBounds(50, 190, 70, 20);

        jLabel3.setText("Fecha");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(590, 190, 80, 20);

        txbFecha.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        getContentPane().add(txbFecha);
        txbFecha.setBounds(590, 210, 110, 30);
        getContentPane().add(txbMovimiento);
        txbMovimiento.setBounds(50, 270, 320, 30);

        jLabel7.setText("Movimiento");
        getContentPane().add(jLabel7);
        jLabel7.setBounds(50, 250, 70, 20);

        btnNuevo.setBackground(new java.awt.Color(0, 153, 102));
        btnNuevo.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnNuevo.setForeground(new java.awt.Color(255, 255, 255));
        btnNuevo.setText("Nuevo");
        btnNuevo.setBorder(null);
        btnNuevo.setBorderPainted(false);
        btnNuevo.setContentAreaFilled(false);
        btnNuevo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNuevo.setOpaque(false);
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });
        getContentPane().add(btnNuevo);
        btnNuevo.setBounds(410, 260, 120, 40);

        btnEmpBusqueda.setBackground(new java.awt.Color(0, 153, 102));
        btnEmpBusqueda.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnEmpBusqueda.setForeground(new java.awt.Color(255, 255, 255));
        btnEmpBusqueda.setText("B");
        btnEmpBusqueda.setBorder(null);
        btnEmpBusqueda.setBorderPainted(false);
        btnEmpBusqueda.setContentAreaFilled(false);
        btnEmpBusqueda.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEmpBusqueda.setOpaque(false);
        btnEmpBusqueda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEmpBusquedaActionPerformed(evt);
            }
        });
        getContentPane().add(btnEmpBusqueda);
        btnEmpBusqueda.setBounds(10, 210, 40, 30);

        txbCantidad.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        getContentPane().add(txbCantidad);
        txbCantidad.setBounds(490, 210, 80, 30);

        jLabel4.setText("Cantidad");
        getContentPane().add(jLabel4);
        jLabel4.setBounds(490, 190, 80, 20);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        guardar();
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnMovBusquedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMovBusquedaActionPerformed

        if (txbCantidad.isEnabled()) {
            new VBusqueda(null, this, BusquedaFrame.VINGRESOEGRESO, mov);
        } else {
            DFOptionPane.mostrar_Error("Inicie un nuevo registro.");
        }

    }//GEN-LAST:event_btnMovBusquedaActionPerformed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        nuevaIngresoEgreso();
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnEmpBusquedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEmpBusquedaActionPerformed
        new VBusqueda(null, this, BusquedaFrame.VINGRESOEGRESO, empleado);
    }//GEN-LAST:event_btnEmpBusquedaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEmpBusqueda;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnMovBusqueda;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblNominas;
    private javax.swing.JTextField txbCantidad;
    private javax.swing.JTextField txbEmpleado;
    private javax.swing.JTextField txbFecha;
    private javax.swing.JTextField txbMonto;
    private javax.swing.JTextField txbMovimiento;
    // End of variables declaration//GEN-END:variables

}
