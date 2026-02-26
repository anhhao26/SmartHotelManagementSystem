package com.smarthotel.dao;

import com.smarthotel.model.Inventory;
import com.smarthotel.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class InventoryDAO {

    public Inventory findWithPessimisticLock(int id, EntityManager em) {
        return em.find(Inventory.class, id, LockModeType.PESSIMISTIC_WRITE);
    }

    // simple find without lock
    public Inventory find(int id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(Inventory.class, id);
        } finally {
            em.close();
        }
    }

    // list all inventory (used by JSP checkout list)
    public List<Inventory> findAll() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Inventory> q = em.createQuery("SELECT i FROM Inventory i", Inventory.class);
            return q.getResultList();
        } finally {
            em.close();
        }
    }
}