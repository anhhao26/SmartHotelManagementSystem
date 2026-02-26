package com.smarthotel.dao;

import com.smarthotel.model.Account;
import com.smarthotel.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

public class AccountDAO {

    private String hashPassword(String password) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (Exception e) {
            return password;
        }
    }

    // Đã sửa: Dùng JPQL để tìm theo cột username thay vì em.find()
    public Account findByUsername(String username) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            String jpql = "SELECT a FROM Account a WHERE a.username = :u";
            TypedQuery<Account> q = em.createQuery(jpql, Account.class);
            q.setParameter("u", username);
            return q.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        } finally {
            em.close();
        }
    }

    public Account findByUsernameAndPassword(String username, String password) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            String hashed = hashPassword(password);
            try {
                String jpql = "SELECT a FROM Account a WHERE a.username = :u AND a.password = :p";
                TypedQuery<Account> q = em.createQuery(jpql, Account.class);
                q.setParameter("u", username);
                q.setParameter("p", hashed);
                return q.getSingleResult();
            } catch (NoResultException ex) {
                try {
                    String jpql2 = "SELECT a FROM Account a WHERE a.username = :u AND a.password = :p2";
                    TypedQuery<Account> q2 = em.createQuery(jpql2, Account.class);
                    q2.setParameter("u", username);
                    q2.setParameter("p2", password);
                    return q2.getSingleResult();
                } catch (NoResultException ex2) {
                    return null;
                }
            }
        } finally {
            em.close();
        }
    }

    public void create(Account acc, String rawPassword) {
        acc.setPassword(hashPassword(rawPassword));
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(acc);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
    }

    public boolean existsByUsername(String username) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Long> q = em.createQuery("SELECT COUNT(a) FROM Account a WHERE a.username = :u", Long.class);
            q.setParameter("u", username);
            Long cnt = q.getSingleResult();
            return cnt != null && cnt > 0;
        } finally {
            em.close();
        }
    }
}