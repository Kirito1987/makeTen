/**
 * File: MainGUI.java
 * Programmer:
 * Date:
 * 
 * Purpose:
 */
//Imports
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;

import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class MainGUI extends JFrame{
    //Class Fields
    private boolean gameStatus = false;
    //GUI Btns
    private JButton newGameBtn = new JButton("New Game");
    private JButton quitGameBtn = new JButton("Quit Game");
    private JButton skipTurnBtn = new JButton("Skip Turn");

    //GUI Panels
    private GameBoard gameboard = new GameBoard();
    private JPanel gameControlsPanel = new JPanel();

    //GUI Card Objects
    private Deck deck = new Deck();
    private int skipButnCounter = 0;

    //Class Constructor
    public MainGUI(){
        //Title For Panels & Buttons
        Border grayline = BorderFactory.createLineBorder(Color.GRAY); 
        TitledBorder tittle = BorderFactory.createTitledBorder(grayline,"Game Buttons");
        tittle.setTitleJustification(TitledBorder.CENTER);

        //Game Constrols Definition
        gameControlsPanel.setLayout(new GridLayout(1,4,20,50));
        gameControlsPanel.setBorder(tittle);

        //New Border and Tittle for Buttons
        Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        tittle = BorderFactory.createTitledBorder(loweredetched, "Game Setting A");
        tittle.setTitleJustification(TitledBorder.RIGHT);
        newGameBtn.setBorder(tittle);
        gameControlsPanel.add(newGameBtn);

        //Title for Quit Game
        tittle = BorderFactory.createTitledBorder(loweredetched, "Game Setting B");
        tittle.setTitleJustification(TitledBorder.LEFT);
        quitGameBtn.setBorder(tittle);
        gameControlsPanel.add(quitGameBtn);

        gameControlsPanel.add(new JLabel());
        tittle = BorderFactory.createTitledBorder(loweredetched, "Game Controls");
        skipTurnBtn.setBorder(tittle);
        gameControlsPanel.add(skipTurnBtn);

        //this.add("Center",gameboard);
        this.add("South",gameControlsPanel);
        //GUI Settings
        this.setSize(900,650);
        this.setBackground(new Color(194,178,128));
        this.setTitle("Steal The Old's Man Bundle Demo");
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        //Set Event Listeners
        newGameBtn.addActionListener(new NewGameBtnL());
        quitGameBtn.addActionListener(new QuitGameBtnL());
        skipTurnBtn.addActionListener(new SkipBtnL());
    }// end of MainGUI

    //Class Methods
    public void showMultipleMsgs(int caseNumber){
        String message = "";
        String titleOfMsg = "";
        int messageCode = 0;
        switch(caseNumber){
            case 1:
            message = "New Game Has Started";
            titleOfMsg = "New Game";
            messageCode = 1;
            break;

            case 2:
            message = "You Have Quit The Game";
            titleOfMsg = "Quit Game";
            messageCode = 1;
            break;

            case 3:
            message = "Both Players Skip Turns.\nReshuffling Cards...Please Wait";
            titleOfMsg ="Game Event";
            messageCode = 1;
            break;

            case 4:
            message = "You Win!! Want To Play Again?";
            titleOfMsg = "End Of Game Announcement";
            messageCode = 1;
            break;

            case 5:
            message = "You Lose. Its Ok.\nStart A New Game And Try Again.";
            titleOfMsg = "End Of Game Announcement";
            messageCode = 1;
            break;

            case 6:
            message = "Please Quit The Game First Before Starting A New One";
            titleOfMsg = "Invalid Input";
            messageCode = 0;
            break;

            case 7:
            message = "Please Start A Game Session First";
            titleOfMsg = "Invalid Input";
            messageCode = 0;
            break;
        }// end of switch statements

        JOptionPane.showMessageDialog(this, message, titleOfMsg, messageCode);
    }//showMultipleMsg
    public void endOfGame(){
        /**
         * This method announces the Game Winner
         */
        int playerScore = gameboard.getPlayerScore(1);
        int cpuScore = gameboard.getPlayerScore(2);
        if(playerScore > cpuScore){
            showMultipleMsgs(4); //Shows You Win Msg
        }
        else{
            showMultipleMsgs(5);//Shows You Lose Msg
        }
    }// end of endOfGame method
    //Inner Classes
    class SkipBtnL implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if(gameStatus){
                //If Game Session is On
                boolean flagForCPUSkip = gameboard.getSkipTurn();
                if(flagForCPUSkip && gameboard.getDeckCount() >= 4){//if both player skip
                    showMultipleMsgs(3);
                    gameboard.reDeal();
                }// end of inner if
                else if(flagForCPUSkip && gameboard.getDeckCount() < 4){
                    skipButnCounter += 1;
                }// end of inner if else
                else{
                    gameboard.cpuTurn();
                }// end of else

                if(skipButnCounter >= 1){
                    //Announces the winner of the game
                    endOfGame();
                }// end of if
            }// end of outer if
            else{
                showMultipleMsgs(7);
            }// end of else
        }
    }//end of SkipBtnL class

    class NewGameBtnL implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if(gameStatus){
                showMultipleMsgs(6);
            }
            else{
                skipButnCounter = 0;
                gameboard = new GameBoard();
                add("Center",gameboard);
                repaint();
                revalidate();
                showMultipleMsgs(1);
                gameStatus = true;
            }//end of else
        }
    }// end of NewGameBtnL

    class QuitGameBtnL implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            remove(gameboard);
            repaint();
            revalidate();
            showMultipleMsgs(2);
            gameStatus = false;
        }
    }
    public static void main(String[] args) {
        MainGUI game = new MainGUI();
    }// end of main method

}// end of MainGui class

