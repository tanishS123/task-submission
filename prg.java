import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

class prg {

    public static boolean isCompound(String term, Set<String> dictionary) {
        if (dictionary.contains(term)) {
            dictionary.remove(term);
        }

        boolean[] isCombinable = new boolean[term.length() + 1];
        isCombinable[0] = true;

        for (int i = 1; i <= term.length(); i++) {
            for (int j = 0; j < i; j++) {
                if (isCombinable[j] && dictionary.contains(term.substring(j, i))) {
                    isCombinable[i] = true;
                    break;
                }
            }
        }

        return isCombinable[term.length()];
    }

    public static void findLongestAndSecondLongestCompoundWords(String filePath) throws IOException {
        long startTimestamp = System.nanoTime();

        List<String> allWords = Files.readAllLines(Paths.get(filePath));
        Set<String> wordDictionary = new HashSet<>(allWords);
        String longestCompound = "";
        String secondLongestCompound = "";

        for (String word : allWords) {
            if (isCompound(word, new HashSet<>(wordDictionary))) {
                if (word.length() > longestCompound.length()) {
                    secondLongestCompound = longestCompound;
                    longestCompound = word;
                } else if (word.length() > secondLongestCompound.length()) {
                    secondLongestCompound = word;
                }
            }
        }

        long endTimestamp = System.nanoTime();
        long durationInNanoSeconds = endTimestamp - startTimestamp;
        long durationInMillis = TimeUnit.NANOSECONDS.toMillis(durationInNanoSeconds);

        System.out.println("Longest Compound Word: " + longestCompound);
        System.out.println("Second Longest Compound Word: " + secondLongestCompound);
        System.out.println("Time taken to process the file: " + durationInMillis + " milliseconds");
    }

    public static void main(String[] args) {
        try {
            System.out.println("Processing Input_01.txt...");
            findLongestAndSecondLongestCompoundWords("Input_01.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
