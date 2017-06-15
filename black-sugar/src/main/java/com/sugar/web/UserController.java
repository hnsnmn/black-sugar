package com.sugar.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sugar.domain.User;
import com.sugar.domain.UserRepository;

@Controller
@RequestMapping("/users")
public class UserController {
	@Autowired
	private UserRepository userRepository ;
	
	@GetMapping("/{id}/form")
	public String updatedform(@PathVariable Long id, Model model, HttpSession session) {
		User sessionedUser = (User) session.getAttribute("sessionUser");
		if (sessionedUser == null) {
			return "redirect:/users/loginForm";
		}
		if (!sessionedUser.getId().equals(id)) {
			throw new IllegalArgumentException("자신의 정보만 수정 가능 합니다.");
		}

		model.addAttribute("user", userRepository.findOne(id));
		return "/user/updatedForm";
	}
	
	@PutMapping("/{id}")
	public String update(@PathVariable Long id, User newUser, HttpSession session) {
		User sessionedUser = (User) session.getAttribute("sessionUser");
		if (sessionedUser == null) {
			return "redirect:/users/loginForm";
		}
		if (!sessionedUser.getId().equals(id)) {
			throw new IllegalArgumentException("자신의 정보만 수정 가능 합니다.");
		}
		
		User user = userRepository.findOne(id);
		System.out.println(user);
		user.update(newUser);
		userRepository.save(user);
		return "redirect:/users";
	}

	@GetMapping("/loginForm")
	public String loginForm() {
		return "/user/login";
	}

	@PostMapping("login")
	public String login(String userId, String password, HttpSession session) {
	    System.out.println("userId : " + userId +":" + password);
        User user = userRepository.findByUserId(userId);
        if (user == null) {
            return "redirect:/users/loginForm";
        }

        if (!password.equals(user.getPassword())) {
            return "redirect:/users/loginForm";
        }
        session.setAttribute("sessionUser", user);
		return "redirect:/";
	}
	
	@GetMapping("logout")
	public String logout(HttpSession session) {
		session.removeAttribute("sessionUser");
		return "redirect:/";
	}

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
}
