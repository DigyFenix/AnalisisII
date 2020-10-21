package Utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;

/**
 *
 * @author echacon
 */
public class HbUtils {

    private static EntityManagerFactory emf;
    private static String usuario, password;
    private static final String PERSISTENCE = "MysqlJPA"; //Unidad de persistencia configurada en persistence.xml
    private static Connection conexionJdbc;

    public static void iniciar(String user, String pass) {
        usuario = user;
        password = pass;
        emf = Persistence.createEntityManagerFactory(PERSISTENCE, getProperties());
    }

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    private static Map getProperties() {
        Map properties = new HashMap<>();
        properties.put("javax.persistence.jdbc.url", "jdbc:mysql://localhost:3306/consulting?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
        properties.put("javax.persistence.jdbc.user", usuario);
        properties.put("javax.persistence.jdbc.password", password);
        properties.put("javax.persistence.jdbc.driver", "com.mysql.cj.jdbc.Driver");
        properties.put("hibernate.show_sql", "true");
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        return properties;
    }

    public static boolean validarCredenciales() {
        try {
            String hql = "SELECT p from Empresa p";
            emf.createEntityManager().createQuery(hql).getResultList();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static Connection getSession() {
        EntityManager em = HbUtils.getEntityManager();
        em.getTransaction().begin();
        Session session = em.unwrap(Session.class);
        session.doWork(new Work() {
            @Override
            public void execute(Connection cnctn) throws SQLException {
                conexionJdbc = cnctn;
            }
        });
        em.getTransaction().commit();
        em.close();
        return conexionJdbc;
    }
}
