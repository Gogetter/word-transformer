package com.sg.reader;

import com.sg.model.FileInput;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FileReaderTest {
    private final FileReader fileReader = new FileReader();
    @Test
    void shouldReturnNoDataWhenInvalidFilePassed() throws Exception {
        assertThat(fileReader.readFile("data/empty.txt"))
            .isNotPresent();
    }

    @Test
    void shouldThrowExceptionWhenFileDoesNotExist() {
        assertThatThrownBy(() -> fileReader.readFile("data/notexistent.txt"))
            .isInstanceOf(FileNotFoundException.class)
            .hasMessage("ERROR: File not found error.");
    }

    @Test
    void shouldReturnExpectedFileInputWhenFileIsValid() throws Exception {
        Optional<FileInput> input = fileReader.readFile("data/validdata.txt");
        assertThat(input).isPresent().get().hasNoNullFieldsOrProperties();

        assertThat(input.get().wordList()).hasSize(10);
    }
}
