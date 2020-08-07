package ca.cmpt213.a4.onlinehangman.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Game {
    private long id;
    private String word = "";
    private List<String> guesses = new ArrayList<>();
    private int numOfGuesses;
    private int numOfIncorrectGuesses; // Max 7
    private String status; // 0: Active, 1:Won, -1:Lost
    private String image = "/images/0.png";



    public Game() {

    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    public void setId(long id) {
        this.id = id;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public void setGuesses(List<String> guesses) {
        this.guesses = guesses;
    }

    public void setNumOfGuesses(int numOfGuesses) {
        this.numOfGuesses = numOfGuesses;
    }

    public void setNumOfIncorrectGuesses(int numOfIncorrectGuesses) {
        this.numOfIncorrectGuesses = numOfIncorrectGuesses;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public String getWord() {
        return word;
    }

    public List<String> getGuesses() {
        return guesses;
    }

    public int getNumOfGuesses() {
        return numOfGuesses;
    }

    public int getNumOfIncorrectGuesses() {
        return numOfIncorrectGuesses;
    }

    public String getStatus() {
        return status;
    }

    public String generateRandomWord(){
        try {
            File myObj = new File("./src/commonWords.txt");
            Scanner myReader = new Scanner(myObj);
            List<String> data = new ArrayList<>();
            while (myReader.hasNextLine()) {
                data.add(myReader.nextLine());
            }
            Random random = new Random();
            int randomNum = random.nextInt(data.size());
            String originalWord = data.get(randomNum);
            myReader.close();
            return originalWord;
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return "";
    }

    public boolean validateInput(String input) {
        if (input.length() == 0) {
            return false;
        } else if (input.length() == 1) {
            String lowerCaseStringInput = input.toLowerCase();
            // check if input is a valid char
            char letter = lowerCaseStringInput.charAt(0);

            if (letter >= 'a' && letter <= 'z') {
                if(!this.word.contains(lowerCaseStringInput)){
                    numOfIncorrectGuesses++;
                }
                numOfGuesses++;
                guesses.add(lowerCaseStringInput);
                return true;
                // else: check if input is already present in triedChars
//                if (guesses.contains(lowerCaseStringInput)) {
//                    // TODO call check original word
//                    // if so, char is already present. break and go back to game
//                    return false;
//                } else {
//
//                    // else: input is not a repeat, add string to triedChars
//                    guesses.add(lowerCaseStringInput);
//                    return true;
//                }

            }
        }
        return false;
    }

    public String createHiddenWord() {
        StringBuilder hiddenWord = new StringBuilder();

        for (String character: word.split("")) {
            boolean isFound = false;

            for (String guess: guesses) {
                if (character.equals(guess)) {
                    hiddenWord.append(character).append(" ");
                    isFound = true;
                    break;
                }
            }

            if(!isFound) {
                // else, if the char in originalWord does not match a char in tries,
                // replace it with underline
                hiddenWord.append("_ ");
            }
        }
        return hiddenWord.toString();
    }


    public boolean checkIfWin(String hiddenWord) {

        String[] splitHiddenWord = hiddenWord.split(" ");
        String wholeHiddenWord = String.join("", splitHiddenWord);
        String wholeHiddenWord2 = hiddenWord.replace(" ", "");

        return wholeHiddenWord2.equals(word);
    }
}
