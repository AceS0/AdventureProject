public class Map {
    private Room currentRoom;

    public Map(){
        Room room1 = new Room("Room 1","You are in a room that resembles a forest\nThe forest is dark with a light pole and there are some trees");
        Room room2 = new Room("Room 2", "You are in the cabin\nThe cabin is warm with lights turned on, and there is something to write on");
        Room room3 = new Room("Room 3", "You are in the hall that is connected to the cabin\nThe hall is cold, but there is some light at the end");
        Room room4 = new Room("Room 4", "You are in a room\nThere is something on the floor.");
        Room room5 = new Room("Room 5", "Congratulations, you came out of the ship\nYou're outside of the ship, there is low gravity and some people");
        Room room6 = new Room("Room 6", "You are in the bedroom\nThere is a bed");
        Room room7 = new Room("Room 7", "You're in a storage room\nThis room has some stuff.");
        Room room8 = new Room("Room 8", "You are in a hallway\nThere is some light outside of the door.");
        Room room9 = new Room("Room 9", "This is a very reflective room, there is something on the wall");

        Item lamp = new Item("Lamp");
        Item papers = new Item("Papers");
        Item flashlight = new Item("Flashlight");
        Item pen = new Item("Pen");
        Item metal = new Item("Metal");

        room2.addItem(lamp);
        room2.addItem(papers);
        room4.addItem(flashlight);
        room4.addItem(pen);
        room9.addItem(metal);

        Item apple = new Food("Apple");
        Item banana = new Food("Banana");
        Item strawberry = new Food("Strawberry");
        Item chips = new Food("Chips");

        room1.addItem(apple);
        room8.addItem(banana);
        room4.addItem(strawberry);
        room6.addItem(chips);

        Item sword = new Weapon("Sword");
        Item bow = new Weapon("Bow");
        Item axe = new Weapon("Axe");

        room3.addItem(sword);
        room8.addItem(bow);
        room7.addItem(axe);


        this.currentRoom = room1;

        room1.setRooms(null, room4, room2, null);
        room2.setRooms(null, null, room3, room1);
        room3.setRooms(null, room6, null, room2);
        room4.setRooms(room1, room7, null, null);
        room5.setRooms(null, room8, null, null);
        room6.setRooms(room3, room9, null, null);
        room7.setRooms(room4, null, room8, null);
        room8.setRooms(room5, null, room9, room7);
        room9.setRooms(room6, null, null, room8);
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }
    public void moveNextRoom(Room nextRoom){
        this.currentRoom = nextRoom;
    }
}
