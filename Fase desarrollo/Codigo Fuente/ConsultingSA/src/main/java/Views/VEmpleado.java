package Views;

import Controladores.DaoDepartamento;
import Controladores.DaoEmpleado;
import Controladores.DaoEmpleadoImagen;
import Controladores.DaoNomina;
import DigyFenix.DFFile;
import DigyFenix.DFImagen;
import DigyFenix.DFMvcEnum;
import DigyFenix.DFOptionPane;
import DigyFenix.DFTable;
import DigyFenix.DFUtils;
import Enums.InsertUpdate;
import Modelos.Departamento;
import Modelos.Empleado;
import Modelos.EmpleadoImagen;
import Modelos.Imagen;
import Modelos.Nomina;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author echacon
 */
public class VEmpleado extends javax.swing.JInternalFrame {

    HashMap<String, Object> mapFields = new HashMap<>();
    Empleado emp;
    DaoEmpleado daoEmp;
    HashMap<String, String> imagenesParaCopiar = new HashMap<>();

    /**
     * Creates new form v_empresa
     */
    public VEmpleado() {
        initComponents();

        emp = new Empleado();
        daoEmp = new DaoEmpleado();

        DFUtils.setColorABotton(btnGuardar);
        DFUtils.setColorABotton(btnImagen);
        DFUtils.setColorABotton(btnNuevo);

        llenarTablaEmpleados();

        emp.limpiarFields(true);
        emp.fieldsActivados(false);

        //generarSwitchMap();
        mapearFiels();

        List<Nomina> noms = new DaoNomina().listar();
        noms.forEach((nomina) -> {
            cbxNomina.addItem(nomina.getCodigoNomina() + ">" + nomina.getDescripcion());
        }
        );

        List<Departamento> deptos = new DaoDepartamento().listar();
        deptos.forEach((d) -> {
            cbxDepartamento.addItem(d.getDescripcion());
        }
        );

    }

    private String[] separaCodigoNomina(String texto) {
        return texto.split(">");
    }

    private void mapearFiels() {
        System.out.println("mapearFiels:" + DFUtils.getMapearFiels(emp.getVariables()));
        //Mapeo las variables a los modelos
        mapFields.put("dpi", txbDpi);
        mapFields.put("nombre", txbNombre);
        mapFields.put("apellido", txbApellido);
        mapFields.put("fechaNacimiento", txbFechaNacimiento);
        mapFields.put("sueldo", txbSueldo);
        mapFields.put("municipio", txbMunicipio);
        mapFields.put("departamento", txbDepartamento);
        mapFields.put("direccion", txbDireccion);
        mapFields.put("telefonos", txbTelefonos);
        mapFields.put("contratable", txbContratable);
        mapFields.put("genero", cbxGenero);
        mapFields.put("activo", cbxActivo);
        emp.mapearFields(mapFields);

    }

    private void generarSwitchMap() {
        System.out.println("generarSwitchMap:" + DFUtils.getGenerarSwitchMap(emp.getVariables()));
        HashMap<String, DFMvcEnum> mapFields = new HashMap<>();
        mapFields.put("dpi", DFMvcEnum.STRING);
        mapFields.put("nombre", DFMvcEnum.STRING);
        mapFields.put("apellido", DFMvcEnum.STRING);
        mapFields.put("fechaNacimiento", DFMvcEnum.DATE);
        mapFields.put("sueldo", DFMvcEnum.FLOAT);
        mapFields.put("municipio", DFMvcEnum.STRING);
        mapFields.put("departamento", DFMvcEnum.STRING);
        mapFields.put("direccion", DFMvcEnum.STRING);
        mapFields.put("telefonos", DFMvcEnum.STRING);
        mapFields.put("contratable", DFMvcEnum.STRING);
        mapFields.put("genero", DFMvcEnum.STRING);
        mapFields.put("activo", DFMvcEnum.STRING);
        System.out.println(DFUtils.getSwitchParaMvc(mapFields));
    }

