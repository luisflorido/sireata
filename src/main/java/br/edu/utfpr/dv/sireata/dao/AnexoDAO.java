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

public class AnexoDAO extends EntityUtil {

	public AnexoDAO() {
		super();
	}

	public Anexo buscarPorId(int id){
		return entityManager.find(Anexo.class, id);
	}
	
	public List<Anexo> listarPorAta(int idAta) throws SQLException{
		return entityManager.createQuery("SELECT anexos.* FROM anexos " +
				"WHERE idAta=" + String.valueOf(idAta) + " ORDER BY anexos.ordem", Anexo.class).getResultList();
	}

	public int store(Anexo anexo) {
		try {
			entityManager.getTransaction().begin();
			entityManager.persist(anexo);
			entityManager.getTransaction().commit();
			return anexo.getIdAnexo();
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
		}
		return -1;
	}

	public int update(Anexo anexo) {
		try {
			entityManager.getTransaction().begin();
			entityManager.merge(anexo);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
		}
		return anexo.getIdAnexo();
	}

	public int salvar(Anexo anexo) {
		boolean insert = (anexo.getIdAnexo() == 0);
		return insert ? store(anexo) : update(anexo);
	}
	
	public void excluir(int id) {
		try {
			entityManager.getTransaction().begin();
			Anexo anexo = entityManager.find(Anexo.class, id);
			entityManager.remove(anexo);
			entityManager.getTransaction().commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			entityManager.getTransaction().rollback();
		}
	}
}
