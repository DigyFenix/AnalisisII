package Views;

import Controladores.DaoEmpresa;
import DigyFenix.*;
import Enums.InsertUpdate;
import Modelos.Empresa;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author echacon
 */
public class VEmpresa extends javax.swing.JInternalFrame {

    HashMap<String, Object> mapFields = new HashMap<>();
    Empresa emp;
    String rutaImagenTemp;
    DaoEmpresa daoEmp;

    /**
     * Creates new form v_empresa
     */
    public VEmpresa() {
        initComponents();

        //Inicio las variables
        nuevaEmpresa();
        daoEmp = new DaoEmpresa();

        //Configuro los botonos
        DFUtils.setColorABotton(btnGuardar);
        DFUtils.setColorABotton(btnNuevo);

        //generarSwitchMap();
        mapearFiels();

        llenarTablaEmpresas();

        emp.limpiarFields(true);
        emp.fieldsActivados(false);
    }

    private void mapearFiels() {
        //System.out.println("mapearFiels:" + DFUtils.getMapearFiels(emp.getVariables()));
        mapFields.put("descripcion", txbDescripcion);
        mapFields.put("nit", txbNit);
        mapFields.put("direccion", txbaDireccion);
        mapFields.put("activo", cbxActivo);
        emp.mapearFields(mapFields);
    }

    private void generarSwitchMap() {
        //System.out.println("generarSwitchMap: " + DFUtils.getGenerarSwitchMap(emp.getVariables()));
        HashMap<String, DFMvcEnum> mapFields = new HashMap<>();
        mapFields.put("descripcion", DFMvcEnum.STRING);
        mapFields.put("nit", DFMvcEnum.STRING);
        mapFields.put("direccion", DFMvcEnum.STRING);
        mapFields.put("activo", DFMvcEnum.STRING);
        //System.out.println(DFUtils.getSwitchParaMvc(mapFields));
    }

    private void guardar() {
        try {
            boolean nueva = (emp.getIdrhEmpresa() == null);
            //Primero se guardan los datos auxiliares para actualizar ids

            String tipo = "";
            if (nueva) {
                daoEmp.insertarActualizar(emp, InsertUpdate.INSERTAR);
                tipo = "insertada";
            } else {
                daoEmp.insertarActualizar(emp, InsertUpdate.ACTUALIZAR);
                tipo = "actualizada";
            }
            llenarTablaEmpresas();
            DFOptionPane.mostrar_Realizado(emp.getClass().getSimpleName() + " " + tipo + " corretamente");
        } catch (Exception e) {
            DFOptionPane.mostrar_Error(e.toString());
        }
    }

    private void nuevaEmpresa() {
        emp = new Empresa();
        emp.mapearFields(mapFields);
        emp.limpiarFields(true);
        txbDescripcion.requestFocus();
        emp.fieldsActivados(true);
    }

    private void llenarTablaEmpresas() {
        String encabezado[] = {"Id", "Nombre"};
        List<Empresa> list = daoEmp.listar();
        List<String[]> rows = new ArrayList<>();
        list.forEach((t) -> {
            String row[] = {"" + t.getIdrhEmpresa(), t.getDescripcion()};
            rows.add(row);
        });

        DFTable.llenarTablaCol0NoEditable(tblEmpresas, encabezado, rows);
    }

    private void cargarDetalle(String id) {
        try {
            emp = daoEmp.buscarPorId(Integer.parseInt(id));
            emp.mapearFields(mapFields);
            emp.llenarFields();
            emp.fieldsActivados(true);
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

        txbDescripcion = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        btnGuardar = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblEmpresas = new javax.swing.JTable();
        tblEmpresas.getSelectionModel().addListSelectionListener( 
            new ListSelectionListener(){
                public void valueChanged(ListSelectionEvent event){
                    if(!event.getValueIsAdjusting() && (tblEmpresas.getSelectedRow() >= 0)){
                        cargarDetalle(""+tblEmpresas.getValueAt(tblEmpresas.getSelectedRow(), 0));
                    }
                }
            }
        );
        btnNuevo = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        txbaDireccion = new javax.swing.JTextArea();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        txbNit = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        cbxActivo = new javax.swing.JComboBox<>();

        setClosable(true);
        setIconifiable(true);
        setResizable(true);
        setTitle("Administracion de empresas");
        setMaximumSize(new java.awt.Dimension(607, 463));
        setMinimumSize(new java.awt.Dimension(607, 463));
        setName(""); // NOI18N
        setPreferredSize(new java.awt.Dimension(607, 463));
        getContentPane().setLayout(null);
        getContentPane().add(txbDescripcion);
        txbDescripcion.setBounds(290, 50, 270, 30);

        jLabel2.setText("Razon social");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(290, 30, 80, 16);

        jLabel3.setText("Direccion");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(290, 150, 90, 16);

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
        btnGuardar.setBounds(480, 350, 80, 30);

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel4.setText("Empresas");
        getContentPane().add(jLabel4);
        jLabel4.setBounds(10, 20, 69, 19);

        tblEmpresas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(tblEmpresas);

        getContentPane().add(jScrollPane2);
        jScrollPane2.setBounds(10, 40, 240, 360);

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
        btnNuevo.setBounds(300, 350, 80, 30);

        txbaDireccion.setColumns(20);
        txbaDireccion.setRows(5);
        jScrollPane1.setViewportView(txbaDireccion);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(290, 170, 270, 90);
        getContentPane().add(jSeparator1);
        jSeparator1.setBounds(290, 320, 280, 10);

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);
        getContentPane().add(jSeparator2);
        jSeparator2.setBounds(270, 0, 10, 440);
        getContentPane().add(txbNit);
        txbNit.setBounds(290, 110, 160, 30);

        jLabel5.setText("Estado");
        getContentPane().add(jLabel5);
        jLabel5.setBounds(460, 90, 70, 16);

        jLabel6.setText("Nit");
        getContentPane().add(jLabel6);
        jLabel6.setBounds(290, 90, 15, 16);

        cbxActivo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Activa", "Inactiva" }));
        getContentPane().add(cbxActivo);
        cbxActivo.setBounds(460, 110, 100, 30);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        guardar();
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        nuevaEmpresa();
    }//GEN-LAST:event_btnNuevoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JComboBox<String> cbxActivo;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTable tblEmpresas;
    private javax.swing.JTextField txbDescripcion;
    private javax.swing.JTextField txbNit;
    private javax.swing.JTextArea txbaDireccion;
    // End of variables declaration//GEN-END:variables

}
