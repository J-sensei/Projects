import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.TreeSet;

public class Game {

    private ArrayList<Player> players;
    private ArrayList<Card> cards;
    private Queue<Card> deck;

    public Game(String p1, String p2, String p3){
        players = new ArrayList<>(); // initialize 3 players
        //test
        players.add(new Player(p1));
        players.add(new Player(p2));
        players.add(new Player(p3));
        initCards(); 
        shuffleDeck();
        dealAllPlayers();
    }

    public void reShuffle(){
        //clear all players cards, make sure they have no card
        for(int i = 0; i < players.size(); ++i)
            players.get(i).clearCards();
        //shuffle deck and deal cards
        shuffleDeck();
        dealAllPlayers();
        printAvailableCards();
    }

    public Player play(){ // test
        //printAvailableCards();
        ArrayList<Integer> points = new ArrayList<>(); // store each player points
        for(Player p:players){
            TreeSet<Card> card = p.getCurrentCard();
            System.out.println(p.getName() + ": " + card + " Point = " + p.getCurrentPoint());
            points.add(p.getCurrentPoint());
        }
        Player winner = players.get(points.indexOf(Collections.max(points)));
        winner.setWinPoints(winner.getWinPoints() + Collections.max(points));
        System.out.println("Winner: " + winner.getName() + "\n");
        
        return winner;
    }

    public void removePlayerCards(){
        for(int i = 0; i < players.size(); ++i){
            players.get(i).removeCards();
        }
    }

    public boolean twoLose(){ //check is more than 1 players have 0 point
        int count = 0;
        for(int i = 0; i < players.size(); ++i){
            if(players.get(i).getWinPoints() == 0)
                ++count;
        }
        if(count >= 2){
            for(int i = 0; i < players.size() ; ++i){
                if(players.get(i).getWinPoints() == 0)
                    players.remove(i);
            }
            return true;
        }else
            return false;
    }

    public void resetPlayerPoint(){
        for(int i = 0; i < players.size(); ++i){
            players.get(i).setTotalPointsEarn(players.get(i).getWinPoints());
            players.get(i).setWinPoints(0);
        }
    }

    public Player removeLosePlayer(){
        ArrayList<Integer> points = new ArrayList<>(); // store each player total points

        System.out.println("Score: ");
        for(Player p:players){
            System.out.println(p.getName() + " = " + p.getWinPoints());
            points.add(p.getWinPoints());
        }
        int loseIndex = points.indexOf(Collections.min(points)); //get the index of which player is lose
        System.out.println(players.get(loseIndex).getName() + " is lose!");

        // drop the lose player out
        return players.remove(loseIndex);
    }

    private void initCards(){
        cards = new ArrayList<>();
        for(int i = 1; i <= 4; ++i){ // 4 faces
            for(int j = 1; j <= 13; ++j) //13 points for each face
                cards.add(new Card(i, j));
        }
    }

    private void shuffleDeck(){ // shuffle deck
        Collections.shuffle(cards);
        deck = new LinkedList<>(cards);
    }

    private void dealAllPlayers(){ // deal cards to all players
        int count = 0;
        while(!deck.isEmpty()){
            if(count >= players.size())
                count = 0;
            else{
                players.get(count).deal(deck.remove());
                ++count;
            }
        }
    }

    // print console
    public void printAvailableCards(){
        System.out.println("Available Cards:");
        for(Player p:players)
            System.out.println(p);
    }

    // getter
    public ArrayList<Player> getPlayers(){
        return players;
    }

}
