package com.groupware.service.community;

import java.util.Collections;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.groupware.common.PageRequestDTO;
import com.groupware.common.PageResponseDTO;
import com.groupware.dao.community.CommunityDAO;
import com.groupware.dao.community.ReplyDAO;
import com.groupware.dto.community.CommunityDTO;
import com.groupware.dto.community.CommunityDetailsDTO;
import com.groupware.dto.community.ReplyDTO;

@Service
public class CommunityService {

	//private static final Logger log = LoggerFactory.getLogger(CommunityService.class);
	
	@Autowired
    private CommunityDAO communityDao;
	@Autowired
    private ReplyDAO replyDao;


    public void save(CommunityDTO community) {

    		communityDao.insert(community);
    		
    }

    public CommunityDTO getCommunityByNum(Long comNum) {
    	
	        CommunityDTO community = communityDao.getCommunityByNum(comNum);
	        return community;
    	
    }

    public List<CommunityDTO> getCommunityList() {

			return communityDao.getCommunityList();
    }

    public void update(Long comNum, Long memberNum, CommunityDTO updateParam) {
		
	        CommunityDTO community = communityDao.getCommunityByNum(comNum);
	
	        if (!community.getMemberNum().equals(memberNum)) {
	            return;
	        }
	        
	        community.setTitle(updateParam.getTitle());
	        community.setContent(updateParam.getContent());
	        
	        communityDao.update(community);
    }

    public void delete(Long comNum, Long memberNum) {
		
	        CommunityDTO community = communityDao.getCommunityByNum(comNum);
	
	        if (!community.getMemberNum().equals(memberNum)) {
	            return;
	        }
	        
	        List<ReplyDTO> replyList = replyDao.getReplyListByComNum(comNum);
	        for (ReplyDTO reply : replyList) {
	            replyDao.delete(reply.getReplyNum());
	        }
	        communityDao.delete(comNum);

    }
    
    public void increaseViews(Long comNum) {
    	
			communityDao.increaseViews(comNum);
    }

    public CommunityDetailsDTO getCommunityDetailsByNum(Long replyNum) {
    	
    		return communityDao.getCommunityDetailsByNum(replyNum);
    }

    
    public PageResponseDTO<CommunityDetailsDTO> getCommunityDetailsList(PageRequestDTO page) {

    		int count = communityDao.count();
    		
    		List<CommunityDetailsDTO> communityDetailsList = communityDao.getCommunityDetailsList(page);
    		
    		return new PageResponseDTO<CommunityDetailsDTO>(page, communityDetailsList, count);

			/*
			 * return new PageResponseDTO<CommunityDetailsDTO>(page,
			 * Collections.emptyList(), 0);
			 */
    }
}
