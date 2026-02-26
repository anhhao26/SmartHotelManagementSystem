package com.smarthotel.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BookingID")
    private Integer bookingID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RoomID", referencedColumnName = "RoomID")
    private Room room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CustomerID", referencedColumnName = "CustomerID")
    private Customer customer;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CheckInDate")
    private Date checkInDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CheckOutDate")
    private Date checkOutDate;

    @Column(name = "Status")
    private String status;

    @Column(name = "TotalAmount")
    private Double totalAmount;

    // getters/setters
    public Integer getBookingID() { return bookingID; }
    public void setBookingID(Integer bookingID) { this.bookingID = bookingID; }
    public Room getRoom() { return room; }
    public void setRoom(Room room) { this.room = room; }
    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }
    public Date getCheckInDate() { return checkInDate; }
    public void setCheckInDate(Date checkInDate) { this.checkInDate = checkInDate; }
    public Date getCheckOutDate() { return checkOutDate; }
    public void setCheckOutDate(Date checkOutDate) { this.checkOutDate = checkOutDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(Double totalAmount) { this.totalAmount = totalAmount; }
}