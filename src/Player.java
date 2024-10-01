import java.util.ArrayList;

public class Player {
    private Room currentRoom;
    private Map map;
    private ArrayList<Item> inventory;

    public Player() {
        this.map = new Map();
        this.currentRoom = map.getCurrentRoom();
        this.inventory = new ArrayList<>();
    }

    public String getCurrentRoomDescription() {
        String description = currentRoom.getCurrentRoomdesc();
        ArrayList<Item> itemsInRoom = currentRoom.getItems();

        if (itemsInRoom.isEmpty()) {
            description += "\nThere isn't any pickable items.";
        } else {
            description += "\nItems in the room:";
            for (Item item : itemsInRoom) {
                description += "\n- " + item.getItemName();
            }
        }
        return description;
    }
    public void handleUserInput(String userInput) {
        String[] inputParts = userInput.split(" ");
        String command = inputParts[0];

        switch (command) {
            case "go north", "north", "n" -> moves("north");
            case "go south", "south", "s" -> moves("south");
            case "go east", "east", "e" -> moves("east");
            case "go west", "west", "w" -> moves("west");
            case "look","l" -> System.out.println("Looking around: " + getCurrentRoomDescription());
            case "take" -> {
                if (inputParts.length > 1) {
                    takeItem(inputParts[1]);
                } else {
                    System.out.println("What do you want to take?");
                }
            }
            case "drop" -> {
                if (inputParts.length > 1) {
                    dropItem(inputParts[1]);
                } else {
                    System.out.println("What do you want to drop?");
                }
            }
            case "inventory","inv" -> showInventory();
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

    public void takeItem(String itemName) {
        Item itemToTake = currentRoom.getItemByName(itemName);
        if (itemToTake != null) {
            inventory.add(itemToTake);
            currentRoom.removeItem(itemToTake);
            System.out.println("You took the " + itemName + ".");
        } else {
            System.out.println("There is no " + itemName + " in the room.");
        }
    }

    private void showInventory() {
        if (inventory.isEmpty()) {
            System.out.println("Your inventory is empty.");
        } else {
            System.out.println("You have the following items:");
            for (Item item : inventory) {
                System.out.println("- " + item.getItemName());
            }
        }
    }
    private void dropItem(String itemName) {
        Item itemToDrop = null;
        for (Item item : inventory) {
            if (item.getItemName().equalsIgnoreCase(itemName)) {
                itemToDrop = item;
                break;
            }
        }
        if (itemToDrop != null) {
            inventory.remove(itemToDrop);
            currentRoom.addItem(itemToDrop);
            System.out.println("You dropped the " + itemToDrop.getItemName() + ".");
        } else {
            System.out.println("You don't have that item.");
        }
    }

}
