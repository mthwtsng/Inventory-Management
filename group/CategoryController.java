package cmpt276.project.group.controllers;

import cmpt276.project.group.models.Category;
import cmpt276.project.group.models.CategoryRepository;

import java.util.Map;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryController() {
        // Constructor code
    }

    @PostMapping("/users/addForm")
    public String addCategory(@RequestParam Map<String, String> newCategory, HttpServletResponse response) {
            System.out.println("Add toy category");
            String newName = newCategory.get("name");
            String newAgeRange = newCategory.get("ageRange");
            int newQuantity = Integer.parseInt(newCategory.get("quantity"));

            categoryRepository.save(new Category(newName, newAgeRange, newQuantity));

            response.setStatus(201);
            return "redirect:/login"; // I will make this redirect to the display page afterwards
            }

    @GetMapping("/addForm")
    public String addForm() {
        return "users/addForm";
            }
    }


