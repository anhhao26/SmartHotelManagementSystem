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
import java.util.List;
import jakarta.persistence.*;

/**
 * Module 2: Kho & Định giá [cite: 12]
 * QUAN TRỌNG: 
 * - Module 7 (Check-out) sẽ đọc giá bán (SellingPrice) từ đây.
 * - Module 2 (Kho) sẽ cập nhật số lượng (Quantity) và giá nhập (CostPrice) tại đây.
 */
@Entity
@Table(name = "Products")
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProductID")
    private int productID;

    @Column(name = "ProductName", nullable = false)
    private String productName;

    @Column(name = "Unit")
    private String unit; // Đơn vị tính (Lon, Chai...)

    @Column(name = "Quantity")
    private int quantity; // Số lượng tồn kho hiện tại

    @Column(name = "MinStock")
    private int minStock; // [cite: 21] Cảnh báo khi thấp hơn mức này

    // [cite: 19] Giá vốn (Dùng cho báo cáo nhập hàng)
    @Column(name = "CostPrice")
    private double costPrice; 

    // [cite: 20] Giá bán (QUAN TRỌNG: Đây là giá tính tiền cho khách)
    // Ví dụ: Nhập Coca 5k -> Set SellingPrice = 15k
    @Column(name = "SellingPrice")
    private double sellingPrice;

    // [cite: 17] True = Hàng bán (Doanh thu), False = Hàng tiêu dùng (Chi phí)
    @Column(name = "IsTradeGood")
    private boolean isTradeGood;
    
    @Column(name = "IsActive")
    private boolean isActive = true; // Mặc định là true

    // Quan hệ N-1: Nhiều sản phẩm thuộc về 1 nhà cung cấp
    @ManyToOne
    @JoinColumn(name = "SupplierID")
    private Supplier supplier;
    
    // Quan hệ 1-N: Một sản phẩm có nhiều lịch sử nhập hàng
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ImportHistory> importHistoryList;

    public Product() {
    }


    public Product(int productID, String productName, String unit, int quantity, int minStock, double costPrice, double sellingPrice, boolean isTradeGood, Supplier supplier, List<ImportHistory> importHistoryList) {
        this.productID = productID;
        this.productName = productName;
        this.unit = unit;
        this.quantity = quantity;
        this.minStock = minStock;
        this.costPrice = costPrice;
        this.sellingPrice = sellingPrice;
        this.isTradeGood = isTradeGood;
        this.supplier = supplier;
        this.importHistoryList = importHistoryList;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getMinStock() {
        return minStock;
    }

    public void setMinStock(int minStock) {
        this.minStock = minStock;
    }

    public double getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(double costPrice) {
        this.costPrice = costPrice;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public boolean isIsTradeGood() {
        return isTradeGood;
    }

    public void setIsTradeGood(boolean isTradeGood) {
        this.isTradeGood = isTradeGood;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public List<ImportHistory> getImportHistoryList() {
        return importHistoryList;
    }

    public void setImportHistoryList(List<ImportHistory> importHistoryList) {
        this.importHistoryList = importHistoryList;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
    

    
}