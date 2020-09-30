package br.edu.utfpr.dv.sireata.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="pautas")
@Data
public class Pauta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idpauta")
	private int idPauta;
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "idata", referencedColumnName = "idata", foreignKey = @ForeignKey(name = "fk_pauta_ata"), nullable = false)
	private Ata ata;
	@Column(name="ordem")
	private int ordem;
	@Column(name="titulo")
	private String titulo;
	@Column(name="descricao")
	private String descricao;
	
	public Pauta(){
		this.setIdPauta(0);
		this.setAta(new Ata());
		this.setOrdem(1);
		this.setTitulo("");
		this.setDescricao("");
	}
}
