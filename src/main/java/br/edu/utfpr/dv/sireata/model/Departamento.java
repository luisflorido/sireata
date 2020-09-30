package br.edu.utfpr.dv.sireata.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="departamentos")
@Data
public class Departamento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="iddepartamento")
	private int idDepartamento;
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "idcampus", referencedColumnName = "idcampus", foreignKey = @ForeignKey(name = "fk_departamento_campus"), nullable = false)
	private Campus campus;
	@Column(name="nome")
	private String nome;
	@Column(name="nomeCompleto")
	private String nomeCompleto;
	@Column(name="logo")
	private byte[] logo;
	@Column(name="ativo")
	private boolean ativo;
	@Column(name="site")
	private String site;
	
	public Departamento(){
		this.setIdDepartamento(0);
		this.setCampus(new Campus());
		this.setNome("");
		this.setNomeCompleto("");
		this.setLogo(null);
		this.setAtivo(true);
		this.setSite("");
	}
}
