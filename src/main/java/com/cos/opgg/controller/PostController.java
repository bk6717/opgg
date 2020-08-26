package com.cos.opgg.controller;




import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cos.opgg.dto.PostUserDto;
import com.cos.opgg.model.Post;
import com.cos.opgg.repository.PostRepository;
import com.cos.opgg.repository.UserRepository;

@RestController
public class PostController {

	@Autowired
	private PostRepository postRepository;
	

	

	//전체 찾기
 	// user 테이블에 user도 가져와야 함
	@GetMapping("/post")
	public List<Post> find(){
		List<Post> post = postRepository.findAll();
		System.out.println(post);
		return post;
	}
	
	

	
	
//	//상세보기  
//	@GetMapping("/post/{id}")
//	public Post findById(@PathVariable int id) {
//		Post post = postRepository.findById(id);
//		
//		return post;
//	}
	
}
