package com.cos.opgg.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cos.opgg.config.auth.PrincipalDetails;
import com.cos.opgg.dto.RespDto;
import com.cos.opgg.model.Post;
import com.cos.opgg.repository.PostRepository;
import com.cos.opgg.service.PostService;

@RestController
@RequestMapping("/post/")
public class PostController {
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private PostRepository postRepository;
	
//	// 커뮤니티 글목록 보기
//	@GetMapping("/test/post")
//	public List<Post> PostTest() {
//		List<Post> post = postRepository.findAll();
//
//		return post;
//	}

	// 커뮤니티 글 검색
	@GetMapping("search/{content}")
	public List<Post> search(@PathVariable(name = "content") String content) {
		List<Post> post = postRepository.findByContent(content);
		System.out.println(post);
		return post;
	}
	
	//글 전체보기
	@GetMapping("{page}")
	public RespDto<?> findAll(@PathVariable  int page){
		if(page < 0) {
			page = 0;
		}
		
		return postService.findAll(page);
	}
	
	//글 검색
	@GetMapping("find/{content}")
	public RespDto<?> findByContent(@PathVariable String content){
		
		System.out.println(content);
		
		return postService.findByContent(content);
	}
	
	//글 상세보기
	@GetMapping("detail/{id}")
	public RespDto<?> postDetail(@PathVariable int id){
		
		return postService.detail(id);
	}
	
	//글 쓰기
	@PostMapping("writeProc")
	public RespDto<?> writeProc(@AuthenticationPrincipal PrincipalDetails principalDetails, @Valid @RequestBody Post post , BindingResult bindingResult){
		
		 return postService.write(principalDetails, post);
	}
	
	//글 수정
	@PutMapping("update")
	public RespDto<?> updateTitleAndContent(@AuthenticationPrincipal PrincipalDetails principalDetails, @Valid @RequestBody Post post, BindingResult bindingResult){
		
		return postService.updateTitleAndContent(principalDetails, post);
	}
	
	//글삭제//////////////////////나중에 바꿔야함
	@DeleteMapping("delete/{postId}")
	public RespDto<?> deleteById(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable int postId){
		
		return postService.deleteById(principalDetails, postId);
	}
	
	//글 뷰카운트 올리기
	@PutMapping("update/view/{postId}")
	public RespDto<?> updateViewCount(@PathVariable int postId){
		
		return postService.updateViewCount(postId);
	}
	
	//글 좋아요 올리기
	@PutMapping("update/like/{postId}")
	public RespDto<?> updateLikeCount(@PathVariable int postId, HttpServletRequest request){
		
		return postService.updateLikeCount(postId);
	}
	
	
}
 