import java.awt.Image;
import javax.swing.ImageIcon;

public class Player extends Actor {
    private int direction;
    private boolean luggage;

    public Player(int x, int y) {
        super(x, y);

        this.direction = 0;
        this.luggage = false;

        initPlayer();
    }

    private void initPlayer() {
        updateImage();
    }

    public void move(int x, int y) {
        int dx = x() + x;
        int dy = y() + y;

        setX(dx);
        setY(dy);
    }

    public void setDirection(int direction, boolean isCollected) {
        this.direction = direction;
        this.luggage = isCollected;
        updateImage();
    }

    private void updateImage() {
        ImageIcon iicon;
        int fileEnding = (luggage ? direction + 4 : direction);
        iicon = new ImageIcon(String.format("src/resources/cyclist_0%d.png", fileEnding));
        Image image = iicon.getImage();
        setImage(image);
    }
}
