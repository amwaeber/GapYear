import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GapYear extends JFrame implements ActionListener{

    JPanel cards;
    CardLayout cardLayout;
    StartScreen startScreen;
    DeliveryGame deliveryGame;

    public GapYear(){
        initUI();
    }

    private void initUI(){
        setLayout(new FlowLayout()); //Use this for now.
        setTitle("Gap Year");
        setSize(new Dimension(800, 600));
        setResizable(false);

        startScreen = new StartScreen();
        startScreen.setPreferredSize(this.getSize());
        startScreen.newGameButton.addActionListener(this);
        startScreen.loadButton.addActionListener(this);
        startScreen.saveButton.addActionListener(this);
        startScreen.quitButton.addActionListener(this);

        deliveryGame = new DeliveryGame();
        startScreen.setPreferredSize(this.getSize());
//        add(deliveryGame);

        cards = new JPanel(new CardLayout());
        cards.add(startScreen, "START_SCREEN");
        cards.add(deliveryGame, "DELIVERY_GAME");
        cardLayout = (CardLayout) cards.getLayout();
        cardLayout.show(cards, "START_SCREEN");

        getContentPane().add(cards);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //getContentPane().setLayout(null);  // check if needed
        setLocationRelativeTo(null);
    }

    public static void main(String[] args){
        EventQueue.invokeLater(() -> {
            GapYear theGame = new GapYear();
            theGame.setVisible(true);
        });
    }

    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == startScreen.newGameButton){
            cardLayout.show(cards,"DELIVERY_GAME");
            deliveryGame.requestFocus();
            deliveryGame.restartLevel();
        }
    }
}
