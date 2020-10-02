import br.edu.utfpr.dv.sireata.model.Campus;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

// POR ALGUM PROBLEMA NA CONFIGURAÇÃO DO SQL, TEM-SE QUE EXECUTAR TESTE POR TESTE
public class CampusTest extends EntityUtilTest {
    @Test
    public void getAll_selectAllExiste() {
        List<Campus> campus = entityManager.createQuery("SELECT c FROM Campus c", Campus.class).getResultList();
        assertEquals(1, campus.size());
    }

    @Test
    public void getById_verificaSeCampus1Existe() {
        Campus c = entityManager.find(Campus.class, 1);
        assertNotNull(c);
        assertEquals(c.getNome(), "Teste");
    }

    @Test
    public void getById_verificaSeCampus2NaoExiste() {
        Campus c = entityManager.find(Campus.class, 2);
        assertNull(c);
    }

    @Test
    public void delete_apagaCampus1EVerificaExclusao() {
        Campus c = entityManager.find(Campus.class, 1);
        entityManager.remove(c);

        Campus cDepois = entityManager.find(Campus.class, 1);
        assertNull(cDepois);
    }

    @Test
    public void save_salvaEVerificaCriacaoCampus() {
        Campus c = new Campus();
        c.setAtivo(true);
        c.setEndereco("Endereco");
        c.setNome("Cornelio");
        c.setLogo(null);
        c.setSite("http://localhost");
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(c);
            entityManager.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Campus cCriado = entityManager.find(Campus.class, 2);
        assertNotNull(cCriado);
        assertEquals(cCriado.getNome(), "Cornelio");
    }
}
