package br.edu.utfpr.dv.sireata.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import br.edu.utfpr.dv.sireata.util.DateUtils;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="ata")
@Data
public class Ata {
	
	public enum TipoAta{
		ORDINARIA(0), EXTRAORDINARIA(1);
		
		private final int value; 
		TipoAta(int value){ 
			this.value = value; 
		}
		
		public int getValue(){ 
			return value;
		}
		
		public static TipoAta valueOf(int value){
			for(TipoAta u : TipoAta.values()){
				if(u.getValue() == value){
					return u;
				}
			}
			
			return null;
		}
		
		public String toString(){
			switch(this){
				case ORDINARIA:
					return "Ordinária";
				case EXTRAORDINARIA:
					return "Extraordinária";
				default:
					return "";
			}
		}
	}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idata")
	private int idAta;
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "idorgao", referencedColumnName = "idorgao", foreignKey = @ForeignKey(name = "fk_ata_orgao"), nullable = false)
	private Orgao orgao;
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "idpresidente", referencedColumnName = "idpresidente", foreignKey = @ForeignKey(name = "fk_ata_presidente"), nullable = false)
	private Usuario presidente;
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "idsecretario", referencedColumnName = "idsecretario", foreignKey = @ForeignKey(name = "fk_ata_secretario"), nullable = false)
	private Usuario secretario;
	@Column(name="tipo")
	private TipoAta tipo;
	@Column(name="numero")
	private int numero;
	@Column(name="data")
	private Date data;
	@Column(name="local")
	private String local;
	@Column(name="localCompleto")
	private String localCompleto;
	@Column(name="dataLimiteComentarios")
	private Date dataLimiteComentarios;
	@Column(name="consideracoesIniciais")
	private String consideracoesIniciais;
	@Column(name="aceitarComentarios")
	private boolean aceitarComentarios;
	@Column(name="audio")
	private byte[] audio;
	@Column(name="publicada")
	private boolean publicada;
	@Column(name="dataPublicacao")
	private Date dataPublicacao;
	@Column(name="documento")
	private byte[] documento;
	@OneToMany(mappedBy = "pautas")
	private List<Pauta> pauta;
	@OneToMany(mappedBy = "ataparticipantes")
	private List<AtaParticipante> participantes;
	@OneToMany(mappedBy = "anexos")
	private List<Anexo> anexos;
	
	public Ata(){
		this.setIdAta(0);
		this.setOrgao(new Orgao());
		this.setPresidente(new Usuario());
		this.setSecretario(new Usuario());
		this.setTipo(TipoAta.ORDINARIA);
		this.setNumero(0);
		this.setData(DateUtils.getNow().getTime());
		this.setLocal("");
		this.setLocalCompleto("");
		this.setDataLimiteComentarios(DateUtils.getToday().getTime());
		this.setConsideracoesIniciais("");
		this.setAudio(null);
		this.setPublicada(false);
		this.setDataPublicacao(new Date());
		this.setPauta(null);
		this.setParticipantes(null);
		this.setAnexos(null);
	}

	public String getNome(){
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		
		return df.format(this.getData());
	}

}
