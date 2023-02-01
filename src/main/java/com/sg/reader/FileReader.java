package com.sg.reader;

import com.sg.model.FileInput;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Optional;

public class FileReader {
    public Optional<FileInput> readFile(final String fileName) throws Exception {
        final var inputStream = getClass().getClassLoader().getResource(fileName);

        if (inputStream == null) {
            throw new FileNotFoundException("ERROR: File not found error.");
        }

        final var inputs =  Files.readAllLines(Paths.get(inputStream.toURI()));

        if (inputs.isEmpty() || inputs.stream().anyMatch(String::isEmpty) || inputs.size() > 2) {
            return Optional.empty();
        }

        final var words = inputs.get(0).split(",");
        return Optional.of(new FileInput(words[0],
                                         words[1],
                                         Arrays.stream(inputs.get(1).split(",")).map(String::trim).toList()));
    }
}
