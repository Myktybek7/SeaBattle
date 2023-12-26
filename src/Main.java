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
        }
    }
}    