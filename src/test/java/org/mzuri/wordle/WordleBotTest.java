package org.mzuri.wordle;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WordleBotTest {

    WordleBot wordleBot;

    @BeforeEach
    void beforeEach() {
        wordleBot = new WordleBot("spike", "roate");
    }

    @Test
    void testGuessWord() {
        final WordleResult wordleResult = wordleBot.guess();

        Assertions.assertThat(wordleResult.getGuessedWord()).isEqualTo("spike");
        Assertions.assertThat(wordleResult.getNoOfGuesses()).isEqualTo(4);
    }
}
