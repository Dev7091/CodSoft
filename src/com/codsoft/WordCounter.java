package com.codsoft;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class WordCounter {

    private static Set<String> commonWordsSet;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Word Counter - Count words from text or file.");

        loadCommonWords(); // Load common words from a file or manually define them in the method.

        while (true) {
            System.out.println("\nEnter '1' to input text, '2' to input a file, or '0' to exit:");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                System.out.println("Enter your text:");
                String text = scanner.nextLine();
                countWordsAndDisplayResult(text);
            } else if (choice == 2) {
                System.out.println("Enter the file path:");
                String filePath = scanner.nextLine();
                filePath = filePath.replace("/", File.separator).replace("\\", File.separator);
                try {
                    String text;
                    if (filePath.endsWith(".docx")) {
                        text = readDocxFileContent(filePath);
                    } else if (filePath.endsWith(".xlsx") || filePath.endsWith(".xls")) {
                        text = readExcelFileContent(filePath);
                    } else if (filePath.endsWith(".doc")) {
                        text = readDocFileContent(filePath);
                    } else if (filePath.endsWith(".txt")) {
                        text = readFileContent(filePath);
                    } else {
                        System.out.println("Unsupported file format. Only .docx, .doc, .xlsx, .xls, and .txt files are supported.");
                        continue;
                    }
                    countWordsAndDisplayResult(text);
                } catch (IOException e) {
                    System.out.println("Error reading the file: " + e.getMessage());
                }
            } else if (choice == 0) {
                break;
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
        System.out.println("Word Counter - Goodbye!");
    }
 // Method to check if a word is a common word
    public static boolean isCommonWord(String word) {
        return commonWordsSet.contains(word);
    }
    static void loadCommonWords() {
        commonWordsSet = new HashSet<>();
        // Here, you can load common words from a file or manually define them in the method.
        // For example:
        commonWordsSet.add("the");
        commonWordsSet.add("and");
        commonWordsSet.add("in");
        // ... and so on.
    }

    static String readFileContent(String filePath) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        }
        return content.toString();
    }

    static void countWordsAndDisplayResult(String text) {
        String[] words = text.split("[\\p{Punct}\\s]+");
        List<String> filteredWordsList = Arrays.stream(words)
                .filter(word -> !commonWordsSet.contains(word.toLowerCase()))
                .collect(Collectors.toList());

        int totalCount = filteredWordsList.size();
        int uniqueCount = (int) filteredWordsList.stream().distinct().count();
        
        Map<String, Integer> wordFrequencyMap = new HashMap<>();
        for (String word : filteredWordsList) {
            wordFrequencyMap.put(word, wordFrequencyMap.getOrDefault(word, 0) + 1);
        }

        System.out.println("\nTotal Words: " + totalCount);
        System.out.println("Unique Words: " + uniqueCount);
        System.out.println("Filtered Words (excluding common words): " + totalCount);
        
        System.out.println("\nWord Frequency:");
        for (Map.Entry<String, Integer> entry : wordFrequencyMap.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }


    // Provided method for reading Word (.doc) file
    private static String readDocFileContent(String filePath) throws IOException {
        FileInputStream fis = new FileInputStream(filePath);
        XWPFWordExtractor extractor = new XWPFWordExtractor(new XWPFDocument(fis));
        String text = extractor.getText();
        fis.close();
        return text;
    }

    // Provided method for reading Excel (.xls and .xlsx) file
    @SuppressWarnings("resource")
    private static String readExcelFileContent(String filePath) throws IOException {
        try (FileInputStream fis = new FileInputStream(filePath)) {
            Workbook workbook;
            if (filePath.endsWith(".xlsx")) {
                workbook = new XSSFWorkbook(fis);
            } else if (filePath.endsWith(".xls")) {
                workbook = new HSSFWorkbook(fis);
            } else {
                throw new IllegalArgumentException("Unsupported file format. Only .xlsx and .xls files are supported.");
            }

            StringBuilder content = new StringBuilder();
            for (Sheet sheet : workbook) {
                for (Row row : sheet) {
                    for (Cell cell : row) {
                        content.append(cell.getStringCellValue()).append(" ");
                    }
                    content.append("\n");
                }
            }
            return content.toString();
        }
    }

    // Provided method for reading Word (.docx) file
    private static String readDocxFileContent(String filePath) throws IOException {
        FileInputStream fis = new FileInputStream(filePath);
        XWPFWordExtractor extractor = new XWPFWordExtractor(new XWPFDocument(fis));
        String text = extractor.getText();
        fis.close();
        return text;
    }
}
