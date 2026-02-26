package com.smarthotel.dao;

import com.smarthotel.model.Customer;
import com.smarthotel.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;   // thêm dòng này

public class CustomerDAO {

    public Customer findById(int id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(Customer.class, id);
        } finally {
            em.close();
        }
    }

    // ✅ THÊM METHOD NÀY
    public List<Customer> findAll() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT c FROM Customer c", Customer.class)
                     .getResultList();
        } finally {
            em.close();
        }
    }

    public Customer create(Customer c) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(c);
            em.getTransaction().commit();
            return c;
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
    }

    public boolean update(Customer c) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(c);
            em.getTransaction().commit();
            return true;
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            ex.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }

    public boolean existsByUniqueFields(String cccd, String phone, String email) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            String jpql = "SELECT COUNT(c) FROM Customer c WHERE c.cccdPassport = :cccd OR c.phone = :phone OR c.email = :email";
            TypedQuery<Long> q = em.createQuery(jpql, Long.class);
            q.setParameter("cccd", cccd);
            q.setParameter("phone", phone);
            q.setParameter("email", email);
            Long cnt = q.getSingleResult();
            return cnt != null && cnt > 0;
        } finally {
            em.close();
        }
    }
}