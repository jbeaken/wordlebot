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

@Slf4j
class WordleBot {

    String wordToGuess = "spill";
    List<String> words;
    int noOfGuessesAllowed = 60;

    public static void main(String[] args) {
        String path = "/home/git/mzuri/wordle/src/main/resources/words.txt";

        WordleBot wordleBot = new WordleBot();

        try {
            wordleBot.words = Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //6 tries, first guess is my favourite roate
        String guess = "roate";

        for(int i = 1; i <= wordleBot.noOfGuessesAllowed; i++) {
            log.info("Guessing word {}", guess);
            wordleBot.guess(guess, i);
            guess = wordleBot.words.get(0);
            log.info("Words size = {}", wordleBot.words.size());
        }

        log.info("Why o why? Haven't guessed it");
        log.info("List of words remaining : {}", wordleBot.words);

    }

    void guess(String guess, int noOfGuesses) {

        //check
        if(guess.equals(wordToGuess)) {
            log.info("Got it in {}! It's {}", noOfGuesses, guess);
            System.exit(0);
        }

        List<Result> results = new ArrayList<>();
        List<String> newList = null;

        //Find if characters in new word
        for(int i = 0; i < guess.length(); i++) {

            final char c = guess.charAt(i);

            if( wordToGuess.indexOf(c) != -1) {
                //is it exact?
                if(wordToGuess.charAt( i ) == guess.charAt( i)) {
                    results.add(new Result(c, i, true, true));
                } else {
                    results.add(new Result(c, i, false, true));
                }
            } else {
                results.add(new Result(c, -1, false, false));
            }
        }

        newList = words.stream().filter(w -> isViableGuess(w, results)).collect(Collectors.toList());

        words = newList;
    }

    private boolean isViableGuess(String word, List<Result> results) {

        for(Result result : results) {

            if(result.isPresent()) {
                if (result.isExact) {
                    if (word.charAt(result.pos) != result.c) {
                        return false;
                    }
                }

                if (word.indexOf(result.c) == -1) {
                    return false;
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
