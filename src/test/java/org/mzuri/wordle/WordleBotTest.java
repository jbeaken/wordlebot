package org.mzuri.wordle;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WordleBotTest {

    WordleBot wordleBot;

    @BeforeEach
    void beforeEach() {
        wordleBot = new WordleBot("spike");
    }

    @Test
    void testGuessWord() {
        wordleBot.guess(WordleBot.guessSeed);
    }
}
