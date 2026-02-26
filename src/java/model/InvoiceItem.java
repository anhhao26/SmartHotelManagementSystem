package com.smarthotel.model;

import jakarta.persistence.*;

@Entity
@Table(name = "InvoiceItems")
public class InvoiceItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "InvoiceItemID")
    private Integer invoiceItemID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "InvoiceID", referencedColumnName = "InvoiceID")
    private Invoice invoice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ItemID", referencedColumnName = "ItemID")
    private Inventory inventory;

    @Column(name = "Quantity")
    private Integer quantity;

    @Column(name = "UnitPrice")
    private Double unitPrice;

    // --- GETTERS & SETTERS ---
    public Integer getInvoiceItemID() { return invoiceItemID; }
    public void setInvoiceItemID(Integer invoiceItemID) { this.invoiceItemID = invoiceItemID; }
    public Invoice getInvoice() { return invoice; }
    public void setInvoice(Invoice invoice) { this.invoice = invoice; }
    public Inventory getInventory() { return inventory; }
    public void setInventory(Inventory inventory) { this.inventory = inventory; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public Double getUnitPrice() { return unitPrice; }
    public void setUnitPrice(Double unitPrice) { this.unitPrice = unitPrice; }
}