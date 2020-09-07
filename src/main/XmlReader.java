package main;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import model.Identificacao;
import model.Item;
import model.NotaFiscal;
import model.NotaFiscal.TipoOperacao;

public class XmlReader {
	
	private Document doc;
	
	public XmlReader(String path) {
		update(path);
	}
	
	public void update(String path) {
		doc = getDocument(new File(path));
	}
	
	public NotaFiscal getNF(Match match) {
		int tpNF;
		NotaFiscal nf = new NotaFiscal();
		
		try {
			
			nf.setNum(getElementsInt("nNF")[0]);
			nf.setCnpj(getElementsString("CNPJ")[0]);
			tpNF = getElementsInt("tpNF")[0];
			nf.setData(formatDate(getElementsString("dhEmi")[0]));
			if (tpNF == 0) {
				nf.setTipoOp(TipoOperacao.ENTRADA);
			} else if (tpNF == 1) {
				nf.setTipoOp(TipoOperacao.SAIDA);
			} else {
				nf.setTipoOp(null);
			}
			
			nf.setItens(getItensNF(match));
		} catch (Exception e) {
			//System.err.println("NÃO COMPATIVEL!");
			//System.err.println(path);
			return null;
		}
		
		return nf;
	}
	
	private Item[] getItensNF(Match match) {
		String[] identificacoes = getElementsString("xProd");
		String[] infAdicionais = getElementsString("infAdProd");
		float[] quantidades = getElementsFloat("qCom");
		Identificacao iden = null;
		
		Item[] itens = new Item[identificacoes.length];
		
		for (int i = 0; i < itens.length; i++) {
			
			iden = match.getIdentificacao(identificacoes[i] + " " + (infAdicionais.length > 0 ? infAdicionais[i] : ""));
			
			itens[i] = new Item(iden.getDescricao());
			
			itens[i].setQuantidade(quantidades[i]);
			
			itens[i].setTipoMad(iden.getTipoMad());
			
			if (iden.getCubagem() == null) {
				// Isso ocorre quando a quantidade do item já está em metros cúbicos
				itens[i].setCubagem(quantidades[i]);
			} else {
				// Neste caso é feito o calcula da cubagem total do item, levando em conta a quantidade de metros cubicos individual
				itens[i].setCubagem(quantidades[i] * iden.getCubagem());
			}
		}
		
		return itens;
	}
	
	private String[] getElementsString(String name) {
		NodeList nodeList = doc.getElementsByTagName(name);
		String[] list = new String[nodeList.getLength()];
		
		for (int i = 0; i < list.length; i++) {
			list[i] = nodeList.item(i).getTextContent();
		}
		
		return list;
	}
	
	private float[] getElementsFloat(String name) {
		String[] strList = getElementsString(name);
		float[] list = new float[strList.length];
		
		for (int i = 0; i < list.length; i++) {
			list[i] = Float.parseFloat(strList[i]);
		}
		
		return list;
	}
	
	private int[] getElementsInt(String name) {
		String[] strList = getElementsString(name);
		int[] list = new int[strList.length];
		
		for (int i = 0; i < list.length; i++) {
			list[i] = Integer.parseInt(strList[i]);
		}
		
		return list;
	}
	
	private Date formatDate(String str) {
		String[] splited = str.split("T");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			
			return format.parse(splited[0]);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private Document getDocument(File file) {
		DocumentBuilder docBuilder;
		
		try {
			
			docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			return docBuilder.parse(file);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
}
