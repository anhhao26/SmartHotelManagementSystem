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
 * Module 2: Quản lý Nhà cung cấp [cite: 14]
 * Class này đại diện cho bảng Suppliers trong Database
 */
@Entity
@Table(name = "Suppliers")
public class Supplier implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Tự tăng (Identity)
    @Column(name = "SupplierID")
    private int supplierID;

    @Column(name = "SupplierName", nullable = false)
    private String supplierName;

    @Column(name = "ContactPhone")
    private String contactPhone;
    
    @Column(name = "Address")
    private String address;

    @Column(name = "Category")
    private String category; // Thực phẩm, Giặt ủi...

    // Quan hệ 1-N: Một nhà cung cấp có nhiều sản phẩm
    // mappedBy trỏ tới biến "supplier" trong class Product
    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL)
    private List<Product> productList;

    public Supplier() {
    }

   

    public Supplier(int supplierID, String supplierName, String contactPhone, String address, String category, List<Product> productList) {
        this.supplierID = supplierID;
        this.supplierName = supplierName;
        this.contactPhone = contactPhone;
        this.address = address;
        this.category = category;
        this.productList = productList;
    }

    public int getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(int supplierID) {
        this.supplierID = supplierID;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }
    
}