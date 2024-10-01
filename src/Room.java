import java.util.ArrayList;

public class Room {
    private String name;
    private String desc;
    private Room north;
    private Room south;
    private Room east;
    private Room west;
    private ArrayList<Item> items;

    public Room(String name, String desc) {
        this.name = name;
        this.desc = desc;
        this.items = new ArrayList<>();
    }

    public String getCurrentRoomdesc() {
        return desc;
    }

    public void addItem(Item item){
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
}
