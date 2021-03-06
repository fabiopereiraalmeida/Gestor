package br.com.grupocaravela.entidade;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="usuario")
public class Usuario implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String nome;
	private String usuario;
	private String senha;
	private Boolean consultor;
	private Boolean comum;
	private Boolean administrador;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "nome", nullable = false, length = 60)
	public String getNome() {
		return nome;
	}
		
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@Column(name = "usuario", nullable = false, length = 20)
	public String getUsuario() {
		return usuario;
	}
	
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	@Column(name = "senha", nullable = false, length = 20)
	public String getSenha() {
		return senha;
	}
	
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	@Column(name = "consultor")
	public Boolean getConsultor() {
		return consultor;
	}
	
	public void setConsultor(Boolean consultor) {
		this.consultor = consultor;
	}
	
	@Column(name = "comum")
	public Boolean getComum() {
		return comum;
	}
	
	public void setComum(Boolean comum) {
		this.comum = comum;
	}
	
	@Column(name = "administrador")
	public Boolean getAdministrador() {
		return administrador;
	}
	
	public void setAdministrador(Boolean administrador) {
		this.administrador = administrador;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	

}
