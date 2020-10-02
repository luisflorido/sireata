package br.edu.utfpr.dv.sireata.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="orgaos")
@Data
public class Orgao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idorgao")
	private int idOrgao;
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "iddepartamento", referencedColumnName = "iddepartamento", foreignKey = @ForeignKey(name = "fk_orgao_departamento"), nullable = false)
	private Departamento departamento;
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "idpresidente", referencedColumnName = "idusuario", foreignKey = @ForeignKey(name = "fk_orgao_presidente"), nullable = false)
	private Usuario presidente;
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "idsecretario", referencedColumnName = "idusuario", foreignKey = @ForeignKey(name = "fk_orgao_secretario"), nullable = false)
	private Usuario secretario;
	@Column(name="nome")
	private String nome;
	@Column(name="nomeCompleto")
	private String nomeCompleto;
	@Column(name="designacaoPresidente")
	private String designacaoPresidente;
	@Column(name="ativo")
	private boolean ativo;
	@OneToMany(mappedBy = "orgao")
	private List<OrgaoMembro> membros;

	public Orgao(){
		this.setIdOrgao(0);
		this.setDepartamento(new Departamento());
		this.setPresidente(new Usuario());
		this.setSecretario(new Usuario());
		this.setNome("");
		this.setNomeCompleto("");
		this.setDesignacaoPresidente("");
		this.setAtivo(true);
		this.setMembros(new ArrayList<OrgaoMembro>());
	}
}
