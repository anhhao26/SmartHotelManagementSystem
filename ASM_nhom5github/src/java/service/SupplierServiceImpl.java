/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

/**
 *
 * @author ntpho
 */


import dao.SupplierDAO;
import entity.Supplier;
import java.util.List;

public class SupplierServiceImpl implements SupplierService {

    private SupplierDAO supplierDAO;

    public SupplierServiceImpl() {
        this.supplierDAO = new SupplierDAO();
    }

    @Override
    public List<Supplier> findAll() {
        return supplierDAO.findAll();
    }

    @Override
    public Supplier findById(int id) {
        return supplierDAO.findById(id);
    }

    @Override
    public void save(Supplier supplier) {
        // Có thể thêm validate số điện thoại ở đây nếu cần
        supplierDAO.save(supplier);
    }

    @Override
    public void delete(int id) throws Exception {
        supplierDAO.delete(id);
    }
}