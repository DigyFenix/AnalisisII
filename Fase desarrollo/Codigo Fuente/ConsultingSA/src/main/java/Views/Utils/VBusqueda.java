package Views.Utils;

import Controladores.DaoEmpleado;
import Controladores.DaoEmpresa;
import Controladores.DaoMovimiento;
import Controladores.DaoNomina;
import Controladores.DaoPeriodo;
import DigyFenix.DFDate;
import DigyFenix.DFOptionPane;
import DigyFenix.DFTable;
import DigyFenix.DFUtils;
import Enums.BusquedaFrame;
import Modelos.Empleado;
import Modelos.Movimiento;
import Modelos.Nomina;
import Modelos.Periodo;
import Views.Reports.VReportEmpleados;
import Views.Reports.VReportNomina;
import Views.VGeneraNomina;
import Views.VIngresoEgreso;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JInternalFrame;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author echacon
 */
public class VBusqueda extends javax.swing.JDialog {

    private final String EMPLEADO = "EMPLEADO", MOVIMIENTO = "MOVIMIENTO", PERIODO = "PERIODO", NOMINA = "NOMINA";

    //Objetos que vienen del frame que solicita la busqueda
    Object obj;
    JInternalFrame frame;

    DaoEmpleado daoEmpl;
    DaoNomina daoNom;
    DaoMovimiento daoMovs;
    String tipo = "";
    BusquedaFrame tipoFrame;
    DaoPeriodo daoPer;

    /**
     * Creates new form VBusqueda
     *
     * @param parent
     * @param modal
     * @param obj
     * @param frame
     * @param tipoFrame
     */
    public VBusqueda(java.awt.Frame parent, JInternalFrame frame, BusquedaFrame tipoFrame, Object obj) {
        super(parent, true);
        initComponents();
        this.obj = obj;
        this.frame = frame;
        this.tipoFrame = tipoFrame;

        if (obj instanceof Empleado) {
            tipo = EMPLEADO;
        } else if (obj instanceof Movimiento) {
            tipo = MOVIMIENTO;
        } else if (obj instanceof Periodo) {
            tipo = PERIODO;
        } else if (obj instanceof Nomina) {
            tipo = NOMINA;
        }
        jLabel1.setForeground(DFUtils.COLOR_PRIMARIO);
        lblObjeto.setText(tipo);
        setLocationRelativeTo(null);
        DFUtils.setColorABotton(btnBuscar);
        DFUtils.setColorABotton(btnSeleccionar);
        this.setVisible(true);
    }

    private void procesar(String texto, String tipoProceso) {
        boolean buscar = tipoProceso.equals("buscar");
        try {
            switch (tipo) {
                case EMPLEADO: {
                    if (buscar) {
                        buscarEmpleado(texto);
                    } else {
                        obj = daoEmpl.buscarPorId(Integer.parseInt(texto));
                    }
                    break;
                }

                case MOVIMIENTO: {
                    if (buscar) {
                        buscarMovimiento(texto);
                    } else {
                        obj = daoMovs.buscarPorId(Integer.parseInt(texto));
                    }
                    break;
                }
                case PERIODO: {
                    if (buscar) {
                        buscarPeriodo(texto);
                    } else {
                        obj = daoPer.buscarPorId(Integer.parseInt(texto));
                    }
                    break;
                }
                case NOMINA: {
                    if (buscar) {
                        buscarNomina(texto);
                    } else {
                        obj = daoNom.buscarPorId(Integer.parseInt(texto));
                    }
                    break;
                }
            }
        } catch (Exception e) {
            DFOptionPane.mostrar_Error(e.toString());
        }
    }

    private void seleccionar() {
        switch (tipoFrame) {
            case VINGRESOEGRESO: {
                ((VIngresoEgreso) frame).busquedaFinalizada(obj);
                break;
            }
            case VGENERANOMINA: {
                ((VGeneraNomina) frame).busquedaFinalizada(obj);
                break;
            }

            case VREPORTE_NOMINA: {
                ((VReportNomina) frame).busquedaFinalizada(obj);
                break;
            }
            case VREPORTE_EMPLEADOS: {
                ((VReportEmpleados) frame).busquedaFinalizada(obj);
                break;
            }
        }
        this.dispose();
    }

    private void buscarEmpleado(String nombre) {
        daoEmpl = new DaoEmpleado();
        String encabezado[] = {"Id", "Nombre", "Direccion"};
        List<Empleado> list = daoEmpl.buscarPorNombre(nombre);
        resultados(list.size());
        List<String[]> rows = new ArrayList<>();
        list.forEach((t) -> {
            String row[] = {"" + t.getIdempleado(), t.getNombre(), t.getDireccion() + " - " + t.getMunicipio()};
            rows.add(row);
        });
        DFTable.llenarTablaCol0NoEditable(tblResultado, encabezado, rows);
    }

    private void buscarNomina(String nombre) {
        daoNom = new DaoNomina();
        String encabezado[] = {"Id", "Empresa", "Codigo", "Descripcion"};
        List<Nomina> list = daoNom.buscarPorDescripcionLike(nombre);
        resultados(list.size());
        List<String[]> rows = new ArrayList<>();
        list.forEach((t) -> {
            String row[] = {"" + t.getIdrhNomina(), new DaoEmpresa().buscarPorId(t.getIdempresa()).getDescripcion(), t.getCodigoNomina(), t.getDescripcion()};
            rows.add(row);
        });
        DFTable.llenarTablaCol0NoEditable(tblResultado, encabezado, rows);
    }

