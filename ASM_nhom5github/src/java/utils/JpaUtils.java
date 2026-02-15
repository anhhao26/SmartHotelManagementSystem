/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

/**
 *
 * @author ntpho
 */

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JpaUtils {
    // Tên "ASM_nhom5githubPU" phải trùng khớp 100% với tên trong Persistence Unit Name 
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("ASM_nhom5githubPU");

    public static EntityManager getEntityManager() {
        // Hàm này sẽ được gọi ở các DAO để lấy kết nối
        return emf.createEntityManager();
    }
    
    // Đóng kết nối khi tắt app (nếu cần)
    public static void shutdown() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}