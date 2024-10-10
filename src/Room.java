import java.util.ArrayList;

public class Room {
    private String name;
    private String desc;
    private Room north;
    private Room south;
    private Room east;
    private Room west;
    private ArrayList<Item> items;
    private ArrayList<Enemy> enemies;

    public Room(String name, String desc) {
        this.name = name;
        this.desc = desc;
        this.items = new ArrayList<>();
        this.enemies = new ArrayList<>();
    }

    public String getCurrentRoomdesc() {
        return desc;
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void setRooms(Room north, Room south, Room east, Room west) {
        this.north = north;
        this.south = south;
        this.east = east;
        this.west = west;
    }

    public Room getRooms(String direction) {
        return switch (direction) {
            case "north" -> north;
            case "south" -> south;
            case "east" -> east;
            case "west" -> west;
            default -> null;
        };
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public Item getItemByName(String name) {
        for (Item item : items) {
            if (item.getItemName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }

    public void removeItem(Item item) {
        items.remove(item);
    }

    public void addEnemy(Enemy enemy) {
        enemies.add(enemy);
    }


    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public void removeEnemy(Enemy enemy) {
        enemies.remove(enemy);
    }

    public Enemy getEnemyByName(String name) {
        for (Enemy enemy : enemies) {
            if (enemy.getName().equalsIgnoreCase(name)) {
                return enemy;
            }
        }
        return null;
    }

    public Enemy getNearestEnemy() {
        if (!enemies.isEmpty()) {
            return enemies.get(0);
        }
        return null;
    }
}
