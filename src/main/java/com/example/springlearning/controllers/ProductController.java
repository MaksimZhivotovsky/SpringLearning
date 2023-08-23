package com.example.springlearning.controllers;


import com.example.springlearning.model.Product;
import com.example.springlearning.service.ProductsService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.List;


@Controller
@RequestMapping("/")
public class ProductController {
    private final ProductsService productsService;

    @Autowired
    public ProductController(ProductsService productsService) {
        this.productsService = productsService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/signup")
    public String showSignUpForm(Product product) {
        return "add-product";
    }

    @GetMapping()
    public String showProducts(Model model, String keyword) {
        return findPaginated(1,"title","asc", model, keyword);
    }

    @PostMapping("/add")
    public String addProduct(@Valid Product product, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-product";
        }
        if(product.getTitle().length() > 0) {
            productsService.save(product);
        }
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Product product = productsService.getProductById(id);
        model.addAttribute("product", product);
        return "update-product";
    }

    @PostMapping("/update/{id}")
    public String updateProduct(@PathVariable("id") long id, @Valid Product product, BindingResult result,
                                Model model) {
        if (result.hasErrors()) {
            product.setId(id);
            return "update-product";
        }

        productsService.save(product);
        model.addAttribute("products", productsService.getAllProducts());
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") long id, Model model) {
        Product product = productsService.getProductById(id);
        productsService.deleteById(id);
        model.addAttribute("products", productsService.getAllProducts());
        return "redirect:/";
    }
    @GetMapping("/page/{pageNumber}")
    public String findPaginated(@PathVariable("pageNumber") Integer pageNumber,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir,
                                Model model, String keyword) {
        Integer pageSize = 5;
        Page<Product> page = productsService.findPaginated(pageNumber, pageSize, sortField, sortDir);
        List<Product> products = page.getContent();
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("totalElements", page.getTotalElements());
        model.addAttribute("pageSize", pageSize);
        if (keyword != null) {
            model.addAttribute("products", productsService.findByKeyword(keyword));
        } else {
            model.addAttribute("products", products);
        }
        return "products";
    }
}
