package StageENSAO;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Python implements PatternSearch {
	private String[][] result;

	public Python() {
		clean();
	}

	/* ----- recherche python (NLTK) ----- */
	public void search(String filePath, String motif) {
		clean();
		String text = "";
		try {
			text = PatternSearch.pdfToTxt(filePath);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		PatternSearch.ecrireTxt(motif, "src/stageENSAO/motif.txt");
		PatternSearch.ecrireTxt(text, "src/stageENSAO/text.txt");
		if (PatternSearch.isArab(text)) {
			PatternSearch.ecrireTxt("y", "src/stageENSAO/isArab.txt");
		} else {
			PatternSearch.ecrireTxt("n", "src/stageENSAO/isArab.txt");
		}

		try {
			Runtime.getRuntime().exec("python search.py", null, new File("src/stageENSAO"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		while (true) {
			if (new File("src/stageENSAO/resLemma.txt").exists()) {
				break;
			}
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		ArrayList<String> tabText = new ArrayList<String>(), tabLemma = new ArrayList<String>(),
				tabPos = new ArrayList<String>();
		ArrayList<Integer> tabTotal = new ArrayList<Integer>();
		ArrayList<String> Text = new ArrayList<>(
				Arrays.asList((PatternSearch.lireTxt("src/stageENSAO/resText.txt")).split("\\n")));
		ArrayList<String> Lemma = new ArrayList<>(
				Arrays.asList((PatternSearch.lireTxt("src/stageENSAO/resLemma.txt")).split("\\n")));
		for (int i = 0; i < Text.size(); i++) {
			if (!tabText.contains((String) Text.get(i))) {
				tabText.add(Text.get(i));
				tabLemma.add(Lemma.get(i));
				tabTotal.add(1);
				tabPos.add(Integer.toString(i + 1));
			} else {
				int pos = tabText.indexOf((String) Text.get(i));
				tabPos.set(pos, tabPos.get(pos) + ", " + Integer.toString(i + 1));
				tabTotal.set(pos, tabTotal.get(pos) + 1);
			}
		}
		ArrayList<ArrayList<String>> tabSorted = new ArrayList<ArrayList<String>>();
		int size = tabLemma.size();
		boolean exist = false;
		ArrayList<String> tmp = new ArrayList<String>();
		tmp.add(tabLemma.get(0));
		tmp.add(tabText.get(0));
		tmp.add(Integer.toString(tabTotal.get(0)));
		tmp.add(tabPos.get(0));
		tabSorted.add(tmp);
		for (int i = 1; i < size; i++) {
			int j;
			for (j = 0; j < tabSorted.size(); j++) {
				if (tabSorted.get(j).get(0).equals((String) tabLemma.get(i))) {
					exist = true;
					break;
				}
			}
			ArrayList<String> temp = new ArrayList<String>();
			temp.add(tabLemma.get(i));
			temp.add(tabText.get(i));
			temp.add(Integer.toString(tabTotal.get(i)));
			temp.add(tabPos.get(i));
			if (exist) {
				temp.set(0, " ");
				tabSorted.add(j+1, temp);
				exist = false;
			} else {
				tabSorted.add(temp);
			}
		}
		result = new String[size][4];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < 4; j++) {
				result[i][j] = tabSorted.get(i).get(j);
			}
		}
		clean();
	}

	/* ----- results getters ----- */
	public String[][] getResult() {
		return result;
	}

	/* ----- cleaner ----- */
	public void clean() {
		new File("src/stageENSAO/resLemma.txt").delete();
		new File("src/stageENSAO/resText.txt").delete();
		new File("src/stageENSAO/motif.txt").delete();
		new File("src/stageENSAO/text.txt").delete();
		new File("src/stageENSAO/isArab.txt").delete();
	}
}