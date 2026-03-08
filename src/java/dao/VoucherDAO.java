package dao;

import model.Voucher;
import util.JPAUtil;
import jakarta.persistence.EntityManager;
import java.util.List;

public class VoucherDAO {
    public Voucher findById(String voucherCode) {
        EntityManager em = JPAUtil.getEntityManager();
        try { return em.find(Voucher.class, voucherCode); } 
        finally { em.close(); }
    }

    public List<Voucher> findAllActive() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT v FROM Voucher v WHERE v.isActive = true", Voucher.class).getResultList();
        } finally { em.close(); }
    }

    public boolean insert(Voucher voucher) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(voucher);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            return false;
        } finally { em.close(); }
    }

    public boolean update(Voucher voucher) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(voucher);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            return false;
        } finally { em.close(); }
    }
}