package Views.Reports;

import DigyFenix.*;
import Enums.BusquedaFrame;
import Enums.ReportNomina;
import Modelos.Periodo;
import Utilidades.JReport;
import Utils.HbUtils;
import Views.Utils.VBusqueda;
import Views.VPrincipal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManager;
import net.sf.jasperreports.view.JasperViewer;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;

/**
 *
 * @author echacon
 */
public class VReportNomina extends javax.swing.JInternalFrame {

    HashMap<String, Object> mapFields = new HashMap<>();

    Periodo per = new Periodo();

    ReportNomina tipo;
    Connection conexion;

    /**
     * Creates new form v_empresa
     */
    public VReportNomina(ReportNomina report) {
        initComponents();

        DFUtils.setColorABotton(btnTrasladar);
        DFUtils.setColorABotton(btnEmpBusqueda);
        tipo = report;

        DFUtils.setColorABotton(btnTrasladar);
        EntityManager em = HbUtils.getEntityManager();
        em.getTransaction().begin();
        Session session = em.unwrap(Session.class);
        session.doWork(new Work() {
            @Override
            public void execute(Connection cnctn) throws SQLException {
                conexion = cnctn;
            }
        });
        em.getTransaction().commit();
        em.close();

    }

    public void busquedaFinalizada(Object obj) {
        try {
            if (obj instanceof Periodo) {
                per = (Periodo) obj;
                txbPeriodo.setText(per.getDescripcion() + " - desde la fecha " + DFDate.get_fecha(per.getFechaInicio()) + " hasta la fecha " + DFDate.get_fecha(per.getFechaFin()));

            }
        } catch (Exception e) {
            DFOptionPane.mostrar_Error(e.getMessage());
        }

    }

    private void generar() {
        try {
            switch (tipo) {
                case NOMINA_EXCEL: {
                    String sql = "select "
                            + "a.idrh_dc_enc idGeneracion, a.no_asiento, a.cod_diario, a.descripcion , "
                            + "f.descripcion empresa, a.idnomina, e.descripcion descripcion_nomina, b.cuenta_contable, g.descripcion departamento, "
                            + "b.idempleado, concat(d.nombre, \" \",d.apellido) empleado, b.idmovimiento, c.descripcion movimiento, b.monto "
                            + "from rh_dc_enc a "
                            + "inner join rh_dc_det b on a.idrh_dc_enc = b.idrh_dc_enc "
                            + "inner join rh_movimiento c on b.idmovimiento = c.idrh_movimiento  "
                            + "inner join rh_empleado d on b.idempleado = d.idrh_empleado  "
                            + "inner join rh_nomina e on a.idnomina  = e.idrh_nomina "
                            + "inner join rh_empresa f on e.idempresa = f.idrh_empresa "
                            + "inner join rh_departamento  g on d.iddepartamento = g.iddepartamento "
                            + "where a.idnomina = " + per.getIdnomina() + " and DATE_FORMAT(b.fecha_desde, \"%d-%m-%Y\") = '" + DFDate.get_fecha(per.getFechaInicio()) + "' "
                            + "and DATE_FORMAT(b.fecha_hasta, \"%d-%m-%Y\") = '" + DFDate.get_fecha(per.getFechaFin()) + "' ";
                    VPrincipal.generarExcel(sql, txbPeriodo.getText());
                    break;
                }
                case EGRESOS: {
                    String reporte = "Descuentos.jasper";
                    Map<String, Object> parametros = new HashMap<>();
                    parametros.put("idperiodo", per.getIdrhPeriodo());

                    try {
                        JasperViewer visor = new JasperViewer(JReport.generarReporte(reporte, parametros, conexion), false);
                        visor.setVisible(true);
                    } catch (Exception e) {
                        System.out.println("Excepcion controlada: " + e.toString());
                    }
                    break;
                }
                case SALARIO: {
                    String reporte = "Salario.jasper";
                    Map<String, Object> parametros = new HashMap<>();
                    parametros.put("idperiodo", per.getIdrhPeriodo());

                    try {
                        JasperViewer visor = new JasperViewer(JReport.generarReporte(reporte, parametros, conexion), false);
                        visor.setVisible(true);
                    } catch (Exception e) {
                        System.out.println("Excepcion controlada: " + e.toString());
                    }
                    break;
                }
            }
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
        setTitle("Reporte de nomina");
        setMaximumSize(new java.awt.Dimension(722, 159));
        setMinimumSize(new java.awt.Dimension(722, 159));
        setName(""); // NOI18N
        setPreferredSize(new java.awt.Dimension(722, 159));
        getContentPane().setLayout(null);

        btnTrasladar.setBackground(new java.awt.Color(0, 153, 102));
        btnTrasladar.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnTrasladar.setForeground(new java.awt.Color(255, 255, 255));
        btnTrasladar.setText("Generar");
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

        jLabel7.setText("Periodo a generar");
        getContentPane().add(jLabel7);
        jLabel7.setBounds(20, 20, 130, 20);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnTrasladarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTrasladarActionPerformed
        if (per.getIdrhPeriodo() != null) {
            generar();
        } else {
            DFOptionPane.mostrar_Error("Debe seleccionar un periodo");
        }

    }//GEN-LAST:event_btnTrasladarActionPerformed

    private void btnEmpBusquedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEmpBusquedaActionPerformed
        new VBusqueda(null, this, BusquedaFrame.VREPORTE_NOMINA, per);
    }//GEN-LAST:event_btnEmpBusquedaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEmpBusqueda;
    private javax.swing.JButton btnTrasladar;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JTextField txbPeriodo;
    // End of variables declaration//GEN-END:variables

}
