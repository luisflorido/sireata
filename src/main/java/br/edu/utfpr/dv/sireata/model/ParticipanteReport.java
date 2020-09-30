package br.edu.utfpr.dv.sireata.model;

import lombok.Data;

@Data
public class ParticipanteReport {
	
	public int ordem;
	public String nome;
	public boolean presente;
	public String motivo;
	
	public ParticipanteReport(){
		this.setOrdem(0);
		this.setNome("");
		this.setPresente(true);
		this.setMotivo("");
	}
}
