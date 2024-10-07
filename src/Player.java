import java.util.ArrayList;

public class Player {
    private Room currentRoom;
    private Map map;
    private UI userInterface;
    private ArrayList<Item> inventory;
    private ArrayList<String> eatenItems;
    private ArrayList<String> equippedItems;
    private int hp = 10;

    public Player() {
        this.map = new Map();
        this.currentRoom = map.getCurrentRoom();
        this.inventory = new ArrayList<>();
        this.userInterface = new UI();
        this.eatenItems = new ArrayList<>();
        this.equippedItems = new ArrayList<>();
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
            case "look","l" -> userInterface.displayMessage("Looking around: " + getCurrentRoomDescription());
            case "take" -> {
                if (inputs.length > 1) {
                    takeItem(inputs[1]);
                } else {
                    userInterface.displayMessage("What do you want to take?");
                }
            }
            case "drop" -> {
                if (inputs.length > 1) {
                    dropItem(inputs[1]);
                } else {
                    userInterface.displayMessage("What do you want to drop?");
                }
            }
            case "inventory","inv" -> showInventory();
            case "health","hp" -> healthPoint();
            case "eat" -> {
                if (inputs.length > 1) {
                    eat(inputs[1]);
                } else {
                    userInterface.displayMessage("What do you want to eat?");
                }
            }
            case "consumed" -> showEatenItems();
            case "equip" -> {
                if (inputs.length > 1) {
                    equip(inputs[1]);
                } else {
                    userInterface.displayMessage("What do you want to equip?");
                }
            }
            case "equipped" -> showEquippedItems();
            case "help" ->
                userInterface.displayMessage("Here is a commandlist:\n" +
                        "-[go north, north, n] to move north.\n" +
                        "-[go south, south, s] to move south.\n" +
                        "-[go east, east, e] to move east.\n" +
                        "-[go west, west, w] to move west.\n" +
                        "-[look, l] to look around.\n" +
                        "-[take] takes an item.\n" +
                        "-[drop] drops an item.\n" +
                        "-[inventory, inv] checks your inventory.\n" +
                        "-[health, hp] checks your hp.\n" +
                        "-[eat] eats an edible food.\n" +
                        "-[consumed] shows consumed food.\n" +
                        "-[equip] equips a weapon.\n" +
                        "-[equipped] shows equipped weapons.\n" +
                        "-[help] views the commandlist again.");

            default -> userInterface.displayMessage("Unknown command. Please try again.");
        }
    }

    private void moves(String direction) {
        Room nextRoom = currentRoom.getRooms(direction);
        if (nextRoom != null) {
            currentRoom = nextRoom;
            userInterface.displayMessage("You move " + direction + ".");
            userInterface.displayMessage(getCurrentRoomDescription());
            map.moveNextRoom(currentRoom);
        } else {
            userInterface.displayMessage("You can't go that way.");
        }
    }

    public void takeItem(String itemName) {
        Item itemToTake = currentRoom.getItemByName(itemName);
        if (itemToTake != null) {
            inventory.add(itemToTake);
            currentRoom.removeItem(itemToTake);
            userInterface.displayMessage("You took the " + itemName + ".");
        } else {
            userInterface.displayMessage("There is no " + itemName + " in the room.");
        }
    }

    private void showInventory() {
        if (inventory.isEmpty()) {
            userInterface.displayMessage("Your inventory is empty.");
        } else {
            userInterface.displayMessage("You have the following items:");
            for (Item item : inventory) {
                userInterface.displayMessage("- " + item.getItemName());
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
            userInterface.displayMessage("You dropped the " + itemToDrop.getItemName() + ".");
        } else {
            userInterface.displayMessage("You don't have that item.");
        }
    }

    private void healthPoint() {
        if (hp < 20) {
            userInterface.displayMessage("Your hp is " + hp + "%\nYou shouldn't fight in this stage.");
        } else if (hp < 50) {
            userInterface.displayMessage("Your hp is " + hp + "%\nYou can fight in this stage, but it's better for you to avoid it");
        } else {
            userInterface.displayMessage("Your hp is " + hp + "%\nYou can fight anytime you want.");
        }
    }

    private void adjustHp(int amount) {
        hp += amount;
        userInterface.displayMessage("Your HP is now " + hp + ".");
    }

    public void eat(String itemName) {
        if (eatItemFromInventory(itemName)) {
            return;
        }


        Item itemToEat = currentRoom.getItemByName(itemName);
        if (itemToEat != null) {
            if (itemToEat instanceof Food) {
                currentRoom.removeItem(itemToEat);
                consumeFood(itemName);
            } else {
                userInterface.displayMessage(itemName + " isn't edible.");
            }
        } else {
            userInterface.displayMessage("There is no " + itemName + " in the room.");
        }
    }

    private boolean eatItemFromInventory(String itemName) {
        Item itemToEat = null;
        for (Item item : inventory) {
            if (item.getItemName().equalsIgnoreCase(itemName)) {
                itemToEat = item;
                break;
            }
        }

        if (itemToEat != null) {
            if (itemToEat instanceof Food) {
                inventory.remove(itemToEat);
                consumeFood(itemName);
                return true;
            } else {
                userInterface.displayMessage(itemName + " isn't edible.");
                return true;
            }
        }
        return false;
    }

    private void consumeFood(String itemName) {
        eatenItems.add(itemName);
        switch (itemName.toLowerCase()) {
            case "apple" -> adjustHp(10);
            case "banana" -> adjustHp(15);
            case "strawberry" -> adjustHp(5);
            default -> userInterface.displayMessage("You ate " +itemName.toLowerCase() + "\nBut it doesn't provide any benefit.");
        }
    }

    public void equip(String itemName) {
        if (equipItemFromInventory(itemName)) {
            return;
        }

        Item itemToEquip = currentRoom.getItemByName(itemName);
        if (itemToEquip != null) {
            if (itemToEquip instanceof Weapon) {
                equippedItems.add(itemToEquip.getItemName());
                inventory.remove(itemToEquip);
                userInterface.displayMessage("You equipped the " + itemName + ".");
            } else {
                userInterface.displayMessage(itemName + "isn't equipable.");
            }
        } else {
            userInterface.displayMessage("There is no " +itemName + " in the room.");
        }
    }

    private boolean equipItemFromInventory(String itemName){
        Item itemToEquip = null;
        for (Item item : inventory) {
            if (item.getItemName().equalsIgnoreCase(itemName)) {
                itemToEquip = item;
                break;
            }
        }

        if (itemToEquip != null) {
            if (itemToEquip instanceof Weapon) {
                equippedItems.add(itemToEquip.getItemName());
                inventory.remove(itemToEquip);
                userInterface.displayMessage("You equipped the " + itemName + ".");
                return true;
            } else {
                userInterface.displayMessage(itemName + " isn't equipable.");
                return true;
            }
        }
        return false;
    }

    public void showEatenItems() {
        if (eatenItems.isEmpty()) {
            userInterface.displayMessage("You haven't consumed anything yet.");
        } else {
            userInterface.displayMessage("You've consumed the following items:");
            for (String item : eatenItems) {
                userInterface.displayMessage("- " +item);
            }
        }
    }

    public void showEquippedItems() {
        if (equippedItems.isEmpty()) {
            userInterface.displayMessage("You haven't equipped anything yet.");
        } else {
            userInterface.displayMessage("You've equipped the following items:");
            for (String item : equippedItems) {
                userInterface.displayMessage("- "+item);
            }
        }
    }

}
