package model;

import jakarta.persistence.*;

@Entity
@Table(name = "Rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RoomID")
    private Integer roomID;

    @Column(name = "RoomNumber") 
    private String roomNumber;

    @Column(name = "Floor")
    private Integer floor;

    @Column(name = "Status")
    private String status = "Available";

    @Column(name = "Price")
    private Double price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RoomTypeID")
    private RoomType roomType;

    public Room() {}

    public Integer getRoomID() { return roomID; }
    public void setRoomID(Integer roomID) { this.roomID = roomID; }
    
    public String getRoomNumber() { return roomNumber; }
    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }
    
    public Integer getFloor() { return floor; }
    public void setFloor(Integer floor) { this.floor = floor; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Double getPrice() { 
        if (price != null && price > 0) return price;
        if (roomType != null && roomType.getPricePerNight() != null) return roomType.getPricePerNight().doubleValue();
        return 0.0; 
    }
    public void setPrice(Double price) { this.price = price; }

    public RoomType getRoomType() { return roomType; }
    public void setRoomType(RoomType roomType) { this.roomType = roomType; }
}