package model;

public class Item implements Comparable<Item> {
	
	private float quantidade;
	
	private String identificacao;
	
	private String tipoMad;
	
	private Float cubagem;
	
	private NotaFiscal nf;
	
	public Item(String identificacao) {
		this(identificacao, null, null);
	}
	
	public Item(String identificacao, String tipoMad, Float cubagem) {
		quantidade = 0;
		this.identificacao = identificacao;
		this.tipoMad = tipoMad;
		this.cubagem = cubagem;
	}

	public Item(float quantidade, String identificacao, String tipoMad, Float cubagem) {
		this.quantidade = quantidade;
		this.identificacao = identificacao;
		this.tipoMad = tipoMad;
		this.cubagem = cubagem;
	}
	
	public float getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(float quantidade) {
		this.quantidade = quantidade;
	}

	public String getIdentificacao() {
		return identificacao;
	}

	public void setIdentificacao(String identificacao) {
		this.identificacao = identificacao;
	}

	public String getTipoMad() {
		return tipoMad;
	}

	public void setTipoMad(String tipoMad) {
		this.tipoMad = tipoMad;
	}

	public Float getCubagem() {
		return cubagem;
	}

	public void setCubagem(Float cubagem) {
		this.cubagem = cubagem > 0 ? cubagem : 0;
	}
	
	public NotaFiscal getNf() {
		return nf;
	}

	public void setNf(NotaFiscal nf) {
		this.nf = nf;
	}

	@Override
	public String toString() {
		return "Quantidade: " + quantidade + " || Identificacao: " + identificacao + " || Tipo Madeira: " + tipoMad + " || Cubagem: " + cubagem;
	}

	@Override
	public int compareTo(Item item) {
		return Integer.compare(getNf().getNum(), item.getNf().getNum());
	}
	
}
