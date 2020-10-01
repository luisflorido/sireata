package br.edu.utfpr.dv.sireata.dao;

import br.edu.utfpr.dv.sireata.EntityUtil;
import br.edu.utfpr.dv.sireata.model.Comentario;

import java.sql.SQLException;
import java.util.List;

public class ComentarioDAO extends EntityUtil {

    public ComentarioDAO() {
        super();
    }

    public Comentario buscarPorId(int id) {
        return entityManager.find(Comentario.class, id);
    }

    public Comentario buscarPorUsuario(int idUsuario, int idPauta) {
        return entityManager.createQuery("SELECT comentarios.*, usuarios.nome AS nomeUsuario FROM comentarios " +
                "INNER JOIN usuarios ON usuarios.idUsuario=comentarios.idUsuario " +
                "WHERE comentarios.idPauta=" + String.valueOf(idPauta) + " AND comentarios.idUsuario=" + String.valueOf(idUsuario), Comentario.class).getSingleResult();
    }

    public List<Comentario> listarPorPauta(int idPauta) throws SQLException {
        return entityManager.createQuery("SELECT comentarios.*, usuarios.nome AS nomeUsuario FROM comentarios " +
                "INNER JOIN usuarios ON usuarios.idUsuario=comentarios.idUsuario " +
                "WHERE comentarios.idPauta=" + String.valueOf(idPauta) + " ORDER BY usuarios.nome", Comentario.class).getResultList();
    }

    public int store(Comentario comentario) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(comentario);
            entityManager.getTransaction().commit();
            return comentario.getIdComentario();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
        return -1;
    }

    public int update(Comentario comentario) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(comentario);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
        return comentario.getIdComentario();
    }

    public int salvar(Comentario comentario) {
        boolean insert = (comentario.getIdComentario() == 0);
        return insert ? store(comentario) : update(comentario);
    }
}
