package com.cos.opgg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cos.opgg.config.auth.PrincipalDetails;
import com.cos.opgg.dto.RespDto;
import com.cos.opgg.model.Reply;
import com.cos.opgg.repository.ReplyRepository;
import com.cos.opgg.service.ReplyService;

@RestController
@RequestMapping("reply/")
public class ReplyController {

	@Autowired
	ReplyService replyService;
	@Autowired
	ReplyRepository replyRepository;
	
	//댓글 입력
	@PostMapping("writeProc")
	public RespDto<?> save(@AuthenticationPrincipal PrincipalDetails principalDetails, @RequestBody Reply reply){		
		return replyService.replySave(principalDetails, reply);
	}
	
	//댓글 수정
	@PutMapping("updateProc")
	public RespDto<?> update(@AuthenticationPrincipal PrincipalDetails principalDetails, @RequestBody Reply reply){
		return replyService.replyUpdate(principalDetails, reply);
	}
	
	
	//댓글 삭제
	@DeleteMapping("delete/{id}")
	public RespDto<?> Delete(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable int id){
		return replyService.replyDelete(principalDetails, id);
	}
	
//	@GetMapping("/reply")
//	public List<Reply> find() {
//		List<Reply> reply = replyRepostory.findAll();
//		return reply;
//	}
}
