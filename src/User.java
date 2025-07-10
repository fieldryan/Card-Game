import java.util.Scanner;

public class User {

    private final int id;
    private int highScore = 0;


    public User(int id){
        this.id = id;
    }

    public void changehighScore(int newHighScore){
        highScore = newHighScore;
    }

    public int gethighScore(){return highScore;}

    public int getId(){return id;}

    /**
     * Asks user for input based to predict
     * a string contianing position, and higher or lower
     *  in format (numbercall)
     *  e.g. 8l which means (8 lower)
     */


    public void playGame(){
        Game game = new Game();
        game.gameStart();
    }



}
