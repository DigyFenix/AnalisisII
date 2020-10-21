package Interfaces;

import Enums.InsertUpdate;
import Utils.HbUtils;
import java.util.List;
import javax.persistence.*;

/**
 *
 * @author echacon
 * @param <T> Se implementa al extender esta clase
 */
public abstract class DaoEntity<T> {

    T t;

    public DaoEntity(T t) {
        this.t = t;
    }

    public List<T> listar() {
        String consulta = "SELECT a FROM " + t.getClass().getSimpleName() + " a ";
        Query query = HbUtils.getEntityManager().createQuery(consulta);
        return query.getResultList();
    }

    public void insertarActualizar(T t, InsertUpdate tipo) throws Exception {
        try {
            EntityManager em = HbUtils.getEntityManager();
            em.getTransaction().begin();
            if (tipo.equals(InsertUpdate.INSERTAR)) {
                em.persist(t);
            } else if (tipo.equals(InsertUpdate.ACTUALIZAR)) {
                em.merge(t);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new Exception(e.getCause().getMessage());
        }
    }

    public T buscarPorId(int id) {
        return HbUtils.getEntityManager().find((Class<T>) t.getClass(), id);
    }

}
