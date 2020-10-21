package Controladores;

import Interfaces.DaoEntity;
import Modelos.Empleado;
import Utils.HbUtils;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author echacon
 */
public class DaoEmpleado extends DaoEntity<Empleado> {

    public DaoEmpleado() {
        super(new Empleado());
    }

    public List<Empleado> buscarPorNombre(String nombre) {
        try {
            String consulta = "SELECT a FROM Empleado a where lower(a.nombre) like '%" + nombre.toLowerCase() + "%'";
            Query query = HbUtils.getEntityManager().createQuery(consulta);
            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }
}
