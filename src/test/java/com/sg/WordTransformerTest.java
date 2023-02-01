package com.sg;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;

class WordTransformerTest {
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    public void cleanup() {
        System.setOut(System.out);
    }

    @Test
    void shouldTransformStartWordToEndWord() {
        WordTransformer.main(new String[]{"data/validdata.txt"});
        assertThat(outputStreamCaptor.toString()).contains("cat, dot, cot, dog");
    }

    @Test
    void shouldReturnErrorOnAttemptToTransformStartWordToEndWordWhenFileDoesNotExist() {
        WordTransformer.main(new String[]{"data/notexistent.txt"});
        assertThat(outputStreamCaptor.toString()).contains("ERROR: File not found error.");
    }

    @Test
    void shouldReturnAppropriateErrorWhenUnableToTransform() {
        WordTransformer.main(new String[]{"data/cannot_transform.txt"});
        assertThat(outputStreamCaptor.toString()).contains("Cannot transform cat to ion");
    }
}
