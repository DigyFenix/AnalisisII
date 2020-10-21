package Controladores;

import Interfaces.DaoEntity;
import Modelos.Movimiento;
import Utils.HbUtils;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author echacon
 */
public class DaoMovimiento extends DaoEntity<Movimiento> {

    public DaoMovimiento() {
        super(new Movimiento());
    }

    public List<Movimiento> buscarPorDescripcionLike(String nombre) {
        String consulta = "SELECT a FROM Movimiento a where lower(a.descripcion) like '%" + nombre.toLowerCase() + "%' and a.automatico != 'S' "
                + " and lower(a.descripcion) not like '%asociacion solidarista%' ";
        Query query = HbUtils.getEntityManager().createQuery(consulta);
        return query.getResultList();
    }

    public Movimiento buscarPorDescripcion(String nombre) {
        String consulta = "SELECT a FROM Movimiento a where lower(a.descripcion) = '" + nombre.toLowerCase() + "'";
        Query query = HbUtils.getEntityManager().createQuery(consulta);
        return (Movimiento) query.getSingleResult();
    }

    public Movimiento buscarPorDescripcionLikeSingle(String nombre) {
        String consulta = "SELECT a FROM Movimiento a where lower(a.descripcion) like '%" + nombre.toLowerCase() + "%'";
        Query query = HbUtils.getEntityManager().createQuery(consulta);
        return (Movimiento) query.getSingleResult();
    }

}
