package com.cos.opgg.controller.admincontroller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.opgg.model.Reply;
import com.cos.opgg.model.User;
import com.cos.opgg.repository.ReplyRepository;

@Controller
@RequestMapping("/admin/reply")
public class AdminReplyController {
	@Autowired
	private ReplyRepository replyRepository;
	
	//댓글 검색기능
	@PostMapping("/search")
	public @ResponseBody List<Reply> search(@RequestBody Map<String, String> data) {
		
		return replyRepository.findByReply(data.get("reply"));
	}
	
	// 댓글 모두 가져오기
	@GetMapping({"","/"})
	public String reply(Model model) {
		
		model.addAttribute("replies", replyRepository.findAllByOrderByIdDesc());
				
		return "adminReply";
	}
	
	// 댓글 지우기
	@DeleteMapping("delete/{id}")
	public @ResponseBody String deleteReply(@PathVariable int id ) {
		
		replyRepository.deleteById(id);
		
		return "성공";
	}
}