    private void guardar() {
        try {
            boolean nueva = (emp.getIdempleado() == null);

            String cn[] = separaCodigoNomina(cbxNomina.getSelectedItem().toString());
            emp.setIdnomina(new DaoNomina().buscarPorCodigoDescripion(cn[0], cn[1]).getIdrhNomina());
            emp.setIddepartamento(new DaoDepartamento().buscarPorDescripcion(cbxDepartamento.getSelectedItem().toString()).getIddepartamento());

            String tipo = "";
            if (nueva) {
                daoEmp.insertarActualizar(emp, InsertUpdate.INSERTAR);
                llenarTablaEmpleados();
                tipo = "insertado";
            } else {
                daoEmp.insertarActualizar(emp, InsertUpdate.ACTUALIZAR);
                tipo = "actualizado";
            }
            imagenesParaCopiar.forEach((t, u) -> {
                DFFile.moverArchivo(u, DFUtils.getRutaImagenes() + "\\" + t);
            });

            nuevoEmpleado();

            DFOptionPane.mostrar_Realizado(emp.getClass().getSimpleName() + " " + tipo + " corretamente");
            llenarTablaEmpleados();

        } catch (Exception e) {
            DFOptionPane.mostrar_Error(e.getMessage());
        }
    }

    private void nuevoEmpleado() {
        emp = new Empleado();
        emp.mapearFields(mapFields);
        emp.limpiarFields(true);
        lblImagen.setIcon(null);
        txbNombre.requestFocus();
        emp.fieldsActivados(true);
        imagenesParaCopiar = new HashMap<>();
    }

    private void llenarTablaEmpleados() {

        String encabezado[] = {"Id", "Nombre", "Sueldo", "Contratable", "Nomina", "Departamento"};
        List<Empleado> list = daoEmp.listar();
        List<String[]> rows = new ArrayList<>();
        list.forEach((t) -> {
            Nomina nom = new DaoNomina().buscarPorId(t.getIdnomina());
            Departamento depto = new DaoDepartamento().buscarPorId(t.getIddepartamento());

            String row[] = {"" + t.getIdempleado(),
                t.getNombre() + " " + t.getApellido(),
                "" + t.getSueldo(),
                t.getContratable(),
                nom.getCodigoNomina() + " - " + nom.getDescripcion(),
                depto.getDescripcion()
            };
            rows.add(row);
        });

        DFTable.llenarTablaCol0NoEditable(tblEmpleados, encabezado, rows);
    }

    private void llenarTablaImagenes() {

        String encabezado[] = {"Tipo"};
        List<String[]> rows = new ArrayList<>();
        emp.getEmpleadoImagen().forEach((t) -> {
            String row[] = {"" + t.getTipo()};
            rows.add(row);
        });

        DFTable.llenarTablaCol0NoEditable(tblImagen, encabezado, rows);
    }

    private void cargarDetalle(String id) {
        try {
            emp = daoEmp.buscarPorId(Integer.parseInt(id));
            emp.mapearFields(mapFields);
            emp.llenarFields();
            emp.fieldsActivados(true);
            llenarTablaImagenes();
            lblImagen.setIcon(null);
            lblImagen.setText(null);
        } catch (Exception e) {
            DFOptionPane.mostrar_Error(e.toString());
        }
    }

    private void cargarDetalleImagen(String id) {
        try {
            EmpleadoImagen eitemp = new DaoEmpleadoImagen().buscarPorTipo(id);
            DFImagen.previewImagen(lblImagen, eitemp.getImagen().getUrl(), true);
        } catch (Exception e) {
            String img = emp.getImagenPorTipo(id);
            if (img != null) {
                String url = imagenesParaCopiar.get(img);
                DFImagen.previewImagen(lblImagen, url, false);
            }
        }

    }

