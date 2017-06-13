package com.sugar.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
	static List<User> userList = new ArrayList<User>();
	
	@PostMapping("/create")
	public String create(User user) {
		userList.add(user);
		return "redirect:/list";
	}
	
	
	@GetMapping("list")
	public String list(Model model) {
		model.addAttribute("users", userList);
		return "list";
	}
}
