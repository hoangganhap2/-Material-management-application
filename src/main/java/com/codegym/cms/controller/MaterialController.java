package com.codegym.cms.controller;


import com.codegym.cms.model.Material;
import com.codegym.cms.model.Supplier;
import com.codegym.cms.service.MaterialService;
import com.codegym.cms.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class MaterialController {

    @Autowired
    private MaterialService materialService;

    @Autowired
    private SupplierService supplierService;

    @ModelAttribute("suppliers")
    public Iterable<Supplier> suppliers() {
        return supplierService.findAll();
    }

//    @GetMapping("/material")
//    public ModelAndView showForm() {
//        ModelAndView modelAndView = new ModelAndView("index");
//        modelAndView.addObject("material", new Material());
//        return modelAndView;
//    }
//
//    @PostMapping("/validateMaterial")
//    public ModelAndView checkValidation(@Validated @ModelAttribute("material") Material material, BindingResult bindingResult) {
//        if (bindingResult.hasFieldErrors()) {
//            ModelAndView modelAndView = new ModelAndView("index");
//            return modelAndView;
//        }
//        ModelAndView modelAndView = new ModelAndView("result");
//        return modelAndView;
//    }

    @GetMapping("/materials")
    public ModelAndView listMaterials(@RequestParam("s") Optional<String>s,@PageableDefault(size = 5, sort = "price") Pageable pageable) {
        Page<Material> materials;
        if(s.isPresent()){
            materials = materialService.findAllByNameContaining(s.get(), pageable);
        } else {
            materials = materialService.findAll(pageable);
        }
        ModelAndView modelAndView = new ModelAndView("/material/list");
        modelAndView.addObject("materials", materials);
        return modelAndView;
    }

    @GetMapping("/create-material")
    public ModelAndView showCreateForm() {
        ModelAndView modelAndView = new ModelAndView("/material/create");
        modelAndView.addObject("material", new Material());
        return modelAndView;
    }

    @PostMapping("/create-material")
    public ModelAndView saveMaterial(@ModelAttribute("material") Material material) {
        materialService.save(material);
        ModelAndView modelAndView = new ModelAndView("/material/create");
        modelAndView.addObject("material", new Material());
        modelAndView.addObject("message","Them vat lieu thanh cong");
        return modelAndView;
    }

    @GetMapping("/edit-material/{id}")
    public ModelAndView showEditMaterial(@PathVariable Long id) {
        Material material = materialService.findById(id);
        if (material != null) {
            ModelAndView modelAndView = new ModelAndView("/material/edit");
            modelAndView.addObject("material",material);
            return modelAndView;
        } else {
            ModelAndView modelAndView = new ModelAndView("/error.404");
            return modelAndView;
        }
    }

    @PostMapping("/edit-material")
    public ModelAndView updateMaterial(@ModelAttribute("material") Material material) {
        materialService.save(material);
        ModelAndView modelAndView = new ModelAndView("/material/edit");
        modelAndView.addObject("material",material);
        modelAndView.addObject("message", "Cap nhat vat lieu thanh cong");
        return modelAndView;
    }

    @GetMapping("/delete-material/{id}")
    public ModelAndView showDeleteForm(@PathVariable Long id){
        Material material = materialService.findById(id);
        if(material != null) {
            ModelAndView modelAndView = new ModelAndView("/material/delete");
            modelAndView.addObject("material", material);
            return modelAndView;

        }else {
            ModelAndView modelAndView = new ModelAndView("/error.404");
            return modelAndView;
        }
    }

    @PostMapping("/delete-material")
    public String deleteCustomer(@ModelAttribute("customer") Material material){
        materialService.remove(material.getId());
        return "redirect:materials";
    }
}
