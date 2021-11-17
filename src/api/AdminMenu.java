package api;

import model.Room;
import model.IRoom;
import model.RoomType;
import model.Customer;
import api.MainMenu;
import Service.ReservationService;

import java.util.*;

public class AdminMenu {
    private static AdminMenu adminMenu = null;
    public final static MainMenu mainMenu = MainMenu.getInstance4();
    private final static AdminResource adminResource = AdminResource.getInstance3();
    private final static HotelResource hotelResource = HotelResource.getInstance6();

    public void start() {

        boolean SystemRunning = true;

        try (Scanner scanner = new Scanner(System.in)) {
            while (SystemRunning) {
                try {
                    System.out.println("1. See all Customers");
                    System.out.println("2. See all Rooms");
                    System.out.println("3. See all Reservations");
                    System.out.println("4. Add a Room");
                    System.out.println("5. back to Main Menu");
                    int option = Integer.parseInt(scanner.next());

                    if (option == 1) {
                        System.out.println("< See all Customers >");
                        System.out.println("-----------------------------------------------------------");
                        Collection<Customer> customers = adminResource.getAllCustomers();
                        for (Customer customer : customers) {
                            System.out.println(customer);
                        }
                        System.out.println("-----------------------------------------------------------");
                    }
                    else if (option == 2) {
                        System.out.println("< See all Rooms >");
                        System.out.println("-----------------------------------------------------------");
                        Collection<IRoom> roomList = adminResource.getAllRooms();
                        for (IRoom room : roomList) {
                            System.out.println(room);
                        }
                        System.out.println("-----------------------------------------------------------");
                    }
                    else if (option == 3) {
                        System.out.println("< See all Reservations >");
                        System.out.println("-----------------------------------------------------------");
                        adminResource.displayAllReservations();
                        System.out.println("-----------------------------------------------------------");
                    }
                    else if (option == 4) {
                        System.out.println("< Add a Room >");
                        System.out.println("-----------------------------------------------------------");
                        boolean keepadding = true;
                        while (keepadding = true) {
                            System.out.println("Enter a Room Number: ");
                            Scanner userInput = new Scanner(System.in);
                            String newRoomNumber = userInput.next();
                            hotelResource.checkARoom(newRoomNumber);
                            System.out.println("Enter price per night: ");
                            Double newPrice = userInput.nextDouble();
                            System.out.println("Enter room type: 1 for single bed, 2 for double bed: ");
                            int inputRoomType = userInput.nextInt();
                            while (SystemRunning) {
                                if (inputRoomType == 1) {
                                    RoomType newRoomType = RoomType.SINGLE;
                                    boolean isFree = true;
                                    IRoom room = new Room(newRoomNumber, newPrice, newRoomType, isFree);
                                    List<IRoom> rooms = new ArrayList<>();
                                    rooms.add(room);
                                    adminResource.addRoom(rooms);
                                    break;
                                }
                                else if (inputRoomType == 2) {
                                    RoomType newRoomType = RoomType.DOUBLE;
                                    boolean isFree = true;
                                    IRoom room = new Room(newRoomNumber, newPrice, newRoomType, isFree);
                                    List<IRoom> rooms = new ArrayList<>();
                                    rooms.add(room);
                                    adminResource.addRoom(rooms);
                                    break;
                                } else {
                                    System.out.println("< Enter Number  1 or 2 >");
                                    inputRoomType = userInput.nextInt();
                                }
                            }
                            System.out.println("Would you like to add another room: Y/N?");
                            String input = userInput.next();
                            while (!input.isEmpty()) {
                                if (input.equalsIgnoreCase("Y")) {
                                    keepadding = true;
                                    break;
                                }
                                else if (input.equalsIgnoreCase("N")) {
                                    keepadding = false;
                                    break;
                                }
                                else if(input != "N" || input != "n" && input != "Y" || input != "y") {
                                    System.out.println("< Enter 'Y' or 'N' >");
                                    input = userInput.next();
                                }
                            }
                            if (input.equalsIgnoreCase("N")) {
                                keepadding = false;
                                break;
                            }
                        }
                    }
                    else if (option == 5) {
                        System.out.println("< Already back to the Main Menu >");
                        System.out.println("-----------------------------------------------------------");
                        mainMenu.start();
                    } else {
                        System.out.println("< Please select a number between 1 and 5 >");
                        System.out.println("-----------------------------------------------------------");
                    }
                } catch (Exception ex) {
                    System.out.println("< Invalid Input, please try again >");
                    System.out.println("-----------------------------------------------------------");
                }
            }
        }
    }

    public static void main(String[] args) {
        AdminMenu adminMenuObj = new AdminMenu();
        adminMenuObj.start();
    }

    public static AdminMenu getInstance5() {
        if(adminMenu == null) {
            adminMenu = new AdminMenu();
        }
        return adminMenu;
    }

}
