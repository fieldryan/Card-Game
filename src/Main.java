import java.util.*;

public class Main {
    record Card(String id, int value) {
        @Override
        public String toString() {
            return id;
        }
    }

    private static final String[] SUITS = {"Spades", "Hearts", "Diamonds", "Clubs"};
    private static final String[] FACES = {"Ace", "Jack", "Queen", "King"};


    private static final Set<Card> fullDeck = new HashSet<>() {{
        for (int j = 0; j < 4; j++) {
            String suit = SUITS[j];
            for (int i = 2; i <= 10; i++) {
                add(new Card(i + " of " + suit, i));
            }

            for (var face : FACES) {
                add(new Card(face + " of " + suit, 1));
            }
        }
    }};
    private static List<Card> gameDeck = new ArrayList<>();
    public static List<Card> gameBoard = new ArrayList<>();


    /**
     * Initiliazes to call for setup
     */
    private static void setUp(){
        gameDeck = new ArrayList<>(fullDeck);
        Collections.shuffle(gameDeck);
        for(int i = 0; i < 9; i++){
            Card card = gameDeck.remove(i);
            gameBoard.add(card);
        }

    }


    private static void visualiseBoard() {
        if (gameBoard.size() != 9) {
            throw new RuntimeException("Unexpected gameboard size " + gameBoard.size());
        }
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                res.append(gameBoard.get(i*3 + j));
                res.append(" | ");
            }
            res.append("\n");
        }
        System.out.println(res);

    }


    /*
    return 1 if faceUp is larger than drawn
    return 0 if same value, draw again
    return -1 if drawn card is larger
     */
    private static int largerCall(Card faceUp, Card drawn){
        if(faceUp.value() == drawn.value()){return 0;}
        if(faceUp.value() > drawn.value()){return 1;}
        return -1;
    }


    /*
    return 1 if faceup is lower than drawn
    return 0 if same value
    return -1 if faceup is higher than drawn
     */
    private static int lowerCall(Card faceUp, Card drawn){
        if(faceUp.value() == drawn.value()){return 0;}
        if(faceUp.value() < drawn.value()){return 1;}
        return -1;
    }



    public static void main(String[] args) {
        setUp();
        visualiseBoard();
    }


}


