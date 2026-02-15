/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author ntpho
 */


import java.io.Serializable;
import java.util.Date;
import jakarta.persistence.*;

/**
 * Module 2: Lưu vết nhập hàng 
 * Class này giúp tạo báo cáo "Top vật tư nhập nhiều nhất" [cite: 163]
 */
@Entity
@Table(name = "ImportHistory")
public class ImportHistory implements Serializable {

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
    private double importPrice; // Giá nhập tại thời điểm đó

    @Column(name = "TotalCost")
    private double totalCost; // = ImportQuantity * ImportPrice

    @ManyToOne
    @JoinColumn(name = "ProductID", nullable = false)
    private Product product;

    public ImportHistory() {
    }
    
  

    public ImportHistory(int importID, Date importDate, int importQuantity, double importPrice, double totalCost, Product product) {
        this.importID = importID;
        this.importDate = importDate;
        this.importQuantity = importQuantity;
        this.importPrice = importPrice;
        this.totalCost = totalCost;
        this.product = product;
    }

    public int getImportID() {
        return importID;
    }

    public void setImportID(int importID) {
        this.importID = importID;
    }

    public Date getImportDate() {
        return importDate;
    }

    public void setImportDate(Date importDate) {
        this.importDate = importDate;
    }

    public int getImportQuantity() {
        return importQuantity;
    }

    public void setImportQuantity(int importQuantity) {
        this.importQuantity = importQuantity;
    }

    public double getImportPrice() {
        return importPrice;
    }

    public void setImportPrice(double importPrice) {
        this.importPrice = importPrice;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
    
}