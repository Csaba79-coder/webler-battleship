import java.util.Random;
import java.util.Scanner;

public class BattleshipGame {

    // future plan: ask as input the player's name
    // handle if a guess has already been to provide a valid one!
    // handle if the player enters a wrong position (e.g., A6) -> to ask a proper one!
    // handle bigger ships (ship size as input!)
    // handle that you can place your own ship!

    private static final int GRID_SIZE = 5;
    private static final int NUM_SHIPS = 3;

    private static final char EMPTY_SYMBOL = ' ';
    private static final char SHIP_SYMBOL = 'S';
    private static final char HIT_SYMBOL = 'X';
    private static final char MISS_SYMBOL = 'O';

    private static char[][] playerGrid = new char[GRID_SIZE][GRID_SIZE];
    private static char[][] computerGrid = new char[GRID_SIZE][GRID_SIZE];
    private static char[][] playerGuessGrid = new char[GRID_SIZE][GRID_SIZE];
    private static char[][] computerGuessGrid = new char[GRID_SIZE][GRID_SIZE];

    private static Scanner scanner = new Scanner(System.in);


    public static void main(String[] args) {
        initializeGrids(playerGrid);
        initializeGrids(computerGrid);
        initializeGrids(playerGuessGrid);
        initializeGrids(computerGuessGrid);

        randomlyPlaceShips(computerGrid);

        System.out.println("Welcome to Battleship!");
        System.out.println("Try to sink the computer's ships.");

        randomlyPlaceShips(playerGrid);

        while (true) {
            System.out.println("Your board:");
            displayGrid(playerGrid);
            System.out.println("Your guesses:");
            displayGrid(playerGuessGrid);

            playerTurn();
            if (isGameOver(computerGrid)) {
                System.out.println("Congratulations! You sunk all the computer's ships.");
                break;
            }

            computerTurn();
            if (isGameOver(playerGrid)) {
                System.out.println("Game over! The computer sunk all your ships.");
                break;
            }
        }

        scanner.close();
    }

    private static boolean isGameOver(char[][] grid) {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if (grid[i][j] == SHIP_SYMBOL) {
                    return false;
                }
            }
        }
        return true;
    }

    private static void playerTurn() {
        System.out.print("Enter your guess (e.g., A3): ");
        String guess = scanner.next().toUpperCase();
        int x = guess.charAt(0) - 'A';
        int y = Integer.parseInt(guess.substring(1)) - 1;

        if (x >= 0 && x < GRID_SIZE && y >= 0 && y < GRID_SIZE) {
            if (computerGrid[x][y] == SHIP_SYMBOL) {
                System.out.println("Congratulations! You hit a ship!");
                playerGuessGrid[x][y] = HIT_SYMBOL;
                computerGrid[x][y] = HIT_SYMBOL;
            } else {
                System.out.println("You missed!");
                playerGuessGrid[x][y] = MISS_SYMBOL;
            }
        } else {
            System.out.println("Invalid guess. Please enter a valid position.");
        }
    }

    private static void computerTurn() {
        Random random = new Random();
        int x;
        int y;
        do {
            x = random.nextInt(GRID_SIZE);
            y = random.nextInt(GRID_SIZE);
        } while (playerGrid[x][y] == HIT_SYMBOL || playerGrid[x][y] == MISS_SYMBOL);

        if (playerGrid[x][y] == SHIP_SYMBOL) {
            System.out.println("Computer hit your ship at " + (char) ('A' + x) + (y + 1) + "!");
            playerGrid[x][y] = HIT_SYMBOL;
        } else {
            System.out.println("Computer missed at " + (char) ('A' + x) + (y + 1) + ".");
            playerGrid[x][y] = MISS_SYMBOL;
        }
    }

    private static void displayGrid(char[][] grid) {
        // System.out.println("  1 2 3 4 5");
        System.out.print("  ");
        for (int i = 1; i < GRID_SIZE + 1; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
        for (int i = 0; i < GRID_SIZE; i++) {
            System.out.print((char) ('A' + i) + " ");
            for (int j = 0; j < GRID_SIZE; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private static void initializeGrids(char[][] grid) {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                grid[i][j] = EMPTY_SYMBOL;
            }
        }
    }

    private static void randomlyPlaceShips(char[][] grid) {
        Random random = new Random();
        for (int i = 0; i < NUM_SHIPS; i++) {
            int x; // co-ordinate
            int y; // co-ordinate
            do {
                x = random.nextInt(GRID_SIZE);
                y = random.nextInt(GRID_SIZE);
            } while (grid[x][y] != EMPTY_SYMBOL);
            grid[x][y] = SHIP_SYMBOL;
        }
    }
}