/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author echacon
 */
@Entity
@Table(name = "rh_empleado_imagen")
@NamedQueries({
    @NamedQuery(name = "EmpleadoImagen.findAll", query = "SELECT e FROM EmpleadoImagen e")})
public class EmpleadoImagen implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idrh_empleado_imagen")
    private Integer idempleadoImagen;
    @Size(max = 45)
    @Column(name = "tipo")
    private String tipo;

    @Column(name = "idempleado")
    private int idempleado;

    @JoinColumn(name = "idimagen", referencedColumnName = "idimagen")
    @ManyToOne(cascade = CascadeType.ALL)
    private Imagen imagen;

    public EmpleadoImagen() {
    }

    public EmpleadoImagen(Integer idempleadoImagen) {
        this.idempleadoImagen = idempleadoImagen;
    }

    public Integer getIdempleadoImagen() {
        return idempleadoImagen;
    }

    public void setIdempleadoImagen(Integer idempleadoImagen) {
        this.idempleadoImagen = idempleadoImagen;
    }

    public int getIdempleado() {
        return idempleado;
    }

    public void setIdempleado(int idempleado) {
        this.idempleado = idempleado;
    }

    public Imagen getImagen() {
        return imagen;
    }

    public void setImagen(Imagen imagen) {
        this.imagen = imagen;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idempleadoImagen != null ? idempleadoImagen.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EmpleadoImagen)) {
            return false;
        }
        EmpleadoImagen other = (EmpleadoImagen) object;
        if ((this.idempleadoImagen == null && other.idempleadoImagen != null) || (this.idempleadoImagen != null && !this.idempleadoImagen.equals(other.idempleadoImagen))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelos.EmpleadoImagen[ idempleadoImagen=" + idempleadoImagen + " ]";
    }

}
