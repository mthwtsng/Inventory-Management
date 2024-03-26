package cmpt276.project.group.controllers;

import cmpt276.project.group.models.Clothing;
import cmpt276.project.group.models.ClothingRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ClothingController {

    @Autowired
    private ClothingRepository clothingRepository;

    public ClothingController() {
        // Constructor code
    }

    @PostMapping("/users/addForm")
    public String addClothingCategory(@RequestParam Map<String, String> newClothingCategory, Model model, HttpServletResponse response) {
        System.out.println("Add clothing category");
        String newType = newClothingCategory.get("type");
        String newGender = newClothingCategory.get("gender");
        String newAgeRange = newClothingCategory.get("ageRange");
        int newQuantity = Integer.parseInt(newClothingCategory.get("quantity"));
        // Find the category by the given parameters
        List<Clothing> clothingList = clothingRepository.findByTypeAndGenderAndAgeRangeAndQuantity(newType,newGender, newAgeRange, newQuantity);

        // Check if the category exists
        if (!clothingList.isEmpty()) {
            model.addAttribute("error","Category already exists");
            response.setStatus(404); // Not Found

            return "users/addForm";


        } else {
            clothingg Repository.save(new Category(newName, newAgeRange, newQuantity));

            response.setStatus(201);
            return "redirect:/showAll"; // I will make this redirect to the display page afterwards
        }
    }
    @GetMapping("/addForm")
    public String addForm() {
        return "users/addForm";
    }


    @GetMapping("/showAll")
    public String getCategories(Model model) {
        List<Category> categories = categoryRepository.findAll(Sort.by(Sort.Direction.ASC, "id")); // Fetch categories from repository
        model.addAttribute("categories", categories); // Add categories to model
        return "users/showAll"; // Return the name of the Thymeleaf template
    }


   /* @GetMapping("/showAll")
    @ResponseBody // This annotation tells Spring MVC to serialize the return value directly to the response body
    public ResponseEntity<List<Category>> getCategories() {
        List<Category> categories = categoryRepository.findAll(Sort.by(Sort.Direction.ASC, "id")); // Fetch categories from repository

        // Return the list of categories with a 200 OK status
        return ResponseEntity.ok().body(categories);
    }
    */

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
    @PostMapping("/update")
    public String updateQuantity(@RequestParam("id") int categoryId, @RequestParam("quantity") int newQuantity) {
        // Find the category by ID
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);


        if (optionalCategory.isPresent()) {
            Category category = optionalCategory.get();
            category.setQuantity(newQuantity); // Update the quantity
            categoryRepository.save(category); // Save the updated category object
        }


        // Redirect back to the showAll page
        return "redirect:/showAll";
    }
    @PostMapping("/remove")
    public String deleteItem(@RequestParam("id") int categoryId) {
        // Find the category by ID
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);


        if (optionalCategory.isPresent()) {
            Category category = optionalCategory.get();
            categoryRepository.deleteById(category.getId());
        }


        // Redirect back to the showAll page
        return "redirect:/showAll";
    }
}

