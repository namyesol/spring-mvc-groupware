package com.groupware.controller.community;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.groupware.dto.MemberDTO;
import com.groupware.dto.community.ReplyDTO;
import com.groupware.service.community.ReplyService;

@Controller
public class ReplyController {
	
	@Autowired
	private ReplyService replyService;
	
	@PostMapping("/NewReplyServlet")
	public String newReply(@RequestParam Long comNum, @RequestParam(required=false) Long parentReplyNum, @RequestParam String content, HttpSession session) {
		MemberDTO member = (MemberDTO) session.getAttribute("login");
		if (member == null) {
			return "redirect:/";
		} else {
			Long memberNum = Long.valueOf(member.getMember_num());
			
			ReplyDTO reply = new ReplyDTO(memberNum, comNum, parentReplyNum, content);
			
			replyService.save(reply);
			
			return "redirect:" + "/CommunityDetailsServlet"+ "?comNum=" + comNum;
		}
	}
	
	@PostMapping("/EditReplyServlet")
	public String updateReply(@RequestParam Long replyNum, @RequestParam Long comNum, @RequestParam String content, HttpSession session) {
		MemberDTO member = (MemberDTO) session.getAttribute("login");
		if (member == null) {
			return "redirect:/";
		} else {
			Long memberNum = Long.valueOf(member.getMember_num());
			
			ReplyDTO updateDTO = new ReplyDTO();
			updateDTO.setContent(content);
			replyService.update(replyNum, memberNum, updateDTO);
			
			return "redirect:" + "/CommunityDetailsServlet" + "?comNum=" + comNum;
		}
	}
	
	@PostMapping("/DeleteReplyServlet")
	public String deleteReply(@RequestParam Long replyNum, @RequestParam Long comNum, HttpSession session) {
		MemberDTO member = (MemberDTO) session.getAttribute("login");
		if (member == null) {
			return "redirect:/";
		} else {
			Long memberNum = Long.valueOf(member.getMember_num());

			replyService.delete(replyNum, memberNum);
			
			return "redirect:" + "/CommunityDetailsServlet" + "?comNum=" + comNum;
		}
	}
}
