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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author echacon
 */
@Entity
@Table(name = "rh_ingreso_egreso")
@NamedQueries({
    @NamedQuery(name = "IngresoEgreso.findAll", query = "SELECT i FROM IngresoEgreso i")})
public class IngresoEgreso implements Serializable, MvcInterface {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idrh_ingreso_egreso")
    private Integer idrhIngresoEgreso;
    @Column(name = "monto")
    private Float monto;
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Column(name = "cantidad")
    private Float cantidad;

    @Column(name = "idempleado")
    private int idempleado;
    @Column(name = "idmovimiento")
    private int idmovimiento;

    @Transient
    private HashMap<String, Object> mapFields = new HashMap<>();

    public IngresoEgreso() {
    }

    public IngresoEgreso(Integer idrhIngresoEgreso) {
        this.idrhIngresoEgreso = idrhIngresoEgreso;
    }

    public Integer getIdrhIngresoEgreso() {
        return idrhIngresoEgreso;
    }

    public void setIdrhIngresoEgreso(Integer idrhIngresoEgreso) {
        this.idrhIngresoEgreso = idrhIngresoEgreso;
    }

    public Float getMonto() {
        return monto;
    }

    public Float getCantidad() {
        return cantidad;
    }

    public void setCantidad(Float cantidad) {
        this.cantidad = cantidad;
    }

    public void setMonto(Float monto) {
        this.monto = monto;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getIdempleado() {
        return idempleado;
    }

    public void setIdempleado(int idempleado) {
        this.idempleado = idempleado;
    }

    public int getIdmovimiento() {
        return idmovimiento;
    }

    public void setIdmovimiento(int idmovimiento) {
        this.idmovimiento = idmovimiento;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idrhIngresoEgreso != null ? idrhIngresoEgreso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IngresoEgreso)) {
            return false;
        }
        IngresoEgreso other = (IngresoEgreso) object;
        if ((this.idrhIngresoEgreso == null && other.idrhIngresoEgreso != null) || (this.idrhIngresoEgreso != null && !this.idrhIngresoEgreso.equals(other.idrhIngresoEgreso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelos.IngresoEgreso[ idrhIngresoEgreso=" + idrhIngresoEgreso + " ]";
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
                case "fecha": {
                    fecha = DFUtils.setValueAux(obj, tipo, eliminarValoresVariables, fecha);
                    break;
                }
                case "monto": {
                    monto = DFUtils.setValueAux(obj, tipo, eliminarValoresVariables, monto);
                    break;
                }
                case "cantidad": {
                    cantidad = DFUtils.setValueAux(obj, tipo, eliminarValoresVariables, cantidad);
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
    public void fieldsActivados(boolean si_o_no
    ) {
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
        list.add("monto");
        list.add("fecha");
        list.add("cantidad");
        return list;
    }

}
