package Controladores;

import Interfaces.DaoEntity;
import Modelos.EmpleadoImagen;
import Utils.HbUtils;
import javax.persistence.Query;

/**
 *
 * @author echacon
 */
public class DaoEmpleadoImagen extends DaoEntity<EmpleadoImagen> {

    public DaoEmpleadoImagen() {
        super(new EmpleadoImagen());
    }

    public EmpleadoImagen buscarPorTipo(String tipo) {
        try {
            String consulta = "SELECT a FROM EmpleadoImagen a where lower(a.tipo) = '" + tipo.toLowerCase() + "'";
            Query query = HbUtils.getEntityManager().createQuery(consulta);
            return (EmpleadoImagen) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
}
