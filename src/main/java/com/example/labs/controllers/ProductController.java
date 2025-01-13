package com.example.labs.controllers;

import com.example.labs.DTO.BrandDTO;
import com.example.labs.models.*;
import com.example.labs.services.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
public class ProductController {

    private final ProductService productService;
    private final UserService userService;
    private final BrandService brandService;
    private final FurnitureTypeService furnitureTypeService;
    private final CountryService countryService;

    @Autowired
    public ProductController(ProductService productService,
                             UserService userService,
                             BrandService brandService,
                             FurnitureTypeService furnitureTypeService,
                             CountryService countryService) {
        this.productService = productService;
        this.userService = userService;
        this.brandService = brandService;
        this.furnitureTypeService = furnitureTypeService;
        this.countryService = countryService;
    }
    @GetMapping("/products/new")
    public String showProductForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("brands", brandService.getAllBrands());
        model.addAttribute("furnitureTypes", furnitureTypeService.getAllFurnitureTypes());
        model.addAttribute("countries", countryService.getAllCountries());
        return "/methods/product_form"; // имя шаблона Thymeleaf
    }

    @PostMapping("/products")
    public String addProduct(@ModelAttribute Product product,
                             @RequestParam("image") MultipartFile imagePath) throws IOException {
        product.setImagePath(imagePath.getBytes());
        productService.save(product);
        return "redirect:/products"; // перенаправление на страницу со списком товаров
    }
    @GetMapping("/products")
    public String getProducts(@RequestParam(required = false) String search,
                          @RequestParam(required = false) String sort,
                          @RequestParam(required = false) List<String> furnitureType,
                          @RequestParam(required = false) List<String> country,
                          @RequestParam(required = false) List<String> brand,
                          @RequestParam(required = false) List<String> priceRange,
                          @RequestParam(required = false) String priceFrom,
                          @RequestParam(required = false) String priceTo,
                          Model model) {
    // Инициализация списка продуктов
    List<Product> products = productService.findAll();

    // Применение поиска
    if (search != null && !search.isEmpty()) {
        products = productService.searchProducts(search);
    }

    // Применение сортировки
    if (sort != null && !sort.isEmpty()) {
        products = productService.sortProducts(sort);
    }

    // Применение фильтров по брендам, типам мебели и странам
    if (furnitureType != null && !furnitureType.isEmpty()) {
        products = productService.getProductsByFurnitureTypes(furnitureType, products);
    }

    if (country != null && !country.isEmpty()) {
        products = productService.getProductsByCountries(country, products);
    }

    if (brand != null && !brand.isEmpty()) {
        products = productService.getProductsByBrands(brand, products);
    }

    // Применение фильтрации по цене
    if (priceRange != null && !priceRange.isEmpty()) {
        products = productService.getProductsByPriceRange(priceRange, products);
    } else if (priceFrom != null && !priceFrom.isEmpty() && priceTo != null && !priceTo.isEmpty()) {
        products = productService.getProductsByPriceFromTo(Integer.parseInt(priceFrom), Integer.parseInt(priceTo), products);
    }

    // Добавляем отфильтрованные товары в модель
    model.addAttribute("products", products);
    model.addAttribute("search", search);
    model.addAttribute("sort", sort);
    model.addAttribute("selectedFurnitureTypes", furnitureType);
    model.addAttribute("selectedCountry", country);
    model.addAttribute("selectedBrand", brand);
    model.addAttribute("selectedPriceRange", priceRange);

    // Получаем список пользователей (если нужно)
    List<User> users = userService.getAllUsers();
    model.addAttribute("users", users);


    return "index";
}

    @GetMapping("/products/item/{id}")
    public String showEditProductForm(@PathVariable Long id, Model model) {
        Product product = productService.findById(id);
        model.addAttribute("product", product);
        return "/methods/product_cart"; // используем тот же шаблон, что и для добавления
    }



}
