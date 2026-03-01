/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author ntpho
 */

import entity.Supplier;
import utils.JpaUtils;
import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

public class SupplierDAO {

    public List<Supplier> findAll() {
        EntityManager em = JpaUtils.getEntityManager();
        try {
            return em.createQuery("SELECT s FROM Supplier s", Supplier.class).getResultList();
        } finally {
            em.close();
        }
    }

    public Supplier findById(int id) {
        EntityManager em = JpaUtils.getEntityManager();
        try {
            return em.find(Supplier.class, id);
        } finally {
            em.close();
        }
    }

    public void save(Supplier supplier) {
        EntityManager em = JpaUtils.getEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            if (supplier.getSupplierID() > 0) {
                em.merge(supplier); // Update
            } else {
                em.persist(supplier); // Insert
            }
            trans.commit();
        } catch (Exception e) {
            trans.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    // Hàm xóa NCC (lưu ý: Nếu NCC đã có hàng trong kho thì sẽ không xóa được do ràng buộc khóa ngoại)
    public void delete(int id) throws Exception {
        EntityManager em = JpaUtils.getEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            Supplier supplier = em.find(Supplier.class, id);
            if (supplier != null) {
                em.remove(supplier);
            }
            trans.commit();
        } catch (Exception e) {
            trans.rollback();
            throw e; // Ném lỗi ra để Controller bắt (VD: Lỗi ràng buộc khóa ngoại)
        } finally {
            em.close();
        }
    }
}