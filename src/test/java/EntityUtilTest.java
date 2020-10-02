import org.h2.tools.RunScript;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;

public class EntityUtilTest {

    protected static EntityManager entityManager;

    @BeforeClass
    public static void init() {
        String FACTORY_NAME = "sireata-test";

        EntityManagerFactory factory = Persistence.createEntityManagerFactory(FACTORY_NAME);
        entityManager = factory.createEntityManager();
    }

    @Before
    public void initDb(){
        Session session = entityManager.unwrap(Session.class);
        session.doWork(new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {
                try {
                    File script = new File(getClass().getResource("/data.sql").getFile());
                    RunScript.execute(connection, new FileReader(script));
                } catch (FileNotFoundException e) {
                    throw new RuntimeException("could not initialize with script");
                }
            }
        });
    }

    @AfterClass
    public static void shutdown(){
        entityManager.clear();
    }
}
