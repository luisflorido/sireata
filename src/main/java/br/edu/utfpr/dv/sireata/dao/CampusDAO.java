package br.edu.utfpr.dv.sireata.dao;

import br.edu.utfpr.dv.sireata.EntityUtil;
import br.edu.utfpr.dv.sireata.model.Campus;

import java.util.List;

public class CampusDAO extends EntityUtil {

    public CampusDAO() {
        super();
    }

    public Campus buscarPorId(int id) {
        return entityManager.find(Campus.class, id);
    }

    public Campus buscarPorDepartamento(int idDepartamento) {
        return entityManager.createQuery("SELECT idCampus FROM departamentos WHERE idDepartamento=" + idDepartamento,
                Campus.class).getSingleResult();
    }

    public List<Campus> listarTodos(boolean apenasAtivos) {
        return entityManager.createQuery("SELECT * FROM campus " + (apenasAtivos ? " WHERE ativo=1" : "") + " ORDER " +
                "BY nome", Campus.class).getResultList();
    }

    public List<Campus> listarParaCriacaoAta(int idUsuario) {
        return entityManager.createQuery("SELECT DISTINCT campus.* FROM campus " +
                "INNER JOIN departamentos ON departamentos.idCampus=campus.idCampus " +
                "INNER JOIN orgaos ON orgaos.idDepartamento=departamentos.idDepartamento " +
                "WHERE campus.ativo=1 AND (orgaos.idPresidente=" + String.valueOf(idUsuario) + " OR orgaos.idSecretario=" + String.valueOf(idUsuario) +
                ") ORDER BY campus.nome", Campus.class).getResultList();
    }

    public List<Campus> listarParaConsultaAtas(int idUsuario) {
        return entityManager.createQuery("SELECT DISTINCT campus.* FROM campus " +
                "INNER JOIN departamentos ON departamentos.idCampus=campus.idCampus " +
                "INNER JOIN orgaos ON orgaos.idDepartamento=departamentos.idDepartamento " +
                "INNER JOIN atas ON atas.idOrgao=orgaos.idOrgao " +
                "INNER JOIN ataParticipantes ON ataParticipantes.idAta=atas.idAta " +
                "WHERE atas.publicada=0 AND ataParticipantes.presente=1 AND ataParticipantes.idUsuario=" + String.valueOf(idUsuario) +
                " ORDER BY campus.nome", Campus.class).getResultList();
    }

    public int store(Campus campus) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(campus);
            entityManager.getTransaction().commit();
            return campus.getIdCampus();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
        return -1;
    }

    public int update(Campus campus) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(campus);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
        return campus.getIdCampus();
    }

    public int salvar(Campus campus) {
        boolean insert = (campus.getIdCampus() == 0);
        return insert ? store(campus) : update(campus);
    }
}
