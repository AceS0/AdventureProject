import java.util.Scanner;

public class Adventure {
    private Room currentRoom;

    public Adventure(){
        Room room1 = new Room("Room 1","You are in a room that resembles a forest\nThe forest is dark with a light pole and there is some trees");
        Room room2 = new Room("Room 2", "You are in the cabin\nThe cabin is warm with a lamp, there is some papers");
        Room room3 = new Room("Room 3", "You are in the hall that is connected to the cabin\nThe hall is cold, but there is some light at the end");
        Room room4 = new Room("Room 4", "You are in a room\nThere is a flashlight on the floor, there is also a pen");
        Room room5 = new Room("Room 5", "Congratulations you came out of the ship\nYou're outside of the ship, there is low gravity and some people");
        Room room6 = new Room("Room 6", "You are in the bedroom\nThere is chips and a bed");
        Room room7 = new Room("Room 7","You're in a blank room\nThis room is empty.");
        Room room8 = new Room("Room 8","You are in a hallway\nThere is some light outside of the door.");
        Room room9 = new Room("Room 9", "This is a very reflective room, there is some metal.");

        this.currentRoom = room1;

        room1.setRoomBehind(room2);

    }
    public void directions(){
        System.out.println("Welcome to The Rooms 1.0");

        boolean run = false;
        while (!run) {
            Scanner input = new Scanner(System.in);
            System.out.print("Type here: ");
            String userInput = input.nextLine();
            switch (userInput.toLowerCase()) {
                case "go north", "north", "n" -> {
                    System.out.println("going north");
                }
                case "go south", "south", "s" -> {
                    System.out.println("going south");
                }
                case "go east", "east", "e" -> {
                    System.out.println("going east");
                }
                case "go west", "west", "w" -> {
                    System.out.println("going west");
                }
                case "look","l" -> {
                    System.out.println("Looking around");
                    System.out.println(currentRoom.getCurrentRoomdesc());
                }
                default -> System.out.println("Uknown request, please try again");
            }
        /*if (userInput.equalsIgnoreCase("go north") || userInput.equalsIgnoreCase("north") || userInput.equalsIgnoreCase("n")) {
            System.out.println("going north");
        }else if (userInput.equalsIgnoreCase("go south") || userInput.equalsIgnoreCase("south") || userInput.equalsIgnoreCase("s")) {
            System.out.println("going south");
        } else {
            System.out.println("ERROR");
        }*/
        }
    }

}
