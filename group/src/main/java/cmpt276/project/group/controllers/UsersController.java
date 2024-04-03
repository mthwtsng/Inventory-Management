package cmpt276.project.group.controllers;

import cmpt276.project.group.models.UserRepository;
import cmpt276.project.group.models.Users;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;




@Controller
public class UsersController {
    
    @Autowired
    private UserRepository usersRepository;

    public UsersController() {
        // users.add(new Users("John", "password", 30));
        // users.add(new Users("Jane", "password", 25));
        // users.add(new Users("Doe", "password", 35));

    }

    @GetMapping("/users/all")
    public String getAllUsers(Model model) {
        System.out.println("Hello from all users");
        List<Users> users = usersRepository.findAll(); // db
        model.addAttribute("users", users);
        return "users/all";
    }
    
    @GetMapping("/login")
    public String getLogin(Model model, HttpServletRequest request, HttpSession session){
        Users user = (Users) session.getAttribute("session_user");
        if (user == null){
            return "users/login";
        }
        else {
            model.addAttribute("user",user);
            return "users/protected";
        }
    }
    @GetMapping("/about")
    public String about(){
        return "users/about";
    }
    @GetMapping("/partners")
    public String partners(){
        return "users/partners";
    }
    @GetMapping("/stories")
    public String stories(){
        return "users/stories";
    }
    @PostMapping("/login")
    public String login(@RequestParam Map<String,String> formData, Model model, HttpServletRequest request, HttpSession session){
        // processing login
        String username = formData.get("username");
        String pwd = formData.get("password");
        List<Users> userlist = usersRepository.findByUsernameAndPassword(username, pwd);
        if (userlist.isEmpty()) {
            model.addAttribute("error", "Incorrect username/password");
            return "users/login";
        }else {
            Users user = userlist.get(0); // Retrieve the first user from the list
            request.getSession().setAttribute("session_user", user);
            model.addAttribute("user", user); // Add the single user object to the model
            return "users/protected";
        }
    }

    @GetMapping("/logout")
    public String destroySession(HttpServletRequest request){
        request.getSession().invalidate();
        return "users/home";
    }

    @GetMapping("/homepage")
    public String home() {
        return "users/home"; 
    }

    @GetMapping("/deleteform")
    public String deleteform(){
        return "users/delete";
    }

    @GetMapping("/showall")
    public String showall() {
        return "users/showAll";
    }
    
    @GetMapping("/signup")
    public String signup() {
        return "users/signup"; 
    }

    @GetMapping("/addform")
    public String addform() {
        return "users/addForm"; 
    }

    @GetMapping("/settings")
    public String settings() {
        return "users/settings";
    }

    @GetMapping("/protected")
    public String loggedin(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Users user = (Users) session.getAttribute("session_user");
        if (user == null) {
            // Redirect to login page if user is not logged in
            return "redirect:/login";
        }
        return "users/protected"; 
    }

    @PostMapping("/signup")
    public String signup(@RequestParam Map<String, String> newUser, Model model, HttpServletResponse response) {
        String name =newUser.get("name");
        String username = newUser.get("username");
        String password = newUser.get("password");
        String empCode = newUser.get("empCode");

        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            return "users/signup"; 
        }
        if (!"childhelp2020".equals(empCode)) {
            model.addAttribute("error", "Invalid empCode");
           return "users/signup";

        }

        if (usersRepository.existsByUsername(username)) {
            model.addAttribute("error","Username already taken");

            return "users/signup"; 
        }

    Users user = new Users();
        user.setName(name);
    user.setUsername(username);
    user.setPassword(password);
    usersRepository.save(user);

   // response.setStatus(HttpServletResponse.SC_CREATED);
    return "users/protected";
}
    
    @PostMapping("/changename")
    public String changeusername(@RequestParam Map<String,String> formData, Model model, HttpServletRequest request, HttpSession session) {
        //get the current user
        Users user = (Users)session.getAttribute("session_user");
    
        //get new name from form 
        String newusername = formData.get("newusername");
    
        //save new data 
        user.setUsername(newusername);
        usersRepository.save(user);
        model.addAttribute("message", "Your username has successfully been changed");
    
        return "users/protected";
    }

    @PostMapping("/changepass")
    public String postMethodName(@RequestParam Map<String,String> formData, Model model, HttpServletRequest request, HttpSession session) {
        //get the current user
        Users user = (Users)session.getAttribute("session_user");

        //get old pass
        String oldpass = user.getPassword(); 

        //get form data
        String oldpassform = formData.get("oldpass");
        String newpassform = formData.get("newpass");
        
        //check if old pass that user inputs is the same as the current one
        if(oldpass.equals(oldpassform)){
            //change to the new pass
            user.setPassword(newpassform);
            usersRepository.save(user);
            model.addAttribute("message", "Your password has successfully been changed");
        } else {
            //the passwords dont match 
            model.addAttribute("message", "Your old password does not match");
        }
        return "users/protected";
    }  
}
