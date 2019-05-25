package StageENSAO;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class CompareDoc {

	/* ----- Calcul ligne ----- */
	public static int calculLigne(String ch) {
		String[] lines = ch.split("\r\n|\r|\n");
		return lines.length;
	}

	/* ----- Calcul mots ----- */
	public static int calculMots(String ch) {
		char[] sentence = ch.toCharArray();
		boolean inWord = false;
		int wordCt = 0;
		for (char c : sentence) {
			if (c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z') {
				if (!inWord) {
					wordCt++;
					inWord = true;
				}
			} else {
				inWord = false;
			}
		}
		return wordCt;
	}

	/* ----- Calcul mots distincts ----- */
	public static int calculMotsDist(String ch) {
		Set<String> noOWoInString = new HashSet<String>();
		String[] words = ch.split(" ");
		// noOWoInString.addAll(words);
		for (String wrd : words) {
			noOWoInString.add(wrd);
		}
		return noOWoInString.size();
	}

	/* ----- Comparer les docs ----- */
	public static String compare(String doc1, String doc2) {
		if(calculLigne(PatternSearch.lireTxt(doc1))>calculLigne(PatternSearch.lireTxt(doc2))) {
			String doc3 = doc1;
			doc1 = doc2;
			doc2 = doc3;
		}
		String txt="";
		try (BufferedReader reader1 = new BufferedReader(new FileReader(doc1));
				BufferedReader reader2 = new BufferedReader(new FileReader(doc2))) {
			HashSet<String> file1 = new HashSet<String>();
			String s = null;
			while ((s = reader1.readLine()) != null) {
				file1.add(s);
			}

			while ((s = reader2.readLine()) != null) {
				if (file1.contains(s))
					txt+=s;
			}
		} catch (IOException e) {
			System.out.println(e);
		}
		return txt;

	}

}
