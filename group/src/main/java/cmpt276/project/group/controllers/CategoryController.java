package cmpt276.project.group.controllers;

import cmpt276.project.group.models.Category;
import cmpt276.project.group.models.CategoryRepository;

import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public String addCategory(@RequestParam Map<String, String> newCategory, Model model, HttpServletResponse response) {
        System.out.println("Add toy category");
        String newName = newCategory.get("name");
        String newAgeRange = newCategory.get("ageRange");
        int newQuantity = Integer.parseInt(newCategory.get("quantity"));
        // Find the category by the given parameters
        List<Category> categoryList = categoryRepository.findByNameAndAgeRangeAndQuantity(newName, newAgeRange, newQuantity);

        // Check if the category exists
        if (!categoryList.isEmpty()) {
            model.addAttribute("error","Category already exists");
            response.setStatus(404); // Not Found

            return "users/addForm";


        } else {
            categoryRepository.save(new Category(newName, newAgeRange, newQuantity));

            response.setStatus(201);
            return "redirect:/login"; // I will make this redirect to the display page afterwards
        }
    }
    @GetMapping("/addForm")
    public String addForm() {
        return "users/addForm";
    }
    @GetMapping("/showAll")
    public String getCategories(Model model) {
        List<Category> categories = categoryRepository.findAll(); // Fetch categories from repository
        model.addAttribute("categories", categories); // Add categories to model
        return "users/showAll"; // Return the name of the Thymeleaf template
    }

   // @GetMapping("/showAll")
   // public String showAll() {
     //   return "users/showAll";
   // }

    @PostMapping("/showAll")
    public String getAllCategories(Model model) {
        System.out.println("getting all items");
        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);
        return "showAll"; // Make sure this matches the template location
    }
    @GetMapping("/delete")
    public String deleteForm() {
        return "users/delete";
    }
    @PostMapping("/users/delete")
    public String deleteItem(@RequestParam Map<String, String> oldCategory, Model model, HttpServletResponse response) {
        System.out.println("delete category");
        String oldName = oldCategory.get("name");
        String oldAgeRange = oldCategory.get("ageRange");
        int oldQuantity = Integer.parseInt(oldCategory.get("quantity"));

        // Find the category by the given parameters
        List<Category> categoryList = categoryRepository.findByNameAndAgeRangeAndQuantity(oldName, oldAgeRange, oldQuantity);

        // Check if the category exists
        if (!categoryList.isEmpty()) {
            Category itemToDelete = categoryList.get(0);
            categoryRepository.deleteById(itemToDelete.getId()); // Assuming your Category entity has an "id" field
            // response.setStatus(204); // No content
            //return "redirect:/home.html"; // Return the path without the leading slash
            return "redirect:/showAll";
        } else {
            model.addAttribute("error","Category not found");
            response.setStatus(404); // Not Found

            return "users/delete";

        }
    }
}