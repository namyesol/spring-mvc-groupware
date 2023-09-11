package com.controller.community;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dto.MemberDTO;
import com.service.community.ReplyService;

@WebServlet("/DeleteReplyServlet")
public class DeleteReplyServlet extends HttpServlet {

	private static final long serialVersionUID = -1469839218469414445L;
	
	private ReplyService replyService;
	
	public DeleteReplyServlet() {
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
			Long comNum = Long.parseLong(request.getParameter("comNum"));
			Long memberNum = Long.valueOf(member.getMember_num());

			replyService.delete(replyNum, memberNum);
			
			response.sendRedirect("/CommunityDetailsServlet" + "?comNum=" + comNum);
		}
	}
}
