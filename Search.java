package StageENSAO;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

// IHM java Recherche de Motifs
public class Search extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JPanel p3, p11, p12, panel_3, p2, panel_1, popupPane, panel_2, panel;
	private JLabel lb1, lb2;
	private JRadioButton outil1, outil2, outil3;
	private JTextField motif;
	private JTable tabResult, prop;
	private JButton bReset, bCalcul, addFile;
	private ButtonGroup bg = new ButtonGroup();
	private JTabbedPane tabbedPane;
	private String filePath = "", docPath1 = "", docPath2 = "";
	private JButton bDoc1, bDoc2;
	private JPanel panel_4;
	private JLabel lbdoc1, lbdoc2;
	private String[][] result;
	private JPanel panel_5;
	private JButton bCompare;
	private JPanel panel_8;
	private JTextField textField;
	private JLabel lblMots;
	private JTextField textField_1;
	private JTextField textField_2;
	private JLabel label, label_2, label_3, label_5;
	private JPanel panel_6, panel_7, panel_9;
	private JPanel panel_10;
	private JLabel lblLignes;
	private JTextField textField_3;
	private JPanel panel_11;
	private JLabel lblMots_2;
	private JTextField textField_4;
	private JPanel panel_12;
	private JLabel lblDistincts;
	private JTextField textField_5;

	/* constructor */
	public Search(String title) {
		super(title);
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		getContentPane().add(tabbedPane);
		p11 = new JPanel();
		p11.setToolTipText("");
		tabbedPane.addTab("Recherche de Motif", null, p11, "Recherche de Motif");
		p11.setBorder(new EmptyBorder(5, 5, 5, 5));
		p11.setLayout(new GridLayout(1, 2, 5, 2));
		p3 = new JPanel();
		p11.add(p3);
		p3.setLayout(new GridLayout(4, 2, 0, 0));

		panel_1 = new JPanel();
		p3.add(panel_1);
		outil1 = new JRadioButton("OpenNLP");
		outil1.setSelected(true);
		panel_1.add(outil1);
		bg.add(outil1);
		outil2 = new JRadioButton("CoreNLP");
		panel_1.add(outil2);
		bg.add(outil2);
		outil3 = new JRadioButton("NLTK");
		panel_1.add(outil3);
		bg.add(outil3);
		outil1.addActionListener(this);
		outil2.addActionListener(this);
		outil3.addActionListener(this);

		panel_2 = new JPanel();
		p3.add(panel_2);
		addFile = new JButton("Upload File");
		addFile.setForeground(new Color(51, 0, 102));
		panel_2.add(addFile);
		lb1 = new JLabel("No File Uploaded Yet");
		panel_2.add(lb1);
		lb1.setHorizontalAlignment(0);
		addFile.addActionListener(this);

		panel = new JPanel();
		p3.add(panel);
		lb2 = new JLabel("Motif \u00E0 rechercher :");
		panel.add(lb2);
		motif = new JTextField();
		motif.setColumns(20);
		panel.add(motif);

		panel_3 = new JPanel();
		p3.add(panel_3);
		bCalcul = new JButton("Rechercher");
		panel_3.add(bCalcul);
		bReset = new JButton("Effacer");
		bReset.setForeground(new Color(220, 20, 60));
		panel_3.add(bReset);
		bReset.addActionListener(this);
		bCalcul.addActionListener(this);

		p2 = new JPanel();
		p11.add(p2);
		p2.setLayout(null);
		prop = new JTable(new DefaultTableModel(
				new Object[][] { { " OpenNLP", "OK", "OK", "OK", "N/A" }, { " CoreNLP", "OK", "OK", "OK", "N/A" },
						{ " NLTK", "OK", "OK", "EN only", "OK" } },
				new String[] { "Outil", "Tokens", "Lemma", "POS", "Arabe" }));
		prop.setEnabled(false);
		prop.setRowHeight(35);
		JScrollPane scrollPane = new JScrollPane(prop);
		scrollPane.setBounds(0, 0, 276, 132);
		p2.add(scrollPane);

		p12 = new JPanel();
		tabbedPane.addTab("Documents Compare", null, p12, null);
		p12.setLayout(new BorderLayout(5, 0));

		panel_5 = new JPanel();
		p12.add(panel_5, BorderLayout.WEST);
		panel_5.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		panel_4 = new JPanel();
		panel_5.add(panel_4);

		bDoc1 = new JButton("Upload Doc1");
		bDoc1.addActionListener(this);
		panel_4.setLayout(new GridLayout(0, 1, 0, 0));
		panel_4.add(bDoc1);

		lbdoc1 = new JLabel("...");
		lbdoc1.setHorizontalAlignment(SwingConstants.CENTER);
		lbdoc1.setForeground(new Color(0, 0, 0));
		panel_4.add(lbdoc1);

		bDoc2 = new JButton("Upload Doc2");
		bDoc2.addActionListener(this);
		panel_4.add(bDoc2);

		lbdoc2 = new JLabel("...");
		lbdoc2.setHorizontalAlignment(SwingConstants.CENTER);
		lbdoc2.setForeground(new Color(0, 0, 0));
		panel_4.add(lbdoc2);

		bCompare = new JButton("Comparer");
		bCompare.setForeground(new Color(0, 100, 0));
		bCompare.addActionListener(this);
		panel_4.add(bCompare);

		panel_8 = new JPanel();
		p12.add(panel_8, BorderLayout.CENTER);
		panel_8.setLayout(new GridLayout(4, 5, 0, 0));

		panel_6 = new JPanel();
		panel_8.add(panel_6);

		JLabel lblNewLabel = new JLabel("Lignes 1:");
		panel_6.add(lblNewLabel);

		textField_1 = new JTextField();
		panel_6.add(textField_1);
		textField_1.setColumns(10);

		panel_10 = new JPanel();
		panel_8.add(panel_10);

		lblLignes = new JLabel("Lignes 2:");
		panel_10.add(lblLignes);

		textField_3 = new JTextField();
		textField_3.setColumns(10);
		panel_10.add(textField_3);

		label = new JLabel("");
		panel_8.add(label);

		panel_7 = new JPanel();
		panel_8.add(panel_7);

		lblMots = new JLabel("Mots 1:");
		panel_7.add(lblMots);

		textField = new JTextField();
		panel_7.add(textField);
		textField.setColumns(10);

		panel_11 = new JPanel();
		panel_8.add(panel_11);

		lblMots_2 = new JLabel("Mots 2:");
		panel_11.add(lblMots_2);

		textField_4 = new JTextField();
		textField_4.setColumns(10);
		panel_11.add(textField_4);

		label_5 = new JLabel("");
		panel_8.add(label_5);

		panel_9 = new JPanel();
		panel_8.add(panel_9);

		JLabel lblMots_1 = new JLabel("Distincts 1:");
		panel_9.add(lblMots_1);

		textField_2 = new JTextField();
		panel_9.add(textField_2);
		textField_2.setColumns(10);

		panel_12 = new JPanel();
		panel_8.add(panel_12);

		lblDistincts = new JLabel("Distincts 2:");
		panel_12.add(lblDistincts);

		textField_5 = new JTextField();
		textField_5.setColumns(10);
		panel_12.add(textField_5);

		label_3 = new JLabel("");
		panel_8.add(label_3);

		label_2 = new JLabel("");
		panel_8.add(label_2);
		setLocationRelativeTo(null);
		setLocation(300, 200);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
	}

	/* events */
	public void actionPerformed(ActionEvent e) {
		/* ----- recherche de motif ----- */
		if (e.getActionCommand().equals(bCalcul.getText()) && !filePath.equals("")) {
			if (outil1.isSelected() && !motif.getText().equals("")) {
				OpenNLP o = new OpenNLP();
				o.search(filePath, motif.getText());
				JDialog dT = new JDialog();
				dT.getContentPane().add(popupPane.add(new JScrollPane(o.getResultat())));
				dT.setLocation(400, 100);
				dT.setSize(500, 500);
				dT.setResizable(false);
				dT.setVisible(true);
			} else if (outil2.isSelected() && !motif.getText().equals("")) {
				CoreNLP c = new CoreNLP();
				c.search(filePath, motif.getText());
				c.affiche();
			} else if (outil3.isSelected()) {
				Python p = new Python();
				p.search(filePath, motif.getText());
				result = p.getResult();
				popupPane = new JPanel();
				tabResult = new JTable(new DefaultTableModel(result, new String[] { "Lemma", "Occurence", "Total", "Positions" }));
				JScrollPane sp = new JScrollPane(tabResult);
				sp.setBounds(0, 0, WIDTH, 100);
				popupPane.add(sp);
				JDialog dT = new JDialog();
				dT.getContentPane().add(popupPane);
				dT.setLocation(400, 100);
				dT.setSize(500, 500);
				dT.setResizable(false);
				dT.setVisible(true);
			}
		} else if (e.getActionCommand().equals(bReset.getText())) {
			result = null;
			motif.setText("");
		} else if (e.getActionCommand().equals(addFile.getText())) {
			filePath = open();
			lb1.setText(new File(filePath).getName());
			/* ----- documents compare ----- */
		} else if (e.getActionCommand().equals(bDoc1.getText())) {
			docPath1 = open();
			lbdoc1.setText(new File(docPath1).getName());
		} else if (e.getActionCommand().equals(bDoc2.getText())) {
			docPath2 = open();
			lbdoc2.setText(new File(docPath2).getName());
		} else if (e.getActionCommand().equals(bCompare.getText()) && !docPath1.equals("") && !docPath2.equals("")) {
			String doc1 = "", doc2 = "";
			try {
				doc1 = PatternSearch.pdfToTxt(docPath1);
				doc2 = PatternSearch.pdfToTxt(docPath2);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			textField_1.setText("" + CompareDoc.calculLigne(doc1));
			textField_3.setText("" + CompareDoc.calculLigne(doc2));
			textField.setText("" + CompareDoc.calculMots(doc1));
			textField_4.setText("" + CompareDoc.calculMots(doc2));
			textField_2.setText("" + CompareDoc.calculMotsDist(doc1));
			textField_5.setText("" + CompareDoc.calculMotsDist(doc2));
			JPanel pppC = new JPanel();
			JTextArea tC = new JTextArea();
			tC.setWrapStyleWord(true);
			tC.setLineWrap(true);
			PatternSearch.ecrireTxt(doc1, "src/stageENSAO/doc1");
			PatternSearch.ecrireTxt(doc2, "src/stageENSAO/doc2");
			tC.setText(CompareDoc.compare("src/stageENSAO/doc1", "src/stageENSAO/doc2"));
			(new File("src/stageENSAO/doc1")).delete();
			(new File("src/stageENSAO/doc2")).delete();
			pppC.add(tC);
			JDialog dT = new JDialog();
			dT.getContentPane().add(pppC);
			dT.setLocation(400, 100);
			dT.setSize(300, 300);
			dT.setResizable(false);
			dT.setVisible(true);

		} else {
			if (outil3.isSelected()) {
				motif.setEnabled(false);
			} else {
				motif.setEnabled(true);
				if (e.getActionCommand().equals(bCalcul.getText())) {
					JDialog emptyJD = new JDialog();
					emptyJD.getContentPane().add(new JLabel("/!\\ Remplissez tous les champs!", 0));
					emptyJD.setSize(300, 100);
					emptyJD.setLocationRelativeTo(null);
					emptyJD.setVisible(true);
				}
			}
		}
	}

	public String open() {
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("pdf", "pdf", "java");
		chooser.setFileFilter(filter);
		chooser.setApproveButtonText("OK");
		String path = "";
		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			File selectedFile = chooser.getSelectedFile();
			path = selectedFile.getAbsolutePath();
		}
		return path;
	}
}