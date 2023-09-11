package com.controller.community;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dto.MemberDTO;
import com.dto.community.ReplyDTO;
import com.service.community.ReplyService;

@WebServlet("/EditReplyServlet")
public class EditReplyServlet extends HttpServlet {

	private static final long serialVersionUID = -7449134374811439274L;
	
	private ReplyService replyService;
	
	public EditReplyServlet() {
		this.replyService = new ReplyService();
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
	
	
}
