package br.edu.utfpr.dv.sireata.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "ataparticipantes")
@Data
public class AtaParticipante {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name="idataparticipante")
	private int idAtaParticipante;
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "idata", referencedColumnName = "idata", foreignKey = @ForeignKey(name = "fk_ataparticipantes_ata"), nullable = false)
	private Ata ata;
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "idparticipante", referencedColumnName = "idusuario", foreignKey = @ForeignKey(name =
			"fk_ataparticipantes_participante"), nullable = false)
	private Usuario participante;
	@Column(name = "presente")
	private boolean presente;
	@Column(name="motivo")
	private String motivo;
	@Column(name="designacao")
	private String designacao;
	@Column(name="membro")
	private boolean membro;
	
	public AtaParticipante(){
		this.setIdAtaParticipante(0);
		this.setAta(new Ata());
		this.setParticipante(new Usuario());
		this.setPresente(true);
		this.setMotivo("");
		this.setDesignacao("");
		this.setMembro(false);
	}
}
