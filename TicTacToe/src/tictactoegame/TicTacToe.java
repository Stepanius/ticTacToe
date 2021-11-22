package tictactoegame;

import java.util.Random;
import java.util.Scanner;

public class TicTacToe {
    public static void main(String[] args) {
        printTableMapping();
        System.out.println("The game has begun");
        char[][] board = {
                {' ', ' ', ' '},
                {' ', ' ', ' '},
                {' ', ' ', ' '}
        };
        printGameTable(board);
        if (new Random().nextBoolean()) {
            makeComputerMove(board);
            printGameTable(board);
        }
        while (true) {
            makeUserMove(board);
            printGameTable(board);
            if (isGameFinished(board)) {
                break;
            }

            makeComputerMove(board);
            printGameTable(board);
            if (isGameFinished(board)) {
                break;
            }
        }
        System.out.println("Game Over!");
    }

    private static void printTableMapping() {
        System.out.println("-------------");
        System.out.println("| 7 | 8 | 9 |");
        System.out.println("-------------");
        System.out.println("| 4 | 5 | 6 |");
        System.out.println("-------------");
        System.out.println("| 1 | 2 | 3 |");
        System.out.println("-------------");
    }

    private static void printGameTable(char[][] board) {
        for (int i = 0; i < 3; i++) {
            System.out.println("-------------");
            for (int j = 0; j < 3; j++) {
                System.out.print("| " + board[i][j] + " ");
            }
            System.out.println("|");
        }
        System.out.println("-------------");
    }

    private static boolean isGameFinished(char[][] board) {
        if (winnerExists(board, 'X')) {
            System.out.println("Player wins!");
            return true;
        }
        if (winnerExists(board, 'O')) {
            System.out.println("Computer wins!");
            return true;
        }
        if (allCellFilled(board)) {
            System.out.println("The game ended in a Draw!");
            return true;
        }
        return false;
    }

    private static boolean allCellFilled(char[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }


    private static boolean winnerExists(char[][] board, char symbol) {
        if (winnerExistsByRows(board, symbol)) {
            return true;
        }
        if (winnerExistsByCols(board, symbol)) {
            return true;
        }
        if (winnerExistsByDiagonals(board, symbol)) {
            return true;
        }
        return false;
    }

    private static boolean winnerExistsByCols(char[][] board, char symbol) {
        for (int i = 0; i < 3; i++) {
            if (board[0][i] == symbol && board[0][i] == board[1][i] && board[1][i] == board[2][i]) {
                return true;
            }
        }
        return false;
    }

    private static boolean winnerExistsByRows(char[][] board, char symbol) {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == symbol && board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
                return true;
            }
        }
        return false;
    }

    private static boolean winnerExistsByDiagonals(char[][] board, char symbol) {
        return board[0][0] == symbol && board[0][0] == board[1][1] && board[1][1] == board[2][2] ||
                board[0][2] == symbol && board[0][2] == board[1][1] && board[1][1] == board[2][0];
    }

    private static void makeComputerMove(char[][] board) {

        if (CheckingForAWinningMoveAndAMoveToPreventThePlayerFromWinning(board)) return;


        if (CheckingTwoMovesAhead(board)) {
            return;
        }

        if (MoveToTheCenterIfItIsFree(board)) {
            return;
        }

        int computerMove = ComputerRandomMove(board);
        System.out.println("Computer chose " + computerMove);
        makeMove(board, Integer.toString(computerMove), 'O');
    }

    private static boolean CheckingForAWinningMoveAndAMoveToPreventThePlayerFromWinning(char[][] board) {
        char []signs = {'O', 'X'};
        for (char sign : signs) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == ' ') {
                        board[i][j] = sign;
                        if (winnerExists(board, sign)) {
                            if (sign == 'X') {
                                board[i][j] = 'O';
                            }
                            return true;
                        } else {
                            board[i][j] = ' ';
                        }
                    }
                }
            }
        }
        return false;
    }

    private static boolean CheckingTwoMovesAhead(final char[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] == 'O') {
                    if (i == 1 && j == 1) {
                        return false;
                    } else if (j == 2 || j == 0) {
                        for (int index = 0; index < 3; index++) {
                            if (board[index][j] == ' ') {
                                board[index][j] = 'O';
                                return true;
                            }
                        }
                    } else if (i == 2 || i == 0) {
                        for (int index = 0; index < 3; index++) {
                            if (board[i][index] == ' ') {
                                board[i][index] = 'O';
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private static boolean MoveToTheCenterIfItIsFree(char[][] board) {
        int center = 1;
        if (board[center][center] == ' ') {
            board[center][center] = 'O';
            return true;
        }
        return false;
    }

    private static int ComputerRandomMove(char[][] board) {
        Random rand = new Random();
        int computerMove;
        while (true) {
            computerMove = rand.nextInt(9) + 1;
            if (isValidNumberAndCellIsEmpty(board, Integer.toString(computerMove))) {
                break;
            }
        }
        return computerMove;
    }

    private static boolean isValidNumberAndCellIsEmpty(char[][] board, String number) {
        switch (number) {
            case "1":
                return (board[2][0] == ' ');
            case "2":
                return (board[2][1] == ' ');
            case "3":
                return (board[2][2] == ' ');
            case "4":
                return (board[1][0] == ' ');
            case "5":
                return (board[1][1] == ' ');
            case "6":
                return (board[1][2] == ' ');
            case "7":
                return (board[0][0] == ' ');
            case "8":
                return (board[0][1] == ' ');
            case "9":
                return (board[0][2] == ' ');
            default:
                return false;
        }
    }

    private static void makeUserMove(char[][] board) {
        String userInput;
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Where do you want to make a move? (1-9)");
            userInput = scanner.nextLine();
            if (isValidNumberAndCellIsEmpty(board, userInput)) {
                break;
            } else {
                System.out.println(userInput + " is not a valid move.");
            }
        }
        makeMove(board, userInput, 'X');
    }

    private static void makeMove(char[][] board, String position, char symbol) {
        switch (position) {
            case "1":
                board[2][0] = symbol;
                break;
            case "2":
                board[2][1] = symbol;
                break;
            case "3":
                board[2][2] = symbol;
                break;
            case "4":
                board[1][0] = symbol;
                break;
            case "5":
                board[1][1] = symbol;
                break;
            case "6":
                board[1][2] = symbol;
                break;
            case "7":
                board[0][0] = symbol;
                break;
            case "8":
                board[0][1] = symbol;
                break;
            case "9":
                board[0][2] = symbol;
                break;
            default:
                System.out.println(":(");
                break;
        }
    }
}
