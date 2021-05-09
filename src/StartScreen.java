import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class StartScreen extends JPanel{

    CustomButton newGameButton, loadButton, saveButton, quitButton;
    private Image bgImage, startImage, loadImage, saveImage, quitImage = null;

    public StartScreen() {
        initScreen();
    }

    private void initScreen() {
        try {
            bgImage = ImageIO.read(new File("src/resources/start_screen.png"));
            startImage = ImageIO.read(new File("src/resources/start_btn.png"));
            loadImage = ImageIO.read(new File("src/resources/load_btn.png"));
            saveImage = ImageIO.read(new File("src/resources/save_btn.png"));
            quitImage = ImageIO.read(new File("src/resources/quit_btn.png"));
        } catch (IOException exp) {
            exp.printStackTrace();
        }

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(Box.createVerticalGlue());
        this.add(Box.createRigidArea(new Dimension(0,80)));

        newGameButton = new CustomButton("", startImage);
        newGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(newGameButton);
        this.add(Box.createRigidArea(new Dimension(0,10)));

        loadButton = new CustomButton("Load", loadImage);
        loadButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(loadButton);
        this.add(Box.createRigidArea(new Dimension(0,10)));

        saveButton = new CustomButton("Save", saveImage);
        saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(saveButton);
        this.add(Box.createRigidArea(new Dimension(0,10)));

        quitButton = new CustomButton("Quit", quitImage);
        quitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(quitButton);
        this.add(Box.createVerticalGlue());

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bgImage, 0, 0, null);
    }

    public static class CustomButton extends JButton {

        private final Image btnImage;

        public CustomButton(String label, Image myImage) {
            super(label);
            btnImage = myImage;
            this.setBorder(null);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(btnImage, 0, 0, null);
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(btnImage.getWidth(null), btnImage.getHeight(null));
        }

        @Override
        public Dimension getMinimumSize() {
            return new Dimension(btnImage.getWidth(null), btnImage.getHeight(null));
        }

        @Override
        public Dimension getMaximumSize() {
            return new Dimension(btnImage.getWidth(null), btnImage.getHeight(null));
        }

    }
}
