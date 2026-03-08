package service;

import dao.SupplierDAO;
import model.Supplier;
import java.util.List;

public class SupplierService {
    private final SupplierDAO supplierDAO = new SupplierDAO();

    public List<Supplier> findAll() { return supplierDAO.findAll(); }
    
    public Supplier findById(int id) { return supplierDAO.findById(id); }
    
    public void save(Supplier supplier) { supplierDAO.save(supplier); }
    
    public void delete(int id) throws Exception { supplierDAO.delete(id); }
}