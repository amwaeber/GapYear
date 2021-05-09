import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.Timer;

public class DeliveryGame extends JPanel{
    private final int OFFSET = 30;
    private final int SPACE = 20;
    private final int LEFT_COLLISION = 1;
    private final int RIGHT_COLLISION = 2;
    private final int TOP_COLLISION = 3;
    private final int BOTTOM_COLLISION = 4;

    private ArrayList<Wall> walls;
    private DeliveryTarget deliverArea;
    private PickUpTarget collectArea;

    private Player player;
    private int w = 0;
    private int h = 0;

    private boolean isCollected = false;
    private boolean isCompleted = false;

    private Timer timer;
    private float time = 0.0f;
    private int cashEarned = 200;

    public DeliveryGame() {
        initBoard();
    }

    private void initBoard() {
        addKeyListener(new TAdapter());
        setFocusable(true);
        initWorld();
    }

    public int getBoardWidth() {
        return this.w;
    }

    public int getBoardHeight() {
        return this.h;
    }

    private void initWorld() {
        walls = new ArrayList<>();

        int x = OFFSET;
        int y = OFFSET;

        Wall wall;

        Maze maze = new Maze(30, 20);
        String level = maze.toString();

        for (int i = 0; i < level.length(); i++) {
            char item = level.charAt(i);
            switch (item) {
                case '\n':
                    y += SPACE;
                    if (this.w < x) {
                        this.w = x;
                    }
                    x = OFFSET;
                    break;

                case '#':
                    wall = new Wall(x, y);
                    walls.add(wall);
                    x += SPACE;
                    break;

                case '$':
                    collectArea = new PickUpTarget(x, y);
                    x += SPACE;
                    break;

                case '.':
                    deliverArea = new DeliveryTarget(x, y);
                    x += SPACE;
                    break;

                case '@':
                    player = new Player(x, y);
                    x += SPACE;
                    break;

                case ' ':
                    x += SPACE;
                    break;

                default:
                    break;
            }

            h = y;
        }
        timer = new Timer(100, new StopListener());
        timer.start();
    }

    private void buildWorld(Graphics g) {
        g.setColor(new Color(250, 240, 170));
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        g.setColor(new Color(0, 0, 0));
        g.drawString(String.format("Time: %.1f", time), 20, 20);

        ArrayList<Actor> world = new ArrayList<>(walls);
        world.add(deliverArea);
        world.add(collectArea);
        world.add(player);

        for (Actor item : world) {
            g.drawImage(item.getImage(), item.x(), item.y(), this);

            if (isCompleted) {
                float delayedDelivery = (time < 10 ? 0 : (time > 40 ? 30 : time - 10));
                cashEarned = (int) (200 - delayedDelivery * 200 / 30);
                g.setColor(new Color(0, 0, 0));
                g.drawString(String.format("You earned %d\u20ac", cashEarned), 250, 20);
            }

        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        buildWorld(g);
    }

    private class TAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {

            if (isCompleted) {
                restartLevel();
            }

            int key = e.getKeyCode();

            switch (key) {
                case KeyEvent.VK_LEFT:
                    if (checkWallCollision(player, LEFT_COLLISION)) {
                        return;
                    }

                    player.move(-SPACE, 0);
                    player.setDirection(2, isCollected);
                    isCompleted();
                    break;

                case KeyEvent.VK_RIGHT:
                    if (checkWallCollision(player, RIGHT_COLLISION)) {
                        return;
                    }

                    player.move(SPACE, 0);
                    player.setDirection(0, isCollected);
                    isCompleted();
                    break;

                case KeyEvent.VK_UP:
                    if (checkWallCollision(player, TOP_COLLISION)) {
                        return;
                    }

                    player.move(0, -SPACE);
                    player.setDirection(1, isCollected);
                    isCompleted();
                    break;

                case KeyEvent.VK_DOWN:
                    if (checkWallCollision(player, BOTTOM_COLLISION)) {
                        return;
                    }

                    player.move(0, SPACE);
                    player.setDirection(3, isCollected);
                    isCompleted();
                    break;

                case KeyEvent.VK_R:
                    restartLevel();
                    break;

                default:
                    break;
            }

            repaint();
        }
    }

    private boolean checkWallCollision(Actor actor, int type) {
        switch (type) {
            case LEFT_COLLISION:
                for (Wall wall : walls) {
                    if (actor.isLeftCollision(wall)) {
                        return true;
                    }
                }
                return false;

            case RIGHT_COLLISION:
                for (Wall wall : walls) {
                    if (actor.isRightCollision(wall)) {
                        return true;
                    }
                }
                return false;

            case TOP_COLLISION:
                for (Wall wall : walls) {
                    if (actor.isTopCollision(wall)) {
                        return true;
                    }
                }
                return false;

            case BOTTOM_COLLISION:
                for (Wall wall : walls) {
                    if (actor.isBottomCollision(wall)) {
                        return true;
                    }
                }
                return false;

            default:
                break;
        }
        return false;
    }

    public void isCompleted() {
        if (player.x() == collectArea.x() && player.y() == collectArea.y()) {
            isCollected = true;
        }
        if (player.x() == deliverArea.x() && player.y() == deliverArea.y() && isCollected) {
            isCompleted = true;
            timer.stop();
            repaint();
        }
    }

    public void restartLevel() {
        timer.stop();  // Ensures reset of timer when first starting the level
        walls.clear();
        time = 0;

        initWorld();

        if (isCollected) {
            isCollected = false;
        }

        if (isCompleted) {
            isCompleted = false;
        }
    }

    private class StopListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            time += 0.1;
            repaint();
            timer.restart();
        }
    }

}
