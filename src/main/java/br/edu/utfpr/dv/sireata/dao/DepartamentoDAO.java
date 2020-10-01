package br.edu.utfpr.dv.sireata.dao;

import br.edu.utfpr.dv.sireata.EntityUtil;
import br.edu.utfpr.dv.sireata.model.Departamento;

import java.util.List;

public class DepartamentoDAO extends EntityUtil {

    public DepartamentoDAO() {
        super();
    }

    public Departamento buscarPorId(int id) {
        return entityManager.find(Departamento.class, id);
    }

    public Departamento buscarPorOrgao(int idOrgao) {
        return entityManager.createQuery("SELECT departamentos.*, campus.nome AS nomeCampus " +
                "FROM departamentos INNER JOIN campus ON campus.idCampus=departamentos.idCampus " +
                "INNER JOIN orgaos ON orgaos.idDepartamento=departamentos.idDepartamento " +
                "WHERE orgaos.idOrgao = " + idOrgao, Departamento.class).getSingleResult();
    }

    public List<Departamento> listarTodos(boolean apenasAtivos) {
        return entityManager.createQuery("SELECT DISTINCT departamentos.*, campus.nome AS nomeCampus " +
                        "FROM departamentos INNER JOIN campus ON campus.idCampus=departamentos.idCampus " +
                        (apenasAtivos ? " WHERE departamentos.ativo=1" : "") + " ORDER BY departamentos.nome",
                Departamento.class).getResultList();
    }

    public List<Departamento> listarPorCampus(int idCampus, boolean apenasAtivos) {
        return entityManager.createQuery("SELECT DISTINCT departamentos.*, campus.nome AS nomeCampus " +
                "FROM departamentos INNER JOIN campus ON campus.idCampus=departamentos.idCampus " +
                "WHERE departamentos.idCampus=" + String.valueOf(idCampus) + (apenasAtivos ? " AND departamentos" +
                ".ativo=1" : "") + " ORDER BY departamentos.nome", Departamento.class).getResultList();
    }

    public List<Departamento> listarParaCriacaoAta(int idCampus, int idUsuario) {
        return entityManager.createQuery("SELECT DISTINCT departamentos.*, campus.nome AS nomeCampus FROM departamentos " +
                "INNER JOIN campus ON campus.idCampus=departamentos.idCampus " +
                "INNER JOIN orgaos ON orgaos.idDepartamento=departamentos.idDepartamento " +
                "WHERE departamentos.ativo=1 AND departamentos.idCampus=" + String.valueOf(idCampus) + " AND (orgaos.idPresidente=" + String.valueOf(idUsuario) + " OR orgaos.idSecretario=" + String.valueOf(idUsuario) +
                ") ORDER BY departamentos.nome", Departamento.class).getResultList();
    }

    public List<Departamento> listarParaConsultaAtas(int idCampus, int idUsuario) {
        return entityManager.createQuery("SELECT DISTINCT departamentos.*, campus.nome AS nomeCampus FROM departamentos " +
                "INNER JOIN campus ON campus.idCampus=departamentos.idCampus " +
                "INNER JOIN orgaos ON orgaos.idDepartamento=departamentos.idDepartamento " +
                "INNER JOIN atas ON atas.idOrgao=orgaos.idOrgao " +
                "INNER JOIN ataParticipantes ON ataParticipantes.idAta=atas.idAta " +
                "WHERE atas.publicada=0 AND ataParticipantes.presente=1 AND departamentos.idCampus=" + String.valueOf(idCampus) + " AND ataParticipantes.idUsuario=" + String.valueOf(idUsuario) +
                " ORDER BY departamentos.nome", Departamento.class).getResultList();
    }

    public int store(Departamento departamento) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(departamento);
            entityManager.getTransaction().commit();
            return departamento.getIdDepartamento();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
        return -1;
    }

    public int update(Departamento departamento) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(departamento);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
        return departamento.getIdDepartamento();
    }

    public int salvar(Departamento departamento) {
        boolean insert = (departamento.getIdDepartamento() == 0);
        return insert ? store(departamento) : update(departamento);
    }
}
