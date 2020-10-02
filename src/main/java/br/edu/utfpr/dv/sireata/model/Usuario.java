package br.edu.utfpr.dv.sireata.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="usuarios")
@Data
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idusuario")
	private int idUsuario;
	@Column(name="nome")
	private String nome;
	@Column(name="login")
	private String login;
	@Column(name="senha")
	private String senha;
	@Column(name="email")
	private String email;
	@Column(name="externo")
	private boolean externo;
	@Column(name="ativo")
	private boolean ativo;
	@Column(name="administrador")
	private boolean administrador;

	public Usuario(){
		this.setIdUsuario(0);
		this.setNome("");
		this.setLogin("");
		this.setSenha("");
		this.setEmail("");
		this.setExterno(true);
		this.setAtivo(true);
		this.setAdministrador(false);
	}
	
	@Override
	public String toString(){
		return this.getNome();
	}
	
	@Override
    public boolean equals(final Object object) {
        if (!(object instanceof Usuario)) {
            return false;
        }else return this.getIdUsuario() == ((Usuario) object).getIdUsuario();
	}
}
