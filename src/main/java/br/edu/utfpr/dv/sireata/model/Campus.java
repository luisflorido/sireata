package br.edu.utfpr.dv.sireata.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "campus")
@ToString
@Data
public class Campus {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idcampus")
	private int idCampus;
	@Column(name="nome")
	private String nome;
	@Column(name="endereco")
	private String endereco;
	@Column(name="logo")
	private byte[] logo;
	@Column(name="ativo")
	private boolean ativo;
	@Column(name="site")
	private String site;
	
	public Campus(){
		this.setIdCampus(0);
		this.setNome("");
		this.setEndereco("");
		this.setLogo(null);
		this.setAtivo(true);
		this.setSite("");
	}
}
