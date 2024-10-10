public class Adventure {
    private Player player = new Player("The player");

    public void start() {

        player.getDisplayMessage("Welcome to Escape Mason v5.0");

        boolean isRunning = true;

        player.getDisplayMessage(player.getCurrentRoomDescription());

        while (isRunning) {

            String input = player.getInput();

            if (input.equals("exit") || input.equals("quit")) {
                player.getDisplayMessage("Thanks for playing, have a good day!");
                isRunning = false;
            } else {
                player.handleUserInput(input);
            }
        }
    }
}
