package Service;

import java.util.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import api.AdminMenu;
import api.MainMenu;
import model.IRoom;
import model.Customer;
import model.Reservation;

public class ReservationService {
    public Collection<IRoom> roomList = new HashSet<>();
    public Collection<Reservation> reservations = new HashSet<>();
    private final static AdminMenu adminMenu = AdminMenu.getInstance5();
    private final static MainMenu mainMenu = MainMenu.getInstance4();

    private static ReservationService reservationService = null;

    public void addRoom(IRoom room) {
        roomList.add(room);
        System.out.println("Successful! ");
        System.out.println(room);
        System.out.println("-----------------------------------------------------------");
    }

    //Reference findRooms: https://knowledge.udacity.com/questions/745156

    public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate) {
        Collection<IRoom> availableRooms = new ArrayList<>();
        for (IRoom room: roomList){
            if (!isRoomReserved(room, checkInDate, checkOutDate)){
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }

    private boolean isRoomReserved(IRoom room, Date checkInDate, Date checkOutDate) {
        if (reservations.isEmpty()) return false;
        for (Reservation reservation: reservations){
            IRoom reservedRoom = reservation.getRoom();
            if (reservedRoom.getRoomNumber().equals(room.getRoomNumber())){
                if (reservationConflictsWithRange(reservation, checkInDate, checkOutDate)){
                    return true;
                }
            }
        }
        return false;
    }

    private boolean reservationConflictsWithRange(Reservation reservation, Date checkInDate, Date checkOutDate){
        return !(checkOutDate.before(reservation.getCheckInDate()) || checkInDate.after(reservation.getCheckOutDate()));
    }

    public IRoom getARoom(String roomNumber) {
        for (IRoom room : roomList) {
            if (roomNumber.equals(room.getRoomNumber())) {
                return room;
            }
        }
        return null;
    }

    public IRoom checkARoom(String roomNumber) {
        for (IRoom room : roomList) {
            if (!roomNumber.equals(room.getRoomNumber())) {
                return room;
            } else {
                System.out.println("This Room has been added");
                System.out.println("-----------------------------------------------------------");
                adminMenu.start();
                return null;
            }
        }
        return null;
    }

    public boolean checkRoomNumber(String roomNumber) {
        for(IRoom room : mainMenu.getAvailableRoom) {
            if(room.getRoomNumber().contains(roomNumber)) {
                return true;
            }
            else {
                return false;
            }
        }
        return true;
    }

    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        Reservation reserved = new Reservation(customer, room, checkInDate, checkOutDate);
        reservations.add(reserved);
        return reserved;
    }

    public Collection<Reservation> getCustomerReservation(Customer customer) {
        Collection<Reservation> customerReservation = new HashSet<>();
        for(Reservation reservation : reservations) {
            if (customer.equals(reservation.getCustomer())){
                customerReservation.add(reservation);
            }
        }
        if (customerReservation.isEmpty()) {
            System.out.println("No reservation found");
            System.out.println("-----------------------------------------------------------");
        }
        for(Reservation res : customerReservation) {
            System.out.println(res);
            System.out.println("-----------------------------------------------------------");
        }
        return customerReservation;
    }

    public void printAllReservation() {
        for (Reservation printReservations : reservations){
            System.out.println(printReservations);
            System.out.println("-----------------------------------------------------------");
        }
        if (reservations.isEmpty()) {
            System.out.println("There are no Reservations added");
        }
    }

    //static Reference for ReservationService

    private ReservationService() {
    }

    public static ReservationService getInstance2() {
        if(reservationService == null) {
            reservationService = new ReservationService();
        }
        return reservationService;
    }

}
