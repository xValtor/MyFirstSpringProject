package com.example.labs.controllers;

import com.example.labs.DTO.BrandDTO;
import com.example.labs.models.Country;
import com.example.labs.models.FurnitureType;
import com.example.labs.models.Product;
import com.example.labs.models.User;
import com.example.labs.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/products")
public class AdminPanelController {

    private final UserService userService;
    private final ProductService productService;
    private final BrandService brandService;
    private final CountryService countryService;
    private final FurnitureTypeService furnitureTypeService;

    @Autowired
    public AdminPanelController(UserService userService, ProductService productService, BrandService brandService, CountryService countryService, FurnitureTypeService furnitureTypeService) {
        this.userService = userService;
        this.productService = productService;
        this.brandService = brandService;
        this.countryService = countryService;
        this.furnitureTypeService = furnitureTypeService;

    }

    @GetMapping("/adminPanel")
    public String adminPanel(Model model) {
        List<User> users= userService.getAllUsers();
        List<Product> products =productService.findAll();
        model.addAttribute("products", products);
        model.addAttribute("users", users);
        return "AdminPanel";
    }
    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.delete(id);
        return "redirect:/products/adminPanel"; // перенаправление на страницу со списком товаров
    }
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Product product = productService.findById(id); // Получаем продукт по id
        List<FurnitureType> furnitureTypes = furnitureTypeService.getAllFurnitureTypes(); // Получаем все категории
        List<BrandDTO> brands = brandService.getAllBrands(); // Получаем все бренды
        List<Country> countries = countryService.getAllCountries(); // Получаем все страны
        model.addAttribute("product", product);
        model.addAttribute("furnitureTypes", furnitureTypes);
        model.addAttribute("brands", brands);
        model.addAttribute("countries", countries);
        return "/methods/redactor"; // Возвращаем представление для редактирования товара
    }
    @PostMapping("/edit/{id}")
    public String editProduct(@PathVariable Long id,
                              @ModelAttribute Product product,
                              @RequestParam(value = "image", required = false) MultipartFile imagePath) throws IOException {

        // Получаем существующий продукт из БД
        Product existingProduct = productService.findById(id);
        // Если изображение не было изменено, оставляем старое
        if (imagePath != null && !imagePath.isEmpty()) {
            product.setImagePath(imagePath.getBytes()); // Если новое изображение загружено, заменяем старое изображение
        } else {
            product.setImagePath(existingProduct.getImagePath()); // Иначе оставляем старое изображение
        }
        // Обновляем продукт в базе данных
        productService.update(id, product);
        // Перенаправление на страницу со списком товаров
        return "redirect:/products";
    }


}
