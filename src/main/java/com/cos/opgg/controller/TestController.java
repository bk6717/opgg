package com.cos.opgg.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.opgg.api.dto.InfoDto;
import com.cos.opgg.dto.CommunityDto;
import com.cos.opgg.dto.RespDto;
import com.cos.opgg.model.Post;
import com.cos.opgg.model.Reply;
import com.cos.opgg.repository.PostRepository;
import com.cos.opgg.repository.ReplyRepository;
import com.cos.opgg.repository.SummonerRepository;
import com.cos.opgg.service.ApiService;

@RestController
public class TestController {

	@Autowired
	SummonerRepository summonerRepository;
	@Autowired
	PostRepository postRepository;
	@Autowired
	ReplyRepository replyRepostory;
	@Autowired
	ApiService apiService;
	
	//댓글 삭제
	@DeleteMapping("test/reply/delete/{id}")
	public RespDto<?> replyDelete(@PathVariable int id){
		
		replyRepostory.deleteById(id);
		
		return new RespDto<String>(HttpStatus.OK.value(), "정상" , null);
	}
	
	//댓글 수정
	@PutMapping("test/reply/updateProc")
	public RespDto<?> replyUpdate(@RequestBody Reply reply){
		
		Reply replyEntity = replyRepostory.findById(reply.getId());
		
		if(replyEntity == null) {
			// 널처리
		}
		
		Reply replyinput = Reply.builder()
				.id(replyEntity.getId())
				.createDate(replyEntity.getCreateDate())
				.reply(reply.getReply())
				.post(replyEntity.getPost())
				.user(replyEntity.getUser())
				.build();
		
		replyEntity = replyRepostory.save(replyinput);
		return new RespDto<String>(HttpStatus.OK.value(), "정상" , null);
	}

	//댓글 입력
	@PostMapping("/test/reply/writeProc")
	public RespDto<?> testReply(@RequestBody Reply reply){
		
		Reply replyEntity = replyRepostory.save(reply);
		
		
		
		return new RespDto<String>(HttpStatus.OK.value(), "정상" , null);
	}
	
	//글삭제 
	@DeleteMapping("/test/post/deleteProc/{id}")
	public RespDto<?> postDelete(@PathVariable int id){
		
		postRepository.deleteById(id);
		
		return new RespDto<String>(HttpStatus.OK.value(), "정상" , null);
	}
	//글 수정
	@PutMapping("/test/post/updateProc")
	public RespDto<?> testUpdate(@RequestBody Post post){
		
		Post postEntity = postRepository.findById(post.getId());
		
		
		
//		if(postEntity == null) {
//			return new RespDto<String>(HttpStatus.BAD_REQUEST.value(), "잘못된 요청입니다." , null);
//		}
		
		Post postInput = Post.builder()
				.id(postEntity.getId())
				.title(post.getTitle())
				.content(post.getContent())
				.createDate(postEntity.getCreateDate())
				.likeCount(postEntity.getLikeCount())
				.viewCount(postEntity.getViewCount())
				.user(postEntity.getUser())
				.build();
		
		postEntity = postRepository.save(postInput);
		
		return new RespDto<String>(HttpStatus.OK. value(), "정상" , null);
	}
	
	//커뮤니티 글쓰기
	@PostMapping("/test/post/writeProc")
	public RespDto<?> testWrite(@RequestBody Post post){
		
		Post postEntity = postRepository.save(post);
		
		return new RespDto<String>(HttpStatus.OK.value(), "정상" , null);
	}
	
	//상세보기 
	@GetMapping("/test/post/detail/{postId}")
	public RespDto<?> testDetail(@PathVariable int postId){
		
		Post post = postRepository.findById(postId);
		CommunityDto communityDto = CommunityDto.builder()
				.type(1)
				.post(post)
				.build();
		
		
		return new RespDto<CommunityDto>(HttpStatus.OK.value(), "정상" , communityDto);
	}
	//글전체 보기
	@GetMapping("/test/post/{page}")
	public RespDto<?> testCommuniy(@PathVariable int page) {

		PageRequest pageRequest = PageRequest.of(page, 40, Sort.by(Direction.DESC, "id"));

		Page<Post> pagePost = postRepository.findAll(pageRequest);
		List<Post> posts = pagePost.getContent();
		
		List<CommunityDto> communityDtos = new ArrayList<>();

		
		for (Post post : posts) {

			CommunityDto communityDto = CommunityDto.builder().type(1).post(post).build();

			communityDtos.add(communityDto);

		}
		
		if(posts.size() < 40) {
			
			CommunityDto communityDtoFooter = CommunityDto.builder().type(posts.size()).build();

			communityDtos.add(communityDtoFooter);
			
			return new RespDto<List<CommunityDto>>(HttpStatus.NO_CONTENT.value(),"마지막페이지입니다",communityDtos);
		}

		CommunityDto communityDtoFooter = CommunityDto.builder().type(2).build();

		communityDtos.add(communityDtoFooter);
		
		if(page == 0) {
			return new RespDto<List<CommunityDto>>(HttpStatus.CREATED.value(),"첫페이지 입니다",communityDtos);
		}

		return new RespDto<List<Post>>(HttpStatus.OK.value(),"정상", posts);
	}

	// rankingDto 가져오기 아이디 검색
	@GetMapping("test/ranking/name/{name}")
	public RespDto<?> getRankByName(@PathVariable String name) {

		return apiService.getRank(name);
	}

	// rankingDto 가져오기
	@GetMapping("test/ranking/page/{page}")
	public RespDto<?> getRankByPage(@PathVariable long page) {

		return apiService.getRank(page);
	}

	// detailDto 가져오기
	@GetMapping("test/detail/gameid/{gameId}")
	public RespDto<?> getDetailByGameId(@PathVariable long gameId) {

		return apiService.getDetail(gameId);
	}

	// infoDto 가져오기
	@GetMapping("test/info/name/{name}")
	public RespDto<?> getInfoByName(@PathVariable String name) {

		return apiService.getInfo(name);
	}

	// 전적갱신하기
	@GetMapping("test/info/update/name/{name}")
	public RespDto<?> updateInfoByName(@PathVariable String name) {

		boolean isGetData = apiService.getApiData(name);

		if (isGetData) {
			return apiService.getInfo(name);
		} else {
			return new RespDto<List<InfoDto>>(HttpStatus.BAD_REQUEST.value(), "전적갱신에 실패하였습니다.", null);
		}

	}

	@GetMapping("test/input/123123123/{name}")
	public String testInput(@PathVariable String name) {

		boolean b = apiService.getApiData(name);

		if (b) {
			return "입력성공";
		} else {
			return "입력실패";
		}
	}

	@GetMapping("test/getallrank/97987987979797987987")
	public String getAllRank() {

		apiService.getAllRank();

		return "clear";
	}

	// 커뮤니티 글목록 보기
	@GetMapping("/test/post")
	public List<Post> PostTest() {
		List<Post> post = postRepository.findAll();

		return post;
	}

	// 커뮤니티 글 상세보기
//	@GetMapping("/test/post/{id}")
//	public Post ttttt(@PathVariable int id) {
//		Post post = postRepository.findById(id);
//		return post;
//
//	}

	// 커뮤니티 글 검색
	@GetMapping("/test/post/search/{content}")
	public List<Post> seach(@PathVariable(name = "content") String content) {
		List<Post> post = postRepository.search(content);
		System.out.println(post);
		return post;
	}

	@GetMapping("/reply")
	public List<Reply> find() {
		List<Reply> reply = replyRepostory.findAll();
		return reply;
	}

}
