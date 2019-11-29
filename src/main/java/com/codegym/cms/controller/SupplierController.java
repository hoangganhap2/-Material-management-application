package com.codegym.cms.controller;

import com.codegym.cms.model.Material;
import com.codegym.cms.model.Supplier;
import com.codegym.cms.service.MaterialService;
import com.codegym.cms.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @Autowired
    private MaterialService materialService;

    @GetMapping("/suppliers")
    public ModelAndView listSuppliers() {
        Iterable<Supplier> suppliers = supplierService.findAll();
        ModelAndView modelAndView = new ModelAndView("/supplier/list");
        modelAndView.addObject("suppliers", suppliers);
        return modelAndView;
    }

    @GetMapping("/create-supplier")
    public ModelAndView showCreateForm() {
        ModelAndView modelAndView = new ModelAndView("/supplier/create");
        modelAndView.addObject("supplier", new Supplier());
        return modelAndView;
    }

    @PostMapping("/create-supplier")
    public ModelAndView saveSupplier(@ModelAttribute("supplier") Supplier supplier) {
        supplierService.save(supplier);

        ModelAndView modelAndView = new ModelAndView("/supplier/create");
        modelAndView.addObject("supplier", new Supplier());
        modelAndView.addObject("message", "them nha cung cap thanh cong");
        return modelAndView;
    }

    @GetMapping("/edit-supplier/{id}")
    public ModelAndView showEditForm(@PathVariable Long id){
        Supplier supplier = supplierService.findById(id);
        if(supplier != null) {
            ModelAndView modelAndView = new ModelAndView("/supplier/edit");
            modelAndView.addObject("supplier", supplier);
            return modelAndView;

        }else {
            ModelAndView modelAndView = new ModelAndView("/error.404");
            return modelAndView;
        }
    }

    @PostMapping("/edit-supplier")
    public ModelAndView updateSupplier(@ModelAttribute("supplier") Supplier supplier){
        supplierService.save(supplier);
        ModelAndView modelAndView = new ModelAndView("/supplier/edit");
        modelAndView.addObject("supplier", supplier);
        modelAndView.addObject("message", "Cap nhat nha cung cap thanh cong");
        return modelAndView;
    }

    @GetMapping("/delete-supplier/{id}")
    public ModelAndView showDeleteForm(@PathVariable Long id){
        Supplier supplier = supplierService.findById(id);
        if(supplier != null) {
            ModelAndView modelAndView = new ModelAndView("/supplier/delete");
            modelAndView.addObject("supplier", supplier);
            return modelAndView;

        }else {
            ModelAndView modelAndView = new ModelAndView("/error.404");
            return modelAndView;
        }
    }

    @PostMapping("/delete-supplier")
    public String deleteSupplier(@ModelAttribute("supplier") Supplier supplier){
        supplierService.remove(supplier.getId());
        return "redirect:suppliers";
    }

    @GetMapping("/view-supplier/{id}")
    public ModelAndView viewSupplier(@PathVariable("id") Long id){
        Supplier supplier = supplierService.findById(id);
        Iterable<Material> materials= materialService.findAllBySupplier(supplier);

        ModelAndView modelAndView = new ModelAndView("/supplier/view");
        modelAndView.addObject("materials", materials);
        return modelAndView;
    }

}
