import java.util.*;

class MineSweeper {



    private static void play() {
        Scanner scanner = new Scanner(System.in);

        while (GameBoard.getTiles() > 0) {
            GameBoard.print();
            System.out.println(GameBoard.getTiles()+" remaining tiles");
            System.out.print("Choose X and Y coordinates ");
            String[] line = scanner.nextLine().split(" ");

            try {
                int x = Integer.parseInt(line[0]) - 1;
                int y = Integer.parseInt(line[1]) - 1;

                boolean flag = line.length > 2;
                if (flag) Logic.setFlag(x, y);
                else Logic.open(x, y);
            } catch (Exception e) {
                System.out.println("Value not included.");
            }
        }

        GameBoard.print();
        if (GameBoard.getTiles() == 0) {
            System.out.println("Congratulations you won");
        } else {
            System.out.println("You lost");
        }
        scanner.close();

    }

    public static void main(String... args) {
        new GameBoard(9, 9, 10);
        play();
    }
}

