package com.groupware.controller.community;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import com.groupware.dto.MemberDTO;
import com.groupware.dto.community.ReplyDTO;
import com.groupware.service.community.ReplyService;

@Controller
public class ReplyController {
	
	@Autowired
	private ReplyService replyService;
	
	@PostMapping("/NewReplyServlet")
	public void newReply(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		MemberDTO member = (MemberDTO) session.getAttribute("login");
		if (member == null) {
			response.sendRedirect("/");
		} else {
			Long memberNum = Long.valueOf(member.getMember_num());
			Long comNum = Long.valueOf(request.getParameter("comNum"));
			String content = request.getParameter("content");
			Long parentReplyNum = null;
			String parentReplyParam = request.getParameter("parentReplyNum");
			if (parentReplyParam == null) {
				parentReplyNum = null;
			} else {
				parentReplyNum = Long.valueOf(parentReplyParam);
			}
			
			ReplyDTO reply = new ReplyDTO(memberNum, comNum, parentReplyNum, content);
			
			replyService.save(reply);
			
			response.sendRedirect("/CommunityDetailsServlet"+ "?comNum=" + comNum);
		}
	}
	
	@PostMapping("/EditReplyServlet")
	public void updateReply(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		MemberDTO member = (MemberDTO) session.getAttribute("login");
		if (member == null) {
			response.sendRedirect("/");
		} else {
			Long replyNum = Long.parseLong(request.getParameter("replyNum"));
			Long memberNum = Long.valueOf(member.getMember_num());
			Long comNum = Long.parseLong(request.getParameter("comNum"));
			String content = request.getParameter("content");
			
			ReplyDTO updateDTO = new ReplyDTO();
			updateDTO.setContent(content);
			replyService.update(replyNum, memberNum, updateDTO);
			
			response.sendRedirect("/CommunityDetailsServlet" + "?comNum=" + comNum);
		}
	}
	
	@PostMapping("/DeleteReplyServlet")
	public void deleteReply(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		MemberDTO member = (MemberDTO) session.getAttribute("login");
		if (member == null) {
			response.sendRedirect("/");
		} else {
			Long replyNum = Long.parseLong(request.getParameter("replyNum"));
			Long comNum = Long.parseLong(request.getParameter("comNum"));
			Long memberNum = Long.valueOf(member.getMember_num());

			replyService.delete(replyNum, memberNum);
			
			response.sendRedirect("/CommunityDetailsServlet" + "?comNum=" + comNum);
		}
	}
}
