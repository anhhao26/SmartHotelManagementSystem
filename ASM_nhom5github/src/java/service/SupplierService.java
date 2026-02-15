/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

/**
 *
 * @author ntpho
 */


import entity.Supplier;
import java.util.List;

/**
 * Module 2: Interface Dịch vụ Nhà Cung Cấp
 * Định nghĩa các chức năng quản lý đối tác (CRUD).
 */
public interface SupplierService {
    
    // Lấy tất cả NCC
    List<Supplier> findAll();
    
    // Tìm NCC theo ID (để sửa)
    Supplier findById(int id);
    
    // Lưu NCC (Tự động phân biệt Thêm mới hoặc Cập nhật)
    void save(Supplier supplier);
    
    // Xóa NCC (Có thể ném ra Exception nếu dính khóa ngoại)
    void delete(int id) throws Exception;
}