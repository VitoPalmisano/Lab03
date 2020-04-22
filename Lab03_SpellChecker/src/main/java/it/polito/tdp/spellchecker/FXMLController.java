package it.polito.tdp.spellchecker;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.*;
import java.util.ResourceBundle;

import it.polito.tdp.spellchecker.model.Model;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class FXMLController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> cmbLanguage;

    @FXML
    private TextArea txtInput;
    
    @FXML
    private ComboBox<String> cmbMetod;

    @FXML
    private Button btnSpell;

    @FXML
    private TextArea txtResult;

    @FXML
    private Label lblErrors;

    @FXML
    private Button btnClear;

    @FXML
    private Label lblExecutionTime;

    @FXML
    void doReset(ActionEvent event) {
    	
    	txtResult.clear();
    	txtInput.clear();
    	lblErrors.setText("");
    	lblExecutionTime.setText("");
    	model.clearControlliDaFare();
    	model.clearWrongWords();
    }

    @FXML
    void doSpell(ActionEvent event) {
    	
    	model.clearControlliDaFare();
    	model.clearWrongWords();
    	    	
    	if(cmbLanguage.getValue()==null) {
    		txtResult.setText("You have to select a language");
    	}
    	else{
    		
	    	if(cmbMetod.getValue().contains("LinkedList"))
	    		model.setLinkedList();
	    	else
	    		model.setArrayList();
	    	
	    	this.selectLanguage(null);
	    	
	    	long startTime = System.nanoTime();
	    	
	    	String s = txtInput.getText();
	    	
	    	int numError=0;
	    	
	    	if(cmbMetod.getValue().contains("list.contains()"))
	    		numError = model.spellCheckText(s);
	    	else if(cmbMetod.getValue().contains("Linear search"))
	    		numError = model.spellCheckTextLinear(s);
	    	else if(cmbMetod.getValue().contains("Dichotomic search"))
	    		numError = model.spellCheckTextDichotomic(s);
	    	
	    	if(numError==0) {
	    		lblErrors.setText("The text contains 0 errors");
	    		txtResult.setText("No wrong word");
	    	}
	    	else if(numError==1) {
	    		lblErrors.setText("The text contains 1 error");
	    		txtResult.setText(model.getWrongWords());
	    	}
	    	else {
	    		lblErrors.setText("The text contains "+numError+" errors");
	    		txtResult.setText(model.getWrongWords());
	    	}
	    	
	    	Double time = (System.nanoTime() - startTime)/10E9;
	    	
	    	DecimalFormat df = new DecimalFormat("###.##############");
	    	
	    	lblExecutionTime.setText("Spell check completed in "+df.format(time)+" seconds");
    	}
    }

    @FXML
    void selectLanguage(ActionEvent event) {
    	
    	String language = cmbLanguage.getValue();
    	
    	if(cmbMetod.getValue().contains("LinkedList"))
    		model.setLinkedList();
    	else
    		model.setArrayList();
    	
    	boolean correctReading = this.model.getVocabulary(language);
    	
    	if(!correctReading)
    		txtResult.setText(language+" vocabulary reading error");
    	else
    		txtResult.setText(language+" language selected");
    }
    
    @FXML
    void initialize() {
        assert cmbLanguage != null : "fx:id=\"cmbLanguage\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtInput != null : "fx:id=\"txtInput\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbMetod != null : "fx:id=\"cmbMetod\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSpell != null : "fx:id=\"btnSpell\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        assert lblErrors != null : "fx:id=\"lblErrors\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnClear != null : "fx:id=\"btnClear\" was not injected: check your FXML file 'Scene.fxml'.";
        assert lblExecutionTime != null : "fx:id=\"lblExecutionTime\" was not injected: check your FXML file 'Scene.fxml'.";
        
    	cmbLanguage.getItems().addAll("Italian", "English");
    	txtResult.setText("Select a language");
    	
    	cmbMetod.getItems().addAll("list.contains() with LinkedList", "list.contains() with ArrayList", "Linear search with LinkedList", "Linear search with ArrayList", "Dichotomic search with LinkedList", "Dichotomic search with ArrayList");
    	cmbMetod.setValue("list.contains() with LinkedList");
    }

	public void setModel(Model model) {
		this.model = model;
	}
}
