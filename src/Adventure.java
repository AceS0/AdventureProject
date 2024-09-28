public class Adventure {
    private Player player;
    private Map map;
    private UI userInterface;

    public Adventure() {
        this.map = new Map();
        this.player = new Player(map);
        this.userInterface = new UI();
    }

    public void start() {

        userInterface.displayMessage("Welcome to the Rooms 2.2");

        boolean isRunning = true;

        userInterface.displayMessage(player.getCurrentRoomDescription());

        while (isRunning) {

            String input = userInterface.getInput("Tyoe your command: ").toLowerCase();

            if (input.equals("exit") || input.equals("quit")) {
                userInterface.displayMessage("Thanks for playing, have a good day!");
                isRunning = false;
            } else {
                player.handleUserInput(input);
            }
        }
    }
}
