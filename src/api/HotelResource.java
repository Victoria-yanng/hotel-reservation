package api;

import model.IRoom;
import model.Customer;
import model.Reservation;
import Service.CustomerService;
import Service.ReservationService;

import java.util.*;

public class HotelResource {
    private CustomerService customerService = CustomerService.getInstance();
    private ReservationService reservationService = ReservationService.getInstance2();

    private static HotelResource hotelResource = null;

    public Customer getCustomer(String Email) {
        return customerService.getCustomer(Email);
    }

    public Customer checkCustomer(String Email) {
        return customerService.checkCustomer(Email);
    }

    public void createACustomer(String firstName, String lastName, String email) {
        customerService.addCustomer(firstName, lastName, email);
        System.out.println("Your Account has been successfully created!");
        System.out.println("-----------------------------------------------------------");
    }

    public IRoom getARoom(String roomNumber) {
        return reservationService.getARoom(roomNumber);
    }

    public IRoom checkARoom(String roomNumber) {
        return reservationService.checkARoom(roomNumber);
    }

    public Reservation bookARoom(String customerEmail, IRoom room, Date checkInDate, Date checkOutDate) {
        Customer customer = customerService.getCustomer(customerEmail);
        return reservationService.reserveARoom(customer, room, checkInDate, checkOutDate);
    }

    public Collection<Reservation> getCustomersReservations(String customerEmail) {
        Customer customer = customerService.getCustomer(customerEmail);
        return reservationService.getCustomerReservation(customer);
    }

    public Collection<IRoom> findARoom(Date checkInDate, Date checkOutDate) {
        return reservationService.findRooms(checkInDate, checkOutDate);
    }

    public static HotelResource getInstance6() {
        if(hotelResource == null) {
            hotelResource = new HotelResource();
        }
        return hotelResource;
    }

}