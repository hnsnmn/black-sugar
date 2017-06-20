package com.sugar.web;

import com.sugar.domain.Question;
import com.sugar.domain.QuestionRepository;
import com.sugar.domain.User;
import com.sun.xml.internal.ws.resources.HttpserverMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * Created by hongseongmin on 2017. 6. 18..
 */
@Controller
@RequestMapping("/questions")
public class QuestionControlloer {
    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/form")
    public String form(HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "/users/loginForm";
        }
        return "/qna/form";
    }

    @PostMapping("")
    public String create(String title, String contents, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "/users/loginForm";
        }
        User sessionUser = HttpSessionUtils.getUserFromSession(session);
        Question newQuestion = new Question(sessionUser, title, contents);
        questionRepository.save(newQuestion);

        return "redirect:/";
    }
    
    @GetMapping("/{id}")
    public String show(@PathVariable Long id, Model model) {
    	model.addAttribute("question", questionRepository.findOne(id));
    	return "/qna/show";
    }
    
    public boolean hasPermission(HttpSession session, Question question) {
    	if (!HttpSessionUtils.isLoginUser(session)) {
    		throw new IllegalStateException("로그인이 필요합니다.");
    	}
    	User loginUser = HttpSessionUtils.getUserFromSession(session);
    	if (!question.isSameUser(loginUser)) {
    		throw new IllegalStateException("자신의 쓴 글만 수정 삭제가 가능합니다.");
    	}
    	
    	return true;
    }
    
    @GetMapping("/{id}/form") 
    public String updatedForm(@PathVariable Long id, Model model, HttpSession session) {
    	Question question = questionRepository.findOne(id);
    	try {
    		hasPermission(session, question);
			model.addAttribute("question", question);
			return "/qna/updatedForm";
    	} catch(IllegalStateException e) {
    		model.addAttribute("errorMessage", e.getMessage());
    		return "/user/login";
    	}
    }
    
    @PutMapping("/{id}")
    public String update(@PathVariable Long id, String title, String contents, Model model, HttpSession session) {
    	Question question = questionRepository.findOne(id);
    	try {
			question.update(title, contents);
			questionRepository.save(question);	
			return String.format("redirect:/questions/%d", id);
    	} catch(IllegalStateException e) {
    		model.addAttribute("errorMessage", e.getMessage());
    		return "/user/login";
    	}
    }
    
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id, Model model, HttpSession session) {
    	Question question = questionRepository.findOne(id);
    	try {
    		hasPermission(session, question);
			questionRepository.delete(id);
			return "redirect:/";
    	} catch(IllegalStateException e) {
    		model.addAttribute("errorMessage", e.getMessage());
    		return "/user/login";
    	}
    }
}
