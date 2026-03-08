package dao;

import model.Inventory;
import model.ImportHistory;
import util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.LockModeType;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.Date;

public class InventoryDAO {

    // --- HÀM CỦA HÀO DÙNG CHO CHECKOUT ---
    public Inventory findWithPessimisticLock(int id, EntityManager em) {
        return em.find(Inventory.class, id, LockModeType.PESSIMISTIC_WRITE);
    }

    public Inventory find(int id) {
        EntityManager em = JPAUtil.getEntityManager();
        try { return em.find(Inventory.class, id); } 
        finally { em.close(); }
    }

    public List<Inventory> findAll() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT i FROM Inventory i ORDER BY i.itemID ASC", Inventory.class).getResultList();
        } finally { em.close(); }
    }

    // --- HÀM CỦA BẠN KIA DÙNG CHO KHO ---
    public List<Inventory> findActiveOnly() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT i FROM Inventory i WHERE i.isActive = true ORDER BY i.itemID ASC", Inventory.class).getResultList();
        } finally { em.close(); }
    }

    public void updateInventoryInfo(Inventory p) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            Inventory existing = em.find(Inventory.class, p.getItemID());
            if (existing != null) {
                existing.setItemName(p.getItemName());
                existing.setUnit(p.getUnit());
                existing.setSellingPrice(p.getSellingPrice());
                existing.setSupplier(p.getSupplier());
                existing.setIsTradeGood(p.getIsTradeGood());
                em.merge(existing);
            }
            trans.commit();
        } catch (Exception e) {
            trans.rollback(); e.printStackTrace();
        } finally { em.close(); }
    }

    public void importStock(int itemId, int quantityToAdd, double newCostPrice) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            Inventory p = em.find(Inventory.class, itemId);
            if (p != null) {
                p.setCostPrice(newCostPrice);
                p.setQuantity(p.getQuantity() + quantityToAdd);
                
                ImportHistory history = new ImportHistory();
                history.setInventory(p);
                history.setImportQuantity(quantityToAdd);
                history.setImportPrice(newCostPrice);
                history.setTotalCost(quantityToAdd * newCostPrice);
                history.setImportDate(new Date());
                
                em.persist(history);
                em.merge(p);
            }
            trans.commit();
        } catch (Exception e) {
            trans.rollback(); e.printStackTrace();
        } finally { em.close(); }
    }

    public void createNewInventory(Inventory p) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            em.persist(p);
            
            ImportHistory history = new ImportHistory();
            history.setInventory(p);
            history.setImportQuantity(p.getQuantity());
            history.setImportPrice(p.getCostPrice());
            history.setTotalCost(p.getQuantity() * p.getCostPrice());
            history.setImportDate(new Date());
            em.persist(history);
            
            trans.commit();
        } catch (Exception e) {
            trans.rollback(); e.printStackTrace();
        } finally { em.close(); }
    }

    public void hardDelete(int id) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            Inventory p = em.find(Inventory.class, id);
            if (p != null) {
                em.refresh(p); 
                em.remove(p);
            }
            trans.commit();
        } catch (Exception e) {
            if (trans.isActive()) trans.rollback();
            e.printStackTrace();
        } finally { em.close(); }
    }

    public void softDelete(int id) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            Inventory p = em.find(Inventory.class, id);
            if (p != null) {
                p.setIsActive(false);
                em.merge(p);
            }
            trans.commit();
        } catch (Exception e) {
            trans.rollback(); e.printStackTrace();
        } finally { em.close(); }
    }

    public void restore(int id) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            Inventory p = em.find(Inventory.class, id);
            if (p != null) {
                p.setIsActive(true);
                em.merge(p);
            }
            trans.commit();
        } catch (Exception e) {
            trans.rollback(); e.printStackTrace();
        } finally { em.close(); }
    }
}