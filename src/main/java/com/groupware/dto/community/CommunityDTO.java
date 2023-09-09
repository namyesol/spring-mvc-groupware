package com.groupware.dto.community;

import java.util.Date;

public class CommunityDTO {

	private Long comNum;
	private Long memberNum;
	private String title;
	private String content;
	private Integer views;
	private Date createdAt;

	public CommunityDTO() {
	}

	public CommunityDTO(Long memberNum, String title, String content) {
		this.memberNum = memberNum;
		this.title = title;
		this.content = content;
		this.views = Integer.valueOf(0);
		this.createdAt = new Date();
	}

	public Long getComNum() {
		return comNum;
	}

	public void setComNum(Long comNum) {
		this.comNum = comNum;
	}

	public Long getMemberNum() {
		return memberNum;
	}

	public void setMemberNum(Long memberNum) {
		this.memberNum = memberNum;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getViews() {
		return views;
	}

	public void setViews(Integer views) {
		this.views = views;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	

	@Override
	public String toString() {
		return "CommunityDTO [comNum=" + comNum + ", memberNum=" + memberNum + ", title=" + title + ", content=" + content
				+ ", views=" + views + ", createdAt=" + createdAt + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((comNum == null) ? 0 : comNum.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CommunityDTO other = (CommunityDTO) obj;
		if (comNum == null) {
			if (other.comNum != null)
				return false;
		} else if (!comNum.equals(other.comNum))
			return false;
		return true;
	}

}
