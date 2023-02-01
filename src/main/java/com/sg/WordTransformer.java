package com.sg;

import com.sg.model.FileInput;
import com.sg.reader.FileReader;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.stream.IntStream;

/**
 *
 *
 */
public class WordTransformer {
    public static void main(String[] args) {

        if (args.length > 0) {
            System.out.println("WordTransformer received " + Arrays.toString(args) + " as filename to process.");

            try {
                Optional<FileInput> fileInput = new FileReader().readFile(args[0]);

                fileInput.ifPresent(input -> new WordTransformer()
                    .transform(input.startWord(), input.endWord(), input.wordList()));

            } catch (Exception exception) {
                if (exception instanceof FileNotFoundException) {
                    System.out.println(exception.getMessage());
                }
            }
        }
    }

    private void transform(final String startWord, final String endWord, final List<String> wordList) {
        Set<String> dictionary = new HashSet<>(wordList);
        Queue<String> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        queue.offer(startWord);
        visited.add(startWord);

        Set<String> wordsInSteps = new HashSet<>();

        while (!queue.isEmpty()) {
            for (int queueIndex = 0; queueIndex < queue.size(); queueIndex++) {
                String currentWord = queue.poll();
                if (currentWord.equals(endWord)) {
                    wordsInSteps.add(endWord);
                    System.out.println(String.join(", ", wordsInSteps));
                    return;
                }

                IntStream.range(0, currentWord.length()).forEach(wordIndex -> {
                    char[] chars = currentWord.toCharArray();
                    for (char charIndex = 'a'; charIndex <= 'z'; charIndex++) {
                        chars[wordIndex] = charIndex;
                        String nextWord = new String(chars);
                        if (dictionary.contains(nextWord) && !visited.contains(nextWord)) {
                            queue.offer(nextWord);
                            visited.add(nextWord);
                            wordsInSteps.add(currentWord);
                        }
                    }
                });
            }
            // Uncomment below to see contents of wordsInSteps for each step
//            System.out.println(wordsInSteps);
        }
        System.out.println("Cannot transform " + startWord + " to " + endWord);
    }

}
