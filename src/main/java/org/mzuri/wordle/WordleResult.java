package org.mzuri.wordle;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class WordleResult {

    private String guessedWord;
    private int noOfGuesses;

}
