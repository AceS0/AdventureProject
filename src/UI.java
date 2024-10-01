import java.util.Scanner;

public class UI {
        private Scanner scanner;

        public UI() {
            this.scanner = new Scanner(System.in);
        }


        public void displayMessage(String message) {
            System.out.println(message);
        }

        public String getInput(String prompt) {
            System.out.print(prompt);
            return scanner.nextLine();
        }
}

