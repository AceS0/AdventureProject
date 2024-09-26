import java.util.Scanner;

public class UI {
        private Adventure adventure;
        private Scanner scanner;

        public UI() {
            this.adventure = new Adventure();
            this.scanner = new Scanner(System.in);
        }

        public void startGame() {
            System.out.println("Welcome to The Rooms 1.0");
            System.out.println(adventure.getCurrentRoomDescription());
            while (true) {
                System.out.print("Type a command: ");
                String userInput = scanner.nextLine().toLowerCase();

                if (userInput.equals("quit")) {
                    System.out.println("Thanks for playing!");
                    break;
                }

                handleUserInput(userInput);
            }
        }

        private void handleUserInput(String userInput) {
            switch (userInput) {
                case "go north", "north", "n" -> move("north");
                case "go south", "south", "s" -> move("south");
                case "go east", "east", "e" -> move("east");
                case "go west", "west", "w" -> move("west");
                case "look", "l" -> System.out.println("Looking around: " + adventure.getCurrentRoomDescription());
                default -> System.out.println("Unknown command. Please try again.");
            }
        }

        private void move(String direction) {
            if (adventure.move(direction)) {
                System.out.println("You move " + direction + ".");
            } else {
                System.out.println("You can't go that way.");
            }
        }
}
