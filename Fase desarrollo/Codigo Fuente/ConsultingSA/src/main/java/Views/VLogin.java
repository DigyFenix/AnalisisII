package Views;

import DigyFenix.DFOptionPane;
import DigyFenix.DFUtils;
import Utilidades.Utilidades;
import Utils.HbUtils;
import java.awt.event.KeyEvent;

/**
 *
 * @author echacon
 */
public class VLogin extends javax.swing.JFrame {

    /**
     * Creates new form InicioSesion
     */
    public VLogin() {
        initComponents();
        iniciarElementos();

        DFUtils.setRutaFija(DFUtils.getRutaEjecucion());
        DFUtils.setCarpetaImagenes("src\\main\\java\\Assets");
        DFUtils.setColores(new java.awt.Color(0, 102, 153), new java.awt.Color(51, 153, 255), new java.awt.Color(153, 0, 51));
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void iniciarElementos() {
        jLabel2.setForeground(Utilidades.COLOR_OSCURO);
        DFUtils.setColorABotton(jButton1);
        txb_usuario.setOpaque(true);
        txb_password.setOpaque(true);
        txb_usuario.setBackground(Utilidades.COLOR_PRIMARIO);
        txb_password.setBackground(Utilidades.COLOR_PRIMARIO);
        jLabel2.setText("Sistema de nomina - Consulting S.A.");
        jLabel4.setForeground(Utilidades.COLOR_OSCURO);
        jLabel5.setForeground(Utilidades.COLOR_OSCURO);

    }

    private void iniciar() {
        try {

            String usuario = txb_usuario.getText().toLowerCase();
            String clave = txb_password.getText();
            HbUtils.iniciar(usuario, clave);
            new VPrincipal().setVisible(true);
            this.dispose();
        } catch (Exception e) {
            DFOptionPane.mostrar_Error("Credenciales incorrectas");
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        txb_usuario = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txb_password = new javax.swing.JPasswordField();
        jLabel5 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(558, 465));
        setMinimumSize(new java.awt.Dimension(558, 465));
        setResizable(false);
        getContentPane().setLayout(null);

        jButton1.setBackground(new java.awt.Color(0, 153, 102));
        jButton1.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Iniciar");
        jButton1.setBorder(null);
        jButton1.setBorderPainted(false);
        jButton1.setContentAreaFilled(false);
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.setOpaque(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1);
        jButton1.setBounds(190, 260, 190, 40);

        jLabel2.setFont(new java.awt.Font("Arial", 1, 10)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 51, 51));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        getContentPane().add(jLabel2);
        jLabel2.setBounds(80, 10, 380, 20);

        txb_usuario.setBackground(new java.awt.Color(0, 102, 153));
        txb_usuario.setFont(new java.awt.Font("Apple SD Gothic Neo", 0, 18)); // NOI18N
        txb_usuario.setForeground(new java.awt.Color(255, 255, 255));
        txb_usuario.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        getContentPane().add(txb_usuario);
        txb_usuario.setBounds(190, 100, 190, 30);

        jLabel4.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Usuario:");
        getContentPane().add(jLabel4);
        jLabel4.setBounds(170, 80, 230, 20);

        txb_password.setBackground(new java.awt.Color(51, 153, 255));
        txb_password.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txb_password.setForeground(new java.awt.Color(255, 255, 255));
        txb_password.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txb_password.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txb_passwordKeyReleased(evt);
            }
        });
        getContentPane().add(txb_password);
        txb_password.setBounds(190, 180, 190, 30);

        jLabel5.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Password:");
        getContentPane().add(jLabel5);
        jLabel5.setBounds(170, 160, 230, 20);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Desarrollador: Edwin Chacon _ 3090-13-20597  *  QA: Oscar Garcia _ 3090-15-12536");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(10, 410, 540, 16);

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Proyecto final análisis de sistemas II - Universidad Mariano Gálvez");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(10, 390, 540, 16);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        iniciar();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txb_passwordKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txb_passwordKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            iniciar();
        }
    }//GEN-LAST:event_txb_passwordKeyReleased

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
                    // UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
            //UIManager.put("nimbusOrange", Utilidades.COLOR_PRIMARIO);
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VLogin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    public javax.swing.JPasswordField txb_password;
    public javax.swing.JTextField txb_usuario;
    // End of variables declaration//GEN-END:variables
}