    private void buscarPeriodo(String descri) {
        daoPer = new DaoPeriodo();
        String encabezado[] = {"Id", "Nomina", "Descripcion", "Fecha Inicio", "Fecha Fin"};
        List<Periodo> list = daoPer.buscarPorDescripcion(descri);
        resultados(list.size());
        List<String[]> rows = new ArrayList<>();
        list.forEach((t) -> {
            try {

                Nomina nom = new DaoNomina().buscarPorId(t.getIdnomina());
                String row[] = {"" + t.getIdrhPeriodo(), nom.getCodigoNomina() + " - " + nom.getDescripcion(), t.getDescripcion(),
                    DFDate.get_fecha(t.getFechaInicio()),
                    DFDate.get_fecha(t.getFechaFin()),};
                rows.add(row);
            } catch (Exception e) {
            }

        });
        DFTable.llenarTablaCol0NoEditable(tblResultado, encabezado, rows);
    }

    private void buscarMovimiento(String nombre) {
        daoMovs = new DaoMovimiento();
        String encabezado[] = {"Id", "Descripcion", "Monto"};
        List<Movimiento> list = daoMovs.buscarPorDescripcionLike(nombre);
        resultados(list.size());
        List<String[]> rows = new ArrayList<>();
        list.forEach((t) -> {
            String row[] = {"" + t.getIdrhMovimiento(), t.getDescripcion(), "" + t.getMonto()};
            rows.add(row);
        });
        DFTable.llenarTablaCol0NoEditable(tblResultado, encabezado, rows);
    }

    private void cargarDetalle(String id) {
        procesar(id, "seleccionar");
    }

    private void resultados(int total) {
        if (total == 0) {
            lblAdvertencia.setText("Sin resultados");
            lblAdvertencia.setForeground(new java.awt.Color(102, 0, 0));
        } else {
            lblAdvertencia.setText(total + " resultados");
            lblAdvertencia.setForeground(DFUtils.COLOR_PRIMARIO);
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

        jLabel1 = new javax.swing.JLabel();
        lblObjeto = new javax.swing.JLabel();
        txbBusqueda = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblResultado = new javax.swing.JTable();
        tblResultado.getSelectionModel().addListSelectionListener( 
            new ListSelectionListener(){
                public void valueChanged(ListSelectionEvent event){
                    if(!event.getValueIsAdjusting() && (tblResultado.getSelectedRow() >= 0)){
                        cargarDetalle(""+tblResultado.getValueAt(tblResultado.getSelectedRow(), 0));
                    }
                }
            }
        );
        lblAdvertencia = new javax.swing.JLabel();
        btnSeleccionar = new javax.swing.JButton();
        btnBuscar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(536, 350));
        setModal(true);
        getContentPane().setLayout(null);

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel1.setText("Cuadro de busquedas, buscando:");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(10, 10, 250, 20);

        lblObjeto.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblObjeto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        getContentPane().add(lblObjeto);
        lblObjeto.setBounds(270, 10, 150, 20);

        txbBusqueda.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        txbBusqueda.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txbBusquedaKeyReleased(evt);
            }
        });
        getContentPane().add(txbBusqueda);
        txbBusqueda.setBounds(10, 40, 410, 30);

        tblResultado.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tblResultado);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(10, 90, 500, 120);

        lblAdvertencia.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblAdvertencia.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        getContentPane().add(lblAdvertencia);
        lblAdvertencia.setBounds(130, 280, 260, 20);

        btnSeleccionar.setBackground(new java.awt.Color(0, 153, 102));
        btnSeleccionar.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnSeleccionar.setForeground(new java.awt.Color(255, 255, 255));
        btnSeleccionar.setText("Seleccionar");
        btnSeleccionar.setBorder(null);
        btnSeleccionar.setBorderPainted(false);
        btnSeleccionar.setContentAreaFilled(false);
        btnSeleccionar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSeleccionar.setOpaque(false);
        btnSeleccionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSeleccionarActionPerformed(evt);
            }
        });
        getContentPane().add(btnSeleccionar);
        btnSeleccionar.setBounds(130, 220, 260, 50);

        btnBuscar.setBackground(new java.awt.Color(0, 153, 102));
        btnBuscar.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnBuscar.setForeground(new java.awt.Color(255, 255, 255));
        btnBuscar.setText("Buscar");
        btnBuscar.setBorder(null);
        btnBuscar.setBorderPainted(false);
        btnBuscar.setContentAreaFilled(false);
        btnBuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnBuscar.setOpaque(false);
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });
        getContentPane().add(btnBuscar);
        btnBuscar.setBounds(430, 40, 80, 31);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txbBusquedaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txbBusquedaKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            procesar(txbBusqueda.getText().toLowerCase(), "buscar");
        }
    }//GEN-LAST:event_txbBusquedaKeyReleased

    private void btnSeleccionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSeleccionarActionPerformed
        seleccionar();
    }//GEN-LAST:event_btnSeleccionarActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        procesar(txbBusqueda.getText().toLowerCase(), "buscar");
    }//GEN-LAST:event_btnBuscarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VBusqueda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VBusqueda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VBusqueda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VBusqueda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnSeleccionar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblAdvertencia;
    private javax.swing.JLabel lblObjeto;
    private javax.swing.JTable tblResultado;
    private javax.swing.JTextField txbBusqueda;
    // End of variables declaration//GEN-END:variables
}
