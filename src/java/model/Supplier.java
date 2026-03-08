package model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "Suppliers")
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SupplierID")
    private int supplierID;

    @Column(name = "SupplierName", nullable = false)
    private String supplierName;

    @Column(name = "ContactPhone")
    private String contactPhone;
    
    @Column(name = "Address")
    private String address;

    @Column(name = "Category")
    private String category;

    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL)
    private List<Inventory> productList; // Dùng Inventory của Hào

    public Supplier() {}

    // Getters and Setters
    public int getSupplierID() { return supplierID; }
    public void setSupplierID(int supplierID) { this.supplierID = supplierID; }
    public String getSupplierName() { return supplierName; }
    public void setSupplierName(String supplierName) { this.supplierName = supplierName; }
    public String getContactPhone() { return contactPhone; }
    public void setContactPhone(String contactPhone) { this.contactPhone = contactPhone; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public List<Inventory> getProductList() { return productList; }
    public void setProductList(List<Inventory> productList) { this.productList = productList; }
}