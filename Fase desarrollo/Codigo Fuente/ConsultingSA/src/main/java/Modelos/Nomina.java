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
import javax.persistence.*;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author echacon
 */
@Entity
@Table(name = "rh_nomina")
public class Nomina implements Serializable, MvcInterface {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idrh_nomina")
    private Integer idrhNomina;
    @Column(name = "codigo_nomina")
    private String codigoNomina;
    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "idempresa")
    private int idempresa;

    @Column(name = "activo")
    private String activo;

    @Transient
    private HashMap<String, Object> mapFields = new HashMap<>();

    public Nomina() {
    }

    public Nomina(Integer idrhNomina) {
        this.idrhNomina = idrhNomina;
    }

    public Integer getIdrhNomina() {
        return idrhNomina;
    }

    public void setIdrhNomina(Integer idrhNomina) {
        this.idrhNomina = idrhNomina;
    }

    public String getCodigoNomina() {
        return codigoNomina;
    }

    public String getActivo() {
        return activo;
    }

    public void setActivo(String activo) {
        this.activo = activo;
    }

    public void setCodigoNomina(String codigoNomina) {
        this.codigoNomina = codigoNomina;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getIdempresa() {
        return idempresa;
    }

    public void setIdempresa(int idempresa) {
        this.idempresa = idempresa;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idrhNomina != null ? idrhNomina.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Nomina)) {
            return false;
        }
        Nomina other = (Nomina) object;
        if ((this.idrhNomina == null && other.idrhNomina != null) || (this.idrhNomina != null && !this.idrhNomina.equals(other.idrhNomina))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelos.Nomina[ idrhNomina=" + idrhNomina + " ]";
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
                case "descripcion": {
                    descripcion = DFUtils.setValueAux(obj, tipo, eliminarValoresVariables, descripcion);
                    break;
                }
                case "codigoNomina": {
                    codigoNomina = DFUtils.setValueAux(obj, tipo, eliminarValoresVariables, codigoNomina);
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
        list.add("codigoNomina");
        list.add("descripcion");
        list.add("activo");
        return list;
    }

}
