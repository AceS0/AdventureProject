import java.util.ArrayList;

public class Player {
    private Room currentRoom;
    private Map map;
    private UI userInterface;
    private ArrayList<Item> inventory;
    private ArrayList<String> eatenItems;
    private ArrayList<Weapon> equippedItems;
    private Weapon activeWeapon;
    private int hp = 10;

    public Player(String name) {
        this.map = new Map();
        this.currentRoom = map.getCurrentRoom();
        this.inventory = new ArrayList<>();
        this.userInterface = new UI();
        this.eatenItems = new ArrayList<>();
        this.equippedItems = new ArrayList<>();
    }

    public String getCurrentRoomDescription() {
        StringBuilder description = new StringBuilder(currentRoom.getCurrentRoomdesc());
        ArrayList<Item> itemsInRoom = currentRoom.getItems();
        ArrayList<Enemy> enemiesInRoom = currentRoom.getEnemies();


        if (itemsInRoom.isEmpty()) {
            description.append("\nThere isn't any pickable items.");
        } else {
            description.append("\nItems in the room:");
            for (Item item : itemsInRoom) {
                description.append("\n- ").append(item.getItemName());
            }
        }
        if (enemiesInRoom.isEmpty()) {
            description.append("\nThere are no enemies in the room.");
        } else {
            description.append("\nEnemies in the room:");
            for (Enemy enemy : enemiesInRoom) {
                description.append("\n- ").append(enemy.getName()).append(": ").append(enemy.getDescription());
            }
        }
        return description.toString();
    }

    public void handleUserInput(String userInput) {
        String[] inputs = userInput.split(" ");
        String command = inputs[0];

        switch (command) {
            case "go north", "north", "n" -> moves("north");
            case "go south", "south", "s" -> moves("south");
            case "go east", "east", "e" -> moves("east");
            case "go west", "west", "w" -> moves("west");
            case "look", "l" -> userInterface.displayMessage("Looking around: " + getCurrentRoomDescription());
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
            case "inventory", "inv" -> showInventory();
            case "health", "hp" -> healthPoint();
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
            case "switchweapon", "chooseweapon" -> chooseWeapon();
            case "attack" -> {
                if (inputs.length > 1) {
                    attack(inputs[1]);
                } else {
                    userInterface.displayMessage("Which enemy do you want to attack?");
                }
            }
            case "help" -> userInterface.displayMessage("Here is a commandlist:\n" +
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
                    "-[attack] attacks an enemy.\n" +
                    "-[switchweapon, chooseweapon] switches between equipped weapons.\n" +
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

    public void adjustHp(int amount) {
        hp += amount;
        userInterface.displayMessage("Your HP is now " + hp + ".");
    }

    public void adjustHpfight(int amount) {
        hp += amount;
        if (hp <= 0) {
            hp = 0;
        }
    }

    private void checkDeath() {
        if (hp <= 0) {
            die();
        }
    }

    private void die() {
        userInterface.displayMessage("You have died. Game over.");
        System.exit(0); // Ends the game
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
            default ->
                    userInterface.displayMessage("You ate " + itemName.toLowerCase() + "\nBut it doesn't provide any benefit.");
        }
    }

    public void equip(String itemName) {
        Weapon weaponToEquip = null;

        for (Item item : inventory) {
            if (item instanceof Weapon && item.getItemName().equalsIgnoreCase(itemName)) {
                weaponToEquip = (Weapon) item;
                break;
            }
        }

        if (weaponToEquip == null) {
            Item itemInRoom = currentRoom.getItemByName(itemName);
            if (itemInRoom instanceof Weapon) {
                weaponToEquip = (Weapon) itemInRoom;
                currentRoom.removeItem(itemInRoom);
                inventory.add(weaponToEquip);
                userInterface.displayMessage("You picked up the " + itemName + " from the room.");
            }
        }

        if (weaponToEquip != null) {
            equippedItems.add(weaponToEquip);
            inventory.remove(weaponToEquip);
            userInterface.displayMessage("You equipped the " + itemName + ".");

            if (activeWeapon == null) {
                activeWeapon = weaponToEquip;
                userInterface.displayMessage(itemName + " is now your active weapon.");
            }
        } else {
            userInterface.displayMessage(itemName + " isn't a weapon or isn't available.");
        }
    }

    public void chooseWeapon() {
        if (equippedItems.isEmpty()) {
            userInterface.displayMessage("You don't have any equipped weapons.");
            return;
        }

        userInterface.displayMessage("Choose a weapon to make active:");
        for (int i = 0; i < equippedItems.size(); i++) {
            userInterface.displayMessage((i + 1) + ". " + equippedItems.get(i).getItemName());
        }

        userInterface.displayMessageNoLN("Type your number here: ");
        int choice = userInterface.getUserInputAsNumber() - 1;
        if (choice >= 0 && choice < equippedItems.size()) {
            activeWeapon = equippedItems.get(choice);
            userInterface.displayMessage("You switched to " + activeWeapon.getItemName() + " as your active weapon.");
        } else {
            userInterface.displayMessage("Invalid choice. Please select a valid weapon.");
        }
    }

    public void showEquippedItems() {
        if (equippedItems.isEmpty()) {
            userInterface.displayMessage("You have no equipped weapons.");
        } else {
            userInterface.displayMessage("Equipped weapons:");
            for (Weapon weapon : equippedItems) {
                userInterface.displayMessage("- " + weapon.getItemName());
            }
        }
    }

    public void attack(String enemyName) {
        if (activeWeapon == null) {
            userInterface.displayMessage("You have no equipped weapon. Attack failed.");
            return;
        }

        Enemy targetEnemy = null;

        if (enemyName != null && !enemyName.isEmpty()) {
            targetEnemy = currentRoom.getEnemyByName(enemyName);
        } else {
            targetEnemy = currentRoom.getNearestEnemy();
        }

        if (targetEnemy == null) {
            userInterface.displayMessage("There's no enemy to attack.");
            return;
        }

        if (activeWeapon instanceof RangedWeapon) {
            RangedWeapon rangedWeapon = (RangedWeapon) activeWeapon;
            if (rangedWeapon.remainingUses() <= 0) {
                userInterface.displayMessage("Your " + activeWeapon.getItemName() + " is out of ammo. Attack failed.");
                return;
            }
        }

        targetEnemy.hit(activeWeapon.getDamage());
        userInterface.displayMessage("You attacked " + targetEnemy.getName() + " for " + activeWeapon.getDamage() + " damage.");

        if (targetEnemy.isAlive()) {
            targetEnemy.attack(this);
            userInterface.displayMessage("Your HP is now " + hp + ".");
            checkDeath();
        } else {
            userInterface.displayMessage(targetEnemy.getName() + " has been defeated!");
            currentRoom.removeEnemy(targetEnemy);
            currentRoom.addItem(targetEnemy.dropWeapon());
        }
    }

    protected void getDisplayMessage(String message) {
        userInterface.displayMessage(message);
    }

    protected String getInput() {
        return userInterface.getInput("Type your command: ").toLowerCase();
    }

    private void showEatenItems() {
        if (eatenItems.isEmpty()) {
            userInterface.displayMessage("You haven't eaten anything yet.");
        } else {
            userInterface.displayMessage("You have eaten:");
            for (String item : eatenItems) {
                userInterface.displayMessage("- " + item);
            }
        }
    }


}
