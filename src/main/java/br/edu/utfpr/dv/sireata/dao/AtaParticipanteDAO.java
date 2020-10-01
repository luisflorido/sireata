package br.edu.utfpr.dv.sireata.dao;

import br.edu.utfpr.dv.sireata.EntityUtil;
import br.edu.utfpr.dv.sireata.model.AtaParticipante;

import java.util.List;

public class AtaParticipanteDAO extends EntityUtil {

    public AtaParticipanteDAO() {
        super();
    }

    public AtaParticipante buscarPorId(int id) {
        return entityManager.find(AtaParticipante.class, id);
    }

    public List<AtaParticipante> listarPorAta(int idAta) {
        return entityManager.createQuery("SELECT ataparticipantes.* FROM ataparticipantes " +
                "INNER JOIN usuarios ON usuarios.idUsuario=ataparticipantes.idUsuario " +
                "WHERE idAta=" + String.valueOf(idAta) + " ORDER BY usuarios.nome", AtaParticipante.class).getResultList();
    }

    public int store(AtaParticipante ataParticipante) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(ataParticipante);
            entityManager.getTransaction().commit();
            return ataParticipante.getIdAtaParticipante();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
        return -1;
    }

    public int update(AtaParticipante ataParticipante) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(ataParticipante);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
        return ataParticipante.getIdAtaParticipante();
    }

    public int salvar(AtaParticipante participante) {
        boolean insert = (participante.getIdAtaParticipante() == 0);
        return insert ? store(participante) : update(participante);
    }

    public void excluir(int id) {
        try {
            entityManager.getTransaction().begin();
            AtaParticipante ataParticipante = entityManager.find(AtaParticipante.class, id);
            entityManager.remove(ataParticipante);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }
}
