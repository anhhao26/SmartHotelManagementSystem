package model;
public class Inventory {
    private int itemID;
    private String itemName;
    private double sellingPrice;
    private int quantity;
    // constructor + getters/setters
    public Inventory() {}
    public Inventory(int itemID, String itemName, double sellingPrice, int quantity) {
        this.itemID = itemID; this.itemName = itemName; this.sellingPrice = sellingPrice; this.quantity = quantity;
    }
    public int getItemID(){ return itemID; }
    public void setItemID(int id){ this.itemID = id;}
    public String getItemName(){ return itemName;}
    public double getSellingPrice(){ return sellingPrice;}
    public int getQuantity(){ return quantity;}
}