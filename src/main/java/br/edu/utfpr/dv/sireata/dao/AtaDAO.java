package br.edu.utfpr.dv.sireata.dao;

import br.edu.utfpr.dv.sireata.EntityUtil;
import br.edu.utfpr.dv.sireata.model.Anexo;
import br.edu.utfpr.dv.sireata.model.Ata;
import br.edu.utfpr.dv.sireata.model.Ata.TipoAta;
import br.edu.utfpr.dv.sireata.util.DateUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class AtaDAO extends EntityUtil {

    public AtaDAO() {
        super();
    }

    public Ata buscarPorId(int id) {
        return entityManager.find(Ata.class, id);
    }

    public Ata buscarPorNumero(int idOrgao, TipoAta tipo, int numero, int ano) {
        return entityManager.createQuery("SELECT atas.*, orgaos.nome AS orgao, p.nome AS presidente, s.nome AS secretario " +
                "FROM atas INNER JOIN orgaos ON orgaos.idOrgao=atas.idOrgao " +
                "INNER JOIN departamentos ON departamentos.idDepartamento=orgaos.idDepartamento " +
                "INNER JOIN usuarios p ON p.idUsuario=atas.idPresidente " +
                "INNER JOIN usuarios s ON s.idUsuario=atas.idSecretario " +
                "WHERE atas.publicada = 1 AND atas.idOrgao = " + idOrgao + " AND atas.tipo = " + tipo.getValue() + " AND atas.numero = " + numero + " AND YEAR(atas.data) = " + ano, Ata.class).getSingleResult();
    }

    public Ata buscarPorPauta(int idPauta) {
        return entityManager.createQuery("SELECT DISTINCT atas.*, orgaos.nome AS orgao, p.nome AS presidente, s.nome AS secretario " + "FROM atas INNER JOIN orgaos ON orgaos.idOrgao=atas.idOrgao " + "INNER JOIN departamentos ON departamentos.idDepartamento=orgaos.idDepartamento " + "INNER JOIN usuarios p ON p.idUsuario=atas.idPresidente " + "INNER JOIN usuarios s ON s.idUsuario=atas.idSecretario " + "INNER JOIN pautas ON pautas.idAta=atas.idAta " + "WHERE pautas.idPauta = " + idPauta, Ata.class).getSingleResult();
    }

    public int buscarProximoNumeroAta(int idOrgao, int ano, TipoAta tipo) {
        return entityManager.createQuery("SELECT MAX(numero) AS numero FROM atas WHERE idOrgao = " + idOrgao + " AND YEAR" +
                "(data) = " + ano +
                " AND tipo = " + tipo, int.class).getSingleResult() + 1;
    }

    public List<Ata> listar(int idUsuario, int idCampus, int idDepartamento, int idOrgao, boolean publicadas) {
        return entityManager.createQuery("SELECT DISTINCT atas.*, orgaos.nome AS orgao, p.nome AS presidente, s.nome AS secretario " +
                "FROM atas INNER JOIN ataparticipantes ON ataparticipantes.idAta=atas.idAta " +
                "INNER JOIN orgaos ON orgaos.idOrgao=atas.idOrgao " +
                "INNER JOIN departamentos ON departamentos.idDepartamento=orgaos.idDepartamento " +
                "INNER JOIN usuarios p ON p.idUsuario=atas.idPresidente " +
                "INNER JOIN usuarios s ON s.idUsuario=atas.idSecretario " +
                "WHERE ataparticipantes.idUsuario = " + String.valueOf(idUsuario) +
                " AND atas.publicada = " + (publicadas ? "1 " : "0 ") +
                (idCampus > 0 ? " AND departamentos.idCampus = " + String.valueOf(idCampus) : "") +
                (idDepartamento > 0 ? " AND departamentos.idDepartamento = " + String.valueOf(idDepartamento) : "") +
                (idOrgao > 0 ? " AND atas.idOrgao = " + String.valueOf(idOrgao) : "") +
                "ORDER BY atas.data DESC", Ata.class).getResultList();
    }

    public List<Ata> listarPublicadas() {
        return entityManager.createQuery("SELECT atas.*, orgaos.nome AS orgao, p.nome AS presidente, s.nome AS secretario " +
                "FROM atas INNER JOIN orgaos ON orgaos.idOrgao=atas.idOrgao " +
                "INNER JOIN departamentos ON departamentos.idDepartamento=orgaos.idDepartamento " +
                "INNER JOIN usuarios p ON p.idUsuario=atas.idPresidente " +
                "INNER JOIN usuarios s ON s.idUsuario=atas.idSecretario " +
                "WHERE atas.publicada=1 ORDER BY atas.data DESC", Ata.class).getResultList();
    }

    public List<Ata> listarPorOrgao(int idOrgao) throws SQLException {
        return entityManager.createQuery("SELECT atas.*, orgaos.nome AS orgao, p.nome AS presidente, s.nome AS secretario " +
                        "FROM atas INNER JOIN orgaos ON orgaos.idOrgao=atas.idOrgao " +
                        "INNER JOIN departamentos ON departamentos.idDepartamento=orgaos.idDepartamento " +
                        "INNER JOIN usuarios p ON p.idUsuario=atas.idPresidente " +
                        "INNER JOIN usuarios s ON s.idUsuario=atas.idSecretario " +
                        "WHERE atas.publicada=1 AND atas.idOrgao=" + String.valueOf(idOrgao) + " ORDER BY atas.data DESC",
                Ata.class).getResultList();
    }

    public List<Ata> listarPorDepartamento(int idDepartamento) {
        return entityManager.createQuery("SELECT atas.*, orgaos.nome AS orgao, p.nome AS presidente, s.nome AS secretario " +
                "FROM atas INNER JOIN orgaos ON orgaos.idOrgao=atas.idOrgao " +
                "INNER JOIN departamentos ON departamentos.idDepartamento=orgaos.idDepartamento " +
                "INNER JOIN usuarios p ON p.idUsuario=atas.idPresidente " +
                "INNER JOIN usuarios s ON s.idUsuario=atas.idSecretario " +
                "WHERE atas.publicada=1 AND Orgaos.idDepartamento=" + String.valueOf(idDepartamento) + " ORDER BY " +
                "atas.data DESC", Ata.class).getResultList();
    }

    public List<Ata> listarPorCampus(int idCampus) {
        return entityManager.createQuery("SELECT atas.*, orgaos.nome AS orgao, p.nome AS presidente, s.nome AS secretario " +
                "FROM atas INNER JOIN orgaos ON orgaos.idOrgao=atas.idOrgao " +
                "INNER JOIN departamentos ON departamentos.idDepartamento=orgaos.idDepartamento " +
                "INNER JOIN usuarios p ON p.idUsuario=atas.idPresidente " +
                "INNER JOIN usuarios s ON s.idUsuario=atas.idSecretario " +
                "WHERE atas.publicada=1 AND departamentos.idCampus=" + String.valueOf(idCampus) + " ORDER BY atas" +
                ".data DESC", Ata.class).getResultList();
    }

    public List<Ata> listarNaoPublicadas(int idUsuario) {
        return entityManager.createQuery("SELECT DISTINCT atas.*, orgaos.nome AS orgao, p.nome AS presidente, s.nome AS secretario " +
                "FROM atas INNER JOIN orgaos ON orgaos.idOrgao=atas.idOrgao " +
                "INNER JOIN departamentos ON departamentos.idDepartamento=orgaos.idDepartamento " +
                "INNER JOIN usuarios p ON p.idUsuario=atas.idPresidente " +
                "INNER JOIN usuarios s ON s.idUsuario=atas.idSecretario " +
                "INNER JOIN ataparticipantes ON ataparticipantes.idAta=atas.idAta " +
                "WHERE atas.publicada=0 AND ataparticipantes.idUsuario=" + String.valueOf(idUsuario) + " ORDER BY " +
                "atas.data DESC", Ata.class).getResultList();
    }

    public List<Ata> listarPorOrgao(int idOrgao, int idUsuario) {
        return entityManager.createQuery("SELECT DISTINCT atas.*, orgaos.nome AS orgao, p.nome AS presidente, s.nome AS secretario " +
                "FROM atas INNER JOIN orgaos ON orgaos.idOrgao=atas.idOrgao " +
                "INNER JOIN departamentos ON departamentos.idDepartamento=orgaos.idDepartamento " +
                "INNER JOIN usuarios p ON p.idUsuario=atas.idPresidente " +
                "INNER JOIN usuarios s ON s.idUsuario=atas.idSecretario " +
                "INNER JOIN ataparticipantes ON ataparticipantes.idAta=atas.idAta " +
                "WHERE atas.publicada=0 AND ataparticipantes.idUsuario=" + String.valueOf(idUsuario) + " AND atas" +
                ".idOrgao=" + String.valueOf(idOrgao) + " ORDER BY atas.data DESC", Ata.class).getResultList();
    }

    public List<Ata> listarPorDepartamento(int idDepartamento, int idUsuario) throws SQLException {
        return entityManager.createQuery("SELECT DISTINCT atas.*, orgaos.nome AS orgao, p.nome AS presidente, s.nome AS secretario " +
                "FROM atas INNER JOIN orgaos ON orgaos.idOrgao=atas.idOrgao " +
                "INNER JOIN departamentos ON departamentos.idDepartamento=orgaos.idDepartamento " +
                "INNER JOIN usuarios p ON p.idUsuario=atas.idPresidente " +
                "INNER JOIN usuarios s ON s.idUsuario=atas.idSecretario " +
                "INNER JOIN ataparticipantes ON ataparticipantes.idAta=atas.idAta " +
                "WHERE atas.publicada=0 AND ataparticipantes.idUsuario=" + String.valueOf(idUsuario) + " AND Orgaos" +
                ".idDepartamento=" + String.valueOf(idDepartamento) + " ORDER BY atas.data DESC", Ata.class).getResultList();
    }

    public List<Ata> listarPorCampus(int idCampus, int idUsuario) throws SQLException {
        return entityManager.createQuery("SELECT DISTINCT atas.*, orgaos.nome AS orgao, p.nome AS presidente, s.nome AS secretario " +
                "FROM atas INNER JOIN orgaos ON orgaos.idOrgao=atas.idOrgao " +
                "INNER JOIN departamentos ON departamentos.idDepartamento=orgaos.idDepartamento " +
                "INNER JOIN usuarios p ON p.idUsuario=atas.idPresidente " +
                "INNER JOIN usuarios s ON s.idUsuario=atas.idSecretario " +
                "INNER JOIN ataparticipantes ON ataparticipantes.idAta=atas.idAta " +
                "WHERE atas.publicada=0 AND ataparticipantes.idUsuario=" + String.valueOf(idUsuario) + " AND " +
                "departamentos.idCampus=" + String.valueOf(idCampus) + " ORDER BY atas.data DESC", Ata.class).getResultList();
    }

    public int store(Ata ata) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(ata);
            entityManager.getTransaction().commit();
            return ata.getIdAta();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
        return -1;
    }

    public int update(Ata ata) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(ata);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
        return ata.getIdAta();
    }

    public int salvar(Ata ata) {
        boolean insert = (ata.getIdAta() == 0);
        return insert ? store(ata) : update(ata);
    }

    public void publicar(int idAta, byte[] documento) {
        try {
            Ata ata = entityManager.find(Ata.class, idAta);
            if (!ata.isPublicada()) {
                return;
            }
            ata.setDocumento(documento);
            ata.setDataPublicacao(DateUtils.getNow().getTime());
            entityManager.getTransaction().begin();
            entityManager.merge(ata);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    public void liberarComentarios(int idAta) {
        try {
            Ata ata = entityManager.find(Ata.class, idAta);
            if (!ata.isPublicada()) {
                return;
            }
            ata.setAceitarComentarios(true);
            entityManager.getTransaction().begin();
            entityManager.merge(ata);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    public void bloquearComentarios(int idAta) {
        try {
            Ata ata = entityManager.find(Ata.class, idAta);
            ata.setAceitarComentarios(false);
            entityManager.getTransaction().begin();
            entityManager.merge(ata);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    public boolean temComentarios(int idAta) {
        return entityManager.createQuery("SELECT COUNT(comentarios.idComentario) AS qtde FROM comentarios " +
                "INNER JOIN pautas ON pautas.idPauta=comentarios.idPauta " +
                "WHERE pautas.idAta=" + String.valueOf(idAta), int.class).getSingleResult() > 0;
    }

    public boolean isPresidenteOuSecretario(int idUsuario, int idAta) {
        return entityManager.createQuery("SELECT atas.idAta FROM atas " +
                "WHERE idAta=" + String.valueOf(idAta) + " AND (idPresidente=" + String.valueOf(idUsuario) + " OR " +
                "idSecretario=" + String.valueOf(idUsuario) + ")", int.class).getSingleResult() != null;
    }

    public boolean isPresidente(int idUsuario, int idAta) {
        return entityManager.createQuery("SELECT atas.idAta FROM atas " +
                "WHERE idAta=" + String.valueOf(idAta) + " AND idPresidente=" + String.valueOf(idUsuario), int.class).getSingleResult() != null;
    }

    public boolean isPublicada(int idAta) {
        Ata ata = entityManager.find(Ata.class, idAta);
        if (!Objects.isNull(ata)) {
            return ata.isPublicada();
        }
        return false;
    }

    public boolean excluir(int idAta) {
        try {
            entityManager.getTransaction().begin();
            Ata ata = entityManager.find(Ata.class, idAta);
            entityManager.remove(ata);
            entityManager.getTransaction().commit();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
        return false;
    }
}
