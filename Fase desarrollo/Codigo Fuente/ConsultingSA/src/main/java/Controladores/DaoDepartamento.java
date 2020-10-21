package Controladores;

import Interfaces.DaoEntity;
import Modelos.Departamento;
import Utils.HbUtils;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author echacon
 */
public class DaoDepartamento extends DaoEntity<Departamento> {

    public DaoDepartamento() {
        super(new Departamento());
    }

    public List<Departamento> buscarPorDescripcionLike(String nombre) {
        String consulta = "SELECT a FROM Departamento a where lower(a.descripcion) like '%" + nombre.toLowerCase() + "%'";
        Query query = HbUtils.getEntityManager().createQuery(consulta);
        return query.getResultList();
    }

    public Departamento buscarPorDescripcion(String nombre) {
        String consulta = "SELECT a FROM Departamento a where lower(a.descripcion) = '" + nombre.toLowerCase() + "'";
        Query query = HbUtils.getEntityManager().createQuery(consulta);
        return (Departamento) query.getSingleResult();
    }
}
