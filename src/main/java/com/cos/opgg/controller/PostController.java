package com.cos.opgg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cos.opgg.dto.RespDto;
import com.cos.opgg.model.Post;
import com.cos.opgg.service.PostService;

@RestController
@RequestMapping("/post/")
public class PostController {
	
	@Autowired
	private PostService postService;
	
	//글 전체보기
	@GetMapping("{page}")
	public RespDto<?> findAll(@PathVariable int page){
		
		return postService.findAll(page);
	}
	//글 상세보기
	@GetMapping("detail/{id}")
	public RespDto<?> postDetail(@PathVariable int id){
		return postService.detail(id);
	}
	
	//글 등록 
	@PostMapping("/writeProc")
	public RespDto<?> writeProc(@RequestBody Post post){
		postService.write(post);
		return new RespDto<String>(HttpStatus.OK. value(), "정상" , null); 
	}
	
	//글 수정
	@PutMapping("/update")
	public RespDto<?> updateTitleAndContent(@RequestBody Post post){
		postService.updateTitleAndContent(post);
		return new RespDto<String>(HttpStatus.OK. value(), "정상" , null); 
	}
	
	//글삭제//////////////////////나중에 바꿔야함 
	@DeleteMapping("/delete/{id}")
	public RespDto<?> deleteById(@PathVariable int id){
		postService.deleteById(id);
		return new RespDto<String>(HttpStatus.OK. value(), "정상" , null);
	}
	
	
}
 