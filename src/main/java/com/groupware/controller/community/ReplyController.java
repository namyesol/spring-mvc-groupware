package com.groupware.controller.community;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.groupware.dto.MemberDTO;
import com.groupware.dto.community.ReplyDTO;
import com.groupware.service.community.ReplyService;

@Controller
public class ReplyController {
	
	@Autowired
	private ReplyService replyService;
	
	@PostMapping("/communities/{comNum}/replies/new")
	public String newReply(@PathVariable Long comNum, @RequestParam(required=false) Long parentReplyNum, @RequestParam String content, HttpSession session) {
		MemberDTO member = (MemberDTO) session.getAttribute("login");
		Long memberNum = Long.valueOf(member.getMember_num());
		
		ReplyDTO reply = new ReplyDTO(memberNum, comNum, parentReplyNum, content);
		
		replyService.save(reply);
		
		return "redirect:" + "/communities/{comNum}";
	}
	
	@PostMapping("/communities/{comNum}/replies/{replyNum}/edit")
	public String updateReply(@PathVariable Long replyNum, @PathVariable Long comNum, @RequestParam String content, HttpSession session) {
		MemberDTO member = (MemberDTO) session.getAttribute("login");
		Long memberNum = Long.valueOf(member.getMember_num());
		
		ReplyDTO updateDTO = new ReplyDTO();
		updateDTO.setContent(content);
		replyService.update(replyNum, memberNum, updateDTO);
		
		return "redirect:" + "/communities/{comNum}";
	}
	
	@PostMapping("/communities/{comNum}/replies/{replyNum}/delete")
	public String deleteReply(@PathVariable Long replyNum, @PathVariable Long comNum, HttpSession session) {
		MemberDTO member = (MemberDTO) session.getAttribute("login");
		Long memberNum = Long.valueOf(member.getMember_num());

		replyService.delete(replyNum, memberNum);
		
		return "redirect:" + "/communities/{comNum}";
	}
}
