import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
class WordleBot {

    String wordToGuess;

    String guessSeed = "roate";

    static String path = "/home/git/mzuri/wordle/src/main/resources/words.txt";

    List<String> words;

    int noOfGuessesAllowed = 10;

    public WordleBot(String wordToGuess) {
        this.wordToGuess = wordToGuess;
        try {
            words = Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void guess() {
        String guessedWord = guessSeed;

        IntStream.range(0, noOfGuessesAllowed).forEach(noOfGuesses -> {

        });

        for(int i = 1; i <= noOfGuessesAllowed; i++) {
            log.info("Guessing word {}, size of words is {}", guessedWord,  words.size());

            isCorrectAnswer(guessedWord, i);

            guess(guessedWord);

            //Just get first word in new list
            guessedWord = words.get(0);
        }

        log.info("Why o why? Haven't guessed it");
        log.info("List of words remaining : {}", words);
    }

    private void isCorrectAnswer(String guessedWord, int noOfGuesses) {
        if(guessedWord.equals(this.wordToGuess)) {
            log.info("Got it in {}! It's {}", noOfGuesses, guessedWord);
            System.exit(0);
        }
    }

    void guess(String guessedWord) {

        List<Result> results = new ArrayList<>();

        //Find if characters in new word
        for(int i = 0; i < guessedWord.length(); i++) {

            final char c = guessedWord.charAt(i);

            if( this.wordToGuess.indexOf(c) != -1) {
                //is it exact?
                if(this.wordToGuess.charAt( i ) == guessedWord.charAt( i)) {
                    results.add(new Result(c, i, true, true));
                } else {
                    results.add(new Result(c, i, false, true));
                }
            } else {
                results.add(new Result(c, -1, false, false));
            }
        }

        words = words.stream().filter(w -> isViableGuess(w, results) && !w.equals(guessedWord)).collect(Collectors.toList());
    }

    private boolean isViableGuess(String word, List<Result> results) {

        for(Result result : results) {

            if(result.isPresent()) {
                if (result.isExact) {
                    if (word.charAt(result.pos) != result.c) {
                        return false;
                    }
                }
            } else {
                //Character not present, remove from list if it's there
                if (word.indexOf(result.c) != -1) {
                    return false;
                }
            }
        }

        return true;
    }

    @Data
    @AllArgsConstructor
    class Result {
        char c;
        int pos;
        boolean isExact;
        boolean present;
    }
}
