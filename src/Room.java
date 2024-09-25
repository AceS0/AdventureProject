public class Room {
    private String name;
    private String desc;
    private Room north;
    private Room south;
    private Room east;
    private Room west;
    public Room(String name, String desc){
        this.name = name;
        this.desc = desc;
    }

    private void MakeRoom(){

    }
    public String getCurrentRoomdesc(){
        return desc;
    }
    public void setRooms(Room north,Room south,Room east,Room west){
    this.north = north;
    this.south = south;
    this.
    }
    public void setCurrentRooms(Room currentRoom){
    this.currentRoom = currentRoom;
    }
}
