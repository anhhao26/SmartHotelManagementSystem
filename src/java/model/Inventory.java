package com.smarthotel.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Inventory")
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ItemID")
    private Integer itemID;

    @Column(name = "ItemName")
    private String itemName;

    @Column(name = "Unit")
    private String unit;

    @Column(name = "Quantity")
    private Integer quantity;

    // ĐÃ FIX: Chuyển từ MinLevel thành MinStock cho khớp với CSDL
    @Column(name = "MinStock")
    private Integer minStock;

    @Column(name = "CostPrice")
    private Double costPrice;

    @Column(name = "SellingPrice")
    private Double sellingPrice;

    @Column(name = "IsTradeGood")
    private Boolean isTradeGood;

    @Column(name = "IsActive")
    private Boolean isActive;

    // --- GETTERS & SETTERS ---
    public Integer getItemID() { return itemID; }
    public void setItemID(Integer itemID) { this.itemID = itemID; }

    public String getItemName() { return itemName; }
    public void setItemName(String itemName) { this.itemName = itemName; }

    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }

    public Integer getQuantity() { return quantity == null ? 0 : quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public Integer getMinStock() { return minStock; }
    public void setMinStock(Integer minStock) { this.minStock = minStock; }

    public Double getCostPrice() { return costPrice; }
    public void setCostPrice(Double costPrice) { this.costPrice = costPrice; }

    public Double getSellingPrice() { return sellingPrice == null ? 0.0 : sellingPrice; }
    public void setSellingPrice(Double sellingPrice) { this.sellingPrice = sellingPrice; }

    public Boolean getIsTradeGood() { return isTradeGood; }
    public void setIsTradeGood(Boolean isTradeGood) { this.isTradeGood = isTradeGood; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
}