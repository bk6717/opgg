package com.cos.opgg.controller.admincontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.opgg.repository.PostRepository;

@Controller
@RequestMapping("/admin/post")
public class AdminPostController {

	@Autowired
	private PostRepository postRepository;
	
	@GetMapping({"","/"})
	public String testpost(Model model) {
		
		model.addAttribute("posts", postRepository.findAll());
		
		
		return "adminPost";
	}
	
	@DeleteMapping("/delete/{id}")
	public @ResponseBody String deletePost(@PathVariable int id ) {
		
		System.out.println("id "+ id );
		postRepository.deleteById(id);
		
		return "성공";
	}
}
