import java.util.Random;

public class GameBoard {
    /*
    Grid states:
    default bomb = -1
    empty = 0
    has bombs around = 1-8
    opened tiles >10 & <19
    flagged bomb = 19
    flagged tile >=20
     */



    private static int[][] grid;
    private static int X;
    private static int Y;
    private static int tiles;

    public static int getTiles() {
        return tiles;
    }

    public static void setTiles(int tiles) {
        GameBoard.tiles = tiles;
    }

    public static void reduceTiles() {
        GameBoard.tiles--;
    }

    public static int getGrid(int x, int y) {
        return grid[x][y];
    }

    public static void setGrid(int x, int y, int state) {
        GameBoard.grid[x][y] = state;
    }

    public static void editGrid(int x, int y, int state) {
        GameBoard.grid[x][y] += state;
    }

    public static int getX() {
        return X;
    }

    public static int getY() {
        return Y;
    }

    protected GameBoard(int x, int y, int bombs) {
        X = x;
        Y = y;

        grid = new int[x][y];
        generateBombs(bombs, new Random());
    }

    private void generateBombs(int bombs, Random random) {
        setTiles(X*Y-bombs);

        while (bombs != 0) {
            int rx = random.nextInt(X);
            int ry = random.nextInt(Y);

            if (Logic.isBomb(rx, ry)) continue;
            editGrid(rx,ry,-1);

            for (int x = -1; x < 2; x++) {
                for (int y = -1; y < 2; y++) {
                    if (Logic.isValid(rx + x, ry + y) && !Logic.isBomb(rx + x, ry + y))
                    editGrid(rx+x,ry+y,1);
                }
            }
            bombs--;
        }
    }

    protected static void print() {
        StringBuilder builder = new StringBuilder();

        for (int y = -1; y < GameBoard.Y; y++) {
            builder.append("\t[").append(y == -1 ? " " : y + 1).append("]");
            for (int x = 0; x < GameBoard.X; x++) {
                builder.append("[").append(y == -1 ? x + 1
                        : Logic.hasFlag(x, y) ? "F"
                        : Logic.isOpen(x, y) ? Logic.isBomb(x, y) ? "*"
                        : GameBoard.getGrid(x,y) == 10 ? " "
                        : GameBoard.getGrid(x,y) - 10
                        : "-").append("]");
            }
            builder.append("\n");
        }
        System.out.println(builder.toString());
    }

}
