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
@Table(name = "rh_movimiento")
@NamedQueries({
    @NamedQuery(name = "Movimiento.findAll", query = "SELECT m FROM Movimiento m")})
public class Movimiento implements Serializable, MvcInterface {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idrh_movimiento")
    private Integer idrhMovimiento;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "porc_sobre_base")
    private String porcSobreBase;
    @Column(name = "monto_fijo")
    private String montoFijo;
    @Column(name = "monto")
    private Float monto;
    @Column(name = "tipo")
    private String tipo;
    @Column(name = "activo")
    private String activo;

    @Column(name = "automatico")
    private String automatico;

    @Transient
    private HashMap<String, Object> mapFields = new HashMap<>();

    public Movimiento() {
    }

    public Movimiento(Integer idrhMovimiento) {
        this.idrhMovimiento = idrhMovimiento;
    }

    public Integer getIdrhMovimiento() {
        return idrhMovimiento;
    }

    public String getActivo() {
        return activo;
    }

    public void setActivo(String activo) {
        this.activo = activo;
    }

    public void setIdrhMovimiento(Integer idrhMovimiento) {
        this.idrhMovimiento = idrhMovimiento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPorcSobreBase() {
        return porcSobreBase;
    }

    public void setPorcSobreBase(String porcSobreBase) {
        this.porcSobreBase = porcSobreBase;
    }

    public String getMontoFijo() {
        return montoFijo;
    }

    public void setMontoFijo(String montoFijo) {
        this.montoFijo = montoFijo;
    }

    public Float getMonto() {
        return monto;
    }

    public void setMonto(Float monto) {
        this.monto = monto;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getAutomatico() {
        return automatico;
    }

    public void setAutomatico(String automatico) {
        this.automatico = automatico;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idrhMovimiento != null ? idrhMovimiento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Movimiento)) {
            return false;
        }
        Movimiento other = (Movimiento) object;
        if ((this.idrhMovimiento == null && other.idrhMovimiento != null) || (this.idrhMovimiento != null && !this.idrhMovimiento.equals(other.idrhMovimiento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelos.Movimiento[ idrhMovimiento=" + idrhMovimiento + " ]";
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
    public void setValue(String llave, DFMvcEnum tip, boolean eliminarValoresVariables) {
        Object obj = mapFields.get(llave);
        try {
            switch (llave) {
                case "descripcion": {
                    descripcion = DFUtils.setValueAux(obj, tip, eliminarValoresVariables, descripcion);
                    break;
                }
                case "automatico": {
                    automatico = DFUtils.setValueAux(obj, tip, eliminarValoresVariables, automatico);
                    break;
                }
                case "tipo": {
                    tipo = DFUtils.setValueAux(obj, tip, eliminarValoresVariables, tipo);
                    break;
                }
                case "monto": {
                    monto = DFUtils.setValueAux(obj, tip, eliminarValoresVariables, monto);
                    break;
                }
                case "montoFijo": {
                    montoFijo = DFUtils.setValueAux(obj, tip, eliminarValoresVariables, montoFijo);
                    break;
                }
                case "porcSobreBase": {
                    porcSobreBase = DFUtils.setValueAux(obj, tip, eliminarValoresVariables, porcSobreBase);
                    break;
                }
                case "activo": {
                    activo = DFUtils.setValueAux(obj, tip, eliminarValoresVariables, activo);
                    break;
                }
            }
            if (obj instanceof JTextField) {
                ((JTextField) obj).setBorder(DFUtils.getBorderNormal());
            } else if (obj instanceof JTextArea) {
                ((JTextArea) obj).setBorder(DFUtils.getBorderNormal());
            }
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

        list.add("descripcion");
        list.add("porcSobreBase");
        list.add("montoFijo");
        list.add("monto");
        list.add("tipo");
        list.add("automatico");
        return list;
    }

}
