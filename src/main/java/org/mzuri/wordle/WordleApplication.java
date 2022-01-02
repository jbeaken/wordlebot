package org.mzuri.wordle;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class WordleApplication {


    public static void main(String[] args) {

        if(args.length > 2 || args[0].length() !=5 || (args.length == 2 && args[1].length() !=5)) {
            log.error("Usage : WordleApplication {wordToGuess} {seedGuess} of exactly 5 letters length");
            System.exit(1);
        }

        String wordToGuess = args[0];
        String seedGuess = (args.length == 2 ? args[1] : "roate");

        log.info("Hello from Wordle Application, booting up bot");

        WordleBot wordleBot = new WordleBot( wordToGuess, seedGuess );

        log.info("Bot ready for wordle action");

        //Do it
        final WordleResult wordleResult = wordleBot.guess();

        log.info("result : {}", wordleResult);
    }
}
