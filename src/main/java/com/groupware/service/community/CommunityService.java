package com.service.community;

import java.util.Collections;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.common.PageRequestDTO;
import com.common.PageResponseDTO;
import com.config.MySqlSessionFactory;
import com.dao.community.CommunityDAO;
import com.dao.community.ReplyDAO;
import com.dto.community.CommunityDTO;
import com.dto.community.CommunityDetailsDTO;
import com.dto.community.ReplyDTO;



public class CommunityService {

	private static final Logger log = LoggerFactory.getLogger(CommunityService.class);
	
    private CommunityDAO communityDao;
    private ReplyDAO replyDao;

    public CommunityService() {
        this.communityDao = new CommunityDAO();
        this.replyDao = new ReplyDAO();
    }

    public void save(CommunityDTO community) {
    	SqlSession session = MySqlSessionFactory.getSession();
    	try {
    		communityDao.insert(session, community);
    		session.commit();
    	} catch (Exception e) {
    		e.printStackTrace();
    	} finally {
    		session.close();
    	}
    }

    public CommunityDTO getCommunityByNum(Long comNum) {
    	SqlSession session = MySqlSessionFactory.getSession();
    	try {
	        CommunityDTO community = communityDao.getCommunityByNum(session, comNum);
	        return community;
    	} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
    	
    	return null;
    }

    public List<CommunityDTO> getCommunityList() {
        SqlSession session = MySqlSessionFactory.getSession();
		try {
			return communityDao.getCommunityList(session);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return Collections.emptyList();
    }

    public void update(Long comNum, Long memberNum, CommunityDTO updateParam) {
		SqlSession session = MySqlSessionFactory.getSession();
		try {
	        CommunityDTO community = communityDao.getCommunityByNum(session, comNum);
	
	        if (!community.getMemberNum().equals(memberNum)) {
	            return;
	        }
	        
	        community.setTitle(updateParam.getTitle());
	        community.setContent(updateParam.getContent());
	        
	        communityDao.update(session, community);
			
	        session.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
    }

    public void delete(Long comNum, Long memberNum) {
		SqlSession session = MySqlSessionFactory.getSession();
		try {
	        CommunityDTO community = communityDao.getCommunityByNum(session, comNum);
	
	        if (!community.getMemberNum().equals(memberNum)) {
	            return;
	        }
	        
	        List<ReplyDTO> replyList = replyDao.getReplyListByComNum(session, comNum);
	        for (ReplyDTO reply : replyList) {
	            replyDao.delete(session, reply.getReplyNum());
	        }
	        communityDao.delete(session, comNum);
	        
	        session.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
    }
    
    public void increaseViews(Long comNum) {
    	SqlSession session = MySqlSessionFactory.getSession();
		try {
			communityDao.increaseViews(session, comNum);
			session.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
    }

    public CommunityDetailsDTO getCommunityDetailsByNum(Long replyNum) {
    	SqlSession session = MySqlSessionFactory.getSession();
    	try {
    		return communityDao.getCommunityDetailsByNum(session, replyNum);
    	} catch (Exception e) {
    		e.printStackTrace();
    	} finally {
    		session.close();
    	}
    	
    	return null;
    }

    
    public PageResponseDTO<CommunityDetailsDTO> getCommunityDetailsList(PageRequestDTO page) {
    	SqlSession session = MySqlSessionFactory.getSession();
    	try {
    		int count = communityDao.count(session);
    		List<CommunityDetailsDTO> communityDetailsList = communityDao.getCommunityDetailsList(session, page);
    		return new PageResponseDTO<CommunityDetailsDTO>(page, communityDetailsList, count);
    	} catch (Exception e) {
    		e.printStackTrace();
    	} finally {
    		session.close();
    	}
    	
		return new PageResponseDTO<CommunityDetailsDTO>(page, Collections.emptyList(), 0);
    }
}
