package com.sugar.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {
	
	@GetMapping("/helloworld")
	public String welcom(String name, Model model) {
		model.addAttribute("name", name);
		return "welcome";
	}
}