    private void agregarImagen() {
        String tipo = JOptionPane.showInputDialog("Ingrese el tipo de imagen, ejemplo: rostro, perfil derecho, etc..");

        if (tipo != null && tipo.length() > 3) {
            EmpleadoImagen eitemp = new DaoEmpleadoImagen().buscarPorTipo(tipo);
            if (eitemp == null) {
                FileNameExtensionFilter filtroImagen = new FileNameExtensionFilter("JPG, PNG", "jpg", "png");
                File file = DFFile.openFileChooser(filtroImagen, this);

                if (file != null) {
                    String nombreArchivo = file.getName();
                    String urlImagen = "sysec_" + new Date().getTime() + "." + DFFile.getExtension(file.getAbsolutePath());

                    if (nombreArchivo.length() >= 44) {
                        nombreArchivo = nombreArchivo.substring(0, 44);
                    }

                    EmpleadoImagen empimg = new EmpleadoImagen();
                    if (emp.getIdempleado() != null) {
                        empimg.setIdempleado(emp.getIdempleado());
                    }

                    empimg.setTipo(tipo);
                    Imagen img = new Imagen();
                    img.setNombre(nombreArchivo);
                    img.setUrl(urlImagen);

                    empimg.setImagen(img);
                    imagenesParaCopiar.put(img.getUrl(), file.getAbsolutePath());
                    emp.getEmpleadoImagen().add(empimg);
                    llenarTablaImagenes();
                }

            } else {
                DFOptionPane.mostrar_Error("Esta imagen ya existe");
            }
        } else {
            DFOptionPane.mostrar_Error("Minimo debe escribir 3 caracteres");
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

        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblEmpleados = new javax.swing.JTable();
        tblEmpleados.getSelectionModel().addListSelectionListener( 
            new ListSelectionListener(){
                public void valueChanged(ListSelectionEvent event){
                    if(!event.getValueIsAdjusting() && (tblEmpleados.getSelectedRow() >= 0)){
                        cargarDetalle(""+tblEmpleados.getValueAt(tblEmpleados.getSelectedRow(), 0));
                    }
                }
            }
        );
        txbNombre = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txbDpi = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        cbxGenero = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        txbFechaNacimiento = new javax.swing.JTextField();
        txbMunicipio = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txbDepartamento = new javax.swing.JTextField();
        txbDireccion = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txbSueldo = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txbContratable = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        cbxNomina = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        txbTelefonos = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        btnGuardar = new javax.swing.JButton();
        btnNuevo = new javax.swing.JButton();
        btnImagen = new javax.swing.JButton();
        lblImagen = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblImagen = new javax.swing.JTable();
        tblImagen.getSelectionModel().addListSelectionListener( 
            new ListSelectionListener(){
                public void valueChanged(ListSelectionEvent event){
                    if(!event.getValueIsAdjusting() && (tblImagen.getSelectedRow() >= 0)){
                        cargarDetalleImagen(""+tblImagen.getValueAt(tblImagen.getSelectedRow(), 0));
                    }
                }
            }
        );
        jLabel21 = new javax.swing.JLabel();
        cbxDepartamento = new javax.swing.JComboBox<>();
        txbApellido = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        cbxActivo = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setResizable(true);
        setTitle("Administracion de empleados");
        setMaximumSize(new java.awt.Dimension(778, 443));
        setMinimumSize(new java.awt.Dimension(778, 443));
        setName(""); // NOI18N
        setPreferredSize(new java.awt.Dimension(778, 443));
        getContentPane().setLayout(null);

        jLabel2.setText("Lista de empleados");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(10, 30, 130, 16);

        tblEmpleados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(tblEmpleados);

        getContentPane().add(jScrollPane2);
        jScrollPane2.setBounds(10, 50, 630, 109);
        getContentPane().add(txbNombre);
        txbNombre.setBounds(320, 190, 180, 30);

        jLabel6.setText("Nombre");
        getContentPane().add(jLabel6);
        jLabel6.setBounds(320, 170, 70, 16);
        getContentPane().add(txbDpi);
        txbDpi.setBounds(70, 250, 130, 30);

        jLabel7.setText("Genero");
        getContentPane().add(jLabel7);
        jLabel7.setBounds(510, 230, 50, 16);

        cbxGenero.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "M", "F" }));
        getContentPane().add(cbxGenero);
        cbxGenero.setBounds(510, 250, 130, 30);

        jLabel8.setText("Imagenes ");
        getContentPane().add(jLabel8);
        jLabel8.setBounds(660, 30, 80, 16);

        txbFechaNacimiento.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        getContentPane().add(txbFechaNacimiento);
        txbFechaNacimiento.setBounds(200, 250, 120, 30);
        getContentPane().add(txbMunicipio);
        txbMunicipio.setBounds(120, 310, 110, 30);

        jLabel10.setText("Municipio");
        getContentPane().add(jLabel10);
        jLabel10.setBounds(120, 290, 100, 16);

        jLabel11.setText("Dpto. domicilio.");
        getContentPane().add(jLabel11);
        jLabel11.setBounds(10, 290, 100, 16);
        getContentPane().add(txbDepartamento);
        txbDepartamento.setBounds(10, 310, 110, 30);
        getContentPane().add(txbDireccion);
        txbDireccion.setBounds(230, 310, 160, 30);

        jLabel12.setText("Direccion");
        getContentPane().add(jLabel12);
        jLabel12.setBounds(230, 290, 120, 16);

        txbSueldo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        getContentPane().add(txbSueldo);
        txbSueldo.setBounds(10, 250, 60, 30);

        jLabel13.setText("Sueldo");
        getContentPane().add(jLabel13);
        jLabel13.setBounds(10, 230, 50, 16);
        getContentPane().add(txbContratable);
        txbContratable.setBounds(390, 310, 170, 30);

        jLabel14.setText("Contratable");
        getContentPane().add(jLabel14);
        jLabel14.setBounds(390, 290, 120, 16);

        jLabel16.setText("Fecha nacimiento ");
        getContentPane().add(jLabel16);
        jLabel16.setBounds(200, 230, 110, 16);

        getContentPane().add(cbxNomina);
        cbxNomina.setBounds(10, 190, 190, 30);

        jLabel9.setText("Nomina");
        getContentPane().add(jLabel9);
        jLabel9.setBounds(10, 170, 80, 16);
        getContentPane().add(txbTelefonos);
        txbTelefonos.setBounds(330, 250, 178, 30);

        jLabel18.setText("Telefonos");
        getContentPane().add(jLabel18);
        jLabel18.setBounds(330, 230, 120, 16);

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
        btnGuardar.setBounds(470, 370, 94, 40);

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
        btnNuevo.setBounds(130, 370, 90, 40);

        btnImagen.setBackground(new java.awt.Color(0, 153, 102));
        btnImagen.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnImagen.setForeground(new java.awt.Color(255, 255, 255));
        btnImagen.setText("+ Imagen");
        btnImagen.setBorder(null);
        btnImagen.setBorderPainted(false);
        btnImagen.setContentAreaFilled(false);
        btnImagen.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnImagen.setOpaque(false);
        btnImagen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImagenActionPerformed(evt);
            }
        });
        getContentPane().add(btnImagen);
        btnImagen.setBounds(650, 270, 100, 31);

        lblImagen.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblImagenMousePressed(evt);
            }
        });
        getContentPane().add(lblImagen);
        lblImagen.setBounds(650, 190, 100, 70);

        jLabel20.setText("Dpi");
        getContentPane().add(jLabel20);
        jLabel20.setBounds(70, 230, 40, 16);
        getContentPane().add(jSeparator1);
        jSeparator1.setBounds(0, 350, 640, 10);

        tblImagen.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tblImagen);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(650, 50, 100, 130);

        jLabel21.setText("Departamento");
        getContentPane().add(jLabel21);
        jLabel21.setBounds(200, 170, 120, 16);

        getContentPane().add(cbxDepartamento);
        cbxDepartamento.setBounds(200, 190, 120, 30);
        getContentPane().add(txbApellido);
        txbApellido.setBounds(500, 190, 140, 30);

        jLabel22.setText("Apellido");
        getContentPane().add(jLabel22);
        jLabel22.setBounds(500, 170, 70, 16);

        cbxActivo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Activa", "Inactiva" }));
        getContentPane().add(cbxActivo);
        cbxActivo.setBounds(560, 310, 80, 30);

        jLabel5.setText("Estado");
        getContentPane().add(jLabel5);
        jLabel5.setBounds(560, 290, 70, 16);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        if (emp.getSueldo() == null || emp.getSueldo() < 1) {
            DFOptionPane.mostrar_Error("El sueldo del empleado debe ser mayor a 0");
        } else {
            guardar();
        }

    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnImagenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImagenActionPerformed
        if (emp.getIdempleado() != null) {
            agregarImagen();
        } else {
            DFOptionPane.mostrar_Error("Debe seleccionar un empleado");
        }
    }//GEN-LAST:event_btnImagenActionPerformed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        nuevoEmpleado();
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void lblImagenMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblImagenMousePressed
        if (lblImagen.getText() != null && lblImagen.getText().length() > 0) {
            VPrincipal.previsualizarImagen(lblImagen.getText(), false);
        }
    }//GEN-LAST:event_lblImagenMousePressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnImagen;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JComboBox<String> cbxActivo;
    private javax.swing.JComboBox<String> cbxDepartamento;
    private javax.swing.JComboBox<String> cbxGenero;
    private javax.swing.JComboBox<String> cbxNomina;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblImagen;
    private javax.swing.JTable tblEmpleados;
    private javax.swing.JTable tblImagen;
    private javax.swing.JTextField txbApellido;
    private javax.swing.JTextField txbContratable;
    private javax.swing.JTextField txbDepartamento;
    private javax.swing.JTextField txbDireccion;
    private javax.swing.JTextField txbDpi;
    private javax.swing.JTextField txbFechaNacimiento;
    private javax.swing.JTextField txbMunicipio;
    private javax.swing.JTextField txbNombre;
    private javax.swing.JTextField txbSueldo;
    private javax.swing.JTextField txbTelefonos;
    // End of variables declaration//GEN-END:variables

}
