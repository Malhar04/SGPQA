package com.sgp.qa.controller;

import com.sgp.qa.model.UnverifiedUser;
import com.sgp.qa.service.HomeService;
import com.sgp.qa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;



@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private HomeService homeService;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/signup")
    public String showSignupForm() {
        return "signup";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String username, @RequestParam String email,
                               @RequestParam String password, @RequestParam String confirmPassword,
                               Model model) {
        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match");
            return "signup";
        }

        UnverifiedUser unverifiedUser = new UnverifiedUser();
        unverifiedUser.setUsername(username);
        unverifiedUser.setEmail(email);
        unverifiedUser.setPassword(passwordEncoder.encode(password));
        userService.registerUser(unverifiedUser);

        return "redirect:/verify"; // Redirect to OTP verification
    }


    @GetMapping("/verify")
    public String showVerificationForm() {
        return "verify";
    }

    @PostMapping("/verify")
    public String verifyOtp(@RequestParam("otp") String otp, Model model) {
        boolean isVerified = userService.verifyOtp(otp);
        if (isVerified) {
            model.addAttribute("message", "OTP successfully verified!");
            return "redirect:/home"; // Redirect to home page upon success
        } else {
            model.addAttribute("error", "Invalid OTP. Please try again.");
            return "verify"; // Reload the same verify page upon failure
        }
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String username, @RequestParam String password,Model model) {
        return "signup";
    }

    @GetMapping("/login")
    public String login() {
        return "login"; // Return the name of the login view (HTML page)
    }

    @RequestMapping("/index")
    public String home(Model model)
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName(); // get logged in username
        model.addAttribute("username", username);
        model.addAttribute("tags",homeService.getAllTags());
        return "index"; // Return the name of the home view (HTML page)

    }
}


