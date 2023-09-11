package com.groupware.service.community;

import java.util.Collections;
import java.util.List;

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

	@Autowired
    private CommunityDAO communityDao;
	
	@Autowired
    private ReplyDAO replyDao;

    public void save(CommunityDTO community) {
		communityDao.insert(community);
    }

    public CommunityDTO getCommunityByNum(Long comNum) {
        return communityDao.getCommunityByNum(comNum);
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
		if (!communityDetailsList.isEmpty()) {
			return new PageResponseDTO<CommunityDetailsDTO>(page, communityDetailsList, count);
		}
	
		return new PageResponseDTO<CommunityDetailsDTO>(page, Collections.emptyList(), 0);
    }
}
