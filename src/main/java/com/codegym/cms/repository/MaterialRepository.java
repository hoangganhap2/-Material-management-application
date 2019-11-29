package com.codegym.cms.repository;

import com.codegym.cms.model.Material;
import com.codegym.cms.model.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MaterialRepository extends PagingAndSortingRepository<Material, Long> {
        Page<Material> findAllByNameContaining(String name, Pageable pageable);

        Iterable<Material> findAllBySupplier(Supplier supplier);
}
