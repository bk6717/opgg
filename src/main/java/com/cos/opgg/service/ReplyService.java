package com.cos.opgg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

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
	public RespDto<?> replyUpdate(Reply reply){
		Reply replyEntity = replyRepository.findById(reply.getId());
		if (replyEntity == null) {
			//null 처리하기
		}
		Reply replyinput = Reply.builder()
				.id(replyEntity.getId())
				.createDate(replyEntity.getCreateDate())
				.reply(reply.getReply())
				.post(replyEntity.getPost())
				.user(replyEntity.getUser())
				.build();
		replyEntity = replyRepository.save(replyinput);
		return new RespDto<String>(HttpStatus.OK.value(), "정상" , null);
	}
	
	//댓글 입력
	public RespDto<?> replySave(Reply reply, PrincipalDetails principalDetails){
		
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
		
		CommunityDto communityDto = CommunityDto.builder()
				.type(1)
				.post(postEntity)
				.build();
		
		return new RespDto<CommunityDto>(HttpStatus.OK.value(), "정상" , communityDto);
	}
	
	//댓글삭제
	public RespDto<?> replyDelete(int id) {
		replyRepository.deleteById(id);
		
		return new  RespDto<String>(HttpStatus.OK.value(), "정상" , null);
	}
}
