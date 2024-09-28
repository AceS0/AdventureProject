public class Player {
    private Room currentRoom;
    private Map map;

    public Player(Map map) {
        this.map = map;
        this.currentRoom = map.getCurrentRoom();
    }

    public String getCurrentRoomDescription() {
        return currentRoom.getCurrentRoomdesc();
    }
    public void handleUserInput(String userInput) {
        switch (userInput) {
            case "go north", "north", "n" -> moves("north");
            case "go south", "south", "s" -> moves("south");
            case "go east", "east", "e" -> moves("east");
            case "go west", "west", "w" -> moves("west");
            case "look", "l" -> System.out.println("Looking around: " + getCurrentRoomDescription());
            default -> System.out.println("Unknown command. Please try again.");
        }
    }
    private void moves(String direction) {
        Room nextRoom = currentRoom.getRooms(direction);
        if (nextRoom != null) {
            currentRoom = nextRoom;
            System.out.println("You move " + direction + ".");
            System.out.println(getCurrentRoomDescription());
            map.moveNextRoom(currentRoom);
        } else {
            System.out.println("You can't go that way.");
        }
    }
}
