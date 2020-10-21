package Controladores;

import Interfaces.DaoEntity;
import Modelos.Empresa;
import Utils.HbUtils;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author echacon
 */
public class DaoEmpresa extends DaoEntity<Empresa> {

    public DaoEmpresa() {
        super(new Empresa());
    }

    public List<Empresa> buscarPorDescripcionLike(String nombre) {
        String consulta = "SELECT a FROM Empresa a where lower(a.descripcion) like '%" + nombre.toLowerCase() + "%'";
        Query query = HbUtils.getEntityManager().createQuery(consulta);
        return query.getResultList();
    }

    public Empresa buscarPorDescripcion(String nombre) {
        String consulta = "SELECT a FROM Empresa a where lower(a.descripcion) = '" + nombre.toLowerCase() + "'";
        Query query = HbUtils.getEntityManager().createQuery(consulta);
        return (Empresa) query.getSingleResult();
    }
}
