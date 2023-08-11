package com.codsoft;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class WordCounterGUI extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextArea outputTextArea;

    public WordCounterGUI() {
        setTitle("Word Counter GUI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());

        outputTextArea = new JTextArea();
        outputTextArea.setEditable(false);
        JScrollPane outputScrollPane = new JScrollPane(outputTextArea);
        outputScrollPane.setBorder(BorderFactory.createTitledBorder("Output"));

        JPanel buttonPanel = new JPanel();
        JButton countButton = new JButton("Count Words");
        countButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showInputDialog();
            }
        });
        buttonPanel.add(countButton);

        mainPanel.add(outputScrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.NORTH);

        getContentPane().add(mainPanel);
    }

    private void showInputDialog() {
        String[] options = {"Input Text", "Input File Path"}; // Changed option label
        int choice = JOptionPane.showOptionDialog(this,
                "Choose input type:", "Input Selection",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, options, options[0]);

        if (choice == 0) {
            String text = JOptionPane.showInputDialog(this, "Enter your text:");
            if (text != null) {
                countWordsAndDisplayResult(text);
            }
        } else if (choice == 1) {
            String filePath = JOptionPane.showInputDialog(this, "Enter the file path:"); // Prompt for file path
            if (filePath != null && !filePath.isEmpty()) {
                try {
                    String text = readFileContent(filePath);
                    countWordsAndDisplayResult(text);
                } catch (IOException e) {
                    outputTextArea.setText("Error reading the file: " + e.getMessage());
                }
            }
        }
    }

    private void countWordsAndDisplayResult(String text) {
        // Your existing countWordsAndDisplayResult logic here
        String[] words = text.split("[\\p{Punct}\\s]+");
        Map<String, Integer> wordFrequencyMap = new HashMap<>();

        for (String word : words) {
            wordFrequencyMap.put(word, wordFrequencyMap.getOrDefault(word, 0) + 1);
        }

        int totalCount = words.length;
        int uniqueCount = wordFrequencyMap.size();

        // Display the results in the outputTextArea
        outputTextArea.setText("Total Words: " + totalCount + "\n"
                + "Unique Words: " + uniqueCount + "\n"
                + "Filtered Words (excluding common words): " + totalCount + "\n"
                + "\nWord Frequency:\n");

        for (Map.Entry<String, Integer> entry : wordFrequencyMap.entrySet()) {
            outputTextArea.append(entry.getKey() + ": " + entry.getValue() + "\n");
        }
    }


    private String readFileContent(String filePath) throws IOException {
    	StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        }
        return content.toString();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new WordCounterGUI().setVisible(true);
            }
        });
    }
}
