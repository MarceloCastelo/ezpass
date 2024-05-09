package org.example.ezpass.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PagesController {
    @GetMapping("/")
    public String rota(){
        return "Api Funcionando";
    }
}