//MiddleGamePanel Definition
class GameBoard extends JPanel{
    //Class Field
    private Border blackline = BorderFactory.createLineBorder(Color.BLACK); 
    private TitledBorder tittle = BorderFactory.createTitledBorder(blackline,"GameBoard");
   
    private Deck deck = new Deck(); //Card Deck of 36 cards
    private Card deckTop = new Card("cards/facedown.jpg", Rank.ZERO); // shows top of the deck when there is cards

    //Playe's Cards
    private Card card1;
    private Card card2;
    private Card card3;
    private Card card4;
    private Card bundle = new Card("cards/empty.jpg", Rank.ZERO);

    private GridBagConstraints gbc = new GridBagConstraints(); //Constriants for Game Layout
    private Point prevPoint;                                   // previos point for card dragging motion

    //Playe's Card Bundle Counters 
    private int playerCounter = 0;
    private int cpuCounter = 0;

    private int cardPick = -1; // integer that represent the card that is being dragged

    //Community Cards
    private Card middle1;
    private Card middle2;
    private Card middle3;
    private Card middle4;

    //CPU's Cards
    private Card cpuCard1;
    private Card cpuCard2;
    private Card cpuCard3;
    private Card cpuCard4;
    private Card cpuCardPile = new Card("cards/empty.jpg", Rank.ZERO);
    private boolean skipBtnNotification = false;

