/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

/**
 *
 * @author ntpho
 */

import entity.Product;
import java.util.List;


public interface ProductService {
    
    // Lấy danh sách sản phẩm 
    List<Product> findAll();
    
    // Lấy danh sách sản phẩm đang active
    List<Product> findActiveOnly();
    
    // Tìm sản phẩm theo ID
    Product findById(int id);
    
    // [Module 2] Tạo sản phẩm mới (Lần đầu)
    void createNewProduct(Product product);
    
    // [Module 2] Cập nhật thông tin (Tên, giá bán...) - Không lưu lịch sử
    void updateProductInfo(Product product);
    
    // [Module 2] Nhập kho (Cộng số lượng & Lưu lịch sử)
    void importStock(int productId, int quantityToAdd, double newCostPrice);
    
    // [Module 2] Xóa mềm (Ẩn sản phẩm)
    void softDelete(int id);
    
    // [Module 2] Xóa vĩnh viễn
    void hardDelete(int id);

    // [Module 2] Khôi phục (Mở lại kinh doanh)
    void restore(int id);
    
    // [Module 7 Gọi hàm này]: Trừ kho khi sử dụng
    boolean deductStock(int productId, int quantityToDeduct);
}