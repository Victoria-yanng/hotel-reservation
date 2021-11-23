package api;

import model.*;
import model.Customer;
import api.AdminResource;
import Service.CustomerService;
import Service.ReservationService;

import java.util.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class MainMenu {
    private static MainMenu mainMenu = null;
    private final static AdminMenu adminMenu = AdminMenu.getInstance5();
    private final static HotelResource hotelResource = HotelResource.getInstance6();

    public void start() {
        try {
            boolean SystemRunning = true;

            while (SystemRunning) {
                Scanner scanner = new Scanner(System.in);

                System.out.println("1. Find and reserve a room");
                System.out.println("2. See my reservations");
                System.out.println("3. Create an account");
                System.out.println("4. Admin");
                System.out.println("5. Exit");
                int option = Integer.parseInt(scanner.next());

                switch (option) {
                    case 1:
                        System.out.println("< Find and reseve a room >");
                        System.out.println("-----------------------------------------------------------");
                        System.out.println("Enter CheckIn Date mm/dd/yyyy example  10/01/2022");
                        Scanner input = new Scanner(System.in);
                        Date inputCheckInDate = new SimpleDateFormat("MM/dd/yyyy").parse(input.next());
                        System.out.println("Enter CheckOut Date mm/dd/yyyy example 10/12/2022");
                        Date inputCheckOutDate = new SimpleDateFormat("MM/dd/yyyy").parse(input.next());
                        SimpleDateFormat date = new SimpleDateFormat("MM/dd/yyyy");
                        Calendar calendar = Calendar.getInstance();
                        Date today = date.parse(date.format(calendar.getTime()));

                        if (inputCheckInDate.equals(today) || inputCheckInDate.after(today) && inputCheckOutDate.after(inputCheckInDate)) {
                            Collection<IRoom> rooms = hotelResource.findARoom(inputCheckInDate, inputCheckOutDate);
                            for (IRoom room : rooms) {
                                System.out.println(room);
                            }
                            System.out.println("-----------------------------------------------------------");

                            if (rooms.isEmpty()) {
                                Calendar checkInDate = Calendar.getInstance();
                                Calendar checkOutDate = Calendar.getInstance();
                                checkInDate.setTime(inputCheckInDate);
                                checkInDate.add(Calendar.DAY_OF_YEAR, 7);
                                checkOutDate.setTime(inputCheckOutDate);
                                checkOutDate.add(Calendar.DAY_OF_YEAR, 7);
                                Date recommendCheckInDate = checkInDate.getTime();
                                Date recommendCheckOutdate = checkOutDate.getTime();

                                System.out.println("< No avaliable Rooms in this specific time >");
                                System.out.println("Recomend Rooms with Check-in and Chck-Out Date after 7 days");
                                System.out.println("-----------------------------------------------------------");
                                System.out.println("< recommend Check-in and Check-out Date >");
                                System.out.println("Check-in Date: " + recommendCheckInDate);
                                System.out.println("Check-out Date: " + recommendCheckOutdate);

                                System.out.println();
                                System.out.println("< Avaliable Rooms >");
                                Collection<IRoom> recommendRooms = hotelResource.findARoom(recommendCheckInDate, recommendCheckOutdate);
                                for (IRoom rm : recommendRooms) {
                                    System.out.println(rm);
                                }

                                System.out.println("-----------------------------------------------------------");

                                makeAReservation(recommendCheckInDate, recommendCheckOutdate);
                                break;
                            }

                        } else {
                            System.out.println("< Invalid input Date >");
                            System.out.println("-----------------------------------------------------------");
                            break;
                        }
                        System.out.println("Would you like to book a Room? Y/N");
                        String userOption = input.next();
                        while (!userOption.isEmpty()) {
                            if (userOption.equalsIgnoreCase("Y")) {
                                System.out.println("Do you have an Account with us? Y/N");
                                userOption = input.next();
                                while (!userOption.isEmpty()) {
                                    if (userOption.equalsIgnoreCase("Y")) {
                                        System.out.println("Enter Email format: name@doamin.com");
                                        String userEmail = input.next();
                                        if (hotelResource.getCustomer(userEmail) == null) {
                                            System.out.println("< No Customer found, please try again >");
                                            System.out.println("-----------------------------------------------------------");
                                            break;
                                        }
                                        System.out.println("What Room Number would you like to reserve?");
                                        String inputRoomNumber = input.next();
                                        IRoom room = hotelResource.getARoom(inputRoomNumber);
                                        if (room == null) {
                                            System.out.println("Wrong Room Number, try again");
                                            System.out.println("-----------------------------------------------------------");
                                            break;
                                        }
                                        hotelResource.bookARoom(userEmail, room, inputCheckInDate, inputCheckOutDate);
                                        System.out.println("Reservation has been successfully made!");
                                        System.out.println("-----------------------------------------------------------");
                                        hotelResource.getCustomersReservations(userEmail);
                                        break;
                                    } else if (userOption.equalsIgnoreCase("N")) {
                                        System.out.println("First Name: ");
                                        String userFirstName = input.next();
                                        System.out.println("Last Name: ");
                                        String userLastName = input.next();
                                        System.out.println("Email Address: ");
                                        String userEmail = input.next();
                                        hotelResource.checkCustomer(userEmail);
                                        hotelResource.createACustomer(userFirstName, userLastName, userEmail);
                                        //
                                        System.out.println("What Room Number would you like to reserve?");
                                        String inputRoomNumber = input.next();
                                        IRoom room = hotelResource.getARoom(inputRoomNumber);
                                        hotelResource.bookARoom(userEmail, room, inputCheckInDate, inputCheckOutDate);
                                        System.out.println("Reservation has been successfully made!");
                                        System.out.println("-----------------------------------------------------------");
                                        hotelResource.getCustomersReservations(userEmail);
                                        break;
                                    } else if (userOption != "N" || userOption != "n" && userOption != "Y" || userOption != "y") {
                                        System.out.println("< Enter 'Y' or 'N' >");
                                        userOption = input.next();
                                    }
                                }
                                break;
                            } else if (userOption.equalsIgnoreCase("N")) {
                                break;
                            } else if (userOption != "N" || userOption != "n" && userOption != "Y" || userOption != "y") {
                                System.out.println("< Enter 'Y' or 'N' >");
                                userOption = input.next();
                            }
                        }
                        break;
                    case 2:
                        System.out.println("< See my reservations >");
                        System.out.println("-----------------------------------------------------------");
                        System.out.println("Email Address: ");
                        Scanner userEmail = new Scanner(System.in);
                        String email = userEmail.next();
                        hotelResource.getCustomersReservations(email);
                        break;
                    case 3:
                        System.out.println("< Create an account >");
                        System.out.println("-----------------------------------------------------------");
                        System.out.println("First Name: ");
                        Scanner userInput = new Scanner(System.in);
                        String inputFirstName = userInput.next();
                        System.out.println("Last Name: ");
                        String inputLastName = userInput.next();
                        System.out.println("Email Address: ");
                        String inputEmail = userInput.next();
                        if(hotelResource.checkCustomer(inputEmail) != null) {
                            break;
                        }
                        hotelResource.createACustomer(inputFirstName, inputLastName, inputEmail);
                        break;
                    case 4:
                        System.out.println("< Already back to the Admin Menu >");
                        System.out.println("-----------------------------------------------------------");
                        adminMenu.start();
                        break;
                    case 5:
                        System.out.println("Successfully logging out!");
                        SystemRunning = false;
                        System.exit(0);
                        break;
                }
            }
        } catch(Exception e){
            System.out.println("< Invalid Input, please try again >");
            System.out.println("-----------------------------------------------------------");
            start();
        }
    }

    public static void main(String[] args) {
        MainMenu mainMenuObj = new MainMenu();
        mainMenuObj.start();
    }

    public static MainMenu getInstance4() {
        if(mainMenu == null) {
            mainMenu = new MainMenu();
        }
        return mainMenu;
    }

    public void makeAReservation(Date checkIn, Date checkOut) {
        Scanner input = new Scanner(System.in);
        System.out.println("Would you like to book a Room? Y/N");
        String userOption = input.next();
        while (!userOption.isEmpty()) {
            if (userOption.equalsIgnoreCase("Y")) {
                System.out.println("Do you have an Account with us? Y/N");
                userOption = input.next();
                while (!userOption.isEmpty()) {
                    if (userOption.equalsIgnoreCase("Y")) {
                        System.out.println("Enter Email format: name@doamin.com");
                        String userEmail = input.next();
                        if (hotelResource.getCustomer(userEmail) == null) {
                            System.out.println("< No Customer found, please try again >");
                            System.out.println("-----------------------------------------------------------");
                            break;
                        }
                        System.out.println("What Room Number would you like to reserve?");
                        String inputRoomNumber = input.next();
                        IRoom room = hotelResource.getARoom(inputRoomNumber);
                        if (room == null) {
                            System.out.println("Wrong Room Number, try again");
                            System.out.println("-----------------------------------------------------------");
                            break;
                        }
                        hotelResource.bookARoom(userEmail, room, checkIn, checkOut);
                        System.out.println("Reservation has been successfully made!");
                        System.out.println("-----------------------------------------------------------");
                        hotelResource.getCustomersReservations(userEmail);
                        break;
                    } else if (userOption.equalsIgnoreCase("N")) {
                        System.out.println("First Name: ");
                        String userFirstName = input.next();
                        System.out.println("Last Name: ");
                        String userLastName = input.next();
                        System.out.println("Email Address: ");
                        String userEmail = input.next();
                        hotelResource.checkCustomer(userEmail);
                        hotelResource.createACustomer(userFirstName, userLastName, userEmail);
                        //
                        System.out.println("What Room Number would you like to reserve?");
                        String inputRoomNumber = input.next();
                        IRoom room = hotelResource.getARoom(inputRoomNumber);
                        hotelResource.bookARoom(userEmail, room, checkIn, checkOut);
                        System.out.println("Reservation has been successfully made!");
                        System.out.println("-----------------------------------------------------------");
                        hotelResource.getCustomersReservations(userEmail);
                        break;
                    } else if (userOption != "N" || userOption != "n" && userOption != "Y" || userOption != "y") {
                        System.out.println("< Enter 'Y' or 'N' >");
                        userOption = input.next();
                    }
                }
                break;
            } else if (userOption.equalsIgnoreCase("N")) {
                break;
            } else if (userOption != "N" || userOption != "n" && userOption != "Y" || userOption != "y") {
                System.out.println("< Enter 'Y' or 'N' >");
                userOption = input.next();
            }
        }
    }

}