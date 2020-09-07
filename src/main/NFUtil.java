package main;

import java.io.File;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import model.Item;
import model.NotaFiscal;

public class NFUtil {
	
	private Match match;
	private String[] paths;
	
	public NFUtil(String path) {
		this.paths = new String[1];
		this.paths[0] = path;
		match = new Match("./Config/Match_Table");
	}
	
	public NFUtil(String[] paths) {
		this.paths = new String[1];
		this.paths = paths;
		match = new Match("./Config/Match_Table");
	}
	
	public static NotaFiscal[] sort(NotaFiscal[] notas) {
		Arrays.sort(notas);
		
		return notas;
	}
	
	public static Item[] sort(Item[] itens) {
		Arrays.sort(itens);
		
		return itens;
	}
	
	public NotaFiscal[] getNFsSorted() {
		ArrayList<NotaFiscal> notas = getNFsUnsorted();
		
		Collections.sort(notas);
		
		return toArray(NotaFiscal.class, notas);
	}
	
	public NotaFiscal[] getNFs() {
		ArrayList<NotaFiscal> notas = getNFsUnsorted();
		
		return toArray(NotaFiscal.class, notas);
	}
	
	public NotaFiscal[] getNFsBetween(String begin, String end) {
		ArrayList<NotaFiscal> notas = new ArrayList<>();
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		Date dataIni = null;
		Date dataFin = null;
		
		try {
			
			dataIni = format.parse(begin);
			dataFin = format.parse(end);
			
			for (NotaFiscal nota : getNFs()) {
				
				if (nota.getData().after(dataIni) && nota.getData().before(dataFin)) {
					notas.add(nota);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return toArray(NotaFiscal.class, notas);
		
	}
	
	public NotaFiscal[] getNFsAfter(String str) {
		ArrayList<NotaFiscal> notas = new ArrayList<>();
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		Date data = null;
		
		try {
			
			data = format.parse(str);
			
			for (NotaFiscal nota : getNFsSorted()) {
				
				if (nota.getData().after(data)) {
					notas.add(nota);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return toArray(NotaFiscal.class, notas);
		
	}
	
	public String[] getNotFoudDescription() {
		ArrayList<String> notFound = new ArrayList<>();
		
		for (NotaFiscal nota : getNFsUnsorted()) {
			
			for (Item item : nota.getItens()) {
				if (item.getIdentificacao().startsWith("NOT") && !notFound.contains(item.getIdentificacao())) {
					notFound.add(item.getIdentificacao());
				}
			}
		}
		
		return toArray(String.class, notFound);
		
	}
	
	public String[] getNotFoudAfter(String str) {
		ArrayList<String> notFound = new ArrayList<>();
		
		for (NotaFiscal nota : getNFsAfter(str)) {
			
			for (Item item : nota.getItens()) {
				if (item.getIdentificacao().startsWith("NOT") && !notFound.contains(item.getIdentificacao())) {
					notFound.add(item.getIdentificacao());
				}
			}
		}
		
		return toArray(String.class, notFound);
	}
	
	public Item[] getItensTipoMad(String tipoMad) {
		return getItensTipoMadBetween(tipoMad, null, null);
	}
	
	public Item[] getItensTipoMadBetween(String tipoMad, String begin, String end) {
		ArrayList<Item> itens = new ArrayList<>();
		NotaFiscal[] notas;
		
		if (begin == null || end == null) {
			
			notas = getNFs();
			
		} else {
			
			notas = getNFsBetween(begin, end);
		}
		
		for (NotaFiscal nota : notas) {
			
			for (Item item : nota.getItens()) {
				
				if (item.getTipoMad() != null) {
					
					if (item.getTipoMad().equals(tipoMad)) {
						itens.add(item);
					}
				}
				
			}	
		}
		
		return toArray(Item.class, itens);
		
	}
	
	private <T> T[] toArray(Class<T>  clazz, List<T> list) {
		@SuppressWarnings("unchecked")
		T[] temp = (T[]) Array.newInstance(clazz, list.size());
		
		list.toArray(temp);
		
		return temp;
	}
	
	private ArrayList<NotaFiscal> getNFsUnsorted() {
		ArrayList<NotaFiscal> notas = new ArrayList<>();
		
		for (String str : paths) {
			get(new File(str), notas);
		}
		
		notas.removeIf(Objects::isNull);
		
		return notas;
	}
	
	private void get(File file, ArrayList<NotaFiscal> notas) {
		if (file.isFile()) {
			if (file.getName().endsWith(".xml")) {
				NotaFiscal nf = new XmlReader(file.getPath()).getNF(match);
				notas.add(nf);
			}
			
		} else {
			String[] dirList = file.list();
			if (dirList == null) {
				
				System.err.println("CHECK DIRECTORY NAME: " + file.getPath());
				
			} else {
				
				for (String str : dirList) {
					get(new File(file.getPath() + "/" + str), notas);
				}
				
			}
			
			
		}
	}
	
}
