package StageENSAO;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import opennlp.tools.chunker.ChunkerME;
import opennlp.tools.chunker.ChunkerModel;
import opennlp.tools.lemmatizer.DictionaryLemmatizer;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

			
public class OpenNLP  implements PatternSearch {
		
			static InputStream tokenModelIn = null;
			static InputStream posModelIn = null;
			static  int occurences=0;
			static String[] tokens;
			static ArrayList<Integer> occurs,position;
			static ArrayList<String>origine;
			private  DefaultTableModel mod;
			private  JTable resultat;
			private String path="D:\\stageENSAO";
			
	public OpenNLP(){
			
				mod=new DefaultTableModel( new Object[][] {},
							new String[] {	"occurence", "origine", "position"
								});
		 
				resultat = new JTable(mod);
			
				occurs = new ArrayList<Integer>();
				origine = new ArrayList<String>();
				position = new ArrayList<Integer>();
	}
	
		
		//----------------------------------------------
		//les fonctions 
	

	
	public void search(String path, String motif) {
		
		String text = null;
		String []mot=null ;
		String[] Lemmas=null;
		try {
				text = PatternSearch.pdfToTxt(path);
				mot =Lemmatizer(tokenize(motif), POSTagger(tokenize(motif)));
			
			} catch (IOException e1) {
				e1.printStackTrace(); }
	
		
				try {
					setTokens(tokenize(text));
					String[]tags = POSTagger(getTokens());
					Lemmas = Lemmatizer(getTokens(),tags);
					
				} catch (IOException e) {
					e.printStackTrace();
				}
		
				regexChecker(mot[0], Lemmas);

				Object []  row =new Object[3];
					for (int i = 0; i < occurs.size(); i++) {
						row[0]= getOccurs().get(i).toString(); 
						row[1]=getOrigine().get(i).toString();
						row[2]=getPosition().get(i).toString();
					mod.addRow(row);
					}
	//				}
	}
	
public String[] tokenize(String txt) throws IOException {
					tokenModelIn = new FileInputStream(path+"\\binFiles\\en-token.bin");
					TokenizerModel tokenModel = new TokenizerModel(tokenModelIn);
					Tokenizer tokenizer = new TokenizerME(tokenModel);
					String[] tokens = tokenizer.tokenize(txt);
			return tokens;
					}
			
			
public String [] POSTagger(String [] tokns) throws IOException {
				posModelIn = new FileInputStream(path+"\\binFiles\\en-pos-maxent.bin");

				// loading the parts-of-speech model from stream
				POSModel posModel = new POSModel(posModelIn);
				// initializing the parts-of-speech tagger with model 
				POSTaggerME posTagger = new POSTaggerME(posModel);
				// Tagger tagging the tokens
				String[] tags= posTagger.tag(tokns);
				// Getting the probabilities of the tags given to the tokens
				double[] probs= posTagger.probs();
				return tags ;	
			}
	//***** getters & setters 

public static int getOccurences() {
	return occurences;
}


public static void setOccurences(int occurences) {
	OpenNLP.occurences = occurences;
}


public static ArrayList<Integer> getOccurs() {
	return occurs;
}


public static void setOccurs(ArrayList<Integer> occurs) {
	OpenNLP.occurs = occurs;
}


public static ArrayList<Integer> getPosition() {
	return position;
}


public static void setPosition(ArrayList<Integer> position) {
	OpenNLP.position = position;
}


public static ArrayList<String> getOrigine() {
	return origine;
}


public static void setOrigine(ArrayList<String> origine) {
	OpenNLP.origine = origine;
}

public void setResultat(JTable resultat) {
	this.resultat = resultat;
}

public JTable getResultat() {
		return resultat;
							}

public String[] Lemmatizer(String [] t1,String []t2) throws IOException{
	
				// loading the dictionary to input stream
	           InputStream dictLemmatizer = new FileInputStream(path+"\\en-lemmatizer.txt");
	           // loading the lemmatizer with dictionary
	            DictionaryLemmatizer lemmatizer = new DictionaryLemmatizer(dictLemmatizer);
	 
	           // finding the lemmas
	         String[]  lemmas = lemmatizer.lemmatize(t1, t2);
	           return lemmas;
			}
	
public String[] Chunker(String [] tk, String []tg) throws  IOException {
				 InputStream ins = new FileInputStream(path+"\\binFiles\\en-chunker.bin");
		         // loading the chunker model
		         ChunkerModel chunkerModel = new ChunkerModel(ins);
		         // initializing chunker(maximum entropy) with chunker model
		         ChunkerME chunker = new ChunkerME(chunkerModel);
		         // chunking the given sentence : chunking requires sentence to be tokenized and pos tagged
		         String[] chunkss = chunker.chunk(tk,tg);
				return chunkss;
		}
	




public static String[] getTokens() {
	return tokens;
}


public static void setTokens(String[] tokens) {
	OpenNLP.tokens = tokens;
}


public static void regexChecker(String motifLemmatized, String []str2Check){
		
			Pattern checkRegex = Pattern.compile(motifLemmatized);
			for (int i = 0; i < str2Check.length; i++) {
				
				Matcher regexMatcher = checkRegex.matcher(str2Check[i]);
				
					while(regexMatcher.find()) {
						setOccurences(getOccurences()+1);
						occurs.add(getOccurences());
						origine.add(tokens[i]);
						position.add(i);
					}
				
			}}

	}




