package edu.web.board.domain;

public class BoardVO {
	private int bno;
	private String title;
	private String content;
	private String userid;
	private String cdate;

	// 생성자
	public BoardVO() {
	}

	public BoardVO(int bno, String title, String content, String userid, String cdate) {
		super();
		this.bno = bno;
		this.title = title;
		this.content = content;
		this.userid = userid;
		this.cdate = cdate;
	}
	// end 생성자

	// getter, setter
	public int getBno() {
		return bno;
	}

	public void setBno(int bno) {
		this.bno = bno;
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

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getCdate() {
		return cdate;
	}

	public void setCdate(String cdate) {
		this.cdate = cdate;
	}
	// end getter, setter

	@Override
	public String toString() {
		String str = "글 번호 : " + bno + ", 제목 : " + title + ", 내용 : " + content + ", 글쓴이 : " + userid + ", 작성일 : " + cdate;
		return str;
	} // end toString()

} // end BoardVO