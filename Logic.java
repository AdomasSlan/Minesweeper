class Logic {

    static boolean isBomb(int x, int y) {
        return isValid(x, y) && (GameBoard.getGrid(x,y) == -1 || GameBoard.getGrid(x,y) == 9 || GameBoard.getGrid(x,y) == 19);
    }

    static boolean isValid(int x, int y) {
        return x >= 0 && y >= 0 && x < GameBoard.getX() && y < GameBoard.getY();
    }

    static boolean hasFlag(int x, int y) {
        return isValid(x, y) && GameBoard.getGrid(x,y) > 18;
    }

    public static boolean isOpen(int x, int y) {
        return isValid(x, y) && GameBoard.getGrid(x,y) > 8 && GameBoard.getGrid(x,y) < 19;
    }


    protected static void setFlag(int x, int y) {
        if (!isValid(x, y) || isOpen(x, y)) return;
        GameBoard.editGrid(x,y,hasFlag(x, y) ? -20 : 20);
    }

    protected static void open(int x, int y) {
        if (!hasFlag(x, y) && isBomb(x, y)) {
            GameBoard.setTiles(-1);
            for (int xx = 0; xx < GameBoard.getX(); xx++) {
                for (int yy = 0; yy < GameBoard.getY(); yy++) {
                    if (isBomb(xx, yy)) GameBoard.setGrid(xx,yy,9);
                }
            }
        } else clear(x, y);
    }

    private static void clear(int x, int y) {
        if (!isValid(x, y) || isOpen(x, y) || hasFlag(x, y)) return;
        GameBoard.editGrid(x,y,10);
        GameBoard.reduceTiles();
        if (GameBoard.getGrid(x,y) > 10) return;
        clear(x - 1, y);
        clear(x + 1, y);
        clear(x, y - 1);
        clear(x, y + 1);
    }
}
