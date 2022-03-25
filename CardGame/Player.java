import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;

public class Player implements Comparable<Player>{
    private String name;
    private int winPoints; // get point if win
    private int totalPointsEarn;

    //Cards
    private Queue<Card> playerCards;

    public Player(String name){
        this.name = name;
        winPoints = 0;
        totalPointsEarn = 0;
        playerCards = new LinkedList<>();// initialize playerCards
    }

    public void deal(Card card){ // deal a card to player
        playerCards.offer(card);
    }

    public void clearCards(){
        playerCards.clear();
    }

    public void removeCards(){ //remove 5 cards
        for(int i = 0; i < 5; ++i){
            playerCards.remove();
        }
    }

    //setter
    public void setWinPoints(int points){
        winPoints = points;
    }

    public void setTotalPointsEarn(int points){
        totalPointsEarn = points;
    }

    //getter
    public int getCurrentPoint(){ // calculate and return point based on the current 5 cards
        ArrayList<Integer> points = new ArrayList<>(); //store all player points
        TreeSet<Card> cards = getCurrentCard();//get current sorted 5 cards
        int count = 0;//count point index
        Card previousCard = cards.pollFirst();
        points.add(previousCard.getPoint());
        for(Card currentCard:cards){
            if(previousCard.getFace() == currentCard.getFace()){
                points.set(count, points.get(count) + currentCard.getPoint());
            }else{
                points.add(currentCard.getPoint());
                ++count;
            }
            previousCard = currentCard;
        }
        return Collections.max(points);
    }

    public TreeSet<Card> getCurrentCard(){ //return 5 current sorted card 
        Set<Card> cards = new HashSet<>();
        //Pop 5 cards from queue
        int count = 0;
        for(Card c:playerCards){
            cards.add(c);
            ++count;
            if(count > 4)
                break;
        }
        return new TreeSet<>(cards);
   }

   public Queue<Card> getPlayeCards(){
       return playerCards;
   }

   public String getName(){
       return name;
   }

   public int getWinPoints(){
        return winPoints;
    }

    public int getTotalPointEarn() {
        return totalPointsEarn;
    }

    @Override
    public int compareTo(Player p) {
        return this.winPoints - p.getWinPoints();
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder(name + ": ");
        int count = 0;
        for(Card card: playerCards){
            sb.append(card);
            ++count;
            if(count >= 5){
                sb.append(", ");
                count = 0;
            }else
                sb.append(" ");
        }
        return sb.toString();
    }

}
