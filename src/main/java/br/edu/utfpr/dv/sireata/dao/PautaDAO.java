package br.edu.utfpr.dv.sireata.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.edu.utfpr.dv.sireata.EntityUtil;
import br.edu.utfpr.dv.sireata.model.Anexo;
import br.edu.utfpr.dv.sireata.model.Pauta;

public class PautaDAO extends EntityUtil {

    public PautaDAO() {
        super();
    }

    public Pauta buscarPorId(int id) {
        return entityManager.find(Pauta.class, id);
    }

    public List<Pauta> listarPorAta(int idAta) {
        return entityManager.createQuery("SELECT * FROM pautas WHERE idAta=" + String.valueOf(idAta) + " ORDER BY " +
                "ordem", Pauta.class).getResultList();
    }

    public int store(Pauta pauta) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(pauta);
            entityManager.getTransaction().commit();
            return pauta.getIdPauta();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
        return -1;
    }

    public int update(Pauta pauta) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(pauta);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
        return pauta.getIdPauta();
    }

    public int salvar(Pauta pauta) {
        boolean insert = (pauta.getIdPauta() == 0);
        return insert ? store(pauta) : update(pauta);
    }

    public void excluir(int id) {
        try {
            entityManager.getTransaction().begin();
            Pauta pauta = entityManager.find(Pauta.class, id);
            entityManager.remove(pauta);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }
}
