/**
 * File: Deck.java
 * Programmer:
 * Date:
 * 
 * Purpose:
 */
//Imports
import java.util.*;
import javax.swing.BorderFactory;
import javax.swing.border.Border;

public class Deck {
    //Class Fields
    private  Queue<Card> deck = new LinkedList<Card>();

    //Class Constructors
    public Deck(){
        makeDeck();
    }// end of deck Class

    //Class Methods
    public void makeDeck(){
        /**
         * This method creates a shuffle deck for the game.
         */

        LinkedList<Card> somedeck = new LinkedList<>();
        //Clubs
        somedeck.add(new Card("cards/c1.jpg", Rank.ONE));
        somedeck.add(new Card("cards/c2.jpg", Rank.TWO));
        somedeck.add(new Card("cards/c3.jpg", Rank.THREE));
        somedeck.add(new Card("cards/c4.jpg", Rank.FOUR));
        somedeck.add(new Card("cards/c5.jpg", Rank.FIVE));
        somedeck.add(new Card("cards/c6.jpg", Rank.SIX));
        somedeck.add(new Card("cards/c7.jpg", Rank.SEVEN));
        somedeck.add(new Card("cards/c8.jpg", Rank.EIGHT));
        somedeck.add(new Card("cards/c9.jpg", Rank.NINE));

        //Spades
        somedeck.add(new Card("cards/s1.jpg", Rank.ONE));
        somedeck.add(new Card("cards/s2.jpg", Rank.TWO));
        somedeck.add(new Card("cards/s3.jpg", Rank.THREE));
        somedeck.add(new Card("cards/s4.jpg", Rank.FOUR));
        somedeck.add(new Card("cards/s5.jpg", Rank.FIVE));
        somedeck.add(new Card("cards/s6.jpg", Rank.SIX));
        somedeck.add(new Card("cards/s7.jpg", Rank.SEVEN));
        somedeck.add(new Card("cards/s8.jpg", Rank.EIGHT));
        somedeck.add(new Card("cards/s9.jpg", Rank.NINE));

        //Hearts
        somedeck.add(new Card("cards/h1.jpg", Rank.ONE));
        somedeck.add(new Card("cards/h2.jpg", Rank.TWO));
        somedeck.add(new Card("cards/h3.jpg", Rank.THREE));
        somedeck.add(new Card("cards/h4.jpg", Rank.FOUR));
        somedeck.add(new Card("cards/h5.jpg", Rank.FIVE));
        somedeck.add(new Card("cards/h6.jpg", Rank.SIX));
        somedeck.add(new Card("cards/h7.jpg", Rank.SEVEN));
        somedeck.add(new Card("cards/h8.jpg", Rank.EIGHT));
        somedeck.add(new Card("cards/h9.jpg", Rank.NINE));

        //Diamounds
        somedeck.add(new Card("cards/d1.jpg", Rank.ONE));
        somedeck.add(new Card("cards/d2.jpg", Rank.TWO));
        somedeck.add(new Card("cards/d3.jpg", Rank.THREE));
        somedeck.add(new Card("cards/d4.jpg", Rank.FOUR));
        somedeck.add(new Card("cards/d5.jpg", Rank.FIVE));
        somedeck.add(new Card("cards/d6.jpg", Rank.SIX));
        somedeck.add(new Card("cards/d7.jpg", Rank.SEVEN));
        somedeck.add(new Card("cards/d8.jpg", Rank.EIGHT));
        somedeck.add(new Card("cards/d9.jpg", Rank.NINE));

        //Shuffle Cards
        Collections.shuffle(somedeck); //shuffle the deck of cards

        //Put them in to the Queue
        for(int i = 0;  i < somedeck.size(); i++){
            deck.add(somedeck.get(i)); //Adds the shuffle card to a deck queue
        }// end of for

    }// end of make Deck method
    public Card dealCard(){
        /**
         * Deals card to player's hand, if the deck is empty
         * The player will be dealt an empty card.
         */
        if(deck.isEmpty()){
            Card emptyDeck = new Card("cards/empty.jpg", Rank.ZERO);
            Border borderPile = BorderFactory.createLoweredBevelBorder();
            emptyDeck.setBorder(borderPile);
            return emptyDeck;
        }// end of if

        return deck.poll();
    }// end of dealCard method

    public void reshuffle(){
        LinkedList<Card> somedeck = new LinkedList<>();

        while(!deck.isEmpty()){
            somedeck.add(deck.poll());
        }// end of while loop
        Collections.shuffle(somedeck);//Shuffles the remaining cards

        for(int i = 0; i < somedeck.size(); i++){
            this.deck.add(somedeck.get(i));
        }// end of for loop
    }// end of reShuffle method

    public void returnCards(Card someCard1, Card someCard2, Card someCard3, Card someCard4){
        /**
         * Method that returns cards back to the main deck.
         */
        Card newCard1 = new Card("cards/empty.jpg", Rank.ZERO);
        Card newCard2 = new Card("cards/empty.jpg", Rank.ZERO);
        Card newCard3 = new Card("cards/empty.jpg", Rank.ZERO);
        Card newCard4 = new Card("cards/empty.jpg", Rank.ZERO);
        newCard1.swapInfo(someCard1);
        newCard2.swapInfo(someCard2);
        newCard3.swapInfo(someCard3);
        newCard4.swapInfo(someCard4);
        this.deck.add(newCard1);
        this.deck.add(newCard2);
        this.deck.add(newCard3);
        this.deck.add(newCard4);
    }// end of returnCards method

    public int deckSize(){
        /**
         * Return the deck size
         */
        return deck.size();
    }// end of deckSize method
    
}// end of Deck Class
