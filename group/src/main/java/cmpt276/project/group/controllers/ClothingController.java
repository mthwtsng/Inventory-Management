package cmpt276.project.group.controllers;

import cmpt276.project.group.models.Category;
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

    @PostMapping("/users/addForm2")
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
            model.addAttribute("error","Clothing category already exists");
            response.setStatus(404); // Not Found

            return "users/addForm2";


        } else {
            clothingRepository.save(new Clothing(newType,newGender, newAgeRange, newQuantity));

            response.setStatus(201);
            return "redirect:/showAllClothes"; // I will make this redirect to the display page afterwards
        }
    }
    @GetMapping("/addForm2")
    public String addForm() {
        return "users/addForm2";
    }


    @GetMapping("/showAllClothes")
    public String getClothing(Model model) {
        List<Clothing> clothes= clothingRepository.findAll(Sort.by(Sort.Direction.ASC, "id")); // Fetch categories from repository
        model.addAttribute("clothes", clothes); // Add categories to model
        return "users/showAllClothes"; // Return the name of the Thymeleaf template
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

    @PostMapping("/showAllClothes")
    public String getAllClothes(Model model) {
        System.out.println("getting all items");
        List<Clothing> clothes = clothingRepository.findAll();
        model.addAttribute("clothes", clothes);
        return "redirect:/showAllClothes"; // Make sure this matches the template location
    }
   /* @GetMapping("/delete")
    public String deleteForm() {
        return "users/delete";
    }
    @PostMapping("/users/delete")
    public String deleteItem(@RequestParam Map<String, String> oldClothing, Model model, HttpServletResponse response) {
        System.out.println("delete category");
        String oldType = oldClothing.get("type");
        String oldGender = oldClothing.get("gender");
        String oldAgeRange = oldClothing.get("ageRange");
        int oldQuantity = Integer.parseInt(oldClothing.get("quantity"));

        // Find the category by the given parameters
        List<Clothing> clothingList = clothingRepository.findByTypeAndGenderAndAgeRangeAndQuantity(oldType,oldGender, oldAgeRange, oldQuantity);

        // Check if the category exists
        if (!clothingList.isEmpty()) {
            Clothing itemToDelete = clothingList.get(0);
            clothingRepository.deleteById(itemToDelete.getId()); // Assuming your Category entity has an "id" field
            // response.setStatus(204); // No content
            //return "redirect:/home.html"; // Return the path without the leading slash
            return "redirect:/showAll";
        } else {
            model.addAttribute("error","Category not found");
            response.setStatus(404); // Not Found

            return "users/delete";

        }
    }
    */
    @PostMapping("/update2")
    public String updateQuantity(@RequestParam("id") int clothingId, @RequestParam("quantity") int newQuantity) {
        // Find the category by ID
        Optional<Clothing> optionalClothing = clothingRepository.findById(clothingId);


        if (optionalClothing.isPresent()) {
            Clothing clothing = optionalClothing.get();
            clothing.setQuantity(newQuantity); // Update the quantity
            clothingRepository.save(clothing); // Save the updated category object
        }


        // Redirect back to the showAll page
        return "redirect:/showAllClothes";
    }
    @PostMapping("/remove2")
    public String deleteClothingItem(@RequestParam("id") int clothingId) {
        // Find the category by ID
        Optional<Clothing> optionalClothing = clothingRepository.findById(clothingId);


        if (optionalClothing.isPresent()) {
            Clothing clothing = optionalClothing.get();
            clothingRepository.deleteById(clothing.getId());
        }


        // Redirect back to the showAll page
        return "redirect:/showAllClothes";
    }

    @GetMapping("/clothingData")
    @ResponseBody 
    public List<Clothing> getCategoryData() {
        return clothingRepository.findAll();
    }
}

