package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {
    @GetMapping("hello")
    public String hello(@org.jetbrains.annotations.NotNull Model model){
        model.addAttribute("data","안녕하세요!!");
        return "hello";
    }

}
