package ca.cmpt213.a4.onlinehangman.controllers;

import ca.cmpt213.a4.onlinehangman.model.Game;
import ca.cmpt213.a4.onlinehangman.model.Message;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class HangmanController {
    private Message promptMessage; //a resusable String object to display a prompt message at the screen
    private AtomicLong nextId = new AtomicLong();
    private List<Game> gameList = new ArrayList<>();
    private List<String> imagePath  = new ArrayList<>();


    //works like a constructor, but wait until dependency injection is done, so it's more like a setup
    @PostConstruct
    public void hangmanControllerInit() {
        promptMessage = new Message("Initializing...");
        imagePath.add("/images/0.png");
        imagePath.add("/images/1.png");
        imagePath.add("/images/2.png");
        imagePath.add("/images/3.png");
        imagePath.add("/images/4.png");
        imagePath.add("/images/5.png");
        imagePath.add("/images/6.png");
        imagePath.add("/images/7.png");
    }

    @GetMapping("/helloworld")
    public String showHelloworldPage(Model model) {


        promptMessage.setMessage("THis is cmpt 213");
        model.addAttribute("promptMessage", promptMessage);

        // take the user to helloworld.html
        return "helloworld"; // Return to the html
    }

    @GetMapping("/welcome")
    public String showWelcome(Model model) {
        model.addAttribute("game", new Game());
        return "welcome";
    }

    @PostMapping("/game")
    public String processGame(@ModelAttribute("game") Game game, Model model) {
        if (game.getWord().isEmpty()) {
            String word = game.generateRandomWord();
            System.out.print("Here1  " + word);
            game.setId(nextId.incrementAndGet());
            game.setWord(word);
            game.setNumOfGuesses(0);
            game.setNumOfIncorrectGuesses(0);
            game.setStatus("Active");
            game.setImage(imagePath.get(0));
        } else {
            System.out.print("Here2  " + game.getWord());
        }

        gameList.add(game);
        String attemptLeft = "" + (8 - game.getNumOfIncorrectGuesses());

        String hiddenWord = game.createHiddenWord();

        model.addAttribute("game", game);
        model.addAttribute("attemptLeft", attemptLeft);
        model.addAttribute("hiddenWord", hiddenWord);

        return "game";
    }

    @PostMapping("game/{gameId}")
    public String gameInPlayPosted(@PathVariable int gameId, @RequestParam String userInput,
                                   Model model, @ModelAttribute("game") Game game) {

        game = gameList.get(gameId - 1);
        System.out.print("Here3  " + game.getWord());

        // validate user input
        if (!game.validateInput(userInput)) {
            // generate the word as underlines
            String hiddenWord = game.createHiddenWord();
            String attemptLeft = "" + (8 - game.getNumOfIncorrectGuesses());
            model.addAttribute("game", game);
            model.addAttribute("attemptLeft", attemptLeft);
            model.addAttribute("hiddenWord", hiddenWord);

            return "game";
        }

        String hiddenWord = game.createHiddenWord();

        if(game.checkIfWin(hiddenWord)) {
            game.setStatus("Won");
            model.addAttribute("title", "Game Over - You Win");
            model.addAttribute("game", game);

            return "gameover";
        }

        if (game.getNumOfIncorrectGuesses() > 7) {
            game.setStatus("Lost");
            model.addAttribute("title", "Game Over - You Lost");
            model.addAttribute("game", game);

            return "gameover";
        }

        String attemptLeft = "" + (8 - game.getNumOfIncorrectGuesses());
        game.setImage(imagePath.get(game.getNumOfIncorrectGuesses()));
        model.addAttribute("game", game);
        model.addAttribute("attemptLeft", attemptLeft);
        model.addAttribute("hiddenWord", hiddenWord);

        return "game";
    }

    @GetMapping("game/{gameId}")
    public String getGameID(@PathVariable("gameId") long gameId, Model model) {
        for (Game game : gameList) {
            if (game.getId() == gameId) {
                if (game.getStatus() == "Active") {
                    String attemptLeft = "" + (8 - game.getNumOfIncorrectGuesses());
                    String hiddenWord = game.createHiddenWord();
                    model.addAttribute("game", game);
                    model.addAttribute("attemptLeft", attemptLeft);
                    model.addAttribute("hiddenWord", hiddenWord);
                    return "game";
                }
                else if (game.getStatus() == "Won") {
                    model.addAttribute("title", "Game Over - You Win");
                    model.addAttribute("game", game);

                    return "gameover";
                }
                else {
                    model.addAttribute("title", "Game Over - You Lost");
                    model.addAttribute("game", game);

                    return "gameover";
                }
            }
        }
        // Throw error ID not found
        return "game";
    }
}