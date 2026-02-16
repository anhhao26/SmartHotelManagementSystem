/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entity.Voucher;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;
import utils.JpaUtils;

public class VoucherDAO {

    // 1. Lấy 1 Voucher theo mã (Dùng cho Service lấy lên để kiểm tra/validate)
    public Voucher findById(String voucherCode) {
        try (EntityManager em = JpaUtils.getEntityManager()) {
            return em.find(Voucher.class, voucherCode);
        }
    }

    // 2. Lấy danh sách tất cả voucher đang hoạt động (Dùng cho UI hiển thị JTable)
    public List<Voucher> findAllActive() {
        try (EntityManager em = JpaUtils.getEntityManager()) {
            String jpql = "SELECT v FROM Voucher v WHERE v.isActive = true";
            TypedQuery<Voucher> query = em.createQuery(jpql, Voucher.class);
            return query.getResultList();
        }
    }

    // 3. Thêm mới một Voucher vào DB
    public boolean insert(Voucher voucher) {
        EntityManager em = JpaUtils.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(voucher);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }

    // 4. Cập nhật thông tin Voucher (Sửa giá trị, số lượng, hoặc set IsActive = false để xóa mềm)
    public boolean update(Voucher voucher) {
        EntityManager em = JpaUtils.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(voucher);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }
}
