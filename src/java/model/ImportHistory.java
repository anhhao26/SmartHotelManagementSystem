package model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ImportHistory")
public class ImportHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ImportID")
    private int importID;

    @Column(name = "ImportDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date importDate;

    @Column(name = "ImportQuantity")
    private int importQuantity;

    @Column(name = "ImportPrice")
    private double importPrice;

    @Column(name = "TotalCost")
    private double totalCost;

    @ManyToOne
    @JoinColumn(name = "ItemID", nullable = false) // Đã sửa trỏ về ItemID của Inventory Hào
    private Inventory inventory;

    public ImportHistory() {}

    // Getters and setters
    public int getImportID() { return importID; }
    public void setImportID(int importID) { this.importID = importID; }
    public Date getImportDate() { return importDate; }
    public void setImportDate(Date importDate) { this.importDate = importDate; }
    public int getImportQuantity() { return importQuantity; }
    public void setImportQuantity(int importQuantity) { this.importQuantity = importQuantity; }
    public double getImportPrice() { return importPrice; }
    public void setImportPrice(double importPrice) { this.importPrice = importPrice; }
    public double getTotalCost() { return totalCost; }
    public void setTotalCost(double totalCost) { this.totalCost = totalCost; }
    public Inventory getInventory() { return inventory; }
    public void setInventory(Inventory inventory) { this.inventory = inventory; }
}