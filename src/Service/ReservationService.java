package Service;

import java.util.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import api.AdminMenu;
import model.IRoom;
import model.Customer;
import model.Reservation;

public class ReservationService {
    public Collection<IRoom> roomList = new HashSet<>();
    public Collection<IRoom> availableRooms = new HashSet<>();
    public Collection<Reservation> reservations = new HashSet<>();
    private final static AdminMenu adminMenu = AdminMenu.getInstance5();

    private static ReservationService reservationService = null;

    public void addRoom(IRoom room) {
        roomList.add(room);
        System.out.println("Successful! ");
        System.out.println(room);
        System.out.println("-----------------------------------------------------------");
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

    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        Reservation reserved = new Reservation(customer, room, checkInDate, checkOutDate);
        reservations.add(reserved);
        return reserved;
    }

    //Reference findRooms: https://knowledge.udacity.com/questions/655317

    public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate) {
        if (reservations.isEmpty()) {
            availableRooms = roomList;
            return availableRooms;
        } else {
            for(IRoom room : roomList) {
                for (Reservation reservation : reservations) {
                    if ((room.getRoomNumber().equals(reservation.room.getRoomNumber())) && ((checkInDate.before(reservation.getCheckInDate()) && checkOutDate.before(reservation.getCheckInDate()))
                            || (checkInDate.after(reservation.getCheckOutDate()) && checkOutDate.after(reservation.getCheckOutDate())))
                            || (!reservation.room.getRoomNumber().contains(room.getRoomNumber()))) {
                        availableRooms.add(room);
                    } else if (room.getRoomNumber().equals(reservation.room.getRoomNumber())) {
                        availableRooms.remove(room);
                    }
                }
            }
        }
        return availableRooms;
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
