package service;

import dao.InventoryDAO;
import model.Inventory;
import java.util.List;

public class InventoryService {
    private final InventoryDAO inventoryDAO = new InventoryDAO();

    public List<Inventory> findAll() { return inventoryDAO.findAll(); }
    public List<Inventory> findActiveOnly() { return inventoryDAO.findActiveOnly(); }
    public Inventory findById(int id) { return inventoryDAO.find(id); }

    public void createNewInventory(Inventory p) {
        // Hàng tiêu dùng nội bộ thì giá bán phải = 0
        if (p.getIsTradeGood() != null && !p.getIsTradeGood()) {
            p.setSellingPrice(0.0);
        }
        inventoryDAO.createNewInventory(p);
    }

    public void updateInventoryInfo(Inventory p) {
        if (p.getIsTradeGood() != null && !p.getIsTradeGood()) {
            p.setSellingPrice(0.0);
        }
        inventoryDAO.updateInventoryInfo(p);
    }

    public void importStock(int itemId, int quantityToAdd, double newCostPrice) {
        if (quantityToAdd <= 0 || newCostPrice < 0) {
            System.out.println("CẢNH BÁO BẢO MẬT: Nhập số lượng âm hoặc giá nhập không hợp lệ!");
            return;
        }
        inventoryDAO.importStock(itemId, quantityToAdd, newCostPrice);
    }

    public void softDelete(int id) { inventoryDAO.softDelete(id); }
    public void hardDelete(int id) { inventoryDAO.hardDelete(id); }
    public void restore(int id) { inventoryDAO.restore(id); }
}