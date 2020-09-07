package model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NotaFiscal implements Comparable<NotaFiscal> {
	
	private int num;
	
	private String cnpj;
	
	private TipoOperacao tipoOp;
	
	private Date data;
	
	private Item[] itens;
	
	public enum TipoOperacao { ENTRADA, SAIDA };
	
	
	public NotaFiscal() {
		num = -1;
		cnpj = "ND";
		tipoOp = null;
		data = new Date();
		itens = null;
	}
	
	public int getNum() {
		return num;
	}



	public void setNum(int num) {
		this.num = num;
	}



	public String getCnpj() {
		return cnpj;
	}



	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}



	public TipoOperacao getTipoOp() {
		return tipoOp;
	}



	public void setTipoOp(TipoOperacao tipoOp) {
		this.tipoOp = tipoOp;
	}
	
	public Item[] getItens() {
		return itens;
	}
	
	public void setItens(Item[] itens) {
		this.itens = itens;
		for (int i = 0; i < itens.length; i++) {
			itens[i].setNf(this);
		}
	}
	
	public Date getData() {
		return data;
	}
	
	public void setData(Date data) {
		this.data = data;
	}

	@Override
	public String toString() {
		String str = "";
		
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		
		str += "Nf: " + num + "\nCNPJ: " + cnpj + "\nTipo operacao: " + tipoOp + "\nData: " + format.format(data) + "\n";
		
		for (Item item : itens) {
			str += item + "\n";
		}
		
		return str;
	}

	@Override
	public int compareTo(NotaFiscal o) {
		return Integer.compare(getNum(), o.getNum());
	}
	
}
