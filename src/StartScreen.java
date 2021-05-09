import javax.swing.*;

public class StartScreen extends JPanel{

    JButton newGameButton, loadButton, saveButton, quitButton;

    public StartScreen() {
        initScreen();
    }

    private void initScreen() {
        newGameButton = new JButton("New Game");
        loadButton = new JButton("Load");
        saveButton = new JButton("Save");
        quitButton = new JButton("Quit");

        this.add(newGameButton);
        this.add(loadButton);
        this.add(saveButton);
        this.add(quitButton);
    }

}
