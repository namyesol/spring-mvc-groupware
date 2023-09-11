package com.groupware.controller.community;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;

import com.groupware.config.ApplicationContextProvider;
import com.groupware.dto.MemberDTO;
import com.groupware.dto.community.CommunityDetailsDTO;
import com.groupware.dto.community.ReplyDetailsDTO;
import com.groupware.service.community.CommunityService;
import com.groupware.service.community.ReplyService;

@WebServlet("/CommunityDetailsServlet")
public class CommunityDetailsServlet extends HttpServlet {
	
	private static final long serialVersionUID = 4666471117727119815L;
	
	private CommunityService communityService;
	private ReplyService replyService;
	
	public CommunityDetailsServlet() {
		ApplicationContext applicationContext = ApplicationContextProvider.getApplicationContext();
		this.communityService = applicationContext.getBean(CommunityService.class);
		this.replyService = applicationContext.getBean(ReplyService.class);
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		MemberDTO member = (MemberDTO) session.getAttribute("login");
		if (member == null) {
			response.sendRedirect("/");
		} else {
			long comNum = Long.parseLong(request.getParameter("comNum"));

			communityService.increaseViews(comNum);
			
			CommunityDetailsDTO communityDetails = communityService.getCommunityDetailsByNum(comNum);
			List<ReplyDetailsDTO> replyDetailsList = replyService.getReplyDetailsListByComNum(comNum);
			
			request.setAttribute("communityDetails", communityDetails);
			request.setAttribute("replyDetailsList", replyDetailsList);

			String nextPage = "WEB-INF/views/community/community-details.jsp";
			RequestDispatcher dispatcher = request.getRequestDispatcher(nextPage);
			dispatcher.forward(request, response);
		}
	}

}
