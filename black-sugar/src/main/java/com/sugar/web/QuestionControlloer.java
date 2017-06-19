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
    
    @GetMapping("/{id}/form") 
    public String updatedForm(@PathVariable Long id, Model model, HttpSession session) {
    	if (!HttpSessionUtils.isLoginUser(session)) {
    		return "/users/loginForm";
    	}
    	User loginUser = HttpSessionUtils.getUserFromSession(session);
    	Question question = questionRepository.findOne(id);
    	if (!question.isSameUser(loginUser)) {
    		return "/users/loginForm";
    	}

		model.addAttribute("question", question);
    	return "/qna/updatedForm";
    }
    
    @PutMapping("/{id}")
    public String update(@PathVariable Long id, String title, String contents, Model model, HttpSession session) {
    	if (!HttpSessionUtils.isLoginUser(session)) {
    		return "/users/loginForm";
    	}
    	User loginUser = HttpSessionUtils.getUserFromSession(session);
    	Question question = questionRepository.findOne(id);
    	if (!question.isSameUser(loginUser)) {
    		return "/users/loginForm";
    	}

    	question.update(title, contents);
    	questionRepository.save(question);	
    	return String.format("redirect:/questions/%d", id);
    }
    
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id, HttpSession session) {
    	if (!HttpSessionUtils.isLoginUser(session)) {
    		return "/users/loginForm";
    	}
    	User loginUser = HttpSessionUtils.getUserFromSession(session);
    	Question question = questionRepository.findOne(id);
    	if (!question.isSameUser(loginUser)) {
    		return "/users/loginForm";
    	}
    	questionRepository.delete(id);
    	return "redirect:/";
    }
}
