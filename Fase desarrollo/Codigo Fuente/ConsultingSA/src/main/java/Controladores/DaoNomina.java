/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Interfaces.DaoEntity;
import Modelos.Nomina;
import Utils.HbUtils;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author echacon
 */
public class DaoNomina extends DaoEntity<Nomina> {

    public DaoNomina() {
        super(new Nomina());
    }

    public List<Nomina> buscarPorDescripcionLike(String nombre) {
        String consulta = "SELECT a FROM Nomina a where lower(a.descripcion) like '%" + nombre.toLowerCase() + "%'";
        Query query = HbUtils.getEntityManager().createQuery(consulta);
        return query.getResultList();
    }

    public Nomina buscarPorDescripcion(String nombre) {
        String consulta = "SELECT a FROM Nomina a where lower(a.descripcion) = '" + nombre.toLowerCase() + "'";
        Query query = HbUtils.getEntityManager().createQuery(consulta);
        return (Nomina) query.getSingleResult();
    }

    public Nomina buscarPorCodigoDescripion(String codigo, String descripcion) {
        String consulta = "SELECT a FROM Nomina a where lower(a.codigoNomina) = '" + codigo.toLowerCase() + "' and lower(a.descripcion) = '" + descripcion.toLowerCase() + "'";
        Query query = HbUtils.getEntityManager().createQuery(consulta);
        return (Nomina) query.getSingleResult();
    }
}
