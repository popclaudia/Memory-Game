import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Collections;
import java.util.Iterator;

public class Board extends JFrame{
    private List<Card> cards;
    private Card selectedCard;
    private Card c1;
    private Card c2;
    private Timer t;
    int pairs = 18;
    int trn;
    List<Card> cardsList = new ArrayList<Card>();
    List<Integer> cardVals = new ArrayList<Integer>();
    List<Color> cardColor = new ArrayList<Color>();

    public void createCards(){
    	int r,g,b;

        for (int i = 0; i < pairs; i++){
            cardVals.add(i);
            cardVals.add(i);
            Random rand=new Random();
            r=rand.nextInt(256);
            g=rand.nextInt(256);
            b=rand.nextInt(256);
            Color cul=new Color(r,g,b);
            cardColor.add(cul);
            cardColor.add(cul);
            
        }
        long seed = System.nanoTime();
        Collections.shuffle(cardVals, new Random(seed));
        Collections.shuffle(cardColor, new Random(seed));
        Iterator<Integer> a=cardVals.iterator();
        for(Color val:cardColor){
        	Card c = new Card();
            c.setCuloare(val);
            
            if(a.hasNext())
            c.setId(a.next());
            c.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent ae){
                    selectedCard = c;
                    
                    doTurn();
                    
                }
            });
            
            cardsList.add(c);
        }
        
        this.cards = cardsList;
        createBoard();  
    }
    public void createBoard(){
    	
        JFrame pane = new JFrame("Memory Match");//getContentPane();
        pane.getContentPane().setBackground(new Color(247,224,200));
        JPanel p = new JPanel(new GridLayout(6, 6, 5, 5));
        p.setBackground(new Color(249,242,184));
        for (Card cc : cards){
            p.add(cc);
            cc.setBackground(Color.WHITE);
        }
        
        JLabel l=new JLabel("Memory Game",SwingConstants.CENTER);
        JLabel ll=new JLabel("          ");
        JLabel lll=new JLabel("          ");
        JLabel llll=new JLabel("  Find the matching cards!  ",SwingConstants.CENTER);
        //JLabel lllll=new JLabel(((Integer)trn).toString());
        l.setFont (l.getFont ().deriveFont (27.0f));
        llll.setFont (l.getFont ().deriveFont (21.0f));
        pane.add(ll, BorderLayout.WEST);
        pane.add(lll, BorderLayout.EAST);
        pane.add(l, BorderLayout.NORTH);
        pane.add(llll, BorderLayout.SOUTH);
        pane.add(p,BorderLayout.CENTER);

        pane.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     // setarea dimensiunii ferestrei
     pane.setSize(500, 600);
     // setarea vizibilității ferestrei
     pane.setVisible(true);
     pane.setLocation(450, 80);  
     setTimer(); 
    }
    
    public void setTimer() {
    	t = new Timer(700, new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                checkCards();
            }
        });

        t.setRepeats(false);
    }

    public void doTurn(){
    	trn++;
        if (c1 == null && c2 == null){
            c1 = selectedCard;
            c1.setFont (c1.getFont ().deriveFont (27.0f));
            c1.setText(String.valueOf(c1.getId()));
            c1.setBackground(c1.getCuloare());
        }

        if (c1 != null && c1 != selectedCard && c2 == null){
            c2 = selectedCard;
            c2.setFont (c2.getFont ().deriveFont (27.0f));
            c2.setText(String.valueOf(c2.getId()));
            c2.setBackground(c2.getCuloare());
           
            t.start();

        }
    }

    public void checkCards(){
        if (c1.getId() == c2.getId()){//match condition
            c1.setEnabled(false); //disables the button
            c2.setEnabled(false);
            c1.setMatched(true); //flags the button as having been matched
            c2.setMatched(true);
            if (this.isGameWon()){
                JOptionPane.showMessageDialog(this, "You won!");
                
               System.exit(0);
            }
        }

        else{
            c1.setText(""); //'hides' text
            c1.setBackground(Color.WHITE);
            c2.setText("");
            c2.setBackground(Color.WHITE);
        }
        c1 = null; 
        c2 = null;
    }

    public boolean isGameWon(){
        for(Card c: this.cards){
            if (c.getMatched() == false){
                return false;
            }
        }
        return true;
    }

}