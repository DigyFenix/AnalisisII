package Views.Reports;

import DigyFenix.*;
import Enums.BusquedaFrame;
import Modelos.Nomina;
import Views.Utils.VBusqueda;
import Views.VPrincipal;
import java.util.HashMap;

/**
 *
 * @author echacon
 */
public class VReportEmpleados extends javax.swing.JInternalFrame {

    HashMap<String, Object> mapFields = new HashMap<>();

    Nomina nomina = new Nomina();

    /**
     * Creates new form v_empresa
     */
    public VReportEmpleados() {
        initComponents();

        DFUtils.setColorABotton(btnTrasladar);
        DFUtils.setColorABotton(btnEmpBusqueda);

    }

    public void busquedaFinalizada(Object obj) {
        try {
            if (obj instanceof Nomina) {
                nomina = (Nomina) obj;
                txbPeriodo.setText(nomina.getCodigoNomina() + " - " + nomina.getDescripcion());
            }
        } catch (Exception e) {
            DFOptionPane.mostrar_Error(e.getMessage());
        }

    }

    private void trasadarExcel() {
        try {
            String sql = "select "
                    + "d.descripcion empresa, c.descripcion departamento, b.codigo_nomina, b.descripcion nomina, "
                    + "a.idrh_empleado idEmpleado, concat(a.nombre,\" \", a.apellido) nombre, a.sueldo, DATE_FORMAT(a.fecha_nacimiento, \"%d-%m-%Y\") fecha_nacimiento, "
                    + "a.municipio, a.departamento depto_direccion "
                    + " from rh_empleado a "
                    + "inner join rh_nomina b on a.idnomina = b.idrh_nomina "
                    + "inner join rh_departamento c on a.iddepartamento = c.iddepartamento "
                    + "inner join rh_empresa d on b.idempresa = d.idrh_empresa "
                    + "where a.activo ='Activa' and a.idnomina = " + nomina.getIdrhNomina();
            VPrincipal.generarExcel(sql, txbPeriodo.getText());
            this.dispose();
        } catch (Exception e) {
            DFOptionPane.mostrar_Error(e.getMessage());
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

        btnTrasladar = new javax.swing.JButton();
        btnEmpBusqueda = new javax.swing.JButton();
        txbPeriodo = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setResizable(true);
        setTitle("Traslado de formato para descuentos");
        setMaximumSize(new java.awt.Dimension(722, 159));
        setMinimumSize(new java.awt.Dimension(722, 159));
        setName(""); // NOI18N
        setPreferredSize(new java.awt.Dimension(722, 159));
        getContentPane().setLayout(null);

        btnTrasladar.setBackground(new java.awt.Color(0, 153, 102));
        btnTrasladar.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnTrasladar.setForeground(new java.awt.Color(255, 255, 255));
        btnTrasladar.setText("Trasladar a excel");
        btnTrasladar.setBorder(null);
        btnTrasladar.setBorderPainted(false);
        btnTrasladar.setContentAreaFilled(false);
        btnTrasladar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnTrasladar.setOpaque(false);
        btnTrasladar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTrasladarActionPerformed(evt);
            }
        });
        getContentPane().add(btnTrasladar);
        btnTrasladar.setBounds(320, 70, 140, 40);

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
        btnEmpBusqueda.setBounds(140, 20, 40, 30);
        getContentPane().add(txbPeriodo);
        txbPeriodo.setBounds(180, 20, 500, 30);

        jLabel7.setText("Nomina");
        getContentPane().add(jLabel7);
        jLabel7.setBounds(20, 20, 110, 30);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnTrasladarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTrasladarActionPerformed
        if (nomina.getIdrhNomina() != null) {
            trasadarExcel();
        } else {
            DFOptionPane.mostrar_Error("Debe seleccionar un periodo");
        }

    }//GEN-LAST:event_btnTrasladarActionPerformed

    private void btnEmpBusquedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEmpBusquedaActionPerformed
        new VBusqueda(null, this, BusquedaFrame.VREPORTE_EMPLEADOS, nomina);
    }//GEN-LAST:event_btnEmpBusquedaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEmpBusqueda;
    private javax.swing.JButton btnTrasladar;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JTextField txbPeriodo;
    // End of variables declaration//GEN-END:variables

}
