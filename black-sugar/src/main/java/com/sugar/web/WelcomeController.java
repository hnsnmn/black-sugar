package com.sugar.web;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {
	
	@GetMapping("/helloworld")
	public String welcom(Model model) {
		List<MyModel> repo = Arrays.asList(new MyModel("resque"), new MyModel("hub"), new MyModel("rip"));
		model.addAttribute("repo", repo);
		return "welcome";
	}
	
}
