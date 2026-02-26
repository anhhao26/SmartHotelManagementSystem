package com.smarthotel.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RoomID")
    private Integer roomID;

    // ĐÃ FIX: Chuyển thành RoomNumber chuẩn với CSDL
    @Column(name = "RoomNumber") 
    private String roomNumber;

    @Column(name = "Floor")
    private Integer floor;

    @Column(name = "Status")
    private String status;

    @Column(name = "RoomTypeID")
    private Integer roomTypeID;

    // --- GETTERS & SETTERS ---
    public Integer getRoomID() { return roomID; }
    public void setRoomID(Integer roomID) { this.roomID = roomID; }
    
    public String getRoomNumber() { return roomNumber; }
    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }
    
    public Integer getFloor() { return floor; }
    public void setFloor(Integer floor) { this.floor = floor; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Integer getRoomTypeID() { return roomTypeID; }
    public void setRoomTypeID(Integer roomTypeID) { this.roomTypeID = roomTypeID; }
}