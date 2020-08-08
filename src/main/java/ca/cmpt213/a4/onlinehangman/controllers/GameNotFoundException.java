package ca.cmpt213.a4.onlinehangman.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


/**
 * A class GameNotFoundException for handling the exception when the users trying to find a non-existing game
 * with an invalid input id
 *
 * @author Gavin Dang (301368907) ttd6@sfu.ca
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class GameNotFoundException extends RuntimeException{
    GameNotFoundException() {}
}