    //CPU's Field View
    private LinkedList<Card> cpusHand = new LinkedList<>();
    //Class Constructor
    public GameBoard(){
        //GameBoard Layout
        this.setLayout(new GridBagLayout());

        //Player Cards
        card1 = deck.dealCard(); card1.flipDown();
        card2 = deck.dealCard(); card2.flipDown();
        card3 = deck.dealCard(); card3.flipDown();
        card4 = deck.dealCard(); card4.flipDown();

        gbc.insets = new Insets(50,10,50,10);
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(card1,gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(card2,gbc);
        gbc.gridx = 2;
        gbc.gridy = 2;
        add(card3,gbc);
        gbc.gridx = 3;
        gbc.gridy = 2;
        add(card4,gbc);

        //Players Empty Card Bundle
        Border borderPile = BorderFactory.createLoweredBevelBorder();

        bundle.setBorder(borderPile);
        gbc.insets = new Insets(0,0,0,0);
        gbc.gridx = 5;
        gbc.gridy = 2;
        add(bundle,gbc);

        //CPU's Hand
        cpuCard1 = deck.dealCard(); //cpuCard1.flipDown();
        cpuCard2 = deck.dealCard(); //cpuCard2.flipDown();
        cpuCard3 = deck.dealCard(); //cpuCard3.flipDown();
        cpuCard4 = deck.dealCard(); //cpuCard4.flipDown();
        gbc.insets = new Insets(50,10,50,10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(cpuCard1, gbc); //CPU's card one location
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(cpuCard2, gbc);
        gbc.gridx = 2;
        gbc.gridy = 0;
        add(cpuCard3, gbc);
        gbc.gridx = 3;
        gbc.gridy = 0;
        add(cpuCard4, gbc);

        //CPU's Card Pile
        borderPile = BorderFactory.createLoweredBevelBorder();
        cpuCardPile.setBorder(borderPile);
        gbc.insets = new Insets(0,0,0,0);
        gbc.gridx = 5;
        gbc.gridy = 0;
        add(cpuCardPile,gbc);

        //Deck And Community Card Positions
        gbc.insets = new Insets(50,10,50,10);
        gbc.gridx = 0;
        gbc.gridy = 1;
        //deckTop = deck.dealCard(); deckTop.flipDown();
        add(deckTop, gbc);
        middle1 = deck.dealCard();
        middle2 = deck.dealCard(); 
        middle3 = deck.dealCard(); 
        middle4 = deck.dealCard(); 

        //Adding Community Cards
        gbc.gridx = 2;
        gbc.gridy = 1;
        add(middle1, gbc);
        gbc.gridx = 3;
        gbc.gridy = 1;
        add(middle2, gbc);
        gbc.gridx = 4;
        gbc.gridy = 1;
        add(middle3, gbc);
        gbc.gridx = 5;
        gbc.gridy = 1;
        add(middle4, gbc);

        //JPanel Settings
        this.setBorder(tittle);
        this.setBackground(new Color(194,178,128));

        //Mouse Listeners
        card1.addMouseListener(new MouseEnterL());
        card1.addMouseListener(new CardPressedL());
        card1.addMouseListener(new CardReleaseL());
        card1.addMouseMotionListener(new CardDragL());

        card2.addMouseListener(new MouseEnterL());
        card2.addMouseListener(new CardPressedL());
        card2.addMouseListener(new CardReleaseL());
        card2.addMouseMotionListener(new CardDragL());

        card3.addMouseListener(new MouseEnterL());
        card3.addMouseListener(new CardPressedL());
        card3.addMouseListener(new CardReleaseL());
        card3.addMouseMotionListener(new CardDragL());

        card4.addMouseListener(new MouseEnterL());
        card4.addMouseListener(new CardPressedL());
        card4.addMouseListener(new CardReleaseL());
        card4.addMouseMotionListener(new CardDragL());

        //Middle Card Listeners
        deckTop.addMouseListener(new ClickOnDeckL());

        //Make CPU Hand
        makeCPUHand();
    }// end of Gameboard constructor

    //Class Method
    public void cpuTurn(){
        /**
         *  CPU's performs a move
         */
        updateCPUHand();//Updates CPU's Hand View
        boolean makeTen = false;
        
        if(!makeTen){makeTen = cpuMoves(cpuCard1);}

        if(!makeTen){makeTen = cpuMoves(cpuCard2);}

        if(!makeTen){makeTen = cpuMoves(cpuCard3);}

        if(!makeTen){makeTen = cpuMoves(cpuCard4);}

        if(makeTen){
            skipBtnNotification = false;
            JOptionPane.showMessageDialog(null, "Players Turn");
        }
        else{
            skipBtnNotification = true;
            JOptionPane.showMessageDialog(null, "CPU Skip Turn.");
        }
    }// end of cpuTurnmethod
    public int getPlayerScore(int selectedScore){
        /**
         * This method is tasked with getting each player's scores
         */
        if(selectedScore == 1){
            return playerCounter;
        }// end of if
        else{
            return cpuCounter;
        }// end of else
    }// end getPlayerScore

    private void makeCPUHand(){
        /**
         * Gives the CPU a view of his hand
         */
        cpusHand.add(cpuCard1);
        cpusHand.add(cpuCard2);
        cpusHand.add(cpuCard3);
        cpusHand.add(cpuCard4);
    }// end of makeCPUHand

    private void updateCPUHand(){
        /**
         * Updates the Hand View of the CPU's Hand
         */
        cpusHand.set(0, cpuCard1);
        cpusHand.set(1, cpuCard2);
        cpusHand.set(2, cpuCard3);
        cpusHand.set(3, cpuCard4);
    }// end of updateCPUHand

    private boolean cpuMoves(Card someCard){
        /**
         * This method handles the cpu field moves
         */
        if(makeTen(someCard, bundle)){
            //Checks if bundle cards makes ten
            cpuCardPile.swapInfo(someCard);
            cpuCounter += playerCounter + 1; //adds the counter of the cards
            playerCounter = 0;// set player counter to 0
            bundle.swapInfo(new Card("cards/empty.jpg", Rank.ZERO));// player bunlde becomes empty
            cardReplace(someCard);
            JOptionPane.showMessageDialog(null, "CPU Stole From Players Bundle");
            return true;
        }
        else if(makeTen(someCard, middle1)){
            cpuCardPile.swapInfo(someCard);
            cpuCounter += 2;
            cardReplace(middle1);
            cardReplace(someCard);
            return true;
        }
        else if(makeTen(someCard, middle2)){
            cpuCardPile.swapInfo(someCard);
            cpuCounter += 2;
            cardReplace(middle2);
            cardReplace(someCard);
            return true;
        }
        else if(makeTen(someCard, middle3)){
            cpuCardPile.swapInfo(someCard);
            cpuCounter += 2;
            cardReplace(middle3);
            cardReplace(someCard);
            return true;
        }
        else if(makeTen(someCard, middle4)){
            cpuCardPile.swapInfo(someCard);
            cpuCounter += 2;
            cardReplace(middle4);
            cardReplace(someCard);
            return true;
        }
        else{
            return false;
        }
    }//end of cpuMoves method
    public boolean getSkipTurn(){
        return skipBtnNotification;
    }

    public int getDeckCount(){
        return deck.deckSize();// returns the size of the deck
    }

    public void reDeal(){
        /**
         * This method re-deals cards to each player whenever both player's skip turns
         */
        deck.returnCards(cpuCard1, cpuCard2, cpuCard3, cpuCard4);
        deck.returnCards(card1, card2, card3, card4);
        deck.returnCards(middle1, middle2, middle3, middle4);
        deck.reshuffle();

        //Deals CPU cards
        cardReplace(cpuCard1);
        cardReplace(cpuCard2);
        cardReplace(cpuCard3);
        cardReplace(cpuCard4);

        //Re-Deals Cards in the middle
        cardReplace(middle1);
        cardReplace(middle2);
        cardReplace(middle3);
        cardReplace(middle4);
        //Deals Player Cards
        cardReplace(card1); card1.flipDown();
        cardReplace(card2); card2.flipDown();
        cardReplace(card3); card3.flipDown();
        cardReplace(card4); card4.flipDown();
        skipBtnNotification = false; //Turns the notification back to false
        repaint();
    }
    private void dragCase(MouseEvent e, Card someCard){
        /**This method handles the dragging action of each card */
        Point currentPt = e.getPoint();
        Point cardLocation = someCard.getLocation();
        cardLocation.translate((int)(currentPt.getX() - (int) prevPoint.getX()),
                               (int)(currentPt.getY() - (int)prevPoint.getY()));
        someCard.setLocation(cardLocation);
        repaint();
    }// end of dragCase

    private void dropCase(Card someCard, int cardNum){
        /**
         * This method set or drop Player cards to their original location.
         */
        switch(cardNum){
            case 1:
            someCard.setLocation(164, 432);
            break;
            case 2:
            someCard.setLocation(260, 432);
            break;
            case 3:
            someCard.setLocation(356, 432);
            break;
            case 4:
            someCard.setLocation(452, 432);
            break;
        }// end of dropCase Method
    }// end of dropCase Method

    private void middleCardLocationCase(Card someCard){
        /**
         * This method verifies that the card that is being drag is on top of a 
         *  middle card.
         *  The function also handles the make 10 action.
         */
        if(someCard.getX() >= 331 && someCard.getX() <= 373 &&
        someCard.getY() >= 229 && someCard.getY() <= 300){ //Boundaries of middle card 1
            if(makeTen(someCard, middle1)){ // if pair of card make 10

                bundle.swapInfo(someCard); //change the top card of the players bundle
                playerCounter += 2;        //Increment the player bundle counter
                cardReplace(middle1);      //replace that middle card with a new card from deck
                cardReplace(someCard);     //replace player card with a new card from deck
                JOptionPane.showMessageDialog(null, "CPU Turn");
                cpuTurn();//Begins CPU's Turn Upon making 10
            }// end of if
            else{dropCase(someCard, cardPick);}// else return the card back to players hand
        }// end of if

        else if(someCard.getX() >= 446 && someCard.getX() <= 459 &&
        someCard.getY() >= 229 && someCard.getY() <= 300){ //Boundaries of middle card 2
            if(makeTen(someCard, middle2)){
                bundle.swapInfo(someCard);
                playerCounter += 2;
                cardReplace(middle2);
                cardReplace(someCard);
                JOptionPane.showMessageDialog(null, "CPU Turn");
                cpuTurn();//Begins CPU's Turn Upon making 10
            }
            else{dropCase(someCard, cardPick);}
        }// end of else if

        else if(someCard.getX() >= 539 && someCard.getX() <= 562 &&
        someCard.getY() >= 229 && someCard.getY() <= 300){ ////Boundaries of middle card 3
            if(makeTen(someCard, middle3)){
                bundle.swapInfo(someCard);
                playerCounter += 2;
                cardReplace(middle3);
                cardReplace(someCard);
                JOptionPane.showMessageDialog(null, "CPU Turn");
                cpuTurn();//Begins CPU's Turn Upon making 10
            }
            else{dropCase(someCard, cardPick);}
        }// end of else if

        else if(someCard.getX() >= 630 && someCard.getX() <= 667 &&
        someCard.getY() >= 229 && someCard.getY() <= 300){//Boundaries of middle card 4
            if(makeTen(someCard, middle4)){
                bundle.swapInfo(someCard);
                playerCounter += 2;
                cardReplace(middle4);
                cardReplace(someCard);
                JOptionPane.showMessageDialog(null, "CPU Turn");
                cpuTurn();//Begins CPU's Turn Upon making 10
            }
            else{dropCase(someCard, cardPick);}
        }// end of else if
        else if(someCard.getX() >= 628 && someCard.getX() <= 675 &&
        someCard.getY() >= 27 && someCard.getY() <= 97){
            if(makeTen(someCard, cpuCardPile)){
                bundle.swapInfo(someCard);
                playerCounter += cpuCounter + 1; //adds the counter of the cards
                cpuCounter = 0;// set player counter to 0
                cpuCardPile.swapInfo(new Card("cards/empty.jpg", Rank.ZERO));// player bunlde becomes empty
                cardReplace(someCard);
                JOptionPane.showMessageDialog(null, "CPU Turn");
                cpuTurn();//CPU's Turn
            }
            else{dropCase(someCard, cardPick);}
        }
        else{
            //Return the selected card to the original location
            //See dropCardcase method
            dropCase(someCard, cardPick);
        }// end of else
    }// end of MiddleCardLocastionCase
    private boolean makeTen(Card somecard1, Card somecard2){
        //This method verifies if player can make 10 with a pair of cards

        int total = somecard1.getRank() + somecard2.getRank();
        if(total == 10){
            return true;
        }// end of
        else{
            return false;
        }// end of else
    }// end of makeTen method
    private void cardReplace(Card somecard){
        /**
         * This method replaces a card with a new one from the deck
         */
        if(deck.deckSize() == 0){
            deckTop.swapInfo(new Card("cards/empty.jpg", Rank.ZERO));
        }
        Card replace = deck.dealCard();
        somecard.swapInfo(replace);
        repaint();
        revalidate();
    }// end of cardReplace method
    @Override
    public void paint(Graphics g) {
        /**
         * Overridden Paint method that paints values and string on the Gameboard 
         */
        super.paint(g);
        // Paints Counters in the GameBoard
        super.paint(g);
        String deckDetails = "Cards In Deck: "+ deck.deckSize();
        g.setFont(new Font("Times", Font.BOLD, 18));
        g.drawString(deckDetails, 100, 225);

        String cpuPile = "CPU's Card Pile: "+ cpuCounter;
        g.drawString(cpuPile, 640, 150);
        String playerPile = "Player's Card Pile: "+playerCounter;
        g.drawString(playerPile, 640, 425);
        repaint();
    }
    //Inner Classes
    private class CardPressedL extends MouseAdapter{
        @Override
        public void mousePressed(MouseEvent e) {
            prevPoint = e.getPoint();
        }
    }
    private class CardDragL extends MouseAdapter{
        @Override
        public void mouseDragged(MouseEvent e) {
            if(card1.faceStatus() == false && card2.faceStatus() == false &&
               card3.faceStatus() == false && card4.faceStatus() == false){

                   if(e.getSource() == card1 && card1.getRank() != 0){dragCase(e, card1); cardPick = 1;}
                   if(e.getSource() == card2 && card2.getRank() != 0){dragCase(e, card2); cardPick = 2;}
                   if(e.getSource() == card3 && card3.getRank() != 0){dragCase(e, card3); cardPick = 3;}
                   if(e.getSource() == card4 && card4.getRank() != 0){dragCase(e, card4); cardPick = 4;}
            }// end of inner if
            else{
                JOptionPane.showMessageDialog(null, "Please Flip All Of Your Cards Firt", "Invalid Move", 1);
            }
        }// end of MouseDragged method

    }// end of CardDragL
    private class MouseEnterL extends MouseAdapter{
        @Override
        public void mouseEntered(MouseEvent e) {
            if(e.getSource() == card1 && card1.faceStatus() == true){
                card1.flipUp();
            }
            if(e.getSource() == card2 && card2.faceStatus() == true){
                card2.flipUp();
            }
            if(e.getSource() == card3 && card3.faceStatus() == true){
                card3.flipUp();
            }

            if(e.getSource() == card4 && card4.faceStatus() == true){
                card4.flipUp();
            }
            repaint();
        }//MouseEntered

    }// end MouseEnterL class
    private class CardReleaseL extends MouseAdapter{
        @Override
        public void mouseReleased(MouseEvent e) {
            
            switch(cardPick){
                case 1:
                middleCardLocationCase(card1);
                break;
                case 2:
                middleCardLocationCase(card2);
                break;
                case 3:
                middleCardLocationCase(card3);
                break;
                case 4:
                middleCardLocationCase(card4);
                break;
            }
        }
    }

    private class ClickOnDeckL extends MouseAdapter{
        @Override
        public void mouseClicked(MouseEvent e) {
            if(deck.deckSize() > 0){
                cardReplace(card1);
            }
            if(deck.deckSize() == 0){
                cardReplace(deckTop);
            }
        }
    }
}
