package it.polito.tdp.spellchecker.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Vocabulary {
	
	private List<String> vocabulary = new LinkedList<String>();
	private boolean correctReading = true;

	public Vocabulary(String language) {
		try {
			FileReader fr = new FileReader("src/main/resources/"+language+".txt");
			
			BufferedReader br = new BufferedReader(fr);
			
			String riga;
			
			while((riga = br.readLine()) != null) {
				vocabulary.add(riga);
			}
			
			fr.close();
		}
		catch(IOException e) {
			correctReading = false;
		}
	}

	public boolean isCorrectReading() {
		return correctReading;
	}

	public List<String> getVocabulary() {
		return vocabulary;
	}
}
