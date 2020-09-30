package br.edu.utfpr.dv.sireata.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="membros")
@Data
public class OrgaoMembro {

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "idorgao", referencedColumnName = "idorgao", foreignKey = @ForeignKey(name = "fk_membro_orgao"), nullable = false)
	private Orgao orgao;
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "idusuario", referencedColumnName = "idusuario", foreignKey = @ForeignKey(name = "fk_membro_usuario"), nullable = false)
	private Usuario usuario;
	@Column(name="designacao")
	private String designacao;
	
	public OrgaoMembro(){
		this.setOrgao(new Orgao());
		this.setUsuario(new Usuario());
		this.setDesignacao("");
	}
}
