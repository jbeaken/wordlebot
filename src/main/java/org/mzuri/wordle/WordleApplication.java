package org.mzuri.wordle;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class WordleApplication {


    public static void main(String[] args) {

        if(args.length !=1 || args[0].length() != 5) {
            log.error("Usage : WordleApplication {wordToGuess} of exactly 5 letters length");
            System.exit(1);
        }

        String wordToGuess = args[0];

        log.info("Hello from Wordle Application, booting up bot");

        WordleBot wordleBot = new WordleBot( wordToGuess );

        log.info("Bot ready for wordle action");

        //Do it
        wordleBot.guess();
    }
}
