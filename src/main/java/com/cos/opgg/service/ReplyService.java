package com.cos.opgg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.cos.opgg.dto.RespDto;
import com.cos.opgg.model.Reply;
import com.cos.opgg.repository.ReplyRepository;

@Service
public class ReplyService {
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
	public RespDto<?> replySave(Reply reply){
		replyRepository.save(reply);
		return new RespDto<String>(HttpStatus.OK.value(), "정상" , null);
	}
	
	//댓글삭제
	public RespDto<?> replyDelete(int id) {
		replyRepository.deleteById(id);
		
		return new  RespDto<String>(HttpStatus.OK.value(), "정상" , null);
	}
}
