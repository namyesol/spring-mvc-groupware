package com.groupware.controller.community;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.groupware.dto.MemberDTO;
import com.groupware.dto.community.CommunityDTO;
import com.groupware.dto.community.ReplyDTO;
import com.groupware.service.community.ReplyService;

@Controller
public class ReplyController {
	
	@Autowired
	ReplyService replyService;
	
	//댓글작성
	@PostMapping("/communities/{comNum}/replies/new")
	public String newReply(
			@PathVariable("comNum") Long comNum,
			@RequestParam("content") String content,
			@RequestParam(required=false) Long parentReplyNum,
			HttpSession session) {
		MemberDTO member =(MemberDTO)session.getAttribute("login");
		
		if(member == null) {
			return "redirect:/";
		}
		Long memberNum = Long.valueOf(member.getMember_num());
		
		ReplyDTO reply = new ReplyDTO(memberNum, comNum, parentReplyNum, content);
		
		replyService.save(reply);
		
		return "redirect:"+"/communities/{comNum}";
	}
	
	//댓글수정
	@PostMapping("/communities/{comNum}/replies/{replyNum}/edit")
	public String updateReply(@PathVariable Long replyNum, @PathVariable Long comNum, @RequestParam String content, HttpSession session) {
		MemberDTO member = (MemberDTO) session.getAttribute("login");
		if (member == null) {
			return "redirect:/";
		} else {
			Long memberNum = Long.valueOf(member.getMember_num());
			
			replyService.update(replyNum, memberNum, content);
			
			return "redirect:/communities/{comNum}";
		}
	}
	
	//댓글삭제
	@PostMapping("/communities/{comNum}/replies/{replyNum}/delete")
	public String deleteReply(
			@PathVariable("comNum") Long comNum,
			@PathVariable("replyNum") Long replyNum,
			HttpSession session) {
		MemberDTO member = (MemberDTO) session.getAttribute("login");
		if (member == null) {
			return "redirect:/";
		} else {
			
			Long memberNum = Long.valueOf(member.getMember_num());

			replyService.delete(replyNum, memberNum);
			
			return "redirect:/communities/{comNum}";
		}
	}
	
}
