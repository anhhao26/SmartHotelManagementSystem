package model;
public class CartItem {
    private int itemID;
    private int quantity;
    public CartItem(int itemID, int quantity) {
        this.itemID = itemID;
        this.quantity = quantity;
    }
    public int getItemID() { return itemID; }
    public int getQuantity() { return quantity; }
}