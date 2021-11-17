package api;

import java.util.*;
import model.IRoom;
import model.Customer;
import model.Reservation;
import Service.CustomerService;
import Service.ReservationService;

public class AdminResource {
    public static AdminResource adminResource = null;
    private CustomerService customerService = CustomerService.getInstance();
    private ReservationService reservationService = ReservationService.getInstance2();

    public Customer getCustomer(String email) {
        for(Customer customer : customerService.customers) {
            if(email.equals(customer.getEmail())) {
                return customer;
            } else {
                System.out.println("No Reservation found");
                return null;
            }
        } return null;
    }

    public void addRoom(List<IRoom> rooms) {
        for (IRoom room : rooms){
            reservationService.addRoom(room);
        }
    }

    public Collection<IRoom> getAllRooms() {
        if(reservationService.roomList.isEmpty()) {
            System.out.println("There are no Rooms added");
        }
        return reservationService.roomList;
    }

    public Collection<Customer> getAllCustomers() {
        if (customerService.customers.isEmpty()) {
            System.out.println("There are no Customers added");
        }
        return customerService.getAllCustomers();
    }

    public void displayAllReservations() {
        reservationService.printAllReservation();
    }

    public static AdminResource getInstance3() {
        if (null == adminResource) {
            adminResource = new AdminResource();
        }
        return adminResource;
    }

}
