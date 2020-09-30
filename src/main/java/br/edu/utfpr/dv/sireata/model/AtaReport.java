package br.edu.utfpr.dv.sireata.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AtaReport {
	
	public String departamento;
	public String orgao;
	public String numero;
	public String dataHora;
	public String local;
	public List<ParticipanteReport> participantesMembros;
	public List<ParticipanteReport> demaisParticipantes;
	public String presidente;
	public String secretario;
	public String texto;
	
	public AtaReport(){
		this.setDepartamento("");
		this.setOrgao("");
		this.setNumero("");
		this.setDataHora("");
		this.setLocal("");
		this.setParticipantesMembros(new ArrayList<ParticipanteReport>());
		this.setDemaisParticipantes(new ArrayList<ParticipanteReport>());
		this.setPresidente("");
		this.setSecretario("");
		this.setTexto("");
	}

	public String getStringParticipantesMembros(){
		if((this.getParticipantesMembros() != null) && (this.getParticipantesMembros().size() > 0)){
			String retorno = this.getParticipantesMembros().get(0).getNome();
			
			for(int i = 1; i < this.getParticipantesMembros().size(); i++){
				retorno += ", " + this.getParticipantesMembros().get(i).getNome();
			}
			
			retorno += ".";
			
			return retorno;
		}else{
			return "";
		}
	}

	public String getStringDemaisParticipantes(){
		if((this.getDemaisParticipantes() != null) && (this.getDemaisParticipantes().size() > 0)){
			String retorno = this.getDemaisParticipantes().get(0).getNome();
			
			for(int i = 1; i < this.getDemaisParticipantes().size(); i++){
				retorno += ", " + this.getDemaisParticipantes().get(i).getNome();
			}
			
			retorno += ".";
			
			return retorno;
		}else{
			return "";
		}
	}
}
