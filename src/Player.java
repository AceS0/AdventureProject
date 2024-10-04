import java.util.ArrayList;

public class Player {
    private Room currentRoom;
    private Map map;
    private ArrayList<Item> inventory;
    private int hp = 10;

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
        String[] inputs = userInput.split(" ");
        String command = inputs[0];

        switch (command) {
            case "go north", "north", "n" -> moves("north");
            case "go south", "south", "s" -> moves("south");
            case "go east", "east", "e" -> moves("east");
            case "go west", "west", "w" -> moves("west");
            case "look","l" -> System.out.println("Looking around: " + getCurrentRoomDescription());
            case "take" -> {
                if (inputs.length > 1) {
                    takeItem(inputs[1]);
                } else {
                    System.out.println("What do you want to take?");
                }
            }
            case "drop" -> {
                if (inputs.length > 1) {
                    dropItem(inputs[1]);
                } else {
                    System.out.println("What do you want to drop?");
                }
            }
            case "inventory","inv" -> showInventory();
            case "health","hp" -> healthPoint();
            case "eat" -> {
                if (inputs.length > 1) {
                    eat(inputs[1]);
                } else {
                    System.out.println("What do you want to eat?");
                }
            }
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

    private void healthPoint() {
        if (hp < 20) {
            System.out.println("Your hp is " + hp + "%\nYou shouldn't fight in this stage.");
        } else if (hp < 50) {
            System.out.println("Your hp is " + hp + "%\nYou can fight in this stage, but it's better for you to avoid it");
        } else {
            System.out.println("Your hp is " + hp + "%\nYou can fight anytime you want.");
        }
    }

    private void adjustHp(int amount) {
        hp += amount;
        System.out.println("Your HP is now " + hp + ".");
    }

    public void eat(String itemName) {
        if (eatItemFromInventory(itemName)) {
        } else if (eatItem(itemName)) {
        } else {
            System.out.println("There is no " + itemName + " in the room.");
        }
    }

    private boolean eatItemFromInventory(String itemName) {
        Item itemToEat = null;
        for (Item item : inventory) {
            if (item.getItemName().equalsIgnoreCase(itemName) && item instanceof Food) {
                itemToEat = item;
                break;
            }
        }

        if (itemToEat != null) {
            inventory.remove(itemToEat);
            consumeFood(itemName);
            return true;
        }
        return false;
    }

    private boolean eatItem(String itemName) {
        Item itemToEat = currentRoom.getItemByName(itemName);
        if (itemToEat instanceof Food) {
            currentRoom.removeItem(itemToEat);
            consumeFood(itemName);
            return true;
        }
        return false;
    }

    private void consumeFood(String itemName) {
        switch (itemName.toLowerCase()) {
            case "apple" -> adjustHp(10);
            case "banana" -> adjustHp(15);
            case "strawberry" -> adjustHp(5);
            default -> System.out.println("This food doesn't provide any benefit.");
        }
    }

}
