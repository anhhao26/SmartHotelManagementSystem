/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

/**
 *
 * @author ntpho
 */


import dao.ProductDAO;
import entity.Product;
import java.util.List;

/**
 * Module 2: Implementation của Service
 *  
 */
public class ProductServiceImpl implements ProductService {

    // Khai báo DAO để Service sử dụng
    private ProductDAO productDAO;

    public ProductServiceImpl() {
        this.productDAO = new ProductDAO();
    }

    @Override
    public List<Product> findAll() {
        return productDAO.findAll();
    }
    
    @Override
    public List<Product> findActiveOnly() {
        return productDAO.findActiveOnly();
    }

    @Override
    public Product findById(int id) {
        return productDAO.findById(id);
    }

    @Override
    public void createNewProduct(Product p) {
    // LOGIC NGHIỆP VỤ: Nếu là hàng tiêu dùng (isTradeGood = false) -> Giá bán phải bằng 0
        if (!p.isIsTradeGood()) {
           p.setSellingPrice(0);
    }   
        productDAO.createNewProduct(p);
    }

    @Override
    public void updateProductInfo(Product p) {
    // LOGIC NGHIỆP VỤ TƯƠNG TỰ
        if (!p.isIsTradeGood()) {
            p.setSellingPrice(0);
    }   
        productDAO.updateProductInfo(p);
    }

    @Override
    public void importStock(int productId, int quantityToAdd, double newCostPrice) {
        productDAO.importStock(productId, quantityToAdd, newCostPrice);
    }

    @Override
    public void softDelete(int id) {
        productDAO.softDelete(id);
    }

    @Override
    public void hardDelete(int id) {
        productDAO.hardDelete(id); // Gọi hàm delete trong DAO
    }

    @Override
    public void restore(int id) {
        productDAO.restore(id);
    }
    
    @Override
    public boolean deductStock(int productId, int quantityToDeduct) {
        // Có thể thêm logic kiểm tra điều kiện ở đây trước khi gọi DAO nếu cần(nhớ xem,hỏi cô rồi làm sau)
        if (quantityToDeduct <= 0) {
            return false; // Không hợp lệ
        }
        
        // Gọi xuống DAO để thực hiện trừ kho trong Database
        return productDAO.deductStock(productId, quantityToDeduct);
    }
}