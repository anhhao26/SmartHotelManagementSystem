/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.io.Serializable;


@Entity
@Table(name = "Rooms")
public class Room implements Serializable {

    @Id
    @Column(name = "RoomID", length = 10)
    private String roomId;

    @ManyToOne
    @JoinColumn(name = "RoomTypeID")
    private RoomType roomType;

    @Column(name = "Status", length = 20)
    private String status = "Available"; // Mặc định là Trống

    @Column(name = "Floor")
    private Integer floor;

    public Room() {}

    // Getters and Setters
    public String getRoomId() { return roomId; }
    public void setRoomId(String roomId) { this.roomId = roomId; }

    public RoomType getRoomType() { return roomType; }
    public void setRoomType(RoomType roomType) { this.roomType = roomType; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Integer getFloor() { return floor; }
    public void setFloor(Integer floor) { this.floor = floor; }
}
