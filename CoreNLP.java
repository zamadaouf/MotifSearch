package StageENSAO;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import edu.stanford.nlp.ling.CoreAnnotations.LemmaAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.simple.Document;
import edu.stanford.nlp.util.CoreMap;
//import stageENSAO.PatternSearch;

public class CoreNLP extends JFrame implements PatternSearch{
	private static final long serialVersionUID = 1L;
	public static String text;
	public static String motifLemmatized;

	public static JTable resultat;
	public static DefaultTableModel model;
	
	public static List <String> words = new ArrayList<String>();
	public static List <String> tokenLemmas = new ArrayList<String>();
	public static List <String> posTags = new ArrayList<String>();
	public static List <String> numSents = new ArrayList<String>();
	public static List <String> numWords = new ArrayList<String>();
	public static String s="";

	
/***************************************************************************************************************************/
	public void search( String filePath,  String motif) {
		
		// creates a StanfordCoreNLP object, with POS tagging, lemmatization
		Properties props = new Properties();
		props.setProperty("annotators", "tokenize, ssplit, pos, lemma");
		StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

		// read some text from a PDF
		try {
			text = PatternSearch.pdfToTxt(filePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// create an empty Annotation just with the given text
		Annotation document = new Annotation(text);
		
		// run all Annotators on this text
		pipeline.annotate(document);
		

		
//		if (!PatternSearch.isArab(text)) {
			// these are all the sentences in this document
			List <CoreMap> sentences = document.get(SentencesAnnotation.class);
			
	        Document motif2Check = new Document(motif);
	        // Lemmatiza the motif
	    	motifLemmatized = motif2Check.sentence(0).lemma(0).toString();
	    	String motif2check = "\\b"+motifLemmatized+"\\b";
	    	
	    	int numSent=1;
	        for (CoreMap sentence : sentences) {
	        	
	        	// traversing the words in the current sentence
	            for (CoreLabel token : sentence.get(TokensAnnotation.class)) {
	            	
	            	// this is the text of the token
	            	String word = token.get(TextAnnotation.class);  
	            	
	            	// this is the Lemma of the token
	            	String tokenLemma = token.get(LemmaAnnotation.class);  
	            	
	            	// this is the POS tag of the token
	            	String pos = token.get(PartOfSpeechAnnotation.class);
	            	
	            	Pattern checkRegex = Pattern.compile(motif2check);
	            	//compare the motifLemmatized with the tokenLemma
	        		Matcher regexMatcher = checkRegex.matcher(tokenLemma);
	        		
	        		while(regexMatcher.find()) {
	        			if(regexMatcher.group().length()!=0) {
			               
	        				words.add(word);                 
			                tokenLemmas.add(tokenLemma);
			                posTags.add(pos);
			                numSents.add(Integer.toString(numSent));
			                numWords.add(Integer.toString(token.index()));
		                
	        			}
	        		}
		        }
	        numSent++;
	        }
	        
//		}else {
//			
//			MaxentTagger tagger = new MaxentTagger("C:\\Users\\amdf\\Downloads\\stanford-postagger-full-2018-02-27\\stanford-postagger-full-2018-02-27\\models\\arabic-train.tagger");
//			
//			List<List<HasWord>> sentences = MaxentTagger.tokenizeText(new StringReader(text));
//			
//			// traversing sentences
//			for (List sentence : sentences) {
//			
//				List tSentence = tagger.tagSentence(sentence);
//				
//				s+=SentenceUtils.listToString(tSentence, false)+"\n";
//					  
//			}
//		}
			
		
	}
	
/***************************************************************************************************************************/
	
	public void affiche() {

//		if (!PatternSearch.isArab(text)) {
			
    		JPanel popUpPanel = new JPanel(new BorderLayout()); 
    		JDialog boite = new JDialog();
    		model= new DefaultTableModel(null, new String[] { "id", "Motif's Lemma", "Words found", "POS", "Sentence's number", "Word's number"});
    		JTable result= new JTable (model);
    		JScrollPane spn = new JScrollPane(result);
    		
			result.setEnabled(false);
			result.setPreferredScrollableViewportSize(new Dimension(680,120));			
			popUpPanel.add(spn);
			
			Object[] ob = new Object[6];
			//add all the words found in a table
    		for (int i = 0; i < words.size(); i++) {
    			ob[0]= i+1; 
    			ob[1]=tokenLemmas.get(i); 
    			ob[2]=words.get(i);
    			ob[3]=posTags.get(i);
    			ob[4]=numSents.get(i);
    			ob[5]=numWords.get(i);
    			model.addRow(ob);
    		}
    		
			boite.getContentPane().add(popUpPanel);
			boite.setLocation(400, 100);
			boite.setSize(600, 500);
			boite.setResizable(false);
			boite.setVisible(true);	
			
//		}else {
////			  JOptionPane.showMessageDialog(this, s);
//			  TextArea zoneText;			  
//			  zoneText=new TextArea(200,50);
//			  zoneText.setText(s);
//			  getContentPane().add(zoneText);
//				  
//			  setSize(480, 220);
//			  setVisible(true);
//		}
	}
	
/***************************************************************************************************************************/

}