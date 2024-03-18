package cmpt276.project.group.controllers;

import cmpt276.project.group.models.UserRepository;
import cmpt276.project.group.models.Users;

import java.util.List;
import java.util.Map;

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
    @PostMapping("/login")
    public String login(@RequestParam Map<String,String> formData, Model model, HttpServletRequest request, HttpSession session){
        // processing login
        String username = formData.get("username");
        String pwd = formData.get("password");
        List<Users> userlist = usersRepository.findByUsernameAndPassword(username, pwd);
        if (userlist.isEmpty()){
            model.addAttribute("error","Incorrect username/password");
            return "users/login";
        }
        else {
            // success
            Users user = userlist.get(0);
            request.getSession().setAttribute("session_user", user);
            model.addAttribute("user", user);
            return "users/protected";
        }
    }

    @GetMapping("/logout")
    public String destroySession(HttpServletRequest request){
        request.getSession().invalidate();
        return "/users/home";
    }

    @GetMapping("/homepage")
    public String home() {
        return "users/home"; 
    }

    @GetMapping("/signup")
    public String signup() {
        return "users/signup"; 
    }


    @PostMapping("/signup")
    public String signup(@RequestParam Map<String, String> newUser, Model model, HttpServletResponse response) {
        String username = newUser.get("username");
        String password = newUser.get("password");

        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            return "users/signup"; 
        }

        if (usersRepository.existsByUsername(username)) {
            return "users/signup"; 
        }

    Users user = new Users();
    user.setUsername(username);
    user.setPassword(password);
    usersRepository.save(user);

    response.setStatus(HttpServletResponse.SC_CREATED);
    return "users/protected";
}

}
