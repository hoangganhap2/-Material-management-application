package com.codegym.cms.service.impl;

import com.codegym.cms.model.Supplier;
import com.codegym.cms.repository.SupplierRepository;
import com.codegym.cms.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;

public class SupplierServiceImpl implements SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;
    @Override
    public Iterable<Supplier> findAll() {
        return supplierRepository.findAll();
    }

    @Override
    public Supplier findById(Long id) {
        return supplierRepository.findOne(id);
    }

    @Override
    public void save(Supplier supplier) {
        supplierRepository.save(supplier);
    }

    @Override
    public void remove(Long id) {
        supplierRepository.delete(id);
    }
}
