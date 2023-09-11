package com.groupware.controller.community;

import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;

import com.groupware.config.ApplicationContextProvider;
import com.groupware.dto.MemberDTO;
import com.groupware.dto.community.ReplyDTO;
import com.groupware.service.community.ReplyService;

@WebServlet("/NewReplyServlet")
public class NewReplyServlet extends HttpServlet {
	
	private static final long serialVersionUID = 6972517525890801447L;

	private ReplyService replyService;
	
	public NewReplyServlet() {
		ApplicationContext applicationContext = ApplicationContextProvider.getApplicationContext();
		this.replyService = applicationContext.getBean(ReplyService.class);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

}
