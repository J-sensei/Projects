public class Card implements Comparable<Card>{
    private int face; // 1-> Club, 2-> Diamond, 3-> Heart, 4-> Spades
    private int point;// 1 - 13, 1 -> A, (11 -> J, 12 -> Q, 13 -> K)

    public Card(int face, int point){
        this.face = face;
        this.point = point;
    }

    @Override
    public int compareTo(Card card){
        if(this.face > card.face) 
            return 1; 
        else if(this.face < card.face)
            return -1;
        else{ //same suit
            if(this.point > card.point)
                return 1;
            else if(this.point == card.point)
                return 0;
            else
                return -1;
        }
    }

    @Override
    public String toString(){
        String faceChar = "";
        String pointChar = "";
        // assign face character
        switch(face){
            case 1:
                faceChar = "c";
                break;
            case 2:
                faceChar = "d";
                break;
            case 3:
                faceChar = "h";
                break;
            case 4:
                faceChar = "s";
                break;
        }
        
        // assign point character
        if(point == 1)// Ace
            pointChar = "A";
        else if(point >= 10){// X, J, Q, K
            switch(point){
                case 10:
                    pointChar = "X";
                    break;
                case 11:
                    pointChar = "J";
                    break;
                case 12:
                    pointChar = "Q";
                    break;
                case 13:
                    pointChar = "K";   
                    break;             
            }
        }else // 2 - 9
            pointChar = Integer.toString(point);

        return faceChar + pointChar;
    }

    // getter
    public int getFace(){
        return face;
    }
    public int getOriginalPoint(){
        return point;
    }
    public int getPoint(){
        if(point <= 10)
            return point; // A - 10
        else
            return 10; // J, Q, K
    }
}
