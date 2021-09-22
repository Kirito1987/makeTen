
/**
 * File: Card.java
 * Programmer:
 * Date:
 * 
 * Purpose:
 */
//Imports
import javax.swing.*;
import javax.swing.JLabel;

//Enum Definition
enum Rank {
    ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8), NINE(9), ZERO(0);
private int value;

Rank(int value){
    this.value = value;
}
public int getValue(){
    return value;
}// end of getValue method

};// end of Rank enum definitions

//Card Class Definition
public class Card extends JLabel{
    //Class Field
    private Rank rank = Rank.ZERO;
    private String faceUpInfo = "";
    private boolean facedown = false;

    //Class Constructors
    public Card(String cardLocation, Rank cardRank){
        this.faceUpInfo = cardLocation;
        this.setIcon(new ImageIcon(cardLocation));
        this.rank = cardRank;
    }// end of Card Class Constructor

    //Class Methods
    public int getRank(){
        /**
         * Gets the integer value of the Rank of a card.
         */
        return rank.getValue();
    }// end oget Rank method

    public void flipUp(){
        /**
         * Flips Up A card
         */
        this.setIcon(null);
        this.setIcon(new ImageIcon(faceUpInfo));//Changes the face of the card to face up.
        facedown = false;
    }

    public void flipDown(){
        /**
         * Flips Down A Card
         */
        this.setIcon(new ImageIcon("cards\\facedown.jpg"));//Changes the face of the card to face down.
        facedown = true;
    }

    public boolean faceStatus(){
        return facedown;
    }
    
    public void swapInfo(Card someCard){
        /**
         * This method swaps the information of the card that call the method
         * with the one passed as a parameter.
         */
        this.rank = someCard.rank;
        this.faceUpInfo = someCard.faceUpInfo;
        this.setIcon(null);
        this.setIcon(new ImageIcon(faceUpInfo));
    }// swapInfo
    
}// end of Card class
