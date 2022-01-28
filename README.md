
# Wordle Bot

Wordle Bot which solves wordle problems


## Execution

Run ```gradle run --args="{wordToGuess}"```
or
Run ```gradle run --args="{wordToGuess} {seedGuess}"```

where wordToGuess is the solution to the wordle and seedGuess is the first guess. Seed guess defaults to `roate`

### Examples
```gradle run --args="spike"``` \
```gradle run --args="block"```
```gradle run --args="block aurie"```