package cmpt276.project.group.controllers;

import cmpt276.project.group.models.Users;
import cmpt276.project.group.models.Clothing;
import cmpt276.project.group.models.ClothingRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Sort;

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

    @PostMapping("/users/clothingAddForm")
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

            return "users/clothingAddForm";


        } else {
            clothingRepository.save(new Clothing(newType,newGender, newAgeRange, newQuantity));

            response.setStatus(201);
            return "redirect:/showAllClothes"; // I will make this redirect to the display page afterwards
        }
    }

    @GetMapping("/clothingAddForm")
    public String addForm(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Users user = (Users) session.getAttribute("session_user");
        if (user == null) {
            return "redirect:/login";
        }
        return "users/clothingAddForm";
    }


    @GetMapping("/showAllClothes")
    public String getClothing(Model model , HttpServletRequest request) {
        HttpSession session = request.getSession();
        Users user = (Users) session.getAttribute("session_user");
        if (user == null) {
            return "redirect:/login";
        }
        List<Clothing> clothes= clothingRepository.findAll(Sort.by(Sort.Direction.ASC, "id")); // Fetch categories from repository
        model.addAttribute("clothes", clothes); // Add categories to model
        return "users/showAllClothes"; // Return the name of the Thymeleaf template
    }




    @PostMapping("/showAllClothes")
    public String getAllClothes(Model model) {
        System.out.println("getting all items");
        List<Clothing> clothes = clothingRepository.findAll();
        model.addAttribute("clothes", clothes);
        return "redirect:/showAllClothes"; // Make sure this matches the template location
    }

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

