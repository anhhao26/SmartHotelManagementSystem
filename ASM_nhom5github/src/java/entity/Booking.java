/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import jakarta.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table(name="Bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookingId;

    @ManyToOne
    @JoinColumn(name="RoomID")
    private Room room;

    private Date checkIn;
    private Date checkOut;

    private String status;
    private String customerName;
    private String email;

    private double totalPrice;

    private Timestamp createdAt = new Timestamp(System.currentTimeMillis());

    public int getBookingId(){ return bookingId; }

    public Room getRoom(){ return room; }
    public void setRoom(Room room){ this.room = room; }

    public Date getCheckIn(){ return checkIn; }
    public void setCheckIn(Date checkIn){ this.checkIn = checkIn; }

    public Date getCheckOut(){ return checkOut; }
    public void setCheckOut(Date checkOut){ this.checkOut = checkOut; }

    public String getStatus(){ return status; }
    public void setStatus(String status){ this.status = status; }

    public String getCustomerName(){ return customerName; }
    public void setCustomerName(String customerName){ this.customerName = customerName; }

    public String getEmail(){ return email; }
    public void setEmail(String email){ this.email = email; }

    public double getTotalPrice(){ return totalPrice; }
    public void setTotalPrice(double totalPrice){ this.totalPrice = totalPrice; }

    public Timestamp getCreatedAt(){ return createdAt; }
}
