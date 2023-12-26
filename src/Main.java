import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        ArrayList<String> playersName = new ArrayList<>();
        ArrayList<Integer> playersPoints = new ArrayList<>();
        int gamesNumber = 0;
        boolean gamesOn = true;
        while (gamesOn) {
            System.out.println("Hit - ×");
            System.out.println("Miss - ○");
            System.out.println("Sunk - ■");
            System.out.print("Enter name: ");
            String playerName = scanner.nextLine();
            clearScreen();

            int[][] battleField = new int[7][7];

            int createdShipsCounter = 0;
            while (createdShipsCounter < 1) {
                int x1 = random.nextInt(7);
                int y1 = random.nextInt(7);
                int x2 = random.nextInt(7);
                int y2 = random.nextInt(7);
                int x3 = random.nextInt(7);
                int y3 = random.nextInt(7);

                if (puttingTrippleShip(x1, x2, x3, y1, y2, y3)) {
                    battleField[x1][y1] = 3;
                    battleField[x2][y2] = 3;
                    battleField[x3][y3] = 3;
                    createdShipsCounter++;
                }
            }
            while (createdShipsCounter < 3) {
                int x1 = random.nextInt(7);
                int y1 = random.nextInt(7);
                int x2 = random.nextInt(7);
                int y2 = random.nextInt(7);
                if (battleField[x1][y1] == 0 && battleField[x2][y2] == 0 && checkAround(x1, y1, battleField) && puttingDoubleShips(x1, y1, x2, y2) && checkAround(x2, y2, battleField)) {
                    battleField[x1][y1] = 2;
                    battleField[x2][y2] = 2;
                    createdShipsCounter++;
                }
            }
            while (createdShipsCounter < 7) {
                int x1 = random.nextInt(7);
                int y1 = random.nextInt(7);
                if (battleField[x1][y1] == 0 && checkAround(x1, y1, battleField)) {
                    battleField[x1][y1] = 1;
                    createdShipsCounter++;
                }
            }
            String[][] PlayingField = new String[8][8];

            int throwsCounter = 0;
            int sunkShips = 0;
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (j == 0 && i != 0) {
                        PlayingField[i][j] = i + "|";
                    }
                    else if (i == 0 && j != 0) {
                        PlayingField[i][j] = j + "|";
                    }
                    else {
                        PlayingField[i][j] = "_|";
                    }
                }
            }
            while (sunkShips != 7) {
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        System.out.print(PlayingField[i][j]);
                    }
                    System.out.println();
                }
                System.out.print("Enter vertical coordinate: ");
                int xShipCoordinates = scanner.nextInt();
                System.out.print("Enter horizontal coordinate: ");
                int yShipCoordinates = scanner.nextInt();
                clearScreen();
                if (xShipCoordinates > 7 || xShipCoordinates < 1 || yShipCoordinates > 7 || yShipCoordinates < 1) {
                    System.out.println("You have entered wrong coordinates. Please enter coordinates again.");

                }
                else if (PlayingField[xShipCoordinates][yShipCoordinates] == "○|" || PlayingField[xShipCoordinates][yShipCoordinates] == "■|" || PlayingField[xShipCoordinates][yShipCoordinates] == "×|") {
                    System.out.println("You have already fired this cage");
                }
                else {
                    throwsCounter++;
                    if (battleField[xShipCoordinates - 1][yShipCoordinates - 1] != 0) {
                        PlayingField[xShipCoordinates][yShipCoordinates] = "×|";
                        if (checkAround(xShipCoordinates - 1, yShipCoordinates - 1, battleField)) {
                            PlayingField[xShipCoordinates][yShipCoordinates] = "■|";
                            System.out.println("Congrats, you have sunk ship");
                            sunkShips++;
                        } else if (checkingShipsAfterHitting(xShipCoordinates, yShipCoordinates, PlayingField, battleField)) {
                            System.out.println("Congrats, you have sunk ship");
                            sunkShips++;
                        }
                        else{
                            System.out.println("Congrats, you have hitted ship");
                        }
                    }
                }
                if(sunkShips == 7){
                    playersName.add(gamesNumber,playerName);
                    playersPoints.add(gamesNumber, throwsCounter);
                }
            }
            System.out.println("You have won!");
            System.out.println("Your number of throws: " + throwsCounter);

            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    System.out.print(PlayingField[i][j]);
                }
                System.out.println();
            }

            System.out.println("Do you want to play again?");
            scanner.nextLine();
            String restartGameAnswer = scanner.nextLine();
            clearScreen();

            if (!playingOneMoreTime(restartGameAnswer)) {
                String[] allPlayersName = new String[playersName.size()];
                playersName.toArray(allPlayersName);
                int[] allPlayersPoints = new int[playersPoints.size()];
                for (int i = 0; i < gamesNumber + 1; i++) {
                    allPlayersPoints[i] = playersPoints.get(i);
                }
            }
        }
    }
    static boolean playingOneMoreTime(String answer){
        if (answer.equals("Yes") || answer.equals("yes")){
            return true;
        }
        else {
            return false;
        }
    }
    static boolean checkingShipsAfterHitting(int x, int y, String [][] PlayingField, int [][] BattleField){
        int checkCounter = 0;
        if (BattleField [x - 1][y - 1] == 3 && y > 2 ){
            if (PlayingField[x][y].equals("×|") && PlayingField[x][y - 1].equals("×|") && PlayingField[x][y - 2].equals("×|")){
                PlayingField[x][y] = "■|";
                PlayingField[x][y - 1] = "■|";
                PlayingField[x][y - 2] = "■|";
                checkCounter++;
            }
        }
        if (BattleField [x - 1][y - 1] == 3 && y < 6){
            if (PlayingField[x][y].equals("×|") && PlayingField[x][y + 1].equals("×|") && PlayingField[x][y + 2].equals("×|")){
                PlayingField[x][y] = "■|";
                PlayingField[x][y + 1] = "■|";
                PlayingField[x][y + 2] = "■|";
                checkCounter++;
            }
        }
        if (BattleField [x - 1][y - 1] == 3 && y < 7 && y > 1){
            if (PlayingField[x][y].equals("×|") && PlayingField[x][y - 1].equals("×|") && PlayingField[x][y + 1].equals("×|")){
                PlayingField[x][y] = "■|";
                PlayingField[x][y + 1] = "■|";
                PlayingField[x][y - 1] = "■|";
                checkCounter++;
            }
        }
        if (BattleField [x - 1][y - 1] == 3 && x < 6){
            if (PlayingField[x][y].equals("×|") && PlayingField[x + 1][y].equals("×|") && PlayingField[x + 2][y].equals("×|")){
                PlayingField[x][y] = "■|";
                PlayingField[x + 1][y] = "■|";
                PlayingField[x + 2][y] = "■|";
                checkCounter++;
            }
        }
        if (BattleField [x - 1][y - 1] == 3 && x > 2){
            if (PlayingField[x][y].equals("×|") && PlayingField[x - 1][y].equals("×|") && PlayingField[x - 2][y].equals("×|")){
                PlayingField[x][y] = "■|";
                PlayingField[x - 1][y] = "■|";
                PlayingField[x - 2][y] = "■|";
                checkCounter++;
            }
        }
        if (BattleField [x - 1][y - 1] == 3 && x < 7 && x > 1){
            if (PlayingField[x][y].equals("×|") && PlayingField[x - 1][y].equals("×|") && PlayingField[x + 1][y].equals("×|")){
                PlayingField[x][y] = "■|";
                PlayingField[x - 1][y] = "■|";
                PlayingField[x + 1][y] = "■|";
                checkCounter++;
            }
        }
        if (BattleField [x - 1][y - 1] == 2 && y < 7){
            if (PlayingField[x][y].equals("×|") && PlayingField[x][y + 1].equals("×|")){
                PlayingField[x][y] = "■|";
                PlayingField[x][y + 1] = "■|";
                checkCounter++;
            }
        }
        if (BattleField [x - 1][y - 1] == 2 && y > 1){
            if (PlayingField[x][y].equals("×|") && PlayingField[x][y - 1].equals("×|")){
                PlayingField[x][y] = "■|";
                PlayingField[x][y - 1] = "■|";
                checkCounter++;
            }
        }
        if (BattleField [x - 1][y - 1] == 2 && x < 7){
            if (PlayingField[x][y].equals("×|") && PlayingField[x + 1][y].equals("×|")){
                PlayingField[x][y] = "■|";
                PlayingField[x + 1][y] = "■|";
                checkCounter++;
            }
        }
        if (BattleField [x - 1][y - 1] == 2 && x > 1){
            if (PlayingField[x][y].equals("×|") && PlayingField[x - 1][y].equals("×|")){
                PlayingField[x][y] = "■|";
                PlayingField[x - 1][y] = "■|";
                checkCounter++;
            }
        }
        return checkCounter > 0;

    }
    static boolean puttingDoubleShips(int x1, int y1, int x2, int y2){
        boolean checkingCellsAround = false;
        if (x1 == x2 && (y1 - y2 == 1 || y1 - y2 == -1)){
            checkingCellsAround = true;
        }
        else if (y1 == y2 && (x1 - x2 == 1 || x1 - x2 == -1)){
            checkingCellsAround = true;
        }
        return checkingCellsAround;
    }
    static boolean checkAround(int x1, int y1, int [][]battleField) {
        boolean checkingCellsAround = false;
        if (x1 == 0 && y1 == 0 && battleField[x1 + 1][y1] == 0 && battleField[x1][y1 + 1] == 0 && battleField[x1 + 1][y1 + 1] == 0) {
            checkingCellsAround = true;
        }
        else if (x1 == 6 && y1 == 6 && battleField[x1 - 1][y1] == 0 && battleField[x1][y1 - 1] == 0 && battleField[x1 - 1][y1 - 1] == 0) {
            checkingCellsAround = true;
        }
        else if (x1 == 0 && y1 !=0 && y1 != 6 && battleField [x1 + 1][y1] == 0 &&  battleField[x1][y1 - 1] == 0 && battleField[x1][y1 + 1] == 0 && battleField[x1 + 1][y1 - 1] == 0 && battleField[x1 + 1][y1 + 1] == 0) {
            checkingCellsAround = true;
        }
        else if (y1 == 0  && x1!= 0 && x1!= 6 && battleField [x1][y1 + 1] == 0 && battleField[x1 - 1][y1] == 0 && battleField[x1 + 1][y1] == 0 && battleField[x1 + 1][y1 + 1] == 0 && battleField[x1 - 1][y1 + 1] == 0) {
            checkingCellsAround = true;
        }
        else if (x1 == 6  && y1 !=0 && y1 != 6 && battleField [x1 - 1][y1] == 0 && battleField [x1][y1 + 1] == 0 && battleField [x1][y1 - 1] == 0 && battleField[x1 - 1][y1 - 1] == 0 && battleField[x1 - 1][y1 + 1] == 0){
            checkingCellsAround = true;
        }
        else if (y1 == 6  && x1!= 0 && x1!= 6 && battleField [x1 + 1][y1] == 0 && battleField[x1 - 1][y1] == 0 && battleField[x1][y1 - 1] == 0 && battleField[x1 - 1][y1 - 1] == 0 && battleField[x1 + 1][y1 - 1] == 0) {
            checkingCellsAround = true;
        }
        else if (x1 == 6 && y1 == 0 && battleField[x1 - 1][y1] == 0 && battleField[x1][y1 + 1] == 0 && battleField[x1 - 1][y1 + 1] == 0){
            checkingCellsAround = true;
        }
        else if (x1 == 0 && y1 == 6 && battleField[x1 + 1][y1] == 0 && battleField[x1][y1 - 1] == 0 && battleField[x1 + 1][y1 - 1] == 0) {
            checkingCellsAround = true;
        }
        else if ( y1 !=0 && y1 != 6 && x1!= 0 && x1!= 6 && battleField[x1 + 1][y1] == 0 && battleField[x1][y1 + 1] == 0 && battleField[x1 - 1][y1] == 0 && battleField[x1 + 1][y1 - 1] == 0 && battleField[x1 - 1][y1 - 1] == 0 && battleField[x1 + 1][y1 + 1] == 0 && battleField[x1 - 1][y1 + 1] == 0 && battleField[x1][y1 - 1] == 0) {
            checkingCellsAround = true;
        }
        return checkingCellsAround;
    }
    static boolean puttingTrippleShip(int x1, int x2, int x3 , int y1, int y2 , int y3) {
        boolean checkingCellsAround = false;
        if (x1 == x2 && x1 == x3 && y1 == y2 - 1 && y2 == y3 - 1){
            checkingCellsAround = true;
        }
        else if (y1 == y2 && y2 == y3 && x1 == x2 - 1 && x3 == x2 + 1) {
            checkingCellsAround = true;
        }
        return checkingCellsAround;
    }
    static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}