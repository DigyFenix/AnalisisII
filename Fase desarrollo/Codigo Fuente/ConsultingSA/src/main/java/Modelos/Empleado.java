/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;

import DigyFenix.DFMvcEnum;
import DigyFenix.DFUtils;
import Interfaces.MvcInterface;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.validation.constraints.Size;

/**
 *
 * @author echacon
 */
@Entity
@Table(name = "rh_empleado")
@NamedQueries({
    @NamedQuery(name = "Empleado.findAll", query = "SELECT e FROM Empleado e")})
public class Empleado implements Serializable, MvcInterface {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idrh_empleado")
    private Integer idempleado;
    @Size(max = 15)
    @Column(name = "dpi")
    private String dpi;
    @Size(max = 45)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 45)
    @Column(name = "apellido")
    private String apellido;
    @Column(name = "fecha_nacimiento")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaNacimiento;
    @Column(name = "sueldo")
    private Float sueldo;
    @Size(max = 45)
    @Column(name = "municipio")
    private String municipio;
    @Size(max = 45)
    @Column(name = "departamento")
    private String departamento;
    @Size(max = 150)
    @Column(name = "direccion")
    private String direccion;
    @Size(max = 45)
    @Column(name = "telefonos")
    private String telefonos;
    @Size(max = 45)
    @Column(name = "contratable")
    private String contratable;
    @Size(max = 1)
    @Column(name = "sexo")
    private String genero;
    @Column(name = "activo")
    private String activo;

    @Column(name = "idnomina")
    private int idnomina;

    @Column(name = "iddepartamento")
    private int iddepartamento;

    @JoinColumn(name = "idempleado")
    @OneToMany(cascade = CascadeType.ALL)
    List<EmpleadoImagen> empleadoImagen;

    @Transient
    HashMap<String, Object> mapFields = new HashMap<>();

    public Empleado() {
    }

    public Empleado(Integer idempleado) {
        this.idempleado = idempleado;
    }

    public Integer getIdempleado() {
        return idempleado;
    }

    public void setIdempleado(Integer idempleado) {
        this.idempleado = idempleado;
    }

    public List<EmpleadoImagen> getEmpleadoImagen() {
        return empleadoImagen;
    }

    public void setEmpleadoImagen(List<EmpleadoImagen> empleadoImagen) {
        this.empleadoImagen = empleadoImagen;
    }

    public String getImagenPorTipo(String tipo) {
        String url = null;

        for (EmpleadoImagen t : empleadoImagen) {
            if (t.getTipo().equals(tipo)) {
                url = t.getImagen().getUrl();
                break;
            }
        }
        return url;
    }

    public String getDpi() {
        return dpi;
    }

    public void setDpi(String dpi) {
        this.dpi = dpi;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Float getSueldo() {
        return sueldo;
    }

    public void setSueldo(Float sueldo) {
        this.sueldo = sueldo;
    }

    public String getTelefonos() {
        return telefonos;
    }

    public void setTelefonos(String telefonos) {
        this.telefonos = telefonos;
    }

    public String getContratable() {
        return contratable;
    }

    public void setContratable(String contratable) {
        this.contratable = contratable;
    }

    public int getIdnomina() {
        return idnomina;
    }

    public void setIdnomina(int idnomina) {
        this.idnomina = idnomina;
    }

    public int getIddepartamento() {
        return iddepartamento;
    }

    public void setIddepartamento(int iddepartamento) {
        this.iddepartamento = iddepartamento;
    }

    public String getActivo() {
        return activo;
    }

    public void setActivo(String activo) {
        this.activo = activo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idempleado != null ? idempleado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Empleado)) {
            return false;
        }
        Empleado other = (Empleado) object;
        if ((this.idempleado == null && other.idempleado != null) || (this.idempleado != null && !this.idempleado.equals(other.idempleado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelos.Empleado[ idempleado=" + idempleado + " ]";
    }

    @Override
    public void mapearFields(HashMap<String, Object> fields) {
        this.mapFields = fields;
        mapFields.forEach((t, field) -> {
            if (field instanceof JTextField) {
                ((JTextField) field).addFocusListener(new FocusListener() {
                    @Override
                    public void focusGained(FocusEvent e) {
                    }

                    @Override
                    public void focusLost(FocusEvent e) {
                        setValue(t, DFMvcEnum.FIELD_A_VARIABLE, false);
                    }
                });
            } else if (field instanceof JTextArea) {
                ((JTextArea) field).addFocusListener(new FocusListener() {
                    @Override
                    public void focusGained(FocusEvent e) {
                    }

                    @Override
                    public void focusLost(FocusEvent e) {
                        setValue(t, DFMvcEnum.FIELD_A_VARIABLE, false);
                    }
                });
            } else if (field instanceof JComboBox) {
                ((JComboBox) field).addItemListener((ItemEvent e) -> {
                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        setValue(t, DFMvcEnum.FIELD_A_VARIABLE, false);
                    }
                });
            } else if (field instanceof JCheckBox) {
                ((JCheckBox) field).addItemListener((ItemEvent e) -> {
                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        setValue(t, DFMvcEnum.FIELD_A_VARIABLE, false);
                    } else {
                        setValue(t, DFMvcEnum.FIELD_A_VARIABLE, false);
                    }
                });
            }
        });

    }

    @Override
    public void limpiarFields(boolean eliminarValoresVariables) {
        this.mapFields.forEach((t, u) -> {
            setValue(t, DFMvcEnum.VARIABLE_A_FIELD, true);
        });
    }

    @Override
    public void setValue(String llave, DFMvcEnum tipo, boolean eliminarValoresVariables) {
        Object obj = mapFields.get(llave);
        try {
            switch (llave) {
                case "fechaNacimiento": {
                    fechaNacimiento = DFUtils.setValueAux(obj, tipo, eliminarValoresVariables, fechaNacimiento);
                    break;
                }
                case "municipio": {
                    municipio = DFUtils.setValueAux(obj, tipo, eliminarValoresVariables, municipio);
                    break;
                }
                case "apellido": {
                    apellido = DFUtils.setValueAux(obj, tipo, eliminarValoresVariables, apellido);
                    break;
                }
                case "genero": {
                    genero = DFUtils.setValueAux(obj, tipo, eliminarValoresVariables, genero);
                    break;
                }
                case "direccion": {
                    direccion = DFUtils.setValueAux(obj, tipo, eliminarValoresVariables, direccion);
                    break;
                }
                case "contratable": {
                    contratable = DFUtils.setValueAux(obj, tipo, eliminarValoresVariables, contratable);
                    break;
                }
                case "sueldo": {
                    sueldo = DFUtils.setValueAux(obj, tipo, eliminarValoresVariables, sueldo);
                    break;
                }
                case "departamento": {
                    departamento = DFUtils.setValueAux(obj, tipo, eliminarValoresVariables, departamento);
                    break;
                }
                case "telefonos": {
                    telefonos = DFUtils.setValueAux(obj, tipo, eliminarValoresVariables, telefonos);
                    break;
                }
                case "dpi": {
                    dpi = DFUtils.setValueAux(obj, tipo, eliminarValoresVariables, dpi);
                    break;
                }
                case "nombre": {
                    nombre = DFUtils.setValueAux(obj, tipo, eliminarValoresVariables, nombre);
                    break;
                }
                case "activo": {
                    activo = DFUtils.setValueAux(obj, tipo, eliminarValoresVariables, activo);
                    break;
                }
            }
            ((JTextField) obj).setBorder(DFUtils.getBorderNormal());
        } catch (Exception ex) {
            if (obj instanceof JTextField) {
                ((JTextField) obj).setBorder(DFUtils.getBorderError());
            } else if (obj instanceof JTextArea) {
                ((JTextArea) obj).setBorder(DFUtils.getBorderError());
            }
        }
    }

    @Override
    public void llenarFields() {
        mapFields.forEach((t, u) -> {
            setValue(t, DFMvcEnum.VARIABLE_A_FIELD, false);
        });
    }

    @Override
    public void fieldsActivados(boolean si_o_no) {
        mapFields.forEach((t, field) -> {
            if (field instanceof JTextField) {
                ((JTextField) field).setEnabled(si_o_no);
            } else if (field instanceof JTextArea) {
                ((JTextArea) field).setEnabled(si_o_no);
            } else if (field instanceof JComboBox) {
                ((JComboBox) field).setEnabled(si_o_no);
            } else if (field instanceof JCheckBox) {
                ((JCheckBox) field).setEnabled(si_o_no);
            }
        });
    }

    @Override
    public List<String> getVariables() {
        List<String> list = new ArrayList<>();
        list.add("dpi");
        list.add("nombre");
        list.add("apellido");
        list.add("fechaNacimiento");
        list.add("sueldo");
        list.add("municipio");
        list.add("departamento");
        list.add("direccion");
        list.add("telefonos");
        list.add("contratable");
        list.add("genero");
        list.add("activo");
        return list;
    }
}
