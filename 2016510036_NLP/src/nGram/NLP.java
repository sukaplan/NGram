package nGram;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import java.awt.EventQueue;

import javax.swing.JFrame;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.awt.TextArea;

import java.awt.Color;

public class NLP {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NLP window = new NLP();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public NLP() throws IOException {
		// combine files into one string
		String corpus = readFile("Novel-Samples\\UNUTULMUÞ DÝYARLAR.txt").toLowerCase()
				+ readFile("Novel-Samples\\BÝLÝM ÝÞ BAÞINDA.txt").toLowerCase()
				+ readFile("Novel-Samples\\DEÐÝÞÝM.txt").toLowerCase()
				+ readFile("Novel-Samples\\DENEMELER.txt").toLowerCase()
				+ readFile("Novel-Samples\\BOZKIRDA.txt").toLowerCase();

		System.out.println(corpus.length());
		// replace all characters that are not in the alphabet or a number
		corpus = corpus.replaceAll("[^a-zA-Zzçðöþüýîâ1-9 ]", "");
		corpus = corpus.replaceAll("  ", " ");
		corpus = corpus.replaceAll("  ", " ");
		
		
		initialize(corpus);
	
	}
	private void initialize(String corpus) {
		frame = new JFrame();
		frame.setBackground(new Color(176, 196, 222));
		frame.getContentPane().setBackground(new Color(205, 92, 92));
		frame.setBounds(100, 100, 425, 566);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		TextArea textArea = new TextArea();
		textArea.setBackground(new Color(255, 245, 238));
		textArea.setBounds(0, 109, 429, 433);
		textArea.setRows(100);
		frame.getContentPane().add(textArea);

		TextArea textArea_1 = new TextArea();
		textArea_1.setBackground(new Color(255, 245, 238));
		textArea_1.setBounds(0, 56, 411, 71);
		frame.getContentPane().add(textArea_1);
		
		JButton diButton = new JButton("Digram");
		diButton.setBounds(152, 29, 85, 21);
		diButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				long startTime_unigram = System.currentTimeMillis();
				
				textArea.setText(ngrams(2, corpus));
				
				long endTime_unigram = System.currentTimeMillis();
				long duration_unigram = (endTime_unigram - startTime_unigram);
				
				textArea_1.setText("Total Running Time of the Bigram: " + duration_unigram + "ms");
			}
		});
		frame.getContentPane().add(diButton);

		JButton triButton = new JButton("Trigram");
		triButton.setBounds(293, 29, 85, 21);
		triButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				long startTime_unigram = System.currentTimeMillis();
								
				textArea.setText(ngrams(3, corpus));
				
				long endTime_unigram = System.currentTimeMillis();
				long duration_unigram = (endTime_unigram - startTime_unigram);
				
				textArea_1.setText("Total Running Time of the Trigram: " + duration_unigram + "ms");
				
			}
		});
		frame.getContentPane().add(triButton);

		JButton uniButton = new JButton("Unigram");
		uniButton.setBounds(22, 29, 85, 21);
		uniButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				long startTime_unigram = System.currentTimeMillis();
				
				textArea.setText(ngrams(1, corpus));
				
				long endTime_unigram = System.currentTimeMillis();
				long duration_unigram = (endTime_unigram - startTime_unigram);
				
				textArea_1.setText("Total Running Time of the Unigram: " + duration_unigram);
			}
		});
		frame.getContentPane().add(uniButton);
		

	}

	public static String concat(String[] words, int start, int end) {
		StringBuilder sb = new StringBuilder();
		for (int i = start; i < end; i++)
			sb.append((i > start ? " " : "") + words[i]);
		return sb.toString();
	}

	public String ngrams(int n, String words) {
		
		String result = "Total number of words = " + words.length() + "\n";
		String[] words_str = words.split(" ");
		List<Count> counts_ngram = new ArrayList<Count>();

		for (int i = 0; i < words_str.length - n + 1; i++) {
			boolean fl = true;
			String ngram_current = concat(words_str, i, i + n);

			for (int j = 0; j < counts_ngram.size(); j++) {
				if (counts_ngram.get(j).getWord().equals(ngram_current)) {
					counts_ngram.get(j).increaseCount();
					fl = false;
				}
				if (!fl) {
					break;
				}
			}
			if (fl) {
				counts_ngram.add(new Count(ngram_current, 1));
			}
		}

		for (int i = 0; i < 100; i++) {
			int max = 0;
			int max_id = 0;
			for (int j = 0; j < counts_ngram.size(); j++) {
				if (counts_ngram.get(j).getCount() > max) {
					max = counts_ngram.get(j).getCount();
					max_id = j;
				}

			}
			result = result + (i + 1) + " Word:" + counts_ngram.get(max_id).getWord() + "\t\tCount:"
					+ counts_ngram.get(max_id).getCount() + "\n";
			counts_ngram.remove(max_id);
		}
		
		return result;

	}

	public static String readFile(String file) throws IOException {

		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "ISO-8859-9"));) {

			String line;
			String corpus = "";

			while ((line = br.readLine()) != null) {
				corpus = corpus + line + "\n";
			}
			return corpus;
		}
	}
}
