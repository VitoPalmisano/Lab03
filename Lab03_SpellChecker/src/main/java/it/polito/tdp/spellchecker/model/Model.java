package it.polito.tdp.spellchecker.model;

import java.io.*;
import java.util.*;

public class Model {

	private List<Word> controlliDaFare;
	private List<String> vocabulary;
	private List<Word> wrongWords;
	
	public boolean getVocabulary(String language) {
		Vocabulary vocabulary = new Vocabulary(language);
		if(vocabulary.isCorrectReading())
			this.vocabulary = vocabulary.getVocabulary();
		return vocabulary.isCorrectReading();
	}
	
	public void setLinkedList() {
		controlliDaFare = new LinkedList<Word>();
		vocabulary = new LinkedList<String>();
		wrongWords = new LinkedList<Word>();
	}
	
	public void setArrayList() {
		controlliDaFare = new ArrayList<Word>();
		vocabulary = new ArrayList<String>();
		wrongWords = new ArrayList<Word>();
	}
	
	public Integer spellCheckText(String s) {
		
		s = s.replaceAll("[.,\\/#!$%\\^&\\*;:{}=\\-_`~()\\[\\]\"?|£1234567890]", "").toLowerCase();
		StringTokenizer st = new StringTokenizer(s, " ");
		
		while(st.hasMoreTokens()) {
			controlliDaFare.add(new Word(st.nextToken()));
		}
		
		for(Word w : controlliDaFare) {
			if(this.vocabulary.contains(w.getWord())) {
				w.setItIsRight();
			}
			else {
				wrongWords.add(w);
			}
		}
		
		return wrongWords.size();
	}
	
	public Integer spellCheckTextLinear(String s) {
		
		s = s.replaceAll("[.,\\/#!$%\\^&\\*;:{}=\\-_`~()\\[\\]\"?|£1234567890]", "").toLowerCase();
		StringTokenizer st = new StringTokenizer(s, " ");
		
		while(st.hasMoreTokens()) {
			controlliDaFare.add(new Word(st.nextToken()));
		}
		
		for(Word w : controlliDaFare) {
			
			boolean trovato = false;
			
			for(String temp : vocabulary) {
				if(temp.compareTo(w.getWord())==0) {
					w.setItIsRight();
					trovato = true;
					break;
				}
			}
			
			if(!trovato)
				wrongWords.add(w);
		}
		
		return wrongWords.size();
	}

	public Integer spellCheckTextDichotomic(String s) {
		
		s = s.replaceAll("[.,\\/#!$%\\^&\\*;:{}=\\-_`~()\\[\\]\"?|£1234567890]", "").toLowerCase();
		StringTokenizer st = new StringTokenizer(s, " ");
		int size = this.vocabulary.size();
		
		while(st.hasMoreTokens()) {
			controlliDaFare.add(new Word(st.nextToken()));
		}
		
		for(Word w : controlliDaFare) {
			
			if(w.getWord().compareTo(this.vocabulary.get(size/2))<0) {
				
				List<String> tempList = vocabulary.subList(0, vocabulary.size()/2);
				boolean trovato = false;
				
				for(String temp : tempList) {
					
					if(temp.compareTo(w.getWord())==0) {
						w.setItIsRight();
						trovato = true;
						break;
					}
				}
				
				if(!trovato)
					wrongWords.add(w);
			}
			else {

				List<String> tempList = vocabulary.subList(vocabulary.size()/2, vocabulary.size());
				boolean trovato = false;
				
				for(String temp : tempList) {
					
					if(temp.compareTo(w.getWord())==0) {
						w.setItIsRight();
						trovato = true;
						break;
					}
				}
				
				if(!trovato)
					wrongWords.add(w);
			}
		}
		
		return wrongWords.size();
	}

	public String getWrongWords() {
		
		String s = "";
		
		for(Word w : wrongWords) {
			s += w.getWord()+"\n";
		}
		return s;
	}

	public void clearControlliDaFare() {
		this.controlliDaFare.clear();
	}

	public void clearWrongWords() {
		this.wrongWords.clear();
	}
}
