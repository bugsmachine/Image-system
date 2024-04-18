package org.example.imagesystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FrontendController {

    @GetMapping("/test")
    public String getFrontend() {
        return "test";
    }
}
