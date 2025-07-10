import java.util.*;

public class Game {
    private final String[] SUITS = {"Spades", "Hearts", "Diamonds", "Clubs"};
    private List<Card> gameDeck = new Stack<>();
    private List<Card> gameBoard = new ArrayList<>();
    private List<Card> drawnCards = new ArrayList<>();
    private Card upsideDown = new Card("Upsidedown", -1);
    private int score = 0;



    private  final Set<Card> fullDeck = Collections.unmodifiableSet(new HashSet<>() {{
        for (int j = 0; j < 4; j++) {
            String suit = SUITS[j];
            for (int i = 1; i <= 13; i++) {
                if(i == 1){add(new Card("Ace of " + suit, 1));}
                else if(i == 11){add(new Card("Jack of " + suit, i));}
                else if(i == 12){add(new Card("Queen of " + suit, i));}
                else if(i == 13){add(new Card("King of " + suit, i));}
                else{ add(new Card(i + " of " + suit, i));}

            }
        }
    }});


    /**
     * Initiliazes to call for setup
     */
    private  void setUp() {
        gameDeck = new ArrayList<>(fullDeck);
        Collections.shuffle(gameDeck);
        for (int i = 0; i < 9; i++) {
            Card card = gameDeck.remove(i);
            gameBoard.add(card);
        }

    }


    private  void visualiseBoard() {
//        for(int i = 0; i<20; i++){System.out.println("\n\n");} // generate whitespace for better visual look
        if (gameBoard.size() != 9) {
            throw new RuntimeException("Unexpected gameboard size " + gameBoard.size());
        }
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                res.append(gameBoard.get(i * 3 + j));
                res.append(" | ");
            }
            res.append("\n");
        }
        System.out.println(res);
    }

    public List<Card> getDrawnCards(){return drawnCards;}

    private  Card draw() {
        Card drawnCard = gameDeck.remove(0);
        drawnCards.add(drawnCard);
        return drawnCard;
    }

    /*
    return 1 if faceUp is larger than drawn
    return 0 if same value, draw again
    return -1 if drawn card is larger
     */
    private int largerCall(Card faceUp, Card drawn) {
        if (faceUp.value() == drawn.value()) {
            return 0;
        }
        if (faceUp.value() < drawn.value()) {
            return 1;
        }
        return -1;
    }


    /*
    return 1 if faceup is lower than drawn
    return 0 if same value
    return -1 if faceup is higher than drawn
     */
    private  int lowerCall(Card faceUp, Card drawn) {
        if (faceUp.value() == drawn.value()) {
            return 0;
        }
        if (faceUp.value() > drawn.value()) {
            return 1;
        }
        return -1;
    }

    /**
     * used boolean as can be used for scoring
     */
    private boolean callLogic(int chosenIndex, boolean high) {
        Card drawn = draw();
        Card chosenCard = gameBoard.get(chosenIndex);
        int call = 0;
        if (high) {call = largerCall(chosenCard, drawn);}
        else {call = lowerCall(chosenCard, drawn);}
        System.out.println("Drawn card was: " + drawn.toString());
        if(call == 0){
            gameBoard.set(chosenIndex, drawn);
            callLogic(chosenIndex, high);
        }
        else if(call == 1){
            gameBoard.set(chosenIndex, drawn);
            return true;
        }
        gameBoard.set(chosenIndex, upsideDown);
        return false;
    }


    public String askInput(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Choose position (0-8): ");
        String position = sc.nextLine();
        System.out.println("Call higher or lower (h/l): ");
        String call = sc.nextLine();
        return position + call;
    }

    public void input() {
        String input = askInput();
        int chosenCard = Character.getNumericValue(input.charAt(0));
        if(gameBoard.get(chosenCard).equals(upsideDown) || chosenCard < 0 || chosenCard > 8){
            System.out.println("Please choose a nunber between 0 and 8, the card must not be upsidown.\n\n");
            input();
            return;
        }
        if (input.charAt(1) == 'h') {
            callLogic(chosenCard, true);
        } else if(input.charAt(1) == 'l'){
            callLogic(chosenCard, false);
        } else{
            System.out.println("Please choose higher or lower, in the form of h or l (h/l)\n\n");
            input();
            return;
        }
        update();
    }


    public boolean isPlaying(){
        return !gameDeck.isEmpty();
    }

    public  void gameStart() {
        setUp();
        System.out.println("Cards are valued from 1 - 13, Ace being 1, King being 13.");
        System.out.println("Choose a an index beween 0 and 8, 0 being top left, 8 being bottom right.");
        System.out.println("Then call higher or lower, if wrong, then that card is flipped, if the same, it is redrawn.");
        System.out.println("Game is lost if all cards are flipped over");
        System.out.println("Game is won if gone through the entire deck with at least 1 card still flipped over");


        visualiseBoard();
        input();
    }

    private void update(){
        visualiseBoard();
        if(gameBoard.stream().allMatch(c -> Objects.equals(c, upsideDown)) || !isPlaying()){endGame();}
        else{input();}
    }

    public void endGame(){
        // print score
        // ask to play again
        if(gameBoard.stream().allMatch(c -> Objects.equals(c, upsideDown))){System.out.println("Game lost");}
        else{System.out.println("Game won");}
        System.out.println("Your Score was: " + score + " still working on score");
    }

    public void restart(){
        gameDeck.clear();
        gameBoard.clear();
        gameStart();
    }


}



