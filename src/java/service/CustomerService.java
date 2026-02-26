package com.smarthotel.service;

import com.smarthotel.dao.CustomerDAO;
import com.smarthotel.model.Customer;
import java.util.List;  // thêm dòng này

public class CustomerService {
    private final CustomerDAO dao = new CustomerDAO();

    public Customer getById(int id) { 
        return dao.findById(id); 
    }

    // ✅ THÊM METHOD NÀY
    public List<Customer> findAll() {
        return dao.findAll();
    }

    public Customer create(Customer c) { 
        return dao.create(c); 
    }

    public boolean update(Customer c) { 
        return dao.update(c); 
    }

    public boolean existsUnique(String cccd, String phone, String email) { 
        return dao.existsByUniqueFields(cccd, phone, email); 
    }
}