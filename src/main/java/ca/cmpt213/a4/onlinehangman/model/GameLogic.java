//package ca.cmpt213.a4.onlinehangman.model;
//
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
//import java.util.Scanner;
//
//public class GameLogic {
//
//    public static String generateRandomWord(){
//        try {
//            File myObj = new File("./src/commonWords.txt");
//            Scanner myReader = new Scanner(myObj);
//            List<String> data = new ArrayList<>();
//            while (myReader.hasNextLine()) {
//                 data.add(myReader.nextLine());
//            }
//            Random random = new Random();
//            int randomNum = random.nextInt(data.size());
//            String originalWord = data.get(randomNum);
//            myReader.close();
//            return originalWord;
//        } catch (FileNotFoundException e) {
//            System.out.println("An error occurred.");
//            e.printStackTrace();
//        }
//        return "";
//    }
//
//    public static boolean validateInput(String input, List<String> listOfGuesses, String originalWord) {
//        if (input.length() == 0) {
//            return false;
//        } else if (input.length() == 1) {
//            String lowerCaseStringInput = input.toLowerCase();
//            // check if input is a valid char
//            char word = lowerCaseStringInput.charAt(0);
//
//            if (word >= 'a' && word <= 'z') {
//
//                // else: check if input is already present in triedChars
//                if (listOfGuesses.contains(lowerCaseStringInput)) {
//                    // TODO call check original word
//                    // if so, char is already present. break and go back to game
//                    return false;
//                } else {
//
//                    // else: input is not a repeat, add string to triedChars
//                    listOfGuesses.add(lowerCaseStringInput);
//                    return true;
//                }
//            }
//        } else { // DOn"T need this situation
//            String lowerCaseStringInput = input.toLowerCase();
//            // check if multi-char userInput is actual originalWord
//            if(originalWord.contains(lowerCaseStringInput)) {
//                listOfGuesses.add(lowerCaseStringInput);
//                return true;
//            }
//            // false
//            else {
//                return false;
//            }
//        }
//        return false;
//    }
//
//    public static String createHiddenWord(String originalWord, List<String> listOfGuesses) {
//        StringBuilder hiddenWord = new StringBuilder();
//
//        for (String character: originalWord.split("")) {
//            boolean isFound = false;
//
//            for (String guess: listOfGuesses) {
//                if (character.equals(guess)) {
//                    hiddenWord.append(character).append(" ");
//                    isFound = true;
//                    break;
//                }
//            }
//
//            if(!isFound) {
//                // else, if the char in originalWord does not match a char in tries,
//                // replace it with underline
//                hiddenWord.append("_ ");
//            }
//        }
//        return hiddenWord.toString();
//    }
//
//    public static int countIncorrectGuess(String originalWord, String userInput) {
//
//        int badTries = 0;
//
//        // if string (of size 1) in not in originalWord, add to badTries
//        if(!originalWord.contains(userInput)){
//            badTries++;
//        }
//
//        return badTries;
//    }
//
//}
