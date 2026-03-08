package dao;

import model.Supplier;
import util.JPAUtil;
import jakarta.persistence.EntityManager;
import java.util.List;

public class SupplierDAO {
    public List<Supplier> findAll() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT s FROM Supplier s", Supplier.class).getResultList();
        } finally { em.close(); }
    }

    public Supplier findById(int id) {
        EntityManager em = JPAUtil.getEntityManager();
        try { return em.find(Supplier.class, id); } 
        finally { em.close(); }
    }

    public void save(Supplier supplier) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            if (supplier.getSupplierID() == 0) { em.persist(supplier); } 
            else { em.merge(supplier); }
            em.getTransaction().commit();
        } catch(Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally { em.close(); }
    }

    public void delete(int id) throws Exception {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Supplier s = em.find(Supplier.class, id);
            if (s != null) { em.remove(s); }
            em.getTransaction().commit();
        } catch(Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally { em.close(); }
    }
}