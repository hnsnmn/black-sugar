package com.sugar.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.sugar.domain.User;
import com.sugar.domain.UserRepository;

@Controller
public class UserController {
	@Autowired
	private UserRepository userRepository ;
	
	@PostMapping("/user/create")
	public String create(User user) {
		userRepository.save(user);
		return "redirect:/user/list";
	}
	
	
	@GetMapping("/user/list")
	public String list(Model model) {
		model.addAttribute("users", userRepository.findAll());
		return "/user/list";
	}
}
