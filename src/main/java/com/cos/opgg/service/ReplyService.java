package com.cos.opgg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.opgg.config.auth.PrincipalDetails;
import com.cos.opgg.dto.CommunityDto;
import com.cos.opgg.dto.RespDto;
import com.cos.opgg.model.Post;
import com.cos.opgg.model.Reply;
import com.cos.opgg.model.User;
import com.cos.opgg.repository.PostRepository;
import com.cos.opgg.repository.ReplyRepository;
import com.cos.opgg.repository.UserRepository;

@Service
public class ReplyService {
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PostRepository postRepository;
	
	@Autowired
	ReplyRepository replyRepository;
	
	
	//댓글 수정
	@Transactional
	public RespDto<?> replyUpdate(PrincipalDetails principalDetails, Reply reply){
		
		Reply replyEntity = replyRepository.findById(reply.getId());
		
		if (replyEntity == null) {
			
			return new RespDto<String>(HttpStatus.BAD_REQUEST.value(), "잘못된 요청입니다." , null);
			
		} else if(replyEntity.getUser().getId() != principalDetails.getUser().getId()) {
			
			return new RespDto<String>(HttpStatus.BAD_REQUEST.value(), "유저가 일치하지 않습니다." , null);

		} else {
			
			replyEntity.setReply(reply.getReply());
			
			return new RespDto<String>(HttpStatus.OK.value(), "정상" , null);
		}
		
	}
	
	//댓글 입력
	@Transactional
	public RespDto<?> replySave(PrincipalDetails principalDetails, Reply reply){
		
		User userEntity = userRepository.findByUsername(principalDetails.getUsername());
		
		User authUser = User.builder()
				.id(userEntity.getId()) // jwt토큰으로 확인한 아이디가져오기
				.build();
				
		
		Reply authReply = Reply.builder()
				.reply(reply.getReply())
				.post(reply.getPost())
				.user(authUser)
				.build();
		
		Reply replyEntity = replyRepository.save(authReply);
		
		Post postEntity = postRepository.findById(replyEntity.getPost().getId());
		
		for (Reply reply2: postEntity.getReplies()) {
			System.out.println(reply2);
		}
		
		CommunityDto communityDto = CommunityDto.builder()
				.type(1)
				.post(postEntity)
				.build();
		
		return new RespDto<CommunityDto>(HttpStatus.OK.value(), "정상" , communityDto);
	}
	
	//댓글삭제
	@Transactional
	public RespDto<?> replyDelete(PrincipalDetails principalDetails, int id) {
		
		Reply replyEntity = replyRepository.findById(id);
		
		if(replyEntity == null) {
			
			return new  RespDto<String>(HttpStatus.BAD_REQUEST.value(), "잘못된 요청입니다." , null);
			
		} else if(replyEntity.getUser().getId() != principalDetails.getUser().getId()) {
			
			return new  RespDto<String>(HttpStatus.BAD_REQUEST.value(), "유저가 일치하지 않습니다." , null);
		}
		
		replyRepository.deleteById(id);
		
		Post postEntity = postRepository.findById(replyEntity.getPost().getId());
		
		CommunityDto communityDto = CommunityDto.builder()
				.type(1)
				.post(postEntity)
				.build();
		
		return new  RespDto<CommunityDto>(HttpStatus.OK.value(), "정상" , communityDto);
	}
}
