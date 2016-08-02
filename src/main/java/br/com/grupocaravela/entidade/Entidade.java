package br.com.grupocaravela.entidade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="entidade")
public class Entidade implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long id;
	private String nome;
	private Date dataCriacao;
	private String obs;
	private Boolean filho;
	private Entidade entidade;
	
	private List<Credito> listaCredito = new ArrayList<>();
	private List<Debito> listaDebito = new ArrayList<>();
	private List<DespesaFixa> listaDespesasFixas = new ArrayList<>();
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "nome", nullable = false, length = 50)
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data", nullable = false)
	public Date getDataCriacao() {
		return dataCriacao;
	}
	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}
	
	@Column(name = "observacao", columnDefinition = "text")
	public String getObs() {
		return obs;
	}
	public void setObs(String obs) {
		this.obs = obs;
	}
	
	public Boolean getFilho() {
		return filho;
	}
	public void setFilho(Boolean filho) {
		this.filho = filho;
	}
	
	@ManyToOne
	@JoinColumn(name = "entidade_id")
	public Entidade getEntidade() {
		return entidade;
	}
	public void setEntidade(Entidade entidade) {
		this.entidade = entidade;
	}
	
	@OneToMany(mappedBy = "entidade", cascade = CascadeType.ALL, orphanRemoval = true)
	public List<Credito> getListaCredito() {
		return listaCredito;
	}
	public void setListaCredito(List<Credito> listaCredito) {
		this.listaCredito = listaCredito;
	}
	
	@OneToMany(mappedBy = "entidade", cascade = CascadeType.ALL, orphanRemoval = true)
	public List<Debito> getListaDebito() {
		return listaDebito;
	}
	public void setListaDebito(List<Debito> listaDebito) {
		this.listaDebito = listaDebito;
	}
	
	@OneToMany(mappedBy = "entidade", cascade = CascadeType.ALL, orphanRemoval = true)
	public List<DespesaFixa> getListaDespesasFixas() {
		return listaDespesasFixas;
	}
	public void setListaDespesasFixas(List<DespesaFixa> listaDespesasFixas) {
		this.listaDespesasFixas = listaDespesasFixas;
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
		Entidade other = (Entidade) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	public String toString(){
        return this.nome;
    }
	
}
