/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author ntpho
 */


import entity.ImportHistory;
import entity.Product;
import utils.JpaUtils;
import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import java.util.Date;

public class ProductDAO {

    // Lấy danh sách sản phẩm (Có thể dùng để hiển thị trang Quản lý Kho)
    //  Chỉ hiện các sản phẩm Đang kinh doanh (Active)
    public List<Product> findAll() {
    EntityManager em = JpaUtils.getEntityManager();
    try {
        TypedQuery<Product> query = em.createQuery("SELECT p FROM Product p ORDER BY p.productID ASC", Product.class);
        
        return query.getResultList();
    } finally {
        em.close();
    }
}
    //  Hàm chỉ lấy hàng đang kinh doanh (Active = true)
    public List<Product> findActiveOnly() {
    EntityManager em = JpaUtils.getEntityManager();
    try {
        TypedQuery<Product> query = em.createQuery(
            "SELECT p FROM Product p WHERE p.isActive = true ORDER BY p.productID ASC", 
            Product.class);
        return query.getResultList();
    } finally {
        em.close();
    }
}

    // Tìm sản phẩm theo ID (Dùng khi chọn sửa sản phẩm)
    public Product findById(int id) {
        EntityManager em = JpaUtils.getEntityManager();
        try {
            return em.find(Product.class, id);
        } finally {
            em.close();
        }
    }

    // [Module 2] Chức năng: Nhập hàng hoặc Cập nhật giá
    // Hàm này vừa tạo mới, vừa update được
    // HÀM 1: CHỈ CẬP NHẬT THÔNG TIN (Không lưu lịch sử)
// Dùng khi sửa tên, đơn vị, giá bán...
public void updateProductInfo(Product p) {
    EntityManager em = JpaUtils.getEntityManager();
    EntityTransaction trans = em.getTransaction();
    try {
        trans.begin();
        Product existing = em.find(Product.class, p.getProductID());
        if (existing != null) {
            existing.setProductName(p.getProductName());
            existing.setUnit(p.getUnit());
            existing.setSellingPrice(p.getSellingPrice());
            existing.setSupplier(p.getSupplier());
            existing.setIsTradeGood(p.isIsTradeGood());
            
            // Lưu ý: Không cho sửa Quantity và CostPrice ở đây để tránh gian lận/nhầm lẫn
            // Hoặc nếu bạn muốn cho sửa sai số thì set ở đây nhưng không ghi history
            em.merge(existing);
        }
        trans.commit();
    } catch (Exception e) {
        trans.rollback();
        e.printStackTrace();
    } finally {
        em.close();
    }
}

// HÀM 2: NHẬP HÀNG (Cộng dồn số lượng & Lưu lịch sử)
public void importStock(int productId, int quantityToAdd, double newCostPrice) {
    EntityManager em = JpaUtils.getEntityManager();
    EntityTransaction trans = em.getTransaction();
    try {
        trans.begin();
        Product p = em.find(Product.class, productId);
        if (p != null) {
            // 1. Cập nhật giá vốn mới nhất
            p.setCostPrice(newCostPrice);
            
            // 2. Cộng dồn số lượng tồn kho (Tồn cũ + Nhập mới)
            p.setQuantity(p.getQuantity() + quantityToAdd);
            
            // 3. Ghi lịch sử nhập hàng
            ImportHistory history = new ImportHistory();
            history.setProduct(p);
            history.setImportQuantity(quantityToAdd); // Chỉ lưu số lượng vừa nhập
            history.setImportPrice(newCostPrice);
            history.setTotalCost(quantityToAdd * newCostPrice);
            history.setImportDate(new java.util.Date());
            
            em.persist(history); // Lưu dòng lịch sử
            em.merge(p);         // Lưu sản phẩm đã update
        }
        trans.commit();
    } catch (Exception e) {
        trans.rollback();
        e.printStackTrace();
    } finally {
        em.close();
    }
}

// HÀM 3: TẠO SẢN PHẨM MỚI TINH (Vẫn cần lưu lịch sử lần đầu)
public void createNewProduct(Product p) {
    EntityManager em = JpaUtils.getEntityManager();
    EntityTransaction trans = em.getTransaction();
    try {
        trans.begin();
        em.persist(p);
        
        // Tạo lịch sử cho lần nhập đầu tiên
        ImportHistory history = new ImportHistory();
        history.setProduct(p);
        history.setImportQuantity(p.getQuantity());
        history.setImportPrice(p.getCostPrice());
        history.setTotalCost(p.getQuantity() * p.getCostPrice());
        history.setImportDate(new java.util.Date());
        em.persist(history);
        
        trans.commit();
    } catch (Exception e) {
        trans.rollback();
        e.printStackTrace();
    } finally {
        em.close();
    }
}    

    // [Module 7 Gọi hàm này]: Trừ kho khi khách dùng Mini-bar
    // Trả về true nếu trừ thành công, false nếu hết hàng
    public boolean deductStock(int productId, int quantityToDeduct) {
    EntityManager em = JpaUtils.getEntityManager();
    EntityTransaction trans = em.getTransaction();
    try {
        trans.begin();
        Product product = em.find(Product.class, productId);
        
        // [NÊN LÀM]: Refresh để lấy số lượng tồn kho mới nhất từ DB
        // Tránh trường hợp cache báo còn 10 nhưng thực tế người khác đã mua hết rồi
        if (product != null) {
            em.refresh(product); 
        }
        
        if (product != null && product.getQuantity() >= quantityToDeduct) {
            // Logic trừ kho
            product.setQuantity(product.getQuantity() - quantityToDeduct);
            em.merge(product);
            trans.commit();
            return true;
        } else {
            
            trans.rollback(); 
            return false;
        }
    } catch (Exception e) {
        // [AN TOÀN]: Chỉ rollback nếu transaction đang hoạt động
        if (trans.isActive()) {
            trans.rollback();
        }
        e.printStackTrace(); // In lỗi ra console để debug
        return false;
    } finally {
        em.close();
    }
}
    public void hardDelete(int id) {
       EntityManager em = JpaUtils.getEntityManager();
    EntityTransaction trans = em.getTransaction();
    try {
        trans.begin();
        
        Product product = em.find(Product.class, id);
        
        if (product != null) {
            
            // Ép buộc tải lại dữ liệu mới nhất từ DB để nhận diện được các dòng ImportHistory con
            em.refresh(product); 
            
            // Sau khi refresh, JPA đã biết sản phẩm này có con -> Xóa cha sẽ tự xóa con (Cascade)
            em.remove(product);
        }
        
        trans.commit();
    } catch (Exception e) {
        System.out.println("Lỗi xóa sản phẩm ID " + id + ": " + e.getMessage());
        e.printStackTrace();
        if (trans.isActive()) trans.rollback();
    } finally {
        em.close();
    }
    }
    public void softDelete(int id) {
        EntityManager em = JpaUtils.getEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            Product p = em.find(Product.class, id);
            if (p != null) {
                p.setIsActive(false); // Đánh dấu là ẩn
                em.merge(p);
            }
            trans.commit();
        } catch (Exception e) {
            trans.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public void restore(int id) {
        EntityManager em = JpaUtils.getEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            Product p = em.find(Product.class, id);
            if (p != null) {
                p.setIsActive(true); // Đánh dấu là hiện (Kinh doanh lại)
                em.merge(p);
            }
            trans.commit();
        } catch (Exception e) {
            trans.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
}