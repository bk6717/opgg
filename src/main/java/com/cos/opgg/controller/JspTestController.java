package com.cos.opgg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.opgg.repository.PostRepository;
import com.cos.opgg.repository.ReplyRepository;
import com.cos.opgg.repository.UserRepository;

@Controller
public class JspTestController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private ReplyRepository replyRepository;
	
	//회원가입
	@GetMapping("/test/login")
	public String login() {
					
		return "loginTest";
	}
	
	@GetMapping("test/jsp/user")
	public String testUser(Model model) {
		
		model.addAttribute("users", userRepository.findAll());
				
		return "adminUser";
	}
	
	@GetMapping("test/jsp/post")
	public String testpost(Model model) {
		
		model.addAttribute("posts", postRepository.findAll());
		
		
		return "adminPost";
	}
	
	@GetMapping("test/jsp/reply")
	public String testreply(Model model) {
		
		model.addAttribute("replies", replyRepository.findAll());
				
		return "adminReply";
	}
	
}
