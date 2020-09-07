package main;

import model.Item;
import model.NotaFiscal;

public class Main {

	public static void main(String[] args) {
		
		NotaFiscal[] notas;
		String[] notFound;
		String[] strArray;
		Item[] itens;
		
		String[] paths = {"./Notas/Notas de entrada", "./Notas/Notas de saida"};
		
		notas = new NFUtil(paths).getNFsAfter("01/01/2019");
		
		notFound = new NFUtil(paths).getNotFoudAfter("01/01/2019");
		
		itens = new NFUtil(paths).getItensTipoMad("eucalipto");
		
		for (Item item : NFUtil.sort(itens)) {
			System.out.println(item + " NF: " + item.getNf().getNum());
		}
		
		/*
		for (NotaFiscal nota : notas) {
			System.out.println(nota);
		}
		
		strArray = new NFLoader(paths).getNotFoudDescription();
		
		for (String str : strArray) {
			System.out.println(str);
		}*/
		
	}

}
