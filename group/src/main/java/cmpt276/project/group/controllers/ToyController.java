package cmpt276.project.group.controllers;

import cmpt276.project.group.models.Toy;
import cmpt276.project.group.models.ToyRepository;
import cmpt276.project.group.models.Users;

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
public class ToyController {

    @Autowired
    private ToyRepository toyRepository;

    public ToyController() {
        // Constructor code
    }

    @PostMapping("/users/toyAddForm")
    public String addToy(@RequestParam Map<String, String> newToy, Model model, HttpServletResponse response) {
        System.out.println("Add toy category");
        String newName = newToy.get("name");
        String newAgeRange = newToy.get("ageRange");
        int newQuantity = Integer.parseInt(newToy.get("quantity"));
        // Find the category by the given parameters
        List<Toy> ToyList = toyRepository.findByNameAndAgeRangeAndQuantity(newName, newAgeRange, newQuantity);

        // Check if the category exists
        if (!ToyList.isEmpty()) {
            model.addAttribute("error","Category already exists");
            response.setStatus(404); // Not Found

            return "users/toyAddForm";


        } else {
            toyRepository.save(new Toy(newName, newAgeRange, newQuantity));

            response.setStatus(201);
            return "redirect:/showAllToys"; // I will make this redirect to the display page afterwards
        }
    }

    @GetMapping("/toyAddForm")
    public String toyAddForm(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Users user = (Users) session.getAttribute("session_user");
        if (user == null) {
            return "redirect:/login";
        }
        return "users/toyAddForm";
    }


    @GetMapping("/showAllToys")
    public String getCategories(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Users user = (Users) session.getAttribute("session_user");
        if (user == null) {
            return "redirect:/login";
        }
        List<Toy> toys = toyRepository.findAll(Sort.by(Sort.Direction.ASC, "id")); // Fetch categories from repository
        model.addAttribute("toys", toys); // Add categories to model
        return "users/showAllToys"; // Return the name of the Thymeleaf template
    }


    // @GetMapping("/showAllToys")
    // public String showAllToys() {
    //   return "users/showAllToys";
    // }

    @PostMapping("/showAllToys")
    public String getAllToys(Model model) {
        System.out.println("getting all items");
        List<Toy> toys = toyRepository.findAll();
        model.addAttribute("toys", toys);
        return "showAllToys"; // Make sure this matches the template location
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
        List<Toy> toyList = toyRepository.findByNameAndAgeRangeAndQuantity(oldName, oldAgeRange, oldQuantity);

        // Check if the category exists
        if (!toyList.isEmpty()) {
            Toy itemToDelete = toyList.get(0);
            toyRepository.deleteById(itemToDelete.getId()); // Assuming your Category entity has an "id" field
            // response.setStatus(204); // No content
            //return "redirect:/home.html"; // Return the path without the leading slash
            return "redirect:/showAllToys";
        } else {
            model.addAttribute("error","Category not found");
            response.setStatus(404); // Not Found

            return "users/delete";

        }
    }
    @PostMapping("/update")
    public String updateQuantity(@RequestParam("id") int categoryId, @RequestParam("quantity") int newQuantity) {
        // Find the category by ID
        Optional<Toy> optionalCategory = toyRepository.findById(categoryId);


        if (optionalCategory.isPresent()) {
            Toy toy = optionalCategory.get();
            toy.setQuantity(newQuantity); // Update the quantity
            toyRepository.save(toy); // Save the updated category object
        }


        // Redirect back to the showAllToys page
        return "redirect:/showAllToys";
    }
    @PostMapping("/remove")
    public String deleteItem(@RequestParam("id") int categoryId) {
        // Find the category by ID
        Optional<Toy> optionalCategory = toyRepository.findById(categoryId);


        if (optionalCategory.isPresent()) {
            Toy toy = optionalCategory.get();
            toyRepository.deleteById(toy.getId());
        }


        // Redirect back to the showAllToys page
        return "redirect:/showAllToys";
    }



    @GetMapping("/toyData")
    @ResponseBody 
    public List<Toy> getToyData() {
        return toyRepository.findAll();
    }

}

