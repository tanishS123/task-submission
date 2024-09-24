import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class prg2 {

    public static boolean isCompoundWord(String currentWord, Set<String> dictionary) {
        if (currentWord.isEmpty()) {
            return false;
        }

        boolean[] canBeCompounded = new boolean[currentWord.length() + 1];
        canBeCompounded[0] = true; 
        
        for (int i = 1; i <= currentWord.length(); i++) {
            for (int j = 0; j < i; j++) {
                if (canBeCompounded[j] && dictionary.contains(currentWord.substring(j, i))) {
                    canBeCompounded[i] = true;
                    break;
                }
            }
        }
        
        return canBeCompounded[currentWord.length()];
    }

    public static void findLongestAndSecondLongestCompoundWords(String fileName) throws IOException {
        long startTime = System.nanoTime();

        List<String> wordList = Files.readAllLines(Paths.get(fileName));
        Set<String> wordDictionary = new HashSet<>(wordList);

        wordList.sort(Comparator.comparingInt(String::length));

        String longestCompoundWord = "";
        String secondLongestCompoundWord = "";

        for (String word : wordList) {
            if (isCompoundWord(word, wordDictionary)) {
                if (word.length() > longestCompoundWord.length()) {
                    secondLongestCompoundWord = longestCompoundWord;
                    longestCompoundWord = word;
                } else if (word.length() > secondLongestCompoundWord.length()) {
                    secondLongestCompoundWord = word;
                }
            }
        }

        long endTime = System.nanoTime();
        long elapsedTime = endTime - startTime;
        long processingTimeInMillis = TimeUnit.NANOSECONDS.toMillis(elapsedTime);

        System.out.println("Longest Compound Word: " + longestCompoundWord);
        System.out.println("Second Longest Compound Word: " + secondLongestCompoundWord);
        System.out.println("Time taken to process the file: " + processingTimeInMillis + " milliseconds");
    }

    public static void main(String[] args) {
        try {
            System.out.println("Processing Input_02.txt...");
            findLongestAndSecondLongestCompoundWords("Input_02.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
