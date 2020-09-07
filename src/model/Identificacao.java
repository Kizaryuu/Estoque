package model;

public class Identificacao {

	private String descricao;
	
	private Float cubagem;
	
	private String tipoMad;
	
	public Identificacao() {
		descricao = null;
		cubagem = -1f;
		tipoMad = null;
	}
	
	public Identificacao(String descricao) {
		this.descricao = descricao;
		cubagem = -1f;
		tipoMad = null;
	}
	
	public String getDescricao() {
		return descricao;
	}



	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}



	public Float getCubagem() {
		return cubagem;
	}



	public void setCubagem(Float cubagem) {
		this.cubagem = cubagem;
	}



	public String getTipoMad() {
		return tipoMad;
	}



	public void setTipoMad(String tipoMad) {
		this.tipoMad = tipoMad;
	}



	@Override
	public boolean equals(Object obj) {
		Identificacao other = (Identificacao) obj;
		
		// Faz a comparação ignorando espaços entre os caracteres
		return this.descricao.replace(" ", "").equalsIgnoreCase(other.descricao.replace(" ", ""));
	}

	
	
	
	
	
}
