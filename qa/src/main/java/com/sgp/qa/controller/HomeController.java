package com.sgp.qa.controller;

import com.sgp.qa.model.Question;
import com.sgp.qa.model.Tags;
import com.sgp.qa.service.HomeService;
import com.sgp.qa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private HomeService homeService;

    @Autowired
    private UserService userService;

    @RequestMapping("/index")
    public String home(Model model)
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName(); // get logged in username
        model.addAttribute("username", username);
        model.addAttribute("tags",homeService.getAllTags());
        model.addAttribute("question", new Question());
        return "index"; // Return the name of the home view (HTML page)

    }




    @PostMapping("/addquestion")
    public String submitQuestion(@ModelAttribute("question") Question question) {

        // Handle the form submission
        homeService.saveQuestion(question);
        return "redirect:index"; // Redirect to a success page or question list
    }



}
