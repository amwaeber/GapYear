import java.awt.Image;
import javax.swing.ImageIcon;

public class DeliveryTarget extends Actor {
    public DeliveryTarget(int x, int y) {
        super(x, y);

        initArea();
    }

    private void initArea() {
        ImageIcon iicon = new ImageIcon("src/resources/finish_00.png");
        Image image = iicon.getImage();
        setImage(image);
    }
}
