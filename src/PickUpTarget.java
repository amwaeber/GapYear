import java.awt.Image;
import javax.swing.ImageIcon;

public class PickUpTarget extends Actor {
    public PickUpTarget(int x, int y) {
        super(x, y);

        initArea();
    }

    private void initArea() {
        ImageIcon iicon = new ImageIcon("src/resources/shop_00.png");
        Image image = iicon.getImage();
        setImage(image);
    }
}
