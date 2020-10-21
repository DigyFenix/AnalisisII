package Controladores;

import Interfaces.DaoEntity;
import Modelos.Periodo;
import Utils.HbUtils;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author echacon
 */
public class DaoPeriodo extends DaoEntity<Periodo> {

    public DaoPeriodo() {
        super(new Periodo());
    }

    public List<Periodo> buscarPorDescripcion(String descripcion) {
        try {
            String consulta = "SELECT a FROM Periodo a where lower(a.descripcion) like '%" + descripcion.toLowerCase() + "%'";
            Query query = HbUtils.getEntityManager().createQuery(consulta);
            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

}
