public class Room {
    private String name;
    private String desc;
    private Room north;
    private Room south;
    private Room east;
    private Room west;

    public Room(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    public String getCurrentRoomdesc() {
        return desc;
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
}
