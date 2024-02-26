package com.MJTest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MjTestController {
    @GetMapping("/hi")
    public String niceMeetYou(Model model){
        model.addAttribute("username","hongpark");
        return "greetings";
    }
}
