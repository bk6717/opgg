package com.cos.opgg.controller.admincontroller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.opgg.model.Post;
import com.cos.opgg.model.User;
import com.cos.opgg.repository.PostRepository;

@Controller
@RequestMapping("/admin/post")
public class AdminPostController {

	@Autowired
	private PostRepository postRepository;
	
	//유저 검색기능
	@PostMapping("/search")
	public @ResponseBody List<Post> search(@RequestBody Map<String, String> data) {
		
		return postRepository.findByContent(data.get("title"));
	}
	
    //게시글 메인
	@GetMapping({"","/"})
	public String post(Model model) {
		
		model.addAttribute("posts", postRepository.findAll());
		
		
		return "adminPost";
	}
	
	//게시글 삭제
	@DeleteMapping("/delete/{id}")
	public @ResponseBody String deletePost(@PathVariable int id ) {
		
		System.out.println("id "+ id );
		postRepository.deleteById(id);
		
		return "성공";
	}

}
