import java.util.*;

public class MineSweeper {


    private final int[][] grid;
    private final int x, y;

    private int tiles;

    private MineSweeper(int x, int y, int bombs) {
        this.x = x;
        this.y = y;

        grid = new int[x][y];
        generateBombs(bombs, new Random());
    }

    private void generateBombs(int bombs, Random random) {
        tiles = x * y - bombs;

        while (bombs != 0) {
            int rx = random.nextInt(x);
            int ry = random.nextInt(y);

            if (isBomb(rx, ry)) continue;
            grid[rx][ry] = -1;

            for (int x = -1; x < 2; x++) {
                for (int y = -1; y < 2; y++) {
                    if (isValid(rx + x, ry + y) && !isBomb(rx + x, ry + y))
                        grid[rx + x][ry + y]++;
                }
            }
            bombs--;
        }
    }

    private boolean isBomb(int x, int y) {
        return isValid(x, y) && (grid[x][y] == -1 || grid[x][y] == 9 || grid[x][y] == 19);
    }

    private boolean isValid(int x, int y) {
        return x >= 0 && y >= 0 && x < this.x && y < this.y;
    }

    private boolean hasFlag(int x, int y) {
        return isValid(x, y) && grid[x][y] > 18;
    }

    public boolean isOpen(int x, int y) {
        return isValid(x, y) && grid[x][y] > 8 && grid[x][y] < 19;
    }

    private void print() {
        StringBuilder builder = new StringBuilder();

        for (int y = -1; y < this.y; y++) {
            builder.append("\t[").append(y == -1 ? " " : y + 1).append("]");
            for (int x = 0; x < this.x; x++) {
                builder.append("[").append(y == -1 ? x + 1
                        : hasFlag(x, y) ? "F"
                        : isOpen(x, y) ? isBomb(x, y) ? "*"
                        : grid[x][y] == 10 ? " "
                        : grid[x][y] - 10
                        : "-").append("]");
            }
            builder.append("\n");
        }
        System.out.println(builder.toString());
    }

    private void setFlag(int x, int y) {
        if (!isValid(x, y) || isOpen(x, y)) return;
        grid[x][y] += hasFlag(x, y) ? -20 : 20;
    }

    private void open(int x, int y) {
        if (!hasFlag(x, y) && isBomb(x, y)) {
            tiles = -1;
            for (int xx = 0; xx < this.x; xx++) {
                for (int yy = 0; yy < this.y; yy++) {
                    if (isBomb(xx, yy)) grid[xx][yy] = 9;
                }
            }
        } else clear(x, y);
    }

    private void clear(int x, int y) {
        if (!isValid(x, y) || isOpen(x, y) || hasFlag(x, y)) return;
        grid[x][y] += 10;
        tiles--;
        if (grid[x][y] > 10) return;
        clear(x - 1, y);
        clear(x + 1, y);
        clear(x, y - 1);
        clear(x, y + 1);
    }

    private void play() {
        Scanner scanner = new Scanner(System.in);

        while (tiles > 0) {
            print();
            System.out.print("Choose X and Y coordinates ");
            String[] line = scanner.nextLine().split(" ");

            try {
                int x = Integer.parseInt(line[0]) - 1;
                int y = Integer.parseInt(line[1]) - 1;

                boolean flag = line.length > 2;
                if (flag) setFlag(x, y);
                else open(x, y);
            } catch (Exception e) {
                System.out.println("Value not included.");
            }
        }

        print();
        if (tiles == 0) {
            System.out.println("Congratulations you won");
        } else {
            System.out.println("You lost");
        }
        scanner.close();

    }

    public static void main(String... args) {
        new MineSweeper(9, 9, 10).play();
    }
}
