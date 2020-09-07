package main;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import model.Identificacao;

public class Match {
	
	private File file;
	private ArrayList<Identificacao> identificacaoList;
	
	public Match(String path) {
		update(path);
	}
	
	public void update(String pathCub) {
		file = new File(pathCub);
		identificacaoList = cleanAndSplit(load());
		
	}
	
	private String load() {
		Scanner scann;
		String str = "";
		String temp;
		
		
		try {
			scann = new Scanner(file);
			
			while (scann.hasNextLine()) {
				temp = scann.nextLine();
				str += temp;
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return str;
	}
	
	private ArrayList<Identificacao> cleanAndSplit(String str) {
		String splited[] = str.split(";");
		ArrayList<Identificacao> list = new ArrayList<>();
		String[] temp;
		Identificacao iden;
		Float cub;
		
		for (String s : splited) {
			
			temp = s.split(":");
			
			iden = new Identificacao();
			iden.setDescricao(temp[0].replace("\"", ""));
			
			cub = temp[1].equals("") ? null : Float.parseFloat(temp[1]);
			iden.setCubagem(cub);
			
			iden.setTipoMad(temp[2]);
			
			list.add(iden);
		}
		
		return list;
	}
	
	public void printMap() {
		for (Identificacao iden : identificacaoList) {
			System.out.println(iden.getDescricao() + "|" + iden.getTipoMad() + "|" + iden.getCubagem());
		}
	}
	
	public Identificacao getIdentificacao(String descricao) {
		Identificacao iden = null;
		
		for (int i = 0; i < identificacaoList.size(); i++) {
			
			if (identificacaoList.get(i).equals(new Identificacao(descricao))) {
				iden = identificacaoList.get(i);
			}
		}
		
		if (iden == null) {
			iden = new Identificacao("NOT FOUND: " + descricao);
		}
		
		return iden;
	}
	
}
