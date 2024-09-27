public class Adventure {
    private Room currentRoom;

    public Adventure() {
        Room room1 = new Room("Room 1","You are in a room that resembles a forest\nThe forest is dark with a light pole and there are some trees");
        Room room2 = new Room("Room 2", "You are in the cabin\nThe cabin is warm with a lamp, and there are some papers");
        Room room3 = new Room("Room 3", "You are in the hall that is connected to the cabin\nThe hall is cold, but there is some light at the end");
        Room room4 = new Room("Room 4", "You are in a room\nThere is a flashlight on the floor, and there is also a pen");
        Room room5 = new Room("Room 5", "Congratulations, you came out of the ship\nYou're outside of the ship, there is low gravity and some people");
        Room room6 = new Room("Room 6", "You are in the bedroom\nThere are chips and a bed");
        Room room7 = new Room("Room 7", "You're in a blank room\nThis room is empty.");
        Room room8 = new Room("Room 8", "You are in a hallway\nThere is some light outside of the door.");
        Room room9 = new Room("Room 9", "This is a very reflective room, there is some metal.");

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

    public String getCurrentRoomDescription() {
        return currentRoom.getCurrentRoomdesc();
    }

    public boolean move(String direction) {
        Room nextRoom = currentRoom.getRooms(direction);
        if (nextRoom != null) {
            currentRoom = nextRoom;
            return true;
        }
        return false;
    }
}
