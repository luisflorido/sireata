package br.edu.utfpr.dv.sireata.dao;

import br.edu.utfpr.dv.sireata.EntityUtil;
import br.edu.utfpr.dv.sireata.model.Orgao;
import br.edu.utfpr.dv.sireata.model.OrgaoMembro;
import br.edu.utfpr.dv.sireata.model.Usuario;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class OrgaoDAO extends EntityUtil {

    public OrgaoDAO() {
        super();
    }

    public Orgao buscarPorId(int id) {
        return entityManager.find(Orgao.class, id);
    }

    public List<Orgao> listarTodos(boolean apenasAtivos) {
        return entityManager.createQuery("SELECT orgaos.*, p.nome AS presidente, s.nome AS secretario, departamentos.nome AS departamento FROM orgaos " +
                "INNER JOIN departamentos ON departamentos.iddepartamento=orgaos.iddepartamento " +
                "INNER JOIN usuarios p ON p.idusuario=orgaos.idpresidente " +
                "INNER JOIN usuarios s ON s.idusuario=orgaos.idsecretario " +
                (apenasAtivos ? " WHERE orgaos.ativo=1" : "") + " ORDER BY orgaos.nome", Orgao.class).getResultList();
    }

    public List<Orgao> listarPorDepartamento(int idDepartamento) {
        return entityManager.createQuery("SELECT DISTINCT orgaos.*, p.nome AS presidente, s.nome AS secretario, departamentos.nome AS departamento FROM orgaos " +
                "INNER JOIN departamentos ON departamentos.iddepartamento=orgaos.iddepartamento " +
                "INNER JOIN usuarios p ON p.idusuario=orgaos.idpresidente " +
                "INNER JOIN usuarios s ON s.idusuario=orgaos.idsecretario " +
                "WHERE orgaos.idDepartamento = " + idDepartamento + " ORDER BY orgaos.nome", Orgao.class).getResultList();
    }

    public List<Orgao> listarPorCampus(int idCampus) {
        return entityManager.createQuery("SELECT DISTINCT orgaos.*, p.nome AS presidente, s.nome AS secretario, departamentos.nome AS departamento FROM orgaos " +
                "INNER JOIN departamentos ON departamentos.iddepartamento=orgaos.iddepartamento " +
                "INNER JOIN usuarios p ON p.idusuario=orgaos.idpresidente " +
                "INNER JOIN usuarios s ON s.idusuario=orgaos.idsecretario " +
                "WHERE departamentos.idCampus = " + idCampus + " ORDER BY departamentos.nome, orgaos.nome", Orgao.class).getResultList();
    }

    public List<Orgao> listarParaCriacaoAta(int idDepartamento, int idUsuario) throws SQLException {
        return entityManager.createQuery("SELECT DISTINCT orgaos.*, p.nome AS presidente, s.nome AS secretario, departamentos.nome AS departamento FROM orgaos " +
                "INNER JOIN departamentos ON departamentos.iddepartamento=orgaos.iddepartamento " +
                "INNER JOIN usuarios p ON p.idusuario=orgaos.idpresidente " +
                "INNER JOIN usuarios s ON s.idusuario=orgaos.idsecretario " +
                "WHERE orgaos.ativo=1 AND orgaos.idDepartamento=" + String.valueOf(idDepartamento) + " AND (orgaos.idPresidente=" + String.valueOf(idUsuario) + " OR orgaos.idSecretario=" + String.valueOf(idUsuario) +
                ") ORDER BY orgaos.nome", Orgao.class).getResultList();
    }

    public List<Orgao> listarParaConsultaAtas(int idDepartamento, int idUsuario) {
        return entityManager.createQuery("SELECT DISTINCT orgaos.*, p.nome AS presidente, s.nome AS secretario, departamentos.nome AS departamento FROM orgaos " +
                "INNER JOIN departamentos ON departamentos.iddepartamento=orgaos.iddepartamento " +
                "INNER JOIN atas ON atas.idOrgao=orgaos.idOrgao " +
                "INNER JOIN ataParticipantes ON ataParticipantes.idAta=atas.idAta " +
                "INNER JOIN usuarios p ON p.idusuario=orgaos.idpresidente " +
                "INNER JOIN usuarios s ON s.idusuario=orgaos.idsecretario " +
                "WHERE atas.publicada=0 AND ataParticipantes.presente=1 AND orgaos.idDepartamento=" + String.valueOf(idDepartamento) + " AND ataParticipantes.idUsuario=" + String.valueOf(idUsuario) +
                " ORDER BY orgaos.nome", Orgao.class).getResultList();
    }

    public Usuario buscarPresidente(int idOrgao) {
        Orgao orgao = entityManager.createQuery("SELECT idPresidente FROM orgaos WHERE idOrgao = " + idOrgao,
                Orgao.class).getSingleResult();
        if (!Objects.isNull(orgao)) {
            return orgao.getPresidente();
        }
        return null;
    }

    public Usuario buscarSecretario(int idOrgao) {
        Orgao orgao = entityManager.createQuery("SELECT idSecretario FROM orgaos WHERE idOrgao = " + idOrgao,
                Orgao.class).getSingleResult();
        if (!Objects.isNull(orgao)) {
            return orgao.getSecretario();
        }
        return null;
    }

    public boolean isMembro(int idOrgao, int idUsuario) {
        OrgaoMembro membro =
                entityManager.createQuery("SELECT * FROM membros WHERE idOrgao = " + idOrgao + " AND idUsuario = " + idUsuario,
                        OrgaoMembro.class).getSingleResult();
        return !Objects.isNull(membro);
    }

    public int store(Orgao orgao) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(orgao);
            entityManager.getTransaction().commit();
            return orgao.getIdOrgao();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
        return -1;
    }

    public int update(Orgao orgao) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(orgao);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
        return orgao.getIdOrgao();
    }

    public int salvar(Orgao orgao) {
        boolean insert = (orgao.getIdOrgao() == 0);
        return insert ? store(orgao) : update(orgao);
    }
}
