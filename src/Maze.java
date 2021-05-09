import java.util.LinkedList;
import java.util.Random;

public class Maze {
    public static final char PASSAGE_CHAR = ' ';
    public static final char WALL_CHAR = '#';
    public static final char PLAYER_CHAR = '@';
    public static final char TARGET_CHAR = '.';
    public static final char PICKUP_CHAR = '$';

    public static final boolean WALL = false;
    public static final boolean PASSAGE = !WALL;

    private final boolean[][] map;
    private final int width;
    private final int height;

    public Maze( final int width, final int height ){
        this.width = width;
        this.height = height;
        this.map = new boolean[width][height];

        final LinkedList<int[]> frontiers = new LinkedList<>();
        final Random random = new Random();
        int x = random.nextInt(width);
        int y = random.nextInt(height);
        frontiers.add(new int[]{x,y,x,y});

        while ( !frontiers.isEmpty() ){
            final int[] f = frontiers.remove( random.nextInt( frontiers.size() ) );
            x = f[2];
            y = f[3];
            if ( map[x][y] == WALL )
            {
                map[f[0]][f[1]] = map[x][y] = PASSAGE;
                if ( x >= 2 && map[x-2][y] == WALL )
                    frontiers.add( new int[]{x-1,y,x-2,y} );
                if ( y >= 2 && map[x][y-2] == WALL )
                    frontiers.add( new int[]{x,y-1,x,y-2} );
                if ( x < width-2 && map[x+2][y] == WALL )
                    frontiers.add( new int[]{x+1,y,x+2,y} );
                if ( y < height-2 && map[x][y+2] == WALL )
                    frontiers.add( new int[]{x,y+1,x,y+2} );
            }
        }
    }

    @Override
    public String toString(){
        int[] playerXY = playerCoordinates();
        int[] targetXY = targetCoordinates();
        int[] pickupXY = pickupCoordinates();

        final StringBuilder b = new StringBuilder();
        boolean doubleWallWest = areAllWall(map[0]);
        boolean doubleWallNorth = areAllWall(getColumn(map, 0));
        if (!doubleWallNorth) {
            b.append(String.valueOf(WALL_CHAR).repeat(width + 1));
            b.append('\n');
        }
        for ( int y = 0; y < height; y++ ){
            if (!doubleWallWest) {
                b.append(WALL_CHAR);
            }
            for ( int x = 0; x < width; x++ )
                if ((x == playerXY[0]) && (y == playerXY[1])) {
                    b.append(PLAYER_CHAR);
                } else if ((x == targetXY[0]) && (y == targetXY[1])) {
                    b.append(TARGET_CHAR);
                } else if ((x == pickupXY[0]) && (y == pickupXY[1])) {
                    b.append(PICKUP_CHAR);
                } else {
                    b.append(map[x][y] == WALL ? WALL_CHAR : PASSAGE_CHAR);
                }
            if (doubleWallWest) {
                b.append(WALL_CHAR);
            }
            b.append( '\n' );
        }
        if (doubleWallNorth) {
            b.append(String.valueOf(WALL_CHAR).repeat(width + 1));
            b.append('\n');
        }
        return b.toString();
    }

    public static boolean areAllWall(boolean[] array) {
        for(boolean b : array) if(b) return false;
        return true;
    }

    public static boolean[] getColumn(boolean[][] array, int index){
        boolean[] column = new boolean[array[0].length]; // Here I assume a rectangular 2D array!
        for(int i=0; i<column.length; i++){
            column[i] = array[i][index];
        }
        return column;
    }

    public int[] playerCoordinates() {
        final LinkedList<int[]> playerXY = new LinkedList<>();
        final Random random = new Random();
        int x = random.nextInt(width/4);
        int y = random.nextInt(height/4 - 1);

        while ( playerXY.isEmpty() ){
            if (map[x][y] == PASSAGE) {
                playerXY.add(new int[]{x, y});
            } else {
                x = random.nextInt(width/4);
                y = random.nextInt(height/4 - 1);
            }
        }
        return playerXY.remove(0);
    }

    public int[] targetCoordinates() {
        final LinkedList<int[]> targetXY = new LinkedList<>();
        final Random random = new Random();
        int x = random.nextInt(3);
        int y = random.nextInt(3 * height / 4) + height / 4 - 1;
        int iter = 25;

        while ( targetXY.isEmpty() ){
            if ((map[x][y] == PASSAGE) && (((map[x+1][y] == WALL) && ((map[x][y-1] == WALL) ^ (map[x][y+1] == WALL)))
                    | iter < 0)) {
                targetXY.add(new int[]{x, y});
            } else {
                x = random.nextInt(3);
                y = random.nextInt(3 * height / 4) + height / 4 - 1;
                iter -= 1;
            }
        }
        return targetXY.remove(0);
    }

    public int[] pickupCoordinates() {
        final LinkedList<int[]> pickupXY = new LinkedList<>();
        final Random random = new Random();
        int x = random.nextInt(3) + width - 3;
        int y = random.nextInt(height - 2) + 1;
        int iter = 25;

        while ( pickupXY.isEmpty() ){
            if ((map[x][y] == PASSAGE) && (((map[x-1][y] == WALL) && ((map[x][y-1] == WALL) ^ (map[x][y+1] == WALL)))
                    | iter < 0)) {
                pickupXY.add(new int[]{x, y});
            } else {
                x = random.nextInt(3) + width - 3;
                y = random.nextInt(height - 2) + 1;
                iter -= 1;
            }
        }
        return pickupXY.remove(0);
    }
}
