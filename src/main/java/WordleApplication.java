import lombok.extern.slf4j.Slf4j;

@Slf4j
class WordleApplication {


    static String wordToGuess = "bloke";

    public static void main(String[] args) {

        log.info("Hello from Wordle Application, booting up bot");

        WordleBot wordleBot = new WordleBot( wordToGuess );

        log.info("Bot ready for wordle action");

        //Do it
        wordleBot.guess();
    }
}
