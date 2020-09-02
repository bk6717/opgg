package com.cos.opgg.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.opgg.config.auth.PrincipalDetails;
import com.cos.opgg.config.handler.exception.MyPostWriteException;
import com.cos.opgg.dto.CommunityDto;
import com.cos.opgg.dto.RespDto;
import com.cos.opgg.model.Post;
import com.cos.opgg.repository.PostRepository;

@Service
public class PostService {

	@Autowired
	PostRepository postRepository;

	// 글전체보기 (페이지 처리)
	@Transactional(readOnly = true)
	public RespDto<?> findAll(int page) {
		PageRequest pageRequest = PageRequest.of(page, 10, Sort.by(Direction.DESC, "id"));

		Page<Post> pagePost = postRepository.findAll(pageRequest);
		List<Post> posts = pagePost.getContent();

		List<CommunityDto> communityDtos = new ArrayList<>();

		for (Post post : posts) {

			CommunityDto communityDto = CommunityDto.builder().type(1).post(post).build();

			communityDtos.add(communityDto);

		}

		if (posts.size() < 10) {

			CommunityDto communityDtoFooter = CommunityDto.builder().type(2).build();

			communityDtos.add(communityDtoFooter);

			return new RespDto<List<CommunityDto>>(HttpStatus.NO_CONTENT.value(), "더이상 값이 없습니다.", communityDtos);
		}

		CommunityDto communityDtoFooter = CommunityDto.builder().type(2).build();

		communityDtos.add(communityDtoFooter);

		if (page == 0) {
			return new RespDto<List<CommunityDto>>(HttpStatus.CREATED.value(), "정상", communityDtos);
		}

		return new RespDto<List<CommunityDto>>(HttpStatus.OK.value(), "정상", communityDtos);
	}

	// 글전체보기 (페이지 처리)
	@Transactional(readOnly = true)
	public RespDto<?> findByContent(String content) {

		List<Post> posts = postRepository.findByContent(content);

		List<CommunityDto> communityDtos = new ArrayList<>();

		for (Post post : posts) {

			CommunityDto communityDto = CommunityDto.builder().type(1).post(post).build();

			communityDtos.add(communityDto);

		}

		CommunityDto communityDtoFooter = CommunityDto.builder().type(2).build();

		communityDtos.add(communityDtoFooter);

		return new RespDto<List<CommunityDto>>(HttpStatus.NO_CONTENT.value(), "더이상 값이 없습니다.", communityDtos);
	}

	// 글 상세보기
	@Transactional(readOnly = true)
	public RespDto<?> detail(int id) {
		Post postEntity = postRepository.findById(id);
		if(postEntity == null) {
			return new RespDto<CommunityDto>(HttpStatus.BAD_REQUEST.value(), "해당 글이 없습니다.", null);
		}
		CommunityDto communityDto = CommunityDto.builder().type(1).post(postEntity).build();
		return new RespDto<CommunityDto>(HttpStatus.CREATED.value(), "정상", communityDto);
	}

	// 글쓰기
	@Transactional
	public RespDto<?> write(PrincipalDetails principalDetails, Post post) {

		// 토큰의 유저를 글쓴이로 설정한다
		post.setUser(principalDetails.getUser());

		Post postEntity = null;
		
		try {
			postEntity = postRepository.save(post);			
		} catch (Exception e) {
			throw new MyPostWriteException();
		}
		

		if (postEntity == null) {
			return new RespDto<String>(HttpStatus.BAD_REQUEST.value(), "에러가 발생하였습니다.", null);
		}

		return new RespDto<String>(HttpStatus.OK.value(), "정상", null);

	}

	// 글 수정
	@Transactional
	public RespDto<?> updateTitleAndContent(PrincipalDetails principalDetails, Post post) {
		
		Post postEntity = postRepository.findById(post.getId());
		
		if(postEntity == null) {
			return new RespDto<String>(HttpStatus.BAD_REQUEST.value(), "유저 아이디가 다릅니다.", null);
		}

		
		if (postEntity.getUser().getId() == principalDetails.getUser().getId()) {
			// 유저 아이디를 principalDetails 것을 사용한다
			post.setUser(principalDetails.getUser());

			postEntity.setTitle(post.getTitle());
			postEntity.setContent(post.getContent());

			return new RespDto<String>(HttpStatus.OK.value(), "정상", null);

		} else {

			return new RespDto<String>(HttpStatus.BAD_REQUEST.value(), "유저 아이디가 다릅니다.", null);
		}
	}

	// 글 삭제
	@Transactional
	public RespDto<?> deleteById(PrincipalDetails principalDetails, int postId) {

		Post post = postRepository.findById(postId);

		int result;
		if (post == null) {
			result = -2; // 없는 게시글
		} else if (post.getUser().getId() == principalDetails.getUser().getId()) {
			postRepository.deleteById(postId);
			result = 1; // 정상처리
		} else {
			result = -1; // 사용자 정보가 틀림
		}

		if (result == -1) {
			return new RespDto<String>(HttpStatus.BAD_REQUEST.value(), "유저정보가 일치하지 않습니다.", null);

		} else if (result == -2) {
			return new RespDto<String>(HttpStatus.BAD_REQUEST.value(), "게시글이 존재하지 않습니다.", null);

		} else if (result == 1) {
			return new RespDto<String>(HttpStatus.NO_CONTENT.value(), "정상", null);
		}

		return new RespDto<String>(HttpStatus.NO_CONTENT.value(), "삭제에 실패했습니다.", null);

	}

	// 글 뷰카운트 올리기
	@Transactional
	public RespDto<?> updateViewCount(int postId) {

		Post postEntity = postRepository.findById(postId);

		if (postEntity == null) {
			return new RespDto<String>(HttpStatus.BAD_REQUEST.value(), "잘못된 요청입니다.", null);
		}

		postEntity.setViewCount(postEntity.getViewCount() + 1);

		return new RespDto<String>(HttpStatus.OK.value(), "정상", null);

	}

	// 글 좋아요 올리기
	@Transactional
	public RespDto<?> updateLikeCount(int postId) {

		Post postEntity = postRepository.findById(postId);

		if (postEntity == null) {
			return new RespDto<String>(HttpStatus.BAD_REQUEST.value(), "잘못된 요청입니다.", null);
		}

		postEntity.setLikeCount(postEntity.getLikeCount() + 1);

		Post postEntity1 = postRepository.findById(postId);

		CommunityDto communityDto = CommunityDto.builder().type(1).post(postEntity).build();
		return new RespDto<CommunityDto>(HttpStatus.CREATED.value(), "정상", communityDto);

//		return new RespDto<String>(HttpStatus.OK.value(), "정상" , null); 

	}
}
