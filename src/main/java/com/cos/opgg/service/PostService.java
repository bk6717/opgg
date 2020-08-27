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

import com.cos.opgg.dto.CommunityDto;
import com.cos.opgg.dto.RespDto;
import com.cos.opgg.model.Post;
import com.cos.opgg.repository.PostRepository;

@Service
public class PostService {
	
	@Autowired
	PostRepository postRepository;
	
	//글전체보기 (페이지 처리)
	@Transactional
	public RespDto<?> findAll(int page){
		PageRequest pageRequest = PageRequest.of(page, 40, Sort.by(Direction.DESC, "id"));

		Page<Post> pagePost = postRepository.findAll(pageRequest);
		List<Post> posts = pagePost.getContent();
		

		List<CommunityDto> communityDtos = new ArrayList<>();

		for (Post post : posts) {

			CommunityDto communityDto = CommunityDto.builder().type(1).post(post).build();

			communityDtos.add(communityDto);

		}
		
		if(posts.size() < 40) {
			
			CommunityDto communityDtoFooter = CommunityDto.builder().type(2).build();

			communityDtos.add(communityDtoFooter);
			
			return new RespDto<List<CommunityDto>>(HttpStatus.NO_CONTENT.value(),"더이상 값이 없습니다.",communityDtos);
		}

		CommunityDto communityDtoFooter = CommunityDto.builder().type(2).build();

		communityDtos.add(communityDtoFooter);
		
		if(page == 0) {
			return new RespDto<List<CommunityDto>>(HttpStatus.CREATED.value(),"정상",communityDtos);
		}

		return new RespDto<List<CommunityDto>>(HttpStatus.OK.value(),"정상",communityDtos);
	}
	
	//글 상세보기
	@Transactional
	public RespDto<?> detail(int id) {
		Post post = postRepository.findById(id);
		CommunityDto communityDto = CommunityDto.builder()
				.type(1)
				.post(post)
				.build();
		return new RespDto<CommunityDto>(HttpStatus.OK.value(), "정상" , communityDto);
	}
	//글쓰기
	@Transactional
	public void write(Post post) {
		postRepository.save(post);
	}
	
	//글 수정
	@Transactional
	public void updateTitleAndContent(Post post) {
		Post postEntity = postRepository.findById(post.getId());
		postEntity.setTitle(post.getTitle());
		postEntity.setContent(post.getContent());
	}
	
	//글 삭제
	@Transactional
	public void deleteById(int id) {
		postRepository.deleteById(id);
		
	}
}
