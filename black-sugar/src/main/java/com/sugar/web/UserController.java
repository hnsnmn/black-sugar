package com.sugar.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sugar.domain.User;
import com.sugar.domain.UserRepository;

@Controller
@RequestMapping("/users")
public class UserController {
	@Autowired
	private UserRepository userRepository ;
	
	@PostMapping("")
	public String create(User user) {
		userRepository.save(user);
		return "redirect:/users";
	}
	
	@GetMapping("")
	public String list(Model model) {
		model.addAttribute("users", userRepository.findAll());
		return "/user/list";
	}

	@GetMapping("/form")
	public String form() {
		return "/user/form";
	}

	@GetMapping("/{id}/form")
	public String updatedform(@PathVariable Long id, Model model) {
		model.addAttribute("user", userRepository.findOne(id));
		return "/user/updatedForm";
	}
	
	@PostMapping("/{id}")
	public String update(@PathVariable Long id, User newUser) {
		User user = userRepository.findOne(id);
		System.out.println(user);
		user.update(newUser);
		userRepository.save(user);
		return "redirect:/users";
	}
}
