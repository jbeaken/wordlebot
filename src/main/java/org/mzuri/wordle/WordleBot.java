package org.mzuri.wordle;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
class WordleBot {

    private final String wordToGuess;

    static String guessSeed = "roate";

    private List<String> words;

    int noOfGuessesAllowed = 10;


    public WordleBot(String wordToGuess) {
        final URL resource = this.getClass().getClassLoader().getResource("words.txt");

        this.wordToGuess = wordToGuess;
        try {
            words = Files.readAllLines(Paths.get(resource.toURI()), StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    String guess() {
        String guessedWord = guessSeed;

g        for(int i = 1; i <= noOfGuessesAllowed; i++) {
            log.info("Guessing word {}, size of words is {}", guessedWord,  words.size());

            if(isCorrectAnswer(guessedWord)) {
                log.info("Got it in {}! It's {}", i, guessedWord);
                return guessedWord;
            }

            guess(guessedWord);

            guessedWord = getNextWordToGuess();
        }

        log.info("Why o why? Haven't guessed it");
        log.info("List of words remaining : {}", words);
    }

    private String getNextWordToGuess() {
        //Just get first word in list (for now!)
        return words.get(0);
    }

    private boolean isCorrectAnswer(String guessedWord) {
        return guessedWord.equals(this.wordToGuess);
    }

    void guess(String guessedWord) {

        List<Result> results = new ArrayList<>();

        //Find if characters in new word
        for(int i = 0; i < guessedWord.length(); i++) {

            final char c = guessedWord.charAt(i);

            if( this.wordToGuess.indexOf(c) != -1) {
                //is it exact?
                if(this.wordToGuess.charAt( i ) == guessedWord.charAt( i )) {
                    results.add(new IsPresentResult(c, i, true));
                } else {
                    results.add(new IsPresentResult(c, i, false));
                }
            } else {
                results.add(new IsAbsentResult(c));
            }
        }

        words = words.stream()
                .filter(wordInDictionary -> isViableWord(wordInDictionary, results) && !wordInDictionary.equals(guessedWord))
                .collect(Collectors.toList());
    }

    private boolean isViableWord(String wordInDictionary, List<Result> results) {

        for(Result result : results) {

            switch(result) {
                case IsPresentResult r:
                    if (r.isExact){
                       if(wordInDictionary.charAt(r.pos) != r.c)  return false;
                    } else {
                        if (wordInDictionary.indexOf(r.c) == -1)  return false;
                    }
                    break;
                case IsAbsentResult r:
                    if (wordInDictionary.indexOf(r.c) != -1)  return false;
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + result);
            }
        }

        return true;
    }

    @Data
    @NoArgsConstructor
    abstract class Result {
        char c;

        public Result(char c) {
            this.c = c;
        }
    }

    @Data
    @EqualsAndHashCode(callSuper=false)
    class IsPresentResult extends Result {
        int pos;
        boolean isExact;

        public IsPresentResult(char c, int pos, boolean isExact) {
            super(c);
            this.pos = pos;
            this.isExact = isExact;
        }
    }

    @Data
    @EqualsAndHashCode(callSuper=false)
    class IsAbsentResult extends Result {

        public IsAbsentResult(char c) {
            super(c);
        }
    }
}
