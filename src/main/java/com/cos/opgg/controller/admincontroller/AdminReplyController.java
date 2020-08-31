package com.cos.opgg.controller.admincontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.opgg.repository.ReplyRepository;

@Controller
@RequestMapping("/admin/reply")
public class AdminReplyController {
	@Autowired
	private ReplyRepository replyRepository;
	
	@GetMapping({"","/"})
	public String reply(Model model) {
		
		model.addAttribute("replies", replyRepository.findAll());
				
		return "adminReply";
	}
	
	@DeleteMapping("delete/{id}")
	public @ResponseBody String deleteReply(@PathVariable int id ) {
		
		System.out.println("id "+ id );
		replyRepository.deleteById(id);
		
		return "성공";
	}
}
