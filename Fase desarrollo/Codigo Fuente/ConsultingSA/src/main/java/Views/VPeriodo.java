package Views;

import Controladores.DaoNomina;
import Controladores.DaoPeriodo;
import DigyFenix.*;
import Enums.InsertUpdate;
import Modelos.Nomina;
import Modelos.Periodo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author echacon
 */
public class VPeriodo extends javax.swing.JInternalFrame {

    HashMap<String, Object> mapFields = new HashMap<>();
    Periodo per;
    DaoPeriodo daoPer;

    /**
     * Creates new form v_empresa
     */
    public VPeriodo() {
        initComponents();

        //Inicio las variables
        nuevaPeriodo();
        daoPer = new DaoPeriodo();

        //Configuro los botonos
        DFUtils.setColorABotton(btnGuardar);
        DFUtils.setColorABotton(btnNuevo);

        generarSwitchMap();
        mapearFiels();

        llenarTablaPeriodos();

        per.limpiarFields(true);
        per.fieldsActivados(false);

        List<Nomina> emp = new DaoNomina().listar();
        emp.forEach((nomina) -> {
            cbxNomina.addItem(nomina.getCodigoNomina() + ">" + nomina.getDescripcion());
        }
        );
    }

    private String[] separaCodigoNomina(String texto) {
        return texto.split(">");
    }

    private void mapearFiels() {
        System.out.println("mapearFiels:" + DFUtils.getMapearFiels(per.getVariables()));
        mapFields.put("fechaInicio", txbFechaInicio);
        mapFields.put("fechaFin", txbFechaFin);
        mapFields.put("descripcion", txbDescripcion);
        mapFields.put("activo", cbxActivo);
        per.mapearFields(mapFields);
    }

    private void generarSwitchMap() {
        System.out.println("generarSwitchMap: " + DFUtils.getGenerarSwitchMap(per.getVariables()));
        HashMap<String, DFMvcEnum> mapFields = new HashMap<>();
        mapFields.put("fechaInicio", DFMvcEnum.DATE);
        mapFields.put("fechaFin", DFMvcEnum.DATE);
        mapFields.put("descripcion", DFMvcEnum.STRING);
        mapFields.put("activo", DFMvcEnum.STRING);
        System.out.println(DFUtils.getSwitchParaMvc(mapFields));
    }

    private void guardar() {
        try {
            boolean nueva = (per.getIdrhPeriodo() == null);
            //Primero se guardan los datos auxiliares para actualizar ids

            String cn[] = separaCodigoNomina(cbxNomina.getSelectedItem().toString());

            int dias = DFDate.get_dias_entre_fechas(txbFechaInicio.getText(), txbFechaFin.getText());
            dias += 1;
            if (dias > 16) {
                DFOptionPane.mostrar_Error("El periodo ingresado tiene " + dias + " dias\nLos periodos ingresados deben ser quincenales");
            } else {
                per.setIdnomina(new DaoNomina().buscarPorCodigoDescripion(cn[0], cn[1]).getIdrhNomina());

                String tipo = "";
                if (nueva) {
                    daoPer.insertarActualizar(per, InsertUpdate.INSERTAR);
                    tipo = "insertado";
                } else {
                    daoPer.insertarActualizar(per, InsertUpdate.ACTUALIZAR);
                    tipo = "actualizado";
                }
                llenarTablaPeriodos();
                DFOptionPane.mostrar_Realizado(per.getClass().getSimpleName() + " " + tipo + " corretamente");
            }

        } catch (Exception e) {
            DFOptionPane.mostrar_Error(e.toString());
        }
    }

    private void nuevaPeriodo() {
        per = new Periodo();
        per.mapearFields(mapFields);
        per.limpiarFields(true);
        txbDescripcion.requestFocus();
        per.fieldsActivados(true);
    }

    private void llenarTablaPeriodos() {
        String encabezado[] = {"Id", "Nomina", "Descripcion", "Fecha inicio", "Fecha fin", "Estado"};
        List<Periodo> list = daoPer.listar();
        List<String[]> rows = new ArrayList<>();
        list.forEach((t) -> {
            try {
                String row[] = {"" + t.getIdrhPeriodo(),
                    new DaoNomina().buscarPorId(t.getIdnomina()).getDescripcion(),
                    t.getDescripcion(),
                    DFDate.get_fecha(t.getFechaInicio()),
                    DFDate.get_fecha(t.getFechaFin()),
                    t.getActivo()};
                rows.add(row);
            } catch (Exception e) {
            }
        });

        DFTable.llenarTablaCol0NoEditable(tblNominas, encabezado, rows);
    }

    private void cargarDetalle(String id) {
        try {
            per = daoPer.buscarPorId(Integer.parseInt(id));
            Nomina nomina = new DaoNomina().buscarPorId(per.getIdnomina());
            cbxNomina.setSelectedItem(nomina.getCodigoNomina() + ">" + nomina.getDescripcion());
            per.mapearFields(mapFields);
            per.llenarFields();
            per.fieldsActivados(true);
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

        txbFechaInicio = new javax.swing.JTextField();
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
        btnNuevo = new javax.swing.JButton();
        txbDescripcion = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        cbxActivo = new javax.swing.JComboBox<>();
        cbxNomina = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txbFechaFin = new javax.swing.JTextField();

        setClosable(true);
        setIconifiable(true);
        setResizable(true);
        setTitle("Administracion de nominas");
        setMaximumSize(new java.awt.Dimension(721, 377));
        setMinimumSize(new java.awt.Dimension(721, 377));
        setName(""); // NOI18N
        setPreferredSize(new java.awt.Dimension(721, 377));
        getContentPane().setLayout(null);

        txbFechaInicio.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        getContentPane().add(txbFechaInicio);
        txbFechaInicio.setBounds(460, 210, 80, 30);

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Fecha inicio");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(460, 190, 80, 20);

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
        btnGuardar.setBounds(420, 260, 80, 30);

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
        btnNuevo.setBounds(220, 260, 80, 30);
        getContentPane().add(txbDescripcion);
        txbDescripcion.setBounds(200, 210, 260, 30);

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Estado");
        getContentPane().add(jLabel5);
        jLabel5.setBounds(620, 190, 80, 20);

        jLabel6.setText("Descripcion");
        getContentPane().add(jLabel6);
        jLabel6.setBounds(200, 190, 70, 20);

        cbxActivo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Activa", "Inactiva" }));
        getContentPane().add(cbxActivo);
        cbxActivo.setBounds(620, 210, 80, 30);

        getContentPane().add(cbxNomina);
        cbxNomina.setBounds(10, 210, 190, 30);

        jLabel9.setText("Nomina");
        getContentPane().add(jLabel9);
        jLabel9.setBounds(10, 190, 80, 16);

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Fecha fin");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(540, 190, 80, 20);

        txbFechaFin.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        getContentPane().add(txbFechaFin);
        txbFechaFin.setBounds(540, 210, 80, 30);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        try {
            if (DFDate.validarFecha(txbFechaInicio.getText())) {
                if (DFDate.validarFecha(txbFechaFin.getText())) {
                    guardar();
                }
            }
        } catch (Exception e) {
            DFOptionPane.mostrar_Error("Error en fechas ");
        }

    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        nuevaPeriodo();
    }//GEN-LAST:event_btnNuevoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JComboBox<String> cbxActivo;
    private javax.swing.JComboBox<String> cbxNomina;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblNominas;
    private javax.swing.JTextField txbDescripcion;
    private javax.swing.JTextField txbFechaFin;
    private javax.swing.JTextField txbFechaInicio;
    // End of variables declaration//GEN-END:variables

}
