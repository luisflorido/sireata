package br.edu.utfpr.dv.sireata.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="comentarios")
@Data
public class Comentario {
	
	public enum SituacaoComentario{
		NAOANALISADO(0), ACEITO(1), RECUSADO(2);
		
		private final int value; 
		SituacaoComentario(int value){ 
			this.value = value; 
		}
		
		public int getValue(){ 
			return value;
		}
		
		public static SituacaoComentario valueOf(int value){
			for(SituacaoComentario u : SituacaoComentario.values()){
				if(u.getValue() == value){
					return u;
				}
			}
			
			return null;
		}
		
		public String toString(){
			switch(this){
				case NAOANALISADO:
					return "Não Analisado";
				case ACEITO:
					return "Aceito";
				case RECUSADO:
					return "Recusado";
				default:
					return "";
			}
		}
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idcomentario")
	private int idComentario;
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "idpauta", referencedColumnName = "idpauta", foreignKey = @ForeignKey(name = "fk_comentario_pauta"), nullable = false)
	private Pauta pauta;
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "idusuario", referencedColumnName = "idusuario", foreignKey = @ForeignKey(name = "fk_comentario_usuario"), nullable = false)
	private Usuario usuario;
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "idsituacao", referencedColumnName = "idsituacao", foreignKey = @ForeignKey(name = "fk_comentario_situacao"), nullable = false)
	private SituacaoComentario situacao;
	@Column(name="comentarios")
	private String comentarios;
	@Column(name="situacaoComentarios")
	private SituacaoComentario situacaoComentarios;
	@Column(name="motivo")
	private String motivo;
	
	public Comentario(){
		this.setIdComentario(0);
		this.setPauta(new Pauta());
		this.setUsuario(new Usuario());
		this.setSituacao(SituacaoComentario.NAOANALISADO);
		this.setSituacaoComentarios(SituacaoComentario.NAOANALISADO);
		this.setComentarios("");
		this.setMotivo("");
	}
}
