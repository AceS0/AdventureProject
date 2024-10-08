import java.util.Scanner;

public class UI {
    private Scanner scanner;

    public UI() {
        this.scanner = new Scanner(System.in);
    }


    public void displayMessage(String message) {
        System.out.println(message);
    }

    public void displayMessageNoLN(String message) {
        System.out.print(message);
    }

    public String getInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public int getUserInputAsNumber() {
        while (true) {
            String input = scanner.nextLine();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                displayMessage("Please enter a valid number.");
            }
        }
    }

}

