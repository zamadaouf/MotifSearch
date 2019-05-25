package StageENSAO;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public interface PatternSearch {
	/* ----- method de recherche a redefinir ----- */
	public void search(String text, String motif);

	/* ----- pdf to text ----- */
	public static String pdfToTxt(String path) throws IOException {
		PDDocument doc = PDDocument.load(new File(path));
		String text = new PDFTextStripper().getText(doc);
		doc.close();
		return text;
	}

	/* ----- detect if arabic or not ----- */
	public static boolean isArab(String ch) {
		for (int i = 0; i < ch.length();) {
			int c = ch.codePointAt(i);
			if (c >= 0x0600 && c <= 0x06E0)
				return true;
			i += Character.charCount(c);
		}
		return false;
	}

	/* ----- lecture fichier externe ----- */
	public static String lireTxt(String path) {
		byte[] encoded = null;
		try {
			encoded = Files.readAllBytes(Paths.get(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new String(encoded, StandardCharsets.UTF_8);
	}

	/* ----- ecriture vers fichier ----- */
	public static void ecrireTxt(String ch, String path) {
		Writer out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path), StandardCharsets.UTF_8));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		try {
			out.write(ch);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
