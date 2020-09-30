package br.edu.utfpr.dv.sireata.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "anexos")
@Data
public class Anexo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idanexo")
	private int idAnexo;
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "idata", referencedColumnName = "idata", foreignKey = @ForeignKey(name = "fk_anexos_ata"), nullable = false)
	private Ata ata;
	@Column(name="ordem")
	private int ordem;
	@Column(name="descricao")
	private String descricao;
	@Column(name="arquivo")
	private byte[] arquivo;
	
	public Anexo() {
		this.setIdAnexo(0);
		this.setAta(new Ata());
		this.setDescricao("");
		this.setOrdem(0);
		this.setArquivo(null);
	}
}
